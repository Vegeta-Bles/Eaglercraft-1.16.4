/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ByteMap
 *  it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongList
 */
package net.minecraft.world.chunk.light;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.math.MathHelper;

public abstract class LevelPropagator {
    private final int levelCount;
    private final LongLinkedOpenHashSet[] pendingIdUpdatesByLevel;
    private final Long2ByteMap pendingUpdates;
    private int minPendingLevel;
    private volatile boolean hasPendingUpdates;

    protected LevelPropagator(int levelCount, final int expectedLevelSize, final int expectedTotalSize) {
        if (levelCount >= 254) {
            throw new IllegalArgumentException("Level count must be < 254.");
        }
        this.levelCount = levelCount;
        this.pendingIdUpdatesByLevel = new LongLinkedOpenHashSet[levelCount];
        for (int i = 0; i < levelCount; ++i) {
            this.pendingIdUpdatesByLevel[i] = new LongLinkedOpenHashSet(expectedLevelSize, 0.5f){

                protected void rehash(int newN) {
                    if (newN > expectedLevelSize) {
                        super.rehash(newN);
                    }
                }
            };
        }
        this.pendingUpdates = new Long2ByteOpenHashMap(expectedTotalSize, 0.5f){

            protected void rehash(int newN) {
                if (newN > expectedTotalSize) {
                    super.rehash(newN);
                }
            }
        };
        this.pendingUpdates.defaultReturnValue((byte)-1);
        this.minPendingLevel = levelCount;
    }

    private int minLevel(int a, int b) {
        int n = a;
        if (n > b) {
            n = b;
        }
        if (n > this.levelCount - 1) {
            n = this.levelCount - 1;
        }
        return n;
    }

    private void increaseMinPendingLevel(int maxLevel) {
        int n = this.minPendingLevel;
        this.minPendingLevel = maxLevel;
        for (_snowman = n + 1; _snowman < maxLevel; ++_snowman) {
            if (this.pendingIdUpdatesByLevel[_snowman].isEmpty()) continue;
            this.minPendingLevel = _snowman;
            break;
        }
    }

    protected void removePendingUpdate(long id) {
        int n = this.pendingUpdates.get(id) & 0xFF;
        if (n == 255) {
            return;
        }
        _snowman = this.getLevel(id);
        _snowman = this.minLevel(_snowman, n);
        this.removePendingUpdate(id, _snowman, this.levelCount, true);
        this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
    }

    public void removePendingUpdateIf(LongPredicate predicate) {
        LongArrayList longArrayList = new LongArrayList();
        this.pendingUpdates.keySet().forEach(arg_0 -> LevelPropagator.method_24207(predicate, (LongList)longArrayList, arg_0));
        longArrayList.forEach(this::removePendingUpdate);
    }

    private void removePendingUpdate(long id, int level, int levelCount, boolean removeFully) {
        if (removeFully) {
            this.pendingUpdates.remove(id);
        }
        this.pendingIdUpdatesByLevel[level].remove(id);
        if (this.pendingIdUpdatesByLevel[level].isEmpty() && this.minPendingLevel == level) {
            this.increaseMinPendingLevel(levelCount);
        }
    }

    private void addPendingUpdate(long id, int level, int targetLevel) {
        this.pendingUpdates.put(id, (byte)level);
        this.pendingIdUpdatesByLevel[targetLevel].add(id);
        if (this.minPendingLevel > targetLevel) {
            this.minPendingLevel = targetLevel;
        }
    }

    protected void resetLevel(long id) {
        this.updateLevel(id, id, this.levelCount - 1, false);
    }

    protected void updateLevel(long sourceId, long id, int level, boolean decrease) {
        this.updateLevel(sourceId, id, level, this.getLevel(id), this.pendingUpdates.get(id) & 0xFF, decrease);
        this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
    }

