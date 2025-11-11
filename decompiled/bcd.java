public class bcd extends bbu {
   private int b;
   private int c;
   private apz d;

   public bcd(bbr var1) {
      super(_snowman);
   }

   @Override
   public void b() {
      this.b++;
      if (this.b % 2 == 0 && this.b < 10) {
         dcn _snowman = this.a.x(1.0F).d();
         _snowman.b((float) (-Math.PI / 4));
         double _snowmanx = this.a.bo.cD();
         double _snowmanxx = this.a.bo.e(0.5);
         double _snowmanxxx = this.a.bo.cH();

         for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
            double _snowmanxxxxx = _snowmanx + this.a.cY().nextGaussian() / 2.0;
            double _snowmanxxxxxx = _snowmanxx + this.a.cY().nextGaussian() / 2.0;
            double _snowmanxxxxxxx = _snowmanxxx + this.a.cY().nextGaussian() / 2.0;

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 6; _snowmanxxxxxxxx++) {
               this.a.l.a(hh.i, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, -_snowman.b * 0.08F * (double)_snowmanxxxxxxxx, -_snowman.c * 0.6F, -_snowman.d * 0.08F * (double)_snowmanxxxxxxxx);
            }

            _snowman.b((float) (Math.PI / 16));
         }
      }
   }

   @Override
   public void c() {
      this.b++;
      if (this.b >= 200) {
         if (this.c >= 4) {
            this.a.eK().a(bch.e);
         } else {
            this.a.eK().a(bch.g);
         }
      } else if (this.b == 10) {
         dcn _snowman = new dcn(this.a.bo.cD() - this.a.cD(), 0.0, this.a.bo.cH() - this.a.cH()).d();
         float _snowmanx = 5.0F;
         double _snowmanxx = this.a.bo.cD() + _snowman.b * 5.0 / 2.0;
         double _snowmanxxx = this.a.bo.cH() + _snowman.d * 5.0 / 2.0;
         double _snowmanxxxx = this.a.bo.e(0.5);
         double _snowmanxxxxx = _snowmanxxxx;
         fx.a _snowmanxxxxxx = new fx.a(_snowmanxx, _snowmanxxxx, _snowmanxxx);

         while (this.a.l.w(_snowmanxxxxxx)) {
            if (--_snowmanxxxxx < 0.0) {
               _snowmanxxxxx = _snowmanxxxx;
               break;
            }

            _snowmanxxxxxx.c(_snowmanxx, _snowmanxxxxx, _snowmanxxx);
         }

         _snowmanxxxxx = (double)(afm.c(_snowmanxxxxx) + 1);
         this.d = new apz(this.a.l, _snowmanxx, _snowmanxxxxx, _snowmanxxx);
         this.d.a(this.a);
         this.d.a(5.0F);
         this.d.b(200);
         this.d.a(hh.i);
         this.d.a(new apu(apw.g));
         this.a.l.c(this.d);
      }
   }

   @Override
   public void d() {
      this.b = 0;
      this.c++;
   }

   @Override
   public void e() {
      if (this.d != null) {
         this.d.ad();
         this.d = null;
      }
   }

   @Override
   public bch<bcd> i() {
      return bch.f;
   }

   public void j() {
      this.c = 0;
   }
}
