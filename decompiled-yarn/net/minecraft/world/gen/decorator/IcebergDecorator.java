package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class IcebergDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public IcebergDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      int _snowmanxxx = _snowman.nextInt(8) + 4 + _snowman.getX();
      int _snowmanxxxx = _snowman.nextInt(8) + 4 + _snowman.getZ();
      return Stream.of(new BlockPos(_snowmanxxx, _snowman.getY(), _snowmanxxxx));
   }
}
