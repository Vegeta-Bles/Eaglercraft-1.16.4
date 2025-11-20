/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.TropicalFishColorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.minecraft.client.render.entity.model.SmallTropicalFishEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TropicalFishEntityRenderer
extends MobEntityRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
    private final SmallTropicalFishEntityModel<TropicalFishEntity> smallModel = new SmallTropicalFishEntityModel(0.0f);
    private final LargeTropicalFishEntityModel<TropicalFishEntity> largeModel = new LargeTropicalFishEntityModel(0.0f);

    public TropicalFishEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SmallTropicalFishEntityModel(0.0f), 0.15f);
        this.addFeature(new TropicalFishColorFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(TropicalFishEntity tropicalFishEntity) {
        return tropicalFishEntity.getShapeId();
    }

    @Override
    public void render(TropicalFishEntity tropicalFishEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        this.model = _snowman = tropicalFishEntity.getShape() == 0 ? this.smallModel : this.largeModel;
        float[] fArray = tropicalFishEntity.getBaseColorComponents();
        _snowman.setColorMultiplier(fArray[0], fArray[1], fArray[2]);
        super.render(tropicalFishEntity, f, f2, matrixStack, vertexConsumerProvider, n);
        _snowman.setColorMultiplier(1.0f, 1.0f, 1.0f);
    }

    @Override
    protected void setupTransforms(TropicalFishEntity tropicalFishEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(tropicalFishEntity, matrixStack, f, f2, f3);
        _snowman = 4.3f * MathHelper.sin(0.6f * f);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        if (!tropicalFishEntity.isTouchingWater()) {
            matrixStack.translate(0.2f, 0.1f, 0.0);
            matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
    }
}

