/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import java.util.Objects;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.CuboidBlockIterator;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.border.WorldBorder;

public class BlockCollisionSpliterator
extends Spliterators.AbstractSpliterator<VoxelShape> {
    @Nullable
    private final Entity entity;
    private final Box box;
    private final ShapeContext context;
    private final CuboidBlockIterator blockIterator;
    private final BlockPos.Mutable pos;
    private final VoxelShape boxShape;
    private final CollisionView world;
    private boolean checkEntity;
    private final BiPredicate<BlockState, BlockPos> blockPredicate;

    public BlockCollisionSpliterator(CollisionView world, @Nullable Entity entity, Box box) {
        this(world, entity, box, (blockState, blockPos) -> true);
    }

    public BlockCollisionSpliterator(CollisionView collisionView, @Nullable Entity entity, Box box, BiPredicate<BlockState, BlockPos> blockPredicate) {
        super(Long.MAX_VALUE, 1280);
        this.context = entity == null ? ShapeContext.absent() : ShapeContext.of(entity);
        this.pos = new BlockPos.Mutable();
        this.boxShape = VoxelShapes.cuboid(box);
        this.world = collisionView;
        this.checkEntity = entity != null;
        this.entity = entity;
        this.box = box;
        this.blockPredicate = blockPredicate;
        int n = MathHelper.floor(box.minX - 1.0E-7) - 1;
        _snowman = MathHelper.floor(box.maxX + 1.0E-7) + 1;
        _snowman = MathHelper.floor(box.minY - 1.0E-7) - 1;
        _snowman = MathHelper.floor(box.maxY + 1.0E-7) + 1;
        _snowman = MathHelper.floor(box.minZ - 1.0E-7) - 1;
        _snowman = MathHelper.floor(box.maxZ + 1.0E-7) + 1;
        this.blockIterator = new CuboidBlockIterator(n, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    @Override
    public boolean tryAdvance(Consumer<? super VoxelShape> consumer) {
        return this.checkEntity && this.offerEntityShape(consumer) || this.offerBlockShape(consumer);
    }

    boolean offerBlockShape(Consumer<? super VoxelShape> consumer) {
        while (this.blockIterator.step()) {
            int n = this.blockIterator.getX();
            _snowman = this.blockIterator.getY();
            _snowman = this.blockIterator.getZ();
            _snowman = this.blockIterator.getEdgeCoordinatesCount();
            if (_snowman == 3 || (_snowman = this.getChunk(n, _snowman)) == null) continue;
            this.pos.set(n, _snowman, _snowman);
            BlockState _snowman2 = _snowman.getBlockState(this.pos);
            if (!this.blockPredicate.test(_snowman2, this.pos) || _snowman == 1 && !_snowman2.exceedsCube() || _snowman == 2 && !_snowman2.isOf(Blocks.MOVING_PISTON)) continue;
            VoxelShape _snowman3 = _snowman2.getCollisionShape(this.world, this.pos, this.context);
            if (_snowman3 == VoxelShapes.fullCube()) {
                if (!this.box.intersects(n, _snowman, _snowman, (double)n + 1.0, (double)_snowman + 1.0, (double)_snowman + 1.0)) continue;
                consumer.accept(_snowman3.offset(n, _snowman, _snowman));
                return true;
            }
            VoxelShape _snowman4 = _snowman3.offset(n, _snowman, _snowman);
            if (!VoxelShapes.matchesAnywhere(_snowman4, this.boxShape, BooleanBiFunction.AND)) continue;
            consumer.accept(_snowman4);
            return true;
        }
        return false;
    }

    @Nullable
    private BlockView getChunk(int x, int z) {
        int n = x >> 4;
        _snowman = z >> 4;
        return this.world.getExistingChunk(n, _snowman);
    }

    boolean offerEntityShape(Consumer<? super VoxelShape> consumer) {
        Objects.requireNonNull(this.entity);
        this.checkEntity = false;
        WorldBorder worldBorder = this.world.getWorldBorder();
        Box _snowman2 = this.entity.getBoundingBox();
        if (!BlockCollisionSpliterator.isInWorldBorder(worldBorder, _snowman2) && !BlockCollisionSpliterator.method_30131(_snowman = worldBorder.asVoxelShape(), _snowman2) && BlockCollisionSpliterator.method_30130(_snowman, _snowman2)) {
            consumer.accept(_snowman);
            return true;
        }
        return false;
    }

    private static boolean method_30130(VoxelShape voxelShape, Box box) {
        return VoxelShapes.matchesAnywhere(voxelShape, VoxelShapes.cuboid(box.expand(1.0E-7)), BooleanBiFunction.AND);
    }

    private static boolean method_30131(VoxelShape voxelShape, Box box) {
        return VoxelShapes.matchesAnywhere(voxelShape, VoxelShapes.cuboid(box.contract(1.0E-7)), BooleanBiFunction.AND);
    }

    public static boolean isInWorldBorder(WorldBorder border, Box box) {
        double d = MathHelper.floor(border.getBoundWest());
        _snowman = MathHelper.floor(border.getBoundNorth());
        _snowman = MathHelper.ceil(border.getBoundEast());
        _snowman = MathHelper.ceil(border.getBoundSouth());
        return box.minX > d && box.minX < _snowman && box.minZ > _snowman && box.minZ < _snowman && box.maxX > d && box.maxX < _snowman && box.maxZ > _snowman && box.maxZ < _snowman;
    }
}

