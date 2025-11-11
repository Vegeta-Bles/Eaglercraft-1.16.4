import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bgx extends bgs implements bgj {
   public static final Predicate<aqm> b = aqm::dO;

   public bgx(aqe<? extends bgx> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgx(brx var1, aqm var2) {
      super(aqe.aJ, _snowman, _snowman);
   }

   public bgx(brx var1, double var2, double var4, double var6) {
      super(aqe.aJ, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected blx h() {
      return bmd.qj;
   }

   @Override
   protected float k() {
      return 0.05F;
   }

   @Override
   protected void a(dcj var1) {
      super.a(_snowman);
      if (!this.l.v) {
         bmb _snowman = this.g();
         bnt _snowmanx = bnv.d(_snowman);
         List<apu> _snowmanxx = bnv.a(_snowman);
         boolean _snowmanxxx = _snowmanx == bnw.b && _snowmanxx.isEmpty();
         gc _snowmanxxxx = _snowman.b();
         fx _snowmanxxxxx = _snowman.a();
         fx _snowmanxxxxxx = _snowmanxxxxx.a(_snowmanxxxx);
         if (_snowmanxxx) {
            this.a(_snowmanxxxxxx, _snowmanxxxx);
            this.a(_snowmanxxxxxx.a(_snowmanxxxx.f()), _snowmanxxxx);

            for (gc _snowmanxxxxxxx : gc.c.a) {
               this.a(_snowmanxxxxxx.a(_snowmanxxxxxxx), _snowmanxxxxxxx);
            }
         }
      }
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      if (!this.l.v) {
         bmb _snowman = this.g();
         bnt _snowmanx = bnv.d(_snowman);
         List<apu> _snowmanxx = bnv.a(_snowman);
         boolean _snowmanxxx = _snowmanx == bnw.b && _snowmanxx.isEmpty();
         if (_snowmanxxx) {
            this.m();
         } else if (!_snowmanxx.isEmpty()) {
            if (this.n()) {
               this.a(_snowman, _snowmanx);
            } else {
               this.a(_snowmanxx, _snowman.c() == dcl.a.c ? ((dck)_snowman).a() : null);
            }
         }

         int _snowmanxxxx = _snowmanx.b() ? 2007 : 2002;
         this.l.c(_snowmanxxxx, this.cB(), bnv.c(_snowman));
         this.ad();
      }
   }

   private void m() {
      dci _snowman = this.cc().c(4.0, 2.0, 4.0);
      List<aqm> _snowmanx = this.l.a(aqm.class, _snowman, b);
      if (!_snowmanx.isEmpty()) {
         for (aqm _snowmanxx : _snowmanx) {
            double _snowmanxxx = this.h(_snowmanxx);
            if (_snowmanxxx < 16.0 && _snowmanxx.dO()) {
               _snowmanxx.a(apk.c(_snowmanxx, this.v()), 1.0F);
            }
         }
      }
   }

   private void a(List<apu> var1, @Nullable aqa var2) {
      dci _snowman = this.cc().c(4.0, 2.0, 4.0);
      List<aqm> _snowmanx = this.l.a(aqm.class, _snowman);
      if (!_snowmanx.isEmpty()) {
         for (aqm _snowmanxx : _snowmanx) {
            if (_snowmanxx.eh()) {
               double _snowmanxxx = this.h(_snowmanxx);
               if (_snowmanxxx < 16.0) {
                  double _snowmanxxxx = 1.0 - Math.sqrt(_snowmanxxx) / 4.0;
                  if (_snowmanxx == _snowman) {
                     _snowmanxxxx = 1.0;
                  }

                  for (apu _snowmanxxxxx : _snowman) {
                     aps _snowmanxxxxxx = _snowmanxxxxx.a();
                     if (_snowmanxxxxxx.a()) {
                        _snowmanxxxxxx.a(this, this.v(), _snowmanxx, _snowmanxxxxx.c(), _snowmanxxxx);
                     } else {
                        int _snowmanxxxxxxx = (int)(_snowmanxxxx * (double)_snowmanxxxxx.b() + 0.5);
                        if (_snowmanxxxxxxx > 20) {
                           _snowmanxx.c(new apu(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxx.c(), _snowmanxxxxx.d(), _snowmanxxxxx.e()));
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void a(bmb var1, bnt var2) {
      apz _snowman = new apz(this.l, this.cD(), this.cE(), this.cH());
      aqa _snowmanx = this.v();
      if (_snowmanx instanceof aqm) {
         _snowman.a((aqm)_snowmanx);
      }

      _snowman.a(3.0F);
      _snowman.b(-0.5F);
      _snowman.d(10);
      _snowman.c(-_snowman.g() / (float)_snowman.m());
      _snowman.a(_snowman);

      for (apu _snowmanxx : bnv.b(_snowman)) {
         _snowman.a(new apu(_snowmanxx));
      }

      md _snowmanxx = _snowman.o();
      if (_snowmanxx != null && _snowmanxx.c("CustomPotionColor", 99)) {
         _snowman.a(_snowmanxx.h("CustomPotionColor"));
      }

      this.l.c(_snowman);
   }

   private boolean n() {
      return this.g().b() == bmd.qm;
   }

   private void a(fx var1, gc var2) {
      ceh _snowman = this.l.d_(_snowman);
      if (_snowman.a(aed.an)) {
         this.l.a(_snowman, false);
      } else if (buy.g(_snowman)) {
         this.l.a(null, 1009, _snowman, 0);
         buy.c(this.l, _snowman, _snowman);
         this.l.a(_snowman, _snowman.a(buy.b, Boolean.valueOf(false)));
      }
   }
}
