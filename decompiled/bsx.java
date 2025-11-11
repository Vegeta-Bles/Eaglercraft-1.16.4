import com.google.common.hash.Hashing;

public class bsx {
   private final bsx.a a;
   private final long b;
   private final bta c;

   public bsx(bsx.a var1, long var2, bta var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public static long a(long var0) {
      return Hashing.sha256().hashLong(_snowman).asLong();
   }

   public bsx a(bsy var1) {
      return new bsx(_snowman, this.b, this.c);
   }

   public bsv a(fx var1) {
      return this.c.a(this.b, _snowman.u(), _snowman.v(), _snowman.w(), this.a);
   }

   public bsv a(double var1, double var3, double var5) {
      int _snowman = afm.c(_snowman) >> 2;
      int _snowmanx = afm.c(_snowman) >> 2;
      int _snowmanxx = afm.c(_snowman) >> 2;
      return this.a(_snowman, _snowmanx, _snowmanxx);
   }

   public bsv b(fx var1) {
      int _snowman = _snowman.u() >> 2;
      int _snowmanx = _snowman.v() >> 2;
      int _snowmanxx = _snowman.w() >> 2;
      return this.a(_snowman, _snowmanx, _snowmanxx);
   }

   public bsv a(int var1, int var2, int var3) {
      return this.a.b(_snowman, _snowman, _snowman);
   }

   public interface a {
      bsv b(int var1, int var2, int var3);
   }
}
