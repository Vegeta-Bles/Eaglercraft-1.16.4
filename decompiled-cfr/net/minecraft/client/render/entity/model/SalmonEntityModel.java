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

public class SalmonEntityModel<T extends Entity>
extends CompositeEntityModel<T> {
    private final ModelPart torso;
    private final ModelPart tail;
    private final ModelPart head;
    private final ModelPart rightFin;
    private final ModelPart leftFin;

    public SalmonEntityModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        int n = 20;
        this.torso = new ModelPart(this, 0, 0);
        this.torso.addCuboid(-1.5f, -2.5f, 0.0f, 3.0f, 5.0f, 8.0f);
        this.torso.setPivot(0.0f, 20.0f, 0.0f);
        this.tail = new ModelPart(this, 0, 13);
        this.tail.addCuboid(-1.5f, -2.5f, 0.0f, 3.0f, 5.0f, 8.0f);
        this.tail.setPivot(0.0f, 20.0f, 8.0f);
        this.head = new ModelPart(this, 22, 0);
        this.head.addCuboid(-1.0f, -2.0f, -3.0f, 2.0f, 4.0f, 3.0f);
        this.head.setPivot(0.0f, 20.0f, 0.0f);
        ModelPart _snowman2 = new ModelPart(this, 20, 10);
        _snowman2.addCuboid(0.0f, -2.5f, 0.0f, 0.0f, 5.0f, 6.0f);
        _snowman2.setPivot(0.0f, 0.0f, 8.0f);
        this.tail.addChild(_snowman2);
        ModelPart _snowman3 = new ModelPart(this, 2, 1);
        _snowman3.addCuboid(0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 3.0f);
        _snowman3.setPivot(0.0f, -4.5f, 5.0f);
        this.torso.addChild(_snowman3);
        ModelPart _snowman4 = new ModelPart(this, 0, 2);
        _snowman4.addCuboid(0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 4.0f);
        _snowman4.setPivot(0.0f, -4.5f, -1.0f);
        this.tail.addChild(_snowman4);
        this.rightFin = new ModelPart(this, -4, 0);
        this.rightFin.addCuboid(-2.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f);
        this.rightFin.setPivot(-1.5f, 21.5f, 0.0f);
        this.rightFin.roll = -0.7853982f;
        this.leftFin = new ModelPart(this, 0, 0);
        this.leftFin.addCuboid(0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 2.0f);
        this.leftFin.setPivot(1.5f, 21.5f, 0.0f);
        this.leftFin.roll = 0.7853982f;
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.torso, (Object)this.tail, (Object)this.head, (Object)this.rightFin, (Object)this.leftFin);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float f = 1.0f;
        _snowman = 1.0f;
        if (!((Entity)entity).isTouchingWater()) {
            f = 1.3f;
            _snowman = 1.7f;
        }
        this.tail.yaw = -f * 0.25f * MathHelper.sin(_snowman * 0.6f * animationProgress);
    }
}

