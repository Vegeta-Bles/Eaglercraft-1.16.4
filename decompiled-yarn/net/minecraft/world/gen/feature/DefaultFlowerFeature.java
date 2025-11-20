package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class DefaultFlowerFeature extends FlowerFeature<RandomPatchFeatureConfig> {
   public DefaultFlowerFeature(Codec<RandomPatchFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean isPosValid(WorldAccess _snowman, BlockPos _snowman, RandomPatchFeatureConfig _snowman) {
      return !_snowman.blacklist.contains(_snowman.getBlockState(_snowman));
   }

   public int getFlowerAmount(RandomPatchFeatureConfig _snowman) {
      return _snowman.tries;
   }

   public BlockPos getPos(Random _snowman, BlockPos _snowman, RandomPatchFeatureConfig _snowman) {
      return _snowman.add(_snowman.nextInt(_snowman.spreadX) - _snowman.nextInt(_snowman.spreadX), _snowman.nextInt(_snowman.spreadY) - _snowman.nextInt(_snowman.spreadY), _snowman.nextInt(_snowman.spreadZ) - _snowman.nextInt(_snowman.spreadZ));
   }

   public BlockState getFlowerState(Random _snowman, BlockPos _snowman, RandomPatchFeatureConfig _snowman) {
      return _snowman.stateProvider.getBlockState(_snowman, _snowman);
   }
}
