/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.chunk;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.level.ColorResolver;

public class ChunkRendererRegion
implements BlockRenderView {
    protected final int chunkXOffset;
    protected final int chunkZOffset;
    protected final BlockPos offset;
    protected final int xSize;
    protected final int ySize;
    protected final int zSize;
    protected final WorldChunk[][] chunks;
    protected final BlockState[] blockStates;
    protected final FluidState[] fluidStates;
    protected final World world;

    @Nullable
    public static ChunkRendererRegion create(World world, BlockPos startPos, BlockPos endPos, int chunkRadius) {
        int n = startPos.getX() - chunkRadius >> 4;
        _snowman = startPos.getZ() - chunkRadius >> 4;
        _snowman = endPos.getX() + chunkRadius >> 4;
        _snowman = endPos.getZ() + chunkRadius >> 4;
        WorldChunk[][] _snowman2 = new WorldChunk[_snowman - n + 1][_snowman - _snowman + 1];
        for (_snowman = n; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                _snowman2[_snowman - n][_snowman - _snowman] = world.getChunk(_snowman, _snowman);
            }
        }
        if (ChunkRendererRegion.method_30000(startPos, endPos, n, _snowman, _snowman2)) {
            return null;
        }
        _snowman = 1;
        BlockPos _snowman3 = startPos.add(-1, -1, -1);
        BlockPos _snowman4 = endPos.add(1, 1, 1);
        return new ChunkRendererRegion(world, n, _snowman, _snowman2, _snowman3, _snowman4);
    }

    public static boolean method_30000(BlockPos blockPos, BlockPos blockPos2, int n, int n2, WorldChunk[][] worldChunkArray) {
        for (int i = blockPos.getX() >> 4; i <= blockPos2.getX() >> 4; ++i) {
            for (_snowman = blockPos.getZ() >> 4; _snowman <= blockPos2.getZ() >> 4; ++_snowman) {
                WorldChunk worldChunk = worldChunkArray[i - n][_snowman - n2];
                if (worldChunk.areSectionsEmptyBetween(blockPos.getY(), blockPos2.getY())) continue;
                return false;
            }
        }
        return true;
    }

    public ChunkRendererRegion(World world, int chunkX, int chunkZ, WorldChunk[][] chunks, BlockPos startPos, BlockPos endPos) {
        this.world = world;
        this.chunkXOffset = chunkX;
        this.chunkZOffset = chunkZ;
        this.chunks = chunks;
        this.offset = startPos;
        this.xSize = endPos.getX() - startPos.getX() + 1;
        this.ySize = endPos.getY() - startPos.getY() + 1;
        this.zSize = endPos.getZ() - startPos.getZ() + 1;
        this.blockStates = new BlockState[this.xSize * this.ySize * this.zSize];
        this.fluidStates = new FluidState[this.xSize * this.ySize * this.zSize];
        for (BlockPos blockPos : BlockPos.iterate(startPos, endPos)) {
            int n = (blockPos.getX() >> 4) - chunkX;
            _snowman = (blockPos.getZ() >> 4) - chunkZ;
            WorldChunk _snowman2 = chunks[n][_snowman];
            _snowman = this.getIndex(blockPos);
            this.blockStates[_snowman] = _snowman2.getBlockState(blockPos);
            this.fluidStates[_snowman] = _snowman2.getFluidState(blockPos);
        }
    }

    protected final int getIndex(BlockPos pos) {
        return this.getIndex(pos.getX(), pos.getY(), pos.getZ());
    }

    protected int getIndex(int x, int y, int z) {
        int n = x - this.offset.getX();
        _snowman = y - this.offset.getY();
        _snowman = z - this.offset.getZ();
        return _snowman * this.xSize * this.ySize + _snowman * this.xSize + n;
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return this.blockStates[this.getIndex(pos)];
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return this.fluidStates[this.getIndex(pos)];
    }

    @Override
    public float getBrightness(Direction direction, boolean shaded) {
        return this.world.getBrightness(direction, shaded);
    }

    @Override
    public LightingProvider getLightingProvider() {
        return this.world.getLightingProvider();
    }

    @Override
    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return this.getBlockEntity(pos, WorldChunk.CreationType.IMMEDIATE);
    }

    @Nullable
    public BlockEntity getBlockEntity(BlockPos blockPos, WorldChunk.CreationType creationType) {
        int n = (blockPos.getX() >> 4) - this.chunkXOffset;
        _snowman = (blockPos.getZ() >> 4) - this.chunkZOffset;
        return this.chunks[n][_snowman].getBlockEntity(blockPos, creationType);
    }

    @Override
    public int getColor(BlockPos pos, ColorResolver colorResolver) {
        return this.world.getColor(pos, colorResolver);
    }
}

