/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.PaintingManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingMotive;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class PaintingEntityRenderer
extends EntityRenderer<PaintingEntity> {
    public PaintingEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(PaintingEntity paintingEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        matrixStack.push();
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0f - f));
        PaintingMotive paintingMotive = paintingEntity.motive;
        float _snowman2 = 0.0625f;
        matrixStack.scale(0.0625f, 0.0625f, 0.0625f);
        VertexConsumer _snowman3 = vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(this.getTexture(paintingEntity)));
        PaintingManager _snowman4 = MinecraftClient.getInstance().getPaintingManager();
        this.method_4074(matrixStack, _snowman3, paintingEntity, paintingMotive.getWidth(), paintingMotive.getHeight(), _snowman4.getPaintingSprite(paintingMotive), _snowman4.getBackSprite());
        matrixStack.pop();
        super.render(paintingEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(PaintingEntity paintingEntity) {
        return MinecraftClient.getInstance().getPaintingManager().getBackSprite().getAtlas().getId();
    }

    private void method_4074(MatrixStack matrixStack, VertexConsumer vertexConsumer, PaintingEntity paintingEntity, int n, int n2, Sprite sprite, Sprite sprite2) {
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f _snowman2 = entry.getModel();
        Matrix3f _snowman3 = entry.getNormal();
        float _snowman4 = (float)(-n) / 2.0f;
        float _snowman5 = (float)(-n2) / 2.0f;
        float _snowman6 = 0.5f;
        float _snowman7 = sprite2.getMinU();
        float _snowman8 = sprite2.getMaxU();
        float _snowman9 = sprite2.getMinV();
        float _snowman10 = sprite2.getMaxV();
        float _snowman11 = sprite2.getMinU();
        float _snowman12 = sprite2.getMaxU();
        float _snowman13 = sprite2.getMinV();
        float _snowman14 = sprite2.getFrameV(1.0);
        float _snowman15 = sprite2.getMinU();
        float _snowman16 = sprite2.getFrameU(1.0);
        float _snowman17 = sprite2.getMinV();
        float _snowman18 = sprite2.getMaxV();
        int _snowman19 = n / 16;
        int _snowman20 = n2 / 16;
        double _snowman21 = 16.0 / (double)_snowman19;
        double _snowman22 = 16.0 / (double)_snowman20;
        for (int i = 0; i < _snowman19; ++i) {
            for (_snowman = 0; _snowman < _snowman20; ++_snowman) {
                float f = _snowman4 + (float)((i + 1) * 16);
                _snowman = _snowman4 + (float)(i * 16);
                _snowman = _snowman5 + (float)((_snowman + 1) * 16);
                _snowman = _snowman5 + (float)(_snowman * 16);
                int _snowman23 = MathHelper.floor(paintingEntity.getX());
                int _snowman24 = MathHelper.floor(paintingEntity.getY() + (double)((_snowman + _snowman) / 2.0f / 16.0f));
                int _snowman25 = MathHelper.floor(paintingEntity.getZ());
                Direction _snowman26 = paintingEntity.getHorizontalFacing();
                if (_snowman26 == Direction.NORTH) {
                    _snowman23 = MathHelper.floor(paintingEntity.getX() + (double)((f + _snowman) / 2.0f / 16.0f));
                }
                if (_snowman26 == Direction.WEST) {
                    _snowman25 = MathHelper.floor(paintingEntity.getZ() - (double)((f + _snowman) / 2.0f / 16.0f));
                }
                if (_snowman26 == Direction.SOUTH) {
                    _snowman23 = MathHelper.floor(paintingEntity.getX() - (double)((f + _snowman) / 2.0f / 16.0f));
                }
                if (_snowman26 == Direction.EAST) {
                    _snowman25 = MathHelper.floor(paintingEntity.getZ() + (double)((f + _snowman) / 2.0f / 16.0f));
                }
                int _snowman27 = WorldRenderer.getLightmapCoordinates(paintingEntity.world, new BlockPos(_snowman23, _snowman24, _snowman25));
                _snowman = sprite.getFrameU(_snowman21 * (double)(_snowman19 - i));
                _snowman = sprite.getFrameU(_snowman21 * (double)(_snowman19 - (i + 1)));
                _snowman = sprite.getFrameV(_snowman22 * (double)(_snowman20 - _snowman));
                _snowman = sprite.getFrameV(_snowman22 * (double)(_snowman20 - (_snowman + 1)));
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman, _snowman, -0.5f, 0, 0, -1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman, _snowman, -0.5f, 0, 0, -1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman, _snowman, -0.5f, 0, 0, -1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman, _snowman, -0.5f, 0, 0, -1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman7, _snowman9, 0.5f, 0, 0, 1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman8, _snowman9, 0.5f, 0, 0, 1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman8, _snowman10, 0.5f, 0, 0, 1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman7, _snowman10, 0.5f, 0, 0, 1, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman11, _snowman13, -0.5f, 0, 1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman12, _snowman13, -0.5f, 0, 1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman12, _snowman14, 0.5f, 0, 1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman11, _snowman14, 0.5f, 0, 1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman11, _snowman13, 0.5f, 0, -1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman12, _snowman13, 0.5f, 0, -1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman12, _snowman14, -0.5f, 0, -1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman11, _snowman14, -0.5f, 0, -1, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman16, _snowman17, 0.5f, -1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman16, _snowman18, 0.5f, -1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman15, _snowman18, -0.5f, -1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, f, _snowman, _snowman15, _snowman17, -0.5f, -1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman16, _snowman17, -0.5f, 1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman16, _snowman18, -0.5f, 1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman15, _snowman18, 0.5f, 1, 0, 0, _snowman27);
                this.method_23188(_snowman2, _snowman3, vertexConsumer, _snowman, _snowman, _snowman15, _snowman17, 0.5f, 1, 0, 0, _snowman27);
            }
        }
    }

    private void method_23188(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float f, float f2, float f3, float f4, float f5, int n, int n2, int n3, int n4) {
        vertexConsumer.vertex(matrix4f, f, f2, f5).color(255, 255, 255, 255).texture(f3, f4).overlay(OverlayTexture.DEFAULT_UV).light(n4).normal(matrix3f, n, n2, n3).next();
    }
}

