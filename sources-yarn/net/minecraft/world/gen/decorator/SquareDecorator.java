package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class SquareDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public SquareDecorator(Codec<NopeDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, NopeDecoratorConfig arg, BlockPos arg2) {
      int i = random.nextInt(16) + arg2.getX();
      int j = random.nextInt(16) + arg2.getZ();
      int k = arg2.getY();
      return Stream.of(new BlockPos(i, k, j));
   }
}
