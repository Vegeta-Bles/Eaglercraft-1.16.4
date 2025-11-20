package net.minecraft.world.gen.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.CountConfig;

public class FireDecorator extends SimpleDecorator<CountConfig> {
   public FireDecorator(Codec<CountConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(Random random, CountConfig arg, BlockPos arg2) {
      List<BlockPos> list = Lists.newArrayList();

      for (int i = 0; i < random.nextInt(random.nextInt(arg.getCount().getValue(random)) + 1) + 1; i++) {
         int j = random.nextInt(16) + arg2.getX();
         int k = random.nextInt(16) + arg2.getZ();
         int l = random.nextInt(120) + 4;
         list.add(new BlockPos(j, l, k));
      }

      return list.stream();
   }
}
