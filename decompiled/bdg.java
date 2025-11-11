import java.util.EnumSet;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bdg extends bdq implements aqs {
   private static final UUID b = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final arj c = new arj(b, "Attacking speed boost", 0.15F, arj.a.a);
   private static final us<Optional<ceh>> d = uv.a(bdg.class, uu.h);
   private static final us<Boolean> bo = uv.a(bdg.class, uu.i);
   private static final us<Boolean> bp = uv.a(bdg.class, uu.i);
   private static final Predicate<aqm> bq = var0 -> var0 instanceof bdh && ((bdh)var0).eK();
   private int br = Integer.MIN_VALUE;
   private int bs;
   private static final afh bt = afu.a(20, 39);
   private int bu;
   private UUID bv;

   public bdg(aqe<? extends bdg> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
      this.a(cwz.h, -1.0F);
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(1, new bdg.a(this));
      this.bk.a(2, new awf(this, 1.0, false));
      this.bk.a(7, new axk(this, 1.0, 0.0F));
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new aws(this));
      this.bk.a(10, new bdg.b(this));
      this.bk.a(11, new bdg.d(this));
      this.bl.a(1, new bdg.c(this, this::a_));
      this.bl.a(2, new axp(this));
      this.bl.a(3, new axq<>(this, bdh.class, 10, true, false, bq));
      this.bl.a(4, new axw<>(this, false));
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 40.0).a(arl.d, 0.3F).a(arl.f, 7.0).a(arl.b, 64.0);
   }

   @Override
   public void h(@Nullable aqm var1) {
      super.h(_snowman);
      arh _snowman = this.a(arl.d);
      if (_snowman == null) {
         this.bs = 0;
         this.R.b(bo, false);
         this.R.b(bp, false);
         _snowman.d(c);
      } else {
         this.bs = this.K;
         this.R.b(bo, true);
         if (!_snowman.a(c)) {
            _snowman.b(c);
         }
      }
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(d, Optional.empty());
      this.R.a(bo, false);
      this.R.a(bp, false);
   }

   @Override
   public void G_() {
      this.a_(bt.a(this.J));
   }

   @Override
   public void a_(int var1) {
      this.bu = _snowman;
   }

   @Override
   public int E_() {
      return this.bu;
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.bv = _snowman;
   }

   @Override
   public UUID F_() {
      return this.bv;
   }

   public void eK() {
      if (this.K >= this.br + 400) {
         this.br = this.K;
         if (!this.aA()) {
            this.l.a(this.cD(), this.cG(), this.cH(), adq.dC, this.cu(), 2.5F, 1.0F, false);
         }
      }
   }

   @Override
   public void a(us<?> var1) {
      if (bo.equals(_snowman) && this.eO() && this.l.v) {
         this.eK();
      }

      super.a(_snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      ceh _snowman = this.eM();
      if (_snowman != null) {
         _snowman.a("carriedBlockState", mp.a(_snowman));
      }

      this.c(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      ceh _snowman = null;
      if (_snowman.c("carriedBlockState", 10)) {
         _snowman = mp.c(_snowman.p("carriedBlockState"));
         if (_snowman.g()) {
            _snowman = null;
         }
      }

      this.c(_snowman);
      this.a((aag)this.l, _snowman);
   }

   private boolean g(bfw var1) {
      bmb _snowman = _snowman.bm.b.get(3);
      if (_snowman.b() == bup.cU.h()) {
         return false;
      } else {
         dcn _snowmanx = _snowman.f(1.0F).d();
         dcn _snowmanxx = new dcn(this.cD() - _snowman.cD(), this.cG() - _snowman.cG(), this.cH() - _snowman.cH());
         double _snowmanxxx = _snowmanxx.f();
         _snowmanxx = _snowmanxx.d();
         double _snowmanxxxx = _snowmanx.b(_snowmanxx);
         return _snowmanxxxx > 1.0 - 0.025 / _snowmanxxx ? _snowman.D(this) : false;
      }
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 2.55F;
   }

   @Override
   public void k() {
      if (this.l.v) {
         for (int _snowman = 0; _snowman < 2; _snowman++) {
            this.l
               .a(hh.Q, this.d(0.5), this.cF() - 0.25, this.g(0.5), (this.J.nextDouble() - 0.5) * 2.0, -this.J.nextDouble(), (this.J.nextDouble() - 0.5) * 2.0);
         }
      }

      this.aQ = false;
      if (!this.l.v) {
         this.a((aag)this.l, true);
      }

      super.k();
   }

   @Override
   public boolean dO() {
      return true;
   }

   @Override
   protected void N() {
      if (this.l.M() && this.K >= this.bs + 600) {
         float _snowman = this.aR();
         if (_snowman > 0.5F && this.l.e(this.cB()) && this.J.nextFloat() * 30.0F < (_snowman - 0.4F) * 2.0F) {
            this.h(null);
            this.eL();
         }
      }

      super.N();
   }

   protected boolean eL() {
      if (!this.l.s_() && this.aX()) {
         double _snowman = this.cD() + (this.J.nextDouble() - 0.5) * 64.0;
         double _snowmanx = this.cE() + (double)(this.J.nextInt(64) - 32);
         double _snowmanxx = this.cH() + (this.J.nextDouble() - 0.5) * 64.0;
         return this.p(_snowman, _snowmanx, _snowmanxx);
      } else {
         return false;
      }
   }

   private boolean a(aqa var1) {
      dcn _snowman = new dcn(this.cD() - _snowman.cD(), this.e(0.5) - _snowman.cG(), this.cH() - _snowman.cH());
      _snowman = _snowman.d();
      double _snowmanx = 16.0;
      double _snowmanxx = this.cD() + (this.J.nextDouble() - 0.5) * 8.0 - _snowman.b * 16.0;
      double _snowmanxxx = this.cE() + (double)(this.J.nextInt(16) - 8) - _snowman.c * 16.0;
      double _snowmanxxxx = this.cH() + (this.J.nextDouble() - 0.5) * 8.0 - _snowman.d * 16.0;
      return this.p(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   private boolean p(double var1, double var3, double var5) {
      fx.a _snowman = new fx.a(_snowman, _snowman, _snowman);

      while (_snowman.v() > 0 && !this.l.d_(_snowman).c().c()) {
         _snowman.c(gc.a);
      }

      ceh _snowmanx = this.l.d_(_snowman);
      boolean _snowmanxx = _snowmanx.c().c();
      boolean _snowmanxxx = _snowmanx.m().a(aef.b);
      if (_snowmanxx && !_snowmanxxx) {
         boolean _snowmanxxxx = this.a(_snowman, _snowman, _snowman, true);
         if (_snowmanxxxx && !this.aA()) {
            this.l.a(null, this.m, this.n, this.o, adq.dD, this.cu(), 1.0F, 1.0F);
            this.a(adq.dD, 1.0F, 1.0F);
         }

         return _snowmanxxxx;
      } else {
         return false;
      }
   }

   @Override
   protected adp I() {
      return this.eN() ? adq.dB : adq.dy;
   }

   @Override
   protected adp e(apk var1) {
      return adq.dA;
   }

   @Override
   protected adp dq() {
      return adq.dz;
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      ceh _snowman = this.eM();
      if (_snowman != null) {
         this.a(_snowman.b());
      }
   }

   public void c(@Nullable ceh var1) {
      this.R.b(d, Optional.ofNullable(_snowman));
   }

   @Nullable
   public ceh eM() {
      return this.R.a(d).orElse(null);
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (_snowman instanceof apm) {
         for (int _snowman = 0; _snowman < 64; _snowman++) {
            if (this.eL()) {
               return true;
            }
         }

         return false;
      } else {
         boolean _snowmanx = super.a(_snowman, _snowman);
         if (!this.l.s_() && !(_snowman.k() instanceof aqm) && this.J.nextInt(10) != 0) {
            this.eL();
         }

         return _snowmanx;
      }
   }

   public boolean eN() {
      return this.R.a(bo);
   }

   public boolean eO() {
      return this.R.a(bp);
   }

   public void eP() {
      this.R.b(bp, true);
   }

   @Override
   public boolean K() {
      return super.K() || this.eM() != null;
   }

   static class a extends avv {
      private final bdg a;
      private aqm b;

      public a(bdg var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.c, avv.a.a));
      }

      @Override
      public boolean a() {
         this.b = this.a.A();
         if (!(this.b instanceof bfw)) {
            return false;
         } else {
            double _snowman = this.b.h(this.a);
            return _snowman > 256.0 ? false : this.a.g((bfw)this.b);
         }
      }

      @Override
      public void c() {
         this.a.x().o();
      }

      @Override
      public void e() {
         this.a.t().a(this.b.cD(), this.b.cG(), this.b.cH());
      }
   }

   static class b extends avv {
      private final bdg a;

      public b(bdg var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         if (this.a.eM() == null) {
            return false;
         } else {
            return !this.a.l.V().b(brt.b) ? false : this.a.cY().nextInt(2000) == 0;
         }
      }

      @Override
      public void e() {
         Random _snowman = this.a.cY();
         brx _snowmanx = this.a.l;
         int _snowmanxx = afm.c(this.a.cD() - 1.0 + _snowman.nextDouble() * 2.0);
         int _snowmanxxx = afm.c(this.a.cE() + _snowman.nextDouble() * 2.0);
         int _snowmanxxxx = afm.c(this.a.cH() - 1.0 + _snowman.nextDouble() * 2.0);
         fx _snowmanxxxxx = new fx(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         ceh _snowmanxxxxxx = _snowmanx.d_(_snowmanxxxxx);
         fx _snowmanxxxxxxx = _snowmanxxxxx.c();
         ceh _snowmanxxxxxxxx = _snowmanx.d_(_snowmanxxxxxxx);
         ceh _snowmanxxxxxxxxx = this.a.eM();
         if (_snowmanxxxxxxxxx != null) {
            _snowmanxxxxxxxxx = buo.b(_snowmanxxxxxxxxx, (bry)this.a.l, _snowmanxxxxx);
            if (this.a(_snowmanx, _snowmanxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx)) {
               _snowmanx.a(_snowmanxxxxx, _snowmanxxxxxxxxx, 3);
               this.a.c(null);
            }
         }
      }

      private boolean a(brx var1, fx var2, ceh var3, ceh var4, ceh var5, fx var6) {
         return _snowman.g() && !_snowman.g() && !_snowman.a(bup.z) && _snowman.r(_snowman, _snowman) && _snowman.a((brz)_snowman, _snowman) && _snowman.a(this.a, dci.a(dcn.b(_snowman))).isEmpty();
      }
   }

   static class c extends axq<bfw> {
      private final bdg i;
      private bfw j;
      private int k;
      private int l;
      private final azg m;
      private final azg n = new azg().c();

      public c(bdg var1, @Nullable Predicate<aqm> var2) {
         super(_snowman, bfw.class, 10, false, false, _snowman);
         this.i = _snowman;
         this.m = new azg().a(this.k()).a(var1x -> _snowman.g((bfw)var1x));
      }

      @Override
      public boolean a() {
         this.j = this.i.l.a(this.m, this.i);
         return this.j != null;
      }

      @Override
      public void c() {
         this.k = 5;
         this.l = 0;
         this.i.eP();
      }

      @Override
      public void d() {
         this.j = null;
         super.d();
      }

      @Override
      public boolean b() {
         if (this.j != null) {
            if (!this.i.g(this.j)) {
               return false;
            } else {
               this.i.a(this.j, 10.0F, 10.0F);
               return true;
            }
         } else {
            return this.c != null && this.n.a(this.i, this.c) ? true : super.b();
         }
      }

      @Override
      public void e() {
         if (this.i.A() == null) {
            super.a(null);
         }

         if (this.j != null) {
            if (--this.k <= 0) {
               this.c = this.j;
               this.j = null;
               super.c();
            }
         } else {
            if (this.c != null && !this.i.br()) {
               if (this.i.g((bfw)this.c)) {
                  if (this.c.h(this.i) < 16.0) {
                     this.i.eL();
                  }

                  this.l = 0;
               } else if (this.c.h(this.i) > 256.0 && this.l++ >= 30 && this.i.a(this.c)) {
                  this.l = 0;
               }
            }

            super.e();
         }
      }
   }

   static class d extends avv {
      private final bdg a;

      public d(bdg var1) {
         this.a = _snowman;
      }

      @Override
      public boolean a() {
         if (this.a.eM() != null) {
            return false;
         } else {
            return !this.a.l.V().b(brt.b) ? false : this.a.cY().nextInt(20) == 0;
         }
      }

      @Override
      public void e() {
         Random _snowman = this.a.cY();
         brx _snowmanx = this.a.l;
         int _snowmanxx = afm.c(this.a.cD() - 2.0 + _snowman.nextDouble() * 4.0);
         int _snowmanxxx = afm.c(this.a.cE() + _snowman.nextDouble() * 3.0);
         int _snowmanxxxx = afm.c(this.a.cH() - 2.0 + _snowman.nextDouble() * 4.0);
         fx _snowmanxxxxx = new fx(_snowmanxx, _snowmanxxx, _snowmanxxxx);
         ceh _snowmanxxxxxx = _snowmanx.d_(_snowmanxxxxx);
         buo _snowmanxxxxxxx = _snowmanxxxxxx.b();
         dcn _snowmanxxxxxxxx = new dcn((double)afm.c(this.a.cD()) + 0.5, (double)_snowmanxxx + 0.5, (double)afm.c(this.a.cH()) + 0.5);
         dcn _snowmanxxxxxxxxx = new dcn((double)_snowmanxx + 0.5, (double)_snowmanxxx + 0.5, (double)_snowmanxxxx + 0.5);
         dcj _snowmanxxxxxxxxxx = _snowmanx.a(new brf(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, brf.a.b, brf.b.a, this.a));
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a().equals(_snowmanxxxxx);
         if (_snowmanxxxxxxx.a(aed.T) && _snowmanxxxxxxxxxxx) {
            _snowmanx.a(_snowmanxxxxx, false);
            this.a.c(_snowmanxxxxxx.b().n());
         }
      }
   }
}
