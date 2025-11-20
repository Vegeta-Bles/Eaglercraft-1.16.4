/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import java.util.Comparator;
import java.util.Optional;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.class_5459;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class PortalForcer {
    private final ServerWorld world;

    public PortalForcer(ServerWorld world) {
        this.world = world;
    }

    public Optional<class_5459.class_5460> method_30483(BlockPos blockPos, boolean bl) {
        PointOfInterestStorage pointOfInterestStorage = this.world.getPointOfInterestStorage();
        int _snowman2 = bl ? 16 : 128;
        pointOfInterestStorage.preloadChunks(this.world, blockPos, _snowman2);
        Optional<PointOfInterest> _snowman3 = pointOfInterestStorage.getInSquare(pointOfInterestType -> pointOfInterestType == PointOfInterestType.NETHER_PORTAL, blockPos, _snowman2, PointOfInterestStorage.OccupationStatus.ANY).sorted(Comparator.comparingDouble(pointOfInterest -> pointOfInterest.getPos().getSquaredDistance(blockPos)).thenComparingInt(pointOfInterest -> pointOfInterest.getPos().getY())).filter(pointOfInterest -> this.world.getBlockState(pointOfInterest.getPos()).contains(Properties.HORIZONTAL_AXIS)).findFirst();
        return _snowman3.map(pointOfInterest -> {
            BlockPos blockPos2 = pointOfInterest.getPos();
            this.world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(blockPos2), 3, blockPos2);
            BlockState _snowman2 = this.world.getBlockState(blockPos2);
            return class_5459.method_30574(blockPos2, _snowman2.get(Properties.HORIZONTAL_AXIS), 21, Direction.Axis.Y, 21, blockPos -> this.world.getBlockState((BlockPos)blockPos) == _snowman2);
        });
    }

    public Optional<class_5459.class_5460> method_30482(BlockPos blockPos2, Direction.Axis axis2) {
        Direction.Axis axis2;
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis2);
        double _snowman2 = -1.0;
        BlockPos _snowman3 = null;
        double _snowman4 = -1.0;
        BlockPos _snowman5 = null;
        WorldBorder _snowman6 = this.world.getWorldBorder();
        int _snowman7 = this.world.getDimensionHeight() - 1;
        BlockPos.Mutable _snowman8 = blockPos2.mutableCopy();
        for (BlockPos.Mutable mutable : BlockPos.method_30512(blockPos2, 16, Direction.EAST, Direction.SOUTH)) {
            int n = Math.min(_snowman7, this.world.getTopY(Heightmap.Type.MOTION_BLOCKING, mutable.getX(), mutable.getZ()));
            _snowman = 1;
            if (!_snowman6.contains(mutable) || !_snowman6.contains(mutable.move(direction, 1))) continue;
            mutable.move(direction.getOpposite(), 1);
            for (_snowman = n; _snowman >= 0; --_snowman) {
                mutable.setY(_snowman);
                if (!this.world.isAir(mutable)) continue;
                _snowman = _snowman;
                while (_snowman > 0 && this.world.isAir(mutable.move(Direction.DOWN))) {
                    --_snowman;
                }
                if (_snowman + 4 > _snowman7 || (_snowman = _snowman - _snowman) > 0 && _snowman < 3) continue;
                mutable.setY(_snowman);
                if (!this.method_30481(mutable, _snowman8, direction, 0)) continue;
                double d = blockPos2.getSquaredDistance(mutable);
                if (this.method_30481(mutable, _snowman8, direction, -1) && this.method_30481(mutable, _snowman8, direction, 1) && (_snowman2 == -1.0 || _snowman2 > d)) {
                    _snowman2 = d;
                    _snowman3 = mutable.toImmutable();
                }
                if (_snowman2 != -1.0 || _snowman4 != -1.0 && !(_snowman4 > d)) continue;
                _snowman4 = d;
                _snowman5 = mutable.toImmutable();
            }
        }
        if (_snowman2 == -1.0 && _snowman4 != -1.0) {
            _snowman3 = _snowman5;
            _snowman2 = _snowman4;
        }
        if (_snowman2 == -1.0) {
            BlockPos blockPos2;
            _snowman3 = new BlockPos(blockPos2.getX(), MathHelper.clamp(blockPos2.getY(), 70, this.world.getDimensionHeight() - 10), blockPos2.getZ()).toImmutable();
            Direction _snowman9 = direction.rotateYClockwise();
            if (!_snowman6.contains(_snowman3)) {
                return Optional.empty();
            }
            for (int i = -1; i < 2; ++i) {
                for (n = 0; n < 2; ++n) {
                    for (_snowman = -1; _snowman < 3; ++_snowman) {
                        BlockState blockState = _snowman < 0 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
                        _snowman8.set(_snowman3, n * direction.getOffsetX() + i * _snowman9.getOffsetX(), _snowman, n * direction.getOffsetZ() + i * _snowman9.getOffsetZ());
                        this.world.setBlockState(_snowman8, blockState);
                    }
                }
            }
        }
        for (int i = -1; i < 3; ++i) {
            for (_snowman = -1; _snowman < 4; ++_snowman) {
                if (i != -1 && i != 2 && _snowman != -1 && _snowman != 3) continue;
                _snowman8.set(_snowman3, i * direction.getOffsetX(), _snowman, i * direction.getOffsetZ());
                this.world.setBlockState(_snowman8, Blocks.OBSIDIAN.getDefaultState(), 3);
            }
        }
        BlockState _snowman10 = (BlockState)Blocks.NETHER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis2);
        for (int i = 0; i < 2; ++i) {
            for (n = 0; n < 3; ++n) {
                _snowman8.set(_snowman3, i * direction.getOffsetX(), n, i * direction.getOffsetZ());
                this.world.setBlockState(_snowman8, _snowman10, 18);
            }
        }
        return Optional.of(new class_5459.class_5460(_snowman3.toImmutable(), 2, 3));
    }

    private boolean method_30481(BlockPos blockPos, BlockPos.Mutable mutable, Direction direction, int n) {
        Direction direction2 = direction.rotateYClockwise();
        for (int i = -1; i < 3; ++i) {
            for (_snowman = -1; _snowman < 4; ++_snowman) {
                mutable.set(blockPos, direction.getOffsetX() * i + direction2.getOffsetX() * n, _snowman, direction.getOffsetZ() * i + direction2.getOffsetZ() * n);
                if (_snowman < 0 && !this.world.getBlockState(mutable).getMaterial().isSolid()) {
                    return false;
                }
                if (_snowman < 0 || this.world.isAir(mutable)) continue;
                return false;
            }
        }
        return true;
    }
}

