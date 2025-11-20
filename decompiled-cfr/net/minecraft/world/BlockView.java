/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;

public interface BlockView {
    @Nullable
    public BlockEntity getBlockEntity(BlockPos var1);

    public BlockState getBlockState(BlockPos var1);

    public FluidState getFluidState(BlockPos var1);

    default public int getLuminance(BlockPos pos) {
        return this.getBlockState(pos).getLuminance();
    }

    default public int getMaxLightLevel() {
        return 15;
    }

    default public int getHeight() {
        return 256;
    }

    default public Stream<BlockState> method_29546(Box box) {
        return BlockPos.stream(box).map(this::getBlockState);
    }

    default public BlockHitResult raycast(RaycastContext context) {
        return BlockView.raycast(context, (raycastContext, blockPos) -> {
            BlockState blockState = this.getBlockState((BlockPos)blockPos);
            FluidState _snowman2 = this.getFluidState((BlockPos)blockPos);
            Vec3d _snowman3 = raycastContext.getStart();
            Vec3d _snowman4 = raycastContext.getEnd();
            VoxelShape _snowman5 = raycastContext.getBlockShape(blockState, this, (BlockPos)blockPos);
            BlockHitResult _snowman6 = this.raycastBlock(_snowman3, _snowman4, (BlockPos)blockPos, _snowman5, blockState);
            VoxelShape _snowman7 = raycastContext.getFluidShape(_snowman2, this, (BlockPos)blockPos);
            BlockHitResult _snowman8 = _snowman7.raycast(_snowman3, _snowman4, (BlockPos)blockPos);
            double _snowman9 = _snowman6 == null ? Double.MAX_VALUE : raycastContext.getStart().squaredDistanceTo(_snowman6.getPos());
            double _snowman10 = _snowman8 == null ? Double.MAX_VALUE : raycastContext.getStart().squaredDistanceTo(_snowman8.getPos());
            return _snowman9 <= _snowman10 ? _snowman6 : _snowman8;
        }, raycastContext -> {
            Vec3d vec3d = raycastContext.getStart().subtract(raycastContext.getEnd());
            return BlockHitResult.createMissed(raycastContext.getEnd(), Direction.getFacing(vec3d.x, vec3d.y, vec3d.z), new BlockPos(raycastContext.getEnd()));
        });
    }

    @Nullable
    default public BlockHitResult raycastBlock(Vec3d start, Vec3d end, BlockPos pos, VoxelShape shape, BlockState state) {
        BlockHitResult blockHitResult = shape.raycast(start, end, pos);
        if (blockHitResult != null && (_snowman = state.getRaycastShape(this, pos).raycast(start, end, pos)) != null && _snowman.getPos().subtract(start).lengthSquared() < blockHitResult.getPos().subtract(start).lengthSquared()) {
            return blockHitResult.withSide(_snowman.getSide());
        }
        return blockHitResult;
    }

    default public double getDismountHeight(VoxelShape blockCollisionShape, Supplier<VoxelShape> belowBlockCollisionShapeGetter) {
        if (!blockCollisionShape.isEmpty()) {
            return blockCollisionShape.getMax(Direction.Axis.Y);
        }
        double d = belowBlockCollisionShapeGetter.get().getMax(Direction.Axis.Y);
        if (d >= 1.0) {
            return d - 1.0;
        }
        return Double.NEGATIVE_INFINITY;
    }

    default public double getDismountHeight(BlockPos pos) {
        return this.getDismountHeight(this.getBlockState(pos).getCollisionShape(this, pos), () -> {
            _snowman = pos.down();
            return this.getBlockState(_snowman).getCollisionShape(this, _snowman);
        });
    }

    public static <T> T raycast(RaycastContext raycastContext, BiFunction<RaycastContext, BlockPos, T> context, Function<RaycastContext, T> blockRaycaster) {
        Vec3d vec3d = raycastContext.getStart();
        if (vec3d.equals(_snowman = raycastContext.getEnd())) {
            return blockRaycaster.apply(raycastContext);
        }
        double _snowman2 = MathHelper.lerp(-1.0E-7, _snowman.x, vec3d.x);
        double _snowman3 = MathHelper.lerp(-1.0E-7, _snowman.y, vec3d.y);
        double _snowman4 = MathHelper.lerp(-1.0E-7, _snowman.z, vec3d.z);
        double _snowman5 = MathHelper.lerp(-1.0E-7, vec3d.x, _snowman.x);
        double _snowman6 = MathHelper.lerp(-1.0E-7, vec3d.y, _snowman.y);
        double _snowman7 = MathHelper.lerp(-1.0E-7, vec3d.z, _snowman.z);
        int _snowman8 = MathHelper.floor(_snowman5);
        BlockPos.Mutable _snowman9 = new BlockPos.Mutable(_snowman8, _snowman = MathHelper.floor(_snowman6), _snowman = MathHelper.floor(_snowman7));
        T _snowman10 = context.apply(raycastContext, _snowman9);
        if (_snowman10 != null) {
            return _snowman10;
        }
        double _snowman11 = _snowman2 - _snowman5;
        double _snowman12 = _snowman3 - _snowman6;
        double _snowman13 = _snowman4 - _snowman7;
        int _snowman14 = MathHelper.sign(_snowman11);
        int _snowman15 = MathHelper.sign(_snowman12);
        int _snowman16 = MathHelper.sign(_snowman13);
        double _snowman17 = _snowman14 == 0 ? Double.MAX_VALUE : (double)_snowman14 / _snowman11;
        double _snowman18 = _snowman15 == 0 ? Double.MAX_VALUE : (double)_snowman15 / _snowman12;
        double _snowman19 = _snowman16 == 0 ? Double.MAX_VALUE : (double)_snowman16 / _snowman13;
        double _snowman20 = _snowman17 * (_snowman14 > 0 ? 1.0 - MathHelper.fractionalPart(_snowman5) : MathHelper.fractionalPart(_snowman5));
        double _snowman21 = _snowman18 * (_snowman15 > 0 ? 1.0 - MathHelper.fractionalPart(_snowman6) : MathHelper.fractionalPart(_snowman6));
        double _snowman22 = _snowman19 * (_snowman16 > 0 ? 1.0 - MathHelper.fractionalPart(_snowman7) : MathHelper.fractionalPart(_snowman7));
        while (_snowman20 <= 1.0 || _snowman21 <= 1.0 || _snowman22 <= 1.0) {
            if (_snowman20 < _snowman21) {
                if (_snowman20 < _snowman22) {
                    _snowman8 += _snowman14;
                    _snowman20 += _snowman17;
                } else {
                    _snowman += _snowman16;
                    _snowman22 += _snowman19;
                }
            } else if (_snowman21 < _snowman22) {
                _snowman += _snowman15;
                _snowman21 += _snowman18;
            } else {
                _snowman += _snowman16;
                _snowman22 += _snowman19;
            }
            if ((_snowman = context.apply(raycastContext, _snowman9.set(_snowman8, _snowman, _snowman))) == null) continue;
            return _snowman;
        }
        return blockRaycaster.apply(raycastContext);
    }
}

