import java.util.Random;

public class bav extends bay {
   public float b;
   public float c;
   public float d;
   public float bo;
   public float bp;
   public float bq;
   public float br;
   public float bs;
   private float bt;
   private float bu;
   private float bv;
   private float bw;
   private float bx;
   private float by;

   public bav(aqe<? extends bav> var1, brx var2) {
      super(_snowman, _snowman);
      this.J.setSeed((long)this.Y());
      this.bu = 1.0F / (this.J.nextFloat() + 1.0F) * 0.2F;
   }

   @Override
   protected void o() {
      this.bk.a(0, new bav.b(this));
      this.bk.a(1, new bav.a());
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 10.0);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.5F;
   }

   @Override
   protected adp I() {
      return adq.oG;
   }

   @Override
   protected adp e(apk var1) {
      return adq.oI;
   }

   @Override
   protected adp dq() {
      return adq.oH;
   }

   @Override
   protected float dG() {
      return 0.4F;
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   public void k() {
      super.k();
      this.c = this.b;
      this.bo = this.d;
      this.bq = this.bp;
      this.bs = this.br;
      this.bp = this.bp + this.bu;
      if ((double)this.bp > Math.PI * 2) {
         if (this.l.v) {
            this.bp = (float) (Math.PI * 2);
         } else {
            this.bp = (float)((double)this.bp - (Math.PI * 2));
            if (this.J.nextInt(10) == 0) {
               this.bu = 1.0F / (this.J.nextFloat() + 1.0F) * 0.2F;
            }

            this.l.a(this, (byte)19);
         }
      }

      if (this.aH()) {
         if (this.bp < (float) Math.PI) {
            float _snowman = this.bp / (float) Math.PI;
            this.br = afm.a(_snowman * _snowman * (float) Math.PI) * (float) Math.PI * 0.25F;
            if ((double)_snowman > 0.75) {
               this.bt = 1.0F;
               this.bv = 1.0F;
            } else {
               this.bv *= 0.8F;
            }
         } else {
            this.br = 0.0F;
            this.bt *= 0.9F;
            this.bv *= 0.99F;
         }

         if (!this.l.v) {
            this.n((double)(this.bw * this.bt), (double)(this.bx * this.bt), (double)(this.by * this.bt));
         }

         dcn _snowman = this.cC();
         float _snowmanx = afm.a(c(_snowman));
         this.aA = this.aA + (-((float)afm.d(_snowman.b, _snowman.d)) * (180.0F / (float)Math.PI) - this.aA) * 0.1F;
         this.p = this.aA;
         this.d = (float)((double)this.d + Math.PI * (double)this.bv * 1.5);
         this.b = this.b + (-((float)afm.d((double)_snowmanx, _snowman.c)) * (180.0F / (float)Math.PI) - this.b) * 0.1F;
      } else {
         this.br = afm.e(afm.a(this.bp)) * (float) Math.PI * 0.25F;
         if (!this.l.v) {
            double _snowman = this.cC().c;
            if (this.a(apw.y)) {
               _snowman = 0.05 * (double)(this.b(apw.y).c() + 1);
            } else if (!this.aB()) {
               _snowman -= 0.08;
            }

            this.n(0.0, _snowman * 0.98F, 0.0);
         }

         this.b = (float)((double)this.b + (double)(-90.0F - this.b) * 0.02);
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (super.a(_snowman, _snowman) && this.cZ() != null) {
         this.eL();
         return true;
      } else {
         return false;
      }
   }

   private dcn i(dcn var1) {
      dcn _snowman = _snowman.a(this.c * (float) (Math.PI / 180.0));
      return _snowman.b(-this.aB * (float) (Math.PI / 180.0));
   }

   private void eL() {
      this.a(adq.oJ, this.dG(), this.dH());
      dcn _snowman = this.i(new dcn(0.0, -1.0, 0.0)).b(this.cD(), this.cE(), this.cH());

      for (int _snowmanx = 0; _snowmanx < 30; _snowmanx++) {
         dcn _snowmanxx = this.i(new dcn((double)this.J.nextFloat() * 0.6 - 0.3, -1.0, (double)this.J.nextFloat() * 0.6 - 0.3));
         dcn _snowmanxxx = _snowmanxx.a(0.3 + (double)(this.J.nextFloat() * 2.0F));
         ((aag)this.l).a(hh.V, _snowman.b, _snowman.c + 0.5, _snowman.d, 0, _snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d, 0.1F);
      }
   }

   @Override
   public void g(dcn var1) {
      this.a(aqr.a, this.cC());
   }

   public static boolean b(aqe<bav> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.v() > 45 && _snowman.v() < _snowman.t_();
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 19) {
         this.bp = 0.0F;
      } else {
         super.a(_snowman);
      }
   }

   public void a(float var1, float var2, float var3) {
      this.bw = _snowman;
      this.bx = _snowman;
      this.by = _snowman;
   }

   public boolean eK() {
      return this.bw != 0.0F || this.bx != 0.0F || this.by != 0.0F;
   }

   class a extends avv {
      private int b;

      private a() {
      }

      @Override
      public boolean a() {
         aqm _snowman = bav.this.cZ();
         return bav.this.aE() && _snowman != null ? bav.this.h((aqa)_snowman) < 100.0 : false;
      }

      @Override
      public void c() {
         this.b = 0;
      }

      @Override
      public void e() {
         this.b++;
         aqm _snowman = bav.this.cZ();
         if (_snowman != null) {
            dcn _snowmanx = new dcn(bav.this.cD() - _snowman.cD(), bav.this.cE() - _snowman.cE(), bav.this.cH() - _snowman.cH());
            ceh _snowmanxx = bav.this.l.d_(new fx(bav.this.cD() + _snowmanx.b, bav.this.cE() + _snowmanx.c, bav.this.cH() + _snowmanx.d));
            cux _snowmanxxx = bav.this.l.b(new fx(bav.this.cD() + _snowmanx.b, bav.this.cE() + _snowmanx.c, bav.this.cH() + _snowmanx.d));
            if (_snowmanxxx.a(aef.b) || _snowmanxx.g()) {
               double _snowmanxxxx = _snowmanx.f();
               if (_snowmanxxxx > 0.0) {
                  _snowmanx.d();
                  float _snowmanxxxxx = 3.0F;
                  if (_snowmanxxxx > 5.0) {
                     _snowmanxxxxx = (float)((double)_snowmanxxxxx - (_snowmanxxxx - 5.0) / 5.0);
                  }

                  if (_snowmanxxxxx > 0.0F) {
                     _snowmanx = _snowmanx.a((double)_snowmanxxxxx);
                  }
               }

               if (_snowmanxx.g()) {
                  _snowmanx = _snowmanx.a(0.0, _snowmanx.c, 0.0);
               }

               bav.this.a((float)_snowmanx.b / 20.0F, (float)_snowmanx.c / 20.0F, (float)_snowmanx.d / 20.0F);
            }

            if (this.b % 10 == 5) {
               bav.this.l.a(hh.e, bav.this.cD(), bav.this.cE(), bav.this.cH(), 0.0, 0.0, 0.0);
            }
         }
      }
   }

   class b extends avv {
      private final bav b;

      public b(bav var2) {
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void e() {
         int _snowman = this.b.dd();
         if (_snowman > 100) {
            this.b.a(0.0F, 0.0F, 0.0F);
         } else if (this.b.cY().nextInt(50) == 0 || !this.b.L || !this.b.eK()) {
            float _snowmanx = this.b.cY().nextFloat() * (float) (Math.PI * 2);
            float _snowmanxx = afm.b(_snowmanx) * 0.2F;
            float _snowmanxxx = -0.1F + this.b.cY().nextFloat() * 0.2F;
            float _snowmanxxxx = afm.a(_snowmanx) * 0.2F;
            this.b.a(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }
   }
}
