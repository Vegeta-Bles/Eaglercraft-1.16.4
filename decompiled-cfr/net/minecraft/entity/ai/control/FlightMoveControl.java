/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class FlightMoveControl
extends MoveControl {
    private final int maxPitchChange;
    private final boolean noGravity;

    public FlightMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
        super(entity);
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    @Override
    public void tick() {
        if (this.state == MoveControl.State.MOVE_TO) {
            this.state = MoveControl.State.WAIT;
            this.entity.setNoGravity(true);
            double d = this.targetX - this.entity.getX();
            _snowman = this.targetY - this.entity.getY();
            _snowman = this.targetZ - this.entity.getZ();
            _snowman = d * d + _snowman * _snowman + _snowman * _snowman;
            if (_snowman < 2.500000277905201E-7) {
                this.entity.setUpwardSpeed(0.0f);
                this.entity.setForwardSpeed(0.0f);
                return;
            }
            float _snowman2 = (float)(MathHelper.atan2(_snowman, d) * 57.2957763671875) - 90.0f;
            this.entity.yaw = this.changeAngle(this.entity.yaw, _snowman2, 90.0f);
            float _snowman3 = this.entity.isOnGround() ? (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)) : (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
            this.entity.setMovementSpeed(_snowman3);
            _snowman = MathHelper.sqrt(d * d + _snowman * _snowman);
            float _snowman4 = (float)(-(MathHelper.atan2(_snowman, _snowman) * 57.2957763671875));
            this.entity.pitch = this.changeAngle(this.entity.pitch, _snowman4, this.maxPitchChange);
            this.entity.setUpwardSpeed(_snowman > 0.0 ? _snowman3 : -_snowman3);
        } else {
            if (!this.noGravity) {
                this.entity.setNoGravity(false);
            }
            this.entity.setUpwardSpeed(0.0f);
            this.entity.setForwardSpeed(0.0f);
        }
    }
}

