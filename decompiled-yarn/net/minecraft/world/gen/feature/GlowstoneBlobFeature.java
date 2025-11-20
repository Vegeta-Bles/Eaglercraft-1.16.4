package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class GlowstoneBlobFeature extends Feature<DefaultFeatureConfig> {
   public GlowstoneBlobFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      if (!_snowman.isAir(_snowman)) {
         return false;
      } else {
         BlockState _snowmanxxxxx = _snowman.getBlockState(_snowman.up());
         if (!_snowmanxxxxx.isOf(Blocks.NETHERRACK) && !_snowmanxxxxx.isOf(Blocks.BASALT) && !_snowmanxxxxx.isOf(Blocks.BLACKSTONE)) {
            return false;
         } else {
            _snowman.setBlockState(_snowman, Blocks.GLOWSTONE.getDefaultState(), 2);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 1500; _snowmanxxxxxx++) {
               BlockPos _snowmanxxxxxxx = _snowman.add(_snowman.nextInt(8) - _snowman.nextInt(8), -_snowman.nextInt(12), _snowman.nextInt(8) - _snowman.nextInt(8));
               if (_snowman.getBlockState(_snowmanxxxxxxx).isAir()) {
                  int _snowmanxxxxxxxx = 0;

                  for (Direction _snowmanxxxxxxxxx : Direction.values()) {
                     if (_snowman.getBlockState(_snowmanxxxxxxx.offset(_snowmanxxxxxxxxx)).isOf(Blocks.GLOWSTONE)) {
                        _snowmanxxxxxxxx++;
                     }

                     if (_snowmanxxxxxxxx > 1) {
                        break;
                     }
                  }

                  if (_snowmanxxxxxxxx == 1) {
                     _snowman.setBlockState(_snowmanxxxxxxx, Blocks.GLOWSTONE.getDefaultState(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
