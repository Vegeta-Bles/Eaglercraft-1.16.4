package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BoatDispenserBehavior extends ItemDispenserBehavior {
   private final ItemDispenserBehavior itemDispenser = new ItemDispenserBehavior();
   private final BoatEntity.Type boatType;

   public BoatDispenserBehavior(BoatEntity.Type type) {
      this.boatType = type;
   }

   @Override
   public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
      Direction lv = pointer.getBlockState().get(DispenserBlock.FACING);
      World lv2 = pointer.getWorld();
      double d = pointer.getX() + (double)((float)lv.getOffsetX() * 1.125F);
      double e = pointer.getY() + (double)((float)lv.getOffsetY() * 1.125F);
      double f = pointer.getZ() + (double)((float)lv.getOffsetZ() * 1.125F);
      BlockPos lv3 = pointer.getBlockPos().offset(lv);
      double g;
      if (lv2.getFluidState(lv3).isIn(FluidTags.WATER)) {
         g = 1.0;
      } else {
         if (!lv2.getBlockState(lv3).isAir() || !lv2.getFluidState(lv3.down()).isIn(FluidTags.WATER)) {
            return this.itemDispenser.dispense(pointer, stack);
         }

         g = 0.0;
      }

      BoatEntity lv4 = new BoatEntity(lv2, d, e + g, f);
      lv4.setBoatType(this.boatType);
      lv4.yaw = lv.asRotation();
      lv2.spawnEntity(lv4);
      stack.decrement(1);
      return stack;
   }

   @Override
   protected void playSound(BlockPointer pointer) {
      pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
   }
}
