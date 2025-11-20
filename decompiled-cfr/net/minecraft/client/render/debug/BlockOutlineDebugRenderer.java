/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

public class BlockOutlineDebugRenderer
implements DebugRenderer.Renderer {
    private final MinecraftClient client;

    public BlockOutlineDebugRenderer(MinecraftClient minecraftClient) {
        this.client = minecraftClient;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, double cameraX, double cameraY, double cameraZ) {
        World world = this.client.player.world;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.lineWidth(2.0f);
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        BlockPos _snowman2 = new BlockPos(cameraX, cameraY, cameraZ);
        for (BlockPos blockPos : BlockPos.iterate(_snowman2.add(-6, -6, -6), _snowman2.add(6, 6, 6))) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(Blocks.AIR)) continue;
            VoxelShape _snowman3 = blockState.getOutlineShape(world, blockPos);
            for (Box box : _snowman3.getBoundingBoxes()) {
                BufferBuilder _snowman8;
                _snowman = box.offset(blockPos).expand(0.002).offset(-cameraX, -cameraY, -cameraZ);
                double d = _snowman.minX;
                _snowman = _snowman.minY;
                _snowman = _snowman.minZ;
                _snowman = _snowman.maxX;
                _snowman = _snowman.maxY;
                _snowman = _snowman.maxZ;
                float _snowman4 = 1.0f;
                float _snowman5 = 0.0f;
                float _snowman6 = 0.0f;
                float _snowman7 = 0.5f;
                if (blockState.isSideSolidFullSquare(world, blockPos, Direction.WEST)) {
                    Tessellator tessellator = Tessellator.getInstance();
                    _snowman8 = tessellator.getBuffer();
                    _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    tessellator.draw();
                }
                if (blockState.isSideSolidFullSquare(world, blockPos, Direction.SOUTH)) {
                    tessellator = Tessellator.getInstance();
                    _snowman8 = tessellator.getBuffer();
                    _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    tessellator.draw();
                }
                if (blockState.isSideSolidFullSquare(world, blockPos, Direction.EAST)) {
                    tessellator = Tessellator.getInstance();
                    _snowman8 = tessellator.getBuffer();
                    _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    tessellator.draw();
                }
                if (blockState.isSideSolidFullSquare(world, blockPos, Direction.NORTH)) {
                    tessellator = Tessellator.getInstance();
                    _snowman8 = tessellator.getBuffer();
                    _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    tessellator.draw();
                }
                if (blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN)) {
                    tessellator = Tessellator.getInstance();
                    _snowman8 = tessellator.getBuffer();
                    _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                    tessellator.draw();
                }
                if (!blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) continue;
                tessellator = Tessellator.getInstance();
                _snowman8 = tessellator.getBuffer();
                _snowman8.begin(5, VertexFormats.POSITION_COLOR);
                _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                _snowman8.vertex(d, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                _snowman8.vertex(_snowman, _snowman, _snowman).color(1.0f, 0.0f, 0.0f, 0.5f).next();
                tessellator.draw();
            }
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}

