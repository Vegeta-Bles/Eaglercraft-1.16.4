/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.chunk;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;

public class DebugChunkGenerator
extends ChunkGenerator {
    public static final Codec<DebugChunkGenerator> CODEC = RegistryLookupCodec.of(Registry.BIOME_KEY).xmap(DebugChunkGenerator::new, DebugChunkGenerator::getBiomeRegistry).stable().codec();
    private static final List<BlockState> BLOCK_STATES = StreamSupport.stream(Registry.BLOCK.spliterator(), false).flatMap(block -> block.getStateManager().getStates().stream()).collect(Collectors.toList());
    private static final int X_SIDE_LENGTH = MathHelper.ceil(MathHelper.sqrt(BLOCK_STATES.size()));
    private static final int Z_SIDE_LENGTH = MathHelper.ceil((float)BLOCK_STATES.size() / (float)X_SIDE_LENGTH);
    protected static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected static final BlockState BARRIER = Blocks.BARRIER.getDefaultState();
    private final Registry<Biome> biomeRegistry;

    public DebugChunkGenerator(Registry<Biome> biomeRegistry) {
        super(new FixedBiomeSource(biomeRegistry.getOrThrow(BiomeKeys.PLAINS)), new StructuresConfig(false));
        this.biomeRegistry = biomeRegistry;
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
    }

    @Override
    public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int _snowman2 = region.getCenterChunkX();
        int _snowman3 = region.getCenterChunkZ();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                _snowman = (_snowman2 << 4) + i;
                _snowman = (_snowman3 << 4) + _snowman;
                region.setBlockState(mutable.set(_snowman, 60, _snowman), BARRIER, 2);
                BlockState blockState = DebugChunkGenerator.getBlockState(_snowman, _snowman);
                if (blockState == null) continue;
                region.setBlockState(mutable.set(_snowman, 70, _snowman), blockState, 2);
            }
        }
    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        return new VerticalBlockSample(new BlockState[0]);
    }

    public static BlockState getBlockState(int x, int z) {
        BlockState blockState = AIR;
        if (x > 0 && z > 0 && x % 2 != 0 && z % 2 != 0 && (x /= 2) <= X_SIDE_LENGTH && (z /= 2) <= Z_SIDE_LENGTH && (_snowman = MathHelper.abs(x * X_SIDE_LENGTH + z)) < BLOCK_STATES.size()) {
            blockState = BLOCK_STATES.get(_snowman);
        }
        return blockState;
    }
}

