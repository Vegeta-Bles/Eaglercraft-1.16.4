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
   public CoralClawFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   @Override
   protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      if (!this.spawnCoralPiece(world, random, pos, state)) {
         return false;
      } else {
         Direction lv = Direction.Type.HORIZONTAL.random(random);
         int i = random.nextInt(2) + 2;
         List<Direction> list = Lists.newArrayList(new Direction[]{lv, lv.rotateYClockwise(), lv.rotateYCounterclockwise()});
         Collections.shuffle(list, random);

         for (Direction lv2 : list.subList(0, i)) {
            BlockPos.Mutable lv3 = pos.mutableCopy();
            int j = random.nextInt(2) + 1;
            lv3.move(lv2);
            int k;
            Direction lv4;
            if (lv2 == lv) {
               lv4 = lv;
               k = random.nextInt(3) + 2;
            } else {
               lv3.move(Direction.UP);
               Direction[] lvs = new Direction[]{lv2, Direction.UP};
               lv4 = Util.getRandom(lvs, random);
               k = random.nextInt(3) + 3;
            }

            for (int m = 0; m < j && this.spawnCoralPiece(world, random, lv3, state); m++) {
               lv3.move(lv4);
            }

            lv3.move(lv4.getOpposite());
            lv3.move(Direction.UP);

            for (int n = 0; n < k; n++) {
               lv3.move(lv);
               if (!this.spawnCoralPiece(world, random, lv3, state)) {
                  break;
               }

               if (random.nextFloat() < 0.25F) {
                  lv3.move(Direction.UP);
               }
            }
         }

         return true;
      }
   }
}
