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
   public BowItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
      if (user instanceof PlayerEntity) {
         PlayerEntity lv = (PlayerEntity)user;
         boolean bl = lv.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
         ItemStack lv2 = lv.getArrowType(stack);
         if (!lv2.isEmpty() || bl) {
            if (lv2.isEmpty()) {
               lv2 = new ItemStack(Items.ARROW);
            }

            int j = this.getMaxUseTime(stack) - remainingUseTicks;
            float f = getPullProgress(j);
            if (!((double)f < 0.1)) {
               boolean bl2 = bl && lv2.getItem() == Items.ARROW;
               if (!world.isClient) {
                  ArrowItem lv3 = (ArrowItem)(lv2.getItem() instanceof ArrowItem ? lv2.getItem() : Items.ARROW);
                  PersistentProjectileEntity lv4 = lv3.createArrow(world, lv2, lv);
                  lv4.setProperties(lv, lv.pitch, lv.yaw, 0.0F, f * 3.0F, 1.0F);
                  if (f == 1.0F) {
                     lv4.setCritical(true);
                  }

                  int k = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                  if (k > 0) {
                     lv4.setDamage(lv4.getDamage() + (double)k * 0.5 + 0.5);
                  }

                  int l = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                  if (l > 0) {
                     lv4.setPunch(l);
                  }

                  if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                     lv4.setOnFireFor(100);
                  }

                  stack.damage(1, lv, p -> p.sendToolBreakStatus(lv.getActiveHand()));
                  if (bl2 || lv.abilities.creativeMode && (lv2.getItem() == Items.SPECTRAL_ARROW || lv2.getItem() == Items.TIPPED_ARROW)) {
                     lv4.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                  }

                  world.spawnEntity(lv4);
               }

               world.playSound(
                  null,
                  lv.getX(),
                  lv.getY(),
                  lv.getZ(),
                  SoundEvents.ENTITY_ARROW_SHOOT,
                  SoundCategory.PLAYERS,
                  1.0F,
                  1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + f * 0.5F
               );
               if (!bl2 && !lv.abilities.creativeMode) {
                  lv2.decrement(1);
                  if (lv2.isEmpty()) {
                     lv.inventory.removeOne(lv2);
                  }
               }

               lv.incrementStat(Stats.USED.getOrCreateStat(this));
            }
         }
      }
   }

   public static float getPullProgress(int useTicks) {
      float f = (float)useTicks / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
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
      ItemStack lv = user.getStackInHand(hand);
      boolean bl = !user.getArrowType(lv).isEmpty();
      if (!user.abilities.creativeMode && !bl) {
         return TypedActionResult.fail(lv);
      } else {
         user.setCurrentHand(hand);
         return TypedActionResult.consume(lv);
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
