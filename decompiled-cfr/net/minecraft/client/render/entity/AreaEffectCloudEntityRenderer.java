/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.util.Identifier;

public class AreaEffectCloudEntityRenderer
extends EntityRenderer<AreaEffectCloudEntity> {
    public AreaEffectCloudEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(AreaEffectCloudEntity areaEffectCloudEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

