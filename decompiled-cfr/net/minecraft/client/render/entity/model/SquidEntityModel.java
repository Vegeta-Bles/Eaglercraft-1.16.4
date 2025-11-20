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

public class SquidEntityModel<T extends Entity>
extends CompositeEntityModel<T> {
    private final ModelPart head;
    private final ModelPart[] tentacles = new ModelPart[8];
    private final ImmutableList<ModelPart> parts;

    public SquidEntityModel() {
        int n = -16;
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-6.0f, -8.0f, -6.0f, 12.0f, 16.0f, 12.0f);
        this.head.pivotY += 8.0f;
        for (_snowman = 0; _snowman < this.tentacles.length; ++_snowman) {
            this.tentacles[_snowman] = new ModelPart(this, 48, 0);
            double d = (double)_snowman * Math.PI * 2.0 / (double)this.tentacles.length;
            float _snowman2 = (float)Math.cos(d) * 5.0f;
            float _snowman3 = (float)Math.sin(d) * 5.0f;
            this.tentacles[_snowman].addCuboid(-1.0f, 0.0f, -1.0f, 2.0f, 18.0f, 2.0f);
            this.tentacles[_snowman].pivotX = _snowman2;
            this.tentacles[_snowman].pivotZ = _snowman3;
            this.tentacles[_snowman].pivotY = 15.0f;
            d = (double)_snowman * Math.PI * -2.0 / (double)this.tentacles.length + 1.5707963267948966;
            this.tentacles[_snowman].yaw = (float)d;
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add((Object)this.head);
        builder.addAll(Arrays.asList(this.tentacles));
        this.parts = builder.build();
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        for (ModelPart modelPart : this.tentacles) {
            modelPart.pitch = animationProgress;
        }
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return this.parts;
    }
}

