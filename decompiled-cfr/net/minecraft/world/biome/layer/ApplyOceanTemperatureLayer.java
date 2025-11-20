/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.IdentityCoordinateTransformer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ApplyOceanTemperatureLayer implements MergingLayer,
IdentityCoordinateTransformer
{
    INSTANCE;


    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int n = sampler1.sample(this.transformX(x), this.transformZ(z));
        _snowman = sampler2.sample(this.transformX(x), this.transformZ(z));
        if (!BiomeLayers.isOcean(n)) {
            return n;
        }
        _snowman = 8;
        _snowman = 4;
        for (_snowman = -8; _snowman <= 8; _snowman += 4) {
            for (_snowman = -8; _snowman <= 8; _snowman += 4) {
                _snowman = sampler1.sample(this.transformX(x + _snowman), this.transformZ(z + _snowman));
                if (BiomeLayers.isOcean(_snowman)) continue;
                if (_snowman == 44) {
                    return 45;
                }
                if (_snowman != 10) continue;
                return 46;
            }
        }
        if (n == 24) {
            if (_snowman == 45) {
                return 48;
            }
            if (_snowman == 0) {
                return 24;
            }
            if (_snowman == 46) {
                return 49;
            }
            if (_snowman == 10) {
                return 50;
            }
        }
        return _snowman;
    }
}

