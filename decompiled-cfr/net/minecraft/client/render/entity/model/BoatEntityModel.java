/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;

public class BoatEntityModel
extends CompositeEntityModel<BoatEntity> {
    private final ModelPart[] paddles = new ModelPart[2];
    private final ModelPart bottom;
    private final ImmutableList<ModelPart> parts;

    public BoatEntityModel() {
        ModelPart[] modelPartArray = new ModelPart[]{new ModelPart(this, 0, 0).setTextureSize(128, 64), new ModelPart(this, 0, 19).setTextureSize(128, 64), new ModelPart(this, 0, 27).setTextureSize(128, 64), new ModelPart(this, 0, 35).setTextureSize(128, 64), new ModelPart(this, 0, 43).setTextureSize(128, 64)};
        int _snowman2 = 32;
        int _snowman3 = 6;
        int _snowman4 = 20;
        int _snowman5 = 4;
        int _snowman6 = 28;
        modelPartArray[0].addCuboid(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f, 0.0f);
        modelPartArray[0].setPivot(0.0f, 3.0f, 1.0f);
        modelPartArray[1].addCuboid(-13.0f, -7.0f, -1.0f, 18.0f, 6.0f, 2.0f, 0.0f);
        modelPartArray[1].setPivot(-15.0f, 4.0f, 4.0f);
        modelPartArray[2].addCuboid(-8.0f, -7.0f, -1.0f, 16.0f, 6.0f, 2.0f, 0.0f);
        modelPartArray[2].setPivot(15.0f, 4.0f, 0.0f);
        modelPartArray[3].addCuboid(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f, 0.0f);
        modelPartArray[3].setPivot(0.0f, 4.0f, -9.0f);
        modelPartArray[4].addCuboid(-14.0f, -7.0f, -1.0f, 28.0f, 6.0f, 2.0f, 0.0f);
        modelPartArray[4].setPivot(0.0f, 4.0f, 9.0f);
        modelPartArray[0].pitch = 1.5707964f;
        modelPartArray[1].yaw = 4.712389f;
        modelPartArray[2].yaw = 1.5707964f;
        modelPartArray[3].yaw = (float)Math.PI;
        this.paddles[0] = this.makePaddle(true);
        this.paddles[0].setPivot(3.0f, -5.0f, 9.0f);
        this.paddles[1] = this.makePaddle(false);
        this.paddles[1].setPivot(3.0f, -5.0f, -9.0f);
        this.paddles[1].yaw = (float)Math.PI;
        this.paddles[0].roll = 0.19634955f;
        this.paddles[1].roll = 0.19634955f;
        this.bottom = new ModelPart(this, 0, 0).setTextureSize(128, 64);
        this.bottom.addCuboid(-14.0f, -9.0f, -3.0f, 28.0f, 16.0f, 3.0f, 0.0f);
        this.bottom.setPivot(0.0f, -3.0f, 1.0f);
        this.bottom.pitch = 1.5707964f;
        ImmutableList.Builder _snowman7 = ImmutableList.builder();
        _snowman7.addAll(Arrays.asList(modelPartArray));
        _snowman7.addAll(Arrays.asList(this.paddles));
        this.parts = _snowman7.build();
    }

    @Override
    public void setAngles(BoatEntity boatEntity, float f, float f2, float f3, float f4, float f5) {
        this.setPaddleAngle(boatEntity, 0, f);
        this.setPaddleAngle(boatEntity, 1, f);
    }

    public ImmutableList<ModelPart> getParts() {
        return this.parts;
    }

    public ModelPart getBottom() {
        return this.bottom;
    }

    protected ModelPart makePaddle(boolean isLeft) {
        ModelPart modelPart = new ModelPart(this, 62, isLeft ? 0 : 20).setTextureSize(128, 64);
        int _snowman2 = 20;
        int _snowman3 = 7;
        int _snowman4 = 6;
        float _snowman5 = -5.0f;
        modelPart.addCuboid(-1.0f, 0.0f, -5.0f, 2.0f, 2.0f, 18.0f);
        modelPart.addCuboid(isLeft ? -1.001f : 0.001f, -3.0f, 8.0f, 1.0f, 6.0f, 7.0f);
        return modelPart;
    }

    protected void setPaddleAngle(BoatEntity boat, int paddle, float angle) {
        float f = boat.interpolatePaddlePhase(paddle, angle);
        ModelPart _snowman2 = this.paddles[paddle];
        _snowman2.pitch = (float)MathHelper.clampedLerp(-1.0471975803375244, -0.2617993950843811, (MathHelper.sin(-f) + 1.0f) / 2.0f);
        _snowman2.yaw = (float)MathHelper.clampedLerp(-0.7853981852531433, 0.7853981852531433, (MathHelper.sin(-f + 1.0f) + 1.0f) / 2.0f);
        if (paddle == 1) {
            _snowman2.yaw = (float)Math.PI - _snowman2.yaw;
        }
    }

    @Override
    public /* synthetic */ Iterable getParts() {
        return this.getParts();
    }
}

