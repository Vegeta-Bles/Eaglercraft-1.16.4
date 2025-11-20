/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class VillagerHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>>
extends FeatureRenderer<T, M> {
    public VillagerHeldItemFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.4f, -0.4f);
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180.0f));
        ItemStack itemStack = ((LivingEntity)t).getEquippedStack(EquipmentSlot.MAINHAND);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem((LivingEntity)t, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, n);
        matrixStack.pop();
    }
}

