package net.minecraft.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintAndSteelItem extends Item {
   public FlintAndSteelItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      PlayerEntity _snowman = context.getPlayer();
      World _snowmanx = context.getWorld();
      BlockPos _snowmanxx = context.getBlockPos();
      BlockState _snowmanxxx = _snowmanx.getBlockState(_snowmanxx);
      if (CampfireBlock.method_30035(_snowmanxxx)) {
         _snowmanx.playSound(_snowman, _snowmanxx, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
         _snowmanx.setBlockState(_snowmanxx, _snowmanxxx.with(Properties.LIT, Boolean.valueOf(true)), 11);
         if (_snowman != null) {
            context.getStack().damage(1, _snowman, p -> p.sendToolBreakStatus(context.getHand()));
         }

         return ActionResult.success(_snowmanx.isClient());
      } else {
         BlockPos _snowmanxxxx = _snowmanxx.offset(context.getSide());
         if (AbstractFireBlock.method_30032(_snowmanx, _snowmanxxxx, context.getPlayerFacing())) {
            _snowmanx.playSound(_snowman, _snowmanxxxx, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
            BlockState _snowmanxxxxx = AbstractFireBlock.getState(_snowmanx, _snowmanxxxx);
            _snowmanx.setBlockState(_snowmanxxxx, _snowmanxxxxx, 11);
            ItemStack _snowmanxxxxxx = context.getStack();
            if (_snowman instanceof ServerPlayerEntity) {
               Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)_snowman, _snowmanxxxx, _snowmanxxxxxx);
               _snowmanxxxxxx.damage(1, _snowman, p -> p.sendToolBreakStatus(context.getHand()));
            }

            return ActionResult.success(_snowmanx.isClient());
         } else {
            return ActionResult.FAIL;
         }
      }
   }
}
