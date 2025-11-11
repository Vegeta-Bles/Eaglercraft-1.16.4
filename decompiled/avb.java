public class avb {
   protected final aqn a;
   protected double b;
   protected double c;
   protected double d;
   protected double e;
   protected float f;
   protected float g;
   protected avb.a h = avb.a.a;

   public avb(aqn var1) {
      this.a = _snowman;
   }

   public boolean b() {
      return this.h == avb.a.b;
   }

   public double c() {
      return this.e;
   }

   public void a(double var1, double var3, double var5, double var7) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      if (this.h != avb.a.d) {
         this.h = avb.a.b;
      }
   }

   public void a(float var1, float var2) {
      this.h = avb.a.c;
      this.f = _snowman;
      this.g = _snowman;
      this.e = 0.25;
   }

   public void a() {
      if (this.h == avb.a.c) {
         float _snowman = (float)this.a.b(arl.d);
         float _snowmanx = (float)this.e * _snowman;
         float _snowmanxx = this.f;
         float _snowmanxxx = this.g;
         float _snowmanxxxx = afm.c(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
         if (_snowmanxxxx < 1.0F) {
            _snowmanxxxx = 1.0F;
         }

         _snowmanxxxx = _snowmanx / _snowmanxxxx;
         _snowmanxx *= _snowmanxxxx;
         _snowmanxxx *= _snowmanxxxx;
         float _snowmanxxxxx = afm.a(this.a.p * (float) (Math.PI / 180.0));
         float _snowmanxxxxxx = afm.b(this.a.p * (float) (Math.PI / 180.0));
         float _snowmanxxxxxxx = _snowmanxx * _snowmanxxxxxx - _snowmanxxx * _snowmanxxxxx;
         float _snowmanxxxxxxxx = _snowmanxxx * _snowmanxxxxxx + _snowmanxx * _snowmanxxxxx;
         if (!this.b(_snowmanxxxxxxx, _snowmanxxxxxxxx)) {
            this.f = 1.0F;
            this.g = 0.0F;
         }

         this.a.q(_snowmanx);
         this.a.t(this.f);
         this.a.v(this.g);
         this.h = avb.a.a;
      } else if (this.h == avb.a.b) {
         this.h = avb.a.a;
         double _snowmanxxxxx = this.b - this.a.cD();
         double _snowmanxxxxxx = this.d - this.a.cH();
         double _snowmanxxxxxxx = this.c - this.a.cE();
         double _snowmanxxxxxxxx = _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxx;
         if (_snowmanxxxxxxxx < 2.5000003E-7F) {
            this.a.t(0.0F);
            return;
         }

         float _snowmanxxxxxxxxx = (float)(afm.d(_snowmanxxxxxx, _snowmanxxxxx) * 180.0F / (float)Math.PI) - 90.0F;
         this.a.p = this.a(this.a.p, _snowmanxxxxxxxxx, 90.0F);
         this.a.q((float)(this.e * this.a.b(arl.d)));
         fx _snowmanxxxxxxxxxx = this.a.cB();
         ceh _snowmanxxxxxxxxxxx = this.a.l.d_(_snowmanxxxxxxxxxx);
         buo _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b();
         ddh _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.k(this.a.l, _snowmanxxxxxxxxxx);
         if (_snowmanxxxxxxx > (double)this.a.G && _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx < (double)Math.max(1.0F, this.a.cy())
            || !_snowmanxxxxxxxxxxxxx.b() && this.a.cE() < _snowmanxxxxxxxxxxxxx.c(gc.a.b) + (double)_snowmanxxxxxxxxxx.v() && !_snowmanxxxxxxxxxxxx.a(aed.p) && !_snowmanxxxxxxxxxxxx.a(aed.M)) {
            this.a.v().a();
            this.h = avb.a.d;
         }
      } else if (this.h == avb.a.d) {
         this.a.q((float)(this.e * this.a.b(arl.d)));
         if (this.a.ao()) {
            this.h = avb.a.a;
         }
      } else {
         this.a.t(0.0F);
      }
   }

   private boolean b(float var1, float var2) {
      ayj _snowman = this.a.x();
      if (_snowman != null) {
         cxc _snowmanx = _snowman.q();
         if (_snowmanx != null && _snowmanx.a(this.a.l, afm.c(this.a.cD() + (double)_snowman), afm.c(this.a.cE()), afm.c(this.a.cH() + (double)_snowman)) != cwz.c) {
            return false;
         }
      }

      return true;
   }

   protected float a(float var1, float var2, float var3) {
      float _snowman = afm.g(_snowman - _snowman);
      if (_snowman > _snowman) {
         _snowman = _snowman;
      }

      if (_snowman < -_snowman) {
         _snowman = -_snowman;
      }

      float _snowmanx = _snowman + _snowman;
      if (_snowmanx < 0.0F) {
         _snowmanx += 360.0F;
      } else if (_snowmanx > 360.0F) {
         _snowmanx -= 360.0F;
      }

      return _snowmanx;
   }

   public double d() {
      return this.b;
   }

   public double e() {
      return this.c;
   }

   public double f() {
      return this.d;
   }

   public static enum a {
      a,
      b,
      c,
      d;

      private a() {
      }
   }
}
