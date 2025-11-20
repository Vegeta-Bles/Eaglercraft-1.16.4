/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class RailPlacementHelper {
    private final World world;
    private final BlockPos pos;
    private final AbstractRailBlock block;
    private BlockState state;
    private final boolean allowCurves;
    private final List<BlockPos> neighbors = Lists.newArrayList();

    public RailPlacementHelper(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
        this.block = (AbstractRailBlock)state.getBlock();
        RailShape railShape = state.get(this.block.getShapeProperty());
        this.allowCurves = this.block.canMakeCurves();
        this.computeNeighbors(railShape);
    }

    public List<BlockPos> getNeighbors() {
        return this.neighbors;
    }

    private void computeNeighbors(RailShape shape) {
        this.neighbors.clear();
        switch (shape) {
            case NORTH_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south());
                break;
            }
            case EAST_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_EAST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.east().up());
                break;
            }
            case ASCENDING_WEST: {
                this.neighbors.add(this.pos.west().up());
                this.neighbors.add(this.pos.east());
                break;
            }
            case ASCENDING_NORTH: {
                this.neighbors.add(this.pos.north().up());
                this.neighbors.add(this.pos.south());
                break;
            }
            case ASCENDING_SOUTH: {
                this.neighbors.add(this.pos.north());
                this.neighbors.add(this.pos.south().up());
                break;
            }
            case SOUTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.south());
                break;
            }
            case SOUTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.south());
                break;
            }
            case NORTH_WEST: {
                this.neighbors.add(this.pos.west());
                this.neighbors.add(this.pos.north());
                break;
            }
            case NORTH_EAST: {
                this.neighbors.add(this.pos.east());
                this.neighbors.add(this.pos.north());
            }
        }
    }

    private void updateNeighborPositions() {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            RailPlacementHelper railPlacementHelper = this.getNeighboringRail(this.neighbors.get(i));
            if (railPlacementHelper == null || !railPlacementHelper.isNeighbor(this)) {
                this.neighbors.remove(i--);
                continue;
            }
            this.neighbors.set(i, railPlacementHelper.pos);
        }
    }

    private boolean isVerticallyNearRail(BlockPos pos) {
        return AbstractRailBlock.isRail(this.world, pos) || AbstractRailBlock.isRail(this.world, pos.up()) || AbstractRailBlock.isRail(this.world, pos.down());
    }

    @Nullable
    private RailPlacementHelper getNeighboringRail(BlockPos pos) {
        BlockPos blockPos = pos;
        BlockState _snowman2 = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(_snowman2)) {
            return new RailPlacementHelper(this.world, blockPos, _snowman2);
        }
        blockPos = pos.up();
        _snowman2 = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(_snowman2)) {
            return new RailPlacementHelper(this.world, blockPos, _snowman2);
        }
        blockPos = pos.down();
        _snowman2 = this.world.getBlockState(blockPos);
        if (AbstractRailBlock.isRail(_snowman2)) {
            return new RailPlacementHelper(this.world, blockPos, _snowman2);
        }
        return null;
    }

    private boolean isNeighbor(RailPlacementHelper other) {
        return this.isNeighbor(other.pos);
    }

    private boolean isNeighbor(BlockPos pos) {
        for (int i = 0; i < this.neighbors.size(); ++i) {
            BlockPos blockPos = this.neighbors.get(i);
            if (blockPos.getX() != pos.getX() || blockPos.getZ() != pos.getZ()) continue;
            return true;
        }
        return false;
    }

    protected int getNeighborCount() {
        int n = 0;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!this.isVerticallyNearRail(this.pos.offset(direction))) continue;
            ++n;
        }
        return n;
    }

    private boolean canConnect(RailPlacementHelper placementHelper) {
        return this.isNeighbor(placementHelper) || this.neighbors.size() != 2;
    }

    private void computeRailShape(RailPlacementHelper placementHelper) {
        this.neighbors.add(placementHelper.pos);
        BlockPos blockPos = this.pos.north();
        _snowman = this.pos.south();
        _snowman = this.pos.west();
        _snowman = this.pos.east();
        boolean _snowman2 = this.isNeighbor(blockPos);
        boolean _snowman3 = this.isNeighbor(_snowman);
        boolean _snowman4 = this.isNeighbor(_snowman);
        boolean _snowman5 = this.isNeighbor(_snowman);
        RailShape _snowman6 = null;
        if (_snowman2 || _snowman3) {
            _snowman6 = RailShape.NORTH_SOUTH;
        }
        if (_snowman4 || _snowman5) {
            _snowman6 = RailShape.EAST_WEST;
        }
        if (!this.allowCurves) {
            if (_snowman3 && _snowman5 && !_snowman2 && !_snowman4) {
                _snowman6 = RailShape.SOUTH_EAST;
            }
            if (_snowman3 && _snowman4 && !_snowman2 && !_snowman5) {
                _snowman6 = RailShape.SOUTH_WEST;
            }
            if (_snowman2 && _snowman4 && !_snowman3 && !_snowman5) {
                _snowman6 = RailShape.NORTH_WEST;
            }
            if (_snowman2 && _snowman5 && !_snowman3 && !_snowman4) {
                _snowman6 = RailShape.NORTH_EAST;
            }
        }
        if (_snowman6 == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                _snowman6 = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_SOUTH;
            }
        }
        if (_snowman6 == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_WEST;
            }
        }
        if (_snowman6 == null) {
            _snowman6 = RailShape.NORTH_SOUTH;
        }
        this.state = (BlockState)this.state.with(this.block.getShapeProperty(), _snowman6);
        this.world.setBlockState(this.pos, this.state, 3);
    }

    private boolean canConnect(BlockPos pos) {
        RailPlacementHelper railPlacementHelper = this.getNeighboringRail(pos);
        if (railPlacementHelper == null) {
            return false;
        }
        railPlacementHelper.updateNeighborPositions();
        return railPlacementHelper.canConnect(this);
    }

    public RailPlacementHelper updateBlockState(boolean powered, boolean forceUpdate, RailShape railShape) {
        BlockPos blockPos = this.pos.north();
        _snowman = this.pos.south();
        _snowman = this.pos.west();
        _snowman = this.pos.east();
        boolean _snowman2 = this.canConnect(blockPos);
        boolean _snowman3 = this.canConnect(_snowman);
        boolean _snowman4 = this.canConnect(_snowman);
        boolean _snowman5 = this.canConnect(_snowman);
        RailShape _snowman6 = null;
        boolean _snowman7 = _snowman2 || _snowman3;
        boolean bl = _snowman = _snowman4 || _snowman5;
        if (_snowman7 && !_snowman) {
            _snowman6 = RailShape.NORTH_SOUTH;
        }
        if (_snowman && !_snowman7) {
            _snowman6 = RailShape.EAST_WEST;
        }
        boolean _snowman8 = _snowman3 && _snowman5;
        boolean _snowman9 = _snowman3 && _snowman4;
        boolean _snowman10 = _snowman2 && _snowman5;
        boolean bl2 = _snowman = _snowman2 && _snowman4;
        if (!this.allowCurves) {
            if (_snowman8 && !_snowman2 && !_snowman4) {
                _snowman6 = RailShape.SOUTH_EAST;
            }
            if (_snowman9 && !_snowman2 && !_snowman5) {
                _snowman6 = RailShape.SOUTH_WEST;
            }
            if (_snowman && !_snowman3 && !_snowman5) {
                _snowman6 = RailShape.NORTH_WEST;
            }
            if (_snowman10 && !_snowman3 && !_snowman4) {
                _snowman6 = RailShape.NORTH_EAST;
            }
        }
        if (_snowman6 == null) {
            if (_snowman7 && _snowman) {
                _snowman6 = railShape;
            } else if (_snowman7) {
                _snowman6 = RailShape.NORTH_SOUTH;
            } else if (_snowman) {
                _snowman6 = RailShape.EAST_WEST;
            }
            if (!this.allowCurves) {
                if (powered) {
                    if (_snowman8) {
                        _snowman6 = RailShape.SOUTH_EAST;
                    }
                    if (_snowman9) {
                        _snowman6 = RailShape.SOUTH_WEST;
                    }
                    if (_snowman10) {
                        _snowman6 = RailShape.NORTH_EAST;
                    }
                    if (_snowman) {
                        _snowman6 = RailShape.NORTH_WEST;
                    }
                } else {
                    if (_snowman) {
                        _snowman6 = RailShape.NORTH_WEST;
                    }
                    if (_snowman10) {
                        _snowman6 = RailShape.NORTH_EAST;
                    }
                    if (_snowman9) {
                        _snowman6 = RailShape.SOUTH_WEST;
                    }
                    if (_snowman8) {
                        _snowman6 = RailShape.SOUTH_EAST;
                    }
                }
            }
        }
        if (_snowman6 == RailShape.NORTH_SOUTH) {
            if (AbstractRailBlock.isRail(this.world, blockPos.up())) {
                _snowman6 = RailShape.ASCENDING_NORTH;
            }
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_SOUTH;
            }
        }
        if (_snowman6 == RailShape.EAST_WEST) {
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_EAST;
            }
            if (AbstractRailBlock.isRail(this.world, _snowman.up())) {
                _snowman6 = RailShape.ASCENDING_WEST;
            }
        }
        if (_snowman6 == null) {
            _snowman6 = railShape;
        }
        this.computeNeighbors(_snowman6);
        this.state = (BlockState)this.state.with(this.block.getShapeProperty(), _snowman6);
        if (forceUpdate || this.world.getBlockState(this.pos) != this.state) {
            this.world.setBlockState(this.pos, this.state, 3);
            for (int i = 0; i < this.neighbors.size(); ++i) {
                RailPlacementHelper railPlacementHelper = this.getNeighboringRail(this.neighbors.get(i));
                if (railPlacementHelper == null) continue;
                railPlacementHelper.updateNeighborPositions();
                if (!railPlacementHelper.canConnect(this)) continue;
                railPlacementHelper.computeRailShape(this);
            }
        }
        return this;
    }

    public BlockState getBlockState() {
        return this.state;
    }
}

