/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SpongeBlock
extends Block {
    protected SpongeBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        this.update(world, pos);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        this.update(world, pos);
        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    protected void update(World world, BlockPos pos) {
        if (this.absorbWater(world, pos)) {
            world.setBlockState(pos, Blocks.WET_SPONGE.getDefaultState(), 2);
            world.syncWorldEvent(2001, pos, Block.getRawIdFromState(Blocks.WATER.getDefaultState()));
        }
    }

    private boolean absorbWater(World world, BlockPos pos) {
        LinkedList linkedList = Lists.newLinkedList();
        linkedList.add(new Pair<BlockPos, Integer>(pos, 0));
        int _snowman2 = 0;
        while (!linkedList.isEmpty()) {
            Pair pair = (Pair)linkedList.poll();
            BlockPos _snowman3 = (BlockPos)pair.getLeft();
            int _snowman4 = (Integer)pair.getRight();
            for (Direction direction : Direction.values()) {
                BlockPos blockPos = _snowman3.offset(direction);
                BlockState _snowman5 = world.getBlockState(blockPos);
                FluidState _snowman6 = world.getFluidState(blockPos);
                Material _snowman7 = _snowman5.getMaterial();
                if (!_snowman6.isIn(FluidTags.WATER)) continue;
                if (_snowman5.getBlock() instanceof FluidDrainable && ((FluidDrainable)((Object)_snowman5.getBlock())).tryDrainFluid(world, blockPos, _snowman5) != Fluids.EMPTY) {
                    ++_snowman2;
                    if (_snowman4 >= 6) continue;
                    linkedList.add(new Pair<BlockPos, Integer>(blockPos, _snowman4 + 1));
                    continue;
                }
                if (_snowman5.getBlock() instanceof FluidBlock) {
                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                    ++_snowman2;
                    if (_snowman4 >= 6) continue;
                    linkedList.add(new Pair<BlockPos, Integer>(blockPos, _snowman4 + 1));
                    continue;
                }
                if (_snowman7 != Material.UNDERWATER_PLANT && _snowman7 != Material.REPLACEABLE_UNDERWATER_PLANT) continue;
                BlockEntity _snowman8 = _snowman5.getBlock().hasBlockEntity() ? world.getBlockEntity(blockPos) : null;
                SpongeBlock.dropStacks(_snowman5, world, blockPos, _snowman8);
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                ++_snowman2;
                if (_snowman4 >= 6) continue;
                linkedList.add(new Pair<BlockPos, Integer>(blockPos, _snowman4 + 1));
            }
            if (_snowman2 <= 64) continue;
            break;
        }
        return _snowman2 > 0;
    }
}

