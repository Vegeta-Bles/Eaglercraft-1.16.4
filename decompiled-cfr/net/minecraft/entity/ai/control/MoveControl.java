/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.entity.ai.control;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class MoveControl {
    protected final MobEntity entity;
    protected double targetX;
    protected double targetY;
    protected double targetZ;
    protected double speed;
    protected float forwardMovement;
    protected float sidewaysMovement;
    protected State state = State.WAIT;

    public MoveControl(MobEntity entity) {
        this.entity = entity;
    }

    public boolean isMoving() {
        return this.state == State.MOVE_TO;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void moveTo(double x, double y, double z, double speed) {
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
        this.speed = speed;
        if (this.state != State.JUMPING) {
            this.state = State.MOVE_TO;
        }
    }

    public void strafeTo(float forward, float sideways) {
        this.state = State.STRAFE;
        this.forwardMovement = forward;
        this.sidewaysMovement = sideways;
        this.speed = 0.25;
    }

    public void tick() {
        if (this.state == State.STRAFE) {
            float f = (float)this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            _snowman = (float)this.speed * f;
            _snowman = this.forwardMovement;
            _snowman = this.sidewaysMovement;
            _snowman = MathHelper.sqrt(_snowman * _snowman + _snowman * _snowman);
            if (_snowman < 1.0f) {
                _snowman = 1.0f;
            }
            _snowman = _snowman / _snowman;
            _snowman = MathHelper.sin(this.entity.yaw * ((float)Math.PI / 180));
            _snowman = MathHelper.cos(this.entity.yaw * ((float)Math.PI / 180));
            _snowman = (_snowman *= _snowman) * _snowman - (_snowman *= _snowman) * _snowman;
            if (!this.method_25946(_snowman, _snowman = _snowman * _snowman + _snowman * _snowman)) {
                this.forwardMovement = 1.0f;
                this.sidewaysMovement = 0.0f;
            }
            this.entity.setMovementSpeed(_snowman);
            this.entity.setForwardSpeed(this.forwardMovement);
            this.entity.setSidewaysSpeed(this.sidewaysMovement);
            this.state = State.WAIT;
        } else if (this.state == State.MOVE_TO) {
            this.state = State.WAIT;
            double d = this.targetX - this.entity.getX();
            _snowman = this.targetZ - this.entity.getZ();
            _snowman = this.targetY - this.entity.getY();
            _snowman = d * d + _snowman * _snowman + _snowman * _snowman;
            if (_snowman < 2.500000277905201E-7) {
                this.entity.setForwardSpeed(0.0f);
                return;
            }
            float _snowman2 = (float)(MathHelper.atan2(_snowman, d) * 57.2957763671875) - 90.0f;
            this.entity.yaw = this.changeAngle(this.entity.yaw, _snowman2, 90.0f);
            this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            BlockPos _snowman3 = this.entity.getBlockPos();
            BlockState _snowman4 = this.entity.world.getBlockState(_snowman3);
            Block _snowman5 = _snowman4.getBlock();
            VoxelShape _snowman6 = _snowman4.getCollisionShape(this.entity.world, _snowman3);
            if (_snowman > (double)this.entity.stepHeight && d * d + _snowman * _snowman < (double)Math.max(1.0f, this.entity.getWidth()) || !_snowman6.isEmpty() && this.entity.getY() < _snowman6.getMax(Direction.Axis.Y) + (double)_snowman3.getY() && !_snowman5.isIn(BlockTags.DOORS) && !_snowman5.isIn(BlockTags.FENCES)) {
                this.entity.getJumpControl().setActive();
                this.state = State.JUMPING;
            }
        } else if (this.state == State.JUMPING) {
            this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
            if (this.entity.isOnGround()) {
                this.state = State.WAIT;
            }
        } else {
            this.entity.setForwardSpeed(0.0f);
        }
    }

    private boolean method_25946(float f, float f2) {
        EntityNavigation entityNavigation = this.entity.getNavigation();
        return entityNavigation == null || (_snowman = entityNavigation.getNodeMaker()) == null || _snowman.getDefaultNodeType(this.entity.world, MathHelper.floor(this.entity.getX() + (double)f), MathHelper.floor(this.entity.getY()), MathHelper.floor(this.entity.getZ() + (double)f2)) == PathNodeType.WALKABLE;
    }

    protected float changeAngle(float from, float to, float max) {
        float f = MathHelper.wrapDegrees(to - from);
        if (f > max) {
            f = max;
        }
        if (f < -max) {
            f = -max;
        }
        if ((_snowman = from + f) < 0.0f) {
            _snowman += 360.0f;
        } else if (_snowman > 360.0f) {
            _snowman -= 360.0f;
        }
        return _snowman;
    }

    public double getTargetX() {
        return this.targetX;
    }

    public double getTargetY() {
        return this.targetY;
    }

    public double getTargetZ() {
        return this.targetZ;
    }

    public static enum State {
        WAIT,
        MOVE_TO,
        STRAFE,
        JUMPING;

    }
}

