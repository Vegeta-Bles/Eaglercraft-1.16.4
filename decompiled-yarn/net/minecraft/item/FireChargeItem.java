package net.minecraft.item;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireChargeItem extends Item {
   public FireChargeItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      boolean _snowmanxxx = false;
      if (CampfireBlock.method_30035(_snowmanxx)) {
         this.playUseSound(_snowman, _snowmanx);
         _snowman.setBlockState(_snowmanx, _snowmanxx.with(CampfireBlock.LIT, Boolean.valueOf(true)));
         _snowmanxxx = true;
      } else {
         _snowmanx = _snowmanx.offset(context.getSide());
         if (AbstractFireBlock.method_30032(_snowman, _snowmanx, context.getPlayerFacing())) {
            this.playUseSound(_snowman, _snowmanx);
            _snowman.setBlockState(_snowmanx, AbstractFireBlock.getState(_snowman, _snowmanx));
            _snowmanxxx = true;
         }
      }

      if (_snowmanxxx) {
         context.getStack().decrement(1);
         return ActionResult.success(_snowman.isClient);
      } else {
         return ActionResult.FAIL;
      }
   }

   private void playUseSound(World world, BlockPos pos) {
      world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2F + 1.0F);
   }
}
