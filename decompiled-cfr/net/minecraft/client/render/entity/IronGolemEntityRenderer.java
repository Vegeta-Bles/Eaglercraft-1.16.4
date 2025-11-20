/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.IronGolemCrackFeatureRenderer;
import net.minecraft.client.render.entity.feature.IronGolemFlowerFeatureRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.util.Identifier;

public class IronGolemEntityRenderer
extends MobEntityRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/iron_golem/iron_golem.png");

    public IronGolemEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new IronGolemEntityModel(), 0.7f);
        this.addFeature(new IronGolemCrackFeatureRenderer(this));
        this.addFeature(new IronGolemFlowerFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(IronGolemEntity ironGolemEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(IronGolemEntity ironGolemEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        super.setupTransforms(ironGolemEntity, matrixStack, f, f2, f3);
        if ((double)ironGolemEntity.limbDistance < 0.01) {
            return;
        }
        _snowman = 13.0f;
        _snowman = ironGolemEntity.limbAngle - ironGolemEntity.limbDistance * (1.0f - f3) + 6.0f;
        _snowman = (Math.abs(_snowman % 13.0f - 6.5f) - 3.25f) / 3.25f;
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(6.5f * _snowman));
    }
}

