package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SaddleItem extends Item {
   public SaddleItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
      if (entity instanceof Saddleable && entity.isAlive()) {
         Saddleable lv = (Saddleable)entity;
         if (!lv.isSaddled() && lv.canBeSaddled()) {
            if (!user.world.isClient) {
               lv.saddle(SoundCategory.NEUTRAL);
               stack.decrement(1);
            }

            return ActionResult.success(user.world.isClient);
         }
      }

      return ActionResult.PASS;
   }
}
