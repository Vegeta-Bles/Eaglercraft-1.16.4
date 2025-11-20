/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ParrotEntityModel;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ParrotEntityRenderer
extends MobEntityRenderer<ParrotEntity, ParrotEntityModel> {
    public static final Identifier[] TEXTURES = new Identifier[]{new Identifier("textures/entity/parrot/parrot_red_blue.png"), new Identifier("textures/entity/parrot/parrot_blue.png"), new Identifier("textures/entity/parrot/parrot_green.png"), new Identifier("textures/entity/parrot/parrot_yellow_blue.png"), new Identifier("textures/entity/parrot/parrot_grey.png")};

    public ParrotEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ParrotEntityModel(), 0.3f);
    }

    @Override
    public Identifier getTexture(ParrotEntity parrotEntity) {
        return TEXTURES[parrotEntity.getVariant()];
    }

    @Override
    public float getAnimationProgress(ParrotEntity parrotEntity, float f) {
        _snowman = MathHelper.lerp(f, parrotEntity.prevFlapProgress, parrotEntity.flapProgress);
        _snowman = MathHelper.lerp(f, parrotEntity.prevMaxWingDeviation, parrotEntity.maxWingDeviation);
        return (MathHelper.sin(_snowman) + 1.0f) * _snowman;
    }
}

