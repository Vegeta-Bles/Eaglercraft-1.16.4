import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cxn {
   private static final ceg.e a = (var0, var1, var2) -> var0.a(bup.bK);
   private final bry b;
   private final gc.a c;
   private final gc d;
   private int e;
   @Nullable
   private fx f;
   private int g;
   private int h;

   public static Optional<cxn> a(bry var0, fx var1, gc.a var2) {
      return a(_snowman, _snowman, var0x -> var0x.a() && var0x.e == 0, _snowman);
   }

   public static Optional<cxn> a(bry var0, fx var1, Predicate<cxn> var2, gc.a var3) {
      Optional<cxn> _snowman = Optional.of(new cxn(_snowman, _snowman, _snowman)).filter(_snowman);
      if (_snowman.isPresent()) {
         return _snowman;
      } else {
         gc.a _snowmanx = _snowman == gc.a.a ? gc.a.c : gc.a.a;
         return Optional.of(new cxn(_snowman, _snowman, _snowmanx)).filter(_snowman);
      }
   }

   public cxn(bry var1, fx var2, gc.a var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman == gc.a.a ? gc.e : gc.d;
      this.f = this.a(_snowman);
      if (this.f == null) {
         this.f = _snowman;
         this.h = 1;
         this.g = 1;
      } else {
         this.h = this.d();
         if (this.h > 0) {
            this.g = this.e();
         }
      }
   }

   @Nullable
   private fx a(fx var1) {
      int _snowman = Math.max(0, _snowman.v() - 21);

      while (_snowman.v() > _snowman && a(this.b.d_(_snowman.c()))) {
         _snowman = _snowman.c();
      }

      gc _snowmanx = this.d.f();
      int _snowmanxx = this.a(_snowman, _snowmanx) - 1;
      return _snowmanxx < 0 ? null : _snowman.a(_snowmanx, _snowmanxx);
   }

   private int d() {
      int _snowman = this.a(this.f, this.d);
      return _snowman >= 2 && _snowman <= 21 ? _snowman : 0;
   }

   private int a(fx var1, gc var2) {
      fx.a _snowman = new fx.a();

      for (int _snowmanx = 0; _snowmanx <= 21; _snowmanx++) {
         _snowman.g(_snowman).c(_snowman, _snowmanx);
         ceh _snowmanxx = this.b.d_(_snowman);
         if (!a(_snowmanxx)) {
            if (a.test(_snowmanxx, this.b, _snowman)) {
               return _snowmanx;
            }
            break;
         }

         ceh _snowmanxxx = this.b.d_(_snowman.c(gc.a));
         if (!a.test(_snowmanxxx, this.b, _snowman)) {
            break;
         }
      }

      return 0;
   }

   private int e() {
      fx.a _snowman = new fx.a();
      int _snowmanx = this.a(_snowman);
      return _snowmanx >= 3 && _snowmanx <= 21 && this.a(_snowman, _snowmanx) ? _snowmanx : 0;
   }

   private boolean a(fx.a var1, int var2) {
      for (int _snowman = 0; _snowman < this.h; _snowman++) {
         fx.a _snowmanx = _snowman.g(this.f).c(gc.b, _snowman).c(this.d, _snowman);
         if (!a.test(this.b.d_(_snowmanx), this.b, _snowmanx)) {
            return false;
         }
      }

      return true;
   }

   private int a(fx.a var1) {
      for (int _snowman = 0; _snowman < 21; _snowman++) {
         _snowman.g(this.f).c(gc.b, _snowman).c(this.d, -1);
         if (!a.test(this.b.d_(_snowman), this.b, _snowman)) {
            return _snowman;
         }

         _snowman.g(this.f).c(gc.b, _snowman).c(this.d, this.h);
         if (!a.test(this.b.d_(_snowman), this.b, _snowman)) {
            return _snowman;
         }

         for (int _snowmanx = 0; _snowmanx < this.h; _snowmanx++) {
            _snowman.g(this.f).c(gc.b, _snowman).c(this.d, _snowmanx);
            ceh _snowmanxx = this.b.d_(_snowman);
            if (!a(_snowmanxx)) {
               return _snowman;
            }

            if (_snowmanxx.a(bup.cT)) {
               this.e++;
            }
         }
      }

      return 21;
   }

   private static boolean a(ceh var0) {
      return _snowman.g() || _snowman.a(aed.an) || _snowman.a(bup.cT);
   }

   public boolean a() {
      return this.f != null && this.h >= 2 && this.h <= 21 && this.g >= 3 && this.g <= 21;
   }

   public void b() {
      ceh _snowman = bup.cT.n().a(byj.a, this.c);
      fx.a(this.f, this.f.a(gc.b, this.g - 1).a(this.d, this.h - 1)).forEach(var2 -> this.b.a(var2, _snowman, 18));
   }

   public boolean c() {
      return this.a() && this.e == this.h * this.g;
   }

   public static dcn a(i.a var0, gc.a var1, dcn var2, aqb var3) {
      double _snowman = (double)_snowman.b - (double)_snowman.a;
      double _snowmanx = (double)_snowman.c - (double)_snowman.b;
      fx _snowmanxx = _snowman.a;
      double _snowmanxxx;
      if (_snowman > 0.0) {
         float _snowmanxxxx = (float)_snowmanxx.a(_snowman) + _snowman.a / 2.0F;
         _snowmanxxx = afm.a(afm.c(_snowman.a(_snowman) - (double)_snowmanxxxx, 0.0, _snowman), 0.0, 1.0);
      } else {
         _snowmanxxx = 0.5;
      }

      double _snowmanxxxx;
      if (_snowmanx > 0.0) {
         gc.a _snowmanxxxxx = gc.a.b;
         _snowmanxxxx = afm.a(afm.c(_snowman.a(_snowmanxxxxx) - (double)_snowmanxx.a(_snowmanxxxxx), 0.0, _snowmanx), 0.0, 1.0);
      } else {
         _snowmanxxxx = 0.0;
      }

      gc.a _snowmanxxxxx = _snowman == gc.a.a ? gc.a.c : gc.a.a;
      double _snowmanxxxxxx = _snowman.a(_snowmanxxxxx) - ((double)_snowmanxx.a(_snowmanxxxxx) + 0.5);
      return new dcn(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx);
   }

   public static cxm a(aag var0, i.a var1, gc.a var2, dcn var3, aqb var4, dcn var5, float var6, float var7) {
      fx _snowman = _snowman.a;
      ceh _snowmanx = _snowman.d_(_snowman);
      gc.a _snowmanxx = _snowmanx.c(cex.E);
      double _snowmanxxx = (double)_snowman.b;
      double _snowmanxxxx = (double)_snowman.c;
      int _snowmanxxxxx = _snowman == _snowmanxx ? 0 : 90;
      dcn _snowmanxxxxxx = _snowman == _snowmanxx ? _snowman : new dcn(_snowman.d, _snowman.c, -_snowman.b);
      double _snowmanxxxxxxx = (double)_snowman.a / 2.0 + (_snowmanxxx - (double)_snowman.a) * _snowman.a();
      double _snowmanxxxxxxxx = (_snowmanxxxx - (double)_snowman.b) * _snowman.b();
      double _snowmanxxxxxxxxx = 0.5 + _snowman.c();
      boolean _snowmanxxxxxxxxxx = _snowmanxx == gc.a.a;
      dcn _snowmanxxxxxxxxxxx = new dcn(
         (double)_snowman.u() + (_snowmanxxxxxxxxxx ? _snowmanxxxxxxx : _snowmanxxxxxxxxx), (double)_snowman.v() + _snowmanxxxxxxxx, (double)_snowman.w() + (_snowmanxxxxxxxxxx ? _snowmanxxxxxxxxx : _snowmanxxxxxxx)
      );
      return new cxm(_snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowman + (float)_snowmanxxxxx, _snowman);
   }
}
