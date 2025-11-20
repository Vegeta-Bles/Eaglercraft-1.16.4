/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.world.chunk.light;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.SkyLightStorage;
import org.apache.commons.lang3.mutable.MutableInt;

public final class ChunkSkyLightProvider
extends ChunkLightProvider<SkyLightStorage.Data, SkyLightStorage> {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final Direction[] HORIZONTAL_DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public ChunkSkyLightProvider(ChunkProvider chunkProvider) {
        super(chunkProvider, LightType.SKY, new SkyLightStorage(chunkProvider));
    }

    @Override
    protected int getPropagatedLevel(long sourceId, long targetId, int level) {
        VoxelShape voxelShape;
        if (targetId == Long.MAX_VALUE) {
            return 15;
        }
        if (sourceId == Long.MAX_VALUE) {
            if (((SkyLightStorage)this.lightStorage).isTopmostBlock(targetId)) {
                level = 0;
            } else {
                return 15;
            }
        }
        if (level >= 15) {
            return level;
        }
        MutableInt mutableInt = new MutableInt();
        BlockState _snowman2 = this.getStateForLighting(targetId, mutableInt);
        if (mutableInt.getValue() >= 15) {
            return 15;
        }
        int _snowman3 = BlockPos.unpackLongX(sourceId);
        int _snowman4 = BlockPos.unpackLongY(sourceId);
        int _snowman5 = BlockPos.unpackLongZ(sourceId);
        int _snowman6 = BlockPos.unpackLongX(targetId);
        int _snowman7 = BlockPos.unpackLongY(targetId);
        int _snowman8 = BlockPos.unpackLongZ(targetId);
        boolean _snowman9 = _snowman3 == _snowman6 && _snowman5 == _snowman8;
        int _snowman10 = Integer.signum(_snowman6 - _snowman3);
        int _snowman11 = Integer.signum(_snowman7 - _snowman4);
        int _snowman12 = Integer.signum(_snowman8 - _snowman5);
        Direction _snowman13 = sourceId == Long.MAX_VALUE ? Direction.DOWN : Direction.fromVector(_snowman10, _snowman11, _snowman12);
        BlockState _snowman14 = this.getStateForLighting(sourceId, null);
        if (_snowman13 != null) {
            voxelShape = this.getOpaqueShape(_snowman14, sourceId, _snowman13);
            if (VoxelShapes.unionCoversFullCube(voxelShape, _snowman = this.getOpaqueShape(_snowman2, targetId, _snowman13.getOpposite()))) {
                return 15;
            }
        } else {
            voxelShape = this.getOpaqueShape(_snowman14, sourceId, Direction.DOWN);
            if (VoxelShapes.unionCoversFullCube(voxelShape, VoxelShapes.empty())) {
                return 15;
            }
            int _snowman15 = _snowman9 ? -1 : 0;
            Direction _snowman16 = Direction.fromVector(_snowman10, _snowman15, _snowman12);
            if (_snowman16 == null) {
                return 15;
            }
            _snowman = this.getOpaqueShape(_snowman2, targetId, _snowman16.getOpposite());
            if (VoxelShapes.unionCoversFullCube(VoxelShapes.empty(), _snowman)) {
                return 15;
            }
        }
        boolean bl = _snowman = sourceId == Long.MAX_VALUE || _snowman9 && _snowman4 > _snowman7;
        if (_snowman && level == 0 && mutableInt.getValue() == 0) {
            return 0;
        }
        return level + Math.max(1, mutableInt.getValue());
    }

    @Override
    protected void propagateLevel(long id, int level, boolean decrease) {
        int n;
        long l = ChunkSectionPos.fromBlockPos(id);
        int _snowman2 = BlockPos.unpackLongY(id);
        int _snowman3 = ChunkSectionPos.getLocalCoord(_snowman2);
        int _snowman4 = ChunkSectionPos.getSectionCoord(_snowman2);
        if (_snowman3 != 0) {
            n = 0;
        } else {
            int n2 = 0;
            while (!((SkyLightStorage)this.lightStorage).hasSection(ChunkSectionPos.offset(l, 0, -n2 - 1, 0)) && ((SkyLightStorage)this.lightStorage).isAboveMinHeight(_snowman4 - n2 - 1)) {
                ++n2;
            }
            n = n2;
        }
        long _snowman5 = BlockPos.add(id, 0, -1 - n * 16, 0);
        long _snowman6 = ChunkSectionPos.fromBlockPos(_snowman5);
        if (l == _snowman6 || ((SkyLightStorage)this.lightStorage).hasSection(_snowman6)) {
            this.propagateLevel(id, _snowman5, level, decrease);
        }
        if (l == (_snowman = ChunkSectionPos.fromBlockPos(_snowman = BlockPos.offset(id, Direction.UP))) || ((SkyLightStorage)this.lightStorage).hasSection(_snowman)) {
            this.propagateLevel(id, _snowman, level, decrease);
        }
        block1: for (Direction direction : HORIZONTAL_DIRECTIONS) {
            int n3 = 0;
            do {
                if (l == (_snowman = ChunkSectionPos.fromBlockPos(_snowman = BlockPos.add(id, direction.getOffsetX(), -n3, direction.getOffsetZ())))) {
                    this.propagateLevel(id, _snowman, level, decrease);
                    continue block1;
                }
                if (!((SkyLightStorage)this.lightStorage).hasSection(_snowman)) continue;
                this.propagateLevel(id, _snowman, level, decrease);
            } while (++n3 <= n * 16);
        }
    }

    @Override
    protected int recalculateLevel(long id, long excludedId, int maxLevel) {
        int n = maxLevel;
        if (Long.MAX_VALUE != excludedId) {
            _snowman = this.getPropagatedLevel(Long.MAX_VALUE, id, 0);
            if (n > _snowman) {
                n = _snowman;
            }
            if (n == 0) {
                return n;
            }
        }
        long _snowman2 = ChunkSectionPos.fromBlockPos(id);
        ChunkNibbleArray _snowman3 = ((SkyLightStorage)this.lightStorage).getLightSection(_snowman2, true);
        for (Direction direction2 : DIRECTIONS) {
            int n2;
            long l;
            Direction direction2;
            long _snowman5 = BlockPos.offset(id, direction2);
            l = ChunkSectionPos.fromBlockPos(_snowman5);
            ChunkNibbleArray _snowman4 = _snowman2 == l ? _snowman3 : ((SkyLightStorage)this.lightStorage).getLightSection(l, true);
            if (_snowman4 != null) {
                if (_snowman5 == excludedId) continue;
                int n3 = this.getPropagatedLevel(_snowman5, id, this.getCurrentLevelFromSection(_snowman4, _snowman5));
                if (n > n3) {
                    n = n3;
                }
                if (n != 0) continue;
                return n;
            }
            if (direction2 == Direction.DOWN) continue;
            _snowman5 = BlockPos.removeChunkSectionLocalY(_snowman5);
            while (!((SkyLightStorage)this.lightStorage).hasSection(l) && !((SkyLightStorage)this.lightStorage).isAtOrAboveTopmostSection(l)) {
                l = ChunkSectionPos.offset(l, Direction.UP);
                _snowman5 = BlockPos.add(_snowman5, 0, 16, 0);
            }
            ChunkNibbleArray _snowman6 = ((SkyLightStorage)this.lightStorage).getLightSection(l, true);
            if (_snowman5 == excludedId) continue;
            if (_snowman6 != null) {
                n2 = this.getPropagatedLevel(_snowman5, id, this.getCurrentLevelFromSection(_snowman6, _snowman5));
            } else {
                int n4 = n2 = ((SkyLightStorage)this.lightStorage).isSectionEnabled(l) ? 0 : 15;
            }
            if (n > n2) {
                n = n2;
            }
            if (n != 0) continue;
            return n;
        }
        return n;
    }

    @Override
    protected void resetLevel(long id) {
        ((SkyLightStorage)this.lightStorage).updateAll();
        long l = ChunkSectionPos.fromBlockPos(id);
        if (((SkyLightStorage)this.lightStorage).hasSection(l)) {
            super.resetLevel(id);
        } else {
            id = BlockPos.removeChunkSectionLocalY(id);
            while (!((SkyLightStorage)this.lightStorage).hasSection(l) && !((SkyLightStorage)this.lightStorage).isAtOrAboveTopmostSection(l)) {
                l = ChunkSectionPos.offset(l, Direction.UP);
                id = BlockPos.add(id, 0, 16, 0);
            }
            if (((SkyLightStorage)this.lightStorage).hasSection(l)) {
                super.resetLevel(id);
            }
        }
    }

    @Override
    public String displaySectionLevel(long sectionPos) {
        return super.displaySectionLevel(sectionPos) + (((SkyLightStorage)this.lightStorage).isAtOrAboveTopmostSection(sectionPos) ? "*" : "");
    }
}

