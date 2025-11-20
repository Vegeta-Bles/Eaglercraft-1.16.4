/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public class Deadmau5FeatureRenderer
extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public Deadmau5FeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!"deadmau5".equals(abstractClientPlayerEntity.getName().getString()) || !abstractClientPlayerEntity.hasSkinTexture() || abstractClientPlayerEntity.isInvisible()) {
            return;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getSkinTexture()));
        int _snowman2 = LivingEntityRenderer.getOverlay(abstractClientPlayerEntity, 0.0f);
        for (int i = 0; i < 2; ++i) {
            float f7 = MathHelper.lerp(f3, abstractClientPlayerEntity.prevYaw, abstractClientPlayerEntity.yaw) - MathHelper.lerp(f3, abstractClientPlayerEntity.prevBodyYaw, abstractClientPlayerEntity.bodyYaw);
            _snowman = MathHelper.lerp(f3, abstractClientPlayerEntity.prevPitch, abstractClientPlayerEntity.pitch);
            matrixStack.push();
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f7));
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman));
            matrixStack.translate(0.375f * (float)(i * 2 - 1), 0.0, 0.0);
            matrixStack.translate(0.0, -0.375, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowman));
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-f7));
            _snowman = 1.3333334f;
            matrixStack.scale(1.3333334f, 1.3333334f, 1.3333334f);
            ((PlayerEntityModel)this.getContextModel()).renderEars(matrixStack, vertexConsumer, n, _snowman2);
            matrixStack.pop();
        }
    }
}

