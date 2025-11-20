/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.HorizontalConnectingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LeadItem;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FenceBlock
extends HorizontalConnectingBlock {
    private final VoxelShape[] cullingShapes;

    public FenceBlock(AbstractBlock.Settings settings) {
        super(2.0f, 2.0f, 16.0f, 16.0f, 24.0f, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
        this.cullingShapes = this.createShapes(2.0f, 1.0f, 16.0f, 6.0f, 15.0f);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return this.cullingShapes[this.getShapeIndex(state)];
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    public boolean canConnect(BlockState state, boolean neighborIsFullSquare, Direction dir) {
        Block block = state.getBlock();
        boolean _snowman2 = this.isFence(block);
        boolean _snowman3 = block instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, dir);
        return !FenceBlock.cannotConnect(block) && neighborIsFullSquare || _snowman2 || _snowman3;
    }

    private boolean isFence(Block block) {
        return block.isIn(BlockTags.FENCES) && block.isIn(BlockTags.WOODEN_FENCES) == this.getDefaultState().isIn(BlockTags.WOODEN_FENCES);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() == Items.LEAD) {
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        }
        return LeadItem.attachHeldMobsToBlock(player, world, pos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos _snowman2 = ctx.getBlockPos();
        FluidState _snowman3 = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos _snowman4 = _snowman2.north();
        BlockPos _snowman5 = _snowman2.east();
        BlockPos _snowman6 = _snowman2.south();
        BlockPos _snowman7 = _snowman2.west();
        BlockState _snowman8 = world.getBlockState(_snowman4);
        BlockState _snowman9 = world.getBlockState(_snowman5);
        BlockState _snowman10 = world.getBlockState(_snowman6);
        BlockState _snowman11 = world.getBlockState(_snowman7);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.getPlacementState(ctx).with(NORTH, this.canConnect(_snowman8, _snowman8.isSideSolidFullSquare(world, _snowman4, Direction.SOUTH), Direction.SOUTH))).with(EAST, this.canConnect(_snowman9, _snowman9.isSideSolidFullSquare(world, _snowman5, Direction.WEST), Direction.WEST))).with(SOUTH, this.canConnect(_snowman10, _snowman10.isSideSolidFullSquare(world, _snowman6, Direction.NORTH), Direction.NORTH))).with(WEST, this.canConnect(_snowman11, _snowman11.isSideSolidFullSquare(world, _snowman7, Direction.EAST), Direction.EAST))).with(WATERLOGGED, _snowman3.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        if (state.get(WATERLOGGED).booleanValue()) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction.getAxis().getType() == Direction.Type.HORIZONTAL) {
            return (BlockState)state.with((Property)FACING_PROPERTIES.get(direction), this.canConnect(newState, newState.isSideSolidFullSquare(world, posFrom, direction.getOpposite()), direction.getOpposite()));
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, WEST, SOUTH, WATERLOGGED);
    }
}

