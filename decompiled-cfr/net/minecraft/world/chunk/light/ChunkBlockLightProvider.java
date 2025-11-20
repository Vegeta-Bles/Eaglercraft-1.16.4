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
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.BlockLightStorage;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import org.apache.commons.lang3.mutable.MutableInt;

public final class ChunkBlockLightProvider
extends ChunkLightProvider<BlockLightStorage.Data, BlockLightStorage> {
    private static final Direction[] DIRECTIONS = Direction.values();
    private final BlockPos.Mutable mutablePos = new BlockPos.Mutable();

    public ChunkBlockLightProvider(ChunkProvider chunkProvider) {
        super(chunkProvider, LightType.BLOCK, new BlockLightStorage(chunkProvider));
    }

    private int getLightSourceLuminance(long blockPos) {
        int n = BlockPos.unpackLongX(blockPos);
        _snowman = BlockPos.unpackLongY(blockPos);
        _snowman = BlockPos.unpackLongZ(blockPos);
        BlockView _snowman2 = this.chunkProvider.getChunk(n >> 4, _snowman >> 4);
        if (_snowman2 != null) {
            return _snowman2.getLuminance(this.mutablePos.set(n, _snowman, _snowman));
        }
        return 0;
    }

    @Override
    protected int getPropagatedLevel(long sourceId, long targetId, int level) {
        if (targetId == Long.MAX_VALUE) {
            return 15;
        }
        if (sourceId == Long.MAX_VALUE) {
            return level + 15 - this.getLightSourceLuminance(targetId);
        }
        if (level >= 15) {
            return level;
        }
        int n = Integer.signum(BlockPos.unpackLongX(targetId) - BlockPos.unpackLongX(sourceId));
        Direction _snowman2 = Direction.fromVector(n, _snowman = Integer.signum(BlockPos.unpackLongY(targetId) - BlockPos.unpackLongY(sourceId)), _snowman = Integer.signum(BlockPos.unpackLongZ(targetId) - BlockPos.unpackLongZ(sourceId)));
        if (_snowman2 == null) {
            return 15;
        }
        MutableInt _snowman3 = new MutableInt();
        BlockState _snowman4 = this.getStateForLighting(targetId, _snowman3);
        if (_snowman3.getValue() >= 15) {
            return 15;
        }
        BlockState _snowman5 = this.getStateForLighting(sourceId, null);
        VoxelShape _snowman6 = this.getOpaqueShape(_snowman5, sourceId, _snowman2);
        if (VoxelShapes.unionCoversFullCube(_snowman6, _snowman = this.getOpaqueShape(_snowman4, targetId, _snowman2.getOpposite()))) {
            return 15;
        }
        return level + Math.max(1, _snowman3.getValue());
    }

    @Override
    protected void propagateLevel(long id, int level, boolean decrease) {
        long l = ChunkSectionPos.fromBlockPos(id);
        for (Direction direction : DIRECTIONS) {
            long l2 = BlockPos.offset(id, direction);
            _snowman = ChunkSectionPos.fromBlockPos(l2);
            if (l != _snowman && !((BlockLightStorage)this.lightStorage).hasSection(_snowman)) continue;
            this.propagateLevel(id, l2, level, decrease);
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
        ChunkNibbleArray _snowman3 = ((BlockLightStorage)this.lightStorage).getLightSection(_snowman2, true);
        for (Direction direction : DIRECTIONS) {
            long l = BlockPos.offset(id, direction);
            if (l == excludedId || (_snowman = _snowman2 == (_snowman = ChunkSectionPos.fromBlockPos(l)) ? _snowman3 : ((BlockLightStorage)this.lightStorage).getLightSection(_snowman, true)) == null) continue;
            int _snowman4 = this.getPropagatedLevel(l, id, this.getCurrentLevelFromSection(_snowman, l));
            if (n > _snowman4) {
                n = _snowman4;
            }
            if (n != 0) continue;
            return n;
        }
        return n;
    }

    @Override
    public void addLightSource(BlockPos pos, int level) {
        ((BlockLightStorage)this.lightStorage).updateAll();
        this.updateLevel(Long.MAX_VALUE, pos.asLong(), 15 - level, true);
    }
}

