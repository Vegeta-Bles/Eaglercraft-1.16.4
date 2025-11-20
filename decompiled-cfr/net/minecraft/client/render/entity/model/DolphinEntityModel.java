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
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class DolphinEntityModel<T extends Entity>
extends CompositeEntityModel<T> {
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart flukes;

    public DolphinEntityModel() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        float f = 18.0f;
        _snowman = -8.0f;
        this.body = new ModelPart(this, 22, 0);
        this.body.addCuboid(-4.0f, -7.0f, 0.0f, 8.0f, 7.0f, 13.0f);
        this.body.setPivot(0.0f, 22.0f, -5.0f);
        ModelPart _snowman2 = new ModelPart(this, 51, 0);
        _snowman2.addCuboid(-0.5f, 0.0f, 8.0f, 1.0f, 4.0f, 5.0f);
        _snowman2.pitch = 1.0471976f;
        this.body.addChild(_snowman2);
        ModelPart _snowman3 = new ModelPart(this, 48, 20);
        _snowman3.mirror = true;
        _snowman3.addCuboid(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f);
        _snowman3.setPivot(2.0f, -2.0f, 4.0f);
        _snowman3.pitch = 1.0471976f;
        _snowman3.roll = 2.0943952f;
        this.body.addChild(_snowman3);
        ModelPart _snowman4 = new ModelPart(this, 48, 20);
        _snowman4.addCuboid(-0.5f, -4.0f, 0.0f, 1.0f, 4.0f, 7.0f);
        _snowman4.setPivot(-2.0f, -2.0f, 4.0f);
        _snowman4.pitch = 1.0471976f;
        _snowman4.roll = -2.0943952f;
        this.body.addChild(_snowman4);
        this.tail = new ModelPart(this, 0, 19);
        this.tail.addCuboid(-2.0f, -2.5f, 0.0f, 4.0f, 5.0f, 11.0f);
        this.tail.setPivot(0.0f, -2.5f, 11.0f);
        this.tail.pitch = -0.10471976f;
        this.body.addChild(this.tail);
        this.flukes = new ModelPart(this, 19, 20);
        this.flukes.addCuboid(-5.0f, -0.5f, 0.0f, 10.0f, 1.0f, 6.0f);
        this.flukes.setPivot(0.0f, 0.0f, 9.0f);
        this.flukes.pitch = 0.0f;
        this.tail.addChild(this.flukes);
        ModelPart _snowman5 = new ModelPart(this, 0, 0);
        _snowman5.addCuboid(-4.0f, -3.0f, -3.0f, 8.0f, 7.0f, 6.0f);
        _snowman5.setPivot(0.0f, -4.0f, -3.0f);
        ModelPart _snowman6 = new ModelPart(this, 0, 13);
        _snowman6.addCuboid(-1.0f, 2.0f, -7.0f, 2.0f, 2.0f, 4.0f);
        _snowman5.addChild(_snowman6);
        this.body.addChild(_snowman5);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.body);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.body.pitch = headPitch * ((float)Math.PI / 180);
        this.body.yaw = headYaw * ((float)Math.PI / 180);
        if (Entity.squaredHorizontalLength(((Entity)entity).getVelocity()) > 1.0E-7) {
            this.body.pitch += -0.05f + -0.05f * MathHelper.cos(animationProgress * 0.3f);
            this.tail.pitch = -0.1f * MathHelper.cos(animationProgress * 0.3f);
            this.flukes.pitch = -0.2f * MathHelper.cos(animationProgress * 0.3f);
        }
    }
}

