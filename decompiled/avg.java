import java.util.function.Predicate;

public class avg extends avm {
   private final Predicate<aor> g;
   protected int a;
   protected int b = -1;
   protected int c = -1;

   public avg(aqn var1, Predicate<aor> var2) {
      super(_snowman);
      this.g = _snowman;
   }

   public avg(aqn var1, int var2, Predicate<aor> var3) {
      this(_snowman, _snowman);
      this.c = _snowman;
   }

   protected int f() {
      return Math.max(240, this.c);
   }

   @Override
   public boolean a() {
      if (!super.a()) {
         return false;
      } else {
         return !this.d.l.V().b(brt.b) ? false : this.a(this.d.l.ad()) && !this.g();
      }
   }

   @Override
   public void c() {
      super.c();
      this.a = 0;
   }

   @Override
   public boolean b() {
      return this.a <= this.f() && !this.g() && this.e.a(this.d.cA(), 2.0) && this.a(this.d.l.ad());
   }

   @Override
   public void d() {
      super.d();
      this.d.l.a(this.d.Y(), this.e, -1);
   }

   @Override
   public void e() {
      super.e();
      if (this.d.cY().nextInt(20) == 0) {
         this.d.l.c(1019, this.e, 0);
         if (!this.d.ai) {
            this.d.a(this.d.dX());
         }
      }

      this.a++;
      int _snowman = (int)((float)this.a / (float)this.f() * 10.0F);
      if (_snowman != this.b) {
         this.d.l.a(this.d.Y(), this.e, _snowman);
         this.b = _snowman;
      }

      if (this.a == this.f() && this.a(this.d.l.ad())) {
         this.d.l.a(this.e, false);
         this.d.l.c(1021, this.e, 0);
         this.d.l.c(2001, this.e, buo.i(this.d.l.d_(this.e)));
      }
   }

   private boolean a(aor var1) {
      return this.g.test(_snowman);
   }
}
