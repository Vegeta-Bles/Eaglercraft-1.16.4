/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.SmallTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.TintableCompositeModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TropicalFishEntity;

public class TropicalFishColorFeatureRenderer
extends FeatureRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
    private final SmallTropicalFishEntityModel<TropicalFishEntity> smallModel = new SmallTropicalFishEntityModel(0.008f);
    private final LargeTropicalFishEntityModel<TropicalFishEntity> largeModel = new LargeTropicalFishEntityModel(0.008f);

    public TropicalFishColorFeatureRenderer(FeatureRendererContext<TropicalFishEntity, EntityModel<TropicalFishEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, TropicalFishEntity tropicalFishEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        TintableCompositeModel tintableCompositeModel = tropicalFishEntity.getShape() == 0 ? this.smallModel : this.largeModel;
        float[] _snowman2 = tropicalFishEntity.getPatternColorComponents();
        TropicalFishColorFeatureRenderer.render(this.getContextModel(), tintableCompositeModel, tropicalFishEntity.getVarietyId(), matrixStack, vertexConsumerProvider, n, tropicalFishEntity, f, f2, f4, f5, f6, f3, _snowman2[0], _snowman2[1], _snowman2[2]);
    }
}

