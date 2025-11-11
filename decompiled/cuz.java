import java.util.Random;
import javax.annotation.Nullable;

public abstract class cuz extends cuv {
   public cuz() {
   }

   @Override
   public cuw d() {
      return cuy.d;
   }

   @Override
   public cuw e() {
      return cuy.e;
   }

   @Override
   public blx a() {
      return bmd.lM;
   }

   @Override
   public void a(brx var1, fx var2, cux var3, Random var4) {
      fx _snowman = _snowman.b();
      if (_snowman.d_(_snowman).g() && !_snowman.d_(_snowman).i(_snowman, _snowman)) {
         if (_snowman.nextInt(100) == 0) {
            double _snowmanx = (double)_snowman.u() + _snowman.nextDouble();
            double _snowmanxx = (double)_snowman.v() + 1.0;
            double _snowmanxxx = (double)_snowman.w() + _snowman.nextDouble();
            _snowman.a(hh.M, _snowmanx, _snowmanxx, _snowmanxxx, 0.0, 0.0, 0.0);
            _snowman.a(_snowmanx, _snowmanxx, _snowmanxxx, adq.gY, adr.e, 0.2F + _snowman.nextFloat() * 0.2F, 0.9F + _snowman.nextFloat() * 0.15F, false);
         }

         if (_snowman.nextInt(200) == 0) {
            _snowman.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), adq.gW, adr.e, 0.2F + _snowman.nextFloat() * 0.2F, 0.9F + _snowman.nextFloat() * 0.15F, false);
         }
      }
   }

   @Override
   public void b(brx var1, fx var2, cux var3, Random var4) {
      if (_snowman.V().b(brt.a)) {
         int _snowman = _snowman.nextInt(3);
         if (_snowman > 0) {
            fx _snowmanx = _snowman;

            for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
               _snowmanx = _snowmanx.b(_snowman.nextInt(3) - 1, 1, _snowman.nextInt(3) - 1);
               if (!_snowman.p(_snowmanx)) {
                  return;
               }

               ceh _snowmanxxx = _snowman.d_(_snowmanx);
               if (_snowmanxxx.g()) {
                  if (this.a((brz)_snowman, _snowmanx)) {
                     _snowman.a(_snowmanx, bue.a(_snowman, _snowmanx));
                     return;
                  }
               } else if (_snowmanxxx.c().c()) {
                  return;
               }
            }
         } else {
            for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
               fx _snowmanxx = _snowman.b(_snowman.nextInt(3) - 1, 0, _snowman.nextInt(3) - 1);
               if (!_snowman.p(_snowmanxx)) {
                  return;
               }

               if (_snowman.w(_snowmanxx.b()) && this.b(_snowman, _snowmanxx)) {
                  _snowman.a(_snowmanxx.b(), bue.a(_snowman, _snowmanxx));
               }
            }
         }
      }
   }

   private boolean a(brz var1, fx var2) {
      for (gc _snowman : gc.values()) {
         if (this.b(_snowman, _snowman.a(_snowman))) {
            return true;
         }
      }

      return false;
   }

   private boolean b(brz var1, fx var2) {
      return _snowman.v() >= 0 && _snowman.v() < 256 && !_snowman.C(_snowman) ? false : _snowman.d_(_snowman).c().d();
   }

   @Nullable
   @Override
   public hf i() {
      return hh.j;
   }

   @Override
   protected void a(bry var1, fx var2, ceh var3) {
      this.a(_snowman, _snowman);
   }

   @Override
   public int b(brz var1) {
      return _snowman.k().d() ? 4 : 2;
   }

   @Override
   public ceh b(cux var1) {
      return bup.B.n().a(byb.a, Integer.valueOf(e(_snowman)));
   }

   @Override
   public boolean a(cuw var1) {
      return _snowman == cuy.e || _snowman == cuy.d;
   }

   @Override
   public int c(brz var1) {
      return _snowman.k().d() ? 1 : 2;
   }

   @Override
   public boolean a(cux var1, brc var2, fx var3, cuw var4, gc var5) {
      return _snowman.a(_snowman, _snowman) >= 0.44444445F && _snowman.a(aef.b);
   }

   @Override
   public int a(brz var1) {
      return _snowman.k().d() ? 10 : 30;
   }

   @Override
   public int a(brx var1, fx var2, cux var3, cux var4) {
      int _snowman = this.a(_snowman);
      if (!_snowman.c() && !_snowman.c() && !_snowman.c(a) && !_snowman.c(a) && _snowman.a((brc)_snowman, _snowman) > _snowman.a((brc)_snowman, _snowman) && _snowman.u_().nextInt(4) != 0) {
         _snowman *= 4;
      }

      return _snowman;
   }

   private void a(bry var1, fx var2) {
      _snowman.c(1501, _snowman, 0);
   }

   @Override
   protected boolean f() {
      return false;
   }

   @Override
   protected void a(bry var1, fx var2, ceh var3, gc var4, cux var5) {
      if (_snowman == gc.a) {
         cux _snowman = _snowman.b(_snowman);
         if (this.a(aef.c) && _snowman.a(aef.b)) {
            if (_snowman.b() instanceof byb) {
               _snowman.a(_snowman, bup.b.n(), 3);
            }

            this.a(_snowman, _snowman);
            return;
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected boolean j() {
      return true;
   }

   @Override
   protected float c() {
      return 100.0F;
   }

   public static class a extends cuz {
      public a() {
      }

      @Override
      protected void a(cei.a<cuw, cux> var1) {
         super.a(_snowman);
         _snowman.a(b);
      }

      @Override
      public int d(cux var1) {
         return _snowman.c(b);
      }

      @Override
      public boolean c(cux var1) {
         return false;
      }
   }

   public static class b extends cuz {
      public b() {
      }

      @Override
      public int d(cux var1) {
         return 8;
      }

      @Override
      public boolean c(cux var1) {
         return true;
      }
   }
}
