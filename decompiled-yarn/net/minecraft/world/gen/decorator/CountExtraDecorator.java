package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class CountExtraDecorator extends SimpleDecorator<CountExtraDecoratorConfig> {
   public CountExtraDecorator(Codec<CountExtraDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, CountExtraDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxx = _snowman.count + (_snowman.nextFloat() < _snowman.extraChance ? _snowman.extraCount : 0);
      return IntStream.range(0, _snowmanxxx).mapToObj(_snowmanxxxx -> _snowman);
   }
}
