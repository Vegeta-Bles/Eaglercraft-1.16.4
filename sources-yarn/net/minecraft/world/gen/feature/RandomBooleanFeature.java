package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomBooleanFeature extends Feature<RandomBooleanFeatureConfig> {
   public RandomBooleanFeature(Codec<RandomBooleanFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, RandomBooleanFeatureConfig arg4) {
      boolean bl = random.nextBoolean();
      return bl ? arg4.featureTrue.get().generate(arg, arg2, random, arg3) : arg4.featureFalse.get().generate(arg, arg2, random, arg3);
   }
}
