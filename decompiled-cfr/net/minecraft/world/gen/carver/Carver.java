/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.mojang.serialization.Codec
 *  org.apache.commons.lang3.mutable.MutableBoolean
 */
package net.minecraft.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.carver.NetherCaveCarver;
import net.minecraft.world.gen.carver.RavineCarver;
import net.minecraft.world.gen.carver.UnderwaterCaveCarver;
import net.minecraft.world.gen.carver.UnderwaterRavineCarver;
import org.apache.commons.lang3.mutable.MutableBoolean;

public abstract class Carver<C extends CarverConfig> {
    public static final Carver<ProbabilityConfig> CAVE = Carver.register("cave", new CaveCarver(ProbabilityConfig.CODEC, 256));
    public static final Carver<ProbabilityConfig> NETHER_CAVE = Carver.register("nether_cave", new NetherCaveCarver(ProbabilityConfig.CODEC));
    public static final Carver<ProbabilityConfig> CANYON = Carver.register("canyon", new RavineCarver(ProbabilityConfig.CODEC));
    public static final Carver<ProbabilityConfig> UNDERWATER_CANYON = Carver.register("underwater_canyon", new UnderwaterRavineCarver(ProbabilityConfig.CODEC));
    public static final Carver<ProbabilityConfig> UNDERWATER_CAVE = Carver.register("underwater_cave", new UnderwaterCaveCarver(ProbabilityConfig.CODEC));
    protected static final BlockState AIR = Blocks.AIR.getDefaultState();
    protected static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    protected static final FluidState WATER = Fluids.WATER.getDefaultState();
    protected static final FluidState LAVA = Fluids.LAVA.getDefaultState();
    protected Set<Block> alwaysCarvableBlocks = ImmutableSet.of((Object)Blocks.STONE, (Object)Blocks.GRANITE, (Object)Blocks.DIORITE, (Object)Blocks.ANDESITE, (Object)Blocks.DIRT, (Object)Blocks.COARSE_DIRT, (Object[])new Block[]{Blocks.PODZOL, Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM, Blocks.SNOW, Blocks.PACKED_ICE});
    protected Set<Fluid> carvableFluids = ImmutableSet.of((Object)Fluids.WATER);
    private final Codec<ConfiguredCarver<C>> codec;
    protected final int heightLimit;

    private static <C extends CarverConfig, F extends Carver<C>> F register(String string, F f) {
        return (F)Registry.register(Registry.CARVER, string, f);
    }

    public Carver(Codec<C> configCodec, int heightLimit) {
        this.heightLimit = heightLimit;
        this.codec = configCodec.fieldOf("config").xmap(this::configure, ConfiguredCarver::getConfig).codec();
    }

    public ConfiguredCarver<C> configure(C config) {
        return new ConfiguredCarver<C>(this, config);
    }

    public Codec<ConfiguredCarver<C>> getCodec() {
        return this.codec;
    }

    public int getBranchFactor() {
        return 4;
    }

    protected boolean carveRegion(Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, int seaLevel, int chunkX, int chunkZ, double x, double y, double z, double yaw, double pitch, BitSet carvingMask) {
        Random random = new Random(seed + (long)chunkX + (long)chunkZ);
        double _snowman2 = chunkX * 16 + 8;
        double _snowman3 = chunkZ * 16 + 8;
        if (x < _snowman2 - 16.0 - yaw * 2.0 || z < _snowman3 - 16.0 - yaw * 2.0 || x > _snowman2 + 16.0 + yaw * 2.0 || z > _snowman3 + 16.0 + yaw * 2.0) {
            return false;
        }
        int _snowman4 = Math.max(MathHelper.floor(x - yaw) - chunkX * 16 - 1, 0);
        if (this.isRegionUncarvable(chunk, chunkX, chunkZ, _snowman4, _snowman = Math.min(MathHelper.floor(x + yaw) - chunkX * 16 + 1, 16), _snowman = Math.max(MathHelper.floor(y - pitch) - 1, 1), _snowman = Math.min(MathHelper.floor(y + pitch) + 1, this.heightLimit - 8), _snowman = Math.max(MathHelper.floor(z - yaw) - chunkZ * 16 - 1, 0), _snowman = Math.min(MathHelper.floor(z + yaw) - chunkZ * 16 + 1, 16))) {
            return false;
        }
        boolean _snowman5 = false;
        BlockPos.Mutable _snowman6 = new BlockPos.Mutable();
        BlockPos.Mutable _snowman7 = new BlockPos.Mutable();
        BlockPos.Mutable _snowman8 = new BlockPos.Mutable();
        for (int i = _snowman4; i < _snowman; ++i) {
            _snowman = i + chunkX * 16;
            double d = ((double)_snowman + 0.5 - x) / yaw;
            for (int j = _snowman; j < _snowman; ++j) {
                _snowman = j + chunkZ * 16;
                double d2 = ((double)_snowman + 0.5 - z) / yaw;
                if (d * d + d2 * d2 >= 1.0) continue;
                MutableBoolean _snowman9 = new MutableBoolean(false);
                for (int k = _snowman; k > _snowman; --k) {
                    double d3 = ((double)k - 0.5 - y) / pitch;
                    if (this.isPositionExcluded(d, d3, d2, k)) continue;
                    _snowman5 |= this.carveAtPoint(chunk, posToBiome, carvingMask, random, _snowman6, _snowman7, _snowman8, seaLevel, chunkX, chunkZ, _snowman, _snowman, i, k, j, _snowman9);
                }
            }
        }
        return _snowman5;
    }

