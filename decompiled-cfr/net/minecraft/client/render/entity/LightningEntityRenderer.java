/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class LightningEntityRenderer
extends EntityRenderer<LightningEntity> {
    public LightningEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public void render(LightningEntity lightningEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider2, int n) {
        VertexConsumerProvider vertexConsumerProvider2;
        float[] fArray = new float[8];
        _snowman = new float[8];
        float _snowman2 = 0.0f;
        float _snowman3 = 0.0f;
        Object _snowman4 = new Random(lightningEntity.seed);
        for (int i = 7; i >= 0; --i) {
            fArray[i] = _snowman2;
            _snowman[i] = _snowman3;
            _snowman2 += (float)(((Random)_snowman4).nextInt(11) - 5);
            _snowman3 += (float)(((Random)_snowman4).nextInt(11) - 5);
        }
        _snowman4 = vertexConsumerProvider2.getBuffer(RenderLayer.getLightning());
        Matrix4f _snowman5 = matrixStack.peek().getModel();
        for (int i = 0; i < 4; ++i) {
            Random random = new Random(lightningEntity.seed);
            for (int j = 0; j < 3; ++j) {
                _snowman = 7;
                _snowman = 0;
                if (j > 0) {
                    _snowman = 7 - j;
                }
                if (j > 0) {
                    _snowman = _snowman - 2;
                }
                float f3 = fArray[_snowman] - _snowman2;
                _snowman = _snowman[_snowman] - _snowman3;
                for (int k = _snowman; k >= _snowman; --k) {
                    float f4 = f3;
                    _snowman = _snowman;
                    if (j == 0) {
                        f3 += (float)(random.nextInt(11) - 5);
                        _snowman += (float)(random.nextInt(11) - 5);
                    } else {
                        f3 += (float)(random.nextInt(31) - 15);
                        _snowman += (float)(random.nextInt(31) - 15);
                    }
                    _snowman = 0.5f;
                    _snowman = 0.45f;
                    _snowman = 0.45f;
                    _snowman = 0.5f;
                    _snowman = 0.1f + (float)i * 0.2f;
                    if (j == 0) {
                        _snowman = (float)((double)_snowman * ((double)k * 0.1 + 1.0));
                    }
                    _snowman = 0.1f + (float)i * 0.2f;
                    if (j == 0) {
                        _snowman *= (float)(k - 1) * 0.1f + 1.0f;
                    }
                    LightningEntityRenderer.method_23183(_snowman5, (VertexConsumer)_snowman4, f3, _snowman, k, f4, _snowman, 0.45f, 0.45f, 0.5f, _snowman, _snowman, false, false, true, false);
                    LightningEntityRenderer.method_23183(_snowman5, (VertexConsumer)_snowman4, f3, _snowman, k, f4, _snowman, 0.45f, 0.45f, 0.5f, _snowman, _snowman, true, false, true, true);
                    LightningEntityRenderer.method_23183(_snowman5, (VertexConsumer)_snowman4, f3, _snowman, k, f4, _snowman, 0.45f, 0.45f, 0.5f, _snowman, _snowman, true, true, false, true);
                    LightningEntityRenderer.method_23183(_snowman5, (VertexConsumer)_snowman4, f3, _snowman, k, f4, _snowman, 0.45f, 0.45f, 0.5f, _snowman, _snowman, false, true, false, false);
                }
            }
        }
    }

    private static void method_23183(Matrix4f matrix4f, VertexConsumer vertexConsumer, float f, float f2, int n, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        vertexConsumer.vertex(matrix4f, f + (bl ? f9 : -f9), n * 16, f2 + (bl2 ? f9 : -f9)).color(f5, f6, f7, 0.3f).next();
        vertexConsumer.vertex(matrix4f, f3 + (bl ? f8 : -f8), (n + 1) * 16, f4 + (bl2 ? f8 : -f8)).color(f5, f6, f7, 0.3f).next();
        vertexConsumer.vertex(matrix4f, f3 + (bl3 ? f8 : -f8), (n + 1) * 16, f4 + (bl4 ? f8 : -f8)).color(f5, f6, f7, 0.3f).next();
        vertexConsumer.vertex(matrix4f, f + (bl3 ? f9 : -f9), n * 16, f2 + (bl4 ? f9 : -f9)).color(f5, f6, f7, 0.3f).next();
    }

    @Override
    public Identifier getTexture(LightningEntity lightningEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

