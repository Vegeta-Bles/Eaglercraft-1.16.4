package net.minecraft.client.render.chunk;

import java.util.BitSet;
import java.util.Set;
import net.minecraft.util.math.Direction;

public class ChunkOcclusionData {
   private static final int DIRECTION_COUNT = Direction.values().length;
   private final BitSet visibility = new BitSet(DIRECTION_COUNT * DIRECTION_COUNT);

   public ChunkOcclusionData() {
   }

   public void addOpenEdgeFaces(Set<Direction> faces) {
      for (Direction _snowman : faces) {
         for (Direction _snowmanx : faces) {
            this.setVisibleThrough(_snowman, _snowmanx, true);
         }
      }
   }

   public void setVisibleThrough(Direction from, Direction to, boolean visible) {
      this.visibility.set(from.ordinal() + to.ordinal() * DIRECTION_COUNT, visible);
      this.visibility.set(to.ordinal() + from.ordinal() * DIRECTION_COUNT, visible);
   }

   public void fill(boolean visible) {
      this.visibility.set(0, this.visibility.size(), visible);
   }

   public boolean isVisibleThrough(Direction from, Direction to) {
      return this.visibility.get(from.ordinal() + to.ordinal() * DIRECTION_COUNT);
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append(' ');

      for (Direction _snowmanx : Direction.values()) {
         _snowman.append(' ').append(_snowmanx.toString().toUpperCase().charAt(0));
      }

      _snowman.append('\n');

      for (Direction _snowmanx : Direction.values()) {
         _snowman.append(_snowmanx.toString().toUpperCase().charAt(0));

         for (Direction _snowmanxx : Direction.values()) {
            if (_snowmanx == _snowmanxx) {
               _snowman.append("  ");
            } else {
               boolean _snowmanxxx = this.isVisibleThrough(_snowmanx, _snowmanxx);
               _snowman.append(' ').append((char)(_snowmanxxx ? 'Y' : 'n'));
            }
         }

         _snowman.append('\n');
      }

      return _snowman.toString();
   }
}
