package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class UnderwaterCaveCarver extends CaveCarver {
   public UnderwaterCaveCarver(Codec<ProbabilityConfig> _snowman) {
      super(_snowman, 256);
      this.alwaysCarvableBlocks = ImmutableSet.of(
         Blocks.STONE,
         Blocks.GRANITE,
         Blocks.DIORITE,
         Blocks.ANDESITE,
         Blocks.DIRT,
         Blocks.COARSE_DIRT,
         new Block[]{
            Blocks.PODZOL,
            Blocks.GRASS_BLOCK,
            Blocks.TERRACOTTA,
            Blocks.WHITE_TERRACOTTA,
            Blocks.ORANGE_TERRACOTTA,
            Blocks.MAGENTA_TERRACOTTA,
            Blocks.LIGHT_BLUE_TERRACOTTA,
            Blocks.YELLOW_TERRACOTTA,
            Blocks.LIME_TERRACOTTA,
            Blocks.PINK_TERRACOTTA,
            Blocks.GRAY_TERRACOTTA,
            Blocks.LIGHT_GRAY_TERRACOTTA,
            Blocks.CYAN_TERRACOTTA,
            Blocks.PURPLE_TERRACOTTA,
            Blocks.BLUE_TERRACOTTA,
            Blocks.BROWN_TERRACOTTA,
            Blocks.GREEN_TERRACOTTA,
            Blocks.RED_TERRACOTTA,
            Blocks.BLACK_TERRACOTTA,
            Blocks.SANDSTONE,
            Blocks.RED_SANDSTONE,
            Blocks.MYCELIUM,
            Blocks.SNOW,
            Blocks.SAND,
            Blocks.GRAVEL,
            Blocks.WATER,
            Blocks.LAVA,
            Blocks.OBSIDIAN,
            Blocks.AIR,
            Blocks.CAVE_AIR,
            Blocks.PACKED_ICE
         }
      );
   }

   @Override
   protected boolean isRegionUncarvable(Chunk chunk, int mainChunkX, int mainChunkZ, int relMinX, int relMaxX, int minY, int maxY, int relMinZ, int relMaxZ) {
      return false;
   }

   @Override
   protected boolean carveAtPoint(
      Chunk chunk,
      Function<BlockPos, Biome> posToBiome,
      BitSet carvingMask,
      Random _snowman,
      BlockPos.Mutable _snowman,
      BlockPos.Mutable _snowman,
      BlockPos.Mutable _snowman,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      int x,
      int z,
      int relativeX,
      int y,
      int relativeZ,
      MutableBoolean _snowman
   ) {
      return carveAtPoint(this, chunk, carvingMask, _snowman, _snowman, seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ);
   }

   protected static boolean carveAtPoint(
      Carver<?> carver,
      Chunk chunk,
      BitSet mask,
      Random random,
      BlockPos.Mutable pos,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      int x,
      int z,
      int relativeX,
      int y,
      int relativeZ
   ) {
      if (y >= seaLevel) {
         return false;
      } else {
         int _snowman = relativeX | relativeZ << 4 | y << 8;
         if (mask.get(_snowman)) {
            return false;
         } else {
            mask.set(_snowman);
            pos.set(x, y, z);
            BlockState _snowmanx = chunk.getBlockState(pos);
            if (!carver.canAlwaysCarveBlock(_snowmanx)) {
               return false;
            } else if (y == 10) {
               float _snowmanxx = random.nextFloat();
               if ((double)_snowmanxx < 0.25) {
                  chunk.setBlockState(pos, Blocks.MAGMA_BLOCK.getDefaultState(), false);
                  chunk.getBlockTickScheduler().schedule(pos, Blocks.MAGMA_BLOCK, 0);
               } else {
                  chunk.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), false);
               }

               return true;
            } else if (y < 10) {
               chunk.setBlockState(pos, Blocks.LAVA.getDefaultState(), false);
               return false;
            } else {
               boolean _snowmanxx = false;

               for (Direction _snowmanxxx : Direction.Type.HORIZONTAL) {
                  int _snowmanxxxx = x + _snowmanxxx.getOffsetX();
                  int _snowmanxxxxx = z + _snowmanxxx.getOffsetZ();
                  if (_snowmanxxxx >> 4 != mainChunkX || _snowmanxxxxx >> 4 != mainChunkZ || chunk.getBlockState(pos.set(_snowmanxxxx, y, _snowmanxxxxx)).isAir()) {
                     chunk.setBlockState(pos, WATER.getBlockState(), false);
                     chunk.getFluidTickScheduler().schedule(pos, WATER.getFluid(), 0);
                     _snowmanxx = true;
                     break;
                  }
               }

               pos.set(x, y, z);
               if (!_snowmanxx) {
                  chunk.setBlockState(pos, WATER.getBlockState(), false);
                  return true;
               } else {
                  return true;
               }
            }
         }
      }
   }
}
