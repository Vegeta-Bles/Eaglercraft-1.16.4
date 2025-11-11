import java.util.EnumSet;
import java.util.function.Predicate;

public class avd<T extends aqm> extends avv {
   protected final aqu a;
   private final double i;
   private final double j;
   protected T b;
   protected final float c;
   protected cxd d;
   protected final ayj e;
   protected final Class<T> f;
   protected final Predicate<aqm> g;
   protected final Predicate<aqm> h;
   private final azg k;

   public avd(aqu var1, Class<T> var2, float var3, double var4, double var6) {
      this(_snowman, _snowman, var0 -> true, _snowman, _snowman, _snowman, aqd.e::test);
   }

   public avd(aqu var1, Class<T> var2, Predicate<aqm> var3, float var4, double var5, double var7, Predicate<aqm> var9) {
      this.a = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.c = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.h = _snowman;
      this.e = _snowman.x();
      this.a(EnumSet.of(avv.a.a));
      this.k = new azg().a((double)_snowman).a(_snowman.and(_snowman));
   }

   public avd(aqu var1, Class<T> var2, float var3, double var4, double var6, Predicate<aqm> var8) {
      this(_snowman, _snowman, var0 -> true, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a() {
      this.b = this.a.l.b(this.f, this.k, this.a, this.a.cD(), this.a.cE(), this.a.cH(), this.a.cc().c((double)this.c, 3.0, (double)this.c));
      if (this.b == null) {
         return false;
      } else {
         dcn _snowman = azj.c(this.a, 16, 7, this.b.cA());
         if (_snowman == null) {
            return false;
         } else if (this.b.h(_snowman.b, _snowman.c, _snowman.d) < this.b.h(this.a)) {
            return false;
         } else {
            this.d = this.e.a(_snowman.b, _snowman.c, _snowman.d, 0);
            return this.d != null;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.e.m();
   }

   @Override
   public void c() {
      this.e.a(this.d, this.i);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (this.a.h((aqa)this.b) < 49.0) {
         this.a.x().a(this.j);
      } else {
         this.a.x().a(this.i);
      }
   }
}
