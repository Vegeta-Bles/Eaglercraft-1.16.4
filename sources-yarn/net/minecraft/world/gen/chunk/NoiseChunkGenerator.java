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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      instance -> instance.group(
               BiomeSource.CODEC.fieldOf("biome_source").forGetter(arg -> arg.populationSource),
               Codec.LONG.fieldOf("seed").stable().forGetter(arg -> arg.seed),
               ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(arg -> arg.settings)
            )
            .apply(instance, instance.stable(NoiseChunkGenerator::new))
   );
   private static final float[] NOISE_WEIGHT_TABLE = Util.make(new float[13824], array -> {
      for (int i = 0; i < 24; i++) {
         for (int j = 0; j < 24; j++) {
            for (int k = 0; k < 24; k++) {
               array[i * 24 * 24 + j * 24 + k] = (float)calculateNoiseWeight(j - 12, k - 12, i - 12);
            }
         }
      }
   });
   private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], array -> {
      for (int i = -2; i <= 2; i++) {
         for (int j = -2; j <= 2; j++) {
            float f = 10.0F / MathHelper.sqrt((float)(i * i + j * j) + 0.2F);
            array[i + 2 + (j + 2) * 5] = f;
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
      ChunkGeneratorSettings lv = settings.get();
      this.settings = settings;
      GenerationShapeConfig lv2 = lv.getGenerationShapeConfig();
      this.worldHeight = lv2.getHeight();
      this.verticalNoiseResolution = lv2.getSizeVertical() * 4;
      this.horizontalNoiseResolution = lv2.getSizeHorizontal() * 4;
      this.defaultBlock = lv.getDefaultBlock();
      this.defaultFluid = lv.getDefaultFluid();
      this.noiseSizeX = 16 / this.horizontalNoiseResolution;
      this.noiseSizeY = lv2.getHeight() / this.verticalNoiseResolution;
      this.noiseSizeZ = 16 / this.horizontalNoiseResolution;
      this.random = new ChunkRandom(seed);
      this.lowerInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      this.upperInterpolatedNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      this.interpolationNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
      this.surfaceDepthNoise = (NoiseSampler)(lv2.hasSimplexSurfaceNoise()
         ? new OctaveSimplexNoiseSampler(this.random, IntStream.rangeClosed(-3, 0))
         : new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-3, 0)));
      this.random.consume(2620);
      this.densityNoise = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
      if (lv2.hasIslandNoiseOverride()) {
         ChunkRandom lv3 = new ChunkRandom(seed);
         lv3.consume(17292);
         this.islandNoise = new SimplexNoiseSampler(lv3);
      } else {
         this.islandNoise = null;
      }
   }

   @Override
   protected Codec<? extends ChunkGenerator> getCodec() {
      return CODEC;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public ChunkGenerator withSeed(long seed) {
      return new NoiseChunkGenerator(this.populationSource.withSeed(seed), seed, this.settings);
   }

   public boolean matchesSettings(long seed, RegistryKey<ChunkGeneratorSettings> settingsKey) {
      return this.seed == seed && this.settings.get().equals(settingsKey);
   }

   private double sampleNoise(int x, int y, int z, double horizontalScale, double verticalScale, double horizontalStretch, double verticalStretch) {
      double h = 0.0;
      double l = 0.0;
      double m = 0.0;
      boolean bl = true;
      double n = 1.0;

      for (int o = 0; o < 16; o++) {
         double p = OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalScale * n);
         double q = OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalScale * n);
         double r = OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalScale * n);
         double s = verticalScale * n;
         PerlinNoiseSampler lv = this.lowerInterpolatedNoise.getOctave(o);
         if (lv != null) {
            h += lv.sample(p, q, r, s, (double)y * s) / n;
         }

         PerlinNoiseSampler lv2 = this.upperInterpolatedNoise.getOctave(o);
         if (lv2 != null) {
            l += lv2.sample(p, q, r, s, (double)y * s) / n;
         }

         if (o < 8) {
            PerlinNoiseSampler lv3 = this.interpolationNoise.getOctave(o);
            if (lv3 != null) {
               m += lv3.sample(
                     OctavePerlinNoiseSampler.maintainPrecision((double)x * horizontalStretch * n),
                     OctavePerlinNoiseSampler.maintainPrecision((double)y * verticalStretch * n),
                     OctavePerlinNoiseSampler.maintainPrecision((double)z * horizontalStretch * n),
                     verticalStretch * n,
                     (double)y * verticalStretch * n
                  )
                  / n;
            }
         }

         n /= 2.0;
      }

      return MathHelper.clampedLerp(h / 512.0, l / 512.0, (m / 10.0 + 1.0) / 2.0);
   }

   private double[] sampleNoiseColumn(int x, int z) {
      double[] ds = new double[this.noiseSizeY + 1];
      this.sampleNoiseColumn(ds, x, z);
      return ds;
   }

   private void sampleNoiseColumn(double[] buffer, int x, int z) {
      GenerationShapeConfig lv = this.settings.get().getGenerationShapeConfig();
      double d;
      double e;
      if (this.islandNoise != null) {
         d = (double)(TheEndBiomeSource.getNoiseAt(this.islandNoise, x, z) - 8.0F);
         if (d > 0.0) {
            e = 0.25;
         } else {
            e = 1.0;
         }
      } else {
         float g = 0.0F;
         float h = 0.0F;
         float k = 0.0F;
         int l = 2;
         int m = this.getSeaLevel();
         float n = this.populationSource.getBiomeForNoiseGen(x, m, z).getDepth();

         for (int o = -2; o <= 2; o++) {
            for (int p = -2; p <= 2; p++) {
               Biome lv2 = this.populationSource.getBiomeForNoiseGen(x + o, m, z + p);
               float q = lv2.getDepth();
               float r = lv2.getScale();
               float s;
               float t;
               if (lv.isAmplified() && q > 0.0F) {
                  s = 1.0F + q * 2.0F;
                  t = 1.0F + r * 4.0F;
               } else {
                  s = q;
                  t = r;
               }

               float w = q > n ? 0.5F : 1.0F;
               float xx = w * BIOME_WEIGHT_TABLE[o + 2 + (p + 2) * 5] / (s + 2.0F);
               g += t * xx;
               h += s * xx;
               k += xx;
            }
         }

         float y = h / k;
         float zx = g / k;
         double aa = (double)(y * 0.5F - 0.125F);
         double ab = (double)(zx * 0.9F + 0.1F);
         d = aa * 0.265625;
         e = 96.0 / ab;
      }

      double ae = 684.412 * lv.getSampling().getXZScale();
      double af = 684.412 * lv.getSampling().getYScale();
      double ag = ae / lv.getSampling().getXZFactor();
      double ah = af / lv.getSampling().getYFactor();
      double ai = (double)lv.getTopSlide().getTarget();
      double aj = (double)lv.getTopSlide().getSize();
      double ak = (double)lv.getTopSlide().getOffset();
      double al = (double)lv.getBottomSlide().getTarget();
      double am = (double)lv.getBottomSlide().getSize();
      double an = (double)lv.getBottomSlide().getOffset();
      double ao = lv.hasRandomDensityOffset() ? this.getRandomDensityAt(x, z) : 0.0;
      double ap = lv.getDensityFactor();
      double aq = lv.getDensityOffset();

      for (int ar = 0; ar <= this.noiseSizeY; ar++) {
         double as = this.sampleNoise(x, ar, z, ae, af, ag, ah);
         double at = 1.0 - (double)ar * 2.0 / (double)this.noiseSizeY + ao;
         double au = at * ap + aq;
         double av = (au + d) * e;
         if (av > 0.0) {
            as += av * 4.0;
         } else {
            as += av;
         }

         if (aj > 0.0) {
            double aw = ((double)(this.noiseSizeY - ar) - ak) / aj;
            as = MathHelper.clampedLerp(ai, as, aw);
         }

         if (am > 0.0) {
            double ax = ((double)ar - an) / am;
            as = MathHelper.clampedLerp(al, as, ax);
         }

         buffer[ar] = as;
      }
   }

   private double getRandomDensityAt(int x, int z) {
      double d = this.densityNoise.sample((double)(x * 200), 10.0, (double)(z * 200), 1.0, 0.0, true);
      double e;
      if (d < 0.0) {
         e = -d * 0.3;
      } else {
         e = d;
      }

      double g = e * 24.575625 - 2.0;
      return g < 0.0 ? g * 0.009486607142857142 : Math.min(g, 1.0) * 0.006640625;
   }

   @Override
   public int getHeight(int x, int z, Heightmap.Type heightmapType) {
      return this.sampleHeightmap(x, z, null, heightmapType.getBlockPredicate());
   }

   @Override
   public BlockView getColumnSample(int x, int z) {
      BlockState[] lvs = new BlockState[this.noiseSizeY * this.verticalNoiseResolution];
      this.sampleHeightmap(x, z, lvs, null);
      return new VerticalBlockSample(lvs);
   }

   private int sampleHeightmap(int x, int z, @Nullable BlockState[] states, @Nullable Predicate<BlockState> predicate) {
      int k = Math.floorDiv(x, this.horizontalNoiseResolution);
      int l = Math.floorDiv(z, this.horizontalNoiseResolution);
      int m = Math.floorMod(x, this.horizontalNoiseResolution);
      int n = Math.floorMod(z, this.horizontalNoiseResolution);
      double d = (double)m / (double)this.horizontalNoiseResolution;
      double e = (double)n / (double)this.horizontalNoiseResolution;
      double[][] ds = new double[][]{
         this.sampleNoiseColumn(k, l), this.sampleNoiseColumn(k, l + 1), this.sampleNoiseColumn(k + 1, l), this.sampleNoiseColumn(k + 1, l + 1)
      };

      for (int o = this.noiseSizeY - 1; o >= 0; o--) {
         double f = ds[0][o];
         double g = ds[1][o];
         double h = ds[2][o];
         double p = ds[3][o];
         double q = ds[0][o + 1];
         double r = ds[1][o + 1];
         double s = ds[2][o + 1];
         double t = ds[3][o + 1];

         for (int u = this.verticalNoiseResolution - 1; u >= 0; u--) {
            double v = (double)u / (double)this.verticalNoiseResolution;
            double w = MathHelper.lerp3(v, d, e, f, q, h, s, g, r, p, t);
            int xx = o * this.verticalNoiseResolution + u;
            BlockState lv = this.getBlockState(w, xx);
            if (states != null) {
               states[xx] = lv;
            }

            if (predicate != null && predicate.test(lv)) {
               return xx + 1;
            }
         }
      }

      return 0;
   }

   protected BlockState getBlockState(double density, int y) {
      BlockState lv;
      if (density > 0.0) {
         lv = this.defaultBlock;
      } else if (y < this.getSeaLevel()) {
         lv = this.defaultFluid;
      } else {
         lv = AIR;
      }

      return lv;
   }

   @Override
   public void buildSurface(ChunkRegion region, Chunk chunk) {
      ChunkPos lv = chunk.getPos();
      int i = lv.x;
      int j = lv.z;
      ChunkRandom lv2 = new ChunkRandom();
      lv2.setTerrainSeed(i, j);
      ChunkPos lv3 = chunk.getPos();
      int k = lv3.getStartX();
      int l = lv3.getStartZ();
      double d = 0.0625;
      BlockPos.Mutable lv4 = new BlockPos.Mutable();

      for (int m = 0; m < 16; m++) {
         for (int n = 0; n < 16; n++) {
            int o = k + m;
            int p = l + n;
            int q = chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, m, n) + 1;
            double e = this.surfaceDepthNoise.sample((double)o * 0.0625, (double)p * 0.0625, 0.0625, (double)m * 0.0625) * 15.0;
            region.getBiome(lv4.set(k + m, q, l + n))
               .buildSurface(lv2, chunk, o, p, q, e, this.defaultBlock, this.defaultFluid, this.getSeaLevel(), region.getSeed());
         }
      }

      this.buildBedrock(chunk, lv2);
   }

   private void buildBedrock(Chunk chunk, Random random) {
      BlockPos.Mutable lv = new BlockPos.Mutable();
      int i = chunk.getPos().getStartX();
      int j = chunk.getPos().getStartZ();
      ChunkGeneratorSettings lv2 = this.settings.get();
      int k = lv2.getBedrockFloorY();
      int l = this.worldHeight - 1 - lv2.getBedrockCeilingY();
      int m = 5;
      boolean bl = l + 4 >= 0 && l < this.worldHeight;
      boolean bl2 = k + 4 >= 0 && k < this.worldHeight;
      if (bl || bl2) {
         for (BlockPos lv3 : BlockPos.iterate(i, 0, j, i + 15, 0, j + 15)) {
            if (bl) {
               for (int n = 0; n < 5; n++) {
                  if (n <= random.nextInt(5)) {
                     chunk.setBlockState(lv.set(lv3.getX(), l - n, lv3.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                  }
               }
            }

            if (bl2) {
               for (int o = 4; o >= 0; o--) {
                  if (o <= random.nextInt(5)) {
                     chunk.setBlockState(lv.set(lv3.getX(), k + o, lv3.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                  }
               }
            }
         }
      }
   }

   @Override
   public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {
      ObjectList<StructurePiece> objectList = new ObjectArrayList(10);
      ObjectList<JigsawJunction> objectList2 = new ObjectArrayList(32);
      ChunkPos lv = chunk.getPos();
      int i = lv.x;
      int j = lv.z;
      int k = i << 4;
      int l = j << 4;

      for (StructureFeature<?> lv2 : StructureFeature.JIGSAW_STRUCTURES) {
         accessor.getStructuresWithChildren(ChunkSectionPos.from(lv, 0), lv2).forEach(start -> {
            for (StructurePiece lvx : start.getChildren()) {
               if (lvx.intersectsChunk(lv, 12)) {
                  if (lvx instanceof PoolStructurePiece) {
                     PoolStructurePiece lv2x = (PoolStructurePiece)lvx;
                     StructurePool.Projection lv3x = lv2x.getPoolElement().getProjection();
                     if (lv3x == StructurePool.Projection.RIGID) {
                        objectList.add(lv2x);
                     }

                     for (JigsawJunction lv4x : lv2x.getJunctions()) {
                        int kx = lv4x.getSourceX();
                        int lx = lv4x.getSourceZ();
                        if (kx > k - 12 && lx > l - 12 && kx < k + 15 + 12 && lx < l + 15 + 12) {
                           objectList2.add(lv4x);
                        }
                     }
                  } else {
                     objectList.add(lvx);
                  }
               }
            }
         });
      }

      double[][][] ds = new double[2][this.noiseSizeZ + 1][this.noiseSizeY + 1];

      for (int m = 0; m < this.noiseSizeZ + 1; m++) {
         ds[0][m] = new double[this.noiseSizeY + 1];
         this.sampleNoiseColumn(ds[0][m], i * this.noiseSizeX, j * this.noiseSizeZ + m);
         ds[1][m] = new double[this.noiseSizeY + 1];
      }

      ProtoChunk lv3 = (ProtoChunk)chunk;
      Heightmap lv4 = lv3.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
      Heightmap lv5 = lv3.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
      BlockPos.Mutable lv6 = new BlockPos.Mutable();
      ObjectListIterator<StructurePiece> objectListIterator = objectList.iterator();
      ObjectListIterator<JigsawJunction> objectListIterator2 = objectList2.iterator();

      for (int n = 0; n < this.noiseSizeX; n++) {
         for (int o = 0; o < this.noiseSizeZ + 1; o++) {
            this.sampleNoiseColumn(ds[1][o], i * this.noiseSizeX + n + 1, j * this.noiseSizeZ + o);
         }

         for (int p = 0; p < this.noiseSizeZ; p++) {
            ChunkSection lv7 = lv3.getSection(15);
            lv7.lock();

            for (int q = this.noiseSizeY - 1; q >= 0; q--) {
               double d = ds[0][p][q];
               double e = ds[0][p + 1][q];
               double f = ds[1][p][q];
               double g = ds[1][p + 1][q];
               double h = ds[0][p][q + 1];
               double r = ds[0][p + 1][q + 1];
               double s = ds[1][p][q + 1];
               double t = ds[1][p + 1][q + 1];

               for (int u = this.verticalNoiseResolution - 1; u >= 0; u--) {
                  int v = q * this.verticalNoiseResolution + u;
                  int w = v & 15;
                  int x = v >> 4;
                  if (lv7.getYOffset() >> 4 != x) {
                     lv7.unlock();
                     lv7 = lv3.getSection(x);
                     lv7.lock();
                  }

                  double y = (double)u / (double)this.verticalNoiseResolution;
                  double z = MathHelper.lerp(y, d, h);
                  double aa = MathHelper.lerp(y, f, s);
                  double ab = MathHelper.lerp(y, e, r);
                  double ac = MathHelper.lerp(y, g, t);

                  for (int ad = 0; ad < this.horizontalNoiseResolution; ad++) {
                     int ae = k + n * this.horizontalNoiseResolution + ad;
                     int af = ae & 15;
                     double ag = (double)ad / (double)this.horizontalNoiseResolution;
                     double ah = MathHelper.lerp(ag, z, aa);
                     double ai = MathHelper.lerp(ag, ab, ac);

                     for (int aj = 0; aj < this.horizontalNoiseResolution; aj++) {
                        int ak = l + p * this.horizontalNoiseResolution + aj;
                        int al = ak & 15;
                        double am = (double)aj / (double)this.horizontalNoiseResolution;
                        double an = MathHelper.lerp(am, ah, ai);
                        double ao = MathHelper.clamp(an / 200.0, -1.0, 1.0);
                        ao = ao / 2.0 - ao * ao * ao / 24.0;

                        while (objectListIterator.hasNext()) {
                           StructurePiece lv8 = (StructurePiece)objectListIterator.next();
                           BlockBox lv9 = lv8.getBoundingBox();
                           int ap = Math.max(0, Math.max(lv9.minX - ae, ae - lv9.maxX));
                           int aq = v - (lv9.minY + (lv8 instanceof PoolStructurePiece ? ((PoolStructurePiece)lv8).getGroundLevelDelta() : 0));
                           int ar = Math.max(0, Math.max(lv9.minZ - ak, ak - lv9.maxZ));
                           ao += getNoiseWeight(ap, aq, ar) * 0.8;
                        }

                        objectListIterator.back(objectList.size());

                        while (objectListIterator2.hasNext()) {
                           JigsawJunction lv10 = (JigsawJunction)objectListIterator2.next();
                           int as = ae - lv10.getSourceX();
                           int at = v - lv10.getSourceGroundY();
                           int au = ak - lv10.getSourceZ();
                           ao += getNoiseWeight(as, at, au) * 0.4;
                        }

                        objectListIterator2.back(objectList2.size());
                        BlockState lv11 = this.getBlockState(ao, v);
                        if (lv11 != AIR) {
                           if (lv11.getLuminance() != 0) {
                              lv6.set(ae, v, ak);
                              lv3.addLightSource(lv6);
                           }

                           lv7.setBlockState(af, w, al, lv11, false);
                           lv4.trackUpdate(af, v, al, lv11);
                           lv5.trackUpdate(af, v, al, lv11);
                        }
                     }
                  }
               }
            }

            lv7.unlock();
         }

         double[][] es = ds[0];
         ds[0] = ds[1];
         ds[1] = es;
      }
   }

   private static double getNoiseWeight(int x, int y, int z) {
      int l = x + 12;
      int m = y + 12;
      int n = z + 12;
      if (l < 0 || l >= 24) {
         return 0.0;
      } else if (m < 0 || m >= 24) {
         return 0.0;
      } else {
         return n >= 0 && n < 24 ? (double)NOISE_WEIGHT_TABLE[n * 24 * 24 + l * 24 + m] : 0.0;
      }
   }

   private static double calculateNoiseWeight(int x, int y, int z) {
      double d = (double)(x * x + z * z);
      double e = (double)y + 0.5;
      double f = e * e;
      double g = Math.pow(Math.E, -(f / 16.0 + d / 16.0));
      double h = -e * MathHelper.fastInverseSqrt(f / 2.0 + d / 2.0) / 2.0;
      return h * g;
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
         int i = region.getCenterChunkX();
         int j = region.getCenterChunkZ();
         Biome lv = region.getBiome(new ChunkPos(i, j).getStartPos());
         ChunkRandom lv2 = new ChunkRandom();
         lv2.setPopulationSeed(region.getSeed(), i << 4, j << 4);
         SpawnHelper.populateEntities(region, lv, i, j, lv2);
      }
   }
}
