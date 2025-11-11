import java.util.OptionalInt;
import javax.annotation.Nullable;

public class bgh extends bgm implements bgj {
   private static final us<bmb> b = uv.a(bgh.class, uu.g);
   private static final us<OptionalInt> c = uv.a(bgh.class, uu.r);
   private static final us<Boolean> d = uv.a(bgh.class, uu.i);
   private int e;
   private int f;
   private aqm g;

   public bgh(aqe<? extends bgh> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgh(brx var1, double var2, double var4, double var6, bmb var8) {
      super(aqe.B, _snowman);
      this.e = 0;
      this.d(_snowman, _snowman, _snowman);
      int _snowman = 1;
      if (!_snowman.a() && _snowman.n()) {
         this.R.b(b, _snowman.i());
         _snowman += _snowman.a("Fireworks").f("Flight");
      }

      this.n(this.J.nextGaussian() * 0.001, 0.05, this.J.nextGaussian() * 0.001);
      this.f = 10 * _snowman + this.J.nextInt(6) + this.J.nextInt(7);
   }

   public bgh(brx var1, @Nullable aqa var2, double var3, double var5, double var7, bmb var9) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman);
   }

   public bgh(brx var1, bmb var2, aqm var3) {
      this(_snowman, _snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman);
      this.R.b(c, OptionalInt.of(_snowman.Y()));
      this.g = _snowman;
   }

   public bgh(brx var1, bmb var2, double var3, double var5, double var7, boolean var9) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
      this.R.b(d, _snowman);
   }

   public bgh(brx var1, bmb var2, aqa var3, double var4, double var6, double var8, boolean var10) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman);
   }

   @Override
   protected void e() {
      this.R.a(b, bmb.b);
      this.R.a(c, OptionalInt.empty());
      this.R.a(d, false);
   }

   @Override
   public boolean a(double var1) {
      return _snowman < 4096.0 && !this.n();
   }

   @Override
   public boolean j(double var1, double var3, double var5) {
      return super.j(_snowman, _snowman, _snowman) && !this.n();
   }

   @Override
   public void j() {
      super.j();
      if (this.n()) {
         if (this.g == null) {
            this.R.a(c).ifPresent(var1x -> {
               aqa _snowman = this.l.a(var1x);
               if (_snowman instanceof aqm) {
                  this.g = (aqm)_snowman;
               }
            });
         }

         if (this.g != null) {
            if (this.g.ef()) {
               dcn _snowman = this.g.bh();
               double _snowmanx = 1.5;
               double _snowmanxx = 0.1;
               dcn _snowmanxxx = this.g.cC();
               this.g.f(_snowmanxxx.b(_snowman.b * 0.1 + (_snowman.b * 1.5 - _snowmanxxx.b) * 0.5, _snowman.c * 0.1 + (_snowman.c * 1.5 - _snowmanxxx.c) * 0.5, _snowman.d * 0.1 + (_snowman.d * 1.5 - _snowmanxxx.d) * 0.5));
            }

            this.d(this.g.cD(), this.g.cE(), this.g.cH());
            this.f(this.g.cC());
         }
      } else {
         if (!this.h()) {
            double _snowman = this.u ? 1.0 : 1.15;
            this.f(this.cC().d(_snowman, 1.0, _snowman).b(0.0, 0.04, 0.0));
         }

         dcn _snowman = this.cC();
         this.a(aqr.a, _snowman);
         this.f(_snowman);
      }

      dcl _snowman = bgn.a(this, this::a);
      if (!this.H) {
         this.a(_snowman);
         this.Z = true;
      }

      this.x();
      if (this.e == 0 && !this.aA()) {
         this.l.a(null, this.cD(), this.cE(), this.cH(), adq.ee, adr.i, 3.0F, 1.0F);
      }

      this.e++;
      if (this.l.v && this.e % 2 < 2) {
         this.l.a(hh.y, this.cD(), this.cE() - 0.3, this.cH(), this.J.nextGaussian() * 0.05, -this.cC().c * 0.5, this.J.nextGaussian() * 0.05);
      }

      if (!this.l.v && this.e > this.f) {
         this.i();
      }
   }

   private void i() {
      this.l.a(this, (byte)17);
      this.m();
      this.ad();
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      if (!this.l.v) {
         this.i();
      }
   }

   @Override
   protected void a(dcj var1) {
      fx _snowman = new fx(_snowman.a());
      this.l.d_(_snowman).a(this.l, _snowman, this);
      if (!this.l.s_() && this.k()) {
         this.i();
      }

      super.a(_snowman);
   }

   private boolean k() {
      bmb _snowman = this.R.a(b);
      md _snowmanx = _snowman.a() ? null : _snowman.b("Fireworks");
      mj _snowmanxx = _snowmanx != null ? _snowmanx.d("Explosions", 10) : null;
      return _snowmanxx != null && !_snowmanxx.isEmpty();
   }

   private void m() {
      float _snowman = 0.0F;
      bmb _snowmanx = this.R.a(b);
      md _snowmanxx = _snowmanx.a() ? null : _snowmanx.b("Fireworks");
      mj _snowmanxxx = _snowmanxx != null ? _snowmanxx.d("Explosions", 10) : null;
      if (_snowmanxxx != null && !_snowmanxxx.isEmpty()) {
         _snowman = 5.0F + (float)(_snowmanxxx.size() * 2);
      }

      if (_snowman > 0.0F) {
         if (this.g != null) {
            this.g.a(apk.a(this, this.v()), 5.0F + (float)(_snowmanxxx.size() * 2));
         }

         double _snowmanxxxx = 5.0;
         dcn _snowmanxxxxx = this.cA();

         for (aqm _snowmanxxxxxx : this.l.a(aqm.class, this.cc().g(5.0))) {
            if (_snowmanxxxxxx != this.g && !(this.h(_snowmanxxxxxx) > 25.0)) {
               boolean _snowmanxxxxxxx = false;

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 2; _snowmanxxxxxxxx++) {
                  dcn _snowmanxxxxxxxxx = new dcn(_snowmanxxxxxx.cD(), _snowmanxxxxxx.e(0.5 * (double)_snowmanxxxxxxxx), _snowmanxxxxxx.cH());
                  dcl _snowmanxxxxxxxxxx = this.l.a(new brf(_snowmanxxxxx, _snowmanxxxxxxxxx, brf.a.a, brf.b.a, this));
                  if (_snowmanxxxxxxxxxx.c() == dcl.a.a) {
                     _snowmanxxxxxxx = true;
                     break;
                  }
               }

               if (_snowmanxxxxxxx) {
                  float _snowmanxxxxxxxxx = _snowman * (float)Math.sqrt((5.0 - (double)this.g(_snowmanxxxxxx)) / 5.0);
                  _snowmanxxxxxx.a(apk.a(this, this.v()), _snowmanxxxxxxxxx);
               }
            }
         }
      }
   }

   private boolean n() {
      return this.R.a(c).isPresent();
   }

   @Override
   public boolean h() {
      return this.R.a(d);
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 17 && this.l.v) {
         if (!this.k()) {
            for (int _snowman = 0; _snowman < this.J.nextInt(3) + 2; _snowman++) {
               this.l.a(hh.P, this.cD(), this.cE(), this.cH(), this.J.nextGaussian() * 0.05, 0.005, this.J.nextGaussian() * 0.05);
            }
         } else {
            bmb _snowman = this.R.a(b);
            md _snowmanx = _snowman.a() ? null : _snowman.b("Fireworks");
            dcn _snowmanxx = this.cC();
            this.l.a(this.cD(), this.cE(), this.cH(), _snowmanxx.b, _snowmanxx.c, _snowmanxx.d, _snowmanx);
         }
      }

      super.a(_snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Life", this.e);
      _snowman.b("LifeTime", this.f);
      bmb _snowman = this.R.a(b);
      if (!_snowman.a()) {
         _snowman.a("FireworksItem", _snowman.b(new md()));
      }

      _snowman.a("ShotAtAngle", this.R.a(d));
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.e = _snowman.h("Life");
      this.f = _snowman.h("LifeTime");
      bmb _snowman = bmb.a(_snowman.p("FireworksItem"));
      if (!_snowman.a()) {
         this.R.b(b, _snowman);
      }

      if (_snowman.e("ShotAtAngle")) {
         this.R.b(d, _snowman.q("ShotAtAngle"));
      }
   }

   @Override
   public bmb g() {
      bmb _snowman = this.R.a(b);
      return _snowman.a() ? new bmb(bmd.po) : _snowman;
   }

   @Override
   public boolean bL() {
      return false;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
