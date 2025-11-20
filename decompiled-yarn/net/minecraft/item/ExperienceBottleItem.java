package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ExperienceBottleItem extends Item {
   public ExperienceBottleItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      world.playSound(
         null,
         user.getX(),
         user.getY(),
         user.getZ(),
         SoundEvents.ENTITY_EXPERIENCE_BOTTLE_THROW,
         SoundCategory.NEUTRAL,
         0.5F,
         0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F)
      );
      if (!world.isClient) {
         ExperienceBottleEntity _snowmanx = new ExperienceBottleEntity(world, user);
         _snowmanx.setItem(_snowman);
         _snowmanx.setProperties(user, user.pitch, user.yaw, -20.0F, 0.7F, 1.0F);
         world.spawnEntity(_snowmanx);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.abilities.creativeMode) {
         _snowman.decrement(1);
      }

      return TypedActionResult.success(_snowman, world.isClient());
   }
}
