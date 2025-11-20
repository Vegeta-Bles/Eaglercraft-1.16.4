package net.minecraft.world.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkTickScheduler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProtoChunk implements Chunk {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ChunkPos pos;
   private volatile boolean shouldSave;
   @Nullable
   private BiomeArray biomes;
   @Nullable
   private volatile LightingProvider lightingProvider;
   private final Map<Heightmap.Type, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Type.class);
   private volatile ChunkStatus status = ChunkStatus.EMPTY;
   private final Map<BlockPos, BlockEntity> blockEntities = Maps.newHashMap();
   private final Map<BlockPos, CompoundTag> blockEntityTags = Maps.newHashMap();
   private final ChunkSection[] sections = new ChunkSection[16];
   private final List<CompoundTag> entities = Lists.newArrayList();
   private final List<BlockPos> lightSources = Lists.newArrayList();
   private final ShortList[] postProcessingLists = new ShortList[16];
   private final Map<StructureFeature<?>, StructureStart<?>> structureStarts = Maps.newHashMap();
   private final Map<StructureFeature<?>, LongSet> structureReferences = Maps.newHashMap();
   private final UpgradeData upgradeData;
   private final ChunkTickScheduler<Block> blockTickScheduler;
   private final ChunkTickScheduler<Fluid> fluidTickScheduler;
   private long inhabitedTime;
   private final Map<GenerationStep.Carver, BitSet> carvingMasks = new Object2ObjectArrayMap();
   private volatile boolean lightOn;

   public ProtoChunk(ChunkPos pos, UpgradeData upgradeData) {
      this(
         pos,
         upgradeData,
         null,
         new ChunkTickScheduler<>(block -> block == null || block.getDefaultState().isAir(), pos),
         new ChunkTickScheduler<>(fluid -> fluid == null || fluid == Fluids.EMPTY, pos)
      );
   }

   public ProtoChunk(
      ChunkPos pos,
      UpgradeData upgradeData,
      @Nullable ChunkSection[] sections,
      ChunkTickScheduler<Block> blockTickScheduler,
      ChunkTickScheduler<Fluid> fluidTickScheduler
   ) {
      this.pos = pos;
      this.upgradeData = upgradeData;
      this.blockTickScheduler = blockTickScheduler;
      this.fluidTickScheduler = fluidTickScheduler;
      if (sections != null) {
         if (this.sections.length == sections.length) {
            System.arraycopy(sections, 0, this.sections, 0, this.sections.length);
         } else {
            LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", sections.length, this.sections.length);
         }
      }
   }

   @Override
   public BlockState getBlockState(BlockPos pos) {
      int _snowman = pos.getY();
      if (World.isOutOfBuildLimitVertically(_snowman)) {
         return Blocks.VOID_AIR.getDefaultState();
      } else {
         ChunkSection _snowmanx = this.getSectionArray()[_snowman >> 4];
         return ChunkSection.isEmpty(_snowmanx) ? Blocks.AIR.getDefaultState() : _snowmanx.getBlockState(pos.getX() & 15, _snowman & 15, pos.getZ() & 15);
      }
   }

   @Override
   public FluidState getFluidState(BlockPos pos) {
      int _snowman = pos.getY();
      if (World.isOutOfBuildLimitVertically(_snowman)) {
         return Fluids.EMPTY.getDefaultState();
      } else {
         ChunkSection _snowmanx = this.getSectionArray()[_snowman >> 4];
         return ChunkSection.isEmpty(_snowmanx) ? Fluids.EMPTY.getDefaultState() : _snowmanx.getFluidState(pos.getX() & 15, _snowman & 15, pos.getZ() & 15);
      }
   }

   @Override
   public Stream<BlockPos> getLightSourcesStream() {
      return this.lightSources.stream();
   }

   public ShortList[] getLightSourcesBySection() {
      ShortList[] _snowman = new ShortList[16];

      for (BlockPos _snowmanx : this.lightSources) {
         Chunk.getList(_snowman, _snowmanx.getY() >> 4).add(getPackedSectionRelative(_snowmanx));
      }

      return _snowman;
   }

   public void addLightSource(short chunkSliceRel, int sectionY) {
      this.addLightSource(joinBlockPos(chunkSliceRel, sectionY, this.pos));
   }

   public void addLightSource(BlockPos pos) {
      this.lightSources.add(pos.toImmutable());
   }

   @Nullable
   @Override
   public BlockState setBlockState(BlockPos pos, BlockState state, boolean moved) {
      int _snowman = pos.getX();
      int _snowmanx = pos.getY();
      int _snowmanxx = pos.getZ();
      if (_snowmanx >= 0 && _snowmanx < 256) {
         if (this.sections[_snowmanx >> 4] == WorldChunk.EMPTY_SECTION && state.isOf(Blocks.AIR)) {
            return state;
         } else {
            if (state.getLuminance() > 0) {
               this.lightSources.add(new BlockPos((_snowman & 15) + this.getPos().getStartX(), _snowmanx, (_snowmanxx & 15) + this.getPos().getStartZ()));
            }

            ChunkSection _snowmanxxx = this.getSection(_snowmanx >> 4);
            BlockState _snowmanxxxx = _snowmanxxx.setBlockState(_snowman & 15, _snowmanx & 15, _snowmanxx & 15, state);
            if (this.status.isAtLeast(ChunkStatus.FEATURES)
               && state != _snowmanxxxx
               && (
                  state.getOpacity(this, pos) != _snowmanxxxx.getOpacity(this, pos)
                     || state.getLuminance() != _snowmanxxxx.getLuminance()
                     || state.hasSidedTransparency()
                     || _snowmanxxxx.hasSidedTransparency()
               )) {
               LightingProvider _snowmanxxxxx = this.getLightingProvider();
               _snowmanxxxxx.checkBlock(pos);
            }

            EnumSet<Heightmap.Type> _snowmanxxxxx = this.getStatus().getHeightmapTypes();
            EnumSet<Heightmap.Type> _snowmanxxxxxx = null;

            for (Heightmap.Type _snowmanxxxxxxx : _snowmanxxxxx) {
               Heightmap _snowmanxxxxxxxx = this.heightmaps.get(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx == null) {
                  if (_snowmanxxxxxx == null) {
                     _snowmanxxxxxx = EnumSet.noneOf(Heightmap.Type.class);
                  }

                  _snowmanxxxxxx.add(_snowmanxxxxxxx);
               }
            }

            if (_snowmanxxxxxx != null) {
               Heightmap.populateHeightmaps(this, _snowmanxxxxxx);
            }

            for (Heightmap.Type _snowmanxxxxxxxx : _snowmanxxxxx) {
               this.heightmaps.get(_snowmanxxxxxxxx).trackUpdate(_snowman & 15, _snowmanx, _snowmanxx & 15, state);
            }

            return _snowmanxxxx;
         }
      } else {
         return Blocks.VOID_AIR.getDefaultState();
      }
   }

   public ChunkSection getSection(int y) {
      if (this.sections[y] == WorldChunk.EMPTY_SECTION) {
         this.sections[y] = new ChunkSection(y << 4);
      }

      return this.sections[y];
   }

   @Override
   public void setBlockEntity(BlockPos pos, BlockEntity blockEntity) {
      blockEntity.setPos(pos);
      this.blockEntities.put(pos, blockEntity);
   }

   @Override
   public Set<BlockPos> getBlockEntityPositions() {
      Set<BlockPos> _snowman = Sets.newHashSet(this.blockEntityTags.keySet());
      _snowman.addAll(this.blockEntities.keySet());
      return _snowman;
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos pos) {
      return this.blockEntities.get(pos);
   }

   public Map<BlockPos, BlockEntity> getBlockEntities() {
      return this.blockEntities;
   }

   public void addEntity(CompoundTag entityTag) {
      this.entities.add(entityTag);
   }

   @Override
   public void addEntity(Entity entity) {
      if (!entity.hasVehicle()) {
         CompoundTag _snowman = new CompoundTag();
         entity.saveToTag(_snowman);
         this.addEntity(_snowman);
      }
   }

   public List<CompoundTag> getEntities() {
      return this.entities;
   }

   public void setBiomes(BiomeArray biomes) {
      this.biomes = biomes;
   }

   @Nullable
   @Override
   public BiomeArray getBiomeArray() {
      return this.biomes;
   }

   @Override
   public void setShouldSave(boolean shouldSave) {
      this.shouldSave = shouldSave;
   }

   @Override
   public boolean needsSaving() {
      return this.shouldSave;
   }

   @Override
   public ChunkStatus getStatus() {
      return this.status;
   }

   public void setStatus(ChunkStatus status) {
      this.status = status;
      this.setShouldSave(true);
   }

   @Override
   public ChunkSection[] getSectionArray() {
      return this.sections;
   }

   @Nullable
   public LightingProvider getLightingProvider() {
      return this.lightingProvider;
   }

   @Override
   public Collection<Entry<Heightmap.Type, Heightmap>> getHeightmaps() {
      return Collections.unmodifiableSet(this.heightmaps.entrySet());
   }

   @Override
   public void setHeightmap(Heightmap.Type type, long[] heightmap) {
      this.getHeightmap(type).setTo(heightmap);
   }

   @Override
   public Heightmap getHeightmap(Heightmap.Type type) {
      return this.heightmaps.computeIfAbsent(type, typex -> new Heightmap(this, typex));
   }

   @Override
   public int sampleHeightmap(Heightmap.Type type, int x, int z) {
      Heightmap _snowman = this.heightmaps.get(type);
      if (_snowman == null) {
         Heightmap.populateHeightmaps(this, EnumSet.of(type));
         _snowman = this.heightmaps.get(type);
      }

      return _snowman.get(x & 15, z & 15) - 1;
   }

   @Override
   public ChunkPos getPos() {
      return this.pos;
   }

   @Override
   public void setLastSaveTime(long lastSaveTime) {
   }

   @Nullable
   @Override
   public StructureStart<?> getStructureStart(StructureFeature<?> structure) {
      return this.structureStarts.get(structure);
   }

   @Override
   public void setStructureStart(StructureFeature<?> structure, StructureStart<?> start) {
      this.structureStarts.put(structure, start);
      this.shouldSave = true;
   }

   @Override
   public Map<StructureFeature<?>, StructureStart<?>> getStructureStarts() {
      return Collections.unmodifiableMap(this.structureStarts);
   }

   @Override
   public void setStructureStarts(Map<StructureFeature<?>, StructureStart<?>> structureStarts) {
      this.structureStarts.clear();
      this.structureStarts.putAll(structureStarts);
      this.shouldSave = true;
   }

   @Override
   public LongSet getStructureReferences(StructureFeature<?> structure) {
      return this.structureReferences.computeIfAbsent(structure, structurex -> new LongOpenHashSet());
   }

   @Override
   public void addStructureReference(StructureFeature<?> structure, long reference) {
      this.structureReferences.computeIfAbsent(structure, structurex -> new LongOpenHashSet()).add(reference);
      this.shouldSave = true;
   }

   @Override
   public Map<StructureFeature<?>, LongSet> getStructureReferences() {
      return Collections.unmodifiableMap(this.structureReferences);
   }

   @Override
   public void setStructureReferences(Map<StructureFeature<?>, LongSet> structureReferences) {
      this.structureReferences.clear();
      this.structureReferences.putAll(structureReferences);
      this.shouldSave = true;
   }

   public static short getPackedSectionRelative(BlockPos pos) {
      int _snowman = pos.getX();
      int _snowmanx = pos.getY();
      int _snowmanxx = pos.getZ();
      int _snowmanxxx = _snowman & 15;
      int _snowmanxxxx = _snowmanx & 15;
      int _snowmanxxxxx = _snowmanxx & 15;
      return (short)(_snowmanxxx | _snowmanxxxx << 4 | _snowmanxxxxx << 8);
   }

   public static BlockPos joinBlockPos(short sectionRel, int sectionY, ChunkPos chunkPos) {
      int _snowman = (sectionRel & 15) + (chunkPos.x << 4);
      int _snowmanx = (sectionRel >>> 4 & 15) + (sectionY << 4);
      int _snowmanxx = (sectionRel >>> 8 & 15) + (chunkPos.z << 4);
      return new BlockPos(_snowman, _snowmanx, _snowmanxx);
   }

   @Override
   public void markBlockForPostProcessing(BlockPos pos) {
      if (!World.isOutOfBuildLimitVertically(pos)) {
         Chunk.getList(this.postProcessingLists, pos.getY() >> 4).add(getPackedSectionRelative(pos));
      }
   }

   @Override
   public ShortList[] getPostProcessingLists() {
      return this.postProcessingLists;
   }

   @Override
   public void markBlockForPostProcessing(short _snowman, int _snowman) {
      Chunk.getList(this.postProcessingLists, _snowman).add(_snowman);
   }

   public ChunkTickScheduler<Block> getBlockTickScheduler() {
      return this.blockTickScheduler;
   }

   public ChunkTickScheduler<Fluid> getFluidTickScheduler() {
      return this.fluidTickScheduler;
   }

   @Override
   public UpgradeData getUpgradeData() {
      return this.upgradeData;
   }

   @Override
   public void setInhabitedTime(long inhabitedTime) {
      this.inhabitedTime = inhabitedTime;
   }

   @Override
   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   @Override
   public void addPendingBlockEntityTag(CompoundTag tag) {
      this.blockEntityTags.put(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")), tag);
   }

   public Map<BlockPos, CompoundTag> getBlockEntityTags() {
      return Collections.unmodifiableMap(this.blockEntityTags);
   }

   @Override
   public CompoundTag getBlockEntityTag(BlockPos pos) {
      return this.blockEntityTags.get(pos);
   }

   @Nullable
   @Override
   public CompoundTag getPackedBlockEntityTag(BlockPos pos) {
      BlockEntity _snowman = this.getBlockEntity(pos);
      return _snowman != null ? _snowman.toTag(new CompoundTag()) : this.blockEntityTags.get(pos);
   }

   @Override
   public void removeBlockEntity(BlockPos pos) {
      this.blockEntities.remove(pos);
      this.blockEntityTags.remove(pos);
   }

   @Nullable
   public BitSet getCarvingMask(GenerationStep.Carver carver) {
      return this.carvingMasks.get(carver);
   }

   public BitSet getOrCreateCarvingMask(GenerationStep.Carver carver) {
      return this.carvingMasks.computeIfAbsent(carver, carverx -> new BitSet(65536));
   }

   public void setCarvingMask(GenerationStep.Carver carver, BitSet mask) {
      this.carvingMasks.put(carver, mask);
   }

   public void setLightingProvider(LightingProvider lightingProvider) {
      this.lightingProvider = lightingProvider;
   }

   @Override
   public boolean isLightOn() {
      return this.lightOn;
   }

   @Override
   public void setLightOn(boolean lightOn) {
      this.lightOn = lightOn;
      this.setShouldSave(true);
   }
}
