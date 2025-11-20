package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CountConfig;

public class CountDecorator extends SimpleDecorator<CountConfig> {
   public CountDecorator(Codec<CountConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, CountConfig _snowman, BlockPos _snowman) {
      return IntStream.range(0, _snowman.getCount().getValue(_snowman)).mapToObj(_snowmanxxxx -> _snowman);
   }
}
