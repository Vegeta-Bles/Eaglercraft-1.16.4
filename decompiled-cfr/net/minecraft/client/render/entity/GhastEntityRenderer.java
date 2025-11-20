/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.GhastEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.Identifier;

public class GhastEntityRenderer
extends MobEntityRenderer<GhastEntity, GhastEntityModel<GhastEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/ghast/ghast.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier("textures/entity/ghast/ghast_shooting.png");

    public GhastEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new GhastEntityModel(), 1.5f);
    }

    @Override
    public Identifier getTexture(GhastEntity ghastEntity) {
        if (ghastEntity.isShooting()) {
            return ANGRY_TEXTURE;
        }
        return TEXTURE;
    }

    @Override
    protected void scale(GhastEntity ghastEntity, MatrixStack matrixStack, float f) {
        _snowman = 1.0f;
        _snowman = 4.5f;
        _snowman = 4.5f;
        matrixStack.scale(4.5f, 4.5f, 4.5f);
    }
}

