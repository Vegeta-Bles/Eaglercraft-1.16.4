/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public abstract class ZombieBaseEntityRenderer<T extends ZombieEntity, M extends ZombieEntityModel<T>>
extends BipedEntityRenderer<T, M> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/zombie.png");

    protected ZombieBaseEntityRenderer(EntityRenderDispatcher dispatcher, M m, M m2, M m3) {
        super(dispatcher, m, 0.5f);
        this.addFeature(new ArmorFeatureRenderer(this, m2, m3));
    }

    @Override
    public Identifier getTexture(ZombieEntity zombieEntity) {
        return TEXTURE;
    }

    @Override
    protected boolean isShaking(T t) {
        return ((ZombieEntity)t).isConvertingInWater();
    }

    @Override
    protected /* synthetic */ boolean isShaking(LivingEntity entity) {
        return this.isShaking((T)((ZombieEntity)entity));
    }
}

