package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class HugeBrownMushroomFeature extends HugeMushroomFeature {
   public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
      int _snowman = config.foliageRadius;

      for (int _snowmanx = -_snowman; _snowmanx <= _snowman; _snowmanx++) {
         for (int _snowmanxx = -_snowman; _snowmanxx <= _snowman; _snowmanxx++) {
            boolean _snowmanxxx = _snowmanx == -_snowman;
            boolean _snowmanxxxx = _snowmanx == _snowman;
            boolean _snowmanxxxxx = _snowmanxx == -_snowman;
            boolean _snowmanxxxxxx = _snowmanxx == _snowman;
            boolean _snowmanxxxxxxx = _snowmanxxx || _snowmanxxxx;
            boolean _snowmanxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxx;
            if (!_snowmanxxxxxxx || !_snowmanxxxxxxxx) {
               mutable.set(start, _snowmanx, y, _snowmanxx);
               if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                  boolean _snowmanxxxxxxxxx = _snowmanxxx || _snowmanxxxxxxxx && _snowmanx == 1 - _snowman;
                  boolean _snowmanxxxxxxxxxx = _snowmanxxxx || _snowmanxxxxxxxx && _snowmanx == _snowman - 1;
                  boolean _snowmanxxxxxxxxxxx = _snowmanxxxxx || _snowmanxxxxxxx && _snowmanxx == 1 - _snowman;
                  boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx || _snowmanxxxxxxx && _snowmanxx == _snowman - 1;
                  this.setBlockState(
                     world,
                     mutable,
                     config.capProvider
                        .getBlockState(random, start)
                        .with(MushroomBlock.WEST, Boolean.valueOf(_snowmanxxxxxxxxx))
                        .with(MushroomBlock.EAST, Boolean.valueOf(_snowmanxxxxxxxxxx))
                        .with(MushroomBlock.NORTH, Boolean.valueOf(_snowmanxxxxxxxxxxx))
                        .with(MushroomBlock.SOUTH, Boolean.valueOf(_snowmanxxxxxxxxxxxx))
                  );
               }
            }
         }
      }
   }

   @Override
   protected int getCapSize(int _snowman, int _snowman, int capSize, int y) {
      return y <= 3 ? 0 : capSize;
   }
}
