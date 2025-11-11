public class bce extends bbu {
   private static final azg b = new azg().a(150.0);
   private final azg c;
   private int d;

   public bce(bbr var1) {
      super(_snowman);
      this.c = new azg().a(20.0).a(var1x -> Math.abs(var1x.cE() - _snowman.cE()) <= 10.0);
   }

   @Override
   public void c() {
      this.d++;
      aqm _snowman = this.a.l.a(this.c, this.a, this.a.cD(), this.a.cE(), this.a.cH());
      if (_snowman != null) {
         if (this.d > 25) {
            this.a.eK().a(bch.h);
         } else {
            dcn _snowmanx = new dcn(_snowman.cD() - this.a.cD(), 0.0, _snowman.cH() - this.a.cH()).d();
            dcn _snowmanxx = new dcn((double)afm.a(this.a.p * (float) (Math.PI / 180.0)), 0.0, (double)(-afm.b(this.a.p * (float) (Math.PI / 180.0)))).d();
            float _snowmanxxx = (float)_snowmanxx.b(_snowmanx);
            float _snowmanxxxx = (float)(Math.acos((double)_snowmanxxx) * 180.0F / (float)Math.PI) + 0.5F;
            if (_snowmanxxxx < 0.0F || _snowmanxxxx > 10.0F) {
               double _snowmanxxxxx = _snowman.cD() - this.a.bo.cD();
               double _snowmanxxxxxx = _snowman.cH() - this.a.bo.cH();
               double _snowmanxxxxxxx = afm.a(afm.g(180.0 - afm.d(_snowmanxxxxx, _snowmanxxxxxx) * 180.0F / (float)Math.PI - (double)this.a.p), -100.0, 100.0);
               this.a.bt *= 0.8F;
               float _snowmanxxxxxxxx = afm.a(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx) + 1.0F;
               float _snowmanxxxxxxxxx = _snowmanxxxxxxxx;
               if (_snowmanxxxxxxxx > 40.0F) {
                  _snowmanxxxxxxxx = 40.0F;
               }

               this.a.bt = (float)((double)this.a.bt + _snowmanxxxxxxx * (double)(0.7F / _snowmanxxxxxxxx / _snowmanxxxxxxxxx));
               this.a.p = this.a.p + this.a.bt;
            }
         }
      } else if (this.d >= 100) {
         _snowman = this.a.l.a(b, this.a, this.a.cD(), this.a.cE(), this.a.cH());
         this.a.eK().a(bch.e);
         if (_snowman != null) {
            this.a.eK().a(bch.i);
            this.a.eK().b(bch.i).a(new dcn(_snowman.cD(), _snowman.cE(), _snowman.cH()));
         }
      }
   }

   @Override
   public void d() {
      this.d = 0;
   }

   @Override
   public bch<bce> i() {
      return bch.g;
   }
}
