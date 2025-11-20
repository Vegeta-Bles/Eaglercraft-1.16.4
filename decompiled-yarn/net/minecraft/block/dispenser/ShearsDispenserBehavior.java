package net.minecraft.block.dispenser;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ShearsDispenserBehavior extends FallibleItemDispenserBehavior {
   public ShearsDispenserBehavior() {
   }

   @Override
   protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
      World _snowman = pointer.getWorld();
      if (!_snowman.isClient()) {
         BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
         this.setSuccess(tryShearBlock((ServerWorld)_snowman, _snowmanx) || tryShearEntity((ServerWorld)_snowman, _snowmanx));
         if (this.isSuccess() && stack.damage(1, _snowman.getRandom(), null)) {
            stack.setCount(0);
         }
      }

      return stack;
   }

   private static boolean tryShearBlock(ServerWorld world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      if (_snowman.isIn(BlockTags.BEEHIVES)) {
         int _snowmanx = _snowman.get(BeehiveBlock.HONEY_LEVEL);
         if (_snowmanx >= 5) {
            world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BeehiveBlock.dropHoneycomb(world, pos);
            ((BeehiveBlock)_snowman.getBlock()).takeHoney(world, _snowman, pos, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
            return true;
         }
      }

      return false;
   }

   private static boolean tryShearEntity(ServerWorld world, BlockPos pos) {
      for (LivingEntity _snowman : world.getEntitiesByClass(LivingEntity.class, new Box(pos), EntityPredicates.EXCEPT_SPECTATOR)) {
         if (_snowman instanceof Shearable) {
            Shearable _snowmanx = (Shearable)_snowman;
            if (_snowmanx.isShearable()) {
               _snowmanx.sheared(SoundCategory.BLOCKS);
               return true;
            }
         }
      }

      return false;
   }
}
