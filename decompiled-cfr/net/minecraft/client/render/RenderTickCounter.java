/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

public class RenderTickCounter {
    public float tickDelta;
    public float lastFrameDuration;
    private long prevTimeMillis;
    private final float tickTime;

    public RenderTickCounter(float tps, long timeMillis) {
        this.tickTime = 1000.0f / tps;
        this.prevTimeMillis = timeMillis;
    }

    public int beginRenderTick(long timeMillis) {
        this.lastFrameDuration = (float)(timeMillis - this.prevTimeMillis) / this.tickTime;
        this.prevTimeMillis = timeMillis;
        this.tickDelta += this.lastFrameDuration;
        int n = (int)this.tickDelta;
        this.tickDelta -= (float)n;
        return n;
    }
}

