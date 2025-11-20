/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class FishingBobberEntityRenderer
extends EntityRenderer<FishingBobberEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/fishing_hook.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

    public FishingBobberEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(FishingBobberEntity fishingBobberEntity, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        float _snowman15;
        PlayerEntity playerEntity = fishingBobberEntity.getPlayerOwner();
        if (playerEntity == null) {
            return;
        }
        matrixStack2.push();
        matrixStack2.push();
        matrixStack2.scale(0.5f, 0.5f, 0.5f);
        matrixStack2.multiply(this.dispatcher.getRotation());
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f));
        MatrixStack.Entry _snowman2 = matrixStack2.peek();
        Matrix4f _snowman3 = _snowman2.getModel();
        Matrix3f _snowman4 = _snowman2.getNormal();
        VertexConsumer _snowman5 = vertexConsumerProvider.getBuffer(LAYER);
        FishingBobberEntityRenderer.method_23840(_snowman5, _snowman3, _snowman4, n, 0.0f, 0, 0, 1);
        FishingBobberEntityRenderer.method_23840(_snowman5, _snowman3, _snowman4, n, 1.0f, 0, 1, 1);
        FishingBobberEntityRenderer.method_23840(_snowman5, _snowman3, _snowman4, n, 1.0f, 1, 1, 0);
        FishingBobberEntityRenderer.method_23840(_snowman5, _snowman3, _snowman4, n, 0.0f, 1, 0, 0);
        matrixStack2.pop();
        int _snowman6 = playerEntity.getMainArm() == Arm.RIGHT ? 1 : -1;
        ItemStack _snowman7 = playerEntity.getMainHandStack();
        if (_snowman7.getItem() != Items.FISHING_ROD) {
            _snowman6 = -_snowman6;
        }
        float _snowman8 = playerEntity.getHandSwingProgress(f2);
        float _snowman9 = MathHelper.sin(MathHelper.sqrt(_snowman8) * (float)Math.PI);
        float _snowman10 = MathHelper.lerp(f2, playerEntity.prevBodyYaw, playerEntity.bodyYaw) * ((float)Math.PI / 180);
        double _snowman11 = MathHelper.sin(_snowman10);
        double _snowman12 = MathHelper.cos(_snowman10);
        double _snowman13 = (double)_snowman6 * 0.35;
        double _snowman14 = 0.8;
        if (this.dispatcher.gameOptions != null && !this.dispatcher.gameOptions.getPerspective().isFirstPerson() || playerEntity != MinecraftClient.getInstance().player) {
            double d = MathHelper.lerp((double)f2, playerEntity.prevX, playerEntity.getX()) - _snowman12 * _snowman13 - _snowman11 * 0.8;
            _snowman = playerEntity.prevY + (double)playerEntity.getStandingEyeHeight() + (playerEntity.getY() - playerEntity.prevY) * (double)f2 - 0.45;
            _snowman = MathHelper.lerp((double)f2, playerEntity.prevZ, playerEntity.getZ()) - _snowman11 * _snowman13 + _snowman12 * 0.8;
            _snowman15 = playerEntity.isInSneakingPose() ? -0.1875f : 0.0f;
        } else {
            _snowman = this.dispatcher.gameOptions.fov;
            Vec3d _snowman16 = new Vec3d((double)_snowman6 * -0.36 * (_snowman /= 100.0), -0.045 * _snowman, 0.4);
            _snowman16 = _snowman16.rotateX(-MathHelper.lerp(f2, playerEntity.prevPitch, playerEntity.pitch) * ((float)Math.PI / 180));
            _snowman16 = _snowman16.rotateY(-MathHelper.lerp(f2, playerEntity.prevYaw, playerEntity.yaw) * ((float)Math.PI / 180));
            _snowman16 = _snowman16.rotateY(_snowman9 * 0.5f);
            _snowman16 = _snowman16.rotateX(-_snowman9 * 0.7f);
            d = MathHelper.lerp((double)f2, playerEntity.prevX, playerEntity.getX()) + _snowman16.x;
            _snowman = MathHelper.lerp((double)f2, playerEntity.prevY, playerEntity.getY()) + _snowman16.y;
            _snowman = MathHelper.lerp((double)f2, playerEntity.prevZ, playerEntity.getZ()) + _snowman16.z;
            _snowman15 = playerEntity.getStandingEyeHeight();
        }
        _snowman = MathHelper.lerp((double)f2, fishingBobberEntity.prevX, fishingBobberEntity.getX());
        _snowman = MathHelper.lerp((double)f2, fishingBobberEntity.prevY, fishingBobberEntity.getY()) + 0.25;
        _snowman = MathHelper.lerp((double)f2, fishingBobberEntity.prevZ, fishingBobberEntity.getZ());
        float f3 = (float)(d - _snowman);
        _snowman = (float)(_snowman - _snowman) + _snowman15;
        _snowman = (float)(_snowman - _snowman);
        VertexConsumer _snowman17 = vertexConsumerProvider.getBuffer(RenderLayer.getLines());
        Matrix4f _snowman18 = matrixStack2.peek().getModel();
        int _snowman19 = 16;
        for (int i = 0; i < 16; ++i) {
            FishingBobberEntityRenderer.method_23172(f3, _snowman, _snowman, _snowman17, _snowman18, FishingBobberEntityRenderer.method_23954(i, 16));
            FishingBobberEntityRenderer.method_23172(f3, _snowman, _snowman, _snowman17, _snowman18, FishingBobberEntityRenderer.method_23954(i + 1, 16));
        }
        matrixStack2.pop();
        super.render(fishingBobberEntity, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    private static float method_23954(int n, int n2) {
        return (float)n / (float)n2;
    }

    private static void method_23840(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int n, float f, int n2, int n3, int n4) {
        vertexConsumer.vertex(matrix4f, f - 0.5f, (float)n2 - 0.5f, 0.0f).color(255, 255, 255, 255).texture(n3, n4).overlay(OverlayTexture.DEFAULT_UV).light(n).normal(matrix3f, 0.0f, 1.0f, 0.0f).next();
    }

    private static void method_23172(float f, float f2, float f3, VertexConsumer vertexConsumer, Matrix4f matrix4f, float f4) {
        vertexConsumer.vertex(matrix4f, f * f4, f2 * (f4 * f4 + f4) * 0.5f + 0.25f, f3 * f4).color(0, 0, 0, 255).next();
    }

    @Override
    public Identifier getTexture(FishingBobberEntity fishingBobberEntity) {
        return TEXTURE;
    }
}

