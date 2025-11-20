package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class CountNoiseBiasedDecorator extends SimpleDecorator<CountNoiseBiasedDecoratorConfig> {
   public CountNoiseBiasedDecorator(Codec<CountNoiseBiasedDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, CountNoiseBiasedDecoratorConfig _snowman, BlockPos _snowman) {
      double _snowmanxxx = Biome.FOLIAGE_NOISE.sample((double)_snowman.getX() / _snowman.noiseFactor, (double)_snowman.getZ() / _snowman.noiseFactor, false);
      int _snowmanxxxx = (int)Math.ceil((_snowmanxxx + _snowman.noiseOffset) * (double)_snowman.noiseToCountRatio);
      return IntStream.range(0, _snowmanxxxx).mapToObj(_snowmanxxxxx -> _snowman);
   }
}
