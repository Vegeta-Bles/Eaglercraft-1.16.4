import java.util.Optional;
import java.util.Random;

public abstract class bue extends buo {
   private final float b;
   protected static final ddh a = buo.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

   public bue(ceg.c var1, float var2) {
      super(_snowman);
      this.b = _snowman;
   }

   @Override
   public ceh a(bny var1) {
      return a(_snowman.p(), _snowman.a());
   }

   public static ceh a(brc var0, fx var1) {
      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      return cac.c(_snowmanx.b()) ? bup.bO.n() : ((bws)bup.bN).b(_snowman, _snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.nextInt(24) == 0) {
         _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.ei, adr.e, 1.0F + _snowman.nextFloat(), _snowman.nextFloat() * 0.7F + 0.3F, false);
      }

      fx _snowman = _snowman.c();
      ceh _snowmanx = _snowman.d_(_snowman);
      if (!this.e(_snowmanx) && !_snowmanx.d(_snowman, _snowman, gc.b)) {
         if (this.e(_snowman.d_(_snowman.f()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble() * 0.1F;
               double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble();
               double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble();
               _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.e(_snowman.d_(_snowman.g()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)(_snowman.u() + 1) - _snowman.nextDouble() * 0.1F;
               double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble();
               double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble();
               _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.e(_snowman.d_(_snowman.d()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble();
               double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble();
               double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble() * 0.1F;
               _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.e(_snowman.d_(_snowman.e()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble();
               double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble();
               double _snowmanxxxxx = (double)(_snowman.w() + 1) - _snowman.nextDouble() * 0.1F;
               _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.e(_snowman.d_(_snowman.b()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble();
               double _snowmanxxxx = (double)(_snowman.v() + 1) - _snowman.nextDouble() * 0.1F;
               double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble();
               _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
            double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble();
            double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble() * 0.5 + 0.5;
            double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble();
            _snowman.a(hh.L, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   protected abstract boolean e(ceh var1);

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.aD()) {
         _snowman.g(_snowman.al() + 1);
         if (_snowman.al() == 0) {
            _snowman.f(8);
         }

         _snowman.a(apk.a, this.b);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         if (a(_snowman)) {
            Optional<cxn> _snowman = cxn.a(_snowman, _snowman, gc.a.a);
            if (_snowman.isPresent()) {
               _snowman.get().b();
               return;
            }
         }

         if (!_snowman.a((brz)_snowman, _snowman)) {
            _snowman.a(_snowman, false);
         }
      }
   }

   private static boolean a(brx var0) {
      return _snowman.Y() == brx.g || _snowman.Y() == brx.h;
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      if (!_snowman.s_()) {
         _snowman.a(null, 1009, _snowman, 0);
      }
   }

   public static boolean a(brx var0, fx var1, gc var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return !_snowman.g() ? false : a(_snowman, _snowman).a((brz)_snowman, _snowman) || b(_snowman, _snowman, _snowman);
   }

   private static boolean b(brx var0, fx var1, gc var2) {
      if (!a(_snowman)) {
         return false;
      } else {
         fx.a _snowman = _snowman.i();
         boolean _snowmanx = false;

         for (gc _snowmanxx : gc.values()) {
            if (_snowman.d_(_snowman.g(_snowman).c(_snowmanxx)).a(bup.bK)) {
               _snowmanx = true;
               break;
            }
         }

         return _snowmanx && cxn.a(_snowman, _snowman, _snowman.h().n()).isPresent();
      }
   }
}
