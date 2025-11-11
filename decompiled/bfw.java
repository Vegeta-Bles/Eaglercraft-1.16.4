import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class bfw extends aqm {
   public static final aqb bh = aqb.b(0.6F, 1.8F);
   private static final Map<aqx, aqb> b = ImmutableMap.builder()
      .put(aqx.a, bh)
      .put(aqx.c, ah)
      .put(aqx.b, aqb.b(0.6F, 0.6F))
      .put(aqx.d, aqb.b(0.6F, 0.6F))
      .put(aqx.e, aqb.b(0.6F, 0.6F))
      .put(aqx.f, aqb.b(0.6F, 1.5F))
      .put(aqx.g, aqb.c(0.2F, 0.2F))
      .build();
   private static final us<Float> c = uv.a(bfw.class, uu.c);
   private static final us<Integer> d = uv.a(bfw.class, uu.b);
   protected static final us<Byte> bi = uv.a(bfw.class, uu.a);
   protected static final us<Byte> bj = uv.a(bfw.class, uu.a);
   protected static final us<md> bk = uv.a(bfw.class, uu.p);
   protected static final us<md> bl = uv.a(bfw.class, uu.p);
   private long e;
   public final bfv bm = new bfv(this);
   protected bji bn = new bji();
   public final biz bo;
   public bic bp;
   protected bhy bq = new bhy();
   protected int br;
   public float bs;
   public float bt;
   public int bu;
   public double bv;
   public double bw;
   public double bx;
   public double by;
   public double bz;
   public double bA;
   private int f;
   protected boolean bB;
   public final bft bC = new bft();
   public int bD;
   public int bE;
   public float bF;
   protected int bG;
   protected final float bH = 0.02F;
   private int g;
   private final GameProfile bJ;
   private boolean bK;
   private bmb bL = bmb.b;
   private final bly bM = this.i();
   @Nullable
   public bgi bI;

   public bfw(brx var1, fx var2, float var3, GameProfile var4) {
      super(aqe.bc, _snowman);
      this.a_(a(_snowman));
      this.bJ = _snowman;
      this.bo = new biz(this.bm, !_snowman.v, this);
      this.bp = this.bo;
      this.b((double)_snowman.u() + 0.5, (double)(_snowman.v() + 1), (double)_snowman.w() + 0.5, _snowman, 0.0F);
      this.aN = 180.0F;
   }

   public boolean a(brx var1, fx var2, bru var3) {
      if (!_snowman.d()) {
         return false;
      } else if (_snowman == bru.e) {
         return true;
      } else if (this.eK()) {
         return false;
      } else {
         bmb _snowman = this.dD();
         return _snowman.a() || !_snowman.a(_snowman.p(), new cel(_snowman, _snowman, false));
      }
   }

   public static ark.a ep() {
      return aqm.cL().a(arl.f, 1.0).a(arl.d, 0.1F).a(arl.h).a(arl.k);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(c, 0.0F);
      this.R.a(d, 0);
      this.R.a(bi, (byte)0);
      this.R.a(bj, (byte)1);
      this.R.a(bk, new md());
      this.R.a(bl, new md());
   }

   @Override
   public void j() {
      this.H = this.a_();
      if (this.a_()) {
         this.t = false;
      }

      if (this.bu > 0) {
         this.bu--;
      }

      if (this.em()) {
         this.f++;
         if (this.f > 100) {
            this.f = 100;
         }

         if (!this.l.v && this.l.M()) {
            this.a(false, true);
         }
      } else if (this.f > 0) {
         this.f++;
         if (this.f >= 110) {
            this.f = 0;
         }
      }

      this.et();
      super.j();
      if (!this.l.v && this.bp != null && !this.bp.a(this)) {
         this.m();
         this.bp = this.bo;
      }

      this.p();
      if (!this.l.v) {
         this.bq.a(this);
         this.a(aea.k);
         if (this.aX()) {
            this.a(aea.l);
         }

         if (this.bx()) {
            this.a(aea.n);
         }

         if (!this.em()) {
            this.a(aea.m);
         }
      }

      int _snowman = 29999999;
      double _snowmanx = afm.a(this.cD(), -2.9999999E7, 2.9999999E7);
      double _snowmanxx = afm.a(this.cH(), -2.9999999E7, 2.9999999E7);
      if (_snowmanx != this.cD() || _snowmanxx != this.cH()) {
         this.d(_snowmanx, this.cE(), _snowmanxx);
      }

      this.at++;
      bmb _snowmanxxx = this.dD();
      if (!bmb.b(this.bL, _snowmanxxx)) {
         if (!bmb.d(this.bL, _snowmanxxx)) {
            this.eS();
         }

         this.bL = _snowmanxxx.i();
      }

      this.o();
      this.bM.a();
      this.eu();
   }

   public boolean eq() {
      return this.bu();
   }

   protected boolean er() {
      return this.bu();
   }

   protected boolean es() {
      return this.bu();
   }

   protected boolean et() {
      this.bB = this.a(aef.b);
      return this.bB;
   }

   private void o() {
      bmb _snowman = this.b(aqf.f);
      if (_snowman.b() == bmd.jY && !this.a(aef.b)) {
         this.c(new apu(apw.m, 200, 0, false, false, true));
      }
   }

   protected bly i() {
      return new bly();
   }

   private void p() {
      this.bv = this.by;
      this.bw = this.bz;
      this.bx = this.bA;
      double _snowman = this.cD() - this.by;
      double _snowmanx = this.cE() - this.bz;
      double _snowmanxx = this.cH() - this.bA;
      double _snowmanxxx = 10.0;
      if (_snowman > 10.0) {
         this.by = this.cD();
         this.bv = this.by;
      }

      if (_snowmanxx > 10.0) {
         this.bA = this.cH();
         this.bx = this.bA;
      }

      if (_snowmanx > 10.0) {
         this.bz = this.cE();
         this.bw = this.bz;
      }

      if (_snowman < -10.0) {
         this.by = this.cD();
         this.bv = this.by;
      }

      if (_snowmanxx < -10.0) {
         this.bA = this.cH();
         this.bx = this.bA;
      }

      if (_snowmanx < -10.0) {
         this.bz = this.cE();
         this.bw = this.bz;
      }

      this.by += _snowman * 0.25;
      this.bA += _snowmanxx * 0.25;
      this.bz += _snowmanx * 0.25;
   }

   protected void eu() {
      if (this.c(aqx.d)) {
         aqx _snowman;
         if (this.ef()) {
            _snowman = aqx.b;
         } else if (this.em()) {
            _snowman = aqx.c;
         } else if (this.bB()) {
            _snowman = aqx.d;
         } else if (this.dR()) {
            _snowman = aqx.e;
         } else if (this.bu() && !this.bC.b) {
            _snowman = aqx.f;
         } else {
            _snowman = aqx.a;
         }

         aqx _snowmanx;
         if (this.a_() || this.br() || this.c(_snowman)) {
            _snowmanx = _snowman;
         } else if (this.c(aqx.f)) {
            _snowmanx = aqx.f;
         } else {
            _snowmanx = aqx.d;
         }

         this.b(_snowmanx);
      }
   }

   @Override
   public int aj() {
      return this.bC.a ? 1 : 80;
   }

   @Override
   protected adp av() {
      return adq.lG;
   }

   @Override
   protected adp aw() {
      return adq.lE;
   }

   @Override
   protected adp ax() {
      return adq.lF;
   }

   @Override
   public int bl() {
      return 10;
   }

   @Override
   public void a(adp var1, float var2, float var3) {
      this.l.a(this, this.cD(), this.cE(), this.cH(), _snowman, this.cu(), _snowman, _snowman);
   }

   public void a(adp var1, adr var2, float var3, float var4) {
   }

   @Override
   public adr cu() {
      return adr.h;
   }

   @Override
   protected int cv() {
      return 20;
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 9) {
         this.s();
      } else if (_snowman == 23) {
         this.bK = false;
      } else if (_snowman == 22) {
         this.bK = true;
      } else if (_snowman == 43) {
         this.a(hh.f);
      } else {
         super.a(_snowman);
      }
   }

   private void a(hf var1) {
      for (int _snowman = 0; _snowman < 5; _snowman++) {
         double _snowmanx = this.J.nextGaussian() * 0.02;
         double _snowmanxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxx = this.J.nextGaussian() * 0.02;
         this.l.a(_snowman, this.d(1.0), this.cF() + 1.0, this.g(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   protected void m() {
      this.bp = this.bo;
   }

   @Override
   public void ba() {
      if (this.er() && this.br()) {
         this.l();
         this.f(false);
      } else {
         double _snowman = this.cD();
         double _snowmanx = this.cE();
         double _snowmanxx = this.cH();
         super.ba();
         this.bs = this.bt;
         this.bt = 0.0F;
         this.q(this.cD() - _snowman, this.cE() - _snowmanx, this.cH() - _snowmanxx);
      }
   }

   @Override
   public void ac() {
      this.b(aqx.a);
      super.ac();
      this.c(this.dx());
      this.aq = 0;
   }

   @Override
   protected void dP() {
      super.dP();
      this.dA();
      this.aC = this.p;
   }

   @Override
   public void k() {
      if (this.br > 0) {
         this.br--;
      }

      if (this.l.ad() == aor.a && this.l.V().b(brt.i)) {
         if (this.dk() < this.dx() && this.K % 20 == 0) {
            this.b(1.0F);
         }

         if (this.bq.c() && this.K % 10 == 0) {
            this.bq.a(this.bq.a() + 1);
         }
      }

      this.bm.j();
      this.bs = this.bt;
      super.k();
      this.aE = 0.02F;
      if (this.bA()) {
         this.aE = (float)((double)this.aE + 0.005999999865889549);
      }

      this.q((float)this.b(arl.d));
      float _snowman;
      if (this.t && !this.dl() && !this.bB()) {
         _snowman = Math.min(0.1F, afm.a(c(this.cC())));
      } else {
         _snowman = 0.0F;
      }

      this.bt = this.bt + (_snowman - this.bt) * 0.4F;
      if (this.dk() > 0.0F && !this.a_()) {
         dci _snowmanx;
         if (this.br() && !this.ct().y) {
            _snowmanx = this.cc().b(this.ct().cc()).c(1.0, 0.0, 1.0);
         } else {
            _snowmanx = this.cc().c(1.0, 0.5, 1.0);
         }

         List<aqa> _snowmanxx = this.l.a(this, _snowmanx);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            aqa _snowmanxxxx = _snowmanxx.get(_snowmanxxx);
            if (!_snowmanxxxx.y) {
               this.c(_snowmanxxxx);
            }
         }
      }

      this.j(this.eP());
      this.j(this.eQ());
      if (!this.l.v && (this.C > 0.5F || this.aE()) || this.bC.b || this.em()) {
         this.eM();
      }
   }

   private void j(@Nullable md var1) {
      if (_snowman != null && (!_snowman.e("Silent") || !_snowman.q("Silent")) && this.l.t.nextInt(200) == 0) {
         String _snowman = _snowman.l("id");
         aqe.a(_snowman).filter(var0 -> var0 == aqe.af).ifPresent(var1x -> {
            if (!bam.a(this.l, this)) {
               this.l.a(null, this.cD(), this.cE(), this.cH(), bam.a(this.l, this.l.t), this.cu(), 1.0F, bam.a(this.l.t));
            }
         });
      }
   }

   private void c(aqa var1) {
      _snowman.a_(this);
   }

   public int ev() {
      return this.R.a(d);
   }

   public void s(int var1) {
      this.R.b(d, _snowman);
   }

   public void t(int var1) {
      int _snowman = this.ev();
      this.R.b(d, _snowman + _snowman);
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
      this.af();
      if (!this.a_()) {
         this.d(_snowman);
      }

      if (_snowman != null) {
         this.n(
            (double)(-afm.b((this.ap + this.p) * (float) (Math.PI / 180.0)) * 0.1F),
            0.1F,
            (double)(-afm.a((this.ap + this.p) * (float) (Math.PI / 180.0)) * 0.1F)
         );
      } else {
         this.n(0.0, 0.1, 0.0);
      }

      this.a(aea.M);
      this.a(aea.i.b(aea.l));
      this.a(aea.i.b(aea.m));
      this.am();
      this.b(0, false);
   }

   @Override
   protected void dn() {
      super.dn();
      if (!this.l.V().b(brt.c)) {
         this.ew();
         this.bm.k();
      }
   }

   protected void ew() {
      for (int _snowman = 0; _snowman < this.bm.Z_(); _snowman++) {
         bmb _snowmanx = this.bm.a(_snowman);
         if (!_snowmanx.a() && bpu.e(_snowmanx)) {
            this.bm.b(_snowman);
         }
      }
   }

   @Override
   protected adp e(apk var1) {
      if (_snowman == apk.c) {
         return adq.lA;
      } else if (_snowman == apk.h) {
         return adq.lz;
      } else {
         return _snowman == apk.u ? adq.lB : adq.ly;
      }
   }

   @Override
   protected adp dq() {
      return adq.lx;
   }

   public boolean a(boolean var1) {
      return this.a(this.bm.a(this.bm.d, _snowman && !this.bm.f().a() ? this.bm.f().E() : 1), false, true) != null;
   }

   @Nullable
   public bcv a(bmb var1, boolean var2) {
      return this.a(_snowman, false, _snowman);
   }

   @Nullable
   public bcv a(bmb var1, boolean var2, boolean var3) {
      if (_snowman.a()) {
         return null;
      } else {
         if (this.l.v) {
            this.a(aot.a);
         }

         double _snowman = this.cG() - 0.3F;
         bcv _snowmanx = new bcv(this.l, this.cD(), _snowman, this.cH(), _snowman);
         _snowmanx.a(40);
         if (_snowman) {
            _snowmanx.c(this.bS());
         }

         if (_snowman) {
            float _snowmanxx = this.J.nextFloat() * 0.5F;
            float _snowmanxxx = this.J.nextFloat() * (float) (Math.PI * 2);
            _snowmanx.n((double)(-afm.a(_snowmanxxx) * _snowmanxx), 0.2F, (double)(afm.b(_snowmanxxx) * _snowmanxx));
         } else {
            float _snowmanxx = 0.3F;
            float _snowmanxxx = afm.a(this.q * (float) (Math.PI / 180.0));
            float _snowmanxxxx = afm.b(this.q * (float) (Math.PI / 180.0));
            float _snowmanxxxxx = afm.a(this.p * (float) (Math.PI / 180.0));
            float _snowmanxxxxxx = afm.b(this.p * (float) (Math.PI / 180.0));
            float _snowmanxxxxxxx = this.J.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxxxxxx = 0.02F * this.J.nextFloat();
            _snowmanx.n(
               (double)(-_snowmanxxxxx * _snowmanxxxx * 0.3F) + Math.cos((double)_snowmanxxxxxxx) * (double)_snowmanxxxxxxxx,
               (double)(-_snowmanxxx * 0.3F + 0.1F + (this.J.nextFloat() - this.J.nextFloat()) * 0.1F),
               (double)(_snowmanxxxxxx * _snowmanxxxx * 0.3F) + Math.sin((double)_snowmanxxxxxxx) * (double)_snowmanxxxxxxxx
            );
         }

         return _snowmanx;
      }
   }

   public float c(ceh var1) {
      float _snowman = this.bm.a(_snowman);
      if (_snowman > 1.0F) {
         int _snowmanx = bpu.f(this);
         bmb _snowmanxx = this.dD();
         if (_snowmanx > 0 && !_snowmanxx.a()) {
            _snowman += (float)(_snowmanx * _snowmanx + 1);
         }
      }

      if (apv.a(this)) {
         _snowman *= 1.0F + (float)(apv.b(this) + 1) * 0.2F;
      }

      if (this.a(apw.d)) {
         float _snowmanx;
         switch (this.b(apw.d).c()) {
            case 0:
               _snowmanx = 0.3F;
               break;
            case 1:
               _snowmanx = 0.09F;
               break;
            case 2:
               _snowmanx = 0.0027F;
               break;
            case 3:
            default:
               _snowmanx = 8.1E-4F;
         }

         _snowman *= _snowmanx;
      }

      if (this.a(aef.b) && !bpu.h(this)) {
         _snowman /= 5.0F;
      }

      if (!this.t) {
         _snowman /= 5.0F;
      }

      return _snowman;
   }

   public boolean d(ceh var1) {
      return !_snowman.q() || this.bm.f().b(_snowman);
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.a_(a(this.bJ));
      mj _snowman = _snowman.d("Inventory", 10);
      this.bm.b(_snowman);
      this.bm.d = _snowman.h("SelectedItemSlot");
      this.f = _snowman.g("SleepTimer");
      this.bF = _snowman.j("XpP");
      this.bD = _snowman.h("XpLevel");
      this.bE = _snowman.h("XpTotal");
      this.bG = _snowman.h("XpSeed");
      if (this.bG == 0) {
         this.bG = this.J.nextInt();
      }

      this.s(_snowman.h("Score"));
      this.bq.a(_snowman);
      this.bC.b(_snowman);
      this.a(arl.d).a((double)this.bC.b());
      if (_snowman.c("EnderItems", 9)) {
         this.bn.a(_snowman.d("EnderItems", 10));
      }

      if (_snowman.c("ShoulderEntityLeft", 10)) {
         this.h(_snowman.p("ShoulderEntityLeft"));
      }

      if (_snowman.c("ShoulderEntityRight", 10)) {
         this.i(_snowman.p("ShoulderEntityRight"));
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("DataVersion", w.a().getWorldVersion());
      _snowman.a("Inventory", this.bm.a(new mj()));
      _snowman.b("SelectedItemSlot", this.bm.d);
      _snowman.a("SleepTimer", (short)this.f);
      _snowman.a("XpP", this.bF);
      _snowman.b("XpLevel", this.bD);
      _snowman.b("XpTotal", this.bE);
      _snowman.b("XpSeed", this.bG);
      _snowman.b("Score", this.ev());
      this.bq.b(_snowman);
      this.bC.a(_snowman);
      _snowman.a("EnderItems", this.bn.g());
      if (!this.eP().isEmpty()) {
         _snowman.a("ShoulderEntityLeft", this.eP());
      }

      if (!this.eQ().isEmpty()) {
         _snowman.a("ShoulderEntityRight", this.eQ());
      }
   }

   @Override
   public boolean b(apk var1) {
      if (super.b(_snowman)) {
         return true;
      } else if (_snowman == apk.h) {
         return !this.l.V().b(brt.A);
      } else if (_snowman == apk.k) {
         return !this.l.V().b(brt.B);
      } else {
         return _snowman.p() ? !this.l.V().b(brt.C) : false;
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (this.bC.a && !_snowman.h()) {
         return false;
      } else {
         this.aI = 0;
         if (this.dl()) {
            return false;
         } else {
            this.eM();
            if (_snowman.s()) {
               if (this.l.ad() == aor.a) {
                  _snowman = 0.0F;
               }

               if (this.l.ad() == aor.b) {
                  _snowman = Math.min(_snowman / 2.0F + 1.0F, _snowman);
               }

               if (this.l.ad() == aor.d) {
                  _snowman = _snowman * 3.0F / 2.0F;
               }
            }

            return _snowman == 0.0F ? false : super.a(_snowman, _snowman);
         }
      }
   }

   @Override
   protected void d(aqm var1) {
      super.d(_snowman);
      if (_snowman.dD().b() instanceof bkd) {
         this.p(true);
      }
   }

   public boolean a(bfw var1) {
      ddp _snowman = this.bG();
      ddp _snowmanx = _snowman.bG();
      if (_snowman == null) {
         return true;
      } else {
         return !_snowman.a(_snowmanx) ? true : _snowman.h();
      }
   }

   @Override
   protected void b(apk var1, float var2) {
      this.bm.a(_snowman, _snowman);
   }

   @Override
   protected void p(float var1) {
      if (this.bc.b() == bmd.qn) {
         if (!this.l.v) {
            this.b(aea.c.b(this.bc.b()));
         }

         if (_snowman >= 3.0F) {
            int _snowman = 1 + afm.d(_snowman);
            aot _snowmanx = this.dX();
            this.bc.a(_snowman, this, var1x -> var1x.d(_snowman));
            if (this.bc.a()) {
               if (_snowmanx == aot.a) {
                  this.a(aqf.a, bmb.b);
               } else {
                  this.a(aqf.b, bmb.b);
               }

               this.bc = bmb.b;
               this.a(adq.mV, 0.8F, 0.8F + this.l.t.nextFloat() * 0.4F);
            }
         }
      }
   }

   @Override
   protected void e(apk var1, float var2) {
      if (!this.b(_snowman)) {
         _snowman = this.c(_snowman, _snowman);
         _snowman = this.d(_snowman, _snowman);
         float var8 = Math.max(_snowman - this.dT(), 0.0F);
         this.s(this.dT() - (_snowman - var8));
         float _snowman = _snowman - var8;
         if (_snowman > 0.0F && _snowman < 3.4028235E37F) {
            this.a(aea.K, Math.round(_snowman * 10.0F));
         }

         if (var8 != 0.0F) {
            this.t(_snowman.g());
            float _snowmanx = this.dk();
            this.c(this.dk() - var8);
            this.dv().a(_snowman, _snowmanx, var8);
            if (var8 < 3.4028235E37F) {
               this.a(aea.I, Math.round(var8 * 10.0F));
            }
         }
      }
   }

   @Override
   protected boolean cP() {
      return !this.bC.b && super.cP();
   }

   public void a(cdf var1) {
   }

   public void a(bqy var1) {
   }

   public void a(cco var1) {
   }

   public void a(cdj var1) {
   }

   public void a(ccz var1) {
   }

   public void a(bbb var1, aon var2) {
   }

   public OptionalInt a(@Nullable aox var1) {
      return OptionalInt.empty();
   }

   public void a(int var1, bqw var2, int var3, int var4, boolean var5, boolean var6) {
   }

   public void a(bmb var1, aot var2) {
   }

   public aou a(aqa var1, aot var2) {
      if (this.a_()) {
         if (_snowman instanceof aox) {
            this.a((aox)_snowman);
         }

         return aou.c;
      } else {
         bmb _snowman = this.b(_snowman);
         bmb _snowmanx = _snowman.i();
         aou _snowmanxx = _snowman.a(this, _snowman);
         if (_snowmanxx.a()) {
            if (this.bC.d && _snowman == this.b(_snowman) && _snowman.E() < _snowmanx.E()) {
               _snowman.e(_snowmanx.E());
            }

            return _snowmanxx;
         } else {
            if (!_snowman.a() && _snowman instanceof aqm) {
               if (this.bC.d) {
                  _snowman = _snowmanx;
               }

               aou _snowmanxxx = _snowman.a(this, (aqm)_snowman, _snowman);
               if (_snowmanxxx.a()) {
                  if (_snowman.a() && !this.bC.d) {
                     this.a(_snowman, bmb.b);
                  }

                  return _snowmanxxx;
               }
            }

            return aou.c;
         }
      }
   }

   @Override
   public double bb() {
      return -0.35;
   }

   @Override
   public void bf() {
      super.bf();
      this.j = 0;
   }

   @Override
   protected boolean dI() {
      return super.dI() || this.em();
   }

   @Override
   public boolean cT() {
      return !this.bC.b;
   }

   @Override
   protected dcn a(dcn var1, aqr var2) {
      if (!this.bC.b && (_snowman == aqr.a || _snowman == aqr.b) && this.es() && this.q()) {
         double _snowman = _snowman.b;
         double _snowmanx = _snowman.d;
         double _snowmanxx = 0.05;

         while (_snowman != 0.0 && this.l.a_(this, this.cc().d(_snowman, (double)(-this.G), 0.0))) {
            if (_snowman < 0.05 && _snowman >= -0.05) {
               _snowman = 0.0;
            } else if (_snowman > 0.0) {
               _snowman -= 0.05;
            } else {
               _snowman += 0.05;
            }
         }

         while (_snowmanx != 0.0 && this.l.a_(this, this.cc().d(0.0, (double)(-this.G), _snowmanx))) {
            if (_snowmanx < 0.05 && _snowmanx >= -0.05) {
               _snowmanx = 0.0;
            } else if (_snowmanx > 0.0) {
               _snowmanx -= 0.05;
            } else {
               _snowmanx += 0.05;
            }
         }

         while (_snowman != 0.0 && _snowmanx != 0.0 && this.l.a_(this, this.cc().d(_snowman, (double)(-this.G), _snowmanx))) {
            if (_snowman < 0.05 && _snowman >= -0.05) {
               _snowman = 0.0;
            } else if (_snowman > 0.0) {
               _snowman -= 0.05;
            } else {
               _snowman += 0.05;
            }

            if (_snowmanx < 0.05 && _snowmanx >= -0.05) {
               _snowmanx = 0.0;
            } else if (_snowmanx > 0.0) {
               _snowmanx -= 0.05;
            } else {
               _snowmanx += 0.05;
            }
         }

         _snowman = new dcn(_snowman, _snowman.c, _snowmanx);
      }

      return _snowman;
   }

   private boolean q() {
      return this.t || this.C < this.G && !this.l.a_(this, this.cc().d(0.0, (double)(this.C - this.G), 0.0));
   }

   public void f(aqa var1) {
      if (_snowman.bL()) {
         if (!_snowman.t(this)) {
            float _snowman = (float)this.b(arl.f);
            float _snowmanx;
            if (_snowman instanceof aqm) {
               _snowmanx = bpu.a(this.dD(), ((aqm)_snowman).dC());
            } else {
               _snowmanx = bpu.a(this.dD(), aqq.a);
            }

            float _snowmanxx = this.u(0.5F);
            _snowman *= 0.2F + _snowmanxx * _snowmanxx * 0.8F;
            _snowmanx *= _snowmanxx;
            this.eS();
            if (_snowman > 0.0F || _snowmanx > 0.0F) {
               boolean _snowmanxxx = _snowmanxx > 0.9F;
               boolean _snowmanxxxx = false;
               int _snowmanxxxxx = 0;
               _snowmanxxxxx += bpu.b(this);
               if (this.bA() && _snowmanxxx) {
                  this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lp, this.cu(), 1.0F, 1.0F);
                  _snowmanxxxxx++;
                  _snowmanxxxx = true;
               }

               boolean _snowmanxxxxxx = _snowmanxxx && this.C > 0.0F && !this.t && !this.c_() && !this.aE() && !this.a(apw.o) && !this.br() && _snowman instanceof aqm;
               _snowmanxxxxxx = _snowmanxxxxxx && !this.bA();
               if (_snowmanxxxxxx) {
                  _snowman *= 1.5F;
               }

               _snowman += _snowmanx;
               boolean _snowmanxxxxxxx = false;
               double _snowmanxxxxxxxx = (double)(this.A - this.z);
               if (_snowmanxxx && !_snowmanxxxxxx && !_snowmanxxxx && this.t && _snowmanxxxxxxxx < (double)this.dN()) {
                  bmb _snowmanxxxxxxxxx = this.b(aot.a);
                  if (_snowmanxxxxxxxxx.b() instanceof bnf) {
                     _snowmanxxxxxxx = true;
                  }
               }

               float _snowmanxxxxxxxxx = 0.0F;
               boolean _snowmanxxxxxxxxxx = false;
               int _snowmanxxxxxxxxxxx = bpu.c(this);
               if (_snowman instanceof aqm) {
                  _snowmanxxxxxxxxx = ((aqm)_snowman).dk();
                  if (_snowmanxxxxxxxxxxx > 0 && !_snowman.bq()) {
                     _snowmanxxxxxxxxxx = true;
                     _snowman.f(1);
                  }
               }

               dcn _snowmanxxxxxxxxxxxx = _snowman.cC();
               boolean _snowmanxxxxxxxxxxxxx = _snowman.a(apk.a(this), _snowman);
               if (_snowmanxxxxxxxxxxxxx) {
                  if (_snowmanxxxxx > 0) {
                     if (_snowman instanceof aqm) {
                        ((aqm)_snowman)
                           .a((float)_snowmanxxxxx * 0.5F, (double)afm.a(this.p * (float) (Math.PI / 180.0)), (double)(-afm.b(this.p * (float) (Math.PI / 180.0))));
                     } else {
                        _snowman.i(
                           (double)(-afm.a(this.p * (float) (Math.PI / 180.0)) * (float)_snowmanxxxxx * 0.5F),
                           0.1,
                           (double)(afm.b(this.p * (float) (Math.PI / 180.0)) * (float)_snowmanxxxxx * 0.5F)
                        );
                     }

                     this.f(this.cC().d(0.6, 1.0, 0.6));
                     this.g(false);
                  }

                  if (_snowmanxxxxxxx) {
                     float _snowmanxxxxxxxxxxxxxx = 1.0F + bpu.a(this) * _snowman;

                     for (aqm _snowmanxxxxxxxxxxxxxxx : this.l.a(aqm.class, _snowman.cc().c(1.0, 0.25, 1.0))) {
                        if (_snowmanxxxxxxxxxxxxxxx != this
                           && _snowmanxxxxxxxxxxxxxxx != _snowman
                           && !this.r(_snowmanxxxxxxxxxxxxxxx)
                           && (!(_snowmanxxxxxxxxxxxxxxx instanceof bcn) || !((bcn)_snowmanxxxxxxxxxxxxxxx).q())
                           && this.h(_snowmanxxxxxxxxxxxxxxx) < 9.0) {
                           _snowmanxxxxxxxxxxxxxxx.a(0.4F, (double)afm.a(this.p * (float) (Math.PI / 180.0)), (double)(-afm.b(this.p * (float) (Math.PI / 180.0))));
                           _snowmanxxxxxxxxxxxxxxx.a(apk.a(this), _snowmanxxxxxxxxxxxxxx);
                        }
                     }

                     this.l.a(null, this.cD(), this.cE(), this.cH(), adq.ls, this.cu(), 1.0F, 1.0F);
                     this.ex();
                  }

                  if (_snowman instanceof aah && _snowman.w) {
                     ((aah)_snowman).b.a(new rc(_snowman));
                     _snowman.w = false;
                     _snowman.f(_snowmanxxxxxxxxxxxx);
                  }

                  if (_snowmanxxxxxx) {
                     this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lo, this.cu(), 1.0F, 1.0F);
                     this.a(_snowman);
                  }

                  if (!_snowmanxxxxxx && !_snowmanxxxxxxx) {
                     if (_snowmanxxx) {
                        this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lr, this.cu(), 1.0F, 1.0F);
                     } else {
                        this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lt, this.cu(), 1.0F, 1.0F);
                     }
                  }

                  if (_snowmanx > 0.0F) {
                     this.b(_snowman);
                  }

                  this.z(_snowman);
                  if (_snowman instanceof aqm) {
                     bpu.a((aqm)_snowman, this);
                  }

                  bpu.b(this, _snowman);
                  bmb _snowmanxxxxxxxxxxxxxx = this.dD();
                  aqa _snowmanxxxxxxxxxxxxxxxx = _snowman;
                  if (_snowman instanceof bbp) {
                     _snowmanxxxxxxxxxxxxxxxx = ((bbp)_snowman).b;
                  }

                  if (!this.l.v && !_snowmanxxxxxxxxxxxxxx.a() && _snowmanxxxxxxxxxxxxxxxx instanceof aqm) {
                     _snowmanxxxxxxxxxxxxxx.a((aqm)_snowmanxxxxxxxxxxxxxxxx, this);
                     if (_snowmanxxxxxxxxxxxxxx.a()) {
                        this.a(aot.a, bmb.b);
                     }
                  }

                  if (_snowman instanceof aqm) {
                     float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx - ((aqm)_snowman).dk();
                     this.a(aea.F, Math.round(_snowmanxxxxxxxxxxxxxxxxx * 10.0F));
                     if (_snowmanxxxxxxxxxxx > 0) {
                        _snowman.f(_snowmanxxxxxxxxxxx * 4);
                     }

                     if (this.l instanceof aag && _snowmanxxxxxxxxxxxxxxxxx > 2.0F) {
                        int _snowmanxxxxxxxxxxxxxxxxxx = (int)((double)_snowmanxxxxxxxxxxxxxxxxx * 0.5);
                        ((aag)this.l).a(hh.h, _snowman.cD(), _snowman.e(0.5), _snowman.cH(), _snowmanxxxxxxxxxxxxxxxxxx, 0.1, 0.0, 0.1, 0.2);
                     }
                  }

                  this.t(0.1F);
               } else {
                  this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lq, this.cu(), 1.0F, 1.0F);
                  if (_snowmanxxxxxxxxxx) {
                     _snowman.am();
                  }
               }
            }
         }
      }
   }

   @Override
   protected void g(aqm var1) {
      this.f(_snowman);
   }

   public void p(boolean var1) {
      float _snowman = 0.25F + (float)bpu.f(this) * 0.05F;
      if (_snowman) {
         _snowman += 0.75F;
      }

      if (this.J.nextFloat() < _snowman) {
         this.eT().a(bmd.qn, 100);
         this.ec();
         this.l.a(this, (byte)30);
      }
   }

   @Override
   public void a(aqa var1) {
   }

   public void b(aqa var1) {
   }

   public void ex() {
      double _snowman = (double)(-afm.a(this.p * (float) (Math.PI / 180.0)));
      double _snowmanx = (double)afm.b(this.p * (float) (Math.PI / 180.0));
      if (this.l instanceof aag) {
         ((aag)this.l).a(hh.W, this.cD() + _snowman, this.e(0.5), this.cH() + _snowmanx, 0, _snowman, 0.0, _snowmanx, 0.0);
      }
   }

   public void ey() {
   }

   @Override
   public void ad() {
      super.ad();
      this.bo.b(this);
      if (this.bp != null) {
         this.bp.b(this);
      }
   }

   public boolean ez() {
      return false;
   }

   public GameProfile eA() {
      return this.bJ;
   }

   public Either<bfw.a, afx> a(fx var1) {
      this.b(_snowman);
      this.f = 0;
      return Either.right(afx.a);
   }

   public void a(boolean var1, boolean var2) {
      super.en();
      if (this.l instanceof aag && _snowman) {
         ((aag)this.l).n_();
      }

      this.f = _snowman ? 0 : 100;
   }

   @Override
   public void en() {
      this.a(true, true);
   }

   public static Optional<dcn> a(aag var0, fx var1, float var2, boolean var3, boolean var4) {
      ceh _snowman = _snowman.d_(_snowman);
      buo _snowmanx = _snowman.b();
      if (_snowmanx instanceof bzj && _snowman.c(bzj.a) > 0 && bzj.a(_snowman)) {
         Optional<dcn> _snowmanxx = bzj.a(aqe.bc, _snowman, _snowman);
         if (!_snowman && _snowmanxx.isPresent()) {
            _snowman.a(_snowman, _snowman.a(bzj.a, Integer.valueOf(_snowman.c(bzj.a) - 1)), 3);
         }

         return _snowmanxx;
      } else if (_snowmanx instanceof buj && buj.a((brx)_snowman)) {
         return buj.a(aqe.bc, _snowman, _snowman, _snowman);
      } else if (!_snowman) {
         return Optional.empty();
      } else {
         boolean _snowmanxx = _snowmanx.ai_();
         boolean _snowmanxxx = _snowman.d_(_snowman.b()).b().ai_();
         return _snowmanxx && _snowmanxxx ? Optional.of(new dcn((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.1, (double)_snowman.w() + 0.5)) : Optional.empty();
      }
   }

   public boolean eB() {
      return this.em() && this.f >= 100;
   }

   public int eC() {
      return this.f;
   }

   public void a(nr var1, boolean var2) {
   }

   public void a(vk var1) {
      this.b(aea.i.b(_snowman));
   }

   public void a(vk var1, int var2) {
      this.a(aea.i.b(_snowman), _snowman);
   }

   public void b(adx<?> var1) {
      this.a(_snowman, 1);
   }

   public void a(adx<?> var1, int var2) {
   }

   public void a(adx<?> var1) {
   }

   public int a(Collection<boq<?>> var1) {
      return 0;
   }

   public void a(vk[] var1) {
   }

   public int b(Collection<boq<?>> var1) {
      return 0;
   }

   @Override
   public void dK() {
      super.dK();
      this.a(aea.D);
      if (this.bA()) {
         this.t(0.2F);
      } else {
         this.t(0.05F);
      }
   }

   @Override
   public void g(dcn var1) {
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      if (this.bB() && !this.br()) {
         double _snowmanxxx = this.bh().c;
         double _snowmanxxxx = _snowmanxxx < -0.2 ? 0.085 : 0.06;
         if (_snowmanxxx <= 0.0 || this.aQ || !this.l.d_(new fx(this.cD(), this.cE() + 1.0 - 0.1, this.cH())).m().c()) {
            dcn _snowmanxxxxx = this.cC();
            this.f(_snowmanxxxxx.b(0.0, (_snowmanxxx - _snowmanxxxxx.c) * _snowmanxxxx, 0.0));
         }
      }

      if (this.bC.b && !this.br()) {
         double _snowmanxxx = this.cC().c;
         float _snowmanxxxx = this.aE;
         this.aE = this.bC.a() * (float)(this.bA() ? 2 : 1);
         super.g(_snowman);
         dcn _snowmanxxxxx = this.cC();
         this.n(_snowmanxxxxx.b, _snowmanxxx * 0.6, _snowmanxxxxx.d);
         this.aE = _snowmanxxxx;
         this.C = 0.0F;
         this.b(7, false);
      } else {
         super.g(_snowman);
      }

      this.p(this.cD() - _snowman, this.cE() - _snowmanx, this.cH() - _snowmanxx);
   }

   @Override
   public void aJ() {
      if (this.bC.b) {
         this.h(false);
      } else {
         super.aJ();
      }
   }

   protected boolean f(fx var1) {
      return !this.l.d_(_snowman).o(this.l, _snowman);
   }

   @Override
   public float dN() {
      return (float)this.b(arl.d);
   }

   public void p(double var1, double var3, double var5) {
      if (!this.br()) {
         if (this.bB()) {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman) * 100.0F);
            if (_snowman > 0) {
               this.a(aea.B, _snowman);
               this.t(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.a(aef.b)) {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman) * 100.0F);
            if (_snowman > 0) {
               this.a(aea.v, _snowman);
               this.t(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.aE()) {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman) * 100.0F);
            if (_snowman > 0) {
               this.a(aea.r, _snowman);
               this.t(0.01F * (float)_snowman * 0.01F);
            }
         } else if (this.c_()) {
            if (_snowman > 0.0) {
               this.a(aea.t, (int)Math.round(_snowman * 100.0));
            }
         } else if (this.t) {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman) * 100.0F);
            if (_snowman > 0) {
               if (this.bA()) {
                  this.a(aea.q, _snowman);
                  this.t(0.1F * (float)_snowman * 0.01F);
               } else if (this.bz()) {
                  this.a(aea.p, _snowman);
                  this.t(0.0F * (float)_snowman * 0.01F);
               } else {
                  this.a(aea.o, _snowman);
                  this.t(0.0F * (float)_snowman * 0.01F);
               }
            }
         } else if (this.ef()) {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman) * 100.0F);
            this.a(aea.A, _snowman);
         } else {
            int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman) * 100.0F);
            if (_snowman > 25) {
               this.a(aea.u, _snowman);
            }
         }
      }
   }

   private void q(double var1, double var3, double var5) {
      if (this.br()) {
         int _snowman = Math.round(afm.a(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman) * 100.0F);
         if (_snowman > 0) {
            aqa _snowmanx = this.ct();
            if (_snowmanx instanceof bhl) {
               this.a(aea.w, _snowman);
            } else if (_snowmanx instanceof bhn) {
               this.a(aea.x, _snowman);
            } else if (_snowmanx instanceof ban) {
               this.a(aea.y, _snowman);
            } else if (_snowmanx instanceof bbb) {
               this.a(aea.z, _snowman);
            } else if (_snowmanx instanceof bed) {
               this.a(aea.C, _snowman);
            }
         }
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      if (this.bC.c) {
         return false;
      } else {
         if (_snowman >= 2.0F) {
            this.a(aea.s, (int)Math.round((double)_snowman * 100.0));
         }

         return super.b(_snowman, _snowman);
      }
   }

   public boolean eD() {
      if (!this.t && !this.ef() && !this.aE() && !this.a(apw.y)) {
         bmb _snowman = this.b(aqf.e);
         if (_snowman.b() == bmd.qo && bld.d(_snowman)) {
            this.eE();
            return true;
         }
      }

      return false;
   }

   public void eE() {
      this.b(7, true);
   }

   public void eF() {
      this.b(7, true);
      this.b(7, false);
   }

   @Override
   protected void aM() {
      if (!this.a_()) {
         super.aM();
      }
   }

   @Override
   protected adp o(int var1) {
      return _snowman > 4 ? adq.lu : adq.lD;
   }

   @Override
   public void a(aag var1, aqm var2) {
      this.b(aea.g.b(_snowman.X()));
   }

   @Override
   public void a(ceh var1, dcn var2) {
      if (!this.bC.b) {
         super.a(_snowman, _snowman);
      }
   }

   public void d(int var1) {
      this.t(_snowman);
      this.bF = this.bF + (float)_snowman / (float)this.eH();
      this.bE = afm.a(this.bE + _snowman, 0, Integer.MAX_VALUE);

      while (this.bF < 0.0F) {
         float _snowman = this.bF * (float)this.eH();
         if (this.bD > 0) {
            this.c(-1);
            this.bF = 1.0F + _snowman / (float)this.eH();
         } else {
            this.c(-1);
            this.bF = 0.0F;
         }
      }

      while (this.bF >= 1.0F) {
         this.bF = (this.bF - 1.0F) * (float)this.eH();
         this.c(1);
         this.bF = this.bF / (float)this.eH();
      }
   }

   public int eG() {
      return this.bG;
   }

   @Override
   public void a(bmb var1, int var2) {
      this.bD -= _snowman;
      if (this.bD < 0) {
         this.bD = 0;
         this.bF = 0.0F;
         this.bE = 0;
      }

      this.bG = this.J.nextInt();
   }

   public void c(int var1) {
      this.bD += _snowman;
      if (this.bD < 0) {
         this.bD = 0;
         this.bF = 0.0F;
         this.bE = 0;
      }

      if (_snowman > 0 && this.bD % 5 == 0 && (float)this.g < (float)this.K - 100.0F) {
         float _snowman = this.bD > 30 ? 1.0F : (float)this.bD / 30.0F;
         this.l.a(null, this.cD(), this.cE(), this.cH(), adq.lC, this.cu(), _snowman * 0.75F, 1.0F);
         this.g = this.K;
      }
   }

   public int eH() {
      if (this.bD >= 30) {
         return 112 + (this.bD - 30) * 9;
      } else {
         return this.bD >= 15 ? 37 + (this.bD - 15) * 5 : 7 + this.bD * 2;
      }
   }

   public void t(float var1) {
      if (!this.bC.a) {
         if (!this.l.v) {
            this.bq.a(_snowman);
         }
      }
   }

   public bhy eI() {
      return this.bq;
   }

   public boolean q(boolean var1) {
      return this.bC.a || _snowman || this.bq.c();
   }

   public boolean eJ() {
      return this.dk() > 0.0F && this.dk() < this.dx();
   }

   public boolean eK() {
      return this.bC.e;
   }

   public boolean a(fx var1, gc var2, bmb var3) {
      if (this.bC.e) {
         return true;
      } else {
         fx _snowman = _snowman.a(_snowman.f());
         cel _snowmanx = new cel(this.l, _snowman, false);
         return _snowman.b(this.l.p(), _snowmanx);
      }
   }

   @Override
   protected int d(bfw var1) {
      if (!this.l.V().b(brt.c) && !this.a_()) {
         int _snowman = this.bD * 7;
         return _snowman > 100 ? 100 : _snowman;
      } else {
         return 0;
      }
   }

   @Override
   protected boolean cX() {
      return true;
   }

   @Override
   public boolean bY() {
      return true;
   }

   @Override
   protected boolean aC() {
      return !this.bC.b && (!this.t || !this.bx());
   }

   @Override
   public void t() {
   }

   public void a(bru var1) {
   }

   @Override
   public nr R() {
      return new oe(this.bJ.getName());
   }

   public bji eL() {
      return this.bn;
   }

   @Override
   public bmb b(aqf var1) {
      if (_snowman == aqf.a) {
         return this.bm.f();
      } else if (_snowman == aqf.b) {
         return this.bm.c.get(0);
      } else {
         return _snowman.a() == aqf.a.b ? this.bm.b.get(_snowman.b()) : bmb.b;
      }
   }

   @Override
   public void a(aqf var1, bmb var2) {
      if (_snowman == aqf.a) {
         this.b(_snowman);
         this.bm.a.set(this.bm.d, _snowman);
      } else if (_snowman == aqf.b) {
         this.b(_snowman);
         this.bm.c.set(0, _snowman);
      } else if (_snowman.a() == aqf.a.b) {
         this.b(_snowman);
         this.bm.b.set(_snowman.b(), _snowman);
      }
   }

   public boolean g(bmb var1) {
      this.b(_snowman);
      return this.bm.e(_snowman);
   }

   @Override
   public Iterable<bmb> bn() {
      return Lists.newArrayList(new bmb[]{this.dD(), this.dE()});
   }

   @Override
   public Iterable<bmb> bo() {
      return this.bm.b;
   }

   public boolean g(md var1) {
      if (this.br() || !this.t || this.aE()) {
         return false;
      } else if (this.eP().isEmpty()) {
         this.h(_snowman);
         this.e = this.l.T();
         return true;
      } else if (this.eQ().isEmpty()) {
         this.i(_snowman);
         this.e = this.l.T();
         return true;
      } else {
         return false;
      }
   }

   protected void eM() {
      if (this.e + 20L < this.l.T()) {
         this.k(this.eP());
         this.h(new md());
         this.k(this.eQ());
         this.i(new md());
      }
   }

   private void k(md var1) {
      if (!this.l.v && !_snowman.isEmpty()) {
         aqe.a(_snowman, this.l).ifPresent(var1x -> {
            if (var1x instanceof are) {
               ((are)var1x).b(this.ad);
            }

            var1x.d(this.cD(), this.cE() + 0.7F, this.cH());
            ((aag)this.l).d(var1x);
         });
      }
   }

   @Override
   public abstract boolean a_();

   @Override
   public boolean bB() {
      return !this.bC.b && !this.a_() && super.bB();
   }

   public abstract boolean b_();

   @Override
   public boolean bV() {
      return !this.bC.b;
   }

   public ddn eN() {
      return this.l.G();
   }

   @Override
   public nr d() {
      nx _snowman = ddl.a(this.bG(), this.R());
      return this.a(_snowman);
   }

   private nx a(nx var1) {
      String _snowman = this.eA().getName();
      return _snowman.a(var2x -> var2x.a(new np(np.a.d, "/tell " + _snowman + " ")).a(this.cb()).a(_snowman));
   }

   @Override
   public String bU() {
      return this.eA().getName();
   }

   @Override
   public float b(aqx var1, aqb var2) {
      switch (_snowman) {
         case d:
         case b:
         case e:
            return 0.4F;
         case f:
            return 1.27F;
         default:
            return 1.62F;
      }
   }

   @Override
   public void s(float var1) {
      if (_snowman < 0.0F) {
         _snowman = 0.0F;
      }

      this.ab().b(c, _snowman);
   }

   @Override
   public float dT() {
      return this.ab().a(c);
   }

   public static UUID a(GameProfile var0) {
      UUID _snowman = _snowman.getId();
      if (_snowman == null) {
         _snowman = c(_snowman.getName());
      }

      return _snowman;
   }

   public static UUID c(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + _snowman).getBytes(StandardCharsets.UTF_8));
   }

   public boolean a(bfx var1) {
      return (this.ab().a(bi) & _snowman.a()) == _snowman.a();
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (_snowman >= 0 && _snowman < this.bm.a.size()) {
         this.bm.a(_snowman, _snowman);
         return true;
      } else {
         aqf _snowman;
         if (_snowman == 100 + aqf.f.b()) {
            _snowman = aqf.f;
         } else if (_snowman == 100 + aqf.e.b()) {
            _snowman = aqf.e;
         } else if (_snowman == 100 + aqf.d.b()) {
            _snowman = aqf.d;
         } else if (_snowman == 100 + aqf.c.b()) {
            _snowman = aqf.c;
         } else {
            _snowman = null;
         }

         if (_snowman == 98) {
            this.a(aqf.a, _snowman);
            return true;
         } else if (_snowman == 99) {
            this.a(aqf.b, _snowman);
            return true;
         } else if (_snowman == null) {
            int _snowmanx = _snowman - 200;
            if (_snowmanx >= 0 && _snowmanx < this.bn.Z_()) {
               this.bn.a(_snowmanx, _snowman);
               return true;
            } else {
               return false;
            }
         } else {
            if (!_snowman.a()) {
               if (!(_snowman.b() instanceof bjy) && !(_snowman.b() instanceof bld)) {
                  if (_snowman != aqf.f) {
                     return false;
                  }
               } else if (aqn.j(_snowman) != _snowman) {
                  return false;
               }
            }

            this.bm.a(_snowman.b() + this.bm.a.size(), _snowman);
            return true;
         }
      }
   }

   public boolean eO() {
      return this.bK;
   }

   public void r(boolean var1) {
      this.bK = _snowman;
   }

   @Override
   public void g(int var1) {
      super.g(this.bC.a ? Math.min(_snowman, 1) : _snowman);
   }

   @Override
   public aqi dV() {
      return this.R.a(bj) == 0 ? aqi.a : aqi.b;
   }

   public void a(aqi var1) {
      this.R.b(bj, (byte)(_snowman == aqi.a ? 0 : 1));
   }

   public md eP() {
      return this.R.a(bk);
   }

   protected void h(md var1) {
      this.R.b(bk, _snowman);
   }

   public md eQ() {
      return this.R.a(bl);
   }

   protected void i(md var1) {
      this.R.b(bl, _snowman);
   }

   public float eR() {
      return (float)(1.0 / this.b(arl.h) * 20.0);
   }

   public float u(float var1) {
      return afm.a(((float)this.at + _snowman) / this.eR(), 0.0F, 1.0F);
   }

   public void eS() {
      this.at = 0;
   }

   public bly eT() {
      return this.bM;
   }

   @Override
   protected float ar() {
      return !this.bC.b && !this.ef() ? super.ar() : 1.0F;
   }

   public float eU() {
      return (float)this.b(arl.k);
   }

   public boolean eV() {
      return this.bC.d && this.y() >= 2;
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      return this.b(_snowman).a();
   }

   @Override
   public aqb a(aqx var1) {
      return b.getOrDefault(_snowman, bh);
   }

   @Override
   public ImmutableList<aqx> ej() {
      return ImmutableList.of(aqx.a, aqx.f, aqx.d);
   }

   @Override
   public bmb f(bmb var1) {
      if (!(_snowman.b() instanceof bmo)) {
         return bmb.b;
      } else {
         Predicate<bmb> _snowman = ((bmo)_snowman.b()).e();
         bmb _snowmanx = bmo.a(this, _snowman);
         if (!_snowmanx.a()) {
            return _snowmanx;
         } else {
            _snowman = ((bmo)_snowman.b()).b();

            for (int _snowmanxx = 0; _snowmanxx < this.bm.Z_(); _snowmanxx++) {
               bmb _snowmanxxx = this.bm.a(_snowmanxx);
               if (_snowman.test(_snowmanxxx)) {
                  return _snowmanxxx;
               }
            }

            return this.bC.d ? new bmb(bmd.kd) : bmb.b;
         }
      }
   }

   @Override
   public bmb a(brx var1, bmb var2) {
      this.eI().a(_snowman.b(), _snowman);
      this.b(aea.c.b(_snowman.b()));
      _snowman.a(null, this.cD(), this.cE(), this.cH(), adq.lw, adr.h, 0.5F, _snowman.t.nextFloat() * 0.1F + 0.9F);
      if (this instanceof aah) {
         ac.z.a((aah)this, _snowman);
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   protected boolean b(ceh var1) {
      return this.bC.b || super.b(_snowman);
   }

   @Override
   public dcn o(float var1) {
      double _snowman = 0.22 * (this.dV() == aqi.b ? -1.0 : 1.0);
      float _snowmanx = afm.g(_snowman * 0.5F, this.q, this.s) * (float) (Math.PI / 180.0);
      float _snowmanxx = afm.g(_snowman, this.aB, this.aA) * (float) (Math.PI / 180.0);
      if (this.ef() || this.dR()) {
         dcn _snowmanxxx = this.f(_snowman);
         dcn _snowmanxxxx = this.cC();
         double _snowmanxxxxx = aqa.c(_snowmanxxxx);
         double _snowmanxxxxxx = aqa.c(_snowmanxxx);
         float _snowmanxxxxxxx;
         if (_snowmanxxxxx > 0.0 && _snowmanxxxxxx > 0.0) {
            double _snowmanxxxxxxxx = (_snowmanxxxx.b * _snowmanxxx.b + _snowmanxxxx.d * _snowmanxxx.d) / Math.sqrt(_snowmanxxxxx * _snowmanxxxxxx);
            double _snowmanxxxxxxxxx = _snowmanxxxx.b * _snowmanxxx.d - _snowmanxxxx.d * _snowmanxxx.b;
            _snowmanxxxxxxx = (float)(Math.signum(_snowmanxxxxxxxxx) * Math.acos(_snowmanxxxxxxxx));
         } else {
            _snowmanxxxxxxx = 0.0F;
         }

         return this.l(_snowman).e(new dcn(_snowman, -0.11, 0.85).c(-_snowmanxxxxxxx).a(-_snowmanx).b(-_snowmanxx));
      } else if (this.bC()) {
         return this.l(_snowman).e(new dcn(_snowman, 0.2, -0.15).a(-_snowmanx).b(-_snowmanxx));
      } else {
         double _snowmanxxx = this.cc().c() - 1.0;
         double _snowmanxxxx = this.bz() ? -0.2 : 0.07;
         return this.l(_snowman).e(new dcn(_snowman, _snowmanxxx, _snowmanxxxx).b(-_snowmanxx));
      }
   }

   public static enum a {
      a,
      b(new of("block.minecraft.bed.no_sleep")),
      c(new of("block.minecraft.bed.too_far_away")),
      d(new of("block.minecraft.bed.obstructed")),
      e,
      f(new of("block.minecraft.bed.not_safe"));

      @Nullable
      private final nr g;

      private a() {
         this.g = null;
      }

      private a(nr var3) {
         this.g = _snowman;
      }

      @Nullable
      public nr a() {
         return this.g;
      }
   }
}
