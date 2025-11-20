package net.minecraft.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.StructureFeature;

public class FeatureUpdater {
   private static final Map<String, String> OLD_TO_NEW = Util.make(Maps.newHashMap(), hashMap -> {
      hashMap.put("Village", "Village");
      hashMap.put("Mineshaft", "Mineshaft");
      hashMap.put("Mansion", "Mansion");
      hashMap.put("Igloo", "Temple");
      hashMap.put("Desert_Pyramid", "Temple");
      hashMap.put("Jungle_Pyramid", "Temple");
      hashMap.put("Swamp_Hut", "Temple");
      hashMap.put("Stronghold", "Stronghold");
      hashMap.put("Monument", "Monument");
      hashMap.put("Fortress", "Fortress");
      hashMap.put("EndCity", "EndCity");
   });
   private static final Map<String, String> ANCIENT_TO_OLD = Util.make(Maps.newHashMap(), hashMap -> {
      hashMap.put("Iglu", "Igloo");
      hashMap.put("TeDP", "Desert_Pyramid");
      hashMap.put("TeJP", "Jungle_Pyramid");
      hashMap.put("TeSH", "Swamp_Hut");
   });
   private final boolean needsUpdate;
   private final Map<String, Long2ObjectMap<CompoundTag>> featureIdToChunkTag = Maps.newHashMap();
   private final Map<String, ChunkUpdateState> updateStates = Maps.newHashMap();
   private final List<String> field_17658;
   private final List<String> field_17659;

   public FeatureUpdater(@Nullable PersistentStateManager arg, List<String> list, List<String> list2) {
      this.field_17658 = list;
      this.field_17659 = list2;
      this.init(arg);
      boolean bl = false;

      for (String string : this.field_17659) {
         bl |= this.featureIdToChunkTag.get(string) != null;
      }

      this.needsUpdate = bl;
   }

   public void markResolved(long l) {
      for (String string : this.field_17658) {
         ChunkUpdateState lv = this.updateStates.get(string);
         if (lv != null && lv.isRemaining(l)) {
            lv.markResolved(l);
            lv.markDirty();
         }
      }
   }

   public CompoundTag getUpdatedReferences(CompoundTag arg) {
      CompoundTag lv = arg.getCompound("Level");
      ChunkPos lv2 = new ChunkPos(lv.getInt("xPos"), lv.getInt("zPos"));
      if (this.needsUpdate(lv2.x, lv2.z)) {
         arg = this.getUpdatedStarts(arg, lv2);
      }

      CompoundTag lv3 = lv.getCompound("Structures");
      CompoundTag lv4 = lv3.getCompound("References");

      for (String string : this.field_17659) {
         StructureFeature<?> lv5 = (StructureFeature<?>)StructureFeature.STRUCTURES.get(string.toLowerCase(Locale.ROOT));
         if (!lv4.contains(string, 12) && lv5 != null) {
            int i = 8;
            LongList longList = new LongArrayList();

            for (int j = lv2.x - 8; j <= lv2.x + 8; j++) {
               for (int k = lv2.z - 8; k <= lv2.z + 8; k++) {
                  if (this.needsUpdate(j, k, string)) {
                     longList.add(ChunkPos.toLong(j, k));
                  }
               }
            }

            lv4.putLongArray(string, longList);
         }
      }

      lv3.put("References", lv4);
      lv.put("Structures", lv3);
      arg.put("Level", lv);
      return arg;
   }

   private boolean needsUpdate(int chunkX, int chunkZ, String id) {
      return !this.needsUpdate
         ? false
         : this.featureIdToChunkTag.get(id) != null && this.updateStates.get(OLD_TO_NEW.get(id)).contains(ChunkPos.toLong(chunkX, chunkZ));
   }

   private boolean needsUpdate(int chunkX, int chunkZ) {
      if (!this.needsUpdate) {
         return false;
      } else {
         for (String string : this.field_17659) {
            if (this.featureIdToChunkTag.get(string) != null && this.updateStates.get(OLD_TO_NEW.get(string)).isRemaining(ChunkPos.toLong(chunkX, chunkZ))) {
               return true;
            }
         }

         return false;
      }
   }

   private CompoundTag getUpdatedStarts(CompoundTag arg, ChunkPos arg2) {
      CompoundTag lv = arg.getCompound("Level");
      CompoundTag lv2 = lv.getCompound("Structures");
      CompoundTag lv3 = lv2.getCompound("Starts");

      for (String string : this.field_17659) {
         Long2ObjectMap<CompoundTag> long2ObjectMap = this.featureIdToChunkTag.get(string);
         if (long2ObjectMap != null) {
            long l = arg2.toLong();
            if (this.updateStates.get(OLD_TO_NEW.get(string)).isRemaining(l)) {
               CompoundTag lv4 = (CompoundTag)long2ObjectMap.get(l);
               if (lv4 != null) {
                  lv3.put(string, lv4);
               }
            }
         }
      }

      lv2.put("Starts", lv3);
      lv.put("Structures", lv2);
      arg.put("Level", lv);
      return arg;
   }

   private void init(@Nullable PersistentStateManager arg) {
      if (arg != null) {
         for (String string : this.field_17658) {
            CompoundTag lv = new CompoundTag();

            try {
               lv = arg.readTag(string, 1493).getCompound("data").getCompound("Features");
               if (lv.isEmpty()) {
                  continue;
               }
            } catch (IOException var13) {
            }

            for (String string2 : lv.getKeys()) {
               CompoundTag lv2 = lv.getCompound(string2);
               long l = ChunkPos.toLong(lv2.getInt("ChunkX"), lv2.getInt("ChunkZ"));
               ListTag lv3 = lv2.getList("Children", 10);
               if (!lv3.isEmpty()) {
                  String string3 = lv3.getCompound(0).getString("id");
                  String string4 = ANCIENT_TO_OLD.get(string3);
                  if (string4 != null) {
                     lv2.putString("id", string4);
                  }
               }

               String string5 = lv2.getString("id");
               this.featureIdToChunkTag.computeIfAbsent(string5, stringx -> new Long2ObjectOpenHashMap()).put(l, lv2);
            }

            String string6 = string + "_index";
            ChunkUpdateState lv4 = arg.getOrCreate(() -> new ChunkUpdateState(string6), string6);
            if (!lv4.getAll().isEmpty()) {
               this.updateStates.put(string, lv4);
            } else {
               ChunkUpdateState lv5 = new ChunkUpdateState(string6);
               this.updateStates.put(string, lv5);

               for (String string7 : lv.getKeys()) {
                  CompoundTag lv6 = lv.getCompound(string7);
                  lv5.add(ChunkPos.toLong(lv6.getInt("ChunkX"), lv6.getInt("ChunkZ")));
               }

               lv5.markDirty();
            }
         }
      }
   }

   public static FeatureUpdater create(RegistryKey<World> arg, @Nullable PersistentStateManager arg2) {
      if (arg == World.OVERWORLD) {
         return new FeatureUpdater(
            arg2,
            ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
            ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
         );
      } else if (arg == World.NETHER) {
         List<String> list = ImmutableList.of("Fortress");
         return new FeatureUpdater(arg2, list, list);
      } else if (arg == World.END) {
         List<String> list2 = ImmutableList.of("EndCity");
         return new FeatureUpdater(arg2, list2, list2);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", arg));
      }
   }
}
