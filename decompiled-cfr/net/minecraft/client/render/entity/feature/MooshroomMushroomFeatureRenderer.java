/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.PassiveEntity;

public class MooshroomMushroomFeatureRenderer<T extends MooshroomEntity>
extends FeatureRenderer<T, CowEntityModel<T>> {
    public MooshroomMushroomFeatureRenderer(FeatureRendererContext<T, CowEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((PassiveEntity)t).isBaby() || ((Entity)t).isInvisible()) {
            return;
        }
        BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
        BlockState _snowman2 = ((MooshroomEntity)t).getMooshroomType().getMushroomState();
        int _snowman3 = LivingEntityRenderer.getOverlay(t, 0.0f);
        matrixStack.push();
        matrixStack.translate(0.2f, -0.35f, 0.5);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(-0.5, -0.5, -0.5);
        blockRenderManager.renderBlockAsEntity(_snowman2, matrixStack, vertexConsumerProvider, n, _snowman3);
        matrixStack.pop();
        matrixStack.push();
        matrixStack.translate(0.2f, -0.35f, 0.5);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(42.0f));
        matrixStack.translate(0.1f, 0.0, -0.6f);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-48.0f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(-0.5, -0.5, -0.5);
        blockRenderManager.renderBlockAsEntity(_snowman2, matrixStack, vertexConsumerProvider, n, _snowman3);
        matrixStack.pop();
        matrixStack.push();
        ((CowEntityModel)this.getContextModel()).getHead().rotate(matrixStack);
        matrixStack.translate(0.0, -0.7f, -0.2f);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-78.0f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(-0.5, -0.5, -0.5);
        blockRenderManager.renderBlockAsEntity(_snowman2, matrixStack, vertexConsumerProvider, n, _snowman3);
        matrixStack.pop();
    }
}

