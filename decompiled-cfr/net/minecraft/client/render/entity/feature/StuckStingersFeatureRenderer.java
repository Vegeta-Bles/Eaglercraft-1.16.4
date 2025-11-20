/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.StuckObjectsFeatureRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class StuckStingersFeatureRenderer<T extends LivingEntity, M extends PlayerEntityModel<T>>
extends StuckObjectsFeatureRenderer<T, M> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/bee/bee_stinger.png");

    public StuckStingersFeatureRenderer(LivingEntityRenderer<T, M> livingEntityRenderer) {
        super(livingEntityRenderer);
    }

    @Override
    protected int getObjectCount(T entity) {
        return ((LivingEntity)entity).getStingerCount();
    }

    @Override
    protected void renderObject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float directionX, float directionY, float directionZ, float tickDelta) {
        float f = MathHelper.sqrt(directionX * directionX + directionZ * directionZ);
        _snowman = (float)(Math.atan2(directionX, directionZ) * 57.2957763671875);
        _snowman = (float)(Math.atan2(directionY, f) * 57.2957763671875);
        matrices.translate(0.0, 0.0, 0.0);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman - 90.0f));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman));
        _snowman = 0.0f;
        _snowman = 0.125f;
        _snowman = 0.0f;
        _snowman = 0.0625f;
        _snowman = 0.03125f;
        matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0f));
        matrices.scale(0.03125f, 0.03125f, 0.03125f);
        matrices.translate(2.5, 0.0, 0.0);
        VertexConsumer _snowman2 = vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(TEXTURE));
        for (int i = 0; i < 4; ++i) {
            matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            MatrixStack.Entry entry = matrices.peek();
            Matrix4f _snowman3 = entry.getModel();
            Matrix3f _snowman4 = entry.getNormal();
            StuckStingersFeatureRenderer.produceVertex(_snowman2, _snowman3, _snowman4, -4.5f, -1, 0.0f, 0.0f, light);
            StuckStingersFeatureRenderer.produceVertex(_snowman2, _snowman3, _snowman4, 4.5f, -1, 0.125f, 0.0f, light);
            StuckStingersFeatureRenderer.produceVertex(_snowman2, _snowman3, _snowman4, 4.5f, 1, 0.125f, 0.0625f, light);
            StuckStingersFeatureRenderer.produceVertex(_snowman2, _snowman3, _snowman4, -4.5f, 1, 0.0f, 0.0625f, light);
        }
    }

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f vertexTransform, Matrix3f normalTransform, float x, int y, float u, float v, int light) {
        vertexConsumer.vertex(vertexTransform, x, y, 0.0f).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalTransform, 0.0f, 1.0f, 0.0f).next();
    }
}

