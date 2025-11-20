/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.server.network;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

public class SpawnLocating {
    @Nullable
    protected static BlockPos findOverworldSpawn(ServerWorld world, int x, int z, boolean validSpawnNeeded) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, 0, z);
        Biome _snowman2 = world.getBiome(mutable);
        boolean _snowman3 = world.getDimension().hasCeiling();
        BlockState _snowman4 = _snowman2.getGenerationSettings().getSurfaceConfig().getTopMaterial();
        if (validSpawnNeeded && !_snowman4.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        }
        WorldChunk _snowman5 = world.getChunk(x >> 4, z >> 4);
        int n = _snowman = _snowman3 ? world.getChunkManager().getChunkGenerator().getSpawnHeight() : _snowman5.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 0xF, z & 0xF);
        if (_snowman < 0) {
            return null;
        }
        int _snowman6 = _snowman5.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 0xF, z & 0xF);
        if (_snowman6 <= _snowman && _snowman6 > _snowman5.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x & 0xF, z & 0xF)) {
            return null;
        }
        for (int i = _snowman + 1; i >= 0; --i) {
            mutable.set(x, i, z);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) break;
            if (!blockState.equals(_snowman4)) continue;
            return ((BlockPos)mutable.up()).toImmutable();
        }
        return null;
    }

    @Nullable
    public static BlockPos findServerSpawnPoint(ServerWorld world, ChunkPos chunkPos, boolean validSpawnNeeded) {
        for (int i = chunkPos.getStartX(); i <= chunkPos.getEndX(); ++i) {
            for (_snowman = chunkPos.getStartZ(); _snowman <= chunkPos.getEndZ(); ++_snowman) {
                BlockPos blockPos = SpawnLocating.findOverworldSpawn(world, i, _snowman, validSpawnNeeded);
                if (blockPos == null) continue;
                return blockPos;
            }
        }
        return null;
    }
}

