import java.util.List;

public class bgd extends bgb {
   public bgd(aqe<? extends bgd> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgd(brx var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(aqe.p, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public bgd(brx var1, aqm var2, double var3, double var5, double var7) {
      super(aqe.p, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dcl var1) {
      super.a(_snowman);
      aqa _snowman = this.v();
      if (_snowman.c() != dcl.a.c || !((dck)_snowman).a().s(_snowman)) {
         if (!this.l.v) {
            List<aqm> _snowmanx = this.l.a(aqm.class, this.cc().c(4.0, 2.0, 4.0));
            apz _snowmanxx = new apz(this.l, this.cD(), this.cE(), this.cH());
            if (_snowman instanceof aqm) {
               _snowmanxx.a((aqm)_snowman);
            }

            _snowmanxx.a(hh.i);
            _snowmanxx.a(3.0F);
            _snowmanxx.b(600);
            _snowmanxx.c((7.0F - _snowmanxx.g()) / (float)_snowmanxx.m());
            _snowmanxx.a(new apu(apw.g, 1, 1));
            if (!_snowmanx.isEmpty()) {
               for (aqm _snowmanxxx : _snowmanx) {
                  double _snowmanxxxx = this.h(_snowmanxxx);
                  if (_snowmanxxxx < 16.0) {
                     _snowmanxx.d(_snowmanxxx.cD(), _snowmanxxx.cE(), _snowmanxxx.cH());
                     break;
                  }
               }
            }

            this.l.c(2006, this.cB(), this.aA() ? -1 : 1);
            this.l.c(_snowmanxx);
            this.ad();
         }
      }
   }

   @Override
   public boolean aT() {
      return false;
   }

   @Override
   public boolean a(apk var1, float var2) {
      return false;
   }

   @Override
   protected hf h() {
      return hh.i;
   }

   @Override
   protected boolean W_() {
      return false;
   }
}
