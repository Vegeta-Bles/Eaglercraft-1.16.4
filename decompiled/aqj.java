import java.util.Random;

public class aqj {
   private final uv d;
   private final us<Integer> e;
   private final us<Boolean> f;
   public boolean a;
   public int b;
   public int c;

   public aqj(uv var1, us<Integer> var2, us<Boolean> var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public void a() {
      this.a = true;
      this.b = 0;
      this.c = this.d.a(this.e);
   }

   public boolean a(Random var1) {
      if (this.a) {
         return false;
      } else {
         this.a = true;
         this.b = 0;
         this.c = _snowman.nextInt(841) + 140;
         this.d.b(this.e, this.c);
         return true;
      }
   }

   public void a(md var1) {
      _snowman.a("Saddle", this.b());
   }

   public void b(md var1) {
      this.a(_snowman.q("Saddle"));
   }

   public void a(boolean var1) {
      this.d.b(this.f, _snowman);
   }

   public boolean b() {
      return this.d.a(this.f);
   }
}
