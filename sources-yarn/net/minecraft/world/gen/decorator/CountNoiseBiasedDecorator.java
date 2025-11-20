package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public class CountNoiseBiasedDecorator extends SimpleDecorator<CountNoiseBiasedDecoratorConfig> {
   public CountNoiseBiasedDecorator(Codec<CountNoiseBiasedDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, CountNoiseBiasedDecoratorConfig arg, BlockPos arg2) {
      double d = Biome.FOLIAGE_NOISE.sample((double)arg2.getX() / arg.noiseFactor, (double)arg2.getZ() / arg.noiseFactor, false);
      int i = (int)Math.ceil((d + arg.noiseOffset) * (double)arg.noiseToCountRatio);
      return IntStream.range(0, i).mapToObj(ix -> arg2);
   }
}
