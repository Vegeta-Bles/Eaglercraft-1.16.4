import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bah extends azz {
   private static final us<Integer> bo = uv.a(bah.class, uu.b);
   private static final us<Byte> bp = uv.a(bah.class, uu.a);
   private static final us<Optional<UUID>> bq = uv.a(bah.class, uu.o);
   private static final us<Optional<UUID>> br = uv.a(bah.class, uu.o);
   private static final Predicate<bcv> bs = var0 -> !var0.p() && var0.aX();
   private static final Predicate<aqa> bt = var0 -> {
      if (!(var0 instanceof aqm)) {
         return false;
      } else {
         aqm _snowman = (aqm)var0;
         return _snowman.db() != null && _snowman.dc() < _snowman.K + 600;
      }
   };
   private static final Predicate<aqa> bu = var0 -> var0 instanceof bac || var0 instanceof baq;
   private static final Predicate<aqa> bv = var0 -> !var0.bx() && aqd.e.test(var0);
   private avv bw;
   private avv bx;
   private avv by;
   private float bz;
   private float bA;
   private float bB;
   private float bC;
   private int bD;

   public bah(aqe<? extends bah> var1, brx var2) {
      super(_snowman, _snowman);
      this.g = new bah.k();
      this.bh = new bah.m();
      this.a(cwz.p, 0.0F);
      this.a(cwz.q, 0.0F);
      this.p(true);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bq, Optional.empty());
      this.R.a(br, Optional.empty());
      this.R.a(bo, 0);
      this.R.a(bp, (byte)0);
   }

   @Override
   protected void o() {
      this.bw = new axq<>(this, azz.class, 10, false, false, var0 -> var0 instanceof bac || var0 instanceof baq);
      this.bx = new axq<>(this, bax.class, 10, false, false, bax.bo);
      this.by = new axq<>(this, azw.class, 20, false, false, var0 -> var0 instanceof azy);
      this.bk.a(0, new bah.g());
      this.bk.a(1, new bah.b());
      this.bk.a(2, new bah.n(2.2));
      this.bk.a(3, new bah.e(1.0));
      this.bk.a(4, new avd<>(this, bfw.class, 16.0F, 1.6, 1.4, var1 -> bv.test(var1) && !this.c(var1.bS()) && !this.fb()));
      this.bk.a(4, new avd<>(this, baz.class, 8.0F, 1.6, 1.4, var1 -> !((baz)var1).eK() && !this.fb()));
      this.bk.a(4, new avd<>(this, bao.class, 8.0F, 1.6, 1.4, var1 -> !this.fb()));
      this.bk.a(5, new bah.u());
      this.bk.a(6, new bah.o());
      this.bk.a(6, new bah.s(1.25));
      this.bk.a(7, new bah.l(1.2F, true));
      this.bk.a(7, new bah.t());
      this.bk.a(8, new bah.h(this, 1.25));
      this.bk.a(9, new bah.q(32, 200));
      this.bk.a(10, new bah.f(1.2F, 12, 2));
      this.bk.a(10, new awb(this, 0.4F));
      this.bk.a(11, new axk(this, 1.0));
      this.bk.a(11, new bah.p());
      this.bk.a(12, new bah.j(this, bfw.class, 24.0F));
      this.bk.a(13, new bah.r());
      this.bl.a(3, new bah.a(aqm.class, false, false, var1 -> bt.test(var1) && !this.c(var1.bS())));
   }

   @Override
   public adp d(bmb var1) {
      return adq.et;
   }

   @Override
   public void k() {
      if (!this.l.v && this.aX() && this.dS()) {
         this.bD++;
         bmb _snowman = this.b(aqf.a);
         if (this.l(_snowman)) {
            if (this.bD > 600) {
               bmb _snowmanx = _snowman.a(this.l, this);
               if (!_snowmanx.a()) {
                  this.a(aqf.a, _snowmanx);
               }

               this.bD = 0;
            } else if (this.bD > 560 && this.J.nextFloat() < 0.1F) {
               this.a(this.d(_snowman), 1.0F, 1.0F);
               this.l.a(this, (byte)45);
            }
         }

         aqm _snowmanx = this.A();
         if (_snowmanx == null || !_snowmanx.aX()) {
            this.v(false);
            this.w(false);
         }
      }

      if (this.em() || this.dI()) {
         this.aQ = false;
         this.aR = 0.0F;
         this.aT = 0.0F;
      }

      super.k();
      if (this.fb() && this.J.nextFloat() < 0.05F) {
         this.a(adq.ep, 1.0F, 1.0F);
      }
   }

   @Override
   protected boolean dI() {
      return this.dl();
   }

   private boolean l(bmb var1) {
      return _snowman.b().s() && this.A() == null && this.t && !this.em();
   }

   @Override
   protected void a(aos var1) {
      if (this.J.nextFloat() < 0.2F) {
         float _snowman = this.J.nextFloat();
         bmb _snowmanx;
         if (_snowman < 0.05F) {
            _snowmanx = new bmb(bmd.oV);
         } else if (_snowman < 0.2F) {
            _snowmanx = new bmb(bmd.mg);
         } else if (_snowman < 0.4F) {
            _snowmanx = this.J.nextBoolean() ? new bmb(bmd.pA) : new bmb(bmd.pB);
         } else if (_snowman < 0.6F) {
            _snowmanx = new bmb(bmd.kW);
         } else if (_snowman < 0.8F) {
            _snowmanx = new bmb(bmd.lS);
         } else {
            _snowmanx = new bmb(bmd.kT);
         }

         this.a(aqf.a, _snowmanx);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 45) {
         bmb _snowman = this.b(aqf.a);
         if (!_snowman.a()) {
            for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
               dcn _snowmanxx = new dcn(((double)this.J.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
                  .a(-this.q * (float) (Math.PI / 180.0))
                  .b(-this.p * (float) (Math.PI / 180.0));
               this.l.a(new he(hh.I, _snowman), this.cD() + this.bh().b / 2.0, this.cE(), this.cH() + this.bh().d / 2.0, _snowmanxx.b, _snowmanxx.c + 0.05, _snowmanxx.d);
            }
         }
      } else {
         super.a(_snowman);
      }
   }

   public static ark.a eK() {
      return aqn.p().a(arl.d, 0.3F).a(arl.a, 10.0).a(arl.b, 32.0).a(arl.f, 2.0);
   }

   public bah b(aag var1, apy var2) {
      bah _snowman = aqe.C.a(_snowman);
      _snowman.a(this.J.nextBoolean() ? this.eL() : ((bah)_snowman).eL());
      return _snowman;
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      Optional<vj<bsv>> _snowman = _snowman.i(this.cB());
      bah.v _snowmanx = bah.v.a(_snowman);
      boolean _snowmanxx = false;
      if (_snowman instanceof bah.i) {
         _snowmanx = ((bah.i)_snowman).a;
         if (((bah.i)_snowman).a() >= 2) {
            _snowmanxx = true;
         }
      } else {
         _snowman = new bah.i(_snowmanx);
      }

      this.a(_snowmanx);
      if (_snowmanxx) {
         this.c_(-24000);
      }

      if (_snowman instanceof aag) {
         this.eZ();
      }

      this.a(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private void eZ() {
      if (this.eL() == bah.v.a) {
         this.bl.a(4, this.bw);
         this.bl.a(4, this.bx);
         this.bl.a(6, this.by);
      } else {
         this.bl.a(4, this.by);
         this.bl.a(6, this.bw);
         this.bl.a(6, this.bx);
      }
   }

   @Override
   protected void a(bfw var1, bmb var2) {
      if (this.k(_snowman)) {
         this.a(this.d(_snowman), 1.0F, 1.0F);
      }

      super.a(_snowman, _snowman);
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return this.w_() ? _snowman.b * 0.85F : 0.4F;
   }

   public bah.v eL() {
      return bah.v.a(this.R.a(bo));
   }

   private void a(bah.v var1) {
      this.R.b(bo, _snowman.b());
   }

   private List<UUID> fa() {
      List<UUID> _snowman = Lists.newArrayList();
      _snowman.add(this.R.a(bq).orElse(null));
      _snowman.add(this.R.a(br).orElse(null));
      return _snowman;
   }

   private void b(@Nullable UUID var1) {
      if (this.R.a(bq).isPresent()) {
         this.R.b(br, Optional.ofNullable(_snowman));
      } else {
         this.R.b(bq, Optional.ofNullable(_snowman));
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      List<UUID> _snowman = this.fa();
      mj _snowmanx = new mj();

      for (UUID _snowmanxx : _snowman) {
         if (_snowmanxx != null) {
            _snowmanx.add(mp.a(_snowmanxx));
         }
      }

      _snowman.a("Trusted", _snowmanx);
      _snowman.a("Sleeping", this.em());
      _snowman.a("Type", this.eL().a());
      _snowman.a("Sitting", this.eM());
      _snowman.a("Crouching", this.bz());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      mj _snowman = _snowman.d("Trusted", 11);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.b(mp.a(_snowman.k(_snowmanx)));
      }

      this.z(_snowman.q("Sleeping"));
      this.a(bah.v.a(_snowman.l("Type")));
      this.t(_snowman.q("Sitting"));
      this.v(_snowman.q("Crouching"));
      if (this.l instanceof aag) {
         this.eZ();
      }
   }

   public boolean eM() {
      return this.t(1);
   }

   public void t(boolean var1) {
      this.d(1, _snowman);
   }

   public boolean eN() {
      return this.t(64);
   }

   private void x(boolean var1) {
      this.d(64, _snowman);
   }

   private boolean fb() {
      return this.t(128);
   }

   private void y(boolean var1) {
      this.d(128, _snowman);
   }

   @Override
   public boolean em() {
      return this.t(32);
   }

   private void z(boolean var1) {
      this.d(32, _snowman);
   }

   private void d(int var1, boolean var2) {
      if (_snowman) {
         this.R.b(bp, (byte)(this.R.a(bp) | _snowman));
      } else {
         this.R.b(bp, (byte)(this.R.a(bp) & ~_snowman));
      }
   }

   private boolean t(int var1) {
      return (this.R.a(bp) & _snowman) != 0;
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      return !this.b(_snowman).a() ? false : _snowman == aqf.a && super.e(_snowman);
   }

   @Override
   public boolean h(bmb var1) {
      blx _snowman = _snowman.b();
      bmb _snowmanx = this.b(aqf.a);
      return _snowmanx.a() || this.bD > 0 && _snowman.s() && !_snowmanx.b().s();
   }

   private void m(bmb var1) {
      if (!_snowman.a() && !this.l.v) {
         bcv _snowman = new bcv(this.l, this.cD() + this.bh().b, this.cE() + 1.0, this.cH() + this.bh().d, _snowman);
         _snowman.a(40);
         _snowman.c(this.bS());
         this.a(adq.ey, 1.0F, 1.0F);
         this.l.c(_snowman);
      }
   }

   private void n(bmb var1) {
      bcv _snowman = new bcv(this.l, this.cD(), this.cE(), this.cH(), _snowman);
      this.l.c(_snowman);
   }

   @Override
   protected void b(bcv var1) {
      bmb _snowman = _snowman.g();
      if (this.h(_snowman)) {
         int _snowmanx = _snowman.E();
         if (_snowmanx > 1) {
            this.n(_snowman.a(_snowmanx - 1));
         }

         this.m(this.b(aqf.a));
         this.a(_snowman);
         this.a(aqf.a, _snowman.a(1));
         this.bm[aqf.a.b()] = 2.0F;
         this.a(_snowman, _snowman.E());
         _snowman.ad();
         this.bD = 0;
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.dS()) {
         boolean _snowman = this.aE();
         if (_snowman || this.A() != null || this.l.W()) {
            this.fc();
         }

         if (_snowman || this.em()) {
            this.t(false);
         }

         if (this.eN() && this.l.t.nextFloat() < 0.2F) {
            fx _snowmanx = this.cB();
            ceh _snowmanxx = this.l.d_(_snowmanx);
            this.l.c(2001, _snowmanx, buo.i(_snowmanxx));
         }
      }

      this.bA = this.bz;
      if (this.eW()) {
         this.bz = this.bz + (1.0F - this.bz) * 0.4F;
      } else {
         this.bz = this.bz + (0.0F - this.bz) * 0.4F;
      }

      this.bC = this.bB;
      if (this.bz()) {
         this.bB += 0.2F;
         if (this.bB > 3.0F) {
            this.bB = 3.0F;
         }
      } else {
         this.bB = 0.0F;
      }
   }

   @Override
   public boolean k(bmb var1) {
      return _snowman.b() == bmd.rm;
   }

   @Override
   protected void a(bfw var1, aqn var2) {
      ((bah)_snowman).b(_snowman.bS());
   }

   public boolean eO() {
      return this.t(16);
   }

   public void u(boolean var1) {
      this.d(16, _snowman);
   }

   public boolean eV() {
      return this.bB == 3.0F;
   }

   public void v(boolean var1) {
      this.d(4, _snowman);
   }

   @Override
   public boolean bz() {
      return this.t(4);
   }

   public void w(boolean var1) {
      this.d(8, _snowman);
   }

   public boolean eW() {
      return this.t(8);
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bA, this.bz) * 0.11F * (float) Math.PI;
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bC, this.bB);
   }

   @Override
   public void h(@Nullable aqm var1) {
      if (this.fb() && _snowman == null) {
         this.y(false);
      }

      super.h(_snowman);
   }

   @Override
   protected int e(float var1, float var2) {
      return afm.f((_snowman - 5.0F) * _snowman);
   }

   private void fc() {
      this.z(false);
   }

   private void fd() {
      this.w(false);
      this.v(false);
      this.t(false);
      this.z(false);
      this.y(false);
      this.x(false);
   }

   private boolean fe() {
      return !this.em() && !this.eM() && !this.eN();
   }

   @Override
   public void F() {
      adp _snowman = this.I();
      if (_snowman == adq.ev) {
         this.a(_snowman, 2.0F, this.dH());
      } else {
         super.F();
      }
   }

   @Nullable
   @Override
   protected adp I() {
      if (this.em()) {
         return adq.ew;
      } else {
         if (!this.l.M() && this.J.nextFloat() < 0.1F) {
            List<bfw> _snowman = this.l.a(bfw.class, this.cc().c(16.0, 16.0, 16.0), aqd.g);
            if (_snowman.isEmpty()) {
               return adq.ev;
            }
         }

         return adq.eq;
      }
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      return adq.eu;
   }

   @Nullable
   @Override
   protected adp dq() {
      return adq.es;
   }

   private boolean c(UUID var1) {
      return this.fa().contains(_snowman);
   }

   @Override
   protected void d(apk var1) {
      bmb _snowman = this.b(aqf.a);
      if (!_snowman.a()) {
         this.a(_snowman);
         this.a(aqf.a, bmb.b);
      }

      super.d(_snowman);
   }

   public static boolean a(bah var0, aqm var1) {
      double _snowman = _snowman.cH() - _snowman.cH();
      double _snowmanx = _snowman.cD() - _snowman.cD();
      double _snowmanxx = _snowman / _snowmanx;
      int _snowmanxxx = 6;

      for (int _snowmanxxxx = 0; _snowmanxxxx < 6; _snowmanxxxx++) {
         double _snowmanxxxxx = _snowmanxx == 0.0 ? 0.0 : _snowman * (double)((float)_snowmanxxxx / 6.0F);
         double _snowmanxxxxxx = _snowmanxx == 0.0 ? _snowmanx * (double)((float)_snowmanxxxx / 6.0F) : _snowmanxxxxx / _snowmanxx;

         for (int _snowmanxxxxxxx = 1; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
            if (!_snowman.l.d_(new fx(_snowman.cD() + _snowmanxxxxxx, _snowman.cE() + (double)_snowmanxxxxxxx, _snowman.cH() + _snowmanxxxxx)).c().e()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, (double)(0.55F * this.ce()), (double)(this.cy() * 0.4F));
   }

   class a extends axq<aqm> {
      @Nullable
      private aqm j;
      private aqm k;
      private int l;

      public a(Class<aqm> var2, boolean var3, boolean var4, Predicate<aqm> var5) {
         super(bah.this, _snowman, 10, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a() {
         if (this.b > 0 && this.e.cY().nextInt(this.b) != 0) {
            return false;
         } else {
            for (UUID _snowman : bah.this.fa()) {
               if (_snowman != null && bah.this.l instanceof aag) {
                  aqa _snowmanx = ((aag)bah.this.l).a(_snowman);
                  if (_snowmanx instanceof aqm) {
                     aqm _snowmanxx = (aqm)_snowmanx;
                     this.k = _snowmanxx;
                     this.j = _snowmanxx.cZ();
                     int _snowmanxxx = _snowmanxx.da();
                     return _snowmanxxx != this.l && this.a(this.j, this.d);
                  }
               }
            }

            return false;
         }
      }

      @Override
      public void c() {
         this.a(this.j);
         this.c = this.j;
         if (this.k != null) {
            this.l = this.k.da();
         }

         bah.this.a(adq.ep, 1.0F, 1.0F);
         bah.this.y(true);
         bah.this.fc();
         super.c();
      }
   }

   class b extends avv {
      int a;

      public b() {
         this.a(EnumSet.of(avv.a.b, avv.a.c, avv.a.a));
      }

      @Override
      public boolean a() {
         return bah.this.eN();
      }

      @Override
      public boolean b() {
         return this.a() && this.a > 0;
      }

      @Override
      public void c() {
         this.a = 40;
      }

      @Override
      public void d() {
         bah.this.x(false);
      }

      @Override
      public void e() {
         this.a--;
      }
   }

   public class c implements Predicate<aqm> {
      public c() {
      }

      public boolean a(aqm var1) {
         if (_snowman instanceof bah) {
            return false;
         } else if (_snowman instanceof bac || _snowman instanceof baq || _snowman instanceof bdq) {
            return true;
         } else if (_snowman instanceof are) {
            return !((are)_snowman).eK();
         } else if (!(_snowman instanceof bfw) || !_snowman.a_() && !((bfw)_snowman).b_()) {
            return bah.this.c(_snowman.bS()) ? false : !_snowman.em() && !_snowman.bx();
         } else {
            return false;
         }
      }
   }

   abstract class d extends avv {
      private final azg b = new azg().a(12.0).c().a(bah.this.new c());

      private d() {
      }

      protected boolean g() {
         fx _snowman = new fx(bah.this.cD(), bah.this.cc().e, bah.this.cH());
         return !bah.this.l.e(_snowman) && bah.this.f(_snowman) >= 0.0F;
      }

      protected boolean h() {
         return !bah.this.l.a(aqm.class, this.b, bah.this, bah.this.cc().c(12.0, 6.0, 12.0)).isEmpty();
      }
   }

   class e extends avi {
      public e(double var2) {
         super(bah.this, _snowman);
      }

      @Override
      public void c() {
         ((bah)this.a).fd();
         ((bah)this.c).fd();
         super.c();
      }

      @Override
      protected void g() {
         aag _snowman = (aag)this.b;
         bah _snowmanx = (bah)this.a.a(_snowman, (apy)this.c);
         if (_snowmanx != null) {
            aah _snowmanxx = this.a.eR();
            aah _snowmanxxx = this.c.eR();
            aah _snowmanxxxx = _snowmanxx;
            if (_snowmanxx != null) {
               _snowmanx.b(_snowmanxx.bS());
            } else {
               _snowmanxxxx = _snowmanxxx;
            }

            if (_snowmanxxx != null && _snowmanxx != _snowmanxxx) {
               _snowmanx.b(_snowmanxxx.bS());
            }

            if (_snowmanxxxx != null) {
               _snowmanxxxx.a(aea.O);
               ac.o.a(_snowmanxxxx, this.a, this.c, _snowmanx);
            }

            this.a.c_(6000);
            this.c.c_(6000);
            this.a.eT();
            this.c.eT();
            _snowmanx.c_(-24000);
            _snowmanx.b(this.a.cD(), this.a.cE(), this.a.cH(), 0.0F, 0.0F);
            _snowman.l(_snowmanx);
            this.b.a(this.a, (byte)18);
            if (this.b.V().b(brt.e)) {
               this.b.c(new aqg(this.b, this.a.cD(), this.a.cE(), this.a.cH(), this.a.cY().nextInt(7) + 1));
            }
         }
      }
   }

   public class f extends awj {
      protected int g;

      public f(double var2, int var4, int var5) {
         super(bah.this, _snowman, _snowman, _snowman);
      }

      @Override
      public double h() {
         return 2.0;
      }

      @Override
      public boolean k() {
         return this.d % 100 == 0;
      }

      @Override
      protected boolean a(brz var1, fx var2) {
         ceh _snowman = _snowman.d_(_snowman);
         return _snowman.a(bup.mg) && _snowman.c(cau.a) >= 2;
      }

      @Override
      public void e() {
         if (this.l()) {
            if (this.g >= 40) {
               this.n();
            } else {
               this.g++;
            }
         } else if (!this.l() && bah.this.J.nextFloat() < 0.05F) {
            bah.this.a(adq.ex, 1.0F, 1.0F);
         }

         super.e();
      }

      protected void n() {
         if (bah.this.l.V().b(brt.b)) {
            ceh _snowman = bah.this.l.d_(this.e);
            if (_snowman.a(bup.mg)) {
               int _snowmanx = _snowman.c(cau.a);
               _snowman.a(cau.a, Integer.valueOf(1));
               int _snowmanxx = 1 + bah.this.l.t.nextInt(2) + (_snowmanx == 3 ? 1 : 0);
               bmb _snowmanxxx = bah.this.b(aqf.a);
               if (_snowmanxxx.a()) {
                  bah.this.a(aqf.a, new bmb(bmd.rm));
                  _snowmanxx--;
               }

               if (_snowmanxx > 0) {
                  buo.a(bah.this.l, this.e, new bmb(bmd.rm, _snowmanxx));
               }

               bah.this.a(adq.oZ, 1.0F, 1.0F);
               bah.this.l.a(this.e, _snowman.a(cau.a, Integer.valueOf(1)), 2);
            }
         }
      }

      @Override
      public boolean a() {
         return !bah.this.em() && super.a();
      }

      @Override
      public void c() {
         this.g = 0;
         bah.this.t(false);
         super.c();
      }
   }

   class g extends avp {
      public g() {
         super(bah.this);
      }

      @Override
      public void c() {
         super.c();
         bah.this.fd();
      }

      @Override
      public boolean a() {
         return bah.this.aE() && bah.this.b(aef.b) > 0.25 || bah.this.aQ();
      }
   }

   class h extends avu {
      private final bah b;

      public h(bah var2, double var3) {
         super(_snowman, _snowman);
         this.b = _snowman;
      }

      @Override
      public boolean a() {
         return !this.b.fb() && super.a();
      }

      @Override
      public boolean b() {
         return !this.b.fb() && super.b();
      }

      @Override
      public void c() {
         this.b.fd();
         super.c();
      }
   }

   public static class i extends apy.a {
      public final bah.v a;

      public i(bah.v var1) {
         super(false);
         this.a = _snowman;
      }
   }

   class j extends awd {
      public j(aqn var2, Class<? extends aqm> var3, float var4) {
         super(_snowman, _snowman, _snowman);
      }

      @Override
      public boolean a() {
         return super.a() && !bah.this.eN() && !bah.this.eW();
      }

      @Override
      public boolean b() {
         return super.b() && !bah.this.eN() && !bah.this.eW();
      }
   }

   public class k extends ava {
      public k() {
         super(bah.this);
      }

      @Override
      public void a() {
         if (!bah.this.em()) {
            super.a();
         }
      }

      @Override
      protected boolean b() {
         return !bah.this.eO() && !bah.this.bz() && !bah.this.eW() & !bah.this.eN();
      }
   }

   class l extends awf {
      public l(double var2, boolean var4) {
         super(bah.this, _snowman, _snowman);
      }

      @Override
      protected void a(aqm var1, double var2) {
         double _snowman = this.a(_snowman);
         if (_snowman <= _snowman && this.h()) {
            this.g();
            this.a.B(_snowman);
            bah.this.a(adq.er, 1.0F, 1.0F);
         }
      }

      @Override
      public void c() {
         bah.this.w(false);
         super.c();
      }

      @Override
      public boolean a() {
         return !bah.this.eM() && !bah.this.em() && !bah.this.bz() && !bah.this.eN() && super.a();
      }
   }

   class m extends avb {
      public m() {
         super(bah.this);
      }

      @Override
      public void a() {
         if (bah.this.fe()) {
            super.a();
         }
      }
   }

   class n extends awp {
      public n(double var2) {
         super(bah.this, _snowman);
      }

      @Override
      public boolean a() {
         return !bah.this.fb() && super.a();
      }
   }

   public class o extends avz {
      public o() {
      }

      @Override
      public boolean a() {
         if (!bah.this.eV()) {
            return false;
         } else {
            aqm _snowman = bah.this.A();
            if (_snowman != null && _snowman.aX()) {
               if (_snowman.ca() != _snowman.bZ()) {
                  return false;
               } else {
                  boolean _snowmanx = bah.a(bah.this, _snowman);
                  if (!_snowmanx) {
                     bah.this.x().a(_snowman, 0);
                     bah.this.v(false);
                     bah.this.w(false);
                  }

                  return _snowmanx;
               }
            } else {
               return false;
            }
         }
      }

      @Override
      public boolean b() {
         aqm _snowman = bah.this.A();
         if (_snowman != null && _snowman.aX()) {
            double _snowmanx = bah.this.cC().c;
            return (!(_snowmanx * _snowmanx < 0.05F) || !(Math.abs(bah.this.q) < 15.0F) || !bah.this.t) && !bah.this.eN();
         } else {
            return false;
         }
      }

      @Override
      public boolean C_() {
         return false;
      }

      @Override
      public void c() {
         bah.this.o(true);
         bah.this.u(true);
         bah.this.w(false);
         aqm _snowman = bah.this.A();
         bah.this.t().a(_snowman, 60.0F, 30.0F);
         dcn _snowmanx = new dcn(_snowman.cD() - bah.this.cD(), _snowman.cE() - bah.this.cE(), _snowman.cH() - bah.this.cH()).d();
         bah.this.f(bah.this.cC().b(_snowmanx.b * 0.8, 0.9, _snowmanx.d * 0.8));
         bah.this.x().o();
      }

      @Override
      public void d() {
         bah.this.v(false);
         bah.this.bB = 0.0F;
         bah.this.bC = 0.0F;
         bah.this.w(false);
         bah.this.u(false);
      }

      @Override
      public void e() {
         aqm _snowman = bah.this.A();
         if (_snowman != null) {
            bah.this.t().a(_snowman, 60.0F, 30.0F);
         }

         if (!bah.this.eN()) {
            dcn _snowmanx = bah.this.cC();
            if (_snowmanx.c * _snowmanx.c < 0.03F && bah.this.q != 0.0F) {
               bah.this.q = afm.j(bah.this.q, 0.0F, 0.2F);
            } else {
               double _snowmanxx = Math.sqrt(aqa.c(_snowmanx));
               double _snowmanxxx = Math.signum(-_snowmanx.c) * Math.acos(_snowmanxx / _snowmanx.f()) * 180.0F / (float)Math.PI;
               bah.this.q = (float)_snowmanxxx;
            }
         }

         if (_snowman != null && bah.this.g(_snowman) <= 2.0F) {
            bah.this.B(_snowman);
         } else if (bah.this.q > 0.0F && bah.this.t && (float)bah.this.cC().c != 0.0F && bah.this.l.d_(bah.this.cB()).a(bup.cC)) {
            bah.this.q = 60.0F;
            bah.this.h(null);
            bah.this.x(true);
         }
      }
   }

   class p extends avv {
      public p() {
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         if (!bah.this.b(aqf.a).a()) {
            return false;
         } else if (bah.this.A() != null || bah.this.cZ() != null) {
            return false;
         } else if (!bah.this.fe()) {
            return false;
         } else if (bah.this.cY().nextInt(10) != 0) {
            return false;
         } else {
            List<bcv> _snowman = bah.this.l.a(bcv.class, bah.this.cc().c(8.0, 8.0, 8.0), bah.bs);
            return !_snowman.isEmpty() && bah.this.b(aqf.a).a();
         }
      }

      @Override
      public void e() {
         List<bcv> _snowman = bah.this.l.a(bcv.class, bah.this.cc().c(8.0, 8.0, 8.0), bah.bs);
         bmb _snowmanx = bah.this.b(aqf.a);
         if (_snowmanx.a() && !_snowman.isEmpty()) {
            bah.this.x().a(_snowman.get(0), 1.2F);
         }
      }

      @Override
      public void c() {
         List<bcv> _snowman = bah.this.l.a(bcv.class, bah.this.cc().c(8.0, 8.0, 8.0), bah.bs);
         if (!_snowman.isEmpty()) {
            bah.this.x().a(_snowman.get(0), 1.2F);
         }
      }
   }

   class q extends axc {
      public q(int var2, int var3) {
         super(bah.this, _snowman);
      }

      @Override
      public void c() {
         bah.this.fd();
         super.c();
      }

      @Override
      public boolean a() {
         return super.a() && this.g();
      }

      @Override
      public boolean b() {
         return super.b() && this.g();
      }

      private boolean g() {
         return !bah.this.em() && !bah.this.eM() && !bah.this.fb() && bah.this.A() == null;
      }
   }

   class r extends bah.d {
      private double c;
      private double d;
      private int e;
      private int f;

      public r() {
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         return bah.this.cZ() == null
            && bah.this.cY().nextFloat() < 0.02F
            && !bah.this.em()
            && bah.this.A() == null
            && bah.this.x().m()
            && !this.h()
            && !bah.this.eO()
            && !bah.this.bz();
      }

      @Override
      public boolean b() {
         return this.f > 0;
      }

      @Override
      public void c() {
         this.j();
         this.f = 2 + bah.this.cY().nextInt(3);
         bah.this.t(true);
         bah.this.x().o();
      }

      @Override
      public void d() {
         bah.this.t(false);
      }

      @Override
      public void e() {
         this.e--;
         if (this.e <= 0) {
            this.f--;
            this.j();
         }

         bah.this.t().a(bah.this.cD() + this.c, bah.this.cG(), bah.this.cH() + this.d, (float)bah.this.Q(), (float)bah.this.O());
      }

      private void j() {
         double _snowman = (Math.PI * 2) * bah.this.cY().nextDouble();
         this.c = Math.cos(_snowman);
         this.d = Math.sin(_snowman);
         this.e = 80 + bah.this.cY().nextInt(20);
      }
   }

   class s extends avo {
      private int c = 100;

      public s(double var2) {
         super(bah.this, _snowman);
      }

      @Override
      public boolean a() {
         if (bah.this.em() || this.a.A() != null) {
            return false;
         } else if (bah.this.l.W()) {
            return true;
         } else if (this.c > 0) {
            this.c--;
            return false;
         } else {
            this.c = 100;
            fx _snowman = this.a.cB();
            return bah.this.l.M() && bah.this.l.e(_snowman) && !((aag)bah.this.l).a_(_snowman) && this.g();
         }
      }

      @Override
      public void c() {
         bah.this.fd();
         super.c();
      }
   }

   class t extends bah.d {
      private int c = bah.this.J.nextInt(140);

      public t() {
         this.a(EnumSet.of(avv.a.a, avv.a.b, avv.a.c));
      }

      @Override
      public boolean a() {
         return bah.this.aR == 0.0F && bah.this.aS == 0.0F && bah.this.aT == 0.0F ? this.j() || bah.this.em() : false;
      }

      @Override
      public boolean b() {
         return this.j();
      }

      private boolean j() {
         if (this.c > 0) {
            this.c--;
            return false;
         } else {
            return bah.this.l.M() && this.g() && !this.h();
         }
      }

      @Override
      public void d() {
         this.c = bah.this.J.nextInt(140);
         bah.this.fd();
      }

      @Override
      public void c() {
         bah.this.t(false);
         bah.this.v(false);
         bah.this.w(false);
         bah.this.o(false);
         bah.this.z(true);
         bah.this.x().o();
         bah.this.u().a(bah.this.cD(), bah.this.cE(), bah.this.cH(), 0.0);
      }
   }

   class u extends avv {
      public u() {
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         if (bah.this.em()) {
            return false;
         } else {
            aqm _snowman = bah.this.A();
            return _snowman != null && _snowman.aX() && bah.bu.test(_snowman) && bah.this.h((aqa)_snowman) > 36.0 && !bah.this.bz() && !bah.this.eW() && !bah.this.aQ;
         }
      }

      @Override
      public void c() {
         bah.this.t(false);
         bah.this.x(false);
      }

      @Override
      public void d() {
         aqm _snowman = bah.this.A();
         if (_snowman != null && bah.a(bah.this, _snowman)) {
            bah.this.w(true);
            bah.this.v(true);
            bah.this.x().o();
            bah.this.t().a(_snowman, (float)bah.this.Q(), (float)bah.this.O());
         } else {
            bah.this.w(false);
            bah.this.v(false);
         }
      }

      @Override
      public void e() {
         aqm _snowman = bah.this.A();
         bah.this.t().a(_snowman, (float)bah.this.Q(), (float)bah.this.O());
         if (bah.this.h((aqa)_snowman) <= 36.0) {
            bah.this.w(true);
            bah.this.v(true);
            bah.this.x().o();
         } else {
            bah.this.x().a(_snowman, 1.5);
         }
      }
   }

   public static enum v {
      a(0, "red", btb.f, btb.t, btb.ae, btb.G, btb.an, btb.H, btb.ao),
      b(1, "snow", btb.E, btb.F, btb.am);

      private static final bah.v[] c = Arrays.stream(values()).sorted(Comparator.comparingInt(bah.v::b)).toArray(bah.v[]::new);
      private static final Map<String, bah.v> d = Arrays.stream(values()).collect(Collectors.toMap(bah.v::a, var0 -> (bah.v)var0));
      private final int e;
      private final String f;
      private final List<vj<bsv>> g;

      private v(int var3, String var4, vj<bsv>... var5) {
         this.e = _snowman;
         this.f = _snowman;
         this.g = Arrays.asList(_snowman);
      }

      public String a() {
         return this.f;
      }

      public int b() {
         return this.e;
      }

      public static bah.v a(String var0) {
         return d.getOrDefault(_snowman, a);
      }

      public static bah.v a(int var0) {
         if (_snowman < 0 || _snowman > c.length) {
            _snowman = 0;
         }

         return c[_snowman];
      }

      public static bah.v a(Optional<vj<bsv>> var0) {
         return _snowman.isPresent() && b.g.contains(_snowman.get()) ? b : a;
      }
   }
}
