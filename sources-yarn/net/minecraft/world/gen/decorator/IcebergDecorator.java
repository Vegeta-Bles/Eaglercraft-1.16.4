package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class IcebergDecorator extends SimpleDecorator<NopeDecoratorConfig> {
   public IcebergDecorator(Codec<NopeDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, NopeDecoratorConfig arg, BlockPos arg2) {
      int i = random.nextInt(8) + 4 + arg2.getX();
      int j = random.nextInt(8) + 4 + arg2.getZ();
      return Stream.of(new BlockPos(i, arg2.getY(), j));
   }
}
