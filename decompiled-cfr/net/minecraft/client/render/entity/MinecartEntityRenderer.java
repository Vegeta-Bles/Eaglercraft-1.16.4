/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MinecartEntityRenderer<T extends AbstractMinecartEntity>
extends EntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/minecart.png");
    protected final EntityModel<T> model = new MinecartEntityModel();

    public MinecartEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.7f;
    }

    @Override
    public void render(T t, float f3, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        super.render(t, f3, f2, matrixStack2, vertexConsumerProvider, n);
        matrixStack2.push();
        long l = (long)((Entity)t).getEntityId() * 493286711L;
        l = l * l * 4392167121L + l * 98761L;
        float _snowman2 = (((float)(l >> 16 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float _snowman3 = (((float)(l >> 20 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        float _snowman4 = (((float)(l >> 24 & 7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        matrixStack2.translate(_snowman2, _snowman3, _snowman4);
        double _snowman5 = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastRenderX, ((Entity)t).getX());
        double _snowman6 = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastRenderY, ((Entity)t).getY());
        double _snowman7 = MathHelper.lerp((double)f2, ((AbstractMinecartEntity)t).lastRenderZ, ((Entity)t).getZ());
        double _snowman8 = 0.3f;
        Vec3d _snowman9 = ((AbstractMinecartEntity)t).snapPositionToRail(_snowman5, _snowman6, _snowman7);
        float _snowman10 = MathHelper.lerp(f2, ((AbstractMinecartEntity)t).prevPitch, ((AbstractMinecartEntity)t).pitch);
        if (_snowman9 != null) {
            Vec3d vec3d = ((AbstractMinecartEntity)t).snapPositionToRailWithOffset(_snowman5, _snowman6, _snowman7, 0.3f);
            _snowman = ((AbstractMinecartEntity)t).snapPositionToRailWithOffset(_snowman5, _snowman6, _snowman7, -0.3f);
            if (vec3d == null) {
                vec3d = _snowman9;
            }
            if (_snowman == null) {
                _snowman = _snowman9;
            }
            matrixStack2.translate(_snowman9.x - _snowman5, (vec3d.y + _snowman.y) / 2.0 - _snowman6, _snowman9.z - _snowman7);
            _snowman = _snowman.add(-vec3d.x, -vec3d.y, -vec3d.z);
            if (_snowman.length() != 0.0) {
                _snowman = _snowman.normalize();
                float f3 = (float)(Math.atan2(_snowman.z, _snowman.x) * 180.0 / Math.PI);
                _snowman10 = (float)(Math.atan(_snowman.y) * 73.0);
            }
        }
        matrixStack2.translate(0.0, 0.375, 0.0);
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f3));
        matrixStack2.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-_snowman10));
        float _snowman11 = (float)((AbstractMinecartEntity)t).getDamageWobbleTicks() - f2;
        float _snowman12 = ((AbstractMinecartEntity)t).getDamageWobbleStrength() - f2;
        if (_snowman12 < 0.0f) {
            _snowman12 = 0.0f;
        }
        if (_snowman11 > 0.0f) {
            matrixStack2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(_snowman11) * _snowman11 * _snowman12 / 10.0f * (float)((AbstractMinecartEntity)t).getDamageWobbleSide()));
        }
        int _snowman13 = ((AbstractMinecartEntity)t).getBlockOffset();
        BlockState _snowman14 = ((AbstractMinecartEntity)t).getContainedBlock();
        if (_snowman14.getRenderType() != BlockRenderType.INVISIBLE) {
            matrixStack2.push();
            float f4 = 0.75f;
            matrixStack2.scale(0.75f, 0.75f, 0.75f);
            matrixStack2.translate(-0.5, (float)(_snowman13 - 8) / 16.0f, 0.5);
            matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
            this.renderBlock(t, f2, _snowman14, matrixStack2, vertexConsumerProvider, n);
            matrixStack2.pop();
        }
        matrixStack2.scale(-1.0f, -1.0f, 1.0f);
        this.model.setAngles(t, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f);
        VertexConsumer _snowman15 = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(t)));
        this.model.render(matrixStack2, _snowman15, n, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack2.pop();
    }

    @Override
    public Identifier getTexture(T t) {
        return TEXTURE;
    }

    protected void renderBlock(T entity, float delta, BlockState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV);
    }
}

