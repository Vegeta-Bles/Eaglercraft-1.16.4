public class cgl {
   public final byte[] a;
   private final int b;
   private final int c;

   public cgl(byte[] var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman + 4;
   }

   public int a(int var1, int var2, int var3) {
      int _snowman = _snowman << this.c | _snowman << this.b | _snowman;
      int _snowmanx = _snowman >> 1;
      int _snowmanxx = _snowman & 1;
      return _snowmanxx == 0 ? this.a[_snowmanx] & 15 : this.a[_snowmanx] >> 4 & 15;
   }
}
