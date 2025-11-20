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
      Item lv = stack.getItem();
      if (lv instanceof BlockItem) {
         Direction lv2 = pointer.getBlockState().get(DispenserBlock.FACING);
         BlockPos lv3 = pointer.getBlockPos().offset(lv2);
         Direction lv4 = pointer.getWorld().isAir(lv3.down()) ? lv2 : Direction.UP;
         this.setSuccess(((BlockItem)lv).place(new AutomaticItemPlacementContext(pointer.getWorld(), lv3, lv2, stack, lv4)).isAccepted());
      }

      return stack;
   }
}
