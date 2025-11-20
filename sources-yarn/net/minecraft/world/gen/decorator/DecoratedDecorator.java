package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class DecoratedDecorator extends Decorator<DecoratedDecoratorConfig> {
   public DecoratedDecorator(Codec<DecoratedDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, DecoratedDecoratorConfig arg2, BlockPos arg3) {
      return arg2.getOuter().getPositions(arg, random, arg3).flatMap(arg3x -> arg2.getInner().getPositions(arg, random, arg3x));
   }
}
