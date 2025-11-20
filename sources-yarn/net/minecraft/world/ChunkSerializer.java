package net.minecraft.world;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SimpleTickScheduler;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadOnlyChunk;
import net.minecraft.world.chunk.UpgradeData;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.poi.PointOfInterestStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkSerializer {
   private static final Logger LOGGER = LogManager.getLogger();

   public static ProtoChunk deserialize(ServerWorld world, StructureManager structureManager, PointOfInterestStorage poiStorage, ChunkPos pos, CompoundTag tag) {
      ChunkGenerator lv = world.getChunkManager().getChunkGenerator();
      BiomeSource lv2 = lv.getBiomeSource();
      CompoundTag lv3 = tag.getCompound("Level");
      ChunkPos lv4 = new ChunkPos(lv3.getInt("xPos"), lv3.getInt("zPos"));
      if (!Objects.equals(pos, lv4)) {
         LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", pos, pos, lv4);
      }

      BiomeArray lv5 = new BiomeArray(
         world.getRegistryManager().get(Registry.BIOME_KEY), pos, lv2, lv3.contains("Biomes", 11) ? lv3.getIntArray("Biomes") : null
      );
      UpgradeData lv6 = lv3.contains("UpgradeData", 10) ? new UpgradeData(lv3.getCompound("UpgradeData")) : UpgradeData.NO_UPGRADE_DATA;
      ChunkTickScheduler<Block> lv7 = new ChunkTickScheduler<>(block -> block == null || block.getDefaultState().isAir(), pos, lv3.getList("ToBeTicked", 9));
      ChunkTickScheduler<Fluid> lv8 = new ChunkTickScheduler<>(fluid -> fluid == null || fluid == Fluids.EMPTY, pos, lv3.getList("LiquidsToBeTicked", 9));
      boolean bl = lv3.getBoolean("isLightOn");
      ListTag lv9 = lv3.getList("Sections", 10);
      int i = 16;
      ChunkSection[] lvs = new ChunkSection[16];
      boolean bl2 = world.getDimension().hasSkyLight();
      ChunkManager lv10 = world.getChunkManager();
      LightingProvider lv11 = lv10.getLightingProvider();
      if (bl) {
         lv11.setRetainData(pos, true);
      }

      for (int j = 0; j < lv9.size(); j++) {
         CompoundTag lv12 = lv9.getCompound(j);
         int k = lv12.getByte("Y");
         if (lv12.contains("Palette", 9) && lv12.contains("BlockStates", 12)) {
            ChunkSection lv13 = new ChunkSection(k << 4);
            lv13.getContainer().read(lv12.getList("Palette", 10), lv12.getLongArray("BlockStates"));
            lv13.calculateCounts();
            if (!lv13.isEmpty()) {
               lvs[k] = lv13;
            }

            poiStorage.initForPalette(pos, lv13);
         }

         if (bl) {
            if (lv12.contains("BlockLight", 7)) {
               lv11.enqueueSectionData(LightType.BLOCK, ChunkSectionPos.from(pos, k), new ChunkNibbleArray(lv12.getByteArray("BlockLight")), true);
            }

            if (bl2 && lv12.contains("SkyLight", 7)) {
               lv11.enqueueSectionData(LightType.SKY, ChunkSectionPos.from(pos, k), new ChunkNibbleArray(lv12.getByteArray("SkyLight")), true);
            }
         }
      }

      long l = lv3.getLong("InhabitedTime");
      ChunkStatus.ChunkType lv14 = getChunkType(tag);
      Chunk lv19;
      if (lv14 == ChunkStatus.ChunkType.field_12807) {
         TickScheduler<Block> lv15;
         if (lv3.contains("TileTicks", 9)) {
            lv15 = SimpleTickScheduler.fromNbt(lv3.getList("TileTicks", 10), Registry.BLOCK::getId, Registry.BLOCK::get);
         } else {
            lv15 = lv7;
         }

         TickScheduler<Fluid> lv17;
         if (lv3.contains("LiquidTicks", 9)) {
            lv17 = SimpleTickScheduler.fromNbt(lv3.getList("LiquidTicks", 10), Registry.FLUID::getId, Registry.FLUID::get);
         } else {
            lv17 = lv8;
         }

         lv19 = new WorldChunk(world.toServerWorld(), pos, lv5, lv6, lv15, lv17, l, lvs, chunk -> writeEntities(lv3, chunk));
      } else {
         ProtoChunk lv20 = new ProtoChunk(pos, lv6, lvs, lv7, lv8);
         lv20.setBiomes(lv5);
         lv19 = lv20;
         lv20.setInhabitedTime(l);
         lv20.setStatus(ChunkStatus.byId(lv3.getString("Status")));
         if (lv20.getStatus().isAtLeast(ChunkStatus.FEATURES)) {
            lv20.setLightingProvider(lv11);
         }

         if (!bl && lv20.getStatus().isAtLeast(ChunkStatus.LIGHT)) {
            for (BlockPos lv22 : BlockPos.iterate(pos.getStartX(), 0, pos.getStartZ(), pos.getEndX(), 255, pos.getEndZ())) {
               if (lv19.getBlockState(lv22).getLuminance() != 0) {
                  lv20.addLightSource(lv22);
               }
            }
         }
      }

      lv19.setLightOn(bl);
      CompoundTag lv23 = lv3.getCompound("Heightmaps");
      EnumSet<Heightmap.Type> enumSet = EnumSet.noneOf(Heightmap.Type.class);

      for (Heightmap.Type lv24 : lv19.getStatus().getHeightmapTypes()) {
         String string = lv24.getName();
         if (lv23.contains(string, 12)) {
            lv19.setHeightmap(lv24, lv23.getLongArray(string));
         } else {
            enumSet.add(lv24);
         }
      }

      Heightmap.populateHeightmaps(lv19, enumSet);
      CompoundTag lv25 = lv3.getCompound("Structures");
      lv19.setStructureStarts(readStructureStarts(structureManager, lv25, world.getSeed()));
      lv19.setStructureReferences(readStructureReferences(pos, lv25));
      if (lv3.getBoolean("shouldSave")) {
         lv19.setShouldSave(true);
      }

      ListTag lv26 = lv3.getList("PostProcessing", 9);

      for (int m = 0; m < lv26.size(); m++) {
         ListTag lv27 = lv26.getList(m);

         for (int n = 0; n < lv27.size(); n++) {
            lv19.markBlockForPostProcessing(lv27.getShort(n), m);
         }
      }

      if (lv14 == ChunkStatus.ChunkType.field_12807) {
         return new ReadOnlyChunk((WorldChunk)lv19);
      } else {
         ProtoChunk lv28 = (ProtoChunk)lv19;
         ListTag lv29 = lv3.getList("Entities", 10);

         for (int o = 0; o < lv29.size(); o++) {
            lv28.addEntity(lv29.getCompound(o));
         }

         ListTag lv30 = lv3.getList("TileEntities", 10);

         for (int p = 0; p < lv30.size(); p++) {
            CompoundTag lv31 = lv30.getCompound(p);
            lv19.addPendingBlockEntityTag(lv31);
         }

         ListTag lv32 = lv3.getList("Lights", 9);

         for (int q = 0; q < lv32.size(); q++) {
            ListTag lv33 = lv32.getList(q);

            for (int r = 0; r < lv33.size(); r++) {
               lv28.addLightSource(lv33.getShort(r), q);
            }
         }

         CompoundTag lv34 = lv3.getCompound("CarvingMasks");

         for (String string2 : lv34.getKeys()) {
            GenerationStep.Carver lv35 = GenerationStep.Carver.valueOf(string2);
            lv28.setCarvingMask(lv35, BitSet.valueOf(lv34.getByteArray(string2)));
         }

         return lv28;
      }
   }

   public static CompoundTag serialize(ServerWorld world, Chunk chunk) {
      ChunkPos lv = chunk.getPos();
      CompoundTag lv2 = new CompoundTag();
      CompoundTag lv3 = new CompoundTag();
      lv2.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      lv2.put("Level", lv3);
      lv3.putInt("xPos", lv.x);
      lv3.putInt("zPos", lv.z);
      lv3.putLong("LastUpdate", world.getTime());
      lv3.putLong("InhabitedTime", chunk.getInhabitedTime());
      lv3.putString("Status", chunk.getStatus().getId());
      UpgradeData lv4 = chunk.getUpgradeData();
      if (!lv4.isDone()) {
         lv3.put("UpgradeData", lv4.toTag());
      }

      ChunkSection[] lvs = chunk.getSectionArray();
      ListTag lv5 = new ListTag();
      LightingProvider lv6 = world.getChunkManager().getLightingProvider();
      boolean bl = chunk.isLightOn();

      for (int i = -1; i < 17; i++) {
         int j = i;
         ChunkSection lv7 = Arrays.stream(lvs)
            .filter(chunkSection -> chunkSection != null && chunkSection.getYOffset() >> 4 == j)
            .findFirst()
            .orElse(WorldChunk.EMPTY_SECTION);
         ChunkNibbleArray lv8 = lv6.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(lv, j));
         ChunkNibbleArray lv9 = lv6.get(LightType.SKY).getLightSection(ChunkSectionPos.from(lv, j));
         if (lv7 != WorldChunk.EMPTY_SECTION || lv8 != null || lv9 != null) {
            CompoundTag lv10 = new CompoundTag();
            lv10.putByte("Y", (byte)(j & 0xFF));
            if (lv7 != WorldChunk.EMPTY_SECTION) {
               lv7.getContainer().write(lv10, "Palette", "BlockStates");
            }

            if (lv8 != null && !lv8.isUninitialized()) {
               lv10.putByteArray("BlockLight", lv8.asByteArray());
            }

            if (lv9 != null && !lv9.isUninitialized()) {
               lv10.putByteArray("SkyLight", lv9.asByteArray());
            }

            lv5.add(lv10);
         }
      }

      lv3.put("Sections", lv5);
      if (bl) {
         lv3.putBoolean("isLightOn", true);
      }

      BiomeArray lv11 = chunk.getBiomeArray();
      if (lv11 != null) {
         lv3.putIntArray("Biomes", lv11.toIntArray());
      }

      ListTag lv12 = new ListTag();

      for (BlockPos lv13 : chunk.getBlockEntityPositions()) {
         CompoundTag lv14 = chunk.getPackedBlockEntityTag(lv13);
         if (lv14 != null) {
            lv12.add(lv14);
         }
      }

      lv3.put("TileEntities", lv12);
      ListTag lv15 = new ListTag();
      if (chunk.getStatus().getChunkType() == ChunkStatus.ChunkType.field_12807) {
         WorldChunk lv16 = (WorldChunk)chunk;
         lv16.setUnsaved(false);

         for (int k = 0; k < lv16.getEntitySectionArray().length; k++) {
            for (Entity lv17 : lv16.getEntitySectionArray()[k]) {
               CompoundTag lv18 = new CompoundTag();
               if (lv17.saveToTag(lv18)) {
                  lv16.setUnsaved(true);
                  lv15.add(lv18);
               }
            }
         }
      } else {
         ProtoChunk lv19 = (ProtoChunk)chunk;
         lv15.addAll(lv19.getEntities());
         lv3.put("Lights", toNbt(lv19.getLightSourcesBySection()));
         CompoundTag lv20 = new CompoundTag();

         for (GenerationStep.Carver lv21 : GenerationStep.Carver.values()) {
            BitSet bitSet = lv19.getCarvingMask(lv21);
            if (bitSet != null) {
               lv20.putByteArray(lv21.toString(), bitSet.toByteArray());
            }
         }

         lv3.put("CarvingMasks", lv20);
      }

      lv3.put("Entities", lv15);
      TickScheduler<Block> lv22 = chunk.getBlockTickScheduler();
      if (lv22 instanceof ChunkTickScheduler) {
         lv3.put("ToBeTicked", ((ChunkTickScheduler)lv22).toNbt());
      } else if (lv22 instanceof SimpleTickScheduler) {
         lv3.put("TileTicks", ((SimpleTickScheduler)lv22).toNbt());
      } else {
         lv3.put("TileTicks", world.getBlockTickScheduler().toTag(lv));
      }

      TickScheduler<Fluid> lv23 = chunk.getFluidTickScheduler();
      if (lv23 instanceof ChunkTickScheduler) {
         lv3.put("LiquidsToBeTicked", ((ChunkTickScheduler)lv23).toNbt());
      } else if (lv23 instanceof SimpleTickScheduler) {
         lv3.put("LiquidTicks", ((SimpleTickScheduler)lv23).toNbt());
      } else {
         lv3.put("LiquidTicks", world.getFluidTickScheduler().toTag(lv));
      }

      lv3.put("PostProcessing", toNbt(chunk.getPostProcessingLists()));
      CompoundTag lv24 = new CompoundTag();

      for (Entry<Heightmap.Type, Heightmap> entry : chunk.getHeightmaps()) {
         if (chunk.getStatus().getHeightmapTypes().contains(entry.getKey())) {
            lv24.put(entry.getKey().getName(), new LongArrayTag(entry.getValue().asLongArray()));
         }
      }

      lv3.put("Heightmaps", lv24);
      lv3.put("Structures", writeStructures(lv, chunk.getStructureStarts(), chunk.getStructureReferences()));
      return lv2;
   }

   public static ChunkStatus.ChunkType getChunkType(@Nullable CompoundTag tag) {
      if (tag != null) {
         ChunkStatus lv = ChunkStatus.byId(tag.getCompound("Level").getString("Status"));
         if (lv != null) {
            return lv.getChunkType();
         }
      }

      return ChunkStatus.ChunkType.field_12808;
   }

   private static void writeEntities(CompoundTag tag, WorldChunk chunk) {
      ListTag lv = tag.getList("Entities", 10);
      World lv2 = chunk.getWorld();

      for (int i = 0; i < lv.size(); i++) {
         CompoundTag lv3 = lv.getCompound(i);
         EntityType.loadEntityWithPassengers(lv3, lv2, entity -> {
            chunk.addEntity(entity);
            return entity;
         });
         chunk.setUnsaved(true);
      }

      ListTag lv4 = tag.getList("TileEntities", 10);

      for (int j = 0; j < lv4.size(); j++) {
         CompoundTag lv5 = lv4.getCompound(j);
         boolean bl = lv5.getBoolean("keepPacked");
         if (bl) {
            chunk.addPendingBlockEntityTag(lv5);
         } else {
            BlockPos lv6 = new BlockPos(lv5.getInt("x"), lv5.getInt("y"), lv5.getInt("z"));
            BlockEntity lv7 = BlockEntity.createFromTag(chunk.getBlockState(lv6), lv5);
            if (lv7 != null) {
               chunk.addBlockEntity(lv7);
            }
         }
      }
   }

   private static CompoundTag writeStructures(
      ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences
   ) {
      CompoundTag lv = new CompoundTag();
      CompoundTag lv2 = new CompoundTag();

      for (Entry<StructureFeature<?>, StructureStart<?>> entry : structureStarts.entrySet()) {
         lv2.put(entry.getKey().getName(), entry.getValue().toTag(pos.x, pos.z));
      }

      lv.put("Starts", lv2);
      CompoundTag lv3 = new CompoundTag();

      for (Entry<StructureFeature<?>, LongSet> entry2 : structureReferences.entrySet()) {
         lv3.put(entry2.getKey().getName(), new LongArrayTag(entry2.getValue()));
      }

      lv.put("References", lv3);
      return lv;
   }

   private static Map<StructureFeature<?>, StructureStart<?>> readStructureStarts(StructureManager arg, CompoundTag tag, long worldSeed) {
      Map<StructureFeature<?>, StructureStart<?>> map = Maps.newHashMap();
      CompoundTag lv = tag.getCompound("Starts");

      for (String string : lv.getKeys()) {
         String string2 = string.toLowerCase(Locale.ROOT);
         StructureFeature<?> lv2 = (StructureFeature<?>)StructureFeature.STRUCTURES.get(string2);
         if (lv2 == null) {
            LOGGER.error("Unknown structure start: {}", string2);
         } else {
            StructureStart<?> lv3 = StructureFeature.readStructureStart(arg, lv.getCompound(string), worldSeed);
            if (lv3 != null) {
               map.put(lv2, lv3);
            }
         }
      }

      return map;
   }

   private static Map<StructureFeature<?>, LongSet> readStructureReferences(ChunkPos pos, CompoundTag tag) {
      Map<StructureFeature<?>, LongSet> map = Maps.newHashMap();
      CompoundTag lv = tag.getCompound("References");

      for (String string : lv.getKeys()) {
         map.put(
            (StructureFeature<?>)StructureFeature.STRUCTURES.get(string.toLowerCase(Locale.ROOT)),
            new LongOpenHashSet(Arrays.stream(lv.getLongArray(string)).filter(l -> {
               ChunkPos lvx = new ChunkPos(l);
               if (lvx.method_24022(pos) > 8) {
                  LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", string, lvx, pos);
                  return false;
               } else {
                  return true;
               }
            }).toArray())
         );
      }

      return map;
   }

   public static ListTag toNbt(ShortList[] lists) {
      ListTag lv = new ListTag();

      for (ShortList shortList : lists) {
         ListTag lv2 = new ListTag();
         if (shortList != null) {
            ShortListIterator var7 = shortList.iterator();

            while (var7.hasNext()) {
               Short short_ = (Short)var7.next();
               lv2.add(ShortTag.of(short_));
            }
         }

         lv.add(lv2);
      }

      return lv;
   }
}
