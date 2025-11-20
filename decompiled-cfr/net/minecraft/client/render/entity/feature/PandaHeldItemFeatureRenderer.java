/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class PandaHeldItemFeatureRenderer
extends FeatureRenderer<PandaEntity, PandaEntityModel<PandaEntity>> {
    public PandaHeldItemFeatureRenderer(FeatureRendererContext<PandaEntity, PandaEntityModel<PandaEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, PandaEntity pandaEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = pandaEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        if (!pandaEntity.isScared() || pandaEntity.isScaredByThunderstorm()) {
            return;
        }
        float _snowman2 = -0.6f;
        float _snowman3 = 1.4f;
        if (pandaEntity.isEating()) {
            _snowman2 -= 0.2f * MathHelper.sin(f4 * 0.6f) + 0.2f;
            _snowman3 -= 0.09f * MathHelper.sin(f4 * 0.6f);
        }
        matrixStack.push();
        matrixStack.translate(0.1f, _snowman3, _snowman2);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(pandaEntity, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, n);
        matrixStack.pop();
    }
}

