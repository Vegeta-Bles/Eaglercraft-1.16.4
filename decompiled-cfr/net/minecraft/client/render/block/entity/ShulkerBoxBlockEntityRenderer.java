/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.state.State;
import net.minecraft.util.math.Direction;

public class ShulkerBoxBlockEntityRenderer
extends BlockEntityRenderer<ShulkerBoxBlockEntity> {
    private final ShulkerEntityModel<?> model;

    public ShulkerBoxBlockEntityRenderer(ShulkerEntityModel<?> shulkerEntityModel, BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
        this.model = shulkerEntityModel;
    }

    @Override
    public void render(ShulkerBoxBlockEntity shulkerBoxBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        Direction direction = Direction.UP;
        if (shulkerBoxBlockEntity.hasWorld() && ((AbstractBlock.AbstractBlockState)(_snowman2 = shulkerBoxBlockEntity.getWorld().getBlockState(shulkerBoxBlockEntity.getPos()))).getBlock() instanceof ShulkerBoxBlock) {
            direction = ((State)_snowman2).get(ShulkerBoxBlock.FACING);
        }
        Object _snowman2 = (_snowman = shulkerBoxBlockEntity.getColor()) == null ? TexturedRenderLayers.SHULKER_TEXTURE_ID : TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(_snowman.getId());
        matrixStack.push();
        matrixStack.translate(0.5, 0.5, 0.5);
        float _snowman3 = 0.9995f;
        matrixStack.scale(0.9995f, 0.9995f, 0.9995f);
        matrixStack.multiply(direction.getRotationQuaternion());
        matrixStack.scale(1.0f, -1.0f, -1.0f);
        matrixStack.translate(0.0, -1.0, 0.0);
        VertexConsumer _snowman4 = ((SpriteIdentifier)_snowman2).getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutoutNoCull);
        this.model.getBottomShell().render(matrixStack, _snowman4, n, n2);
        matrixStack.translate(0.0, -shulkerBoxBlockEntity.getAnimationProgress(f) * 0.5f, 0.0);
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0f * shulkerBoxBlockEntity.getAnimationProgress(f)));
        this.model.getTopShell().render(matrixStack, _snowman4, n, n2);
        matrixStack.pop();
    }
}

