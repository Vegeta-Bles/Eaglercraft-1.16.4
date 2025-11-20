/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.math.MathHelper;

public class HorseEntityModel<T extends HorseBaseEntity>
extends AnimalModel<T> {
    protected final ModelPart torso;
    protected final ModelPart head;
    private final ModelPart leftBackLeg;
    private final ModelPart rightBackLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart field_20930;
    private final ModelPart field_20931;
    private final ModelPart field_20932;
    private final ModelPart field_20933;
    private final ModelPart tail;
    private final ModelPart[] field_3304;
    private final ModelPart[] field_3301;

    public HorseEntityModel(float scale) {
        super(true, 16.2f, 1.36f, 2.7272f, 2.0f, 20.0f);
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.torso = new ModelPart(this, 0, 32);
        this.torso.addCuboid(-5.0f, -8.0f, -17.0f, 10.0f, 10.0f, 22.0f, 0.05f);
        this.torso.setPivot(0.0f, 11.0f, 5.0f);
        this.head = new ModelPart(this, 0, 35);
        this.head.addCuboid(-2.05f, -6.0f, -2.0f, 4.0f, 12.0f, 7.0f);
        this.head.pitch = 0.5235988f;
        ModelPart modelPart = new ModelPart(this, 0, 13);
        modelPart.addCuboid(-3.0f, -11.0f, -2.0f, 6.0f, 5.0f, 7.0f, scale);
        _snowman = new ModelPart(this, 56, 36);
        _snowman.addCuboid(-1.0f, -11.0f, 5.01f, 2.0f, 16.0f, 2.0f, scale);
        _snowman = new ModelPart(this, 0, 25);
        _snowman.addCuboid(-2.0f, -11.0f, -7.0f, 4.0f, 5.0f, 5.0f, scale);
        this.head.addChild(modelPart);
        this.head.addChild(_snowman);
        this.head.addChild(_snowman);
        this.method_2789(this.head);
        this.leftBackLeg = new ModelPart(this, 48, 21);
        this.leftBackLeg.mirror = true;
        this.leftBackLeg.addCuboid(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, scale);
        this.leftBackLeg.setPivot(4.0f, 14.0f, 7.0f);
        this.rightBackLeg = new ModelPart(this, 48, 21);
        this.rightBackLeg.addCuboid(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, scale);
        this.rightBackLeg.setPivot(-4.0f, 14.0f, 7.0f);
        this.leftFrontLeg = new ModelPart(this, 48, 21);
        this.leftFrontLeg.mirror = true;
        this.leftFrontLeg.addCuboid(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, scale);
        this.leftFrontLeg.setPivot(4.0f, 6.0f, -12.0f);
        this.rightFrontLeg = new ModelPart(this, 48, 21);
        this.rightFrontLeg.addCuboid(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, scale);
        this.rightFrontLeg.setPivot(-4.0f, 6.0f, -12.0f);
        float _snowman2 = 5.5f;
        this.field_20930 = new ModelPart(this, 48, 21);
        this.field_20930.mirror = true;
        this.field_20930.addCuboid(-3.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, scale, scale + 5.5f, scale);
        this.field_20930.setPivot(4.0f, 14.0f, 7.0f);
        this.field_20931 = new ModelPart(this, 48, 21);
        this.field_20931.addCuboid(-1.0f, -1.01f, -1.0f, 4.0f, 11.0f, 4.0f, scale, scale + 5.5f, scale);
        this.field_20931.setPivot(-4.0f, 14.0f, 7.0f);
        this.field_20932 = new ModelPart(this, 48, 21);
        this.field_20932.mirror = true;
        this.field_20932.addCuboid(-3.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, scale, scale + 5.5f, scale);
        this.field_20932.setPivot(4.0f, 6.0f, -12.0f);
        this.field_20933 = new ModelPart(this, 48, 21);
        this.field_20933.addCuboid(-1.0f, -1.01f, -1.9f, 4.0f, 11.0f, 4.0f, scale, scale + 5.5f, scale);
        this.field_20933.setPivot(-4.0f, 6.0f, -12.0f);
        this.tail = new ModelPart(this, 42, 36);
        this.tail.addCuboid(-1.5f, 0.0f, 0.0f, 3.0f, 14.0f, 4.0f, scale);
        this.tail.setPivot(0.0f, -5.0f, 2.0f);
        this.tail.pitch = 0.5235988f;
        this.torso.addChild(this.tail);
        _snowman = new ModelPart(this, 26, 0);
        _snowman.addCuboid(-5.0f, -8.0f, -9.0f, 10.0f, 9.0f, 9.0f, 0.5f);
        this.torso.addChild(_snowman);
        _snowman = new ModelPart(this, 29, 5);
        _snowman.addCuboid(2.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, scale);
        this.head.addChild(_snowman);
        _snowman = new ModelPart(this, 29, 5);
        _snowman.addCuboid(-3.0f, -9.0f, -6.0f, 1.0f, 2.0f, 2.0f, scale);
        this.head.addChild(_snowman);
        _snowman = new ModelPart(this, 32, 2);
        _snowman.addCuboid(3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, scale);
        _snowman.pitch = -0.5235988f;
        this.head.addChild(_snowman);
        _snowman = new ModelPart(this, 32, 2);
        _snowman.addCuboid(-3.1f, -6.0f, -8.0f, 0.0f, 3.0f, 16.0f, scale);
        _snowman.pitch = -0.5235988f;
        this.head.addChild(_snowman);
        _snowman = new ModelPart(this, 1, 1);
        _snowman.addCuboid(-3.0f, -11.0f, -1.9f, 6.0f, 5.0f, 6.0f, 0.2f);
        this.head.addChild(_snowman);
        _snowman = new ModelPart(this, 19, 0);
        _snowman.addCuboid(-2.0f, -11.0f, -4.0f, 4.0f, 5.0f, 2.0f, 0.2f);
        this.head.addChild(_snowman);
        this.field_3304 = new ModelPart[]{_snowman, _snowman, _snowman, _snowman, _snowman};
        this.field_3301 = new ModelPart[]{_snowman, _snowman};
    }

    protected void method_2789(ModelPart modelPart) {
        _snowman = new ModelPart(this, 19, 16);
        _snowman.addCuboid(0.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, -0.001f);
        _snowman = new ModelPart(this, 19, 16);
        _snowman.addCuboid(-2.55f, -13.0f, 4.0f, 2.0f, 3.0f, 1.0f, -0.001f);
        modelPart.addChild(_snowman);
        modelPart.addChild(_snowman);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        boolean bl = ((HorseBaseEntity)t).isSaddled();
        _snowman = ((Entity)t).hasPassengers();
        for (ModelPart modelPart : this.field_3304) {
            modelPart.visible = bl;
        }
        for (ModelPart modelPart : this.field_3301) {
            modelPart.visible = _snowman && bl;
        }
        this.torso.pivotY = 11.0f;
    }

    @Override
    public Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of((Object)this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of((Object)this.torso, (Object)this.leftBackLeg, (Object)this.rightBackLeg, (Object)this.leftFrontLeg, (Object)this.rightFrontLeg, (Object)this.field_20930, (Object)this.field_20931, (Object)this.field_20932, (Object)this.field_20933);
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        super.animateModel(t, f, f2, f3);
        _snowman = MathHelper.lerpAngle(((HorseBaseEntity)t).prevBodyYaw, ((HorseBaseEntity)t).bodyYaw, f3);
        _snowman = MathHelper.lerpAngle(((HorseBaseEntity)t).prevHeadYaw, ((HorseBaseEntity)t).headYaw, f3);
        _snowman = MathHelper.lerp(f3, ((HorseBaseEntity)t).prevPitch, ((HorseBaseEntity)t).pitch);
        _snowman = _snowman - _snowman;
        _snowman = _snowman * ((float)Math.PI / 180);
        if (_snowman > 20.0f) {
            _snowman = 20.0f;
        }
        if (_snowman < -20.0f) {
            _snowman = -20.0f;
        }
        if (f2 > 0.2f) {
            _snowman += MathHelper.cos(f * 0.4f) * 0.15f * f2;
        }
        _snowman = ((HorseBaseEntity)t).getEatingGrassAnimationProgress(f3);
        _snowman = ((HorseBaseEntity)t).getAngryAnimationProgress(f3);
        _snowman = 1.0f - _snowman;
        _snowman = ((HorseBaseEntity)t).getEatingAnimationProgress(f3);
        boolean bl = ((HorseBaseEntity)t).tailWagTicks != 0;
        float _snowman2 = (float)((HorseBaseEntity)t).age + f3;
        this.head.pivotY = 4.0f;
        this.head.pivotZ = -12.0f;
        this.torso.pitch = 0.0f;
        this.head.pitch = 0.5235988f + _snowman;
        this.head.yaw = _snowman * ((float)Math.PI / 180);
        float _snowman3 = ((Entity)t).isTouchingWater() ? 0.2f : 1.0f;
        float _snowman4 = MathHelper.cos(_snowman3 * f * 0.6662f + (float)Math.PI);
        float _snowman5 = _snowman4 * 0.8f * f2;
        float _snowman6 = (1.0f - Math.max(_snowman, _snowman)) * (0.5235988f + _snowman + _snowman * MathHelper.sin(_snowman2) * 0.05f);
        this.head.pitch = _snowman * (0.2617994f + _snowman) + _snowman * (2.1816616f + MathHelper.sin(_snowman2) * 0.05f) + _snowman6;
        this.head.yaw = _snowman * _snowman * ((float)Math.PI / 180) + (1.0f - Math.max(_snowman, _snowman)) * this.head.yaw;
        this.head.pivotY = _snowman * -4.0f + _snowman * 11.0f + (1.0f - Math.max(_snowman, _snowman)) * this.head.pivotY;
        this.head.pivotZ = _snowman * -4.0f + _snowman * -12.0f + (1.0f - Math.max(_snowman, _snowman)) * this.head.pivotZ;
        this.torso.pitch = _snowman * -0.7853982f + _snowman * this.torso.pitch;
        float _snowman7 = 0.2617994f * _snowman;
        float _snowman8 = MathHelper.cos(_snowman2 * 0.6f + (float)Math.PI);
        this.leftFrontLeg.pivotY = 2.0f * _snowman + 14.0f * _snowman;
        this.leftFrontLeg.pivotZ = -6.0f * _snowman - 10.0f * _snowman;
        this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
        this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
        float _snowman9 = (-1.0471976f + _snowman8) * _snowman + _snowman5 * _snowman;
        float _snowman10 = (-1.0471976f - _snowman8) * _snowman - _snowman5 * _snowman;
        this.leftBackLeg.pitch = _snowman7 - _snowman4 * 0.5f * f2 * _snowman;
        this.rightBackLeg.pitch = _snowman7 + _snowman4 * 0.5f * f2 * _snowman;
        this.leftFrontLeg.pitch = _snowman9;
        this.rightFrontLeg.pitch = _snowman10;
        this.tail.pitch = 0.5235988f + f2 * 0.75f;
        this.tail.pivotY = -5.0f + f2;
        this.tail.pivotZ = 2.0f + f2 * 2.0f;
        this.tail.yaw = bl ? MathHelper.cos(_snowman2 * 0.7f) : 0.0f;
        this.field_20930.pivotY = this.leftBackLeg.pivotY;
        this.field_20930.pivotZ = this.leftBackLeg.pivotZ;
        this.field_20930.pitch = this.leftBackLeg.pitch;
        this.field_20931.pivotY = this.rightBackLeg.pivotY;
        this.field_20931.pivotZ = this.rightBackLeg.pivotZ;
        this.field_20931.pitch = this.rightBackLeg.pitch;
        this.field_20932.pivotY = this.leftFrontLeg.pivotY;
        this.field_20932.pivotZ = this.leftFrontLeg.pivotZ;
        this.field_20932.pitch = this.leftFrontLeg.pitch;
        this.field_20933.pivotY = this.rightFrontLeg.pivotY;
        this.field_20933.pivotZ = this.rightFrontLeg.pivotZ;
        this.field_20933.pitch = this.rightFrontLeg.pitch;
        _snowman = ((PassiveEntity)t).isBaby();
        this.leftBackLeg.visible = !_snowman;
        this.rightBackLeg.visible = !_snowman;
        this.leftFrontLeg.visible = !_snowman;
        this.rightFrontLeg.visible = !_snowman;
        this.field_20930.visible = _snowman;
        this.field_20931.visible = _snowman;
        this.field_20932.visible = _snowman;
        this.field_20933.visible = _snowman;
        this.torso.pivotY = _snowman ? 10.8f : 0.0f;
    }
}

