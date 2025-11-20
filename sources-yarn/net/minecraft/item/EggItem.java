package net.minecraft.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class EggItem extends Item {
   public EggItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack lv = user.getStackInHand(hand);
      world.playSound(
         null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F)
      );
      if (!world.isClient) {
         EggEntity lv2 = new EggEntity(world, user);
         lv2.setItem(lv);
         lv2.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
         world.spawnEntity(lv2);
      }

      user.incrementStat(Stats.USED.getOrCreateStat(this));
      if (!user.abilities.creativeMode) {
         lv.decrement(1);
      }

      return TypedActionResult.success(lv, world.isClient());
   }
}
