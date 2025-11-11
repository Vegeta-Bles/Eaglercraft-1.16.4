import java.util.EnumSet;

public class ave extends avv {
   private final baz a;
   private bfw b;
   private final brx c;
   private final float d;
   private int e;
   private final azg f;

   public ave(baz var1, float var2) {
      this.a = _snowman;
      this.c = _snowman.l;
      this.d = _snowman;
      this.f = new azg().a((double)_snowman).a().b().d();
      this.a(EnumSet.of(avv.a.b));
   }

   @Override
   public boolean a() {
      this.b = this.c.a(this.f, this.a);
      return this.b == null ? false : this.a(this.b);
   }

   @Override
   public boolean b() {
      if (!this.b.aX()) {
         return false;
      } else {
         return this.a.h(this.b) > (double)(this.d * this.d) ? false : this.e > 0 && this.a(this.b);
      }
   }

   @Override
   public void c() {
      this.a.x(true);
      this.e = 40 + this.a.cY().nextInt(40);
   }

   @Override
   public void d() {
      this.a.x(false);
      this.b = null;
   }

   @Override
   public void e() {
      this.a.t().a(this.b.cD(), this.b.cG(), this.b.cH(), 10.0F, (float)this.a.O());
      this.e--;
   }

   private boolean a(bfw var1) {
      for (aot _snowman : aot.values()) {
         bmb _snowmanx = _snowman.b(_snowman);
         if (this.a.eK() && _snowmanx.b() == bmd.mL) {
            return true;
         }

         if (this.a.k(_snowmanx)) {
            return true;
         }
      }

      return false;
   }
}
