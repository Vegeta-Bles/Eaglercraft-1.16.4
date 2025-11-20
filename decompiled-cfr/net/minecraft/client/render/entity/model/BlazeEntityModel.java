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
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class BlazeEntityModel<T extends Entity>
extends CompositeEntityModel<T> {
    private final ModelPart[] rods;
    private final ModelPart head = new ModelPart(this, 0, 0);
    private final ImmutableList<ModelPart> parts;

    public BlazeEntityModel() {
        this.head.addCuboid(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f);
        this.rods = new ModelPart[12];
        for (int i = 0; i < this.rods.length; ++i) {
            this.rods[i] = new ModelPart(this, 0, 16);
            this.rods[i].addCuboid(0.0f, 0.0f, 0.0f, 2.0f, 8.0f, 2.0f);
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add((Object)this.head);
        builder.addAll(Arrays.asList(this.rods));
        this.parts = builder.build();
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return this.parts;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        int n;
        float f = animationProgress * (float)Math.PI * -0.1f;
        for (n = 0; n < 4; ++n) {
            this.rods[n].pivotY = -2.0f + MathHelper.cos(((float)(n * 2) + animationProgress) * 0.25f);
            this.rods[n].pivotX = MathHelper.cos(f) * 9.0f;
            this.rods[n].pivotZ = MathHelper.sin(f) * 9.0f;
            f += 1.5707964f;
        }
        f = 0.7853982f + animationProgress * (float)Math.PI * 0.03f;
        for (n = 4; n < 8; ++n) {
            this.rods[n].pivotY = 2.0f + MathHelper.cos(((float)(n * 2) + animationProgress) * 0.25f);
            this.rods[n].pivotX = MathHelper.cos(f) * 7.0f;
            this.rods[n].pivotZ = MathHelper.sin(f) * 7.0f;
            f += 1.5707964f;
        }
        f = 0.47123894f + animationProgress * (float)Math.PI * -0.05f;
        for (n = 8; n < 12; ++n) {
            this.rods[n].pivotY = 11.0f + MathHelper.cos(((float)n * 1.5f + animationProgress) * 0.5f);
            this.rods[n].pivotX = MathHelper.cos(f) * 5.0f;
            this.rods[n].pivotZ = MathHelper.sin(f) * 5.0f;
            f += 1.5707964f;
        }
        this.head.yaw = headYaw * ((float)Math.PI / 180);
        this.head.pitch = headPitch * ((float)Math.PI / 180);
    }
}

