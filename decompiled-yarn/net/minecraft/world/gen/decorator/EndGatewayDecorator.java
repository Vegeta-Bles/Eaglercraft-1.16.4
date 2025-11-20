package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class EndGatewayDecorator extends Decorator<NopeDecoratorConfig> {
   public EndGatewayDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      if (_snowman.nextInt(700) == 0) {
         int _snowmanxxxx = _snowman.nextInt(16) + _snowman.getX();
         int _snowmanxxxxx = _snowman.nextInt(16) + _snowman.getZ();
         int _snowmanxxxxxx = _snowman.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowmanxxxx, _snowmanxxxxx);
         if (_snowmanxxxxxx > 0) {
            int _snowmanxxxxxxx = _snowmanxxxxxx + 3 + _snowman.nextInt(7);
            return Stream.of(new BlockPos(_snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx));
         }
      }

      return Stream.empty();
   }
}
