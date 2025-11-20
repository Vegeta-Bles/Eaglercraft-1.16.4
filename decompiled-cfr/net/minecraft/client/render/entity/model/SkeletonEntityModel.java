/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class SkeletonEntityModel<T extends MobEntity>
extends BipedEntityModel<T> {
    public SkeletonEntityModel() {
        this(0.0f, false);
    }

    public SkeletonEntityModel(float stretch, boolean isClothing) {
        super(stretch);
        if (!isClothing) {
            this.rightArm = new ModelPart(this, 40, 16);
            this.rightArm.addCuboid(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, stretch);
            this.rightArm.setPivot(-5.0f, 2.0f, 0.0f);
            this.leftArm = new ModelPart(this, 40, 16);
            this.leftArm.mirror = true;
            this.leftArm.addCuboid(-1.0f, -2.0f, -1.0f, 2.0f, 12.0f, 2.0f, stretch);
            this.leftArm.setPivot(5.0f, 2.0f, 0.0f);
            this.rightLeg = new ModelPart(this, 0, 16);
            this.rightLeg.addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f, stretch);
            this.rightLeg.setPivot(-2.0f, 12.0f, 0.0f);
            this.leftLeg = new ModelPart(this, 0, 16);
            this.leftLeg.mirror = true;
            this.leftLeg.addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, 12.0f, 2.0f, stretch);
            this.leftLeg.setPivot(2.0f, 12.0f, 0.0f);
        }
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)t).getStackInHand(Hand.MAIN_HAND);
        if (itemStack.getItem() == Items.BOW && ((MobEntity)t).isAttacking()) {
            if (((MobEntity)t).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.animateModel(t, f, f2, f3);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        ItemStack itemStack = ((LivingEntity)t).getMainHandStack();
        if (((MobEntity)t).isAttacking() && (itemStack.isEmpty() || itemStack.getItem() != Items.BOW)) {
            float f6 = MathHelper.sin(this.handSwingProgress * (float)Math.PI);
            _snowman = MathHelper.sin((1.0f - (1.0f - this.handSwingProgress) * (1.0f - this.handSwingProgress)) * (float)Math.PI);
            this.rightArm.roll = 0.0f;
            this.leftArm.roll = 0.0f;
            this.rightArm.yaw = -(0.1f - f6 * 0.6f);
            this.leftArm.yaw = 0.1f - f6 * 0.6f;
            this.rightArm.pitch = -1.5707964f;
            this.leftArm.pitch = -1.5707964f;
            this.rightArm.pitch -= f6 * 1.2f - _snowman * 0.4f;
            this.leftArm.pitch -= f6 * 1.2f - _snowman * 0.4f;
            CrossbowPosing.method_29350(this.rightArm, this.leftArm, f3);
        }
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        ModelPart _snowman2 = this.getArm(arm);
        _snowman2.pivotX += f;
        _snowman2.rotate(matrices);
        _snowman2.pivotX -= f;
    }
}

