/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class WitchHeldItemFeatureRenderer<T extends LivingEntity>
extends VillagerHeldItemFeatureRenderer<T, WitchEntityModel<T>> {
    public WitchHeldItemFeatureRenderer(FeatureRendererContext<T, WitchEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = ((LivingEntity)t).getMainHandStack();
        matrixStack.push();
        if (itemStack.getItem() == Items.POTION) {
            ((WitchEntityModel)this.getContextModel()).getHead().rotate(matrixStack);
            ((WitchEntityModel)this.getContextModel()).getNose().rotate(matrixStack);
            matrixStack.translate(0.0625, 0.25, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
            matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(140.0f));
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(10.0f));
            matrixStack.translate(0.0, -0.4f, 0.4f);
        }
        super.render(matrixStack, vertexConsumerProvider, n, t, f, f2, f3, f4, f5, f6);
        matrixStack.pop();
    }
}

