/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class CrossbowPosing {
    public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        _snowman = rightArmed ? otherArm : holdingArm;
        modelPart.yaw = (rightArmed ? -0.3f : 0.3f) + head.yaw;
        _snowman.yaw = (rightArmed ? 0.6f : -0.6f) + head.yaw;
        modelPart.pitch = -1.5707964f + head.pitch + 0.1f;
        _snowman.pitch = -1.5f + head.pitch;
    }

    public static void charge(ModelPart holdingArm, ModelPart pullingArm, LivingEntity actor, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : pullingArm;
        _snowman = rightArmed ? pullingArm : holdingArm;
        modelPart.yaw = rightArmed ? -0.8f : 0.8f;
        _snowman.pitch = modelPart.pitch = -0.97079635f;
        float _snowman2 = CrossbowItem.getPullTime(actor.getActiveItem());
        float _snowman3 = MathHelper.clamp((float)actor.getItemUseTime(), 0.0f, _snowman2);
        float _snowman4 = _snowman3 / _snowman2;
        _snowman.yaw = MathHelper.lerp(_snowman4, 0.4f, 0.85f) * (float)(rightArmed ? 1 : -1);
        _snowman.pitch = MathHelper.lerp(_snowman4, _snowman.pitch, -1.5707964f);
    }

    public static <T extends MobEntity> void method_29351(ModelPart modelPart, ModelPart modelPart2, T t, float f, float f2) {
        _snowman = MathHelper.sin(f * (float)Math.PI);
        _snowman = MathHelper.sin((1.0f - (1.0f - f) * (1.0f - f)) * (float)Math.PI);
        modelPart.roll = 0.0f;
        modelPart2.roll = 0.0f;
        modelPart.yaw = 0.15707964f;
        modelPart2.yaw = -0.15707964f;
        if (t.getMainArm() == Arm.RIGHT) {
            modelPart.pitch = -1.8849558f + MathHelper.cos(f2 * 0.09f) * 0.15f;
            modelPart2.pitch = -0.0f + MathHelper.cos(f2 * 0.19f) * 0.5f;
            modelPart.pitch += _snowman * 2.2f - _snowman * 0.4f;
            modelPart2.pitch += _snowman * 1.2f - _snowman * 0.4f;
        } else {
            modelPart.pitch = -0.0f + MathHelper.cos(f2 * 0.19f) * 0.5f;
            modelPart2.pitch = -1.8849558f + MathHelper.cos(f2 * 0.09f) * 0.15f;
            modelPart.pitch += _snowman * 1.2f - _snowman * 0.4f;
            modelPart2.pitch += _snowman * 2.2f - _snowman * 0.4f;
        }
        CrossbowPosing.method_29350(modelPart, modelPart2, f2);
    }

    public static void method_29350(ModelPart modelPart, ModelPart modelPart2, float f) {
        modelPart.roll += MathHelper.cos(f * 0.09f) * 0.05f + 0.05f;
        modelPart2.roll -= MathHelper.cos(f * 0.09f) * 0.05f + 0.05f;
        modelPart.pitch += MathHelper.sin(f * 0.067f) * 0.05f;
        modelPart2.pitch -= MathHelper.sin(f * 0.067f) * 0.05f;
    }

    public static void method_29352(ModelPart modelPart, ModelPart modelPart2, boolean bl, float f, float f2) {
        _snowman = MathHelper.sin(f * (float)Math.PI);
        _snowman = MathHelper.sin((1.0f - (1.0f - f) * (1.0f - f)) * (float)Math.PI);
        modelPart2.roll = 0.0f;
        modelPart.roll = 0.0f;
        modelPart2.yaw = -(0.1f - _snowman * 0.6f);
        modelPart.yaw = 0.1f - _snowman * 0.6f;
        modelPart2.pitch = _snowman = (float)(-Math.PI) / (bl ? 1.5f : 2.25f);
        modelPart.pitch = _snowman;
        modelPart2.pitch += _snowman * 1.2f - _snowman * 0.4f;
        modelPart.pitch += _snowman * 1.2f - _snowman * 0.4f;
        CrossbowPosing.method_29350(modelPart2, modelPart, f2);
    }
}

