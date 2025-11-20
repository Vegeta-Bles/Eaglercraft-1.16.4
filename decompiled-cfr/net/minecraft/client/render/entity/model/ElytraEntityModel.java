/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class ElytraEntityModel<T extends LivingEntity>
extends AnimalModel<T> {
    private final ModelPart field_3364;
    private final ModelPart field_3365 = new ModelPart(this, 22, 0);

    public ElytraEntityModel() {
        this.field_3365.addCuboid(-10.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, 1.0f);
        this.field_3364 = new ModelPart(this, 22, 0);
        this.field_3364.mirror = true;
        this.field_3364.addCuboid(0.0f, 0.0f, 0.0f, 10.0f, 20.0f, 2.0f, 1.0f);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of((Object)this.field_3365, (Object)this.field_3364);
    }

    @Override
    public void setAngles(T t2, float f, float f2, float f3, float f4, float f5) {
        float f6;
        float f7;
        T t2;
        f7 = 0.2617994f;
        _snowman = -0.2617994f;
        f6 = 0.0f;
        _snowman = 0.0f;
        if (((LivingEntity)t2).isFallFlying()) {
            float f8;
            f8 = 1.0f;
            Vec3d vec3d = ((Entity)t2).getVelocity();
            if (vec3d.y < 0.0) {
                _snowman = vec3d.normalize();
                f8 = 1.0f - (float)Math.pow(-_snowman.y, 1.5);
            }
            f7 = f8 * 0.34906584f + (1.0f - f8) * f7;
            _snowman = f8 * -1.5707964f + (1.0f - f8) * _snowman;
        } else if (((Entity)t2).isInSneakingPose()) {
            f7 = 0.6981317f;
            _snowman = -0.7853982f;
            f6 = 3.0f;
            _snowman = 0.08726646f;
        }
        this.field_3365.pivotX = 5.0f;
        this.field_3365.pivotY = f6;
        if (t2 instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity = (AbstractClientPlayerEntity)t2;
            abstractClientPlayerEntity.elytraPitch = (float)((double)abstractClientPlayerEntity.elytraPitch + (double)(f7 - abstractClientPlayerEntity.elytraPitch) * 0.1);
            abstractClientPlayerEntity.elytraYaw = (float)((double)abstractClientPlayerEntity.elytraYaw + (double)(_snowman - abstractClientPlayerEntity.elytraYaw) * 0.1);
            abstractClientPlayerEntity.elytraRoll = (float)((double)abstractClientPlayerEntity.elytraRoll + (double)(_snowman - abstractClientPlayerEntity.elytraRoll) * 0.1);
            this.field_3365.pitch = abstractClientPlayerEntity.elytraPitch;
            this.field_3365.yaw = abstractClientPlayerEntity.elytraYaw;
            this.field_3365.roll = abstractClientPlayerEntity.elytraRoll;
        } else {
            this.field_3365.pitch = f7;
            this.field_3365.roll = _snowman;
            this.field_3365.yaw = _snowman;
        }
        this.field_3364.pivotX = -this.field_3365.pivotX;
        this.field_3364.yaw = -this.field_3365.yaw;
        this.field_3364.pivotY = this.field_3365.pivotY;
        this.field_3364.pitch = this.field_3365.pitch;
        this.field_3364.roll = -this.field_3365.roll;
    }
}

