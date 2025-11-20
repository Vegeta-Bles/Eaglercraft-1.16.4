/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class SkullEntityModel
extends Model {
    protected final ModelPart skull;

    public SkullEntityModel() {
        this(0, 35, 64, 64);
    }

    public SkullEntityModel(int textureU, int textureV, int textureWidth, int textureHeight) {
        super(RenderLayer::getEntityTranslucent);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.skull = new ModelPart(this, textureU, textureV);
        this.skull.addCuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, 0.0f);
        this.skull.setPivot(0.0f, 0.0f, 0.0f);
    }

    public void method_2821(float f, float f2, float f3) {
        this.skull.yaw = f2 * ((float)Math.PI / 180);
        this.skull.pitch = f3 * ((float)Math.PI / 180);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.skull.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}

