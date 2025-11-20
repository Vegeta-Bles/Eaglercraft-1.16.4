package net.minecraft.entity.projectile.thrown;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PotionEntity extends ThrownItemEntity implements FlyingItemEntity {
   public static final Predicate<LivingEntity> WATER_HURTS = LivingEntity::hurtByWater;

   public PotionEntity(EntityType<? extends PotionEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public PotionEntity(World world, LivingEntity owner) {
      super(EntityType.POTION, owner, world);
   }

   public PotionEntity(World world, double x, double y, double z) {
      super(EntityType.POTION, x, y, z, world);
   }

   @Override
   protected Item getDefaultItem() {
      return Items.SPLASH_POTION;
   }

   @Override
   protected float getGravity() {
      return 0.05F;
   }

   @Override
   protected void onBlockHit(BlockHitResult blockHitResult) {
      super.onBlockHit(blockHitResult);
      if (!this.world.isClient) {
         ItemStack _snowman = this.getStack();
         Potion _snowmanx = PotionUtil.getPotion(_snowman);
         List<StatusEffectInstance> _snowmanxx = PotionUtil.getPotionEffects(_snowman);
         boolean _snowmanxxx = _snowmanx == Potions.WATER && _snowmanxx.isEmpty();
         Direction _snowmanxxxx = blockHitResult.getSide();
         BlockPos _snowmanxxxxx = blockHitResult.getBlockPos();
         BlockPos _snowmanxxxxxx = _snowmanxxxxx.offset(_snowmanxxxx);
         if (_snowmanxxx) {
            this.extinguishFire(_snowmanxxxxxx, _snowmanxxxx);
            this.extinguishFire(_snowmanxxxxxx.offset(_snowmanxxxx.getOpposite()), _snowmanxxxx);

            for (Direction _snowmanxxxxxxx : Direction.Type.HORIZONTAL) {
               this.extinguishFire(_snowmanxxxxxx.offset(_snowmanxxxxxxx), _snowmanxxxxxxx);
            }
         }
      }
   }

   @Override
   protected void onCollision(HitResult hitResult) {
      super.onCollision(hitResult);
      if (!this.world.isClient) {
         ItemStack _snowman = this.getStack();
         Potion _snowmanx = PotionUtil.getPotion(_snowman);
         List<StatusEffectInstance> _snowmanxx = PotionUtil.getPotionEffects(_snowman);
         boolean _snowmanxxx = _snowmanx == Potions.WATER && _snowmanxx.isEmpty();
         if (_snowmanxxx) {
            this.damageEntitiesHurtByWater();
         } else if (!_snowmanxx.isEmpty()) {
            if (this.isLingering()) {
               this.applyLingeringPotion(_snowman, _snowmanx);
            } else {
               this.applySplashPotion(_snowmanxx, hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)hitResult).getEntity() : null);
            }
         }

         int _snowmanxxxx = _snowmanx.hasInstantEffect() ? 2007 : 2002;
         this.world.syncWorldEvent(_snowmanxxxx, this.getBlockPos(), PotionUtil.getColor(_snowman));
         this.remove();
      }
   }

   private void damageEntitiesHurtByWater() {
      Box _snowman = this.getBoundingBox().expand(4.0, 2.0, 4.0);
      List<LivingEntity> _snowmanx = this.world.getEntitiesByClass(LivingEntity.class, _snowman, WATER_HURTS);
      if (!_snowmanx.isEmpty()) {
         for (LivingEntity _snowmanxx : _snowmanx) {
            double _snowmanxxx = this.squaredDistanceTo(_snowmanxx);
            if (_snowmanxxx < 16.0 && _snowmanxx.hurtByWater()) {
               _snowmanxx.damage(DamageSource.magic(_snowmanxx, this.getOwner()), 1.0F);
            }
         }
      }
   }

   private void applySplashPotion(List<StatusEffectInstance> statusEffects, @Nullable Entity entity) {
      Box _snowman = this.getBoundingBox().expand(4.0, 2.0, 4.0);
      List<LivingEntity> _snowmanx = this.world.getNonSpectatingEntities(LivingEntity.class, _snowman);
      if (!_snowmanx.isEmpty()) {
         for (LivingEntity _snowmanxx : _snowmanx) {
            if (_snowmanxx.isAffectedBySplashPotions()) {
               double _snowmanxxx = this.squaredDistanceTo(_snowmanxx);
               if (_snowmanxxx < 16.0) {
                  double _snowmanxxxx = 1.0 - Math.sqrt(_snowmanxxx) / 4.0;
                  if (_snowmanxx == entity) {
                     _snowmanxxxx = 1.0;
                  }

                  for (StatusEffectInstance _snowmanxxxxx : statusEffects) {
                     StatusEffect _snowmanxxxxxx = _snowmanxxxxx.getEffectType();
                     if (_snowmanxxxxxx.isInstant()) {
                        _snowmanxxxxxx.applyInstantEffect(this, this.getOwner(), _snowmanxx, _snowmanxxxxx.getAmplifier(), _snowmanxxxx);
                     } else {
                        int _snowmanxxxxxxx = (int)(_snowmanxxxx * (double)_snowmanxxxxx.getDuration() + 0.5);
                        if (_snowmanxxxxxxx > 20) {
                           _snowmanxx.addStatusEffect(
                              new StatusEffectInstance(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxx.getAmplifier(), _snowmanxxxxx.isAmbient(), _snowmanxxxxx.shouldShowParticles())
                           );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void applyLingeringPotion(ItemStack stack, Potion potion) {
      AreaEffectCloudEntity _snowman = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
      Entity _snowmanx = this.getOwner();
      if (_snowmanx instanceof LivingEntity) {
         _snowman.setOwner((LivingEntity)_snowmanx);
      }

      _snowman.setRadius(3.0F);
      _snowman.setRadiusOnUse(-0.5F);
      _snowman.setWaitTime(10);
      _snowman.setRadiusGrowth(-_snowman.getRadius() / (float)_snowman.getDuration());
      _snowman.setPotion(potion);

      for (StatusEffectInstance _snowmanxx : PotionUtil.getCustomPotionEffects(stack)) {
         _snowman.addEffect(new StatusEffectInstance(_snowmanxx));
      }

      CompoundTag _snowmanxx = stack.getTag();
      if (_snowmanxx != null && _snowmanxx.contains("CustomPotionColor", 99)) {
         _snowman.setColor(_snowmanxx.getInt("CustomPotionColor"));
      }

      this.world.spawnEntity(_snowman);
   }

   private boolean isLingering() {
      return this.getStack().getItem() == Items.LINGERING_POTION;
   }

   private void extinguishFire(BlockPos pos, Direction direction) {
      BlockState _snowman = this.world.getBlockState(pos);
      if (_snowman.isIn(BlockTags.FIRE)) {
         this.world.removeBlock(pos, false);
      } else if (CampfireBlock.isLitCampfire(_snowman)) {
         this.world.syncWorldEvent(null, 1009, pos, 0);
         CampfireBlock.extinguish(this.world, pos, _snowman);
         this.world.setBlockState(pos, _snowman.with(CampfireBlock.LIT, Boolean.valueOf(false)));
      }
   }
}
