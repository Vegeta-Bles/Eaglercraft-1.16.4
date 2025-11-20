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
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.SwampHutGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class SwampHutFeature
extends StructureFeature<DefaultFeatureConfig> {
    private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of((Object)new SpawnSettings.SpawnEntry(EntityType.WITCH, 1, 1, 1));
    private static final List<SpawnSettings.SpawnEntry> CREATURE_SPAWNS = ImmutableList.of((Object)new SpawnSettings.SpawnEntry(EntityType.CAT, 1, 1, 1));

    public SwampHutFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return MONSTER_SPAWNS;
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getCreatureSpawns() {
        return CREATURE_SPAWNS;
    }

    public static class Start
    extends StructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, DefaultFeatureConfig defaultFeatureConfig) {
            SwampHutGenerator swampHutGenerator = new SwampHutGenerator(this.random, n * 16, n2 * 16);
            this.children.add(swampHutGenerator);
            this.setBoundingBoxFromChildren();
        }
    }
}

