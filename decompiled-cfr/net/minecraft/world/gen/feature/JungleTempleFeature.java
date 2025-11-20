/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.JungleTempleGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class JungleTempleFeature
extends StructureFeature<DefaultFeatureConfig> {
    public JungleTempleFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start
    extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            JungleTempleGenerator jungleTempleGenerator = new JungleTempleGenerator(this.random, n * 16, n2 * 16);
            this.children.add(jungleTempleGenerator);
            this.setBoundingBoxFromChildren();
        }
    }
}

