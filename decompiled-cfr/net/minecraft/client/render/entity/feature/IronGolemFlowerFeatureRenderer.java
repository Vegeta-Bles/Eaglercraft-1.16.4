/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;

public class IronGolemFlowerFeatureRenderer
extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
    public IronGolemFlowerFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, IronGolemEntity ironGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (ironGolemEntity.getLookingAtVillagerTicks() == 0) {
            return;
        }
        matrixStack.push();
        ModelPart modelPart = ((IronGolemEntityModel)this.getContextModel()).getRightArm();
        modelPart.rotate(matrixStack);
        matrixStack.translate(-1.1875, 1.0625, -0.9375);
        matrixStack.translate(0.5, 0.5, 0.5);
        float _snowman2 = 0.5f;
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0f));
        matrixStack.translate(-0.5, -0.5, -0.5);
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.POPPY.getDefaultState(), matrixStack, vertexConsumerProvider, n, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }
}

