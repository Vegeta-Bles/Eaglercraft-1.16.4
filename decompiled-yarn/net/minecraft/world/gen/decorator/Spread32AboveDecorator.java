package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class Spread32AboveDecorator extends Decorator<NopeDecoratorConfig> {
   public Spread32AboveDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxxx = _snowman.nextInt(_snowman.getY() + 32);
      return Stream.of(new BlockPos(_snowman.getX(), _snowmanxxxx, _snowman.getZ()));
   }
}
