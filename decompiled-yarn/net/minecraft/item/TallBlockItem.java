package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class TallBlockItem extends BlockItem {
   public TallBlockItem(Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected boolean place(ItemPlacementContext context, BlockState state) {
      context.getWorld().setBlockState(context.getBlockPos().up(), Blocks.AIR.getDefaultState(), 27);
      return super.place(context, state);
   }
}
