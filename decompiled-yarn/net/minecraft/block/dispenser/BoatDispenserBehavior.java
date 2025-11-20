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
      Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
      World _snowmanx = pointer.getWorld();
      double _snowmanxx = pointer.getX() + (double)((float)_snowman.getOffsetX() * 1.125F);
      double _snowmanxxx = pointer.getY() + (double)((float)_snowman.getOffsetY() * 1.125F);
      double _snowmanxxxx = pointer.getZ() + (double)((float)_snowman.getOffsetZ() * 1.125F);
      BlockPos _snowmanxxxxx = pointer.getBlockPos().offset(_snowman);
      double _snowmanxxxxxx;
      if (_snowmanx.getFluidState(_snowmanxxxxx).isIn(FluidTags.WATER)) {
         _snowmanxxxxxx = 1.0;
      } else {
         if (!_snowmanx.getBlockState(_snowmanxxxxx).isAir() || !_snowmanx.getFluidState(_snowmanxxxxx.down()).isIn(FluidTags.WATER)) {
            return this.itemDispenser.dispense(pointer, stack);
         }

         _snowmanxxxxxx = 0.0;
      }

      BoatEntity _snowmanxxxxxxx = new BoatEntity(_snowmanx, _snowmanxx, _snowmanxxx + _snowmanxxxxxx, _snowmanxxxx);
      _snowmanxxxxxxx.setBoatType(this.boatType);
      _snowmanxxxxxxx.yaw = _snowman.asRotation();
      _snowmanx.spawnEntity(_snowmanxxxxxxx);
      stack.decrement(1);
      return stack;
   }

   @Override
   protected void playSound(BlockPointer pointer) {
      pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
   }
}
