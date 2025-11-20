package net.minecraft.world;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class Heightmap {
   private static final Predicate<BlockState> ALWAYS_TRUE = arg -> !arg.isAir();
   private static final Predicate<BlockState> SUFFOCATES = arg -> arg.getMaterial().blocksMovement();
   private final PackedIntegerArray storage = new PackedIntegerArray(9, 256);
   private final Predicate<BlockState> blockPredicate;
   private final Chunk chunk;

   public Heightmap(Chunk arg, Heightmap.Type type) {
      this.blockPredicate = type.getBlockPredicate();
      this.chunk = arg;
   }

   public static void populateHeightmaps(Chunk arg, Set<Heightmap.Type> types) {
      int i = types.size();
      ObjectList<Heightmap> objectList = new ObjectArrayList(i);
      ObjectListIterator<Heightmap> objectListIterator = objectList.iterator();
      int j = arg.getHighestNonEmptySectionYOffset() + 16;
      BlockPos.Mutable lv = new BlockPos.Mutable();

      for (int k = 0; k < 16; k++) {
         for (int l = 0; l < 16; l++) {
            for (Heightmap.Type lv2 : types) {
               objectList.add(arg.getHeightmap(lv2));
            }

            for (int m = j - 1; m >= 0; m--) {
               lv.set(k, m, l);
               BlockState lv3 = arg.getBlockState(lv);
               if (!lv3.isOf(Blocks.AIR)) {
                  while (objectListIterator.hasNext()) {
                     Heightmap lv4 = (Heightmap)objectListIterator.next();
                     if (lv4.blockPredicate.test(lv3)) {
                        lv4.set(k, l, m + 1);
                        objectListIterator.remove();
                     }
                  }

                  if (objectList.isEmpty()) {
                     break;
                  }

                  objectListIterator.back(i);
               }
            }
         }
      }
   }

   public boolean trackUpdate(int x, int y, int z, BlockState state) {
      int l = this.get(x, z);
      if (y <= l - 2) {
         return false;
      } else {
         if (this.blockPredicate.test(state)) {
            if (y >= l) {
               this.set(x, z, y + 1);
               return true;
            }
         } else if (l - 1 == y) {
            BlockPos.Mutable lv = new BlockPos.Mutable();

            for (int m = y - 1; m >= 0; m--) {
               lv.set(x, m, z);
               if (this.blockPredicate.test(this.chunk.getBlockState(lv))) {
                  this.set(x, z, m + 1);
                  return true;
               }
            }

            this.set(x, z, 0);
            return true;
         }

         return false;
      }
   }

   public int get(int x, int z) {
      return this.get(toIndex(x, z));
   }

   private int get(int index) {
      return this.storage.get(index);
   }

   private void set(int x, int z, int height) {
      this.storage.set(toIndex(x, z), height);
   }

   public void setTo(long[] heightmap) {
      System.arraycopy(heightmap, 0, this.storage.getStorage(), 0, heightmap.length);
   }

   public long[] asLongArray() {
      return this.storage.getStorage();
   }

   private static int toIndex(int x, int z) {
      return x + z * 16;
   }

   public static enum Purpose {
      WORLDGEN,
      LIVE_WORLD,
      CLIENT;

      private Purpose() {
      }
   }

   public static enum Type implements StringIdentifiable {
      WORLD_SURFACE_WG("WORLD_SURFACE_WG", Heightmap.Purpose.WORLDGEN, Heightmap.ALWAYS_TRUE),
      WORLD_SURFACE("WORLD_SURFACE", Heightmap.Purpose.CLIENT, Heightmap.ALWAYS_TRUE),
      OCEAN_FLOOR_WG("OCEAN_FLOOR_WG", Heightmap.Purpose.WORLDGEN, Heightmap.SUFFOCATES),
      OCEAN_FLOOR("OCEAN_FLOOR", Heightmap.Purpose.LIVE_WORLD, Heightmap.SUFFOCATES),
      MOTION_BLOCKING("MOTION_BLOCKING", Heightmap.Purpose.CLIENT, arg -> arg.getMaterial().blocksMovement() || !arg.getFluidState().isEmpty()),
      MOTION_BLOCKING_NO_LEAVES(
         "MOTION_BLOCKING_NO_LEAVES",
         Heightmap.Purpose.LIVE_WORLD,
         arg -> (arg.getMaterial().blocksMovement() || !arg.getFluidState().isEmpty()) && !(arg.getBlock() instanceof LeavesBlock)
      );

      public static final Codec<Heightmap.Type> CODEC = StringIdentifiable.createCodec(Heightmap.Type::values, Heightmap.Type::byName);
      private final String name;
      private final Heightmap.Purpose purpose;
      private final Predicate<BlockState> blockPredicate;
      private static final Map<String, Heightmap.Type> BY_NAME = Util.make(Maps.newHashMap(), hashMap -> {
         for (Heightmap.Type lv : values()) {
            hashMap.put(lv.name, lv);
         }
      });

      private Type(String name, Heightmap.Purpose purpose, Predicate<BlockState> blockPredicate) {
         this.name = name;
         this.purpose = purpose;
         this.blockPredicate = blockPredicate;
      }

      public String getName() {
         return this.name;
      }

      public boolean shouldSendToClient() {
         return this.purpose == Heightmap.Purpose.CLIENT;
      }

      @Environment(EnvType.CLIENT)
      public boolean isStoredServerSide() {
         return this.purpose != Heightmap.Purpose.WORLDGEN;
      }

      @Nullable
      public static Heightmap.Type byName(String name) {
         return BY_NAME.get(name);
      }

      public Predicate<BlockState> getBlockPredicate() {
         return this.blockPredicate;
      }

      @Override
      public String asString() {
         return this.name;
      }
   }
}
