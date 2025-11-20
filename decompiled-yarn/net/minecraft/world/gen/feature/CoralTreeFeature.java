package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

public class CoralTreeFeature extends CoralFeature {
   public CoralTreeFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean spawnCoral(WorldAccess world, Random random, BlockPos pos, BlockState state) {
      BlockPos.Mutable _snowman = pos.mutableCopy();
      int _snowmanx = random.nextInt(3) + 1;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         if (!this.spawnCoralPiece(world, random, _snowman, state)) {
            return true;
         }

         _snowman.move(Direction.UP);
      }

      BlockPos _snowmanxx = _snowman.toImmutable();
      int _snowmanxxx = random.nextInt(3) + 2;
      List<Direction> _snowmanxxxx = Lists.newArrayList(Direction.Type.HORIZONTAL);
      Collections.shuffle(_snowmanxxxx, random);

      for (Direction _snowmanxxxxx : _snowmanxxxx.subList(0, _snowmanxxx)) {
         _snowman.set(_snowmanxx);
         _snowman.move(_snowmanxxxxx);
         int _snowmanxxxxxx = random.nextInt(5) + 2;
         int _snowmanxxxxxxx = 0;

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx && this.spawnCoralPiece(world, random, _snowman, state); _snowmanxxxxxxxx++) {
            _snowmanxxxxxxx++;
            _snowman.move(Direction.UP);
            if (_snowmanxxxxxxxx == 0 || _snowmanxxxxxxx >= 2 && random.nextFloat() < 0.25F) {
               _snowman.move(_snowmanxxxxx);
               _snowmanxxxxxxx = 0;
            }
         }
      }

      return true;
   }
}
