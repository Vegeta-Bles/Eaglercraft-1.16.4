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
         StrongholdConfig _snowman = this.structuresConfig.getStronghold();
         if (_snowman != null && _snowman.getCount() != 0) {
            List<Biome> _snowmanx = Lists.newArrayList();

            for (Biome _snowmanxx : this.populationSource.getBiomes()) {
               if (_snowmanxx.getGenerationSettings().hasStructureFeature(StructureFeature.STRONGHOLD)) {
                  _snowmanx.add(_snowmanxx);
               }
            }

            int _snowmanxxx = _snowman.getDistance();
            int _snowmanxxxx = _snowman.getCount();
            int _snowmanxxxxx = _snowman.getSpread();
            Random _snowmanxxxxxx = new Random();
            _snowmanxxxxxx.setSeed(this.worldSeed);
            double _snowmanxxxxxxx = _snowmanxxxxxx.nextDouble() * Math.PI * 2.0;
            int _snowmanxxxxxxxx = 0;
            int _snowmanxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxx = (double)(4 * _snowmanxxx + _snowmanxxx * _snowmanxxxxxxxxx * 6) + (_snowmanxxxxxx.nextDouble() - 0.5) * (double)_snowmanxxx * 2.5;
               int _snowmanxxxxxxxxxxxx = (int)Math.round(Math.cos(_snowmanxxxxxxx) * _snowmanxxxxxxxxxxx);
               int _snowmanxxxxxxxxxxxxx = (int)Math.round(Math.sin(_snowmanxxxxxxx) * _snowmanxxxxxxxxxxx);
               BlockPos _snowmanxxxxxxxxxxxxxx = this.populationSource.locateBiome((_snowmanxxxxxxxxxxxx << 4) + 8, 0, (_snowmanxxxxxxxxxxxxx << 4) + 8, 112, _snowmanx::contains, _snowmanxxxxxx);
               if (_snowmanxxxxxxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getX() >> 4;
                  _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getZ() >> 4;
               }

               this.strongholds.add(new ChunkPos(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx));
               _snowmanxxxxxxx += (Math.PI * 2) / (double)_snowmanxxxxx;
               if (++_snowmanxxxxxxxx == _snowmanxxxxx) {
                  _snowmanxxxxxxxxx++;
                  _snowmanxxxxxxxx = 0;
                  _snowmanxxxxx += 2 * _snowmanxxxxx / (_snowmanxxxxxxxxx + 1);
                  _snowmanxxxxx = Math.min(_snowmanxxxxx, _snowmanxxxx - _snowmanxxxxxxxxxx);
                  _snowmanxxxxxxx += _snowmanxxxxxx.nextDouble() * Math.PI * 2.0;
               }
            }
         }
      }
   }

   protected abstract Codec<? extends ChunkGenerator> getCodec();

   public abstract ChunkGenerator withSeed(long seed);

   public void populateBiomes(Registry<Biome> biomeRegistry, Chunk chunk) {
      ChunkPos _snowman = chunk.getPos();
      ((ProtoChunk)chunk).setBiomes(new BiomeArray(biomeRegistry, _snowman, this.biomeSource));
   }

   public void carve(long seed, BiomeAccess access, Chunk chunk, GenerationStep.Carver carver) {
      BiomeAccess _snowman = access.withSource(this.populationSource);
      ChunkRandom _snowmanx = new ChunkRandom();
      int _snowmanxx = 8;
      ChunkPos _snowmanxxx = chunk.getPos();
      int _snowmanxxxx = _snowmanxxx.x;
      int _snowmanxxxxx = _snowmanxxx.z;
      GenerationSettings _snowmanxxxxxx = this.populationSource.getBiomeForNoiseGen(_snowmanxxx.x << 2, 0, _snowmanxxx.z << 2).getGenerationSettings();
      BitSet _snowmanxxxxxxx = ((ProtoChunk)chunk).getOrCreateCarvingMask(carver);

      for (int _snowmanxxxxxxxx = _snowmanxxxx - 8; _snowmanxxxxxxxx <= _snowmanxxxx + 8; _snowmanxxxxxxxx++) {
         for (int _snowmanxxxxxxxxx = _snowmanxxxxx - 8; _snowmanxxxxxxxxx <= _snowmanxxxxx + 8; _snowmanxxxxxxxxx++) {
            List<Supplier<ConfiguredCarver<?>>> _snowmanxxxxxxxxxx = _snowmanxxxxxx.getCarversForStep(carver);
            ListIterator<Supplier<ConfiguredCarver<?>>> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.listIterator();

            while (_snowmanxxxxxxxxxxx.hasNext()) {
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.nextIndex();
               ConfiguredCarver<?> _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.next().get();
               _snowmanx.setCarverSeed(seed + (long)_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx.shouldCarve(_snowmanx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx)) {
                  _snowmanxxxxxxxxxxxxx.carve(chunk, _snowman::getBiome, _snowmanx, this.getSeaLevel(), _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
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
         BlockPos _snowman = null;
         double _snowmanx = Double.MAX_VALUE;
         BlockPos.Mutable _snowmanxx = new BlockPos.Mutable();

         for (ChunkPos _snowmanxxx : this.strongholds) {
            _snowmanxx.set((_snowmanxxx.x << 4) + 8, 32, (_snowmanxxx.z << 4) + 8);
            double _snowmanxxxx = _snowmanxx.getSquaredDistance(center);
            if (_snowman == null) {
               _snowman = new BlockPos(_snowmanxx);
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxx < _snowmanx) {
               _snowman = new BlockPos(_snowmanxx);
               _snowmanx = _snowmanxxxx;
            }
         }

         return _snowman;
      } else {
         StructureConfig _snowman = this.structuresConfig.getForType(feature);
         return _snowman == null ? null : feature.locateStructure(world, world.getStructureAccessor(), center, radius, skipExistingChunks, world.getSeed(), _snowman);
      }
   }

   public void generateFeatures(ChunkRegion region, StructureAccessor accessor) {
      int _snowman = region.getCenterChunkX();
      int _snowmanx = region.getCenterChunkZ();
      int _snowmanxx = _snowman * 16;
      int _snowmanxxx = _snowmanx * 16;
      BlockPos _snowmanxxxx = new BlockPos(_snowmanxx, 0, _snowmanxxx);
      Biome _snowmanxxxxx = this.populationSource.getBiomeForNoiseGen((_snowman << 2) + 2, 2, (_snowmanx << 2) + 2);
      ChunkRandom _snowmanxxxxxx = new ChunkRandom();
      long _snowmanxxxxxxx = _snowmanxxxxxx.setPopulationSeed(region.getSeed(), _snowmanxx, _snowmanxxx);

      try {
         _snowmanxxxxx.generateFeatureStep(accessor, this, region, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxx);
      } catch (Exception var14) {
         CrashReport _snowmanxxxxxxxx = CrashReport.create(var14, "Biome decoration");
         _snowmanxxxxxxxx.addElement("Generation").add("CenterX", _snowman).add("CenterZ", _snowmanx).add("Seed", _snowmanxxxxxxx).add("Biome", _snowmanxxxxx);
         throw new CrashException(_snowmanxxxxxxxx);
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

   public void setStructureStarts(DynamicRegistryManager _snowman, StructureAccessor _snowman, Chunk _snowman, StructureManager _snowman, long worldSeed) {
      ChunkPos _snowmanxxxx = _snowman.getPos();
      Biome _snowmanxxxxx = this.populationSource.getBiomeForNoiseGen((_snowmanxxxx.x << 2) + 2, 0, (_snowmanxxxx.z << 2) + 2);
      this.setStructureStart(ConfiguredStructureFeatures.STRONGHOLD, _snowman, _snowman, _snowman, _snowman, worldSeed, _snowmanxxxx, _snowmanxxxxx);

      for (Supplier<ConfiguredStructureFeature<?, ?>> _snowmanxxxxxx : _snowmanxxxxx.getGenerationSettings().getStructureFeatures()) {
         this.setStructureStart(_snowmanxxxxxx.get(), _snowman, _snowman, _snowman, _snowman, worldSeed, _snowmanxxxx, _snowmanxxxxx);
      }
   }

   private void setStructureStart(
      ConfiguredStructureFeature<?, ?> _snowman, DynamicRegistryManager _snowman, StructureAccessor _snowman, Chunk _snowman, StructureManager _snowman, long worldSeed, ChunkPos _snowman, Biome _snowman
   ) {
      StructureStart<?> _snowmanxxxxxxx = _snowman.getStructureStart(ChunkSectionPos.from(_snowman.getPos(), 0), _snowman.feature, _snowman);
      int _snowmanxxxxxxxx = _snowmanxxxxxxx != null ? _snowmanxxxxxxx.getReferences() : 0;
      StructureConfig _snowmanxxxxxxxxx = this.structuresConfig.getForType(_snowman.feature);
      if (_snowmanxxxxxxxxx != null) {
         StructureStart<?> _snowmanxxxxxxxxxx = _snowman.tryPlaceStart(_snowman, this, this.populationSource, _snowman, worldSeed, _snowman, _snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         _snowman.setStructureStart(ChunkSectionPos.from(_snowman.getPos(), 0), _snowman.feature, _snowmanxxxxxxxxxx, _snowman);
      }
   }

   public void addStructureReferences(StructureWorldAccess world, StructureAccessor accessor, Chunk chunk) {
      int _snowman = 8;
      int _snowmanx = chunk.getPos().x;
      int _snowmanxx = chunk.getPos().z;
      int _snowmanxxx = _snowmanx << 4;
      int _snowmanxxxx = _snowmanxx << 4;
      ChunkSectionPos _snowmanxxxxx = ChunkSectionPos.from(chunk.getPos(), 0);

      for (int _snowmanxxxxxx = _snowmanx - 8; _snowmanxxxxxx <= _snowmanx + 8; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = _snowmanxx - 8; _snowmanxxxxxxx <= _snowmanxx + 8; _snowmanxxxxxxx++) {
            long _snowmanxxxxxxxx = ChunkPos.toLong(_snowmanxxxxxx, _snowmanxxxxxxx);

            for (StructureStart<?> _snowmanxxxxxxxxx : world.getChunk(_snowmanxxxxxx, _snowmanxxxxxxx).getStructureStarts().values()) {
               try {
                  if (_snowmanxxxxxxxxx != StructureStart.DEFAULT && _snowmanxxxxxxxxx.getBoundingBox().intersectsXZ(_snowmanxxx, _snowmanxxxx, _snowmanxxx + 15, _snowmanxxxx + 15)) {
                     accessor.addStructureReference(_snowmanxxxxx, _snowmanxxxxxxxxx.getFeature(), _snowmanxxxxxxxx, chunk);
                     DebugInfoSender.sendStructureStart(world, _snowmanxxxxxxxxx);
                  }
               } catch (Exception var19) {
                  CrashReport _snowmanxxxxxxxxxx = CrashReport.create(var19, "Generating structure reference");
                  CrashReportSection _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.addElement("Structure");
                  _snowmanxxxxxxxxxxx.add("Id", () -> Registry.STRUCTURE_FEATURE.getId(_snowman.getFeature()).toString());
                  _snowmanxxxxxxxxxxx.add("Name", () -> _snowman.getFeature().getName());
                  _snowmanxxxxxxxxxxx.add("Class", () -> _snowman.getFeature().getClass().getCanonicalName());
                  throw new CrashException(_snowmanxxxxxxxxxx);
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

   public boolean isStrongholdStartingChunk(ChunkPos _snowman) {
      this.generateStrongholdPositions();
      return this.strongholds.contains(_snowman);
   }

   static {
      Registry.register(Registry.CHUNK_GENERATOR, "noise", NoiseChunkGenerator.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "flat", FlatChunkGenerator.CODEC);
      Registry.register(Registry.CHUNK_GENERATOR, "debug", DebugChunkGenerator.CODEC);
   }
}
