/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MinecartEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.util.math.MathHelper;

public class TntMinecartEntityRenderer
extends MinecartEntityRenderer<TntMinecartEntity> {
    public TntMinecartEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected void renderBlock(TntMinecartEntity tntMinecartEntity, float f, BlockState blockState2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        BlockState blockState2;
        _snowman = tntMinecartEntity.getFuseTicks();
        if (_snowman > -1 && (float)_snowman - f + 1.0f < 10.0f) {
            float f2 = 1.0f - ((float)_snowman - f + 1.0f) / 10.0f;
            f2 = MathHelper.clamp(f2, 0.0f, 1.0f);
            f2 *= f2;
            f2 *= f2;
            _snowman = 1.0f + f2 * 0.3f;
            matrixStack.scale(_snowman, _snowman, _snowman);
        }
        TntMinecartEntityRenderer.renderFlashingBlock(blockState2, matrixStack, vertexConsumerProvider, n, _snowman > -1 && _snowman / 5 % 2 == 0);
    }

    public static void renderFlashingBlock(BlockState blockState, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean drawFlash) {
        int n = drawFlash ? OverlayTexture.packUv(OverlayTexture.getU(1.0f), 10) : OverlayTexture.DEFAULT_UV;
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, matrices, vertexConsumers, light, n);
    }
}

