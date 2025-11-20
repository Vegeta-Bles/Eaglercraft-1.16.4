package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class MagmaDecorator extends Decorator<NopeDecoratorConfig> {
   public MagmaDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxxx = _snowman.getSeaLevel();
      int _snowmanxxxxx = _snowmanxxxx - 5 + _snowman.nextInt(10);
      return Stream.of(new BlockPos(_snowman.getX(), _snowmanxxxxx, _snowman.getZ()));
   }
}
