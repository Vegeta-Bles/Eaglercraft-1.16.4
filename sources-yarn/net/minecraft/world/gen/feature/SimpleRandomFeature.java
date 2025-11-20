package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SimpleRandomFeature extends Feature<SimpleRandomFeatureConfig> {
   public SimpleRandomFeature(Codec<SimpleRandomFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(StructureWorldAccess arg, ChunkGenerator arg2, Random random, BlockPos arg3, SimpleRandomFeatureConfig arg4) {
      int i = random.nextInt(arg4.features.size());
      ConfiguredFeature<?, ?> lv = arg4.features.get(i).get();
      return lv.generate(arg, arg2, random, arg3);
   }
}
