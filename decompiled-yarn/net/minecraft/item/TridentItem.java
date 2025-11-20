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

   public TridentItem(Item.Settings _snowman) {
      super(_snowman);
      Builder<EntityAttribute, EntityAttributeModifier> _snowmanx = ImmutableMultimap.builder();
      _snowmanx.put(
         EntityAttributes.GENERIC_ATTACK_DAMAGE,
         new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0, EntityAttributeModifier.Operation.ADDITION)
      );
      _snowmanx.put(
         EntityAttributes.GENERIC_ATTACK_SPEED,
         new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9F, EntityAttributeModifier.Operation.ADDITION)
      );
      this.attributeModifiers = _snowmanx.build();
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
         PlayerEntity _snowman = (PlayerEntity)user;
         int _snowmanx = this.getMaxUseTime(stack) - remainingUseTicks;
         if (_snowmanx >= 10) {
            int _snowmanxx = EnchantmentHelper.getRiptide(stack);
            if (_snowmanxx <= 0 || _snowman.isTouchingWaterOrRain()) {
               if (!world.isClient) {
                  stack.damage(1, _snowman, p -> p.sendToolBreakStatus(user.getActiveHand()));
                  if (_snowmanxx == 0) {
                     TridentEntity _snowmanxxx = new TridentEntity(world, _snowman, stack);
                     _snowmanxxx.setProperties(_snowman, _snowman.pitch, _snowman.yaw, 0.0F, 2.5F + (float)_snowmanxx * 0.5F, 1.0F);
                     if (_snowman.abilities.creativeMode) {
                        _snowmanxxx.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                     }

                     world.spawnEntity(_snowmanxxx);
                     world.playSoundFromEntity(null, _snowmanxxx, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                     if (!_snowman.abilities.creativeMode) {
                        _snowman.inventory.removeOne(stack);
                     }
                  }
               }

               _snowman.incrementStat(Stats.USED.getOrCreateStat(this));
               if (_snowmanxx > 0) {
                  float _snowmanxxxx = _snowman.yaw;
                  float _snowmanxxxxx = _snowman.pitch;
                  float _snowmanxxxxxx = -MathHelper.sin(_snowmanxxxx * (float) (Math.PI / 180.0)) * MathHelper.cos(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxx = -MathHelper.sin(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxx = MathHelper.cos(_snowmanxxxx * (float) (Math.PI / 180.0)) * MathHelper.cos(_snowmanxxxxx * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxxx = MathHelper.sqrt(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx);
                  float _snowmanxxxxxxxxxx = 3.0F * ((1.0F + (float)_snowmanxx) / 4.0F);
                  _snowmanxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowmanxxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowmanxxxxxxxx *= _snowmanxxxxxxxxxx / _snowmanxxxxxxxxx;
                  _snowman.addVelocity((double)_snowmanxxxxxx, (double)_snowmanxxxxxxx, (double)_snowmanxxxxxxxx);
                  _snowman.setRiptideTicks(20);
                  if (_snowman.isOnGround()) {
                     float _snowmanxxxxxxxxxxx = 1.1999999F;
                     _snowman.move(MovementType.SELF, new Vec3d(0.0, 1.1999999F, 0.0));
                  }

                  SoundEvent _snowmanxxxxxxxxxxx;
                  if (_snowmanxx >= 3) {
                     _snowmanxxxxxxxxxxx = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                  } else if (_snowmanxx == 2) {
                     _snowmanxxxxxxxxxxx = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                  } else {
                     _snowmanxxxxxxxxxxx = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                  }

                  world.playSoundFromEntity(null, _snowman, _snowmanxxxxxxxxxxx, SoundCategory.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      if (_snowman.getDamage() >= _snowman.getMaxDamage() - 1) {
         return TypedActionResult.fail(_snowman);
      } else if (EnchantmentHelper.getRiptide(_snowman) > 0 && !user.isTouchingWaterOrRain()) {
         return TypedActionResult.fail(_snowman);
      } else {
         user.setCurrentHand(hand);
         return TypedActionResult.consume(_snowman);
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
