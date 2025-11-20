package net.minecraft.world;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.light.LevelPropagator;

public abstract class ChunkPosDistanceLevelPropagator extends LevelPropagator {
   protected ChunkPosDistanceLevelPropagator(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean isMarker(long id) {
      return id == ChunkPos.MARKER;
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      ChunkPos _snowman = new ChunkPos(id);
      int _snowmanx = _snowman.x;
      int _snowmanxx = _snowman.z;

      for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
         for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
            long _snowmanxxxxx = ChunkPos.toLong(_snowmanx + _snowmanxxx, _snowmanxx + _snowmanxxxx);
            if (_snowmanxxxxx != id) {
               this.propagateLevel(id, _snowmanxxxxx, level, decrease);
            }
         }
      }
   }

   @Override
   protected int recalculateLevel(long id, long excludedId, int maxLevel) {
      int _snowman = maxLevel;
      ChunkPos _snowmanx = new ChunkPos(id);
      int _snowmanxx = _snowmanx.x;
      int _snowmanxxx = _snowmanx.z;

      for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
         for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
            long _snowmanxxxxxx = ChunkPos.toLong(_snowmanxx + _snowmanxxxx, _snowmanxxx + _snowmanxxxxx);
            if (_snowmanxxxxxx == id) {
               _snowmanxxxxxx = ChunkPos.MARKER;
            }

            if (_snowmanxxxxxx != excludedId) {
               int _snowmanxxxxxxx = this.getPropagatedLevel(_snowmanxxxxxx, id, this.getLevel(_snowmanxxxxxx));
               if (_snowman > _snowmanxxxxxxx) {
                  _snowman = _snowmanxxxxxxx;
               }

               if (_snowman == 0) {
                  return _snowman;
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected int getPropagatedLevel(long sourceId, long targetId, int level) {
      return sourceId == ChunkPos.MARKER ? this.getInitialLevel(targetId) : level + 1;
   }

   protected abstract int getInitialLevel(long id);

   public void updateLevel(long chunkPos, int distance, boolean decrease) {
      this.updateLevel(ChunkPos.MARKER, chunkPos, distance, decrease);
   }
}
