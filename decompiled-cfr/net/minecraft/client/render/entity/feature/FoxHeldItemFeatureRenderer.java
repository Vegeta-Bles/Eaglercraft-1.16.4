/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;

public class FoxHeldItemFeatureRenderer
extends FeatureRenderer<FoxEntity, FoxEntityModel<FoxEntity>> {
    public FoxHeldItemFeatureRenderer(FeatureRendererContext<FoxEntity, FoxEntityModel<FoxEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n, FoxEntity foxEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        MatrixStack matrixStack2;
        float _snowman2;
        boolean bl = foxEntity.isSleeping();
        _snowman = foxEntity.isBaby();
        matrixStack2.push();
        if (_snowman) {
            _snowman2 = 0.75f;
            matrixStack2.scale(0.75f, 0.75f, 0.75f);
            matrixStack2.translate(0.0, 0.5, 0.209375f);
        }
        matrixStack2.translate(((FoxEntityModel)this.getContextModel()).head.pivotX / 16.0f, ((FoxEntityModel)this.getContextModel()).head.pivotY / 16.0f, ((FoxEntityModel)this.getContextModel()).head.pivotZ / 16.0f);
        _snowman2 = foxEntity.getHeadRoll(f3);
        matrixStack2.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(_snowman2));
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(f5));
        matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(f6));
        if (foxEntity.isBaby()) {
            if (bl) {
                matrixStack2.translate(0.4f, 0.26f, 0.15f);
            } else {
                matrixStack2.translate(0.06f, 0.26f, -0.5);
            }
        } else if (bl) {
            matrixStack2.translate(0.46f, 0.26f, 0.22f);
        } else {
            matrixStack2.translate(0.06f, 0.27f, -0.5);
        }
        matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
        if (bl) {
            matrixStack2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
        ItemStack _snowman3 = foxEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(foxEntity, _snowman3, ModelTransformation.Mode.GROUND, false, matrixStack2, vertexConsumerProvider, n);
        matrixStack2.pop();
    }
}

