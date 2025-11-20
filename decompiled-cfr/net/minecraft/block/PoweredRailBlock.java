/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PoweredRailBlock
extends AbstractRailBlock {
    public static final EnumProperty<RailShape> SHAPE = Properties.STRAIGHT_RAIL_SHAPE;
    public static final BooleanProperty POWERED = Properties.POWERED;

    protected PoweredRailBlock(AbstractBlock.Settings settings) {
        super(true, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(SHAPE, RailShape.NORTH_SOUTH)).with(POWERED, false));
    }

    protected boolean isPoweredByOtherRails(World world, BlockPos pos, BlockState state, boolean boolean4, int distance) {
        if (distance >= 8) {
            return false;
        }
        int n = pos.getX();
        _snowman = pos.getY();
        _snowman = pos.getZ();
        boolean _snowman2 = true;
        RailShape _snowman3 = state.get(SHAPE);
        switch (_snowman3) {
            case NORTH_SOUTH: {
                if (boolean4) {
                    ++_snowman;
                    break;
                }
                --_snowman;
                break;
            }
            case EAST_WEST: {
                if (boolean4) {
                    --n;
                    break;
                }
                ++n;
                break;
            }
            case ASCENDING_EAST: {
                if (boolean4) {
                    --n;
                } else {
                    ++n;
                    ++_snowman;
                    _snowman2 = false;
                }
                _snowman3 = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_WEST: {
                if (boolean4) {
                    --n;
                    ++_snowman;
                    _snowman2 = false;
                } else {
                    ++n;
                }
                _snowman3 = RailShape.EAST_WEST;
                break;
            }
            case ASCENDING_NORTH: {
                if (boolean4) {
                    ++_snowman;
                } else {
                    --_snowman;
                    ++_snowman;
                    _snowman2 = false;
                }
                _snowman3 = RailShape.NORTH_SOUTH;
                break;
            }
            case ASCENDING_SOUTH: {
                if (boolean4) {
                    ++_snowman;
                    ++_snowman;
                    _snowman2 = false;
                } else {
                    --_snowman;
                }
                _snowman3 = RailShape.NORTH_SOUTH;
            }
        }
        if (this.isPoweredByOtherRails(world, new BlockPos(n, _snowman, _snowman), boolean4, distance, _snowman3)) {
            return true;
        }
        return _snowman2 && this.isPoweredByOtherRails(world, new BlockPos(n, _snowman - 1, _snowman), boolean4, distance, _snowman3);
    }

    protected boolean isPoweredByOtherRails(World world, BlockPos pos, boolean bl, int distance, RailShape shape) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.isOf(this)) {
            return false;
        }
        RailShape _snowman2 = blockState.get(SHAPE);
        if (shape == RailShape.EAST_WEST && (_snowman2 == RailShape.NORTH_SOUTH || _snowman2 == RailShape.ASCENDING_NORTH || _snowman2 == RailShape.ASCENDING_SOUTH)) {
            return false;
        }
        if (shape == RailShape.NORTH_SOUTH && (_snowman2 == RailShape.EAST_WEST || _snowman2 == RailShape.ASCENDING_EAST || _snowman2 == RailShape.ASCENDING_WEST)) {
            return false;
        }
        if (blockState.get(POWERED).booleanValue()) {
            if (world.isReceivingRedstonePower(pos)) {
                return true;
            }
            return this.isPoweredByOtherRails(world, pos, blockState, bl, distance + 1);
        }
        return false;
    }

    @Override
    protected void updateBlockState(BlockState state, World world, BlockPos pos, Block neighbor) {
        boolean bl = state.get(POWERED);
        boolean bl2 = _snowman = world.isReceivingRedstonePower(pos) || this.isPoweredByOtherRails(world, pos, state, true, 0) || this.isPoweredByOtherRails(world, pos, state, false, 0);
        if (_snowman != bl) {
            world.setBlockState(pos, (BlockState)state.with(POWERED, _snowman), 3);
            world.updateNeighborsAlways(pos.down(), this);
            if (state.get(SHAPE).isAscending()) {
                world.updateNeighborsAlways(pos.up(), this);
            }
        }
    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            case CLOCKWISE_180: {
                switch (state.get(SHAPE)) {
                    case ASCENDING_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                }
            }
            case COUNTERCLOCKWISE_90: {
                switch (state.get(SHAPE)) {
                    case NORTH_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    }
                }
            }
            case CLOCKWISE_90: {
                switch (state.get(SHAPE)) {
                    case NORTH_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.EAST_WEST);
                    }
                    case EAST_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_SOUTH);
                    }
                    case ASCENDING_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case ASCENDING_NORTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                }
            }
        }
        return state;
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        RailShape railShape = state.get(SHAPE);
        switch (mirror) {
            case LEFT_RIGHT: {
                switch (railShape) {
                    case ASCENDING_NORTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_SOUTH);
                    }
                    case ASCENDING_SOUTH: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_NORTH);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                }
                break;
            }
            case FRONT_BACK: {
                switch (railShape) {
                    case ASCENDING_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_WEST);
                    }
                    case ASCENDING_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.ASCENDING_EAST);
                    }
                    case SOUTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_WEST);
                    }
                    case SOUTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.SOUTH_EAST);
                    }
                    case NORTH_WEST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_EAST);
                    }
                    case NORTH_EAST: {
                        return (BlockState)state.with(SHAPE, RailShape.NORTH_WEST);
                    }
                }
                break;
            }
        }
        return super.mirror(state, mirror);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, POWERED);
    }
}

