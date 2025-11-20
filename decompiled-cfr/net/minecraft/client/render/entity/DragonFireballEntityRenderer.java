/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class DragonFireballEntityRenderer
extends EntityRenderer<DragonFireballEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/enderdragon/dragon_fireball.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);

    public DragonFireballEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected int getBlockLight(DragonFireballEntity dragonFireballEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(DragonFireballEntity dragonFireballEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        matrixStack.scale(2.0f, 2.0f, 2.0f);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f _snowman2 = entry.getModel();
        Matrix3f _snowman3 = entry.getNormal();
        VertexConsumer _snowman4 = vertexConsumerProvider.getBuffer(LAYER);
        DragonFireballEntityRenderer.produceVertex(_snowman4, _snowman2, _snowman3, n, 0.0f, 0, 0, 1);
        DragonFireballEntityRenderer.produceVertex(_snowman4, _snowman2, _snowman3, n, 1.0f, 0, 1, 1);
        DragonFireballEntityRenderer.produceVertex(_snowman4, _snowman2, _snowman3, n, 1.0f, 1, 1, 0);
        DragonFireballEntityRenderer.produceVertex(_snowman4, _snowman2, _snowman3, n, 0.0f, 1, 0, 0);
        matrixStack.pop();
        super.render(dragonFireballEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV) {
        vertexConsumer.vertex(modelMatrix, x - 0.5f, (float)y - 0.25f, 0.0f).color(255, 255, 255, 255).texture(textureU, textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0f, 1.0f, 0.0f).next();
    }

    @Override
    public Identifier getTexture(DragonFireballEntity dragonFireballEntity) {
        return TEXTURE;
    }
}

