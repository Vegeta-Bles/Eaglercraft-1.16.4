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
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class IllagerEntityModel<T extends IllagerEntity>
extends CompositeEntityModel<T>
implements ModelWithArms,
ModelWithHead {
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart torso;
    private final ModelPart arms;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightAttackingArm;
    private final ModelPart leftAttackingArm;

    public IllagerEntityModel(float scale, float pivotY, int textureWidth, int textureHeight) {
        this.head = new ModelPart(this).setTextureSize(textureWidth, textureHeight);
        this.head.setPivot(0.0f, 0.0f + pivotY, 0.0f);
        this.head.setTextureOffset(0, 0).addCuboid(-4.0f, -10.0f, -4.0f, 8.0f, 10.0f, 8.0f, scale);
        this.hat = new ModelPart(this, 32, 0).setTextureSize(textureWidth, textureHeight);
        this.hat.addCuboid(-4.0f, -10.0f, -4.0f, 8.0f, 12.0f, 8.0f, scale + 0.45f);
        this.head.addChild(this.hat);
        this.hat.visible = false;
        ModelPart modelPart = new ModelPart(this).setTextureSize(textureWidth, textureHeight);
        modelPart.setPivot(0.0f, pivotY - 2.0f, 0.0f);
        modelPart.setTextureOffset(24, 0).addCuboid(-1.0f, -1.0f, -6.0f, 2.0f, 4.0f, 2.0f, scale);
        this.head.addChild(modelPart);
        this.torso = new ModelPart(this).setTextureSize(textureWidth, textureHeight);
        this.torso.setPivot(0.0f, 0.0f + pivotY, 0.0f);
        this.torso.setTextureOffset(16, 20).addCuboid(-4.0f, 0.0f, -3.0f, 8.0f, 12.0f, 6.0f, scale);
        this.torso.setTextureOffset(0, 38).addCuboid(-4.0f, 0.0f, -3.0f, 8.0f, 18.0f, 6.0f, scale + 0.5f);
        this.arms = new ModelPart(this).setTextureSize(textureWidth, textureHeight);
        this.arms.setPivot(0.0f, 0.0f + pivotY + 2.0f, 0.0f);
        this.arms.setTextureOffset(44, 22).addCuboid(-8.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, scale);
        _snowman = new ModelPart(this, 44, 22).setTextureSize(textureWidth, textureHeight);
        _snowman.mirror = true;
        _snowman.addCuboid(4.0f, -2.0f, -2.0f, 4.0f, 8.0f, 4.0f, scale);
        this.arms.addChild(_snowman);
        this.arms.setTextureOffset(40, 38).addCuboid(-4.0f, 2.0f, -2.0f, 8.0f, 4.0f, 4.0f, scale);
        this.rightLeg = new ModelPart(this, 0, 22).setTextureSize(textureWidth, textureHeight);
        this.rightLeg.setPivot(-2.0f, 12.0f + pivotY, 0.0f);
        this.rightLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, scale);
        this.leftLeg = new ModelPart(this, 0, 22).setTextureSize(textureWidth, textureHeight);
        this.leftLeg.mirror = true;
        this.leftLeg.setPivot(2.0f, 12.0f + pivotY, 0.0f);
        this.leftLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, scale);
        this.rightAttackingArm = new ModelPart(this, 40, 46).setTextureSize(textureWidth, textureHeight);
        this.rightAttackingArm.addCuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, scale);
        this.rightAttackingArm.setPivot(-5.0f, 2.0f + pivotY, 0.0f);
        this.leftAttackingArm = new ModelPart(this, 40, 46).setTextureSize(textureWidth, textureHeight);
        this.leftAttackingArm.mirror = true;
        this.leftAttackingArm.addCuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, scale);
        this.leftAttackingArm.setPivot(5.0f, 2.0f + pivotY, 0.0f);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.head, (Object)this.torso, (Object)this.rightLeg, (Object)this.leftLeg, (Object)this.arms, (Object)this.rightAttackingArm, (Object)this.leftAttackingArm);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        this.head.yaw = f4 * ((float)Math.PI / 180);
        this.head.pitch = f5 * ((float)Math.PI / 180);
        this.arms.pivotY = 3.0f;
        this.arms.pivotZ = -1.0f;
        this.arms.pitch = -0.75f;
        if (this.riding) {
            this.rightAttackingArm.pitch = -0.62831855f;
            this.rightAttackingArm.yaw = 0.0f;
            this.rightAttackingArm.roll = 0.0f;
            this.leftAttackingArm.pitch = -0.62831855f;
            this.leftAttackingArm.yaw = 0.0f;
            this.leftAttackingArm.roll = 0.0f;
            this.rightLeg.pitch = -1.4137167f;
            this.rightLeg.yaw = 0.31415927f;
            this.rightLeg.roll = 0.07853982f;
            this.leftLeg.pitch = -1.4137167f;
            this.leftLeg.yaw = -0.31415927f;
            this.leftLeg.roll = -0.07853982f;
        } else {
            this.rightAttackingArm.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 2.0f * f2 * 0.5f;
            this.rightAttackingArm.yaw = 0.0f;
            this.rightAttackingArm.roll = 0.0f;
            this.leftAttackingArm.pitch = MathHelper.cos(f * 0.6662f) * 2.0f * f2 * 0.5f;
            this.leftAttackingArm.yaw = 0.0f;
            this.leftAttackingArm.roll = 0.0f;
            this.rightLeg.pitch = MathHelper.cos(f * 0.6662f) * 1.4f * f2 * 0.5f;
            this.rightLeg.yaw = 0.0f;
            this.rightLeg.roll = 0.0f;
            this.leftLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * 1.4f * f2 * 0.5f;
            this.leftLeg.yaw = 0.0f;
            this.leftLeg.roll = 0.0f;
        }
        IllagerEntity.State state = ((IllagerEntity)t).getState();
        if (state == IllagerEntity.State.ATTACKING) {
            if (((LivingEntity)t).getMainHandStack().isEmpty()) {
                CrossbowPosing.method_29352(this.leftAttackingArm, this.rightAttackingArm, true, this.handSwingProgress, f3);
            } else {
                CrossbowPosing.method_29351(this.rightAttackingArm, this.leftAttackingArm, t, this.handSwingProgress, f3);
            }
        } else if (state == IllagerEntity.State.SPELLCASTING) {
            this.rightAttackingArm.pivotZ = 0.0f;
            this.rightAttackingArm.pivotX = -5.0f;
            this.leftAttackingArm.pivotZ = 0.0f;
            this.leftAttackingArm.pivotX = 5.0f;
            this.rightAttackingArm.pitch = MathHelper.cos(f3 * 0.6662f) * 0.25f;
            this.leftAttackingArm.pitch = MathHelper.cos(f3 * 0.6662f) * 0.25f;
            this.rightAttackingArm.roll = 2.3561945f;
            this.leftAttackingArm.roll = -2.3561945f;
            this.rightAttackingArm.yaw = 0.0f;
            this.leftAttackingArm.yaw = 0.0f;
        } else if (state == IllagerEntity.State.BOW_AND_ARROW) {
            this.rightAttackingArm.yaw = -0.1f + this.head.yaw;
            this.rightAttackingArm.pitch = -1.5707964f + this.head.pitch;
            this.leftAttackingArm.pitch = -0.9424779f + this.head.pitch;
            this.leftAttackingArm.yaw = this.head.yaw - 0.4f;
            this.leftAttackingArm.roll = 1.5707964f;
        } else if (state == IllagerEntity.State.CROSSBOW_HOLD) {
            CrossbowPosing.hold(this.rightAttackingArm, this.leftAttackingArm, this.head, true);
        } else if (state == IllagerEntity.State.CROSSBOW_CHARGE) {
            CrossbowPosing.charge(this.rightAttackingArm, this.leftAttackingArm, t, true);
        } else if (state == IllagerEntity.State.CELEBRATING) {
            this.rightAttackingArm.pivotZ = 0.0f;
            this.rightAttackingArm.pivotX = -5.0f;
            this.rightAttackingArm.pitch = MathHelper.cos(f3 * 0.6662f) * 0.05f;
            this.rightAttackingArm.roll = 2.670354f;
            this.rightAttackingArm.yaw = 0.0f;
            this.leftAttackingArm.pivotZ = 0.0f;
            this.leftAttackingArm.pivotX = 5.0f;
            this.leftAttackingArm.pitch = MathHelper.cos(f3 * 0.6662f) * 0.05f;
            this.leftAttackingArm.roll = -2.3561945f;
            this.leftAttackingArm.yaw = 0.0f;
        }
        this.arms.visible = _snowman = state == IllagerEntity.State.CROSSED;
        this.leftAttackingArm.visible = !_snowman;
        this.rightAttackingArm.visible = !_snowman;
    }

    private ModelPart getAttackingArm(Arm arm) {
        if (arm == Arm.LEFT) {
            return this.leftAttackingArm;
        }
        return this.rightAttackingArm;
    }

    public ModelPart getHat() {
        return this.hat;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getAttackingArm(arm).rotate(matrices);
    }
}

