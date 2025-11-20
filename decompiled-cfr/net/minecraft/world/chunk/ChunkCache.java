/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.chunk;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.EmptyChunk;

public class ChunkCache
implements BlockView,
CollisionView {
    protected final int minX;
    protected final int minZ;
    protected final Chunk[][] chunks;
    protected boolean empty;
    protected final World world;

    public ChunkCache(World world, BlockPos minPos, BlockPos maxPos) {
        this.world = world;
        this.minX = minPos.getX() >> 4;
        this.minZ = minPos.getZ() >> 4;
        int n = maxPos.getX() >> 4;
        _snowman = maxPos.getZ() >> 4;
        this.chunks = new Chunk[n - this.minX + 1][_snowman - this.minZ + 1];
        ChunkManager _snowman2 = world.getChunkManager();
        this.empty = true;
        for (_snowman = this.minX; _snowman <= n; ++_snowman) {
            for (_snowman = this.minZ; _snowman <= _snowman; ++_snowman) {
                this.chunks[_snowman - this.minX][_snowman - this.minZ] = _snowman2.getWorldChunk(_snowman, _snowman);
            }
        }
        for (_snowman = minPos.getX() >> 4; _snowman <= maxPos.getX() >> 4; ++_snowman) {
            for (_snowman = minPos.getZ() >> 4; _snowman <= maxPos.getZ() >> 4; ++_snowman) {
                Chunk chunk = this.chunks[_snowman - this.minX][_snowman - this.minZ];
                if (chunk == null || chunk.areSectionsEmptyBetween(minPos.getY(), maxPos.getY())) continue;
                this.empty = false;
                return;
            }
        }
    }

    private Chunk method_22354(BlockPos blockPos) {
        return this.method_22353(blockPos.getX() >> 4, blockPos.getZ() >> 4);
    }

    private Chunk method_22353(int n, int n2) {
        _snowman = n - this.minX;
        _snowman = n2 - this.minZ;
        if (_snowman < 0 || _snowman >= this.chunks.length || _snowman < 0 || _snowman >= this.chunks[_snowman].length) {
            return new EmptyChunk(this.world, new ChunkPos(n, n2));
        }
        Chunk chunk = this.chunks[_snowman][_snowman];
        return chunk != null ? chunk : new EmptyChunk(this.world, new ChunkPos(n, n2));
    }

    @Override
    public WorldBorder getWorldBorder() {
        return this.world.getWorldBorder();
    }

    @Override
    public BlockView getExistingChunk(int chunkX, int chunkZ) {
        return this.method_22353(chunkX, chunkZ);
    }

    @Override
    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        Chunk chunk = this.method_22354(pos);
        return chunk.getBlockEntity(pos);
    }

    @Override
    public BlockState getBlockState(BlockPos pos) {
        if (World.isOutOfBuildLimitVertically(pos)) {
            return Blocks.AIR.getDefaultState();
        }
        Chunk chunk = this.method_22354(pos);
        return chunk.getBlockState(pos);
    }

    @Override
    public Stream<VoxelShape> getEntityCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate) {
        return Stream.empty();
    }

    @Override
    public Stream<VoxelShape> getCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate) {
        return this.getBlockCollisions(entity, box);
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        if (World.isOutOfBuildLimitVertically(pos)) {
            return Fluids.EMPTY.getDefaultState();
        }
        Chunk chunk = this.method_22354(pos);
        return chunk.getFluidState(pos);
    }
}

