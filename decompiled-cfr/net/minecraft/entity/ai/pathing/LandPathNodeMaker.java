/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanMap
 *  it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai.pathing;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.ai.pathing.TargetPathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.chunk.ChunkCache;

public class LandPathNodeMaker
extends PathNodeMaker {
    protected float waterPathNodeTypeWeight;
    private final Long2ObjectMap<PathNodeType> field_25190 = new Long2ObjectOpenHashMap();
    private final Object2BooleanMap<Box> field_25191 = new Object2BooleanOpenHashMap();

    @Override
    public void init(ChunkCache cachedWorld, MobEntity entity) {
        super.init(cachedWorld, entity);
        this.waterPathNodeTypeWeight = entity.getPathfindingPenalty(PathNodeType.WATER);
    }

    @Override
    public void clear() {
        this.entity.setPathfindingPenalty(PathNodeType.WATER, this.waterPathNodeTypeWeight);
        this.field_25190.clear();
        this.field_25191.clear();
        super.clear();
    }

    @Override
    public PathNode getStart() {
        Object _snowman5;
        BlockPos blockPos;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int _snowman2 = MathHelper.floor(this.entity.getY());
        BlockState _snowman3 = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), (double)_snowman2, this.entity.getZ()));
        if (this.entity.canWalkOnFluid(_snowman3.getFluidState().getFluid())) {
            while (this.entity.canWalkOnFluid(_snowman3.getFluidState().getFluid())) {
                _snowman3 = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), (double)(++_snowman2), this.entity.getZ()));
            }
            --_snowman2;
        } else if (this.canSwim() && this.entity.isTouchingWater()) {
            while (_snowman3.getBlock() == Blocks.WATER || _snowman3.getFluidState() == Fluids.WATER.getStill(false)) {
                _snowman3 = this.cachedWorld.getBlockState(mutable.set(this.entity.getX(), (double)(++_snowman2), this.entity.getZ()));
            }
            --_snowman2;
        } else if (this.entity.isOnGround()) {
            _snowman2 = MathHelper.floor(this.entity.getY() + 0.5);
        } else {
            blockPos = this.entity.getBlockPos();
            while ((this.cachedWorld.getBlockState(blockPos).isAir() || this.cachedWorld.getBlockState(blockPos).canPathfindThrough(this.cachedWorld, blockPos, NavigationType.LAND)) && blockPos.getY() > 0) {
                blockPos = blockPos.down();
            }
            _snowman2 = blockPos.up().getY();
        }
        blockPos = this.entity.getBlockPos();
        PathNodeType _snowman4 = this.method_29303(this.entity, blockPos.getX(), _snowman2, blockPos.getZ());
        if (this.entity.getPathfindingPenalty(_snowman4) < 0.0f) {
            _snowman5 = this.entity.getBoundingBox();
            if (this.method_27139(mutable.set(((Box)_snowman5).minX, (double)_snowman2, ((Box)_snowman5).minZ)) || this.method_27139(mutable.set(((Box)_snowman5).minX, (double)_snowman2, ((Box)_snowman5).maxZ)) || this.method_27139(mutable.set(((Box)_snowman5).maxX, (double)_snowman2, ((Box)_snowman5).minZ)) || this.method_27139(mutable.set(((Box)_snowman5).maxX, (double)_snowman2, ((Box)_snowman5).maxZ))) {
                PathNode pathNode = this.method_27137(mutable);
                pathNode.type = this.getNodeType(this.entity, pathNode.getPos());
                pathNode.penalty = this.entity.getPathfindingPenalty(pathNode.type);
                return pathNode;
            }
        }
        _snowman5 = this.getNode(blockPos.getX(), _snowman2, blockPos.getZ());
        ((PathNode)_snowman5).type = this.getNodeType(this.entity, ((PathNode)_snowman5).getPos());
        ((PathNode)_snowman5).penalty = this.entity.getPathfindingPenalty(((PathNode)_snowman5).type);
        return _snowman5;
    }

    private boolean method_27139(BlockPos blockPos) {
        PathNodeType pathNodeType = this.getNodeType(this.entity, blockPos);
        return this.entity.getPathfindingPenalty(pathNodeType) >= 0.0f;
    }

    @Override
    public TargetPathNode getNode(double x, double y, double z) {
        return new TargetPathNode(this.getNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z)));
    }

    @Override
    public int getSuccessors(PathNode[] successors, PathNode node) {
        int n = 0;
        _snowman = 0;
        PathNodeType _snowman2 = this.method_29303(this.entity, node.x, node.y + 1, node.z);
        PathNodeType _snowman3 = this.method_29303(this.entity, node.x, node.y, node.z);
        if (this.entity.getPathfindingPenalty(_snowman2) >= 0.0f && _snowman3 != PathNodeType.STICKY_HONEY) {
            _snowman = MathHelper.floor(Math.max(1.0f, this.entity.stepHeight));
        }
        if (this.isValidDiagonalSuccessor(_snowman = this.getPathNode(node.x, node.y, node.z + 1, _snowman, _snowman = LandPathNodeMaker.getFeetY(this.cachedWorld, new BlockPos(node.x, node.y, node.z)), Direction.SOUTH, _snowman3), node)) {
            successors[n++] = _snowman;
        }
        if (this.isValidDiagonalSuccessor(_snowman = this.getPathNode(node.x - 1, node.y, node.z, _snowman, _snowman, Direction.WEST, _snowman3), node)) {
            successors[n++] = _snowman;
        }
        if (this.isValidDiagonalSuccessor(_snowman = this.getPathNode(node.x + 1, node.y, node.z, _snowman, _snowman, Direction.EAST, _snowman3), node)) {
            successors[n++] = _snowman;
        }
        if (this.isValidDiagonalSuccessor(_snowman = this.getPathNode(node.x, node.y, node.z - 1, _snowman, _snowman, Direction.NORTH, _snowman3), node)) {
            successors[n++] = _snowman;
        }
        if (this.method_29579(node, _snowman, _snowman, _snowman = this.getPathNode(node.x - 1, node.y, node.z - 1, _snowman, _snowman, Direction.NORTH, _snowman3))) {
            successors[n++] = _snowman;
        }
        if (this.method_29579(node, _snowman, _snowman, _snowman = this.getPathNode(node.x + 1, node.y, node.z - 1, _snowman, _snowman, Direction.NORTH, _snowman3))) {
            successors[n++] = _snowman;
        }
        if (this.method_29579(node, _snowman, _snowman, _snowman = this.getPathNode(node.x - 1, node.y, node.z + 1, _snowman, _snowman, Direction.SOUTH, _snowman3))) {
            successors[n++] = _snowman;
        }
        if (this.method_29579(node, _snowman, _snowman, _snowman = this.getPathNode(node.x + 1, node.y, node.z + 1, _snowman, _snowman, Direction.SOUTH, _snowman3))) {
            successors[n++] = _snowman;
        }
        return n;
    }

    private boolean isValidDiagonalSuccessor(PathNode node, PathNode successor1) {
        return node != null && !node.visited && (node.penalty >= 0.0f || successor1.penalty < 0.0f);
    }

    private boolean method_29579(PathNode pathNode, @Nullable PathNode pathNode2, @Nullable PathNode pathNode3, @Nullable PathNode pathNode4) {
        if (pathNode4 == null || pathNode3 == null || pathNode2 == null) {
            return false;
        }
        if (pathNode4.visited) {
            return false;
        }
        if (pathNode3.y > pathNode.y || pathNode2.y > pathNode.y) {
            return false;
        }
        if (pathNode2.type == PathNodeType.WALKABLE_DOOR || pathNode3.type == PathNodeType.WALKABLE_DOOR || pathNode4.type == PathNodeType.WALKABLE_DOOR) {
            return false;
        }
        boolean bl = pathNode3.type == PathNodeType.FENCE && pathNode2.type == PathNodeType.FENCE && (double)this.entity.getWidth() < 0.5;
        return pathNode4.penalty >= 0.0f && (pathNode3.y < pathNode.y || pathNode3.penalty >= 0.0f || bl) && (pathNode2.y < pathNode.y || pathNode2.penalty >= 0.0f || bl);
    }

    private boolean method_29578(PathNode pathNode) {
        Vec3d vec3d = new Vec3d((double)pathNode.x - this.entity.getX(), (double)pathNode.y - this.entity.getY(), (double)pathNode.z - this.entity.getZ());
        Box _snowman2 = this.entity.getBoundingBox();
        int _snowman3 = MathHelper.ceil(vec3d.length() / _snowman2.getAverageSideLength());
        vec3d = vec3d.multiply(1.0f / (float)_snowman3);
        for (int i = 1; i <= _snowman3; ++i) {
            if (!this.method_29304(_snowman2 = _snowman2.offset(vec3d))) continue;
            return false;
        }
        return true;
    }

    public static double getFeetY(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        VoxelShape _snowman2 = world.getBlockState(blockPos).getCollisionShape(world, blockPos);
        return (double)blockPos.getY() + (_snowman2.isEmpty() ? 0.0 : _snowman2.getMax(Direction.Axis.Y));
    }

    @Nullable
    private PathNode getPathNode(int x, int y, int z, int maxYStep, double prevFeetY, Direction direction, PathNodeType pathNodeType) {
        PathNode pathNode = null;
        BlockPos.Mutable _snowman2 = new BlockPos.Mutable();
        double _snowman3 = LandPathNodeMaker.getFeetY(this.cachedWorld, _snowman2.set(x, y, z));
        if (_snowman3 - prevFeetY > 1.125) {
            return null;
        }
        PathNodeType _snowman4 = this.method_29303(this.entity, x, y, z);
        float _snowman5 = this.entity.getPathfindingPenalty(_snowman4);
        double _snowman6 = (double)this.entity.getWidth() / 2.0;
        if (_snowman5 >= 0.0f) {
            pathNode = this.getNode(x, y, z);
            pathNode.type = _snowman4;
            pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
        }
        if (pathNodeType == PathNodeType.FENCE && pathNode != null && pathNode.penalty >= 0.0f && !this.method_29578(pathNode)) {
            pathNode = null;
        }
        if (_snowman4 == PathNodeType.WALKABLE) {
            return pathNode;
        }
        if ((pathNode == null || pathNode.penalty < 0.0f) && maxYStep > 0 && _snowman4 != PathNodeType.FENCE && _snowman4 != PathNodeType.UNPASSABLE_RAIL && _snowman4 != PathNodeType.TRAPDOOR && (pathNode = this.getPathNode(x, y + 1, z, maxYStep - 1, prevFeetY, direction, pathNodeType)) != null && (pathNode.type == PathNodeType.OPEN || pathNode.type == PathNodeType.WALKABLE) && this.entity.getWidth() < 1.0f && this.method_29304(_snowman = new Box((_snowman = (double)(x - direction.getOffsetX()) + 0.5) - _snowman6, LandPathNodeMaker.getFeetY(this.cachedWorld, _snowman2.set(_snowman, (double)(y + 1), _snowman = (double)(z - direction.getOffsetZ()) + 0.5)) + 0.001, _snowman - _snowman6, _snowman + _snowman6, (double)this.entity.getHeight() + LandPathNodeMaker.getFeetY(this.cachedWorld, _snowman2.set((double)pathNode.x, (double)pathNode.y, (double)pathNode.z)) - 0.002, _snowman + _snowman6))) {
            pathNode = null;
        }
        if (_snowman4 == PathNodeType.WATER && !this.canSwim()) {
            if (this.method_29303(this.entity, x, y - 1, z) != PathNodeType.WATER) {
                return pathNode;
            }
            while (y > 0) {
                if ((_snowman4 = this.method_29303(this.entity, x, --y, z)) == PathNodeType.WATER) {
                    pathNode = this.getNode(x, y, z);
                    pathNode.type = _snowman4;
                    pathNode.penalty = Math.max(pathNode.penalty, this.entity.getPathfindingPenalty(_snowman4));
                    continue;
                }
                return pathNode;
            }
        }
        if (_snowman4 == PathNodeType.OPEN) {
            int n = 0;
            _snowman = y;
            while (_snowman4 == PathNodeType.OPEN) {
                if (--y < 0) {
                    PathNode pathNode2 = this.getNode(x, _snowman, z);
                    pathNode2.type = PathNodeType.BLOCKED;
                    pathNode2.penalty = -1.0f;
                    return pathNode2;
                }
                if (n++ >= this.entity.getSafeFallDistance()) {
                    PathNode pathNode3 = this.getNode(x, y, z);
                    pathNode3.type = PathNodeType.BLOCKED;
                    pathNode3.penalty = -1.0f;
                    return pathNode3;
                }
                _snowman4 = this.method_29303(this.entity, x, y, z);
                _snowman5 = this.entity.getPathfindingPenalty(_snowman4);
                if (_snowman4 != PathNodeType.OPEN && _snowman5 >= 0.0f) {
                    pathNode = this.getNode(x, y, z);
                    pathNode.type = _snowman4;
                    pathNode.penalty = Math.max(pathNode.penalty, _snowman5);
                    break;
                }
                if (!(_snowman5 < 0.0f)) continue;
                PathNode pathNode4 = this.getNode(x, y, z);
                pathNode4.type = PathNodeType.BLOCKED;
                pathNode4.penalty = -1.0f;
                return pathNode4;
            }
        }
        if (_snowman4 == PathNodeType.FENCE) {
            pathNode = this.getNode(x, y, z);
            pathNode.visited = true;
            pathNode.type = _snowman4;
            pathNode.penalty = _snowman4.getDefaultPenalty();
        }
        return pathNode;
    }

    private boolean method_29304(Box box) {
        return (Boolean)this.field_25191.computeIfAbsent((Object)box, box2 -> !this.cachedWorld.isSpaceEmpty(this.entity, box));
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
        if (enumSet.contains((Object)PathNodeType.UNPASSABLE_RAIL)) {
            return PathNodeType.UNPASSABLE_RAIL;
        }
        PathNodeType _snowman4 = PathNodeType.BLOCKED;
        for (PathNodeType pathNodeType : enumSet) {
            if (mob.getPathfindingPenalty(pathNodeType) < 0.0f) {
                return pathNodeType;
            }
            if (!(mob.getPathfindingPenalty(pathNodeType) >= mob.getPathfindingPenalty(_snowman4))) continue;
            _snowman4 = pathNodeType;
        }
        if (_snowman2 == PathNodeType.OPEN && mob.getPathfindingPenalty(_snowman4) == 0.0f && sizeX <= 1) {
            return PathNodeType.OPEN;
        }
        return _snowman4;
    }

    public PathNodeType findNearbyNodeTypes(BlockView world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, boolean canOpenDoors, boolean canEnterOpenDoors, EnumSet<PathNodeType> nearbyTypes, PathNodeType type, BlockPos pos) {
        for (int i = 0; i < sizeX; ++i) {
            for (_snowman = 0; _snowman < sizeY; ++_snowman) {
                for (_snowman = 0; _snowman < sizeZ; ++_snowman) {
                    _snowman = i + x;
                    _snowman = _snowman + y;
                    _snowman = _snowman + z;
                    PathNodeType pathNodeType = this.getDefaultNodeType(world, _snowman, _snowman, _snowman);
                    pathNodeType = this.adjustNodeType(world, canOpenDoors, canEnterOpenDoors, pos, pathNodeType);
                    if (i == 0 && _snowman == 0 && _snowman == 0) {
                        type = pathNodeType;
                    }
                    nearbyTypes.add(pathNodeType);
                }
            }
        }
        return type;
    }

    protected PathNodeType adjustNodeType(BlockView world, boolean canOpenDoors, boolean canEnterOpenDoors, BlockPos pos, PathNodeType type) {
        if (type == PathNodeType.DOOR_WOOD_CLOSED && canOpenDoors && canEnterOpenDoors) {
            type = PathNodeType.WALKABLE_DOOR;
        }
        if (type == PathNodeType.DOOR_OPEN && !canEnterOpenDoors) {
            type = PathNodeType.BLOCKED;
        }
        if (type == PathNodeType.RAIL && !(world.getBlockState(pos).getBlock() instanceof AbstractRailBlock) && !(world.getBlockState(pos.down()).getBlock() instanceof AbstractRailBlock)) {
            type = PathNodeType.UNPASSABLE_RAIL;
        }
        if (type == PathNodeType.LEAVES) {
            type = PathNodeType.BLOCKED;
        }
        return type;
    }

    private PathNodeType getNodeType(MobEntity entity, BlockPos pos) {
        return this.method_29303(entity, pos.getX(), pos.getY(), pos.getZ());
    }

    private PathNodeType method_29303(MobEntity mobEntity, int n, int n2, int n3) {
        return (PathNodeType)((Object)this.field_25190.computeIfAbsent(BlockPos.asLong(n, n2, n3), l -> this.getNodeType(this.cachedWorld, n, n2, n3, mobEntity, this.entityBlockXSize, this.entityBlockYSize, this.entityBlockZSize, this.canOpenDoors(), this.canEnterOpenDoors())));
    }

    @Override
    public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
        return LandPathNodeMaker.getLandNodeType(world, new BlockPos.Mutable(x, y, z));
    }

    public static PathNodeType getLandNodeType(BlockView blockView2, BlockPos.Mutable mutable) {
        int n = mutable.getX();
        _snowman = mutable.getY();
        _snowman = mutable.getZ();
        PathNodeType _snowman2 = LandPathNodeMaker.getCommonNodeType(blockView2, mutable);
        if (_snowman2 == PathNodeType.OPEN && _snowman >= 1) {
            PathNodeType pathNodeType = LandPathNodeMaker.getCommonNodeType(blockView2, mutable.set(n, _snowman - 1, _snowman));
            PathNodeType pathNodeType2 = _snowman2 = pathNodeType == PathNodeType.WALKABLE || pathNodeType == PathNodeType.OPEN || pathNodeType == PathNodeType.WATER || pathNodeType == PathNodeType.LAVA ? PathNodeType.OPEN : PathNodeType.WALKABLE;
            if (pathNodeType == PathNodeType.DAMAGE_FIRE) {
                _snowman2 = PathNodeType.DAMAGE_FIRE;
            }
            if (pathNodeType == PathNodeType.DAMAGE_CACTUS) {
                _snowman2 = PathNodeType.DAMAGE_CACTUS;
            }
            if (pathNodeType == PathNodeType.DAMAGE_OTHER) {
                _snowman2 = PathNodeType.DAMAGE_OTHER;
            }
            if (pathNodeType == PathNodeType.STICKY_HONEY) {
                _snowman2 = PathNodeType.STICKY_HONEY;
            }
        }
        if (_snowman2 == PathNodeType.WALKABLE) {
            BlockView blockView2;
            _snowman2 = LandPathNodeMaker.getNodeTypeFromNeighbors(blockView2, mutable.set(n, _snowman, _snowman), _snowman2);
        }
        return _snowman2;
    }

    public static PathNodeType getNodeTypeFromNeighbors(BlockView blockView, BlockPos.Mutable mutable, PathNodeType pathNodeType2) {
        PathNodeType pathNodeType2;
        int n = mutable.getX();
        _snowman = mutable.getY();
        _snowman = mutable.getZ();
        for (_snowman = -1; _snowman <= 1; ++_snowman) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    if (_snowman == 0 && _snowman == 0) continue;
                    mutable.set(n + _snowman, _snowman + _snowman, _snowman + _snowman);
                    BlockState blockState = blockView.getBlockState(mutable);
                    if (blockState.isOf(Blocks.CACTUS)) {
                        return PathNodeType.DANGER_CACTUS;
                    }
                    if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) {
                        return PathNodeType.DANGER_OTHER;
                    }
                    if (LandPathNodeMaker.method_27138(blockState)) {
                        return PathNodeType.DANGER_FIRE;
                    }
                    if (!blockView.getFluidState(mutable).isIn(FluidTags.WATER)) continue;
                    return PathNodeType.WATER_BORDER;
                }
            }
        }
        return pathNodeType2;
    }

    protected static PathNodeType getCommonNodeType(BlockView blockView, BlockPos blockPos) {
        BlockState blockState = blockView.getBlockState(blockPos);
        Block _snowman2 = blockState.getBlock();
        Material _snowman3 = blockState.getMaterial();
        if (blockState.isAir()) {
            return PathNodeType.OPEN;
        }
        if (blockState.isIn(BlockTags.TRAPDOORS) || blockState.isOf(Blocks.LILY_PAD)) {
            return PathNodeType.TRAPDOOR;
        }
        if (blockState.isOf(Blocks.CACTUS)) {
            return PathNodeType.DAMAGE_CACTUS;
        }
        if (blockState.isOf(Blocks.SWEET_BERRY_BUSH)) {
            return PathNodeType.DAMAGE_OTHER;
        }
        if (blockState.isOf(Blocks.HONEY_BLOCK)) {
            return PathNodeType.STICKY_HONEY;
        }
        if (blockState.isOf(Blocks.COCOA)) {
            return PathNodeType.COCOA;
        }
        FluidState _snowman4 = blockView.getFluidState(blockPos);
        if (_snowman4.isIn(FluidTags.WATER)) {
            return PathNodeType.WATER;
        }
        if (_snowman4.isIn(FluidTags.LAVA)) {
            return PathNodeType.LAVA;
        }
        if (LandPathNodeMaker.method_27138(blockState)) {
            return PathNodeType.DAMAGE_FIRE;
        }
        if (DoorBlock.isWoodenDoor(blockState) && !blockState.get(DoorBlock.OPEN).booleanValue()) {
            return PathNodeType.DOOR_WOOD_CLOSED;
        }
        if (_snowman2 instanceof DoorBlock && _snowman3 == Material.METAL && !blockState.get(DoorBlock.OPEN).booleanValue()) {
            return PathNodeType.DOOR_IRON_CLOSED;
        }
        if (_snowman2 instanceof DoorBlock && blockState.get(DoorBlock.OPEN).booleanValue()) {
            return PathNodeType.DOOR_OPEN;
        }
        if (_snowman2 instanceof AbstractRailBlock) {
            return PathNodeType.RAIL;
        }
        if (_snowman2 instanceof LeavesBlock) {
            return PathNodeType.LEAVES;
        }
        if (_snowman2.isIn(BlockTags.FENCES) || _snowman2.isIn(BlockTags.WALLS) || _snowman2 instanceof FenceGateBlock && !blockState.get(FenceGateBlock.OPEN).booleanValue()) {
            return PathNodeType.FENCE;
        }
        if (!blockState.canPathfindThrough(blockView, blockPos, NavigationType.LAND)) {
            return PathNodeType.BLOCKED;
        }
        return PathNodeType.OPEN;
    }

    private static boolean method_27138(BlockState blockState) {
        return blockState.isIn(BlockTags.FIRE) || blockState.isOf(Blocks.LAVA) || blockState.isOf(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(blockState);
    }
}

