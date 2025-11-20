/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class EndGatewayBlockEntityRenderer
extends EndPortalBlockEntityRenderer<EndGatewayBlockEntity> {
    private static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/end_gateway_beam.png");

    public EndGatewayBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(EndGatewayBlockEntity endGatewayBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        if (endGatewayBlockEntity.isRecentlyGenerated() || endGatewayBlockEntity.needsCooldownBeforeTeleporting()) {
            float f2 = endGatewayBlockEntity.isRecentlyGenerated() ? endGatewayBlockEntity.getRecentlyGeneratedBeamHeight(f) : endGatewayBlockEntity.getCooldownBeamHeight(f);
            double _snowman2 = endGatewayBlockEntity.isRecentlyGenerated() ? 256.0 : 50.0;
            f2 = MathHelper.sin(f2 * (float)Math.PI);
            int _snowman3 = MathHelper.floor((double)f2 * _snowman2);
            float[] _snowman4 = endGatewayBlockEntity.isRecentlyGenerated() ? DyeColor.MAGENTA.getColorComponents() : DyeColor.PURPLE.getColorComponents();
            long _snowman5 = endGatewayBlockEntity.getWorld().getTime();
            BeaconBlockEntityRenderer.renderLightBeam(matrixStack, vertexConsumerProvider, BEAM_TEXTURE, f, f2, _snowman5, 0, _snowman3, _snowman4, 0.15f, 0.175f);
            BeaconBlockEntityRenderer.renderLightBeam(matrixStack, vertexConsumerProvider, BEAM_TEXTURE, f, f2, _snowman5, 0, -_snowman3, _snowman4, 0.15f, 0.175f);
        }
        super.render(endGatewayBlockEntity, f, matrixStack, vertexConsumerProvider, n, n2);
    }

    @Override
    protected int method_3592(double d) {
        return super.method_3592(d) + 1;
    }

    @Override
    protected float method_3594() {
        return 1.0f;
    }
}

