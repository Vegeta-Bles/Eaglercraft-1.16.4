/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.util.Identifier;

public class VindicatorEntityRenderer
extends IllagerEntityRenderer<VindicatorEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/illager/vindicator.png");

    public VindicatorEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IllagerEntityModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<VindicatorEntity, IllagerEntityModel<VindicatorEntity>>((FeatureRendererContext)this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, VindicatorEntity vindicatorEntity, float f, float f2, float f3, float f4, float f5, float f6) {
                if (vindicatorEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, n, vindicatorEntity, f, f2, f3, f4, f5, f6);
                }
            }
        });
    }

    @Override
    public Identifier getTexture(VindicatorEntity vindicatorEntity) {
        return TEXTURE;
    }
}

