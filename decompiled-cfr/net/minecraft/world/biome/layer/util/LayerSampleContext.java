/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer.util;

import net.minecraft.world.biome.layer.util.LayerOperator;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface LayerSampleContext<R extends LayerSampler>
extends LayerRandomnessSource {
    public void initSeed(long var1, long var3);

    public R createSampler(LayerOperator var1);

    default public R createSampler(LayerOperator operator, R parent) {
        return this.createSampler(operator);
    }

    default public R createSampler(LayerOperator operator, R r, R r2) {
        return this.createSampler(operator);
    }

    default public int choose(int a, int b) {
        return this.nextInt(2) == 0 ? a : b;
    }

    default public int choose(int a, int b, int c, int d) {
        int n = this.nextInt(4);
        if (n == 0) {
            return a;
        }
        if (n == 1) {
            return b;
        }
        if (n == 2) {
            return c;
        }
        return d;
    }
}

