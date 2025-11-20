/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;

public class BowAttackGoal<T extends HostileEntity>
extends Goal {
    private final T actor;
    private final double speed;
    private int attackInterval;
    private final float squaredRange;
    private int cooldown = -1;
    private int targetSeeingTicker;
    private boolean movingToLeft;
    private boolean backward;
    private int combatTicks = -1;

    public BowAttackGoal(T actor, double speed, int attackInterval, float range) {
        this.actor = actor;
        this.speed = speed;
        this.attackInterval = attackInterval;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public void setAttackInterval(int attackInterval) {
        this.attackInterval = attackInterval;
    }

    @Override
    public boolean canStart() {
        if (((MobEntity)this.actor).getTarget() == null) {
            return false;
        }
        return this.isHoldingBow();
    }

    protected boolean isHoldingBow() {
        return ((LivingEntity)this.actor).isHolding(Items.BOW);
    }

    @Override
    public boolean shouldContinue() {
        return (this.canStart() || !((MobEntity)this.actor).getNavigation().isIdle()) && this.isHoldingBow();
    }

    @Override
    public void start() {
        super.start();
        ((MobEntity)this.actor).setAttacking(true);
    }

    @Override
    public void stop() {
        super.stop();
        ((MobEntity)this.actor).setAttacking(false);
        this.targetSeeingTicker = 0;
        this.cooldown = -1;
        ((LivingEntity)this.actor).clearActiveItem();
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = ((MobEntity)this.actor).getTarget();
        if (livingEntity == null) {
            return;
        }
        double _snowman2 = ((Entity)this.actor).squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        boolean _snowman3 = ((MobEntity)this.actor).getVisibilityCache().canSee(livingEntity);
        boolean bl = _snowman = this.targetSeeingTicker > 0;
        if (_snowman3 != _snowman) {
            this.targetSeeingTicker = 0;
        }
        this.targetSeeingTicker = _snowman3 ? ++this.targetSeeingTicker : --this.targetSeeingTicker;
        if (_snowman2 > (double)this.squaredRange || this.targetSeeingTicker < 20) {
            ((MobEntity)this.actor).getNavigation().startMovingTo(livingEntity, this.speed);
            this.combatTicks = -1;
        } else {
            ((MobEntity)this.actor).getNavigation().stop();
            ++this.combatTicks;
        }
        if (this.combatTicks >= 20) {
            if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                boolean bl2 = this.movingToLeft = !this.movingToLeft;
            }
            if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                this.backward = !this.backward;
            }
            this.combatTicks = 0;
        }
        if (this.combatTicks > -1) {
            if (_snowman2 > (double)(this.squaredRange * 0.75f)) {
                this.backward = false;
            } else if (_snowman2 < (double)(this.squaredRange * 0.25f)) {
                this.backward = true;
            }
            ((MobEntity)this.actor).getMoveControl().strafeTo(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
            ((MobEntity)this.actor).lookAtEntity(livingEntity, 30.0f, 30.0f);
        } else {
            ((MobEntity)this.actor).getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
        }
        if (((LivingEntity)this.actor).isUsingItem()) {
            if (!_snowman3 && this.targetSeeingTicker < -60) {
                ((LivingEntity)this.actor).clearActiveItem();
            } else if (_snowman3 && (_snowman = ((LivingEntity)this.actor).getItemUseTime()) >= 20) {
                ((LivingEntity)this.actor).clearActiveItem();
                ((RangedAttackMob)this.actor).attack(livingEntity, BowItem.getPullProgress(_snowman));
                this.cooldown = this.attackInterval;
            }
        } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
            ((LivingEntity)this.actor).setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.BOW));
        }
    }
}

