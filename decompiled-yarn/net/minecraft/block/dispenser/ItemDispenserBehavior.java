package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ItemDispenserBehavior implements DispenserBehavior {
   public ItemDispenserBehavior() {
   }

   @Override
   public final ItemStack dispense(BlockPointer _snowman, ItemStack _snowman) {
      ItemStack _snowmanxx = this.dispenseSilently(_snowman, _snowman);
      this.playSound(_snowman);
      this.spawnParticles(_snowman, _snowman.getBlockState().get(DispenserBlock.FACING));
      return _snowmanxx;
   }

   protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
      Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
      Position _snowmanx = DispenserBlock.getOutputLocation(pointer);
      ItemStack _snowmanxx = stack.split(1);
      spawnItem(pointer.getWorld(), _snowmanxx, 6, _snowman, _snowmanx);
      return stack;
   }

   public static void spawnItem(World world, ItemStack stack, int offset, Direction side, Position pos) {
      double _snowman = pos.getX();
      double _snowmanx = pos.getY();
      double _snowmanxx = pos.getZ();
      if (side.getAxis() == Direction.Axis.Y) {
         _snowmanx -= 0.125;
      } else {
         _snowmanx -= 0.15625;
      }

      ItemEntity _snowmanxxx = new ItemEntity(world, _snowman, _snowmanx, _snowmanxx, stack);
      double _snowmanxxxx = world.random.nextDouble() * 0.1 + 0.2;
      _snowmanxxx.setVelocity(
         world.random.nextGaussian() * 0.0075F * (double)offset + (double)side.getOffsetX() * _snowmanxxxx,
         world.random.nextGaussian() * 0.0075F * (double)offset + 0.2F,
         world.random.nextGaussian() * 0.0075F * (double)offset + (double)side.getOffsetZ() * _snowmanxxxx
      );
      world.spawnEntity(_snowmanxxx);
   }

   protected void playSound(BlockPointer pointer) {
      pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
   }

   protected void spawnParticles(BlockPointer pointer, Direction side) {
      pointer.getWorld().syncWorldEvent(2000, pointer.getBlockPos(), side.getId());
   }
}
