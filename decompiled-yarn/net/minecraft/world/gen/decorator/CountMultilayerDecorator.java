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
   public CountMultilayerDecorator(Codec<CountConfig> _snowman) {
      super(_snowman);
   }

   public Stream<BlockPos> getPositions(DecoratorContext _snowman, Random _snowman, CountConfig _snowman, BlockPos _snowman) {
      List<BlockPos> _snowmanxxxx = Lists.newArrayList();
      int _snowmanxxxxx = 0;

      boolean _snowmanxxxxxx;
      do {
         _snowmanxxxxxx = false;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman.getCount().getValue(_snowman); _snowmanxxxxxxx++) {
            int _snowmanxxxxxxxx = _snowman.nextInt(16) + _snowman.getX();
            int _snowmanxxxxxxxxx = _snowman.nextInt(16) + _snowman.getZ();
            int _snowmanxxxxxxxxxx = _snowman.getTopY(Heightmap.Type.MOTION_BLOCKING, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxx = findPos(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx);
            if (_snowmanxxxxxxxxxxx != Integer.MAX_VALUE) {
               _snowmanxxxx.add(new BlockPos(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx));
               _snowmanxxxxxx = true;
            }
         }

         _snowmanxxxxx++;
      } while (_snowmanxxxxxx);

      return _snowmanxxxx.stream();
   }

   private static int findPos(DecoratorContext context, int x, int y, int z, int targetY) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable(x, y, z);
      int _snowmanx = 0;
      BlockState _snowmanxx = context.getBlockState(_snowman);

      for (int _snowmanxxx = y; _snowmanxxx >= 1; _snowmanxxx--) {
         _snowman.setY(_snowmanxxx - 1);
         BlockState _snowmanxxxx = context.getBlockState(_snowman);
         if (!blocksSpawn(_snowmanxxxx) && blocksSpawn(_snowmanxx) && !_snowmanxxxx.isOf(Blocks.BEDROCK)) {
            if (_snowmanx == targetY) {
               return _snowman.getY() + 1;
            }

            _snowmanx++;
         }

         _snowmanxx = _snowmanxxxx;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean blocksSpawn(BlockState state) {
      return state.isAir() || state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA);
   }
}
