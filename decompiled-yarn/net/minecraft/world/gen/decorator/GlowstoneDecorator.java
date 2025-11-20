package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CountConfig;

public class GlowstoneDecorator extends SimpleDecorator<CountConfig> {
   public GlowstoneDecorator(Codec<CountConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, CountConfig _snowman, BlockPos _snowman) {
      return IntStream.range(0, _snowman.nextInt(_snowman.nextInt(_snowman.getCount().getValue(_snowman)) + 1)).mapToObj(_snowmanxxxxxxx -> {
         int _snowmanxxx = _snowman.nextInt(16) + _snowman.getX();
         int _snowmanxxxx = _snowman.nextInt(16) + _snowman.getZ();
         int _snowmanxxxxx = _snowman.nextInt(120) + 4;
         return new BlockPos(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx);
      });
   }
}
