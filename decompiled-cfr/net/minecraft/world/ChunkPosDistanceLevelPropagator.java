/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.light.LevelPropagator;

public abstract class ChunkPosDistanceLevelPropagator
extends LevelPropagator {
    protected ChunkPosDistanceLevelPropagator(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected boolean isMarker(long id) {
        return id == ChunkPos.MARKER;
    }

    @Override
    protected void propagateLevel(long id, int level, boolean decrease) {
        ChunkPos chunkPos = new ChunkPos(id);
        int _snowman2 = chunkPos.x;
        int _snowman3 = chunkPos.z;
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                long l = ChunkPos.toLong(_snowman2 + i, _snowman3 + _snowman);
                if (l == id) continue;
                this.propagateLevel(id, l, level, decrease);
            }
        }
    }

    @Override
    protected int recalculateLevel(long id, long excludedId, int maxLevel) {
        int n = maxLevel;
        ChunkPos _snowman2 = new ChunkPos(id);
        _snowman = _snowman2.x;
        _snowman = _snowman2.z;
        for (_snowman = -1; _snowman <= 1; ++_snowman) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                long l = ChunkPos.toLong(_snowman + _snowman, _snowman + _snowman);
                if (l == id) {
                    l = ChunkPos.MARKER;
                }
                if (l == excludedId) continue;
                int _snowman3 = this.getPropagatedLevel(l, id, this.getLevel(l));
                if (n > _snowman3) {
                    n = _snowman3;
                }
                if (n != 0) continue;
                return n;
            }
        }
        return n;
    }

    @Override
    protected int getPropagatedLevel(long sourceId, long targetId, int level) {
        if (sourceId == ChunkPos.MARKER) {
            return this.getInitialLevel(targetId);
        }
        return level + 1;
    }

    protected abstract int getInitialLevel(long var1);

    public void updateLevel(long chunkPos, int distance, boolean decrease) {
        this.updateLevel(ChunkPos.MARKER, chunkPos, distance, decrease);
    }
}

