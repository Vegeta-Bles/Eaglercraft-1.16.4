/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class AmphibiousPathNodeMaker
extends LandPathNodeMaker {
    private float oldWalkablePenalty;
    private float oldWaterBorderPenalty;

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        entity.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.oldWalkablePenalty = entity.getPathfindingPenalty(PathNodeType.WALKABLE);
        entity.setPathfindingPenalty(PathNodeType.WALKABLE, 6.0f);
        this.oldWaterBorderPenalty = entity.getPathfindingPenalty(PathNodeType.WATER_BORDER);
        entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, 4.0f);
    }

    @Override
    public void clear() {
        this.entity.setPathfindingPenalty(PathNodeType.WALKABLE, this.oldWalkablePenalty);
        this.entity.setPathfindingPenalty(PathNodeType.WATER_BORDER, this.oldWaterBorderPenalty);
        super.clear();
    }

    @Override
    public PathNode getStart() {
        return this.getNode(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY + 0.5), MathHelper.floor(this.entity.getBoundingBox().minZ));
    }

    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return new TargetPathNode(this.getNode(MathHelper.floor(x), MathHelper.floor(y + 0.5), MathHelper.floor(z)));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int n = 0;
        boolean _snowman2 = true;
        BlockPos _snowman3 = new BlockPos(node.x, node.y, node.z);
        double _snowman4 = this.getFeetY(_snowman3);
        PathNode _snowman5 = this.getPathNode(node.x, node.y, node.z + 1, 1, _snowman4);
        PathNode _snowman6 = this.getPathNode(node.x - 1, node.y, node.z, 1, _snowman4);
        PathNode _snowman7 = this.getPathNode(node.x + 1, node.y, node.z, 1, _snowman4);
        PathNode _snowman8 = this.getPathNode(node.x, node.y, node.z - 1, 1, _snowman4);
        PathNode _snowman9 = this.getPathNode(node.x, node.y + 1, node.z, 0, _snowman4);
        PathNode _snowman10 = this.getPathNode(node.x, node.y - 1, node.z, 1, _snowman4);
        if (_snowman5 != null && !_snowman5.visited) {
            successors[n++] = _snowman5;
        }
        if (_snowman6 != null && !_snowman6.visited) {
            successors[n++] = _snowman6;
        }
        if (_snowman7 != null && !_snowman7.visited) {
            successors[n++] = _snowman7;
        }
        if (_snowman8 != null && !_snowman8.visited) {
            successors[n++] = _snowman8;
        }
        if (_snowman9 != null && !_snowman9.visited) {
            successors[n++] = _snowman9;
        }
        if (_snowman10 != null && !_snowman10.visited) {
            successors[n++] = _snowman10;
        }
        boolean _snowman11 = _snowman8 == null || _snowman8.type == PathNodeType.OPEN || _snowman8.penalty != 0.0f;
        boolean _snowman12 = _snowman5 == null || _snowman5.type == PathNodeType.OPEN || _snowman5.penalty != 0.0f;
        boolean _snowman13 = _snowman7 == null || _snowman7.type == PathNodeType.OPEN || _snowman7.penalty != 0.0f;
        boolean bl = _snowman = _snowman6 == null || _snowman6.type == PathNodeType.OPEN || _snowman6.penalty != 0.0f;
        if (_snowman11 && _snowman && (_snowman = this.getPathNode(node.x - 1, node.y, node.z - 1, 1, _snowman4)) != null && !_snowman.visited) {
            successors[n++] = _snowman;
        }
        if (_snowman11 && _snowman13 && (_snowman = this.getPathNode(node.x + 1, node.y, node.z - 1, 1, _snowman4)) != null && !_snowman.visited) {
            successors[n++] = _snowman;
        }
        if (_snowman12 && _snowman && (_snowman = this.getPathNode(node.x - 1, node.y, node.z + 1, 1, _snowman4)) != null && !_snowman.visited) {
            successors[n++] = _snowman;
        }
        if (_snowman12 && _snowman13 && (_snowman = this.getPathNode(node.x + 1, node.y, node.z + 1, 1, _snowman4)) != null && !_snowman.visited) {
            successors[n++] = _snowman;
        }
        return n;
    }

    private double getFeetY(BlockPos pos) {
        if (!this.entity.isTouchingWater()) {
            BlockPos blockPos = pos.down();
            VoxelShape _snowman2 = this.cachedWorld.getBlockState(blockPos).getCollisionShape(this.cachedWorld, blockPos);
            return (double)blockPos.getY() + (_snowman2.isEmpty() ? 0.0 : _snowman2.getMax(Direction.Axis.Y));
        }
        return (double)pos.getY() + 0.5;
    }

    @Nullable
    private PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY) {
        PathNode pathNode = null;
        BlockPos _snowman2 = new BlockPos(x, y, z);
        double _snowman3 = this.getFeetY(_snowman2);
        if (_snowman3 - prevFeetY > 1.125) {
            return null;
        }
        PathNodeType _snowman4 = this.getNodeType(this.cachedWorld, x, y, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false);
        float _snowman5 = this.entity.getPathfindingPenalty(_snowman4);
        double _snowman6 = (double)this.entity.getWidth() / 2.0;
        if (_snowman5 >= 0.0f) {
            pathNode = this.getNode(x, y, z);
            pathNode.type = _snowman4;
            pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
        }
        if (_snowman4 == PathNodeType.WATER || _snowman4 == PathNodeType.WALKABLE) {
            if (y < this.entity.world.getSeaLevel() - 10 && pathNode != null) {
                pathNode.penalty += 1.0f;
            }
            return pathNode;
        }
        if (pathNode == null && maxYStep > 0 && _snowman4 != PathNodeType.FENCE && _snowman4 != PathNodeType.UNPASSABLE_RAIL && _snowman4 != PathNodeType.TRAPDOOR) {
            pathNode = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY);
        }
        if (_snowman4 == PathNodeType.OPEN) {
            Box box = new Box((double)x - _snowman6 + 0.5, (double)y + 0.001, (double)z - _snowman6 + 0.5, (double)x + _snowman6 + 0.5, (float)y + this.entity.getHeight(), (double)z + _snowman6 + 0.5);
            if (!this.entity.world.isSpaceEmpty(this.entity, box)) {
                return null;
            }
            PathNodeType _snowman7 = this.getNodeType(this.cachedWorld, x, y - 1, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false);
            if (_snowman7 == PathNodeType.BLOCKED) {
                pathNode = this.getNode(x, y, z);
                pathNode.type = PathNodeType.WALKABLE;
                pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
                return pathNode;
            }
            if (_snowman7 == PathNodeType.WATER) {
                pathNode = this.getNode(x, y, z);
                pathNode.type = PathNodeType.WATER;
                pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
                return pathNode;
            }
            int n = 0;
            while (y > 0 && _snowman4 == PathNodeType.OPEN) {
                --y;
                if (n++ >= this.entity.getSafeFallDistance()) {
                    return null;
                }
                _snowman4 = this.getNodeType(this.cachedWorld, x, y, z, this.entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, false, false);
                _snowman5 = this.entity.getPathfindingPenalty(_snowman4);
                if (_snowman4 != PathNodeType.OPEN && _snowman5 >= 0.0f) {
                    pathNode = this.getNode(x, y, z);
                    pathNode.type = _snowman4;
                    pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
                    break;
                }
                if (!(_snowman5 < 0.0f)) continue;
                return null;
            }
        }
        return pathNode;
    }

    @Override
    protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
        if (type == PathNodeType.RAIL && !(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof AbstractRailBlock)) {
            type = PathNodeType.UNPASSABLE_RAIL;
        }
        if (type == PathNodeType.DOOR_OPEN || type == PathNodeType.DOOR_WOOD_CLOSED || type == PathNodeType.DOOR_IRON_CLOSED) {
            type = PathNodeType.BLOCKED;
        }
        if (type == PathNodeType.LEAVES) {
            type = PathNodeType.BLOCKED;
        }
        return type;
    }

    @Override
    public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType _snowman2 = AmphibiousPathNodeMaker.getCommonNodeType(world, mutable.set(x, y, z));
        if (_snowman2 == PathNodeType.WATER) {
            for (Direction direction : Direction.values()) {
                PathNodeType pathNodeType = AmphibiousPathNodeMaker.getCommonNodeType(world, mutable.set(x, y, z).move(direction));
                if (pathNodeType != PathNodeType.BLOCKED) continue;
                return PathNodeType.WATER_BORDER;
            }
            return PathNodeType.WATER;
        }
        if (_snowman2 == PathNodeType.OPEN && y >= 1) {
            BlockState blockState = world.getBlockState(new BlockPos(x, y - 1, z));
            PathNodeType _snowman3 = AmphibiousPathNodeMaker.getCommonNodeType(world, mutable.set(x, y - 1, z));
            _snowman2 = _snowman3 == PathNodeType.WALKABLE || _snowman3 == PathNodeType.OPEN || _snowman3 == PathNodeType.LAVA ? PathNodeType.OPEN : PathNodeType.WALKABLE;
            if (_snowman3 == PathNodeType.DAMAGE_FIRE || blockState.isOf(Blocks.MAGMA_BLOCK) || blockState.isIn(BlockTags.CAMPFIRES)) {
                _snowman2 = PathNodeType.DAMAGE_FIRE;
            }
            if (_snowman3 == PathNodeType.DAMAGE_CACTUS) {
                _snowman2 = PathNodeType.DAMAGE_CACTUS;
            }
            if (_snowman3 == PathNodeType.DAMAGE_OTHER) {
                _snowman2 = PathNodeType.DAMAGE_OTHER;
            }
        }
        if (_snowman2 == PathNodeType.WALKABLE) {
            _snowman2 = AmphibiousPathNodeMaker.getNodeTypeFromNeighbors(world, mutable.set(x, y, z), _snowman2);
        }
        return _snowman2;
    }
}

