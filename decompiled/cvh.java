import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;

public final class cvh implements cvf {
   private final cwv a;
   private final Long2IntLinkedOpenHashMap b;
   private final int c;

   public cvh(Long2IntLinkedOpenHashMap var1, int var2, cwv var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.a = _snowman;
   }

   @Override
   public int a(int var1, int var2) {
      long _snowman = brd.a(_snowman, _snowman);
      synchronized (this.b) {
         int _snowmanx = this.b.get(_snowman);
         if (_snowmanx != Integer.MIN_VALUE) {
            return _snowmanx;
         } else {
            int _snowmanxx = this.a.apply(_snowman, _snowman);
            this.b.put(_snowman, _snowmanxx);
            if (this.b.size() > this.c) {
               for (int _snowmanxxx = 0; _snowmanxxx < this.c / 16; _snowmanxxx++) {
                  this.b.removeFirstInt();
               }
            }

            return _snowmanxx;
         }
      }
   }

   public int a() {
      return this.c;
   }
}
