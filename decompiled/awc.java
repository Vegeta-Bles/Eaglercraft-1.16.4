import java.util.EnumSet;
import java.util.List;

public class awc extends avv {
   public final bbe a;
   private double b;
   private int c;

   public awc(bbe var1, double var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (!this.a.eB() && !this.a.fC()) {
         List<aqa> _snowman = this.a.l.a(this.a, this.a.cc().c(9.0, 4.0, 9.0), var0 -> {
            aqe<?> _snowmanx = var0.X();
            return _snowmanx == aqe.Q || _snowmanx == aqe.aL;
         });
         bbe _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (aqa _snowmanxxx : _snowman) {
            bbe _snowmanxxxx = (bbe)_snowmanxxx;
            if (_snowmanxxxx.fC() && !_snowmanxxxx.fB()) {
               double _snowmanxxxxx = this.a.h(_snowmanxxxx);
               if (!(_snowmanxxxxx > _snowmanxx)) {
                  _snowmanxx = _snowmanxxxxx;
                  _snowmanx = _snowmanxxxx;
               }
            }
         }

         if (_snowmanx == null) {
            for (aqa _snowmanxxxx : _snowman) {
               bbe _snowmanxxxxx = (bbe)_snowmanxxxx;
               if (_snowmanxxxxx.eB() && !_snowmanxxxxx.fB()) {
                  double _snowmanxxxxxx = this.a.h(_snowmanxxxxx);
                  if (!(_snowmanxxxxxx > _snowmanxx)) {
                     _snowmanxx = _snowmanxxxxxx;
                     _snowmanx = _snowmanxxxxx;
                  }
               }
            }
         }

         if (_snowmanx == null) {
            return false;
         } else if (_snowmanxx < 4.0) {
            return false;
         } else if (!_snowmanx.eB() && !this.a(_snowmanx, 1)) {
            return false;
         } else {
            this.a.a(_snowmanx);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      if (this.a.fC() && this.a.fD().aX() && this.a(this.a, 0)) {
         double _snowman = this.a.h(this.a.fD());
         if (_snowman > 676.0) {
            if (this.b <= 3.0) {
               this.b *= 1.2;
               this.c = 40;
               return true;
            }

            if (this.c == 0) {
               return false;
            }
         }

         if (this.c > 0) {
            this.c--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void d() {
      this.a.fA();
      this.b = 2.1;
   }

   @Override
   public void e() {
      if (this.a.fC()) {
         if (!(this.a.eC() instanceof bcq)) {
            bbe _snowman = this.a.fD();
            double _snowmanx = (double)this.a.g(_snowman);
            float _snowmanxx = 2.0F;
            dcn _snowmanxxx = new dcn(_snowman.cD() - this.a.cD(), _snowman.cE() - this.a.cE(), _snowman.cH() - this.a.cH()).d().a(Math.max(_snowmanx - 2.0, 0.0));
            this.a.x().a(this.a.cD() + _snowmanxxx.b, this.a.cE() + _snowmanxxx.c, this.a.cH() + _snowmanxxx.d, this.b);
         }
      }
   }

   private boolean a(bbe var1, int var2) {
      if (_snowman > 8) {
         return false;
      } else if (_snowman.fC()) {
         return _snowman.fD().eB() ? true : this.a(_snowman.fD(), ++_snowman);
      } else {
         return false;
      }
   }
}
