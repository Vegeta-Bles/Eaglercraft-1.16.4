package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DepthAverageDecorator extends SimpleDecorator<DepthAverageDecoratorConfig> {
   public DepthAverageDecorator(Codec<DepthAverageDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, DepthAverageDecoratorConfig arg, BlockPos arg2) {
      int i = arg.baseline;
      int j = arg.spread;
      int k = arg2.getX();
      int l = arg2.getZ();
      int m = random.nextInt(j) + random.nextInt(j) - j + i;
      return Stream.of(new BlockPos(k, m, l));
   }
}
