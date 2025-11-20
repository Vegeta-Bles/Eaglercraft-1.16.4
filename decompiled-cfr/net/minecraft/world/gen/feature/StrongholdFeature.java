/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class StrongholdFeature
extends StructureFeature<DefaultFeatureConfig> {
    public StrongholdFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return chunkGenerator.isStrongholdStartingChunk(new ChunkPos(n, n2));
    }

    public static class Start
    extends StructureStart<DefaultFeatureConfig> {
        private final long seed;

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
            this.seed = l;
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            int n3 = 0;
            do {
                this.children.clear();
                this.boundingBox = BlockBox.empty();
                this.random.setCarverSeed(this.seed + (long)n3++, n, n2);
                StrongholdGenerator.init();
                StrongholdGenerator.Start start = new StrongholdGenerator.Start(this.random, (n << 4) + 2, (n2 << 4) + 2);
                this.children.add(start);
                start.fillOpenings(start, this.children, this.random);
                List<StructurePiece> _snowman2 = start.pieces;
                while (!_snowman2.isEmpty()) {
                    int n4 = this.random.nextInt(_snowman2.size());
                    StructurePiece _snowman3 = _snowman2.remove(n4);
                    _snowman3.fillOpenings(start, this.children, this.random);
                }
                this.setBoundingBoxFromChildren();
                this.randomUpwardTranslation(chunkGenerator.getSeaLevel(), this.random, 10);
            } while (this.children.isEmpty() || start.portalRoom == null);
        }
    }
}

