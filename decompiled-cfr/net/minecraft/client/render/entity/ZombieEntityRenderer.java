/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.mob.ZombieEntity;

public class ZombieEntityRenderer
extends ZombieBaseEntityRenderer<ZombieEntity, ZombieEntityModel<ZombieEntity>> {
    public ZombieEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ZombieEntityModel(0.0f, false), new ZombieEntityModel(0.5f, true), new ZombieEntityModel(1.0f, true));
    }
}

