/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public abstract class MoveToTargetPosGoal
extends Goal {
    protected final PathAwareEntity mob;
    public final double speed;
    protected int cooldown;
    protected int tryingTime;
    private int safeWaitingTime;
    protected BlockPos targetPos = BlockPos.ORIGIN;
    private boolean reached;
    private final int range;
    private final int maxYDifference;
    protected int lowestY;

    public MoveToTargetPosGoal(PathAwareEntity mob, double speed, int range) {
        this(mob, speed, range, 1);
    }

    public MoveToTargetPosGoal(PathAwareEntity mob, double speed, int range, int maxYDifference) {
        this.mob = mob;
        this.speed = speed;
        this.range = range;
        this.lowestY = 0;
        this.maxYDifference = maxYDifference;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
    }

    @Override
    public boolean canStart() {
        if (this.cooldown > 0) {
            --this.cooldown;
            return false;
        }
        this.cooldown = this.getInterval(this.mob);
        return this.findTargetPos();
    }

    protected int getInterval(PathAwareEntity mob) {
        return 200 + mob.getRandom().nextInt(200);
    }

    @Override
    public boolean shouldContinue() {
        return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.isTargetPos(this.mob.world, this.targetPos);
    }

    @Override
    public void start() {
        this.startMovingToTarget();
        this.tryingTime = 0;
        this.safeWaitingTime = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
    }

    protected void startMovingToTarget() {
        this.mob.getNavigation().startMovingTo((double)this.targetPos.getX() + 0.5, this.targetPos.getY() + 1, (double)this.targetPos.getZ() + 0.5, this.speed);
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 1.0;
    }

    protected BlockPos getTargetPos() {
        return this.targetPos.up();
    }

    @Override
    public void tick() {
        BlockPos blockPos = this.getTargetPos();
        if (!blockPos.isWithinDistance(this.mob.getPos(), this.getDesiredSquaredDistanceToTarget())) {
            this.reached = false;
            ++this.tryingTime;
            if (this.shouldResetPath()) {
                this.mob.getNavigation().startMovingTo((double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, this.speed);
            }
        } else {
            this.reached = true;
            --this.tryingTime;
        }
    }

    public boolean shouldResetPath() {
        return this.tryingTime % 40 == 0;
    }

    protected boolean hasReached() {
        return this.reached;
    }

    protected boolean findTargetPos() {
        int n = this.range;
        _snowman = this.maxYDifference;
        BlockPos _snowman2 = this.mob.getBlockPos();
        BlockPos.Mutable _snowman3 = new BlockPos.Mutable();
        _snowman = this.lowestY;
        while (_snowman <= _snowman) {
            for (_snowman = 0; _snowman < n; ++_snowman) {
                _snowman = 0;
                while (_snowman <= _snowman) {
                    int n2 = _snowman = _snowman < _snowman && _snowman > -_snowman ? _snowman : 0;
                    while (_snowman <= _snowman) {
                        _snowman3.set(_snowman2, _snowman, _snowman - 1, _snowman);
                        if (this.mob.isInWalkTargetRange(_snowman3) && this.isTargetPos(this.mob.world, _snowman3)) {
                            this.targetPos = _snowman3;
                            return true;
                        }
                        _snowman = _snowman > 0 ? -_snowman : 1 - _snowman;
                    }
                    _snowman = _snowman > 0 ? -_snowman : 1 - _snowman;
                }
            }
            _snowman = _snowman > 0 ? -_snowman : 1 - _snowman;
        }
        return false;
    }

    protected abstract boolean isTargetPos(WorldView var1, BlockPos var2);
}

