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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class PillagerOutpostFeature
extends JigsawFeature {
    private static final List<SpawnSettings.SpawnEntry> MONSTER_SPAWNS = ImmutableList.of((Object)new SpawnSettings.SpawnEntry(EntityType.PILLAGER, 1, 1, 1));

    public PillagerOutpostFeature(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, 0, true, true);
    }

    @Override
    public List<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return MONSTER_SPAWNS;
    }

    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long l, ChunkRandom chunkRandom, int n, int n2, Biome biome, ChunkPos chunkPos, StructurePoolFeatureConfig structurePoolFeatureConfig) {
        int n3 = n >> 4;
        _snowman = n2 >> 4;
        chunkRandom.setSeed((long)(n3 ^ _snowman << 4) ^ l);
        chunkRandom.nextInt();
        if (chunkRandom.nextInt(5) != 0) {
            return false;
        }
        return !this.method_30845(chunkGenerator, l, chunkRandom, n, n2);
    }

    private boolean method_30845(ChunkGenerator chunkGenerator, long l, ChunkRandom chunkRandom, int n, int n2) {
        StructureConfig structureConfig = chunkGenerator.getStructuresConfig().getForType(StructureFeature.VILLAGE);
        if (structureConfig == null) {
            return false;
        }
        for (int i = n - 10; i <= n + 10; ++i) {
            for (_snowman = n2 - 10; _snowman <= n2 + 10; ++_snowman) {
                ChunkPos chunkPos = StructureFeature.VILLAGE.getStartChunk(structureConfig, l, chunkRandom, i, _snowman);
                if (i != chunkPos.x || _snowman != chunkPos.z) continue;
                return true;
            }
        }
        return false;
    }
}

