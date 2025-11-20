/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.util.Identifier;

public class SpectralArrowEntityRenderer
extends ProjectileEntityRenderer<SpectralArrowEntity> {
    public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/spectral_arrow.png");

    public SpectralArrowEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(SpectralArrowEntity spectralArrowEntity) {
        return TEXTURE;
    }
}

