import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;

public class bde extends bej implements bdu {
   private boolean d;
   protected final ayl b;
   protected final ayi c;

   public bde(aqe<? extends bde> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
      this.bh = new bde.d(this);
      this.a(cwz.h, 0.0F);
      this.b = new ayl(this, _snowman);
      this.c = new ayi(this, _snowman);
   }

   @Override
   protected void m() {
      this.bk.a(1, new bde.c(this, 1.0));
      this.bk.a(2, new bde.f(this, 1.0, 40, 10.0F));
      this.bk.a(2, new bde.a(this, 1.0, false));
      this.bk.a(5, new bde.b(this, 1.0));
      this.bk.a(6, new bde.e(this, 1.0, this.l.t_()));
      this.bk.a(7, new awt(this, 1.0));
      this.bl.a(1, new axp(this, bde.class).a(bel.class));
      this.bl.a(2, new axq<>(this, bfw.class, 10, true, false, this::i));
      this.bl.a(3, new axq<>(this, bfe.class, false));
      this.bl.a(3, new axq<>(this, bai.class, true));
      this.bl.a(5, new axq<>(this, bax.class, 10, true, false, bax.bo));
   }

   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.b(aqf.b).a() && this.J.nextFloat() < 0.03F) {
         this.a(aqf.b, new bmb(bmd.qO));
         this.bm[aqf.b.b()] = 2.0F;
      }

      return _snowman;
   }

   public static boolean a(aqe<bde> var0, bsk var1, aqp var2, fx var3, Random var4) {
      Optional<vj<bsv>> _snowman = _snowman.i(_snowman);
      boolean _snowmanx = _snowman.ad() != aor.a && a(_snowman, _snowman, _snowman) && (_snowman == aqp.c || _snowman.b(_snowman).a(aef.b));
      return !Objects.equals(_snowman, Optional.of(btb.h)) && !Objects.equals(_snowman, Optional.of(btb.l)) ? _snowman.nextInt(40) == 0 && a(_snowman, _snowman) && _snowmanx : _snowman.nextInt(15) == 0 && _snowmanx;
   }

   private static boolean a(bry var0, fx var1) {
      return _snowman.v() < _snowman.t_() - 5;
   }

   @Override
   protected boolean eK() {
      return false;
   }

   @Override
   protected adp I() {
      return this.aE() ? adq.cU : adq.cT;
   }

   @Override
   protected adp e(apk var1) {
      return this.aE() ? adq.cY : adq.cX;
   }

   @Override
   protected adp dq() {
      return this.aE() ? adq.cW : adq.cV;
   }

   @Override
   protected adp eL() {
      return adq.da;
   }

   @Override
   protected adp av() {
      return adq.db;
   }

   @Override
   protected bmb eM() {
      return bmb.b;
   }

   @Override
   protected void a(aos var1) {
      if ((double)this.J.nextFloat() > 0.9) {
         int _snowman = this.J.nextInt(16);
         if (_snowman < 10) {
            this.a(aqf.a, new bmb(bmd.qM));
         } else {
            this.a(aqf.a, new bmb(bmd.mi));
         }
      }
   }

   @Override
   protected boolean a(bmb var1, bmb var2) {
      if (_snowman.b() == bmd.qO) {
         return false;
      } else if (_snowman.b() == bmd.qM) {
         return _snowman.b() == bmd.qM ? _snowman.g() < _snowman.g() : false;
      } else {
         return _snowman.b() == bmd.qM ? true : super.a(_snowman, _snowman);
      }
   }

   @Override
   protected boolean eN() {
      return false;
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this);
   }

   public boolean i(@Nullable aqm var1) {
      return _snowman != null ? !this.l.M() || _snowman.aE() : false;
   }

   @Override
   public boolean bV() {
      return !this.bB();
   }

   private boolean eW() {
      if (this.d) {
         return true;
      } else {
         aqm _snowman = this.A();
         return _snowman != null && _snowman.aE();
      }
   }

   @Override
   public void g(dcn var1) {
      if (this.dS() && this.aE() && this.eW()) {
         this.a(0.01F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.9));
      } else {
         super.g(_snowman);
      }
   }

   @Override
   public void aJ() {
      if (!this.l.v) {
         if (this.dS() && this.aE() && this.eW()) {
            this.bj = this.b;
            this.h(true);
         } else {
            this.bj = this.c;
            this.h(false);
         }
      }
   }

   protected boolean eO() {
      cxd _snowman = this.x().k();
      if (_snowman != null) {
         fx _snowmanx = _snowman.m();
         if (_snowmanx != null) {
            double _snowmanxx = this.h((double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w());
            if (_snowmanxx < 4.0) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void a(aqm var1, float var2) {
      bgy _snowman = new bgy(this.l, this, new bmb(bmd.qM));
      double _snowmanx = _snowman.cD() - this.cD();
      double _snowmanxx = _snowman.e(0.3333333333333333) - _snowman.cE();
      double _snowmanxxx = _snowman.cH() - this.cH();
      double _snowmanxxxx = (double)afm.a(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
      _snowman.c(_snowmanx, _snowmanxx + _snowmanxxxx * 0.2F, _snowmanxxx, 1.6F, (float)(14 - this.l.ad().a() * 4));
      this.a(adq.cZ, 1.0F, 1.0F / (this.cY().nextFloat() * 0.4F + 0.8F));
      this.l.c(_snowman);
   }

   public void t(boolean var1) {
      this.d = _snowman;
   }

   static class a extends axm {
      private final bde b;

      public a(bde var1, double var2, boolean var4) {
         super(_snowman, _snowman, _snowman);
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return super.a() && this.b.i(this.b.A());
      }

      @Override
      public boolean b() {
         return super.b() && this.b.i(this.b.A());
      }
   }

   static class b extends awj {
      private final bde g;

      public b(bde var1, double var2) {
         super(_snowman, _snowman, 8, 2);
         this.g = _snowman;
      }

      @Override
      public boolean a() {
         return super.a() && !this.g.l.M() && this.g.aE() && this.g.cE() >= (double)(this.g.l.t_() - 3);
      }

      @Override
      public boolean b() {
         return super.b();
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         fx _snowman = _snowman.b();
         return _snowman.w(_snowman) && _snowman.w(_snowman.b()) ? _snowman.d_(_snowman).a(_snowman, _snowman, this.g) : false;
      }

      @Override
      public void c() {
         this.g.t(false);
         this.g.bj = this.g.c;
         super.c();
      }

      @Override
      public void d() {
         super.d();
      }
   }

   static class c extends avv {
      private final aqu a;
      private double b;
      private double c;
      private double d;
      private final double e;
      private final brx f;

      public c(aqu var1, double var2) {
         this.a = _snowman;
         this.e = _snowman;
         this.f = _snowman.l;
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         if (!this.f.M()) {
            return false;
         } else if (this.a.aE()) {
            return false;
         } else {
            dcn _snowman = this.g();
            if (_snowman == null) {
               return false;
            } else {
               this.b = _snowman.b;
               this.c = _snowman.c;
               this.d = _snowman.d;
               return true;
            }
         }
      }

      @Override
      public boolean b() {
         return !this.a.x().m();
      }

      @Override
      public void c() {
         this.a.x().a(this.b, this.c, this.d, this.e);
      }

      @Nullable
      private dcn g() {
         Random _snowman = this.a.cY();
         fx _snowmanx = this.a.cB();

         for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
            fx _snowmanxxx = _snowmanx.b(_snowman.nextInt(20) - 10, 2 - _snowman.nextInt(8), _snowman.nextInt(20) - 10);
            if (this.f.d_(_snowmanxxx).a(bup.A)) {
               return dcn.c(_snowmanxxx);
            }
         }

         return null;
      }
   }

   static class d extends avb {
      private final bde i;

      public d(bde var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         aqm _snowman = this.i.A();
         if (this.i.eW() && this.i.aE()) {
            if (_snowman != null && _snowman.cE() > this.i.cE() || this.i.d) {
               this.i.f(this.i.cC().b(0.0, 0.002, 0.0));
            }

            if (this.h != avb.a.b || this.i.x().m()) {
               this.i.q(0.0F);
               return;
            }

            double _snowmanx = this.b - this.i.cD();
            double _snowmanxx = this.c - this.i.cE();
            double _snowmanxxx = this.d - this.i.cH();
            double _snowmanxxxx = (double)afm.a(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            _snowmanxx /= _snowmanxxxx;
            float _snowmanxxxxx = (float)(afm.d(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F;
            this.i.p = this.a(this.i.p, _snowmanxxxxx, 90.0F);
            this.i.aA = this.i.p;
            float _snowmanxxxxxx = (float)(this.e * this.i.b(arl.d));
            float _snowmanxxxxxxx = afm.g(0.125F, this.i.dN(), _snowmanxxxxxx);
            this.i.q(_snowmanxxxxxxx);
            this.i.f(this.i.cC().b((double)_snowmanxxxxxxx * _snowmanx * 0.005, (double)_snowmanxxxxxxx * _snowmanxx * 0.1, (double)_snowmanxxxxxxx * _snowmanxxx * 0.005));
         } else {
            if (!this.i.t) {
               this.i.f(this.i.cC().b(0.0, -0.008, 0.0));
            }

            super.a();
         }
      }
   }

   static class e extends avv {
      private final bde a;
      private final double b;
      private final int c;
      private boolean d;

      public e(bde var1, double var2, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public boolean a() {
         return !this.a.l.M() && this.a.aE() && this.a.cE() < (double)(this.c - 2);
      }

      @Override
      public boolean b() {
         return this.a() && !this.d;
      }

      @Override
      public void e() {
         if (this.a.cE() < (double)(this.c - 1) && (this.a.x().m() || this.a.eO())) {
            dcn _snowman = azj.b(this.a, 4, 8, new dcn(this.a.cD(), (double)(this.c - 1), this.a.cH()));
            if (_snowman == null) {
               this.d = true;
               return;
            }

            this.a.x().a(_snowman.b, _snowman.c, _snowman.d, this.b);
         }
      }

      @Override
      public void c() {
         this.a.t(true);
         this.d = false;
      }

      @Override
      public void d() {
         this.a.t(false);
      }
   }

   static class f extends awv {
      private final bde a;

      public f(bdu var1, double var2, int var4, float var5) {
         super(_snowman, _snowman, _snowman, _snowman);
         this.a = (bde)_snowman;
      }

      @Override
      public boolean a() {
         return super.a() && this.a.dD().b() == bmd.qM;
      }

      @Override
      public void c() {
         super.c();
         this.a.s(true);
         this.a.c(aot.a);
      }

      @Override
      public void d() {
         super.d();
         this.a.ec();
         this.a.s(false);
      }
   }
}
