package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class NopeDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public NopeDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      return Stream.of(_snowman);
   }
}
