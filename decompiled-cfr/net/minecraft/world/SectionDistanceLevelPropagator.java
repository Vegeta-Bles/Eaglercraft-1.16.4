/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.light.LevelPropagator;

public abstract class SectionDistanceLevelPropagator
extends LevelPropagator {
    protected SectionDistanceLevelPropagator(int n, int n2, int n3) {
        super(n, n2, n3);
    }

    @Override
    protected boolean isMarker(long id) {
        return id == Long.MAX_VALUE;
    }

    @Override
    protected void propagateLevel(long id, int level, boolean decrease) {
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    long l = ChunkSectionPos.offset(id, i, _snowman, _snowman);
                    if (l == id) continue;
                    this.propagateLevel(id, l, level, decrease);
                }
            }
        }
    }

    @Override
    protected int recalculateLevel(long id, long excludedId, int maxLevel) {
        int n = maxLevel;
        for (_snowman = -1; _snowman <= 1; ++_snowman) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    long l = ChunkSectionPos.offset(id, _snowman, _snowman, _snowman);
                    if (l == id) {
                        l = Long.MAX_VALUE;
                    }
                    if (l == excludedId) continue;
                    int _snowman2 = this.getPropagatedLevel(l, id, this.getLevel(l));
                    if (n > _snowman2) {
                        n = _snowman2;
                    }
                    if (n != 0) continue;
                    return n;
                }
            }
        }
        return n;
    }

    @Override
    protected int getPropagatedLevel(long sourceId, long targetId, int level) {
        if (sourceId == Long.MAX_VALUE) {
            return this.getInitialLevel(targetId);
        }
        return level + 1;
    }

    protected abstract int getInitialLevel(long var1);

    public void update(long id, int level, boolean decrease) {
        this.updateLevel(Long.MAX_VALUE, id, level, decrease);
    }
}

