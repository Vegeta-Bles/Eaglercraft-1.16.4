import java.util.Random;
import javax.annotation.Nullable;

public class bzq extends buu implements buq, bzu {
   public static final cfg a = cex.ay;
   public static final cey b = cex.C;
   protected static final ddh c = buo.a(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
   protected static final ddh d = buo.a(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
   protected static final ddh e = buo.a(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   protected static final ddh f = buo.a(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

   protected bzq(ceg.c var1) {
      super(_snowman);
      this.j(this.n.b().a(a, Integer.valueOf(1)).a(b, Boolean.valueOf(true)));
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a());
      if (_snowman.a(this)) {
         return _snowman.a(a, Integer.valueOf(Math.min(4, _snowman.c(a) + 1)));
      } else {
         cux _snowmanx = _snowman.p().b(_snowman.a());
         boolean _snowmanxx = _snowmanx.a() == cuy.c;
         return super.a(_snowman).a(b, Boolean.valueOf(_snowmanxx));
      }
   }

   public static boolean h(ceh var0) {
      return !_snowman.c(b);
   }

   @Override
   protected boolean c(ceh var1, brc var2, fx var3) {
      return !_snowman.k(_snowman, _snowman).a(gc.b).b() || _snowman.d(_snowman, _snowman, gc.b);
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.c();
      return this.c(_snowman.d_(_snowman), _snowman, _snowman);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      if (!_snowman.a(_snowman, _snowman)) {
         return bup.a.n();
      } else {
         if (_snowman.c(b)) {
            _snowman.I().a(_snowman, cuy.c, cuy.c.a(_snowman));
         }

         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(ceh var1, bny var2) {
      return _snowman.m().b() == this.h() && _snowman.c(a) < 4 ? true : super.a(_snowman, _snowman);
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      switch (_snowman.c(a)) {
         case 1:
         default:
            return c;
         case 2:
            return d;
         case 3:
            return e;
         case 4:
            return f;
      }
   }

   @Override
   public cux d(ceh var1) {
      return _snowman.c(b) ? cuy.c.a(false) : super.d(_snowman);
   }

   @Override
   protected void a(cei.a<buo, ceh> var1) {
      _snowman.a(a, b);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      if (!h(_snowman) && _snowman.d_(_snowman.c()).a(aed.Y)) {
         int _snowman = 5;
         int _snowmanx = 1;
         int _snowmanxx = 2;
         int _snowmanxxx = 0;
         int _snowmanxxxx = _snowman.u() - 2;
         int _snowmanxxxxx = 0;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 5; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanx; _snowmanxxxxxxx++) {
               int _snowmanxxxxxxxx = 2 + _snowman.v() - 1;

               for (int _snowmanxxxxxxxxx = _snowmanxxxxxxxx - 2; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                  fx _snowmanxxxxxxxxxx = new fx(_snowmanxxxx + _snowmanxxxxxx, _snowmanxxxxxxxxx, _snowman.w() - _snowmanxxxxx + _snowmanxxxxxxx);
                  if (_snowmanxxxxxxxxxx != _snowman && _snowman.nextInt(6) == 0 && _snowman.d_(_snowmanxxxxxxxxxx).a(bup.A)) {
                     ceh _snowmanxxxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxxxx.c());
                     if (_snowmanxxxxxxxxxxx.a(aed.Y)) {
                        _snowman.a(_snowmanxxxxxxxxxx, bup.kU.n().a(a, Integer.valueOf(_snowman.nextInt(4) + 1)), 3);
                     }
                  }
               }
            }

            if (_snowmanxxx < 2) {
               _snowmanx += 2;
               _snowmanxxxxx++;
            } else {
               _snowmanx -= 2;
               _snowmanxxxxx--;
            }

            _snowmanxxx++;
         }

         _snowman.a(_snowman, _snowman.a(a, Integer.valueOf(4)), 2);
      }
   }

   @Override
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      return false;
   }
}
