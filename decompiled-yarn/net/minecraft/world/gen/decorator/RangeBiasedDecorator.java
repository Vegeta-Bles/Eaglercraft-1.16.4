package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class RangeBiasedDecorator extends SimpleDecorator<RangeDecoratorConfig> {
   public RangeBiasedDecorator(Codec<RangeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, RangeDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxx = _snowman.getX();
      int _snowmanxxxx = _snowman.getZ();
      int _snowmanxxxxx = _snowman.nextInt(_snowman.nextInt(_snowman.maximum - _snowman.topOffset) + _snowman.bottomOffset);
      return Stream.of(new BlockPos(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx));
   }
}
