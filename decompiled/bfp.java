import java.util.EnumSet;
import javax.annotation.Nullable;

public class bfp extends bfe {
   @Nullable
   private fx bp;
   private int bq;

   public bfp(aqe<? extends bfp> var1, brx var2) {
      super(_snowman, _snowman);
      this.k = true;
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(0, new axi<>(this, bnv.a(new bmb(bmd.nv), bnw.h), adq.qu, var1 -> this.l.N() && !var1.bF()));
      this.bk.a(0, new axi<>(this, new bmb(bmd.lT), adq.qz, var1 -> this.l.M() && var1.bF()));
      this.bk.a(1, new axg(this));
      this.bk.a(1, new avd<>(this, bej.class, 8.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bdj.class, 12.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bef.class, 8.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bee.class, 8.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bdt.class, 15.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bdo.class, 12.0F, 0.5, 0.5));
      this.bk.a(1, new avd<>(this, bei.class, 10.0F, 0.5, 0.5));
      this.bk.a(1, new awp(this, 0.5));
      this.bk.a(1, new awe(this));
      this.bk.a(2, new bfp.a(this, 2.0, 0.35));
      this.bk.a(4, new awk(this, 0.35));
      this.bk.a(8, new axk(this, 0.35));
      this.bk.a(9, new avy(this, bfw.class, 3.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      return null;
   }

   @Override
   public boolean eP() {
      return false;
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() != bmd.oG && this.aX() && !this.eN() && !this.w_()) {
         if (_snowman == aot.a) {
            _snowman.a(aea.R);
         }

         if (this.eO().isEmpty()) {
            return aou.a(this.l.v);
         } else {
            if (!this.l.v) {
               this.f(_snowman);
               this.a(_snowman, this.d(), 1);
            }

            return aou.a(this.l.v);
         }
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   @Override
   protected void eW() {
      bfn.f[] _snowman = (bfn.f[])bfn.b.get(1);
      bfn.f[] _snowmanx = (bfn.f[])bfn.b.get(2);
      if (_snowman != null && _snowmanx != null) {
         bqw _snowmanxx = this.eO();
         this.a(_snowmanxx, _snowman, 5);
         int _snowmanxxx = this.J.nextInt(_snowmanx.length);
         bfn.f _snowmanxxxx = _snowmanx[_snowmanxxx];
         bqv _snowmanxxxxx = _snowmanxxxx.a(this, this.J);
         if (_snowmanxxxxx != null) {
            _snowmanxx.add(_snowmanxxxxx);
         }
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("DespawnDelay", this.bq);
      if (this.bp != null) {
         _snowman.a("WanderTarget", mp.a(this.bp));
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("DespawnDelay", 99)) {
         this.bq = _snowman.h("DespawnDelay");
      }

      if (_snowman.e("WanderTarget")) {
         this.bp = mp.b(_snowman.p("WanderTarget"));
      }

      this.c_(Math.max(0, this.i()));
   }

   @Override
   public boolean h(double var1) {
      return false;
   }

   @Override
   protected void b(bqv var1) {
      if (_snowman.s()) {
         int _snowman = 3 + this.J.nextInt(4);
         this.l.c(new aqg(this.l, this.cD(), this.cE() + 0.5, this.cH(), _snowman));
      }
   }

   @Override
   protected adp I() {
      return this.eN() ? adq.qA : adq.qs;
   }

   @Override
   protected adp e(apk var1) {
      return adq.qx;
   }

   @Override
   protected adp dq() {
      return adq.qt;
   }

   @Override
   protected adp c(bmb var1) {
      blx _snowman = _snowman.b();
      return _snowman == bmd.lT ? adq.qv : adq.qw;
   }

   @Override
   protected adp t(boolean var1) {
      return _snowman ? adq.qB : adq.qy;
   }

   @Override
   public adp eQ() {
      return adq.qB;
   }

   public void u(int var1) {
      this.bq = _snowman;
   }

   public int eX() {
      return this.bq;
   }

   @Override
   public void k() {
      super.k();
      if (!this.l.v) {
         this.eY();
      }
   }

   private void eY() {
      if (this.bq > 0 && !this.eN() && --this.bq == 0) {
         this.ad();
      }
   }

   public void g(@Nullable fx var1) {
      this.bp = _snowman;
   }

   @Nullable
   private fx eZ() {
      return this.bp;
   }

   class a extends avv {
      final bfp a;
      final double b;
      final double c;

      a(bfp var2, double var3, double var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public void d() {
         this.a.g(null);
         bfp.this.bj.o();
      }

      @Override
      public boolean a() {
         fx _snowman = this.a.eZ();
         return _snowman != null && this.a(_snowman, this.b);
      }

      @Override
      public void e() {
         fx _snowman = this.a.eZ();
         if (_snowman != null && bfp.this.bj.m()) {
            if (this.a(_snowman, 10.0)) {
               dcn _snowmanx = new dcn((double)_snowman.u() - this.a.cD(), (double)_snowman.v() - this.a.cE(), (double)_snowman.w() - this.a.cH()).d();
               dcn _snowmanxx = _snowmanx.a(10.0).b(this.a.cD(), this.a.cE(), this.a.cH());
               bfp.this.bj.a(_snowmanxx.b, _snowmanxx.c, _snowmanxx.d, this.c);
            } else {
               bfp.this.bj.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), this.c);
            }
         }
      }

      private boolean a(fx var1, double var2) {
         return !_snowman.a(this.a.cA(), _snowman);
      }
   }
}
