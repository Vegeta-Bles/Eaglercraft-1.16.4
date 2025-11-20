/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class DrownedEntityModel<T extends ZombieEntity>
extends ZombieEntityModel<T> {
    public DrownedEntityModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
        this.rightArm = new ModelPart(this, 32, 48);
        this.rightArm.addCuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.rightArm.setPivot(-5.0f, 2.0f + f2, 0.0f);
        this.rightLeg = new ModelPart(this, 16, 48);
        this.rightLeg.addCuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, f);
        this.rightLeg.setPivot(-1.9f, 12.0f + f2, 0.0f);
    }

    public DrownedEntityModel(float f, boolean bl) {
        super(f, 0.0f, 64, bl ? 32 : 64);
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)t).getStackInHand(Hand.MAIN_HAND);
        if (itemStack.getItem() == Items.TRIDENT && ((MobEntity)t).isAttacking()) {
            if (((MobEntity)t).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            } else {
                this.leftArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
            }
        }
        super.animateModel(t, f, f2, f3);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        if (this.leftArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
            this.leftArm.pitch = this.leftArm.pitch * 0.5f - (float)Math.PI;
            this.leftArm.yaw = 0.0f;
        }
        if (this.rightArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
            this.rightArm.pitch = this.rightArm.pitch * 0.5f - (float)Math.PI;
            this.rightArm.yaw = 0.0f;
        }
        if (this.leaningPitch > 0.0f) {
            this.rightArm.pitch = this.lerpAngle(this.leaningPitch, this.rightArm.pitch, -2.5132742f) + this.leaningPitch * 0.35f * MathHelper.sin(0.1f * f3);
            this.leftArm.pitch = this.lerpAngle(this.leaningPitch, this.leftArm.pitch, -2.5132742f) - this.leaningPitch * 0.35f * MathHelper.sin(0.1f * f3);
            this.rightArm.roll = this.lerpAngle(this.leaningPitch, this.rightArm.roll, -0.15f);
            this.leftArm.roll = this.lerpAngle(this.leaningPitch, this.leftArm.roll, 0.15f);
            this.leftLeg.pitch -= this.leaningPitch * 0.55f * MathHelper.sin(0.1f * f3);
            this.rightLeg.pitch += this.leaningPitch * 0.55f * MathHelper.sin(0.1f * f3);
            this.head.pitch = 0.0f;
        }
    }
}

