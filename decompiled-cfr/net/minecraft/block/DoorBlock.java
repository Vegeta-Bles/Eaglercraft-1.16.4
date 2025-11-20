/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DoorBlock
extends Block {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final EnumProperty<DoorHinge> HINGE = Properties.DOOR_HINGE;
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    protected DoorBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(OPEN, false)).with(HINGE, DoorHinge.LEFT)).with(POWERED, false)).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        boolean _snowman2 = state.get(OPEN) == false;
        boolean _snowman3 = state.get(HINGE) == DoorHinge.RIGHT;
        switch (direction) {
            default: {
                return _snowman2 ? WEST_SHAPE : (_snowman3 ? SOUTH_SHAPE : NORTH_SHAPE);
            }
            case SOUTH: {
                return _snowman2 ? NORTH_SHAPE : (_snowman3 ? WEST_SHAPE : EAST_SHAPE);
            }
            case WEST: {
                return _snowman2 ? EAST_SHAPE : (_snowman3 ? NORTH_SHAPE : SOUTH_SHAPE);
            }
            case NORTH: 
        }
        return _snowman2 ? SOUTH_SHAPE : (_snowman3 ? EAST_SHAPE : WEST_SHAPE);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            if (newState.isOf(this) && newState.get(HALF) != doubleBlockHalf) {
                return (BlockState)((BlockState)((BlockState)((BlockState)state.with(FACING, newState.get(FACING))).with(OPEN, newState.get(OPEN))).with(HINGE, newState.get(HINGE))).with(POWERED, newState.get(POWERED));
            }
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            TallPlantBlock.onBreakInCreative(world, pos, state, player);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch (type) {
            case LAND: {
                return state.get(OPEN);
            }
            case WATER: {
                return false;
            }
            case AIR: {
                return state.get(OPEN);
            }
        }
        return false;
    }

    private int getOpenSoundEventId() {
        return this.material == Material.METAL ? 1011 : 1012;
    }

    private int getCloseSoundEventId() {
        return this.material == Material.METAL ? 1005 : 1006;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        if (blockPos.getY() < 255 && ctx.getWorld().getBlockState(blockPos.up()).canReplace(ctx)) {
            World world = ctx.getWorld();
            boolean _snowman2 = world.isReceivingRedstonePower(blockPos) || world.isReceivingRedstonePower(blockPos.up());
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing())).with(HINGE, this.getHinge(ctx))).with(POWERED, _snowman2)).with(OPEN, _snowman2)).with(HALF, DoubleBlockHalf.LOWER);
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    private DoorHinge getHinge(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos _snowman2 = ctx.getBlockPos();
        Direction _snowman3 = ctx.getPlayerFacing();
        BlockPos _snowman4 = _snowman2.up();
        Direction _snowman5 = _snowman3.rotateYCounterclockwise();
        BlockPos _snowman6 = _snowman2.offset(_snowman5);
        BlockState _snowman7 = world.getBlockState(_snowman6);
        BlockPos _snowman8 = _snowman4.offset(_snowman5);
        BlockState _snowman9 = world.getBlockState(_snowman8);
        Direction _snowman10 = _snowman3.rotateYClockwise();
        BlockPos _snowman11 = _snowman2.offset(_snowman10);
        BlockState _snowman12 = world.getBlockState(_snowman11);
        BlockPos _snowman13 = _snowman4.offset(_snowman10);
        BlockState _snowman14 = world.getBlockState(_snowman13);
        int _snowman15 = (_snowman7.isFullCube(world, _snowman6) ? -1 : 0) + (_snowman9.isFullCube(world, _snowman8) ? -1 : 0) + (_snowman12.isFullCube(world, _snowman11) ? 1 : 0) + (_snowman14.isFullCube(world, _snowman13) ? 1 : 0);
        boolean _snowman16 = _snowman7.isOf(this) && _snowman7.get(HALF) == DoubleBlockHalf.LOWER;
        boolean bl = _snowman = _snowman12.isOf(this) && _snowman12.get(HALF) == DoubleBlockHalf.LOWER;
        if (_snowman16 && !_snowman || _snowman15 > 0) {
            return DoorHinge.RIGHT;
        }
        if (_snowman && !_snowman16 || _snowman15 < 0) {
            return DoorHinge.LEFT;
        }
        int _snowman17 = _snowman3.getOffsetX();
        int _snowman18 = _snowman3.getOffsetZ();
        Vec3d _snowman19 = ctx.getHitPos();
        double _snowman20 = _snowman19.x - (double)_snowman2.getX();
        double _snowman21 = _snowman19.z - (double)_snowman2.getZ();
        return _snowman17 < 0 && _snowman21 < 0.5 || _snowman17 > 0 && _snowman21 > 0.5 || _snowman18 < 0 && _snowman20 > 0.5 || _snowman18 > 0 && _snowman20 < 0.5 ? DoorHinge.RIGHT : DoorHinge.LEFT;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (this.material == Material.METAL) {
            return ActionResult.PASS;
        }
        state = (BlockState)state.cycle(OPEN);
        world.setBlockState(pos, state, 10);
        world.syncWorldEvent(player, state.get(OPEN) != false ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), pos, 0);
        return ActionResult.success(world.isClient);
    }

    public boolean method_30841(BlockState blockState) {
        return blockState.get(OPEN);
    }

    public void setOpen(World world, BlockState blockState, BlockPos blockPos, boolean bl) {
        if (!blockState.isOf(this) || blockState.get(OPEN) == bl) {
            return;
        }
        world.setBlockState(blockPos, (BlockState)blockState.with(OPEN, bl), 10);
        this.playOpenCloseSound(world, blockPos, bl);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        boolean bl;
        boolean bl2 = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN)) ? true : (bl = false);
        if (block != this && bl != state.get(POWERED)) {
            if (bl != state.get(OPEN)) {
                this.playOpenCloseSound(world, pos, bl);
            }
            world.setBlockState(pos, (BlockState)((BlockState)state.with(POWERED, bl)).with(OPEN, bl), 2);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState _snowman2 = world.getBlockState(blockPos);
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return _snowman2.isSideSolidFullSquare(world, blockPos, Direction.UP);
        }
        return _snowman2.isOf(this);
    }

    private void playOpenCloseSound(World world, BlockPos pos, boolean open) {
        world.syncWorldEvent(null, open ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), pos, 0);
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        if (mirror == BlockMirror.NONE) {
            return state;
        }
        return (BlockState)state.rotate(mirror.getRotation(state.get(FACING))).cycle(HINGE);
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, HINGE, POWERED);
    }

    public static boolean isWoodenDoor(World world, BlockPos pos) {
        return DoorBlock.isWoodenDoor(world.getBlockState(pos));
    }

    public static boolean isWoodenDoor(BlockState state) {
        return state.getBlock() instanceof DoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
    }
}

