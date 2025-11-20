package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class BedItem extends BlockItem {
   public BedItem(Block arg, Item.Settings arg2) {
      super(arg, arg2);
   }

   @Override
   protected boolean place(ItemPlacementContext context, BlockState state) {
      return context.getWorld().setBlockState(context.getBlockPos(), state, 26);
   }
}
