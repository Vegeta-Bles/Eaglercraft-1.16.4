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
   private static final Map<String, String> OLD_TO_NEW = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("Village", "Village");
      _snowman.put("Mineshaft", "Mineshaft");
      _snowman.put("Mansion", "Mansion");
      _snowman.put("Igloo", "Temple");
      _snowman.put("Desert_Pyramid", "Temple");
      _snowman.put("Jungle_Pyramid", "Temple");
      _snowman.put("Swamp_Hut", "Temple");
      _snowman.put("Stronghold", "Stronghold");
      _snowman.put("Monument", "Monument");
      _snowman.put("Fortress", "Fortress");
      _snowman.put("EndCity", "EndCity");
   });
   private static final Map<String, String> ANCIENT_TO_OLD = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("Iglu", "Igloo");
      _snowman.put("TeDP", "Desert_Pyramid");
      _snowman.put("TeJP", "Jungle_Pyramid");
      _snowman.put("TeSH", "Swamp_Hut");
   });
   private final boolean needsUpdate;
   private final Map<String, Long2ObjectMap<CompoundTag>> featureIdToChunkTag = Maps.newHashMap();
   private final Map<String, ChunkUpdateState> updateStates = Maps.newHashMap();
   private final List<String> field_17658;
   private final List<String> field_17659;

   public FeatureUpdater(@Nullable PersistentStateManager _snowman, List<String> _snowman, List<String> _snowman) {
      this.field_17658 = _snowman;
      this.field_17659 = _snowman;
      this.init(_snowman);
      boolean _snowmanxxx = false;

      for (String _snowmanxxxx : this.field_17659) {
         _snowmanxxx |= this.featureIdToChunkTag.get(_snowmanxxxx) != null;
      }

      this.needsUpdate = _snowmanxxx;
   }

   public void markResolved(long _snowman) {
      for (String _snowmanx : this.field_17658) {
         ChunkUpdateState _snowmanxx = this.updateStates.get(_snowmanx);
         if (_snowmanxx != null && _snowmanxx.isRemaining(_snowman)) {
            _snowmanxx.markResolved(_snowman);
            _snowmanxx.markDirty();
         }
      }
   }

   public CompoundTag getUpdatedReferences(CompoundTag _snowman) {
      CompoundTag _snowmanx = _snowman.getCompound("Level");
      ChunkPos _snowmanxx = new ChunkPos(_snowmanx.getInt("xPos"), _snowmanx.getInt("zPos"));
      if (this.needsUpdate(_snowmanxx.x, _snowmanxx.z)) {
         _snowman = this.getUpdatedStarts(_snowman, _snowmanxx);
      }

      CompoundTag _snowmanxxx = _snowmanx.getCompound("Structures");
      CompoundTag _snowmanxxxx = _snowmanxxx.getCompound("References");

      for (String _snowmanxxxxx : this.field_17659) {
         StructureFeature<?> _snowmanxxxxxx = (StructureFeature<?>)StructureFeature.STRUCTURES.get(_snowmanxxxxx.toLowerCase(Locale.ROOT));
         if (!_snowmanxxxx.contains(_snowmanxxxxx, 12) && _snowmanxxxxxx != null) {
            int _snowmanxxxxxxx = 8;
            LongList _snowmanxxxxxxxx = new LongArrayList();

            for (int _snowmanxxxxxxxxx = _snowmanxx.x - 8; _snowmanxxxxxxxxx <= _snowmanxx.x + 8; _snowmanxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxx = _snowmanxx.z - 8; _snowmanxxxxxxxxxx <= _snowmanxx.z + 8; _snowmanxxxxxxxxxx++) {
                  if (this.needsUpdate(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxx)) {
                     _snowmanxxxxxxxx.add(ChunkPos.toLong(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx));
                  }
               }
            }

            _snowmanxxxx.putLongArray(_snowmanxxxxx, _snowmanxxxxxxxx);
         }
      }

      _snowmanxxx.put("References", _snowmanxxxx);
      _snowmanx.put("Structures", _snowmanxxx);
      _snowman.put("Level", _snowmanx);
      return _snowman;
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
         for (String _snowman : this.field_17659) {
            if (this.featureIdToChunkTag.get(_snowman) != null && this.updateStates.get(OLD_TO_NEW.get(_snowman)).isRemaining(ChunkPos.toLong(chunkX, chunkZ))) {
               return true;
            }
         }

         return false;
      }
   }

   private CompoundTag getUpdatedStarts(CompoundTag _snowman, ChunkPos _snowman) {
      CompoundTag _snowmanxx = _snowman.getCompound("Level");
      CompoundTag _snowmanxxx = _snowmanxx.getCompound("Structures");
      CompoundTag _snowmanxxxx = _snowmanxxx.getCompound("Starts");

      for (String _snowmanxxxxx : this.field_17659) {
         Long2ObjectMap<CompoundTag> _snowmanxxxxxx = this.featureIdToChunkTag.get(_snowmanxxxxx);
         if (_snowmanxxxxxx != null) {
            long _snowmanxxxxxxx = _snowman.toLong();
            if (this.updateStates.get(OLD_TO_NEW.get(_snowmanxxxxx)).isRemaining(_snowmanxxxxxxx)) {
               CompoundTag _snowmanxxxxxxxx = (CompoundTag)_snowmanxxxxxx.get(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx != null) {
                  _snowmanxxxx.put(_snowmanxxxxx, _snowmanxxxxxxxx);
               }
            }
         }
      }

      _snowmanxxx.put("Starts", _snowmanxxxx);
      _snowmanxx.put("Structures", _snowmanxxx);
      _snowman.put("Level", _snowmanxx);
      return _snowman;
   }

   private void init(@Nullable PersistentStateManager _snowman) {
      if (_snowman != null) {
         for (String _snowmanx : this.field_17658) {
            CompoundTag _snowmanxx = new CompoundTag();

            try {
               _snowmanxx = _snowman.readTag(_snowmanx, 1493).getCompound("data").getCompound("Features");
               if (_snowmanxx.isEmpty()) {
                  continue;
               }
            } catch (IOException var13) {
            }

            for (String _snowmanxxx : _snowmanxx.getKeys()) {
               CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
               long _snowmanxxxxx = ChunkPos.toLong(_snowmanxxxx.getInt("ChunkX"), _snowmanxxxx.getInt("ChunkZ"));
               ListTag _snowmanxxxxxx = _snowmanxxxx.getList("Children", 10);
               if (!_snowmanxxxxxx.isEmpty()) {
                  String _snowmanxxxxxxx = _snowmanxxxxxx.getCompound(0).getString("id");
                  String _snowmanxxxxxxxx = ANCIENT_TO_OLD.get(_snowmanxxxxxxx);
                  if (_snowmanxxxxxxxx != null) {
                     _snowmanxxxx.putString("id", _snowmanxxxxxxxx);
                  }
               }

               String _snowmanxxxxxxx = _snowmanxxxx.getString("id");
               this.featureIdToChunkTag.computeIfAbsent(_snowmanxxxxxxx, _snowmanxxxxxxxx -> new Long2ObjectOpenHashMap()).put(_snowmanxxxxx, _snowmanxxxx);
            }

            String _snowmanxxx = _snowmanx + "_index";
            ChunkUpdateState _snowmanxxxx = _snowman.getOrCreate(() -> new ChunkUpdateState(_snowman), _snowmanxxx);
            if (!_snowmanxxxx.getAll().isEmpty()) {
               this.updateStates.put(_snowmanx, _snowmanxxxx);
            } else {
               ChunkUpdateState _snowmanxxxxx = new ChunkUpdateState(_snowmanxxx);
               this.updateStates.put(_snowmanx, _snowmanxxxxx);

               for (String _snowmanxxxxxx : _snowmanxx.getKeys()) {
                  CompoundTag _snowmanxxxxxxx = _snowmanxx.getCompound(_snowmanxxxxxx);
                  _snowmanxxxxx.add(ChunkPos.toLong(_snowmanxxxxxxx.getInt("ChunkX"), _snowmanxxxxxxx.getInt("ChunkZ")));
               }

               _snowmanxxxxx.markDirty();
            }
         }
      }
   }

   public static FeatureUpdater create(RegistryKey<World> _snowman, @Nullable PersistentStateManager _snowman) {
      if (_snowman == World.OVERWORLD) {
         return new FeatureUpdater(
            _snowman,
            ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
            ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
         );
      } else if (_snowman == World.NETHER) {
         List<String> _snowmanxx = ImmutableList.of("Fortress");
         return new FeatureUpdater(_snowman, _snowmanxx, _snowmanxx);
      } else if (_snowman == World.END) {
         List<String> _snowmanxx = ImmutableList.of("EndCity");
         return new FeatureUpdater(_snowman, _snowmanxx, _snowmanxx);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", _snowman));
      }
   }
}
