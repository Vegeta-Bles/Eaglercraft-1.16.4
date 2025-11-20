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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public abstract class ProjectileEntityRenderer<T extends PersistentProjectileEntity>
extends EntityRenderer<T> {
    public ProjectileEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        matrixStack2.push();
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(f2, ((PersistentProjectileEntity)t).prevYaw, ((PersistentProjectileEntity)t).yaw) - 90.0f));
        matrixStack2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(f2, ((PersistentProjectileEntity)t).prevPitch, ((PersistentProjectileEntity)t).pitch)));
        boolean bl = false;
        float _snowman2 = 0.0f;
        float _snowman3 = 0.5f;
        float _snowman4 = 0.0f;
        float _snowman5 = 0.15625f;
        float _snowman6 = 0.0f;
        float _snowman7 = 0.15625f;
        float _snowman8 = 0.15625f;
        float _snowman9 = 0.3125f;
        float _snowman10 = 0.05625f;
        float _snowman11 = (float)((PersistentProjectileEntity)t).shake - f2;
        if (_snowman11 > 0.0f) {
            float f3 = -MathHelper.sin(_snowman11 * 3.0f) * _snowman11;
            matrixStack2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(f3));
        }
        matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(45.0f));
        matrixStack2.scale(0.05625f, 0.05625f, 0.05625f);
        matrixStack2.translate(-4.0, 0.0, 0.0);
        VertexConsumer _snowman12 = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(t)));
        MatrixStack.Entry _snowman13 = matrixStack2.peek();
        Matrix4f _snowman14 = _snowman13.getModel();
        Matrix3f _snowman15 = _snowman13.getNormal();
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, n);
        this.method_23153(_snowman14, _snowman15, _snowman12, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, n);
        for (int i = 0; i < 4; ++i) {
            matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            this.method_23153(_snowman14, _snowman15, _snowman12, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, n);
            this.method_23153(_snowman14, _snowman15, _snowman12, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, n);
            this.method_23153(_snowman14, _snowman15, _snowman12, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, n);
            this.method_23153(_snowman14, _snowman15, _snowman12, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, n);
        }
        matrixStack2.pop();
        super.render(t, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    public void method_23153(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int n, int n2, int n3, float f, float f2, int n4, int n5, int n6, int n7) {
        vertexConsumer.vertex(matrix4f, n, n2, n3).color(255, 255, 255, 255).texture(f, f2).overlay(OverlayTexture.DEFAULT_UV).light(n7).normal(matrix3f, n4, n6, n5).next();
    }
}

