/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.block.DoorBlock;
import net.minecraft.class_5493;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class MoveThroughVillageGoal
extends Goal {
    protected final PathAwareEntity mob;
    private final double speed;
    private Path targetPath;
    private BlockPos target;
    private final boolean requiresNighttime;
    private final List<BlockPos> visitedTargets = Lists.newArrayList();
    private final int distance;
    private final BooleanSupplier doorPassingThroughGetter;

    public MoveThroughVillageGoal(PathAwareEntity pathAwareEntity, double speed, boolean requiresNighttime, int distance, BooleanSupplier doorPassingThroughGetter) {
        this.mob = pathAwareEntity;
        this.speed = speed;
        this.requiresNighttime = requiresNighttime;
        this.distance = distance;
        this.doorPassingThroughGetter = doorPassingThroughGetter;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
        if (!class_5493.method_30955(pathAwareEntity)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }

    @Override
    public boolean canStart() {
        if (!class_5493.method_30955(this.mob)) {
            return false;
        }
        this.forgetOldTarget();
        if (this.requiresNighttime && this.mob.world.isDay()) {
            return false;
        }
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        BlockPos _snowman2 = this.mob.getBlockPos();
        if (!serverWorld.isNearOccupiedPointOfInterest(_snowman2, 6)) {
            return false;
        }
        Vec3d _snowman3 = TargetFinder.findGroundTarget(this.mob, 15, 7, blockPos2 -> {
            if (!serverWorld.isNearOccupiedPointOfInterest((BlockPos)blockPos2)) {
                return Double.NEGATIVE_INFINITY;
            }
            Optional<BlockPos> optional = serverWorld.getPointOfInterestStorage().getPosition(PointOfInterestType.ALWAYS_TRUE, this::shouldVisit, (BlockPos)blockPos2, 10, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED);
            if (!optional.isPresent()) {
                return Double.NEGATIVE_INFINITY;
            }
            return -optional.get().getSquaredDistance(_snowman2);
        });
        if (_snowman3 == null) {
            return false;
        }
        Optional<BlockPos> _snowman4 = serverWorld.getPointOfInterestStorage().getPosition(PointOfInterestType.ALWAYS_TRUE, this::shouldVisit, new BlockPos(_snowman3), 10, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED);
        if (!_snowman4.isPresent()) {
            return false;
        }
        this.target = _snowman4.get().toImmutable();
        MobNavigation _snowman5 = (MobNavigation)this.mob.getNavigation();
        boolean _snowman6 = _snowman5.canEnterOpenDoors();
        _snowman5.setCanPathThroughDoors(this.doorPassingThroughGetter.getAsBoolean());
        this.targetPath = _snowman5.findPathTo(this.target, 0);
        _snowman5.setCanPathThroughDoors(_snowman6);
        if (this.targetPath == null) {
            Vec3d vec3d = TargetFinder.findTargetTowards(this.mob, 10, 7, Vec3d.ofBottomCenter(this.target));
            if (vec3d == null) {
                return false;
            }
            _snowman5.setCanPathThroughDoors(this.doorPassingThroughGetter.getAsBoolean());
            this.targetPath = this.mob.getNavigation().findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
            _snowman5.setCanPathThroughDoors(_snowman6);
            if (this.targetPath == null) {
                return false;
            }
        }
        for (int i = 0; i < this.targetPath.getLength(); ++i) {
            PathNode pathNode = this.targetPath.getNode(i);
            BlockPos _snowman7 = new BlockPos(pathNode.x, pathNode.y + 1, pathNode.z);
            if (!DoorBlock.isWoodenDoor(this.mob.world, _snowman7)) continue;
            this.targetPath = this.mob.getNavigation().findPathTo(pathNode.x, pathNode.y, pathNode.z, 0);
            break;
        }
        return this.targetPath != null;
    }

    @Override
    public boolean shouldContinue() {
        if (this.mob.getNavigation().isIdle()) {
            return false;
        }
        return !this.target.isWithinDistance(this.mob.getPos(), (double)(this.mob.getWidth() + (float)this.distance));
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingAlong(this.targetPath, this.speed);
    }

    @Override
    public void stop() {
        if (this.mob.getNavigation().isIdle() || this.target.isWithinDistance(this.mob.getPos(), (double)this.distance)) {
            this.visitedTargets.add(this.target);
        }
    }

    private boolean shouldVisit(BlockPos pos) {
        for (BlockPos blockPos : this.visitedTargets) {
            if (!Objects.equals(pos, blockPos)) continue;
            return false;
        }
        return true;
    }

    private void forgetOldTarget() {
        if (this.visitedTargets.size() > 15) {
            this.visitedTargets.remove(0);
        }
    }
}

