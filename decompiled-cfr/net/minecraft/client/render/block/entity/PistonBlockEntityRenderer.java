/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.enums.PistonType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonBlockEntityRenderer
extends BlockEntityRenderer<PistonBlockEntity> {
    private final BlockRenderManager manager = MinecraftClient.getInstance().getBlockRenderManager();

    public PistonBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(PistonBlockEntity pistonBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        World world = pistonBlockEntity.getWorld();
        if (world == null) {
            return;
        }
        BlockPos _snowman2 = pistonBlockEntity.getPos().offset(pistonBlockEntity.getMovementDirection().getOpposite());
        BlockState _snowman3 = pistonBlockEntity.getPushedBlock();
        if (_snowman3.isAir()) {
            return;
        }
        BlockModelRenderer.enableBrightnessCache();
        matrixStack.push();
        matrixStack.translate(pistonBlockEntity.getRenderOffsetX(f), pistonBlockEntity.getRenderOffsetY(f), pistonBlockEntity.getRenderOffsetZ(f));
        if (_snowman3.isOf(Blocks.PISTON_HEAD) && pistonBlockEntity.getProgress(f) <= 4.0f) {
            _snowman3 = (BlockState)_snowman3.with(PistonHeadBlock.SHORT, pistonBlockEntity.getProgress(f) <= 0.5f);
            this.method_3575(_snowman2, _snowman3, matrixStack, vertexConsumerProvider, world, false, n2);
        } else if (pistonBlockEntity.isSource() && !pistonBlockEntity.isExtending()) {
            PistonType pistonType = _snowman3.isOf(Blocks.STICKY_PISTON) ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState _snowman4 = (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.TYPE, pistonType)).with(PistonHeadBlock.FACING, _snowman3.get(PistonBlock.FACING));
            _snowman4 = (BlockState)_snowman4.with(PistonHeadBlock.SHORT, pistonBlockEntity.getProgress(f) >= 0.5f);
            this.method_3575(_snowman2, _snowman4, matrixStack, vertexConsumerProvider, world, false, n2);
            BlockPos _snowman5 = _snowman2.offset(pistonBlockEntity.getMovementDirection());
            matrixStack.pop();
            matrixStack.push();
            _snowman3 = (BlockState)_snowman3.with(PistonBlock.EXTENDED, true);
            this.method_3575(_snowman5, _snowman3, matrixStack, vertexConsumerProvider, world, true, n2);
        } else {
            this.method_3575(_snowman2, _snowman3, matrixStack, vertexConsumerProvider, world, false, n2);
        }
        matrixStack.pop();
        BlockModelRenderer.disableBrightnessCache();
    }

    private void method_3575(BlockPos blockPos, BlockState blockState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, World world, boolean bl, int n) {
        RenderLayer renderLayer = RenderLayers.getMovingBlockLayer(blockState);
        VertexConsumer _snowman2 = vertexConsumerProvider.getBuffer(renderLayer);
        this.manager.getModelRenderer().render(world, this.manager.getModel(blockState), blockState, blockPos, matrixStack, _snowman2, bl, new Random(), blockState.getRenderingSeed(blockPos), n);
    }
}

