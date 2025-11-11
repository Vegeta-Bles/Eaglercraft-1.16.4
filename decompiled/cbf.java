import java.util.Random;
import javax.annotation.Nullable;

public class cbf extends buo {
   private static final ddh c = buo.a(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
   private static final ddh d = buo.a(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
   public static final cfg a = cex.ap;
   public static final cfg b = cex.ao;

   public cbf(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(0)).a(b, Integer.valueOf(1)));
   }

   @Override
   public void a(brx var1, fx var2, aqa var3) {
      this.a(_snowman, _snowman, _snowman, 100);
      super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(brx var1, fx var2, aqa var3, float var4) {
      if (!(_snowman instanceof bej)) {
         this.a(_snowman, _snowman, _snowman, 3);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void a(brx var1, fx var2, aqa var3, int var4) {
      if (this.a(_snowman, _snowman)) {
         if (!_snowman.v && _snowman.t.nextInt(_snowman) == 0) {
            ceh _snowman = _snowman.d_(_snowman);
            if (_snowman.a(bup.kf)) {
               this.a(_snowman, _snowman, _snowman);
            }
         }
      }
   }

   private void a(brx var1, fx var2, ceh var3) {
      _snowman.a(null, _snowman, adq.pw, adr.e, 0.7F, 0.9F + _snowman.t.nextFloat() * 0.2F);
      int _snowman = _snowman.c(b);
      if (_snowman <= 1) {
         _snowman.b(_snowman, false);
      } else {
         _snowman.a(_snowman, _snowman.a(b, Integer.valueOf(_snowman - 1)), 2);
         _snowman.c(2001, _snowman, buo.i(_snowman));
      }
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (this.a(_snowman) && a(_snowman, _snowman)) {
         int _snowman = _snowman.c(a);
         if (_snowman < 2) {
            _snowman.a(null, _snowman, adq.px, adr.e, 0.7F, 0.9F + _snowman.nextFloat() * 0.2F);
            _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(_snowman + 1)), 2);
         } else {
            _snowman.a(null, _snowman, adq.py, adr.e, 0.7F, 0.9F + _snowman.nextFloat() * 0.2F);
            _snowman.a(_snowman, false);

            for (int _snowmanx = 0; _snowmanx < _snowman.c(b); _snowmanx++) {
               _snowman.c(2001, _snowman, buo.i(_snowman));
               bax _snowmanxx = aqe.aN.a(_snowman);
               _snowmanxx.c_(-24000);
               _snowmanxx.g(_snowman);
               _snowmanxx.b((double)_snowman.u() + 0.3 + (double)_snowmanx * 0.2, (double)_snowman.v(), (double)_snowman.w() + 0.3, 0.0F, 0.0F);
               _snowman.c(_snowmanxx);
            }
         }
      }
   }

   public static boolean a(brc var0, fx var1) {
      return b(_snowman, _snowman.c());
   }

   public static boolean b(brc var0, fx var1) {
      return _snowman.d_(_snowman).a(aed.C);
   }

   @Override
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (a(_snowman, _snowman) && !_snowman.v) {
         _snowman.c(2005, _snowman, 0);
      }
   }

   private boolean a(brx var1) {
      float _snowman = _snowman.f(1.0F);
      return (double)_snowman < 0.69 && (double)_snowman > 0.65 ? true : _snowman.t.nextInt(500) == 0;
   }

   @Override
   public void a(brx var1, bfw var2, fx var3, ceh var4, @Nullable ccj var5, bmb var6) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      return _snowman.m().b() == this.h() && _snowman.c(b) < 4 ? true : super.a(_snowman, _snowman);
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      return _snowman.a(this) ? _snowman.a(b, Integer.valueOf(Math.min(4, _snowman.c(b) + 1))) : super.a(_snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return _snowman.c(b) > 1 ? d : c;
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   private boolean a(brx var1, aqa var2) {
      if (_snowman instanceof bax || _snowman instanceof azu) {
         return false;
      } else {
         return !(_snowman instanceof aqm) ? false : _snowman instanceof bfw || _snowman.V().b(brt.b);
      }
   }
}
