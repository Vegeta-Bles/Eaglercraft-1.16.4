/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.TintableAnimalModel;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.math.MathHelper;

public class WolfEntityModel<T extends WolfEntity>
extends TintableAnimalModel<T> {
    private final ModelPart head;
    private final ModelPart field_20788;
    private final ModelPart torso;
    private final ModelPart rightBackLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;
    private final ModelPart field_20789;
    private final ModelPart neck;

    public WolfEntityModel() {
        float f = 0.0f;
        _snowman = 13.5f;
        this.head = new ModelPart(this, 0, 0);
        this.head.setPivot(-1.0f, 13.5f, -7.0f);
        this.field_20788 = new ModelPart(this, 0, 0);
        this.field_20788.addCuboid(-2.0f, -3.0f, -2.0f, 6.0f, 6.0f, 4.0f, 0.0f);
        this.head.addChild(this.field_20788);
        this.torso = new ModelPart(this, 18, 14);
        this.torso.addCuboid(-3.0f, -2.0f, -3.0f, 6.0f, 9.0f, 6.0f, 0.0f);
        this.torso.setPivot(0.0f, 14.0f, 2.0f);
        this.neck = new ModelPart(this, 21, 0);
        this.neck.addCuboid(-3.0f, -3.0f, -3.0f, 8.0f, 6.0f, 7.0f, 0.0f);
        this.neck.setPivot(-1.0f, 14.0f, 2.0f);
        this.rightBackLeg = new ModelPart(this, 0, 18);
        this.rightBackLeg.addCuboid(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.rightBackLeg.setPivot(-2.5f, 16.0f, 7.0f);
        this.leftBackLeg = new ModelPart(this, 0, 18);
        this.leftBackLeg.addCuboid(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.leftBackLeg.setPivot(0.5f, 16.0f, 7.0f);
        this.rightFrontLeg = new ModelPart(this, 0, 18);
        this.rightFrontLeg.addCuboid(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.rightFrontLeg.setPivot(-2.5f, 16.0f, -4.0f);
        this.leftFrontLeg = new ModelPart(this, 0, 18);
        this.leftFrontLeg.addCuboid(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.leftFrontLeg.setPivot(0.5f, 16.0f, -4.0f);
        this.tail = new ModelPart(this, 9, 18);
        this.tail.setPivot(-1.0f, 12.0f, 8.0f);
        this.field_20789 = new ModelPart(this, 9, 18);
        this.field_20789.addCuboid(0.0f, 0.0f, -1.0f, 2.0f, 8.0f, 2.0f, 0.0f);
        this.tail.addChild(this.field_20789);
        this.field_20788.setTextureOffset(16, 14).addCuboid(-2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.field_20788.setTextureOffset(16, 14).addCuboid(2.0f, -5.0f, 0.0f, 2.0f, 2.0f, 1.0f, 0.0f);
        this.field_20788.setTextureOffset(0, 10).addCuboid(-0.5f, 0.0f, -5.0f, 3.0f, 3.0f, 4.0f, 0.0f);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of((Object)this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of((Object)this.torso, (Object)this.rightBackLeg, (Object)this.leftBackLeg, (Object)this.rightFrontLeg, (Object)this.leftFrontLeg, (Object)this.tail, (Object)this.neck);
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        this.tail.yaw = t.hasAngerTime() ? 0.0f : MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        if (((TameableEntity)t).isInSittingPose()) {
            this.neck.setPivot(-1.0f, 16.0f, -3.0f);
            this.neck.pitch = 1.2566371f;
            this.neck.yaw = 0.0f;
            this.torso.setPivot(0.0f, 18.0f, 0.0f);
            this.torso.pitch = 0.7853982f;
            this.tail.setPivot(-1.0f, 21.0f, 6.0f);
            this.rightBackLeg.setPivot(-2.5f, 22.7f, 2.0f);
            this.rightBackLeg.pitch = 4.712389f;
            this.leftBackLeg.setPivot(0.5f, 22.7f, 2.0f);
            this.leftBackLeg.pitch = 4.712389f;
            this.rightFrontLeg.pitch = 5.811947f;
            this.rightFrontLeg.setPivot(-2.49f, 17.0f, -4.0f);
            this.leftFrontLeg.pitch = 5.811947f;
            this.leftFrontLeg.setPivot(0.51f, 17.0f, -4.0f);
        } else {
            this.torso.setPivot(0.0f, 14.0f, 2.0f);
            this.torso.pitch = 1.5707964f;
            this.neck.setPivot(-1.0f, 14.0f, -3.0f);
            this.neck.pitch = this.torso.pitch;
            this.tail.setPivot(-1.0f, 12.0f, 8.0f);
            this.rightBackLeg.setPivot(-2.5f, 16.0f, 7.0f);
            this.leftBackLeg.setPivot(0.5f, 16.0f, 7.0f);
            this.rightFrontLeg.setPivot(-2.5f, 16.0f, -4.0f);
            this.leftFrontLeg.setPivot(0.5f, 16.0f, -4.0f);
            this.rightBackLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
            this.leftBackLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
            this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2;
            this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f2;
        }
        this.field_20788.roll = ((WolfEntity)t).getBegAnimationProgress(f3) + ((WolfEntity)t).getShakeAnimationProgress(f3, 0.0f);
        this.neck.roll = ((WolfEntity)t).getShakeAnimationProgress(f3, -0.08f);
        this.torso.roll = ((WolfEntity)t).getShakeAnimationProgress(f3, -0.16f);
        this.field_20789.roll = ((WolfEntity)t).getShakeAnimationProgress(f3, -0.2f);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.head.pitch = f5 * ((float)Math.PI / 180);
        this.head.yaw = f4 * ((float)Math.PI / 180);
        this.tail.pitch = f3;
    }
}

