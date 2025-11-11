import java.util.List;
import java.util.Random;

public class bff implements brj {
   private int a;

   public bff() {
   }

   @Override
   public int a(aag var1, boolean var2, boolean var3) {
      if (_snowman && _snowman.V().b(brt.d)) {
         this.a--;
         if (this.a > 0) {
            return 0;
         } else {
            this.a = 1200;
            bfw _snowman = _snowman.q_();
            if (_snowman == null) {
               return 0;
            } else {
               Random _snowmanx = _snowman.t;
               int _snowmanxx = (8 + _snowmanx.nextInt(24)) * (_snowmanx.nextBoolean() ? -1 : 1);
               int _snowmanxxx = (8 + _snowmanx.nextInt(24)) * (_snowmanx.nextBoolean() ? -1 : 1);
               fx _snowmanxxxx = _snowman.cB().b(_snowmanxx, 0, _snowmanxxx);
               if (!_snowman.a(_snowmanxxxx.u() - 10, _snowmanxxxx.v() - 10, _snowmanxxxx.w() - 10, _snowmanxxxx.u() + 10, _snowmanxxxx.v() + 10, _snowmanxxxx.w() + 10)) {
                  return 0;
               } else {
                  if (bsg.a(ard.c.a, _snowman, _snowmanxxxx, aqe.h)) {
                     if (_snowman.a(_snowmanxxxx, 2)) {
                        return this.a(_snowman, _snowmanxxxx);
                     }

                     if (_snowman.a().a(_snowmanxxxx, true, cla.j).e()) {
                        return this.b(_snowman, _snowmanxxxx);
                     }
                  }

                  return 0;
               }
            }
         }
      } else {
         return 0;
      }
   }

   private int a(aag var1, fx var2) {
      int _snowman = 48;
      if (_snowman.y().a(azr.r.c(), _snowman, 48, azo.b.b) > 4L) {
         List<bab> _snowmanx = _snowman.a(bab.class, new dci(_snowman).c(48.0, 8.0, 48.0));
         if (_snowmanx.size() < 5) {
            return this.a(_snowman, _snowman);
         }
      }

      return 0;
   }

   private int b(aag var1, fx var2) {
      int _snowman = 16;
      List<bab> _snowmanx = _snowman.a(bab.class, new dci(_snowman).c(16.0, 8.0, 16.0));
      return _snowmanx.size() < 1 ? this.a(_snowman, _snowman) : 0;
   }

   private int a(fx var1, aag var2) {
      bab _snowman = aqe.h.a(_snowman);
      if (_snowman == null) {
         return 0;
      } else {
         _snowman.a(_snowman, _snowman.d(_snowman), aqp.a, null, null);
         _snowman.a(_snowman, 0.0F, 0.0F);
         _snowman.l(_snowman);
         return 1;
      }
   }
}
