/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.DolphinEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class DolphinHeldItemFeatureRenderer
extends FeatureRenderer<DolphinEntity, DolphinEntityModel<DolphinEntity>> {
    public DolphinHeldItemFeatureRenderer(FeatureRendererContext<DolphinEntity, DolphinEntityModel<DolphinEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, DolphinEntity dolphinEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean bl = dolphinEntity.getMainArm() == Arm.RIGHT;
        matrixStack.push();
        float _snowman2 = 1.0f;
        float _snowman3 = -1.0f;
        float _snowman4 = MathHelper.abs(dolphinEntity.pitch) / 60.0f;
        if (dolphinEntity.pitch < 0.0f) {
            matrixStack.translate(0.0, 1.0f - _snowman4 * 0.5f, -1.0f + _snowman4 * 0.5f);
        } else {
            matrixStack.translate(0.0, 1.0f + _snowman4 * 0.8f, -1.0f + _snowman4 * 0.2f);
        }
        ItemStack _snowman5 = bl ? dolphinEntity.getMainHandStack() : dolphinEntity.getOffHandStack();
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(dolphinEntity, _snowman5, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, n);
        matrixStack.pop();
    }
}

