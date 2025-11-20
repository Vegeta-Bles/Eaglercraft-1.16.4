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
   public UnderwaterCaveCarver(Codec<ProbabilityConfig> codec) {
      super(codec, 256);
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
      Random random,
      BlockPos.Mutable arg2,
      BlockPos.Mutable arg3,
      BlockPos.Mutable arg4,
      int seaLevel,
      int mainChunkX,
      int mainChunkZ,
      int x,
      int z,
      int relativeX,
      int y,
      int relativeZ,
      MutableBoolean mutableBoolean
   ) {
      return carveAtPoint(this, chunk, carvingMask, random, arg2, seaLevel, mainChunkX, mainChunkZ, x, z, relativeX, y, relativeZ);
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
         int q = relativeX | relativeZ << 4 | y << 8;
         if (mask.get(q)) {
            return false;
         } else {
            mask.set(q);
            pos.set(x, y, z);
            BlockState lv = chunk.getBlockState(pos);
            if (!carver.canAlwaysCarveBlock(lv)) {
               return false;
            } else if (y == 10) {
               float f = random.nextFloat();
               if ((double)f < 0.25) {
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
               boolean bl = false;

               for (Direction lv2 : Direction.Type.HORIZONTAL) {
                  int r = x + lv2.getOffsetX();
                  int s = z + lv2.getOffsetZ();
                  if (r >> 4 != mainChunkX || s >> 4 != mainChunkZ || chunk.getBlockState(pos.set(r, y, s)).isAir()) {
                     chunk.setBlockState(pos, WATER.getBlockState(), false);
                     chunk.getFluidTickScheduler().schedule(pos, WATER.getFluid(), 0);
                     bl = true;
                     break;
                  }
               }

               pos.set(x, y, z);
               if (!bl) {
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
