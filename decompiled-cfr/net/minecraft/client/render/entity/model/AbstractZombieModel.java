/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.entity.mob.HostileEntity;

public abstract class AbstractZombieModel<T extends HostileEntity>
extends BipedEntityModel<T> {
    protected AbstractZombieModel(float f, float f2, int n, int n2) {
        super(f, f2, n, n2);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        CrossbowPosing.method_29352(this.leftArm, this.rightArm, this.isAttacking(t), this.handSwingProgress, f3);
    }

    public abstract boolean isAttacking(T var1);
}

