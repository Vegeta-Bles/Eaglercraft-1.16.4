/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.Random;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class GhastEntityModel<T extends Entity>
extends CompositeEntityModel<T> {
    private final ModelPart[] tentacles = new ModelPart[9];
    private final ImmutableList<ModelPart> parts;

    public GhastEntityModel() {
        ImmutableList.Builder builder = ImmutableList.builder();
        ModelPart _snowman2 = new ModelPart(this, 0, 0);
        _snowman2.addCuboid(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f);
        _snowman2.pivotY = 17.6f;
        builder.add((Object)_snowman2);
        Random _snowman3 = new Random(1660L);
        for (int i = 0; i < this.tentacles.length; ++i) {
            this.tentacles[i] = new ModelPart(this, 0, 0);
            float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            _snowman = ((float)(i / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int _snowman4 = _snowman3.nextInt(7) + 8;
            this.tentacles[i].addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, _snowman4, 2.0f);
            this.tentacles[i].pivotX = f;
            this.tentacles[i].pivotZ = _snowman;
            this.tentacles[i].pivotY = 24.6f;
            builder.add((Object)this.tentacles[i]);
        }
        this.parts = builder.build();
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        for (int i = 0; i < this.tentacles.length; ++i) {
            this.tentacles[i].pitch = 0.2f * MathHelper.sin(animationProgress * 0.3f + (float)i) + 0.4f;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return this.parts;
    }
}

