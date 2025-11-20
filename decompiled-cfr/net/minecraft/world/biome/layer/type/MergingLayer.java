/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface MergingLayer
extends CoordinateTransformer {
    default public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> layer1, LayerFactory<R> layer2) {
        return () -> {
            Object a = layer1.make();
            _snowman = layer2.make();
            return context.createSampler((n, n2) -> {
                context.initSeed(n, n2);
                return this.sample(context, (LayerSampler)a, (LayerSampler)_snowman, n, n2);
            }, a, _snowman);
        };
    }

    public int sample(LayerRandomnessSource var1, LayerSampler var2, LayerSampler var3, int var4, int var5);
}

