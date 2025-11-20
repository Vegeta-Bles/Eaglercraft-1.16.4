/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

public class FpsSmoother {
    private final long[] times;
    private int size;
    private int index;

    public FpsSmoother(int size) {
        this.times = new long[size];
    }

    public long getTargetUsedTime(long time) {
        long l;
        if (this.size < this.times.length) {
            ++this.size;
        }
        this.times[this.index] = time;
        this.index = (this.index + 1) % this.times.length;
        long l2 = Long.MAX_VALUE;
        _snowman = Long.MIN_VALUE;
        l = 0L;
        for (int i = 0; i < this.size; ++i) {
            long l3 = this.times[i];
            l += l3;
            l2 = Math.min(l2, l3);
            _snowman = Math.max(_snowman, l3);
        }
        if (this.size > 2) {
            return (l -= l2 + _snowman) / (long)(this.size - 2);
        }
        if (l > 0L) {
            return (long)this.size / l;
        }
        return 0L;
    }
}

