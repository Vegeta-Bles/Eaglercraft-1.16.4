package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class RandomBooleanFeature extends Feature<RandomBooleanFeatureConfig> {
   public RandomBooleanFeature(Codec<RandomBooleanFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, RandomBooleanFeatureConfig _snowman) {
      boolean _snowmanxxxxx = _snowman.nextBoolean();
      return _snowmanxxxxx ? _snowman.featureTrue.get().generate(_snowman, _snowman, _snowman, _snowman) : _snowman.featureFalse.get().generate(_snowman, _snowman, _snowman, _snowman);
   }
}
