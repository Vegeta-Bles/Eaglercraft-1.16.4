/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.mob;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public abstract class PatrolEntity
extends HostileEntity {
    private BlockPos patrolTarget;
    private boolean patrolLeader;
    private boolean patrolling;

    protected PatrolEntity(EntityType<? extends PatrolEntity> entityType, World world) {
        super((EntityType<? extends HostileEntity>)entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(4, new PatrolGoal<PatrolEntity>(this, 0.7, 0.595));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        if (this.patrolTarget != null) {
            tag.put("PatrolTarget", NbtHelper.fromBlockPos(this.patrolTarget));
        }
        tag.putBoolean("PatrolLeader", this.patrolLeader);
        tag.putBoolean("Patrolling", this.patrolling);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        if (tag.contains("PatrolTarget")) {
            this.patrolTarget = NbtHelper.toBlockPos(tag.getCompound("PatrolTarget"));
        }
        this.patrolLeader = tag.getBoolean("PatrolLeader");
        this.patrolling = tag.getBoolean("Patrolling");
    }

    @Override
    public double getHeightOffset() {
        return -0.45;
    }

    public boolean canLead() {
        return true;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag) {
        if (spawnReason != SpawnReason.PATROL && spawnReason != SpawnReason.EVENT && spawnReason != SpawnReason.STRUCTURE && this.random.nextFloat() < 0.06f && this.canLead()) {
            this.patrolLeader = true;
        }
        if (this.isPatrolLeader()) {
            this.equipStack(EquipmentSlot.HEAD, Raid.getOminousBanner());
            this.setEquipmentDropChance(EquipmentSlot.HEAD, 2.0f);
        }
        if (spawnReason == SpawnReason.PATROL) {
            this.patrolling = true;
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
    }

    public static boolean canSpawn(EntityType<? extends PatrolEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 8) {
            return false;
        }
        return PatrolEntity.canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.patrolling || distanceSquared > 16384.0;
    }

    public void setPatrolTarget(BlockPos targetPos) {
        this.patrolTarget = targetPos;
        this.patrolling = true;
    }

    public BlockPos getPatrolTarget() {
        return this.patrolTarget;
    }

    public boolean hasPatrolTarget() {
        return this.patrolTarget != null;
    }

    public void setPatrolLeader(boolean patrolLeader) {
        this.patrolLeader = patrolLeader;
        this.patrolling = true;
    }

    public boolean isPatrolLeader() {
        return this.patrolLeader;
    }

    public boolean hasNoRaid() {
        return true;
    }

    public void setRandomPatrolTarget() {
        this.patrolTarget = this.getBlockPos().add(-500 + this.random.nextInt(1000), 0, -500 + this.random.nextInt(1000));
        this.patrolling = true;
    }

    protected boolean isRaidCenterSet() {
        return this.patrolling;
    }

    protected void setPatrolling(boolean patrolling) {
        this.patrolling = patrolling;
    }

    public static class PatrolGoal<T extends PatrolEntity>
    extends Goal {
        private final T entity;
        private final double leaderSpeed;
        private final double followSpeed;
        private long nextPatrolSearchTime;

        public PatrolGoal(T entity, double leaderSpeed, double followSpeed) {
            this.entity = entity;
            this.leaderSpeed = leaderSpeed;
            this.followSpeed = followSpeed;
            this.nextPatrolSearchTime = -1L;
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            boolean bl = ((PatrolEntity)this.entity).world.getTime() < this.nextPatrolSearchTime;
            return ((PatrolEntity)this.entity).isRaidCenterSet() && ((MobEntity)this.entity).getTarget() == null && !((Entity)this.entity).hasPassengers() && ((PatrolEntity)this.entity).hasPatrolTarget() && !bl;
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }

        @Override
        public void tick() {
            boolean bl = ((PatrolEntity)this.entity).isPatrolLeader();
            EntityNavigation _snowman2 = ((MobEntity)this.entity).getNavigation();
            if (_snowman2.isIdle()) {
                List<PatrolEntity> list = this.findPatrolTargets();
                if (((PatrolEntity)this.entity).isRaidCenterSet() && list.isEmpty()) {
                    ((PatrolEntity)this.entity).setPatrolling(false);
                } else if (!bl || !((PatrolEntity)this.entity).getPatrolTarget().isWithinDistance(((Entity)this.entity).getPos(), 10.0)) {
                    Vec3d vec3d = Vec3d.ofBottomCenter(((PatrolEntity)this.entity).getPatrolTarget());
                    _snowman = ((Entity)this.entity).getPos();
                    _snowman = _snowman.subtract(vec3d);
                    vec3d = _snowman.rotateY(90.0f).multiply(0.4).add(vec3d);
                    _snowman = vec3d.subtract(_snowman).normalize().multiply(10.0).add(_snowman);
                    BlockPos _snowman3 = new BlockPos(_snowman);
                    if (!_snowman2.startMovingTo((_snowman3 = ((PatrolEntity)this.entity).world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowman3)).getX(), _snowman3.getY(), _snowman3.getZ(), bl ? this.followSpeed : this.leaderSpeed)) {
                        this.wander();
                        this.nextPatrolSearchTime = ((PatrolEntity)this.entity).world.getTime() + 200L;
                    } else if (bl) {
                        for (PatrolEntity patrolEntity : list) {
                            patrolEntity.setPatrolTarget(_snowman3);
                        }
                    }
                } else {
                    ((PatrolEntity)this.entity).setRandomPatrolTarget();
                }
            }
        }

        private List<PatrolEntity> findPatrolTargets() {
            return ((PatrolEntity)this.entity).world.getEntitiesByClass(PatrolEntity.class, ((Entity)this.entity).getBoundingBox().expand(16.0), patrolEntity -> patrolEntity.hasNoRaid() && !patrolEntity.isPartOf((Entity)this.entity));
        }

        private boolean wander() {
            Random random = ((LivingEntity)this.entity).getRandom();
            BlockPos _snowman2 = ((PatrolEntity)this.entity).world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ((Entity)this.entity).getBlockPos().add(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
            return ((MobEntity)this.entity).getNavigation().startMovingTo(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ(), this.leaderSpeed);
        }
    }
}

