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
      ChunkGenerator _snowman = world.getChunkManager().getChunkGenerator();
      BiomeSource _snowmanx = _snowman.getBiomeSource();
      CompoundTag _snowmanxx = tag.getCompound("Level");
      ChunkPos _snowmanxxx = new ChunkPos(_snowmanxx.getInt("xPos"), _snowmanxx.getInt("zPos"));
      if (!Objects.equals(pos, _snowmanxxx)) {
         LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", pos, pos, _snowmanxxx);
      }

      BiomeArray _snowmanxxxx = new BiomeArray(
         world.getRegistryManager().get(Registry.BIOME_KEY), pos, _snowmanx, _snowmanxx.contains("Biomes", 11) ? _snowmanxx.getIntArray("Biomes") : null
      );
      UpgradeData _snowmanxxxxx = _snowmanxx.contains("UpgradeData", 10) ? new UpgradeData(_snowmanxx.getCompound("UpgradeData")) : UpgradeData.NO_UPGRADE_DATA;
      ChunkTickScheduler<Block> _snowmanxxxxxx = new ChunkTickScheduler<>(block -> block == null || block.getDefaultState().isAir(), pos, _snowmanxx.getList("ToBeTicked", 9));
      ChunkTickScheduler<Fluid> _snowmanxxxxxxx = new ChunkTickScheduler<>(fluid -> fluid == null || fluid == Fluids.EMPTY, pos, _snowmanxx.getList("LiquidsToBeTicked", 9));
      boolean _snowmanxxxxxxxx = _snowmanxx.getBoolean("isLightOn");
      ListTag _snowmanxxxxxxxxx = _snowmanxx.getList("Sections", 10);
      int _snowmanxxxxxxxxxx = 16;
      ChunkSection[] _snowmanxxxxxxxxxxx = new ChunkSection[16];
      boolean _snowmanxxxxxxxxxxxx = world.getDimension().hasSkyLight();
      ChunkManager _snowmanxxxxxxxxxxxxx = world.getChunkManager();
      LightingProvider _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getLightingProvider();
      if (_snowmanxxxxxxxx) {
         _snowmanxxxxxxxxxxxxxx.setRetainData(pos, true);
      }

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx.size(); _snowmanxxxxxxxxxxxxxxx++) {
         CompoundTag _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.getCompound(_snowmanxxxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getByte("Y");
         if (_snowmanxxxxxxxxxxxxxxxx.contains("Palette", 9) && _snowmanxxxxxxxxxxxxxxxx.contains("BlockStates", 12)) {
            ChunkSection _snowmanxxxxxxxxxxxxxxxxxx = new ChunkSection(_snowmanxxxxxxxxxxxxxxxxx << 4);
            _snowmanxxxxxxxxxxxxxxxxxx.getContainer().read(_snowmanxxxxxxxxxxxxxxxx.getList("Palette", 10), _snowmanxxxxxxxxxxxxxxxx.getLongArray("BlockStates"));
            _snowmanxxxxxxxxxxxxxxxxxx.calculateCounts();
            if (!_snowmanxxxxxxxxxxxxxxxxxx.isEmpty()) {
               _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxxx;
            }

            poiStorage.initForPalette(pos, _snowmanxxxxxxxxxxxxxxxxxx);
         }

         if (_snowmanxxxxxxxx) {
            if (_snowmanxxxxxxxxxxxxxxxx.contains("BlockLight", 7)) {
               _snowmanxxxxxxxxxxxxxx.enqueueSectionData(
                  LightType.BLOCK, ChunkSectionPos.from(pos, _snowmanxxxxxxxxxxxxxxxxx), new ChunkNibbleArray(_snowmanxxxxxxxxxxxxxxxx.getByteArray("BlockLight")), true
               );
            }

            if (_snowmanxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxx.contains("SkyLight", 7)) {
               _snowmanxxxxxxxxxxxxxx.enqueueSectionData(
                  LightType.SKY, ChunkSectionPos.from(pos, _snowmanxxxxxxxxxxxxxxxxx), new ChunkNibbleArray(_snowmanxxxxxxxxxxxxxxxx.getByteArray("SkyLight")), true
               );
            }
         }
      }

      long _snowmanxxxxxxxxxxxxxxx = _snowmanxx.getLong("InhabitedTime");
      ChunkStatus.ChunkType _snowmanxxxxxxxxxxxxxxxxxx = getChunkType(tag);
      Chunk _snowmanxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxxxxxxx == ChunkStatus.ChunkType.field_12807) {
         TickScheduler<Block> _snowmanxxxxxxxxxxxxxxxxxxxx;
         if (_snowmanxx.contains("TileTicks", 9)) {
            _snowmanxxxxxxxxxxxxxxxxxxxx = SimpleTickScheduler.fromNbt(_snowmanxx.getList("TileTicks", 10), Registry.BLOCK::getId, Registry.BLOCK::get);
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         }

         TickScheduler<Fluid> _snowmanxxxxxxxxxxxxxxxxxxxxx;
         if (_snowmanxx.contains("LiquidTicks", 9)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = SimpleTickScheduler.fromNbt(_snowmanxx.getList("LiquidTicks", 10), Registry.FLUID::getId, Registry.FLUID::get);
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
         }

         _snowmanxxxxxxxxxxxxxxxxxxx = new WorldChunk(
            world.toServerWorld(),
            pos,
            _snowmanxxxx,
            _snowmanxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxxxxxx,
            _snowmanxxxxxxxxxxx,
            chunk -> writeEntities(_snowman, chunk)
         );
      } else {
         ProtoChunk _snowmanxxxxxxxxxxxxxxxxxxxxx = new ProtoChunk(pos, _snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxxxx.setBiomes(_snowmanxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxxxxxxxxx.setInhabitedTime(_snowmanxxxxxxxxxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxxxx.setStatus(ChunkStatus.byId(_snowmanxx.getString("Status")));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxx.getStatus().isAtLeast(ChunkStatus.FEATURES)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx.setLightingProvider(_snowmanxxxxxxxxxxxxxx);
         }

         if (!_snowmanxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxx.getStatus().isAtLeast(ChunkStatus.LIGHT)) {
            for (BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxx : BlockPos.iterate(pos.getStartX(), 0, pos.getStartZ(), pos.getEndX(), 255, pos.getEndZ())) {
               if (_snowmanxxxxxxxxxxxxxxxxxxx.getBlockState(_snowmanxxxxxxxxxxxxxxxxxxxxxx).getLuminance() != 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx.addLightSource(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      _snowmanxxxxxxxxxxxxxxxxxxx.setLightOn(_snowmanxxxxxxxx);
      CompoundTag _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getCompound("Heightmaps");
      EnumSet<Heightmap.Type> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = EnumSet.noneOf(Heightmap.Type.class);

      for (Heightmap.Type _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxx.getStatus().getHeightmapTypes()) {
         String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.getName();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx.contains(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, 12)) {
            _snowmanxxxxxxxxxxxxxxxxxxx.setHeightmap(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx.getLongArray(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      Heightmap.populateHeightmaps(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
      CompoundTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getCompound("Structures");
      _snowmanxxxxxxxxxxxxxxxxxxx.setStructureStarts(readStructureStarts(structureManager, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, world.getSeed()));
      _snowmanxxxxxxxxxxxxxxxxxxx.setStructureReferences(readStructureReferences(pos, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
      if (_snowmanxx.getBoolean("shouldSave")) {
         _snowmanxxxxxxxxxxxxxxxxxxx.setShouldSave(true);
      }

      ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getList("PostProcessing", 9);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.size(); _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
         ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.getList(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            _snowmanxxxxxxxxxxxxxxxxxxx.markBlockForPostProcessing(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getShort(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxxxx == ChunkStatus.ChunkType.field_12807) {
         return new ReadOnlyChunk((WorldChunk)_snowmanxxxxxxxxxxxxxxxxxxx);
      } else {
         ProtoChunk _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (ProtoChunk)_snowmanxxxxxxxxxxxxxxxxxxx;
         ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getList("Entities", 10);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.addEntity(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCompound(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
         }

         ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getList("TileEntities", 10);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            CompoundTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getCompound(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxxxxxx.addPendingBlockEntityTag(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         }

         ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getList("Lights", 9);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            ListTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getList(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.addLightSource(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getShort(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               );
            }
         }

         CompoundTag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.getCompound("CarvingMasks");

         for (String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getKeys()) {
            GenerationStep.Carver _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = GenerationStep.Carver.valueOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.setCarvingMask(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, BitSet.valueOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getByteArray(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx))
            );
         }

         return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      }
   }

   public static CompoundTag serialize(ServerWorld world, Chunk chunk) {
      ChunkPos _snowman = chunk.getPos();
      CompoundTag _snowmanx = new CompoundTag();
      CompoundTag _snowmanxx = new CompoundTag();
      _snowmanx.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      _snowmanx.put("Level", _snowmanxx);
      _snowmanxx.putInt("xPos", _snowman.x);
      _snowmanxx.putInt("zPos", _snowman.z);
      _snowmanxx.putLong("LastUpdate", world.getTime());
      _snowmanxx.putLong("InhabitedTime", chunk.getInhabitedTime());
      _snowmanxx.putString("Status", chunk.getStatus().getId());
      UpgradeData _snowmanxxx = chunk.getUpgradeData();
      if (!_snowmanxxx.isDone()) {
         _snowmanxx.put("UpgradeData", _snowmanxxx.toTag());
      }

      ChunkSection[] _snowmanxxxx = chunk.getSectionArray();
      ListTag _snowmanxxxxx = new ListTag();
      LightingProvider _snowmanxxxxxx = world.getChunkManager().getLightingProvider();
      boolean _snowmanxxxxxxx = chunk.isLightOn();

      for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx < 17; _snowmanxxxxxxxx++) {
         int _snowmanxxxxxxxxx = _snowmanxxxxxxxx;
         ChunkSection _snowmanxxxxxxxxxx = Arrays.stream(_snowmanxxxx)
            .filter(chunkSection -> chunkSection != null && chunkSection.getYOffset() >> 4 == _snowman)
            .findFirst()
            .orElse(WorldChunk.EMPTY_SECTION);
         ChunkNibbleArray _snowmanxxxxxxxxxxx = _snowmanxxxxxx.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(_snowman, _snowmanxxxxxxxxx));
         ChunkNibbleArray _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.get(LightType.SKY).getLightSection(ChunkSectionPos.from(_snowman, _snowmanxxxxxxxxx));
         if (_snowmanxxxxxxxxxx != WorldChunk.EMPTY_SECTION || _snowmanxxxxxxxxxxx != null || _snowmanxxxxxxxxxxxx != null) {
            CompoundTag _snowmanxxxxxxxxxxxxx = new CompoundTag();
            _snowmanxxxxxxxxxxxxx.putByte("Y", (byte)(_snowmanxxxxxxxxx & 0xFF));
            if (_snowmanxxxxxxxxxx != WorldChunk.EMPTY_SECTION) {
               _snowmanxxxxxxxxxx.getContainer().write(_snowmanxxxxxxxxxxxxx, "Palette", "BlockStates");
            }

            if (_snowmanxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxx.isUninitialized()) {
               _snowmanxxxxxxxxxxxxx.putByteArray("BlockLight", _snowmanxxxxxxxxxxx.asByteArray());
            }

            if (_snowmanxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxx.isUninitialized()) {
               _snowmanxxxxxxxxxxxxx.putByteArray("SkyLight", _snowmanxxxxxxxxxxxx.asByteArray());
            }

            _snowmanxxxxx.add(_snowmanxxxxxxxxxxxxx);
         }
      }

      _snowmanxx.put("Sections", _snowmanxxxxx);
      if (_snowmanxxxxxxx) {
         _snowmanxx.putBoolean("isLightOn", true);
      }

      BiomeArray _snowmanxxxxxxxxx = chunk.getBiomeArray();
      if (_snowmanxxxxxxxxx != null) {
         _snowmanxx.putIntArray("Biomes", _snowmanxxxxxxxxx.toIntArray());
      }

      ListTag _snowmanxxxxxxxxxx = new ListTag();

      for (BlockPos _snowmanxxxxxxxxxxx : chunk.getBlockEntityPositions()) {
         CompoundTag _snowmanxxxxxxxxxxxx = chunk.getPackedBlockEntityTag(_snowmanxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxxx.add(_snowmanxxxxxxxxxxxx);
         }
      }

      _snowmanxx.put("TileEntities", _snowmanxxxxxxxxxx);
      ListTag _snowmanxxxxxxxxxxxx = new ListTag();
      if (chunk.getStatus().getChunkType() == ChunkStatus.ChunkType.field_12807) {
         WorldChunk _snowmanxxxxxxxxxxxxxx = (WorldChunk)chunk;
         _snowmanxxxxxxxxxxxxxx.setUnsaved(false);

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.getEntitySectionArray().length; _snowmanxxxxxxxxxxxxxxx++) {
            for (Entity _snowmanxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx.getEntitySectionArray()[_snowmanxxxxxxxxxxxxxxx]) {
               CompoundTag _snowmanxxxxxxxxxxxxxxxxx = new CompoundTag();
               if (_snowmanxxxxxxxxxxxxxxxx.saveToTag(_snowmanxxxxxxxxxxxxxxxxx)) {
                  _snowmanxxxxxxxxxxxxxx.setUnsaved(true);
                  _snowmanxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxx);
               }
            }
         }
      } else {
         ProtoChunk _snowmanxxxxxxxxxxxxxx = (ProtoChunk)chunk;
         _snowmanxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxx.getEntities());
         _snowmanxx.put("Lights", toNbt(_snowmanxxxxxxxxxxxxxx.getLightSourcesBySection()));
         CompoundTag _snowmanxxxxxxxxxxxxxxx = new CompoundTag();

         for (GenerationStep.Carver _snowmanxxxxxxxxxxxxxxxxx : GenerationStep.Carver.values()) {
            BitSet _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.getCarvingMask(_snowmanxxxxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxxxxx != null) {
               _snowmanxxxxxxxxxxxxxxx.putByteArray(_snowmanxxxxxxxxxxxxxxxxx.toString(), _snowmanxxxxxxxxxxxxxxxxxx.toByteArray());
            }
         }

         _snowmanxx.put("CarvingMasks", _snowmanxxxxxxxxxxxxxxx);
      }

      _snowmanxx.put("Entities", _snowmanxxxxxxxxxxxx);
      TickScheduler<Block> _snowmanxxxxxxxxxxxxxx = chunk.getBlockTickScheduler();
      if (_snowmanxxxxxxxxxxxxxx instanceof ChunkTickScheduler) {
         _snowmanxx.put("ToBeTicked", ((ChunkTickScheduler)_snowmanxxxxxxxxxxxxxx).toNbt());
      } else if (_snowmanxxxxxxxxxxxxxx instanceof SimpleTickScheduler) {
         _snowmanxx.put("TileTicks", ((SimpleTickScheduler)_snowmanxxxxxxxxxxxxxx).toNbt());
      } else {
         _snowmanxx.put("TileTicks", world.getBlockTickScheduler().toTag(_snowman));
      }

      TickScheduler<Fluid> _snowmanxxxxxxxxxxxxxxx = chunk.getFluidTickScheduler();
      if (_snowmanxxxxxxxxxxxxxxx instanceof ChunkTickScheduler) {
         _snowmanxx.put("LiquidsToBeTicked", ((ChunkTickScheduler)_snowmanxxxxxxxxxxxxxxx).toNbt());
      } else if (_snowmanxxxxxxxxxxxxxxx instanceof SimpleTickScheduler) {
         _snowmanxx.put("LiquidTicks", ((SimpleTickScheduler)_snowmanxxxxxxxxxxxxxxx).toNbt());
      } else {
         _snowmanxx.put("LiquidTicks", world.getFluidTickScheduler().toTag(_snowman));
      }

      _snowmanxx.put("PostProcessing", toNbt(chunk.getPostProcessingLists()));
      CompoundTag _snowmanxxxxxxxxxxxxxxxxxx = new CompoundTag();

      for (Entry<Heightmap.Type, Heightmap> _snowmanxxxxxxxxxxxxxxxxxxx : chunk.getHeightmaps()) {
         if (chunk.getStatus().getHeightmapTypes().contains(_snowmanxxxxxxxxxxxxxxxxxxx.getKey())) {
            _snowmanxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxxxx.getKey().getName(), new LongArrayTag(_snowmanxxxxxxxxxxxxxxxxxxx.getValue().asLongArray()));
         }
      }

      _snowmanxx.put("Heightmaps", _snowmanxxxxxxxxxxxxxxxxxx);
      _snowmanxx.put("Structures", writeStructures(_snowman, chunk.getStructureStarts(), chunk.getStructureReferences()));
      return _snowmanx;
   }

   public static ChunkStatus.ChunkType getChunkType(@Nullable CompoundTag tag) {
      if (tag != null) {
         ChunkStatus _snowman = ChunkStatus.byId(tag.getCompound("Level").getString("Status"));
         if (_snowman != null) {
            return _snowman.getChunkType();
         }
      }

      return ChunkStatus.ChunkType.field_12808;
   }

   private static void writeEntities(CompoundTag tag, WorldChunk chunk) {
      ListTag _snowman = tag.getList("Entities", 10);
      World _snowmanx = chunk.getWorld();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         CompoundTag _snowmanxxx = _snowman.getCompound(_snowmanxx);
         EntityType.loadEntityWithPassengers(_snowmanxxx, _snowmanx, entity -> {
            chunk.addEntity(entity);
            return entity;
         });
         chunk.setUnsaved(true);
      }

      ListTag _snowmanxx = tag.getList("TileEntities", 10);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
         CompoundTag _snowmanxxxx = _snowmanxx.getCompound(_snowmanxxx);
         boolean _snowmanxxxxx = _snowmanxxxx.getBoolean("keepPacked");
         if (_snowmanxxxxx) {
            chunk.addPendingBlockEntityTag(_snowmanxxxx);
         } else {
            BlockPos _snowmanxxxxxx = new BlockPos(_snowmanxxxx.getInt("x"), _snowmanxxxx.getInt("y"), _snowmanxxxx.getInt("z"));
            BlockEntity _snowmanxxxxxxx = BlockEntity.createFromTag(chunk.getBlockState(_snowmanxxxxxx), _snowmanxxxx);
            if (_snowmanxxxxxxx != null) {
               chunk.addBlockEntity(_snowmanxxxxxxx);
            }
         }
      }
   }

   private static CompoundTag writeStructures(
      ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences
   ) {
      CompoundTag _snowman = new CompoundTag();
      CompoundTag _snowmanx = new CompoundTag();

      for (Entry<StructureFeature<?>, StructureStart<?>> _snowmanxx : structureStarts.entrySet()) {
         _snowmanx.put(_snowmanxx.getKey().getName(), _snowmanxx.getValue().toTag(pos.x, pos.z));
      }

      _snowman.put("Starts", _snowmanx);
      CompoundTag _snowmanxx = new CompoundTag();

      for (Entry<StructureFeature<?>, LongSet> _snowmanxxx : structureReferences.entrySet()) {
         _snowmanxx.put(_snowmanxxx.getKey().getName(), new LongArrayTag(_snowmanxxx.getValue()));
      }

      _snowman.put("References", _snowmanxx);
      return _snowman;
   }

   private static Map<StructureFeature<?>, StructureStart<?>> readStructureStarts(StructureManager _snowman, CompoundTag tag, long worldSeed) {
      Map<StructureFeature<?>, StructureStart<?>> _snowmanx = Maps.newHashMap();
      CompoundTag _snowmanxx = tag.getCompound("Starts");

      for (String _snowmanxxx : _snowmanxx.getKeys()) {
         String _snowmanxxxx = _snowmanxxx.toLowerCase(Locale.ROOT);
         StructureFeature<?> _snowmanxxxxx = (StructureFeature<?>)StructureFeature.STRUCTURES.get(_snowmanxxxx);
         if (_snowmanxxxxx == null) {
            LOGGER.error("Unknown structure start: {}", _snowmanxxxx);
         } else {
            StructureStart<?> _snowmanxxxxxx = StructureFeature.readStructureStart(_snowman, _snowmanxx.getCompound(_snowmanxxx), worldSeed);
            if (_snowmanxxxxxx != null) {
               _snowmanx.put(_snowmanxxxxx, _snowmanxxxxxx);
            }
         }
      }

      return _snowmanx;
   }

   private static Map<StructureFeature<?>, LongSet> readStructureReferences(ChunkPos pos, CompoundTag tag) {
      Map<StructureFeature<?>, LongSet> _snowman = Maps.newHashMap();
      CompoundTag _snowmanx = tag.getCompound("References");

      for (String _snowmanxx : _snowmanx.getKeys()) {
         _snowman.put(
            (StructureFeature<?>)StructureFeature.STRUCTURES.get(_snowmanxx.toLowerCase(Locale.ROOT)),
            new LongOpenHashSet(Arrays.stream(_snowmanx.getLongArray(_snowmanxx)).filter(_snowmanxxx -> {
               ChunkPos _snowmanxxxx = new ChunkPos(_snowmanxxx);
               if (_snowmanxxxx.method_24022(pos) > 8) {
                  LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", _snowman, _snowmanxxxx, pos);
                  return false;
               } else {
                  return true;
               }
            }).toArray())
         );
      }

      return _snowman;
   }

   public static ListTag toNbt(ShortList[] lists) {
      ListTag _snowman = new ListTag();

      for (ShortList _snowmanx : lists) {
         ListTag _snowmanxx = new ListTag();
         if (_snowmanx != null) {
            ShortListIterator var7 = _snowmanx.iterator();

            while (var7.hasNext()) {
               Short _snowmanxxx = (Short)var7.next();
               _snowmanxx.add(ShortTag.of(_snowmanxxx));
            }
         }

         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }
}
