/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CreeperEntityRenderer
extends MobEntityRenderer<CreeperEntity, CreeperEntityModel<CreeperEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/creeper/creeper.png");

    public CreeperEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new CreeperEntityModel(), 0.5f);
        this.addFeature(new CreeperChargeFeatureRenderer(this));
    }

    @Override
    protected void scale(CreeperEntity creeperEntity, MatrixStack matrixStack, float f) {
        _snowman = creeperEntity.getClientFuseTime(f);
        _snowman = 1.0f + MathHelper.sin(_snowman * 100.0f) * _snowman * 0.01f;
        _snowman = MathHelper.clamp(_snowman, 0.0f, 1.0f);
        _snowman *= _snowman;
        _snowman *= _snowman;
        _snowman = (1.0f + _snowman * 0.4f) * _snowman;
        _snowman = (1.0f + _snowman * 0.1f) / _snowman;
        matrixStack.scale(_snowman, _snowman, _snowman);
    }

    @Override
    protected float getAnimationCounter(CreeperEntity creeperEntity, float f) {
        _snowman = creeperEntity.getClientFuseTime(f);
        if ((int)(_snowman * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return MathHelper.clamp(_snowman, 0.5f, 1.0f);
    }

    @Override
    public Identifier getTexture(CreeperEntity creeperEntity) {
        return TEXTURE;
    }

    @Override
    protected /* synthetic */ float getAnimationCounter(LivingEntity entity, float tickDelta) {
        return this.getAnimationCounter((CreeperEntity)entity, tickDelta);
    }
}

