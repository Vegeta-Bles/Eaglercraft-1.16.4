package net.minecraft.world.chunk;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.DummyClientTickScheduler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ChunkHolder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SimpleTickScheduler;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.collection.TypeFilterableList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkTickScheduler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldChunk implements Chunk {
   private static final Logger LOGGER = LogManager.getLogger();
   @Nullable
   public static final ChunkSection EMPTY_SECTION = null;
   private final ChunkSection[] sections = new ChunkSection[16];
   private BiomeArray biomeArray;
   private final Map<BlockPos, CompoundTag> pendingBlockEntityTags = Maps.newHashMap();
   private boolean loadedToWorld;
   private final World world;
   private final Map<Heightmap.Type, Heightmap> heightmaps = Maps.newEnumMap(Heightmap.Type.class);
   private final UpgradeData upgradeData;
   private final Map<BlockPos, BlockEntity> blockEntities = Maps.newHashMap();
   private final TypeFilterableList<Entity>[] entitySections;
   private final Map<StructureFeature<?>, StructureStart<?>> structureStarts = Maps.newHashMap();
   private final Map<StructureFeature<?>, LongSet> structureReferences = Maps.newHashMap();
   private final ShortList[] postProcessingLists = new ShortList[16];
   private TickScheduler<Block> blockTickScheduler;
   private TickScheduler<Fluid> fluidTickScheduler;
   private boolean unsaved;
   private long lastSaveTime;
   private volatile boolean shouldSave;
   private long inhabitedTime;
   @Nullable
   private Supplier<ChunkHolder.LevelType> levelTypeProvider;
   @Nullable
   private Consumer<WorldChunk> loadToWorldConsumer;
   private final ChunkPos pos;
   private volatile boolean lightOn;

   public WorldChunk(World world, ChunkPos pos, BiomeArray biomes) {
      this(
         world,
         pos,
         biomes,
         UpgradeData.NO_UPGRADE_DATA,
         DummyClientTickScheduler.<Block>get(),
         DummyClientTickScheduler.<Fluid>get(),
         0L,
         null,
         null
      );
   }

   public WorldChunk(
      World world,
      ChunkPos pos,
      BiomeArray biomes,
      UpgradeData upgradeData,
      TickScheduler<Block> blockTickScheduler,
      TickScheduler<Fluid> fluidTickScheduler,
      long inhabitedTime,
      @Nullable ChunkSection[] sections,
      @Nullable Consumer<WorldChunk> loadToWorldConsumer
   ) {
      this.entitySections = new TypeFilterableList[16];
      this.world = world;
      this.pos = pos;
      this.upgradeData = upgradeData;

      for (Heightmap.Type lv : Heightmap.Type.values()) {
         if (ChunkStatus.FULL.getHeightmapTypes().contains(lv)) {
            this.heightmaps.put(lv, new Heightmap(this, lv));
         }
      }

      for (int i = 0; i < this.entitySections.length; i++) {
         this.entitySections[i] = new TypeFilterableList<>(Entity.class);
      }

      this.biomeArray = biomes;
      this.blockTickScheduler = blockTickScheduler;
      this.fluidTickScheduler = fluidTickScheduler;
      this.inhabitedTime = inhabitedTime;
      this.loadToWorldConsumer = loadToWorldConsumer;
      if (sections != null) {
         if (this.sections.length == sections.length) {
            System.arraycopy(sections, 0, this.sections, 0, this.sections.length);
         } else {
            LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", sections.length, this.sections.length);
         }
      }
   }

   public WorldChunk(World world, ProtoChunk protoChunk) {
      this(
         world,
         protoChunk.getPos(),
         protoChunk.getBiomeArray(),
         protoChunk.getUpgradeData(),
         protoChunk.getBlockTickScheduler(),
         protoChunk.getFluidTickScheduler(),
         protoChunk.getInhabitedTime(),
         protoChunk.getSectionArray(),
         null
      );

      for (CompoundTag lv : protoChunk.getEntities()) {
         EntityType.loadEntityWithPassengers(lv, world, entity -> {
            this.addEntity(entity);
            return entity;
         });
      }

      for (BlockEntity lv2 : protoChunk.getBlockEntities().values()) {
         this.addBlockEntity(lv2);
      }

      this.pendingBlockEntityTags.putAll(protoChunk.getBlockEntityTags());

      for (int i = 0; i < protoChunk.getPostProcessingLists().length; i++) {
         this.postProcessingLists[i] = protoChunk.getPostProcessingLists()[i];
      }

      this.setStructureStarts(protoChunk.getStructureStarts());
      this.setStructureReferences(protoChunk.getStructureReferences());

      for (Entry<Heightmap.Type, Heightmap> entry : protoChunk.getHeightmaps()) {
         if (ChunkStatus.FULL.getHeightmapTypes().contains(entry.getKey())) {
            this.getHeightmap(entry.getKey()).setTo(entry.getValue().asLongArray());
         }
      }

      this.setLightOn(protoChunk.isLightOn());
      this.shouldSave = true;
   }

   @Override
   public Heightmap getHeightmap(Heightmap.Type type) {
      return this.heightmaps.computeIfAbsent(type, typex -> new Heightmap(this, typex));
   }

   @Override
   public Set<BlockPos> getBlockEntityPositions() {
      Set<BlockPos> set = Sets.newHashSet(this.pendingBlockEntityTags.keySet());
      set.addAll(this.blockEntities.keySet());
      return set;
   }

   @Override
   public ChunkSection[] getSectionArray() {
      return this.sections;
   }

   @Override
   public BlockState getBlockState(BlockPos pos) {
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();
      if (this.world.isDebugWorld()) {
         BlockState lv = null;
         if (j == 60) {
            lv = Blocks.BARRIER.getDefaultState();
         }

         if (j == 70) {
            lv = DebugChunkGenerator.getBlockState(i, k);
         }

         return lv == null ? Blocks.AIR.getDefaultState() : lv;
      } else {
         try {
            if (j >= 0 && j >> 4 < this.sections.length) {
               ChunkSection lv2 = this.sections[j >> 4];
               if (!ChunkSection.isEmpty(lv2)) {
                  return lv2.getBlockState(i & 15, j & 15, k & 15);
               }
            }

            return Blocks.AIR.getDefaultState();
         } catch (Throwable var8) {
            CrashReport lv3 = CrashReport.create(var8, "Getting block state");
            CrashReportSection lv4 = lv3.addElement("Block being got");
            lv4.add("Location", () -> CrashReportSection.createPositionString(i, j, k));
            throw new CrashException(lv3);
         }
      }
   }

   @Override
   public FluidState getFluidState(BlockPos pos) {
      return this.getFluidState(pos.getX(), pos.getY(), pos.getZ());
   }

   public FluidState getFluidState(int x, int y, int z) {
      try {
         if (y >= 0 && y >> 4 < this.sections.length) {
            ChunkSection lv = this.sections[y >> 4];
            if (!ChunkSection.isEmpty(lv)) {
               return lv.getFluidState(x & 15, y & 15, z & 15);
            }
         }

         return Fluids.EMPTY.getDefaultState();
      } catch (Throwable var7) {
         CrashReport lv2 = CrashReport.create(var7, "Getting fluid state");
         CrashReportSection lv3 = lv2.addElement("Block being got");
         lv3.add("Location", () -> CrashReportSection.createPositionString(x, y, z));
         throw new CrashException(lv2);
      }
   }

   @Nullable
   @Override
   public BlockState setBlockState(BlockPos pos, BlockState state, boolean moved) {
      int i = pos.getX() & 15;
      int j = pos.getY();
      int k = pos.getZ() & 15;
      ChunkSection lv = this.sections[j >> 4];
      if (lv == EMPTY_SECTION) {
         if (state.isAir()) {
            return null;
         }

         lv = new ChunkSection(j >> 4 << 4);
         this.sections[j >> 4] = lv;
      }

      boolean bl2 = lv.isEmpty();
      BlockState lv2 = lv.setBlockState(i, j & 15, k, state);
      if (lv2 == state) {
         return null;
      } else {
         Block lv3 = state.getBlock();
         Block lv4 = lv2.getBlock();
         this.heightmaps.get(Heightmap.Type.MOTION_BLOCKING).trackUpdate(i, j, k, state);
         this.heightmaps.get(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).trackUpdate(i, j, k, state);
         this.heightmaps.get(Heightmap.Type.OCEAN_FLOOR).trackUpdate(i, j, k, state);
         this.heightmaps.get(Heightmap.Type.WORLD_SURFACE).trackUpdate(i, j, k, state);
         boolean bl3 = lv.isEmpty();
         if (bl2 != bl3) {
            this.world.getChunkManager().getLightingProvider().setSectionStatus(pos, bl3);
         }

         if (!this.world.isClient) {
            lv2.onStateReplaced(this.world, pos, state, moved);
         } else if (lv4 != lv3 && lv4 instanceof BlockEntityProvider) {
            this.world.removeBlockEntity(pos);
         }

         if (!lv.getBlockState(i, j & 15, k).isOf(lv3)) {
            return null;
         } else {
            if (lv4 instanceof BlockEntityProvider) {
               BlockEntity lv5 = this.getBlockEntity(pos, WorldChunk.CreationType.CHECK);
               if (lv5 != null) {
                  lv5.resetBlock();
               }
            }

            if (!this.world.isClient) {
               state.onBlockAdded(this.world, pos, lv2, moved);
            }

            if (lv3 instanceof BlockEntityProvider) {
               BlockEntity lv6 = this.getBlockEntity(pos, WorldChunk.CreationType.CHECK);
               if (lv6 == null) {
                  lv6 = ((BlockEntityProvider)lv3).createBlockEntity(this.world);
                  this.world.setBlockEntity(pos, lv6);
               } else {
                  lv6.resetBlock();
               }
            }

            this.shouldSave = true;
            return lv2;
         }
      }
   }

   @Nullable
   public LightingProvider getLightingProvider() {
      return this.world.getChunkManager().getLightingProvider();
   }

   @Override
   public void addEntity(Entity entity) {
      this.unsaved = true;
      int i = MathHelper.floor(entity.getX() / 16.0);
      int j = MathHelper.floor(entity.getZ() / 16.0);
      if (i != this.pos.x || j != this.pos.z) {
         LOGGER.warn("Wrong location! ({}, {}) should be ({}, {}), {}", i, j, this.pos.x, this.pos.z, entity);
         entity.removed = true;
      }

      int k = MathHelper.floor(entity.getY() / 16.0);
      if (k < 0) {
         k = 0;
      }

      if (k >= this.entitySections.length) {
         k = this.entitySections.length - 1;
      }

      entity.updateNeeded = true;
      entity.chunkX = this.pos.x;
      entity.chunkY = k;
      entity.chunkZ = this.pos.z;
      this.entitySections[k].add(entity);
   }

   @Override
   public void setHeightmap(Heightmap.Type type, long[] heightmap) {
      this.heightmaps.get(type).setTo(heightmap);
   }

   public void remove(Entity entity) {
      this.remove(entity, entity.chunkY);
   }

   public void remove(Entity entity, int section) {
      if (section < 0) {
         section = 0;
      }

      if (section >= this.entitySections.length) {
         section = this.entitySections.length - 1;
      }

      this.entitySections[section].remove(entity);
   }

   @Override
   public int sampleHeightmap(Heightmap.Type type, int x, int z) {
      return this.heightmaps.get(type).get(x & 15, z & 15) - 1;
   }

   @Nullable
   private BlockEntity createBlockEntity(BlockPos pos) {
      BlockState lv = this.getBlockState(pos);
      Block lv2 = lv.getBlock();
      return !lv2.hasBlockEntity() ? null : ((BlockEntityProvider)lv2).createBlockEntity(this.world);
   }

   @Nullable
   @Override
   public BlockEntity getBlockEntity(BlockPos pos) {
      return this.getBlockEntity(pos, WorldChunk.CreationType.CHECK);
   }

   @Nullable
   public BlockEntity getBlockEntity(BlockPos pos, WorldChunk.CreationType creationType) {
      BlockEntity lv = this.blockEntities.get(pos);
      if (lv == null) {
         CompoundTag lv2 = this.pendingBlockEntityTags.remove(pos);
         if (lv2 != null) {
            BlockEntity lv3 = this.loadBlockEntity(pos, lv2);
            if (lv3 != null) {
               return lv3;
            }
         }
      }

      if (lv == null) {
         if (creationType == WorldChunk.CreationType.IMMEDIATE) {
            lv = this.createBlockEntity(pos);
            this.world.setBlockEntity(pos, lv);
         }
      } else if (lv.isRemoved()) {
         this.blockEntities.remove(pos);
         return null;
      }

      return lv;
   }

   public void addBlockEntity(BlockEntity blockEntity) {
      this.setBlockEntity(blockEntity.getPos(), blockEntity);
      if (this.loadedToWorld || this.world.isClient()) {
         this.world.setBlockEntity(blockEntity.getPos(), blockEntity);
      }
   }

   @Override
   public void setBlockEntity(BlockPos pos, BlockEntity blockEntity) {
      if (this.getBlockState(pos).getBlock() instanceof BlockEntityProvider) {
         blockEntity.setLocation(this.world, pos);
         blockEntity.cancelRemoval();
         BlockEntity lv = this.blockEntities.put(pos.toImmutable(), blockEntity);
         if (lv != null && lv != blockEntity) {
            lv.markRemoved();
         }
      }
   }

   @Override
   public void addPendingBlockEntityTag(CompoundTag tag) {
      this.pendingBlockEntityTags.put(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")), tag);
   }

   @Nullable
   @Override
   public CompoundTag getPackedBlockEntityTag(BlockPos pos) {
      BlockEntity lv = this.getBlockEntity(pos);
      if (lv != null && !lv.isRemoved()) {
         CompoundTag lv2 = lv.toTag(new CompoundTag());
         lv2.putBoolean("keepPacked", false);
         return lv2;
      } else {
         CompoundTag lv3 = this.pendingBlockEntityTags.get(pos);
         if (lv3 != null) {
            lv3 = lv3.copy();
            lv3.putBoolean("keepPacked", true);
         }

         return lv3;
      }
   }

   @Override
   public void removeBlockEntity(BlockPos pos) {
      if (this.loadedToWorld || this.world.isClient()) {
         BlockEntity lv = this.blockEntities.remove(pos);
         if (lv != null) {
            lv.markRemoved();
         }
      }
   }

   public void loadToWorld() {
      if (this.loadToWorldConsumer != null) {
         this.loadToWorldConsumer.accept(this);
         this.loadToWorldConsumer = null;
      }
   }

   public void markDirty() {
      this.shouldSave = true;
   }

   public void collectOtherEntities(@Nullable Entity except, Box box, List<Entity> entityList, @Nullable Predicate<? super Entity> predicate) {
      int i = MathHelper.floor((box.minY - 2.0) / 16.0);
      int j = MathHelper.floor((box.maxY + 2.0) / 16.0);
      i = MathHelper.clamp(i, 0, this.entitySections.length - 1);
      j = MathHelper.clamp(j, 0, this.entitySections.length - 1);

      for (int k = i; k <= j; k++) {
         TypeFilterableList<Entity> lv = this.entitySections[k];
         List<Entity> list2 = lv.method_29903();
         int l = list2.size();

         for (int m = 0; m < l; m++) {
            Entity lv2 = list2.get(m);
            if (lv2.getBoundingBox().intersects(box) && lv2 != except) {
               if (predicate == null || predicate.test(lv2)) {
                  entityList.add(lv2);
               }

               if (lv2 instanceof EnderDragonEntity) {
                  for (EnderDragonPart lv3 : ((EnderDragonEntity)lv2).getBodyParts()) {
                     if (lv3 != except && lv3.getBoundingBox().intersects(box) && (predicate == null || predicate.test(lv3))) {
                        entityList.add(lv3);
                     }
                  }
               }
            }
         }
      }
   }

   public <T extends Entity> void collectEntities(@Nullable EntityType<?> type, Box box, List<? super T> result, Predicate<? super T> predicate) {
      int i = MathHelper.floor((box.minY - 2.0) / 16.0);
      int j = MathHelper.floor((box.maxY + 2.0) / 16.0);
      i = MathHelper.clamp(i, 0, this.entitySections.length - 1);
      j = MathHelper.clamp(j, 0, this.entitySections.length - 1);

      for (int k = i; k <= j; k++) {
         for (Entity lv : this.entitySections[k].getAllOfType(Entity.class)) {
            if ((type == null || lv.getType() == type) && lv.getBoundingBox().intersects(box) && predicate.test((T)lv)) {
               result.add((T)lv);
            }
         }
      }
   }

   public <T extends Entity> void collectEntitiesByClass(Class<? extends T> entityClass, Box box, List<T> result, @Nullable Predicate<? super T> predicate) {
      int i = MathHelper.floor((box.minY - 2.0) / 16.0);
      int j = MathHelper.floor((box.maxY + 2.0) / 16.0);
      i = MathHelper.clamp(i, 0, this.entitySections.length - 1);
      j = MathHelper.clamp(j, 0, this.entitySections.length - 1);

      for (int k = i; k <= j; k++) {
         for (T lv : this.entitySections[k].getAllOfType(entityClass)) {
            if (lv.getBoundingBox().intersects(box) && (predicate == null || predicate.test(lv))) {
               result.add(lv);
            }
         }
      }
   }

   public boolean isEmpty() {
      return false;
   }

   @Override
   public ChunkPos getPos() {
      return this.pos;
   }

   @Environment(EnvType.CLIENT)
   public void loadFromPacket(@Nullable BiomeArray biomes, PacketByteBuf buf, CompoundTag tag, int verticalStripBitmask) {
      boolean bl = biomes != null;
      Predicate<BlockPos> predicate = bl ? pos -> true : pos -> (verticalStripBitmask & 1 << (pos.getY() >> 4)) != 0;
      Sets.newHashSet(this.blockEntities.keySet()).stream().filter(predicate).forEach(this.world::removeBlockEntity);

      for (int j = 0; j < this.sections.length; j++) {
         ChunkSection lv = this.sections[j];
         if ((verticalStripBitmask & 1 << j) == 0) {
            if (bl && lv != EMPTY_SECTION) {
               this.sections[j] = EMPTY_SECTION;
            }
         } else {
            if (lv == EMPTY_SECTION) {
               lv = new ChunkSection(j << 4);
               this.sections[j] = lv;
            }

            lv.fromPacket(buf);
         }
      }

      if (biomes != null) {
         this.biomeArray = biomes;
      }

      for (Heightmap.Type lv2 : Heightmap.Type.values()) {
         String string = lv2.getName();
         if (tag.contains(string, 12)) {
            this.setHeightmap(lv2, tag.getLongArray(string));
         }
      }

      for (BlockEntity lv3 : this.blockEntities.values()) {
         lv3.resetBlock();
      }
   }

   @Override
   public BiomeArray getBiomeArray() {
      return this.biomeArray;
   }

   public void setLoadedToWorld(boolean loaded) {
      this.loadedToWorld = loaded;
   }

   public World getWorld() {
      return this.world;
   }

   @Override
   public Collection<Entry<Heightmap.Type, Heightmap>> getHeightmaps() {
      return Collections.unmodifiableSet(this.heightmaps.entrySet());
   }

   public Map<BlockPos, BlockEntity> getBlockEntities() {
      return this.blockEntities;
   }

   public TypeFilterableList<Entity>[] getEntitySectionArray() {
      return this.entitySections;
   }

   @Override
   public CompoundTag getBlockEntityTag(BlockPos pos) {
      return this.pendingBlockEntityTags.get(pos);
   }

   @Override
   public Stream<BlockPos> getLightSourcesStream() {
      return StreamSupport.stream(
            BlockPos.iterate(this.pos.getStartX(), 0, this.pos.getStartZ(), this.pos.getEndX(), 255, this.pos.getEndZ()).spliterator(), false
         )
         .filter(arg -> this.getBlockState(arg).getLuminance() != 0);
   }

   @Override
   public TickScheduler<Block> getBlockTickScheduler() {
      return this.blockTickScheduler;
   }

   @Override
   public TickScheduler<Fluid> getFluidTickScheduler() {
      return this.fluidTickScheduler;
   }

   @Override
   public void setShouldSave(boolean shouldSave) {
      this.shouldSave = shouldSave;
   }

   @Override
   public boolean needsSaving() {
      return this.shouldSave || this.unsaved && this.world.getTime() != this.lastSaveTime;
   }

   public void setUnsaved(boolean unsaved) {
      this.unsaved = unsaved;
   }

   @Override
   public void setLastSaveTime(long lastSaveTime) {
      this.lastSaveTime = lastSaveTime;
   }

   @Nullable
   @Override
   public StructureStart<?> getStructureStart(StructureFeature<?> structure) {
      return this.structureStarts.get(structure);
   }

   @Override
   public void setStructureStart(StructureFeature<?> structure, StructureStart<?> start) {
      this.structureStarts.put(structure, start);
   }

   @Override
   public Map<StructureFeature<?>, StructureStart<?>> getStructureStarts() {
      return this.structureStarts;
   }

   @Override
   public void setStructureStarts(Map<StructureFeature<?>, StructureStart<?>> structureStarts) {
      this.structureStarts.clear();
      this.structureStarts.putAll(structureStarts);
   }

   @Override
   public LongSet getStructureReferences(StructureFeature<?> structure) {
      return this.structureReferences.computeIfAbsent(structure, structurex -> new LongOpenHashSet());
   }

   @Override
   public void addStructureReference(StructureFeature<?> structure, long reference) {
      this.structureReferences.computeIfAbsent(structure, structurex -> new LongOpenHashSet()).add(reference);
   }

   @Override
   public Map<StructureFeature<?>, LongSet> getStructureReferences() {
      return this.structureReferences;
   }

   @Override
   public void setStructureReferences(Map<StructureFeature<?>, LongSet> structureReferences) {
      this.structureReferences.clear();
      this.structureReferences.putAll(structureReferences);
   }

   @Override
   public long getInhabitedTime() {
      return this.inhabitedTime;
   }

   @Override
   public void setInhabitedTime(long inhabitedTime) {
      this.inhabitedTime = inhabitedTime;
   }

   public void runPostProcessing() {
      ChunkPos lv = this.getPos();

      for (int i = 0; i < this.postProcessingLists.length; i++) {
         if (this.postProcessingLists[i] != null) {
            ShortListIterator lv5 = this.postProcessingLists[i].iterator();

            while (lv5.hasNext()) {
               Short short_ = (Short)lv5.next();
               BlockPos lv2 = ProtoChunk.joinBlockPos(short_, i, lv);
               BlockState lv3 = this.getBlockState(lv2);
               BlockState lv4 = Block.postProcessState(lv3, this.world, lv2);
               this.world.setBlockState(lv2, lv4, 20);
            }

            this.postProcessingLists[i].clear();
         }
      }

      this.disableTickSchedulers();

      for (BlockPos lv5 : Sets.newHashSet(this.pendingBlockEntityTags.keySet())) {
         this.getBlockEntity(lv5);
      }

      this.pendingBlockEntityTags.clear();
      this.upgradeData.upgrade(this);
   }

   @Nullable
   private BlockEntity loadBlockEntity(BlockPos pos, CompoundTag tag) {
      BlockState lv = this.getBlockState(pos);
      BlockEntity lv3;
      if ("DUMMY".equals(tag.getString("id"))) {
         Block lv2 = lv.getBlock();
         if (lv2 instanceof BlockEntityProvider) {
            lv3 = ((BlockEntityProvider)lv2).createBlockEntity(this.world);
         } else {
            lv3 = null;
            LOGGER.warn("Tried to load a DUMMY block entity @ {} but found not block entity block {} at location", pos, lv);
         }
      } else {
         lv3 = BlockEntity.createFromTag(lv, tag);
      }

      if (lv3 != null) {
         lv3.setLocation(this.world, pos);
         this.addBlockEntity(lv3);
      } else {
         LOGGER.warn("Tried to load a block entity for block {} but failed at location {}", lv, pos);
      }

      return lv3;
   }

   @Override
   public UpgradeData getUpgradeData() {
      return this.upgradeData;
   }

   @Override
   public ShortList[] getPostProcessingLists() {
      return this.postProcessingLists;
   }

   public void disableTickSchedulers() {
      if (this.blockTickScheduler instanceof ChunkTickScheduler) {
         ((ChunkTickScheduler<Block>)this.blockTickScheduler)
            .tick(this.world.getBlockTickScheduler(), pos -> this.getBlockState(pos).getBlock());
         this.blockTickScheduler = DummyClientTickScheduler.<Block>get();
      } else if (this.blockTickScheduler instanceof SimpleTickScheduler) {
         ((SimpleTickScheduler)this.blockTickScheduler).scheduleTo(this.world.getBlockTickScheduler());
         this.blockTickScheduler = DummyClientTickScheduler.<Block>get();
      }

      if (this.fluidTickScheduler instanceof ChunkTickScheduler) {
         ((ChunkTickScheduler<Fluid>)this.fluidTickScheduler)
            .tick(this.world.getFluidTickScheduler(), pos -> this.getFluidState(pos).getFluid());
         this.fluidTickScheduler = DummyClientTickScheduler.<Fluid>get();
      } else if (this.fluidTickScheduler instanceof SimpleTickScheduler) {
         ((SimpleTickScheduler)this.fluidTickScheduler).scheduleTo(this.world.getFluidTickScheduler());
         this.fluidTickScheduler = DummyClientTickScheduler.<Fluid>get();
      }
   }

   public void enableTickSchedulers(ServerWorld world) {
      if (this.blockTickScheduler == DummyClientTickScheduler.<Block>get()) {
         this.blockTickScheduler = new SimpleTickScheduler<>(
            Registry.BLOCK::getId, world.getBlockTickScheduler().getScheduledTicksInChunk(this.pos, true, false), world.getTime()
         );
         this.setShouldSave(true);
      }

      if (this.fluidTickScheduler == DummyClientTickScheduler.<Fluid>get()) {
         this.fluidTickScheduler = new SimpleTickScheduler<>(
            Registry.FLUID::getId, world.getFluidTickScheduler().getScheduledTicksInChunk(this.pos, true, false), world.getTime()
         );
         this.setShouldSave(true);
      }
   }

   @Override
   public ChunkStatus getStatus() {
      return ChunkStatus.FULL;
   }

   public ChunkHolder.LevelType getLevelType() {
      return this.levelTypeProvider == null ? ChunkHolder.LevelType.BORDER : this.levelTypeProvider.get();
   }

   public void setLevelTypeProvider(Supplier<ChunkHolder.LevelType> levelTypeProvider) {
      this.levelTypeProvider = levelTypeProvider;
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

   public static enum CreationType {
      IMMEDIATE,
      QUEUED,
      CHECK;

      private CreationType() {
      }
   }
}
