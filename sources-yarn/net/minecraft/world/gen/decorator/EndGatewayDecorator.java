package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class EndGatewayDecorator extends Decorator<NopeDecoratorConfig> {
   public EndGatewayDecorator(Codec<NopeDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, NopeDecoratorConfig arg2, BlockPos arg3) {
      if (random.nextInt(700) == 0) {
         int i = random.nextInt(16) + arg3.getX();
         int j = random.nextInt(16) + arg3.getZ();
         int k = arg.getTopY(Heightmap.Type.MOTION_BLOCKING, i, j);
         if (k > 0) {
            int l = k + 3 + random.nextInt(7);
            return Stream.of(new BlockPos(i, l, j));
         }
      }

      return Stream.empty();
   }
}
