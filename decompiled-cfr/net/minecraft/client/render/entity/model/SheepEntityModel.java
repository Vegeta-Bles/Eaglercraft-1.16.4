/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.SheepEntity;

public class SheepEntityModel<T extends SheepEntity>
extends QuadrupedEntityModel<T> {
    private float headPitchModifier;

    public SheepEntityModel() {
        super(12, 0.0f, false, 8.0f, 4.0f, 2.0f, 2.0f, 24);
        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0f, -4.0f, -6.0f, 6.0f, 6.0f, 8.0f, 0.0f);
        this.head.setPivot(0.0f, 6.0f, -8.0f);
        this.torso = new ModelPart(this, 28, 8);
        this.torso.addCuboid(-4.0f, -10.0f, -7.0f, 8.0f, 16.0f, 6.0f, 0.0f);
        this.torso.setPivot(0.0f, 5.0f, 2.0f);
    }

    @Override
    public void animateModel(T t, float f, float f2, float f3) {
        super.animateModel(t, f, f2, f3);
        this.head.pivotY = 6.0f + ((SheepEntity)t).getNeckAngle(f3) * 9.0f;
        this.headPitchModifier = ((SheepEntity)t).getHeadAngle(f3);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        this.head.pitch = this.headPitchModifier;
    }
}

