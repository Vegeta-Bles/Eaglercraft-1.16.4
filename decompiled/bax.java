import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bax extends azz {
   private static final us<fx> bp = uv.a(bax.class, uu.l);
   private static final us<Boolean> bq = uv.a(bax.class, uu.i);
   private static final us<Boolean> br = uv.a(bax.class, uu.i);
   private static final us<fx> bs = uv.a(bax.class, uu.l);
   private static final us<Boolean> bt = uv.a(bax.class, uu.i);
   private static final us<Boolean> bu = uv.a(bax.class, uu.i);
   private int bv;
   public static final Predicate<aqm> bo = var0 -> var0.w_() && !var0.aE();

   public bax(aqe<? extends bax> var1, brx var2) {
      super(_snowman, _snowman);
      this.a(cwz.h, 0.0F);
      this.bh = new bax.e(this);
      this.G = 1.0F;
   }

   public void g(fx var1) {
      this.R.b(bp, _snowman);
   }

   private fx eN() {
      return this.R.a(bp);
   }

   private void h(fx var1) {
      this.R.b(bs, _snowman);
   }

   private fx eO() {
      return this.R.a(bs);
   }

   public boolean eK() {
      return this.R.a(bq);
   }

   private void t(boolean var1) {
      this.R.b(bq, _snowman);
   }

   public boolean eL() {
      return this.R.a(br);
   }

   private void u(boolean var1) {
      this.bv = _snowman ? 1 : 0;
      this.R.b(br, _snowman);
   }

   private boolean eU() {
      return this.R.a(bt);
   }

   private void v(boolean var1) {
      this.R.b(bt, _snowman);
   }

   private boolean eV() {
      return this.R.a(bu);
   }

   private void w(boolean var1) {
      this.R.b(bu, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bp, fx.b);
      this.R.a(bq, false);
      this.R.a(bs, fx.b);
      this.R.a(bt, false);
      this.R.a(bu, false);
      this.R.a(br, false);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("HomePosX", this.eN().u());
      _snowman.b("HomePosY", this.eN().v());
      _snowman.b("HomePosZ", this.eN().w());
      _snowman.a("HasEgg", this.eK());
      _snowman.b("TravelPosX", this.eO().u());
      _snowman.b("TravelPosY", this.eO().v());
      _snowman.b("TravelPosZ", this.eO().w());
   }

   @Override
   public void a(md var1) {
      int _snowman = _snowman.h("HomePosX");
      int _snowmanx = _snowman.h("HomePosY");
      int _snowmanxx = _snowman.h("HomePosZ");
      this.g(new fx(_snowman, _snowmanx, _snowmanxx));
      super.a(_snowman);
      this.t(_snowman.q("HasEgg"));
      int _snowmanxxx = _snowman.h("TravelPosX");
      int _snowmanxxxx = _snowman.h("TravelPosY");
      int _snowmanxxxxx = _snowman.h("TravelPosZ");
      this.h(new fx(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.g(this.cB());
      this.h(fx.b);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static boolean c(aqe<bax> var0, bry var1, aqp var2, fx var3, Random var4) {
      return _snowman.v() < _snowman.t_() + 4 && cbf.a(_snowman, _snowman) && _snowman.b(_snowman, 0) > 8;
   }

   @Override
   protected void o() {
      this.bk.a(0, new bax.f(this, 1.2));
      this.bk.a(1, new bax.a(this, 1.0));
      this.bk.a(1, new bax.d(this, 1.0));
      this.bk.a(2, new bax.i(this, 1.1, bup.aU.h()));
      this.bk.a(3, new bax.c(this, 1.0));
      this.bk.a(4, new bax.b(this, 1.0));
      this.bk.a(7, new bax.j(this, 1.0));
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(9, new bax.h(this, 1.0, 100));
   }

   public static ark.a eM() {
      return aqn.p().a(arl.a, 30.0).a(arl.d, 0.25);
   }

   @Override
   public boolean bV() {
      return false;
   }

   @Override
   public boolean cM() {
      return true;
   }

   @Override
   public aqq dC() {
      return aqq.e;
   }

   @Override
   public int D() {
      return 200;
   }

   @Nullable
   @Override
   protected adp I() {
      return !this.aE() && this.t && !this.w_() ? adq.pt : super.I();
   }

   @Override
   protected void d(float var1) {
      super.d(_snowman * 1.5F);
   }

   @Override
   protected adp av() {
      return adq.pE;
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      return this.w_() ? adq.pA : adq.pz;
   }

   @Nullable
   @Override
   protected adp dq() {
      return this.w_() ? adq.pv : adq.pu;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      adp _snowman = this.w_() ? adq.pD : adq.pC;
      this.a(_snowman, 0.15F, 1.0F);
   }

   @Override
   public boolean eP() {
      return super.eP() && !this.eK();
   }

   @Override
   protected float at() {
      return this.B + 0.15F;
   }

   @Override
   public float cS() {
      return this.w_() ? 0.3F : 1.0F;
   }

   @Override
   protected ayj b(brx var1) {
      return new bax.g(this, _snowman);
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      return aqe.aN.a(_snowman);
   }

   @Override
   public boolean k(bmb var1) {
      return _snowman.b() == bup.aU.h();
   }

   @Override
   public float a(fx var1, brz var2) {
      if (!this.eU() && _snowman.b(_snowman).a(aef.b)) {
         return 10.0F;
      } else {
         return cbf.a(_snowman, _snowman) ? 10.0F : _snowman.y(_snowman) - 0.5F;
      }
   }

   @Override
   public void k() {
      super.k();
      if (this.aX() && this.eL() && this.bv >= 1 && this.bv % 5 == 0) {
         fx _snowman = this.cB();
         if (cbf.a(this.l, _snowman)) {
            this.l.c(2001, _snowman, buo.i(bup.C.n()));
         }
      }
   }

   @Override
   protected void m() {
      super.m();
      if (!this.w_() && this.l.V().b(brt.e)) {
         this.a(bmd.jZ, 1);
      }
   }

   @Override
   public void g(dcn var1) {
      if (this.dS() && this.aE()) {
         this.a(0.1F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.9));
         if (this.A() == null && (!this.eU() || !this.eN().a(this.cA(), 20.0))) {
            this.f(this.cC().b(0.0, -0.005, 0.0));
         }
      } else {
         super.g(_snowman);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return false;
   }

   @Override
   public void a(aag var1, aql var2) {
      this.a(apk.b, Float.MAX_VALUE);
   }

   static class a extends avi {
      private final bax d;

      a(bax var1, double var2) {
         super(_snowman, _snowman);
         this.d = _snowman;
      }

      @Override
      public boolean a() {
         return super.a() && !this.d.eK();
      }

      @Override
      protected void g() {
         aah _snowman = this.a.eR();
         if (_snowman == null && this.c.eR() != null) {
            _snowman = this.c.eR();
         }

         if (_snowman != null) {
            _snowman.a(aea.O);
            ac.o.a(_snowman, this.a, this.c, null);
         }

         this.d.t(true);
         this.a.eT();
         this.c.eT();
         Random _snowmanx = this.a.cY();
         if (this.b.V().b(brt.e)) {
            this.b.c(new aqg(this.b, this.a.cD(), this.a.cE(), this.a.cH(), _snowmanx.nextInt(7) + 1));
         }
      }
   }

   static class b extends avv {
      private final bax a;
      private final double b;
      private boolean c;
      private int d;

      b(bax var1, double var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         if (this.a.w_()) {
            return false;
         } else if (this.a.eK()) {
            return true;
         } else {
            return this.a.cY().nextInt(700) != 0 ? false : !this.a.eN().a(this.a.cA(), 64.0);
         }
      }

      @Override
      public void c() {
         this.a.v(true);
         this.c = false;
         this.d = 0;
      }

      @Override
      public void d() {
         this.a.v(false);
      }

      @Override
      public boolean b() {
         return !this.a.eN().a(this.a.cA(), 7.0) && !this.c && this.d <= 600;
      }

      @Override
      public void e() {
         fx _snowman = this.a.eN();
         boolean _snowmanx = _snowman.a(this.a.cA(), 16.0);
         if (_snowmanx) {
            this.d++;
         }

         if (this.a.x().m()) {
            dcn _snowmanxx = dcn.c(_snowman);
            dcn _snowmanxxx = azj.a(this.a, 16, 3, _snowmanxx, (float) (Math.PI / 10));
            if (_snowmanxxx == null) {
               _snowmanxxx = azj.b(this.a, 8, 7, _snowmanxx);
            }

            if (_snowmanxxx != null && !_snowmanx && !this.a.l.d_(new fx(_snowmanxxx)).a(bup.A)) {
               _snowmanxxx = azj.b(this.a, 16, 5, _snowmanxx);
            }

            if (_snowmanxxx == null) {
               this.c = true;
               return;
            }

            this.a.x().a(_snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d, this.b);
         }
      }
   }

   static class c extends awj {
      private final bax g;

      private c(bax var1, double var2) {
         super(_snowman, _snowman.w_() ? 2.0 : _snowman, 24);
         this.g = _snowman;
         this.f = -1;
      }

      @Override
      public boolean b() {
         return !this.g.aE() && this.d <= 1200 && this.a(this.g.l, this.e);
      }

      @Override
      public boolean a() {
         if (this.g.w_() && !this.g.aE()) {
            return super.a();
         } else {
            return !this.g.eU() && !this.g.aE() && !this.g.eK() ? super.a() : false;
         }
      }

      @Override
      public boolean k() {
         return this.d % 160 == 0;
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         return _snowman.d_(_snowman).a(bup.A);
      }
   }

   static class d extends awj {
      private final bax g;

      d(bax var1, double var2) {
         super(_snowman, _snowman, 16);
         this.g = _snowman;
      }

      @Override
      public boolean a() {
         return this.g.eK() && this.g.eN().a(this.g.cA(), 9.0) ? super.a() : false;
      }

      @Override
      public boolean b() {
         return super.b() && this.g.eK() && this.g.eN().a(this.g.cA(), 9.0);
      }

      @Override
      public void e() {
         super.e();
         fx _snowman = this.g.cB();
         if (!this.g.aE() && this.l()) {
            if (this.g.bv < 1) {
               this.g.u(true);
            } else if (this.g.bv > 200) {
               brx _snowmanx = this.g.l;
               _snowmanx.a(null, _snowman, adq.pB, adr.e, 0.3F, 0.9F + _snowmanx.t.nextFloat() * 0.2F);
               _snowmanx.a(this.e.b(), bup.kf.n().a(cbf.b, Integer.valueOf(this.g.J.nextInt(4) + 1)), 3);
               this.g.t(false);
               this.g.u(false);
               this.g.s(600);
            }

            if (this.g.eL()) {
               this.g.bv++;
            }
         }
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         return !_snowman.w(_snowman.b()) ? false : cbf.b(_snowman, _snowman);
      }
   }

   static class e extends avb {
      private final bax i;

      e(bax var1) {
         super(_snowman);
         this.i = _snowman;
      }

      private void g() {
         if (this.i.aE()) {
            this.i.f(this.i.cC().b(0.0, 0.005, 0.0));
            if (!this.i.eN().a(this.i.cA(), 16.0)) {
               this.i.q(Math.max(this.i.dN() / 2.0F, 0.08F));
            }

            if (this.i.w_()) {
               this.i.q(Math.max(this.i.dN() / 3.0F, 0.06F));
            }
         } else if (this.i.t) {
            this.i.q(Math.max(this.i.dN() / 2.0F, 0.06F));
         }
      }

      @Override
      public void a() {
         this.g();
         if (this.h == avb.a.b && !this.i.x().m()) {
            double _snowman = this.b - this.i.cD();
            double _snowmanx = this.c - this.i.cE();
            double _snowmanxx = this.d - this.i.cH();
            double _snowmanxxx = (double)afm.a(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
            _snowmanx /= _snowmanxxx;
            float _snowmanxxxx = (float)(afm.d(_snowmanxx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
            this.i.p = this.a(this.i.p, _snowmanxxxx, 90.0F);
            this.i.aA = this.i.p;
            float _snowmanxxxxx = (float)(this.e * this.i.b(arl.d));
            this.i.q(afm.g(0.125F, this.i.dN(), _snowmanxxxxx));
            this.i.f(this.i.cC().b(0.0, (double)this.i.dN() * _snowmanx * 0.1, 0.0));
         } else {
            this.i.q(0.0F);
         }
      }
   }

   static class f extends awp {
      f(bax var1, double var2) {
         super(_snowman, _snowman);
      }

      @Override
      public boolean a() {
         if (this.a.cZ() == null && !this.a.bq()) {
            return false;
         } else {
            fx _snowman = this.a(this.a.l, this.a, 7, 4);
            if (_snowman != null) {
               this.c = (double)_snowman.u();
               this.d = (double)_snowman.v();
               this.e = (double)_snowman.w();
               return true;
            } else {
               return this.g();
            }
         }
      }
   }

   static class g extends ayl {
      g(bax var1, brx var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean a() {
         return true;
      }

      @Override
      protected cxf a(int var1) {
         this.o = new cxi();
         return new cxf(this.o, _snowman);
      }

      @Override
      public boolean a(fx var1) {
         if (this.a instanceof bax) {
            bax _snowman = (bax)this.a;
            if (_snowman.eV()) {
               return this.b.d_(_snowman).a(bup.A);
            }
         }

         return !this.b.d_(_snowman.c()).g();
      }
   }

   static class h extends awt {
      private final bax h;

      private h(bax var1, double var2, int var4) {
         super(_snowman, _snowman, _snowman);
         this.h = _snowman;
      }

      @Override
      public boolean a() {
         return !this.a.aE() && !this.h.eU() && !this.h.eK() ? super.a() : false;
      }
   }

   static class i extends avv {
      private static final azg a = new azg().a(10.0).b().a();
      private final bax b;
      private final double c;
      private bfw d;
      private int e;
      private final Set<blx> f;

      i(bax var1, double var2, blx var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.f = Sets.newHashSet(new blx[]{_snowman});
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         if (this.e > 0) {
            this.e--;
            return false;
         } else {
            this.d = this.b.l.a(a, this.b);
            return this.d == null ? false : this.a(this.d.dD()) || this.a(this.d.dE());
         }
      }

      private boolean a(bmb var1) {
         return this.f.contains(_snowman.b());
      }

      @Override
      public boolean b() {
         return this.a();
      }

      @Override
      public void d() {
         this.d = null;
         this.b.x().o();
         this.e = 100;
      }

      @Override
      public void e() {
         this.b.t().a(this.d, (float)(this.b.Q() + 20), (float)this.b.O());
         if (this.b.h(this.d) < 6.25) {
            this.b.x().o();
         } else {
            this.b.x().a(this.d, this.c);
         }
      }
   }

   static class j extends avv {
      private final bax a;
      private final double b;
      private boolean c;

      j(bax var1, double var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return !this.a.eU() && !this.a.eK() && this.a.aE();
      }

      @Override
      public void c() {
         int _snowman = 512;
         int _snowmanx = 4;
         Random _snowmanxx = this.a.J;
         int _snowmanxxx = _snowmanxx.nextInt(1025) - 512;
         int _snowmanxxxx = _snowmanxx.nextInt(9) - 4;
         int _snowmanxxxxx = _snowmanxx.nextInt(1025) - 512;
         if ((double)_snowmanxxxx + this.a.cE() > (double)(this.a.l.t_() - 1)) {
            _snowmanxxxx = 0;
         }

         fx _snowmanxxxxxx = new fx((double)_snowmanxxx + this.a.cD(), (double)_snowmanxxxx + this.a.cE(), (double)_snowmanxxxxx + this.a.cH());
         this.a.h(_snowmanxxxxxx);
         this.a.w(true);
         this.c = false;
      }

      @Override
      public void e() {
         if (this.a.x().m()) {
            dcn _snowman = dcn.c(this.a.eO());
            dcn _snowmanx = azj.a(this.a, 16, 3, _snowman, (float) (Math.PI / 10));
            if (_snowmanx == null) {
               _snowmanx = azj.b(this.a, 8, 7, _snowman);
            }

            if (_snowmanx != null) {
               int _snowmanxx = afm.c(_snowmanx.b);
               int _snowmanxxx = afm.c(_snowmanx.d);
               int _snowmanxxxx = 34;
               if (!this.a.l.a(_snowmanxx - 34, 0, _snowmanxxx - 34, _snowmanxx + 34, 0, _snowmanxxx + 34)) {
                  _snowmanx = null;
               }
            }

            if (_snowmanx == null) {
               this.c = true;
               return;
            }

            this.a.x().a(_snowmanx.b, _snowmanx.c, _snowmanx.d, this.b);
         }
      }

      @Override
      public boolean b() {
         return !this.a.x().m() && !this.c && !this.a.eU() && !this.a.eS() && !this.a.eK();
      }

      @Override
      public void d() {
         this.a.w(false);
         super.d();
      }
   }
}
