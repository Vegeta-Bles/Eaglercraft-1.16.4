package net.minecraft.world.gen.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CountConfig;

public class FireDecorator extends SimpleDecorator<CountConfig> {
   public FireDecorator(Codec<CountConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(Random _snowman, CountConfig _snowman, BlockPos _snowman) {
      List<BlockPos> _snowmanxxx = Lists.newArrayList();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.nextInt(_snowman.nextInt(_snowman.getCount().getValue(_snowman)) + 1) + 1; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowman.nextInt(16) + _snowman.getX();
         int _snowmanxxxxxx = _snowman.nextInt(16) + _snowman.getZ();
         int _snowmanxxxxxxx = _snowman.nextInt(120) + 4;
         _snowmanxxx.add(new BlockPos(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx));
      }

      return _snowmanxxx.stream();
   }
}
