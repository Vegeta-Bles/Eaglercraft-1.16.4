/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.TntMinecartEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.TntEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TntEntityRenderer
extends EntityRenderer<TntEntity> {
    public TntEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(TntEntity tntEntity, float f, float f2, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider, int n) {
        MatrixStack matrixStack2;
        matrixStack2.push();
        matrixStack2.translate(0.0, 0.5, 0.0);
        if ((float)tntEntity.getFuseTimer() - f2 + 1.0f < 10.0f) {
            float f3 = 1.0f - ((float)tntEntity.getFuseTimer() - f2 + 1.0f) / 10.0f;
            f3 = MathHelper.clamp(f3, 0.0f, 1.0f);
            f3 *= f3;
            f3 *= f3;
            _snowman = 1.0f + f3 * 0.3f;
            matrixStack2.scale(_snowman, _snowman, _snowman);
        }
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0f));
        matrixStack2.translate(-0.5, -0.5, 0.5);
        matrixStack2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
        TntMinecartEntityRenderer.renderFlashingBlock(Blocks.TNT.getDefaultState(), matrixStack2, vertexConsumerProvider, n, tntEntity.getFuseTimer() / 5 % 2 == 0);
        matrixStack2.pop();
        super.render(tntEntity, f, f2, matrixStack2, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(TntEntity tntEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

