/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

public class HorseArmorFeatureRenderer
extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
    private final HorseEntityModel<HorseEntity> model = new HorseEntityModel(0.1f);

    public HorseArmorFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, HorseEntity horseEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        float _snowman5;
        float _snowman4;
        float _snowman3;
        ItemStack itemStack = horseEntity.getArmorType();
        if (!(itemStack.getItem() instanceof HorseArmorItem)) {
            return;
        }
        HorseArmorItem _snowman2 = (HorseArmorItem)itemStack.getItem();
        ((HorseEntityModel)this.getContextModel()).copyStateTo(this.model);
        this.model.animateModel(horseEntity, f, f2, f3);
        this.model.setAngles(horseEntity, f, f2, f4, f5, f6);
        if (_snowman2 instanceof DyeableHorseArmorItem) {
            int n2 = ((DyeableHorseArmorItem)_snowman2).getColor(itemStack);
            _snowman3 = (float)(n2 >> 16 & 0xFF) / 255.0f;
            _snowman4 = (float)(n2 >> 8 & 0xFF) / 255.0f;
            _snowman5 = (float)(n2 & 0xFF) / 255.0f;
        } else {
            _snowman3 = 1.0f;
            _snowman4 = 1.0f;
            _snowman5 = 1.0f;
        }
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(_snowman2.getEntityTexture()));
        this.model.render(matrixStack, vertexConsumer, n, OverlayTexture.DEFAULT_UV, _snowman3, _snowman4, _snowman5, 1.0f);
    }
}

