/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
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
import net.minecraft.entity.mob.ElderGuardianEntity;
import net.minecraft.entity.mob.HostileEntity;
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

public class GuardianEntity
extends HostileEntity {
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

    public GuardianEntity(EntityType<? extends GuardianEntity> entityType, World world) {
        super((EntityType<? extends HostileEntity>)entityType, world);
        this.experiencePoints = 10;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.moveControl = new GuardianMoveControl(this);
        this.prevSpikesExtension = this.spikesExtension = this.random.nextFloat();
    }

    @Override
    protected void initGoals() {
        GoToWalkTargetGoal goToWalkTargetGoal = new GoToWalkTargetGoal(this, 1.0);
        this.wanderGoal = new WanderAroundGoal(this, 1.0, 80);
        this.goalSelector.add(4, new FireBeamGoal(this));
        this.goalSelector.add(5, goToWalkTargetGoal);
        this.goalSelector.add(7, this.wanderGoal);
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(8, new LookAtEntityGoal(this, GuardianEntity.class, 12.0f, 0.01f));
        this.goalSelector.add(9, new LookAroundGoal(this));
        this.wanderGoal.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        goToWalkTargetGoal.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        this.targetSelector.add(1, new FollowTargetGoal<LivingEntity>(this, LivingEntity.class, 10, true, false, new GuardianTargetPredicate(this)));
    }

    public static DefaultAttributeContainer.Builder createGuardianAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0).add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0);
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
        }
        if (this.world.isClient) {
            if (this.cachedBeamTarget != null) {
                return this.cachedBeamTarget;
            }
            Entity entity = this.world.getEntityById(this.dataTracker.get(BEAM_TARGET_ID));
            if (entity instanceof LivingEntity) {
                this.cachedBeamTarget = (LivingEntity)entity;
                return this.cachedBeamTarget;
            }
            return null;
        }
        return this.getTarget();
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
        return dimensions.height * 0.5f;
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return 10.0f + world.getBrightness(pos) - 0.5f;
        }
        return super.getPathfindingFavor(pos, world);
    }

    @Override
    public void tickMovement() {
        if (this.isAlive()) {
            if (this.world.isClient) {
                Object object;
                this.prevSpikesExtension = this.spikesExtension;
                if (!this.isTouchingWater()) {
                    this.spikesExtensionRate = 2.0f;
                    object = this.getVelocity();
                    if (((Vec3d)object).y > 0.0 && this.flopping && !this.isSilent()) {
                        this.world.playSound(this.getX(), this.getY(), this.getZ(), this.getFlopSound(), this.getSoundCategory(), 1.0f, 1.0f, false);
                    }
                    this.flopping = ((Vec3d)object).y < 0.0 && this.world.isTopSolid(this.getBlockPos().down(), this);
                } else {
                    this.spikesExtensionRate = this.areSpikesRetracted() ? (this.spikesExtensionRate < 0.5f ? 4.0f : (this.spikesExtensionRate += (0.5f - this.spikesExtensionRate) * 0.1f)) : (this.spikesExtensionRate += (0.125f - this.spikesExtensionRate) * 0.2f);
                }
                this.spikesExtension += this.spikesExtensionRate;
                this.prevTailAngle = this.tailAngle;
                this.tailAngle = !this.isInsideWaterOrBubbleColumn() ? this.random.nextFloat() : (this.areSpikesRetracted() ? (this.tailAngle += (0.0f - this.tailAngle) * 0.25f) : (this.tailAngle += (1.0f - this.tailAngle) * 0.06f));
                if (this.areSpikesRetracted() && this.isTouchingWater()) {
                    object = this.getRotationVec(0.0f);
                    for (int i = 0; i < 2; ++i) {
                        this.world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(0.5) - ((Vec3d)object).x * 1.5, this.getRandomBodyY() - ((Vec3d)object).y * 1.5, this.getParticleZ(0.5) - ((Vec3d)object).z * 1.5, 0.0, 0.0, 0.0);
                    }
                }
                if (this.hasBeamTarget()) {
                    if (this.beamTicks < this.getWarmupTime()) {
                        ++this.beamTicks;
                    }
                    if ((object = this.getBeamTarget()) != null) {
                        this.getLookControl().lookAt((Entity)object, 90.0f, 90.0f);
                        this.getLookControl().tick();
                        double d = this.getBeamProgress(0.0f);
                        _snowman = ((Entity)object).getX() - this.getX();
                        _snowman = ((Entity)object).getBodyY(0.5) - this.getEyeY();
                        _snowman = ((Entity)object).getZ() - this.getZ();
                        _snowman = Math.sqrt(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
                        _snowman /= _snowman;
                        _snowman /= _snowman;
                        _snowman /= _snowman;
                        _snowman = this.random.nextDouble();
                        while (_snowman < _snowman) {
                            this.world.addParticle(ParticleTypes.BUBBLE, this.getX() + _snowman * (_snowman += 1.8 - d + this.random.nextDouble() * (1.7 - d)), this.getEyeY() + _snowman * _snowman, this.getZ() + _snowman * _snowman, 0.0, 0.0, 0.0);
                        }
                    }
                }
            }
            if (this.isInsideWaterOrBubbleColumn()) {
                this.setAir(300);
            } else if (this.onGround) {
                this.setVelocity(this.getVelocity().add((this.random.nextFloat() * 2.0f - 1.0f) * 0.4f, 0.5, (this.random.nextFloat() * 2.0f - 1.0f) * 0.4f));
                this.yaw = this.random.nextFloat() * 360.0f;
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
        return !(random.nextInt(20) != 0 && world.isSkyVisibleAllowingSea(pos) || world.getDifficulty() == Difficulty.PEACEFUL || spawnReason != SpawnReason.SPAWNER && !world.getFluidState(pos).isIn(FluidTags.WATER));
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.areSpikesRetracted() && !source.getMagic() && source.getSource() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity)source.getSource();
            if (!source.isExplosive()) {
                livingEntity.damage(DamageSource.thorns(this), 2.0f);
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
            this.updateVelocity(0.1f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (!this.areSpikesRetracted() && this.getTarget() == null) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    static class GuardianMoveControl
    extends MoveControl {
        private final GuardianEntity guardian;

        public GuardianMoveControl(GuardianEntity guardian) {
            super(guardian);
            this.guardian = guardian;
        }

        @Override
        public void tick() {
            if (this.state != MoveControl.State.MOVE_TO || this.guardian.getNavigation().isIdle()) {
                this.guardian.setMovementSpeed(0.0f);
                this.guardian.setSpikesRetracted(false);
                return;
            }
            Vec3d vec3d = new Vec3d(this.targetX - this.guardian.getX(), this.targetY - this.guardian.getY(), this.targetZ - this.guardian.getZ());
            double _snowman2 = vec3d.length();
            double _snowman3 = vec3d.x / _snowman2;
            double _snowman4 = vec3d.y / _snowman2;
            double _snowman5 = vec3d.z / _snowman2;
            float _snowman6 = (float)(MathHelper.atan2(vec3d.z, vec3d.x) * 57.2957763671875) - 90.0f;
            this.guardian.bodyYaw = this.guardian.yaw = this.changeAngle(this.guardian.yaw, _snowman6, 90.0f);
            float _snowman7 = (float)(this.speed * this.guardian.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            float _snowman8 = MathHelper.lerp(0.125f, this.guardian.getMovementSpeed(), _snowman7);
            this.guardian.setMovementSpeed(_snowman8);
            double _snowman9 = Math.sin((double)(this.guardian.age + this.guardian.getEntityId()) * 0.5) * 0.05;
            double _snowman10 = Math.cos(this.guardian.yaw * ((float)Math.PI / 180));
            double _snowman11 = Math.sin(this.guardian.yaw * ((float)Math.PI / 180));
            double _snowman12 = Math.sin((double)(this.guardian.age + this.guardian.getEntityId()) * 0.75) * 0.05;
            this.guardian.setVelocity(this.guardian.getVelocity().add(_snowman9 * _snowman10, _snowman12 * (_snowman11 + _snowman10) * 0.25 + (double)_snowman8 * _snowman4 * 0.1, _snowman9 * _snowman11));
            LookControl _snowman13 = this.guardian.getLookControl();
            double _snowman14 = this.guardian.getX() + _snowman3 * 2.0;
            double _snowman15 = this.guardian.getEyeY() + _snowman4 / _snowman2;
            double _snowman16 = this.guardian.getZ() + _snowman5 * 2.0;
            double _snowman17 = _snowman13.getLookX();
            double _snowman18 = _snowman13.getLookY();
            double _snowman19 = _snowman13.getLookZ();
            if (!_snowman13.isActive()) {
                _snowman17 = _snowman14;
                _snowman18 = _snowman15;
                _snowman19 = _snowman16;
            }
            this.guardian.getLookControl().lookAt(MathHelper.lerp(0.125, _snowman17, _snowman14), MathHelper.lerp(0.125, _snowman18, _snowman15), MathHelper.lerp(0.125, _snowman19, _snowman16), 10.0f, 40.0f);
            this.guardian.setSpikesRetracted(true);
        }
    }

    static class FireBeamGoal
    extends Goal {
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
            LivingEntity livingEntity = this.guardian.getTarget();
            return livingEntity != null && livingEntity.isAlive();
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && (this.elder || this.guardian.squaredDistanceTo(this.guardian.getTarget()) > 9.0);
        }

        @Override
        public void start() {
            this.beamTicks = -10;
            this.guardian.getNavigation().stop();
            this.guardian.getLookControl().lookAt(this.guardian.getTarget(), 90.0f, 90.0f);
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
            LivingEntity livingEntity = this.guardian.getTarget();
            this.guardian.getNavigation().stop();
            this.guardian.getLookControl().lookAt(livingEntity, 90.0f, 90.0f);
            if (!this.guardian.canSee(livingEntity)) {
                this.guardian.setTarget(null);
                return;
            }
            ++this.beamTicks;
            if (this.beamTicks == 0) {
                this.guardian.setBeamTarget(this.guardian.getTarget().getEntityId());
                if (!this.guardian.isSilent()) {
                    this.guardian.world.sendEntityStatus(this.guardian, (byte)21);
                }
            } else if (this.beamTicks >= this.guardian.getWarmupTime()) {
                float f = 1.0f;
                if (this.guardian.world.getDifficulty() == Difficulty.HARD) {
                    f += 2.0f;
                }
                if (this.elder) {
                    f += 2.0f;
                }
                livingEntity.damage(DamageSource.magic(this.guardian, this.guardian), f);
                livingEntity.damage(DamageSource.mob(this.guardian), (float)this.guardian.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
                this.guardian.setTarget(null);
            }
            super.tick();
        }
    }

    static class GuardianTargetPredicate
    implements Predicate<LivingEntity> {
        private final GuardianEntity owner;

        public GuardianTargetPredicate(GuardianEntity owner) {
            this.owner = owner;
        }

        @Override
        public boolean test(@Nullable LivingEntity livingEntity) {
            return (livingEntity instanceof PlayerEntity || livingEntity instanceof SquidEntity) && livingEntity.squaredDistanceTo(this.owner) > 9.0;
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object context) {
            return this.test((LivingEntity)context);
        }
    }
}

