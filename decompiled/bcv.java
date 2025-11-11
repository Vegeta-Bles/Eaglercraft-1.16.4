import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public class bcv extends aqa {
   private static final us<bmb> c = uv.a(bcv.class, uu.g);
   private int d;
   private int e;
   private int f = 5;
   private UUID g;
   private UUID ag;
   public final float b;

   public bcv(aqe<? extends bcv> var1, brx var2) {
      super(_snowman, _snowman);
      this.b = (float)(Math.random() * Math.PI * 2.0);
   }

   public bcv(brx var1, double var2, double var4, double var6) {
      this(aqe.L, _snowman);
      this.d(_snowman, _snowman, _snowman);
      this.p = this.J.nextFloat() * 360.0F;
      this.n(this.J.nextDouble() * 0.2 - 0.1, 0.2, this.J.nextDouble() * 0.2 - 0.1);
   }

   public bcv(brx var1, double var2, double var4, double var6, bmb var8) {
      this(_snowman, _snowman, _snowman, _snowman);
      this.b(_snowman);
   }

   private bcv(bcv var1) {
      super(_snowman.X(), _snowman.l);
      this.b(_snowman.g().i());
      this.u(_snowman);
      this.d = _snowman.d;
      this.b = _snowman.b;
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
      this.ab().a(c, bmb.b);
   }

   @Override
   public void j() {
      if (this.g().a()) {
         this.ad();
      } else {
         super.j();
         if (this.e > 0 && this.e != 32767) {
            this.e--;
         }

         this.m = this.cD();
         this.n = this.cE();
         this.o = this.cH();
         dcn _snowman = this.cC();
         float _snowmanx = this.ce() - 0.11111111F;
         if (this.aE() && this.b(aef.b) > (double)_snowmanx) {
            this.u();
         } else if (this.aQ() && this.b(aef.c) > (double)_snowmanx) {
            this.v();
         } else if (!this.aB()) {
            this.f(this.cC().b(0.0, -0.04, 0.0));
         }

         if (this.l.v) {
            this.H = false;
         } else {
            this.H = !this.l.k(this);
            if (this.H) {
               this.l(this.cD(), (this.cc().b + this.cc().e) / 2.0, this.cH());
            }
         }

         if (!this.t || c(this.cC()) > 1.0E-5F || (this.K + this.Y()) % 4 == 0) {
            this.a(aqr.a, this.cC());
            float _snowmanxx = 0.98F;
            if (this.t) {
               _snowmanxx = this.l.d_(new fx(this.cD(), this.cE() - 1.0, this.cH())).b().j() * 0.98F;
            }

            this.f(this.cC().d((double)_snowmanxx, 0.98, (double)_snowmanxx));
            if (this.t) {
               dcn _snowmanxxx = this.cC();
               if (_snowmanxxx.c < 0.0) {
                  this.f(_snowmanxxx.d(1.0, -0.5, 1.0));
               }
            }
         }

         boolean _snowmanxxx = afm.c(this.m) != afm.c(this.cD()) || afm.c(this.n) != afm.c(this.cE()) || afm.c(this.o) != afm.c(this.cH());
         int _snowmanxxxx = _snowmanxxx ? 2 : 40;
         if (this.K % _snowmanxxxx == 0) {
            if (this.l.b(this.cB()).a(aef.c) && !this.aD()) {
               this.a(adq.eH, 0.4F, 2.0F + this.J.nextFloat() * 0.4F);
            }

            if (!this.l.v && this.z()) {
               this.x();
            }
         }

         if (this.d != -32768) {
            this.d++;
         }

         this.Z = this.Z | this.aK();
         if (!this.l.v) {
            double _snowmanxxxxx = this.cC().d(_snowman).g();
            if (_snowmanxxxxx > 0.01) {
               this.Z = true;
            }
         }

         if (!this.l.v && this.d >= 6000) {
            this.ad();
         }
      }
   }

   private void u() {
      dcn _snowman = this.cC();
      this.n(_snowman.b * 0.99F, _snowman.c + (double)(_snowman.c < 0.06F ? 5.0E-4F : 0.0F), _snowman.d * 0.99F);
   }

   private void v() {
      dcn _snowman = this.cC();
      this.n(_snowman.b * 0.95F, _snowman.c + (double)(_snowman.c < 0.06F ? 5.0E-4F : 0.0F), _snowman.d * 0.95F);
   }

   private void x() {
      if (this.z()) {
         for (bcv _snowman : this.l.a(bcv.class, this.cc().c(0.5, 0.0, 0.5), var1 -> var1 != this && var1.z())) {
            if (_snowman.z()) {
               this.a(_snowman);
               if (this.y) {
                  break;
               }
            }
         }
      }
   }

   private boolean z() {
      bmb _snowman = this.g();
      return this.aX() && this.e != 32767 && this.d != -32768 && this.d < 6000 && _snowman.E() < _snowman.c();
   }

   private void a(bcv var1) {
      bmb _snowman = this.g();
      bmb _snowmanx = _snowman.g();
      if (Objects.equals(this.h(), _snowman.h()) && a(_snowman, _snowmanx)) {
         if (_snowmanx.E() < _snowman.E()) {
            a(this, _snowman, _snowman, _snowmanx);
         } else {
            a(_snowman, _snowmanx, this, _snowman);
         }
      }
   }

   public static boolean a(bmb var0, bmb var1) {
      if (_snowman.b() != _snowman.b()) {
         return false;
      } else if (_snowman.E() + _snowman.E() > _snowman.c()) {
         return false;
      } else {
         return _snowman.n() ^ _snowman.n() ? false : !_snowman.n() || _snowman.o().equals(_snowman.o());
      }
   }

   public static bmb a(bmb var0, bmb var1, int var2) {
      int _snowman = Math.min(Math.min(_snowman.c(), _snowman) - _snowman.E(), _snowman.E());
      bmb _snowmanx = _snowman.i();
      _snowmanx.f(_snowman);
      _snowman.g(_snowman);
      return _snowmanx;
   }

   private static void a(bcv var0, bmb var1, bmb var2) {
      bmb _snowman = a(_snowman, _snowman, 64);
      _snowman.b(_snowman);
   }

   private static void a(bcv var0, bmb var1, bcv var2, bmb var3) {
      a(_snowman, _snowman, _snowman);
      _snowman.e = Math.max(_snowman.e, _snowman.e);
      _snowman.d = Math.min(_snowman.d, _snowman.d);
      if (_snowman.a()) {
         _snowman.ad();
      }
   }

   @Override
   public boolean aD() {
      return this.g().b().u() || super.aD();
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (!this.g().a() && this.g().b() == bmd.pm && _snowman.d()) {
         return false;
      } else if (!this.g().b().a(_snowman)) {
         return false;
      } else {
         this.aS();
         this.f = (int)((float)this.f - _snowman);
         if (this.f <= 0) {
            this.ad();
         }

         return false;
      }
   }

   @Override
   public void b(md var1) {
      _snowman.a("Health", (short)this.f);
      _snowman.a("Age", (short)this.d);
      _snowman.a("PickupDelay", (short)this.e);
      if (this.i() != null) {
         _snowman.a("Thrower", this.i());
      }

      if (this.h() != null) {
         _snowman.a("Owner", this.h());
      }

      if (!this.g().a()) {
         _snowman.a("Item", this.g().b(new md()));
      }
   }

   @Override
   public void a(md var1) {
      this.f = _snowman.g("Health");
      this.d = _snowman.g("Age");
      if (_snowman.e("PickupDelay")) {
         this.e = _snowman.g("PickupDelay");
      }

      if (_snowman.b("Owner")) {
         this.ag = _snowman.a("Owner");
      }

      if (_snowman.b("Thrower")) {
         this.g = _snowman.a("Thrower");
      }

      md _snowman = _snowman.p("Item");
      this.b(bmb.a(_snowman));
      if (this.g().a()) {
         this.ad();
      }
   }

   @Override
   public void a_(bfw var1) {
      if (!this.l.v) {
         bmb _snowman = this.g();
         blx _snowmanx = _snowman.b();
         int _snowmanxx = _snowman.E();
         if (this.e == 0 && (this.ag == null || this.ag.equals(_snowman.bS())) && _snowman.bm.e(_snowman)) {
            _snowman.a(this, _snowmanxx);
            if (_snowman.a()) {
               this.ad();
               _snowman.e(_snowmanxx);
            }

            _snowman.a(aea.e.b(_snowmanx), _snowmanxx);
            _snowman.a(this);
         }
      }
   }

   @Override
   public nr R() {
      nr _snowman = this.T();
      return (nr)(_snowman != null ? _snowman : new of(this.g().j()));
   }

   @Override
   public boolean bL() {
      return false;
   }

   @Nullable
   @Override
   public aqa b(aag var1) {
      aqa _snowman = super.b(_snowman);
      if (!this.l.v && _snowman instanceof bcv) {
         ((bcv)_snowman).x();
      }

      return _snowman;
   }

   public bmb g() {
      return this.ab().a(c);
   }

   public void b(bmb var1) {
      this.ab().b(c, _snowman);
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (c.equals(_snowman)) {
         this.g().a(this);
      }
   }

   @Nullable
   public UUID h() {
      return this.ag;
   }

   public void b(@Nullable UUID var1) {
      this.ag = _snowman;
   }

   @Nullable
   public UUID i() {
      return this.g;
   }

   public void c(@Nullable UUID var1) {
      this.g = _snowman;
   }

   public int k() {
      return this.d;
   }

   @Override
   public void m() {
      this.e = 10;
   }

   public void n() {
      this.e = 0;
   }

   public void o() {
      this.e = 32767;
   }

   public void a(int var1) {
      this.e = _snowman;
   }

   public boolean p() {
      return this.e > 0;
   }

   public void r() {
      this.d = -6000;
   }

   public void s() {
      this.o();
      this.d = 5999;
   }

   public float a(float var1) {
      return ((float)this.k() + _snowman) / 20.0F + this.b;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }

   public bcv t() {
      return new bcv(this);
   }
}
