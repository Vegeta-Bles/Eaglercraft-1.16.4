package net.minecraft.world.gen.chunk;

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
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.structure.JigsawJunction;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
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
import net.minecraft.world.gen.feature.StructureFeature;

public final class NoiseChunkGenerator extends ChunkGenerator {
   public static final Codec<NoiseChunkGenerator> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(
               BiomeSource.CODEC.fieldOf("biome_source").forGetter(_snowmanx -> _snowmanx.populationSource),
               Codec.LONG.fieldOf("seed").stable().forGetter(_snowmanx -> _snowmanx.seed),
               ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(_snowmanx -> _snowmanx.settings)
            )
            .apply(_snowman, _snowman.stable(NoiseChunkGenerator::new))
   );
   private static final float[] NOISE_WEIGHT_TABLE = Util.make(new float[13824], array -> {
      for (int _snowman = 0; _snowman < 24; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 24; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 24; _snowmanxx++) {
               array[_snowman * 24 * 24 + _snowmanx * 24 + _snowmanxx] = (float)calculateNoiseWeight(_snowmanx - 12, _snowmanxx - 12, _snowman - 12);
            }
         }
      }
   });
   private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], array -> {
      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            float _snowmanxx = 10.0F / MathHelper.sqrt((float)(_snowman * _snowman + _snowmanx * _snowmanx) + 0.2F);
            array[_snowman + 2 + (_snowmanx + 2) * 5] = _snowmanxx;
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
      ChunkGeneratorSettings _snowman = settings.get();
      this.settings = settings;
      GenerationShapeConfig _snowmanx = _snowman.getGenerationShapeConfig();
      this.worldHeight = _snowmanx.getHeight();
      this.verticalNoiseResolution = _snowmanx.getSizeVertical() * 4;
      this.horizontalNoiseResolution = _snowmanx.getSizeHorizontal() * 4;
      this.defaultBlock = _snowman.getDefaultBlock();
      this.defaultFluid = _snowman.getDefaultFluid();
      this.noiseSizeX = 16 / this.horizontalNoiseResolution;
      this.noiseSizeY = _snowmanx.getHeight() / this.verticalNoiseResolution;
      this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
      this.random = new ChunkRandom(seed);
      this.lowerInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      this.upperInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      this.interpolationNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
      this.surfaceDepthNoise = (NoiseSampler)(_snowmanx.hasSimplexSurfaceNoise()
         ? new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0))
         : new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-3, 0)));
      this.random.consume(2620);
      this.densityNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      if (_snowmanx.hasIslandNoiseOverride()) {
         ChunkRandom _snowmanxx = new ChunkRandom(seed);
         _snowmanxx.consume(17292);
         this.islandNoise = new SimplexNoiseSampler(_snowmanxx);
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
      double _snowman = 0.0;
      double _snowmanx = 0.0;
      double _snowmanxx = 0.0;
      boolean _snowmanxxx = true;
      double _snowmanxxxx = 1.0;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16; _snowmanxxxxx++) {
         double _snowmanxxxxxx = OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalScale * _snowmanxxxx);
         double _snowmanxxxxxxx = OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalScale * _snowmanxxxx);
         double _snowmanxxxxxxxx = OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalScale * _snowmanxxxx);
         double _snowmanxxxxxxxxx = verticalScale * _snowmanxxxx;
         PerlinNoiseSampler _snowmanxxxxxxxxxx = this.lowerInterpolatedNoise.getOctave(_snowmanxxxxx);
         if (_snowmanxxxxxxxxxx != null) {
            _snowman += _snowmanxxxxxxxxxx.sample(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)y * _snowmanxxxxxxxxx) / _snowmanxxxx;
         }

         PerlinNoiseSampler _snowmanxxxxxxxxxxx = this.upperInterpolatedNoise.getOctave(_snowmanxxxxx);
         if (_snowmanxxxxxxxxxxx != null) {
            _snowmanx += _snowmanxxxxxxxxxxx.sample(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, (double)y * _snowmanxxxxxxxxx) / _snowmanxxxx;
         }

         if (_snowmanxxxxx < 8) {
            PerlinNoiseSampler _snowmanxxxxxxxxxxxx = this.interpolationNoise.getOctave(_snowmanxxxxx);
            if (_snowmanxxxxxxxxxxxx != null) {
               _snowmanxx += _snowmanxxxxxxxxxxxx.sample(
                     OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalStretch * _snowmanxxxx),
                     OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalStretch * _snowmanxxxx),
                     OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalStretch * _snowmanxxxx),
                     verticalStretch * _snowmanxxxx,
                     (double)y * verticalStretch * _snowmanxxxx
                  )
                  / _snowmanxxxx;
            }
         }

         _snowmanxxxx /= 2.0;
      }

      return MathHelper.clampedLerp(_snowman / 512.0, _snowmanx / 512.0, (_snowmanxx / 10.0 + 1.0) / 2.0);
   }

   private double[] sampleNoiseColumn(int x, int z) {
      double[] _snowman = new double[this.noiseSizeY + 1];
      this.sampleNoiseColumn(_snowman, x, z);
      return _snowman;
   }

   private void sampleNoiseColumn(double[] buffer, int x, int z) {
      GenerationShapeConfig _snowman = this.settings.get().getGenerationShapeConfig();
      double _snowmanx;
      double _snowmanxx;
      if (this.islandNoise != null) {
         _snowmanx = (double)(TheEndBiomeSource.getNoiseAt(this.islandNoise, x, z) - 8.0F);
         if (_snowmanx > 0.0) {
            _snowmanxx = 0.25;
         } else {
            _snowmanxx = 1.0;
         }
      } else {
         float _snowmanxxx = 0.0F;
         float _snowmanxxxx = 0.0F;
         float _snowmanxxxxx = 0.0F;
         int _snowmanxxxxxx = 2;
         int _snowmanxxxxxxx = this.getSeaLevel();
         float _snowmanxxxxxxxx = this.populationSource.getBiomeForNoiseGen(x, _snowmanxxxxxxx, z).getDepth();

         for (int _snowmanxxxxxxxxx = -2; _snowmanxxxxxxxxx <= 2; _snowmanxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxx = -2; _snowmanxxxxxxxxxx <= 2; _snowmanxxxxxxxxxx++) {
               Biome _snowmanxxxxxxxxxxx = this.populationSource.getBiomeForNoiseGen(x + _snowmanxxxxxxxxx, _snowmanxxxxxxx, z + _snowmanxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getDepth();
               float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getScale();
               float _snowmanxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxx;
               if (_snowman.isAmplified() && _snowmanxxxxxxxxxxxx > 0.0F) {
                  _snowmanxxxxxxxxxxxxxx = 1.0F + _snowmanxxxxxxxxxxxx * 2.0F;
                  _snowmanxxxxxxxxxxxxxxx = 1.0F + _snowmanxxxxxxxxxxxxx * 4.0F;
               } else {
                  _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               }

               float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx > _snowmanxxxxxxxx ? 0.5F : 1.0F;
               float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * BIOME_WEIGHT_TABLE[_snowmanxxxxxxxxx + 2 + (_snowmanxxxxxxxxxx + 2) * 5] / (_snowmanxxxxxxxxxxxxxx + 2.0F);
               _snowmanxxx += _snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxx += _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
            }
         }

         float _snowmanxxxxxxxxx = _snowmanxxxx / _snowmanxxxxx;
         float _snowmanxxxxxxxxxx = _snowmanxxx / _snowmanxxxxx;
         double _snowmanxxxxxxxxxxx = (double)(_snowmanxxxxxxxxx * 0.5F - 0.125F);
         double _snowmanxxxxxxxxxxxx = (double)(_snowmanxxxxxxxxxx * 0.9F + 0.1F);
         _snowmanx = _snowmanxxxxxxxxxxx * 0.265625;
         _snowmanxx = 96.0 / _snowmanxxxxxxxxxxxx;
      }

      double _snowmanxxx = 684.412 * _snowman.getSampling().getXZScale();
      double _snowmanxxxx = 684.412 * _snowman.getSampling().getYScale();
      double _snowmanxxxxx = _snowmanxxx / _snowman.getSampling().getXZFactor();
      double _snowmanxxxxxx = _snowmanxxxx / _snowman.getSampling().getYFactor();
      double _snowmanxxxxxxx = (double)_snowman.getTopSlide().getTarget();
      double _snowmanxxxxxxxx = (double)_snowman.getTopSlide().getSize();
      double _snowmanxxxxxxxxx = (double)_snowman.getTopSlide().getOffset();
      double _snowmanxxxxxxxxxx = (double)_snowman.getBottomSlide().getTarget();
      double _snowmanxxxxxxxxxxx = (double)_snowman.getBottomSlide().getSize();
      double _snowmanxxxxxxxxxxxx = (double)_snowman.getBottomSlide().getOffset();
      double _snowmanxxxxxxxxxxxxx = _snowman.hasRandomDensityOffset() ? this.getRandomDensityAt(x, z) : 0.0;
      double _snowmanxxxxxxxxxxxxxx = _snowman.getDensityFactor();
      double _snowmanxxxxxxxxxxxxxxx = _snowman.getDensityOffset();

      for (int _snowmanxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxx <= this.noiseSizeY; _snowmanxxxxxxxxxxxxxxxx++) {
         double _snowmanxxxxxxxxxxxxxxxxx = this.sampleNoise(x, _snowmanxxxxxxxxxxxxxxxx, z, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         double _snowmanxxxxxxxxxxxxxxxxxx = 1.0 - (double)_snowmanxxxxxxxxxxxxxxxx * 2.0 / (double)this.noiseSizeY + _snowmanxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxx + _snowmanx) * _snowmanxx;
         if (_snowmanxxxxxxxxxxxxxxxxxxxx > 0.0) {
            _snowmanxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx * 4.0;
         } else {
            _snowmanxxxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxxxxxx;
         }

         if (_snowmanxxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)(this.noiseSizeY - _snowmanxxxxxxxxxxxxxxxx) - _snowmanxxxxxxxxx) / _snowmanxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = MathHelper.clampedLerp(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         }

         if (_snowmanxxxxxxxxxxx > 0.0) {
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = ((double)_snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx) / _snowmanxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxx = MathHelper.clampedLerp(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         }

         buffer[_snowmanxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxx;
      }
   }

   private double getRandomDensityAt(int x, int z) {
      double _snowman = this.densityNoise.sample((double)(x * 200), 10.0, (double)(z * 200), 1.0, 0.0, true);
      double _snowmanx;
      if (_snowman < 0.0) {
         _snowmanx = -_snowman * 0.3;
      } else {
         _snowmanx = _snowman;
      }

      double _snowmanxx = _snowmanx * 24.575625 - 2.0;
      return _snowmanxx < 0.0 ? _snowmanxx * 0.009486607142857142 : Math.min(_snowmanxx, 1.0) * 0.006640625;
   }

   @Override
   public int getHeight(int x, int z, Heightmap.Type heightmapType) {
      return this.sampleHeightmap(x, z, null, heightmapType.getBlockPredicate());
   }

   @Override
   public BlockView getColumnSample(int x, int z) {
      BlockState[] _snowman = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
      this.sampleHeightmap(x, z, _snowman, null);
      return new VerticalBlockSample(_snowman);
   }

   private int sampleHeightmap(int x, int z, @Nullable BlockState[] states, @Nullable Predicate<BlockState> predicate) {
      int _snowman = Math.floorDiv(x, this.horizontalNoiseResolution);
      int _snowmanx = Math.floorDiv(z, this.horizontalNoiseResolution);
      int _snowmanxx = Math.floorMod(x, this.horizontalNoiseResolution);
      int _snowmanxxx = Math.floorMod(z, this.horizontalNoiseResolution);
      double _snowmanxxxx = (double)_snowmanxx / (double)this.horizontalNoiseResolution;
      double _snowmanxxxxx = (double)_snowmanxxx / (double)this.horizontalNoiseResolution;
      double[][] _snowmanxxxxxx = new double[][]{
         this.sampleNoiseColumn(_snowman, _snowmanx), this.sampleNoiseColumn(_snowman, _snowmanx + 1), this.sampleNoiseColumn(_snowman + 1, _snowmanx), this.sampleNoiseColumn(_snowman + 1, _snowmanx + 1)
      };

      for (int _snowmanxxxxxxx = this.noiseSizeY - 1; _snowmanxxxxxxx >= 0; _snowmanxxxxxxx--) {
         double _snowmanxxxxxxxx = _snowmanxxxxxx[0][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxx = _snowmanxxxxxx[1][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxx = _snowmanxxxxxx[2][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxxx = _snowmanxxxxxx[3][_snowmanxxxxxxx];
         double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx[0][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx[1][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxx[2][_snowmanxxxxxxx + 1];
         double _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx[3][_snowmanxxxxxxx + 1];

         for (int _snowmanxxxxxxxxxxxxxxxx = this.verticalNoiseResolution - 1; _snowmanxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxx--) {
            double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxx / (double)this.verticalNoiseResolution;
            double _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.lerp3(
               _snowmanxxxxxxxxxxxxxxxxx,
               _snowmanxxxx,
               _snowmanxxxxx,
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxxxxx,
               _snowmanxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx * this.verticalNoiseResolution + _snowmanxxxxxxxxxxxxxxxx;
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxx = this.getBlockState(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
            if (states != null) {
               states[_snowmanxxxxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxxxxx;
            }

            if (predicate != null && predicate.test(_snowmanxxxxxxxxxxxxxxxxxxxx)) {
               return _snowmanxxxxxxxxxxxxxxxxxxx + 1;
            }
         }
      }

      return 0;
   }

   protected BlockState getBlockState(double density, int y) {
      BlockState _snowman;
      if (density > 0.0) {
         _snowman = this.defaultBlock;
      } else if (y < this.getSeaLevel()) {
         _snowman = this.defaultFluid;
      } else {
         _snowman = AIR;
      }

      return _snowman;
   }

   @Override
   public void buildSurface(ChunkRegion region, Chunk chunk) {
      ChunkPos _snowman = chunk.getPos();
      int _snowmanx = _snowman.x;
      int _snowmanxx = _snowman.z;
      ChunkRandom _snowmanxxx = new ChunkRandom();
      _snowmanxxx.setTerrainSeed(_snowmanx, _snowmanxx);
      ChunkPos _snowmanxxxx = chunk.getPos();
      int _snowmanxxxxx = _snowmanxxxx.getStartX();
      int _snowmanxxxxxx = _snowmanxxxx.getStartZ();
      double _snowmanxxxxxxx = 0.0625;
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 16; _snowmanxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxx = _snowmanxxxxx + _snowmanxxxxxxxxx;
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxx = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx) + 1;
            double _snowmanxxxxxxxxxxxxxx = this.surfaceDepthNoise
                  .sample((double)_snowmanxxxxxxxxxxx * 0.0625, (double)_snowmanxxxxxxxxxxxx * 0.0625, 0.0625, (double)_snowmanxxxxxxxxx * 0.0625)
               * 15.0;
            region.getBiome(_snowmanxxxxxxxx.set(_snowmanxxxxx + _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxx + _snowmanxxxxxxxxxx))
               .buildSurface(
                  _snowmanxxx,
                  chunk,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxx,
                  this.defaultBlock,
                  this.defaultFluid,
                  this.getSeaLevel(),
                  region.getSeed()
               );
         }
      }

      this.buildBedrock(chunk, _snowmanxxx);
   }

   private void buildBedrock(Chunk chunk, Random random) {
      BlockPos.Mutable _snowman = new BlockPos.Mutable();
      int _snowmanx = chunk.getPos().getStartX();
      int _snowmanxx = chunk.getPos().getStartZ();
      ChunkGeneratorSettings _snowmanxxx = this.settings.get();
      int _snowmanxxxx = _snowmanxxx.getBedrockFloorY();
      int _snowmanxxxxx = this.worldHeight - 1 - _snowmanxxx.getBedrockCeilingY();
      int _snowmanxxxxxx = 5;
      boolean _snowmanxxxxxxx = _snowmanxxxxx + 4 >= 0 && _snowmanxxxxx < this.worldHeight;
      boolean _snowmanxxxxxxxx = _snowmanxxxx + 4 >= 0 && _snowmanxxxx < this.worldHeight;
      if (_snowmanxxxxxxx || _snowmanxxxxxxxx) {
         for (BlockPos _snowmanxxxxxxxxx : BlockPos.iterate(_snowmanx, 0, _snowmanxx, _snowmanx + 15, 0, _snowmanxx + 15)) {
            if (_snowmanxxxxxxx) {
               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 5; _snowmanxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxx <= random.nextInt(5)) {
                     chunk.setBlockState(_snowman.set(_snowmanxxxxxxxxx.getX(), _snowmanxxxxx - _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                  }
               }
            }

            if (_snowmanxxxxxxxx) {
               for (int _snowmanxxxxxxxxxxx = 4; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
                  if (_snowmanxxxxxxxxxxx <= random.nextInt(5)) {
                     chunk.setBlockState(_snowman.set(_snowmanxxxxxxxxx.getX(), _snowmanxxxx + _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                  }
               }
            }
         }
      }
   }

   @Override
   public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
      ObjectList<StructurePiece> _snowman = new ObjectArrayList(10);
      ObjectList<JigsawJunction> _snowmanx = new ObjectArrayList(32);
      ChunkPos _snowmanxx = chunk.getPos();
      int _snowmanxxx = _snowmanxx.x;
      int _snowmanxxxx = _snowmanxx.z;
      int _snowmanxxxxx = _snowmanxxx << 4;
      int _snowmanxxxxxx = _snowmanxxxx << 4;

      for (StructureFeature<?> _snowmanxxxxxxx : StructureFeature.JIGSAW_STRUCTURES) {
         accessor.getStructuresWithChildren(ChunkSectionPos.from(_snowmanxx, 0), _snowmanxxxxxxx).forEach(start -> {
            for (StructurePiece _snowmanxxxxxxxx : start.getChildren()) {
               if (_snowmanxxxxxxxx.intersectsChunk(_snowman, 12)) {
                  if (_snowmanxxxxxxxx instanceof PoolStructurePiece) {
                     PoolStructurePiece _snowmanx = (PoolStructurePiece)_snowmanxxxxxxxx;
                     StructurePool.Projection _snowmanxx = _snowmanx.getPoolElement().getProjection();
                     if (_snowmanxx == StructurePool.Projection.RIGID) {
                        _snowman.add(_snowmanx);
                     }

                     for (JigsawJunction _snowmanxxx : _snowmanx.getJunctions()) {
                        int _snowmanxxxx = _snowmanxxx.getSourceX();
                        int _snowmanxxxxx = _snowmanxxx.getSourceZ();
                        if (_snowmanxxxx > _snowman - 12 && _snowmanxxxxx > _snowman - 12 && _snowmanxxxx < _snowman + 15 + 12 && _snowmanxxxxx < _snowman + 15 + 12) {
                           _snowman.add(_snowmanxxx);
                        }
                     }
                  } else {
                     _snowman.add(_snowmanxxxxxxxx);
                  }
               }
            }
         });
      }

      double[][][] _snowmanxxxxxxx = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < this.noiseSizeZ + 1; _snowmanxxxxxxxx++) {
         _snowmanxxxxxxx[0][_snowmanxxxxxxxx] = new double[this.noiseSizeY + 1];
         this.sampleNoiseColumn(_snowmanxxxxxxx[0][_snowmanxxxxxxxx], _snowmanxxx * this.noiseSizeX, _snowmanxxxx * this.noiseSizeZ + _snowmanxxxxxxxx);
         _snowmanxxxxxxx[1][_snowmanxxxxxxxx] = new double[this.noiseSizeY + 1];
      }

      ProtoChunk _snowmanxxxxxxxx = (ProtoChunk)chunk;
      Heightmap _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
      Heightmap _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
      BlockPos.Mutable _snowmanxxxxxxxxxxx = new BlockPos.Mutable();
      ObjectListIterator<StructurePiece> _snowmanxxxxxxxxxxxx = _snowman.iterator();
      ObjectListIterator<JigsawJunction> _snowmanxxxxxxxxxxxxx = _snowmanx.iterator();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < this.noiseSizeX; _snowmanxxxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.noiseSizeZ + 1; _snowmanxxxxxxxxxxxxxxx++) {
            this.sampleNoiseColumn(_snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx], _snowmanxxx * this.noiseSizeX + _snowmanxxxxxxxxxxxxxx + 1, _snowmanxxxx * this.noiseSizeZ + _snowmanxxxxxxxxxxxxxxx);
         }

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.noiseSizeZ; _snowmanxxxxxxxxxxxxxxx++) {
            ChunkSection _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getSection(15);
            _snowmanxxxxxxxxxxxxxxxx.lock();

            for (int _snowmanxxxxxxxxxxxxxxxxx = this.noiseSizeY - 1; _snowmanxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxx--) {
               double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx][_snowmanxxxxxxxxxxxxxxxxx + 1];
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[1][_snowmanxxxxxxxxxxxxxxx + 1][_snowmanxxxxxxxxxxxxxxxxx + 1];

               for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.verticalNoiseResolution - 1; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx--) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx * this.verticalNoiseResolution + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx >> 4;
                  if (_snowmanxxxxxxxxxxxxxxxx.getYOffset() >> 4 != _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxx.unlock();
                     _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx.getSection(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxx.lock();
                  }

                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.verticalNoiseResolution;
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                  double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
                  );

                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.horizontalNoiseResolution;
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
                  ) {
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx
                        + _snowmanxxxxxxxxxxxxxx * this.horizontalNoiseResolution
                        + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / (double)this.horizontalNoiseResolution;
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     );
                     double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                     );

                     for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.horizontalNoiseResolution;
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
                     ) {
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx
                           + _snowmanxxxxxxxxxxxxxxx * this.horizontalNoiseResolution
                           + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 15;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           / (double)this.horizontalNoiseResolution;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.lerp(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.clamp(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 200.0, -1.0, 1.0
                        );
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 2.0
                           - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              / 24.0;

                        while (_snowmanxxxxxxxxxxxx.hasNext()) {
                           StructurePiece _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (StructurePiece)_snowmanxxxxxxxxxxxx.next();
                           BlockBox _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getBoundingBox();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                              0,
                              Math.max(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minX - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.maxX
                              )
                           );
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - (
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minY
                                    + (
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx instanceof PoolStructurePiece
                                          ? ((PoolStructurePiece)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).getGroundLevelDelta()
                                          : 0
                                    )
                              );
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                              0,
                              Math.max(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.minZ - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.maxZ
                              )
                           );
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += getNoiseWeight(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                              * 0.8;
                        }

                        _snowmanxxxxxxxxxxxx.back(_snowman.size());

                        while (_snowmanxxxxxxxxxxxxx.hasNext()) {
                           JigsawJunction _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (JigsawJunction)_snowmanxxxxxxxxxxxxx.next();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSourceX();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSourceGroundY();
                           int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getSourceZ();
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx += getNoiseWeight(
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                              )
                              * 0.4;
                        }

                        _snowmanxxxxxxxxxxxxx.back(_snowmanx.size());
                        BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getBlockState(
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                        );
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx != AIR) {
                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getLuminance() != 0) {
                              _snowmanxxxxxxxxxxx.set(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                              _snowmanxxxxxxxx.addLightSource(_snowmanxxxxxxxxxxx);
                           }

                           _snowmanxxxxxxxxxxxxxxxx.setBlockState(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              false
                           );
                           _snowmanxxxxxxxxx.trackUpdate(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                           _snowmanxxxxxxxxxx.trackUpdate(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           );
                        }
                     }
                  }
               }
            }

            _snowmanxxxxxxxxxxxxxxxx.unlock();
         }

         double[][] _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx[0];
         _snowmanxxxxxxx[0] = _snowmanxxxxxxx[1];
         _snowmanxxxxxxx[1] = _snowmanxxxxxxxxxxxxxxx;
      }
   }

   private static double getNoiseWeight(int x, int y, int z) {
      int _snowman = x + 12;
      int _snowmanx = y + 12;
      int _snowmanxx = z + 12;
      if (_snowman < 0 || _snowman >= 24) {
         return 0.0;
      } else if (_snowmanx < 0 || _snowmanx >= 24) {
         return 0.0;
      } else {
         return _snowmanxx >= 0 && _snowmanxx < 24 ? (double)NOISE_WEIGHT_TABLE[_snowmanxx * 24 * 24 + _snowman * 24 + _snowmanx] : 0.0;
      }
   }

   private static double calculateNoiseWeight(int x, int y, int z) {
      double _snowman = (double)(x * x + z * z);
      double _snowmanx = (double)y + 0.5;
      double _snowmanxx = _snowmanx * _snowmanx;
      double _snowmanxxx = Math.pow(Math.E, -(_snowmanxx / 16.0 + _snowman / 16.0));
      double _snowmanxxxx = -_snowmanx * MathHelper.fastInverseSqrt(_snowmanxx / 2.0 + _snowman / 2.0) / 2.0;
      return _snowmanxxxx * _snowmanxxx;
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
      if (!this.settings.get().isMobGenerationDisabled()) {
         int _snowman = region.getCenterChunkX();
         int _snowmanx = region.getCenterChunkZ();
         Biome _snowmanxx = region.getBiome(new ChunkPos(_snowman, _snowmanx).getStartPos());
         ChunkRandom _snowmanxxx = new ChunkRandom();
         _snowmanxxx.setPopulationSeed(region.getSeed(), _snowman << 4, _snowmanx << 4);
         SpawnHelper.populateEntities(region, _snowmanxx, _snowman, _snowmanx, _snowmanxxx);
      }
   }
}
