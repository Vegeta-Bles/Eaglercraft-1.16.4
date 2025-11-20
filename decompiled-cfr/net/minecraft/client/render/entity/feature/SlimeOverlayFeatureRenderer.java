/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class SlimeOverlayFeatureRenderer<T extends LivingEntity>
extends FeatureRenderer<T, SlimeEntityModel<T>> {
    private final EntityModel<T> model = new SlimeEntityModel(0);

    public SlimeOverlayFeatureRenderer(FeatureRendererContext<T, SlimeEntityModel<T>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (((Entity)t).isInvisible()) {
            return;
        }
        ((SlimeEntityModel)this.getContextModel()).copyStateTo(this.model);
        this.model.animateModel(t, f, f2, f3);
        this.model.setAngles(t, f, f2, f4, f5, f6);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(t)));
        this.model.render(matrixStack, vertexConsumer, n, LivingEntityRenderer.getOverlay(t, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

