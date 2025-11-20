/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.math.MathHelper;

public class RavagerEntityModel
extends CompositeEntityModel<RavagerEntity> {
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart torso;
    private final ModelPart rightBackLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart neck;

    public RavagerEntityModel() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        int n = 16;
        float _snowman2 = 0.0f;
        this.neck = new ModelPart(this);
        this.neck.setPivot(0.0f, -7.0f, -1.5f);
        this.neck.setTextureOffset(68, 73).addCuboid(-5.0f, -1.0f, -18.0f, 10.0f, 10.0f, 18.0f, 0.0f);
        this.head = new ModelPart(this);
        this.head.setPivot(0.0f, 16.0f, -17.0f);
        this.head.setTextureOffset(0, 0).addCuboid(-8.0f, -20.0f, -14.0f, 16.0f, 20.0f, 16.0f, 0.0f);
        this.head.setTextureOffset(0, 0).addCuboid(-2.0f, -6.0f, -18.0f, 4.0f, 8.0f, 4.0f, 0.0f);
        ModelPart _snowman3 = new ModelPart(this);
        _snowman3.setPivot(-10.0f, -14.0f, -8.0f);
        _snowman3.setTextureOffset(74, 55).addCuboid(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f, 0.0f);
        _snowman3.pitch = 1.0995574f;
        this.head.addChild(_snowman3);
        ModelPart _snowman4 = new ModelPart(this);
        _snowman4.mirror = true;
        _snowman4.setPivot(8.0f, -14.0f, -8.0f);
        _snowman4.setTextureOffset(74, 55).addCuboid(0.0f, -14.0f, -2.0f, 2.0f, 14.0f, 4.0f, 0.0f);
        _snowman4.pitch = 1.0995574f;
        this.head.addChild(_snowman4);
        this.jaw = new ModelPart(this);
        this.jaw.setPivot(0.0f, -2.0f, 2.0f);
        this.jaw.setTextureOffset(0, 36).addCuboid(-8.0f, 0.0f, -16.0f, 16.0f, 3.0f, 16.0f, 0.0f);
        this.head.addChild(this.jaw);
        this.neck.addChild(this.head);
        this.torso = new ModelPart(this);
        this.torso.setTextureOffset(0, 55).addCuboid(-7.0f, -10.0f, -7.0f, 14.0f, 16.0f, 20.0f, 0.0f);
        this.torso.setTextureOffset(0, 91).addCuboid(-6.0f, 6.0f, -7.0f, 12.0f, 13.0f, 18.0f, 0.0f);
        this.torso.setPivot(0.0f, 1.0f, 2.0f);
        this.rightBackLeg = new ModelPart(this, 96, 0);
        this.rightBackLeg.addCuboid(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.rightBackLeg.setPivot(-8.0f, -13.0f, 18.0f);
        this.leftBackLeg = new ModelPart(this, 96, 0);
        this.leftBackLeg.mirror = true;
        this.leftBackLeg.addCuboid(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.leftBackLeg.setPivot(8.0f, -13.0f, 18.0f);
        this.rightFrontLeg = new ModelPart(this, 64, 0);
        this.rightFrontLeg.addCuboid(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.rightFrontLeg.setPivot(-8.0f, -13.0f, -5.0f);
        this.leftFrontLeg = new ModelPart(this, 64, 0);
        this.leftFrontLeg.mirror = true;
        this.leftFrontLeg.addCuboid(-4.0f, 0.0f, -4.0f, 8.0f, 37.0f, 8.0f, 0.0f);
        this.leftFrontLeg.setPivot(8.0f, -13.0f, -5.0f);
    }

    @Override
    public Iterable<ModelPart> getParts() {
        return ImmutableList.of((Object)this.neck, (Object)this.torso, (Object)this.rightBackLeg, (Object)this.leftBackLeg, (Object)this.rightFrontLeg, (Object)this.leftFrontLeg);
    }

    @Override
    public void setAngles(RavagerEntity ravagerEntity, float f, float f2, float f3, float f4, float f5) {
        this.head.pitch = f5 * ((float)Math.PI / 180);
        this.head.yaw = f4 * ((float)Math.PI / 180);
        this.torso.pitch = 1.5707964f;
        _snowman = 0.4f * f2;
        this.rightBackLeg.pitch = MathHelper.cos(f * 0.6662f) * _snowman;
        this.leftBackLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * _snowman;
        this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662f + (float)Math.PI) * _snowman;
        this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662f) * _snowman;
    }

    @Override
    public void animateModel(RavagerEntity ravagerEntity, float f, float f2, float f3) {
        super.animateModel(ravagerEntity, f, f2, f3);
        int n = ravagerEntity.getStunTick();
        n2 = ravagerEntity.getRoarTick();
        _snowman = 20;
        _snowman = ravagerEntity.getAttackTick();
        _snowman = 10;
        if (_snowman > 0) {
            float f4 = MathHelper.method_24504((float)_snowman - f3, 10.0f);
            _snowman = (1.0f + f4) * 0.5f;
            _snowman = _snowman * _snowman * _snowman * 12.0f;
            _snowman = _snowman * MathHelper.sin(this.neck.pitch);
            this.neck.pivotZ = -6.5f + _snowman;
            this.neck.pivotY = -7.0f - _snowman;
            _snowman = MathHelper.sin(((float)_snowman - f3) / 10.0f * (float)Math.PI * 0.25f);
            this.jaw.pitch = 1.5707964f * _snowman;
            this.jaw.pitch = _snowman > 5 ? MathHelper.sin(((float)(-4 + _snowman) - f3) / 4.0f) * (float)Math.PI * 0.4f : 0.15707964f * MathHelper.sin((float)Math.PI * ((float)_snowman - f3) / 10.0f);
        } else {
            int n2;
            float f5 = -1.0f;
            _snowman = -1.0f * MathHelper.sin(this.neck.pitch);
            this.neck.pivotX = 0.0f;
            this.neck.pivotY = -7.0f - _snowman;
            this.neck.pivotZ = 5.5f;
            boolean _snowman2 = n > 0;
            this.neck.pitch = _snowman2 ? 0.21991149f : 0.0f;
            this.jaw.pitch = (float)Math.PI * (_snowman2 ? 0.05f : 0.01f);
            if (_snowman2) {
                double d = (double)n / 40.0;
                this.neck.pivotX = (float)Math.sin(d * 10.0) * 3.0f;
            } else if (n2 > 0) {
                float f6 = MathHelper.sin(((float)(20 - n2) - f3) / 20.0f * (float)Math.PI * 0.25f);
                this.jaw.pitch = 1.5707964f * f6;
            }
        }
    }
}

