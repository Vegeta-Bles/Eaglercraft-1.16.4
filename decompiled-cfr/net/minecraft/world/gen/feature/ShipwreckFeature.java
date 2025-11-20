/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ShipwreckFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ShipwreckFeature
extends StructureFeature<ShipwreckFeatureConfig> {
    public ShipwreckFeature(Codec<ShipwreckFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<ShipwreckFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start
    extends StructureStart<ShipwreckFeatureConfig> {
        public Start(StructureFeature<ShipwreckFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, ShipwreckFeatureConfig shipwreckFeatureConfig) {
            BlockRotation blockRotation = BlockRotation.random(this.random);
            BlockPos _snowman2 = new BlockPos(n * 16, 90, n2 * 16);
            ShipwreckGenerator.addParts(structureManager, _snowman2, blockRotation, this.children, this.random, shipwreckFeatureConfig);
            this.setBoundingBoxFromChildren();
        }
    }
}

