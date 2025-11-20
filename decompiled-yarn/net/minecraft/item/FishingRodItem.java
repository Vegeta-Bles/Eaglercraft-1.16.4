package net.minecraft.item;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class FishingRodItem extends Item implements Vanishable {
   public FishingRodItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      if (user.fishHook != null) {
         if (!world.isClient) {
            int _snowmanx = user.fishHook.use(_snowman);
            _snowman.damage(_snowmanx, user, p -> p.sendToolBreakStatus(hand));
         }

         world.playSound(
            null,
            user.getX(),
            user.getY(),
            user.getZ(),
            SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE,
            SoundCategory.NEUTRAL,
            1.0F,
            0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F)
         );
      } else {
         world.playSound(
            null,
            user.getX(),
            user.getY(),
            user.getZ(),
            SoundEvents.ENTITY_FISHING_BOBBER_THROW,
            SoundCategory.NEUTRAL,
            0.5F,
            0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F)
         );
         if (!world.isClient) {
            int _snowmanx = EnchantmentHelper.getLure(_snowman);
            int _snowmanxx = EnchantmentHelper.getLuckOfTheSea(_snowman);
            world.spawnEntity(new FishingBobberEntity(user, world, _snowmanxx, _snowmanx));
         }

         user.incrementStat(Stats.USED.getOrCreateStat(this));
      }

      return TypedActionResult.success(_snowman, world.isClient());
   }

   @Override
   public int getEnchantability() {
      return 1;
   }
}
