public class eap {
   private final long[] a;
   private int b;
   private int c;

   public eap(int var1) {
      this.a = new long[_snowman];
   }

   public long a(long var1) {
      if (this.b < this.a.length) {
         this.b++;
      }

      this.a[this.c] = _snowman;
      this.c = (this.c + 1) % this.a.length;
      long _snowman = Long.MAX_VALUE;
      long _snowmanx = Long.MIN_VALUE;
      long _snowmanxx = 0L;

      for (int _snowmanxxx = 0; _snowmanxxx < this.b; _snowmanxxx++) {
         long _snowmanxxxx = this.a[_snowmanxxx];
         _snowmanxx += _snowmanxxxx;
         _snowman = Math.min(_snowman, _snowmanxxxx);
         _snowmanx = Math.max(_snowmanx, _snowmanxxxx);
      }

      if (this.b > 2) {
         _snowmanxx -= _snowman + _snowmanx;
         return _snowmanxx / (long)(this.b - 2);
      } else {
         return _snowmanxx > 0L ? (long)this.b / _snowmanxx : 0L;
      }
   }
}
