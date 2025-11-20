/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.PolarBearEntity;

public class PolarBearEntityModel<T extends PolarBearEntity>
extends QuadrupedEntityModel<T> {
    public PolarBearEntityModel() {
        super(12, 0.0f, true, 16.0f, 4.0f, 2.25f, 2.0f, 24);
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.5f, -3.0f, -3.0f, 7.0f, 7.0f, 7.0f, 0.0f);
        this.head.setPivot(0.0f, 10.0f, -16.0f);
        this.head.setTextureOffset(0, 44).addCuboid(-2.5f, 1.0f, -6.0f, 5.0f, 3.0f, 3.0f, 0.0f);
        this.head.setTextureOffset(26, 0).addCuboid(-4.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        ModelPart modelPart = this.head.setTextureOffset(26, 0);
        modelPart.mirror = true;
        modelPart.addCuboid(2.5f, -4.0f, -1.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.torso = new ModelPart(this);
        this.torso.setTextureOffset(0, 19).addCuboid(-5.0f, -13.0f, -7.0f, 14.0f, 14.0f, 11.0f, 0.0f);
        this.torso.setTextureOffset(39, 0).addCuboid(-4.0f, -25.0f, -7.0f, 12.0f, 12.0f, 10.0f, 0.0f);
        this.torso.setPivot(-2.0f, 9.0f, 12.0f);
        int _snowman2 = 10;
        this.backRightLeg = new ModelPart(this, 50, 22);
        this.backRightLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 8.0f, 0.0f);
        this.backRightLeg.setPivot(-3.5f, 14.0f, 6.0f);
        this.backLeftLeg = new ModelPart(this, 50, 22);
        this.backLeftLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 8.0f, 0.0f);
        this.backLeftLeg.setPivot(3.5f, 14.0f, 6.0f);
        this.frontRightLeg = new ModelPart(this, 50, 40);
        this.frontRightLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 6.0f, 0.0f);
        this.frontRightLeg.setPivot(-2.5f, 14.0f, -7.0f);
        this.frontLeftLeg = new ModelPart(this, 50, 40);
        this.frontLeftLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 10.0f, 6.0f, 0.0f);
        this.frontLeftLeg.setPivot(2.5f, 14.0f, -7.0f);
        this.backRightLeg.pivotX -= 1.0f;
        this.backLeftLeg.pivotX += 1.0f;
        this.backRightLeg.pivotZ += 0.0f;
        this.backLeftLeg.pivotZ += 0.0f;
        this.frontRightLeg.pivotX -= 1.0f;
        this.frontLeftLeg.pivotX += 1.0f;
        this.frontRightLeg.pivotZ -= 1.0f;
        this.frontLeftLeg.pivotZ -= 1.0f;
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        _snowman = f3 - (float)((PolarBearEntity)t).age;
        _snowman = ((PolarBearEntity)t).getWarningAnimationProgress(_snowman);
        _snowman *= _snowman;
        _snowman = 1.0f - _snowman;
        this.torso.pitch = 1.5707964f - _snowman * (float)Math.PI * 0.35f;
        this.torso.pivotY = 9.0f * _snowman + 11.0f * _snowman;
        this.frontRightLeg.pivotY = 14.0f * _snowman - 6.0f * _snowman;
        this.frontRightLeg.pivotZ = -8.0f * _snowman - 4.0f * _snowman;
        this.frontRightLeg.pitch -= _snowman * (float)Math.PI * 0.45f;
        this.frontLeftLeg.pivotY = this.frontRightLeg.pivotY;
        this.frontLeftLeg.pivotZ = this.frontRightLeg.pivotZ;
        this.frontLeftLeg.pitch -= _snowman * (float)Math.PI * 0.45f;
        if (this.child) {
            this.head.pivotY = 10.0f * _snowman - 9.0f * _snowman;
            this.head.pivotZ = -16.0f * _snowman - 7.0f * _snowman;
        } else {
            this.head.pivotY = 10.0f * _snowman - 14.0f * _snowman;
            this.head.pivotZ = -16.0f * _snowman - 3.0f * _snowman;
        }
        this.head.pitch += _snowman * (float)Math.PI * 0.15f;
    }
}

