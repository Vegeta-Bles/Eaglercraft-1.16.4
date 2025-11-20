/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;

public class WanderAroundPointOfInterestGoal
extends WanderAroundGoal {
    public WanderAroundPointOfInterestGoal(PathAwareEntity pathAwareEntity, double d, boolean bl) {
        super(pathAwareEntity, d, 10, bl);
    }

    @Override
    public boolean canStart() {
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        BlockPos _snowman2 = this.mob.getBlockPos();
        if (serverWorld.isNearOccupiedPointOfInterest(_snowman2)) {
            return false;
        }
        return super.canStart();
    }

    @Override
    @Nullable
    protected Vec3d getWanderTarget() {
        ServerWorld serverWorld = (ServerWorld)this.mob.world;
        BlockPos _snowman2 = this.mob.getBlockPos();
        ChunkSectionPos _snowman3 = ChunkSectionPos.from(_snowman2);
        ChunkSectionPos _snowman4 = LookTargetUtil.getPosClosestToOccupiedPointOfInterest(serverWorld, _snowman3, 2);
        if (_snowman4 != _snowman3) {
            return TargetFinder.findTargetTowards(this.mob, 10, 7, Vec3d.ofBottomCenter(_snowman4.getCenterPos()));
        }
        return null;
    }
}

