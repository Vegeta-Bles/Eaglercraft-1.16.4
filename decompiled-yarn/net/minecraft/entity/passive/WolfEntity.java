package net.minecraft.entity.passive;

import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.FollowTargetIfTamedGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.ai.goal.UniversalAngerGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WolfBegGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WolfEntity extends TameableEntity implements Angerable {
   private static final TrackedData<Boolean> BEGGING = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> COLLAR_COLOR = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
   public static final Predicate<LivingEntity> FOLLOW_TAMED_PREDICATE = _snowman -> {
      EntityType<?> _snowmanx = _snowman.getType();
      return _snowmanx == EntityType.SHEEP || _snowmanx == EntityType.RABBIT || _snowmanx == EntityType.FOX;
   };
   private float begAnimationProgress;
   private float lastBegAnimationProgress;
   private boolean furWet;
   private boolean canShakeWaterOff;
   private float shakeProgress;
   private float lastShakeProgress;
   private static final IntRange ANGER_TIME_RANGE = Durations.betweenSeconds(20, 39);
   private UUID targetUuid;

   public WolfEntity(EntityType<? extends WolfEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setTamed(false);
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(1, new SwimGoal(this));
      this.goalSelector.add(2, new SitGoal(this));
      this.goalSelector.add(3, new WolfEntity.AvoidLlamaGoal<>(this, LlamaEntity.class, 24.0F, 1.5, 1.5));
      this.goalSelector.add(4, new PounceAtTargetGoal(this, 0.4F));
      this.goalSelector.add(5, new MeleeAttackGoal(this, 1.0, true));
      this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F, false));
      this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
      this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(9, new WolfBegGoal(this, 8.0F));
      this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(10, new LookAroundGoal(this));
      this.targetSelector.add(1, new TrackOwnerAttackerGoal(this));
      this.targetSelector.add(2, new AttackWithOwnerGoal(this));
      this.targetSelector.add(3, new RevengeGoal(this).setGroupRevenge());
      this.targetSelector.add(4, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
      this.targetSelector.add(5, new FollowTargetIfTamedGoal<>(this, AnimalEntity.class, false, FOLLOW_TAMED_PREDICATE));
      this.targetSelector.add(6, new FollowTargetIfTamedGoal<>(this, TurtleEntity.class, false, TurtleEntity.BABY_TURTLE_ON_LAND_FILTER));
      this.targetSelector.add(7, new FollowTargetGoal<>(this, AbstractSkeletonEntity.class, false));
      this.targetSelector.add(8, new UniversalAngerGoal<>(this, true));
   }

   public static DefaultAttributeContainer.Builder createWolfAttributes() {
      return MobEntity.createMobAttributes()
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3F)
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0)
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(BEGGING, false);
      this.dataTracker.startTracking(COLLAR_COLOR, DyeColor.RED.getId());
      this.dataTracker.startTracking(ANGER_TIME, 0);
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putByte("CollarColor", (byte)this.getCollarColor().getId());
      this.angerToTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("CollarColor", 99)) {
         this.setCollarColor(DyeColor.byId(tag.getInt("CollarColor")));
      }

      this.angerFromTag((ServerWorld)this.world, tag);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      if (this.hasAngerTime()) {
         return SoundEvents.ENTITY_WOLF_GROWL;
      } else if (this.random.nextInt(3) == 0) {
         return this.isTamed() && this.getHealth() < 10.0F ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
      } else {
         return SoundEvents.ENTITY_WOLF_AMBIENT;
      }
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_WOLF_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_WOLF_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      if (!this.world.isClient && this.furWet && !this.canShakeWaterOff && !this.isNavigating() && this.onGround) {
         this.canShakeWaterOff = true;
         this.shakeProgress = 0.0F;
         this.lastShakeProgress = 0.0F;
         this.world.sendEntityStatus(this, (byte)8);
      }

      if (!this.world.isClient) {
         this.tickAngerLogic((ServerWorld)this.world, true);
      }
   }

   @Override
   public void tick() {
      super.tick();
      if (this.isAlive()) {
         this.lastBegAnimationProgress = this.begAnimationProgress;
         if (this.isBegging()) {
            this.begAnimationProgress = this.begAnimationProgress + (1.0F - this.begAnimationProgress) * 0.4F;
         } else {
            this.begAnimationProgress = this.begAnimationProgress + (0.0F - this.begAnimationProgress) * 0.4F;
         }

         if (this.isWet()) {
            this.furWet = true;
            if (this.canShakeWaterOff && !this.world.isClient) {
               this.world.sendEntityStatus(this, (byte)56);
               this.method_31167();
            }
         } else if ((this.furWet || this.canShakeWaterOff) && this.canShakeWaterOff) {
            if (this.shakeProgress == 0.0F) {
               this.playSound(SoundEvents.ENTITY_WOLF_SHAKE, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            }

            this.lastShakeProgress = this.shakeProgress;
            this.shakeProgress += 0.05F;
            if (this.lastShakeProgress >= 2.0F) {
               this.furWet = false;
               this.canShakeWaterOff = false;
               this.lastShakeProgress = 0.0F;
               this.shakeProgress = 0.0F;
            }

            if (this.shakeProgress > 0.4F) {
               float _snowman = (float)this.getY();
               int _snowmanx = (int)(MathHelper.sin((this.shakeProgress - 0.4F) * (float) Math.PI) * 7.0F);
               Vec3d _snowmanxx = this.getVelocity();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
                  float _snowmanxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                  float _snowmanxxxxx = (this.random.nextFloat() * 2.0F - 1.0F) * this.getWidth() * 0.5F;
                  this.world
                     .addParticle(ParticleTypes.SPLASH, this.getX() + (double)_snowmanxxxx, (double)(_snowman + 0.8F), this.getZ() + (double)_snowmanxxxxx, _snowmanxx.x, _snowmanxx.y, _snowmanxx.z);
               }
            }
         }
      }
   }

   private void method_31167() {
      this.canShakeWaterOff = false;
      this.shakeProgress = 0.0F;
      this.lastShakeProgress = 0.0F;
   }

   @Override
   public void onDeath(DamageSource source) {
      this.furWet = false;
      this.canShakeWaterOff = false;
      this.lastShakeProgress = 0.0F;
      this.shakeProgress = 0.0F;
      super.onDeath(source);
   }

   public boolean isFurWet() {
      return this.furWet;
   }

   public float getFurWetBrightnessMultiplier(float tickDelta) {
      return Math.min(0.5F + MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) / 2.0F * 0.5F, 1.0F);
   }

   public float getShakeAnimationProgress(float tickDelta, float _snowman) {
      float _snowmanx = (MathHelper.lerp(tickDelta, this.lastShakeProgress, this.shakeProgress) + _snowman) / 1.8F;
      if (_snowmanx < 0.0F) {
         _snowmanx = 0.0F;
      } else if (_snowmanx > 1.0F) {
         _snowmanx = 1.0F;
      }

      return MathHelper.sin(_snowmanx * (float) Math.PI) * MathHelper.sin(_snowmanx * (float) Math.PI * 11.0F) * 0.15F * (float) Math.PI;
   }

   public float getBegAnimationProgress(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.lastBegAnimationProgress, this.begAnimationProgress) * 0.15F * (float) Math.PI;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.8F;
   }

   @Override
   public int getLookPitchSpeed() {
      return this.isInSittingPose() ? 20 : super.getLookPitchSpeed();
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         Entity _snowman = source.getAttacker();
         this.setSitting(false);
         if (_snowman != null && !(_snowman instanceof PlayerEntity) && !(_snowman instanceof PersistentProjectileEntity)) {
            amount = (amount + 1.0F) / 2.0F;
         }

         return super.damage(source, amount);
      }
   }

   @Override
   public boolean tryAttack(Entity target) {
      boolean _snowman = target.damage(DamageSource.mob(this), (float)((int)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
      if (_snowman) {
         this.dealDamage(this, target);
      }

      return _snowman;
   }

   @Override
   public void setTamed(boolean tamed) {
      super.setTamed(tamed);
      if (tamed) {
         this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20.0);
         this.setHealth(20.0F);
      } else {
         this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0);
      }

      this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0);
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      Item _snowmanx = _snowman.getItem();
      if (this.world.isClient) {
         boolean _snowmanxx = this.isOwner(player) || this.isTamed() || _snowmanx == Items.BONE && !this.isTamed() && !this.hasAngerTime();
         return _snowmanxx ? ActionResult.CONSUME : ActionResult.PASS;
      } else {
         if (this.isTamed()) {
            if (this.isBreedingItem(_snowman) && this.getHealth() < this.getMaxHealth()) {
               if (!player.abilities.creativeMode) {
                  _snowman.decrement(1);
               }

               this.heal((float)_snowmanx.getFoodComponent().getHunger());
               return ActionResult.SUCCESS;
            }

            if (!(_snowmanx instanceof DyeItem)) {
               ActionResult _snowmanxx = super.interactMob(player, hand);
               if ((!_snowmanxx.isAccepted() || this.isBaby()) && this.isOwner(player)) {
                  this.setSitting(!this.isSitting());
                  this.jumping = false;
                  this.navigation.stop();
                  this.setTarget(null);
                  return ActionResult.SUCCESS;
               }

               return _snowmanxx;
            }

            DyeColor _snowmanxx = ((DyeItem)_snowmanx).getColor();
            if (_snowmanxx != this.getCollarColor()) {
               this.setCollarColor(_snowmanxx);
               if (!player.abilities.creativeMode) {
                  _snowman.decrement(1);
               }

               return ActionResult.SUCCESS;
            }
         } else if (_snowmanx == Items.BONE && !this.hasAngerTime()) {
            if (!player.abilities.creativeMode) {
               _snowman.decrement(1);
            }

            if (this.random.nextInt(3) == 0) {
               this.setOwner(player);
               this.navigation.stop();
               this.setTarget(null);
               this.setSitting(true);
               this.world.sendEntityStatus(this, (byte)7);
            } else {
               this.world.sendEntityStatus(this, (byte)6);
            }

            return ActionResult.SUCCESS;
         }

         return super.interactMob(player, hand);
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 8) {
         this.canShakeWaterOff = true;
         this.shakeProgress = 0.0F;
         this.lastShakeProgress = 0.0F;
      } else if (status == 56) {
         this.method_31167();
      } else {
         super.handleStatus(status);
      }
   }

   public float getTailAngle() {
      if (this.hasAngerTime()) {
         return 1.5393804F;
      } else {
         return this.isTamed() ? (0.55F - (this.getMaxHealth() - this.getHealth()) * 0.02F) * (float) Math.PI : (float) (Math.PI / 5);
      }
   }

   @Override
   public boolean isBreedingItem(ItemStack stack) {
      Item _snowman = stack.getItem();
      return _snowman.isFood() && _snowman.getFoodComponent().isMeat();
   }

   @Override
   public int getLimitPerChunk() {
      return 8;
   }

   @Override
   public int getAngerTime() {
      return this.dataTracker.get(ANGER_TIME);
   }

   @Override
   public void setAngerTime(int ticks) {
      this.dataTracker.set(ANGER_TIME, ticks);
   }

   @Override
   public void chooseRandomAngerTime() {
      this.setAngerTime(ANGER_TIME_RANGE.choose(this.random));
   }

   @Nullable
   @Override
   public UUID getAngryAt() {
      return this.targetUuid;
   }

   @Override
   public void setAngryAt(@Nullable UUID uuid) {
      this.targetUuid = uuid;
   }

   public DyeColor getCollarColor() {
      return DyeColor.byId(this.dataTracker.get(COLLAR_COLOR));
   }

   public void setCollarColor(DyeColor color) {
      this.dataTracker.set(COLLAR_COLOR, color.getId());
   }

   public WolfEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      WolfEntity _snowmanxx = EntityType.WOLF.create(_snowman);
      UUID _snowmanxxx = this.getOwnerUuid();
      if (_snowmanxxx != null) {
         _snowmanxx.setOwnerUuid(_snowmanxxx);
         _snowmanxx.setTamed(true);
      }

      return _snowmanxx;
   }

   public void setBegging(boolean begging) {
      this.dataTracker.set(BEGGING, begging);
   }

   @Override
   public boolean canBreedWith(AnimalEntity other) {
      if (other == this) {
         return false;
      } else if (!this.isTamed()) {
         return false;
      } else if (!(other instanceof WolfEntity)) {
         return false;
      } else {
         WolfEntity _snowman = (WolfEntity)other;
         if (!_snowman.isTamed()) {
            return false;
         } else {
            return _snowman.isInSittingPose() ? false : this.isInLove() && _snowman.isInLove();
         }
      }
   }

   public boolean isBegging() {
      return this.dataTracker.get(BEGGING);
   }

   @Override
   public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
      if (target instanceof CreeperEntity || target instanceof GhastEntity) {
         return false;
      } else if (target instanceof WolfEntity) {
         WolfEntity _snowman = (WolfEntity)target;
         return !_snowman.isTamed() || _snowman.getOwner() != owner;
      } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity)owner).shouldDamagePlayer((PlayerEntity)target)) {
         return false;
      } else {
         return target instanceof HorseBaseEntity && ((HorseBaseEntity)target).isTame()
            ? false
            : !(target instanceof TameableEntity) || !((TameableEntity)target).isTamed();
      }
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return !this.hasAngerTime() && super.canBeLeashedBy(player);
   }

   @Override
   public Vec3d method_29919() {
      return new Vec3d(0.0, (double)(0.6F * this.getStandingEyeHeight()), (double)(this.getWidth() * 0.4F));
   }

   class AvoidLlamaGoal<T extends LivingEntity> extends FleeEntityGoal<T> {
      private final WolfEntity wolf;

      public AvoidLlamaGoal(WolfEntity wolf, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
         super(wolf, fleeFromType, distance, slowSpeed, fastSpeed);
         this.wolf = wolf;
      }

      @Override
      public boolean canStart() {
         return super.canStart() && this.targetEntity instanceof LlamaEntity ? !this.wolf.isTamed() && this.isScaredOf((LlamaEntity)this.targetEntity) : false;
      }

      private boolean isScaredOf(LlamaEntity llama) {
         return llama.getStrength() >= WolfEntity.this.random.nextInt(5);
      }

      @Override
      public void start() {
         WolfEntity.this.setTarget(null);
         super.start();
      }

      @Override
      public void tick() {
         WolfEntity.this.setTarget(null);
         super.tick();
      }
   }
}
