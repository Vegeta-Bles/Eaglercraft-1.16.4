/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.structure;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.EmptyBlockView;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class Structure {
    private final List<PalettedBlockInfoList> blockInfoLists = Lists.newArrayList();
    private final List<StructureEntityInfo> entities = Lists.newArrayList();
    private BlockPos size = BlockPos.ORIGIN;
    private String author = "?";

    public BlockPos getSize() {
        return this.size;
    }

    public void setAuthor(String name) {
        this.author = name;
    }

    public String getAuthor() {
        return this.author;
    }

    public void saveFromWorld(World world, BlockPos start, BlockPos size, boolean includeEntities, @Nullable Block ignoredBlock) {
        if (size.getX() < 1 || size.getY() < 1 || size.getZ() < 1) {
            return;
        }
        BlockPos blockPos = start.add(size).add(-1, -1, -1);
        ArrayList _snowman2 = Lists.newArrayList();
        ArrayList _snowman3 = Lists.newArrayList();
        ArrayList _snowman4 = Lists.newArrayList();
        _snowman = new BlockPos(Math.min(start.getX(), blockPos.getX()), Math.min(start.getY(), blockPos.getY()), Math.min(start.getZ(), blockPos.getZ()));
        _snowman = new BlockPos(Math.max(start.getX(), blockPos.getX()), Math.max(start.getY(), blockPos.getY()), Math.max(start.getZ(), blockPos.getZ()));
        this.size = size;
        for (BlockPos blockPos2 : BlockPos.iterate(_snowman, _snowman)) {
            StructureBlockInfo _snowman6;
            _snowman = blockPos2.subtract(_snowman);
            BlockState blockState = world.getBlockState(blockPos2);
            if (ignoredBlock != null && ignoredBlock == blockState.getBlock()) continue;
            BlockEntity _snowman5 = world.getBlockEntity(blockPos2);
            if (_snowman5 != null) {
                CompoundTag compoundTag = _snowman5.toTag(new CompoundTag());
                compoundTag.remove("x");
                compoundTag.remove("y");
                compoundTag.remove("z");
                _snowman6 = new StructureBlockInfo(_snowman, blockState, compoundTag.copy());
            } else {
                _snowman6 = new StructureBlockInfo(_snowman, blockState, null);
            }
            Structure.method_28054(_snowman6, _snowman2, _snowman3, _snowman4);
        }
        List<StructureBlockInfo> list = Structure.method_28055(_snowman2, _snowman3, _snowman4);
        this.blockInfoLists.clear();
        this.blockInfoLists.add(new PalettedBlockInfoList(list));
        if (includeEntities) {
            this.addEntitiesFromWorld(world, _snowman, _snowman.add(1, 1, 1));
        } else {
            this.entities.clear();
        }
    }

    private static void method_28054(StructureBlockInfo structureBlockInfo, List<StructureBlockInfo> list, List<StructureBlockInfo> list2, List<StructureBlockInfo> list3) {
        if (structureBlockInfo.tag != null) {
            list2.add(structureBlockInfo);
        } else if (!structureBlockInfo.state.getBlock().hasDynamicBounds() && structureBlockInfo.state.isFullCube(EmptyBlockView.INSTANCE, BlockPos.ORIGIN)) {
            list.add(structureBlockInfo);
        } else {
            list3.add(structureBlockInfo);
        }
    }

    private static List<StructureBlockInfo> method_28055(List<StructureBlockInfo> list, List<StructureBlockInfo> list2, List<StructureBlockInfo> list3) {
        Comparator<StructureBlockInfo> comparator = Comparator.comparingInt(structureBlockInfo -> structureBlockInfo.pos.getY()).thenComparingInt(structureBlockInfo -> structureBlockInfo.pos.getX()).thenComparingInt(structureBlockInfo -> structureBlockInfo.pos.getZ());
        list.sort(comparator);
        list3.sort(comparator);
        list2.sort(comparator);
        ArrayList _snowman2 = Lists.newArrayList();
        _snowman2.addAll(list);
        _snowman2.addAll(list3);
        _snowman2.addAll(list2);
        return _snowman2;
    }

    private void addEntitiesFromWorld(World world, BlockPos firstCorner, BlockPos secondCorner) {
        List<Entity> list = world.getEntitiesByClass(Entity.class, new Box(firstCorner, secondCorner), entity -> !(entity instanceof PlayerEntity));
        this.entities.clear();
        for (Entity entity2 : list) {
            Vec3d vec3d = new Vec3d(entity2.getX() - (double)firstCorner.getX(), entity2.getY() - (double)firstCorner.getY(), entity2.getZ() - (double)firstCorner.getZ());
            CompoundTag _snowman2 = new CompoundTag();
            entity2.saveToTag(_snowman2);
            BlockPos _snowman3 = entity2 instanceof PaintingEntity ? ((PaintingEntity)entity2).getDecorationBlockPos().subtract(firstCorner) : new BlockPos(vec3d);
            this.entities.add(new StructureEntityInfo(vec3d, _snowman3, _snowman2.copy()));
        }
    }

    public List<StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block) {
        return this.getInfosForBlock(pos, placementData, block, true);
    }

    public List<StructureBlockInfo> getInfosForBlock(BlockPos pos, StructurePlacementData placementData, Block block, boolean transformed) {
        ArrayList arrayList = Lists.newArrayList();
        BlockBox _snowman2 = placementData.getBoundingBox();
        if (this.blockInfoLists.isEmpty()) {
            return Collections.emptyList();
        }
        for (StructureBlockInfo structureBlockInfo : placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAllOf(block)) {
            BlockPos blockPos = _snowman = transformed ? Structure.transform(placementData, structureBlockInfo.pos).add(pos) : structureBlockInfo.pos;
            if (_snowman2 != null && !_snowman2.contains(_snowman)) continue;
            arrayList.add(new StructureBlockInfo(_snowman, structureBlockInfo.state.rotate(placementData.getRotation()), structureBlockInfo.tag));
        }
        return arrayList;
    }

    public BlockPos transformBox(StructurePlacementData placementData1, BlockPos pos1, StructurePlacementData placementData2, BlockPos pos2) {
        BlockPos blockPos = Structure.transform(placementData1, pos1);
        _snowman = Structure.transform(placementData2, pos2);
        return blockPos.subtract(_snowman);
    }

    public static BlockPos transform(StructurePlacementData placementData, BlockPos pos) {
        return Structure.transformAround(pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition());
    }

    public void place(ServerWorldAccess serverWorldAccess, BlockPos pos, StructurePlacementData placementData, Random random) {
        placementData.calculateBoundingBox();
        this.placeAndNotifyListeners(serverWorldAccess, pos, placementData, random);
    }

    public void placeAndNotifyListeners(ServerWorldAccess serverWorldAccess, BlockPos pos, StructurePlacementData data, Random random) {
        this.place(serverWorldAccess, pos, pos, data, random, 2);
    }

    public boolean place(ServerWorldAccess serverWorldAccess2, BlockPos pos, BlockPos blockPos, StructurePlacementData placementData, Random random, int n) {
        Object object;
        if (this.blockInfoLists.isEmpty()) {
            return false;
        }
        List<StructureBlockInfo> list = placementData.getRandomBlockInfos(this.blockInfoLists, pos).getAll();
        if (list.isEmpty() && (placementData.shouldIgnoreEntities() || this.entities.isEmpty()) || this.size.getX() < 1 || this.size.getY() < 1 || this.size.getZ() < 1) {
            return false;
        }
        BlockBox _snowman2 = placementData.getBoundingBox();
        ArrayList _snowman3 = Lists.newArrayListWithCapacity((int)(placementData.shouldPlaceFluids() ? list.size() : 0));
        ArrayList _snowman4 = Lists.newArrayListWithCapacity((int)list.size());
        int _snowman5 = Integer.MAX_VALUE;
        int _snowman6 = Integer.MAX_VALUE;
        int _snowman7 = Integer.MAX_VALUE;
        int _snowman8 = Integer.MIN_VALUE;
        int _snowman9 = Integer.MIN_VALUE;
        int _snowman10 = Integer.MIN_VALUE;
        List<StructureBlockInfo> list2 = Structure.process(serverWorldAccess2, pos, blockPos, placementData, list);
        for (StructureBlockInfo structureBlockInfo : list2) {
            BlockPos object2 = structureBlockInfo.pos;
            if (_snowman2 != null && !_snowman2.contains(object2)) continue;
            Object object3 = placementData.shouldPlaceFluids() ? serverWorldAccess2.getFluidState(object2) : null;
            _snowman = structureBlockInfo.state.mirror(placementData.getMirror()).rotate(placementData.getRotation());
            if (structureBlockInfo.tag != null) {
                BlockEntity object22 = serverWorldAccess2.getBlockEntity(object2);
                Clearable.clear(object22);
                serverWorldAccess2.setBlockState(object2, Blocks.BARRIER.getDefaultState(), 20);
            }
            if (!serverWorldAccess2.setBlockState(object2, (BlockState)_snowman, n)) continue;
            _snowman5 = Math.min(_snowman5, object2.getX());
            _snowman6 = Math.min(_snowman6, object2.getY());
            _snowman7 = Math.min(_snowman7, object2.getZ());
            _snowman8 = Math.max(_snowman8, object2.getX());
            _snowman9 = Math.max(_snowman9, object2.getY());
            _snowman10 = Math.max(_snowman10, object2.getZ());
            _snowman4.add(Pair.of((Object)object2, (Object)structureBlockInfo.tag));
            if (structureBlockInfo.tag != null && (_snowman = serverWorldAccess2.getBlockEntity(object2)) != null) {
                structureBlockInfo.tag.putInt("x", object2.getX());
                structureBlockInfo.tag.putInt("y", object2.getY());
                structureBlockInfo.tag.putInt("z", object2.getZ());
                if (_snowman instanceof LootableContainerBlockEntity) {
                    structureBlockInfo.tag.putLong("LootTableSeed", random.nextLong());
                }
                ((BlockEntity)_snowman).fromTag(structureBlockInfo.state, structureBlockInfo.tag);
                ((BlockEntity)_snowman).applyMirror(placementData.getMirror());
                ((BlockEntity)_snowman).applyRotation(placementData.getRotation());
            }
            if (object3 == null || !(((AbstractBlock.AbstractBlockState)_snowman).getBlock() instanceof FluidFillable)) continue;
            ((FluidFillable)((Object)((AbstractBlock.AbstractBlockState)_snowman).getBlock())).tryFillWithFluid(serverWorldAccess2, object2, (BlockState)_snowman, (FluidState)object3);
            if (((FluidState)object3).isStill()) continue;
            _snowman3.add(object2);
        }
        boolean _snowman12 = true;
        Direction[] directionArray = new Direction[]{Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        while (_snowman12 && !_snowman3.isEmpty()) {
            _snowman12 = false;
            object = _snowman3.iterator();
            while (object.hasNext()) {
                _snowman = object3 = (BlockPos)object.next();
                _snowman = serverWorldAccess2.getFluidState((BlockPos)_snowman);
                for (int i = 0; i < directionArray.length && !((FluidState)_snowman).isStill(); ++i) {
                    BlockPos object3 = ((BlockPos)_snowman).offset(directionArray[i]);
                    object4 = serverWorldAccess2.getFluidState(object3);
                    if (!(((FluidState)object4).getHeight(serverWorldAccess2, object3) > ((FluidState)_snowman).getHeight(serverWorldAccess2, (BlockPos)_snowman)) && (!((FluidState)object4).isStill() || ((FluidState)_snowman).isStill())) continue;
                    _snowman = object4;
                    _snowman = object3;
                }
                if (!((FluidState)_snowman).isStill() || !((block = (_snowman = serverWorldAccess2.getBlockState((BlockPos)object3)).getBlock()) instanceof FluidFillable)) continue;
                ((FluidFillable)((Object)block)).tryFillWithFluid(serverWorldAccess2, (BlockPos)object3, _snowman, (FluidState)_snowman);
                _snowman12 = true;
                object.remove();
            }
        }
        if (_snowman5 <= _snowman8) {
            if (!placementData.shouldUpdateNeighbors()) {
                object = new BitSetVoxelSet(_snowman8 - _snowman5 + 1, _snowman9 - _snowman6 + 1, _snowman10 - _snowman7 + 1);
                int _snowman13 = _snowman5;
                int _snowman14 = _snowman6;
                int _snowman15 = _snowman7;
                for (Block block : _snowman4) {
                    Object object4 = (BlockPos)block.getFirst();
                    ((VoxelSet)object).set(((Vec3i)object4).getX() - _snowman13, ((Vec3i)object4).getY() - _snowman14, ((Vec3i)object4).getZ() - _snowman15, true, true);
                }
                Structure.updateCorner(serverWorldAccess2, n, (VoxelSet)object, _snowman13, _snowman14, _snowman15);
            }
            for (Pair _snowman16 : _snowman4) {
                BlockEntity blockEntity;
                BlockPos blockPos2 = (BlockPos)_snowman16.getFirst();
                if (!placementData.shouldUpdateNeighbors()) {
                    BlockState blockState;
                    BlockState blockState2 = serverWorldAccess2.getBlockState(blockPos2);
                    if (blockState2 != (blockState = Block.postProcessState(blockState2, serverWorldAccess2, blockPos2))) {
                        serverWorldAccess2.setBlockState(blockPos2, blockState, n & 0xFFFFFFFE | 0x10);
                    }
                    serverWorldAccess2.updateNeighbors(blockPos2, blockState.getBlock());
                }
                if (_snowman16.getSecond() == null || (blockEntity = serverWorldAccess2.getBlockEntity(blockPos2)) == null) continue;
                blockEntity.markDirty();
            }
        }
        if (!placementData.shouldIgnoreEntities()) {
            this.spawnEntities(serverWorldAccess2, pos, placementData.getMirror(), placementData.getRotation(), placementData.getPosition(), _snowman2, placementData.method_27265());
        }
        return true;
    }

    public static void updateCorner(WorldAccess world, int flags, VoxelSet voxelSet, int startX, int startY, int startZ) {
        voxelSet.forEachDirection((direction, n5, n6, n7) -> {
            BlockPos blockPos = new BlockPos(startX + n5, startY + n6, startZ + n7);
            _snowman = blockPos.offset(direction);
            BlockState _snowman2 = world.getBlockState(blockPos);
            if (_snowman2 != (_snowman = _snowman2.getStateForNeighborUpdate(direction, _snowman = world.getBlockState(_snowman), world, blockPos, _snowman))) {
                world.setBlockState(blockPos, _snowman, flags & 0xFFFFFFFE);
            }
            if (_snowman != (_snowman = _snowman.getStateForNeighborUpdate(direction.getOpposite(), _snowman, world, _snowman, blockPos))) {
                world.setBlockState(_snowman, _snowman, flags & 0xFFFFFFFE);
            }
        });
    }

    public static List<StructureBlockInfo> process(WorldAccess world, BlockPos pos, BlockPos blockPos, StructurePlacementData structurePlacementData, List<StructureBlockInfo> list) {
        ArrayList arrayList = Lists.newArrayList();
        for (StructureBlockInfo structureBlockInfo : list) {
            BlockPos blockPos2 = Structure.transform(structurePlacementData, structureBlockInfo.pos).add(pos);
            StructureBlockInfo _snowman2 = new StructureBlockInfo(blockPos2, structureBlockInfo.state, structureBlockInfo.tag != null ? structureBlockInfo.tag.copy() : null);
            Iterator<StructureProcessor> _snowman3 = structurePlacementData.getProcessors().iterator();
            while (_snowman2 != null && _snowman3.hasNext()) {
                _snowman2 = _snowman3.next().process(world, pos, blockPos, structureBlockInfo, _snowman2, structurePlacementData);
            }
            if (_snowman2 == null) continue;
            arrayList.add(_snowman2);
        }
        return arrayList;
    }

    private void spawnEntities(ServerWorldAccess serverWorldAccess, BlockPos pos, BlockMirror blockMirror, BlockRotation blockRotation, BlockPos pivot, @Nullable BlockBox area, boolean bl) {
        for (StructureEntityInfo structureEntityInfo : this.entities) {
            BlockPos blockPos = Structure.transformAround(structureEntityInfo.blockPos, blockMirror, blockRotation, pivot).add(pos);
            if (area != null && !area.contains(blockPos)) continue;
            CompoundTag _snowman2 = structureEntityInfo.tag.copy();
            Vec3d _snowman3 = Structure.transformAround(structureEntityInfo.pos, blockMirror, blockRotation, pivot);
            Vec3d _snowman4 = _snowman3.add(pos.getX(), pos.getY(), pos.getZ());
            ListTag _snowman5 = new ListTag();
            _snowman5.add(DoubleTag.of(_snowman4.x));
            _snowman5.add(DoubleTag.of(_snowman4.y));
            _snowman5.add(DoubleTag.of(_snowman4.z));
            _snowman2.put("Pos", _snowman5);
            _snowman2.remove("UUID");
            Structure.getEntity(serverWorldAccess, _snowman2).ifPresent(entity -> {
                float f = entity.applyMirror(blockMirror);
                entity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, f += entity.yaw - entity.applyRotation(blockRotation), entity.pitch);
                if (bl && entity instanceof MobEntity) {
                    ((MobEntity)entity).initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(new BlockPos(_snowman4)), SpawnReason.STRUCTURE, null, _snowman2);
                }
                serverWorldAccess.spawnEntityAndPassengers((Entity)entity);
            });
        }
    }

    private static Optional<Entity> getEntity(ServerWorldAccess serverWorldAccess, CompoundTag compoundTag) {
        try {
            return EntityType.getEntityFromTag(compoundTag, serverWorldAccess.toServerWorld());
        }
        catch (Exception exception) {
            return Optional.empty();
        }
    }

    public BlockPos getRotatedSize(BlockRotation blockRotation) {
        switch (blockRotation) {
            case COUNTERCLOCKWISE_90: 
            case CLOCKWISE_90: {
                return new BlockPos(this.size.getZ(), this.size.getY(), this.size.getX());
            }
        }
        return this.size;
    }

    public static BlockPos transformAround(BlockPos pos, BlockMirror blockMirror, BlockRotation blockRotation, BlockPos pivot) {
        int n = pos.getX();
        _snowman = pos.getY();
        _snowman = pos.getZ();
        boolean _snowman2 = true;
        switch (blockMirror) {
            case LEFT_RIGHT: {
                _snowman = -_snowman;
                break;
            }
            case FRONT_BACK: {
                n = -n;
                break;
            }
            default: {
                _snowman2 = false;
            }
        }
        _snowman = pivot.getX();
        _snowman = pivot.getZ();
        switch (blockRotation) {
            case CLOCKWISE_180: {
                return new BlockPos(_snowman + _snowman - n, _snowman, _snowman + _snowman - _snowman);
            }
            case COUNTERCLOCKWISE_90: {
                return new BlockPos(_snowman - _snowman + _snowman, _snowman, _snowman + _snowman - n);
            }
            case CLOCKWISE_90: {
                return new BlockPos(_snowman + _snowman - _snowman, _snowman, _snowman - _snowman + n);
            }
        }
        return _snowman2 ? new BlockPos(n, _snowman, _snowman) : pos;
    }

    public static Vec3d transformAround(Vec3d point, BlockMirror blockMirror, BlockRotation blockRotation, BlockPos pivot) {
        double d = point.x;
        _snowman = point.y;
        _snowman = point.z;
        boolean _snowman2 = true;
        switch (blockMirror) {
            case LEFT_RIGHT: {
                _snowman = 1.0 - _snowman;
                break;
            }
            case FRONT_BACK: {
                d = 1.0 - d;
                break;
            }
            default: {
                _snowman2 = false;
            }
        }
        int _snowman3 = pivot.getX();
        int _snowman4 = pivot.getZ();
        switch (blockRotation) {
            case CLOCKWISE_180: {
                return new Vec3d((double)(_snowman3 + _snowman3 + 1) - d, _snowman, (double)(_snowman4 + _snowman4 + 1) - _snowman);
            }
            case COUNTERCLOCKWISE_90: {
                return new Vec3d((double)(_snowman3 - _snowman4) + _snowman, _snowman, (double)(_snowman3 + _snowman4 + 1) - d);
            }
            case CLOCKWISE_90: {
                return new Vec3d((double)(_snowman3 + _snowman4 + 1) - _snowman, _snowman, (double)(_snowman4 - _snowman3) + d);
            }
        }
        return _snowman2 ? new Vec3d(d, _snowman, _snowman) : point;
    }

    public BlockPos offsetByTransformedSize(BlockPos blockPos, BlockMirror blockMirror, BlockRotation blockRotation) {
        return Structure.applyTransformedOffset(blockPos, blockMirror, blockRotation, this.getSize().getX(), this.getSize().getZ());
    }

    public static BlockPos applyTransformedOffset(BlockPos blockPos, BlockMirror blockMirror, BlockRotation blockRotation, int offsetX, int offsetZ) {
        int n = blockMirror == BlockMirror.FRONT_BACK ? --offsetX : 0;
        _snowman = blockMirror == BlockMirror.LEFT_RIGHT ? --offsetZ : 0;
        BlockPos _snowman2 = blockPos;
        switch (blockRotation) {
            case NONE: {
                _snowman2 = blockPos.add(n, 0, _snowman);
                break;
            }
            case CLOCKWISE_90: {
                _snowman2 = blockPos.add(offsetZ - _snowman, 0, n);
                break;
            }
            case CLOCKWISE_180: {
                _snowman2 = blockPos.add(offsetX - n, 0, offsetZ - _snowman);
                break;
            }
            case COUNTERCLOCKWISE_90: {
                _snowman2 = blockPos.add(_snowman, 0, offsetX - n);
            }
        }
        return _snowman2;
    }

    public BlockBox calculateBoundingBox(StructurePlacementData structurePlacementData, BlockPos pos) {
        return this.method_27267(pos, structurePlacementData.getRotation(), structurePlacementData.getPosition(), structurePlacementData.getMirror());
    }

    public BlockBox method_27267(BlockPos blockPos, BlockRotation blockRotation, BlockPos blockPos2, BlockMirror blockMirror) {
        BlockPos blockPos3 = this.getRotatedSize(blockRotation);
        int _snowman2 = blockPos2.getX();
        int _snowman3 = blockPos2.getZ();
        int _snowman4 = blockPos3.getX() - 1;
        int _snowman5 = blockPos3.getY() - 1;
        int _snowman6 = blockPos3.getZ() - 1;
        BlockBox _snowman7 = new BlockBox(0, 0, 0, 0, 0, 0);
        switch (blockRotation) {
            case NONE: {
                _snowman7 = new BlockBox(0, 0, 0, _snowman4, _snowman5, _snowman6);
                break;
            }
            case CLOCKWISE_180: {
                _snowman7 = new BlockBox(_snowman2 + _snowman2 - _snowman4, 0, _snowman3 + _snowman3 - _snowman6, _snowman2 + _snowman2, _snowman5, _snowman3 + _snowman3);
                break;
            }
            case COUNTERCLOCKWISE_90: {
                _snowman7 = new BlockBox(_snowman2 - _snowman3, 0, _snowman2 + _snowman3 - _snowman6, _snowman2 - _snowman3 + _snowman4, _snowman5, _snowman2 + _snowman3);
                break;
            }
            case CLOCKWISE_90: {
                _snowman7 = new BlockBox(_snowman2 + _snowman3 - _snowman4, 0, _snowman3 - _snowman2, _snowman2 + _snowman3, _snowman5, _snowman3 - _snowman2 + _snowman6);
            }
        }
        switch (blockMirror) {
            case NONE: {
                break;
            }
            case FRONT_BACK: {
                this.mirrorBoundingBox(blockRotation, _snowman4, _snowman6, _snowman7, Direction.WEST, Direction.EAST);
                break;
            }
            case LEFT_RIGHT: {
                this.mirrorBoundingBox(blockRotation, _snowman6, _snowman4, _snowman7, Direction.NORTH, Direction.SOUTH);
            }
        }
        _snowman7.move(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return _snowman7;
    }

    private void mirrorBoundingBox(BlockRotation rotation, int offsetX, int offsetZ, BlockBox boundingBox, Direction direction, Direction direction2) {
        BlockPos blockPos = BlockPos.ORIGIN;
        blockPos = rotation == BlockRotation.CLOCKWISE_90 || rotation == BlockRotation.COUNTERCLOCKWISE_90 ? blockPos.offset(rotation.rotate(direction), offsetZ) : (rotation == BlockRotation.CLOCKWISE_180 ? blockPos.offset(direction2, offsetX) : blockPos.offset(direction, offsetX));
        boundingBox.move(blockPos.getX(), 0, blockPos.getZ());
    }

    /*
     * WARNING - void declaration
     */
    public CompoundTag toTag(CompoundTag tag) {
        Object object;
        if (this.blockInfoLists.isEmpty()) {
            tag.put("blocks", new ListTag());
            tag.put("palette", new ListTag());
        } else {
            void var4_5;
            ArrayList abstractList = Lists.newArrayList();
            Palette _snowman2 = new Palette();
            abstractList.add(_snowman2);
            boolean bl = true;
            while (var4_5 < this.blockInfoLists.size()) {
                abstractList.add(new Palette());
                ++var4_5;
            }
            ListTag listTag = new ListTag();
            object = this.blockInfoLists.get(0).getAll();
            for (int i = 0; i < object.size(); ++i) {
                StructureBlockInfo structureBlockInfo = (StructureBlockInfo)object.get(i);
                CompoundTag _snowman3 = new CompoundTag();
                _snowman3.put("pos", this.createIntListTag(structureBlockInfo.pos.getX(), structureBlockInfo.pos.getY(), structureBlockInfo.pos.getZ()));
                int _snowman4 = _snowman2.getId(structureBlockInfo.state);
                _snowman3.putInt("state", _snowman4);
                if (structureBlockInfo.tag != null) {
                    _snowman3.put("nbt", structureBlockInfo.tag);
                }
                listTag.add(_snowman3);
                for (int j = 1; j < this.blockInfoLists.size(); ++j) {
                    Palette palette = (Palette)abstractList.get(j);
                    palette.set(this.blockInfoLists.get((int)j).getAll().get((int)i).state, _snowman4);
                }
            }
            tag.put("blocks", listTag);
            if (abstractList.size() == 1) {
                ListTag listTag2 = new ListTag();
                for (Object object2 : _snowman2) {
                    listTag2.add(NbtHelper.fromBlockState((BlockState)object2));
                }
                tag.put("palette", listTag2);
            } else {
                ListTag listTag3 = new ListTag();
                for (Object object2 : abstractList) {
                    ListTag listTag4 = new ListTag();
                    Iterator<BlockState> iterator = ((Palette)object2).iterator();
                    while (iterator.hasNext()) {
                        BlockState object3 = iterator.next();
                        listTag4.add(NbtHelper.fromBlockState(object3));
                    }
                    listTag3.add(listTag4);
                }
                tag.put("palettes", listTag3);
            }
        }
        ListTag listTag = new ListTag();
        for (StructureEntityInfo structureEntityInfo : this.entities) {
            object = new CompoundTag();
            ((CompoundTag)object).put("pos", this.createDoubleListTag(structureEntityInfo.pos.x, structureEntityInfo.pos.y, structureEntityInfo.pos.z));
            ((CompoundTag)object).put("blockPos", this.createIntListTag(structureEntityInfo.blockPos.getX(), structureEntityInfo.blockPos.getY(), structureEntityInfo.blockPos.getZ()));
            if (structureEntityInfo.tag != null) {
                ((CompoundTag)object).put("nbt", structureEntityInfo.tag);
            }
            listTag.add(object);
        }
        tag.put("entities", listTag);
        tag.put("size", this.createIntListTag(this.size.getX(), this.size.getY(), this.size.getZ()));
        tag.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        int n;
        this.blockInfoLists.clear();
        this.entities.clear();
        ListTag listTag = tag.getList("size", 3);
        this.size = new BlockPos(listTag.getInt(0), listTag.getInt(1), listTag.getInt(2));
        listTag2 = tag.getList("blocks", 10);
        if (tag.contains("palettes", 9)) {
            listTag3 = tag.getList("palettes", 9);
            for (n = 0; n < listTag3.size(); ++n) {
                this.loadPalettedBlockInfo(listTag3.getList(n), listTag2);
            }
        } else {
            ListTag listTag2;
            this.loadPalettedBlockInfo(tag.getList("palette", 10), listTag2);
        }
        ListTag listTag3 = tag.getList("entities", 10);
        for (n = 0; n < listTag3.size(); ++n) {
            CompoundTag compoundTag = listTag3.getCompound(n);
            ListTag _snowman2 = compoundTag.getList("pos", 6);
            Vec3d _snowman3 = new Vec3d(_snowman2.getDouble(0), _snowman2.getDouble(1), _snowman2.getDouble(2));
            ListTag _snowman4 = compoundTag.getList("blockPos", 3);
            BlockPos _snowman5 = new BlockPos(_snowman4.getInt(0), _snowman4.getInt(1), _snowman4.getInt(2));
            if (!compoundTag.contains("nbt")) continue;
            _snowman = compoundTag.getCompound("nbt");
            this.entities.add(new StructureEntityInfo(_snowman3, _snowman5, _snowman));
        }
    }

    private void loadPalettedBlockInfo(ListTag paletteTag, ListTag blocksTag) {
        Palette palette = new Palette();
        for (int i = 0; i < paletteTag.size(); ++i) {
            palette.set(NbtHelper.toBlockState(paletteTag.getCompound(i)), i);
        }
        ArrayList arrayList = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        _snowman = Lists.newArrayList();
        for (int i = 0; i < blocksTag.size(); ++i) {
            CompoundTag compoundTag = blocksTag.getCompound(i);
            ListTag _snowman2 = compoundTag.getList("pos", 3);
            BlockPos _snowman3 = new BlockPos(_snowman2.getInt(0), _snowman2.getInt(1), _snowman2.getInt(2));
            BlockState _snowman4 = palette.getState(compoundTag.getInt("state"));
            _snowman = compoundTag.contains("nbt") ? compoundTag.getCompound("nbt") : null;
            StructureBlockInfo _snowman5 = new StructureBlockInfo(_snowman3, _snowman4, _snowman);
            Structure.method_28054(_snowman5, arrayList, _snowman, _snowman);
        }
        List<StructureBlockInfo> list = Structure.method_28055(arrayList, _snowman, _snowman);
        this.blockInfoLists.add(new PalettedBlockInfoList(list));
    }

    private ListTag createIntListTag(int ... nArray) {
        ListTag listTag = new ListTag();
        for (int n : nArray) {
            listTag.add(IntTag.of(n));
        }
        return listTag;
    }

    private ListTag createDoubleListTag(double ... dArray) {
        ListTag listTag = new ListTag();
        for (double d : dArray) {
            listTag.add(DoubleTag.of(d));
        }
        return listTag;
    }

    public static final class PalettedBlockInfoList {
        private final List<StructureBlockInfo> infos;
        private final Map<Block, List<StructureBlockInfo>> blockToInfos = Maps.newHashMap();

        private PalettedBlockInfoList(List<StructureBlockInfo> infos) {
            this.infos = infos;
        }

        public List<StructureBlockInfo> getAll() {
            return this.infos;
        }

        public List<StructureBlockInfo> getAllOf(Block block2) {
            return this.blockToInfos.computeIfAbsent(block2, block -> this.infos.stream().filter(structureBlockInfo -> structureBlockInfo.state.isOf((Block)block)).collect(Collectors.toList()));
        }
    }

    public static class StructureEntityInfo {
        public final Vec3d pos;
        public final BlockPos blockPos;
        public final CompoundTag tag;

        public StructureEntityInfo(Vec3d pos, BlockPos blockPos, CompoundTag tag) {
            this.pos = pos;
            this.blockPos = blockPos;
            this.tag = tag;
        }
    }

    public static class StructureBlockInfo {
        public final BlockPos pos;
        public final BlockState state;
        public final CompoundTag tag;

        public StructureBlockInfo(BlockPos pos, BlockState state, @Nullable CompoundTag tag) {
            this.pos = pos;
            this.state = state;
            this.tag = tag;
        }

        public String toString() {
            return String.format("<StructureBlockInfo | %s | %s | %s>", this.pos, this.state, this.tag);
        }
    }

    static class Palette
    implements Iterable<BlockState> {
        public static final BlockState AIR = Blocks.AIR.getDefaultState();
        private final IdList<BlockState> ids = new IdList(16);
        private int currentIndex;

        private Palette() {
        }

        public int getId(BlockState state) {
            int n = this.ids.getRawId(state);
            if (n == -1) {
                n = this.currentIndex++;
                this.ids.set(state, n);
            }
            return n;
        }

        @Nullable
        public BlockState getState(int id) {
            BlockState blockState = this.ids.get(id);
            return blockState == null ? AIR : blockState;
        }

        @Override
        public Iterator<BlockState> iterator() {
            return this.ids.iterator();
        }

        public void set(BlockState state, int id) {
            this.ids.set(state, id);
        }
    }
}

