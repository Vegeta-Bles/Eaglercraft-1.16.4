/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;

public abstract class MobEntityRenderer<T extends MobEntity, M extends EntityModel<T>>
extends LivingEntityRenderer<T, M> {
    public MobEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, M m, float f) {
        super(entityRenderDispatcher, m, f);
    }

    @Override
    protected boolean hasLabel(T t) {
        return super.hasLabel(t) && (((LivingEntity)t).shouldRenderName() || ((Entity)t).hasCustomName() && t == this.dispatcher.targetedEntity);
    }

    @Override
    public boolean shouldRender(T t, Frustum frustum, double d, double d2, double d3) {
        if (super.shouldRender(t, frustum, d, d2, d3)) {
            return true;
        }
        Entity entity = ((MobEntity)t).getHoldingEntity();
        if (entity != null) {
            return frustum.isVisible(entity.getVisibilityBoundingBox());
        }
        return false;
    }

    @Override
    public void render(T t, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        super.render(t, f, f2, matrixStack, vertexConsumerProvider, n);
        Entity entity = ((MobEntity)t).getHoldingEntity();
        if (entity == null) {
            return;
        }
        this.method_4073(t, f2, matrixStack, vertexConsumerProvider, entity);
    }

    private <E extends Entity> void method_4073(T t, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, E e) {
        matrixStack.push();
        Vec3d vec3d = e.method_30951(f);
        double _snowman2 = (double)(MathHelper.lerp(f, ((MobEntity)t).bodyYaw, ((MobEntity)t).prevBodyYaw) * ((float)Math.PI / 180)) + 1.5707963267948966;
        _snowman = ((Entity)t).method_29919();
        double _snowman3 = Math.cos(_snowman2) * _snowman.z + Math.sin(_snowman2) * _snowman.x;
        double _snowman4 = Math.sin(_snowman2) * _snowman.z - Math.cos(_snowman2) * _snowman.x;
        double _snowman5 = MathHelper.lerp((double)f, ((MobEntity)t).prevX, ((Entity)t).getX()) + _snowman3;
        double _snowman6 = MathHelper.lerp((double)f, ((MobEntity)t).prevY, ((Entity)t).getY()) + _snowman.y;
        double _snowman7 = MathHelper.lerp((double)f, ((MobEntity)t).prevZ, ((Entity)t).getZ()) + _snowman4;
        matrixStack.translate(_snowman3, _snowman.y, _snowman4);
        float _snowman8 = (float)(vec3d.x - _snowman5);
        float _snowman9 = (float)(vec3d.y - _snowman6);
        float _snowman10 = (float)(vec3d.z - _snowman7);
        float _snowman11 = 0.025f;
        VertexConsumer _snowman12 = vertexConsumerProvider.getBuffer(RenderLayer.getLeash());
        Matrix4f _snowman13 = matrixStack.peek().getModel();
        float _snowman14 = MathHelper.fastInverseSqrt(_snowman8 * _snowman8 + _snowman10 * _snowman10) * 0.025f / 2.0f;
        float _snowman15 = _snowman10 * _snowman14;
        float _snowman16 = _snowman8 * _snowman14;
        BlockPos _snowman17 = new BlockPos(((Entity)t).getCameraPosVec(f));
        BlockPos _snowman18 = new BlockPos(e.getCameraPosVec(f));
        int _snowman19 = this.getBlockLight(t, _snowman17);
        int _snowman20 = this.dispatcher.getRenderer(e).getBlockLight(e, _snowman18);
        int _snowman21 = ((MobEntity)t).world.getLightLevel(LightType.SKY, _snowman17);
        int _snowman22 = ((MobEntity)t).world.getLightLevel(LightType.SKY, _snowman18);
        MobEntityRenderer.method_23186(_snowman12, _snowman13, _snowman8, _snowman9, _snowman10, _snowman19, _snowman20, _snowman21, _snowman22, 0.025f, 0.025f, _snowman15, _snowman16);
        MobEntityRenderer.method_23186(_snowman12, _snowman13, _snowman8, _snowman9, _snowman10, _snowman19, _snowman20, _snowman21, _snowman22, 0.025f, 0.0f, _snowman15, _snowman16);
        matrixStack.pop();
    }

    public static void method_23186(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float f2, float f3, int n, int n2, int n3, int n4, float f4, float f5, float f6, float f7) {
        int n5 = 24;
        for (_snowman = 0; _snowman < 24; ++_snowman) {
            float f8 = (float)_snowman / 23.0f;
            int _snowman2 = (int)MathHelper.lerp(f8, n, n2);
            int _snowman3 = (int)MathHelper.lerp(f8, n3, n4);
            int _snowman4 = LightmapTextureManager.pack(_snowman2, _snowman3);
            MobEntityRenderer.method_23187(vertexConsumer, matrix4f, _snowman4, f, f2, f3, f4, f5, 24, _snowman, false, f6, f7);
            MobEntityRenderer.method_23187(vertexConsumer, matrix4f, _snowman4, f, f2, f3, f4, f5, 24, _snowman + 1, true, f6, f7);
        }
    }

    public static void method_23187(VertexConsumer vertexConsumer, Matrix4f matrix4f, int n, float f, float f2, float f3, float f4, float f5, int n2, int n3, boolean bl, float f6, float f7) {
        _snowman = 0.5f;
        _snowman = 0.4f;
        _snowman = 0.3f;
        if (n3 % 2 == 0) {
            _snowman *= 0.7f;
            _snowman *= 0.7f;
            _snowman *= 0.7f;
        }
        _snowman = (float)n3 / (float)n2;
        _snowman = f * _snowman;
        _snowman = f2 > 0.0f ? f2 * _snowman * _snowman : f2 - f2 * (1.0f - _snowman) * (1.0f - _snowman);
        _snowman = f3 * _snowman;
        if (!bl) {
            vertexConsumer.vertex(matrix4f, _snowman + f6, _snowman + f4 - f5, _snowman - f7).color(_snowman, _snowman, _snowman, 1.0f).light(n).next();
        }
        vertexConsumer.vertex(matrix4f, _snowman - f6, _snowman + f5, _snowman + f7).color(_snowman, _snowman, _snowman, 1.0f).light(n).next();
        if (bl) {
            vertexConsumer.vertex(matrix4f, _snowman + f6, _snowman + f4 - f5, _snowman - f7).color(_snowman, _snowman, _snowman, 1.0f).light(n).next();
        }
    }
}

