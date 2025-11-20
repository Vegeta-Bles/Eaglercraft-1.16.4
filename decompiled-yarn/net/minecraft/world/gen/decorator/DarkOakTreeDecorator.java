package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;

public class DarkOakTreeDecorator extends AbstractHeightmapDecorator<NopeDecoratorConfig> {
   public DarkOakTreeDecorator(Codec<NopeDecoratorConfig> _snowman) {
      super(_snowman);
   }

   protected Heightmap.Type getHeightmapType(NopeDecoratorConfig _snowman) {
      return Heightmap.Type.MOTION_BLOCKING;
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, NopeDecoratorConfig _snowman, BlockPos _snowman) {
      return IntStream.range(0, 16).mapToObj(_snowmanxxxxxxxxx -> {
         int _snowmanxxxxx = _snowmanxxxxxxxxx / 4;
         int _snowmanxxxxxx = _snowmanxxxxxxxxx % 4;
         int _snowmanxxxxxxx = _snowmanxxxxx * 4 + 1 + _snowman.nextInt(3) + _snowman.getX();
         int _snowmanxxxxxxxx = _snowmanxxxxxx * 4 + 1 + _snowman.nextInt(3) + _snowman.getZ();
         int _snowmanxxxxxxxxx = _snowman.getTopY(this.getHeightmapType(_snowman), _snowmanxxxxxxx, _snowmanxxxxxxxx);
         return new BlockPos(_snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx);
      });
   }
}
