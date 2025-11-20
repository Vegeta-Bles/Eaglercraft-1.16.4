package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class ChanceDecorator extends SimpleDecorator<ChanceDecoratorConfig> {
   public ChanceDecorator(Codec<ChanceDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, ChanceDecoratorConfig arg, BlockPos arg2) {
      return random.nextFloat() < 1.0F / (float)arg.chance ? Stream.of(arg2) : Stream.empty();
   }
}
