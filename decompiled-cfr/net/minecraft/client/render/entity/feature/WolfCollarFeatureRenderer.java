/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class WolfCollarFeatureRenderer
extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    private static final Identifier SKIN = new Identifier("textures/entity/wolf/wolf_collar.png");

    public WolfCollarFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, WolfEntity wolfEntity, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!wolfEntity.isTamed() || wolfEntity.isInvisible()) {
            return;
        }
        float[] fArray = wolfEntity.getCollarColor().getColorComponents();
        WolfCollarFeatureRenderer.renderModel(this.getContextModel(), SKIN, matrixStack, vertexConsumerProvider, n, wolfEntity, fArray[0], fArray[1], fArray[2]);
    }
}

