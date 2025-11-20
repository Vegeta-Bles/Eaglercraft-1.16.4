package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class NetherCaveCarver extends CaveCarver {
   public NetherCaveCarver(Codec<ProbabilityConfig> configCodec) {
      super(configCodec, 128);
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
            Blocks.NETHERRACK,
            Blocks.SOUL_SAND,
            Blocks.SOUL_SOIL,
            Blocks.CRIMSON_NYLIUM,
            Blocks.WARPED_NYLIUM,
            Blocks.NETHER_WART_BLOCK,
            Blocks.WARPED_WART_BLOCK,
            Blocks.BASALT,
            Blocks.BLACKSTONE
         }
      );
      this.carvableFluids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
   }

   @Override
   protected int getMaxCaveCount() {
      return 10;
   }

   @Override
   protected float getTunnelSystemWidth(Random random) {
      return (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
   }

   @Override
   protected double getTunnelSystemHeightWidthRatio() {
      return 5.0;
   }

   @Override
   protected int getCaveY(Random random) {
      return random.nextInt(this.heightLimit);
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
      int _snowmanxxxxx = relativeX | relativeZ << 4 | y << 8;
      if (carvingMask.get(_snowmanxxxxx)) {
         return false;
      } else {
         carvingMask.set(_snowmanxxxxx);
         _snowman.set(x, y, z);
         if (this.canAlwaysCarveBlock(chunk.getBlockState(_snowman))) {
            BlockState _snowmanxxxxxx;
            if (y <= 31) {
               _snowmanxxxxxx = LAVA.getBlockState();
            } else {
               _snowmanxxxxxx = CAVE_AIR;
            }

            chunk.setBlockState(_snowman, _snowmanxxxxxx, false);
            return true;
         } else {
            return false;
         }
      }
   }
}
