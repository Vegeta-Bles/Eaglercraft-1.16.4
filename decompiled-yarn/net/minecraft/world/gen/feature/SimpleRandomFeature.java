package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SimpleRandomFeature extends Feature<SimpleRandomFeatureConfig> {
   public SimpleRandomFeature(Codec<SimpleRandomFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, SimpleRandomFeatureConfig _snowman) {
      int _snowmanxxxxx = _snowman.nextInt(_snowman.features.size());
      ConfiguredFeature<?, ?> _snowmanxxxxxx = _snowman.features.get(_snowmanxxxxx).get();
      return _snowmanxxxxxx.generate(_snowman, _snowman, _snowman, _snowman);
   }
}
