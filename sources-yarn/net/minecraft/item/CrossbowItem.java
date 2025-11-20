package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CrossbowItem extends RangedWeaponItem implements Vanishable {
   private boolean charged = false;
   private boolean loaded = false;

   public CrossbowItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public Predicate<ItemStack> getHeldProjectiles() {
      return CROSSBOW_HELD_PROJECTILES;
   }

   @Override
   public Predicate<ItemStack> getProjectiles() {
      return BOW_PROJECTILES;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack lv = user.getStackInHand(hand);
      if (isCharged(lv)) {
         shootAll(world, user, hand, lv, getSpeed(lv), 1.0F);
         setCharged(lv, false);
         return TypedActionResult.consume(lv);
      } else if (!user.getArrowType(lv).isEmpty()) {
         if (!isCharged(lv)) {
            this.charged = false;
            this.loaded = false;
            user.setCurrentHand(hand);
         }

         return TypedActionResult.consume(lv);
      } else {
         return TypedActionResult.fail(lv);
      }
   }

   @Override
   public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
      int j = this.getMaxUseTime(stack) - remainingUseTicks;
      float f = getPullProgress(j, stack);
      if (f >= 1.0F && !isCharged(stack) && loadProjectiles(user, stack)) {
         setCharged(stack, true);
         SoundCategory lv = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
         world.playSound(
            null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, lv, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
      int i = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
      int j = i == 0 ? 1 : 3;
      boolean bl = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).abilities.creativeMode;
      ItemStack lv = shooter.getArrowType(projectile);
      ItemStack lv2 = lv.copy();

      for (int k = 0; k < j; k++) {
         if (k > 0) {
            lv = lv2.copy();
         }

         if (lv.isEmpty() && bl) {
            lv = new ItemStack(Items.ARROW);
            lv2 = lv.copy();
         }

         if (!loadProjectile(shooter, projectile, lv, k > 0, bl)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
      if (projectile.isEmpty()) {
         return false;
      } else {
         boolean bl3 = creative && projectile.getItem() instanceof ArrowItem;
         ItemStack lv;
         if (!bl3 && !creative && !simulated) {
            lv = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
               ((PlayerEntity)shooter).inventory.removeOne(projectile);
            }
         } else {
            lv = projectile.copy();
         }

         putProjectile(crossbow, lv);
         return true;
      }
   }

   public static boolean isCharged(ItemStack stack) {
      CompoundTag lv = stack.getTag();
      return lv != null && lv.getBoolean("Charged");
   }

   public static void setCharged(ItemStack stack, boolean charged) {
      CompoundTag lv = stack.getOrCreateTag();
      lv.putBoolean("Charged", charged);
   }

   private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
      CompoundTag lv = crossbow.getOrCreateTag();
      ListTag lv2;
      if (lv.contains("ChargedProjectiles", 9)) {
         lv2 = lv.getList("ChargedProjectiles", 10);
      } else {
         lv2 = new ListTag();
      }

      CompoundTag lv4 = new CompoundTag();
      projectile.toTag(lv4);
      lv2.add(lv4);
      lv.put("ChargedProjectiles", lv2);
   }

   private static List<ItemStack> getProjectiles(ItemStack crossbow) {
      List<ItemStack> list = Lists.newArrayList();
      CompoundTag lv = crossbow.getTag();
      if (lv != null && lv.contains("ChargedProjectiles", 9)) {
         ListTag lv2 = lv.getList("ChargedProjectiles", 10);
         if (lv2 != null) {
            for (int i = 0; i < lv2.size(); i++) {
               CompoundTag lv3 = lv2.getCompound(i);
               list.add(ItemStack.fromTag(lv3));
            }
         }
      }

      return list;
   }

   private static void clearProjectiles(ItemStack crossbow) {
      CompoundTag lv = crossbow.getTag();
      if (lv != null) {
         ListTag lv2 = lv.getList("ChargedProjectiles", 9);
         lv2.clear();
         lv.put("ChargedProjectiles", lv2);
      }
   }

   public static boolean hasProjectile(ItemStack crossbow, Item projectile) {
      return getProjectiles(crossbow).stream().anyMatch(s -> s.getItem() == projectile);
   }

   private static void shoot(
      World world,
      LivingEntity shooter,
      Hand hand,
      ItemStack crossbow,
      ItemStack projectile,
      float soundPitch,
      boolean creative,
      float speed,
      float divergence,
      float simulated
   ) {
      if (!world.isClient) {
         boolean bl2 = projectile.getItem() == Items.FIREWORK_ROCKET;
         ProjectileEntity lv;
         if (bl2) {
            lv = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
         } else {
            lv = createArrow(world, shooter, crossbow, projectile);
            if (creative || simulated != 0.0F) {
               ((PersistentProjectileEntity)lv).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
         }

         if (shooter instanceof CrossbowUser) {
            CrossbowUser lv3 = (CrossbowUser)shooter;
            lv3.shoot(lv3.getTarget(), crossbow, lv, simulated);
         } else {
            Vec3d lv4 = shooter.getOppositeRotationVector(1.0F);
            Quaternion lv5 = new Quaternion(new Vector3f(lv4), simulated, true);
            Vec3d lv6 = shooter.getRotationVec(1.0F);
            Vector3f lv7 = new Vector3f(lv6);
            lv7.rotate(lv5);
            lv.setVelocity((double)lv7.getX(), (double)lv7.getY(), (double)lv7.getZ(), speed, divergence);
         }

         crossbow.damage(bl2 ? 3 : 1, shooter, e -> e.sendToolBreakStatus(hand));
         world.spawnEntity(lv);
         world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
      }
   }

   private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
      ArrowItem lv = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
      PersistentProjectileEntity lv2 = lv.createArrow(world, arrow, entity);
      if (entity instanceof PlayerEntity) {
         lv2.setCritical(true);
      }

      lv2.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
      lv2.setShotFromCrossbow(true);
      int i = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
      if (i > 0) {
         lv2.setPierceLevel((byte)i);
      }

      return lv2;
   }

   public static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence) {
      List<ItemStack> list = getProjectiles(stack);
      float[] fs = getSoundPitches(entity.getRandom());

      for (int i = 0; i < list.size(); i++) {
         ItemStack lv = list.get(i);
         boolean bl = entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.creativeMode;
         if (!lv.isEmpty()) {
            if (i == 0) {
               shoot(world, entity, hand, stack, lv, fs[i], bl, speed, divergence, 0.0F);
            } else if (i == 1) {
               shoot(world, entity, hand, stack, lv, fs[i], bl, speed, divergence, -10.0F);
            } else if (i == 2) {
               shoot(world, entity, hand, stack, lv, fs[i], bl, speed, divergence, 10.0F);
            }
         }
      }

      postShoot(world, entity, stack);
   }

   private static float[] getSoundPitches(Random random) {
      boolean bl = random.nextBoolean();
      return new float[]{1.0F, getSoundPitch(bl), getSoundPitch(!bl)};
   }

   private static float getSoundPitch(boolean flag) {
      float f = flag ? 0.63F : 0.43F;
      return 1.0F / (RANDOM.nextFloat() * 0.5F + 1.8F) + f;
   }

   private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity lv = (ServerPlayerEntity)entity;
         if (!world.isClient) {
            Criteria.SHOT_CROSSBOW.trigger(lv, stack);
         }

         lv.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
      }

      clearProjectiles(stack);
   }

   @Override
   public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.isClient) {
         int j = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
         SoundEvent lv = this.getQuickChargeSound(j);
         SoundEvent lv2 = j == 0 ? SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE : null;
         float f = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)getPullTime(stack);
         if (f < 0.2F) {
            this.charged = false;
            this.loaded = false;
         }

         if (f >= 0.2F && !this.charged) {
            this.charged = true;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), lv, SoundCategory.PLAYERS, 0.5F, 1.0F);
         }

         if (f >= 0.5F && lv2 != null && !this.loaded) {
            this.loaded = true;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), lv2, SoundCategory.PLAYERS, 0.5F, 1.0F);
         }
      }
   }

   @Override
   public int getMaxUseTime(ItemStack stack) {
      return getPullTime(stack) + 3;
   }

   public static int getPullTime(ItemStack stack) {
      int i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
      return i == 0 ? 25 : 25 - 5 * i;
   }

   @Override
   public UseAction getUseAction(ItemStack stack) {
      return UseAction.CROSSBOW;
   }

   private SoundEvent getQuickChargeSound(int stage) {
      switch (stage) {
         case 1:
            return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1;
         case 2:
            return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2;
         case 3:
            return SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3;
         default:
            return SoundEvents.ITEM_CROSSBOW_LOADING_START;
      }
   }

   private static float getPullProgress(int useTicks, ItemStack stack) {
      float f = (float)useTicks / (float)getPullTime(stack);
      if (f > 1.0F) {
         f = 1.0F;
      }

      return f;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      List<ItemStack> list2 = getProjectiles(stack);
      if (isCharged(stack) && !list2.isEmpty()) {
         ItemStack lv = list2.get(0);
         tooltip.add(new TranslatableText("item.minecraft.crossbow.projectile").append(" ").append(lv.toHoverableText()));
         if (context.isAdvanced() && lv.getItem() == Items.FIREWORK_ROCKET) {
            List<Text> list3 = Lists.newArrayList();
            Items.FIREWORK_ROCKET.appendTooltip(lv, world, list3, context);
            if (!list3.isEmpty()) {
               for (int i = 0; i < list3.size(); i++) {
                  list3.set(i, new LiteralText("  ").append(list3.get(i)).formatted(Formatting.GRAY));
               }

               tooltip.addAll(list3);
            }
         }
      }
   }

   private static float getSpeed(ItemStack stack) {
      return stack.getItem() == Items.CROSSBOW && hasProjectile(stack, Items.FIREWORK_ROCKET) ? 1.6F : 3.15F;
   }

   @Override
   public int getRange() {
      return 8;
   }
}
