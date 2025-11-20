/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class BonusChestFeature
extends Feature<DefaultFeatureConfig> {
    public BonusChestFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        List _snowman2 = IntStream.rangeClosed(chunkPos.getStartX(), chunkPos.getEndX()).boxed().collect(Collectors.toList());
        Collections.shuffle(_snowman2, random);
        List _snowman3 = IntStream.rangeClosed(chunkPos.getStartZ(), chunkPos.getEndZ()).boxed().collect(Collectors.toList());
        Collections.shuffle(_snowman3, random);
        BlockPos.Mutable _snowman4 = new BlockPos.Mutable();
        for (Integer n : _snowman2) {
            for (Integer n2 : _snowman3) {
                _snowman4.set(n, 0, n2);
                BlockPos blockPos2 = structureWorldAccess.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowman4);
                if (!structureWorldAccess.isAir(blockPos2) && !structureWorldAccess.getBlockState(blockPos2).getCollisionShape(structureWorldAccess, blockPos2).isEmpty()) continue;
                structureWorldAccess.setBlockState(blockPos2, Blocks.CHEST.getDefaultState(), 2);
                LootableContainerBlockEntity.setLootTable(structureWorldAccess, random, blockPos2, LootTables.SPAWN_BONUS_CHEST);
                BlockState _snowman5 = Blocks.TORCH.getDefaultState();
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    BlockPos blockPos3 = blockPos2.offset(direction);
                    if (!_snowman5.canPlaceAt(structureWorldAccess, blockPos3)) continue;
                    structureWorldAccess.setBlockState(blockPos3, _snowman5, 2);
                }
                return true;
            }
        }
        return false;
    }
}

