/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render;

import javax.annotation.Nullable;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BuiltChunkStorage {
    protected final WorldRenderer worldRenderer;
    protected final World world;
    protected int sizeY;
    protected int sizeX;
    protected int sizeZ;
    public ChunkBuilder.BuiltChunk[] chunks;

    public BuiltChunkStorage(ChunkBuilder chunkBuilder, World world, int viewDistance, WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
        this.world = world;
        this.setViewDistance(viewDistance);
        this.createChunks(chunkBuilder);
    }

    protected void createChunks(ChunkBuilder chunkBuilder) {
        int n = this.sizeX * this.sizeY * this.sizeZ;
        this.chunks = new ChunkBuilder.BuiltChunk[n];
        for (_snowman = 0; _snowman < this.sizeX; ++_snowman) {
            for (_snowman = 0; _snowman < this.sizeY; ++_snowman) {
                for (_snowman = 0; _snowman < this.sizeZ; ++_snowman) {
                    _snowman = this.getChunkIndex(_snowman, _snowman, _snowman);
                    this.chunks[_snowman] = chunkBuilder.new ChunkBuilder.BuiltChunk();
                    this.chunks[_snowman].setOrigin(_snowman * 16, _snowman * 16, _snowman * 16);
                }
            }
        }
    }

    public void clear() {
        for (ChunkBuilder.BuiltChunk builtChunk : this.chunks) {
            builtChunk.delete();
        }
    }

    private int getChunkIndex(int x, int y, int z) {
        return (z * this.sizeY + y) * this.sizeX + x;
    }

    protected void setViewDistance(int viewDistance) {
        int n;
        this.sizeX = n = viewDistance * 2 + 1;
        this.sizeY = 16;
        this.sizeZ = n;
    }

    public void updateCameraPosition(double x, double z) {
        int n = MathHelper.floor(x);
        _snowman = MathHelper.floor(z);
        for (_snowman = 0; _snowman < this.sizeX; ++_snowman) {
            _snowman = this.sizeX * 16;
            _snowman = n - 8 - _snowman / 2;
            _snowman = _snowman + Math.floorMod(_snowman * 16 - _snowman, _snowman);
            for (_snowman = 0; _snowman < this.sizeZ; ++_snowman) {
                _snowman = this.sizeZ * 16;
                _snowman = _snowman - 8 - _snowman / 2;
                _snowman = _snowman + Math.floorMod(_snowman * 16 - _snowman, _snowman);
                for (_snowman = 0; _snowman < this.sizeY; ++_snowman) {
                    _snowman = _snowman * 16;
                    ChunkBuilder.BuiltChunk builtChunk = this.chunks[this.getChunkIndex(_snowman, _snowman, _snowman)];
                    builtChunk.setOrigin(_snowman, _snowman, _snowman);
                }
            }
        }
    }

    public void scheduleRebuild(int x, int y, int z, boolean important) {
        int n = Math.floorMod(x, this.sizeX);
        _snowman = Math.floorMod(y, this.sizeY);
        _snowman = Math.floorMod(z, this.sizeZ);
        ChunkBuilder.BuiltChunk _snowman2 = this.chunks[this.getChunkIndex(n, _snowman, _snowman)];
        _snowman2.scheduleRebuild(important);
    }

    @Nullable
    protected ChunkBuilder.BuiltChunk getRenderedChunk(BlockPos pos) {
        int n = MathHelper.floorDiv(pos.getX(), 16);
        _snowman = MathHelper.floorDiv(pos.getY(), 16);
        _snowman = MathHelper.floorDiv(pos.getZ(), 16);
        if (_snowman < 0 || _snowman >= this.sizeY) {
            return null;
        }
        n = MathHelper.floorMod(n, this.sizeX);
        _snowman = MathHelper.floorMod(_snowman, this.sizeZ);
        return this.chunks[this.getChunkIndex(n, _snowman, _snowman)];
    }
}

