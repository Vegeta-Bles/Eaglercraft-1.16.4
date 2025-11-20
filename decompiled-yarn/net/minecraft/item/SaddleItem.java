package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class SaddleItem extends Item {
   public SaddleItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
      if (entity instanceof Saddleable && entity.isAlive()) {
         Saddleable _snowman = (Saddleable)entity;
         if (!_snowman.isSaddled() && _snowman.canBeSaddled()) {
            if (!user.world.isClient) {
               _snowman.saddle(SoundCategory.NEUTRAL);
               stack.decrement(1);
            }

            return ActionResult.success(user.world.isClient);
         }
      }

      return ActionResult.PASS;
   }
}
