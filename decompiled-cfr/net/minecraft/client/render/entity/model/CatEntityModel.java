/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelUtil;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;

public class CatEntityModel<T extends CatEntity>
extends OcelotEntityModel<T> {
    private float sleepAnimation;
    private float tailCurlAnimation;
    private float headDownAnimation;

    public CatEntityModel(float f) {
        super(f);
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        this.sleepAnimation = ((CatEntity)t).getSleepAnimation(f3);
        this.tailCurlAnimation = ((CatEntity)t).getTailCurlAnimation(f3);
        this.headDownAnimation = ((CatEntity)t).getHeadDownAnimation(f3);
        if (this.sleepAnimation <= 0.0f) {
            this.head.pitch = 0.0f;
            this.head.roll = 0.0f;
            this.leftFrontLeg.pitch = 0.0f;
            this.leftFrontLeg.roll = 0.0f;
            this.rightFrontLeg.pitch = 0.0f;
            this.rightFrontLeg.roll = 0.0f;
            this.rightFrontLeg.pivotX = -1.2f;
            this.leftBackLeg.pitch = 0.0f;
            this.rightBackLeg.pitch = 0.0f;
            this.rightBackLeg.roll = 0.0f;
            this.rightBackLeg.pivotX = -1.1f;
            this.rightBackLeg.pivotY = 18.0f;
        }
        super.animateModel(t, f, f2, f3);
        if (((TameableEntity)t).isInSittingPose()) {
            this.torso.pitch = 0.7853982f;
            this.torso.pivotY += -4.0f;
            this.torso.pivotZ += 5.0f;
            this.head.pivotY += -3.3f;
            this.head.pivotZ += 1.0f;
            this.upperTail.pivotY += 8.0f;
            this.upperTail.pivotZ += -2.0f;
            this.lowerTail.pivotY += 2.0f;
            this.lowerTail.pivotZ += -0.8f;
            this.upperTail.pitch = 1.7278761f;
            this.lowerTail.pitch = 2.670354f;
            this.leftFrontLeg.pitch = -0.15707964f;
            this.leftFrontLeg.pivotY = 16.1f;
            this.leftFrontLeg.pivotZ = -7.0f;
            this.rightFrontLeg.pitch = -0.15707964f;
            this.rightFrontLeg.pivotY = 16.1f;
            this.rightFrontLeg.pivotZ = -7.0f;
            this.leftBackLeg.pitch = -1.5707964f;
            this.leftBackLeg.pivotY = 21.0f;
            this.leftBackLeg.pivotZ = 1.0f;
            this.rightBackLeg.pitch = -1.5707964f;
            this.rightBackLeg.pivotY = 21.0f;
            this.rightBackLeg.pivotZ = 1.0f;
            this.animationState = 3;
        }
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        if (this.sleepAnimation > 0.0f) {
            this.head.roll = ModelUtil.interpolateAngle(this.head.roll, -1.2707963f, this.sleepAnimation);
            this.head.yaw = ModelUtil.interpolateAngle(this.head.yaw, 1.2707963f, this.sleepAnimation);
            this.leftFrontLeg.pitch = -1.2707963f;
            this.rightFrontLeg.pitch = -0.47079635f;
            this.rightFrontLeg.roll = -0.2f;
            this.rightFrontLeg.pivotX = -0.2f;
            this.leftBackLeg.pitch = -0.4f;
            this.rightBackLeg.pitch = 0.5f;
            this.rightBackLeg.roll = -0.5f;
            this.rightBackLeg.pivotX = -0.3f;
            this.rightBackLeg.pivotY = 20.0f;
            this.upperTail.pitch = ModelUtil.interpolateAngle(this.upperTail.pitch, 0.8f, this.tailCurlAnimation);
            this.lowerTail.pitch = ModelUtil.interpolateAngle(this.lowerTail.pitch, -0.4f, this.tailCurlAnimation);
        }
        if (this.headDownAnimation > 0.0f) {
            this.head.pitch = ModelUtil.interpolateAngle(this.head.pitch, -0.58177644f, this.headDownAnimation);
        }
    }
}

