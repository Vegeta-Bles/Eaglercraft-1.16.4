/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.MathHelper;

public class ShulkerEntityModel<T extends ShulkerEntity>
extends CompositeEntityModel<T> {
    private final ModelPart bottomShell;
    private final ModelPart topShell = new ModelPart(64, 64, 0, 0);
    private final ModelPart head;

    public ShulkerEntityModel() {
        super(RenderLayer::getEntityCutoutNoCullZOffset);
        this.bottomShell = new ModelPart(64, 64, 0, 28);
        this.head = new ModelPart(64, 64, 0, 52);
        this.topShell.addCuboid(-8.0f, -16.0f, -8.0f, 16.0f, 12.0f, 16.0f);
        this.topShell.setPivot(0.0f, 24.0f, 0.0f);
        this.bottomShell.addCuboid(-8.0f, -8.0f, -8.0f, 16.0f, 8.0f, 16.0f);
        this.bottomShell.setPivot(0.0f, 24.0f, 0.0f);
        this.head.addCuboid(-3.0f, 0.0f, -3.0f, 6.0f, 6.0f, 6.0f);
        this.head.setPivot(0.0f, 12.0f, 0.0f);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        _snowman = f3 - (float)((ShulkerEntity)t).age;
        _snowman = (0.5f + ((ShulkerEntity)t).getOpenProgress(_snowman)) * (float)Math.PI;
        _snowman = -1.0f + MathHelper.sin(_snowman);
        _snowman = 0.0f;
        if (_snowman > (float)Math.PI) {
            _snowman = MathHelper.sin(f3 * 0.1f) * 0.7f;
        }
        this.topShell.setPivot(0.0f, 16.0f + MathHelper.sin(_snowman) * 8.0f + _snowman, 0.0f);
        this.topShell.yaw = ((ShulkerEntity)t).getOpenProgress(_snowman) > 0.3f ? _snowman * _snowman * _snowman * _snowman * (float)Math.PI * 0.125f : 0.0f;
        this.head.pitch = f5 * ((float)Math.PI / 180);
        this.head.yaw = (((ShulkerEntity)t).headYaw - 180.0f - ((ShulkerEntity)t).bodyYaw) * ((float)Math.PI / 180);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.bottomShell, (Object)this.topShell);
    }

    public ModelPart getBottomShell() {
        return this.bottomShell;
    }

    public ModelPart getTopShell() {
        return this.topShell;
    }

    public ModelPart getHead() {
        return this.head;
    }
}

