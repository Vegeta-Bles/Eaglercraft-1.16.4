/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.structure.OceanRuinGenerator;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.OceanRuinFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class OceanRuinFeature
extends StructureFeature<OceanRuinFeatureConfig> {
    public OceanRuinFeature(Codec<OceanRuinFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureFeature.StructureStartFactory<OceanRuinFeatureConfig> getStructureStartFactory() {
        return Start::new;
    }

    public static enum BiomeType implements StringIdentifiable
    {
        WARM("warm"),
        COLD("cold");

        public static final Codec<BiomeType> CODEC;
        private static final Map<String, BiomeType> BY_NAME;
        private final String name;

        private BiomeType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Nullable
        public static BiomeType byName(String name) {
            return BY_NAME.get(name);
        }

        @Override
        public String asString() {
            return this.name;
        }

        static {
            CODEC = StringIdentifiable.createCodec(BiomeType::values, BiomeType::byName);
            BY_NAME = Arrays.stream(BiomeType.values()).collect(Collectors.toMap(BiomeType::getName, biomeType -> biomeType));
        }
    }

    public static class Start
    extends StructureStart<OceanRuinFeatureConfig> {
        public Start(StructureFeature<OceanRuinFeatureConfig> structureFeature, int n, int n2, BlockBox blockBox, int n3, long l) {
            super(structureFeature, n, n2, blockBox, n3, l);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, int n, int n2, Biome biome, OceanRuinFeatureConfig oceanRuinFeatureConfig) {
            int n3 = n * 16;
            _snowman = n2 * 16;
            BlockPos _snowman2 = new BlockPos(n3, 90, _snowman);
            BlockRotation _snowman3 = BlockRotation.random(this.random);
            OceanRuinGenerator.addPieces(structureManager, _snowman2, _snowman3, this.children, this.random, oceanRuinFeatureConfig);
            this.setBoundingBoxFromChildren();
        }
    }
}

