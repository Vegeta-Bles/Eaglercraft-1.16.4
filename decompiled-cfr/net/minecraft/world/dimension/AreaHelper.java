/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.dimension;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.class_5459;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.WorldAccess;

public class AreaHelper {
    private static final AbstractBlock.ContextPredicate IS_VALID_FRAME_BLOCK = (blockState, blockView, blockPos) -> blockState.isOf(Blocks.OBSIDIAN);
    private final WorldAccess world;
    private final Direction.Axis axis;
    private final Direction negativeDir;
    private int foundPortalBlocks;
    @Nullable
    private BlockPos lowerCorner;
    private int height;
    private int width;

    public static Optional<AreaHelper> method_30485(WorldAccess worldAccess, BlockPos blockPos, Direction.Axis axis) {
        return AreaHelper.method_30486(worldAccess, blockPos, areaHelper -> areaHelper.isValid() && areaHelper.foundPortalBlocks == 0, axis);
    }

    public static Optional<AreaHelper> method_30486(WorldAccess worldAccess, BlockPos blockPos, Predicate<AreaHelper> predicate, Direction.Axis axis) {
        Optional<AreaHelper> optional = Optional.of(new AreaHelper(worldAccess, blockPos, axis)).filter(predicate);
        if (optional.isPresent()) {
            return optional;
        }
        Direction.Axis _snowman2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        return Optional.of(new AreaHelper(worldAccess, blockPos, _snowman2)).filter(predicate);
    }

    public AreaHelper(WorldAccess world, BlockPos blockPos, Direction.Axis axis) {
        this.world = world;
        this.axis = axis;
        this.negativeDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.lowerCorner = this.method_30492(blockPos);
        if (this.lowerCorner == null) {
            this.lowerCorner = blockPos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.method_30495();
            if (this.width > 0) {
                this.height = this.method_30496();
            }
        }
    }

    @Nullable
    private BlockPos method_30492(BlockPos blockPos2) {
        int n = Math.max(0, blockPos2.getY() - 21);
        while (blockPos2.getY() > n && AreaHelper.validStateInsidePortal(this.world.getBlockState(blockPos2.down()))) {
            BlockPos blockPos2 = blockPos2.down();
        }
        Direction direction = this.negativeDir.getOpposite();
        int _snowman2 = this.method_30493(blockPos2, direction) - 1;
        if (_snowman2 < 0) {
            return null;
        }
        return blockPos2.offset(direction, _snowman2);
    }

    private int method_30495() {
        int n = this.method_30493(this.lowerCorner, this.negativeDir);
        if (n < 2 || n > 21) {
            return 0;
        }
        return n;
    }

