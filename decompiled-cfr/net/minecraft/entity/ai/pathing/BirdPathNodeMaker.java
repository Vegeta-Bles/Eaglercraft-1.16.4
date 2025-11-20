/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.HashSet;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class BirdPathNodeMaker
extends LandPathNodeMaker {
    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        this.waterPathNodeTypeWeight = entity.getPathfindingPenalty(PathNodeType.WATER);
    }

    @Override
    public void clear() {
        this.entity.setPathfindingPenalty(PathNodeType.WATER, this.waterPathNodeTypeWeight);
        super.clear();
    }

    @Override
    public PathNode getStart() {
        Object _snowman3;
        BlockPos _snowman2;
        int n;
        if (this.canSwim() && this.entity.isTouchingWater()) {
            n = MathHelper.floor(this.entity.getY());
            _snowman2 = new BlockPos.Mutable(this.entity.getX(), (double)n, this.entity.getZ());
            _snowman3 = this.cachedWorld.getBlockState(_snowman2).getBlock();
            while (_snowman3 == Blocks.WATER) {
                ((BlockPos.Mutable)_snowman2).set(this.entity.getX(), ++n, this.entity.getZ());
                _snowman3 = this.cachedWorld.getBlockState(_snowman2).getBlock();
            }
        } else {
            n = MathHelper.floor(this.entity.getY() + 0.5);
        }
        if (this.entity.getPathfindingPenalty((PathNodeType)((Object)(_snowman3 = this.getNodeType(this.entity, (_snowman2 = this.entity.getBlockPos()).getX(), n, _snowman2.getZ())))) < 0.0f) {
            HashSet _snowman4 = Sets.newHashSet();
            _snowman4.add(new BlockPos(this.entity.getBoundingBox().minX, (double)n, this.entity.getBoundingBox().minZ));
            _snowman4.add(new BlockPos(this.entity.getBoundingBox().minX, (double)n, this.entity.getBoundingBox().maxZ));
            _snowman4.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)n, this.entity.getBoundingBox().minZ));
            _snowman4.add(new BlockPos(this.entity.getBoundingBox().maxX, (double)n, this.entity.getBoundingBox().maxZ));
            for (BlockPos blockPos : _snowman4) {
                PathNodeType pathNodeType = this.getNodeType(this.entity, blockPos);
                if (!(this.entity.getPathfindingPenalty(pathNodeType) >= 0.0f)) continue;
                return super.getNode(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            }
        }
        return super.getNode(_snowman2.getX(), n, _snowman2.getZ());
    }

    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return new TargetPathNode(super.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int n = 0;
        PathNode _snowman2 = this.getNode(node.x, node.y, node.z + 1);
        if (this.unvisited(_snowman2)) {
            successors[n++] = _snowman2;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y, node.z))) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y, node.z))) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y, node.z - 1))) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y + 1, node.z))) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y - 1, node.z))) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y + 1, node.z + 1)) && this.isPassable(_snowman2) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y + 1, node.z)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y + 1, node.z)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y + 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y - 1, node.z + 1)) && this.isPassable(_snowman2) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y - 1, node.z)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y - 1, node.z)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x, node.y - 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y, node.z + 1)) && this.isPassable(_snowman2) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y, node.z + 1)) && this.isPassable(_snowman2) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y + 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y + 1, node.z + 1)) && this.isPassable(_snowman) && this.isPassable(_snowman2) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y + 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) & this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y + 1, node.z + 1)) && this.isPassable(_snowman) && this.isPassable(_snowman2) && this.isPassable(_snowman) & this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y - 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x + 1, node.y - 1, node.z + 1)) && this.isPassable(_snowman) && this.isPassable(_snowman2) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y - 1, node.z - 1)) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        if (this.unvisited(_snowman = this.getNode(node.x - 1, node.y - 1, node.z + 1)) && this.isPassable(_snowman) && this.isPassable(_snowman2) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman) && this.isPassable(_snowman)) {
            successors[n++] = _snowman;
        }
        return n;
    }

    private boolean isPassable(@Nullable PathNode node) {
        return node != null && node.penalty >= 0.0f;
    }

    private boolean unvisited(@Nullable PathNode node) {
        return node != null && !node.visited;
    }

    @Override
    @Nullable
    protected PathNode getNode(int x, int y, int z) {
        PathNode pathNode = null;
        PathNodeType _snowman2 = this.getNodeType(this.entity, x, y, z);
        float _snowman3 = this.entity.getPathfindingPenalty(_snowman2);
        if (_snowman3 >= 0.0f) {
            pathNode = super.getNode(x, y, z);
            pathNode.type = _snowman2;
            pathNode.penalty = Math.max(pathNode.penalty, _snowman3);
            if (_snowman2 == PathNodeType.WALKABLE) {
                pathNode.penalty += 1.0f;
            }
        }
        if (_snowman2 == PathNodeType.OPEN || _snowman2 == PathNodeType.WALKABLE) {
            return pathNode;
        }
        return pathNode;
    }

    @Override
    public PathNodeType getNodeType(BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors) {
        EnumSet<PathNodeType> enumSet = EnumSet.noneOf(PathNodeType.class);
        PathNodeType _snowman2 = PathNodeType.BLOCKED;
        BlockPos _snowman3 = mob.getBlockPos();
        _snowman2 = this.findNearbyNodeTypes(world, x, y, z, sizeX, sizeY, sizeZ, canOpenDoors, canEnterOpenDoors, enumSet, _snowman2, _snowman3);
        if (enumSet.contains((Object)PathNodeType.FENCE)) {
            return PathNodeType.FENCE;
        }
        PathNodeType _snowman4 = PathNodeType.BLOCKED;
        for (PathNodeType pathNodeType : enumSet) {
            if (mob.getPathfindingPenalty(pathNodeType) < 0.0f) {
                return pathNodeType;
            }
            if (!(mob.getPathfindingPenalty(pathNodeType) >= mob.getPathfindingPenalty(_snowman4))) continue;
            _snowman4 = pathNodeType;
        }
        if (_snowman2 == PathNodeType.OPEN && mob.getPathfindingPenalty(_snowman4) == 0.0f) {
            return PathNodeType.OPEN;
        }
        return _snowman4;
    }

    @Override
    public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        PathNodeType _snowman2 = BirdPathNodeMaker.getCommonNodeType(world, mutable.set(x, y, z));
        if (_snowman2 == PathNodeType.OPEN && y >= 1) {
            BlockState blockState = world.getBlockState(mutable.set(x, y - 1, z));
            PathNodeType _snowman3 = BirdPathNodeMaker.getCommonNodeType(world, mutable.set(x, y - 1, z));
            if (_snowman3 == PathNodeType.DAMAGE_FIRE || blockState.isOf(Blocks.MAGMA_BLOCK) || _snowman3 == PathNodeType.LAVA || blockState.isIn(BlockTags.CAMPFIRES)) {
                _snowman2 = PathNodeType.DAMAGE_FIRE;
            } else if (_snowman3 == PathNodeType.DAMAGE_CACTUS) {
                _snowman2 = PathNodeType.DAMAGE_CACTUS;
            } else if (_snowman3 == PathNodeType.DAMAGE_OTHER) {
                _snowman2 = PathNodeType.DAMAGE_OTHER;
            } else if (_snowman3 == PathNodeType.COCOA) {
                _snowman2 = PathNodeType.COCOA;
            } else if (_snowman3 == PathNodeType.FENCE) {
                _snowman2 = PathNodeType.FENCE;
            } else {
                PathNodeType pathNodeType = _snowman2 = _snowman3 == PathNodeType.WALKABLE || _snowman3 == PathNodeType.OPEN || _snowman3 == PathNodeType.WATER ? PathNodeType.OPEN : PathNodeType.WALKABLE;
            }
        }
        if (_snowman2 == PathNodeType.WALKABLE || _snowman2 == PathNodeType.OPEN) {
            _snowman2 = BirdPathNodeMaker.getNodeTypeFromNeighbors(world, mutable.set(x, y, z), _snowman2);
        }
        return _snowman2;
    }

    private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
        return this.getNodeType(entity, pos.getX(), pos.getY(), pos.getZ());
    }

    private PathNodeType getNodeType(MobEntity entity, int x, int y, int z) {
        return this.getNodeType(this.cachedWorld, x, y, z, entity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, this.canOpenDoors(), this.canEnterOpenDoors());
    }
}

