/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WanderAroundTask
extends Task<MobEntity> {
    private int pathUpdateCountdownTicks;
    @Nullable
    private Path path;
    @Nullable
    private BlockPos lookTargetPos;
    private float speed;

    public WanderAroundTask() {
        this(150, 250);
    }

    public WanderAroundTask(int n, int n2) {
        super((Map<MemoryModuleType<?>, MemoryModuleState>)ImmutableMap.of(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, (Object)((Object)MemoryModuleState.REGISTERED), MemoryModuleType.PATH, (Object)((Object)MemoryModuleState.VALUE_ABSENT), MemoryModuleType.WALK_TARGET, (Object)((Object)MemoryModuleState.VALUE_PRESENT)), n, n2);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
        if (this.pathUpdateCountdownTicks > 0) {
            --this.pathUpdateCountdownTicks;
            return false;
        }
        Brain<?> brain = mobEntity.getBrain();
        WalkTarget _snowman2 = brain.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
        boolean _snowman3 = this.hasReached(mobEntity, _snowman2);
        if (!_snowman3 && this.hasFinishedPath(mobEntity, _snowman2, serverWorld.getTime())) {
            this.lookTargetPos = _snowman2.getLookTarget().getBlockPos();
            return true;
        }
        brain.forget(MemoryModuleType.WALK_TARGET);
        if (_snowman3) {
            brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        }
        return false;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (this.path == null || this.lookTargetPos == null) {
            return false;
        }
        Optional<WalkTarget> optional = mobEntity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);
        EntityNavigation _snowman2 = mobEntity.getNavigation();
        return !_snowman2.isIdle() && optional.isPresent() && !this.hasReached(mobEntity, optional.get());
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        if (mobEntity.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET) && !this.hasReached(mobEntity, mobEntity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).get()) && mobEntity.getNavigation().isNearPathStartPos()) {
            this.pathUpdateCountdownTicks = serverWorld.getRandom().nextInt(40);
        }
        mobEntity.getNavigation().stop();
        mobEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        mobEntity.getBrain().forget(MemoryModuleType.PATH);
        this.path = null;
    }

    @Override
    protected void run(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        mobEntity.getBrain().remember(MemoryModuleType.PATH, this.path);
        mobEntity.getNavigation().startMovingAlong(this.path, this.speed);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, MobEntity mobEntity, long l) {
        Path path = mobEntity.getNavigation().getCurrentPath();
        Brain<?> _snowman2 = mobEntity.getBrain();
        if (this.path != path) {
            this.path = path;
            _snowman2.remember(MemoryModuleType.PATH, path);
        }
        if (path == null || this.lookTargetPos == null) {
            return;
        }
        WalkTarget _snowman3 = _snowman2.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
        if (_snowman3.getLookTarget().getBlockPos().getSquaredDistance(this.lookTargetPos) > 4.0 && this.hasFinishedPath(mobEntity, _snowman3, serverWorld.getTime())) {
            this.lookTargetPos = _snowman3.getLookTarget().getBlockPos();
            this.run(serverWorld, mobEntity, l);
        }
    }

    private boolean hasFinishedPath(MobEntity mobEntity, WalkTarget walkTarget, long time) {
        BlockPos blockPos = walkTarget.getLookTarget().getBlockPos();
        this.path = mobEntity.getNavigation().findPathTo(blockPos, 0);
        this.speed = walkTarget.getSpeed();
        Brain<Long> _snowman2 = mobEntity.getBrain();
        if (this.hasReached(mobEntity, walkTarget)) {
            _snowman2.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        } else {
            boolean bl = _snowman = this.path != null && this.path.reachesTarget();
            if (_snowman) {
                _snowman2.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            } else if (!_snowman2.hasMemoryModule(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
                _snowman2.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
            }
            if (this.path != null) {
                return true;
            }
            Vec3d vec3d = TargetFinder.findTargetTowards((PathAwareEntity)mobEntity, 10, 7, Vec3d.ofBottomCenter(blockPos));
            if (vec3d != null) {
                this.path = mobEntity.getNavigation().findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                return this.path != null;
            }
        }
        return false;
    }

    private boolean hasReached(MobEntity entity, WalkTarget walkTarget) {
        return walkTarget.getLookTarget().getBlockPos().getManhattanDistance(entity.getBlockPos()) <= walkTarget.getCompletionRange();
    }

    @Override
    protected /* synthetic */ boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        return this.shouldKeepRunning(world, (MobEntity)entity, time);
    }

    @Override
    protected /* synthetic */ void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        this.finishRunning(world, (MobEntity)entity, time);
    }

    @Override
    protected /* synthetic */ void run(ServerWorld world, LivingEntity entity, long time) {
        this.run(world, (MobEntity)entity, time);
    }
}

