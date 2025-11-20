/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.entity.passive.AbstractDonkeyEntity;

public class DonkeyEntityModel<T extends AbstractDonkeyEntity>
extends HorseEntityModel<T> {
    private final ModelPart leftChest = new ModelPart(this, 26, 21);
    private final ModelPart rightChest;

    public DonkeyEntityModel(float f) {
        super(f);
        this.leftChest.addCuboid(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        this.rightChest = new ModelPart(this, 26, 21);
        this.rightChest.addCuboid(-4.0f, 0.0f, -2.0f, 8.0f, 8.0f, 3.0f);
        this.leftChest.yaw = -1.5707964f;
        this.rightChest.yaw = 1.5707964f;
        this.leftChest.setPivot(6.0f, -8.0f, 0.0f);
        this.rightChest.setPivot(-6.0f, -8.0f, 0.0f);
        this.torso.addChild(this.leftChest);
        this.torso.addChild(this.rightChest);
    }

    @Override
    protected void method_2789(ModelPart modelPart) {
        _snowman = new ModelPart(this, 0, 12);
        _snowman.addCuboid(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        _snowman.setPivot(1.25f, -10.0f, 4.0f);
        _snowman = new ModelPart(this, 0, 12);
        _snowman.addCuboid(-1.0f, -7.0f, 0.0f, 2.0f, 7.0f, 1.0f);
        _snowman.setPivot(-1.25f, -10.0f, 4.0f);
        _snowman.pitch = 0.2617994f;
        _snowman.roll = 0.2617994f;
        _snowman.pitch = 0.2617994f;
        _snowman.roll = -0.2617994f;
        modelPart.addChild(_snowman);
        modelPart.addChild(_snowman);
    }

    @Override
    public void setAngles(T t, float f, float f2, float f3, float f4, float f5) {
        super.setAngles(t, f, f2, f3, f4, f5);
        if (((AbstractDonkeyEntity)t).hasChest()) {
            this.leftChest.visible = true;
            this.rightChest.visible = true;
        } else {
            this.leftChest.visible = false;
            this.rightChest.visible = false;
        }
    }
}

