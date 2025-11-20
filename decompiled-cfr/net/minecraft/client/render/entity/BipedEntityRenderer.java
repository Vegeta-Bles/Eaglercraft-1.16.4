/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class BipedEntityRenderer<T extends MobEntity, M extends BipedEntityModel<T>>
extends MobEntityRenderer<T, M> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/steve.png");

    public BipedEntityRenderer(EntityRenderDispatcher dispatcher, M model, float f) {
        this(dispatcher, model, f, 1.0f, 1.0f, 1.0f);
    }

    public BipedEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, M m, float f, float f2, float f3, float f4) {
        super(entityRenderDispatcher, m, f);
        this.addFeature(new HeadFeatureRenderer(this, f2, f3, f4));
        this.addFeature(new ElytraFeatureRenderer(this));
        this.addFeature(new HeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(T t) {
        return TEXTURE;
    }
}

