package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class CountNoiseDecorator extends Decorator<CountNoiseDecoratorConfig> {
   public CountNoiseDecorator(Codec<CountNoiseDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, CountNoiseDecoratorConfig _snowman, BlockPos _snowman) {
      double _snowmanxxxx = Biome.FOLIAGE_NOISE.sample((double)_snowman.getX() / 200.0, (double)_snowman.getZ() / 200.0, false);
      int _snowmanxxxxx = _snowmanxxxx < _snowman.noiseLevel ? _snowman.belowNoise : _snowman.aboveNoise;
      return IntStream.range(0, _snowmanxxxxx).mapToObj(_snowmanxxxxxx -> _snowman);
   }
}
