/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

public class EvokerEntityRenderer<T extends SpellcastingIllagerEntity>
extends IllagerEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/illager/evoker.png");

    public EvokerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IllagerEntityModel(0.0f, 0.0f, 64, 64), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<T, IllagerEntityModel<T>>(this){

            @Override
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
                if (((SpellcastingIllagerEntity)t).isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, n, t, f, f2, f3, f4, f5, f6);
                }
            }
        });
    }

    @Override
    public Identifier getTexture(T t) {
        return TEXTURE;
    }
}

