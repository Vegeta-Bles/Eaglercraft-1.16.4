package net.minecraft.world;

import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.light.LevelPropagator;

public abstract class SectionDistanceLevelPropagator extends LevelPropagator {
   protected SectionDistanceLevelPropagator(int _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected boolean isMarker(long id) {
      return id == Long.MAX_VALUE;
   }

   @Override
   protected void propagateLevel(long id, int level, boolean decrease) {
      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
               long _snowmanxxx = ChunkSectionPos.offset(id, _snowman, _snowmanx, _snowmanxx);
               if (_snowmanxxx != id) {
                  this.propagateLevel(id, _snowmanxxx, level, decrease);
               }
            }
         }
      }
   }

   @Override
   protected int recalculateLevel(long id, long excludedId, int maxLevel) {
      int _snowman = maxLevel;

      for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
         for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
            for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
               long _snowmanxxxx = ChunkSectionPos.offset(id, _snowmanx, _snowmanxx, _snowmanxxx);
               if (_snowmanxxxx == id) {
                  _snowmanxxxx = Long.MAX_VALUE;
               }

               if (_snowmanxxxx != excludedId) {
                  int _snowmanxxxxx = this.getPropagatedLevel(_snowmanxxxx, id, this.getLevel(_snowmanxxxx));
                  if (_snowman > _snowmanxxxxx) {
                     _snowman = _snowmanxxxxx;
                  }

                  if (_snowman == 0) {
                     return _snowman;
                  }
               }
            }
         }
      }

      return _snowman;
   }

   @Override
   protected int getPropagatedLevel(long sourceId, long targetId, int level) {
      return sourceId == Long.MAX_VALUE ? this.getInitialLevel(targetId) : level + 1;
   }

   protected abstract int getInitialLevel(long id);

   public void update(long id, int level, boolean decrease) {
      this.updateLevel(Long.MAX_VALUE, id, level, decrease);
   }
}
