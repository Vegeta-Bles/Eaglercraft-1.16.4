/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.render.entity.model.AbstractZombieModel;
import net.minecraft.entity.mob.GiantEntity;

public class GiantEntityModel
extends AbstractZombieModel<GiantEntity> {
    public GiantEntityModel() {
        this(0.0f, false);
    }

    public GiantEntityModel(float scale, boolean bl) {
        super(scale, 0.0f, 64, bl ? 32 : 64);
    }

    @Override
    public boolean isAttacking(GiantEntity giantEntity) {
        return false;
    }
}

