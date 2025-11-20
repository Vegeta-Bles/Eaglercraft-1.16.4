/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.passive;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BatEntity
extends AmbientEntity {
    private static final TrackedData<Byte> BAT_FLAGS = DataTracker.registerData(BatEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(4.0).includeTeammates();
    private BlockPos hangingPosition;

    public BatEntity(EntityType<? extends BatEntity> entityType, World world) {
        super((EntityType<? extends AmbientEntity>)entityType, world);
        this.setRoosting(true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(BAT_FLAGS, (byte)0);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95f;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        if (this.isRoosting() && this.random.nextInt(4) != 0) {
            return null;
        }
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
    }

    public static DefaultAttributeContainer.Builder createBatAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0);
    }

    public boolean isRoosting() {
        return (this.dataTracker.get(BAT_FLAGS) & 1) != 0;
    }

    public void setRoosting(boolean roosting) {
        byte by = this.dataTracker.get(BAT_FLAGS);
        if (roosting) {
            this.dataTracker.set(BAT_FLAGS, (byte)(by | 1));
        } else {
            this.dataTracker.set(BAT_FLAGS, (byte)(by & 0xFFFFFFFE));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isRoosting()) {
            this.setVelocity(Vec3d.ZERO);
            this.setPos(this.getX(), (double)MathHelper.floor(this.getY()) + 1.0 - (double)this.getHeight(), this.getZ());
        } else {
            this.setVelocity(this.getVelocity().multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        BlockPos blockPos = this.getBlockPos();
        _snowman = blockPos.up();
        if (this.isRoosting()) {
            boolean bl = this.isSilent();
            if (this.world.getBlockState(_snowman).isSolidBlock(this.world, blockPos)) {
                if (this.random.nextInt(200) == 0) {
                    this.headYaw = this.random.nextInt(360);
                }
                if (this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, this) != null) {
                    this.setRoosting(false);
                    if (!bl) {
                        this.world.syncWorldEvent(null, 1025, blockPos, 0);
                    }
                }
            } else {
                this.setRoosting(false);
                if (!bl) {
                    this.world.syncWorldEvent(null, 1025, blockPos, 0);
                }
            }
        } else {
            if (!(this.hangingPosition == null || this.world.isAir(this.hangingPosition) && this.hangingPosition.getY() >= 1)) {
                this.hangingPosition = null;
            }
            if (this.hangingPosition == null || this.random.nextInt(30) == 0 || this.hangingPosition.isWithinDistance(this.getPos(), 2.0)) {
                this.hangingPosition = new BlockPos(this.getX() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7), this.getY() + (double)this.random.nextInt(6) - 2.0, this.getZ() + (double)this.random.nextInt(7) - (double)this.random.nextInt(7));
            }
            double d = (double)this.hangingPosition.getX() + 0.5 - this.getX();
            _snowman = (double)this.hangingPosition.getY() + 0.1 - this.getY();
            _snowman = (double)this.hangingPosition.getZ() + 0.5 - this.getZ();
            Vec3d _snowman2 = this.getVelocity();
            Vec3d _snowman3 = _snowman2.add((Math.signum(d) * 0.5 - _snowman2.x) * (double)0.1f, (Math.signum(_snowman) * (double)0.7f - _snowman2.y) * (double)0.1f, (Math.signum(_snowman) * 0.5 - _snowman2.z) * (double)0.1f);
            this.setVelocity(_snowman3);
            float _snowman4 = (float)(MathHelper.atan2(_snowman3.z, _snowman3.x) * 57.2957763671875) - 90.0f;
            float _snowman5 = MathHelper.wrapDegrees(_snowman4 - this.yaw);
            this.forwardSpeed = 0.5f;
            this.yaw += _snowman5;
            if (this.random.nextInt(100) == 0 && this.world.getBlockState(_snowman).isSolidBlock(this.world, _snowman)) {
                this.setRoosting(true);
            }
        }
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!this.world.isClient && this.isRoosting()) {
            this.setRoosting(false);
        }
        return super.damage(source, amount);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.set(BAT_FLAGS, tag.getByte("BatFlags"));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte("BatFlags", this.dataTracker.get(BAT_FLAGS));
    }

    public static boolean canSpawn(EntityType<BatEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (pos.getY() >= world.getSeaLevel()) {
            return false;
        }
        int n = world.getLightLevel(pos);
        _snowman = 4;
        if (BatEntity.isTodayAroundHalloween()) {
            _snowman = 7;
        } else if (random.nextBoolean()) {
            return false;
        }
        if (n > random.nextInt(_snowman)) {
            return false;
        }
        return BatEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }

    private static boolean isTodayAroundHalloween() {
        LocalDate localDate = LocalDate.now();
        int _snowman2 = localDate.get(ChronoField.DAY_OF_MONTH);
        int _snowman3 = localDate.get(ChronoField.MONTH_OF_YEAR);
        return _snowman3 == 10 && _snowman2 >= 20 || _snowman3 == 11 && _snowman2 <= 3;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0f;
    }
}

