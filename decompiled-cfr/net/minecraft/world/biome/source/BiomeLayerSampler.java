/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.biome.source;

import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeLayerSampler {
    private static final Logger LOGGER = LogManager.getLogger();
    private final CachingLayerSampler sampler;

    public BiomeLayerSampler(LayerFactory<CachingLayerSampler> layerFactory) {
        this.sampler = layerFactory.make();
    }

    public Biome sample(Registry<Biome> registry, int n, int n2) {
        _snowman = this.sampler.sample(n, n2);
        RegistryKey<Biome> registryKey = BuiltinBiomes.fromRawId(_snowman);
        if (registryKey == null) {
            throw new IllegalStateException("Unknown biome id emitted by layers: " + _snowman);
        }
        Biome _snowman2 = registry.get(registryKey);
        if (_snowman2 == null) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("Unknown biome id: " + _snowman));
            }
            LOGGER.warn("Unknown biome id: ", (Object)_snowman);
            return registry.get(BuiltinBiomes.fromRawId(0));
        }
        return _snowman2;
    }
}

