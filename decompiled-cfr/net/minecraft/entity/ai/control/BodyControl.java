/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.control;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class BodyControl {
    private final MobEntity entity;
    private int activeTicks;
    private float lastHeadYaw;

    public BodyControl(MobEntity entity) {
        this.entity = entity;
    }

    public void tick() {
        if (this.isMoving()) {
            this.entity.bodyYaw = this.entity.yaw;
            this.rotateHead();
            this.lastHeadYaw = this.entity.headYaw;
            this.activeTicks = 0;
            return;
        }
        if (this.isIndependent()) {
            if (Math.abs(this.entity.headYaw - this.lastHeadYaw) > 15.0f) {
                this.activeTicks = 0;
                this.lastHeadYaw = this.entity.headYaw;
                this.rotateLook();
            } else {
                ++this.activeTicks;
                if (this.activeTicks > 10) {
                    this.rotateBody();
                }
            }
        }
    }

    private void rotateLook() {
        this.entity.bodyYaw = MathHelper.stepAngleTowards(this.entity.bodyYaw, this.entity.headYaw, this.entity.getBodyYawSpeed());
    }

    private void rotateHead() {
        this.entity.headYaw = MathHelper.stepAngleTowards(this.entity.headYaw, this.entity.bodyYaw, this.entity.getBodyYawSpeed());
    }

    private void rotateBody() {
        int n = this.activeTicks - 10;
        float _snowman2 = MathHelper.clamp((float)n / 10.0f, 0.0f, 1.0f);
        float _snowman3 = (float)this.entity.getBodyYawSpeed() * (1.0f - _snowman2);
        this.entity.bodyYaw = MathHelper.stepAngleTowards(this.entity.bodyYaw, this.entity.headYaw, _snowman3);
    }

    private boolean isIndependent() {
        return this.entity.getPassengerList().isEmpty() || !(this.entity.getPassengerList().get(0) instanceof MobEntity);
    }

    private boolean isMoving() {
        double d = this.entity.getX() - this.entity.prevX;
        return d * d + (_snowman = this.entity.getZ() - this.entity.prevZ) * _snowman > 2.500000277905201E-7;
    }
}

