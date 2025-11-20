package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChorusFruitItem extends Item {
   public ChorusFruitItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack _snowman = super.finishUsing(stack, world, user);
      if (!world.isClient) {
         double _snowmanx = user.getX();
         double _snowmanxx = user.getY();
         double _snowmanxxx = user.getZ();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            double _snowmanxxxxx = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            double _snowmanxxxxxx = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), 0.0, (double)(world.getDimensionHeight() - 1));
            double _snowmanxxxxxxx = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
            if (user.hasVehicle()) {
               user.stopRiding();
            }

            if (user.teleport(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, true)) {
               SoundEvent _snowmanxxxxxxxx = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
               world.playSound(null, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxxxx, SoundCategory.PLAYERS, 1.0F, 1.0F);
               user.playSound(_snowmanxxxxxxxx, 1.0F, 1.0F);
               break;
            }
         }

         if (user instanceof PlayerEntity) {
            ((PlayerEntity)user).getItemCooldownManager().set(this, 20);
         }
      }

      return _snowman;
   }
}
