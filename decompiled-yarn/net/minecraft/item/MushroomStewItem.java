package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MushroomStewItem extends Item {
   public MushroomStewItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack _snowman = super.finishUsing(stack, world, user);
      return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? _snowman : new ItemStack(Items.BOWL);
   }
}
