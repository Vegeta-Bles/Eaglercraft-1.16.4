package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class HeightmapSpreadDoubleDecorator<DC extends DecoratorConfig> extends AbstractHeightmapDecorator<DC> {
   public HeightmapSpreadDoubleDecorator(Codec<DC> _snowman) {
      super(_snowman);
   }

   @Override
   protected Heightmap.Type getHeightmapType(DC config) {
      return Heightmap.Type.MOTION_BLOCKING;
   }

   @Override
   public Stream<BlockPos> getPositions(DecoratorContext context, Random random, DC config, BlockPos pos) {
      int _snowman = pos.getX();
      int _snowmanx = pos.getZ();
      int _snowmanxx = context.getTopY(this.getHeightmapType(config), _snowman, _snowmanx);
      return _snowmanxx == 0 ? Stream.of() : Stream.of(new BlockPos(_snowman, random.nextInt(_snowmanxx * 2), _snowmanx));
   }
}
