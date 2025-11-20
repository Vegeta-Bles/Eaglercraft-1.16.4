/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class GoToVillageGoal
extends Goal {
    private final PathAwareEntity mob;
    private final int searchRange;
    @Nullable
    private BlockPos targetPosition;

    public GoToVillageGoal(PathAwareEntity mob, int searchRange) {
        this.mob = mob;
        this.searchRange = searchRange;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        }
        if (this.mob.world.isDay()) {
            return false;
        }
        if (this.mob.getRandom().nextInt(this.searchRange) != 0) {
            return false;
        }
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        BlockPos _snowman2 = this.mob.getBlockPos();
        if (!serverWorld.isNearOccupiedPointOfInterest(_snowman2, 6)) {
            return false;
        }
        Vec3d _snowman3 = TargetFinder.findGroundTarget(this.mob, 15, 7, blockPos -> -serverWorld.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(blockPos)));
        this.targetPosition = _snowman3 == null ? null : new BlockPos(_snowman3);
        return this.targetPosition != null;
    }

    @Override
    public boolean shouldContinue() {
        return this.targetPosition != null && !this.mob.getNavigation().isIdle() && this.mob.getNavigation().getTargetPos().equals(this.targetPosition);
    }

    @Override
    public void tick() {
        if (this.targetPosition == null) {
            return;
        }
        EntityNavigation entityNavigation = this.mob.getNavigation();
        if (entityNavigation.isIdle() && !this.targetPosition.isWithinDistance(this.mob.getPos(), 10.0)) {
            Vec3d vec3d = Vec3d.ofBottomCenter(this.targetPosition);
            _snowman = this.mob.getPos();
            _snowman = _snowman.subtract(vec3d);
            vec3d = _snowman.multiply(0.4).add(vec3d);
            _snowman = vec3d.subtract(_snowman).normalize().multiply(10.0).add(_snowman);
            BlockPos _snowman2 = new BlockPos(_snowman);
            if (!entityNavigation.startMovingTo((_snowman2 = this.mob.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowman2)).getX(), _snowman2.getY(), _snowman2.getZ(), 1.0)) {
                this.findOtherWaypoint();
            }
        }
    }

    private void findOtherWaypoint() {
        Random random = this.mob.getRandom();
        BlockPos _snowman2 = this.mob.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.mob.getBlockPos().add(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
        this.mob.getNavigation().startMovingTo(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ(), 1.0);
    }
}

