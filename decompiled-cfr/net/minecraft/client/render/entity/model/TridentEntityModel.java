/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TridentEntityModel
extends Model {
    public static final Identifier TEXTURE = new Identifier("textures/entity/trident.png");
    private final ModelPart trident = new ModelPart(32, 32, 0, 6);

    public TridentEntityModel() {
        super(RenderLayer::getEntitySolid);
        this.trident.addCuboid(-0.5f, 2.0f, -0.5f, 1.0f, 25.0f, 1.0f, 0.0f);
        ModelPart modelPart = new ModelPart(32, 32, 4, 0);
        modelPart.addCuboid(-1.5f, 0.0f, -0.5f, 3.0f, 2.0f, 1.0f);
        this.trident.addChild(modelPart);
        _snowman = new ModelPart(32, 32, 4, 3);
        _snowman.addCuboid(-2.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f);
        this.trident.addChild(_snowman);
        _snowman = new ModelPart(32, 32, 0, 0);
        _snowman.addCuboid(-0.5f, -4.0f, -0.5f, 1.0f, 4.0f, 1.0f, 0.0f);
        this.trident.addChild(_snowman);
        _snowman = new ModelPart(32, 32, 4, 3);
        _snowman.mirror = true;
        _snowman.addCuboid(1.5f, -3.0f, -0.5f, 1.0f, 4.0f, 1.0f);
        this.trident.addChild(_snowman);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.trident.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}