    private void updateLevel(long sourceId, long id, int level, int currentLevel, int pendingLevel, boolean decrease) {
        boolean bl;
        if (this.isMarker(id)) {
            return;
        }
        level = MathHelper.clamp(level, 0, this.levelCount - 1);
        currentLevel = MathHelper.clamp(currentLevel, 0, this.levelCount - 1);
        if (pendingLevel == 255) {
            bl = true;
            pendingLevel = currentLevel;
        } else {
            bl = false;
        }
        int n = decrease ? Math.min(pendingLevel, level) : MathHelper.clamp(this.recalculateLevel(id, sourceId, level), 0, this.levelCount - 1);
        _snowman = this.minLevel(currentLevel, pendingLevel);
        if (currentLevel != n) {
            _snowman = this.minLevel(currentLevel, n);
            if (_snowman != _snowman && !bl) {
                this.removePendingUpdate(id, _snowman, _snowman, false);
            }
            this.addPendingUpdate(id, n, _snowman);
        } else if (!bl) {
            this.removePendingUpdate(id, _snowman, this.levelCount, true);
        }
    }

    protected final void propagateLevel(long sourceId, long targetId, int level, boolean decrease) {
        int n = this.pendingUpdates.get(targetId) & 0xFF;
        _snowman = MathHelper.clamp(this.getPropagatedLevel(sourceId, targetId, level), 0, this.levelCount - 1);
        if (decrease) {
            this.updateLevel(sourceId, targetId, _snowman, this.getLevel(targetId), n, true);
        } else {
            int _snowman2;
            if (n == 255) {
                boolean bl = true;
                _snowman2 = MathHelper.clamp(this.getLevel(targetId), 0, this.levelCount - 1);
            } else {
                _snowman2 = n;
                bl = false;
            }
            if (_snowman == _snowman2) {
                this.updateLevel(sourceId, targetId, this.levelCount - 1, bl ? _snowman2 : this.getLevel(targetId), n, false);
            }
        }
    }

    protected final boolean hasPendingUpdates() {
        return this.hasPendingUpdates;
    }

    protected final int applyPendingUpdates(int maxSteps) {
        if (this.minPendingLevel >= this.levelCount) {
            return maxSteps;
        }
        while (this.minPendingLevel < this.levelCount && maxSteps > 0) {
            --maxSteps;
            LongLinkedOpenHashSet longLinkedOpenHashSet = this.pendingIdUpdatesByLevel[this.minPendingLevel];
            long _snowman2 = longLinkedOpenHashSet.removeFirstLong();
            int _snowman3 = MathHelper.clamp(this.getLevel(_snowman2), 0, this.levelCount - 1);
            if (longLinkedOpenHashSet.isEmpty()) {
                this.increaseMinPendingLevel(this.levelCount);
            }
            if ((_snowman = this.pendingUpdates.remove(_snowman2) & 0xFF) < _snowman3) {
                this.setLevel(_snowman2, _snowman);
                this.propagateLevel(_snowman2, _snowman, true);
                continue;
            }
            if (_snowman <= _snowman3) continue;
            this.addPendingUpdate(_snowman2, _snowman, this.minLevel(this.levelCount - 1, _snowman));
            this.setLevel(_snowman2, this.levelCount - 1);
            this.propagateLevel(_snowman2, _snowman3, false);
        }
        this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
        return maxSteps;
    }

    public int getPendingUpdateCount() {
        return this.pendingUpdates.size();
    }

    protected abstract boolean isMarker(long var1);

    protected abstract int recalculateLevel(long var1, long var3, int var5);

    protected abstract void propagateLevel(long var1, int var3, boolean var4);

    protected abstract int getLevel(long var1);

    protected abstract void setLevel(long var1, int var3);

    protected abstract int getPropagatedLevel(long var1, long var3, int var5);

    private static /* synthetic */ void method_24207(LongPredicate longPredicate, LongList longList, long l) {
        if (longPredicate.test(l)) {
            longList.add(l);
        }
    }
}

