/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.biome.layer;

import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.biome.layer.type.InitLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

public enum OceanTemperatureLayer implements InitLayer
{
    INSTANCE;


    @Override
    public int sample(LayerRandomnessSource context, int x, int y) {
        PerlinNoiseSampler perlinNoiseSampler = context.getNoiseSampler();
        double _snowman2 = perlinNoiseSampler.sample((double)x / 8.0, (double)y / 8.0, 0.0, 0.0, 0.0);
        if (_snowman2 > 0.4) {
            return 44;
        }
        if (_snowman2 > 0.2) {
            return 45;
        }
        if (_snowman2 < -0.4) {
            return 10;
        }
        if (_snowman2 < -0.2) {
            return 46;
        }
        return 0;
    }
}

