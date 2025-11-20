package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class CoralClawFeature extends CoralFeature {
   public CoralClawFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      if (!this.spawnCoralPiece(world, random, pos, state)) {
         return false;
      } else {
         Direction _snowman = Direction.Type.HORIZONTAL.random(random);
         int _snowmanx = random.nextInt(2) + 2;
         List<Direction> _snowmanxx = Lists.newArrayList(new Direction[]{_snowman, _snowman.rotateYClockwise(), _snowman.rotateYCounterclockwise()});
         Collections.shuffle(_snowmanxx, random);

         for (Direction _snowmanxxx : _snowmanxx.subList(0, _snowmanx)) {
            BlockPos.Mutable _snowmanxxxx = pos.mutableCopy();
            int _snowmanxxxxx = random.nextInt(2) + 1;
            _snowmanxxxx.move(_snowmanxxx);
            int _snowmanxxxxxx;
            Direction _snowmanxxxxxxx;
            if (_snowmanxxx == _snowman) {
               _snowmanxxxxxxx = _snowman;
               _snowmanxxxxxx = random.nextInt(3) + 2;
            } else {
               _snowmanxxxx.move(Direction.UP);
               Direction[] _snowmanxxxxxxxx = new Direction[]{_snowmanxxx, Direction.UP};
               _snowmanxxxxxxx = Util.getRandom(_snowmanxxxxxxxx, random);
               _snowmanxxxxxx = random.nextInt(3) + 3;
            }

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx && this.spawnCoralPiece(world, random, _snowmanxxxx, state); _snowmanxxxxxxxx++) {
               _snowmanxxxx.move(_snowmanxxxxxxx);
            }

            _snowmanxxxx.move(_snowmanxxxxxxx.getOpposite());
            _snowmanxxxx.move(Direction.UP);

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               _snowmanxxxx.move(_snowman);
               if (!this.spawnCoralPiece(world, random, _snowmanxxxx, state)) {
                  break;
               }

               if (random.nextFloat() < 0.25F) {
                  _snowmanxxxx.move(Direction.UP);
               }
            }
         }

         return true;
      }
   }
}
