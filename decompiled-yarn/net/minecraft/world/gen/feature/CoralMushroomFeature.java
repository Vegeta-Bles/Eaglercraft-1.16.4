package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class CoralMushroomFeature extends CoralFeature {
   public CoralMushroomFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      int _snowman = random.nextInt(3) + 3;
      int _snowmanx = random.nextInt(3) + 3;
      int _snowmanxx = random.nextInt(3) + 3;
      int _snowmanxxx = random.nextInt(3) + 1;
      BlockPos.Mutable _snowmanxxxx = pos.mutableCopy();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx <= _snowmanx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowman; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= _snowmanxx; _snowmanxxxxxxx++) {
               _snowmanxxxx.set(_snowmanxxxxx + pos.getX(), _snowmanxxxxxx + pos.getY(), _snowmanxxxxxxx + pos.getZ());
               _snowmanxxxx.move(Direction.DOWN, _snowmanxxx);
               if ((_snowmanxxxxx != 0 && _snowmanxxxxx != _snowmanx || _snowmanxxxxxx != 0 && _snowmanxxxxxx != _snowman)
                  && (_snowmanxxxxxxx != 0 && _snowmanxxxxxxx != _snowmanxx || _snowmanxxxxxx != 0 && _snowmanxxxxxx != _snowman)
                  && (_snowmanxxxxx != 0 && _snowmanxxxxx != _snowmanx || _snowmanxxxxxxx != 0 && _snowmanxxxxxxx != _snowmanxx)
                  && (_snowmanxxxxx == 0 || _snowmanxxxxx == _snowmanx || _snowmanxxxxxx == 0 || _snowmanxxxxxx == _snowman || _snowmanxxxxxxx == 0 || _snowmanxxxxxxx == _snowmanxx)
                  && !(random.nextFloat() < 0.1F)
                  && !this.spawnCoralPiece(world, random, _snowmanxxxx, state)) {
               }
            }
         }
      }

      return true;
   }
}
