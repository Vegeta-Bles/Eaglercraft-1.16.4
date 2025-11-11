import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bcn extends aqm {
   private static final go bj = new go(0.0F, 0.0F, 0.0F);
   private static final go bk = new go(0.0F, 0.0F, 0.0F);
   private static final go bl = new go(-10.0F, 0.0F, -10.0F);
   private static final go bm = new go(-15.0F, 0.0F, 10.0F);
   private static final go bn = new go(-1.0F, 0.0F, -1.0F);
   private static final go bo = new go(1.0F, 0.0F, 1.0F);
   private static final aqb bp = new aqb(0.0F, 0.0F, true);
   private static final aqb bq = aqe.b.l().a(0.5F);
   public static final us<Byte> b = uv.a(bcn.class, uu.a);
   public static final us<go> c = uv.a(bcn.class, uu.k);
   public static final us<go> d = uv.a(bcn.class, uu.k);
   public static final us<go> e = uv.a(bcn.class, uu.k);
   public static final us<go> f = uv.a(bcn.class, uu.k);
   public static final us<go> g = uv.a(bcn.class, uu.k);
   public static final us<go> bh = uv.a(bcn.class, uu.k);
   private static final Predicate<aqa> br = var0 -> var0 instanceof bhl && ((bhl)var0).o() == bhl.a.a;
   private final gj<bmb> bs = gj.a(2, bmb.b);
   private final gj<bmb> bt = gj.a(4, bmb.b);
   private boolean bu;
   public long bi;
   private int bv;
   private go bw = bj;
   private go bx = bk;
   private go by = bl;
   private go bz = bm;
   private go bA = bn;
   private go bB = bo;

   public bcn(aqe<? extends bcn> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 0.0F;
   }

   public bcn(brx var1, double var2, double var4, double var6) {
      this(aqe.b, _snowman);
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   public void x_() {
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      super.x_();
      this.d(_snowman, _snowmanx, _snowmanxx);
   }

   private boolean A() {
      return !this.q() && !this.aB();
   }

   @Override
   public boolean dS() {
      return super.dS() && this.A();
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
      this.R.a(c, bj);
      this.R.a(d, bk);
      this.R.a(e, bl);
      this.R.a(f, bm);
      this.R.a(g, bn);
      this.R.a(bh, bo);
   }

   @Override
   public Iterable<bmb> bn() {
      return this.bs;
   }

   @Override
   public Iterable<bmb> bo() {
      return this.bt;
   }

   @Override
   public bmb b(aqf var1) {
      switch (_snowman.a()) {
         case a:
            return this.bs.get(_snowman.b());
         case b:
            return this.bt.get(_snowman.b());
         default:
            return bmb.b;
      }
   }

   @Override
   public void a(aqf var1, bmb var2) {
      switch (_snowman.a()) {
         case a:
            this.b(_snowman);
            this.bs.set(_snowman.b(), _snowman);
            break;
         case b:
            this.b(_snowman);
            this.bt.set(_snowman.b(), _snowman);
      }
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      aqf _snowman;
      if (_snowman == 98) {
         _snowman = aqf.a;
      } else if (_snowman == 99) {
         _snowman = aqf.b;
      } else if (_snowman == 100 + aqf.f.b()) {
         _snowman = aqf.f;
      } else if (_snowman == 100 + aqf.e.b()) {
         _snowman = aqf.e;
      } else if (_snowman == 100 + aqf.d.b()) {
         _snowman = aqf.d;
      } else {
         if (_snowman != 100 + aqf.c.b()) {
            return false;
         }

         _snowman = aqf.c;
      }

      if (!_snowman.a() && !aqn.c(_snowman, _snowman) && _snowman != aqf.f) {
         return false;
      } else {
         this.a(_snowman, _snowman);
         return true;
      }
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      return this.b(_snowman).a() && !this.d(_snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      mj _snowman = new mj();

      for (bmb _snowmanx : this.bt) {
         md _snowmanxx = new md();
         if (!_snowmanx.a()) {
            _snowmanx.b(_snowmanxx);
         }

         _snowman.add(_snowmanxx);
      }

      _snowman.a("ArmorItems", _snowman);
      mj _snowmanx = new mj();

      for (bmb _snowmanxx : this.bs) {
         md _snowmanxxx = new md();
         if (!_snowmanxx.a()) {
            _snowmanxx.b(_snowmanxxx);
         }

         _snowmanx.add(_snowmanxxx);
      }

      _snowman.a("HandItems", _snowmanx);
      _snowman.a("Invisible", this.bF());
      _snowman.a("Small", this.m());
      _snowman.a("ShowArms", this.o());
      _snowman.b("DisabledSlots", this.bv);
      _snowman.a("NoBasePlate", this.p());
      if (this.q()) {
         _snowman.a("Marker", this.q());
      }

      _snowman.a("Pose", this.B());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("ArmorItems", 9)) {
         mj _snowman = _snowman.d("ArmorItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.bt.size(); _snowmanx++) {
            this.bt.set(_snowmanx, bmb.a(_snowman.a(_snowmanx)));
         }
      }

      if (_snowman.c("HandItems", 9)) {
         mj _snowman = _snowman.d("HandItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.bs.size(); _snowmanx++) {
            this.bs.set(_snowmanx, bmb.a(_snowman.a(_snowmanx)));
         }
      }

      this.j(_snowman.q("Invisible"));
      this.a(_snowman.q("Small"));
      this.p(_snowman.q("ShowArms"));
      this.bv = _snowman.h("DisabledSlots");
      this.q(_snowman.q("NoBasePlate"));
      this.r(_snowman.q("Marker"));
      this.H = !this.A();
      md _snowman = _snowman.p("Pose");
      this.g(_snowman);
   }

   private void g(md var1) {
      mj _snowman = _snowman.d("Head", 5);
      this.a(_snowman.isEmpty() ? bj : new go(_snowman));
      mj _snowmanx = _snowman.d("Body", 5);
      this.b(_snowmanx.isEmpty() ? bk : new go(_snowmanx));
      mj _snowmanxx = _snowman.d("LeftArm", 5);
      this.c(_snowmanxx.isEmpty() ? bl : new go(_snowmanxx));
      mj _snowmanxxx = _snowman.d("RightArm", 5);
      this.d(_snowmanxxx.isEmpty() ? bm : new go(_snowmanxxx));
      mj _snowmanxxxx = _snowman.d("LeftLeg", 5);
      this.e(_snowmanxxxx.isEmpty() ? bn : new go(_snowmanxxxx));
      mj _snowmanxxxxx = _snowman.d("RightLeg", 5);
      this.f(_snowmanxxxxx.isEmpty() ? bo : new go(_snowmanxxxxx));
   }

   private md B() {
      md _snowman = new md();
      if (!bj.equals(this.bw)) {
         _snowman.a("Head", this.bw.a());
      }

      if (!bk.equals(this.bx)) {
         _snowman.a("Body", this.bx.a());
      }

      if (!bl.equals(this.by)) {
         _snowman.a("LeftArm", this.by.a());
      }

      if (!bm.equals(this.bz)) {
         _snowman.a("RightArm", this.bz.a());
      }

      if (!bn.equals(this.bA)) {
         _snowman.a("LeftLeg", this.bA.a());
      }

      if (!bo.equals(this.bB)) {
         _snowman.a("RightLeg", this.bB.a());
      }

      return _snowman;
   }

   @Override
   public boolean aU() {
      return false;
   }

   @Override
   protected void C(aqa var1) {
   }

   @Override
   protected void dQ() {
      List<aqa> _snowman = this.l.a(this, this.cc(), br);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         aqa _snowmanxx = _snowman.get(_snowmanx);
         if (this.h(_snowmanxx) <= 0.2) {
            _snowmanxx.i(this);
         }
      }
   }

   @Override
   public aou a(bfw var1, dcn var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      if (this.q() || _snowman.b() == bmd.pI) {
         return aou.c;
      } else if (_snowman.a_()) {
         return aou.a;
      } else if (_snowman.l.v) {
         return aou.b;
      } else {
         aqf _snowmanx = aqn.j(_snowman);
         if (_snowman.a()) {
            aqf _snowmanxx = this.i(_snowman);
            aqf _snowmanxxx = this.d(_snowmanxx) ? _snowmanx : _snowmanxx;
            if (this.a(_snowmanxxx) && this.a(_snowman, _snowmanxxx, _snowman, _snowman)) {
               return aou.a;
            }
         } else {
            if (this.d(_snowmanx)) {
               return aou.d;
            }

            if (_snowmanx.a() == aqf.a.a && !this.o()) {
               return aou.d;
            }

            if (this.a(_snowman, _snowmanx, _snowman, _snowman)) {
               return aou.a;
            }
         }

         return aou.c;
      }
   }

   private aqf i(dcn var1) {
      aqf _snowman = aqf.a;
      boolean _snowmanx = this.m();
      double _snowmanxx = _snowmanx ? _snowman.c * 2.0 : _snowman.c;
      aqf _snowmanxxx = aqf.c;
      if (_snowmanxx >= 0.1 && _snowmanxx < 0.1 + (_snowmanx ? 0.8 : 0.45) && this.a(_snowmanxxx)) {
         _snowman = aqf.c;
      } else if (_snowmanxx >= 0.9 + (_snowmanx ? 0.3 : 0.0) && _snowmanxx < 0.9 + (_snowmanx ? 1.0 : 0.7) && this.a(aqf.e)) {
         _snowman = aqf.e;
      } else if (_snowmanxx >= 0.4 && _snowmanxx < 0.4 + (_snowmanx ? 1.0 : 0.8) && this.a(aqf.d)) {
         _snowman = aqf.d;
      } else if (_snowmanxx >= 1.6 && this.a(aqf.f)) {
         _snowman = aqf.f;
      } else if (!this.a(aqf.a) && this.a(aqf.b)) {
         _snowman = aqf.b;
      }

      return _snowman;
   }

   private boolean d(aqf var1) {
      return (this.bv & 1 << _snowman.c()) != 0 || _snowman.a() == aqf.a.a && !this.o();
   }

   private boolean a(bfw var1, aqf var2, bmb var3, aot var4) {
      bmb _snowman = this.b(_snowman);
      if (!_snowman.a() && (this.bv & 1 << _snowman.c() + 8) != 0) {
         return false;
      } else if (_snowman.a() && (this.bv & 1 << _snowman.c() + 16) != 0) {
         return false;
      } else if (_snowman.bC.d && _snowman.a() && !_snowman.a()) {
         bmb _snowmanx = _snowman.i();
         _snowmanx.e(1);
         this.a(_snowman, _snowmanx);
         return true;
      } else if (_snowman.a() || _snowman.E() <= 1) {
         this.a(_snowman, _snowman);
         _snowman.a(_snowman, _snowman);
         return true;
      } else if (!_snowman.a()) {
         return false;
      } else {
         bmb _snowmanx = _snowman.i();
         _snowmanx.e(1);
         this.a(_snowman, _snowmanx);
         _snowman.g(1);
         return true;
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.l.v || this.y) {
         return false;
      } else if (apk.m.equals(_snowman)) {
         this.ad();
         return false;
      } else if (this.b(_snowman) || this.bu || this.q()) {
         return false;
      } else if (_snowman.d()) {
         this.g(_snowman);
         this.ad();
         return false;
      } else if (apk.a.equals(_snowman)) {
         if (this.bq()) {
            this.f(_snowman, 0.15F);
         } else {
            this.f(5);
         }

         return false;
      } else if (apk.c.equals(_snowman) && this.dk() > 0.5F) {
         this.f(_snowman, 4.0F);
         return false;
      } else {
         boolean _snowman = _snowman.j() instanceof bga;
         boolean _snowmanx = _snowman && ((bga)_snowman.j()).r() > 0;
         boolean _snowmanxx = "player".equals(_snowman.q());
         if (!_snowmanxx && !_snowman) {
            return false;
         } else if (_snowman.k() instanceof bfw && !((bfw)_snowman.k()).bC.e) {
            return false;
         } else if (_snowman.v()) {
            this.F();
            this.D();
            this.ad();
            return _snowmanx;
         } else {
            long _snowmanxxx = this.l.T();
            if (_snowmanxxx - this.bi > 5L && !_snowman) {
               this.l.a(this, (byte)32);
               this.bi = _snowmanxxx;
            } else {
               this.f(_snowman);
               this.D();
               this.ad();
            }

            return true;
         }
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 32) {
         if (this.l.v) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.U, this.cu(), 0.3F, 1.0F, false);
            this.bi = this.l.T();
         }
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public boolean a(double var1) {
      double _snowman = this.cc().a() * 4.0;
      if (Double.isNaN(_snowman) || _snowman == 0.0) {
         _snowman = 4.0;
      }

      _snowman *= 64.0;
      return _snowman < _snowman * _snowman;
   }

   private void D() {
      if (this.l instanceof aag) {
         ((aag)this.l)
            .a(
               new hc(hh.d, bup.n.n()),
               this.cD(),
               this.e(0.6666666666666666),
               this.cH(),
               10,
               (double)(this.cy() / 4.0F),
               (double)(this.cz() / 4.0F),
               (double)(this.cy() / 4.0F),
               0.05
            );
      }
   }

   private void f(apk var1, float var2) {
      float _snowman = this.dk();
      _snowman -= _snowman;
      if (_snowman <= 0.5F) {
         this.g(_snowman);
         this.ad();
      } else {
         this.c(_snowman);
      }
   }

   private void f(apk var1) {
      buo.a(this.l, this.cB(), new bmb(bmd.pC));
      this.g(_snowman);
   }

   private void g(apk var1) {
      this.F();
      this.d(_snowman);

      for (int _snowman = 0; _snowman < this.bs.size(); _snowman++) {
         bmb _snowmanx = this.bs.get(_snowman);
         if (!_snowmanx.a()) {
            buo.a(this.l, this.cB().b(), _snowmanx);
            this.bs.set(_snowman, bmb.b);
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.bt.size(); _snowmanx++) {
         bmb _snowmanxx = this.bt.get(_snowmanx);
         if (!_snowmanxx.a()) {
            buo.a(this.l, this.cB().b(), _snowmanxx);
            this.bt.set(_snowmanx, bmb.b);
         }
      }
   }

   private void F() {
      this.l.a(null, this.cD(), this.cE(), this.cH(), adq.S, this.cu(), 1.0F, 1.0F);
   }

   @Override
   protected float f(float var1, float var2) {
      this.aB = this.r;
      this.aA = this.p;
      return 0.0F;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * (this.w_() ? 0.5F : 0.9F);
   }

   @Override
   public double bb() {
      return this.q() ? 0.0 : 0.1F;
   }

   @Override
   public void g(dcn var1) {
      if (this.A()) {
         super.g(_snowman);
      }
   }

   @Override
   public void n(float var1) {
      this.aB = this.r = _snowman;
      this.aD = this.aC = _snowman;
   }

   @Override
   public void m(float var1) {
      this.aB = this.r = _snowman;
      this.aD = this.aC = _snowman;
   }

   @Override
   public void j() {
      super.j();
      go _snowman = this.R.a(c);
      if (!this.bw.equals(_snowman)) {
         this.a(_snowman);
      }

      go _snowmanx = this.R.a(d);
      if (!this.bx.equals(_snowmanx)) {
         this.b(_snowmanx);
      }

      go _snowmanxx = this.R.a(e);
      if (!this.by.equals(_snowmanxx)) {
         this.c(_snowmanxx);
      }

      go _snowmanxxx = this.R.a(f);
      if (!this.bz.equals(_snowmanxxx)) {
         this.d(_snowmanxxx);
      }

      go _snowmanxxxx = this.R.a(g);
      if (!this.bA.equals(_snowmanxxxx)) {
         this.e(_snowmanxxxx);
      }

      go _snowmanxxxxx = this.R.a(bh);
      if (!this.bB.equals(_snowmanxxxxx)) {
         this.f(_snowmanxxxxx);
      }
   }

   @Override
   protected void C() {
      this.j(this.bu);
   }

   @Override
   public void j(boolean var1) {
      this.bu = _snowman;
      super.j(_snowman);
   }

   @Override
   public boolean w_() {
      return this.m();
   }

   @Override
   public void aa() {
      this.ad();
   }

   @Override
   public boolean ci() {
      return this.bF();
   }

   @Override
   public cvc y_() {
      return this.q() ? cvc.d : super.y_();
   }

   private void a(boolean var1) {
      this.R.b(b, this.a(this.R.a(b), 1, _snowman));
   }

   public boolean m() {
      return (this.R.a(b) & 1) != 0;
   }

   private void p(boolean var1) {
      this.R.b(b, this.a(this.R.a(b), 4, _snowman));
   }

   public boolean o() {
      return (this.R.a(b) & 4) != 0;
   }

   private void q(boolean var1) {
      this.R.b(b, this.a(this.R.a(b), 8, _snowman));
   }

   public boolean p() {
      return (this.R.a(b) & 8) != 0;
   }

   private void r(boolean var1) {
      this.R.b(b, this.a(this.R.a(b), 16, _snowman));
   }

   public boolean q() {
      return (this.R.a(b) & 16) != 0;
   }

   private byte a(byte var1, int var2, boolean var3) {
      if (_snowman) {
         _snowman = (byte)(_snowman | _snowman);
      } else {
         _snowman = (byte)(_snowman & ~_snowman);
      }

      return _snowman;
   }

   public void a(go var1) {
      this.bw = _snowman;
      this.R.b(c, _snowman);
   }

   public void b(go var1) {
      this.bx = _snowman;
      this.R.b(d, _snowman);
   }

   public void c(go var1) {
      this.by = _snowman;
      this.R.b(e, _snowman);
   }

   public void d(go var1) {
      this.bz = _snowman;
      this.R.b(f, _snowman);
   }

   public void e(go var1) {
      this.bA = _snowman;
      this.R.b(g, _snowman);
   }

   public void f(go var1) {
      this.bB = _snowman;
      this.R.b(bh, _snowman);
   }

   public go r() {
      return this.bw;
   }

   public go t() {
      return this.bx;
   }

   public go u() {
      return this.by;
   }

   public go v() {
      return this.bz;
   }

   public go x() {
      return this.bA;
   }

   public go z() {
      return this.bB;
   }

   @Override
   public boolean aT() {
      return super.aT() && !this.q();
   }

   @Override
   public boolean t(aqa var1) {
      return _snowman instanceof bfw && !this.l.a((bfw)_snowman, this.cB());
   }

   @Override
   public aqi dV() {
      return aqi.b;
   }

   @Override
   protected adp o(int var1) {
      return adq.T;
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      return adq.U;
   }

   @Nullable
   @Override
   protected adp dq() {
      return adq.S;
   }

   @Override
   public void a(aag var1, aql var2) {
   }

   @Override
   public boolean eh() {
      return false;
   }

   @Override
   public void a(us<?> var1) {
      if (b.equals(_snowman)) {
         this.x_();
         this.i = !this.q();
      }

      super.a(_snowman);
   }

   @Override
   public boolean ei() {
      return false;
   }

   @Override
   public aqb a(aqx var1) {
      return this.s(this.q());
   }

   private aqb s(boolean var1) {
      if (_snowman) {
         return bp;
      } else {
         return this.w_() ? bq : this.X().l();
      }
   }

   @Override
   public dcn k(float var1) {
      if (this.q()) {
         dci _snowman = this.s(false).a(this.cA());
         fx _snowmanx = this.cB();
         int _snowmanxx = Integer.MIN_VALUE;

         for (fx _snowmanxxx : fx.a(new fx(_snowman.a, _snowman.b, _snowman.c), new fx(_snowman.d, _snowman.e, _snowman.f))) {
            int _snowmanxxxx = Math.max(this.l.a(bsf.b, _snowmanxxx), this.l.a(bsf.a, _snowmanxxx));
            if (_snowmanxxxx == 15) {
               return dcn.a(_snowmanxxx);
            }

            if (_snowmanxxxx > _snowmanxx) {
               _snowmanxx = _snowmanxxxx;
               _snowmanx = _snowmanxxx.h();
            }
         }

         return dcn.a(_snowmanx);
      } else {
         return super.k(_snowman);
      }
   }
}
