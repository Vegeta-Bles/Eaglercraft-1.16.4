/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Blocks;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.WoodlandMansionGenerator;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class WoodlandMansionFeature
extends StructureFeature<DefaultFeatureConfig> {
    public WoodlandMansionFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean isUniformDistribution() {
        return false;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        Set<Biome> set = biomeSource.getBiomesInArea(n * 16 + 9, chunkGenerator.getSeaLevel(), n2 * 16 + 9, 32);
        for (Biome biome2 : set) {
            if (biome2.getGenerationSettings().hasStructureFeature(this)) continue;
            return false;
        }
        return true;
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
            BlockRotation blockRotation = BlockRotation.random(this.random);
            int _snowman2 = 5;
            int _snowman3 = 5;
            if (blockRotation == BlockRotation.CLOCKWISE_90) {
                _snowman2 = -5;
            } else if (blockRotation == BlockRotation.CLOCKWISE_180) {
                _snowman2 = -5;
                _snowman3 = -5;
            } else if (blockRotation == BlockRotation.COUNTERCLOCKWISE_90) {
                _snowman3 = -5;
            }
            int _snowman4 = (n << 4) + 7;
            int _snowman5 = (n2 << 4) + 7;
            int _snowman6 = chunkGenerator.getHeightInGround(_snowman4, _snowman5, Heightmap.Type.WORLD_SURFACE_WG);
            int _snowman7 = chunkGenerator.getHeightInGround(_snowman4, _snowman5 + _snowman3, Heightmap.Type.WORLD_SURFACE_WG);
            int _snowman8 = chunkGenerator.getHeightInGround(_snowman4 + _snowman2, _snowman5, Heightmap.Type.WORLD_SURFACE_WG);
            int _snowman9 = chunkGenerator.getHeightInGround(_snowman4 + _snowman2, _snowman5 + _snowman3, Heightmap.Type.WORLD_SURFACE_WG);
            int _snowman10 = Math.min(Math.min(_snowman6, _snowman7), Math.min(_snowman8, _snowman9));
            if (_snowman10 < 60) {
                return;
            }
            BlockPos _snowman11 = new BlockPos(n * 16 + 8, _snowman10 + 1, n2 * 16 + 8);
            LinkedList _snowman12 = Lists.newLinkedList();
            WoodlandMansionGenerator.addPieces(structureManager, _snowman11, blockRotation, _snowman12, this.random);
            this.children.addAll(_snowman12);
            this.setBoundingBoxFromChildren();
        }

        @Override
        public void generateStructure(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos) {
            super.generateStructure(world, structureAccessor, chunkGenerator, random, box, chunkPos);
            int n = this.boundingBox.minY;
            for (_snowman = box.minX; _snowman <= box.maxX; ++_snowman) {
                for (_snowman = box.minZ; _snowman <= box.maxZ; ++_snowman) {
                    BlockPos blockPos = new BlockPos(_snowman, n, _snowman);
                    if (world.isAir(blockPos) || !this.boundingBox.contains(blockPos)) continue;
                    boolean _snowman2 = false;
                    for (Object object : this.children) {
                        if (!((StructurePiece)object).getBoundingBox().contains(blockPos)) continue;
                        _snowman2 = true;
                        break;
                    }
                    if (!_snowman2) continue;
                    for (int i = n - 1; i > 1 && (world.isAir((BlockPos)(object = new BlockPos(_snowman, i, _snowman))) || world.getBlockState((BlockPos)object).getMaterial().isLiquid()); --i) {
                        world.setBlockState((BlockPos)object, Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                }
            }
        }
    }
}

