import java.util.Map.Entry;

public class aqg extends aqa {
   public int b;
   public int c;
   public int d;
   private int e = 5;
   private int f;
   private bfw g;
   private int ag;

   public aqg(brx var1, double var2, double var4, double var6, int var8) {
      this(aqe.y, _snowman);
      this.d(_snowman, _snowman, _snowman);
      this.p = (float)(this.J.nextDouble() * 360.0);
      this.n((this.J.nextDouble() * 0.2F - 0.1F) * 2.0, this.J.nextDouble() * 0.2 * 2.0, (this.J.nextDouble() * 0.2F - 0.1F) * 2.0);
      this.f = _snowman;
   }

   public aqg(aqe<? extends aqg> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
   }

   @Override
   public void j() {
      super.j();
      if (this.d > 0) {
         this.d--;
      }

      this.m = this.cD();
      this.n = this.cE();
      this.o = this.cH();
      if (this.a(aef.b)) {
         this.i();
      } else if (!this.aB()) {
         this.f(this.cC().b(0.0, -0.03, 0.0));
      }

      if (this.l.b(this.cB()).a(aef.c)) {
         this.n((double)((this.J.nextFloat() - this.J.nextFloat()) * 0.2F), 0.2F, (double)((this.J.nextFloat() - this.J.nextFloat()) * 0.2F));
         this.a(adq.eH, 0.4F, 2.0F + this.J.nextFloat() * 0.4F);
      }

      if (!this.l.b(this.cc())) {
         this.l(this.cD(), (this.cc().b + this.cc().e) / 2.0, this.cH());
      }

      double _snowman = 8.0;
      if (this.ag < this.b - 20 + this.Y() % 100) {
         if (this.g == null || this.g.h(this) > 64.0) {
            this.g = this.l.a(this, 8.0);
         }

         this.ag = this.b;
      }

      if (this.g != null && this.g.a_()) {
         this.g = null;
      }

      if (this.g != null) {
         dcn _snowmanx = new dcn(this.g.cD() - this.cD(), this.g.cE() + (double)this.g.ce() / 2.0 - this.cE(), this.g.cH() - this.cH());
         double _snowmanxx = _snowmanx.g();
         if (_snowmanxx < 64.0) {
            double _snowmanxxx = 1.0 - Math.sqrt(_snowmanxx) / 8.0;
            this.f(this.cC().e(_snowmanx.d().a(_snowmanxxx * _snowmanxxx * 0.1)));
         }
      }

      this.a(aqr.a, this.cC());
      float _snowmanx = 0.98F;
      if (this.t) {
         _snowmanx = this.l.d_(new fx(this.cD(), this.cE() - 1.0, this.cH())).b().j() * 0.98F;
      }

      this.f(this.cC().d((double)_snowmanx, 0.98, (double)_snowmanx));
      if (this.t) {
         this.f(this.cC().d(1.0, -0.9, 1.0));
      }

      this.b++;
      this.c++;
      if (this.c >= 6000) {
         this.ad();
      }
   }

   private void i() {
      dcn _snowman = this.cC();
      this.n(_snowman.b * 0.99F, Math.min(_snowman.c + 5.0E-4F, 0.06F), _snowman.d * 0.99F);
   }

   @Override
   protected void aM() {
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         this.aS();
         this.e = (int)((float)this.e - _snowman);
         if (this.e <= 0) {
            this.ad();
         }

         return false;
      }
   }

   @Override
   public void b(md var1) {
      _snowman.a("Health", (short)this.e);
      _snowman.a("Age", (short)this.c);
      _snowman.a("Value", (short)this.f);
   }

   @Override
   public void a(md var1) {
      this.e = _snowman.g("Health");
      this.c = _snowman.g("Age");
      this.f = _snowman.g("Value");
   }

   @Override
   public void a_(bfw var1) {
      if (!this.l.v) {
         if (this.d == 0 && _snowman.bu == 0) {
            _snowman.bu = 2;
            _snowman.a(this, 1);
            Entry<aqf, bmb> _snowman = bpu.a(bpw.K, _snowman, bmb::f);
            if (_snowman != null) {
               bmb _snowmanx = _snowman.getValue();
               if (!_snowmanx.a() && _snowmanx.f()) {
                  int _snowmanxx = Math.min(this.c(this.f), _snowmanx.g());
                  this.f = this.f - this.b(_snowmanxx);
                  _snowmanx.b(_snowmanx.g() - _snowmanxx);
               }
            }

            if (this.f > 0) {
               _snowman.d(this.f);
            }

            this.ad();
         }
      }
   }

   private int b(int var1) {
      return _snowman / 2;
   }

   private int c(int var1) {
      return _snowman * 2;
   }

   public int g() {
      return this.f;
   }

   public int h() {
      if (this.f >= 2477) {
         return 10;
      } else if (this.f >= 1237) {
         return 9;
      } else if (this.f >= 617) {
         return 8;
      } else if (this.f >= 307) {
         return 7;
      } else if (this.f >= 149) {
         return 6;
      } else if (this.f >= 73) {
         return 5;
      } else if (this.f >= 37) {
         return 4;
      } else if (this.f >= 17) {
         return 3;
      } else if (this.f >= 7) {
         return 2;
      } else {
         return this.f >= 3 ? 1 : 0;
      }
   }

   public static int a(int var0) {
      if (_snowman >= 2477) {
         return 2477;
      } else if (_snowman >= 1237) {
         return 1237;
      } else if (_snowman >= 617) {
         return 617;
      } else if (_snowman >= 307) {
         return 307;
      } else if (_snowman >= 149) {
         return 149;
      } else if (_snowman >= 73) {
         return 73;
      } else if (_snowman >= 37) {
         return 37;
      } else if (_snowman >= 17) {
         return 17;
      } else if (_snowman >= 7) {
         return 7;
      } else {
         return _snowman >= 3 ? 3 : 1;
      }
   }

   @Override
   public boolean bL() {
      return false;
   }

   @Override
   public oj<?> P() {
      return new oo(this);
   }
}
