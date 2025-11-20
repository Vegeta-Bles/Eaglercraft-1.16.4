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

   protected LevelPropagator(int levelCount, int expectedLevelSize, int expectedTotalSize) {
      if (levelCount >= 254) {
         throw new IllegalArgumentException("Level count must be < 254.");
      } else {
         this.levelCount = levelCount;
         this.pendingIdUpdatesByLevel = new LongLinkedOpenHashSet[levelCount];

         for (int _snowman = 0; _snowman < levelCount; _snowman++) {
            this.pendingIdUpdatesByLevel[_snowman] = new LongLinkedOpenHashSet(expectedLevelSize, 0.5F) {
               protected void rehash(int newN) {
                  if (newN > expectedLevelSize) {
                     super.rehash(newN);
                  }
               }
            };
         }

         this.pendingUpdates = new Long2ByteOpenHashMap(expectedTotalSize, 0.5F) {
            protected void rehash(int newN) {
               if (newN > expectedTotalSize) {
                  super.rehash(newN);
               }
            }
         };
         this.pendingUpdates.defaultReturnValue((byte)-1);
         this.minPendingLevel = levelCount;
      }
   }

   private int minLevel(int a, int b) {
      int _snowman = a;
      if (a > b) {
         _snowman = b;
      }

      if (_snowman > this.levelCount - 1) {
         _snowman = this.levelCount - 1;
      }

      return _snowman;
   }

   private void increaseMinPendingLevel(int maxLevel) {
      int _snowman = this.minPendingLevel;
      this.minPendingLevel = maxLevel;

      for (int _snowmanx = _snowman + 1; _snowmanx < maxLevel; _snowmanx++) {
         if (!this.pendingIdUpdatesByLevel[_snowmanx].isEmpty()) {
            this.minPendingLevel = _snowmanx;
            break;
         }
      }
   }

   protected void removePendingUpdate(long id) {
      int _snowman = this.pendingUpdates.get(id) & 255;
      if (_snowman != 255) {
         int _snowmanx = this.getLevel(id);
         int _snowmanxx = this.minLevel(_snowmanx, _snowman);
         this.removePendingUpdate(id, _snowmanxx, this.levelCount, true);
         this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
      }
   }

   public void removePendingUpdateIf(LongPredicate predicate) {
      LongList _snowman = new LongArrayList();
      this.pendingUpdates.keySet().forEach(_snowmanxx -> {
         if (predicate.test(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      });
      _snowman.forEach(this::removePendingUpdate);
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
      this.updateLevel(sourceId, id, level, this.getLevel(id), this.pendingUpdates.get(id) & 255, decrease);
      this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
   }

   private void updateLevel(long sourceId, long id, int level, int currentLevel, int pendingLevel, boolean decrease) {
      if (!this.isMarker(id)) {
         level = MathHelper.clamp(level, 0, this.levelCount - 1);
         currentLevel = MathHelper.clamp(currentLevel, 0, this.levelCount - 1);
         boolean _snowman;
         if (pendingLevel == 255) {
            _snowman = true;
            pendingLevel = currentLevel;
         } else {
            _snowman = false;
         }

         int _snowmanx;
         if (decrease) {
            _snowmanx = Math.min(pendingLevel, level);
         } else {
            _snowmanx = MathHelper.clamp(this.recalculateLevel(id, sourceId, level), 0, this.levelCount - 1);
         }

         int _snowmanxx = this.minLevel(currentLevel, pendingLevel);
         if (currentLevel != _snowmanx) {
            int _snowmanxxx = this.minLevel(currentLevel, _snowmanx);
            if (_snowmanxx != _snowmanxxx && !_snowman) {
               this.removePendingUpdate(id, _snowmanxx, _snowmanxxx, false);
            }

            this.addPendingUpdate(id, _snowmanx, _snowmanxxx);
         } else if (!_snowman) {
            this.removePendingUpdate(id, _snowmanxx, this.levelCount, true);
         }
      }
   }

   protected final void propagateLevel(long sourceId, long targetId, int level, boolean decrease) {
      int _snowman = this.pendingUpdates.get(targetId) & 255;
      int _snowmanx = MathHelper.clamp(this.getPropagatedLevel(sourceId, targetId, level), 0, this.levelCount - 1);
      if (decrease) {
         this.updateLevel(sourceId, targetId, _snowmanx, this.getLevel(targetId), _snowman, true);
      } else {
         int _snowmanxx;
         boolean _snowmanxxx;
         if (_snowman == 255) {
            _snowmanxxx = true;
            _snowmanxx = MathHelper.clamp(this.getLevel(targetId), 0, this.levelCount - 1);
         } else {
            _snowmanxx = _snowman;
            _snowmanxxx = false;
         }

         if (_snowmanx == _snowmanxx) {
            this.updateLevel(sourceId, targetId, this.levelCount - 1, _snowmanxxx ? _snowmanxx : this.getLevel(targetId), _snowman, false);
         }
      }
   }

   protected final boolean hasPendingUpdates() {
      return this.hasPendingUpdates;
   }

   protected final int applyPendingUpdates(int maxSteps) {
      if (this.minPendingLevel >= this.levelCount) {
         return maxSteps;
      } else {
         while (this.minPendingLevel < this.levelCount && maxSteps > 0) {
            maxSteps--;
            LongLinkedOpenHashSet _snowman = this.pendingIdUpdatesByLevel[this.minPendingLevel];
            long _snowmanx = _snowman.removeFirstLong();
            int _snowmanxx = MathHelper.clamp(this.getLevel(_snowmanx), 0, this.levelCount - 1);
            if (_snowman.isEmpty()) {
               this.increaseMinPendingLevel(this.levelCount);
            }

            int _snowmanxxx = this.pendingUpdates.remove(_snowmanx) & 255;
            if (_snowmanxxx < _snowmanxx) {
               this.setLevel(_snowmanx, _snowmanxxx);
               this.propagateLevel(_snowmanx, _snowmanxxx, true);
            } else if (_snowmanxxx > _snowmanxx) {
               this.addPendingUpdate(_snowmanx, _snowmanxxx, this.minLevel(this.levelCount - 1, _snowmanxxx));
               this.setLevel(_snowmanx, this.levelCount - 1);
               this.propagateLevel(_snowmanx, _snowmanxx, false);
            }
         }

         this.hasPendingUpdates = this.minPendingLevel < this.levelCount;
         return maxSteps;
      }
   }

   public int getPendingUpdateCount() {
      return this.pendingUpdates.size();
   }

   protected abstract boolean isMarker(long id);

   protected abstract int recalculateLevel(long id, long excludedId, int maxLevel);

   protected abstract void propagateLevel(long id, int level, boolean decrease);

   protected abstract int getLevel(long id);

   protected abstract void setLevel(long id, int level);

   protected abstract int getPropagatedLevel(long sourceId, long targetId, int level);
}
