import java.util.EnumSet;

public class bda extends bdq {
   private float b = 0.5F;
   private int c;
   private static final us<Byte> d = uv.a(bda.class, uu.a);

   public bda(aqe<? extends bda> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.h, -1.0F);
      this.a(cwz.g, 8.0F);
      this.a(cwz.l, 0.0F);
      this.a(cwz.m, 0.0F);
      this.f = 10;
   }

   @Override
   protected void o() {
      this.bk.a(4, new bda.a(this));
      this.bk.a(5, new awk(this, 1.0));
      this.bk.a(7, new axk(this, 1.0, 0.0F));
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new aws(this));
      this.bl.a(1, new axp(this).a());
      this.bl.a(2, new axq<>(this, bfw.class, true));
   }

   public static ark.a m() {
      return bdq.eR().a(arl.f, 6.0).a(arl.d, 0.23F).a(arl.b, 48.0);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(d, (byte)0);
   }

   @Override
   protected adp I() {
      return adq.aL;
   }

   @Override
   protected adp e(apk var1) {
      return adq.aO;
   }

   @Override
   protected adp dq() {
      return adq.aN;
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Override
   public void k() {
      if (!this.t && this.cC().c < 0.0) {
         this.f(this.cC().d(1.0, 0.6, 1.0));
      }

      if (this.l.v) {
         if (this.J.nextInt(24) == 0 && !this.aA()) {
            this.l.a(this.cD() + 0.5, this.cE() + 0.5, this.cH() + 0.5, adq.aM, this.cu(), 1.0F + this.J.nextFloat(), this.J.nextFloat() * 0.7F + 0.3F, false);
         }

         for (int _snowman = 0; _snowman < 2; _snowman++) {
            this.l.a(hh.L, this.d(0.5), this.cF(), this.g(0.5), 0.0, 0.0, 0.0);
         }
      }

      super.k();
   }

   @Override
   public boolean dO() {
      return true;
   }

   @Override
   protected void N() {
      this.c--;
      if (this.c <= 0) {
         this.c = 100;
         this.b = 0.5F + (float)this.J.nextGaussian() * 3.0F;
      }

      aqm _snowman = this.A();
      if (_snowman != null && _snowman.cG() > this.cG() + (double)this.b && this.c(_snowman)) {
         dcn _snowmanx = this.cC();
         this.f(this.cC().b(0.0, (0.3F - _snowmanx.c) * 0.3F, 0.0));
         this.Z = true;
      }

      super.N();
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   public boolean bq() {
      return this.eK();
   }

   private boolean eK() {
      return (this.R.a(d) & 1) != 0;
   }

   private void t(boolean var1) {
      byte _snowman = this.R.a(d);
      if (_snowman) {
         _snowman = (byte)(_snowman | 1);
      } else {
         _snowman = (byte)(_snowman & -2);
      }

      this.R.b(d, _snowman);
   }

   static class a extends avv {
      private final bda a;
      private int b;
      private int c;
      private int d;

      public a(bda var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         aqm _snowman = this.a.A();
         return _snowman != null && _snowman.aX() && this.a.c(_snowman);
      }

      @Override
      public void c() {
         this.b = 0;
      }

      @Override
      public void d() {
         this.a.t(false);
         this.d = 0;
      }

      @Override
      public void e() {
         this.c--;
         aqm _snowman = this.a.A();
         if (_snowman != null) {
            boolean _snowmanx = this.a.z().a(_snowman);
            if (_snowmanx) {
               this.d = 0;
            } else {
               this.d++;
            }

            double _snowmanxx = this.a.h((aqa)_snowman);
            if (_snowmanxx < 4.0) {
               if (!_snowmanx) {
                  return;
               }

               if (this.c <= 0) {
                  this.c = 20;
                  this.a.B(_snowman);
               }

               this.a.u().a(_snowman.cD(), _snowman.cE(), _snowman.cH(), 1.0);
            } else if (_snowmanxx < this.g() * this.g() && _snowmanx) {
               double _snowmanxxx = _snowman.cD() - this.a.cD();
               double _snowmanxxxx = _snowman.e(0.5) - this.a.e(0.5);
               double _snowmanxxxxx = _snowman.cH() - this.a.cH();
               if (this.c <= 0) {
                  this.b++;
                  if (this.b == 1) {
                     this.c = 60;
                     this.a.t(true);
                  } else if (this.b <= 4) {
                     this.c = 6;
                  } else {
                     this.c = 100;
                     this.b = 0;
                     this.a.t(false);
                  }

                  if (this.b > 1) {
                     float _snowmanxxxxxx = afm.c(afm.a(_snowmanxx)) * 0.5F;
                     if (!this.a.aA()) {
                        this.a.l.a(null, 1018, this.a.cB(), 0);
                     }

                     for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 1; _snowmanxxxxxxx++) {
                        bgp _snowmanxxxxxxxx = new bgp(
                           this.a.l, this.a, _snowmanxxx + this.a.cY().nextGaussian() * (double)_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxx + this.a.cY().nextGaussian() * (double)_snowmanxxxxxx
                        );
                        _snowmanxxxxxxxx.d(_snowmanxxxxxxxx.cD(), this.a.e(0.5) + 0.5, _snowmanxxxxxxxx.cH());
                        this.a.l.c(_snowmanxxxxxxxx);
                     }
                  }
               }

               this.a.t().a(_snowman, 10.0F, 10.0F);
            } else if (this.d < 5) {
               this.a.u().a(_snowman.cD(), _snowman.cE(), _snowman.cH(), 1.0);
            }

            super.e();
         }
      }

      private double g() {
         return this.a.b(arl.b);
      }
   }
}
