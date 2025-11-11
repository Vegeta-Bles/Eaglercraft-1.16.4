public class auy extends avb {
   private final int i;
   private final boolean j;

   public auy(aqn var1, int var2, boolean var3) {
      super(_snowman);
      this.i = _snowman;
      this.j = _snowman;
   }

   @Override
   public void a() {
      if (this.h == avb.a.b) {
         this.h = avb.a.a;
         this.a.e(true);
         double _snowman = this.b - this.a.cD();
         double _snowmanx = this.c - this.a.cE();
         double _snowmanxx = this.d - this.a.cH();
         double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
         if (_snowmanxxx < 2.5000003E-7F) {
            this.a.u(0.0F);
            this.a.t(0.0F);
            return;
         }

         float _snowmanxxxx = (float)(afm.d(_snowmanxx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
         this.a.p = this.a(this.a.p, _snowmanxxxx, 90.0F);
         float _snowmanxxxxx;
         if (this.a.ao()) {
            _snowmanxxxxx = (float)(this.e * this.a.b(arl.d));
         } else {
            _snowmanxxxxx = (float)(this.e * this.a.b(arl.e));
         }

         this.a.q(_snowmanxxxxx);
         double _snowmanxxxxxx = (double)afm.a(_snowman * _snowman + _snowmanxx * _snowmanxx);
         float _snowmanxxxxxxx = (float)(-(afm.d(_snowmanx, _snowmanxxxxxx) * 180.0F / (float)Math.PI));
         this.a.q = this.a(this.a.q, _snowmanxxxxxxx, (float)this.i);
         this.a.u(_snowmanx > 0.0 ? _snowmanxxxxx : -_snowmanxxxxx);
      } else {
         if (!this.j) {
            this.a.e(false);
         }

         this.a.u(0.0F);
         this.a.t(0.0F);
      }
   }
}
