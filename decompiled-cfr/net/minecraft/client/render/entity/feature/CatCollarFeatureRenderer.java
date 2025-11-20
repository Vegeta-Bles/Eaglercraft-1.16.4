/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;

public class CatCollarFeatureRenderer
extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/cat/cat_collar.png");
    private final CatEntityModel<CatEntity> model = new CatEntityModel(0.01f);

    public CatCollarFeatureRenderer(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, CatEntity catEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!catEntity.isTamed()) {
            return;
        }
        float[] fArray = catEntity.getCollarColor().getColorComponents();
        CatCollarFeatureRenderer.render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, n, catEntity, f, f2, f4, f5, f6, f3, fArray[0], fArray[1], fArray[2]);
    }
}

