package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class EndIslandDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public EndIslandDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      Stream<BlockPos> _snowmanxxx = Stream.empty();
      if (_snowman.nextInt(14) == 0) {
         _snowmanxxx = Stream.concat(_snowmanxxx, Stream.of(_snowman.add(_snowman.nextInt(16), 55 + _snowman.nextInt(16), _snowman.nextInt(16))));
         if (_snowman.nextInt(4) == 0) {
            _snowmanxxx = Stream.concat(_snowmanxxx, Stream.of(_snowman.add(_snowman.nextInt(16), 55 + _snowman.nextInt(16), _snowman.nextInt(16))));
         }

         return _snowmanxxx;
      } else {
         return Stream.empty();
      }
   }
}
