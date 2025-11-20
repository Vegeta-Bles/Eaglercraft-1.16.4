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
   public FlintAndSteelItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      PlayerEntity lv = context.getPlayer();
      World lv2 = context.getWorld();
      BlockPos lv3 = context.getBlockPos();
      BlockState lv4 = lv2.getBlockState(lv3);
      if (CampfireBlock.method_30035(lv4)) {
         lv2.playSound(lv, lv3, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
         lv2.setBlockState(lv3, lv4.with(Properties.LIT, Boolean.valueOf(true)), 11);
         if (lv != null) {
            context.getStack().damage(1, lv, p -> p.sendToolBreakStatus(context.getHand()));
         }

         return ActionResult.success(lv2.isClient());
      } else {
         BlockPos lv5 = lv3.offset(context.getSide());
         if (AbstractFireBlock.method_30032(lv2, lv5, context.getPlayerFacing())) {
            lv2.playSound(lv, lv5, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
            BlockState lv6 = AbstractFireBlock.getState(lv2, lv5);
            lv2.setBlockState(lv5, lv6, 11);
            ItemStack lv7 = context.getStack();
            if (lv instanceof ServerPlayerEntity) {
               Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)lv, lv5, lv7);
               lv7.damage(1, lv, p -> p.sendToolBreakStatus(context.getHand()));
            }

            return ActionResult.success(lv2.isClient());
         } else {
            return ActionResult.FAIL;
         }
      }
   }
}
