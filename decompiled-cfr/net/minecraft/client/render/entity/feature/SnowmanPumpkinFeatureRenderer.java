/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.item.ItemStack;

public class SnowmanPumpkinFeatureRenderer
extends FeatureRenderer<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> {
    public SnowmanPumpkinFeatureRenderer(FeatureRendererContext<SnowGolemEntity, SnowGolemEntityModel<SnowGolemEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, SnowGolemEntity snowGolemEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (snowGolemEntity.isInvisible() || !snowGolemEntity.hasPumpkin()) {
            return;
        }
        matrixStack.push();
        ((SnowGolemEntityModel)this.getContextModel()).getTopSnowball().rotate(matrixStack);
        _snowman = 0.625f;
        matrixStack.translate(0.0, -0.34375, 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        matrixStack.scale(0.625f, -0.625f, -0.625f);
        ItemStack itemStack = new ItemStack(Blocks.CARVED_PUMPKIN);
        MinecraftClient.getInstance().getItemRenderer().renderItem(snowGolemEntity, itemStack, ModelTransformation.Mode.HEAD, false, matrixStack, vertexConsumerProvider, snowGolemEntity.world, n, LivingEntityRenderer.getOverlay(snowGolemEntity, 0.0f));
        matrixStack.pop();
    }
}

