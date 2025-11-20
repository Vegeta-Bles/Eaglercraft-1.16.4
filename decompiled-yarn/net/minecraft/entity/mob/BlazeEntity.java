package net.minecraft.entity.mob;

import java.util.EnumSet;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlazeEntity extends HostileEntity {
   private float eyeOffset = 0.5F;
   private int eyeOffsetCooldown;
   private static final TrackedData<Byte> BLAZE_FLAGS = DataTracker.registerData(BlazeEntity.class, TrackedDataHandlerRegistry.BYTE);

   public BlazeEntity(EntityType<? extends BlazeEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.setPathfindingPenalty(PathNodeType.WATER, -1.0F);
      this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
      this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
      this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
      this.experiencePoints = 10;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(4, new BlazeEntity.ShootFireballGoal(this));
      this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
      this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
      this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
      this.targetSelector.add(1, new RevengeGoal(this).setGroupRevenge());
      this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));
   }

   public static DefaultAttributeContainer.Builder createBlazeAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23F)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(BLAZE_FLAGS, (byte)0);
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_BLAZE_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_BLAZE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_BLAZE_DEATH;
   }

   @Override
   public float getBrightnessAtEyes() {
      return 1.0F;
   }

   @Override
   public void tickMovement() {
      if (!this.onGround && this.getVelocity().y < 0.0) {
         this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
      }

      if (this.world.isClient) {
         if (this.random.nextInt(24) == 0 && !this.isSilent()) {
            this.world
               .playSound(
                  this.getX() + 0.5,
                  this.getY() + 0.5,
                  this.getZ() + 0.5,
                  SoundEvents.ENTITY_BLAZE_BURN,
                  this.getSoundCategory(),
                  1.0F + this.random.nextFloat(),
                  this.random.nextFloat() * 0.7F + 0.3F,
                  false
               );
         }

         for (int _snowman = 0; _snowman < 2; _snowman++) {
            this.world.addParticle(ParticleTypes.LARGE_SMOKE, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), 0.0, 0.0, 0.0);
         }
      }

      super.tickMovement();
   }

   @Override
   public boolean hurtByWater() {
      return true;
   }

   @Override
   protected void mobTick() {
      this.eyeOffsetCooldown--;
      if (this.eyeOffsetCooldown <= 0) {
         this.eyeOffsetCooldown = 100;
         this.eyeOffset = 0.5F + (float)this.random.nextGaussian() * 3.0F;
      }

      LivingEntity _snowman = this.getTarget();
      if (_snowman != null && _snowman.getEyeY() > this.getEyeY() + (double)this.eyeOffset && this.canTarget(_snowman)) {
         Vec3d _snowmanx = this.getVelocity();
         this.setVelocity(this.getVelocity().add(0.0, (0.3F - _snowmanx.y) * 0.3F, 0.0));
         this.velocityDirty = true;
      }

      super.mobTick();
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      return false;
   }

   @Override
   public boolean isOnFire() {
      return this.isFireActive();
   }

   private boolean isFireActive() {
      return (this.dataTracker.get(BLAZE_FLAGS) & 1) != 0;
   }

   private void setFireActive(boolean fireActive) {
      byte _snowman = this.dataTracker.get(BLAZE_FLAGS);
      if (fireActive) {
         _snowman = (byte)(_snowman | 1);
      } else {
         _snowman = (byte)(_snowman & -2);
      }

      this.dataTracker.set(BLAZE_FLAGS, _snowman);
   }

   static class ShootFireballGoal extends Goal {
      private final BlazeEntity blaze;
      private int fireballsFired;
      private int fireballCooldown;
      private int targetNotVisibleTicks;

      public ShootFireballGoal(BlazeEntity blaze) {
         this.blaze = blaze;
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         LivingEntity _snowman = this.blaze.getTarget();
         return _snowman != null && _snowman.isAlive() && this.blaze.canTarget(_snowman);
      }

      @Override
      public void start() {
         this.fireballsFired = 0;
      }

      @Override
      public void stop() {
         this.blaze.setFireActive(false);
         this.targetNotVisibleTicks = 0;
      }

      @Override
      public void tick() {
         this.fireballCooldown--;
         LivingEntity _snowman = this.blaze.getTarget();
         if (_snowman != null) {
            boolean _snowmanx = this.blaze.getVisibilityCache().canSee(_snowman);
            if (_snowmanx) {
               this.targetNotVisibleTicks = 0;
            } else {
               this.targetNotVisibleTicks++;
            }

            double _snowmanxx = this.blaze.squaredDistanceTo(_snowman);
            if (_snowmanxx < 4.0) {
               if (!_snowmanx) {
                  return;
               }

               if (this.fireballCooldown <= 0) {
                  this.fireballCooldown = 20;
                  this.blaze.tryAttack(_snowman);
               }

               this.blaze.getMoveControl().moveTo(_snowman.getX(), _snowman.getY(), _snowman.getZ(), 1.0);
            } else if (_snowmanxx < this.getFollowRange() * this.getFollowRange() && _snowmanx) {
               double _snowmanxxx = _snowman.getX() - this.blaze.getX();
               double _snowmanxxxx = _snowman.getBodyY(0.5) - this.blaze.getBodyY(0.5);
               double _snowmanxxxxx = _snowman.getZ() - this.blaze.getZ();
               if (this.fireballCooldown <= 0) {
                  this.fireballsFired++;
                  if (this.fireballsFired == 1) {
                     this.fireballCooldown = 60;
                     this.blaze.setFireActive(true);
                  } else if (this.fireballsFired <= 4) {
                     this.fireballCooldown = 6;
                  } else {
                     this.fireballCooldown = 100;
                     this.fireballsFired = 0;
                     this.blaze.setFireActive(false);
                  }

                  if (this.fireballsFired > 1) {
                     float _snowmanxxxxxx = MathHelper.sqrt(MathHelper.sqrt(_snowmanxx)) * 0.5F;
                     if (!this.blaze.isSilent()) {
                        this.blaze.world.syncWorldEvent(null, 1018, this.blaze.getBlockPos(), 0);
                     }

                     for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 1; _snowmanxxxxxxx++) {
                        SmallFireballEntity _snowmanxxxxxxxx = new SmallFireballEntity(
                           this.blaze.world,
                           this.blaze,
                           _snowmanxxx + this.blaze.getRandom().nextGaussian() * (double)_snowmanxxxxxx,
                           _snowmanxxxx,
                           _snowmanxxxxx + this.blaze.getRandom().nextGaussian() * (double)_snowmanxxxxxx
                        );
                        _snowmanxxxxxxxx.updatePosition(_snowmanxxxxxxxx.getX(), this.blaze.getBodyY(0.5) + 0.5, _snowmanxxxxxxxx.getZ());
                        this.blaze.world.spawnEntity(_snowmanxxxxxxxx);
                     }
                  }
               }

               this.blaze.getLookControl().lookAt(_snowman, 10.0F, 10.0F);
            } else if (this.targetNotVisibleTicks < 5) {
               this.blaze.getMoveControl().moveTo(_snowman.getX(), _snowman.getY(), _snowman.getZ(), 1.0);
            }

            super.tick();
         }
      }

      private double getFollowRange() {
         return this.blaze.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
      }
   }
}
