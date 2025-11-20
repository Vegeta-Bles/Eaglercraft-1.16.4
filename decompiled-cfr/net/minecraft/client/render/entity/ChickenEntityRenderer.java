/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ChickenEntityRenderer
extends MobEntityRenderer<ChickenEntity, ChickenEntityModel<ChickenEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/chicken.png");

    public ChickenEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ChickenEntityModel(), 0.3f);
    }

    @Override
    public Identifier getTexture(ChickenEntity chickenEntity) {
        return TEXTURE;
    }

    @Override
    protected float getAnimationProgress(ChickenEntity chickenEntity, float f) {
        _snowman = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
        _snowman = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.maxWingDeviation);
        return (MathHelper.sin(_snowman) + 1.0f) * _snowman;
    }
}

