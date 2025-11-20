/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.Identifier;

public class TurtleEntityRenderer
extends MobEntityRenderer<TurtleEntity, TurtleEntityModel<TurtleEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/turtle/big_sea_turtle.png");

    public TurtleEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new TurtleEntityModel(0.0f), 0.7f);
    }

    @Override
    public void render(TurtleEntity turtleEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        if (turtleEntity.isBaby()) {
            this.shadowRadius *= 0.5f;
        }
        super.render(turtleEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(TurtleEntity turtleEntity) {
        return TEXTURE;
    }
}

