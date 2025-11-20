/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.chunk;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

public class FlatChunkGenerator
extends ChunkGenerator {
    public static final Codec<FlatChunkGenerator> CODEC = FlatChunkGeneratorConfig.CODEC.fieldOf("settings").xmap(FlatChunkGenerator::new, FlatChunkGenerator::getConfig).codec();
    private final FlatChunkGeneratorConfig config;

    public FlatChunkGenerator(FlatChunkGeneratorConfig config) {
        super(new FixedBiomeSource(config.createBiome()), new FixedBiomeSource(config.getBiome()), config.getStructuresConfig(), 0L);
        this.config = config;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    public FlatChunkGeneratorConfig getConfig() {
        return this.config;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
    }

    @Override
    public int getSpawnHeight() {
        BlockState[] blockStateArray = this.config.getLayerBlocks();
        for (int i = 0; i < blockStateArray.length; ++i) {
            BlockState blockState = _snowman = blockStateArray[i] == null ? Blocks.AIR.getDefaultState() : blockStateArray[i];
            if (Heightmap.Type.MOTION_BLOCKING.getBlockPredicate().test(_snowman)) continue;
            return i - 1;
        }
        return blockStateArray.length;
    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        BlockState[] blockStateArray = this.config.getLayerBlocks();
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        Heightmap _snowman3 = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap _snowman4 = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        for (int i = 0; i < blockStateArray.length; ++i) {
            BlockState blockState = blockStateArray[i];
            if (blockState == null) continue;
            for (int j = 0; j < 16; ++j) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    chunk.setBlockState(_snowman2.set(j, i, _snowman), blockState, false);
                    _snowman3.trackUpdate(j, i, _snowman, blockState);
                    _snowman4.trackUpdate(j, i, _snowman, blockState);
                }
            }
        }
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        BlockState[] blockStateArray = this.config.getLayerBlocks();
        for (int i = blockStateArray.length - 1; i >= 0; --i) {
            BlockState blockState = blockStateArray[i];
            if (blockState == null || !heightmapType.getBlockPredicate().test(blockState)) continue;
            return i + 1;
        }
        return 0;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        return new VerticalBlockSample((BlockState[])Arrays.stream(this.config.getLayerBlocks()).map(state -> state == null ? Blocks.AIR.getDefaultState() : state).toArray(BlockState[]::new));
    }
}

