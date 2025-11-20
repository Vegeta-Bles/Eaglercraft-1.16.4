package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class LavaLakeDecorator extends Decorator<ChanceDecoratorConfig> {
   public LavaLakeDecorator(Codec<ChanceDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, ChanceDecoratorConfig _snowman, BlockPos _snowman) {
      if (_snowman.nextInt(_snowman.chance / 10) == 0) {
         int _snowmanxxxx = _snowman.nextInt(16) + _snowman.getX();
         int _snowmanxxxxx = _snowman.nextInt(16) + _snowman.getZ();
         int _snowmanxxxxxx = _snowman.nextInt(_snowman.nextInt(_snowman.getMaxY() - 8) + 8);
         if (_snowmanxxxxxx < _snowman.getSeaLevel() || _snowman.nextInt(_snowman.chance / 8) == 0) {
            return Stream.of(new BlockPos(_snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx));
         }
      }

      return Stream.empty();
   }
}
