package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class SquidEntity extends WaterCreatureEntity {
   public float tiltAngle;
   public float prevTiltAngle;
   public float rollAngle;
   public float prevRollAngle;
   public float thrustTimer;
   public float prevThrustTimer;
   public float tentacleAngle;
   public float prevTentacleAngle;
   private float swimVelocityScale;
   private float thrustTimerSpeed;
   private float turningSpeed;
   private float swimX;
   private float swimY;
   private float swimZ;

   public SquidEntity(EntityType<? extends SquidEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.random.setSeed((long)this.getEntityId());
      this.thrustTimerSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
   }

   @Override
   protected void initGoals() {
      this.goalSelector.add(0, new SquidEntity.SwimGoal(this));
      this.goalSelector.add(1, new SquidEntity.EscapeAttackerGoal());
   }

   public static DefaultAttributeContainer.Builder createSquidAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0);
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.5F;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SQUID_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_SQUID_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SQUID_DEATH;
   }

   @Override
   protected float getSoundVolume() {
      return 0.4F;
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   public void tickMovement() {
      super.tickMovement();
      this.prevTiltAngle = this.tiltAngle;
      this.prevRollAngle = this.rollAngle;
      this.prevThrustTimer = this.thrustTimer;
      this.prevTentacleAngle = this.tentacleAngle;
      this.thrustTimer = this.thrustTimer + this.thrustTimerSpeed;
      if ((double)this.thrustTimer > Math.PI * 2) {
         if (this.world.isClient) {
            this.thrustTimer = (float) (Math.PI * 2);
         } else {
            this.thrustTimer = (float)((double)this.thrustTimer - (Math.PI * 2));
            if (this.random.nextInt(10) == 0) {
               this.thrustTimerSpeed = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
            }

            this.world.sendEntityStatus(this, (byte)19);
         }
      }

      if (this.isInsideWaterOrBubbleColumn()) {
         if (this.thrustTimer < (float) Math.PI) {
            float _snowman = this.thrustTimer / (float) Math.PI;
            this.tentacleAngle = MathHelper.sin(_snowman * _snowman * (float) Math.PI) * (float) Math.PI * 0.25F;
            if ((double)_snowman > 0.75) {
               this.swimVelocityScale = 1.0F;
               this.turningSpeed = 1.0F;
            } else {
               this.turningSpeed *= 0.8F;
            }
         } else {
            this.tentacleAngle = 0.0F;
            this.swimVelocityScale *= 0.9F;
            this.turningSpeed *= 0.99F;
         }

         if (!this.world.isClient) {
            this.setVelocity(
               (double)(this.swimX * this.swimVelocityScale), (double)(this.swimY * this.swimVelocityScale), (double)(this.swimZ * this.swimVelocityScale)
            );
         }

         Vec3d _snowman = this.getVelocity();
         float _snowmanx = MathHelper.sqrt(squaredHorizontalLength(_snowman));
         this.bodyYaw = this.bodyYaw + (-((float)MathHelper.atan2(_snowman.x, _snowman.z)) * (180.0F / (float)Math.PI) - this.bodyYaw) * 0.1F;
         this.yaw = this.bodyYaw;
         this.rollAngle = (float)((double)this.rollAngle + Math.PI * (double)this.turningSpeed * 1.5);
         this.tiltAngle = this.tiltAngle + (-((float)MathHelper.atan2((double)_snowmanx, _snowman.y)) * (180.0F / (float)Math.PI) - this.tiltAngle) * 0.1F;
      } else {
         this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.thrustTimer)) * (float) Math.PI * 0.25F;
         if (!this.world.isClient) {
            double _snowman = this.getVelocity().y;
            if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
               _snowman = 0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1);
            } else if (!this.hasNoGravity()) {
               _snowman -= 0.08;
            }

            this.setVelocity(0.0, _snowman * 0.98F, 0.0);
         }

         this.tiltAngle = (float)((double)this.tiltAngle + (double)(-90.0F - this.tiltAngle) * 0.02);
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (super.damage(source, amount) && this.getAttacker() != null) {
         this.squirt();
         return true;
      } else {
         return false;
      }
   }

   private Vec3d applyBodyRotations(Vec3d shootVector) {
      Vec3d _snowman = shootVector.rotateX(this.prevTiltAngle * (float) (Math.PI / 180.0));
      return _snowman.rotateY(-this.prevBodyYaw * (float) (Math.PI / 180.0));
   }

   private void squirt() {
      this.playSound(SoundEvents.ENTITY_SQUID_SQUIRT, this.getSoundVolume(), this.getSoundPitch());
      Vec3d _snowman = this.applyBodyRotations(new Vec3d(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());

      for (int _snowmanx = 0; _snowmanx < 30; _snowmanx++) {
         Vec3d _snowmanxx = this.applyBodyRotations(new Vec3d((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
         Vec3d _snowmanxxx = _snowmanxx.multiply(0.3 + (double)(this.random.nextFloat() * 2.0F));
         ((ServerWorld)this.world).spawnParticles(ParticleTypes.SQUID_INK, _snowman.x, _snowman.y + 0.5, _snowman.z, 0, _snowmanxxx.x, _snowmanxxx.y, _snowmanxxx.z, 0.1F);
      }
   }

   @Override
   public void travel(Vec3d movementInput) {
      this.move(MovementType.SELF, this.getVelocity());
   }

   public static boolean canSpawn(EntityType<SquidEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return pos.getY() > 45 && pos.getY() < world.getSeaLevel();
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 19) {
         this.thrustTimer = 0.0F;
      } else {
         super.handleStatus(status);
      }
   }

   public void setSwimmingVector(float x, float y, float z) {
      this.swimX = x;
      this.swimY = y;
      this.swimZ = z;
   }

   public boolean hasSwimmingVector() {
      return this.swimX != 0.0F || this.swimY != 0.0F || this.swimZ != 0.0F;
   }

   class EscapeAttackerGoal extends Goal {
      private int timer;

      private EscapeAttackerGoal() {
      }

      @Override
      public boolean canStart() {
         LivingEntity _snowman = SquidEntity.this.getAttacker();
         return SquidEntity.this.isTouchingWater() && _snowman != null ? SquidEntity.this.squaredDistanceTo(_snowman) < 100.0 : false;
      }

      @Override
      public void start() {
         this.timer = 0;
      }

      @Override
      public void tick() {
         this.timer++;
         LivingEntity _snowman = SquidEntity.this.getAttacker();
         if (_snowman != null) {
            Vec3d _snowmanx = new Vec3d(SquidEntity.this.getX() - _snowman.getX(), SquidEntity.this.getY() - _snowman.getY(), SquidEntity.this.getZ() - _snowman.getZ());
            BlockState _snowmanxx = SquidEntity.this.world
               .getBlockState(new BlockPos(SquidEntity.this.getX() + _snowmanx.x, SquidEntity.this.getY() + _snowmanx.y, SquidEntity.this.getZ() + _snowmanx.z));
            FluidState _snowmanxxx = SquidEntity.this.world
               .getFluidState(new BlockPos(SquidEntity.this.getX() + _snowmanx.x, SquidEntity.this.getY() + _snowmanx.y, SquidEntity.this.getZ() + _snowmanx.z));
            if (_snowmanxxx.isIn(FluidTags.WATER) || _snowmanxx.isAir()) {
               double _snowmanxxxx = _snowmanx.length();
               if (_snowmanxxxx > 0.0) {
                  _snowmanx.normalize();
                  float _snowmanxxxxx = 3.0F;
                  if (_snowmanxxxx > 5.0) {
                     _snowmanxxxxx = (float)((double)_snowmanxxxxx - (_snowmanxxxx - 5.0) / 5.0);
                  }

                  if (_snowmanxxxxx > 0.0F) {
                     _snowmanx = _snowmanx.multiply((double)_snowmanxxxxx);
                  }
               }

               if (_snowmanxx.isAir()) {
                  _snowmanx = _snowmanx.subtract(0.0, _snowmanx.y, 0.0);
               }

               SquidEntity.this.setSwimmingVector((float)_snowmanx.x / 20.0F, (float)_snowmanx.y / 20.0F, (float)_snowmanx.z / 20.0F);
            }

            if (this.timer % 10 == 5) {
               SquidEntity.this.world
                  .addParticle(ParticleTypes.BUBBLE, SquidEntity.this.getX(), SquidEntity.this.getY(), SquidEntity.this.getZ(), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   class SwimGoal extends Goal {
      private final SquidEntity squid;

      public SwimGoal(SquidEntity squid) {
         this.squid = squid;
      }

      @Override
      public boolean canStart() {
         return true;
      }

      @Override
      public void tick() {
         int _snowman = this.squid.getDespawnCounter();
         if (_snowman > 100) {
            this.squid.setSwimmingVector(0.0F, 0.0F, 0.0F);
         } else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.touchingWater || !this.squid.hasSwimmingVector()) {
            float _snowmanx = this.squid.getRandom().nextFloat() * (float) (Math.PI * 2);
            float _snowmanxx = MathHelper.cos(_snowmanx) * 0.2F;
            float _snowmanxxx = -0.1F + this.squid.getRandom().nextFloat() * 0.2F;
            float _snowmanxxxx = MathHelper.sin(_snowmanx) * 0.2F;
            this.squid.setSwimmingVector(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }
   }
}
