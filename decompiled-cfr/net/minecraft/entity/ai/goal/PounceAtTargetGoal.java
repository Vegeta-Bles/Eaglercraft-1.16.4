/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

public class PounceAtTargetGoal
extends Goal {
    private final MobEntity mob;
    private LivingEntity target;
    private final float velocity;

    public PounceAtTargetGoal(MobEntity mob, float velocity) {
        this.mob = mob;
        this.velocity = velocity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        }
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        }
        double d = this.mob.squaredDistanceTo(this.target);
        if (d < 4.0 || d > 16.0) {
            return false;
        }
        if (!this.mob.isOnGround()) {
            return false;
        }
        return this.mob.getRandom().nextInt(5) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void start() {
        Vec3d vec3d = this.mob.getVelocity();
        _snowman = new Vec3d(this.target.getX() - this.mob.getX(), 0.0, this.target.getZ() - this.mob.getZ());
        if (_snowman.lengthSquared() > 1.0E-7) {
            _snowman = _snowman.normalize().multiply(0.4).add(vec3d.multiply(0.2));
        }
        this.mob.setVelocity(_snowman.x, this.velocity, _snowman.z);
    }
}

