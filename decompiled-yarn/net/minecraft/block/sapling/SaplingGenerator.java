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
   protected abstract ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random var1, boolean var2);

   public boolean generate(ServerWorld _snowman, ChunkGenerator _snowman, BlockPos _snowman, BlockState _snowman, Random _snowman) {
      ConfiguredFeature<TreeFeatureConfig, ?> _snowmanxxxxx = this.createTreeFeature(_snowman, this.method_24282(_snowman, _snowman));
      if (_snowmanxxxxx == null) {
         return false;
      } else {
         _snowman.setBlockState(_snowman, Blocks.AIR.getDefaultState(), 4);
         _snowmanxxxxx.config.ignoreFluidCheck();
         if (_snowmanxxxxx.generate(_snowman, _snowman, _snowman, _snowman)) {
            return true;
         } else {
            _snowman.setBlockState(_snowman, _snowman, 4);
            return false;
         }
      }
   }

   private boolean method_24282(WorldAccess _snowman, BlockPos _snowman) {
      for (BlockPos _snowmanxx : BlockPos.Mutable.iterate(_snowman.down().north(2).west(2), _snowman.up().south(2).east(2))) {
         if (_snowman.getBlockState(_snowmanxx).isIn(BlockTags.FLOWERS)) {
            return true;
         }
      }

      return false;
   }
}
