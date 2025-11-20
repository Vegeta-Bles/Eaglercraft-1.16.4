/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.Identifier;

public class CowEntityRenderer
extends MobEntityRenderer<CowEntity, CowEntityModel<CowEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/cow/cow.png");

    public CowEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CowEntityModel(), 0.7f);
    }

    @Override
    public Identifier getTexture(CowEntity cowEntity) {
        return TEXTURE;
    }
}

