/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 */
package net.minecraft.client.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.IntSupplier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class BiomeColorCache {
    private final ThreadLocal<Last> last = ThreadLocal.withInitial(() -> new Last());
    private final Long2ObjectLinkedOpenHashMap<int[]> colors = new Long2ObjectLinkedOpenHashMap(256, 0.25f);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public int getBiomeColor(BlockPos pos, IntSupplier colorFactory) {
        int n = pos.getX() >> 4;
        _snowman = pos.getZ() >> 4;
        Last _snowman2 = this.last.get();
        if (_snowman2.x != n || _snowman2.z != _snowman) {
            _snowman2.x = n;
            _snowman2.z = _snowman;
            _snowman2.colors = this.getColorArray(n, _snowman);
        }
        _snowman = pos.getX() & 0xF;
        _snowman = pos.getZ() & 0xF;
        _snowman = _snowman << 4 | _snowman;
        _snowman = _snowman2.colors[_snowman];
        if (_snowman != -1) {
            return _snowman;
        }
        _snowman2.colors[_snowman] = _snowman = colorFactory.getAsInt();
        return _snowman;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset(int chunkX, int chunkZ) {
        try {
            this.lock.writeLock().lock();
            for (int i = -1; i <= 1; ++i) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    long l = ChunkPos.toLong(chunkX + i, chunkZ + _snowman);
                    this.colors.remove(l);
                }
            }
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    public void reset() {
        try {
            this.lock.writeLock().lock();
            this.colors.clear();
        }
        finally {
            this.lock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int[] getColorArray(int chunkX, int chunkZ) {
        int[] nArray;
        long l = ChunkPos.toLong(chunkX, chunkZ);
        this.lock.readLock().lock();
        try {
            nArray = (int[])this.colors.get(l);
        }
        finally {
            this.lock.readLock().unlock();
        }
        if (nArray != null) {
            return nArray;
        }
        _snowman = new int[256];
        Arrays.fill(_snowman, -1);
        try {
            this.lock.writeLock().lock();
            if (this.colors.size() >= 256) {
                this.colors.removeFirst();
            }
            this.colors.put(l, (Object)_snowman);
        }
        finally {
            this.lock.writeLock().unlock();
        }
        return _snowman;
    }

    static class Last {
        public int x = Integer.MIN_VALUE;
        public int z = Integer.MIN_VALUE;
        public int[] colors;

        private Last() {
        }
    }
}

