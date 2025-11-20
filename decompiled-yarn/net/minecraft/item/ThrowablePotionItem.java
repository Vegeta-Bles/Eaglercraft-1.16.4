package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ThrowablePotionItem extends PotionItem {
   public ThrowablePotionItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      if (!world.isClient) {
         PotionEntity _snowmanx = new PotionEntity(world, user);
         _snowmanx.setItem(_snowman);
         _snowmanx.setProperties(user, user.pitch, user.yaw, -20.0F, 0.5F, 1.0F);
         world.spawnEntity(_snowmanx);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.abilities.creativeMode) {
         _snowman.decrement(1);
      }

      return TypedActionResult.success(_snowman, world.isClient());
   }
}
