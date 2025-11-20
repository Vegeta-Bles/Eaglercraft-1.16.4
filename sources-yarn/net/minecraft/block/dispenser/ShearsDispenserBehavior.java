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
      World lv = pointer.getWorld();
      if (!lv.isClient()) {
         BlockPos lv2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
         this.setSuccess(tryShearBlock((ServerWorld)lv, lv2) || tryShearEntity((ServerWorld)lv, lv2));
         if (this.isSuccess() && stack.damage(1, lv.getRandom(), null)) {
            stack.setCount(0);
         }
      }

      return stack;
   }

   private static boolean tryShearBlock(ServerWorld world, BlockPos pos) {
      BlockState lv = world.getBlockState(pos);
      if (lv.isIn(BlockTags.BEEHIVES)) {
         int i = lv.get(BeehiveBlock.HONEY_LEVEL);
         if (i >= 5) {
            world.playSound(null, pos, SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            BeehiveBlock.dropHoneycomb(world, pos);
            ((BeehiveBlock)lv.getBlock()).takeHoney(world, lv, pos, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
            return true;
         }
      }

      return false;
   }

   private static boolean tryShearEntity(ServerWorld world, BlockPos pos) {
      for (LivingEntity lv : world.getEntitiesByClass(LivingEntity.class, new Box(pos), EntityPredicates.EXCEPT_SPECTATOR)) {
         if (lv instanceof Shearable) {
            Shearable lv2 = (Shearable)lv;
            if (lv2.isShearable()) {
               lv2.sheared(SoundCategory.BLOCKS);
               return true;
            }
         }
      }

      return false;
   }
}
