import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class bfq implements brj {
   private final Random a = new Random();
   private final cym b;
   private int c;
   private int d;
   private int e;

   public bfq(cym var1) {
      this.b = _snowman;
      this.c = 1200;
      this.d = _snowman.v();
      this.e = _snowman.w();
      if (this.d == 0 && this.e == 0) {
         this.d = 24000;
         _snowman.g(this.d);
         this.e = 25;
         _snowman.h(this.e);
      }
   }

   @Override
   public int a(aag var1, boolean var2, boolean var3) {
      if (!_snowman.V().b(brt.E)) {
         return 0;
      } else if (--this.c > 0) {
         return 0;
      } else {
         this.c = 1200;
         this.d -= 1200;
         this.b.g(this.d);
         if (this.d > 0) {
            return 0;
         } else {
            this.d = 24000;
            if (!_snowman.V().b(brt.d)) {
               return 0;
            } else {
               int _snowman = this.e;
               this.e = afm.a(this.e + 25, 25, 75);
               this.b.h(this.e);
               if (this.a.nextInt(100) > _snowman) {
                  return 0;
               } else if (this.a(_snowman)) {
                  this.e = 25;
                  return 1;
               } else {
                  return 0;
               }
            }
         }
      }
   }

   private boolean a(aag var1) {
      bfw _snowman = _snowman.q_();
      if (_snowman == null) {
         return true;
      } else if (this.a.nextInt(10) != 0) {
         return false;
      } else {
         fx _snowmanx = _snowman.cB();
         int _snowmanxx = 48;
         azo _snowmanxxx = _snowman.y();
         Optional<fx> _snowmanxxxx = _snowmanxxx.c(azr.s.c(), var0 -> true, _snowmanx, 48, azo.b.c);
         fx _snowmanxxxxx = _snowmanxxxx.orElse(_snowmanx);
         fx _snowmanxxxxxx = this.a(_snowman, _snowmanxxxxx, 48);
         if (_snowmanxxxxxx != null && this.a(_snowman, _snowmanxxxxxx)) {
            if (_snowman.i(_snowmanxxxxxx).equals(Optional.of(btb.Z))) {
               return false;
            }

            bfp _snowmanxxxxxxx = aqe.aR.a(_snowman, null, null, null, _snowmanxxxxxx, aqp.h, false, false);
            if (_snowmanxxxxxxx != null) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 2; _snowmanxxxxxxxx++) {
                  this.a(_snowman, _snowmanxxxxxxx, 4);
               }

               this.b.a(_snowmanxxxxxxx.bS());
               _snowmanxxxxxxx.u(48000);
               _snowmanxxxxxxx.g(_snowmanxxxxx);
               _snowmanxxxxxxx.a(_snowmanxxxxx, 16);
               return true;
            }
         }

         return false;
      }
   }

   private void a(aag var1, bfp var2, int var3) {
      fx _snowman = this.a(_snowman, _snowman.cB(), _snowman);
      if (_snowman != null) {
         bbj _snowmanx = aqe.aL.a(_snowman, null, null, null, _snowman, aqp.h, false, false);
         if (_snowmanx != null) {
            _snowmanx.b(_snowman, true);
         }
      }
   }

   @Nullable
   private fx a(brz var1, fx var2, int var3) {
      fx _snowman = null;

      for (int _snowmanx = 0; _snowmanx < 10; _snowmanx++) {
         int _snowmanxx = _snowman.u() + this.a.nextInt(_snowman * 2) - _snowman;
         int _snowmanxxx = _snowman.w() + this.a.nextInt(_snowman * 2) - _snowman;
         int _snowmanxxxx = _snowman.a(chn.a.b, _snowmanxx, _snowmanxxx);
         fx _snowmanxxxxx = new fx(_snowmanxx, _snowmanxxxx, _snowmanxxx);
         if (bsg.a(ard.c.a, _snowman, _snowmanxxxxx, aqe.aR)) {
            _snowman = _snowmanxxxxx;
            break;
         }
      }

      return _snowman;
   }

   private boolean a(brc var1, fx var2) {
      for (fx _snowman : fx.a(_snowman, _snowman.b(1, 2, 1))) {
         if (!_snowman.d_(_snowman).k(_snowman, _snowman).b()) {
            return false;
         }
      }

      return true;
   }
}
