import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class baa extends azz implements aqs, bag {
   private static final us<Byte> bo = uv.a(baa.class, uu.a);
   private static final us<Integer> bp = uv.a(baa.class, uu.b);
   private static final afh bq = afu.a(20, 39);
   private UUID br;
   private float bs;
   private float bt;
   private int bu;
   private int bv;
   private int bw;
   private int bx;
   private int by = 0;
   private int bz = 0;
   @Nullable
   private fx bA = null;
   @Nullable
   private fx bB = null;
   private baa.k bC;
   private baa.e bD;
   private baa.f bE;
   private int bF;

   public baa(aqe<? extends baa> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new auy(this, 20, true);
      this.g = new baa.j(this);
      this.a(cwz.l, -1.0F);
      this.a(cwz.h, -1.0F);
      this.a(cwz.i, 16.0F);
      this.a(cwz.x, -1.0F);
      this.a(cwz.f, -1.0F);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bo, (byte)0);
      this.R.a(bp, 0);
   }

   @Override
   public float a(fx var1, brz var2) {
      return _snowman.d_(_snowman).g() ? 10.0F : 0.0F;
   }

   @Override
   protected void o() {
      this.bk.a(0, new baa.b(this, 1.4F, true));
      this.bk.a(1, new baa.d());
      this.bk.a(2, new avi(this, 1.0));
      this.bk.a(3, new axf(this, 1.25, bon.a(aeg.M), false));
      this.bC = new baa.k();
      this.bk.a(4, this.bC);
      this.bk.a(5, new avu(this, 1.25));
      this.bk.a(5, new baa.i());
      this.bD = new baa.e();
      this.bk.a(5, this.bD);
      this.bE = new baa.f();
      this.bk.a(6, this.bE);
      this.bk.a(7, new baa.g());
      this.bk.a(8, new baa.l());
      this.bk.a(9, new avp(this));
      this.bl.a(1, new baa.h(this).a(new Class[0]));
      this.bl.a(2, new baa.c(this));
      this.bl.a(3, new axw<>(this, true));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.eU()) {
         _snowman.a("HivePos", mp.a(this.eV()));
      }

      if (this.eL()) {
         _snowman.a("FlowerPos", mp.a(this.eK()));
      }

      _snowman.a("HasNectar", this.eX());
      _snowman.a("HasStung", this.eY());
      _snowman.b("TicksSincePollination", this.bv);
      _snowman.b("CannotEnterHiveTicks", this.bw);
      _snowman.b("CropsGrownSincePollination", this.bx);
      this.c(_snowman);
   }

   @Override
   public void a(md var1) {
      this.bB = null;
      if (_snowman.e("HivePos")) {
         this.bB = mp.b(_snowman.p("HivePos"));
      }

      this.bA = null;
      if (_snowman.e("FlowerPos")) {
         this.bA = mp.b(_snowman.p("FlowerPos"));
      }

      super.a(_snowman);
      this.t(_snowman.q("HasNectar"));
      this.u(_snowman.q("HasStung"));
      this.bv = _snowman.h("TicksSincePollination");
      this.bw = _snowman.h("CannotEnterHiveTicks");
      this.bx = _snowman.h("CropsGrownSincePollination");
      this.a((aag)this.l, _snowman);
   }

   @Override
   public boolean B(aqa var1) {
      boolean _snowman = _snowman.a(apk.b(this), (float)((int)this.b(arl.f)));
      if (_snowman) {
         this.a(this, _snowman);
         if (_snowman instanceof aqm) {
            ((aqm)_snowman).q(((aqm)_snowman).dz() + 1);
            int _snowmanx = 0;
            if (this.l.ad() == aor.c) {
               _snowmanx = 10;
            } else if (this.l.ad() == aor.d) {
               _snowmanx = 18;
            }

            if (_snowmanx > 0) {
               ((aqm)_snowman).c(new apu(apw.s, _snowmanx * 20, 0));
            }
         }

         this.u(true);
         this.J_();
         this.a(adq.aC, 1.0F, 1.0F);
      }

      return _snowman;
   }

   @Override
   public void j() {
      super.j();
      if (this.eX() && this.fg() < 10 && this.J.nextFloat() < 0.05F) {
         for (int _snowman = 0; _snowman < this.J.nextInt(2) + 1; _snowman++) {
            this.a(this.l, this.cD() - 0.3F, this.cD() + 0.3F, this.cH() - 0.3F, this.cH() + 0.3F, this.e(0.5), hh.al);
         }
      }

      this.fe();
   }

   private void a(brx var1, double var2, double var4, double var6, double var8, double var10, hf var12) {
      _snowman.a(_snowman, afm.d(_snowman.t.nextDouble(), _snowman, _snowman), _snowman, afm.d(_snowman.t.nextDouble(), _snowman, _snowman), 0.0, 0.0, 0.0);
   }

   private void h(fx var1) {
      dcn _snowman = dcn.c(_snowman);
      int _snowmanx = 0;
      fx _snowmanxx = this.cB();
      int _snowmanxxx = (int)_snowman.c - _snowmanxx.v();
      if (_snowmanxxx > 2) {
         _snowmanx = 4;
      } else if (_snowmanxxx < -2) {
         _snowmanx = -4;
      }

      int _snowmanxxxx = 6;
      int _snowmanxxxxx = 8;
      int _snowmanxxxxxx = _snowmanxx.k(_snowman);
      if (_snowmanxxxxxx < 15) {
         _snowmanxxxx = _snowmanxxxxxx / 2;
         _snowmanxxxxx = _snowmanxxxxxx / 2;
      }

      dcn _snowmanxxxxxxx = azj.b(this, _snowmanxxxx, _snowmanxxxxx, _snowmanx, _snowman, (float) (Math.PI / 10));
      if (_snowmanxxxxxxx != null) {
         this.bj.a(0.5F);
         this.bj.a(_snowmanxxxxxxx.b, _snowmanxxxxxxx.c, _snowmanxxxxxxx.d, 1.0);
      }
   }

   @Nullable
   public fx eK() {
      return this.bA;
   }

   public boolean eL() {
      return this.bA != null;
   }

   public void g(fx var1) {
      this.bA = _snowman;
   }

   private boolean fc() {
      return this.bv > 3600;
   }

   private boolean fd() {
      if (this.bw <= 0 && !this.bC.k() && !this.eY() && this.A() == null) {
         boolean _snowman = this.fc() || this.l.X() || this.l.N() || this.eX();
         return _snowman && !this.ff();
      } else {
         return false;
      }
   }

   public void t(int var1) {
      this.bw = _snowman;
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bt, this.bs);
   }

   private void fe() {
      this.bt = this.bs;
      if (this.fk()) {
         this.bs = Math.min(1.0F, this.bs + 0.2F);
      } else {
         this.bs = Math.max(0.0F, this.bs - 0.24F);
      }
   }

   @Override
   protected void N() {
      boolean _snowman = this.eY();
      if (this.aH()) {
         this.bF++;
      } else {
         this.bF = 0;
      }

      if (this.bF > 20) {
         this.a(apk.h, 1.0F);
      }

      if (_snowman) {
         this.bu++;
         if (this.bu % 5 == 0 && this.J.nextInt(afm.a(1200 - this.bu, 1, 1200)) == 0) {
            this.a(apk.n, this.dk());
         }
      }

      if (!this.eX()) {
         this.bv++;
      }

      if (!this.l.v) {
         this.a((aag)this.l, false);
      }
   }

   public void eO() {
      this.bv = 0;
   }

   private boolean ff() {
      if (this.bB == null) {
         return false;
      } else {
         ccj _snowman = this.l.c(this.bB);
         return _snowman instanceof ccg && ((ccg)_snowman).d();
      }
   }

   @Override
   public int E_() {
      return this.R.a(bp);
   }

   @Override
   public void a_(int var1) {
      this.R.b(bp, _snowman);
   }

   @Override
   public UUID F_() {
      return this.br;
   }

   @Override
   public void a(@Nullable UUID var1) {
      this.br = _snowman;
   }

   @Override
   public void G_() {
      this.a_(bq.a(this.J));
   }

   private boolean i(fx var1) {
      ccj _snowman = this.l.c(_snowman);
      return _snowman instanceof ccg ? !((ccg)_snowman).h() : false;
   }

   public boolean eU() {
      return this.bB != null;
   }

   @Nullable
   public fx eV() {
      return this.bB;
   }

   @Override
   protected void M() {
      super.M();
      rz.a(this);
   }

   private int fg() {
      return this.bx;
   }

   private void fh() {
      this.bx = 0;
   }

   private void fi() {
      this.bx++;
   }

   @Override
   public void k() {
      super.k();
      if (!this.l.v) {
         if (this.bw > 0) {
            this.bw--;
         }

         if (this.by > 0) {
            this.by--;
         }

         if (this.bz > 0) {
            this.bz--;
         }

         boolean _snowman = this.H_() && !this.eY() && this.A() != null && this.A().h(this) < 4.0;
         this.v(_snowman);
         if (this.K % 20 == 0 && !this.fj()) {
            this.bB = null;
         }
      }
   }

   private boolean fj() {
      if (!this.eU()) {
         return false;
      } else {
         ccj _snowman = this.l.c(this.bB);
         return _snowman != null && _snowman.u() == cck.G;
      }
   }

   public boolean eX() {
      return this.u(8);
   }

   private void t(boolean var1) {
      if (_snowman) {
         this.eO();
      }

      this.d(8, _snowman);
   }

   public boolean eY() {
      return this.u(4);
   }

   private void u(boolean var1) {
      this.d(4, _snowman);
   }

   private boolean fk() {
      return this.u(2);
   }

   private void v(boolean var1) {
      this.d(2, _snowman);
   }

   private boolean j(fx var1) {
      return !this.b(_snowman, 32);
   }

   private void d(int var1, boolean var2) {
      if (_snowman) {
         this.R.b(bo, (byte)(this.R.a(bo) | _snowman));
      } else {
         this.R.b(bo, (byte)(this.R.a(bo) & ~_snowman));
      }
   }

   private boolean u(int var1) {
      return (this.R.a(bo) & _snowman) != 0;
   }

   public static ark.a eZ() {
      return aqn.p().a(arl.a, 10.0).a(arl.e, 0.6F).a(arl.d, 0.3F).a(arl.f, 2.0).a(arl.b, 48.0);
   }

   @Override
   protected ayj b(brx var1) {
      ayh _snowman = new ayh(this, _snowman) {
         @Override
         public boolean a(fx var1) {
            return !this.b.d_(_snowman.c()).g();
         }

         @Override
         public void c() {
            if (!baa.this.bC.k()) {
               super.c();
            }
         }
      };
      _snowman.a(false);
      _snowman.d(false);
      _snowman.b(true);
      return _snowman;
   }

   @Override
   public boolean k(bmb var1) {
      return _snowman.b().a(aeg.M);
   }

   private boolean k(fx var1) {
      return this.l.p(_snowman) && this.l.d_(_snowman).b().a(aed.O);
   }

   @Override
   protected void b(fx var1, ceh var2) {
   }

   @Override
   protected adp I() {
      return null;
   }

   @Override
   protected adp e(apk var1) {
      return adq.az;
   }

   @Override
   protected adp dq() {
      return adq.ay;
   }

   @Override
   protected float dG() {
      return 0.4F;
   }

   public baa b(aag var1, apy var2) {
      return aqe.e.a(_snowman);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? _snowman.b * 0.5F : _snowman.b * 0.5F;
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
   }

   @Override
   protected boolean az() {
      return true;
   }

   public void fb() {
      this.t(false);
      this.fh();
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         aqa _snowman = _snowman.k();
         if (!this.l.v) {
            this.bC.l();
         }

         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public aqq dC() {
      return aqq.c;
   }

   @Override
   protected void c(ael<cuw> var1) {
      this.f(this.cC().b(0.0, 0.01, 0.0));
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.5F * this.ce()), (double)(this.cy() * 0.2F));
   }

   private boolean b(fx var1, int var2) {
      return _snowman.a(this.cB(), (double)_snowman);
   }

   abstract class a extends avv {
      private a() {
      }

      public abstract boolean g();

      public abstract boolean h();

      @Override
      public boolean a() {
         return this.g() && !baa.this.H_();
      }

      @Override
      public boolean b() {
         return this.h() && !baa.this.H_();
      }
   }

   class b extends awf {
      b(aqu var2, double var3, boolean var5) {
         super(_snowman, _snowman, _snowman);
      }

      @Override
      public boolean a() {
         return super.a() && baa.this.H_() && !baa.this.eY();
      }

      @Override
      public boolean b() {
         return super.b() && baa.this.H_() && !baa.this.eY();
      }
   }

   static class c extends axq<bfw> {
      c(baa var1) {
         super(_snowman, bfw.class, 10, true, false, _snowman::a_);
      }

      @Override
      public boolean a() {
         return this.h() && super.a();
      }

      @Override
      public boolean b() {
         boolean _snowman = this.h();
         if (_snowman && this.e.A() != null) {
            return super.b();
         } else {
            this.g = null;
            return false;
         }
      }

      private boolean h() {
         baa _snowman = (baa)this.e;
         return _snowman.H_() && !_snowman.eY();
      }
   }

   class d extends baa.a {
      private d() {
      }

      @Override
      public boolean g() {
         if (baa.this.eU() && baa.this.fd() && baa.this.bB.a(baa.this.cA(), 2.0)) {
            ccj _snowman = baa.this.l.c(baa.this.bB);
            if (_snowman instanceof ccg) {
               ccg _snowmanx = (ccg)_snowman;
               if (!_snowmanx.h()) {
                  return true;
               }

               baa.this.bB = null;
            }
         }

         return false;
      }

      @Override
      public boolean h() {
         return false;
      }

      @Override
      public void c() {
         ccj _snowman = baa.this.l.c(baa.this.bB);
         if (_snowman instanceof ccg) {
            ccg _snowmanx = (ccg)_snowman;
            _snowmanx.a(baa.this, baa.this.eX());
         }
      }
   }

   public class e extends baa.a {
      private int c = baa.this.l.t.nextInt(10);
      private List<fx> d = Lists.newArrayList();
      @Nullable
      private cxd e = null;
      private int f;

      e() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean g() {
         return baa.this.bB != null && !baa.this.ez() && baa.this.fd() && !this.d(baa.this.bB) && baa.this.l.d_(baa.this.bB).a(aed.aj);
      }

      @Override
      public boolean h() {
         return this.g();
      }

      @Override
      public void c() {
         this.c = 0;
         this.f = 0;
         super.c();
      }

      @Override
      public void d() {
         this.c = 0;
         this.f = 0;
         baa.this.bj.o();
         baa.this.bj.g();
      }

      @Override
      public void e() {
         if (baa.this.bB != null) {
            this.c++;
            if (this.c > 600) {
               this.k();
            } else if (!baa.this.bj.n()) {
               if (!baa.this.b(baa.this.bB, 16)) {
                  if (baa.this.j(baa.this.bB)) {
                     this.l();
                  } else {
                     baa.this.h(baa.this.bB);
                  }
               } else {
                  boolean _snowman = this.a(baa.this.bB);
                  if (!_snowman) {
                     this.k();
                  } else if (this.e != null && baa.this.bj.k().a(this.e)) {
                     this.f++;
                     if (this.f > 60) {
                        this.l();
                        this.f = 0;
                     }
                  } else {
                     this.e = baa.this.bj.k();
                  }
               }
            }
         }
      }

      private boolean a(fx var1) {
         baa.this.bj.a(10.0F);
         baa.this.bj.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), 1.0);
         return baa.this.bj.k() != null && baa.this.bj.k().j();
      }

      private boolean b(fx var1) {
         return this.d.contains(_snowman);
      }

      private void c(fx var1) {
         this.d.add(_snowman);

         while (this.d.size() > 3) {
            this.d.remove(0);
         }
      }

      private void j() {
         this.d.clear();
      }

      private void k() {
         if (baa.this.bB != null) {
            this.c(baa.this.bB);
         }

         this.l();
      }

      private void l() {
         baa.this.bB = null;
         baa.this.by = 200;
      }

      private boolean d(fx var1) {
         if (baa.this.b(_snowman, 2)) {
            return true;
         } else {
            cxd _snowman = baa.this.bj.k();
            return _snowman != null && _snowman.m().equals(_snowman) && _snowman.j() && _snowman.c();
         }
      }
   }

   public class f extends baa.a {
      private int c = baa.this.l.t.nextInt(10);

      f() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean g() {
         return baa.this.bA != null && !baa.this.ez() && this.j() && baa.this.k(baa.this.bA) && !baa.this.b(baa.this.bA, 2);
      }

      @Override
      public boolean h() {
         return this.g();
      }

      @Override
      public void c() {
         this.c = 0;
         super.c();
      }

      @Override
      public void d() {
         this.c = 0;
         baa.this.bj.o();
         baa.this.bj.g();
      }

      @Override
      public void e() {
         if (baa.this.bA != null) {
            this.c++;
            if (this.c > 600) {
               baa.this.bA = null;
            } else if (!baa.this.bj.n()) {
               if (baa.this.j(baa.this.bA)) {
                  baa.this.bA = null;
               } else {
                  baa.this.h(baa.this.bA);
               }
            }
         }
      }

      private boolean j() {
         return baa.this.bv > 2400;
      }
   }

   class g extends baa.a {
      private g() {
      }

      @Override
      public boolean g() {
         if (baa.this.fg() >= 10) {
            return false;
         } else {
            return baa.this.J.nextFloat() < 0.3F ? false : baa.this.eX() && baa.this.fj();
         }
      }

      @Override
      public boolean h() {
         return this.g();
      }

      @Override
      public void e() {
         if (baa.this.J.nextInt(30) == 0) {
            for (int _snowman = 1; _snowman <= 2; _snowman++) {
               fx _snowmanx = baa.this.cB().c(_snowman);
               ceh _snowmanxx = baa.this.l.d_(_snowmanx);
               buo _snowmanxxx = _snowmanxx.b();
               boolean _snowmanxxxx = false;
               cfg _snowmanxxxxx = null;
               if (_snowmanxxx.a(aed.al)) {
                  if (_snowmanxxx instanceof bvs) {
                     bvs _snowmanxxxxxx = (bvs)_snowmanxxx;
                     if (!_snowmanxxxxxx.h(_snowmanxx)) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = _snowmanxxxxxx.c();
                     }
                  } else if (_snowmanxxx instanceof cam) {
                     int _snowmanxxxxxx = _snowmanxx.c(cam.a);
                     if (_snowmanxxxxxx < 7) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = cam.a;
                     }
                  } else if (_snowmanxxx == bup.mg) {
                     int _snowmanxxxxxx = _snowmanxx.c(cau.a);
                     if (_snowmanxxxxxx < 3) {
                        _snowmanxxxx = true;
                        _snowmanxxxxx = cau.a;
                     }
                  }

                  if (_snowmanxxxx) {
                     baa.this.l.c(2005, _snowmanx, 0);
                     baa.this.l.a(_snowmanx, _snowmanxx.a(_snowmanxxxxx, Integer.valueOf(_snowmanxx.c(_snowmanxxxxx) + 1)));
                     baa.this.fi();
                  }
               }
            }
         }
      }
   }

   class h extends axp {
      h(baa var2) {
         super(_snowman);
      }

      @Override
      public boolean b() {
         return baa.this.H_() && super.b();
      }

      @Override
      protected void a(aqn var1, aqm var2) {
         if (_snowman instanceof baa && this.e.D(_snowman)) {
            _snowman.h(_snowman);
         }
      }
   }

   class i extends baa.a {
      private i() {
      }

      @Override
      public boolean g() {
         return baa.this.by == 0 && !baa.this.eU() && baa.this.fd();
      }

      @Override
      public boolean h() {
         return false;
      }

      @Override
      public void c() {
         baa.this.by = 200;
         List<fx> _snowman = this.j();
         if (!_snowman.isEmpty()) {
            for (fx _snowmanx : _snowman) {
               if (!baa.this.bD.b(_snowmanx)) {
                  baa.this.bB = _snowmanx;
                  return;
               }
            }

            baa.this.bD.j();
            baa.this.bB = _snowman.get(0);
         }
      }

      private List<fx> j() {
         fx _snowman = baa.this.cB();
         azo _snowmanx = ((aag)baa.this.l).y();
         Stream<azp> _snowmanxx = _snowmanx.c(var0 -> var0 == azr.t || var0 == azr.u, _snowman, 20, azo.b.c);
         return _snowmanxx.map(azp::f).filter(var1x -> baa.this.i(var1x)).sorted(Comparator.comparingDouble(var1x -> var1x.j(_snowman))).collect(Collectors.toList());
      }
   }

   class j extends ava {
      j(aqn var2) {
         super(_snowman);
      }

      @Override
      public void a() {
         if (!baa.this.H_()) {
            super.a();
         }
      }

      @Override
      protected boolean b() {
         return !baa.this.bC.k();
      }
   }

   class k extends baa.a {
      private final Predicate<ceh> c = var0 -> {
         if (var0.a(aed.N)) {
            return var0.a(bup.gU) ? var0.c(bwd.a) == cfd.a : true;
         } else {
            return var0.a(aed.K);
         }
      };
      private int d = 0;
      private int e = 0;
      private boolean f;
      private dcn g;
      private int h = 0;

      k() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean g() {
         if (baa.this.bz > 0) {
            return false;
         } else if (baa.this.eX()) {
            return false;
         } else if (baa.this.l.X()) {
            return false;
         } else if (baa.this.J.nextFloat() < 0.7F) {
            return false;
         } else {
            Optional<fx> _snowman = this.o();
            if (_snowman.isPresent()) {
               baa.this.bA = _snowman.get();
               baa.this.bj.a((double)baa.this.bA.u() + 0.5, (double)baa.this.bA.v() + 0.5, (double)baa.this.bA.w() + 0.5, 1.2F);
               return true;
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean h() {
         if (!this.f) {
            return false;
         } else if (!baa.this.eL()) {
            return false;
         } else if (baa.this.l.X()) {
            return false;
         } else if (this.j()) {
            return baa.this.J.nextFloat() < 0.2F;
         } else if (baa.this.K % 20 == 0 && !baa.this.k(baa.this.bA)) {
            baa.this.bA = null;
            return false;
         } else {
            return true;
         }
      }

      private boolean j() {
         return this.d > 400;
      }

      private boolean k() {
         return this.f;
      }

      private void l() {
         this.f = false;
      }

      @Override
      public void c() {
         this.d = 0;
         this.h = 0;
         this.e = 0;
         this.f = true;
         baa.this.eO();
      }

      @Override
      public void d() {
         if (this.j()) {
            baa.this.t(true);
         }

         this.f = false;
         baa.this.bj.o();
         baa.this.bz = 200;
      }

      @Override
      public void e() {
         this.h++;
         if (this.h > 600) {
            baa.this.bA = null;
         } else {
            dcn _snowman = dcn.c(baa.this.bA).b(0.0, 0.6F, 0.0);
            if (_snowman.f(baa.this.cA()) > 1.0) {
               this.g = _snowman;
               this.m();
            } else {
               if (this.g == null) {
                  this.g = _snowman;
               }

               boolean _snowmanx = baa.this.cA().f(this.g) <= 0.1;
               boolean _snowmanxx = true;
               if (!_snowmanx && this.h > 600) {
                  baa.this.bA = null;
               } else {
                  if (_snowmanx) {
                     boolean _snowmanxxx = baa.this.J.nextInt(25) == 0;
                     if (_snowmanxxx) {
                        this.g = new dcn(_snowman.a() + (double)this.n(), _snowman.b(), _snowman.c() + (double)this.n());
                        baa.this.bj.o();
                     } else {
                        _snowmanxx = false;
                     }

                     baa.this.t().a(_snowman.a(), _snowman.b(), _snowman.c());
                  }

                  if (_snowmanxx) {
                     this.m();
                  }

                  this.d++;
                  if (baa.this.J.nextFloat() < 0.05F && this.d > this.e + 60) {
                     this.e = this.d;
                     baa.this.a(adq.aD, 1.0F, 1.0F);
                  }
               }
            }
         }
      }

      private void m() {
         baa.this.u().a(this.g.a(), this.g.b(), this.g.c(), 0.35F);
      }

      private float n() {
         return (baa.this.J.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
      }

      private Optional<fx> o() {
         return this.a(this.c, 5.0);
      }

      private Optional<fx> a(Predicate<ceh> var1, double var2) {
         fx _snowman = baa.this.cB();
         fx.a _snowmanx = new fx.a();

         for (int _snowmanxx = 0; (double)_snowmanxx <= _snowman; _snowmanxx = _snowmanxx > 0 ? -_snowmanxx : 1 - _snowmanxx) {
            for (int _snowmanxxx = 0; (double)_snowmanxxx < _snowman; _snowmanxxx++) {
               for (int _snowmanxxxx = 0; _snowmanxxxx <= _snowmanxxx; _snowmanxxxx = _snowmanxxxx > 0 ? -_snowmanxxxx : 1 - _snowmanxxxx) {
                  for (int _snowmanxxxxx = _snowmanxxxx < _snowmanxxx && _snowmanxxxx > -_snowmanxxx ? _snowmanxxx : 0; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx = _snowmanxxxxx > 0 ? -_snowmanxxxxx : 1 - _snowmanxxxxx) {
                     _snowmanx.a(_snowman, _snowmanxxxx, _snowmanxx - 1, _snowmanxxxxx);
                     if (_snowman.a(_snowmanx, _snowman) && _snowman.test(baa.this.l.d_(_snowmanx))) {
                        return Optional.of(_snowmanx);
                     }
                  }
               }
            }
         }

         return Optional.empty();
      }
   }

   class l extends avv {
      l() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         return baa.this.bj.m() && baa.this.J.nextInt(10) == 0;
      }

      @Override
      public boolean b() {
         return baa.this.bj.n();
      }

      @Override
      public void c() {
         dcn _snowman = this.g();
         if (_snowman != null) {
            baa.this.bj.a(baa.this.bj.a(new fx(_snowman), 1), 1.0);
         }
      }

      @Nullable
      private dcn g() {
         dcn _snowman;
         if (baa.this.fj() && !baa.this.b(baa.this.bB, 22)) {
            dcn _snowmanx = dcn.a(baa.this.bB);
            _snowman = _snowmanx.d(baa.this.cA()).d();
         } else {
            _snowman = baa.this.f(0.0F);
         }

         int _snowmanx = 8;
         dcn _snowmanxx = azj.a(baa.this, 8, 7, _snowman, (float) (Math.PI / 2), 2, 1);
         return _snowmanxx != null ? _snowmanxx : azj.a(baa.this, 8, 4, -2, _snowman, (float) (Math.PI / 2));
      }
   }
}
