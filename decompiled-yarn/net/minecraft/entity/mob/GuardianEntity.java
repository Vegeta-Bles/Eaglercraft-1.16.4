package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.GoToWalkTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class GuardianEntity extends HostileEntity {
   private static final TrackedData<Boolean> SPIKES_RETRACTED = DataTracker.registerData(GuardianEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<Integer> BEAM_TARGET_ID = DataTracker.registerData(GuardianEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private float spikesExtension;
   private float prevSpikesExtension;
   private float spikesExtensionRate;
   private float tailAngle;
   private float prevTailAngle;
   private LivingEntity cachedBeamTarget;
   private int beamTicks;
   private boolean flopping;
   protected WanderAroundGoal wanderGoal;

   public GuardianEntity(EntityType<? extends GuardianEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.experiencePoints = 10;
      this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
      this.moveControl = new GuardianEntity.GuardianMoveControl(this);
      this.spikesExtension = this.random.nextFloat();
      this.prevSpikesExtension = this.spikesExtension;
   }

   @Override
   protected void initGoals() {
      GoToWalkTargetGoal _snowman = new GoToWalkTargetGoal(this, 1.0);
      this.wanderGoal = new WanderAroundGoal(this, 1.0, 80);
      this.goalSelector.add(4, new GuardianEntity.FireBeamGoal(this));
      this.goalSelector.add(5, _snowman);
      this.goalSelector.add(7, this.wanderGoal);
      this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
      this.goalSelector.add(8, new LookAtEntityGoal(this, GuardianEntity.class, 12.0F, 0.01F));
      this.goalSelector.add(9, new LookAroundGoal(this));
      this.wanderGoal.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      _snowman.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      this.targetSelector.add(1, new FollowTargetGoal<>(this, LivingEntity.class, 10, true, false, new GuardianEntity.GuardianTargetPredicate(this)));
   }

   public static DefaultAttributeContainer.Builder createGuardianAttributes() {
      return HostileEntity.createHostileAttributes()
         .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
         .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
         .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
         .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0);
   }

   @Override
   protected EntityNavigation createNavigation(World world) {
      return new SwimNavigation(this, world);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(SPIKES_RETRACTED, false);
      this.dataTracker.startTracking(BEAM_TARGET_ID, 0);
   }

   @Override
   public boolean canBreatheInWater() {
      return true;
   }

   @Override
   public EntityGroup getGroup() {
      return EntityGroup.AQUATIC;
   }

   public boolean areSpikesRetracted() {
      return this.dataTracker.get(SPIKES_RETRACTED);
   }

   private void setSpikesRetracted(boolean retracted) {
      this.dataTracker.set(SPIKES_RETRACTED, retracted);
   }

   public int getWarmupTime() {
      return 80;
   }

   private void setBeamTarget(int entityId) {
      this.dataTracker.set(BEAM_TARGET_ID, entityId);
   }

   public boolean hasBeamTarget() {
      return this.dataTracker.get(BEAM_TARGET_ID) != 0;
   }

   @Nullable
   public LivingEntity getBeamTarget() {
      if (!this.hasBeamTarget()) {
         return null;
      } else if (this.world.isClient) {
         if (this.cachedBeamTarget != null) {
            return this.cachedBeamTarget;
         } else {
            Entity _snowman = this.world.getEntityById(this.dataTracker.get(BEAM_TARGET_ID));
            if (_snowman instanceof LivingEntity) {
               this.cachedBeamTarget = (LivingEntity)_snowman;
               return this.cachedBeamTarget;
            } else {
               return null;
            }
         }
      } else {
         return this.getTarget();
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      super.onTrackedDataSet(data);
      if (BEAM_TARGET_ID.equals(data)) {
         this.beamTicks = 0;
         this.cachedBeamTarget = null;
      }
   }

   @Override
   public int getMinAmbientSoundDelay() {
      return 160;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return this.isInsideWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isInsideWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return this.isInsideWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND;
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return dimensions.height * 0.5F;
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return world.getFluidState(pos).isIn(FluidTags.WATER) ? 10.0F + world.getBrightness(pos) - 0.5F : super.getPathfindingFavor(pos, world);
   }

   @Override
   public void tickMovement() {
      if (this.isAlive()) {
         if (this.world.isClient) {
            this.prevSpikesExtension = this.spikesExtension;
            if (!this.isTouchingWater()) {
               this.spikesExtensionRate = 2.0F;
               Vec3d _snowman = this.getVelocity();
               if (_snowman.y > 0.0 && this.flopping && !this.isSilent()) {
                  this.world.playSound(this.getX(), this.getY(), this.getZ(), this.getFlopSound(), this.getSoundCategory(), 1.0F, 1.0F, false);
               }

               this.flopping = _snowman.y < 0.0 && this.world.isTopSolid(this.getBlockPos().down(), this);
            } else if (this.areSpikesRetracted()) {
               if (this.spikesExtensionRate < 0.5F) {
                  this.spikesExtensionRate = 4.0F;
               } else {
                  this.spikesExtensionRate = this.spikesExtensionRate + (0.5F - this.spikesExtensionRate) * 0.1F;
               }
            } else {
               this.spikesExtensionRate = this.spikesExtensionRate + (0.125F - this.spikesExtensionRate) * 0.2F;
            }

            this.spikesExtension = this.spikesExtension + this.spikesExtensionRate;
            this.prevTailAngle = this.tailAngle;
            if (!this.isInsideWaterOrBubbleColumn()) {
               this.tailAngle = this.random.nextFloat();
            } else if (this.areSpikesRetracted()) {
               this.tailAngle = this.tailAngle + (0.0F - this.tailAngle) * 0.25F;
            } else {
               this.tailAngle = this.tailAngle + (1.0F - this.tailAngle) * 0.06F;
            }

            if (this.areSpikesRetracted() && this.isTouchingWater()) {
               Vec3d _snowman = this.getRotationVec(0.0F);

               for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
                  this.world
                     .addParticle(
                        ParticleTypes.BUBBLE,
                        this.getParticleX(0.5) - _snowman.x * 1.5,
                        this.getRandomBodyY() - _snowman.y * 1.5,
                        this.getParticleZ(0.5) - _snowman.z * 1.5,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }

            if (this.hasBeamTarget()) {
               if (this.beamTicks < this.getWarmupTime()) {
                  this.beamTicks++;
               }

               LivingEntity _snowman = this.getBeamTarget();
               if (_snowman != null) {
                  this.getLookControl().lookAt(_snowman, 90.0F, 90.0F);
                  this.getLookControl().tick();
                  double _snowmanx = (double)this.getBeamProgress(0.0F);
                  double _snowmanxx = _snowman.getX() - this.getX();
                  double _snowmanxxx = _snowman.getBodyY(0.5) - this.getEyeY();
                  double _snowmanxxxx = _snowman.getZ() - this.getZ();
                  double _snowmanxxxxx = Math.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx);
                  _snowmanxx /= _snowmanxxxxx;
                  _snowmanxxx /= _snowmanxxxxx;
                  _snowmanxxxx /= _snowmanxxxxx;
                  double _snowmanxxxxxx = this.random.nextDouble();

                  while (_snowmanxxxxxx < _snowmanxxxxx) {
                     _snowmanxxxxxx += 1.8 - _snowmanx + this.random.nextDouble() * (1.7 - _snowmanx);
                     this.world
                        .addParticle(
                           ParticleTypes.BUBBLE, this.getX() + _snowmanxx * _snowmanxxxxxx, this.getEyeY() + _snowmanxxx * _snowmanxxxxxx, this.getZ() + _snowmanxxxx * _snowmanxxxxxx, 0.0, 0.0, 0.0
                        );
                  }
               }
            }
         }

         if (this.isInsideWaterOrBubbleColumn()) {
            this.setAir(300);
         } else if (this.onGround) {
            this.setVelocity(
               this.getVelocity().add((double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5, (double)((this.random.nextFloat() * 2.0F - 1.0F) * 0.4F))
            );
            this.yaw = this.random.nextFloat() * 360.0F;
            this.onGround = false;
            this.velocityDirty = true;
         }

         if (this.hasBeamTarget()) {
            this.yaw = this.headYaw;
         }
      }

      super.tickMovement();
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.ENTITY_GUARDIAN_FLOP;
   }

   public float getSpikesExtension(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.prevSpikesExtension, this.spikesExtension);
   }

   public float getTailAngle(float tickDelta) {
      return MathHelper.lerp(tickDelta, this.prevTailAngle, this.tailAngle);
   }

   public float getBeamProgress(float tickDelta) {
      return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
   }

   @Override
   public boolean canSpawn(WorldView world) {
      return world.intersectsEntities(this);
   }

   public static boolean canSpawn(EntityType<? extends GuardianEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return (random.nextInt(20) == 0 || !world.isSkyVisibleAllowingSea(pos))
         && world.getDifficulty() != Difficulty.PEACEFUL
         && (spawnReason == SpawnReason.SPAWNER || world.getFluidState(pos).isIn(FluidTags.WATER));
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (!this.areSpikesRetracted() && !source.getMagic() && source.getSource() instanceof LivingEntity) {
         LivingEntity _snowman = (LivingEntity)source.getSource();
         if (!source.isExplosive()) {
            _snowman.damage(DamageSource.thorns(this), 2.0F);
         }
      }

      if (this.wanderGoal != null) {
         this.wanderGoal.ignoreChanceOnce();
      }

      return super.damage(source, amount);
   }

   @Override
   public int getLookPitchSpeed() {
      return 180;
   }

   @Override
   public void travel(Vec3d movementInput) {
      if (this.canMoveVoluntarily() && this.isTouchingWater()) {
         this.updateVelocity(0.1F, movementInput);
         this.move(MovementType.SELF, this.getVelocity());
         this.setVelocity(this.getVelocity().multiply(0.9));
         if (!this.areSpikesRetracted() && this.getTarget() == null) {
            this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(movementInput);
      }
   }

   static class FireBeamGoal extends Goal {
      private final GuardianEntity guardian;
      private int beamTicks;
      private final boolean elder;

      public FireBeamGoal(GuardianEntity guardian) {
         this.guardian = guardian;
         this.elder = guardian instanceof ElderGuardianEntity;
         this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      }

      @Override
      public boolean canStart() {
         LivingEntity _snowman = this.guardian.getTarget();
         return _snowman != null && _snowman.isAlive();
      }

      @Override
      public boolean shouldContinue() {
         return super.shouldContinue() && (this.elder || this.guardian.squaredDistanceTo(this.guardian.getTarget()) > 9.0);
      }

      @Override
      public void start() {
         this.beamTicks = -10;
         this.guardian.getNavigation().stop();
         this.guardian.getLookControl().lookAt(this.guardian.getTarget(), 90.0F, 90.0F);
         this.guardian.velocityDirty = true;
      }

      @Override
      public void stop() {
         this.guardian.setBeamTarget(0);
         this.guardian.setTarget(null);
         this.guardian.wanderGoal.ignoreChanceOnce();
      }

      @Override
      public void tick() {
         LivingEntity _snowman = this.guardian.getTarget();
         this.guardian.getNavigation().stop();
         this.guardian.getLookControl().lookAt(_snowman, 90.0F, 90.0F);
         if (!this.guardian.canSee(_snowman)) {
            this.guardian.setTarget(null);
         } else {
            this.beamTicks++;
            if (this.beamTicks == 0) {
               this.guardian.setBeamTarget(this.guardian.getTarget().getEntityId());
               if (!this.guardian.isSilent()) {
                  this.guardian.world.sendEntityStatus(this.guardian, (byte)21);
               }
            } else if (this.beamTicks >= this.guardian.getWarmupTime()) {
               float _snowmanx = 1.0F;
               if (this.guardian.world.getDifficulty() == Difficulty.HARD) {
                  _snowmanx += 2.0F;
               }

               if (this.elder) {
                  _snowmanx += 2.0F;
               }

               _snowman.damage(DamageSource.magic(this.guardian, this.guardian), _snowmanx);
               _snowman.damage(DamageSource.mob(this.guardian), (float)this.guardian.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
               this.guardian.setTarget(null);
            }

            super.tick();
         }
      }
   }

   static class GuardianMoveControl extends MoveControl {
      private final GuardianEntity guardian;

      public GuardianMoveControl(GuardianEntity guardian) {
         super(guardian);
         this.guardian = guardian;
      }

      @Override
      public void tick() {
         if (this.state == MoveControl.State.MOVE_TO && !this.guardian.getNavigation().isIdle()) {
            Vec3d _snowman = new Vec3d(this.targetX - this.guardian.getX(), this.targetY - this.guardian.getY(), this.targetZ - this.guardian.getZ());
            double _snowmanx = _snowman.length();
            double _snowmanxx = _snowman.x / _snowmanx;
            double _snowmanxxx = _snowman.y / _snowmanx;
            double _snowmanxxxx = _snowman.z / _snowmanx;
            float _snowmanxxxxx = (float)(MathHelper.atan2(_snowman.z, _snowman.x) * 180.0F / (float)Math.PI) - 90.0F;
            this.guardian.yaw = this.changeAngle(this.guardian.yaw, _snowmanxxxxx, 90.0F);
            this.guardian.bodyYaw = this.guardian.yaw;
            float _snowmanxxxxxx = (float)(this.speed * this.guardian.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            float _snowmanxxxxxxx = MathHelper.lerp(0.125F, this.guardian.getMovementSpeed(), _snowmanxxxxxx);
            this.guardian.setMovementSpeed(_snowmanxxxxxxx);
            double _snowmanxxxxxxxx = Math.sin((double)(this.guardian.age + this.guardian.getEntityId()) * 0.5) * 0.05;
            double _snowmanxxxxxxxxx = Math.cos((double)(this.guardian.yaw * (float) (Math.PI / 180.0)));
            double _snowmanxxxxxxxxxx = Math.sin((double)(this.guardian.yaw * (float) (Math.PI / 180.0)));
            double _snowmanxxxxxxxxxxx = Math.sin((double)(this.guardian.age + this.guardian.getEntityId()) * 0.75) * 0.05;
            this.guardian
               .setVelocity(
                  this.guardian
                     .getVelocity()
                     .add(_snowmanxxxxxxxx * _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx * (_snowmanxxxxxxxxxx + _snowmanxxxxxxxxx) * 0.25 + (double)_snowmanxxxxxxx * _snowmanxxx * 0.1, _snowmanxxxxxxxx * _snowmanxxxxxxxxxx)
               );
            LookControl _snowmanxxxxxxxxxxxx = this.guardian.getLookControl();
            double _snowmanxxxxxxxxxxxxx = this.guardian.getX() + _snowmanxx * 2.0;
            double _snowmanxxxxxxxxxxxxxx = this.guardian.getEyeY() + _snowmanxxx / _snowmanx;
            double _snowmanxxxxxxxxxxxxxxx = this.guardian.getZ() + _snowmanxxxx * 2.0;
            double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getLookX();
            double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getLookY();
            double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.getLookZ();
            if (!_snowmanxxxxxxxxxxxx.isActive()) {
               _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
            }

            this.guardian
               .getLookControl()
               .lookAt(
                  MathHelper.lerp(0.125, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx),
                  MathHelper.lerp(0.125, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx),
                  MathHelper.lerp(0.125, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx),
                  10.0F,
                  40.0F
               );
            this.guardian.setSpikesRetracted(true);
         } else {
            this.guardian.setMovementSpeed(0.0F);
            this.guardian.setSpikesRetracted(false);
         }
      }
   }

   static class GuardianTargetPredicate implements Predicate<LivingEntity> {
      private final GuardianEntity owner;

      public GuardianTargetPredicate(GuardianEntity owner) {
         this.owner = owner;
      }

      public boolean test(@Nullable LivingEntity _snowman) {
         return (_snowman instanceof PlayerEntity || _snowman instanceof SquidEntity) && _snowman.squaredDistanceTo(this.owner) > 9.0;
      }
   }
}
