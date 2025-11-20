/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FallingBlockEntityRenderer
extends EntityRenderer<FallingBlockEntity> {
    public FallingBlockEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(FallingBlockEntity fallingBlockEntity, float f, float f2, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n) {
        BlockState blockState = fallingBlockEntity.getBlockState();
        if (blockState.getRenderType() != BlockRenderType.MODEL) {
            return;
        }
        World _snowman2 = fallingBlockEntity.getWorldClient();
        if (blockState == _snowman2.getBlockState(fallingBlockEntity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        matrixStack.push();
        BlockPos _snowman3 = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
        matrixStack.translate(-0.5, 0.0, -0.5);
        BlockRenderManager _snowman4 = MinecraftClient.getInstance().getBlockRenderManager();
        _snowman4.getModelRenderer().render(_snowman2, _snowman4.getModel(blockState), blockState, _snowman3, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, new Random(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(fallingBlockEntity, f, f2, matrixStack, vertexConsumerProvider, n);
    }

    @Override
    public Identifier getTexture(FallingBlockEntity fallingBlockEntity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}

