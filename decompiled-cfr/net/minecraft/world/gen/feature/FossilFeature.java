/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.BlockRotStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class FossilFeature
extends Feature<DefaultFeatureConfig> {
    private static final Identifier SPINE_1 = new Identifier("fossil/spine_1");
    private static final Identifier SPINE_2 = new Identifier("fossil/spine_2");
    private static final Identifier SPINE_3 = new Identifier("fossil/spine_3");
    private static final Identifier SPINE_4 = new Identifier("fossil/spine_4");
    private static final Identifier SPINE_1_COAL = new Identifier("fossil/spine_1_coal");
    private static final Identifier SPINE_2_COAL = new Identifier("fossil/spine_2_coal");
    private static final Identifier SPINE_3_COAL = new Identifier("fossil/spine_3_coal");
    private static final Identifier SPINE_4_COAL = new Identifier("fossil/spine_4_coal");
    private static final Identifier SKULL_1 = new Identifier("fossil/skull_1");
    private static final Identifier SKULL_2 = new Identifier("fossil/skull_2");
    private static final Identifier SKULL_3 = new Identifier("fossil/skull_3");
    private static final Identifier SKULL_4 = new Identifier("fossil/skull_4");
    private static final Identifier SKULL_1_COAL = new Identifier("fossil/skull_1_coal");
    private static final Identifier SKULL_2_COAL = new Identifier("fossil/skull_2_coal");
    private static final Identifier SKULL_3_COAL = new Identifier("fossil/skull_3_coal");
    private static final Identifier SKULL_4_COAL = new Identifier("fossil/skull_4_coal");
    private static final Identifier[] FOSSILS = new Identifier[]{SPINE_1, SPINE_2, SPINE_3, SPINE_4, SKULL_1, SKULL_2, SKULL_3, SKULL_4};
    private static final Identifier[] COAL_FOSSILS = new Identifier[]{SPINE_1_COAL, SPINE_2_COAL, SPINE_3_COAL, SPINE_4_COAL, SKULL_1_COAL, SKULL_2_COAL, SKULL_3_COAL, SKULL_4_COAL};

    public FossilFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random2, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        Random random2;
        int _snowman13;
        BlockRotation blockRotation = BlockRotation.random(random2);
        int _snowman2 = random2.nextInt(FOSSILS.length);
        StructureManager _snowman3 = structureWorldAccess.toServerWorld().getServer().getStructureManager();
        Structure _snowman4 = _snowman3.getStructureOrBlank(FOSSILS[_snowman2]);
        Structure _snowman5 = _snowman3.getStructureOrBlank(COAL_FOSSILS[_snowman2]);
        ChunkPos _snowman6 = new ChunkPos(blockPos);
        BlockBox _snowman7 = new BlockBox(_snowman6.getStartX(), 0, _snowman6.getStartZ(), _snowman6.getEndX(), 256, _snowman6.getEndZ());
        StructurePlacementData _snowman8 = new StructurePlacementData().setRotation(blockRotation).setBoundingBox(_snowman7).setRandom(random2).addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        BlockPos _snowman9 = _snowman4.getRotatedSize(blockRotation);
        int _snowman10 = random2.nextInt(16 - _snowman9.getX());
        int _snowman11 = random2.nextInt(16 - _snowman9.getZ());
        int _snowman12 = 256;
        for (_snowman13 = 0; _snowman13 < _snowman9.getX(); ++_snowman13) {
            for (_snowman = 0; _snowman < _snowman9.getZ(); ++_snowman) {
                _snowman12 = Math.min(_snowman12, structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, blockPos.getX() + _snowman13 + _snowman10, blockPos.getZ() + _snowman + _snowman11));
            }
        }
        _snowman13 = Math.max(_snowman12 - 15 - random2.nextInt(10), 10);
        BlockPos _snowman14 = _snowman4.offsetByTransformedSize(blockPos.add(_snowman10, _snowman13, _snowman11), BlockMirror.NONE, blockRotation);
        BlockRotStructureProcessor _snowman15 = new BlockRotStructureProcessor(0.9f);
        _snowman8.clearProcessors().addProcessor(_snowman15);
        _snowman4.place(structureWorldAccess, _snowman14, _snowman14, _snowman8, random2, 4);
        _snowman8.removeProcessor(_snowman15);
        BlockRotStructureProcessor _snowman16 = new BlockRotStructureProcessor(0.1f);
        _snowman8.clearProcessors().addProcessor(_snowman16);
        _snowman5.place(structureWorldAccess, _snowman14, _snowman14, _snowman8, random2, 4);
        return true;
    }
}

