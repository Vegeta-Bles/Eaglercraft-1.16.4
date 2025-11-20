package net.minecraft.block.sapling;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public abstract class LargeTreeSaplingGenerator extends SaplingGenerator {
   public LargeTreeSaplingGenerator() {
   }

   @Override
   public boolean generate(ServerWorld _snowman, ChunkGenerator _snowman, BlockPos _snowman, BlockState _snowman, Random _snowman) {
      for (int _snowmanxxxxx = 0; _snowmanxxxxx >= -1; _snowmanxxxxx--) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx >= -1; _snowmanxxxxxx--) {
            if (canGenerateLargeTree(_snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx)) {
               return this.generateLargeTree(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }

      return super.generate(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   protected abstract ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random var1);

   public boolean generateLargeTree(ServerWorld _snowman, ChunkGenerator _snowman, BlockPos _snowman, BlockState _snowman, Random _snowman, int _snowman, int _snowman) {
      ConfiguredFeature<TreeFeatureConfig, ?> _snowmanxxxxxxx = this.createLargeTreeFeature(_snowman);
      if (_snowmanxxxxxxx == null) {
         return false;
      } else {
         _snowmanxxxxxxx.config.ignoreFluidCheck();
         BlockState _snowmanxxxxxxxx = Blocks.AIR.getDefaultState();
         _snowman.setBlockState(_snowman.add(_snowman, 0, _snowman), _snowmanxxxxxxxx, 4);
         _snowman.setBlockState(_snowman.add(_snowman + 1, 0, _snowman), _snowmanxxxxxxxx, 4);
         _snowman.setBlockState(_snowman.add(_snowman, 0, _snowman + 1), _snowmanxxxxxxxx, 4);
         _snowman.setBlockState(_snowman.add(_snowman + 1, 0, _snowman + 1), _snowmanxxxxxxxx, 4);
         if (_snowmanxxxxxxx.generate(_snowman, _snowman, _snowman, _snowman.add(_snowman, 0, _snowman))) {
            return true;
         } else {
            _snowman.setBlockState(_snowman.add(_snowman, 0, _snowman), _snowman, 4);
            _snowman.setBlockState(_snowman.add(_snowman + 1, 0, _snowman), _snowman, 4);
            _snowman.setBlockState(_snowman.add(_snowman, 0, _snowman + 1), _snowman, 4);
            _snowman.setBlockState(_snowman.add(_snowman + 1, 0, _snowman + 1), _snowman, 4);
            return false;
         }
      }
   }

   public static boolean canGenerateLargeTree(BlockState state, BlockView world, BlockPos pos, int x, int z) {
      Block _snowman = state.getBlock();
      return _snowman == world.getBlockState(pos.add(x, 0, z)).getBlock()
         && _snowman == world.getBlockState(pos.add(x + 1, 0, z)).getBlock()
         && _snowman == world.getBlockState(pos.add(x, 0, z + 1)).getBlock()
         && _snowman == world.getBlockState(pos.add(x + 1, 0, z + 1)).getBlock();
   }
}
