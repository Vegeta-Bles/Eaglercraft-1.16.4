package net.minecraft.entity.mob;

import java.util.List;
import java.util.UUID;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.DisableableFollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.ai.goal.RaidGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WitchEntity extends RaiderEntity implements RangedAttackMob {
   private static final UUID DRINKING_SPEED_PENALTY_MODIFIER_ID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final EntityAttributeModifier DRINKING_SPEED_PENALTY_MODIFIER = new EntityAttributeModifier(
      DRINKING_SPEED_PENALTY_MODIFIER_ID, "Drinking speed penalty", -0.25, EntityAttributeModifier.Operation.ADDITION
   );
   private static final TrackedData<Boolean> DRINKING = DataTracker.registerData(WitchEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private int drinkTimeLeft;
   private RaidGoal<RaiderEntity> raidGoal;
   private DisableableFollowTargetGoal<PlayerEntity> attackPlayerGoal;

   public WitchEntity(EntityType<? extends WitchEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      super.initGoals();
      this.raidGoal = new RaidGoal<>(this, RaiderEntity.class, true, _snowman -> _snowman != null && this.hasActiveRaid() && _snowman.getType() != EntityType.WITCH);
      this.attackPlayerGoal = new DisableableFollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, null);
      this.goalSelector.add(1, new SwimGoal(this));
      this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0, 60, 10.0F));
      this.goalSelector.add(2, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(3, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(3, new LookAroundGoal(this));
      this.targetSelector.add(1, new RevengeGoal(this, RaiderEntity.class));
      this.targetSelector.add(2, this.raidGoal);
      this.targetSelector.add(3, this.attackPlayerGoal);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.getDataTracker().startTracking(DRINKING, false);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_WITCH_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_WITCH_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WITCH_DEATH;
   }

   public void setDrinking(boolean drinking) {
      this.getDataTracker().set(DRINKING, drinking);
   }

   public boolean isDrinking() {
      return this.getDataTracker().get(DRINKING);
   }

   public static DefaultAttributeContainer.Builder createWitchAttributes() {
      return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 26.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
   }

   @Override
   public void tickMovement() {
      if (!this.world.isClient && this.isAlive()) {
         this.raidGoal.decreaseCooldown();
         if (this.raidGoal.getCooldown() <= 0) {
            this.attackPlayerGoal.setEnabled(true);
         } else {
            this.attackPlayerGoal.setEnabled(false);
         }

         if (this.isDrinking()) {
            if (this.drinkTimeLeft-- <= 0) {
               this.setDrinking(false);
               ItemStack _snowman = this.getMainHandStack();
               this.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
               if (_snowman.getItem() == Items.POTION) {
                  List<StatusEffectInstance> _snowmanx = PotionUtil.getPotionEffects(_snowman);
                  if (_snowmanx != null) {
                     for (StatusEffectInstance _snowmanxx : _snowmanx) {
                        this.addStatusEffect(new StatusEffectInstance(_snowmanxx));
                     }
                  }
               }

               this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).removeModifier(DRINKING_SPEED_PENALTY_MODIFIER);
            }
         } else {
            Potion _snowman = null;
            if (this.random.nextFloat() < 0.15F && this.isSubmergedIn(FluidTags.WATER) && !this.hasStatusEffect(StatusEffects.WATER_BREATHING)) {
               _snowman = Potions.WATER_BREATHING;
            } else if (this.random.nextFloat() < 0.15F
               && (this.isOnFire() || this.getRecentDamageSource() != null && this.getRecentDamageSource().isFire())
               && !this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
               _snowman = Potions.FIRE_RESISTANCE;
            } else if (this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
               _snowman = Potions.HEALING;
            } else if (this.random.nextFloat() < 0.5F
               && this.getTarget() != null
               && !this.hasStatusEffect(StatusEffects.SPEED)
               && this.getTarget().squaredDistanceTo(this) > 121.0) {
               _snowman = Potions.SWIFTNESS;
            }

            if (_snowman != null) {
               this.equipStack(EquipmentSlot.MAINHAND, PotionUtil.setPotion(new ItemStack(Items.POTION), _snowman));
               this.drinkTimeLeft = this.getMainHandStack().getMaxUseTime();
               this.setDrinking(true);
               if (!this.isSilent()) {
                  this.world
                     .playSound(
                        null,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        SoundEvents.ENTITY_WITCH_DRINK,
                        this.getSoundCategory(),
                        1.0F,
                        0.8F + this.random.nextFloat() * 0.4F
                     );
               }

               EntityAttributeInstance _snowmanx = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
               _snowmanx.removeModifier(DRINKING_SPEED_PENALTY_MODIFIER);
               _snowmanx.addTemporaryModifier(DRINKING_SPEED_PENALTY_MODIFIER);
            }
         }

         if (this.random.nextFloat() < 7.5E-4F) {
            this.world.sendEntityStatus(this, (byte)15);
         }
      }

      super.tickMovement();
   }

   @Override
   public SoundEvent getCelebratingSound() {
      return SoundEvents.ENTITY_WITCH_CELEBRATE;
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 15) {
         for (int _snowman = 0; _snowman < this.random.nextInt(35) + 10; _snowman++) {
            this.world
               .addParticle(
                  ParticleTypes.WITCH,
                  this.getX() + this.random.nextGaussian() * 0.13F,
                  this.getBoundingBox().maxY + 0.5 + this.random.nextGaussian() * 0.13F,
                  this.getZ() + this.random.nextGaussian() * 0.13F,
                  0.0,
                  0.0,
                  0.0
               );
         }
      } else {
         super.handleStatus(status);
      }
   }

   @Override
   protected float applyEnchantmentsToDamage(DamageSource source, float amount) {
      amount = super.applyEnchantmentsToDamage(source, amount);
      if (source.getAttacker() == this) {
         amount = 0.0F;
      }

      if (source.getMagic()) {
         amount = (float)((double)amount * 0.15);
      }

      return amount;
   }

   @Override
   public void attack(LivingEntity target, float pullProgress) {
      if (!this.isDrinking()) {
         Vec3d _snowman = target.getVelocity();
         double _snowmanx = target.getX() + _snowman.x - this.getX();
         double _snowmanxx = target.getEyeY() - 1.1F - this.getY();
         double _snowmanxxx = target.getZ() + _snowman.z - this.getZ();
         float _snowmanxxxx = MathHelper.sqrt(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
         Potion _snowmanxxxxx = Potions.HARMING;
         if (target instanceof RaiderEntity) {
            if (target.getHealth() <= 4.0F) {
               _snowmanxxxxx = Potions.HEALING;
            } else {
               _snowmanxxxxx = Potions.REGENERATION;
            }

            this.setTarget(null);
         } else if (_snowmanxxxx >= 8.0F && !target.hasStatusEffect(StatusEffects.SLOWNESS)) {
            _snowmanxxxxx = Potions.SLOWNESS;
         } else if (target.getHealth() >= 8.0F && !target.hasStatusEffect(StatusEffects.POISON)) {
            _snowmanxxxxx = Potions.POISON;
         } else if (_snowmanxxxx <= 3.0F && !target.hasStatusEffect(StatusEffects.WEAKNESS) && this.random.nextFloat() < 0.25F) {
            _snowmanxxxxx = Potions.WEAKNESS;
         }

         PotionEntity _snowmanxxxxxx = new PotionEntity(this.world, this);
         _snowmanxxxxxx.setItem(PotionUtil.setPotion(new ItemStack(Items.SPLASH_POTION), _snowmanxxxxx));
         _snowmanxxxxxx.pitch -= -20.0F;
         _snowmanxxxxxx.setVelocity(_snowmanx, _snowmanxx + (double)(_snowmanxxxx * 0.2F), _snowmanxxx, 0.75F, 8.0F);
         if (!this.isSilent()) {
            this.world
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  SoundEvents.ENTITY_WITCH_THROW,
                  this.getSoundCategory(),
                  1.0F,
                  0.8F + this.random.nextFloat() * 0.4F
               );
         }

         this.world.spawnEntity(_snowmanxxxxxx);
      }
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 1.62F;
   }

   @Override
   public void addBonusForWave(int wave, boolean unused) {
   }

   @Override
   public boolean canLead() {
      return false;
   }
}
