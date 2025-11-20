/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.NetherFortressGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class NetherFortressFeature
extends StructureFeature<DefaultFeatureConfig> {
    private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of((Object)new SpawnSettings.SpawnEntry(EntityType.BLAZE, 10, 2, 3), (Object)new SpawnSettings.SpawnEntry(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4), (Object)new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 8, 5, 5), (Object)new SpawnSettings.SpawnEntry(EntityType.SKELETON, 2, 5, 5), (Object)new SpawnSettings.SpawnEntry(EntityType.MAGMA_CUBE, 3, 4, 4));

    public NetherFortressFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        return chunkRandom.nextInt(5) < 2;
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return MONSTER_SPAWNS;
    }

    public static class Start
    extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            NetherFortressGenerator.Start start = new NetherFortressGenerator.Start(this.random, (n << 4) + 2, (n2 << 4) + 2);
            this.children.add(start);
            start.fillOpenings(start, this.children, this.random);
            List<StructurePiece> _snowman2 = start.pieces;
            while (!_snowman2.isEmpty()) {
                int n3 = this.random.nextInt(_snowman2.size());
                StructurePiece _snowman3 = _snowman2.remove(n3);
                _snowman3.fillOpenings(start, this.children, this.random);
            }
            this.setBoundingBoxFromChildren();
            this.randomUpwardTranslation(this.random, 48, 70);
        }
    }
}

