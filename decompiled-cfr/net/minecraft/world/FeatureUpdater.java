/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.ChunkUpdateState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class FeatureUpdater {
    private static final Map<String, String> OLD_TO_NEW = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put("Village", "Village");
        hashMap.put("Mineshaft", "Mineshaft");
        hashMap.put("Mansion", "Mansion");
        hashMap.put("Igloo", "Temple");
        hashMap.put("Desert_Pyramid", "Temple");
        hashMap.put("Jungle_Pyramid", "Temple");
        hashMap.put("Swamp_Hut", "Temple");
        hashMap.put("Stronghold", "Stronghold");
        hashMap.put("Monument", "Monument");
        hashMap.put("Fortress", "Fortress");
        hashMap.put("EndCity", "EndCity");
    });
    private static final Map<String, String> ANCIENT_TO_OLD = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put("Iglu", "Igloo");
        hashMap.put("TeDP", "Desert_Pyramid");
        hashMap.put("TeJP", "Jungle_Pyramid");
        hashMap.put("TeSH", "Swamp_Hut");
    });
    private final boolean needsUpdate;
    private final Map<String, Long2ObjectMap<CompoundTag>> featureIdToChunkTag = Maps.newHashMap();
    private final Map<String, ChunkUpdateState> updateStates = Maps.newHashMap();
    private final List<String> field_17658;
    private final List<String> field_17659;

    public FeatureUpdater(@Nullable PersistentStateManager persistentStateManager, List<String> list, List<String> list2) {
        this.field_17658 = list;
        this.field_17659 = list2;
        this.init(persistentStateManager);
        boolean bl = false;
        for (String string : this.field_17659) {
            bl |= this.featureIdToChunkTag.get(string) != null;
        }
        this.needsUpdate = bl;
    }

    public void markResolved(long l) {
        for (String string : this.field_17658) {
            ChunkUpdateState chunkUpdateState = this.updateStates.get(string);
            if (chunkUpdateState == null || !chunkUpdateState.isRemaining(l)) continue;
            chunkUpdateState.markResolved(l);
            chunkUpdateState.markDirty();
        }
    }

    public CompoundTag getUpdatedReferences(CompoundTag compoundTag4) {
        CompoundTag compoundTag2;
        CompoundTag compoundTag3;
        compoundTag3 = compoundTag4.getCompound("Level");
        ChunkPos chunkPos = new ChunkPos(compoundTag3.getInt("xPos"), compoundTag3.getInt("zPos"));
        if (this.needsUpdate(chunkPos.x, chunkPos.z)) {
            CompoundTag compoundTag4 = this.getUpdatedStarts(compoundTag4, chunkPos);
        }
        compoundTag2 = compoundTag3.getCompound("Structures");
        compoundTag5 = compoundTag2.getCompound("References");
        for (String string : this.field_17659) {
            CompoundTag compoundTag5;
            StructureFeature structureFeature = (StructureFeature)StructureFeature.STRUCTURES.get((Object)string.toLowerCase(Locale.ROOT));
            if (compoundTag5.contains(string, 12) || structureFeature == null) continue;
            int _snowman2 = 8;
            LongArrayList _snowman3 = new LongArrayList();
            for (int i = chunkPos.x - 8; i <= chunkPos.x + 8; ++i) {
                for (_snowman = chunkPos.z - 8; _snowman <= chunkPos.z + 8; ++_snowman) {
                    if (!this.needsUpdate(i, _snowman, string)) continue;
                    _snowman3.add(ChunkPos.toLong(i, _snowman));
                }
            }
            compoundTag5.putLongArray(string, (List<Long>)_snowman3);
        }
        compoundTag2.put("References", compoundTag5);
        compoundTag3.put("Structures", compoundTag2);
        compoundTag4.put("Level", compoundTag3);
        return compoundTag4;
    }

    private boolean needsUpdate(int chunkX, int chunkZ, String id) {
        if (!this.needsUpdate) {
            return false;
        }
        return this.featureIdToChunkTag.get(id) != null && this.updateStates.get(OLD_TO_NEW.get(id)).contains(ChunkPos.toLong(chunkX, chunkZ));
    }

    private boolean needsUpdate(int chunkX, int chunkZ) {
        if (!this.needsUpdate) {
            return false;
        }
        for (String string : this.field_17659) {
            if (this.featureIdToChunkTag.get(string) == null || !this.updateStates.get(OLD_TO_NEW.get(string)).isRemaining(ChunkPos.toLong(chunkX, chunkZ))) continue;
            return true;
        }
        return false;
    }

    private CompoundTag getUpdatedStarts(CompoundTag compoundTag, ChunkPos chunkPos) {
        CompoundTag compoundTag2 = compoundTag.getCompound("Level");
        _snowman = compoundTag2.getCompound("Structures");
        _snowman = _snowman.getCompound("Starts");
        for (String string : this.field_17659) {
            Long2ObjectMap<CompoundTag> long2ObjectMap = this.featureIdToChunkTag.get(string);
            if (long2ObjectMap == null) continue;
            long _snowman2 = chunkPos.toLong();
            if (!this.updateStates.get(OLD_TO_NEW.get(string)).isRemaining(_snowman2) || (_snowman = (CompoundTag)long2ObjectMap.get(_snowman2)) == null) continue;
            _snowman.put(string, _snowman);
        }
        _snowman.put("Starts", _snowman);
        compoundTag2.put("Structures", _snowman);
        compoundTag.put("Level", compoundTag2);
        return compoundTag;
    }

    private void init(@Nullable PersistentStateManager persistentStateManager) {
        if (persistentStateManager == null) {
            return;
        }
        for (String string22 : this.field_17658) {
            CompoundTag compoundTag = new CompoundTag();
            try {
                compoundTag = persistentStateManager.readTag(string22, 1493).getCompound("data").getCompound("Features");
                if (compoundTag.isEmpty()) {
                    continue;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            for (String string2 : compoundTag.getKeys()) {
                String string3;
                CompoundTag object = compoundTag.getCompound(string2);
                long l = ChunkPos.toLong(object.getInt("ChunkX"), object.getInt("ChunkZ"));
                ListTag _snowman2 = object.getList("Children", 10);
                if (!_snowman2.isEmpty() && (string3 = ANCIENT_TO_OLD.get(_snowman = _snowman2.getCompound(0).getString("id"))) != null) {
                    object.putString("id", string3);
                }
                String _snowman3 = object.getString("id");
                this.featureIdToChunkTag.computeIfAbsent(_snowman3, string -> new Long2ObjectOpenHashMap()).put(l, (Object)object);
            }
            String string3 = string22 + "_index";
            ChunkUpdateState chunkUpdateState = persistentStateManager.getOrCreate(() -> new ChunkUpdateState(string3), string3);
            if (chunkUpdateState.getAll().isEmpty()) {
                ChunkUpdateState chunkUpdateState2 = new ChunkUpdateState(string3);
                this.updateStates.put(string22, chunkUpdateState2);
                for (String string4 : compoundTag.getKeys()) {
                    CompoundTag compoundTag2 = compoundTag.getCompound(string4);
                    chunkUpdateState2.add(ChunkPos.toLong(compoundTag2.getInt("ChunkX"), compoundTag2.getInt("ChunkZ")));
                }
                chunkUpdateState2.markDirty();
                continue;
            }
            this.updateStates.put(string22, chunkUpdateState);
        }
    }

    public static FeatureUpdater create(RegistryKey<World> registryKey2, @Nullable PersistentStateManager persistentStateManager) {
        RegistryKey<World> registryKey2;
        if (registryKey2 == World.OVERWORLD) {
            return new FeatureUpdater(persistentStateManager, (List<String>)ImmutableList.of((Object)"Monument", (Object)"Stronghold", (Object)"Village", (Object)"Mineshaft", (Object)"Temple", (Object)"Mansion"), (List<String>)ImmutableList.of((Object)"Village", (Object)"Mineshaft", (Object)"Mansion", (Object)"Igloo", (Object)"Desert_Pyramid", (Object)"Jungle_Pyramid", (Object)"Swamp_Hut", (Object)"Stronghold", (Object)"Monument"));
        }
        if (registryKey2 == World.NETHER) {
            ImmutableList immutableList = ImmutableList.of((Object)"Fortress");
            return new FeatureUpdater(persistentStateManager, (List<String>)immutableList, (List<String>)immutableList);
        }
        if (registryKey2 == World.END) {
            ImmutableList immutableList = ImmutableList.of((Object)"EndCity");
            return new FeatureUpdater(persistentStateManager, (List<String>)immutableList, (List<String>)immutableList);
        }
        throw new RuntimeException(String.format("Unknown dimension type : %s", registryKey2));
    }
}

