import java.util.Random;

public class bzp extends buo implements bzu {
   private static final ddh d;
   private static final ddh e;
   private static final ddh f = buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   private static final ddh g = dde.b().a(0.0, -1.0, 0.0);
   public static final cfg a = cex.aB;
   public static final cey b = cex.C;
   public static final cey c = cex.b;

   protected bzp(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(7)).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b, c);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      if (!_snowman.a(_snowman.b().h())) {
         return _snowman.c(c) ? e : d;
      } else {
         return dde.b();
      }
   }

   @Override
   public ddh a_(ceh var1, brc var2, fx var3) {
      return dde.b();
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      return _snowman.m().b() == this.h();
   }

   @Override
   public ceh a(bny var1) {
      fx _snowman = _snowman.a();
      brx _snowmanx = _snowman.p();
      int _snowmanxx = a(_snowmanx, _snowman);
      return this.n().a(b, Boolean.valueOf(_snowmanx.b(_snowman).a() == cuy.c)).a(a, Integer.valueOf(_snowmanxx)).a(c, Boolean.valueOf(this.a(_snowmanx, _snowman, _snowmanxx)));
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.v) {
         _snowman.J().a(_snowman, this, 1);
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(b)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      if (!_snowman.s_()) {
         _snowman.J().a(_snowman, this, 1);
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      int _snowman = a(_snowman, _snowman);
      ceh _snowmanx = _snowman.a(a, Integer.valueOf(_snowman)).a(c, Boolean.valueOf(this.a(_snowman, _snowman, _snowman)));
      if (_snowmanx.c(a) == 7) {
         if (_snowman.c(a) == 7) {
            _snowman.c(new bcu(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5, _snowmanx.a(b, Boolean.valueOf(false))));
         } else {
            _snowman.b(_snowman, true);
         }
      } else if (_snowman != _snowmanx) {
         _snowman.a(_snowman, _snowmanx, 3);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      return a(_snowman, _snowman) < 7;
   }

   @Override
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      if (_snowman.a(dde.b(), _snowman, true) && !_snowman.b()) {
         return d;
      } else {
         return _snowman.c(a) != 0 && _snowman.c(c) && _snowman.a(g, _snowman, true) ? f : dde.a();
      }
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }

   private boolean a(brc var1, fx var2, int var3) {
      return _snowman > 0 && !_snowman.d_(_snowman.c()).a(this);
   }

   public static int a(brc var0, fx var1) {
      fx.a _snowman = _snowman.i().c(gc.a);
      ceh _snowmanx = _snowman.d_(_snowman);
      int _snowmanxx = 7;
      if (_snowmanx.a(bup.lQ)) {
         _snowmanxx = _snowmanx.c(a);
      } else if (_snowmanx.d(_snowman, _snowman, gc.b)) {
         return 0;
      }

      for (gc _snowmanxxx : gc.c.a) {
         ceh _snowmanxxxx = _snowman.d_(_snowman.a(_snowman, _snowmanxxx));
         if (_snowmanxxxx.a(bup.lQ)) {
            _snowmanxx = Math.min(_snowmanxx, _snowmanxxxx.c(a) + 1);
            if (_snowmanxx == 1) {
               break;
            }
         }
      }

      return _snowmanxx;
   }

   static {
      ddh _snowman = buo.a(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
      ddh _snowmanx = buo.a(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
      ddh _snowmanxx = buo.a(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
      ddh _snowmanxxx = buo.a(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
      ddh _snowmanxxxx = buo.a(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
      d = dde.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      ddh _snowmanxxxxx = buo.a(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
      ddh _snowmanxxxxxx = buo.a(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
      ddh _snowmanxxxxxxx = buo.a(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
      ddh _snowmanxxxxxxxx = buo.a(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
      e = dde.a(bzp.f, d, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
   }
}
