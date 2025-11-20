/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.StrayOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.SkeletonEntityModel;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class StrayEntityRenderer
extends SkeletonEntityRenderer {
    private static final Identifier TEXTURE = new Identifier("textures/entity/skeleton/stray.png");

    public StrayEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.addFeature(new StrayOverlayFeatureRenderer<AbstractSkeletonEntity, SkeletonEntityModel<AbstractSkeletonEntity>>(this));
    }

    @Override
    public Identifier getTexture(AbstractSkeletonEntity abstractSkeletonEntity) {
        return TEXTURE;
    }
}

