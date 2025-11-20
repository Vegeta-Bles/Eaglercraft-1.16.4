/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer.type;

import net.minecraft.world.biome.layer.util.CoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerFactory;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public interface ParentedLayer
extends CoordinateTransformer {
    default public <R extends LayerSampler> LayerFactory<R> create(LayerSampleContext<R> context, LayerFactory<R> parent) {
        return () -> {
            Object a = parent.make();
            return context.createSampler((n, n2) -> {
                context.initSeed(n, n2);
                return this.sample(context, (LayerSampler)a, n, n2);
            }, a);
        };
    }

    public int sample(LayerSampleContext<?> var1, LayerSampler var2, int var3, int var4);
}

