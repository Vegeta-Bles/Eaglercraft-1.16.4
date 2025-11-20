/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public class EscapeDangerGoal
extends Goal {
    protected final PathAwareEntity mob;
    protected final double speed;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected boolean active;

    public EscapeDangerGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        BlockPos blockPos;
        if (this.mob.getAttacker() == null && !this.mob.isOnFire()) {
            return false;
        }
        if (this.mob.isOnFire() && (blockPos = this.locateClosestWater(this.mob.world, this.mob, 5, 4)) != null) {
            this.targetX = blockPos.getX();
            this.targetY = blockPos.getY();
            this.targetZ = blockPos.getZ();
            return true;
        }
        return this.findTarget();
    }

    protected boolean findTarget() {
        Vec3d vec3d = TargetFinder.findTarget(this.mob, 5, 4);
        if (vec3d == null) {
            return false;
        }
        this.targetX = vec3d.x;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z;
        return true;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public void start() {
        this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
        this.active = true;
    }

    @Override
    public void stop() {
        this.active = false;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.getNavigation().isIdle();
    }

    @Nullable
    protected BlockPos locateClosestWater(BlockView blockView, Entity entity, int rangeX, int rangeY) {
        BlockPos blockPos;
        BlockPos blockPos2 = entity.getBlockPos();
        int _snowman2 = blockPos2.getX();
        int _snowman3 = blockPos2.getY();
        int _snowman4 = blockPos2.getZ();
        float _snowman5 = rangeX * rangeX * rangeY * 2;
        blockPos = null;
        BlockPos.Mutable _snowman6 = new BlockPos.Mutable();
        for (int i = _snowman2 - rangeX; i <= _snowman2 + rangeX; ++i) {
            for (_snowman = _snowman3 - rangeY; _snowman <= _snowman3 + rangeY; ++_snowman) {
                for (_snowman = _snowman4 - rangeX; _snowman <= _snowman4 + rangeX; ++_snowman) {
                    _snowman6.set(i, _snowman, _snowman);
                    if (!blockView.getFluidState(_snowman6).isIn(FluidTags.WATER) || !((_snowman = (float)((i - _snowman2) * (i - _snowman2) + (_snowman - _snowman3) * (_snowman - _snowman3) + (_snowman - _snowman4) * (_snowman - _snowman4))) < _snowman5)) continue;
                    _snowman5 = _snowman;
                    blockPos = new BlockPos(_snowman6);
                }
            }
        }
        return blockPos;
    }
}

