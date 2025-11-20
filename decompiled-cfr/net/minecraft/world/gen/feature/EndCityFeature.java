/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.structure.EndCityGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class EndCityFeature
extends StructureFeature<DefaultFeatureConfig> {
    public EndCityFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean isUniformDistribution() {
        return false;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return EndCityFeature.getGenerationHeight(n, n2, chunkGenerator) >= 60;
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    private static int getGenerationHeight(int chunkX, int chunkZ, ChunkGenerator chunkGenerator) {
        Random random = new Random(chunkX + chunkZ * 10387313);
        BlockRotation _snowman2 = BlockRotation.random(random);
        int _snowman3 = 5;
        int _snowman4 = 5;
        if (_snowman2 == BlockRotation.CLOCKWISE_90) {
            _snowman3 = -5;
        } else if (_snowman2 == BlockRotation.CLOCKWISE_180) {
            _snowman3 = -5;
            _snowman4 = -5;
        } else if (_snowman2 == BlockRotation.COUNTERCLOCKWISE_90) {
            _snowman4 = -5;
        }
        int _snowman5 = (chunkX << 4) + 7;
        int _snowman6 = (chunkZ << 4) + 7;
        int _snowman7 = chunkGenerator.getHeightInGround(_snowman5, _snowman6, Heightmap.Type.WORLD_SURFACE_WG);
        int _snowman8 = chunkGenerator.getHeightInGround(_snowman5, _snowman6 + _snowman4, Heightmap.Type.WORLD_SURFACE_WG);
        int _snowman9 = chunkGenerator.getHeightInGround(_snowman5 + _snowman3, _snowman6, Heightmap.Type.WORLD_SURFACE_WG);
        int _snowman10 = chunkGenerator.getHeightInGround(_snowman5 + _snowman3, _snowman6 + _snowman4, Heightmap.Type.WORLD_SURFACE_WG);
        return Math.min(Math.min(_snowman7, _snowman8), Math.min(_snowman9, _snowman10));
    }

    public static class Start
    extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            BlockRotation blockRotation = BlockRotation.random(this.random);
            int _snowman2 = EndCityFeature.getGenerationHeight(n, n2, chunkGenerator);
            if (_snowman2 < 60) {
                return;
            }
            BlockPos _snowman3 = new BlockPos(n * 16 + 8, _snowman2, n2 * 16 + 8);
            EndCityGenerator.addPieces(structureManager, _snowman3, blockRotation, this.children, this.random);
            this.setBoundingBoxFromChildren();
        }
    }
}

