package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class RangeVeryBiasedDecorator extends SimpleDecorator<RangeDecoratorConfig> {
   public RangeVeryBiasedDecorator(Codec<RangeDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, RangeDecoratorConfig arg, BlockPos arg2) {
      int i = arg2.getX();
      int j = arg2.getZ();
      int k = random.nextInt(random.nextInt(random.nextInt(arg.maximum - arg.topOffset) + arg.bottomOffset) + arg.bottomOffset);
      return Stream.of(new BlockPos(i, k, j));
   }
}
