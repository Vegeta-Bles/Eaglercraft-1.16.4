/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.util.Identifier;

public class DrownedOverlayFeatureRenderer<T extends DrownedEntity>
extends FeatureRenderer<T, DrownedEntityModel<T>> {
    private static final Identifier SKIN = new Identifier("textures/entity/zombie/drowned_outer_layer.png");
    private final DrownedEntityModel<T> model = new DrownedEntityModel(0.25f, 0.0f, 64, 64);

    public DrownedOverlayFeatureRenderer(FeatureRendererContext<T, DrownedEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        DrownedOverlayFeatureRenderer.render(this.getContextModel(), this.model, SKIN, matrixStack, vertexConsumerProvider, n, t, f, f2, f4, f5, f6, f3, 1.0f, 1.0f, 1.0f);
    }
}

