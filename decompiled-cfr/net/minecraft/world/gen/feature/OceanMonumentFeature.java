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
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.OceanMonumentGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class OceanMonumentFeature
extends StructureFeature<DefaultFeatureConfig> {
    private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of((Object)new SpawnSettings.SpawnEntry(EntityType.GUARDIAN, 1, 2, 4));

    public OceanMonumentFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    protected boolean isUniformDistribution() {
        return false;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource2, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, DefaultFeatureConfig defaultFeatureConfig) {
        BiomeSource biomeSource2;
        Set<Biome> set = biomeSource2.getBiomesInArea(n * 16 + 9, chunkGenerator.getSeaLevel(), n2 * 16 + 9, 16);
        for (Biome biome2 : set) {
            if (biome2.getGenerationSettings().hasStructureFeature(this)) continue;
            return false;
        }
        Set<Biome> _snowman2 = biomeSource2.getBiomesInArea(n * 16 + 9, chunkGenerator.getSeaLevel(), n2 * 16 + 9, 29);
        for (Biome biome3 : _snowman2) {
            if (biome3.getCategory() == Biome.Category.OCEAN || biome3.getCategory() == Biome.Category.RIVER) continue;
            return false;
        }
        return true;
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
        private boolean field_13717;

        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            this.method_16588(n, n2);
        }

        private void method_16588(int chunkX, int chunkZ) {
            int n = chunkX * 16 - 29;
            _snowman = chunkZ * 16 - 29;
            Direction _snowman2 = Direction.Type.HORIZONTAL.random(this.random);
            this.children.add(new OceanMonumentGenerator.Base(this.random, n, _snowman, _snowman2));
            this.setBoundingBoxFromChildren();
            this.field_13717 = true;
        }

        @Override
        public void generateStructure(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos) {
            if (!this.field_13717) {
                this.children.clear();
                this.method_16588(this.getChunkX(), this.getChunkZ());
            }
            super.generateStructure(world, structureAccessor, chunkGenerator, random, box, chunkPos);
        }
    }
}

