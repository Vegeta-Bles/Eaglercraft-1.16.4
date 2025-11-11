import java.util.Random;

public class bus extends buo implements but {
   public static final cey a = cex.e;

   public bus(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Boolean.valueOf(true)));
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      ceh _snowman = _snowman.d_(_snowman.b());
      if (_snowman.g()) {
         _snowman.k(_snowman.c(a));
         if (!_snowman.v) {
            aag _snowmanx = (aag)_snowman;

            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               _snowmanx.a(hh.Z, (double)_snowman.u() + _snowman.t.nextDouble(), (double)(_snowman.v() + 1), (double)_snowman.w() + _snowman.t.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
               _snowmanx.a(hh.e, (double)_snowman.u() + _snowman.t.nextDouble(), (double)(_snowman.v() + 1), (double)_snowman.w() + _snowman.t.nextDouble(), 1, 0.0, 0.01, 0.0, 0.2);
            }
         }
      } else {
         _snowman.l(_snowman.c(a));
      }
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      a(_snowman, _snowman.b(), a((brc)_snowman, _snowman.c()));
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      a(_snowman, _snowman.b(), a((brc)_snowman, _snowman));
   }

   @Override
   public cux d(ceh var1) {
      return cuy.c.a(false);
   }

   public static void a(bry var0, fx var1, boolean var2) {
      if (a(_snowman, _snowman)) {
         _snowman.a(_snowman, bup.lc.n().a(a, Boolean.valueOf(_snowman)), 2);
      }
   }

   public static boolean a(bry var0, fx var1) {
      cux _snowman = _snowman.b(_snowman);
      return _snowman.d_(_snowman).a(bup.A) && _snowman.e() >= 8 && _snowman.b();
   }

   private static boolean a(brc var0, fx var1) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman.a(bup.lc) ? _snowman.c(a) : !_snowman.a(bup.cM);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      double _snowman = (double)_snowman.u();
      double _snowmanx = (double)_snowman.v();
      double _snowmanxx = (double)_snowman.w();
      if (_snowman.c(a)) {
         _snowman.b(hh.ac, _snowman + 0.5, _snowmanx + 0.8, _snowmanxx, 0.0, 0.0, 0.0);
         if (_snowman.nextInt(200) == 0) {
            _snowman.a(_snowman, _snowmanx, _snowmanxx, adq.bh, adr.e, 0.2F + _snowman.nextFloat() * 0.2F, 0.9F + _snowman.nextFloat() * 0.15F, false);
         }
      } else {
         _snowman.b(hh.ad, _snowman + 0.5, _snowmanx, _snowmanxx + 0.5, 0.0, 0.04, 0.0);
         _snowman.b(hh.ad, _snowman + (double)_snowman.nextFloat(), _snowmanx + (double)_snowman.nextFloat(), _snowmanxx + (double)_snowman.nextFloat(), 0.0, 0.04, 0.0);
         if (_snowman.nextInt(200) == 0) {
            _snowman.a(_snowman, _snowmanx, _snowmanxx, adq.bf, adr.e, 0.2F + _snowman.nextFloat() * 0.2F, 0.9F + _snowman.nextFloat() * 0.15F, false);
         }
      }
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         return bup.A.n();
      } else {
         if (_snowman == gc.a) {
            _snowman.a(_snowman, bup.lc.n().a(a, Boolean.valueOf(a((brc)_snowman, _snowman))), 2);
         } else if (_snowman == gc.b && !_snowman.a(bup.lc) && a(_snowman, _snowman)) {
            _snowman.J().a(_snowman, this, 5);
         }

         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman.c());
      return _snowman.a(bup.lc) || _snowman.a(bup.iJ) || _snowman.a(bup.cM);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return dde.a();
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.a;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a);
   }

   @Override
   public cuw b(bry var1, fx var2, ceh var3) {
      _snowman.a(_snowman, bup.a.n(), 11);
      return cuy.c;
   }
}
