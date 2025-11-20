/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class MetricsData {
    private final long[] samples = new long[240];
    private int startIndex;
    private int sampleCount;
    private int writeIndex;

    public void pushSample(long time) {
        this.samples[this.writeIndex] = time;
        ++this.writeIndex;
        if (this.writeIndex == 240) {
            this.writeIndex = 0;
        }
        if (this.sampleCount < 240) {
            this.startIndex = 0;
            ++this.sampleCount;
        } else {
            this.startIndex = this.wrapIndex(this.writeIndex + 1);
        }
    }

    public int method_15248(long l, int n, int n2) {
        double d = (double)l / (double)(1000000000L / (long)n2);
        return (int)(d * (double)n);
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public int getCurrentIndex() {
        return this.writeIndex;
    }

    public int wrapIndex(int index) {
        return index % 240;
    }

    public long[] getSamples() {
        return this.samples;
    }
}

