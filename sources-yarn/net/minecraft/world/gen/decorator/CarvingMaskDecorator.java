package net.minecraft.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class CarvingMaskDecorator extends Decorator<CarvingMaskDecoratorConfig> {
   public CarvingMaskDecorator(Codec<CarvingMaskDecoratorConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, CarvingMaskDecoratorConfig arg2, BlockPos arg3) {
      ChunkPos lv = new ChunkPos(arg3);
      BitSet bitSet = arg.getOrCreateCarvingMask(lv, arg2.step);
      return IntStream.range(0, bitSet.length()).filter(i -> bitSet.get(i) && random.nextFloat() < arg2.probability).mapToObj(i -> {
         int j = i & 15;
         int k = i >> 4 & 15;
         int l = i >> 8;
         return new BlockPos(lv.getStartX() + j, l, lv.getStartZ() + k);
      });
   }
}
