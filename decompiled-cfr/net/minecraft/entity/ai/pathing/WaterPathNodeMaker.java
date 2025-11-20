/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;

public class WaterPathNodeMaker
extends PathNodeMaker {
    private final boolean canJumpOutOfWater;

    public WaterPathNodeMaker(boolean canJumpOutOfWater) {
        this.canJumpOutOfWater = canJumpOutOfWater;
    }

    @Override
    public PathNode getStart() {
        return super.getNode(MathHelper.floor(this.entity.getBoundingBox().minX), MathHelper.floor(this.entity.getBoundingBox().minY + 0.5), MathHelper.floor(this.entity.getBoundingBox().minZ));
    }

    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return new TargetPathNode(super.getNode(MathHelper.floor(x - (double)(this.entity.getWidth() / 2.0f)), MathHelper.floor(y + 0.5), MathHelper.floor(z - (double)(this.entity.getWidth() / 2.0f))));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int n = 0;
        for (Direction direction : Direction.values()) {
            PathNode pathNode = this.getPathNodeInWater(node.x + direction.getOffsetX(), node.y + direction.getOffsetY(), node.z + direction.getOffsetZ());
            if (pathNode == null || pathNode.visited) continue;
            successors[n++] = pathNode;
        }
        return n;
    }

    @Override
    public PathNodeType getNodeType(BlockView world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors) {
        return this.getDefaultNodeType(world, x, y, z);
    }

    @Override
    public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        FluidState _snowman2 = world.getFluidState(blockPos);
        BlockState _snowman3 = world.getBlockState(blockPos);
        if (_snowman2.isEmpty() && _snowman3.canPathfindThrough(world, blockPos.down(), NavigationType.WATER) && _snowman3.isAir()) {
            return PathNodeType.BREACH;
        }
        if (!_snowman2.isIn(FluidTags.WATER) || !_snowman3.canPathfindThrough(world, blockPos, NavigationType.WATER)) {
            return PathNodeType.BLOCKED;
        }
        return PathNodeType.WATER;
    }

    @Nullable
    private PathNode getPathNodeInWater(int x, int y, int z) {
        PathNodeType pathNodeType = this.getNodeType(x, y, z);
        if (this.canJumpOutOfWater && pathNodeType == PathNodeType.BREACH || pathNodeType == PathNodeType.WATER) {
            return this.getNode(x, y, z);
        }
        return null;
    }

    @Override
    @Nullable
    protected PathNode getNode(int x, int y, int z) {
        PathNode pathNode = null;
        PathNodeType _snowman2 = this.getDefaultNodeType(this.entity.world, x, y, z);
        float _snowman3 = this.entity.getPathfindingPenalty(_snowman2);
        if (_snowman3 >= 0.0f) {
            pathNode = super.getNode(x, y, z);
            pathNode.type = _snowman2;
            pathNode.penalty = Math.max(pathNode.penalty, _snowman3);
            if (this.cachedWorld.getFluidState(new BlockPos(x, y, z)).isEmpty()) {
                pathNode.penalty += 8.0f;
            }
        }
        if (_snowman2 == PathNodeType.OPEN) {
            return pathNode;
        }
        return pathNode;
    }

    private PathNodeType getNodeType(int x, int y, int z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = x; i < x + this.entityBlockXSize; ++i) {
            for (_snowman = y; _snowman < y + this.entityBlockYSize; ++_snowman) {
                for (_snowman = z; _snowman < z + this.entityBlockZSize; ++_snowman) {
                    FluidState fluidState = this.cachedWorld.getFluidState(mutable.set(i, _snowman, _snowman));
                    BlockState _snowman2 = this.cachedWorld.getBlockState(mutable.set(i, _snowman, _snowman));
                    if (fluidState.isEmpty() && _snowman2.canPathfindThrough(this.cachedWorld, (BlockPos)mutable.down(), NavigationType.WATER) && _snowman2.isAir()) {
                        return PathNodeType.BREACH;
                    }
                    if (fluidState.isIn(FluidTags.WATER)) continue;
                    return PathNodeType.BLOCKED;
                }
            }
        }
        BlockState blockState = this.cachedWorld.getBlockState(mutable);
        if (blockState.canPathfindThrough(this.cachedWorld, mutable, NavigationType.WATER)) {
            return PathNodeType.WATER;
        }
        return PathNodeType.BLOCKED;
    }
}

