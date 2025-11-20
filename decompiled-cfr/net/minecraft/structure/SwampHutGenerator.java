/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.structure;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.structure.StructurePieceWithDimensions;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class SwampHutGenerator
extends StructurePieceWithDimensions {
    private boolean hasWitch;
    private boolean hasCat;

    public SwampHutGenerator(Random random, int n, int n2) {
        super(StructurePieceType.SWAMP_HUT, random, n, 64, n2, 7, 7, 9);
    }

    public SwampHutGenerator(StructureManager structureManager, CompoundTag compoundTag) {
        super(StructurePieceType.SWAMP_HUT, compoundTag);
        this.hasWitch = compoundTag.getBoolean("Witch");
        this.hasCat = compoundTag.getBoolean("Cat");
    }

    @Override
    protected void toNbt(CompoundTag tag) {
        super.toNbt(tag);
        tag.putBoolean("Witch", this.hasWitch);
        tag.putBoolean("Cat", this.hasCat);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess2, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos blockPos) {
        StructureWorldAccess structureWorldAccess2;
        int n;
        if (!this.method_14839(structureWorldAccess2, boundingBox, 0)) {
            return false;
        }
        this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.getDefaultState(), Blocks.SPRUCE_PLANKS.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.getDefaultState(), Blocks.OAK_LOG.getDefaultState(), false);
        this.addBlock(structureWorldAccess2, Blocks.OAK_FENCE.getDefaultState(), 2, 3, 2, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.OAK_FENCE.getDefaultState(), 3, 3, 7, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.AIR.getDefaultState(), 1, 3, 4, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.AIR.getDefaultState(), 5, 3, 4, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.AIR.getDefaultState(), 5, 3, 5, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 1, 3, 5, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.CRAFTING_TABLE.getDefaultState(), 3, 2, 6, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.CAULDRON.getDefaultState(), 4, 2, 6, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.OAK_FENCE.getDefaultState(), 1, 2, 1, boundingBox);
        this.addBlock(structureWorldAccess2, Blocks.OAK_FENCE.getDefaultState(), 5, 2, 1, boundingBox);
        BlockState blockState = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.NORTH);
        _snowman = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.EAST);
        _snowman = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.WEST);
        _snowman = (BlockState)Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, Direction.SOUTH);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 4, 1, 6, 4, 1, blockState, blockState, false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 4, 2, 0, 4, 7, _snowman, _snowman, false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 6, 4, 2, 6, 4, 7, _snowman, _snowman, false);
        this.fillWithOutline(structureWorldAccess2, boundingBox, 0, 4, 8, 6, 4, 8, _snowman, _snowman, false);
        this.addBlock(structureWorldAccess2, (BlockState)blockState.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 0, 4, 1, boundingBox);
        this.addBlock(structureWorldAccess2, (BlockState)blockState.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 6, 4, 1, boundingBox);
        this.addBlock(structureWorldAccess2, (BlockState)_snowman.with(StairsBlock.SHAPE, StairShape.OUTER_LEFT), 0, 4, 8, boundingBox);
        this.addBlock(structureWorldAccess2, (BlockState)_snowman.with(StairsBlock.SHAPE, StairShape.OUTER_RIGHT), 6, 4, 8, boundingBox);
        for (n = 2; n <= 7; n += 5) {
            for (_snowman = 1; _snowman <= 5; _snowman += 4) {
                this.fillDownwards(structureWorldAccess2, Blocks.OAK_LOG.getDefaultState(), _snowman, -1, n, boundingBox);
            }
        }
        if (!this.hasWitch && boundingBox.contains(new BlockPos(n = this.applyXTransform(2, 5), _snowman = this.applyYTransform(2), _snowman = this.applyZTransform(2, 5)))) {
            this.hasWitch = true;
            WitchEntity witchEntity = EntityType.WITCH.create(structureWorldAccess2.toServerWorld());
            witchEntity.setPersistent();
            witchEntity.refreshPositionAndAngles((double)n + 0.5, _snowman, (double)_snowman + 0.5, 0.0f, 0.0f);
            witchEntity.initialize(structureWorldAccess2, structureWorldAccess2.getLocalDifficulty(new BlockPos(n, _snowman, _snowman)), SpawnReason.STRUCTURE, null, null);
            structureWorldAccess2.spawnEntityAndPassengers(witchEntity);
        }
        this.method_16181(structureWorldAccess2, boundingBox);
        return true;
    }

    private void method_16181(ServerWorldAccess serverWorldAccess, BlockBox blockBox) {
        if (!this.hasCat && blockBox.contains(new BlockPos(_snowman = this.applyXTransform(2, 5), _snowman = this.applyYTransform(2), _snowman = this.applyZTransform(2, 5)))) {
            this.hasCat = true;
            CatEntity catEntity = EntityType.CAT.create(serverWorldAccess.toServerWorld());
            catEntity.setPersistent();
            catEntity.refreshPositionAndAngles((double)_snowman + 0.5, _snowman, (double)_snowman + 0.5, 0.0f, 0.0f);
            catEntity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(new BlockPos(_snowman, _snowman, _snowman)), SpawnReason.STRUCTURE, null, null);
            serverWorldAccess.spawnEntityAndPassengers(catEntity);
        }
    }
}

