/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.block.enums.PistonType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.piston.PistonHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PistonBlock
extends FacingBlock {
    public static final BooleanProperty EXTENDED = Properties.EXTENDED;
    protected static final VoxelShape EXTENDED_EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 12.0, 16.0, 16.0);
    protected static final VoxelShape EXTENDED_WEST_SHAPE = Block.createCuboidShape(4.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EXTENDED_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 12.0);
    protected static final VoxelShape EXTENDED_NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EXTENDED_UP_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    protected static final VoxelShape EXTENDED_DOWN_SHAPE = Block.createCuboidShape(0.0, 4.0, 0.0, 16.0, 16.0, 16.0);
    private final boolean sticky;

    public PistonBlock(boolean sticky, AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(EXTENDED, false));
        this.sticky = sticky;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(EXTENDED).booleanValue()) {
            switch (state.get(FACING)) {
                case DOWN: {
                    return EXTENDED_DOWN_SHAPE;
                }
                default: {
                    return EXTENDED_UP_SHAPE;
                }
                case NORTH: {
                    return EXTENDED_NORTH_SHAPE;
                }
                case SOUTH: {
                    return EXTENDED_SOUTH_SHAPE;
                }
                case WEST: {
                    return EXTENDED_WEST_SHAPE;
                }
                case EAST: 
            }
            return EXTENDED_EAST_SHAPE;
        }
        return VoxelShapes.fullCube();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient) {
            this.tryMove(world, pos, state);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            this.tryMove(world, pos, state);
        }
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (oldState.isOf(state.getBlock())) {
            return;
        }
        if (!world.isClient && world.getBlockEntity(pos) == null) {
            this.tryMove(world, pos, state);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite())).with(EXTENDED, false);
    }

    private void tryMove(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(FACING);
        boolean _snowman2 = this.shouldExtend(world, pos, direction);
        if (_snowman2 && !state.get(EXTENDED).booleanValue()) {
            if (new PistonHandler(world, pos, direction, true).calculatePush()) {
                world.addSyncedBlockEvent(pos, this, 0, direction.getId());
            }
        } else if (!_snowman2 && state.get(EXTENDED).booleanValue()) {
            BlockPos blockPos = pos.offset(direction, 2);
            BlockState _snowman3 = world.getBlockState(blockPos);
            int _snowman4 = 1;
            if (_snowman3.isOf(Blocks.MOVING_PISTON) && _snowman3.get(FACING) == direction && (_snowman = world.getBlockEntity(blockPos)) instanceof PistonBlockEntity && (_snowman = (PistonBlockEntity)_snowman).isExtending() && (_snowman.getProgress(0.0f) < 0.5f || world.getTime() == _snowman.getSavedWorldTime() || ((ServerWorld)world).isInBlockTick())) {
                _snowman4 = 2;
            }
            world.addSyncedBlockEvent(pos, this, _snowman4, direction.getId());
        }
    }

    private boolean shouldExtend(World world, BlockPos pos, Direction pistonFace) {
        for (Direction direction : Direction.values()) {
            if (direction == pistonFace || !world.isEmittingRedstonePower(pos.offset(direction), direction)) continue;
            return true;
        }
        if (world.isEmittingRedstonePower(pos, Direction.DOWN)) {
            return true;
        }
        BlockPos blockPos = pos.up();
        for (Direction direction : Direction.values()) {
            if (direction == Direction.DOWN || !world.isEmittingRedstonePower(blockPos.offset(direction), direction)) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data) {
        Direction direction = state.get(FACING);
        if (!world.isClient) {
            boolean bl = this.shouldExtend(world, pos, direction);
            if (bl && (type == 1 || type == 2)) {
                world.setBlockState(pos, (BlockState)state.with(EXTENDED, true), 2);
                return false;
            }
            if (!bl && type == 0) {
                return false;
            }
        }
        if (type == 0) {
            if (!this.move(world, pos, direction, true)) return false;
            world.setBlockState(pos, (BlockState)state.with(EXTENDED, true), 67);
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.25f + 0.6f);
            return true;
        } else {
            if (type != 1 && type != 2) return true;
            BlockEntity _snowman2 = world.getBlockEntity(pos.offset(direction));
            if (_snowman2 instanceof PistonBlockEntity) {
                ((PistonBlockEntity)_snowman2).finish();
            }
            BlockState _snowman3 = (BlockState)((BlockState)Blocks.MOVING_PISTON.getDefaultState().with(PistonExtensionBlock.FACING, direction)).with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
            world.setBlockState(pos, _snowman3, 20);
            world.setBlockEntity(pos, PistonExtensionBlock.createBlockEntityPiston((BlockState)this.getDefaultState().with(FACING, Direction.byId(data & 7)), direction, false, true));
            world.updateNeighbors(pos, _snowman3.getBlock());
            _snowman3.updateNeighbors(world, pos, 2);
            if (this.sticky) {
                BlockPos blockPos = pos.add(direction.getOffsetX() * 2, direction.getOffsetY() * 2, direction.getOffsetZ() * 2);
                BlockState _snowman4 = world.getBlockState(blockPos);
                boolean _snowman5 = false;
                if (_snowman4.isOf(Blocks.MOVING_PISTON) && (_snowman = world.getBlockEntity(blockPos)) instanceof PistonBlockEntity && (_snowman = (PistonBlockEntity)_snowman).getFacing() == direction && _snowman.isExtending()) {
                    _snowman.finish();
                    _snowman5 = true;
                }
                if (!_snowman5) {
                    if (type == 1 && !_snowman4.isAir() && PistonBlock.isMovable(_snowman4, world, blockPos, direction.getOpposite(), false, direction) && (_snowman4.getPistonBehavior() == PistonBehavior.NORMAL || _snowman4.isOf(Blocks.PISTON) || _snowman4.isOf(Blocks.STICKY_PISTON))) {
                        this.move(world, pos, direction, false);
                    } else {
                        world.removeBlock(pos.offset(direction), false);
                    }
                }
            } else {
                world.removeBlock(pos.offset(direction), false);
            }
            world.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5f, world.random.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }

    public static boolean isMovable(BlockState blockState, World world, BlockPos blockPos, Direction direction, boolean canBreak, Direction pistonDir) {
        if (blockPos.getY() < 0 || blockPos.getY() > world.getHeight() - 1 || !world.getWorldBorder().contains(blockPos)) {
            return false;
        }
        if (blockState.isAir()) {
            return true;
        }
        if (blockState.isOf(Blocks.OBSIDIAN) || blockState.isOf(Blocks.CRYING_OBSIDIAN) || blockState.isOf(Blocks.RESPAWN_ANCHOR)) {
            return false;
        }
        if (direction == Direction.DOWN && blockPos.getY() == 0) {
            return false;
        }
        if (direction == Direction.UP && blockPos.getY() == world.getHeight() - 1) {
            return false;
        }
        if (blockState.isOf(Blocks.PISTON) || blockState.isOf(Blocks.STICKY_PISTON)) {
            if (blockState.get(EXTENDED).booleanValue()) {
                return false;
            }
        } else {
            if (blockState.getHardness(world, blockPos) == -1.0f) {
                return false;
            }
            switch (blockState.getPistonBehavior()) {
                case BLOCK: {
                    return false;
                }
                case DESTROY: {
                    return canBreak;
                }
                case PUSH_ONLY: {
                    return direction == pistonDir;
                }
            }
        }
        return !blockState.getBlock().hasBlockEntity();
    }

    private boolean move(World world, BlockPos pos, Direction dir, boolean retract) {
        BlockPos blockPos;
        Object object;
        PistonHandler pistonHandler;
        BlockPos blockPos2 = pos.offset(dir);
        if (!retract && world.getBlockState(blockPos2).isOf(Blocks.PISTON_HEAD)) {
            world.setBlockState(blockPos2, Blocks.AIR.getDefaultState(), 20);
        }
        if (!(pistonHandler = new PistonHandler(world, pos, dir, retract)).calculatePush()) {
            return false;
        }
        HashMap _snowman2 = Maps.newHashMap();
        List<BlockPos> _snowman3 = pistonHandler.getMovedBlocks();
        ArrayList _snowman4 = Lists.newArrayList();
        for (int i = 0; i < _snowman3.size(); ++i) {
            BlockPos _snowman6 = _snowman3.get(i);
            BlockState _snowman5 = world.getBlockState(_snowman6);
            _snowman4.add(_snowman5);
            _snowman2.put(_snowman6, _snowman5);
        }
        List<BlockPos> list = pistonHandler.getBrokenBlocks();
        BlockState[] _snowman9 = new BlockState[_snowman3.size() + list.size()];
        Direction _snowman11 = retract ? dir : dir.getOpposite();
        int _snowman7 = 0;
        for (int n2 = list.size() - 1; n2 >= 0; --n2) {
            BlockPos _snowman8 = list.get(n2);
            BlockState blockState = world.getBlockState(_snowman8);
            BlockEntity object2 = blockState.getBlock().hasBlockEntity() ? world.getBlockEntity(_snowman8) : null;
            PistonBlock.dropStacks(blockState, world, _snowman8, object2);
            world.setBlockState(_snowman8, Blocks.AIR.getDefaultState(), 18);
            _snowman9[_snowman7++] = blockState;
        }
        for (int i = _snowman3.size() - 1; i >= 0; --i) {
            object = _snowman3.get(i);
            BlockState blockState = world.getBlockState((BlockPos)object);
            object = ((BlockPos)object).offset(_snowman11);
            _snowman2.remove(object);
            world.setBlockState((BlockPos)object, (BlockState)Blocks.MOVING_PISTON.getDefaultState().with(FACING, dir), 68);
            world.setBlockEntity((BlockPos)object, PistonExtensionBlock.createBlockEntityPiston((BlockState)_snowman4.get(i), dir, retract, false));
            _snowman9[_snowman7++] = blockState;
        }
        if (retract) {
            PistonType pistonType = this.sticky ? PistonType.STICKY : PistonType.DEFAULT;
            object = (BlockState)((BlockState)Blocks.PISTON_HEAD.getDefaultState().with(PistonHeadBlock.FACING, dir)).with(PistonHeadBlock.TYPE, pistonType);
            BlockState blockState = (BlockState)((BlockState)Blocks.MOVING_PISTON.getDefaultState().with(PistonExtensionBlock.FACING, dir)).with(PistonExtensionBlock.TYPE, this.sticky ? PistonType.STICKY : PistonType.DEFAULT);
            _snowman2.remove(blockPos2);
            world.setBlockState(blockPos2, blockState, 68);
            world.setBlockEntity(blockPos2, PistonExtensionBlock.createBlockEntityPiston((BlockState)object, dir, true, true));
        }
        BlockState blockState = Blocks.AIR.getDefaultState();
        for (BlockPos blockPos3 : _snowman2.keySet()) {
            world.setBlockState(blockPos3, blockState, 82);
        }
        for (Map.Entry entry : _snowman2.entrySet()) {
            blockPos = (BlockPos)entry.getKey();
            BlockState _snowman10 = (BlockState)entry.getValue();
            _snowman10.prepare(world, blockPos, 2);
            blockState.updateNeighbors(world, blockPos, 2);
            blockState.prepare(world, blockPos, 2);
        }
        _snowman7 = 0;
        for (int n = list.size() - 1; n >= 0; --n) {
            BlockState blockState2 = _snowman9[_snowman7++];
            blockPos = list.get(n);
            blockState2.prepare(world, blockPos, 2);
            world.updateNeighborsAlways(blockPos, blockState2.getBlock());
        }
        for (int i = _snowman3.size() - 1; i >= 0; --i) {
            world.updateNeighborsAlways(_snowman3.get(i), _snowman9[_snowman7++].getBlock());
        }
        if (retract) {
            world.updateNeighborsAlways(blockPos2, Blocks.PISTON_HEAD);
        }
        return true;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, EXTENDED);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return state.get(EXTENDED);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}

