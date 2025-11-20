/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTables;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DungeonFeature
extends Feature<DefaultFeatureConfig> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final EntityType<?>[] MOB_SPAWNER_ENTITIES = new EntityType[]{EntityType.SKELETON, EntityType.ZOMBIE, EntityType.ZOMBIE, EntityType.SPIDER};
    private static final BlockState AIR = Blocks.CAVE_AIR.getDefaultState();

    public DungeonFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
        StructureWorldAccess structureWorldAccess2;
        int n;
        Object _snowman2;
        BlockPos blockPos2;
        int n2 = 3;
        _snowman = random.nextInt(2) + 2;
        _snowman = -_snowman - 1;
        _snowman = _snowman + 1;
        _snowman = -1;
        _snowman = 4;
        _snowman = random.nextInt(2) + 2;
        _snowman = -_snowman - 1;
        _snowman = _snowman + 1;
        _snowman = 0;
        for (n = _snowman; n <= _snowman; ++n) {
            for (_snowman = -1; _snowman <= 4; ++_snowman) {
                for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                    blockPos2 = blockPos.add(n, _snowman, _snowman);
                    _snowman2 = structureWorldAccess2.getBlockState(blockPos2).getMaterial();
                    boolean _snowman3 = ((Material)_snowman2).isSolid();
                    if (_snowman == -1 && !_snowman3) {
                        return false;
                    }
                    if (_snowman == 4 && !_snowman3) {
                        return false;
                    }
                    if (n != _snowman && n != _snowman && _snowman != _snowman && _snowman != _snowman || _snowman != 0 || !structureWorldAccess2.isAir(blockPos2) || !structureWorldAccess2.isAir(blockPos2.up())) continue;
                    ++_snowman;
                }
            }
        }
        if (_snowman < 1 || _snowman > 5) {
            return false;
        }
        for (n = _snowman; n <= _snowman; ++n) {
            for (_snowman = 3; _snowman >= -1; --_snowman) {
                for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                    blockPos2 = blockPos.add(n, _snowman, _snowman);
                    _snowman2 = structureWorldAccess2.getBlockState(blockPos2);
                    if (n == _snowman || _snowman == -1 || _snowman == _snowman || n == _snowman || _snowman == 4 || _snowman == _snowman) {
                        if (blockPos2.getY() >= 0 && !structureWorldAccess2.getBlockState(blockPos2.down()).getMaterial().isSolid()) {
                            structureWorldAccess2.setBlockState(blockPos2, AIR, 2);
                            continue;
                        }
                        if (!((AbstractBlock.AbstractBlockState)_snowman2).getMaterial().isSolid() || ((AbstractBlock.AbstractBlockState)_snowman2).isOf(Blocks.CHEST)) continue;
                        if (_snowman == -1 && random.nextInt(4) != 0) {
                            structureWorldAccess2.setBlockState(blockPos2, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                            continue;
                        }
                        structureWorldAccess2.setBlockState(blockPos2, Blocks.COBBLESTONE.getDefaultState(), 2);
                        continue;
                    }
                    if (((AbstractBlock.AbstractBlockState)_snowman2).isOf(Blocks.CHEST) || ((AbstractBlock.AbstractBlockState)_snowman2).isOf(Blocks.SPAWNER)) continue;
                    structureWorldAccess2.setBlockState(blockPos2, AIR, 2);
                }
            }
        }
        block6: for (n = 0; n < 2; ++n) {
            for (_snowman = 0; _snowman < 3; ++_snowman) {
                _snowman = blockPos.getX() + random.nextInt(_snowman * 2 + 1) - _snowman;
                BlockPos blockPos3 = new BlockPos(_snowman, _snowman = blockPos.getY(), _snowman = blockPos.getZ() + random.nextInt(_snowman * 2 + 1) - _snowman);
                if (!structureWorldAccess2.isAir(blockPos3)) continue;
                int _snowman4 = 0;
                for (Direction direction : Direction.Type.HORIZONTAL) {
                    if (!structureWorldAccess2.getBlockState(blockPos3.offset(direction)).getMaterial().isSolid()) continue;
                    ++_snowman4;
                }
                if (_snowman4 != 1) continue;
                structureWorldAccess2.setBlockState(blockPos3, StructurePiece.orientateChest(structureWorldAccess2, blockPos3, Blocks.CHEST.getDefaultState()), 2);
                LootableContainerBlockEntity.setLootTable(structureWorldAccess2, random, blockPos3, LootTables.SIMPLE_DUNGEON_CHEST);
                continue block6;
            }
        }
        structureWorldAccess2.setBlockState(blockPos, Blocks.SPAWNER.getDefaultState(), 2);
        BlockEntity _snowman5 = structureWorldAccess2.getBlockEntity(blockPos);
        if (_snowman5 instanceof MobSpawnerBlockEntity) {
            ((MobSpawnerBlockEntity)_snowman5).getLogic().setEntityId(this.getMobSpawnerEntity(random));
        } else {
            LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", (Object)blockPos.getX(), (Object)blockPos.getY(), (Object)blockPos.getZ());
        }
        return true;
    }

    private EntityType<?> getMobSpawnerEntity(Random random) {
        return Util.getRandom(MOB_SPAWNER_ENTITIES, random);
    }
}

