package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class EmeraldOreDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public EmeraldOreDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxx = 3 + _snowman.nextInt(6);
      return IntStream.range(0, _snowmanxxx).mapToObj(_snowmanxxxxxxx -> {
         int _snowmanxxx = _snowman.nextInt(16) + _snowman.getX();
         int _snowmanxxxx = _snowman.nextInt(16) + _snowman.getZ();
         int _snowmanxxxxx = _snowman.nextInt(28) + 4;
         return new BlockPos(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx);
      });
   }
}
