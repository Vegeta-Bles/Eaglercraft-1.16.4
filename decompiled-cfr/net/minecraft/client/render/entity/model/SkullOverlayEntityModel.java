/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SkullOverlayEntityModel
extends SkullEntityModel {
    private final ModelPart skullOverlay = new ModelPart(this, 32, 0);

    public SkullOverlayEntityModel() {
        super(0, 0, 64, 64);
        this.skullOverlay.addCuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, 0.25f);
        this.skullOverlay.setPivot(0.0f, 0.0f, 0.0f);
    }

    @Override
    public void method_2821(float f, float f2, float f3) {
        super.method_2821(f, f2, f3);
        this.skullOverlay.yaw = this.skull.yaw;
        this.skullOverlay.pitch = this.skull.pitch;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        this.skullOverlay.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}

