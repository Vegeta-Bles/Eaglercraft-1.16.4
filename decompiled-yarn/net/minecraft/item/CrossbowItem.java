package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
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

   public CrossbowItem(Item.Settings _snowman) {
      super(_snowman);
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
      ItemStack _snowman = user.getStackInHand(hand);
      if (isCharged(_snowman)) {
         shootAll(world, user, hand, _snowman, getSpeed(_snowman), 1.0F);
         setCharged(_snowman, false);
         return TypedActionResult.consume(_snowman);
      } else if (!user.getArrowType(_snowman).isEmpty()) {
         if (!isCharged(_snowman)) {
            this.charged = false;
            this.loaded = false;
            user.setCurrentHand(hand);
         }

         return TypedActionResult.consume(_snowman);
      } else {
         return TypedActionResult.fail(_snowman);
      }
   }

   @Override
   public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
      int _snowman = this.getMaxUseTime(stack) - remainingUseTicks;
      float _snowmanx = getPullProgress(_snowman, stack);
      if (_snowmanx >= 1.0F && !isCharged(stack) && loadProjectiles(user, stack)) {
         setCharged(stack, true);
         SoundCategory _snowmanxx = user instanceof PlayerEntity ? SoundCategory.PLAYERS : SoundCategory.HOSTILE;
         world.playSound(
            null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_LOADING_END, _snowmanxx, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.5F + 1.0F) + 0.2F
         );
      }
   }

   private static boolean loadProjectiles(LivingEntity shooter, ItemStack projectile) {
      int _snowman = EnchantmentHelper.getLevel(Enchantments.MULTISHOT, projectile);
      int _snowmanx = _snowman == 0 ? 1 : 3;
      boolean _snowmanxx = shooter instanceof PlayerEntity && ((PlayerEntity)shooter).abilities.creativeMode;
      ItemStack _snowmanxxx = shooter.getArrowType(projectile);
      ItemStack _snowmanxxxx = _snowmanxxx.copy();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanx; _snowmanxxxxx++) {
         if (_snowmanxxxxx > 0) {
            _snowmanxxx = _snowmanxxxx.copy();
         }

         if (_snowmanxxx.isEmpty() && _snowmanxx) {
            _snowmanxxx = new ItemStack(Items.ARROW);
            _snowmanxxxx = _snowmanxxx.copy();
         }

         if (!loadProjectile(shooter, projectile, _snowmanxxx, _snowmanxxxxx > 0, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   private static boolean loadProjectile(LivingEntity shooter, ItemStack crossbow, ItemStack projectile, boolean simulated, boolean creative) {
      if (projectile.isEmpty()) {
         return false;
      } else {
         boolean _snowman = creative && projectile.getItem() instanceof ArrowItem;
         ItemStack _snowmanx;
         if (!_snowman && !creative && !simulated) {
            _snowmanx = projectile.split(1);
            if (projectile.isEmpty() && shooter instanceof PlayerEntity) {
               ((PlayerEntity)shooter).inventory.removeOne(projectile);
            }
         } else {
            _snowmanx = projectile.copy();
         }

         putProjectile(crossbow, _snowmanx);
         return true;
      }
   }

   public static boolean isCharged(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null && _snowman.getBoolean("Charged");
   }

   public static void setCharged(ItemStack stack, boolean charged) {
      CompoundTag _snowman = stack.getOrCreateTag();
      _snowman.putBoolean("Charged", charged);
   }

   private static void putProjectile(ItemStack crossbow, ItemStack projectile) {
      CompoundTag _snowman = crossbow.getOrCreateTag();
      ListTag _snowmanx;
      if (_snowman.contains("ChargedProjectiles", 9)) {
         _snowmanx = _snowman.getList("ChargedProjectiles", 10);
      } else {
         _snowmanx = new ListTag();
      }

      CompoundTag _snowmanxx = new CompoundTag();
      projectile.toTag(_snowmanxx);
      _snowmanx.add(_snowmanxx);
      _snowman.put("ChargedProjectiles", _snowmanx);
   }

   private static List<ItemStack> getProjectiles(ItemStack crossbow) {
      List<ItemStack> _snowman = Lists.newArrayList();
      CompoundTag _snowmanx = crossbow.getTag();
      if (_snowmanx != null && _snowmanx.contains("ChargedProjectiles", 9)) {
         ListTag _snowmanxx = _snowmanx.getList("ChargedProjectiles", 10);
         if (_snowmanxx != null) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
               CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
               _snowman.add(ItemStack.fromTag(_snowmanxxxx));
            }
         }
      }

      return _snowman;
   }

   private static void clearProjectiles(ItemStack crossbow) {
      CompoundTag _snowman = crossbow.getTag();
      if (_snowman != null) {
         ListTag _snowmanx = _snowman.getList("ChargedProjectiles", 9);
         _snowmanx.clear();
         _snowman.put("ChargedProjectiles", _snowmanx);
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
         boolean _snowman = projectile.getItem() == Items.FIREWORK_ROCKET;
         ProjectileEntity _snowmanx;
         if (_snowman) {
            _snowmanx = new FireworkRocketEntity(world, projectile, shooter, shooter.getX(), shooter.getEyeY() - 0.15F, shooter.getZ(), true);
         } else {
            _snowmanx = createArrow(world, shooter, crossbow, projectile);
            if (creative || simulated != 0.0F) {
               ((PersistentProjectileEntity)_snowmanx).pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
         }

         if (shooter instanceof CrossbowUser) {
            CrossbowUser _snowmanxx = (CrossbowUser)shooter;
            _snowmanxx.shoot(_snowmanxx.getTarget(), crossbow, _snowmanx, simulated);
         } else {
            Vec3d _snowmanxx = shooter.getOppositeRotationVector(1.0F);
            Quaternion _snowmanxxx = new Quaternion(new Vector3f(_snowmanxx), simulated, true);
            Vec3d _snowmanxxxx = shooter.getRotationVec(1.0F);
            Vector3f _snowmanxxxxx = new Vector3f(_snowmanxxxx);
            _snowmanxxxxx.rotate(_snowmanxxx);
            _snowmanx.setVelocity((double)_snowmanxxxxx.getX(), (double)_snowmanxxxxx.getY(), (double)_snowmanxxxxx.getZ(), speed, divergence);
         }

         crossbow.damage(_snowman ? 3 : 1, shooter, e -> e.sendToolBreakStatus(hand));
         world.spawnEntity(_snowmanx);
         world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, soundPitch);
      }
   }

   private static PersistentProjectileEntity createArrow(World world, LivingEntity entity, ItemStack crossbow, ItemStack arrow) {
      ArrowItem _snowman = (ArrowItem)(arrow.getItem() instanceof ArrowItem ? arrow.getItem() : Items.ARROW);
      PersistentProjectileEntity _snowmanx = _snowman.createArrow(world, arrow, entity);
      if (entity instanceof PlayerEntity) {
         _snowmanx.setCritical(true);
      }

      _snowmanx.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
      _snowmanx.setShotFromCrossbow(true);
      int _snowmanxx = EnchantmentHelper.getLevel(Enchantments.PIERCING, crossbow);
      if (_snowmanxx > 0) {
         _snowmanx.setPierceLevel((byte)_snowmanxx);
      }

      return _snowmanx;
   }

   public static void shootAll(World world, LivingEntity entity, Hand hand, ItemStack stack, float speed, float divergence) {
      List<ItemStack> _snowman = getProjectiles(stack);
      float[] _snowmanx = getSoundPitches(entity.getRandom());

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.get(_snowmanxx);
         boolean _snowmanxxxx = entity instanceof PlayerEntity && ((PlayerEntity)entity).abilities.creativeMode;
         if (!_snowmanxxx.isEmpty()) {
            if (_snowmanxx == 0) {
               shoot(world, entity, hand, stack, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, speed, divergence, 0.0F);
            } else if (_snowmanxx == 1) {
               shoot(world, entity, hand, stack, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, speed, divergence, -10.0F);
            } else if (_snowmanxx == 2) {
               shoot(world, entity, hand, stack, _snowmanxxx, _snowmanx[_snowmanxx], _snowmanxxxx, speed, divergence, 10.0F);
            }
         }
      }

      postShoot(world, entity, stack);
   }

   private static float[] getSoundPitches(Random random) {
      boolean _snowman = random.nextBoolean();
      return new float[]{1.0F, getSoundPitch(_snowman), getSoundPitch(!_snowman)};
   }

   private static float getSoundPitch(boolean flag) {
      float _snowman = flag ? 0.63F : 0.43F;
      return 1.0F / (RANDOM.nextFloat() * 0.5F + 1.8F) + _snowman;
   }

   private static void postShoot(World world, LivingEntity entity, ItemStack stack) {
      if (entity instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowman = (ServerPlayerEntity)entity;
         if (!world.isClient) {
            Criteria.SHOT_CROSSBOW.trigger(_snowman, stack);
         }

         _snowman.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
      }

      clearProjectiles(stack);
   }

   @Override
   public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
      if (!world.isClient) {
         int _snowman = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
         SoundEvent _snowmanx = this.getQuickChargeSound(_snowman);
         SoundEvent _snowmanxx = _snowman == 0 ? SoundEvents.ITEM_CROSSBOW_LOADING_MIDDLE : null;
         float _snowmanxxx = (float)(stack.getMaxUseTime() - remainingUseTicks) / (float)getPullTime(stack);
         if (_snowmanxxx < 0.2F) {
            this.charged = false;
            this.loaded = false;
         }

         if (_snowmanxxx >= 0.2F && !this.charged) {
            this.charged = true;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), _snowmanx, SoundCategory.PLAYERS, 0.5F, 1.0F);
         }

         if (_snowmanxxx >= 0.5F && _snowmanxx != null && !this.loaded) {
            this.loaded = true;
            world.playSound(null, user.getX(), user.getY(), user.getZ(), _snowmanxx, SoundCategory.PLAYERS, 0.5F, 1.0F);
         }
      }
   }

   @Override
   public int getMaxUseTime(ItemStack stack) {
      return getPullTime(stack) + 3;
   }

   public static int getPullTime(ItemStack stack) {
      int _snowman = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);
      return _snowman == 0 ? 25 : 25 - 5 * _snowman;
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
      float _snowman = (float)useTicks / (float)getPullTime(stack);
      if (_snowman > 1.0F) {
         _snowman = 1.0F;
      }

      return _snowman;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      List<ItemStack> _snowman = getProjectiles(stack);
      if (isCharged(stack) && !_snowman.isEmpty()) {
         ItemStack _snowmanx = _snowman.get(0);
         tooltip.add(new TranslatableText("item.minecraft.crossbow.projectile").append(" ").append(_snowmanx.toHoverableText()));
         if (context.isAdvanced() && _snowmanx.getItem() == Items.FIREWORK_ROCKET) {
            List<Text> _snowmanxx = Lists.newArrayList();
            Items.FIREWORK_ROCKET.appendTooltip(_snowmanx, world, _snowmanxx, context);
            if (!_snowmanxx.isEmpty()) {
               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
                  _snowmanxx.set(_snowmanxxx, new LiteralText("  ").append(_snowmanxx.get(_snowmanxxx)).formatted(Formatting.GRAY));
               }

               tooltip.addAll(_snowmanxx);
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
