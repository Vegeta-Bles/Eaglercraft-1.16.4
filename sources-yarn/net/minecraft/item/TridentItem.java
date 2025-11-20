package net.minecraft.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TridentItem extends Item implements Vanishable {
   private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

   public TridentItem(Item.Settings arg) {
      super(arg);
      Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
      builder.put(
         EntityAttributes.GENERIC_ATTACK_DAMAGE,
         new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0, EntityAttributeModifier.Operation.ADDITION)
      );
      builder.put(
         EntityAttributes.GENERIC_ATTACK_SPEED,
         new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9F, EntityAttributeModifier.Operation.ADDITION)
      );
      this.attributeModifiers = builder.build();
   }

   @Override
   public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
      return !miner.isCreative();
   }

   @Override
   public UseAction getUseAction(ItemStack stack) {
      return UseAction.SPEAR;
   }

   @Override
   public int getMaxUseTime(ItemStack stack) {
      return 72000;
   }

   @Override
   public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
      if (user instanceof PlayerEntity) {
         PlayerEntity lv = (PlayerEntity)user;
         int j = this.getMaxUseTime(stack) - remainingUseTicks;
         if (j >= 10) {
            int k = EnchantmentHelper.getRiptide(stack);
            if (k <= 0 || lv.isTouchingWaterOrRain()) {
               if (!world.isClient) {
                  stack.damage(1, lv, p -> p.sendToolBreakStatus(user.getActiveHand()));
                  if (k == 0) {
                     TridentEntity lv2 = new TridentEntity(world, lv, stack);
                     lv2.setProperties(lv, lv.pitch, lv.yaw, 0.0F, 2.5F + (float)k * 0.5F, 1.0F);
                     if (lv.abilities.creativeMode) {
                        lv2.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                     }

                     world.spawnEntity(lv2);
                     world.playSoundFromEntity(null, lv2, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                     if (!lv.abilities.creativeMode) {
                        lv.inventory.removeOne(stack);
                     }
                  }
               }

               lv.incrementStat(Stats.USED.getOrCreateStat(this));
               if (k > 0) {
                  float f = lv.yaw;
                  float g = lv.pitch;
                  float h = -MathHelper.sin(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
                  float l = -MathHelper.sin(g * (float) (Math.PI / 180.0));
                  float m = MathHelper.cos(f * (float) (Math.PI / 180.0)) * MathHelper.cos(g * (float) (Math.PI / 180.0));
                  float n = MathHelper.sqrt(h * h + l * l + m * m);
                  float o = 3.0F * ((1.0F + (float)k) / 4.0F);
                  h *= o / n;
                  l *= o / n;
                  m *= o / n;
                  lv.addVelocity((double)h, (double)l, (double)m);
                  lv.setRiptideTicks(20);
                  if (lv.isOnGround()) {
                     float p = 1.1999999F;
                     lv.move(MovementType.SELF, new Vec3d(0.0, 1.1999999F, 0.0));
                  }

                  SoundEvent lv3;
                  if (k >= 3) {
                     lv3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                  } else if (k == 2) {
                     lv3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                  } else {
                     lv3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                  }

                  world.playSoundFromEntity(null, lv, lv3, SoundCategory.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack lv = user.getStackInHand(hand);
      if (lv.getDamage() >= lv.getMaxDamage() - 1) {
         return TypedActionResult.fail(lv);
      } else if (EnchantmentHelper.getRiptide(lv) > 0 && !user.isTouchingWaterOrRain()) {
         return TypedActionResult.fail(lv);
      } else {
         user.setCurrentHand(hand);
         return TypedActionResult.consume(lv);
      }
   }

   @Override
   public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
      return true;
   }

   @Override
   public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
      if ((double)state.getHardness(world, pos) != 0.0) {
         stack.damage(2, miner, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
      }

      return true;
   }

   @Override
   public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
      return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
   }

   @Override
   public int getEnchantability() {
      return 1;
   }
}
