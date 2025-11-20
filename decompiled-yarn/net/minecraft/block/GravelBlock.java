package net.minecraft.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class GravelBlock extends FallingBlock {
   public GravelBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public int getColor(BlockState state, BlockView world, BlockPos pos) {
      return -8356741;
   }
}
