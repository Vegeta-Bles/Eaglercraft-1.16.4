package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class CarvingMaskDecorator extends Decorator<CarvingMaskDecoratorConfig> {
   public CarvingMaskDecorator(Codec<CarvingMaskDecoratorConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, CarvingMaskDecoratorConfig _snowman, BlockPos _snowman) {
      ChunkPos _snowmanxxxx = new ChunkPos(_snowman);
      BitSet _snowmanxxxxx = _snowman.getOrCreateCarvingMask(_snowmanxxxx, _snowman.step);
      return IntStream.range(0, _snowmanxxxxx.length()).filter(_snowmanxxxxxx -> _snowman.get(_snowmanxxxxxx) && _snowman.nextFloat() < _snowman.probability).mapToObj(_snowmanxxxxxxxx -> {
         int _snowmanxx = _snowmanxxxxxxxx & 15;
         int _snowmanxxx = _snowmanxxxxxxxx >> 4 & 15;
         int _snowmanxxxxxxxx = _snowmanxxxxxxxx >> 8;
         return new BlockPos(_snowman.getStartX() + _snowmanxx, _snowmanxxxxxxxx, _snowman.getStartZ() + _snowmanxxx);
      });
   }
}
