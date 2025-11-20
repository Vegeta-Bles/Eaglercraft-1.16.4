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
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.util.math.MathHelper;

public class MagmaCubeEntityModel<T extends SlimeEntity>
extends CompositeEntityModel<T> {
    private final ModelPart[] field_3427 = new ModelPart[8];
    private final ModelPart innerCube;
    private final ImmutableList<ModelPart> parts;

    public MagmaCubeEntityModel() {
        for (int i = 0; i < this.field_3427.length; ++i) {
            _snowman = 0;
            _snowman = i;
            if (i == 2) {
                _snowman = 24;
                _snowman = 10;
            } else if (i == 3) {
                _snowman = 24;
                _snowman = 19;
            }
            this.field_3427[i] = new ModelPart(this, _snowman, _snowman);
            this.field_3427[i].addCuboid(-4.0f, 16 + i, -4.0f, 8.0f, 1.0f, 8.0f);
        }
        this.innerCube = new ModelPart(this, 0, 16);
        this.innerCube.addCuboid(-2.0f, 18.0f, -2.0f, 4.0f, 4.0f, 4.0f);
        ImmutableList.Builder builder = ImmutableList.builder();
        builder.add((Object)this.innerCube);
        builder.addAll(Arrays.asList(this.field_3427));
        this.parts = builder.build();
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        _snowman = MathHelper.lerp(f3, ((SlimeEntity)t).lastStretch, ((SlimeEntity)t).stretch);
        if (_snowman < 0.0f) {
            _snowman = 0.0f;
        }
        for (int i = 0; i < this.field_3427.length; ++i) {
            this.field_3427[i].pivotY = (float)(-(4 - i)) * _snowman * 1.7f;
        }
    }

    public ImmutableList<ModelPart> getParts() {
        return this.parts;
    }

    @Override
    public /* synthetic */ Iterable getParts() {
        return this.getParts();
    }
}

