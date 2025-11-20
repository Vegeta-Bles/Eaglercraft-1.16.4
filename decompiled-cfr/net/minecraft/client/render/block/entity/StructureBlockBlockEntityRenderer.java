/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.block.enums.StructureBlockMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureBlockBlockEntityRenderer
extends BlockEntityRenderer<StructureBlockBlockEntity> {
    public StructureBlockBlockEntityRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher) {
        super(blockEntityRenderDispatcher);
    }

    @Override
    public void render(StructureBlockBlockEntity structureBlockBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int n, int n2) {
        double _snowman11;
        double _snowman10;
        double _snowman9;
        double _snowman8;
        double _snowman7;
        double _snowman6;
        if (!MinecraftClient.getInstance().player.isCreativeLevelTwoOp() && !MinecraftClient.getInstance().player.isSpectator()) {
            return;
        }
        BlockPos blockPos = structureBlockBlockEntity.getOffset();
        blockPos2 = structureBlockBlockEntity.getSize();
        if (blockPos2.getX() < 1 || blockPos2.getY() < 1 || blockPos2.getZ() < 1) {
            return;
        }
        if (structureBlockBlockEntity.getMode() != StructureBlockMode.SAVE && structureBlockBlockEntity.getMode() != StructureBlockMode.LOAD) {
            return;
        }
        double _snowman2 = blockPos.getX();
        double _snowman3 = blockPos.getZ();
        double _snowman4 = blockPos.getY();
        double _snowman5 = _snowman4 + (double)blockPos2.getY();
        switch (structureBlockBlockEntity.getMirror()) {
            case LEFT_RIGHT: {
                _snowman6 = blockPos2.getX();
                _snowman7 = -blockPos2.getZ();
                break;
            }
            case FRONT_BACK: {
                BlockPos blockPos2;
                _snowman6 = -blockPos2.getX();
                _snowman7 = blockPos2.getZ();
                break;
            }
            default: {
                _snowman6 = blockPos2.getX();
                _snowman7 = blockPos2.getZ();
            }
        }
        switch (structureBlockBlockEntity.getRotation()) {
            case CLOCKWISE_90: {
                _snowman8 = _snowman7 < 0.0 ? _snowman2 : _snowman2 + 1.0;
                _snowman9 = _snowman6 < 0.0 ? _snowman3 + 1.0 : _snowman3;
                _snowman10 = _snowman8 - _snowman7;
                _snowman11 = _snowman9 + _snowman6;
                break;
            }
            case CLOCKWISE_180: {
                _snowman8 = _snowman6 < 0.0 ? _snowman2 : _snowman2 + 1.0;
                _snowman9 = _snowman7 < 0.0 ? _snowman3 : _snowman3 + 1.0;
                _snowman10 = _snowman8 - _snowman6;
                _snowman11 = _snowman9 - _snowman7;
                break;
            }
            case COUNTERCLOCKWISE_90: {
                _snowman8 = _snowman7 < 0.0 ? _snowman2 + 1.0 : _snowman2;
                _snowman9 = _snowman6 < 0.0 ? _snowman3 : _snowman3 + 1.0;
                _snowman10 = _snowman8 + _snowman7;
                _snowman11 = _snowman9 - _snowman6;
                break;
            }
            default: {
                _snowman8 = _snowman6 < 0.0 ? _snowman2 + 1.0 : _snowman2;
                _snowman9 = _snowman7 < 0.0 ? _snowman3 + 1.0 : _snowman3;
                _snowman10 = _snowman8 + _snowman6;
                _snowman11 = _snowman9 + _snowman7;
            }
        }
        float f2 = 1.0f;
        _snowman = 0.9f;
        _snowman = 0.5f;
        VertexConsumer _snowman12 = vertexConsumerProvider.getBuffer(RenderLayer.getLines());
        if (structureBlockBlockEntity.getMode() == StructureBlockMode.SAVE || structureBlockBlockEntity.shouldShowBoundingBox()) {
            WorldRenderer.drawBox(matrixStack, _snowman12, _snowman8, _snowman4, _snowman9, _snowman10, _snowman5, _snowman11, 0.9f, 0.9f, 0.9f, 1.0f, 0.5f, 0.5f, 0.5f);
        }
        if (structureBlockBlockEntity.getMode() == StructureBlockMode.SAVE && structureBlockBlockEntity.shouldShowAir()) {
            this.method_3585(structureBlockBlockEntity, _snowman12, blockPos, true, matrixStack);
            this.method_3585(structureBlockBlockEntity, _snowman12, blockPos, false, matrixStack);
        }
    }

    private void method_3585(StructureBlockBlockEntity structureBlockBlockEntity, VertexConsumer vertexConsumer, BlockPos blockPos, boolean bl, MatrixStack matrixStack) {
        World world = structureBlockBlockEntity.getWorld();
        BlockPos _snowman2 = structureBlockBlockEntity.getPos();
        BlockPos _snowman3 = _snowman2.add(blockPos);
        for (BlockPos blockPos2 : BlockPos.iterate(_snowman3, _snowman3.add(structureBlockBlockEntity.getSize()).add(-1, -1, -1))) {
            BlockState blockState = world.getBlockState(blockPos2);
            boolean _snowman4 = blockState.isAir();
            boolean _snowman5 = blockState.isOf(Blocks.STRUCTURE_VOID);
            if (!_snowman4 && !_snowman5) continue;
            float _snowman6 = _snowman4 ? 0.05f : 0.0f;
            double _snowman7 = (float)(blockPos2.getX() - _snowman2.getX()) + 0.45f - _snowman6;
            double _snowman8 = (float)(blockPos2.getY() - _snowman2.getY()) + 0.45f - _snowman6;
            double _snowman9 = (float)(blockPos2.getZ() - _snowman2.getZ()) + 0.45f - _snowman6;
            double _snowman10 = (float)(blockPos2.getX() - _snowman2.getX()) + 0.55f + _snowman6;
            double _snowman11 = (float)(blockPos2.getY() - _snowman2.getY()) + 0.55f + _snowman6;
            double _snowman12 = (float)(blockPos2.getZ() - _snowman2.getZ()) + 0.55f + _snowman6;
            if (bl) {
                WorldRenderer.drawBox(matrixStack, vertexConsumer, _snowman7, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f);
                continue;
            }
            if (_snowman4) {
                WorldRenderer.drawBox(matrixStack, vertexConsumer, _snowman7, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12, 0.5f, 0.5f, 1.0f, 1.0f, 0.5f, 0.5f, 1.0f);
                continue;
            }
            WorldRenderer.drawBox(matrixStack, vertexConsumer, _snowman7, _snowman8, _snowman9, _snowman10, _snowman11, _snowman12, 1.0f, 0.25f, 0.25f, 1.0f, 1.0f, 0.25f, 0.25f);
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(StructureBlockBlockEntity structureBlockBlockEntity) {
        return true;
    }
}

