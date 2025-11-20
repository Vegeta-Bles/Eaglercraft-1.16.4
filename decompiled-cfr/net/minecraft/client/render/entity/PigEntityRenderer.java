/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.Identifier;

public class PigEntityRenderer
extends MobEntityRenderer<PigEntity, PigEntityModel<PigEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/pig/pig.png");

    public PigEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new PigEntityModel(), 0.7f);
        this.addFeature(new SaddleFeatureRenderer(this, new PigEntityModel(0.5f), new Identifier("textures/entity/pig/pig_saddle.png")));
    }

    @Override
    public Identifier getTexture(PigEntity pigEntity) {
        return TEXTURE;
    }
}

