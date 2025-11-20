/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.math.MathHelper;

public class IronGolemEntityModel<T extends IronGolemEntity>
extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart torso;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public IronGolemEntityModel() {
        int n = 128;
        _snowman = 128;
        this.head = new ModelPart(this).setTextureSize(128, 128);
        this.head.setPivot(0.0f, -7.0f, -2.0f);
        this.head.setTextureOffset(0, 0).addCuboid(-4.0f, -12.0f, -5.5f, 8.0f, 10.0f, 8.0f, 0.0f);
        this.head.setTextureOffset(24, 0).addCuboid(-1.0f, -5.0f, -7.5f, 2.0f, 4.0f, 2.0f, 0.0f);
        this.torso = new ModelPart(this).setTextureSize(128, 128);
        this.torso.setPivot(0.0f, -7.0f, 0.0f);
        this.torso.setTextureOffset(0, 40).addCuboid(-9.0f, -2.0f, -6.0f, 18.0f, 12.0f, 11.0f, 0.0f);
        this.torso.setTextureOffset(0, 70).addCuboid(-4.5f, 10.0f, -3.0f, 9.0f, 5.0f, 6.0f, 0.5f);
        this.rightArm = new ModelPart(this).setTextureSize(128, 128);
        this.rightArm.setPivot(0.0f, -7.0f, 0.0f);
        this.rightArm.setTextureOffset(60, 21).addCuboid(-13.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, 0.0f);
        this.leftArm = new ModelPart(this).setTextureSize(128, 128);
        this.leftArm.setPivot(0.0f, -7.0f, 0.0f);
        this.leftArm.setTextureOffset(60, 58).addCuboid(9.0f, -2.5f, -3.0f, 4.0f, 30.0f, 6.0f, 0.0f);
        this.rightLeg = new ModelPart(this, 0, 22).setTextureSize(128, 128);
        this.rightLeg.setPivot(-4.0f, 11.0f, 0.0f);
        this.rightLeg.setTextureOffset(37, 0).addCuboid(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, 0.0f);
        this.leftLeg = new ModelPart(this, 0, 22).setTextureSize(128, 128);
        this.leftLeg.mirror = true;
        this.leftLeg.setTextureOffset(60, 0).setPivot(5.0f, 11.0f, 0.0f);
        this.leftLeg.addCuboid(-3.5f, -3.0f, -3.0f, 6.0f, 16.0f, 5.0f, 0.0f);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.head, (Object)this.torso, (Object)this.rightLeg, (Object)this.leftLeg, (Object)this.rightArm, (Object)this.leftArm);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.head.yaw = f4 * ((float)Math.PI / 180);
        this.head.pitch = f5 * ((float)Math.PI / 180);
        this.rightLeg.pitch = -1.5f * MathHelper.method_24504(f, 13.0f) * f2;
        this.leftLeg.pitch = 1.5f * MathHelper.method_24504(f, 13.0f) * f2;
        this.rightLeg.yaw = 0.0f;
        this.leftLeg.yaw = 0.0f;
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        int n = ((IronGolemEntity)t).getAttackTicksLeft();
        if (n > 0) {
            this.rightArm.pitch = -2.0f + 1.5f * MathHelper.method_24504((float)n - f3, 10.0f);
            this.leftArm.pitch = -2.0f + 1.5f * MathHelper.method_24504((float)n - f3, 10.0f);
        } else {
            _snowman = ((IronGolemEntity)t).getLookingAtVillagerTicks();
            if (_snowman > 0) {
                this.rightArm.pitch = -0.8f + 0.025f * MathHelper.method_24504(_snowman, 70.0f);
                this.leftArm.pitch = 0.0f;
            } else {
                this.rightArm.pitch = (-0.2f + 1.5f * MathHelper.method_24504(f, 13.0f)) * f2;
                this.leftArm.pitch = (-0.2f - 1.5f * MathHelper.method_24504(f, 13.0f)) * f2;
            }
        }
    }

    public ModelPart getRightArm() {
        return this.rightArm;
    }
}

