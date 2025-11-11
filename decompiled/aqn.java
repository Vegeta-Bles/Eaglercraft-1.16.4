import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public abstract class aqn extends aqm {
   private static final us<Byte> b = uv.a(aqn.class, uu.a);
   public int e;
   protected int f;
   protected ava g;
   protected avb bh;
   protected auz bi;
   private final auu c;
   protected ayj bj;
   protected final avw bk;
   protected final avw bl;
   private aqm d;
   private final aza bo;
   private final gj<bmb> bp = gj.a(2, bmb.b);
   protected final float[] bm = new float[2];
   private final gj<bmb> bq = gj.a(4, bmb.b);
   protected final float[] bn = new float[4];
   private boolean br;
   private boolean bs;
   private final Map<cwz, Float> bt = Maps.newEnumMap(cwz.class);
   private vk bu;
   private long bv;
   @Nullable
   private aqa bw;
   private int bx;
   @Nullable
   private md by;
   private fx bz = fx.b;
   private float bA = -1.0F;

   protected aqn(aqe<? extends aqn> var1, brx var2) {
      super(_snowman, _snowman);
      this.bk = new avw(_snowman.aa());
      this.bl = new avw(_snowman.aa());
      this.g = new ava(this);
      this.bh = new avb(this);
      this.bi = new auz(this);
      this.c = this.r();
      this.bj = this.b(_snowman);
      this.bo = new aza(this);
      Arrays.fill(this.bn, 0.085F);
      Arrays.fill(this.bm, 0.085F);
      if (_snowman != null && !_snowman.v) {
         this.o();
      }
   }

   @Override
   protected void o() {
   }

   public static ark.a p() {
      return aqm.cL().a(arl.b, 16.0).a(arl.g);
   }

   protected ayj b(brx var1) {
      return new ayi(this, _snowman);
   }

   protected boolean q() {
      return false;
   }

   public float a(cwz var1) {
      aqn _snowman;
      if (this.ct() instanceof aqn && ((aqn)this.ct()).q()) {
         _snowman = (aqn)this.ct();
      } else {
         _snowman = this;
      }

      Float _snowmanx = _snowman.bt.get(_snowman);
      return _snowmanx == null ? _snowman.a() : _snowmanx;
   }

   public void a(cwz var1, float var2) {
      this.bt.put(_snowman, _snowman);
   }

   public boolean b(cwz var1) {
      return _snowman != cwz.l && _snowman != cwz.n && _snowman != cwz.p && _snowman != cwz.d;
   }

   protected auu r() {
      return new auu(this);
   }

   public ava t() {
      return this.g;
   }

   public avb u() {
      if (this.br() && this.ct() instanceof aqn) {
         aqn _snowman = (aqn)this.ct();
         return _snowman.u();
      } else {
         return this.bh;
      }
   }

   public auz v() {
      return this.bi;
   }

   public ayj x() {
      if (this.br() && this.ct() instanceof aqn) {
         aqn _snowman = (aqn)this.ct();
         return _snowman.x();
      } else {
         return this.bj;
      }
   }

   public aza z() {
      return this.bo;
   }

   @Nullable
   public aqm A() {
      return this.d;
   }

   public void h(@Nullable aqm var1) {
      this.d = _snowman;
   }

   @Override
   public boolean a(aqe<?> var1) {
      return _snowman != aqe.D;
   }

   public boolean a(bmo var1) {
      return false;
   }

   public void B() {
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, (byte)0);
   }

   public int D() {
      return 80;
   }

   public void F() {
      adp _snowman = this.I();
      if (_snowman != null) {
         this.a(_snowman, this.dG(), this.dH());
      }
   }

   @Override
   public void ag() {
      super.ag();
      this.l.Z().a("mobBaseTick");
      if (this.aX() && this.J.nextInt(1000) < this.e++) {
         this.m();
         this.F();
      }

      this.l.Z().c();
   }

   @Override
   protected void c(apk var1) {
      this.m();
      super.c(_snowman);
   }

   private void m() {
      this.e = -this.D();
   }

   @Override
   protected int d(bfw var1) {
      if (this.f > 0) {
         int _snowman = this.f;

         for (int _snowmanx = 0; _snowmanx < this.bq.size(); _snowmanx++) {
            if (!this.bq.get(_snowmanx).a() && this.bn[_snowmanx] <= 1.0F) {
               _snowman += 1 + this.J.nextInt(3);
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < this.bp.size(); _snowmanxx++) {
            if (!this.bp.get(_snowmanxx).a() && this.bm[_snowmanxx] <= 1.0F) {
               _snowman += 1 + this.J.nextInt(3);
            }
         }

         return _snowman;
      } else {
         return this.f;
      }
   }

   public void G() {
      if (this.l.v) {
         for (int _snowman = 0; _snowman < 20; _snowman++) {
            double _snowmanx = this.J.nextGaussian() * 0.02;
            double _snowmanxx = this.J.nextGaussian() * 0.02;
            double _snowmanxxx = this.J.nextGaussian() * 0.02;
            double _snowmanxxxx = 10.0;
            this.l.a(hh.P, this.c(1.0) - _snowmanx * 10.0, this.cF() - _snowmanxx * 10.0, this.g(1.0) - _snowmanxxx * 10.0, _snowmanx, _snowmanxx, _snowmanxxx);
         }
      } else {
         this.l.a(this, (byte)20);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 20) {
         this.G();
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public void j() {
      super.j();
      if (!this.l.v) {
         this.eA();
         if (this.K % 5 == 0) {
            this.H();
         }
      }
   }

   protected void H() {
      boolean _snowman = !(this.cm() instanceof aqn);
      boolean _snowmanx = !(this.ct() instanceof bhn);
      this.bk.a(avv.a.a, _snowman);
      this.bk.a(avv.a.c, _snowman && _snowmanx);
      this.bk.a(avv.a.b, _snowman);
   }

   @Override
   protected float f(float var1, float var2) {
      this.c.a();
      return _snowman;
   }

   @Nullable
   protected adp I() {
      return null;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("CanPickUpLoot", this.et());
      _snowman.a("PersistenceRequired", this.bs);
      mj _snowman = new mj();

      for (bmb _snowmanx : this.bq) {
         md _snowmanxx = new md();
         if (!_snowmanx.a()) {
            _snowmanx.b(_snowmanxx);
         }

         _snowman.add(_snowmanxx);
      }

      _snowman.a("ArmorItems", _snowman);
      mj _snowmanx = new mj();

      for (bmb _snowmanxx : this.bp) {
         md _snowmanxxx = new md();
         if (!_snowmanxx.a()) {
            _snowmanxx.b(_snowmanxxx);
         }

         _snowmanx.add(_snowmanxxx);
      }

      _snowman.a("HandItems", _snowmanx);
      mj _snowmanxx = new mj();

      for (float _snowmanxxx : this.bn) {
         _snowmanxx.add(mg.a(_snowmanxxx));
      }

      _snowman.a("ArmorDropChances", _snowmanxx);
      mj _snowmanxxx = new mj();

      for (float _snowmanxxxx : this.bm) {
         _snowmanxxx.add(mg.a(_snowmanxxxx));
      }

      _snowman.a("HandDropChances", _snowmanxxx);
      if (this.bw != null) {
         md _snowmanxxxx = new md();
         if (this.bw instanceof aqm) {
            UUID _snowmanxxxxx = this.bw.bS();
            _snowmanxxxx.a("UUID", _snowmanxxxxx);
         } else if (this.bw instanceof bco) {
            fx _snowmanxxxxx = ((bco)this.bw).n();
            _snowmanxxxx.b("X", _snowmanxxxxx.u());
            _snowmanxxxx.b("Y", _snowmanxxxxx.v());
            _snowmanxxxx.b("Z", _snowmanxxxxx.w());
         }

         _snowman.a("Leash", _snowmanxxxx);
      } else if (this.by != null) {
         _snowman.a("Leash", this.by.g());
      }

      _snowman.a("LeftHanded", this.eE());
      if (this.bu != null) {
         _snowman.a("DeathLootTable", this.bu.toString());
         if (this.bv != 0L) {
            _snowman.a("DeathLootTableSeed", this.bv);
         }
      }

      if (this.eD()) {
         _snowman.a("NoAI", this.eD());
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("CanPickUpLoot", 1)) {
         this.p(_snowman.q("CanPickUpLoot"));
      }

      this.bs = _snowman.q("PersistenceRequired");
      if (_snowman.c("ArmorItems", 9)) {
         mj _snowman = _snowman.d("ArmorItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.bq.size(); _snowmanx++) {
            this.bq.set(_snowmanx, bmb.a(_snowman.a(_snowmanx)));
         }
      }

      if (_snowman.c("HandItems", 9)) {
         mj _snowman = _snowman.d("HandItems", 10);

         for (int _snowmanx = 0; _snowmanx < this.bp.size(); _snowmanx++) {
            this.bp.set(_snowmanx, bmb.a(_snowman.a(_snowmanx)));
         }
      }

      if (_snowman.c("ArmorDropChances", 9)) {
         mj _snowman = _snowman.d("ArmorDropChances", 5);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.bn[_snowmanx] = _snowman.i(_snowmanx);
         }
      }

      if (_snowman.c("HandDropChances", 9)) {
         mj _snowman = _snowman.d("HandDropChances", 5);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.bm[_snowmanx] = _snowman.i(_snowmanx);
         }
      }

      if (_snowman.c("Leash", 10)) {
         this.by = _snowman.p("Leash");
      }

      this.r(_snowman.q("LeftHanded"));
      if (_snowman.c("DeathLootTable", 8)) {
         this.bu = new vk(_snowman.l("DeathLootTable"));
         this.bv = _snowman.i("DeathLootTableSeed");
      }

      this.q(_snowman.q("NoAI"));
   }

   @Override
   protected void a(apk var1, boolean var2) {
      super.a(_snowman, _snowman);
      this.bu = null;
   }

   @Override
   protected cyv.a a(boolean var1, apk var2) {
      return super.a(_snowman, _snowman).a(this.bv, this.J);
   }

   @Override
   public final vk dp() {
      return this.bu == null ? this.J() : this.bu;
   }

   protected vk J() {
      return super.dp();
   }

   public void t(float var1) {
      this.aT = _snowman;
   }

   public void u(float var1) {
      this.aS = _snowman;
   }

   public void v(float var1) {
      this.aR = _snowman;
   }

   @Override
   public void q(float var1) {
      super.q(_snowman);
      this.t(_snowman);
   }

   @Override
   public void k() {
      super.k();
      this.l.Z().a("looting");
      if (!this.l.v && this.et() && this.aX() && !this.aH && this.l.V().b(brt.b)) {
         for (bcv _snowman : this.l.a(bcv.class, this.cc().c(1.0, 0.0, 1.0))) {
            if (!_snowman.y && !_snowman.g().a() && !_snowman.p() && this.i(_snowman.g())) {
               this.b(_snowman);
            }
         }
      }

      this.l.Z().c();
   }

   protected void b(bcv var1) {
      bmb _snowman = _snowman.g();
      if (this.g(_snowman)) {
         this.a(_snowman);
         this.a(_snowman, _snowman.E());
         _snowman.ad();
      }
   }

   public boolean g(bmb var1) {
      aqf _snowman = j(_snowman);
      bmb _snowmanx = this.b(_snowman);
      boolean _snowmanxx = this.a(_snowman, _snowmanx);
      if (_snowmanxx && this.h(_snowman)) {
         double _snowmanxxx = (double)this.e(_snowman);
         if (!_snowmanx.a() && (double)Math.max(this.J.nextFloat() - 0.1F, 0.0F) < _snowmanxxx) {
            this.a(_snowmanx);
         }

         this.b(_snowman, _snowman);
         this.b(_snowman);
         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void b(aqf var1, bmb var2) {
      this.a(_snowman, _snowman);
      this.d(_snowman);
      this.bs = true;
   }

   public void d(aqf var1) {
      switch (_snowman.a()) {
         case a:
            this.bm[_snowman.b()] = 2.0F;
            break;
         case b:
            this.bn[_snowman.b()] = 2.0F;
      }
   }

   protected boolean a(bmb var1, bmb var2) {
      if (_snowman.a()) {
         return true;
      } else if (_snowman.b() instanceof bnf) {
         if (!(_snowman.b() instanceof bnf)) {
            return true;
         } else {
            bnf _snowman = (bnf)_snowman.b();
            bnf _snowmanx = (bnf)_snowman.b();
            return _snowman.f() != _snowmanx.f() ? _snowman.f() > _snowmanx.f() : this.b(_snowman, _snowman);
         }
      } else if (_snowman.b() instanceof bkm && _snowman.b() instanceof bkm) {
         return this.b(_snowman, _snowman);
      } else if (_snowman.b() instanceof bkt && _snowman.b() instanceof bkt) {
         return this.b(_snowman, _snowman);
      } else if (_snowman.b() instanceof bjy) {
         if (bpu.d(_snowman)) {
            return false;
         } else if (!(_snowman.b() instanceof bjy)) {
            return true;
         } else {
            bjy _snowman = (bjy)_snowman.b();
            bjy _snowmanx = (bjy)_snowman.b();
            if (_snowman.e() != _snowmanx.e()) {
               return _snowman.e() > _snowmanx.e();
            } else {
               return _snowman.f() != _snowmanx.f() ? _snowman.f() > _snowmanx.f() : this.b(_snowman, _snowman);
            }
         }
      } else {
         if (_snowman.b() instanceof bkv) {
            if (_snowman.b() instanceof bkh) {
               return true;
            }

            if (_snowman.b() instanceof bkv) {
               bkv _snowman = (bkv)_snowman.b();
               bkv _snowmanx = (bkv)_snowman.b();
               if (_snowman.d() != _snowmanx.d()) {
                  return _snowman.d() > _snowmanx.d();
               }

               return this.b(_snowman, _snowman);
            }
         }

         return false;
      }
   }

   public boolean b(bmb var1, bmb var2) {
      if (_snowman.g() >= _snowman.g() && (!_snowman.n() || _snowman.n())) {
         return _snowman.n() && _snowman.n()
            ? _snowman.o().d().stream().anyMatch(var0 -> !var0.equals("Damage")) && !_snowman.o().d().stream().anyMatch(var0 -> !var0.equals("Damage"))
            : false;
      } else {
         return true;
      }
   }

   public boolean h(bmb var1) {
      return true;
   }

   public boolean i(bmb var1) {
      return this.h(_snowman);
   }

   public boolean h(double var1) {
      return true;
   }

   public boolean K() {
      return this.br();
   }

   protected boolean L() {
      return false;
   }

   @Override
   public void cI() {
      if (this.l.ad() == aor.a && this.L()) {
         this.ad();
      } else if (!this.eu() && !this.K()) {
         aqa _snowman = this.l.a(this, -1.0);
         if (_snowman != null) {
            double _snowmanx = _snowman.h(this);
            int _snowmanxx = this.X().e().f();
            int _snowmanxxx = _snowmanxx * _snowmanxx;
            if (_snowmanx > (double)_snowmanxxx && this.h(_snowmanx)) {
               this.ad();
            }

            int _snowmanxxxx = this.X().e().g();
            int _snowmanxxxxx = _snowmanxxxx * _snowmanxxxx;
            if (this.aI > 600 && this.J.nextInt(800) == 0 && _snowmanx > (double)_snowmanxxxxx && this.h(_snowmanx)) {
               this.ad();
            } else if (_snowmanx < (double)_snowmanxxxxx) {
               this.aI = 0;
            }
         }
      } else {
         this.aI = 0;
      }
   }

   @Override
   protected final void dP() {
      this.aI++;
      this.l.Z().a("sensing");
      this.bo.a();
      this.l.Z().c();
      this.l.Z().a("targetSelector");
      this.bl.b();
      this.l.Z().c();
      this.l.Z().a("goalSelector");
      this.bk.b();
      this.l.Z().c();
      this.l.Z().a("navigation");
      this.bj.c();
      this.l.Z().c();
      this.l.Z().a("mob tick");
      this.N();
      this.l.Z().c();
      this.l.Z().a("controls");
      this.l.Z().a("move");
      this.bh.a();
      this.l.Z().b("look");
      this.g.a();
      this.l.Z().b("jump");
      this.bi.b();
      this.l.Z().c();
      this.l.Z().c();
      this.M();
   }

   protected void M() {
      rz.a(this.l, this, this.bk);
   }

   protected void N() {
   }

   public int O() {
      return 40;
   }

   public int Q() {
      return 75;
   }

   public int ep() {
      return 10;
   }

   public void a(aqa var1, float var2, float var3) {
      double _snowman = _snowman.cD() - this.cD();
      double _snowmanx = _snowman.cH() - this.cH();
      double _snowmanxx;
      if (_snowman instanceof aqm) {
         aqm _snowmanxxx = (aqm)_snowman;
         _snowmanxx = _snowmanxxx.cG() - this.cG();
      } else {
         _snowmanxx = (_snowman.cc().b + _snowman.cc().e) / 2.0 - this.cG();
      }

      double _snowmanxxx = (double)afm.a(_snowman * _snowman + _snowmanx * _snowmanx);
      float _snowmanxxxx = (float)(afm.d(_snowmanx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
      float _snowmanxxxxx = (float)(-(afm.d(_snowmanxx, _snowmanxxx) * 180.0F / (float)Math.PI));
      this.q = this.a(this.q, _snowmanxxxxx, _snowman);
      this.p = this.a(this.p, _snowmanxxxx, _snowman);
   }

   private float a(float var1, float var2, float var3) {
      float _snowman = afm.g(_snowman - _snowman);
      if (_snowman > _snowman) {
         _snowman = _snowman;
      }

      if (_snowman < -_snowman) {
         _snowman = -_snowman;
      }

      return _snowman + _snowman;
   }

   public static boolean a(aqe<? extends aqn> var0, bry var1, aqp var2, fx var3, Random var4) {
      fx _snowman = _snowman.c();
      return _snowman == aqp.c || _snowman.d_(_snowman).a(_snowman, _snowman, _snowman);
   }

   public boolean a(bry var1, aqp var2) {
      return true;
   }

   public boolean a(brz var1) {
      return !_snowman.d(this.cc()) && _snowman.j(this);
   }

   public int eq() {
      return 4;
   }

   public boolean c(int var1) {
      return false;
   }

   @Override
   public int bP() {
      if (this.A() == null) {
         return 3;
      } else {
         int _snowman = (int)(this.dk() - this.dx() * 0.33F);
         _snowman -= (3 - this.l.ad().a()) * 4;
         if (_snowman < 0) {
            _snowman = 0;
         }

         return _snowman + 3;
      }
   }

   @Override
   public Iterable<bmb> bn() {
      return this.bp;
   }

   @Override
   public Iterable<bmb> bo() {
      return this.bq;
   }

   @Override
   public bmb b(aqf var1) {
      switch (_snowman.a()) {
         case a:
            return this.bp.get(_snowman.b());
         case b:
            return this.bq.get(_snowman.b());
         default:
            return bmb.b;
      }
   }

   @Override
   public void a(aqf var1, bmb var2) {
      switch (_snowman.a()) {
         case a:
            this.bp.set(_snowman.b(), _snowman);
            break;
         case b:
            this.bq.set(_snowman.b(), _snowman);
      }
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);

      for (aqf _snowman : aqf.values()) {
         bmb _snowmanx = this.b(_snowman);
         float _snowmanxx = this.e(_snowman);
         boolean _snowmanxxx = _snowmanxx > 1.0F;
         if (!_snowmanx.a() && !bpu.e(_snowmanx) && (_snowman || _snowmanxxx) && Math.max(this.J.nextFloat() - (float)_snowman * 0.01F, 0.0F) < _snowmanxx) {
            if (!_snowmanxxx && _snowmanx.e()) {
               _snowmanx.b(_snowmanx.h() - this.J.nextInt(1 + this.J.nextInt(Math.max(_snowmanx.h() - 3, 1))));
            }

            this.a(_snowmanx);
            this.a(_snowman, bmb.b);
         }
      }
   }

   protected float e(aqf var1) {
      float _snowman;
      switch (_snowman.a()) {
         case a:
            _snowman = this.bm[_snowman.b()];
            break;
         case b:
            _snowman = this.bn[_snowman.b()];
            break;
         default:
            _snowman = 0.0F;
      }

      return _snowman;
   }

   protected void a(aos var1) {
      if (this.J.nextFloat() < 0.15F * _snowman.d()) {
         int _snowman = this.J.nextInt(2);
         float _snowmanx = this.l.ad() == aor.d ? 0.1F : 0.25F;
         if (this.J.nextFloat() < 0.095F) {
            _snowman++;
         }

         if (this.J.nextFloat() < 0.095F) {
            _snowman++;
         }

         if (this.J.nextFloat() < 0.095F) {
            _snowman++;
         }

         boolean _snowmanxx = true;

         for (aqf _snowmanxxx : aqf.values()) {
            if (_snowmanxxx.a() == aqf.a.b) {
               bmb _snowmanxxxx = this.b(_snowmanxxx);
               if (!_snowmanxx && this.J.nextFloat() < _snowmanx) {
                  break;
               }

               _snowmanxx = false;
               if (_snowmanxxxx.a()) {
                  blx _snowmanxxxxx = a(_snowmanxxx, _snowman);
                  if (_snowmanxxxxx != null) {
                     this.a(_snowmanxxx, new bmb(_snowmanxxxxx));
                  }
               }
            }
         }
      }
   }

   public static aqf j(bmb var0) {
      blx _snowman = _snowman.b();
      if (_snowman != bup.cU.h() && (!(_snowman instanceof bkh) || !(((bkh)_snowman).e() instanceof btq))) {
         if (_snowman instanceof bjy) {
            return ((bjy)_snowman).b();
         } else if (_snowman == bmd.qo) {
            return aqf.e;
         } else {
            return _snowman == bmd.qn ? aqf.b : aqf.a;
         }
      } else {
         return aqf.f;
      }
   }

   @Nullable
   public static blx a(aqf var0, int var1) {
      switch (_snowman) {
         case f:
            if (_snowman == 0) {
               return bmd.kY;
            } else if (_snowman == 1) {
               return bmd.lo;
            } else if (_snowman == 2) {
               return bmd.lc;
            } else if (_snowman == 3) {
               return bmd.lg;
            } else if (_snowman == 4) {
               return bmd.lk;
            }
         case e:
            if (_snowman == 0) {
               return bmd.kZ;
            } else if (_snowman == 1) {
               return bmd.lp;
            } else if (_snowman == 2) {
               return bmd.ld;
            } else if (_snowman == 3) {
               return bmd.lh;
            } else if (_snowman == 4) {
               return bmd.ll;
            }
         case d:
            if (_snowman == 0) {
               return bmd.la;
            } else if (_snowman == 1) {
               return bmd.lq;
            } else if (_snowman == 2) {
               return bmd.le;
            } else if (_snowman == 3) {
               return bmd.li;
            } else if (_snowman == 4) {
               return bmd.lm;
            }
         case c:
            if (_snowman == 0) {
               return bmd.lb;
            } else if (_snowman == 1) {
               return bmd.lr;
            } else if (_snowman == 2) {
               return bmd.lf;
            } else if (_snowman == 3) {
               return bmd.lj;
            } else if (_snowman == 4) {
               return bmd.ln;
            }
         default:
            return null;
      }
   }

   protected void b(aos var1) {
      float _snowman = _snowman.d();
      this.w(_snowman);

      for (aqf _snowmanx : aqf.values()) {
         if (_snowmanx.a() == aqf.a.b) {
            this.a(_snowman, _snowmanx);
         }
      }
   }

   protected void w(float var1) {
      if (!this.dD().a() && this.J.nextFloat() < 0.25F * _snowman) {
         this.a(aqf.a, bpu.a(this.J, this.dD(), (int)(5.0F + _snowman * (float)this.J.nextInt(18)), false));
      }
   }

   protected void a(float var1, aqf var2) {
      bmb _snowman = this.b(_snowman);
      if (!_snowman.a() && this.J.nextFloat() < 0.5F * _snowman) {
         this.a(_snowman, bpu.a(this.J, _snowman, (int)(5.0F + _snowman * (float)this.J.nextInt(18)), false));
      }
   }

   @Nullable
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.a(arl.b).c(new arj("Random spawn bonus", this.J.nextGaussian() * 0.05, arj.a.b));
      if (this.J.nextFloat() < 0.05F) {
         this.r(true);
      } else {
         this.r(false);
      }

      return _snowman;
   }

   public boolean er() {
      return false;
   }

   public void es() {
      this.bs = true;
   }

   public void a(aqf var1, float var2) {
      switch (_snowman.a()) {
         case a:
            this.bm[_snowman.b()] = _snowman;
            break;
         case b:
            this.bn[_snowman.b()] = _snowman;
      }
   }

   public boolean et() {
      return this.br;
   }

   public void p(boolean var1) {
      this.br = _snowman;
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = j(_snowman);
      return this.b(_snowman).a() && this.et();
   }

   public boolean eu() {
      return this.bs;
   }

   @Override
   public final aou a(bfw var1, aot var2) {
      if (!this.aX()) {
         return aou.c;
      } else if (this.eC() == _snowman) {
         this.a(true, !_snowman.bC.d);
         return aou.a(this.l.v);
      } else {
         aou _snowman = this.c(_snowman, _snowman);
         if (_snowman.a()) {
            return _snowman;
         } else {
            _snowman = this.b(_snowman, _snowman);
            return _snowman.a() ? _snowman : super.a(_snowman, _snowman);
         }
      }
   }

   private aou c(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (_snowman.b() == bmd.pH && this.a(_snowman)) {
         this.b(_snowman, true);
         _snowman.g(1);
         return aou.a(this.l.v);
      } else {
         if (_snowman.b() == bmd.pI) {
            aou _snowmanx = _snowman.a(_snowman, this, _snowman);
            if (_snowmanx.a()) {
               return _snowmanx;
            }
         }

         if (_snowman.b() instanceof bna) {
            if (this.l instanceof aag) {
               bna _snowmanx = (bna)_snowman.b();
               Optional<aqn> _snowmanxx = _snowmanx.a(_snowman, this, (aqe<? extends aqn>)this.X(), (aag)this.l, this.cA(), _snowman);
               _snowmanxx.ifPresent(var2x -> this.a(_snowman, var2x));
               return _snowmanxx.isPresent() ? aou.a : aou.c;
            } else {
               return aou.b;
            }
         } else {
            return aou.c;
         }
      }
   }

   protected void a(bfw var1, aqn var2) {
   }

   protected aou b(bfw var1, aot var2) {
      return aou.c;
   }

   public boolean ev() {
      return this.a(this.cB());
   }

   public boolean a(fx var1) {
      return this.bA == -1.0F ? true : this.bz.j(_snowman) < (double)(this.bA * this.bA);
   }

   public void a(fx var1, int var2) {
      this.bz = _snowman;
      this.bA = (float)_snowman;
   }

   public fx ew() {
      return this.bz;
   }

   public float ex() {
      return this.bA;
   }

   public boolean ez() {
      return this.bA != -1.0F;
   }

   @Nullable
   public <T extends aqn> T a(aqe<T> var1, boolean var2) {
      if (this.y) {
         return null;
      } else {
         T _snowman = (T)_snowman.a(this.l);
         _snowman.u(this);
         _snowman.a(this.w_());
         _snowman.q(this.eD());
         if (this.S()) {
            _snowman.a(this.T());
            _snowman.n(this.bX());
         }

         if (this.eu()) {
            _snowman.es();
         }

         _snowman.m(this.bM());
         if (_snowman) {
            _snowman.p(this.et());

            for (aqf _snowmanx : aqf.values()) {
               bmb _snowmanxx = this.b(_snowmanx);
               if (!_snowmanxx.a()) {
                  _snowman.a(_snowmanx, _snowmanxx.i());
                  _snowman.a(_snowmanx, this.e(_snowmanx));
                  _snowmanxx.e(0);
               }
            }
         }

         this.l.c(_snowman);
         if (this.br()) {
            aqa _snowmanxx = this.ct();
            this.l();
            _snowman.a(_snowmanxx, true);
         }

         this.ad();
         return _snowman;
      }
   }

   protected void eA() {
      if (this.by != null) {
         this.eI();
      }

      if (this.bw != null) {
         if (!this.aX() || !this.bw.aX()) {
            this.a(true, true);
         }
      }
   }

   public void a(boolean var1, boolean var2) {
      if (this.bw != null) {
         this.k = false;
         if (!(this.bw instanceof bfw)) {
            this.bw.k = false;
         }

         this.bw = null;
         this.by = null;
         if (!this.l.v && _snowman) {
            this.a(bmd.pH);
         }

         if (!this.l.v && _snowman && this.l instanceof aag) {
            ((aag)this.l).i().b(this, new rb(this, null));
         }
      }
   }

   public boolean a(bfw var1) {
      return !this.eB() && !(this instanceof bdi);
   }

   public boolean eB() {
      return this.bw != null;
   }

   @Nullable
   public aqa eC() {
      if (this.bw == null && this.bx != 0 && this.l.v) {
         this.bw = this.l.a(this.bx);
      }

      return this.bw;
   }

   public void b(aqa var1, boolean var2) {
      this.bw = _snowman;
      this.by = null;
      this.k = true;
      if (!(this.bw instanceof bfw)) {
         this.bw.k = true;
      }

      if (!this.l.v && _snowman && this.l instanceof aag) {
         ((aag)this.l).i().b(this, new rb(this, this.bw));
      }

      if (this.br()) {
         this.l();
      }
   }

   public void d(int var1) {
      this.bx = _snowman;
      this.a(false, false);
   }

   @Override
   public boolean a(aqa var1, boolean var2) {
      boolean _snowman = super.a(_snowman, _snowman);
      if (_snowman && this.eB()) {
         this.a(true, true);
      }

      return _snowman;
   }

   private void eI() {
      if (this.by != null && this.l instanceof aag) {
         if (this.by.b("UUID")) {
            UUID _snowman = this.by.a("UUID");
            aqa _snowmanx = ((aag)this.l).a(_snowman);
            if (_snowmanx != null) {
               this.b(_snowmanx, true);
               return;
            }
         } else if (this.by.c("X", 99) && this.by.c("Y", 99) && this.by.c("Z", 99)) {
            fx _snowman = new fx(this.by.h("X"), this.by.h("Y"), this.by.h("Z"));
            this.b(bcq.a(this.l, _snowman), true);
            return;
         }

         if (this.K > 100) {
            this.a(bmd.pH);
            this.by = null;
         }
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

      if (!_snowman.a() && !c(_snowman, _snowman) && _snowman != aqf.f) {
         return false;
      } else {
         this.a(_snowman, _snowman);
         return true;
      }
   }

   @Override
   public boolean cs() {
      return this.er() && super.cs();
   }

   public static boolean c(aqf var0, bmb var1) {
      aqf _snowman = j(_snowman);
      return _snowman == _snowman || _snowman == aqf.a && _snowman == aqf.b || _snowman == aqf.b && _snowman == aqf.a;
   }

   @Override
   public boolean dS() {
      return super.dS() && !this.eD();
   }

   public void q(boolean var1) {
      byte _snowman = this.R.a(b);
      this.R.b(b, _snowman ? (byte)(_snowman | 1) : (byte)(_snowman & -2));
   }

   public void r(boolean var1) {
      byte _snowman = this.R.a(b);
      this.R.b(b, _snowman ? (byte)(_snowman | 2) : (byte)(_snowman & -3));
   }

   public void s(boolean var1) {
      byte _snowman = this.R.a(b);
      this.R.b(b, _snowman ? (byte)(_snowman | 4) : (byte)(_snowman & -5));
   }

   public boolean eD() {
      return (this.R.a(b) & 1) != 0;
   }

   public boolean eE() {
      return (this.R.a(b) & 2) != 0;
   }

   public boolean eF() {
      return (this.R.a(b) & 4) != 0;
   }

   public void a(boolean var1) {
   }

   @Override
   public aqi dV() {
      return this.eE() ? aqi.a : aqi.b;
   }

   @Override
   public boolean c(aqm var1) {
      return _snowman.X() == aqe.bc && ((bfw)_snowman).bC.a ? false : super.c(_snowman);
   }

   @Override
   public boolean B(aqa var1) {
      float _snowman = (float)this.b(arl.f);
      float _snowmanx = (float)this.b(arl.g);
      if (_snowman instanceof aqm) {
         _snowman += bpu.a(this.dD(), ((aqm)_snowman).dC());
         _snowmanx += (float)bpu.b(this);
      }

      int _snowmanxx = bpu.c(this);
      if (_snowmanxx > 0) {
         _snowman.f(_snowmanxx * 4);
      }

      boolean _snowmanxxx = _snowman.a(apk.c(this), _snowman);
      if (_snowmanxxx) {
         if (_snowmanx > 0.0F && _snowman instanceof aqm) {
            ((aqm)_snowman).a(_snowmanx * 0.5F, (double)afm.a(this.p * (float) (Math.PI / 180.0)), (double)(-afm.b(this.p * (float) (Math.PI / 180.0))));
            this.f(this.cC().d(0.6, 1.0, 0.6));
         }

         if (_snowman instanceof bfw) {
            bfw _snowmanxxxx = (bfw)_snowman;
            this.a(_snowmanxxxx, this.dD(), _snowmanxxxx.dW() ? _snowmanxxxx.dY() : bmb.b);
         }

         this.a(this, _snowman);
         this.z(_snowman);
      }

      return _snowmanxxx;
   }

   private void a(bfw var1, bmb var2, bmb var3) {
      if (!_snowman.a() && !_snowman.a() && _snowman.b() instanceof bkd && _snowman.b() == bmd.qn) {
         float _snowman = 0.25F + (float)bpu.f(this) * 0.05F;
         if (this.J.nextFloat() < _snowman) {
            _snowman.eT().a(bmd.qn, 100);
            this.l.a(_snowman, (byte)30);
         }
      }
   }

   protected boolean eG() {
      if (this.l.M() && !this.l.v) {
         float _snowman = this.aR();
         fx _snowmanx = this.ct() instanceof bhn
            ? new fx(this.cD(), (double)Math.round(this.cE()), this.cH()).b()
            : new fx(this.cD(), (double)Math.round(this.cE()), this.cH());
         if (_snowman > 0.5F && this.J.nextFloat() * 30.0F < (_snowman - 0.4F) * 2.0F && this.l.e(_snowmanx)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected void c(ael<cuw> var1) {
      if (this.x().r()) {
         super.c(_snowman);
      } else {
         this.f(this.cC().b(0.0, 0.3, 0.0));
      }
   }

   @Override
   protected void bN() {
      super.bN();
      this.a(true, false);
   }
}
