public class ga {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private int k;

   public ga(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman - _snowman + 1;
      this.e = _snowman - _snowman + 1;
      this.f = _snowman - _snowman + 1;
      this.g = this.d * this.e * this.f;
   }

   public boolean a() {
      if (this.h == this.g) {
         return false;
      } else {
         this.i = this.h % this.d;
         int _snowman = this.h / this.d;
         this.j = _snowman % this.e;
         this.k = _snowman / this.e;
         this.h++;
         return true;
      }
   }

   public int b() {
      return this.a + this.i;
   }

   public int c() {
      return this.b + this.j;
   }

   public int d() {
      return this.c + this.k;
   }

   public int e() {
      int _snowman = 0;
      if (this.i == 0 || this.i == this.d - 1) {
         _snowman++;
      }

      if (this.j == 0 || this.j == this.e - 1) {
         _snowman++;
      }

      if (this.k == 0 || this.k == this.f - 1) {
         _snowman++;
      }

      return _snowman;
   }
}
