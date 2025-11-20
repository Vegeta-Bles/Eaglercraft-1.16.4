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
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public abstract class EnergySwirlOverlayFeatureRenderer<T extends Entity, M extends EntityModel<T>>
extends FeatureRenderer<T, M> {
    public EnergySwirlOverlayFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!((SkinOverlayOwner)entity).shouldRenderOverlay()) {
            return;
        }
        float f = (float)((Entity)entity).age + tickDelta;
        EntityModel<T> _snowman2 = this.getEnergySwirlModel();
        _snowman2.animateModel(entity, limbAngle, limbDistance, tickDelta);
        ((EntityModel)this.getContextModel()).copyStateTo(_snowman2);
        VertexConsumer _snowman3 = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(f), f * 0.01f));
        _snowman2.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        _snowman2.render(matrices, _snowman3, light, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1.0f);
    }

    protected abstract float getEnergySwirlX(float var1);

    protected abstract Identifier getEnergySwirlTexture();

    protected abstract EntityModel<T> getEnergySwirlModel();
}

