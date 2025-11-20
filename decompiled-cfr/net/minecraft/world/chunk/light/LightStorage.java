/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  javax.annotation.Nullable
 */
package net.minecraft.world.chunk.light;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.SectionDistanceLevelPropagator;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkToNibbleArrayMap;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public abstract class LightStorage<M extends ChunkToNibbleArrayMap<M>>
extends SectionDistanceLevelPropagator {
    protected static final ChunkNibbleArray EMPTY = new ChunkNibbleArray();
    private static final Direction[] DIRECTIONS = Direction.values();
    private final LightType lightType;
    private final ChunkProvider chunkProvider;
    protected final LongSet readySections = new LongOpenHashSet();
    protected final LongSet markedNotReadySections = new LongOpenHashSet();
    protected final LongSet markedReadySections = new LongOpenHashSet();
    protected volatile M uncachedStorage;
    protected final M storage;
    protected final LongSet dirtySections = new LongOpenHashSet();
    protected final LongSet notifySections = new LongOpenHashSet();
    protected final Long2ObjectMap<ChunkNibbleArray> queuedSections = Long2ObjectMaps.synchronize((Long2ObjectMap)new Long2ObjectOpenHashMap());
    private final LongSet queuedEdgeSections = new LongOpenHashSet();
    private final LongSet columnsToRetain = new LongOpenHashSet();
    private final LongSet sectionsToRemove = new LongOpenHashSet();
    protected volatile boolean hasLightUpdates;

    protected LightStorage(LightType lightType, ChunkProvider chunkProvider, M lightData) {
        super(3, 16, 256);
        this.lightType = lightType;
        this.chunkProvider = chunkProvider;
        this.storage = lightData;
        this.uncachedStorage = ((ChunkToNibbleArrayMap)lightData).copy();
        ((ChunkToNibbleArrayMap)this.uncachedStorage).disableCache();
    }

    protected boolean hasSection(long sectionPos) {
        return this.getLightSection(sectionPos, true) != null;
    }

    @Nullable
    protected ChunkNibbleArray getLightSection(long sectionPos, boolean cached) {
        return this.getLightSection(cached ? this.storage : this.uncachedStorage, sectionPos);
    }

    @Nullable
    protected ChunkNibbleArray getLightSection(M storage, long sectionPos) {
        return ((ChunkToNibbleArrayMap)storage).get(sectionPos);
    }

    @Nullable
    public ChunkNibbleArray getLightSection(long sectionPos) {
        ChunkNibbleArray chunkNibbleArray = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
        if (chunkNibbleArray != null) {
            return chunkNibbleArray;
        }
        return this.getLightSection(sectionPos, false);
    }

    protected abstract int getLight(long var1);

    protected int get(long blockPos) {
        long l = ChunkSectionPos.fromBlockPos(blockPos);
        ChunkNibbleArray _snowman2 = this.getLightSection(l, true);
        return _snowman2.get(ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos)));
    }

    protected void set(long blockPos, int value) {
        long l = ChunkSectionPos.fromBlockPos(blockPos);
        if (this.dirtySections.add(l)) {
            ((ChunkToNibbleArrayMap)this.storage).replaceWithCopy(l);
        }
        ChunkNibbleArray _snowman2 = this.getLightSection(l, true);
        _snowman2.set(ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos)), value);
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(blockPos, _snowman, _snowman, i)));
                }
            }
        }
    }

    @Override
    protected int getLevel(long id) {
        if (id == Long.MAX_VALUE) {
            return 2;
        }
        if (this.readySections.contains(id)) {
            return 0;
        }
        if (!this.sectionsToRemove.contains(id) && ((ChunkToNibbleArrayMap)this.storage).containsKey(id)) {
            return 1;
        }
        return 2;
    }

    @Override
    protected int getInitialLevel(long id) {
        if (this.markedNotReadySections.contains(id)) {
            return 2;
        }
        if (this.readySections.contains(id) || this.markedReadySections.contains(id)) {
            return 0;
        }
        return 2;
    }

    @Override
    protected void setLevel(long id, int level) {
        int n = this.getLevel(id);
        if (n != 0 && level == 0) {
            this.readySections.add(id);
            this.markedReadySections.remove(id);
        }
        if (n == 0 && level != 0) {
            this.readySections.remove(id);
            this.markedNotReadySections.remove(id);
        }
        if (n >= 2 && level != 2) {
            if (this.sectionsToRemove.contains(id)) {
                this.sectionsToRemove.remove(id);
            } else {
                ((ChunkToNibbleArrayMap)this.storage).put(id, this.createSection(id));
                this.dirtySections.add(id);
                this.onLoadSection(id);
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    for (_snowman = -1; _snowman <= 1; ++_snowman) {
                        for (_snowman = -1; _snowman <= 1; ++_snowman) {
                            this.notifySections.add(ChunkSectionPos.fromBlockPos(BlockPos.add(id, _snowman, _snowman, _snowman)));
                        }
                    }
                }
            }
        }
        if (n != 2 && level >= 2) {
            this.sectionsToRemove.add(id);
        }
        this.hasLightUpdates = !this.sectionsToRemove.isEmpty();
    }

    protected ChunkNibbleArray createSection(long sectionPos) {
        ChunkNibbleArray chunkNibbleArray = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
        if (chunkNibbleArray != null) {
            return chunkNibbleArray;
        }
        return new ChunkNibbleArray();
    }

    protected void removeSection(ChunkLightProvider<?, ?> storage, long sectionPos) {
        if (storage.getPendingUpdateCount() < 8192) {
            storage.removePendingUpdateIf(l2 -> ChunkSectionPos.fromBlockPos(l2) == sectionPos);
            return;
        }
        int n = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
        _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
        _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));
        for (_snowman = 0; _snowman < 16; ++_snowman) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    long l = BlockPos.asLong(n + _snowman, _snowman + _snowman, _snowman + _snowman);
                    storage.removePendingUpdate(l);
                }
            }
        }
    }

    protected boolean hasLightUpdates() {
        return this.hasLightUpdates;
    }

    protected void updateLight(ChunkLightProvider<M, ?> lightProvider, boolean doSkylight, boolean skipEdgeLightPropagation) {
        ChunkNibbleArray _snowman3;
        if (!this.hasLightUpdates() && this.queuedSections.isEmpty()) {
            return;
        }
        LongIterator longIterator = this.sectionsToRemove.iterator();
        while (longIterator.hasNext()) {
            long l = (Long)longIterator.next();
            this.removeSection(lightProvider, l);
            ChunkNibbleArray _snowman2 = (ChunkNibbleArray)this.queuedSections.remove(l);
            _snowman3 = ((ChunkToNibbleArrayMap)this.storage).removeChunk(l);
            if (!this.columnsToRetain.contains(ChunkSectionPos.withZeroY(l))) continue;
            if (_snowman2 != null) {
                this.queuedSections.put(l, (Object)_snowman2);
                continue;
            }
            if (_snowman3 == null) continue;
            this.queuedSections.put(l, (Object)_snowman3);
        }
        ((ChunkToNibbleArrayMap)this.storage).clearCache();
        longIterator = this.sectionsToRemove.iterator();
        while (longIterator.hasNext()) {
            l = (Long)longIterator.next();
            this.onUnloadSection(l);
        }
        this.sectionsToRemove.clear();
        this.hasLightUpdates = false;
        for (Long2ObjectMap.Entry _snowman4 : this.queuedSections.long2ObjectEntrySet()) {
            _snowman5 = _snowman4.getLongKey();
            if (!this.hasSection(_snowman5)) continue;
            _snowman3 = (ChunkNibbleArray)_snowman4.getValue();
            if (((ChunkToNibbleArrayMap)this.storage).get(_snowman5) == _snowman3) continue;
            this.removeSection(lightProvider, _snowman5);
            ((ChunkToNibbleArrayMap)this.storage).put(_snowman5, _snowman3);
            this.dirtySections.add(_snowman5);
        }
        ((ChunkToNibbleArrayMap)this.storage).clearCache();
        if (!skipEdgeLightPropagation) {
            longIterator = this.queuedSections.keySet().iterator();
            while (longIterator.hasNext()) {
                _snowman = (Long)longIterator.next();
                this.updateSection(lightProvider, _snowman);
            }
        } else {
            longIterator = this.queuedEdgeSections.iterator();
            while (longIterator.hasNext()) {
                _snowman = (Long)longIterator.next();
                this.updateSection(lightProvider, _snowman);
            }
        }
        this.queuedEdgeSections.clear();
        ObjectIterator objectIterator = this.queuedSections.long2ObjectEntrySet().iterator();
        while (objectIterator.hasNext()) {
            Long2ObjectMap.Entry entry = (Long2ObjectMap.Entry)objectIterator.next();
            long _snowman5 = entry.getLongKey();
            if (!this.hasSection(_snowman5)) continue;
            objectIterator.remove();
        }
    }

    private void updateSection(ChunkLightProvider<M, ?> lightProvider, long sectionPos) {
        if (!this.hasSection(sectionPos)) {
            return;
        }
        int n = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(sectionPos));
        _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(sectionPos));
        _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(sectionPos));
        for (Direction direction : DIRECTIONS) {
            long l = ChunkSectionPos.offset(sectionPos, direction);
            if (this.queuedSections.containsKey(l) || !this.hasSection(l)) continue;
            for (int i = 0; i < 16; ++i) {
                for (_snowman = 0; _snowman < 16; ++_snowman) {
                    long _snowman3;
                    long _snowman2;
                    switch (direction) {
                        case DOWN: {
                            _snowman2 = BlockPos.asLong(n + _snowman, _snowman, _snowman + i);
                            _snowman3 = BlockPos.asLong(n + _snowman, _snowman - 1, _snowman + i);
                            break;
                        }
                        case UP: {
                            _snowman2 = BlockPos.asLong(n + _snowman, _snowman + 16 - 1, _snowman + i);
                            _snowman3 = BlockPos.asLong(n + _snowman, _snowman + 16, _snowman + i);
                            break;
                        }
                        case NORTH: {
                            _snowman2 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman);
                            _snowman3 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman - 1);
                            break;
                        }
                        case SOUTH: {
                            _snowman2 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman + 16 - 1);
                            _snowman3 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman + 16);
                            break;
                        }
                        case WEST: {
                            _snowman2 = BlockPos.asLong(n, _snowman + i, _snowman + _snowman);
                            _snowman3 = BlockPos.asLong(n - 1, _snowman + i, _snowman + _snowman);
                            break;
                        }
                        default: {
                            _snowman2 = BlockPos.asLong(n + 16 - 1, _snowman + i, _snowman + _snowman);
                            _snowman3 = BlockPos.asLong(n + 16, _snowman + i, _snowman + _snowman);
                        }
                    }
                    lightProvider.updateLevel(_snowman2, _snowman3, lightProvider.getPropagatedLevel(_snowman2, _snowman3, lightProvider.getLevel(_snowman2)), false);
                    lightProvider.updateLevel(_snowman3, _snowman2, lightProvider.getPropagatedLevel(_snowman3, _snowman2, lightProvider.getLevel(_snowman3)), false);
                }
            }
        }
    }

    protected void onLoadSection(long sectionPos) {
    }

    protected void onUnloadSection(long sectionPos) {
    }

    protected void setColumnEnabled(long columnPos, boolean enabled) {
    }

    public void setRetainColumn(long sectionPos, boolean retain) {
        if (retain) {
            this.columnsToRetain.add(sectionPos);
        } else {
            this.columnsToRetain.remove(sectionPos);
        }
    }

    protected void enqueueSectionData(long sectionPos, @Nullable ChunkNibbleArray array, boolean bl) {
        if (array != null) {
            this.queuedSections.put(sectionPos, (Object)array);
            if (!bl) {
                this.queuedEdgeSections.add(sectionPos);
            }
        } else {
            this.queuedSections.remove(sectionPos);
        }
    }

    protected void setSectionStatus(long sectionPos, boolean notReady) {
        boolean bl = this.readySections.contains(sectionPos);
        if (!bl && !notReady) {
            this.markedReadySections.add(sectionPos);
            this.updateLevel(Long.MAX_VALUE, sectionPos, 0, true);
        }
        if (bl && notReady) {
            this.markedNotReadySections.add(sectionPos);
            this.updateLevel(Long.MAX_VALUE, sectionPos, 2, false);
        }
    }

    protected void updateAll() {
        if (this.hasPendingUpdates()) {
            this.applyPendingUpdates(Integer.MAX_VALUE);
        }
    }

    protected void notifyChanges() {
        Object object;
        if (!this.dirtySections.isEmpty()) {
            object = ((ChunkToNibbleArrayMap)this.storage).copy();
            ((ChunkToNibbleArrayMap)object).disableCache();
            this.uncachedStorage = object;
            this.dirtySections.clear();
        }
        if (!this.notifySections.isEmpty()) {
            object = this.notifySections.iterator();
            while (object.hasNext()) {
                long l = object.nextLong();
                this.chunkProvider.onLightUpdate(this.lightType, ChunkSectionPos.from(l));
            }
            this.notifySections.clear();
        }
    }
}

