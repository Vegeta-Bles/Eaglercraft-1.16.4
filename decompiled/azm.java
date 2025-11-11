import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class azm implements brj {
   private static final Logger a = LogManager.getLogger();
   private boolean b;
   private azm.a c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;

   public azm() {
      this.c = azm.a.c;
   }

   @Override
   public int a(aag var1, boolean var2, boolean var3) {
      if (!_snowman.M() && _snowman) {
         float _snowman = _snowman.f(0.0F);
         if ((double)_snowman == 0.5) {
            this.c = _snowman.t.nextInt(10) == 0 ? azm.a.b : azm.a.c;
         }

         if (this.c == azm.a.c) {
            return 0;
         } else {
            if (!this.b) {
               if (!this.a(_snowman)) {
                  return 0;
               }

               this.b = true;
            }

            if (this.e > 0) {
               this.e--;
               return 0;
            } else {
               this.e = 2;
               if (this.d > 0) {
                  this.b(_snowman);
                  this.d--;
               } else {
                  this.c = azm.a.c;
               }

               return 1;
            }
         }
      } else {
         this.c = azm.a.c;
         this.b = false;
         return 0;
      }
   }

   private boolean a(aag var1) {
      for (bfw _snowman : _snowman.x()) {
         if (!_snowman.a_()) {
            fx _snowmanx = _snowman.cB();
            if (_snowman.a_(_snowmanx) && _snowman.v(_snowmanx).t() != bsv.b.p) {
               for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
                  float _snowmanxxx = _snowman.t.nextFloat() * (float) (Math.PI * 2);
                  this.f = _snowmanx.u() + afm.d(afm.b(_snowmanxxx) * 32.0F);
                  this.g = _snowmanx.v();
                  this.h = _snowmanx.w() + afm.d(afm.a(_snowmanxxx) * 32.0F);
                  if (this.a(_snowman, new fx(this.f, this.g, this.h)) != null) {
                     this.e = 0;
                     this.d = 20;
                     break;
                  }
               }

               return true;
            }
         }
      }

      return false;
   }

   private void b(aag var1) {
      dcn _snowman = this.a(_snowman, new fx(this.f, this.g, this.h));
      if (_snowman != null) {
         bej _snowmanx;
         try {
            _snowmanx = new bej(_snowman);
            _snowmanx.a(_snowman, _snowman.d(_snowmanx.cB()), aqp.h, null, null);
         } catch (Exception var5) {
            a.warn("Failed to create zombie for village siege at {}", _snowman, var5);
            return;
         }

         _snowmanx.b(_snowman.b, _snowman.c, _snowman.d, _snowman.t.nextFloat() * 360.0F, 0.0F);
         _snowman.l(_snowmanx);
      }
   }

   @Nullable
   private dcn a(aag var1, fx var2) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         int _snowmanx = _snowman.u() + _snowman.t.nextInt(16) - 8;
         int _snowmanxx = _snowman.w() + _snowman.t.nextInt(16) - 8;
         int _snowmanxxx = _snowman.a(chn.a.b, _snowmanx, _snowmanxx);
         fx _snowmanxxxx = new fx(_snowmanx, _snowmanxxx, _snowmanxx);
         if (_snowman.a_(_snowmanxxxx) && bdq.b(aqe.aY, _snowman, aqp.h, _snowmanxxxx, _snowman.t)) {
            return dcn.c(_snowmanxxxx);
         }
      }

      return null;
   }

   static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
