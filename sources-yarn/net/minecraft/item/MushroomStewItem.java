package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MushroomStewItem extends Item {
   public MushroomStewItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack lv = super.finishUsing(stack, world, user);
      return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? lv : new ItemStack(Items.BOWL);
   }
}
