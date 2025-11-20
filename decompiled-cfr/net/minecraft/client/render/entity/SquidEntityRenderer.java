/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SquidEntityRenderer
extends MobEntityRenderer<SquidEntity, SquidEntityModel<SquidEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/squid.png");

    public SquidEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new SquidEntityModel(), 0.7f);
    }

    @Override
    public Identifier getTexture(SquidEntity squidEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(SquidEntity squidEntity, MatrixStack matrixStack, float f, float f2, float f3) {
        _snowman = MathHelper.lerp(f3, squidEntity.prevTiltAngle, squidEntity.tiltAngle);
        _snowman = MathHelper.lerp(f3, squidEntity.prevRollAngle, squidEntity.rollAngle);
        matrixStack.translate(0.0, 0.5, 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f2));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
        matrixStack.translate(0.0, -1.2f, 0.0);
    }

    @Override
    protected float getAnimationProgress(SquidEntity squidEntity, float f) {
        return MathHelper.lerp(f, squidEntity.prevTentacleAngle, squidEntity.tentacleAngle);
    }
}

