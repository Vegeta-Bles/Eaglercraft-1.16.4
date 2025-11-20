/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.util.math;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class ChunkPos {
    public static final long MARKER = ChunkPos.toLong(1875016, 1875016);
    public final int x;
    public final int z;

    public ChunkPos(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkPos(BlockPos pos) {
        this.x = pos.getX() >> 4;
        this.z = pos.getZ() >> 4;
    }

    public ChunkPos(long pos) {
        this.x = (int)pos;
        this.z = (int)(pos >> 32);
    }

    public long toLong() {
        return ChunkPos.toLong(this.x, this.z);
    }

    public static long toLong(int chunkX, int chunkZ) {
        return (long)chunkX & 0xFFFFFFFFL | ((long)chunkZ & 0xFFFFFFFFL) << 32;
    }

    public static int getPackedX(long pos) {
        return (int)(pos & 0xFFFFFFFFL);
    }

    public static int getPackedZ(long pos) {
        return (int)(pos >>> 32 & 0xFFFFFFFFL);
    }

    public int hashCode() {
        int n = 1664525 * this.x + 1013904223;
        _snowman = 1664525 * (this.z ^ 0xDEADBEEF) + 1013904223;
        return n ^ _snowman;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ChunkPos) {
            ChunkPos chunkPos = (ChunkPos)o;
            return this.x == chunkPos.x && this.z == chunkPos.z;
        }
        return false;
    }

    public int getStartX() {
        return this.x << 4;
    }

    public int getStartZ() {
        return this.z << 4;
    }

    public int getEndX() {
        return (this.x << 4) + 15;
    }

    public int getEndZ() {
        return (this.z << 4) + 15;
    }

    public int getRegionX() {
        return this.x >> 5;
    }

    public int getRegionZ() {
        return this.z >> 5;
    }

    public int getRegionRelativeX() {
        return this.x & 0x1F;
    }

    public int getRegionRelativeZ() {
        return this.z & 0x1F;
    }

    public String toString() {
        return "[" + this.x + ", " + this.z + "]";
    }

    public BlockPos getStartPos() {
        return new BlockPos(this.getStartX(), 0, this.getStartZ());
    }

    public int method_24022(ChunkPos chunkPos) {
        return Math.max(Math.abs(this.x - chunkPos.x), Math.abs(this.z - chunkPos.z));
    }

    public static Stream<ChunkPos> stream(ChunkPos center, int radius) {
        return ChunkPos.stream(new ChunkPos(center.x - radius, center.z - radius), new ChunkPos(center.x + radius, center.z + radius));
    }

    public static Stream<ChunkPos> stream(final ChunkPos pos1, final ChunkPos pos2) {
        int n = Math.abs(pos1.x - pos2.x) + 1;
        _snowman = Math.abs(pos1.z - pos2.z) + 1;
        _snowman = pos1.x < pos2.x ? 1 : -1;
        _snowman = pos1.z < pos2.z ? 1 : -1;
        return StreamSupport.stream(new Spliterators.AbstractSpliterator<ChunkPos>((long)(n * _snowman), 64){
            @Nullable
            private ChunkPos position;

            @Override
            public boolean tryAdvance(Consumer<? super ChunkPos> consumer) {
                if (this.position == null) {
                    this.position = pos1;
                } else {
                    int n = this.position.x;
                    _snowman = this.position.z;
                    if (n == pos2.x) {
                        if (_snowman == pos2.z) {
                            return false;
                        }
                        this.position = new ChunkPos(pos1.x, _snowman + _snowman);
                    } else {
                        this.position = new ChunkPos(n + _snowman, _snowman);
                    }
                }
                consumer.accept(this.position);
                return true;
            }
        }, false);
    }
}

