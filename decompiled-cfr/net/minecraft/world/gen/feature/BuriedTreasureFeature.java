/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.structure.BuriedTreasureGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;

public class BuriedTreasureFeature
extends StructureFeature<ProbabilityConfig> {
    public BuriedTreasureFeature(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, ProbabilityConfig probabilityConfig) {
        chunkRandom.setRegionSeed(l, n, n2, 10387320);
        return chunkRandom.nextFloat() < probabilityConfig.probability;
    }

    @Override
    public StructureFeature.StructureStartFactory<ProbabilityConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static class Start
    extends StructureStart<ProbabilityConfig> {
        public Start(StructureFeature<ProbabilityConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, ProbabilityConfig probabilityConfig) {
            int n3 = n * 16;
            _snowman = n2 * 16;
            BlockPos _snowman2 = new BlockPos(n3 + 9, 90, _snowman + 9);
            this.children.add(new BuriedTreasureGenerator.Piece(_snowman2));
            this.setBoundingBoxFromChildren();
        }

        @Override
        public BlockPos getPos() {
            return new BlockPos((this.getChunkX() << 4) + 9, 0, (this.getChunkZ() << 4) + 9);
        }
    }
}

