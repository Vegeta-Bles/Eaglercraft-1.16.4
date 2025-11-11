import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;

public class avi extends avv {
   private static final azg d = new azg().a(8.0).a().b().c();
   protected final azz a;
   private final Class<? extends azz> e;
   protected final brx b;
   protected azz c;
   private int f;
   private final double g;

   public avi(azz var1, double var2) {
      this(_snowman, _snowman, (Class<? extends azz>)_snowman.getClass());
   }

   public avi(azz var1, double var2, Class<? extends azz> var4) {
      this.a = _snowman;
      this.b = _snowman.l;
      this.e = _snowman;
      this.g = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      if (!this.a.eS()) {
         return false;
      } else {
         this.c = this.h();
         return this.c != null;
      }
   }

   @Override
   public boolean b() {
      return this.c.aX() && this.c.eS() && this.f < 60;
   }

   @Override
   public void d() {
      this.c = null;
      this.f = 0;
   }

   @Override
   public void e() {
      this.a.t().a(this.c, 10.0F, (float)this.a.O());
      this.a.x().a(this.c, this.g);
      this.f++;
      if (this.f >= 60 && this.a.h(this.c) < 9.0) {
         this.g();
      }
   }

   @Nullable
   private azz h() {
      List<azz> _snowman = this.b.a(this.e, d, this.a, this.a.cc().g(8.0));
      double _snowmanx = Double.MAX_VALUE;
      azz _snowmanxx = null;

      for (azz _snowmanxxx : _snowman) {
         if (this.a.a(_snowmanxxx) && this.a.h(_snowmanxxx) < _snowmanx) {
            _snowmanxx = _snowmanxxx;
            _snowmanx = this.a.h(_snowmanxxx);
         }
      }

      return _snowmanxx;
   }

   protected void g() {
      this.a.a((aag)this.b, this.c);
   }
}