    protected boolean carveAtPoint(Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, Random random, BlockPos.Mutable mutable, BlockPos.Mutable mutable2, BlockPos.Mutable mutable3, int seaLevel, int mainChunkX, int mainChunkZ, int x, int z, int relativeX, int y, int relativeZ, MutableBoolean mutableBoolean) {
        int n = relativeX | relativeZ << 4 | y << 8;
        if (carvingMask.get(n)) {
            return false;
        }
        carvingMask.set(n);
        mutable.set(x, y, z);
        BlockState _snowman2 = chunk.getBlockState(mutable);
        BlockState _snowman3 = chunk.getBlockState(mutable2.set(mutable, Direction.UP));
        if (_snowman2.isOf(Blocks.GRASS_BLOCK) || _snowman2.isOf(Blocks.MYCELIUM)) {
            mutableBoolean.setTrue();
        }
        if (!this.canCarveBlock(_snowman2, _snowman3)) {
            return false;
        }
        if (y < 11) {
            chunk.setBlockState(mutable, LAVA.getBlockState(), false);
        } else {
            chunk.setBlockState(mutable, CAVE_AIR, false);
            if (mutableBoolean.isTrue()) {
                mutable3.set(mutable, Direction.DOWN);
                if (chunk.getBlockState(mutable3).isOf(Blocks.DIRT)) {
                    chunk.setBlockState(mutable3, posToBiome.apply(mutable).getGenerationSettings().getSurfaceConfig().getTopMaterial(), false);
                }
            }
        }
        return true;
    }

    public abstract boolean carve(Chunk var1, Function<BlockPos, Biome> var2, Random var3, int var4, int var5, int var6, int var7, int var8, BitSet var9, C var10);

    public abstract boolean shouldCarve(Random var1, int var2, int var3, C var4);

    protected boolean canAlwaysCarveBlock(BlockState state) {
        return this.alwaysCarvableBlocks.contains(state.getBlock());
    }

    protected boolean canCarveBlock(BlockState state, BlockState stateAbove) {
        return this.canAlwaysCarveBlock(state) || (state.isOf(Blocks.SAND) || state.isOf(Blocks.GRAVEL)) && !stateAbove.getFluidState().isIn(FluidTags.WATER);
    }

    protected boolean isRegionUncarvable(Chunk chunk, int mainChunkX, int mainChunkZ, int relMinX, int relMaxX, int minY, int maxY, int relMinZ, int relMaxZ) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = relMinX; i < relMaxX; ++i) {
            for (_snowman = relMinZ; _snowman < relMaxZ; ++_snowman) {
                for (_snowman = minY - 1; _snowman <= maxY + 1; ++_snowman) {
                    if (this.carvableFluids.contains(chunk.getFluidState(mutable.set(i + mainChunkX * 16, _snowman, _snowman + mainChunkZ * 16)).getFluid())) {
                        return true;
                    }
                    if (_snowman == maxY + 1 || this.isOnBoundary(relMinX, relMaxX, relMinZ, relMaxZ, i, _snowman)) continue;
                    _snowman = maxY;
                }
            }
        }
        return false;
    }

    private boolean isOnBoundary(int minX, int maxX, int minZ, int maxZ, int x, int z) {
        return x == minX || x == maxX - 1 || z == minZ || z == maxZ - 1;
    }

    protected boolean canCarveBranch(int mainChunkX, int mainChunkZ, double x, double z, int branch, int branchCount, float baseWidth) {
        double d = mainChunkX * 16 + 8;
        _snowman = x - d;
        _snowman = mainChunkZ * 16 + 8;
        _snowman = z - _snowman;
        _snowman = branchCount - branch;
        _snowman = baseWidth + 2.0f + 16.0f;
        return _snowman * _snowman + _snowman * _snowman - _snowman * _snowman <= _snowman * _snowman;
    }

    protected abstract boolean isPositionExcluded(double var1, double var3, double var5, int var7);
}

