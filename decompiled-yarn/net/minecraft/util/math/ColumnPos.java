package net.minecraft.util.math;

public class ColumnPos {
   public final int x;
   public final int z;

   public ColumnPos(int x, int z) {
      this.x = x;
      this.z = z;
   }

   public ColumnPos(BlockPos pos) {
      this.x = pos.getX();
      this.z = pos.getZ();
   }

   @Override
   public String toString() {
      return "[" + this.x + ", " + this.z + "]";
   }

   @Override
   public int hashCode() {
      int _snowman = 1664525 * this.x + 1013904223;
      int _snowmanx = 1664525 * (this.z ^ -559038737) + 1013904223;
      return _snowman ^ _snowmanx;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof ColumnPos)) {
         return false;
      } else {
         ColumnPos _snowmanx = (ColumnPos)_snowman;
         return this.x == _snowmanx.x && this.z == _snowmanx.z;
      }
   }
}
