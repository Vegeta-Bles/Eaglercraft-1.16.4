package net.minecraft.world.storage;

import java.util.BitSet;

public class SectorMap {
   private final BitSet field_20433 = new BitSet();

   public SectorMap() {
   }

   public void allocate(int _snowman, int _snowman) {
      this.field_20433.set(_snowman, _snowman + _snowman);
   }

   public void free(int _snowman, int _snowman) {
      this.field_20433.clear(_snowman, _snowman + _snowman);
   }

   public int allocate(int _snowman) {
      int _snowmanx = 0;

      while (true) {
         int _snowmanxx = this.field_20433.nextClearBit(_snowmanx);
         int _snowmanxxx = this.field_20433.nextSetBit(_snowmanxx);
         if (_snowmanxxx == -1 || _snowmanxxx - _snowmanxx >= _snowman) {
            this.allocate(_snowmanxx, _snowman);
            return _snowmanxx;
         }

         _snowmanx = _snowmanxxx;
      }
   }
}
