/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  javax.annotation.Nullable
 */
package net.minecraft.structure.processor;

import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class BlockAgeStructureProcessor
extends StructureProcessor {
    public static final Codec<BlockAgeStructureProcessor> CODEC = Codec.FLOAT.fieldOf("mossiness").xmap(BlockAgeStructureProcessor::new, blockAgeStructureProcessor -> Float.valueOf(blockAgeStructureProcessor.mossiness)).codec();
    private final float mossiness;

    public BlockAgeStructureProcessor(float mossiness) {
        this.mossiness = mossiness;
    }

    @Override
    @Nullable
    public Structure.StructureBlockInfo process(WorldView worldView, BlockPos pos, BlockPos blockPos, Structure.StructureBlockInfo structureBlockInfo, Structure.StructureBlockInfo structureBlockInfo2, StructurePlacementData structurePlacementData) {
        Random random = structurePlacementData.getRandom(structureBlockInfo2.pos);
        BlockState _snowman2 = structureBlockInfo2.state;
        BlockPos _snowman3 = structureBlockInfo2.pos;
        BlockState _snowman4 = null;
        if (_snowman2.isOf(Blocks.STONE_BRICKS) || _snowman2.isOf(Blocks.STONE) || _snowman2.isOf(Blocks.CHISELED_STONE_BRICKS)) {
            _snowman4 = this.processBlocks(random);
        } else if (_snowman2.isIn(BlockTags.STAIRS)) {
            _snowman4 = this.processStairs(random, structureBlockInfo2.state);
        } else if (_snowman2.isIn(BlockTags.SLABS)) {
            _snowman4 = this.processSlabs(random);
        } else if (_snowman2.isIn(BlockTags.WALLS)) {
            _snowman4 = this.processWalls(random);
        } else if (_snowman2.isOf(Blocks.OBSIDIAN)) {
            _snowman4 = this.processObsidian(random);
        }
        if (_snowman4 != null) {
            return new Structure.StructureBlockInfo(_snowman3, _snowman4, structureBlockInfo2.tag);
        }
        return structureBlockInfo2;
    }

    @Nullable
    private BlockState processBlocks(Random random) {
        if (random.nextFloat() >= 0.5f) {
            return null;
        }
        BlockState[] blockStateArray = new BlockState[]{Blocks.CRACKED_STONE_BRICKS.getDefaultState(), BlockAgeStructureProcessor.randomStairProperties(random, Blocks.STONE_BRICK_STAIRS)};
        _snowman = new BlockState[]{Blocks.MOSSY_STONE_BRICKS.getDefaultState(), BlockAgeStructureProcessor.randomStairProperties(random, Blocks.MOSSY_STONE_BRICK_STAIRS)};
        return this.process(random, blockStateArray, _snowman);
    }

    @Nullable
    private BlockState processStairs(Random random, BlockState state) {
        Direction direction = state.get(StairsBlock.FACING);
        BlockHalf _snowman2 = state.get(StairsBlock.HALF);
        if (random.nextFloat() >= 0.5f) {
            return null;
        }
        BlockState[] _snowman3 = new BlockState[]{Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_BRICK_SLAB.getDefaultState()};
        BlockState[] _snowman4 = new BlockState[]{(BlockState)((BlockState)Blocks.MOSSY_STONE_BRICK_STAIRS.getDefaultState().with(StairsBlock.FACING, direction)).with(StairsBlock.HALF, _snowman2), Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState()};
        return this.process(random, _snowman3, _snowman4);
    }

    @Nullable
    private BlockState processSlabs(Random random) {
        if (random.nextFloat() < this.mossiness) {
            return Blocks.MOSSY_STONE_BRICK_SLAB.getDefaultState();
        }
        return null;
    }

    @Nullable
    private BlockState processWalls(Random random) {
        if (random.nextFloat() < this.mossiness) {
            return Blocks.MOSSY_STONE_BRICK_WALL.getDefaultState();
        }
        return null;
    }

    @Nullable
    private BlockState processObsidian(Random random) {
        if (random.nextFloat() < 0.15f) {
            return Blocks.CRYING_OBSIDIAN.getDefaultState();
        }
        return null;
    }

    private static BlockState randomStairProperties(Random random, Block stairs) {
        return (BlockState)((BlockState)stairs.getDefaultState().with(StairsBlock.FACING, Direction.Type.HORIZONTAL.random(random))).with(StairsBlock.HALF, BlockHalf.values()[random.nextInt(BlockHalf.values().length)]);
    }

    private BlockState process(Random random, BlockState[] regularStates, BlockState[] mossyStates) {
        if (random.nextFloat() < this.mossiness) {
            return BlockAgeStructureProcessor.randomState(random, mossyStates);
        }
        return BlockAgeStructureProcessor.randomState(random, regularStates);
    }

    private static BlockState randomState(Random random, BlockState[] states) {
        return states[random.nextInt(states.length)];
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return StructureProcessorType.BLOCK_AGE;
    }
}

