package net.minecraft.item;

import java.util.function.Predicate;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BowItem extends RangedWeaponItem implements Vanishable {
   public BowItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
      if (user instanceof PlayerEntity) {
         PlayerEntity _snowman = (PlayerEntity)user;
         boolean _snowmanx = _snowman.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
         ItemStack _snowmanxx = _snowman.getArrowType(stack);
         if (!_snowmanxx.isEmpty() || _snowmanx) {
            if (_snowmanxx.isEmpty()) {
               _snowmanxx = new ItemStack(Items.ARROW);
            }

            int _snowmanxxx = this.getMaxUseTime(stack) - remainingUseTicks;
            float _snowmanxxxx = getPullProgress(_snowmanxxx);
            if (!((double)_snowmanxxxx < 0.1)) {
               boolean _snowmanxxxxx = _snowmanx && _snowmanxx.getItem() == Items.ARROW;
               if (!world.isClient) {
                  ArrowItem _snowmanxxxxxx = (ArrowItem)(_snowmanxx.getItem() instanceof ArrowItem ? _snowmanxx.getItem() : Items.ARROW);
                  PersistentProjectileEntity _snowmanxxxxxxx = _snowmanxxxxxx.createArrow(world, _snowmanxx, _snowman);
                  _snowmanxxxxxxx.setProperties(_snowman, _snowman.pitch, _snowman.yaw, 0.0F, _snowmanxxxx * 3.0F, 1.0F);
                  if (_snowmanxxxx == 1.0F) {
                     _snowmanxxxxxxx.setCritical(true);
                  }

                  int _snowmanxxxxxxxx = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                  if (_snowmanxxxxxxxx > 0) {
                     _snowmanxxxxxxx.setDamage(_snowmanxxxxxxx.getDamage() + (double)_snowmanxxxxxxxx * 0.5 + 0.5);
                  }

                  int _snowmanxxxxxxxxx = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                  if (_snowmanxxxxxxxxx > 0) {
                     _snowmanxxxxxxx.setPunch(_snowmanxxxxxxxxx);
                  }

                  if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                     _snowmanxxxxxxx.setOnFireFor(100);
                  }

                  stack.damage(1, _snowman, p -> p.sendToolBreakStatus(_snowman.getActiveHand()));
                  if (_snowmanxxxxx || _snowman.abilities.creativeMode && (_snowmanxx.getItem() == Items.SPECTRAL_ARROW || _snowmanxx.getItem() == Items.TIPPED_ARROW)) {
                     _snowmanxxxxxxx.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                  }

                  world.spawnEntity(_snowmanxxxxxxx);
               }

               world.playSound(
                  null,
                  _snowman.getX(),
                  _snowman.getY(),
                  _snowman.getZ(),
                  SoundEvents.ENTITY_ARROW_SHOOT,
                  SoundCategory.PLAYERS,
                  1.0F,
                  1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + _snowmanxxxx * 0.5F
               );
               if (!_snowmanxxxxx && !_snowman.abilities.creativeMode) {
                  _snowmanxx.decrement(1);
                  if (_snowmanxx.isEmpty()) {
                     _snowman.inventory.removeOne(_snowmanxx);
                  }
               }

               _snowman.incrementStat(Stats.USED.getOrCreateStat(this));
            }
         }
      }
   }

   public static float getPullProgress(int useTicks) {
      float _snowman = (float)useTicks / 20.0F;
      _snowman = (_snowman * _snowman + _snowman * 2.0F) / 3.0F;
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return _snowman;
   }

   @Override
   public int getMaxUseTime(ItemStack stack) {
      return 72000;
   }

   @Override
   public UseAction getUseAction(ItemStack stack) {
      return UseAction.BOW;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      boolean _snowmanx = !user.getArrowType(_snowman).isEmpty();
      if (!user.abilities.creativeMode && !_snowmanx) {
         return TypedActionResult.fail(_snowman);
      } else {
         user.setCurrentHand(hand);
         return TypedActionResult.consume(_snowman);
      }
   }

   @Override
   public Predicate<ItemStack> getProjectiles() {
      return BOW_PROJECTILES;
   }

   @Override
   public int getRange() {
      return 15;
   }
}
