package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class Spread32AboveDecorator extends Decorator<NopeDecoratorConfig> {
   public Spread32AboveDecorator(Codec<NopeDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, NopeDecoratorConfig arg2, BlockPos arg3) {
      int i = random.nextInt(arg3.getY() + 32);
      return Stream.of(new BlockPos(arg3.getX(), i, arg3.getZ()));
   }
}
