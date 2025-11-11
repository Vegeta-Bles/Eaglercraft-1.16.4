public class zw {
   public final int a;
   public final int b;

   public zw(int var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public zw(fx var1) {
      this.a = _snowman.u();
      this.b = _snowman.w();
   }

   @Override
   public String toString() {
      return "[" + this.a + ", " + this.b + "]";
   }

   @Override
   public int hashCode() {
      int _snowman = 1664525 * this.a + 1013904223;
      int _snowmanx = 1664525 * (this.b ^ -559038737) + 1013904223;
      return _snowman ^ _snowmanx;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof zw)) {
         return false;
      } else {
         zw _snowman = (zw)_snowman;
         return this.a == _snowman.a && this.b == _snowman.b;
      }
   }
}
