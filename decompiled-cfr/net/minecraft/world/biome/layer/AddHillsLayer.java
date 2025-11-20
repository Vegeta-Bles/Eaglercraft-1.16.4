/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntMap
 *  it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.biome.layer;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.util.Util;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.layer.type.MergingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;
import net.minecraft.world.biome.layer.util.LayerSampler;
import net.minecraft.world.biome.layer.util.NorthWestCoordinateTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum AddHillsLayer implements MergingLayer,
NorthWestCoordinateTransformer
{
    INSTANCE;

    private static final Logger LOGGER;
    private static final Int2IntMap field_26727;

    @Override
    public int sample(LayerRandomnessSource context, LayerSampler sampler1, LayerSampler sampler2, int x, int z) {
        int n = sampler1.sample(this.transformX(x + 1), this.transformZ(z + 1));
        _snowman = sampler2.sample(this.transformX(x + 1), this.transformZ(z + 1));
        if (n > 255) {
            LOGGER.debug("old! {}", (Object)n);
        }
        _snowman = (_snowman - 2) % 29;
        if (!BiomeLayers.isShallowOcean(n) && _snowman >= 2 && _snowman == 1) {
            return field_26727.getOrDefault(n, n);
        }
        if (context.nextInt(3) == 0 || _snowman == 0) {
            _snowman = n;
            if (n == 2) {
                _snowman = 17;
            } else if (n == 4) {
                _snowman = 18;
            } else if (n == 27) {
                _snowman = 28;
            } else if (n == 29) {
                _snowman = 1;
            } else if (n == 5) {
                _snowman = 19;
            } else if (n == 32) {
                _snowman = 33;
            } else if (n == 30) {
                _snowman = 31;
            } else if (n == 1) {
                _snowman = context.nextInt(3) == 0 ? 18 : 4;
            } else if (n == 12) {
                _snowman = 13;
            } else if (n == 21) {
                _snowman = 22;
            } else if (n == 168) {
                _snowman = 169;
            } else if (n == 0) {
                _snowman = 24;
            } else if (n == 45) {
                _snowman = 48;
            } else if (n == 46) {
                _snowman = 49;
            } else if (n == 10) {
                _snowman = 50;
            } else if (n == 3) {
                _snowman = 34;
            } else if (n == 35) {
                _snowman = 36;
            } else if (BiomeLayers.areSimilar(n, 38)) {
                _snowman = 37;
            } else if ((n == 24 || n == 48 || n == 49 || n == 50) && context.nextInt(3) == 0) {
                int n2 = _snowman = context.nextInt(2) == 0 ? 1 : 4;
            }
            if (_snowman == 0 && _snowman != n) {
                _snowman = field_26727.getOrDefault(_snowman, n);
            }
            if (_snowman != n) {
                _snowman = 0;
                if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 0)), n)) {
                    ++_snowman;
                }
                if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 2), this.transformZ(z + 1)), n)) {
                    ++_snowman;
                }
                if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 0), this.transformZ(z + 1)), n)) {
                    ++_snowman;
                }
                if (BiomeLayers.areSimilar(sampler1.sample(this.transformX(x + 1), this.transformZ(z + 2)), n)) {
                    ++_snowman;
                }
                if (_snowman >= 3) {
                    return _snowman;
                }
            }
        }
        return n;
    }

    static {
        LOGGER = LogManager.getLogger();
        field_26727 = (Int2IntMap)Util.make(new Int2IntOpenHashMap(), int2IntOpenHashMap -> {
            int2IntOpenHashMap.put(1, 129);
            int2IntOpenHashMap.put(2, 130);
            int2IntOpenHashMap.put(3, 131);
            int2IntOpenHashMap.put(4, 132);
            int2IntOpenHashMap.put(5, 133);
            int2IntOpenHashMap.put(6, 134);
            int2IntOpenHashMap.put(12, 140);
            int2IntOpenHashMap.put(21, 149);
            int2IntOpenHashMap.put(23, 151);
            int2IntOpenHashMap.put(27, 155);
            int2IntOpenHashMap.put(28, 156);
            int2IntOpenHashMap.put(29, 157);
            int2IntOpenHashMap.put(30, 158);
            int2IntOpenHashMap.put(32, 160);
            int2IntOpenHashMap.put(33, 161);
            int2IntOpenHashMap.put(34, 162);
            int2IntOpenHashMap.put(35, 163);
            int2IntOpenHashMap.put(36, 164);
            int2IntOpenHashMap.put(37, 165);
            int2IntOpenHashMap.put(38, 166);
            int2IntOpenHashMap.put(39, 167);
        });
    }
}

