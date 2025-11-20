/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ConcretePowderBlock
extends FallingBlock {
    private final BlockState hardenedState;

    public ConcretePowderBlock(Block hardened, AbstractBlock.Settings settings) {
        super(settings);
        this.hardenedState = hardened.getDefaultState();
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        if (ConcretePowderBlock.shouldHarden(world, pos, currentStateInPos)) {
            world.setBlockState(pos, this.hardenedState, 3);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        if (ConcretePowderBlock.shouldHarden(world, _snowman = ctx.getBlockPos(), _snowman = world.getBlockState(_snowman))) {
            return this.hardenedState;
        }
        return super.getPlacementState(ctx);
    }

    private static boolean shouldHarden(BlockView world, BlockPos pos, BlockState state) {
        return ConcretePowderBlock.hardensIn(state) || ConcretePowderBlock.hardensOnAnySide(world, pos);
    }

    private static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
        boolean _snowman3 = false;
        BlockPos.Mutable _snowman2 = pos.mutableCopy();
        for (Direction direction : Direction.values()) {
            BlockState blockState = world.getBlockState(_snowman2);
            if (direction == Direction.DOWN && !ConcretePowderBlock.hardensIn(blockState)) continue;
            _snowman2.set(pos, direction);
            blockState = world.getBlockState(_snowman2);
            if (!ConcretePowderBlock.hardensIn(blockState) || blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) continue;
            _snowman3 = true;
            break;
        }
        return _snowman3;
    }

    private static boolean hardensIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (ConcretePowderBlock.hardensOnAnySide(world, pos)) {
            return this.hardenedState;
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return state.getTopMaterialColor((BlockView)world, (BlockPos)pos).color;
    }
}

