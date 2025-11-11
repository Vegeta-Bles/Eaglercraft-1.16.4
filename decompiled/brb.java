public class brb {
   private final fx a;
   private final buo b;
   private final int c;
   private final int d;

   public brb(fx var1, buo var2, int var3, int var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public fx a() {
      return this.a;
   }

   public buo b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (!(_snowman instanceof brb)) {
         return false;
      } else {
         brb _snowman = (brb)_snowman;
         return this.a.equals(_snowman.a) && this.c == _snowman.c && this.d == _snowman.d && this.b == _snowman.b;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a.hashCode();
      _snowman = 31 * _snowman + this.b.hashCode();
      _snowman = 31 * _snowman + this.c;
      return 31 * _snowman + this.d;
   }

   @Override
   public String toString() {
      return "TE(" + this.a + ")," + this.c + "," + this.d + "," + this.b;
   }
}
