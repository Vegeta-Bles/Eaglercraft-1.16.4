/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class EndermanEntityModel<T extends LivingEntity>
extends BipedEntityModel<T> {
    public boolean carryingBlock;
    public boolean angry;

    public EndermanEntityModel(float f) {
        super(0.0f, -14.0f, 64, 32);
        _snowman = -14.0f;
        this.helmet = new ModelPart(this, 0, 16);
        this.helmet.addCuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, f - 0.5f);
        this.helmet.setPivot(0.0f, -14.0f, 0.0f);
        this.torso = new ModelPart(this, 32, 16);
        this.torso.addCuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, f);
        this.torso.setPivot(0.0f, -14.0f, 0.0f);
        this.rightArm = new ModelPart(this, 56, 0);
        this.rightArm.addCuboid(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.rightArm.setPivot(-3.0f, -12.0f, 0.0f);
        this.leftArm = new ModelPart(this, 56, 0);
        this.leftArm.mirror = true;
        this.leftArm.addCuboid(-1.0f, -2.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.leftArm.setPivot(5.0f, -12.0f, 0.0f);
        this.rightLeg = new ModelPart(this, 56, 0);
        this.rightLeg.addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.rightLeg.setPivot(-2.0f, -2.0f, 0.0f);
        this.leftLeg = new ModelPart(this, 56, 0);
        this.leftLeg.mirror = true;
        this.leftLeg.addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, 30.0f, 2.0f, f);
        this.leftLeg.setPivot(2.0f, -2.0f, 0.0f);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        this.head.visible = true;
        _snowman = -14.0f;
        this.torso.pitch = 0.0f;
        this.torso.pivotY = -14.0f;
        this.torso.pivotZ = -0.0f;
        this.rightLeg.pitch -= 0.0f;
        this.leftLeg.pitch -= 0.0f;
        this.rightArm.pitch = (float)((double)this.rightArm.pitch * 0.5);
        this.leftArm.pitch = (float)((double)this.leftArm.pitch * 0.5);
        this.rightLeg.pitch = (float)((double)this.rightLeg.pitch * 0.5);
        this.leftLeg.pitch = (float)((double)this.leftLeg.pitch * 0.5);
        _snowman = 0.4f;
        if (this.rightArm.pitch > 0.4f) {
            this.rightArm.pitch = 0.4f;
        }
        if (this.leftArm.pitch > 0.4f) {
            this.leftArm.pitch = 0.4f;
        }
        if (this.rightArm.pitch < -0.4f) {
            this.rightArm.pitch = -0.4f;
        }
        if (this.leftArm.pitch < -0.4f) {
            this.leftArm.pitch = -0.4f;
        }
        if (this.rightLeg.pitch > 0.4f) {
            this.rightLeg.pitch = 0.4f;
        }
        if (this.leftLeg.pitch > 0.4f) {
            this.leftLeg.pitch = 0.4f;
        }
        if (this.rightLeg.pitch < -0.4f) {
            this.rightLeg.pitch = -0.4f;
        }
        if (this.leftLeg.pitch < -0.4f) {
            this.leftLeg.pitch = -0.4f;
        }
        if (this.carryingBlock) {
            this.rightArm.pitch = -0.5f;
            this.leftArm.pitch = -0.5f;
            this.rightArm.roll = 0.05f;
            this.leftArm.roll = -0.05f;
        }
        this.rightArm.pivotZ = 0.0f;
        this.leftArm.pivotZ = 0.0f;
        this.rightLeg.pivotZ = 0.0f;
        this.leftLeg.pivotZ = 0.0f;
        this.rightLeg.pivotY = -5.0f;
        this.leftLeg.pivotY = -5.0f;
        this.head.pivotZ = -0.0f;
        this.head.pivotY = -13.0f;
        this.helmet.pivotX = this.head.pivotX;
        this.helmet.pivotY = this.head.pivotY;
        this.helmet.pivotZ = this.head.pivotZ;
        this.helmet.pitch = this.head.pitch;
        this.helmet.yaw = this.head.yaw;
        this.helmet.roll = this.head.roll;
        if (this.angry) {
            _snowman = 1.0f;
            this.head.pivotY -= 5.0f;
        }
        _snowman = -14.0f;
        this.rightArm.setPivot(-5.0f, -12.0f, 0.0f);
        this.leftArm.setPivot(5.0f, -12.0f, 0.0f);
    }
}

