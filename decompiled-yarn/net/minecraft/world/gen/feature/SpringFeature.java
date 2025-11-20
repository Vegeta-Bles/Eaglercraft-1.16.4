package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SpringFeature extends Feature<SpringFeatureConfig> {
   public SpringFeature(Codec<SpringFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SpringFeatureConfig _snowman) {
      if (!_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.up()).getBlock())) {
         return false;
      } else if (_snowman.requiresBlockBelow && !_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.down()).getBlock())) {
         return false;
      } else {
         BlockState _snowmanxxxxx = _snowman.getBlockState(_snowman);
         if (!_snowmanxxxxx.isAir() && !_snowman.validBlocks.contains(_snowmanxxxxx.getBlock())) {
            return false;
         } else {
            int _snowmanxxxxxx = 0;
            int _snowmanxxxxxxx = 0;
            if (_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.west()).getBlock())) {
               _snowmanxxxxxxx++;
            }

            if (_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.east()).getBlock())) {
               _snowmanxxxxxxx++;
            }

            if (_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.north()).getBlock())) {
               _snowmanxxxxxxx++;
            }

            if (_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.south()).getBlock())) {
               _snowmanxxxxxxx++;
            }

            if (_snowman.validBlocks.contains(_snowman.getBlockState(_snowman.down()).getBlock())) {
               _snowmanxxxxxxx++;
            }

            int _snowmanxxxxxxxx = 0;
            if (_snowman.isAir(_snowman.west())) {
               _snowmanxxxxxxxx++;
            }

            if (_snowman.isAir(_snowman.east())) {
               _snowmanxxxxxxxx++;
            }

            if (_snowman.isAir(_snowman.north())) {
               _snowmanxxxxxxxx++;
            }

            if (_snowman.isAir(_snowman.south())) {
               _snowmanxxxxxxxx++;
            }

            if (_snowman.isAir(_snowman.down())) {
               _snowmanxxxxxxxx++;
            }

            if (_snowmanxxxxxxx == _snowman.rockCount && _snowmanxxxxxxxx == _snowman.holeCount) {
               _snowman.setBlockState(_snowman, _snowman.state.getBlockState(), 2);
               _snowman.getFluidTickScheduler().schedule(_snowman, _snowman.state.getFluid(), 0);
               _snowmanxxxxxx++;
            }

            return _snowmanxxxxxx > 0;
         }
      }
   }
}
