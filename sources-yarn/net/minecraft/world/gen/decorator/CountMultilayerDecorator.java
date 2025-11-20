package net.minecraft.world.gen.decorator;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.CountConfig;

public class CountMultilayerDecorator extends Decorator<CountConfig> {
   public CountMultilayerDecorator(Codec<CountConfig> codec) {
      super(codec);
   }

   public Stream<BlockPos> getPositions(DecoratorContext arg, Random random, CountConfig arg2, BlockPos arg3) {
      List<BlockPos> list = Lists.newArrayList();
      int i = 0;

      boolean bl;
      do {
         bl = false;

         for (int j = 0; j < arg2.getCount().getValue(random); j++) {
            int k = random.nextInt(16) + arg3.getX();
            int l = random.nextInt(16) + arg3.getZ();
            int m = arg.getTopY(Heightmap.Type.MOTION_BLOCKING, k, l);
            int n = findPos(arg, k, m, l, i);
            if (n != Integer.MAX_VALUE) {
               list.add(new BlockPos(k, n, l));
               bl = true;
            }
         }

         i++;
      } while (bl);

      return list.stream();
   }

   private static int findPos(DecoratorContext context, int x, int y, int z, int targetY) {
      BlockPos.Mutable lv = new BlockPos.Mutable(x, y, z);
      int m = 0;
      BlockState lv2 = context.getBlockState(lv);

      for (int n = y; n >= 1; n--) {
         lv.setY(n - 1);
         BlockState lv3 = context.getBlockState(lv);
         if (!blocksSpawn(lv3) && blocksSpawn(lv2) && !lv3.isOf(Blocks.BEDROCK)) {
            if (m == targetY) {
               return lv.getY() + 1;
            }

            m++;
         }

         lv2 = lv3;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean blocksSpawn(BlockState state) {
      return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
   }
}
