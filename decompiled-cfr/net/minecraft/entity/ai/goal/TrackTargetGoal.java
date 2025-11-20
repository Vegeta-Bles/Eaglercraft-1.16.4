/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.math.MathHelper;

public abstract class TrackTargetGoal
extends Goal {
    protected final MobEntity mob;
    protected final boolean checkVisibility;
    private final boolean checkCanNavigate;
    private int canNavigateFlag;
    private int checkCanNavigateCooldown;
    private int timeWithoutVisibility;
    protected LivingEntity target;
    protected int maxTimeWithoutVisibility = 60;

    public TrackTargetGoal(MobEntity mob, boolean checkVisibility) {
        this(mob, checkVisibility, false);
    }

    public TrackTargetGoal(MobEntity mob, boolean checkVisibility, boolean checkNavigable) {
        this.mob = mob;
        this.checkVisibility = checkVisibility;
        this.checkCanNavigate = checkNavigable;
    }

    @Override
    public boolean shouldContinue() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            livingEntity = this.target;
        }
        if (livingEntity == null) {
            return false;
        }
        if (!livingEntity.isAlive()) {
            return false;
        }
        AbstractTeam _snowman2 = this.mob.getScoreboardTeam();
        AbstractTeam _snowman3 = livingEntity.getScoreboardTeam();
        if (_snowman2 != null && _snowman3 == _snowman2) {
            return false;
        }
        double _snowman4 = this.getFollowRange();
        if (this.mob.squaredDistanceTo(livingEntity) > _snowman4 * _snowman4) {
            return false;
        }
        if (this.checkVisibility) {
            if (this.mob.getVisibilityCache().canSee(livingEntity)) {
                this.timeWithoutVisibility = 0;
            } else if (++this.timeWithoutVisibility > this.maxTimeWithoutVisibility) {
                return false;
            }
        }
        if (livingEntity instanceof PlayerEntity && ((PlayerEntity)livingEntity).abilities.invulnerable) {
            return false;
        }
        this.mob.setTarget(livingEntity);
        return true;
    }

    protected double getFollowRange() {
        return this.mob.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
    }

    @Override
    public void start() {
        this.canNavigateFlag = 0;
        this.checkCanNavigateCooldown = 0;
        this.timeWithoutVisibility = 0;
    }

    @Override
    public void stop() {
        this.mob.setTarget(null);
        this.target = null;
    }

    protected boolean canTrack(@Nullable LivingEntity target, TargetPredicate targetPredicate) {
        if (target == null) {
            return false;
        }
        if (!targetPredicate.test(this.mob, target)) {
            return false;
        }
        if (!this.mob.isInWalkTargetRange(target.getBlockPos())) {
            return false;
        }
        if (this.checkCanNavigate) {
            if (--this.checkCanNavigateCooldown <= 0) {
                this.canNavigateFlag = 0;
            }
            if (this.canNavigateFlag == 0) {
                int n = this.canNavigateFlag = this.canNavigateToEntity(target) ? 1 : 2;
            }
            if (this.canNavigateFlag == 2) {
                return false;
            }
        }
        return true;
    }

    private boolean canNavigateToEntity(LivingEntity entity) {
        this.checkCanNavigateCooldown = 10 + this.mob.getRandom().nextInt(5);
        Path path = this.mob.getNavigation().findPathTo(entity, 0);
        if (path == null) {
            return false;
        }
        PathNode _snowman2 = path.getEnd();
        if (_snowman2 == null) {
            return false;
        }
        int _snowman3 = _snowman2.x - MathHelper.floor(entity.getX());
        return (double)(_snowman3 * _snowman3 + (_snowman = _snowman2.z - MathHelper.floor(entity.getZ())) * _snowman) <= 2.25;
    }

    public TrackTargetGoal setMaxTimeWithoutVisibility(int time) {
        this.maxTimeWithoutVisibility = time;
        return this;
    }
}

