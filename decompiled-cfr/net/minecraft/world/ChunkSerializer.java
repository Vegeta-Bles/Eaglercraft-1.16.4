/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.shorts.ShortList
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world;

import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerLightingProvider;
import net.minecraft.server.world.ServerTickScheduler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.world.SimpleTickScheduler;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkTickScheduler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LightType;
import net.minecraft.world.TickScheduler;
import net.minecraft.world.World;
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
        ProtoChunk _snowman19;
        Object object;
        Object object2;
        ChunkGenerator chunkGenerator = world.getChunkManager().getChunkGenerator();
        BiomeSource _snowman2 = chunkGenerator.getBiomeSource();
        CompoundTag _snowman3 = tag.getCompound("Level");
        ChunkPos _snowman4 = new ChunkPos(_snowman3.getInt("xPos"), _snowman3.getInt("zPos"));
        if (!Objects.equals(pos, _snowman4)) {
            LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", (Object)pos, (Object)pos, (Object)_snowman4);
        }
        BiomeArray _snowman5 = new BiomeArray(world.getRegistryManager().get(Registry.BIOME_KEY), pos, _snowman2, _snowman3.contains("Biomes", 11) ? _snowman3.getIntArray("Biomes") : null);
        UpgradeData _snowman6 = _snowman3.contains("UpgradeData", 10) ? new UpgradeData(_snowman3.getCompound("UpgradeData")) : UpgradeData.NO_UPGRADE_DATA;
        ChunkTickScheduler<Block> _snowman7 = new ChunkTickScheduler<Block>(block -> block == null || block.getDefaultState().isAir(), pos, _snowman3.getList("ToBeTicked", 9));
        ChunkTickScheduler<Fluid> _snowman8 = new ChunkTickScheduler<Fluid>(fluid -> fluid == null || fluid == Fluids.EMPTY, pos, _snowman3.getList("LiquidsToBeTicked", 9));
        boolean _snowman9 = _snowman3.getBoolean("isLightOn");
        ListTag _snowman10 = _snowman3.getList("Sections", 10);
        int _snowman11 = 16;
        ChunkSection[] _snowman12 = new ChunkSection[16];
        boolean _snowman13 = world.getDimension().hasSkyLight();
        ServerChunkManager _snowman14 = world.getChunkManager();
        LightingProvider _snowman15 = ((ChunkManager)_snowman14).getLightingProvider();
        if (_snowman9) {
            _snowman15.setRetainData(pos, true);
        }
        for (int i = 0; i < _snowman10.size(); ++i) {
            CompoundTag compoundTag = _snowman10.getCompound(i);
            byte _snowman16 = compoundTag.getByte("Y");
            if (compoundTag.contains("Palette", 9) && compoundTag.contains("BlockStates", 12)) {
                object2 = new ChunkSection(_snowman16 << 4);
                ((ChunkSection)object2).getContainer().read(compoundTag.getList("Palette", 10), compoundTag.getLongArray("BlockStates"));
                ((ChunkSection)object2).calculateCounts();
                if (!((ChunkSection)object2).isEmpty()) {
                    _snowman12[_snowman16] = object2;
                }
                poiStorage.initForPalette(pos, (ChunkSection)object2);
            }
            if (!_snowman9) continue;
            if (compoundTag.contains("BlockLight", 7)) {
                _snowman15.enqueueSectionData(LightType.BLOCK, ChunkSectionPos.from(pos, _snowman16), new ChunkNibbleArray(compoundTag.getByteArray("BlockLight")), true);
            }
            if (!_snowman13 || !compoundTag.contains("SkyLight", 7)) continue;
            _snowman15.enqueueSectionData(LightType.SKY, ChunkSectionPos.from(pos, _snowman16), new ChunkNibbleArray(compoundTag.getByteArray("SkyLight")), true);
        }
        long l = _snowman3.getLong("InhabitedTime");
        ChunkStatus.ChunkType _snowman17 = ChunkSerializer.getChunkType(tag);
        if (_snowman17 == ChunkStatus.ChunkType.field_12807) {
            ChunkTickScheduler<Fluid> _snowman18;
            ChunkTickScheduler<Block> tickScheduler;
            if (_snowman3.contains("TileTicks", 9)) {
                object = SimpleTickScheduler.fromNbt(_snowman3.getList("TileTicks", 10), Registry.BLOCK::getId, Registry.BLOCK::get);
            } else {
                tickScheduler = _snowman7;
            }
            if (_snowman3.contains("LiquidTicks", 9)) {
                Object object3 = SimpleTickScheduler.fromNbt(_snowman3.getList("LiquidTicks", 10), Registry.FLUID::getId, Registry.FLUID::get);
            } else {
                _snowman18 = _snowman8;
            }
            object2 = new WorldChunk(world.toServerWorld(), pos, _snowman5, _snowman6, tickScheduler, _snowman18, l, _snowman12, chunk -> ChunkSerializer.writeEntities(_snowman3, chunk));
        } else {
            object = new ProtoChunk(pos, _snowman6, _snowman12, _snowman7, _snowman8);
            ((ProtoChunk)object).setBiomes(_snowman5);
            _snowman19 = object;
            _snowman19.setInhabitedTime(l);
            ((ProtoChunk)object).setStatus(ChunkStatus.byId(_snowman3.getString("Status")));
            if (_snowman19.getStatus().isAtLeast(ChunkStatus.FEATURES)) {
                ((ProtoChunk)object).setLightingProvider(_snowman15);
            }
            if (!_snowman9 && _snowman19.getStatus().isAtLeast(ChunkStatus.LIGHT)) {
                for (BlockPos blockPos : BlockPos.iterate(pos.getStartX(), 0, pos.getStartZ(), pos.getEndX(), 255, pos.getEndZ())) {
                    if (_snowman19.getBlockState(blockPos).getLuminance() == 0) continue;
                    ((ProtoChunk)object).addLightSource(blockPos);
                }
            }
        }
        _snowman19.setLightOn(_snowman9);
        object = _snowman3.getCompound("Heightmaps");
        object3 = EnumSet.noneOf(Heightmap.Type.class);
        for (Heightmap.Type type : _snowman19.getStatus().getHeightmapTypes()) {
            String string = type.getName();
            if (((CompoundTag)object).contains(string, 12)) {
                _snowman19.setHeightmap(type, ((CompoundTag)object).getLongArray(string));
                continue;
            }
            ((AbstractCollection)object3).add(type);
        }
        Heightmap.populateHeightmaps(_snowman19, (Set<Heightmap.Type>)object3);
        CompoundTag compoundTag = _snowman3.getCompound("Structures");
        _snowman19.setStructureStarts(ChunkSerializer.readStructureStarts(structureManager, compoundTag, world.getSeed()));
        _snowman19.setStructureReferences(ChunkSerializer.readStructureReferences(pos, compoundTag));
        if (_snowman3.getBoolean("shouldSave")) {
            _snowman19.setShouldSave(true);
        }
        ListTag listTag = _snowman3.getList("PostProcessing", 9);
        for (int i = 0; i < listTag.size(); ++i) {
            ListTag _snowman20 = listTag.getList(i);
            for (int n = 0; n < _snowman20.size(); ++n) {
                _snowman19.markBlockForPostProcessing(_snowman20.getShort(n), i);
            }
        }
        if (_snowman17 == ChunkStatus.ChunkType.field_12807) {
            return new ReadOnlyChunk((WorldChunk)((Object)_snowman19));
        }
        ProtoChunk protoChunk = _snowman19;
        ListTag _snowman21 = _snowman3.getList("Entities", 10);
        for (int i = 0; i < _snowman21.size(); ++i) {
            protoChunk.addEntity(_snowman21.getCompound(i));
        }
        ListTag listTag2 = _snowman3.getList("TileEntities", 10);
        for (int i = 0; i < listTag2.size(); ++i) {
            CompoundTag compoundTag2 = listTag2.getCompound(i);
            _snowman19.addPendingBlockEntityTag(compoundTag2);
        }
        ListTag listTag22 = _snowman3.getList("Lights", 9);
        for (int i = 0; i < listTag22.size(); ++i) {
            ListTag listTag3 = listTag22.getList(i);
            for (int j = 0; j < listTag3.size(); ++j) {
                protoChunk.addLightSource(listTag3.getShort(j), i);
            }
        }
        CompoundTag compoundTag3 = _snowman3.getCompound("CarvingMasks");
        for (String string : compoundTag3.getKeys()) {
            GenerationStep.Carver carver = GenerationStep.Carver.valueOf(string);
            protoChunk.setCarvingMask(carver, BitSet.valueOf(compoundTag3.getByteArray(string)));
        }
        return protoChunk;
    }

    public static CompoundTag serialize(ServerWorld world, Chunk chunk) {
        CompoundTag compoundTag;
        BiomeArray biomeArray;
        ChunkPos chunkPos = chunk.getPos();
        CompoundTag _snowman2 = new CompoundTag();
        CompoundTag _snowman3 = new CompoundTag();
        _snowman2.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
        _snowman2.put("Level", _snowman3);
        _snowman3.putInt("xPos", chunkPos.x);
        _snowman3.putInt("zPos", chunkPos.z);
        _snowman3.putLong("LastUpdate", world.getTime());
        _snowman3.putLong("InhabitedTime", chunk.getInhabitedTime());
        _snowman3.putString("Status", chunk.getStatus().getId());
        UpgradeData _snowman4 = chunk.getUpgradeData();
        if (!_snowman4.isDone()) {
            _snowman3.put("UpgradeData", _snowman4.toTag());
        }
        ChunkSection[] _snowman5 = chunk.getSectionArray();
        ListTag _snowman6 = new ListTag();
        ServerLightingProvider _snowman7 = world.getChunkManager().getLightingProvider();
        boolean _snowman8 = chunk.isLightOn();
        for (int i = -1; i < 17; ++i) {
            int n = i;
            ChunkSection object2 = Arrays.stream(_snowman5).filter(chunkSection -> chunkSection != null && chunkSection.getYOffset() >> 4 == n).findFirst().orElse(WorldChunk.EMPTY_SECTION);
            ChunkNibbleArray chunkNibbleArray = _snowman7.get(LightType.BLOCK).getLightSection(ChunkSectionPos.from(chunkPos, n));
            ChunkNibbleArray object4 = _snowman7.get(LightType.SKY).getLightSection(ChunkSectionPos.from(chunkPos, n));
            if (object2 == WorldChunk.EMPTY_SECTION && chunkNibbleArray == null && object4 == null) continue;
            GenerationStep.Carver[] object = new CompoundTag();
            object.putByte("Y", (byte)(n & 0xFF));
            if (object2 != WorldChunk.EMPTY_SECTION) {
                object2.getContainer().write((CompoundTag)object, "Palette", "BlockStates");
            }
            if (chunkNibbleArray != null && !chunkNibbleArray.isUninitialized()) {
                object.putByteArray("BlockLight", chunkNibbleArray.asByteArray());
            }
            if (object4 != null && !object4.isUninitialized()) {
                object.putByteArray("SkyLight", object4.asByteArray());
            }
            _snowman6.add(object);
        }
        _snowman3.put("Sections", _snowman6);
        if (_snowman8) {
            _snowman3.putBoolean("isLightOn", true);
        }
        if ((biomeArray = chunk.getBiomeArray()) != null) {
            _snowman3.putIntArray("Biomes", biomeArray.toIntArray());
        }
        ListTag _snowman9 = new ListTag();
        for (BlockPos blockPos : chunk.getBlockEntityPositions()) {
            compoundTag = chunk.getPackedBlockEntityTag(blockPos);
            if (compoundTag == null) continue;
            _snowman9.add(compoundTag);
        }
        _snowman3.put("TileEntities", _snowman9);
        ListTag listTag = new ListTag();
        if (chunk.getStatus().getChunkType() == ChunkStatus.ChunkType.field_12807) {
            WorldChunk worldChunk = (WorldChunk)chunk;
            worldChunk.setUnsaved(false);
            for (int i = 0; i < worldChunk.getEntitySectionArray().length; ++i) {
                for (Entity entity : worldChunk.getEntitySectionArray()[i]) {
                    if (!entity.saveToTag(_snowman = new CompoundTag())) continue;
                    worldChunk.setUnsaved(true);
                    listTag.add(_snowman);
                }
            }
        } else {
            ProtoChunk protoChunk = (ProtoChunk)chunk;
            listTag.addAll(protoChunk.getEntities());
            _snowman3.put("Lights", ChunkSerializer.toNbt(protoChunk.getLightSourcesBySection()));
            compoundTag = new CompoundTag();
            for (GenerationStep.Carver carver : GenerationStep.Carver.values()) {
                BitSet bitSet = protoChunk.getCarvingMask(carver);
                if (bitSet == null) continue;
                compoundTag.putByteArray(carver.toString(), bitSet.toByteArray());
            }
            _snowman3.put("CarvingMasks", compoundTag);
        }
        _snowman3.put("Entities", listTag);
        TickScheduler<Block> tickScheduler = chunk.getBlockTickScheduler();
        if (tickScheduler instanceof ChunkTickScheduler) {
            _snowman3.put("ToBeTicked", ((ChunkTickScheduler)tickScheduler).toNbt());
        } else if (tickScheduler instanceof SimpleTickScheduler) {
            _snowman3.put("TileTicks", ((SimpleTickScheduler)tickScheduler).toNbt());
        } else {
            _snowman3.put("TileTicks", ((ServerTickScheduler)world.getBlockTickScheduler()).toTag(chunkPos));
        }
        TickScheduler<Fluid> _snowman10 = chunk.getFluidTickScheduler();
        if (_snowman10 instanceof ChunkTickScheduler) {
            _snowman3.put("LiquidsToBeTicked", ((ChunkTickScheduler)_snowman10).toNbt());
        } else if (_snowman10 instanceof SimpleTickScheduler) {
            _snowman3.put("LiquidTicks", ((SimpleTickScheduler)_snowman10).toNbt());
        } else {
            _snowman3.put("LiquidTicks", ((ServerTickScheduler)world.getFluidTickScheduler()).toTag(chunkPos));
        }
        _snowman3.put("PostProcessing", ChunkSerializer.toNbt(chunk.getPostProcessingLists()));
        CompoundTag _snowman11 = new CompoundTag();
        for (Map.Entry<Heightmap.Type, Heightmap> entry : chunk.getHeightmaps()) {
            if (!chunk.getStatus().getHeightmapTypes().contains(entry.getKey())) continue;
            _snowman11.put(entry.getKey().getName(), new LongArrayTag(entry.getValue().asLongArray()));
        }
        _snowman3.put("Heightmaps", _snowman11);
        _snowman3.put("Structures", ChunkSerializer.writeStructures(chunkPos, chunk.getStructureStarts(), chunk.getStructureReferences()));
        return _snowman2;
    }

    public static ChunkStatus.ChunkType getChunkType(@Nullable CompoundTag tag) {
        ChunkStatus chunkStatus;
        if (tag != null && (chunkStatus = ChunkStatus.byId(tag.getCompound("Level").getString("Status"))) != null) {
            return chunkStatus.getChunkType();
        }
        return ChunkStatus.ChunkType.field_12808;
    }

    private static void writeEntities(CompoundTag tag, WorldChunk chunk) {
        ListTag listTag = tag.getList("Entities", 10);
        World _snowman2 = chunk.getWorld();
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            EntityType.loadEntityWithPassengers(compoundTag, _snowman2, entity -> {
                chunk.addEntity((Entity)entity);
                return entity;
            });
            chunk.setUnsaved(true);
        }
        ListTag listTag2 = tag.getList("TileEntities", 10);
        for (int i = 0; i < listTag2.size(); ++i) {
            CompoundTag compoundTag = listTag2.getCompound(i);
            boolean _snowman3 = compoundTag.getBoolean("keepPacked");
            if (_snowman3) {
                chunk.addPendingBlockEntityTag(compoundTag);
                continue;
            }
            BlockPos _snowman4 = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
            BlockEntity _snowman5 = BlockEntity.createFromTag(chunk.getBlockState(_snowman4), compoundTag);
            if (_snowman5 == null) continue;
            chunk.addBlockEntity(_snowman5);
        }
    }

    private static CompoundTag writeStructures(ChunkPos pos, Map<StructureFeature<?>, StructureStart<?>> structureStarts, Map<StructureFeature<?>, LongSet> structureReferences) {
        CompoundTag compoundTag = new CompoundTag();
        _snowman = new CompoundTag();
        for (Map.Entry<StructureFeature<?>, StructureStart<?>> entry : structureStarts.entrySet()) {
            _snowman.put(entry.getKey().getName(), entry.getValue().toTag(pos.x, pos.z));
        }
        compoundTag.put("Starts", _snowman);
        _snowman = new CompoundTag();
        for (Map.Entry<StructureFeature<?>, LongSet> entry : structureReferences.entrySet()) {
            _snowman.put(entry.getKey().getName(), new LongArrayTag(entry.getValue()));
        }
        compoundTag.put("References", _snowman);
        return compoundTag;
    }

    private static Map<StructureFeature<?>, StructureStart<?>> readStructureStarts(StructureManager structureManager, CompoundTag tag, long worldSeed) {
        HashMap hashMap = Maps.newHashMap();
        CompoundTag _snowman2 = tag.getCompound("Starts");
        for (String string : _snowman2.getKeys()) {
            _snowman = string.toLowerCase(Locale.ROOT);
            StructureFeature structureFeature = (StructureFeature)StructureFeature.STRUCTURES.get((Object)_snowman);
            if (structureFeature == null) {
                LOGGER.error("Unknown structure start: {}", (Object)_snowman);
                continue;
            }
            StructureStart<?> _snowman3 = StructureFeature.readStructureStart(structureManager, _snowman2.getCompound(string), worldSeed);
            if (_snowman3 == null) continue;
            hashMap.put(structureFeature, _snowman3);
        }
        return hashMap;
    }

    private static Map<StructureFeature<?>, LongSet> readStructureReferences(ChunkPos pos, CompoundTag tag) {
        HashMap hashMap = Maps.newHashMap();
        CompoundTag _snowman2 = tag.getCompound("References");
        for (String string : _snowman2.getKeys()) {
            hashMap.put(StructureFeature.STRUCTURES.get((Object)string.toLowerCase(Locale.ROOT)), new LongOpenHashSet(Arrays.stream(_snowman2.getLongArray(string)).filter(l -> {
                ChunkPos chunkPos2 = new ChunkPos(l);
                if (chunkPos2.method_24022(pos) > 8) {
                    LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", (Object)string, (Object)chunkPos2, (Object)pos);
                    return false;
                }
                return true;
            }).toArray()));
        }
        return hashMap;
    }

    public static ListTag toNbt(ShortList[] lists) {
        ListTag listTag = new ListTag();
        for (ShortList shortList : lists) {
            ListTag listTag2 = new ListTag();
            if (shortList != null) {
                for (Short s : shortList) {
                    listTag2.add(ShortTag.of(s));
                }
            }
            listTag.add(listTag2);
        }
        return listTag;
    }
}

