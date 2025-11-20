/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;

public class ZombieEntityModel<T extends ZombieEntity>
extends AbstractZombieModel<T> {
    public ZombieEntityModel(float scale, boolean bl) {
        this(scale, 0.0f, 64, bl ? 32 : 64);
    }

    protected ZombieEntityModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
    }

    @Override
    public boolean isAttacking(T t) {
        return ((MobEntity)t).isAttacking();
    }
}

