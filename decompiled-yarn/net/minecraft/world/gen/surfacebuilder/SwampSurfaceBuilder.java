package net.minecraft.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class SwampSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
   public SwampSurfaceBuilder(Codec<TernarySurfaceConfig> _snowman) {
      super(_snowman);
   }

   public void generate(Random _snowman, Chunk _snowman, Biome _snowman, int _snowman, int _snowman, int _snowman, double _snowman, BlockState _snowman, BlockState _snowman, int _snowman, long _snowman, TernarySurfaceConfig _snowman) {
      double _snowmanxxxxxxxxxxxx = Biome.FOLIAGE_NOISE.sample((double)_snowman * 0.25, (double)_snowman * 0.25, false);
      if (_snowmanxxxxxxxxxxxx > 0.0) {
         int _snowmanxxxxxxxxxxxxx = _snowman & 15;
         int _snowmanxxxxxxxxxxxxxx = _snowman & 15;
         BlockPos.Mutable _snowmanxxxxxxxxxxxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxxxxxxxxxxxx = _snowman; _snowmanxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxx--) {
            _snowmanxxxxxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
            if (!_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxx).isAir()) {
               if (_snowmanxxxxxxxxxxxxxxxx == 62 && !_snowman.getBlockState(_snowmanxxxxxxxxxxxxxxx).isOf(_snowman.getBlock())) {
                  _snowman.setBlockState(_snowmanxxxxxxxxxxxxxxx, _snowman, false);
               }
               break;
            }
         }
      }

      SurfaceBuilder.DEFAULT.generate(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
