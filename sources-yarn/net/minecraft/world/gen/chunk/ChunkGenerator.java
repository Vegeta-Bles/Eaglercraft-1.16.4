package net.minecraft.world.gen.chunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class ChunkGenerator {
   public static final Codec<ChunkGenerator> CODEC = Registry.CHUNK_GENERATOR.dispatchStable(ChunkGenerator::getCodec, Function.identity());
   protected final BiomeSource populationSource;
   protected final BiomeSource biomeSource;
   private final StructuresConfig structuresConfig;
   private final long worldSeed;
   private final List<ChunkPos> strongholds = Lists.newArrayList();

   public ChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
      this(biomeSource, biomeSource, structuresConfig, 0L);
   }

   public ChunkGenerator(BiomeSource populationSource, BiomeSource biomeSource, StructuresConfig structuresConfig, long worldSeed) {
      this.populationSource = populationSource;
      this.biomeSource = biomeSource;
      this.structuresConfig = structuresConfig;
      this.worldSeed = worldSeed;
   }

   private void generateStrongholdPositions() {
      if (this.strongholds.isEmpty()) {
         StrongholdConfig lv = this.structuresConfig.getStronghold();
         if (lv != null && lv.getCount() != 0) {
            List<Biome> list = Lists.newArrayList();

            for (Biome lv2 : this.populationSource.getBiomes()) {
               if (lv2.getGenerationSettings().hasStructureFeature(StructureFeature.STRONGHOLD)) {
                  list.add(lv2);
               }
            }

            int i = lv.getDistance();
            int j = lv.getCount();
            int k = lv.getSpread();
            Random random = new Random();
            random.setSeed(this.worldSeed);
            double d = random.nextDouble() * Math.PI * 2.0;
            int l = 0;
            int m = 0;

            for (int n = 0; n < j; n++) {
               double e = (double)(4 * i + i * m * 6) + (random.nextDouble() - 0.5) * (double)i * 2.5;
               int o = (int)Math.round(Math.cos(d) * e);
               int p = (int)Math.round(Math.sin(d) * e);
               BlockPos lv3 = this.populationSource.locateBiome((o << 4) + 8, 0, (p << 4) + 8, 112, list::contains, random);
               if (lv3 != null) {
                  o = lv3.getX() >> 4;
                  p = lv3.getZ() >> 4;
               }

               this.strongholds.add(new ChunkPos(o, p));
               d += (Math.PI * 2) / (double)k;
               if (++l == k) {
                  m++;
                  l = 0;
                  k += 2 * k / (m + 1);
                  k = Math.min(k, j - n);
                  d += random.nextDouble() * Math.PI * 2.0;
               }
            }
         }
      }
   }

   protected abstract Codec<? extends ChunkGenerator> getCodec();

   @Environment(EnvType.CLIENT)
   public abstract ChunkGenerator withSeed(long seed);

   public void populateBiomes(Registry<Biome> biomeRegistry, Chunk chunk) {
      ChunkPos lv = chunk.getPos();
      ((ProtoChunk)chunk).setBiomes(new BiomeArray(biomeRegistry, lv, this.biomeSource));
   }

   public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
      BiomeAccess lv = access.withSource(this.populationSource);
      ChunkRandom lv2 = new ChunkRandom();
      int i = 8;
      ChunkPos lv3 = chunk.getPos();
      int j = lv3.x;
      int k = lv3.z;
      GenerationSettings lv4 = this.populationSource.getBiomeForNoiseGen(lv3.x << 2, 0, lv3.z << 2).getGenerationSettings();
      BitSet bitSet = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);

      for (int m = j - 8; m <= j + 8; m++) {
         for (int n = k - 8; n <= k + 8; n++) {
            List<Supplier<ConfiguredCarver<?>>> list = lv4.getCarversForStep(carver);
            ListIterator<Supplier<ConfiguredCarver<?>>> listIterator = list.listIterator();

            while (listIterator.hasNext()) {
               int o = listIterator.nextIndex();
               ConfiguredCarver<?> lv5 = listIterator.next().get();
               lv2.setCarverSeed(seed + (long)o, m, n);
               if (lv5.shouldCarve(lv2, m, n)) {
                  lv5.carve(chunk, lv::getBiome, lv2, this.getSeaLevel(), m, n, j, k, bitSet);
               }
            }
         }
      }
   }

   @Nullable
   public BlockPos locateStructure(ServerWorld world, StructureFeature<?> feature, BlockPos center, int radius, boolean skipExistingChunks) {
      if (!this.populationSource.hasStructureFeature(feature)) {
         return null;
      } else if (feature == StructureFeature.STRONGHOLD) {
         this.generateStrongholdPositions();
         BlockPos lv = null;
         double d = Double.MAX_VALUE;
         BlockPos.Mutable lv2 = new BlockPos.Mutable();

         for (ChunkPos lv3 : this.strongholds) {
            lv2.set((lv3.x << 4) + 8, 32, (lv3.z << 4) + 8);
            double e = lv2.getSquaredDistance(center);
            if (lv == null) {
               lv = new BlockPos(lv2);
               d = e;
            } else if (e < d) {
               lv = new BlockPos(lv2);
               d = e;
            }
         }

         return lv;
      } else {
         StructureConfig lv4 = this.structuresConfig.getForType(feature);
         return lv4 == null ? null : feature.locateStructure(world, world.getStructureAccessor(), center, radius, skipExistingChunks, world.getSeed(), lv4);
      }
   }

   public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
      int i = region.getCenterChunkX();
      int j = region.getCenterChunkZ();
      int k = i * 16;
      int l = j * 16;
      BlockPos lv = new BlockPos(k, 0, l);
      Biome lv2 = this.populationSource.getBiomeForNoiseGen((i << 2) + 2, 2, (j << 2) + 2);
      ChunkRandom lv3 = new ChunkRandom();
      long m = lv3.setPopulationSeed(region.getSeed(), k, l);

      try {
         lv2.generateFeatureStep(accessor, this, region, m, lv3, lv);
      } catch (Exception var14) {
         CrashReport lv4 = CrashReport.create(var14, "Biome decoration");
         lv4.addElement("Generation").add("CenterX", i).add("CenterZ", j).add("Seed", m).add("Biome", lv2);
         throw new CrashException(lv4);
      }
   }

   public abstract void buildSurface(ChunkRegion region, Chunk chunk);

   public void populateEntities(ChunkRegion region) {
   }

   public StructuresConfig getStructuresConfig() {
      return this.structuresConfig;
   }

   public int getSpawnHeight() {
      return 64;
   }

   public BiomeSource getBiomeSource() {
      return this.biomeSource;
   }

   public int getWorldHeight() {
      return 256;
   }

   public List<SpawnSettings.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
      return biome.getSpawnSettings().getSpawnEntry(group);
   }

   public void setStructureStarts(DynamicRegistryManager arg, StructureAccessor arg2, Chunk arg3, StructureManager arg4, long worldSeed) {
      ChunkPos lv = arg3.getPos();
      Biome lv2 = this.populationSource.getBiomeForNoiseGen((lv.x << 2) + 2, 0, (lv.z << 2) + 2);
      this.setStructureStart(ConfiguredStructureFeatures.STRONGHOLD, arg, arg2, arg3, arg4, worldSeed, lv, lv2);

      for (Supplier<ConfiguredStructureFeature<?, ?>> supplier : lv2.getGenerationSettings().getStructureFeatures()) {
         this.setStructureStart(supplier.get(), arg, arg2, arg3, arg4, worldSeed, lv, lv2);
      }
   }

   private void setStructureStart(
      ConfiguredStructureFeature<?, ?> arg,
      DynamicRegistryManager arg2,
      StructureAccessor arg3,
      Chunk arg4,
      StructureManager arg5,
      long worldSeed,
      ChunkPos arg6,
      Biome arg7
   ) {
      StructureStart<?> lv = arg3.getStructureStart(ChunkSectionPos.from(arg4.getPos(), 0), arg.feature, arg4);
      int i = lv != null ? lv.getReferences() : 0;
      StructureConfig lv2 = this.structuresConfig.getForType(arg.feature);
      if (lv2 != null) {
         StructureStart<?> lv3 = arg.tryPlaceStart(arg2, this, this.populationSource, arg5, worldSeed, arg6, arg7, i, lv2);
         arg3.setStructureStart(ChunkSectionPos.from(arg4.getPos(), 0), arg.feature, lv3, arg4);
      }
   }

   public void addStructureReferences(StructureWorldAccess world, StructureAccessor accessor, Chunk chunk) {
      int i = 8;
      int j = chunk.getPos().x;
      int k = chunk.getPos().z;
      int l = j << 4;
      int m = k << 4;
      ChunkSectionPos lv = ChunkSectionPos.from(chunk.getPos(), 0);

      for (int n = j - 8; n <= j + 8; n++) {
         for (int o = k - 8; o <= k + 8; o++) {
            long p = ChunkPos.toLong(n, o);

            for (StructureStart<?> lv2 : world.getChunk(n, o).getStructureStarts().values()) {
               try {
                  if (lv2 != StructureStart.DEFAULT && lv2.getBoundingBox().intersectsXZ(l, m, l + 15, m + 15)) {
                     accessor.addStructureReference(lv, lv2.getFeature(), p, chunk);
                     DebugInfoSender.sendStructureStart(world, lv2);
                  }
               } catch (Exception var19) {
                  CrashReport lv3 = CrashReport.create(var19, "Generating structure reference");
                  CrashReportSection lv4 = lv3.addElement("Structure");
                  lv4.add("Id", () -> Registry.STRUCTURE_FEATURE.getId(lv2.getFeature()).toString());
                  lv4.add("Name", () -> lv2.getFeature().getName());
                  lv4.add("Class", () -> lv2.getFeature().getClass().getCanonicalName());
                  throw new CrashException(lv3);
               }
            }
         }
      }
   }

   public abstract void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk);

   public int getSeaLevel() {
      return 63;
   }

   public abstract int getHeight(int x, int z, Heightmap.Type heightmapType);

   public abstract BlockView getColumnSample(int x, int z);

   public int getHeightOnGround(int x, int z, Heightmap.Type heightmapType) {
      return this.getHeight(x, z, heightmapType);
   }

   public int getHeightInGround(int x, int z, Heightmap.Type heightmapType) {
      return this.getHeight(x, z, heightmapType) - 1;
   }

   public boolean isStrongholdStartingChunk(ChunkPos arg) {
      this.generateStrongholdPositions();
      return this.strongholds.contains(arg);
   }

   static {
      Registry.register(Registry.CHUNK_GENERATOR, "noise", NoiseChunkGenerator.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "flat", FlatChunkGenerator.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "debug", DebugChunkGenerator.CODEC);
   }
}
