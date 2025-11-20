package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DepthAverageDecorator extends SimpleDecorator<DepthAverageDecoratorConfig> {
   public DepthAverageDecorator(Codec<DepthAverageDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, DepthAverageDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxx = _snowman.baseline;
      int _snowmanxxxx = _snowman.spread;
      int _snowmanxxxxx = _snowman.getX();
      int _snowmanxxxxxx = _snowman.getZ();
      int _snowmanxxxxxxx = _snowman.nextInt(_snowmanxxxx) + _snowman.nextInt(_snowmanxxxx) - _snowmanxxxx + _snowmanxxx;
      return Stream.of(new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx));
   }
}
