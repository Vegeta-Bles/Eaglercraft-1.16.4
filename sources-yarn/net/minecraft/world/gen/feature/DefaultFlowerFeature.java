package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;

public class DefaultFlowerFeature extends FlowerFeature<RandomPatchFeatureConfig> {
   public DefaultFlowerFeature(Codec<RandomPatchFeatureConfig> codec) {
      super(codec);
   }

   public boolean isPosValid(WorldAccess arg, BlockPos arg2, RandomPatchFeatureConfig arg3) {
      return !arg3.blacklist.contains(arg.getBlockState(arg2));
   }

   public int getFlowerAmount(RandomPatchFeatureConfig arg) {
      return arg.tries;
   }

   public BlockPos getPos(Random random, BlockPos arg, RandomPatchFeatureConfig arg2) {
      return arg.add(
         random.nextInt(arg2.spreadX) - random.nextInt(arg2.spreadX),
         random.nextInt(arg2.spreadY) - random.nextInt(arg2.spreadY),
         random.nextInt(arg2.spreadZ) - random.nextInt(arg2.spreadZ)
      );
   }

   public BlockState getFlowerState(Random random, BlockPos arg, RandomPatchFeatureConfig arg2) {
      return arg2.stateProvider.getBlockState(random, arg);
   }
}
