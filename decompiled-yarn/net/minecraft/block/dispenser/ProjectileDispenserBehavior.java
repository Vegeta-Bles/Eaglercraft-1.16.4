package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public abstract class ProjectileDispenserBehavior extends ItemDispenserBehavior {
   public ProjectileDispenserBehavior() {
   }

   @Override
   public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
      World _snowman = pointer.getWorld();
      Position _snowmanx = DispenserBlock.getOutputLocation(pointer);
      Direction _snowmanxx = pointer.getBlockState().get(DispenserBlock.FACING);
      ProjectileEntity _snowmanxxx = this.createProjectile(_snowman, _snowmanx, stack);
      _snowmanxxx.setVelocity((double)_snowmanxx.getOffsetX(), (double)((float)_snowmanxx.getOffsetY() + 0.1F), (double)_snowmanxx.getOffsetZ(), this.getForce(), this.getVariation());
      _snowman.spawnEntity(_snowmanxxx);
      stack.decrement(1);
      return stack;
   }

   @Override
   protected void playSound(BlockPointer pointer) {
      pointer.getWorld().syncWorldEvent(1002, pointer.getBlockPos(), 0);
   }

   protected abstract ProjectileEntity createProjectile(World world, Position position, ItemStack stack);

   protected float getVariation() {
      return 6.0F;
   }

   protected float getForce() {
      return 1.1F;
   }
}
