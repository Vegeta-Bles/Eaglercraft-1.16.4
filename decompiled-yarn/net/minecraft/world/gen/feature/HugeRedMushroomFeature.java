package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class HugeRedMushroomFeature extends HugeMushroomFeature {
   public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
      for (int _snowman = y - 3; _snowman <= y; _snowman++) {
         int _snowmanx = _snowman < y ? config.foliageRadius : config.foliageRadius - 1;
         int _snowmanxx = config.foliageRadius - 2;

         for (int _snowmanxxx = -_snowmanx; _snowmanxxx <= _snowmanx; _snowmanxxx++) {
            for (int _snowmanxxxx = -_snowmanx; _snowmanxxxx <= _snowmanx; _snowmanxxxx++) {
               boolean _snowmanxxxxx = _snowmanxxx == -_snowmanx;
               boolean _snowmanxxxxxx = _snowmanxxx == _snowmanx;
               boolean _snowmanxxxxxxx = _snowmanxxxx == -_snowmanx;
               boolean _snowmanxxxxxxxx = _snowmanxxxx == _snowmanx;
               boolean _snowmanxxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
               boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxx || _snowmanxxxxxxxx;
               if (_snowman >= y || _snowmanxxxxxxxxx != _snowmanxxxxxxxxxx) {
                  mutable.set(start, _snowmanxxx, _snowman, _snowmanxxxx);
                  if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                     this.setBlockState(
                        world,
                        mutable,
                        config.capProvider
                           .getBlockState(random, start)
                           .with(MushroomBlock.UP, Boolean.valueOf(_snowman >= y - 1))
                           .with(MushroomBlock.WEST, Boolean.valueOf(_snowmanxxx < -_snowmanxx))
                           .with(MushroomBlock.EAST, Boolean.valueOf(_snowmanxxx > _snowmanxx))
                           .with(MushroomBlock.NORTH, Boolean.valueOf(_snowmanxxxx < -_snowmanxx))
                           .with(MushroomBlock.SOUTH, Boolean.valueOf(_snowmanxxxx > _snowmanxx))
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   protected int getCapSize(int _snowman, int _snowman, int capSize, int y) {
      int _snowmanxx = 0;
      if (y < _snowman && y >= _snowman - 3) {
         _snowmanxx = capSize;
      } else if (y == _snowman) {
         _snowmanxx = capSize;
      }

      return _snowmanxx;
   }
}
