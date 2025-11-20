/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.world.chunk.light;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.Arrays;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkToNibbleArrayMap;
import net.minecraft.world.chunk.ColumnChunkNibbleArray;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.LightStorage;

public class SkyLightStorage
extends LightStorage<Data> {
    private static final Direction[] LIGHT_REDUCTION_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private final LongSet field_15820 = new LongOpenHashSet();
    private final LongSet sectionsToUpdate = new LongOpenHashSet();
    private final LongSet sectionsToRemove = new LongOpenHashSet();
    private final LongSet enabledColumns = new LongOpenHashSet();
    private volatile boolean hasUpdates;

    protected SkyLightStorage(ChunkProvider chunkProvider) {
        super(LightType.SKY, chunkProvider, new Data((Long2ObjectOpenHashMap<ChunkNibbleArray>)new Long2ObjectOpenHashMap(), new Long2IntOpenHashMap(), Integer.MAX_VALUE));
    }

    @Override
    protected int getLight(long blockPos) {
        long l = ChunkSectionPos.fromBlockPos(blockPos);
        int _snowman2 = ChunkSectionPos.unpackY(l);
        Data _snowman3 = (Data)this.uncachedStorage;
        int _snowman4 = _snowman3.columnToTopSection.get(ChunkSectionPos.withZeroY(l));
        if (_snowman4 == _snowman3.minSectionY || _snowman2 >= _snowman4) {
            return 15;
        }
        ChunkNibbleArray _snowman5 = this.getLightSection(_snowman3, l);
        if (_snowman5 == null) {
            blockPos = BlockPos.removeChunkSectionLocalY(blockPos);
            while (_snowman5 == null) {
                l = ChunkSectionPos.offset(l, Direction.UP);
                if (++_snowman2 >= _snowman4) {
                    return 15;
                }
                blockPos = BlockPos.add(blockPos, 0, 16, 0);
                _snowman5 = this.getLightSection(_snowman3, l);
            }
        }
        return _snowman5.get(ChunkSectionPos.getLocalCoord(BlockPos.unpackLongX(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongY(blockPos)), ChunkSectionPos.getLocalCoord(BlockPos.unpackLongZ(blockPos)));
    }

    @Override
    protected void onLoadSection(long sectionPos) {
        int n = ChunkSectionPos.unpackY(sectionPos);
        if (((Data)this.storage).minSectionY > n) {
            ((Data)this.storage).minSectionY = n;
            ((Data)this.storage).columnToTopSection.defaultReturnValue(((Data)this.storage).minSectionY);
        }
        long _snowman2 = ChunkSectionPos.withZeroY(sectionPos);
        _snowman = ((Data)this.storage).columnToTopSection.get(_snowman2);
        if (_snowman < n + 1) {
            ((Data)this.storage).columnToTopSection.put(_snowman2, n + 1);
            if (this.enabledColumns.contains(_snowman2)) {
                this.enqueueAddSection(sectionPos);
                if (_snowman > ((Data)this.storage).minSectionY) {
                    long l = ChunkSectionPos.asLong(ChunkSectionPos.unpackX(sectionPos), _snowman - 1, ChunkSectionPos.unpackZ(sectionPos));
                    this.enqueueRemoveSection(l);
                }
                this.checkForUpdates();
            }
        }
    }

    private void enqueueRemoveSection(long sectionPos) {
        this.sectionsToRemove.add(sectionPos);
        this.sectionsToUpdate.remove(sectionPos);
    }

    private void enqueueAddSection(long sectionPos) {
        this.sectionsToUpdate.add(sectionPos);
        this.sectionsToRemove.remove(sectionPos);
    }

    private void checkForUpdates() {
        this.hasUpdates = !this.sectionsToUpdate.isEmpty() || !this.sectionsToRemove.isEmpty();
    }

    @Override
    protected void onUnloadSection(long sectionPos) {
        long l = ChunkSectionPos.withZeroY(sectionPos);
        boolean _snowman2 = this.enabledColumns.contains(l);
        if (_snowman2) {
            this.enqueueRemoveSection(sectionPos);
        }
        int _snowman3 = ChunkSectionPos.unpackY(sectionPos);
        if (((Data)this.storage).columnToTopSection.get(l) == _snowman3 + 1) {
            _snowman = sectionPos;
            while (!this.hasSection(_snowman) && this.isAboveMinHeight(_snowman3)) {
                --_snowman3;
                _snowman = ChunkSectionPos.offset(_snowman, Direction.DOWN);
            }
            if (this.hasSection(_snowman)) {
                ((Data)this.storage).columnToTopSection.put(l, _snowman3 + 1);
                if (_snowman2) {
                    this.enqueueAddSection(_snowman);
                }
            } else {
                ((Data)this.storage).columnToTopSection.remove(l);
            }
        }
        if (_snowman2) {
            this.checkForUpdates();
        }
    }

    @Override
    protected void setColumnEnabled(long columnPos, boolean enabled) {
        this.updateAll();
        if (enabled && this.enabledColumns.add(columnPos)) {
            int n = ((Data)this.storage).columnToTopSection.get(columnPos);
            if (n != ((Data)this.storage).minSectionY) {
                long l = ChunkSectionPos.asLong(ChunkSectionPos.unpackX(columnPos), n - 1, ChunkSectionPos.unpackZ(columnPos));
                this.enqueueAddSection(l);
                this.checkForUpdates();
            }
        } else if (!enabled) {
            this.enabledColumns.remove(columnPos);
        }
    }

    @Override
    protected boolean hasLightUpdates() {
        return super.hasLightUpdates() || this.hasUpdates;
    }

    @Override
    protected ChunkNibbleArray createSection(long sectionPos) {
        ChunkNibbleArray chunkNibbleArray = (ChunkNibbleArray)this.queuedSections.get(sectionPos);
        if (chunkNibbleArray != null) {
            return chunkNibbleArray;
        }
        long _snowman2 = ChunkSectionPos.offset(sectionPos, Direction.UP);
        int _snowman3 = ((Data)this.storage).columnToTopSection.get(ChunkSectionPos.withZeroY(sectionPos));
        if (_snowman3 == ((Data)this.storage).minSectionY || ChunkSectionPos.unpackY(_snowman2) >= _snowman3) {
            return new ChunkNibbleArray();
        }
        while ((_snowman = this.getLightSection(_snowman2, true)) == null) {
            _snowman2 = ChunkSectionPos.offset(_snowman2, Direction.UP);
        }
        return new ChunkNibbleArray(new ColumnChunkNibbleArray(_snowman, 0).asByteArray());
    }

    @Override
    protected void updateLight(ChunkLightProvider<Data, ?> lightProvider, boolean doSkylight, boolean skipEdgeLightPropagation) {
        int n;
        int _snowman2;
        long l;
        LongIterator longIterator;
        super.updateLight(lightProvider, doSkylight, skipEdgeLightPropagation);
        if (!doSkylight) {
            return;
        }
        if (!this.sectionsToUpdate.isEmpty()) {
            longIterator = this.sectionsToUpdate.iterator();
            while (longIterator.hasNext()) {
                l = (Long)longIterator.next();
                _snowman2 = this.getLevel(l);
                if (_snowman2 == 2 || this.sectionsToRemove.contains(l) || !this.field_15820.add(l)) continue;
                if (_snowman2 == 1) {
                    this.removeSection(lightProvider, l);
                    if (this.dirtySections.add(l)) {
                        ((Data)this.storage).replaceWithCopy(l);
                    }
                    Arrays.fill(this.getLightSection(l, true).asByteArray(), (byte)-1);
                    n = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(l));
                    _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(l));
                    _snowman = ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(l));
                    for (Direction direction : LIGHT_REDUCTION_DIRECTIONS) {
                        long l2 = ChunkSectionPos.offset(l, direction);
                        if (!this.sectionsToRemove.contains(l2) && (this.field_15820.contains(l2) || this.sectionsToUpdate.contains(l2)) || !this.hasSection(l2)) continue;
                        for (int i = 0; i < 16; ++i) {
                            for (_snowman = 0; _snowman < 16; ++_snowman) {
                                long _snowman4;
                                long _snowman3;
                                switch (direction) {
                                    case NORTH: {
                                        _snowman3 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman);
                                        _snowman4 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman - 1);
                                        break;
                                    }
                                    case SOUTH: {
                                        _snowman3 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman + 16 - 1);
                                        _snowman4 = BlockPos.asLong(n + i, _snowman + _snowman, _snowman + 16);
                                        break;
                                    }
                                    case WEST: {
                                        _snowman3 = BlockPos.asLong(n, _snowman + i, _snowman + _snowman);
                                        _snowman4 = BlockPos.asLong(n - 1, _snowman + i, _snowman + _snowman);
                                        break;
                                    }
                                    default: {
                                        _snowman3 = BlockPos.asLong(n + 16 - 1, _snowman + i, _snowman + _snowman);
                                        _snowman4 = BlockPos.asLong(n + 16, _snowman + i, _snowman + _snowman);
                                    }
                                }
                                lightProvider.updateLevel(_snowman3, _snowman4, lightProvider.getPropagatedLevel(_snowman3, _snowman4, 0), true);
                            }
                        }
                    }
                    for (_snowman = 0; _snowman < 16; ++_snowman) {
                        for (_snowman = 0; _snowman < 16; ++_snowman) {
                            long l3 = BlockPos.asLong(ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(l)) + _snowman, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(l)), ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(l)) + _snowman);
                            l2 = BlockPos.asLong(ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(l)) + _snowman, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(l)) - 1, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(l)) + _snowman);
                            lightProvider.updateLevel(l3, l2, lightProvider.getPropagatedLevel(l3, l2, 0), true);
                        }
                    }
                    continue;
                }
                for (n = 0; n < 16; ++n) {
                    for (_snowman = 0; _snowman < 16; ++_snowman) {
                        long l4 = BlockPos.asLong(ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(l)) + n, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(l)) + 16 - 1, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(l)) + _snowman);
                        lightProvider.updateLevel(Long.MAX_VALUE, l4, 0, true);
                    }
                }
            }
        }
        this.sectionsToUpdate.clear();
        if (!this.sectionsToRemove.isEmpty()) {
            longIterator = this.sectionsToRemove.iterator();
            while (longIterator.hasNext()) {
                l = (Long)longIterator.next();
                if (!this.field_15820.remove(l) || !this.hasSection(l)) continue;
                for (_snowman2 = 0; _snowman2 < 16; ++_snowman2) {
                    for (n = 0; n < 16; ++n) {
                        long l5 = BlockPos.asLong(ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackX(l)) + _snowman2, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackY(l)) + 16 - 1, ChunkSectionPos.getBlockCoord(ChunkSectionPos.unpackZ(l)) + n);
                        lightProvider.updateLevel(Long.MAX_VALUE, l5, 15, false);
                    }
                }
            }
        }
        this.sectionsToRemove.clear();
        this.hasUpdates = false;
    }

    protected boolean isAboveMinHeight(int sectionY) {
        return sectionY >= ((Data)this.storage).minSectionY;
    }

    protected boolean isTopmostBlock(long blockPos) {
        int n = BlockPos.unpackLongY(blockPos);
        if ((n & 0xF) != 15) {
            return false;
        }
        long _snowman2 = ChunkSectionPos.fromBlockPos(blockPos);
        long _snowman3 = ChunkSectionPos.withZeroY(_snowman2);
        if (!this.enabledColumns.contains(_snowman3)) {
            return false;
        }
        _snowman = ((Data)this.storage).columnToTopSection.get(_snowman3);
        return ChunkSectionPos.getBlockCoord(_snowman) == n + 16;
    }

    protected boolean isAtOrAboveTopmostSection(long sectionPos) {
        long l = ChunkSectionPos.withZeroY(sectionPos);
        int _snowman2 = ((Data)this.storage).columnToTopSection.get(l);
        return _snowman2 == ((Data)this.storage).minSectionY || ChunkSectionPos.unpackY(sectionPos) >= _snowman2;
    }

    protected boolean isSectionEnabled(long sectionPos) {
        long l = ChunkSectionPos.withZeroY(sectionPos);
        return this.enabledColumns.contains(l);
    }

    public static final class Data
    extends ChunkToNibbleArrayMap<Data> {
        private int minSectionY;
        private final Long2IntOpenHashMap columnToTopSection;

        public Data(Long2ObjectOpenHashMap<ChunkNibbleArray> arrays, Long2IntOpenHashMap columnToTopSection, int minSectionY) {
            super(arrays);
            this.columnToTopSection = columnToTopSection;
            columnToTopSection.defaultReturnValue(minSectionY);
            this.minSectionY = minSectionY;
        }

        @Override
        public Data copy() {
            return new Data((Long2ObjectOpenHashMap<ChunkNibbleArray>)this.arrays.clone(), this.columnToTopSection.clone(), this.minSectionY);
        }

        @Override
        public /* synthetic */ ChunkToNibbleArrayMap copy() {
            return this.copy();
        }
    }
}

