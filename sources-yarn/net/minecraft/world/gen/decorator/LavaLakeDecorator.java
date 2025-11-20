package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class LavaLakeDecorator extends Decorator<ChanceDecoratorConfig> {
   public LavaLakeDecorator(Codec<ChanceDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, ChanceDecoratorConfig arg2, BlockPos arg3) {
      if (random.nextInt(arg2.chance / 10) == 0) {
         int i = random.nextInt(16) + arg3.getX();
         int j = random.nextInt(16) + arg3.getZ();
         int k = random.nextInt(random.nextInt(arg.getMaxY() - 8) + 8);
         if (k < arg.getSeaLevel() || random.nextInt(arg2.chance / 8) == 0) {
            return Stream.of(new BlockPos(i, k, j));
         }
      }

      return Stream.empty();
   }
}
