package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BlockPlacementDispenserBehavior extends FallibleItemDispenserBehavior {
   public BlockPlacementDispenserBehavior() {
   }

   @Override
   protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
      this.setSuccess(false);
      Item _snowman = stack.getItem();
      if (_snowman instanceof BlockItem) {
         Direction _snowmanx = pointer.getBlockState().get(DispenserBlock.FACING);
         BlockPos _snowmanxx = pointer.getBlockPos().offset(_snowmanx);
         Direction _snowmanxxx = pointer.getWorld().isAir(_snowmanxx.down()) ? _snowmanx : Direction.UP;
         this.setSuccess(((BlockItem)_snowman).place(new AutomaticItemPlacementContext(pointer.getWorld(), _snowmanxx, _snowmanx, stack, _snowmanxxx)).isAccepted());
      }

      return stack;
   }
}
