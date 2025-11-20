/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum AddRiversLayer implements MergingLayer,
IdentityCoordinateTransformer
{
    INSTANCE;


    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int n = sampler1.sample(this.transformX(x), this.transformZ(z));
        _snowman = sampler2.sample(this.transformX(x), this.transformZ(z));
        if (BiomeLayers.isOcean(n)) {
            return n;
        }
        if (_snowman == 7) {
            if (n == 12) {
                return 11;
            }
            if (n == 14 || n == 15) {
                return 15;
            }
            return _snowman & 0xFF;
        }
        return n;
    }
}

