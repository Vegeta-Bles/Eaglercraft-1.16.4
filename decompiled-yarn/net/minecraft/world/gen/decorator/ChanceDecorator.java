package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class ChanceDecorator extends SimpleDecorator<ChanceDecoratorConfig> {
   public ChanceDecorator(Codec<ChanceDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, ChanceDecoratorConfig _snowman, BlockPos _snowman) {
      return _snowman.nextFloat() < 1.0F / (float)_snowman.chance ? Stream.of(_snowman) : Stream.empty();
   }
}
