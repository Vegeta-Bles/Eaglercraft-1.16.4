/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerBulletEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class ShulkerBulletEntityRenderer
extends EntityRenderer<ShulkerBulletEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/shulker/spark.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
    private final ShulkerBulletEntityModel<ShulkerBulletEntity> model = new ShulkerBulletEntityModel();

    public ShulkerBulletEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    protected int getBlockLight(ShulkerBulletEntity shulkerBulletEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public void render(ShulkerBulletEntity shulkerBulletEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        float f3 = MathHelper.lerpAngle(shulkerBulletEntity.prevYaw, shulkerBulletEntity.yaw, f2);
        _snowman = MathHelper.lerp(f2, shulkerBulletEntity.prevPitch, shulkerBulletEntity.pitch);
        _snowman = (float)shulkerBulletEntity.age + f2;
        matrixStack.translate(0.0, 0.15f, 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(_snowman * 0.1f) * 180.0f));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.cos(_snowman * 0.1f) * 180.0f));
        matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(_snowman * 0.15f) * 360.0f));
        matrixStack.scale(-0.5f, -0.5f, 0.5f);
        this.model.setAngles(shulkerBulletEntity, 0.0f, 0.0f, 0.0f, f3, _snowman);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, _snowman2, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.scale(1.5f, 1.5f, 1.5f);
        VertexConsumer _snowman3 = vertexConsumerProvider.getBuffer(LAYER);
        this.model.render(matrixStack, _snowman3, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 0.15f);
        matrixStack.pop();
        super.render(shulkerBulletEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(ShulkerBulletEntity shulkerBulletEntity) {
        return TEXTURE;
    }
}

