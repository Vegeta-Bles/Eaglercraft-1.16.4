import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class buy extends bud implements bzu {
   protected static final ddh a = buo.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
   public static final cey b = cex.r;
   public static final cey c = cex.y;
   public static final cey d = cex.C;
   public static final cfb e = cex.O;
   private static final ddh f = buo.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   private final boolean g;
   private final int h;

   public buy(boolean var1, int var2, ceg.c var3) {
      super(_snowman);
      this.g = _snowman;
      this.h = _snowman;
      this.j(this.n.b().a(b, Boolean.valueOf(true)).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)).a(e, gc.c));
   }

   @Override
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccm) {
         ccm _snowmanx = (ccm)_snowman;
         bmb _snowmanxx = _snowman.b(_snowman);
         Optional<boh> _snowmanxxx = _snowmanx.a(_snowmanxx);
         if (_snowmanxxx.isPresent()) {
            if (!_snowman.v && _snowmanx.a(_snowman.bC.d ? _snowmanxx.i() : _snowmanxx, _snowmanxxx.get().e())) {
               _snowman.a(aea.au);
               return aou.a;
            }

            return aou.b;
         }
      }

      return aou.c;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
      if (!_snowman.aD() && _snowman.c(b) && _snowman instanceof aqm && !bpu.i((aqm)_snowman)) {
         _snowman.a(apk.a, (float)this.h);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (!_snowman.a(_snowman.b())) {
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccm) {
            aoq.a(_snowman, _snowman, ((ccm)_snowman).d());
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      bry _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      boolean _snowmanxx = _snowman.b(_snowmanx).a() == cuy.c;
      return this.n().a(d, Boolean.valueOf(_snowmanxx)).a(c, Boolean.valueOf(this.l(_snowman.d_(_snowmanx.c())))).a(b, Boolean.valueOf(!_snowmanxx)).a(e, _snowman.f());
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (_snowman.c(d)) {
         _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
      }

      return _snowman == gc.a ? _snowman.a(c, Boolean.valueOf(this.l(_snowman))) : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private boolean l(ceh var1) {
      return _snowman.a(bup.gA);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return a;
   }

   @Override
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Override
   public void a(ceh var1, brx var2, fx var3, Random var4) {
      if (_snowman.c(b)) {
         if (_snowman.nextInt(10) == 0) {
            _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, adq.bp, adr.e, 0.5F + _snowman.nextFloat(), _snowman.nextFloat() * 0.7F + 0.6F, false);
         }

         if (this.g && _snowman.nextInt(5) == 0) {
            for (int _snowman = 0; _snowman < _snowman.nextInt(1) + 1; _snowman++) {
               _snowman.a(hh.M, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, (double)(_snowman.nextFloat() / 2.0F), 5.0E-5, (double)(_snowman.nextFloat() / 2.0F));
            }
         }
      }
   }

   public static void c(bry var0, fx var1, ceh var2) {
      if (_snowman.s_()) {
         for (int _snowman = 0; _snowman < 20; _snowman++) {
            a((brx)_snowman, _snowman, _snowman.c(c), true);
         }
      }

      ccj _snowman = _snowman.c(_snowman);
      if (_snowman instanceof ccm) {
         ((ccm)_snowman).f();
      }
   }

   @Override
   public boolean a(bry var1, fx var2, ceh var3, cux var4) {
      if (!_snowman.c(cex.C) && _snowman.a() == cuy.c) {
         boolean _snowman = _snowman.c(b);
         if (_snowman) {
            if (!_snowman.s_()) {
               _snowman.a(null, _snowman, adq.eM, adr.e, 1.0F, 1.0F);
            }

            c(_snowman, _snowman, _snowman);
         }

         _snowman.a(_snowman, _snowman.a(d, Boolean.valueOf(true)).a(b, Boolean.valueOf(false)), 3);
         _snowman.I().a(_snowman, _snowman.a(), _snowman.a().a(_snowman));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(brx var1, ceh var2, dcj var3, bgm var4) {
      if (!_snowman.v && _snowman.bq()) {
         aqa _snowman = _snowman.v();
         boolean _snowmanx = _snowman == null || _snowman instanceof bfw || _snowman.V().b(brt.b);
         if (_snowmanx && !_snowman.c(b) && !_snowman.c(d)) {
            fx _snowmanxx = _snowman.a();
            _snowman.a(_snowmanxx, _snowman.a(cex.r, Boolean.valueOf(true)), 11);
         }
      }
   }

   public static void a(brx var0, fx var1, boolean var2, boolean var3) {
      Random _snowman = _snowman.u_();
      hi _snowmanx = _snowman ? hh.ah : hh.ag;
      _snowman.b(
         _snowmanx,
         true,
         (double)_snowman.u() + 0.5 + _snowman.nextDouble() / 3.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
         (double)_snowman.v() + _snowman.nextDouble() + _snowman.nextDouble(),
         (double)_snowman.w() + 0.5 + _snowman.nextDouble() / 3.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
         0.0,
         0.07,
         0.0
      );
      if (_snowman) {
         _snowman.a(
            hh.S,
            (double)_snowman.u() + 0.25 + _snowman.nextDouble() / 2.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
            (double)_snowman.v() + 0.4,
            (double)_snowman.w() + 0.25 + _snowman.nextDouble() / 2.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
            0.0,
            0.005,
            0.0
         );
      }
   }

   public static boolean a(brx var0, fx var1) {
      for (int _snowman = 1; _snowman <= 5; _snowman++) {
         fx _snowmanx = _snowman.c(_snowman);
         ceh _snowmanxx = _snowman.d_(_snowmanx);
         if (g(_snowmanxx)) {
            return true;
         }

         boolean _snowmanxxx = dde.c(f, _snowmanxx.b(_snowman, _snowman, dcs.a()), dcr.i);
         if (_snowmanxxx) {
            ceh _snowmanxxxx = _snowman.d_(_snowmanx.c());
            return g(_snowmanxxxx);
         }
      }

      return false;
   }

   public static boolean g(ceh var0) {
      return _snowman.b(b) && _snowman.a(aed.ay) && _snowman.c(b);
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(d) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   public ceh a(ceh var1, bzm var2) {
      return _snowman.a(e, _snowman.a(_snowman.c(e)));
   }

   @Override
   public ceh a(ceh var1, byg var2) {
      return _snowman.a(_snowman.a(_snowman.c(e)));
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(b, c, d, e);
   }

   @Override
   public ccj a(brc var1) {
      return new ccm();
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }

   public static boolean h(ceh var0) {
      return _snowman.a(aed.ay, var0x -> var0x.b(cex.C) && var0x.b(cex.r)) && !_snowman.c(cex.C) && !_snowman.c(cex.r);
   }
}
