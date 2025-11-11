import java.util.EnumSet;
import java.util.Random;

public class bdk extends aqh implements bdi {
   private static final us<Boolean> b = uv.a(bdk.class, uu.i);
   private int c = 1;

   public bdk(aqe<? extends bdk> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 5;
      this.bh = new bdk.b(this);
   }

   @Override
   protected void o() {
      this.bk.a(5, new bdk.d(this));
      this.bk.a(7, new bdk.a(this));
      this.bk.a(7, new bdk.c(this));
      this.bl.a(1, new axq<>(this, bfw.class, 10, true, false, var1 -> Math.abs(var1.cE() - this.cE()) <= 4.0));
   }

   public boolean m() {
      return this.R.a(b);
   }

   public void t(boolean var1) {
      this.R.b(b, _snowman);
   }

   public int eI() {
      return this.c;
   }

   @Override
   protected boolean L() {
      return true;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (_snowman.j() instanceof bgk && _snowman.k() instanceof bfw) {
         super.a(_snowman, 1000.0F);
         return true;
      } else {
         return super.a(_snowman, _snowman);
      }
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
   }

   public static ark.a eJ() {
      return aqn.p().a(arl.a, 10.0).a(arl.b, 100.0);
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected adp I() {
      return adq.eR;
   }

   @Override
   protected adp e(apk var1) {
      return adq.eT;
   }

   @Override
   protected adp dq() {
      return adq.eS;
   }

   @Override
   protected float dG() {
      return 5.0F;
   }

   public static boolean b(aqe<bdk> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.ad() != aor.a && _snowman.nextInt(20) == 0 && a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public int eq() {
      return 1;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("ExplosionPower", this.c);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("ExplosionPower", 99)) {
         this.c = _snowman.h("ExplosionPower");
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 2.6F;
   }

   static class a extends avv {
      private final bdk a;

      public a(bdk var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.b));
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public void e() {
         if (this.a.A() == null) {
            dcn _snowman = this.a.cC();
            this.a.p = -((float)afm.d(_snowman.b, _snowman.d)) * (180.0F / (float)Math.PI);
            this.a.aA = this.a.p;
         } else {
            aqm _snowman = this.a.A();
            double _snowmanx = 64.0;
            if (_snowman.h(this.a) < 4096.0) {
               double _snowmanxx = _snowman.cD() - this.a.cD();
               double _snowmanxxx = _snowman.cH() - this.a.cH();
               this.a.p = -((float)afm.d(_snowmanxx, _snowmanxxx)) * (180.0F / (float)Math.PI);
               this.a.aA = this.a.p;
            }
         }
      }
   }

   static class b extends avb {
      private final bdk i;
      private int j;

      public b(bdk var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.h == avb.a.b) {
            if (this.j-- <= 0) {
               this.j = this.j + this.i.cY().nextInt(5) + 2;
               dcn _snowman = new dcn(this.b - this.i.cD(), this.c - this.i.cE(), this.d - this.i.cH());
               double _snowmanx = _snowman.f();
               _snowman = _snowman.d();
               if (this.a(_snowman, afm.f(_snowmanx))) {
                  this.i.f(this.i.cC().e(_snowman.a(0.1)));
               } else {
                  this.h = avb.a.a;
               }
            }
         }
      }

      private boolean a(dcn var1, int var2) {
         dci _snowman = this.i.cc();

         for (int _snowmanx = 1; _snowmanx < _snowman; _snowmanx++) {
            _snowman = _snowman.c(_snowman);
            if (!this.i.l.a_(this.i, _snowman)) {
               return false;
            }
         }

         return true;
      }
   }

   static class c extends avv {
      private final bdk b;
      public int a;

      public c(bdk var1) {
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return this.b.A() != null;
      }

      @Override
      public void c() {
         this.a = 0;
      }

      @Override
      public void d() {
         this.b.t(false);
      }

      @Override
      public void e() {
         aqm _snowman = this.b.A();
         double _snowmanx = 64.0;
         if (_snowman.h(this.b) < 4096.0 && this.b.D(_snowman)) {
            brx _snowmanxx = this.b.l;
            this.a++;
            if (this.a == 10 && !this.b.aA()) {
               _snowmanxx.a(null, 1015, this.b.cB(), 0);
            }

            if (this.a == 20) {
               double _snowmanxxx = 4.0;
               dcn _snowmanxxxx = this.b.f(1.0F);
               double _snowmanxxxxx = _snowman.cD() - (this.b.cD() + _snowmanxxxx.b * 4.0);
               double _snowmanxxxxxx = _snowman.e(0.5) - (0.5 + this.b.e(0.5));
               double _snowmanxxxxxxx = _snowman.cH() - (this.b.cH() + _snowmanxxxx.d * 4.0);
               if (!this.b.aA()) {
                  _snowmanxx.a(null, 1016, this.b.cB(), 0);
               }

               bgk _snowmanxxxxxxxx = new bgk(_snowmanxx, this.b, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
               _snowmanxxxxxxxx.e = this.b.eI();
               _snowmanxxxxxxxx.d(this.b.cD() + _snowmanxxxx.b * 4.0, this.b.e(0.5) + 0.5, _snowmanxxxxxxxx.cH() + _snowmanxxxx.d * 4.0);
               _snowmanxx.c(_snowmanxxxxxxxx);
               this.a = -40;
            }
         } else if (this.a > 0) {
            this.a--;
         }

         this.b.t(this.a > 10);
      }
   }

   static class d extends avv {
      private final bdk a;

      public d(bdk var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         avb _snowman = this.a.u();
         if (!_snowman.b()) {
            return true;
         } else {
            double _snowmanx = _snowman.d() - this.a.cD();
            double _snowmanxx = _snowman.e() - this.a.cE();
            double _snowmanxxx = _snowman.f() - this.a.cH();
            double _snowmanxxxx = _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
            return _snowmanxxxx < 1.0 || _snowmanxxxx > 3600.0;
         }
      }

      @Override
      public boolean b() {
         return false;
      }

      @Override
      public void c() {
         Random _snowman = this.a.cY();
         double _snowmanx = this.a.cD() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double _snowmanxx = this.a.cE() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double _snowmanxxx = this.a.cH() + (double)((_snowman.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.a.u().a(_snowmanx, _snowmanxx, _snowmanxxx, 1.0);
      }
   }
}
