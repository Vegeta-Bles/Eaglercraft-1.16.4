package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class SaplingGenerator {
   public SaplingGenerator() {
   }

   @Nullable
   protected abstract ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl);

   public boolean generate(ServerWorld arg, ChunkGenerator arg2, BlockPos arg3, BlockState arg4, Random random) {
      ConfiguredFeature<TreeFeatureConfig, ?> lv = this.createTreeFeature(random, this.method_24282(arg, arg3));
      if (lv == null) {
         return false;
      } else {
         arg.setBlockState(arg3, Blocks.AIR.getDefaultState(), 4);
         lv.config.ignoreFluidCheck();
         if (lv.generate(arg, arg2, random, arg3)) {
            return true;
         } else {
            arg.setBlockState(arg3, arg4, 4);
            return false;
         }
      }
   }

   private boolean method_24282(WorldAccess arg, BlockPos arg2) {
      for (BlockPos lv : BlockPos.Mutable.iterate(arg2.down().north(2).west(2), arg2.up().south(2).east(2))) {
         if (arg.getBlockState(lv).isIn(BlockTags.FLOWERS)) {
            return true;
         }
      }

      return false;
   }
}
