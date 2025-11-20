package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DecoratedDecorator extends Decorator<DecoratedDecoratorConfig> {
   public DecoratedDecorator(Codec<DecoratedDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, DecoratedDecoratorConfig _snowman, BlockPos _snowman) {
      return _snowman.getOuter().getPositions(_snowman, _snowman, _snowman).flatMap(_snowmanxxxxx -> _snowman.getInner().getPositions(_snowman, _snowman, _snowmanxxxxx));
   }
}
