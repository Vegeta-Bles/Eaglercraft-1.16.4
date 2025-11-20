/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class ShulkerHeadFeatureRenderer
extends FeatureRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
    public ShulkerHeadFeatureRenderer(FeatureRendererContext<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, ShulkerEntity shulkerEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        matrixStack.push();
        matrixStack.translate(0.0, 1.0, 0.0);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        Quaternion quaternion = shulkerEntity.getAttachedFace().getOpposite().getRotationQuaternion();
        quaternion.conjugate();
        matrixStack.multiply(quaternion);
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.0, 0.0);
        DyeColor _snowman2 = shulkerEntity.getColor();
        Identifier _snowman3 = _snowman2 == null ? ShulkerEntityRenderer.TEXTURE : ShulkerEntityRenderer.COLORED_TEXTURES[_snowman2.getId()];
        VertexConsumer _snowman4 = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(_snowman3));
        ((ShulkerEntityModel)this.getContextModel()).getHead().render(matrixStack, _snowman4, n, LivingEntityRenderer.getOverlay(shulkerEntity, 0.0f));
        matrixStack.pop();
    }
}

