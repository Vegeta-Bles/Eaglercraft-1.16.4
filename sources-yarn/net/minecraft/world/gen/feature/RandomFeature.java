package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomFeature extends Feature<RandomFeatureConfig> {
   public RandomFeature(Codec<RandomFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, RandomFeatureConfig arg4) {
      for (RandomFeatureEntry lv : arg4.features) {
         if (random.nextFloat() < lv.chance) {
            return lv.generate(arg, arg2, random, arg3);
         }
      }

      return arg4.defaultFeature.get().generate(arg, arg2, random, arg3);
   }
}
