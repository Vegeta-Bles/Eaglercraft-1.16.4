/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectListIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.gen.chunk;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.noise.SimplexNoiseSampler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;

public final class NoiseChunkGenerator
extends ChunkGenerator {
    public static final Codec<NoiseChunkGenerator> CODEC = RecordCodecBuilder.create(instance -> instance.group((App)BiomeSource.CODEC.fieldOf("biome_source").forGetter(noiseChunkGenerator -> noiseChunkGenerator.populationSource), (App)Codec.LONG.fieldOf("seed").stable().forGetter(noiseChunkGenerator -> noiseChunkGenerator.seed), (App)ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(noiseChunkGenerator -> noiseChunkGenerator.settings)).apply((Applicative)instance, instance.stable(NoiseChunkGenerator::new)));
    private static final float[] NOISE_WEIGHT_TABLE = Util.make(new float[13824], array -> {
        for (int i = 0; i < 24; ++i) {
            for (_snowman = 0; _snowman < 24; ++_snowman) {
                for (_snowman = 0; _snowman < 24; ++_snowman) {
                    array[i * 24 * 24 + _snowman * 24 + _snowman] = (float)NoiseChunkGenerator.calculateNoiseWeight(_snowman - 12, _snowman - 12, i - 12);
                }
            }
        }
    });
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], array -> {
        for (int i = -2; i <= 2; ++i) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                array[i + 2 + (_snowman + 2) * 5] = _snowman = 10.0f / MathHelper.sqrt((float)(i * i + _snowman * _snowman) + 0.2f);
            }
        }
    });
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private final int verticalNoiseResolution;
    private final int horizontalNoiseResolution;
    private final int noiseSizeX;
    private final int noiseSizeY;
    private final int noiseSizeZ;
    protected final ChunkRandom random;
    private final OctavePerlinNoiseSampler lowerInterpolatedNoise;
    private final OctavePerlinNoiseSampler upperInterpolatedNoise;
    private final OctavePerlinNoiseSampler interpolationNoise;
    private final NoiseSampler surfaceDepthNoise;
    private final OctavePerlinNoiseSampler densityNoise;
    @Nullable
    private final SimplexNoiseSampler islandNoise;
    protected final BlockState defaultBlock;
    protected final BlockState defaultFluid;
    private final long seed;
    protected final Supplier<ChunkGeneratorSettings> settings;
    private final int worldHeight;

    public NoiseChunkGenerator(BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        this(biomeSource, biomeSource, seed, settings);
    }

    private NoiseChunkGenerator(BiomeSource populationSource, BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
        super(populationSource, biomeSource, settings.get().getStructuresConfig(), seed);
        this.seed = seed;
        ChunkGeneratorSettings chunkGeneratorSettings = settings.get();
        this.settings = settings;
        GenerationShapeConfig _snowman2 = chunkGeneratorSettings.getGenerationShapeConfig();
        this.worldHeight = _snowman2.getHeight();
        this.verticalNoiseResolution = _snowman2.getSizeVertical() * 4;
        this.horizontalNoiseResolution = _snowman2.getSizeHorizontal() * 4;
        this.defaultBlock = chunkGeneratorSettings.getDefaultBlock();
        this.defaultFluid = chunkGeneratorSettings.getDefaultFluid();
        this.noiseSizeX = 16 / this.horizontalNoiseResolution;
        this.noiseSizeY = _snowman2.getHeight() / this.verticalNoiseResolution;
        this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
        this.random = new ChunkRandom(seed);
        this.lowerInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.upperInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.interpolationNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
        this.surfaceDepthNoise = _snowman2.hasSimplexSurfaceNoise() ? new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0)) : new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-3, 0));
        this.random.consume(2620);
        this.densityNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        if (_snowman2.hasIslandNoiseOverride()) {
            ChunkRandom chunkRandom = new ChunkRandom(seed);
            chunkRandom.consume(17292);
            this.islandNoise = new SimplexNoiseSampler(chunkRandom);
        } else {
            this.islandNoise = null;
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new NoiseChunkGenerator(this.populationSource.withSeed(seed), seed, this.settings);
    }

    public boolean matchesSettings(long seed, RegistryKey<ChunkGeneratorSettings> settingsKey) {
        return this.seed == seed && this.settings.get().equals(settingsKey);
    }

    private double sampleNoise(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
        double d = 0.0;
        _snowman = 0.0;
        _snowman = 0.0;
        boolean _snowman2 = true;
        _snowman = 1.0;
        for (int i = 0; i < 16; ++i) {
            double d2 = OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalScale * _snowman);
            _snowman = OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalScale * _snowman);
            _snowman = OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalScale * _snowman);
            _snowman = verticalScale * _snowman;
            PerlinNoiseSampler _snowman3 = this.lowerInterpolatedNoise.getOctave(i);
            if (_snowman3 != null) {
                d += _snowman3.sample(d2, _snowman, _snowman, _snowman, (double)y * _snowman) / _snowman;
            }
            if ((_snowman = this.upperInterpolatedNoise.getOctave(i)) != null) {
                _snowman += _snowman.sample(d2, _snowman, _snowman, _snowman, (double)y * _snowman) / _snowman;
            }
            if (i < 8 && (_snowman = this.interpolationNoise.getOctave(i)) != null) {
                _snowman += _snowman.sample(OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalStretch * _snowman), OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalStretch * _snowman), OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalStretch * _snowman), verticalStretch * _snowman, (double)y * verticalStretch * _snowman) / _snowman;
            }
            _snowman /= 2.0;
        }
        return MathHelper.clampedLerp(d / 512.0, _snowman / 512.0, (_snowman / 10.0 + 1.0) / 2.0);
    }

    private double[] sampleNoiseColumn(int x, int z) {
        double[] dArray = new double[this.noiseSizeY + 1];
        this.sampleNoiseColumn(dArray, x, z);
        return dArray;
    }

    private void sampleNoiseColumn(double[] buffer, int x, int z) {
        double _snowman9;
        double _snowman7;
        double _snowman6;
        double _snowman8;
        GenerationShapeConfig generationShapeConfig = this.settings.get().getGenerationShapeConfig();
        if (this.islandNoise != null) {
            _snowman8 = TheEndBiomeSource.getNoiseAt(this.islandNoise, x, z) - 8.0f;
            _snowman9 = _snowman8 > 0.0 ? 0.25 : 1.0;
        } else {
            float f;
            float f2 = 0.0f;
            f = 0.0f;
            _snowman = 0.0f;
            int _snowman2 = 2;
            int _snowman3 = this.getSeaLevel();
            f3 = this.populationSource.getBiomeForNoiseGen(x, _snowman3, z).getDepth();
            for (int i = -2; i <= 2; ++i) {
                for (_snowman = -2; _snowman <= 2; ++_snowman) {
                    float f3;
                    float f4;
                    Biome biome = this.populationSource.getBiomeForNoiseGen(x + i, _snowman3, z + _snowman);
                    float _snowman4 = biome.getDepth();
                    float _snowman5 = biome.getScale();
                    if (generationShapeConfig.isAmplified() && _snowman4 > 0.0f) {
                        f4 = 1.0f + _snowman4 * 2.0f;
                        _snowman = 1.0f + _snowman5 * 4.0f;
                    } else {
                        f4 = _snowman4;
                        _snowman = _snowman5;
                    }
                    _snowman = _snowman4 > f3 ? 0.5f : 1.0f;
                    _snowman = _snowman * BIOME_WEIGHT_TABLE[i + 2 + (_snowman + 2) * 5] / (f4 + 2.0f);
                    f2 += _snowman * _snowman;
                    f += f4 * _snowman;
                    _snowman += _snowman;
                }
            }
            _snowman = f / _snowman;
            _snowman = f2 / _snowman;
            _snowman6 = _snowman * 0.5f - 0.125f;
            _snowman7 = _snowman * 0.9f + 0.1f;
            _snowman8 = _snowman6 * 0.265625;
            _snowman9 = 96.0 / _snowman7;
        }
        double d = 684.412 * generationShapeConfig.getSampling().getXZScale();
        _snowman = 684.412 * generationShapeConfig.getSampling().getYScale();
        _snowman = d / generationShapeConfig.getSampling().getXZFactor();
        _snowman = _snowman / generationShapeConfig.getSampling().getYFactor();
        _snowman6 = generationShapeConfig.getTopSlide().getTarget();
        _snowman7 = generationShapeConfig.getTopSlide().getSize();
        _snowman = generationShapeConfig.getTopSlide().getOffset();
        _snowman = generationShapeConfig.getBottomSlide().getTarget();
        _snowman = generationShapeConfig.getBottomSlide().getSize();
        _snowman = generationShapeConfig.getBottomSlide().getOffset();
        _snowman = generationShapeConfig.hasRandomDensityOffset() ? this.getRandomDensityAt(x, z) : 0.0;
        _snowman = generationShapeConfig.getDensityFactor();
        _snowman = generationShapeConfig.getDensityOffset();
        for (int i = 0; i <= this.noiseSizeY; ++i) {
            double d2 = this.sampleNoise(x, i, z, d, _snowman, _snowman, _snowman);
            _snowman = 1.0 - (double)i * 2.0 / (double)this.noiseSizeY + _snowman;
            _snowman = _snowman * _snowman + _snowman;
            _snowman = (_snowman + _snowman8) * _snowman9;
            d2 = _snowman > 0.0 ? (d2 += _snowman * 4.0) : (d2 += _snowman);
            if (_snowman7 > 0.0) {
                _snowman = ((double)(this.noiseSizeY - i) - _snowman) / _snowman7;
                d2 = MathHelper.clampedLerp(_snowman6, d2, _snowman);
            }
            if (_snowman > 0.0) {
                _snowman = ((double)i - _snowman) / _snowman;
                d2 = MathHelper.clampedLerp(_snowman, d2, _snowman);
            }
            buffer[i] = d2;
        }
    }

    private double getRandomDensityAt(int x, int z) {
        double d = this.densityNoise.sample(x * 200, 10.0, z * 200, 1.0, 0.0, true);
        _snowman = d < 0.0 ? -d * 0.3 : d;
        _snowman = _snowman * 24.575625 - 2.0;
        if (_snowman < 0.0) {
            return _snowman * 0.009486607142857142;
        }
        return Math.min(_snowman, 1.0) * 0.006640625;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return this.sampleHeightmap(x, z, null, heightmapType.getBlockPredicate());
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        BlockState[] blockStateArray = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
        this.sampleHeightmap(x, z, blockStateArray, null);
        return new VerticalBlockSample(blockStateArray);
    }

    private int sampleHeightmap(int x, int z, @Nullable BlockState[] states, @Nullable Predicate<BlockState> predicate) {
        int n = Math.floorDiv(x, this.horizontalNoiseResolution);
        _snowman = Math.floorDiv(z, this.horizontalNoiseResolution);
        _snowman = Math.floorMod(x, this.horizontalNoiseResolution);
        _snowman = Math.floorMod(z, this.horizontalNoiseResolution);
        double _snowman2 = (double)_snowman / (double)this.horizontalNoiseResolution;
        double _snowman3 = (double)_snowman / (double)this.horizontalNoiseResolution;
        double[][] _snowman4 = new double[][]{this.sampleNoiseColumn(n, _snowman), this.sampleNoiseColumn(n, _snowman + 1), this.sampleNoiseColumn(n + 1, _snowman), this.sampleNoiseColumn(n + 1, _snowman + 1)};
        for (_snowman = this.noiseSizeY - 1; _snowman >= 0; --_snowman) {
            double d = _snowman4[0][_snowman];
            _snowman = _snowman4[1][_snowman];
            _snowman = _snowman4[2][_snowman];
            _snowman = _snowman4[3][_snowman];
            _snowman = _snowman4[0][_snowman + 1];
            _snowman = _snowman4[1][_snowman + 1];
            _snowman = _snowman4[2][_snowman + 1];
            _snowman = _snowman4[3][_snowman + 1];
            for (int i = this.verticalNoiseResolution - 1; i >= 0; --i) {
                double d2 = (double)i / (double)this.verticalNoiseResolution;
                _snowman = MathHelper.lerp3(d2, _snowman2, _snowman3, d, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
                int _snowman5 = _snowman * this.verticalNoiseResolution + i;
                BlockState _snowman6 = this.getBlockState(_snowman, _snowman5);
                if (states != null) {
                    states[_snowman5] = _snowman6;
                }
                if (predicate == null || !predicate.test(_snowman6)) continue;
                return _snowman5 + 1;
            }
        }
        return 0;
    }

    protected BlockState getBlockState(double density, int y) {
        BlockState blockState = density > 0.0 ? this.defaultBlock : (y < this.getSeaLevel() ? this.defaultFluid : AIR);
        return blockState;
    }

    @Override
    public void buildSurface(ChunkRegion region, Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int _snowman2 = chunkPos.x;
        int _snowman3 = chunkPos.z;
        ChunkRandom _snowman4 = new ChunkRandom();
        _snowman4.setTerrainSeed(_snowman2, _snowman3);
        _snowman = chunk.getPos();
        int _snowman5 = _snowman.getStartX();
        int _snowman6 = _snowman.getStartZ();
        double _snowman7 = 0.0625;
        BlockPos.Mutable _snowman8 = new BlockPos.Mutable();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                _snowman = _snowman5 + i;
                _snowman = _snowman6 + _snowman;
                _snowman = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, i, _snowman) + 1;
                double d = this.surfaceDepthNoise.sample((double)_snowman * 0.0625, (double)_snowman * 0.0625, 0.0625, (double)i * 0.0625) * 15.0;
                region.getBiome(_snowman8.set(_snowman5 + i, _snowman, _snowman6 + _snowman)).buildSurface(_snowman4, chunk, _snowman, _snowman, _snowman, d, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), region.getSeed());
            }
        }
        this.buildBedrock(chunk, _snowman4);
    }

    private void buildBedrock(Chunk chunk, Random random) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int _snowman2 = chunk.getPos().getStartX();
        int _snowman3 = chunk.getPos().getStartZ();
        ChunkGeneratorSettings _snowman4 = this.settings.get();
        int _snowman5 = _snowman4.getBedrockFloorY();
        int _snowman6 = this.worldHeight - 1 - _snowman4.getBedrockCeilingY();
        int _snowman7 = 5;
        boolean _snowman8 = _snowman6 + 4 >= 0 && _snowman6 < this.worldHeight;
        boolean bl = bl2 = _snowman5 + 4 >= 0 && _snowman5 < this.worldHeight;
        if (!_snowman8 && !bl2) {
            return;
        }
        for (BlockPos blockPos : BlockPos.iterate(_snowman2, 0, _snowman3, _snowman2 + 15, 0, _snowman3 + 15)) {
            boolean bl2;
            int n;
            if (_snowman8) {
                for (n = 0; n < 5; ++n) {
                    if (n > random.nextInt(5)) continue;
                    chunk.setBlockState(mutable.set(blockPos.getX(), _snowman6 - n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                }
            }
            if (!bl2) continue;
            for (n = 4; n >= 0; --n) {
                if (n > random.nextInt(5)) continue;
                chunk.setBlockState(mutable.set(blockPos.getX(), _snowman5 + n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
            }
        }
    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
        ObjectArrayList objectArrayList = new ObjectArrayList(10);
        _snowman = new ObjectArrayList(32);
        ChunkPos _snowman2 = chunk.getPos();
        int _snowman3 = _snowman2.x;
        int _snowman4 = _snowman2.z;
        int _snowman5 = _snowman3 << 4;
        int _snowman6 = _snowman4 << 4;
        for (StructureFeature<?> structureFeature : StructureFeature.JIGSAW_STRUCTURES) {
            accessor.getStructuresWithChildren(ChunkSectionPos.from(_snowman2, 0), structureFeature).forEach(arg_0 -> NoiseChunkGenerator.method_26983(_snowman2, (ObjectList)objectArrayList, _snowman5, _snowman6, (ObjectList)_snowman, arg_0));
        }
        double[][][] dArray = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];
        for (int i = 0; i < this.noiseSizeZ + 1; ++i) {
            dArray[0][i] = new double[this.noiseSizeY + 1];
            this.sampleNoiseColumn(dArray[0][i], _snowman3 * this.noiseSizeX, _snowman4 * this.noiseSizeZ + i);
            dArray[1][i] = new double[this.noiseSizeY + 1];
        }
        ProtoChunk protoChunk = (ProtoChunk)chunk;
        Heightmap _snowman7 = protoChunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap _snowman8 = protoChunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
        BlockPos.Mutable _snowman9 = new BlockPos.Mutable();
        ObjectListIterator _snowman10 = objectArrayList.iterator();
        ObjectListIterator _snowman11 = _snowman.iterator();
        for (int i = 0; i < this.noiseSizeX; ++i) {
            for (_snowman = 0; _snowman < this.noiseSizeZ + 1; ++_snowman) {
                this.sampleNoiseColumn(dArray[1][_snowman], _snowman3 * this.noiseSizeX + i + 1, _snowman4 * this.noiseSizeZ + _snowman);
            }
            for (_snowman = 0; _snowman < this.noiseSizeZ; ++_snowman) {
                ChunkSection chunkSection = protoChunk.getSection(15);
                chunkSection.lock();
                for (int j = this.noiseSizeY - 1; j >= 0; --j) {
                    double d = dArray[0][_snowman][j];
                    _snowman = dArray[0][_snowman + 1][j];
                    _snowman = dArray[1][_snowman][j];
                    _snowman = dArray[1][_snowman + 1][j];
                    _snowman = dArray[0][_snowman][j + 1];
                    _snowman = dArray[0][_snowman + 1][j + 1];
                    _snowman = dArray[1][_snowman][j + 1];
                    _snowman = dArray[1][_snowman + 1][j + 1];
                    for (int k = this.verticalNoiseResolution - 1; k >= 0; --k) {
                        _snowman = j * this.verticalNoiseResolution + k;
                        _snowman = _snowman & 0xF;
                        _snowman = _snowman >> 4;
                        if (chunkSection.getYOffset() >> 4 != _snowman) {
                            chunkSection.unlock();
                            chunkSection = protoChunk.getSection(_snowman);
                            chunkSection.lock();
                        }
                        double _snowman12 = (double)k / (double)this.verticalNoiseResolution;
                        double _snowman13 = MathHelper.lerp(_snowman12, d, _snowman);
                        double _snowman14 = MathHelper.lerp(_snowman12, _snowman, _snowman);
                        double _snowman15 = MathHelper.lerp(_snowman12, _snowman, _snowman);
                        double _snowman16 = MathHelper.lerp(_snowman12, _snowman, _snowman);
                        for (_snowman = 0; _snowman < this.horizontalNoiseResolution; ++_snowman) {
                            _snowman = _snowman5 + i * this.horizontalNoiseResolution + _snowman;
                            _snowman = _snowman & 0xF;
                            double d2 = (double)_snowman / (double)this.horizontalNoiseResolution;
                            _snowman = MathHelper.lerp(d2, _snowman13, _snowman14);
                            _snowman = MathHelper.lerp(d2, _snowman15, _snowman16);
                            for (int i2 = 0; i2 < this.horizontalNoiseResolution; ++i2) {
                                int _snowman19;
                                int _snowman18;
                                _snowman = _snowman6 + _snowman * this.horizontalNoiseResolution + i2;
                                _snowman = _snowman & 0xF;
                                double d3 = (double)i2 / (double)this.horizontalNoiseResolution;
                                _snowman = MathHelper.lerp(d3, _snowman, _snowman);
                                _snowman = MathHelper.clamp(_snowman / 200.0, -1.0, 1.0);
                                _snowman = _snowman / 2.0 - _snowman * _snowman * _snowman / 24.0;
                                while (_snowman10.hasNext()) {
                                    Object object = (StructurePiece)_snowman10.next();
                                    BlockBox _snowman17 = ((StructurePiece)object).getBoundingBox();
                                    _snowman18 = Math.max(0, Math.max(_snowman17.minX - _snowman, _snowman - _snowman17.maxX));
                                    _snowman19 = _snowman - (_snowman17.minY + (object instanceof PoolStructurePiece ? ((PoolStructurePiece)object).getGroundLevelDelta() : 0));
                                    int _snowman20 = Math.max(0, Math.max(_snowman17.minZ - _snowman, _snowman - _snowman17.maxZ));
                                    _snowman += NoiseChunkGenerator.getNoiseWeight(_snowman18, _snowman19, _snowman20) * 0.8;
                                }
                                _snowman10.back(objectArrayList.size());
                                while (_snowman11.hasNext()) {
                                    object = (JigsawJunction)_snowman11.next();
                                    int _snowman21 = _snowman - ((JigsawJunction)object).getSourceX();
                                    _snowman18 = _snowman - ((JigsawJunction)object).getSourceGroundY();
                                    _snowman19 = _snowman - ((JigsawJunction)object).getSourceZ();
                                    _snowman += NoiseChunkGenerator.getNoiseWeight(_snowman21, _snowman18, _snowman19) * 0.4;
                                }
                                _snowman11.back(_snowman.size());
                                object = this.getBlockState(_snowman, _snowman);
                                if (object == AIR) continue;
                                if (((AbstractBlock.AbstractBlockState)object).getLuminance() != 0) {
                                    _snowman9.set(_snowman, _snowman, _snowman);
                                    protoChunk.addLightSource(_snowman9);
                                }
                                chunkSection.setBlockState(_snowman, _snowman, _snowman, (BlockState)object, false);
                                _snowman7.trackUpdate(_snowman, _snowman, _snowman, (BlockState)object);
                                _snowman8.trackUpdate(_snowman, _snowman, _snowman, (BlockState)object);
                            }
                        }
                    }
                }
                chunkSection.unlock();
            }
            double[][] dArray2 = dArray[0];
            dArray[0] = dArray[1];
            dArray[1] = dArray2;
        }
    }

    private static double getNoiseWeight(int x, int y, int z) {
        int n = x + 12;
        _snowman = y + 12;
        _snowman = z + 12;
        if (n < 0 || n >= 24) {
            return 0.0;
        }
        if (_snowman < 0 || _snowman >= 24) {
            return 0.0;
        }
        if (_snowman < 0 || _snowman >= 24) {
            return 0.0;
        }
        return NOISE_WEIGHT_TABLE[_snowman * 24 * 24 + n * 24 + _snowman];
    }

    private static double calculateNoiseWeight(int x, int y, int z) {
        double d = x * x + z * z;
        _snowman = (double)y + 0.5;
        _snowman = _snowman * _snowman;
        _snowman = Math.pow(Math.E, -(_snowman / 16.0 + d / 16.0));
        _snowman = -_snowman * MathHelper.fastInverseSqrt(_snowman / 2.0 + d / 2.0) / 2.0;
        return _snowman * _snowman;
    }

    @Override
    public int getWorldHeight() {
        return this.worldHeight;
    }

    @Override
    public int getSeaLevel() {
        return this.settings.get().getSeaLevel();
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        if (accessor.getStructureAt(pos, true, StructureFeature.SWAMP_HUT).hasChildren()) {
            if (group == SpawnGroup.MONSTER) {
                return StructureFeature.SWAMP_HUT.getMonsterSpawns();
            }
            if (group == SpawnGroup.CREATURE) {
                return StructureFeature.SWAMP_HUT.getCreatureSpawns();
            }
        }
        if (group == SpawnGroup.MONSTER) {
            if (accessor.getStructureAt(pos, false, StructureFeature.PILLAGER_OUTPOST).hasChildren()) {
                return StructureFeature.PILLAGER_OUTPOST.getMonsterSpawns();
            }
            if (accessor.getStructureAt(pos, false, StructureFeature.MONUMENT).hasChildren()) {
                return StructureFeature.MONUMENT.getMonsterSpawns();
            }
            if (accessor.getStructureAt(pos, true, StructureFeature.FORTRESS).hasChildren()) {
                return StructureFeature.FORTRESS.getMonsterSpawns();
            }
        }
        return super.getEntitySpawnList(biome, accessor, group, pos);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        if (this.settings.get().isMobGenerationDisabled()) {
            return;
        }
        int n = region.getCenterChunkX();
        _snowman = region.getCenterChunkZ();
        Biome _snowman2 = region.getBiome(new ChunkPos(n, _snowman).getStartPos());
        ChunkRandom _snowman3 = new ChunkRandom();
        _snowman3.setPopulationSeed(region.getSeed(), n << 4, _snowman << 4);
        SpawnHelper.populateEntities(region, _snowman2, n, _snowman, _snowman3);
    }

    private static /* synthetic */ void method_26983(ChunkPos chunkPos, ObjectList objectList3, int n, int n2, ObjectList objectList2, StructureStart start) {
        for (StructurePiece structurePiece : start.getChildren()) {
            ObjectList objectList3;
            if (!structurePiece.intersectsChunk(chunkPos, 12)) continue;
            if (structurePiece instanceof PoolStructurePiece) {
                PoolStructurePiece poolStructurePiece = (PoolStructurePiece)structurePiece;
                StructurePool.Projection _snowman2 = poolStructurePiece.getPoolElement().getProjection();
                if (_snowman2 == StructurePool.Projection.RIGID) {
                    objectList3.add((Object)poolStructurePiece);
                }
                for (JigsawJunction jigsawJunction : poolStructurePiece.getJunctions()) {
                    int n3 = jigsawJunction.getSourceX();
                    _snowman = jigsawJunction.getSourceZ();
                    if (n3 <= n - 12 || _snowman <= n2 - 12 || n3 >= n + 15 + 12 || _snowman >= n2 + 15 + 12) continue;
                    objectList2.add((Object)jigsawJunction);
                }
                continue;
            }
            objectList3.add((Object)structurePiece);
        }
    }
}