    private int method_30493(BlockPos blockPos, Direction direction) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 0; i <= 21; ++i) {
            mutable.set(blockPos).move(direction, i);
            BlockState blockState = this.world.getBlockState(mutable);
            if (!AreaHelper.validStateInsidePortal(blockState)) {
                if (!IS_VALID_FRAME_BLOCK.test(blockState, this.world, mutable)) break;
                return i;
            }
            _snowman = this.world.getBlockState(mutable.move(Direction.DOWN));
            if (!IS_VALID_FRAME_BLOCK.test(_snowman, this.world, mutable)) break;
        }
        return 0;
    }

    private int method_30496() {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int _snowman2 = this.method_30490(mutable);
        if (_snowman2 < 3 || _snowman2 > 21 || !this.method_30491(mutable, _snowman2)) {
            return 0;
        }
        return _snowman2;
    }

    private boolean method_30491(BlockPos.Mutable mutable, int n) {
        for (_snowman = 0; _snowman < this.width; ++_snowman) {
            BlockPos.Mutable mutable2 = mutable.set(this.lowerCorner).move(Direction.UP, n).move(this.negativeDir, _snowman);
            if (IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(mutable2), this.world, mutable2)) continue;
            return false;
        }
        return true;
    }

    private int method_30490(BlockPos.Mutable mutable) {
        for (int i = 0; i < 21; ++i) {
            mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, -1);
            if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }
            mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, this.width);
            if (!IS_VALID_FRAME_BLOCK.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }
            for (_snowman = 0; _snowman < this.width; ++_snowman) {
                mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, _snowman);
                BlockState blockState = this.world.getBlockState(mutable);
                if (!AreaHelper.validStateInsidePortal(blockState)) {
                    return i;
                }
                if (!blockState.isOf(Blocks.NETHER_PORTAL)) continue;
                ++this.foundPortalBlocks;
            }
        }
        return 21;
    }

    private static boolean validStateInsidePortal(BlockState blockState) {
        return blockState.isAir() || blockState.isIn(BlockTags.FIRE) || blockState.isOf(Blocks.NETHER_PORTAL);
    }

    public boolean isValid() {
        return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortal() {
        BlockState blockState = (BlockState)Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1)).forEach(blockPos -> this.world.setBlockState((BlockPos)blockPos, blockState, 18));
    }

    public boolean wasAlreadyValid() {
        return this.isValid() && this.foundPortalBlocks == this.width * this.height;
    }

    public static Vec3d method_30494(class_5459.class_5460 class_54602, Direction.Axis axis, Vec3d vec3d, EntityDimensions entityDimensions) {
        double _snowman5;
        Direction.Axis _snowman4;
        double _snowman3;
        double d = (double)class_54602.field_25937 - (double)entityDimensions.width;
        _snowman = (double)class_54602.field_25938 - (double)entityDimensions.height;
        BlockPos _snowman2 = class_54602.field_25936;
        if (d > 0.0) {
            float f = (float)_snowman2.getComponentAlongAxis(axis) + entityDimensions.width / 2.0f;
            _snowman3 = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(axis) - (double)f, 0.0, d), 0.0, 1.0);
        } else {
            _snowman3 = 0.5;
        }
        if (_snowman > 0.0) {
            _snowman4 = Direction.Axis.Y;
            _snowman5 = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(_snowman4) - (double)_snowman2.getComponentAlongAxis(_snowman4), 0.0, _snowman), 0.0, 1.0);
        } else {
            _snowman5 = 0.0;
        }
        _snowman4 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double d2 = vec3d.getComponentAlongAxis(_snowman4) - ((double)_snowman2.getComponentAlongAxis(_snowman4) + 0.5);
        return new Vec3d(_snowman3, _snowman5, d2);
    }

    public static TeleportTarget method_30484(ServerWorld serverWorld, class_5459.class_5460 class_54602, Direction.Axis axis, Vec3d vec3d, EntityDimensions entityDimensions, Vec3d vec3d2, float f, float f2) {
        BlockPos blockPos = class_54602.field_25936;
        BlockState _snowman2 = serverWorld.getBlockState(blockPos);
        Direction.Axis _snowman3 = _snowman2.get(Properties.HORIZONTAL_AXIS);
        double _snowman4 = class_54602.field_25937;
        double _snowman5 = class_54602.field_25938;
        int _snowman6 = axis == _snowman3 ? 0 : 90;
        Vec3d _snowman7 = axis == _snowman3 ? vec3d2 : new Vec3d(vec3d2.z, vec3d2.y, -vec3d2.x);
        double _snowman8 = (double)entityDimensions.width / 2.0 + (_snowman4 - (double)entityDimensions.width) * vec3d.getX();
        double _snowman9 = (_snowman5 - (double)entityDimensions.height) * vec3d.getY();
        double _snowman10 = 0.5 + vec3d.getZ();
        boolean _snowman11 = _snowman3 == Direction.Axis.X;
        Vec3d _snowman12 = new Vec3d((double)blockPos.getX() + (_snowman11 ? _snowman8 : _snowman10), (double)blockPos.getY() + _snowman9, (double)blockPos.getZ() + (_snowman11 ? _snowman10 : _snowman8));
        return new TeleportTarget(_snowman12, _snowman7, f + (float)_snowman6, f2);
    }
}

