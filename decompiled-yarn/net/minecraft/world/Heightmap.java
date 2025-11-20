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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

public class Heightmap {
   private static final Predicate<BlockState> ALWAYS_TRUE = _snowman -> !_snowman.isAir();
   private static final Predicate<BlockState> SUFFOCATES = _snowman -> _snowman.getMaterial().blocksMovement();
   private final PackedIntegerArray storage = new PackedIntegerArray(9, 256);
   private final Predicate<BlockState> blockPredicate;
   private final Chunk chunk;

   public Heightmap(Chunk _snowman, Heightmap.Type type) {
      this.blockPredicate = type.getBlockPredicate();
      this.chunk = _snowman;
   }

   public static void populateHeightmaps(Chunk _snowman, Set<Heightmap.Type> types) {
      int _snowmanx = types.size();
      ObjectList<Heightmap> _snowmanxx = new ObjectArrayList(_snowmanx);
      ObjectListIterator<Heightmap> _snowmanxxx = _snowmanxx.iterator();
      int _snowmanxxxx = _snowman.getHighestNonEmptySectionYOffset() + 16;
      BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
            for (Heightmap.Type _snowmanxxxxxxxx : types) {
               _snowmanxx.add(_snowman.getHeightmap(_snowmanxxxxxxxx));
            }

            for (int _snowmanxxxxxxxx = _snowmanxxxx - 1; _snowmanxxxxxxxx >= 0; _snowmanxxxxxxxx--) {
               _snowmanxxxxx.set(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
               BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
               if (!_snowmanxxxxxxxxx.isOf(Blocks.AIR)) {
                  while (_snowmanxxx.hasNext()) {
                     Heightmap _snowmanxxxxxxxxxx = (Heightmap)_snowmanxxx.next();
                     if (_snowmanxxxxxxxxxx.blockPredicate.test(_snowmanxxxxxxxxx)) {
                        _snowmanxxxxxxxxxx.set(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx + 1);
                        _snowmanxxx.remove();
                     }
                  }

                  if (_snowmanxx.isEmpty()) {
                     break;
                  }

                  _snowmanxxx.back(_snowmanx);
               }
            }
         }
      }
   }

   public boolean trackUpdate(int x, int y, int z, BlockState state) {
      int _snowman = this.get(x, z);
      if (y <= _snowman - 2) {
         return false;
      } else {
         if (this.blockPredicate.test(state)) {
            if (y >= _snowman) {
               this.set(x, z, y + 1);
               return true;
            }
         } else if (_snowman - 1 == y) {
            BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

            for (int _snowmanxx = y - 1; _snowmanxx >= 0; _snowmanxx--) {
               _snowmanx.set(x, _snowmanxx, z);
               if (this.blockPredicate.test(this.chunk.getBlockState(_snowmanx))) {
                  this.set(x, z, _snowmanxx + 1);
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
      MOTION_BLOCKING("MOTION_BLOCKING", Heightmap.Purpose.CLIENT, _snowman -> _snowman.getMaterial().blocksMovement() || !_snowman.getFluidState().isEmpty()),
      MOTION_BLOCKING_NO_LEAVES(
         "MOTION_BLOCKING_NO_LEAVES",
         Heightmap.Purpose.LIVE_WORLD,
         _snowman -> (_snowman.getMaterial().blocksMovement() || !_snowman.getFluidState().isEmpty()) && !(_snowman.getBlock() instanceof LeavesBlock)
      );

      public static final Codec<Heightmap.Type> CODEC = StringIdentifiable.createCodec(Heightmap.Type::values, Heightmap.Type::byName);
      private final String name;
      private final Heightmap.Purpose purpose;
      private final Predicate<BlockState> blockPredicate;
      private static final Map<String, Heightmap.Type> BY_NAME = Util.make(Maps.newHashMap(), _snowman -> {
         for (Heightmap.Type _snowmanx : values()) {
            _snowman.put(_snowmanx.name, _snowmanx);
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
