package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public abstract class HeightmapDecorator<DC extends DecoratorConfig> extends AbstractHeightmapDecorator<DC> {
   public HeightmapDecorator(Codec<DC> _snowman) {
      super(_snowman);
   }

   @Override
   public Stream<BlockPos> getPositions(DecoratorContext context, Random random, DC config, BlockPos pos) {
      int _snowman = pos.getX();
      int _snowmanx = pos.getZ();
      int _snowmanxx = context.getTopY(this.getHeightmapType(config), _snowman, _snowmanx);
      return _snowmanxx > 0 ? Stream.of(new BlockPos(_snowman, _snowmanxx, _snowmanx)) : Stream.of();
   }
}
