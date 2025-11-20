/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class CapeFeatureRenderer
extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public CapeFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!abstractClientPlayerEntity.canRenderCapeTexture() || abstractClientPlayerEntity.isInvisible() || !abstractClientPlayerEntity.isPartVisible(PlayerModelPart.CAPE) || abstractClientPlayerEntity.getCapeTexture() == null) {
            return;
        }
        ItemStack itemStack = abstractClientPlayerEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.getItem() == Items.ELYTRA) {
            return;
        }
        matrixStack.push();
        matrixStack.translate(0.0, 0.0, 0.125);
        double _snowman2 = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevCapeX, abstractClientPlayerEntity.capeX) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevX, abstractClientPlayerEntity.getX());
        double _snowman3 = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevCapeY, abstractClientPlayerEntity.capeY) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevY, abstractClientPlayerEntity.getY());
        double _snowman4 = MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevCapeZ, abstractClientPlayerEntity.capeZ) - MathHelper.lerp((double)f3, abstractClientPlayerEntity.prevZ, abstractClientPlayerEntity.getZ());
        float _snowman5 = abstractClientPlayerEntity.prevBodyYaw + (abstractClientPlayerEntity.bodyYaw - abstractClientPlayerEntity.prevBodyYaw);
        double _snowman6 = MathHelper.sin(_snowman5 * ((float)Math.PI / 180));
        double _snowman7 = -MathHelper.cos(_snowman5 * ((float)Math.PI / 180));
        float _snowman8 = (float)_snowman3 * 10.0f;
        _snowman8 = MathHelper.clamp(_snowman8, -6.0f, 32.0f);
        float _snowman9 = (float)(_snowman2 * _snowman6 + _snowman4 * _snowman7) * 100.0f;
        _snowman9 = MathHelper.clamp(_snowman9, 0.0f, 150.0f);
        float _snowman10 = (float)(_snowman2 * _snowman7 - _snowman4 * _snowman6) * 100.0f;
        _snowman10 = MathHelper.clamp(_snowman10, -20.0f, 20.0f);
        if (_snowman9 < 0.0f) {
            _snowman9 = 0.0f;
        }
        float _snowman11 = MathHelper.lerp(f3, abstractClientPlayerEntity.prevStrideDistance, abstractClientPlayerEntity.strideDistance);
        _snowman8 += MathHelper.sin(MathHelper.lerp(f3, abstractClientPlayerEntity.prevHorizontalSpeed, abstractClientPlayerEntity.horizontalSpeed) * 6.0f) * 32.0f * _snowman11;
        if (abstractClientPlayerEntity.isInSneakingPose()) {
            _snowman8 += 25.0f;
        }
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(6.0f + _snowman9 / 2.0f + _snowman8));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(_snowman10 / 2.0f));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - _snowman10 / 2.0f));
        VertexConsumer _snowman12 = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(abstractClientPlayerEntity.getCapeTexture()));
        ((PlayerEntityModel)this.getContextModel()).renderCape(matrixStack, _snowman12, n, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }
}

