/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.PhantomEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.PhantomEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.util.Identifier;

public class PhantomEntityRenderer
extends MobEntityRenderer<PhantomEntity, PhantomEntityModel<PhantomEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/phantom.png");

    public PhantomEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new PhantomEntityModel(), 0.75f);
        this.addFeature(new PhantomEyesFeatureRenderer<PhantomEntity>(this));
    }

    @Override
    public Identifier getTexture(PhantomEntity phantomEntity) {
        return TEXTURE;
    }

    @Override
    protected void scale(PhantomEntity phantomEntity, MatrixStack matrixStack, float f) {
        int n = phantomEntity.getPhantomSize();
        float _snowman2 = 1.0f + 0.15f * (float)n;
        matrixStack.scale(_snowman2, _snowman2, _snowman2);
        matrixStack.translate(0.0, 1.3125, 0.1875);
    }

    @Override
    protected void setupTransforms(PhantomEntity phantomEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(phantomEntity, matrixStack, f, f2, f3);
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(phantomEntity.pitch));
    }
}

