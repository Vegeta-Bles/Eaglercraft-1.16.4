/*
 * Decompiled with CFR 0.152.
 */
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

public class SquidEntity
extends WaterCreatureEntity {
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

    public SquidEntity(EntityType<? extends SquidEntity> entityType, World world) {
        super((EntityType<? extends WaterCreatureEntity>)entityType, world);
        this.random.setSeed(this.getEntityId());
        this.thrustTimerSpeed = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeAttackerGoal());
    }

    public static DefaultAttributeContainer.Builder createSquidAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.5f;
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
        return 0.4f;
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
        this.thrustTimer += this.thrustTimerSpeed;
        if ((double)this.thrustTimer > Math.PI * 2) {
            if (this.world.isClient) {
                this.thrustTimer = (float)Math.PI * 2;
            } else {
                this.thrustTimer = (float)((double)this.thrustTimer - Math.PI * 2);
                if (this.random.nextInt(10) == 0) {
                    this.thrustTimerSpeed = 1.0f / (this.random.nextFloat() + 1.0f) * 0.2f;
                }
                this.world.sendEntityStatus(this, (byte)19);
            }
        }
        if (this.isInsideWaterOrBubbleColumn()) {
            if (this.thrustTimer < (float)Math.PI) {
                float f = this.thrustTimer / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25f;
                if ((double)f > 0.75) {
                    this.swimVelocityScale = 1.0f;
                    this.turningSpeed = 1.0f;
                } else {
                    this.turningSpeed *= 0.8f;
                }
            } else {
                this.tentacleAngle = 0.0f;
                this.swimVelocityScale *= 0.9f;
                this.turningSpeed *= 0.99f;
            }
            if (!this.world.isClient) {
                this.setVelocity(this.swimX * this.swimVelocityScale, this.swimY * this.swimVelocityScale, this.swimZ * this.swimVelocityScale);
            }
            Vec3d vec3d = this.getVelocity();
            float _snowman2 = MathHelper.sqrt(SquidEntity.squaredHorizontalLength(vec3d));
            this.bodyYaw += (-((float)MathHelper.atan2(vec3d.x, vec3d.z)) * 57.295776f - this.bodyYaw) * 0.1f;
            this.yaw = this.bodyYaw;
            this.rollAngle = (float)((double)this.rollAngle + Math.PI * (double)this.turningSpeed * 1.5);
            this.tiltAngle += (-((float)MathHelper.atan2(_snowman2, vec3d.y)) * 57.295776f - this.tiltAngle) * 0.1f;
        } else {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.thrustTimer)) * (float)Math.PI * 0.25f;
            if (!this.world.isClient) {
                double d = this.getVelocity().y;
                if (this.hasStatusEffect(StatusEffects.LEVITATION)) {
                    d = 0.05 * (double)(this.getStatusEffect(StatusEffects.LEVITATION).getAmplifier() + 1);
                } else if (!this.hasNoGravity()) {
                    d -= 0.08;
                }
                this.setVelocity(0.0, d * (double)0.98f, 0.0);
            }
            this.tiltAngle = (float)((double)this.tiltAngle + (double)(-90.0f - this.tiltAngle) * 0.02);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (super.damage(source, amount) && this.getAttacker() != null) {
            this.squirt();
            return true;
        }
        return false;
    }

    private Vec3d applyBodyRotations(Vec3d shootVector) {
        Vec3d vec3d = shootVector.rotateX(this.prevTiltAngle * ((float)Math.PI / 180));
        vec3d = vec3d.rotateY(-this.prevBodyYaw * ((float)Math.PI / 180));
        return vec3d;
    }

    private void squirt() {
        this.playSound(SoundEvents.ENTITY_SQUID_SQUIRT, this.getSoundVolume(), this.getSoundPitch());
        Vec3d vec3d = this.applyBodyRotations(new Vec3d(0.0, -1.0, 0.0)).add(this.getX(), this.getY(), this.getZ());
        for (int i = 0; i < 30; ++i) {
            Vec3d vec3d2 = this.applyBodyRotations(new Vec3d((double)this.random.nextFloat() * 0.6 - 0.3, -1.0, (double)this.random.nextFloat() * 0.6 - 0.3));
            _snowman = vec3d2.multiply(0.3 + (double)(this.random.nextFloat() * 2.0f));
            ((ServerWorld)this.world).spawnParticles(ParticleTypes.SQUID_INK, vec3d.x, vec3d.y + 0.5, vec3d.z, 0, _snowman.x, _snowman.y, _snowman.z, 0.1f);
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
            this.thrustTimer = 0.0f;
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
        return this.swimX != 0.0f || this.swimY != 0.0f || this.swimZ != 0.0f;
    }

    class EscapeAttackerGoal
    extends Goal {
        private int timer;

        private EscapeAttackerGoal() {
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = SquidEntity.this.getAttacker();
            if (SquidEntity.this.isTouchingWater() && livingEntity != null) {
                return SquidEntity.this.squaredDistanceTo(livingEntity) < 100.0;
            }
            return false;
        }

        @Override
        public void start() {
            this.timer = 0;
        }

        @Override
        public void tick() {
            ++this.timer;
            LivingEntity livingEntity = SquidEntity.this.getAttacker();
            if (livingEntity == null) {
                return;
            }
            Vec3d _snowman2 = new Vec3d(SquidEntity.this.getX() - livingEntity.getX(), SquidEntity.this.getY() - livingEntity.getY(), SquidEntity.this.getZ() - livingEntity.getZ());
            BlockState _snowman3 = SquidEntity.this.world.getBlockState(new BlockPos(SquidEntity.this.getX() + _snowman2.x, SquidEntity.this.getY() + _snowman2.y, SquidEntity.this.getZ() + _snowman2.z));
            FluidState _snowman4 = SquidEntity.this.world.getFluidState(new BlockPos(SquidEntity.this.getX() + _snowman2.x, SquidEntity.this.getY() + _snowman2.y, SquidEntity.this.getZ() + _snowman2.z));
            if (_snowman4.isIn(FluidTags.WATER) || _snowman3.isAir()) {
                double d = _snowman2.length();
                if (d > 0.0) {
                    _snowman2.normalize();
                    float f = 3.0f;
                    if (d > 5.0) {
                        f = (float)((double)f - (d - 5.0) / 5.0);
                    }
                    if (f > 0.0f) {
                        _snowman2 = _snowman2.multiply(f);
                    }
                }
                if (_snowman3.isAir()) {
                    _snowman2 = _snowman2.subtract(0.0, _snowman2.y, 0.0);
                }
                SquidEntity.this.setSwimmingVector((float)_snowman2.x / 20.0f, (float)_snowman2.y / 20.0f, (float)_snowman2.z / 20.0f);
            }
            if (this.timer % 10 == 5) {
                SquidEntity.this.world.addParticle(ParticleTypes.BUBBLE, SquidEntity.this.getX(), SquidEntity.this.getY(), SquidEntity.this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    class SwimGoal
    extends Goal {
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
            int n = this.squid.getDespawnCounter();
            if (n > 100) {
                this.squid.setSwimmingVector(0.0f, 0.0f, 0.0f);
            } else if (this.squid.getRandom().nextInt(50) == 0 || !this.squid.touchingWater || !this.squid.hasSwimmingVector()) {
                float f = this.squid.getRandom().nextFloat() * ((float)Math.PI * 2);
                _snowman = MathHelper.cos(f) * 0.2f;
                _snowman = -0.1f + this.squid.getRandom().nextFloat() * 0.2f;
                _snowman = MathHelper.sin(f) * 0.2f;
                this.squid.setSwimmingVector(_snowman, _snowman, _snowman);
            }
        }
    }
}

