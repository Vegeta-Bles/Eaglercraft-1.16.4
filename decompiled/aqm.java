import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class aqm extends aqa {
   private static final UUID b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final UUID c = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
   private static final arj d = new arj(b, "Sprinting speed boost", 0.3F, arj.a.c);
   protected static final us<Byte> ag = uv.a(aqm.class, uu.a);
   private static final us<Float> e = uv.a(aqm.class, uu.c);
   private static final us<Integer> f = uv.a(aqm.class, uu.b);
   private static final us<Boolean> g = uv.a(aqm.class, uu.i);
   private static final us<Integer> bh = uv.a(aqm.class, uu.b);
   private static final us<Integer> bi = uv.a(aqm.class, uu.b);
   private static final us<Optional<fx>> bj = uv.a(aqm.class, uu.m);
   protected static final aqb ah = aqb.c(0.2F, 0.2F);
   private final ari bk;
   private final apj bl = new apj(this);
   private final Map<aps, apu> bm = Maps.newHashMap();
   private final gj<bmb> bn = gj.a(2, bmb.b);
   private final gj<bmb> bo = gj.a(4, bmb.b);
   public boolean ai;
   public aot aj;
   public int ak;
   public int al;
   public int am;
   public int an;
   public int ao;
   public float ap;
   public int aq;
   public float ar;
   public float as;
   protected int at;
   public float au;
   public float av;
   public float aw;
   public final int ax = 20;
   public final float ay;
   public final float az;
   public float aA;
   public float aB;
   public float aC;
   public float aD;
   public float aE = 0.02F;
   @Nullable
   protected bfw aF;
   protected int aG;
   protected boolean aH;
   protected int aI;
   protected float aJ;
   protected float aK;
   protected float aL;
   protected float aM;
   protected float aN;
   protected int aO;
   protected float aP;
   protected boolean aQ;
   public float aR;
   public float aS;
   public float aT;
   protected int aU;
   protected double aV;
   protected double aW;
   protected double aX;
   protected double aY;
   protected double aZ;
   protected double ba;
   protected int bb;
   private boolean bp = true;
   @Nullable
   private aqm bq;
   private int br;
   private aqm bs;
   private int bt;
   private float bu;
   private int bv;
   private float bw;
   protected bmb bc = bmb.b;
   protected int bd;
   protected int be;
   private fx bx;
   private Optional<fx> by = Optional.empty();
   private apk bz;
   private long bA;
   protected int bf;
   private float bB;
   private float bC;
   protected arf<?> bg;

   protected aqm(aqe<? extends aqm> var1, brx var2) {
      super(_snowman, _snowman);
      this.bk = new ari(arm.a(_snowman));
      this.c(this.dx());
      this.i = true;
      this.az = (float)((Math.random() + 1.0) * 0.01F);
      this.af();
      this.ay = (float)Math.random() * 12398.0F;
      this.p = (float)(Math.random() * (float) (Math.PI * 2));
      this.aC = this.p;
      this.G = 0.6F;
      mo _snowman = mo.a;
      this.bg = this.a(new Dynamic(_snowman, _snowman.createMap(ImmutableMap.of(_snowman.a("memories"), _snowman.emptyMap()))));
   }

   public arf<?> cJ() {
      return this.bg;
   }

   protected arf.b<?> cK() {
      return arf.a(ImmutableList.of(), ImmutableList.of());
   }

   protected arf<?> a(Dynamic<?> var1) {
      return this.cK().a(_snowman);
   }

   @Override
   public void aa() {
      this.a(apk.m, Float.MAX_VALUE);
   }

   public boolean a(aqe<?> var1) {
      return true;
   }

   @Override
   protected void e() {
      this.R.a(ag, (byte)0);
      this.R.a(f, 0);
      this.R.a(g, false);
      this.R.a(bh, 0);
      this.R.a(bi, 0);
      this.R.a(e, 1.0F);
      this.R.a(bj, Optional.empty());
   }

   public static ark.a cL() {
      return ark.a().a(arl.a).a(arl.c).a(arl.d).a(arl.i).a(arl.j);
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
      if (!this.aE()) {
         this.aL();
      }

      if (!this.l.v && _snowman && this.C > 0.0F) {
         this.cQ();
         this.cR();
      }

      if (!this.l.v && this.C > 3.0F && _snowman) {
         float _snowman = (float)afm.f(this.C - 3.0F);
         if (!_snowman.g()) {
            double _snowmanx = Math.min((double)(0.2F + _snowman / 15.0F), 2.5);
            int _snowmanxx = (int)(150.0 * _snowmanx);
            ((aag)this.l).a(new hc(hh.d, _snowman), this.cD(), this.cE(), this.cH(), _snowmanxx, 0.0, 0.0, 0.0, 0.15F);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   public boolean cM() {
      return this.dC() == aqq.b;
   }

   public float a(float var1) {
      return afm.g(_snowman, this.bC, this.bB);
   }

   @Override
   public void ag() {
      this.ar = this.as;
      if (this.Q) {
         this.ek().ifPresent(this::a);
      }

      if (this.cN()) {
         this.cO();
      }

      super.ag();
      this.l.Z().a("livingEntityBaseTick");
      boolean _snowman = this instanceof bfw;
      if (this.aX()) {
         if (this.aY()) {
            this.a(apk.f, 1.0F);
         } else if (_snowman && !this.l.f().a(this.cc())) {
            double _snowmanx = this.l.f().a(this) + this.l.f().n();
            if (_snowmanx < 0.0) {
               double _snowmanxx = this.l.f().o();
               if (_snowmanxx > 0.0) {
                  this.a(apk.f, (float)Math.max(1, afm.c(-_snowmanx * _snowmanxx)));
               }
            }
         }
      }

      if (this.aD() || this.l.v) {
         this.am();
      }

      boolean _snowmanx = _snowman && ((bfw)this).bC.a;
      if (this.aX()) {
         if (this.a(aef.b) && !this.l.d_(new fx(this.cD(), this.cG(), this.cH())).a(bup.lc)) {
            if (!this.cM() && !apv.c(this) && !_snowmanx) {
               this.j(this.l(this.bI()));
               if (this.bI() == -20) {
                  this.j(0);
                  dcn _snowmanxx = this.cC();

                  for (int _snowmanxxx = 0; _snowmanxxx < 8; _snowmanxxx++) {
                     double _snowmanxxxx = this.J.nextDouble() - this.J.nextDouble();
                     double _snowmanxxxxx = this.J.nextDouble() - this.J.nextDouble();
                     double _snowmanxxxxxx = this.J.nextDouble() - this.J.nextDouble();
                     this.l.a(hh.e, this.cD() + _snowmanxxxx, this.cE() + _snowmanxxxxx, this.cH() + _snowmanxxxxxx, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d);
                  }

                  this.a(apk.h, 2.0F);
               }
            }

            if (!this.l.v && this.br() && this.ct() != null && !this.ct().bt()) {
               this.l();
            }
         } else if (this.bI() < this.bH()) {
            this.j(this.m(this.bI()));
         }

         if (!this.l.v) {
            fx _snowmanxx = this.cB();
            if (!Objects.equal(this.bx, _snowmanxx)) {
               this.bx = _snowmanxx;
               this.c(_snowmanxx);
            }
         }
      }

      if (this.aX() && this.aG()) {
         this.am();
      }

      if (this.an > 0) {
         this.an--;
      }

      if (this.P > 0 && !(this instanceof aah)) {
         this.P--;
      }

      if (this.dl()) {
         this.cU();
      }

      if (this.aG > 0) {
         this.aG--;
      } else {
         this.aF = null;
      }

      if (this.bs != null && !this.bs.aX()) {
         this.bs = null;
      }

      if (this.bq != null) {
         if (!this.bq.aX()) {
            this.a(null);
         } else if (this.K - this.br > 100) {
            this.a(null);
         }
      }

      this.de();
      this.aM = this.aL;
      this.aB = this.aA;
      this.aD = this.aC;
      this.r = this.p;
      this.s = this.q;
      this.l.Z().c();
   }

   public boolean cN() {
      return this.K % 5 == 0 && this.cC().b != 0.0 && this.cC().d != 0.0 && !this.a_() && bpu.j(this) && this.cP();
   }

   protected void cO() {
      dcn _snowman = this.cC();
      this.l
         .a(
            hh.C,
            this.cD() + (this.J.nextDouble() - 0.5) * (double)this.cy(),
            this.cE() + 0.1,
            this.cH() + (this.J.nextDouble() - 0.5) * (double)this.cy(),
            _snowman.b * -0.2,
            0.1,
            _snowman.d * -0.2
         );
      float _snowmanx = this.J.nextFloat() * 0.4F + this.J.nextFloat() > 0.9F ? 0.6F : 0.0F;
      this.a(adq.nZ, _snowmanx, 0.6F + this.J.nextFloat() * 0.4F);
   }

   protected boolean cP() {
      return this.l.d_(this.as()).a(aed.ar);
   }

   @Override
   protected float ar() {
      return this.cP() && bpu.a(bpw.l, this) > 0 ? 1.0F : super.ar();
   }

   protected boolean b(ceh var1) {
      return !_snowman.g() || this.ef();
   }

   protected void cQ() {
      arh _snowman = this.a(arl.d);
      if (_snowman != null) {
         if (_snowman.a(c) != null) {
            _snowman.b(c);
         }
      }
   }

   protected void cR() {
      if (!this.aN().g()) {
         int _snowman = bpu.a(bpw.l, this);
         if (_snowman > 0 && this.cP()) {
            arh _snowmanx = this.a(arl.d);
            if (_snowmanx == null) {
               return;
            }

            _snowmanx.b(new arj(c, "Soul speed boost", (double)(0.03F * (1.0F + (float)_snowman * 0.35F)), arj.a.a));
            if (this.cY().nextFloat() < 0.04F) {
               bmb _snowmanxx = this.b(aqf.c);
               _snowmanxx.a(1, this, var0 -> var0.c(aqf.c));
            }
         }
      }
   }

   protected void c(fx var1) {
      int _snowman = bpu.a(bpw.j, this);
      if (_snowman > 0) {
         bpz.a(this, this.l, _snowman, _snowman);
      }

      if (this.b(this.aN())) {
         this.cQ();
      }

      this.cR();
   }

   public boolean w_() {
      return false;
   }

   public float cS() {
      return this.w_() ? 0.5F : 1.0F;
   }

   protected boolean cT() {
      return true;
   }

   @Override
   public boolean bt() {
      return false;
   }

   protected void cU() {
      this.aq++;
      if (this.aq == 20) {
         this.ad();

         for (int _snowman = 0; _snowman < 20; _snowman++) {
            double _snowmanx = this.J.nextGaussian() * 0.02;
            double _snowmanxx = this.J.nextGaussian() * 0.02;
            double _snowmanxxx = this.J.nextGaussian() * 0.02;
            this.l.a(hh.P, this.d(1.0), this.cF(), this.g(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
         }
      }
   }

   protected boolean cV() {
      return !this.w_();
   }

   protected boolean cW() {
      return !this.w_();
   }

   protected int l(int var1) {
      int _snowman = bpu.d(this);
      return _snowman > 0 && this.J.nextInt(_snowman + 1) > 0 ? _snowman : _snowman - 1;
   }

   protected int m(int var1) {
      return Math.min(_snowman + 4, this.bH());
   }

   protected int d(bfw var1) {
      return 0;
   }

   protected boolean cX() {
      return false;
   }

   public Random cY() {
      return this.J;
   }

   @Nullable
   public aqm cZ() {
      return this.bq;
   }

   public int da() {
      return this.br;
   }

   public void e(@Nullable bfw var1) {
      this.aF = _snowman;
      this.aG = this.K;
   }

   public void a(@Nullable aqm var1) {
      this.bq = _snowman;
      this.br = this.K;
   }

   @Nullable
   public aqm db() {
      return this.bs;
   }

   public int dc() {
      return this.bt;
   }

   public void z(aqa var1) {
      if (_snowman instanceof aqm) {
         this.bs = (aqm)_snowman;
      } else {
         this.bs = null;
      }

      this.bt = this.K;
   }

   public int dd() {
      return this.aI;
   }

   public void n(int var1) {
      this.aI = _snowman;
   }

   protected void b(bmb var1) {
      if (!_snowman.a()) {
         adp _snowman = adq.M;
         blx _snowmanx = _snowman.b();
         if (_snowmanx instanceof bjy) {
            _snowman = ((bjy)_snowmanx).ab_().b();
         } else if (_snowmanx == bmd.qo) {
            _snowman = adq.L;
         }

         this.a(_snowman, 1.0F, 1.0F);
      }
   }

   @Override
   public void b(md var1) {
      _snowman.a("Health", this.dk());
      _snowman.a("HurtTime", (short)this.an);
      _snowman.b("HurtByTimestamp", this.br);
      _snowman.a("DeathTime", (short)this.aq);
      _snowman.a("AbsorptionAmount", this.dT());
      _snowman.a("Attributes", this.dB().c());
      if (!this.bm.isEmpty()) {
         mj _snowman = new mj();

         for (apu _snowmanx : this.bm.values()) {
            _snowman.add(_snowmanx.a(new md()));
         }

         _snowman.a("ActiveEffects", _snowman);
      }

      _snowman.a("FallFlying", this.ef());
      this.ek().ifPresent(var1x -> {
         _snowman.b("SleepingX", var1x.u());
         _snowman.b("SleepingY", var1x.v());
         _snowman.b("SleepingZ", var1x.w());
      });
      DataResult<mt> _snowman = this.bg.a(mo.a);
      _snowman.resultOrPartial(h::error).ifPresent(var1x -> _snowman.a("Brain", var1x));
   }

   @Override
   public void a(md var1) {
      this.s(_snowman.j("AbsorptionAmount"));
      if (_snowman.c("Attributes", 9) && this.l != null && !this.l.v) {
         this.dB().a(_snowman.d("Attributes", 10));
      }

      if (_snowman.c("ActiveEffects", 9)) {
         mj _snowman = _snowman.d("ActiveEffects", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            md _snowmanxx = _snowman.a(_snowmanx);
            apu _snowmanxxx = apu.b(_snowmanxx);
            if (_snowmanxxx != null) {
               this.bm.put(_snowmanxxx.a(), _snowmanxxx);
            }
         }
      }

      if (_snowman.c("Health", 99)) {
         this.c(_snowman.j("Health"));
      }

      this.an = _snowman.g("HurtTime");
      this.aq = _snowman.g("DeathTime");
      this.br = _snowman.h("HurtByTimestamp");
      if (_snowman.c("Team", 8)) {
         String _snowman = _snowman.l("Team");
         ddl _snowmanxx = this.l.G().f(_snowman);
         boolean _snowmanxxx = _snowmanxx != null && this.l.G().a(this.bT(), _snowmanxx);
         if (!_snowmanxxx) {
            h.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", _snowman);
         }
      }

      if (_snowman.q("FallFlying")) {
         this.b(7, true);
      }

      if (_snowman.c("SleepingX", 99) && _snowman.c("SleepingY", 99) && _snowman.c("SleepingZ", 99)) {
         fx _snowman = new fx(_snowman.h("SleepingX"), _snowman.h("SleepingY"), _snowman.h("SleepingZ"));
         this.e(_snowman);
         this.R.b(T, aqx.c);
         if (!this.Q) {
            this.a(_snowman);
         }
      }

      if (_snowman.c("Brain", 10)) {
         this.bg = this.a(new Dynamic(mo.a, _snowman.c("Brain")));
      }
   }

   protected void de() {
      Iterator<aps> _snowman = this.bm.keySet().iterator();

      try {
         while (_snowman.hasNext()) {
            aps _snowmanx = _snowman.next();
            apu _snowmanxx = this.bm.get(_snowmanx);
            if (!_snowmanxx.a(this, () -> this.a(_snowman, true))) {
               if (!this.l.v) {
                  _snowman.remove();
                  this.b(_snowmanxx);
               }
            } else if (_snowmanxx.b() % 600 == 0) {
               this.a(_snowmanxx, false);
            }
         }
      } catch (ConcurrentModificationException var11) {
      }

      if (this.bp) {
         if (!this.l.v) {
            this.C();
         }

         this.bp = false;
      }

      int _snowmanx = this.R.a(f);
      boolean _snowmanxx = this.R.a(g);
      if (_snowmanx > 0) {
         boolean _snowmanxxx;
         if (this.bF()) {
            _snowmanxxx = this.J.nextInt(15) == 0;
         } else {
            _snowmanxxx = this.J.nextBoolean();
         }

         if (_snowmanxx) {
            _snowmanxxx &= this.J.nextInt(5) == 0;
         }

         if (_snowmanxxx && _snowmanx > 0) {
            double _snowmanxxxx = (double)(_snowmanx >> 16 & 0xFF) / 255.0;
            double _snowmanxxxxx = (double)(_snowmanx >> 8 & 0xFF) / 255.0;
            double _snowmanxxxxxx = (double)(_snowmanx >> 0 & 0xFF) / 255.0;
            this.l.a(_snowmanxx ? hh.a : hh.u, this.d(0.5), this.cF(), this.g(0.5), _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
         }
      }
   }

   protected void C() {
      if (this.bm.isEmpty()) {
         this.df();
         this.j(false);
      } else {
         Collection<apu> _snowman = this.bm.values();
         this.R.b(g, c(_snowman));
         this.R.b(f, bnv.a(_snowman));
         this.j(this.a(apw.n));
      }
   }

   public double A(@Nullable aqa var1) {
      double _snowman = 1.0;
      if (this.bx()) {
         _snowman *= 0.8;
      }

      if (this.bF()) {
         float _snowmanx = this.dF();
         if (_snowmanx < 0.1F) {
            _snowmanx = 0.1F;
         }

         _snowman *= 0.7 * (double)_snowmanx;
      }

      if (_snowman != null) {
         bmb _snowmanx = this.b(aqf.f);
         blx _snowmanxx = _snowmanx.b();
         aqe<?> _snowmanxxx = _snowman.X();
         if (_snowmanxxx == aqe.av && _snowmanxx == bmd.pe || _snowmanxxx == aqe.aY && _snowmanxx == bmd.ph || _snowmanxxx == aqe.m && _snowmanxx == bmd.pi) {
            _snowman *= 0.5;
         }
      }

      return _snowman;
   }

   public boolean c(aqm var1) {
      return true;
   }

   public boolean a(aqm var1, azg var2) {
      return _snowman.a(this, _snowman);
   }

   public static boolean c(Collection<apu> var0) {
      for (apu _snowman : _snowman) {
         if (!_snowman.d()) {
            return false;
         }
      }

      return true;
   }

   protected void df() {
      this.R.b(g, false);
      this.R.b(f, 0);
   }

   public boolean dg() {
      if (this.l.v) {
         return false;
      } else {
         Iterator<apu> _snowman = this.bm.values().iterator();

         boolean _snowmanx;
         for (_snowmanx = false; _snowman.hasNext(); _snowmanx = true) {
            this.b(_snowman.next());
            _snowman.remove();
         }

         return _snowmanx;
      }
   }

   public Collection<apu> dh() {
      return this.bm.values();
   }

   public Map<aps, apu> di() {
      return this.bm;
   }

   public boolean a(aps var1) {
      return this.bm.containsKey(_snowman);
   }

   @Nullable
   public apu b(aps var1) {
      return this.bm.get(_snowman);
   }

   public boolean c(apu var1) {
      if (!this.d(_snowman)) {
         return false;
      } else {
         apu _snowman = this.bm.get(_snowman.a());
         if (_snowman == null) {
            this.bm.put(_snowman.a(), _snowman);
            this.a(_snowman);
            return true;
         } else if (_snowman.b(_snowman)) {
            this.a(_snowman, true);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean d(apu var1) {
      if (this.dC() == aqq.b) {
         aps _snowman = _snowman.a();
         if (_snowman == apw.j || _snowman == apw.s) {
            return false;
         }
      }

      return true;
   }

   public void e(apu var1) {
      if (this.d(_snowman)) {
         apu _snowman = this.bm.put(_snowman.a(), _snowman);
         if (_snowman == null) {
            this.a(_snowman);
         } else {
            this.a(_snowman, true);
         }
      }
   }

   public boolean dj() {
      return this.dC() == aqq.b;
   }

   @Nullable
   public apu c(@Nullable aps var1) {
      return this.bm.remove(_snowman);
   }

   public boolean d(aps var1) {
      apu _snowman = this.c(_snowman);
      if (_snowman != null) {
         this.b(_snowman);
         return true;
      } else {
         return false;
      }
   }

   protected void a(apu var1) {
      this.bp = true;
      if (!this.l.v) {
         _snowman.a().b(this, this.dB(), _snowman.c());
      }
   }

   protected void a(apu var1, boolean var2) {
      this.bp = true;
      if (_snowman && !this.l.v) {
         aps _snowman = _snowman.a();
         _snowman.a(this, this.dB(), _snowman.c());
         _snowman.b(this, this.dB(), _snowman.c());
      }
   }

   protected void b(apu var1) {
      this.bp = true;
      if (!this.l.v) {
         _snowman.a().a(this, this.dB(), _snowman.c());
      }
   }

   public void b(float var1) {
      float _snowman = this.dk();
      if (_snowman > 0.0F) {
         this.c(_snowman + _snowman);
      }
   }

   public float dk() {
      return this.R.a(e);
   }

   public void c(float var1) {
      this.R.b(e, afm.a(_snowman, 0.0F, this.dx()));
   }

   public boolean dl() {
      return this.dk() <= 0.0F;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (this.l.v) {
         return false;
      } else if (this.dl()) {
         return false;
      } else if (_snowman.p() && this.a(apw.l)) {
         return false;
      } else {
         if (this.em() && !this.l.v) {
            this.en();
         }

         this.aI = 0;
         float _snowman = _snowman;
         if ((_snowman == apk.q || _snowman == apk.r) && !this.b(aqf.f).a()) {
            this.b(aqf.f).a((int)(_snowman * 4.0F + this.J.nextFloat() * _snowman * 2.0F), this, var0 -> var0.c(aqf.f));
            _snowman *= 0.75F;
         }

         boolean _snowmanx = false;
         float _snowmanxx = 0.0F;
         if (_snowman > 0.0F && this.g(_snowman)) {
            this.p(_snowman);
            _snowmanxx = _snowman;
            _snowman = 0.0F;
            if (!_snowman.b()) {
               aqa _snowmanxxx = _snowman.j();
               if (_snowmanxxx instanceof aqm) {
                  this.d((aqm)_snowmanxxx);
               }
            }

            _snowmanx = true;
         }

         this.av = 1.5F;
         boolean _snowmanxxx = true;
         if ((float)this.P > 10.0F) {
            if (_snowman <= this.aP) {
               return false;
            }

            this.e(_snowman, _snowman - this.aP);
            this.aP = _snowman;
            _snowmanxxx = false;
         } else {
            this.aP = _snowman;
            this.P = 20;
            this.e(_snowman, _snowman);
            this.ao = 10;
            this.an = this.ao;
         }

         this.ap = 0.0F;
         aqa _snowmanxxxx = _snowman.k();
         if (_snowmanxxxx != null) {
            if (_snowmanxxxx instanceof aqm) {
               this.a((aqm)_snowmanxxxx);
            }

            if (_snowmanxxxx instanceof bfw) {
               this.aG = 100;
               this.aF = (bfw)_snowmanxxxx;
            } else if (_snowmanxxxx instanceof baz) {
               baz _snowmanxxxxx = (baz)_snowmanxxxx;
               if (_snowmanxxxxx.eK()) {
                  this.aG = 100;
                  aqm _snowmanxxxxxx = _snowmanxxxxx.eN();
                  if (_snowmanxxxxxx != null && _snowmanxxxxxx.X() == aqe.bc) {
                     this.aF = (bfw)_snowmanxxxxxx;
                  } else {
                     this.aF = null;
                  }
               }
            }
         }

         if (_snowmanxxx) {
            if (_snowmanx) {
               this.l.a(this, (byte)29);
            } else if (_snowman instanceof apl && ((apl)_snowman).y()) {
               this.l.a(this, (byte)33);
            } else {
               byte _snowmanxxxxx;
               if (_snowman == apk.h) {
                  _snowmanxxxxx = 36;
               } else if (_snowman.p()) {
                  _snowmanxxxxx = 37;
               } else if (_snowman == apk.u) {
                  _snowmanxxxxx = 44;
               } else {
                  _snowmanxxxxx = 2;
               }

               this.l.a(this, _snowmanxxxxx);
            }

            if (_snowman != apk.h && (!_snowmanx || _snowman > 0.0F)) {
               this.aS();
            }

            if (_snowmanxxxx != null) {
               double _snowmanxxxxx = _snowmanxxxx.cD() - this.cD();

               double _snowmanxxxxxx;
               for (_snowmanxxxxxx = _snowmanxxxx.cH() - this.cH(); _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx < 1.0E-4; _snowmanxxxxxx = (Math.random() - Math.random()) * 0.01) {
                  _snowmanxxxxx = (Math.random() - Math.random()) * 0.01;
               }

               this.ap = (float)(afm.d(_snowmanxxxxxx, _snowmanxxxxx) * 180.0F / (float)Math.PI - (double)this.p);
               this.a(0.4F, _snowmanxxxxx, _snowmanxxxxxx);
            } else {
               this.ap = (float)((int)(Math.random() * 2.0) * 180);
            }
         }

         if (this.dl()) {
            if (!this.f(_snowman)) {
               adp _snowmanxxxxx = this.dq();
               if (_snowmanxxx && _snowmanxxxxx != null) {
                  this.a(_snowmanxxxxx, this.dG(), this.dH());
               }

               this.a(_snowman);
            }
         } else if (_snowmanxxx) {
            this.c(_snowman);
         }

         boolean _snowmanxxxxx = !_snowmanx || _snowman > 0.0F;
         if (_snowmanxxxxx) {
            this.bz = _snowman;
            this.bA = this.l.T();
         }

         if (this instanceof aah) {
            ac.h.a((aah)this, _snowman, _snowman, _snowman, _snowmanx);
            if (_snowmanxx > 0.0F && _snowmanxx < 3.4028235E37F) {
               ((aah)this).a(aea.J, Math.round(_snowmanxx * 10.0F));
            }
         }

         if (_snowmanxxxx instanceof aah) {
            ac.g.a((aah)_snowmanxxxx, this, _snowman, _snowman, _snowman, _snowmanx);
         }

         return _snowmanxxxxx;
      }
   }

   protected void d(aqm var1) {
      _snowman.e(this);
   }

   protected void e(aqm var1) {
      _snowman.a(0.5F, _snowman.cD() - this.cD(), _snowman.cH() - this.cH());
   }

   private boolean f(apk var1) {
      if (_snowman.h()) {
         return false;
      } else {
         bmb _snowman = null;

         for (aot _snowmanx : aot.values()) {
            bmb _snowmanxx = this.b(_snowmanx);
            if (_snowmanxx.b() == bmd.qu) {
               _snowman = _snowmanxx.i();
               _snowmanxx.g(1);
               break;
            }
         }

         if (_snowman != null) {
            if (this instanceof aah) {
               aah _snowmanxx = (aah)this;
               _snowmanxx.b(aea.c.b(bmd.qu));
               ac.B.a(_snowmanxx, _snowman);
            }

            this.c(1.0F);
            this.dg();
            this.c(new apu(apw.j, 900, 1));
            this.c(new apu(apw.v, 100, 1));
            this.c(new apu(apw.l, 800, 0));
            this.l.a(this, (byte)35);
         }

         return _snowman != null;
      }
   }

   @Nullable
   public apk dm() {
      if (this.l.T() - this.bA > 40L) {
         this.bz = null;
      }

      return this.bz;
   }

   protected void c(apk var1) {
      adp _snowman = this.e(_snowman);
      if (_snowman != null) {
         this.a(_snowman, this.dG(), this.dH());
      }
   }

   private boolean g(apk var1) {
      aqa _snowman = _snowman.j();
      boolean _snowmanx = false;
      if (_snowman instanceof bga) {
         bga _snowmanxx = (bga)_snowman;
         if (_snowmanxx.r() > 0) {
            _snowmanx = true;
         }
      }

      if (!_snowman.f() && this.ed() && !_snowmanx) {
         dcn _snowmanxx = _snowman.w();
         if (_snowmanxx != null) {
            dcn _snowmanxxx = this.f(1.0F);
            dcn _snowmanxxxx = _snowmanxx.a(this.cA()).d();
            _snowmanxxxx = new dcn(_snowmanxxxx.b, 0.0, _snowmanxxxx.d);
            if (_snowmanxxxx.b(_snowmanxxx) < 0.0) {
               return true;
            }
         }
      }

      return false;
   }

   private void g(bmb var1) {
      if (!_snowman.a()) {
         if (!this.aA()) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.gK, this.cu(), 0.8F, 0.8F + this.l.t.nextFloat() * 0.4F, false);
         }

         this.a(_snowman, 5);
      }
   }

   public void a(apk var1) {
      if (!this.y && !this.aH) {
         aqa _snowman = _snowman.k();
         aqm _snowmanx = this.dw();
         if (this.aO >= 0 && _snowmanx != null) {
            _snowmanx.a(this, this.aO, _snowman);
         }

         if (this.em()) {
            this.en();
         }

         this.aH = true;
         this.dv().g();
         if (this.l instanceof aag) {
            if (_snowman != null) {
               _snowman.a((aag)this.l, this);
            }

            this.d(_snowman);
            this.f(_snowmanx);
         }

         this.l.a(this, (byte)3);
         this.b(aqx.g);
      }
   }

   protected void f(@Nullable aqm var1) {
      if (!this.l.v) {
         boolean _snowman = false;
         if (_snowman instanceof bcl) {
            if (this.l.V().b(brt.b)) {
               fx _snowmanx = this.cB();
               ceh _snowmanxx = bup.bA.n();
               if (this.l.d_(_snowmanx).g() && _snowmanxx.a((brz)this.l, _snowmanx)) {
                  this.l.a(_snowmanx, _snowmanxx, 3);
                  _snowman = true;
               }
            }

            if (!_snowman) {
               bcv _snowmanx = new bcv(this.l, this.cD(), this.cE(), this.cH(), new bmb(bmd.bt));
               this.l.c(_snowmanx);
            }
         }
      }
   }

   protected void d(apk var1) {
      aqa _snowman = _snowman.k();
      int _snowmanx;
      if (_snowman instanceof bfw) {
         _snowmanx = bpu.g((aqm)_snowman);
      } else {
         _snowmanx = 0;
      }

      boolean _snowmanxx = this.aG > 0;
      if (this.cW() && this.l.V().b(brt.e)) {
         this.a(_snowman, _snowmanxx);
         this.a(_snowman, _snowmanx, _snowmanxx);
      }

      this.dn();
      this.do_();
   }

   protected void dn() {
   }

   protected void do_() {
      if (!this.l.v && (this.cX() || this.aG > 0 && this.cV() && this.l.V().b(brt.e))) {
         int _snowman = this.d(this.aF);

         while (_snowman > 0) {
            int _snowmanx = aqg.a(_snowman);
            _snowman -= _snowmanx;
            this.l.c(new aqg(this.l, this.cD(), this.cE(), this.cH(), _snowmanx));
         }
      }
   }

   protected void a(apk var1, int var2, boolean var3) {
   }

   public vk dp() {
      return this.X().i();
   }

   protected void a(apk var1, boolean var2) {
      vk _snowman = this.dp();
      cyy _snowmanx = this.l.l().aJ().a(_snowman);
      cyv.a _snowmanxx = this.a(_snowman, _snowman);
      _snowmanx.b(_snowmanxx.a(dbb.f), this::a);
   }

   protected cyv.a a(boolean var1, apk var2) {
      cyv.a _snowman = new cyv.a((aag)this.l).a(this.J).a(dbc.a, this).a(dbc.f, this.cA()).a(dbc.c, _snowman).b(dbc.d, _snowman.k()).b(dbc.e, _snowman.j());
      if (_snowman && this.aF != null) {
         _snowman = _snowman.a(dbc.b, this.aF).a(this.aF.eU());
      }

      return _snowman;
   }

   public void a(float var1, double var2, double var4) {
      _snowman = (float)((double)_snowman * (1.0 - this.b(arl.c)));
      if (!(_snowman <= 0.0F)) {
         this.Z = true;
         dcn _snowman = this.cC();
         dcn _snowmanx = new dcn(_snowman, 0.0, _snowman).d().a((double)_snowman);
         this.n(_snowman.b / 2.0 - _snowmanx.b, this.t ? Math.min(0.4, _snowman.c / 2.0 + (double)_snowman) : _snowman.c, _snowman.d / 2.0 - _snowmanx.d);
      }
   }

   @Nullable
   protected adp e(apk var1) {
      return adq.eN;
   }

   @Nullable
   protected adp dq() {
      return adq.eI;
   }

   protected adp o(int var1) {
      return _snowman > 4 ? adq.eG : adq.eO;
   }

   protected adp c(bmb var1) {
      return _snowman.G();
   }

   public adp d(bmb var1) {
      return _snowman.H();
   }

   @Override
   public void c(boolean var1) {
      super.c(_snowman);
      if (_snowman) {
         this.by = Optional.empty();
      }
   }

   public Optional<fx> dr() {
      return this.by;
   }

   public boolean c_() {
      if (this.a_()) {
         return false;
      } else {
         fx _snowman = this.cB();
         ceh _snowmanx = this.ds();
         buo _snowmanxx = _snowmanx.b();
         if (_snowmanxx.a(aed.at)) {
            this.by = Optional.of(_snowman);
            return true;
         } else if (_snowmanxx instanceof cbb && this.c(_snowman, _snowmanx)) {
            this.by = Optional.of(_snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   public ceh ds() {
      return this.l.d_(this.cB());
   }

   private boolean c(fx var1, ceh var2) {
      if (_snowman.c(cbb.a)) {
         ceh _snowman = this.l.d_(_snowman.c());
         if (_snowman.a(bup.cg) && _snowman.c(bxv.a) == _snowman.c(cbb.aq)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean aX() {
      return !this.y && this.dk() > 0.0F;
   }

   @Override
   public boolean b(float var1, float var2) {
      boolean _snowman = super.b(_snowman, _snowman);
      int _snowmanx = this.e(_snowman, _snowman);
      if (_snowmanx > 0) {
         this.a(this.o(_snowmanx), 1.0F, 1.0F);
         this.dt();
         this.a(apk.k, (float)_snowmanx);
         return true;
      } else {
         return _snowman;
      }
   }

   protected int e(float var1, float var2) {
      apu _snowman = this.b(apw.h);
      float _snowmanx = _snowman == null ? 0.0F : (float)(_snowman.c() + 1);
      return afm.f((_snowman - 3.0F - _snowmanx) * _snowman);
   }

   protected void dt() {
      if (!this.aA()) {
         int _snowman = afm.c(this.cD());
         int _snowmanx = afm.c(this.cE() - 0.2F);
         int _snowmanxx = afm.c(this.cH());
         ceh _snowmanxxx = this.l.d_(new fx(_snowman, _snowmanx, _snowmanxx));
         if (!_snowmanxxx.g()) {
            cae _snowmanxxxx = _snowmanxxx.o();
            this.a(_snowmanxxxx.g(), _snowmanxxxx.a() * 0.5F, _snowmanxxxx.b() * 0.75F);
         }
      }
   }

   @Override
   public void bm() {
      this.ao = 10;
      this.an = this.ao;
      this.ap = 0.0F;
   }

   public int du() {
      return afm.c(this.b(arl.i));
   }

   protected void b(apk var1, float var2) {
   }

   protected void p(float var1) {
   }

   protected float c(apk var1, float var2) {
      if (!_snowman.f()) {
         this.b(_snowman, _snowman);
         _snowman = api.a(_snowman, (float)this.du(), (float)this.b(arl.j));
      }

      return _snowman;
   }

   protected float d(apk var1, float var2) {
      if (_snowman.i()) {
         return _snowman;
      } else {
         if (this.a(apw.k) && _snowman != apk.m) {
            int _snowman = (this.b(apw.k).c() + 1) * 5;
            int _snowmanx = 25 - _snowman;
            float _snowmanxx = _snowman * (float)_snowmanx;
            float _snowmanxxx = _snowman;
            _snowman = Math.max(_snowmanxx / 25.0F, 0.0F);
            float _snowmanxxxx = _snowmanxxx - _snowman;
            if (_snowmanxxxx > 0.0F && _snowmanxxxx < 3.4028235E37F) {
               if (this instanceof aah) {
                  ((aah)this).a(aea.L, Math.round(_snowmanxxxx * 10.0F));
               } else if (_snowman.k() instanceof aah) {
                  ((aah)_snowman.k()).a(aea.H, Math.round(_snowmanxxxx * 10.0F));
               }
            }
         }

         if (_snowman <= 0.0F) {
            return 0.0F;
         } else {
            int _snowman = bpu.a(this.bo(), _snowman);
            if (_snowman > 0) {
               _snowman = api.a(_snowman, (float)_snowman);
            }

            return _snowman;
         }
      }
   }

   protected void e(apk var1, float var2) {
      if (!this.b(_snowman)) {
         _snowman = this.c(_snowman, _snowman);
         _snowman = this.d(_snowman, _snowman);
         float var8 = Math.max(_snowman - this.dT(), 0.0F);
         this.s(this.dT() - (_snowman - var8));
         float _snowman = _snowman - var8;
         if (_snowman > 0.0F && _snowman < 3.4028235E37F && _snowman.k() instanceof aah) {
            ((aah)_snowman.k()).a(aea.G, Math.round(_snowman * 10.0F));
         }

         if (var8 != 0.0F) {
            float _snowmanx = this.dk();
            this.c(_snowmanx - var8);
            this.dv().a(_snowman, _snowmanx, var8);
            this.s(this.dT() - var8);
         }
      }
   }

   public apj dv() {
      return this.bl;
   }

   @Nullable
   public aqm dw() {
      if (this.bl.c() != null) {
         return this.bl.c();
      } else if (this.aF != null) {
         return this.aF;
      } else {
         return this.bq != null ? this.bq : null;
      }
   }

   public final float dx() {
      return (float)this.b(arl.a);
   }

   public final int dy() {
      return this.R.a(bh);
   }

   public final void p(int var1) {
      this.R.b(bh, _snowman);
   }

   public final int dz() {
      return this.R.a(bi);
   }

   public final void q(int var1) {
      this.R.b(bi, _snowman);
   }

   private int m() {
      if (apv.a(this)) {
         return 6 - (1 + apv.b(this));
      } else {
         return this.a(apw.d) ? 6 + (1 + this.b(apw.d).c()) * 2 : 6;
      }
   }

   public void a(aot var1) {
      this.a(_snowman, false);
   }

   public void a(aot var1, boolean var2) {
      if (!this.ai || this.ak >= this.m() / 2 || this.ak < 0) {
         this.ak = -1;
         this.ai = true;
         this.aj = _snowman;
         if (this.l instanceof aag) {
            os _snowman = new os(this, _snowman == aot.a ? 0 : 3);
            aae _snowmanx = ((aag)this.l).i();
            if (_snowman) {
               _snowmanx.a(this, _snowman);
            } else {
               _snowmanx.b(this, _snowman);
            }
         }
      }
   }

   @Override
   public void a(byte var1) {
      switch (_snowman) {
         case 2:
         case 33:
         case 36:
         case 37:
         case 44:
            boolean _snowmanx = _snowman == 33;
            boolean _snowmanxx = _snowman == 36;
            boolean _snowmanxxx = _snowman == 37;
            boolean _snowmanxxxx = _snowman == 44;
            this.av = 1.5F;
            this.P = 20;
            this.ao = 10;
            this.an = this.ao;
            this.ap = 0.0F;
            if (_snowmanx) {
               this.a(adq.pa, this.dG(), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
            }

            apk _snowmanxxxxx;
            if (_snowmanxxx) {
               _snowmanxxxxx = apk.c;
            } else if (_snowmanxx) {
               _snowmanxxxxx = apk.h;
            } else if (_snowmanxxxx) {
               _snowmanxxxxx = apk.u;
            } else {
               _snowmanxxxxx = apk.n;
            }

            adp _snowmanxxxxxx = this.e(_snowmanxxxxx);
            if (_snowmanxxxxxx != null) {
               this.a(_snowmanxxxxxx, this.dG(), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
            }

            this.a(apk.n, 0.0F);
            break;
         case 3:
            adp _snowman = this.dq();
            if (_snowman != null) {
               this.a(_snowman, this.dG(), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
            }

            if (!(this instanceof bfw)) {
               this.c(0.0F);
               this.a(apk.n);
            }
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 31:
         case 32:
         case 34:
         case 35:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 45:
         case 53:
         default:
            super.a(_snowman);
            break;
         case 29:
            this.a(adq.mU, 1.0F, 0.8F + this.l.t.nextFloat() * 0.4F);
            break;
         case 30:
            this.a(adq.mV, 0.8F, 0.8F + this.l.t.nextFloat() * 0.4F);
            break;
         case 46:
            int _snowmanxxxxxx = 128;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 128; _snowmanxxxxxxx++) {
               double _snowmanxxxxxxxx = (double)_snowmanxxxxxxx / 127.0;
               float _snowmanxxxxxxxxx = (this.J.nextFloat() - 0.5F) * 0.2F;
               float _snowmanxxxxxxxxxx = (this.J.nextFloat() - 0.5F) * 0.2F;
               float _snowmanxxxxxxxxxxx = (this.J.nextFloat() - 0.5F) * 0.2F;
               double _snowmanxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxx, this.m, this.cD()) + (this.J.nextDouble() - 0.5) * (double)this.cy() * 2.0;
               double _snowmanxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxx, this.n, this.cE()) + this.J.nextDouble() * (double)this.cz();
               double _snowmanxxxxxxxxxxxxxx = afm.d(_snowmanxxxxxxxx, this.o, this.cH()) + (this.J.nextDouble() - 0.5) * (double)this.cy() * 2.0;
               this.l.a(hh.Q, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowmanxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx);
            }
            break;
         case 47:
            this.g(this.b(aqf.a));
            break;
         case 48:
            this.g(this.b(aqf.b));
            break;
         case 49:
            this.g(this.b(aqf.f));
            break;
         case 50:
            this.g(this.b(aqf.e));
            break;
         case 51:
            this.g(this.b(aqf.d));
            break;
         case 52:
            this.g(this.b(aqf.c));
            break;
         case 54:
            bxk.b(this);
            break;
         case 55:
            this.o();
      }
   }

   private void o() {
      bmb _snowman = this.b(aqf.b);
      this.a(aqf.b, this.b(aqf.a));
      this.a(aqf.a, _snowman);
   }

   @Override
   protected void an() {
      this.a(apk.m, 4.0F);
   }

   protected void dA() {
      int _snowman = this.m();
      if (this.ai) {
         this.ak++;
         if (this.ak >= _snowman) {
            this.ak = 0;
            this.ai = false;
         }
      } else {
         this.ak = 0;
      }

      this.as = (float)this.ak / (float)_snowman;
   }

   @Nullable
   public arh a(arg var1) {
      return this.dB().a(_snowman);
   }

   public double b(arg var1) {
      return this.dB().c(_snowman);
   }

   public double c(arg var1) {
      return this.dB().d(_snowman);
   }

   public ari dB() {
      return this.bk;
   }

   public aqq dC() {
      return aqq.a;
   }

   public bmb dD() {
      return this.b(aqf.a);
   }

   public bmb dE() {
      return this.b(aqf.b);
   }

   public boolean a(blx var1) {
      return this.a((Predicate<blx>)(var1x -> var1x == _snowman));
   }

   public boolean a(Predicate<blx> var1) {
      return _snowman.test(this.dD().b()) || _snowman.test(this.dE().b());
   }

   public bmb b(aot var1) {
      if (_snowman == aot.a) {
         return this.b(aqf.a);
      } else if (_snowman == aot.b) {
         return this.b(aqf.b);
      } else {
         throw new IllegalArgumentException("Invalid hand " + _snowman);
      }
   }

   public void a(aot var1, bmb var2) {
      if (_snowman == aot.a) {
         this.a(aqf.a, _snowman);
      } else {
         if (_snowman != aot.b) {
            throw new IllegalArgumentException("Invalid hand " + _snowman);
         }

         this.a(aqf.b, _snowman);
      }
   }

   public boolean a(aqf var1) {
      return !this.b(_snowman).a();
   }

   @Override
   public abstract Iterable<bmb> bo();

   public abstract bmb b(aqf var1);

   @Override
   public abstract void a(aqf var1, bmb var2);

   public float dF() {
      Iterable<bmb> _snowman = this.bo();
      int _snowmanx = 0;
      int _snowmanxx = 0;

      for (bmb _snowmanxxx : _snowman) {
         if (!_snowmanxxx.a()) {
            _snowmanxx++;
         }

         _snowmanx++;
      }

      return _snowmanx > 0 ? (float)_snowmanxx / (float)_snowmanx : 0.0F;
   }

   @Override
   public void g(boolean var1) {
      super.g(_snowman);
      arh _snowman = this.a(arl.d);
      if (_snowman.a(b) != null) {
         _snowman.d(d);
      }

      if (_snowman) {
         _snowman.b(d);
      }
   }

   protected float dG() {
      return 1.0F;
   }

   protected float dH() {
      return this.w_() ? (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.5F : (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean dI() {
      return this.dl();
   }

   @Override
   public void i(aqa var1) {
      if (!this.em()) {
         super.i(_snowman);
      }
   }

   private void a(aqa var1) {
      dcn _snowman;
      if (!_snowman.y && !this.l.d_(_snowman.cB()).b().a(aed.am)) {
         _snowman = _snowman.b(this);
      } else {
         _snowman = new dcn(_snowman.cD(), _snowman.cE() + (double)_snowman.cz(), _snowman.cH());
      }

      this.a(_snowman.b, _snowman.c, _snowman.d);
   }

   @Override
   public boolean bY() {
      return this.bX();
   }

   protected float dJ() {
      return 0.42F * this.aq();
   }

   protected void dK() {
      float _snowman = this.dJ();
      if (this.a(apw.h)) {
         _snowman += 0.1F * (float)(this.b(apw.h).c() + 1);
      }

      dcn _snowmanx = this.cC();
      this.n(_snowmanx.b, (double)_snowman, _snowmanx.d);
      if (this.bA()) {
         float _snowmanxx = this.p * (float) (Math.PI / 180.0);
         this.f(this.cC().b((double)(-afm.a(_snowmanxx) * 0.2F), 0.0, (double)(afm.b(_snowmanxx) * 0.2F)));
      }

      this.Z = true;
   }

   protected void dL() {
      this.f(this.cC().b(0.0, -0.04F, 0.0));
   }

   protected void c(ael<cuw> var1) {
      this.f(this.cC().b(0.0, 0.04F, 0.0));
   }

   protected float dM() {
      return 0.8F;
   }

   public boolean a(cuw var1) {
      return false;
   }

   public void g(dcn var1) {
      if (this.dS() || this.cs()) {
         double _snowman = 0.08;
         boolean _snowmanx = this.cC().c <= 0.0;
         if (_snowmanx && this.a(apw.B)) {
            _snowman = 0.01;
            this.C = 0.0F;
         }

         cux _snowmanxx = this.l.b(this.cB());
         if (this.aE() && this.cT() && !this.a(_snowmanxx.a())) {
            double _snowmanxxx = this.cE();
            float _snowmanxxxx = this.bA() ? 0.9F : this.dM();
            float _snowmanxxxxx = 0.02F;
            float _snowmanxxxxxx = (float)bpu.e(this);
            if (_snowmanxxxxxx > 3.0F) {
               _snowmanxxxxxx = 3.0F;
            }

            if (!this.t) {
               _snowmanxxxxxx *= 0.5F;
            }

            if (_snowmanxxxxxx > 0.0F) {
               _snowmanxxxx += (0.54600006F - _snowmanxxxx) * _snowmanxxxxxx / 3.0F;
               _snowmanxxxxx += (this.dN() - _snowmanxxxxx) * _snowmanxxxxxx / 3.0F;
            }

            if (this.a(apw.D)) {
               _snowmanxxxx = 0.96F;
            }

            this.a(_snowmanxxxxx, _snowman);
            this.a(aqr.a, this.cC());
            dcn _snowmanxxxxxxx = this.cC();
            if (this.u && this.c_()) {
               _snowmanxxxxxxx = new dcn(_snowmanxxxxxxx.b, 0.2, _snowmanxxxxxxx.d);
            }

            this.f(_snowmanxxxxxxx.d((double)_snowmanxxxx, 0.8F, (double)_snowmanxxxx));
            dcn _snowmanxxxxxxxx = this.a(_snowman, _snowmanx, this.cC());
            this.f(_snowmanxxxxxxxx);
            if (this.u && this.e(_snowmanxxxxxxxx.b, _snowmanxxxxxxxx.c + 0.6F - this.cE() + _snowmanxxx, _snowmanxxxxxxxx.d)) {
               this.n(_snowmanxxxxxxxx.b, 0.3F, _snowmanxxxxxxxx.d);
            }
         } else if (this.aQ() && this.cT() && !this.a(_snowmanxx.a())) {
            double _snowmanxxxxxxxx = this.cE();
            this.a(0.02F, _snowman);
            this.a(aqr.a, this.cC());
            if (this.b(aef.c) <= this.cx()) {
               this.f(this.cC().d(0.5, 0.8F, 0.5));
               dcn _snowmanxxxxxxxxx = this.a(_snowman, _snowmanx, this.cC());
               this.f(_snowmanxxxxxxxxx);
            } else {
               this.f(this.cC().a(0.5));
            }

            if (!this.aB()) {
               this.f(this.cC().b(0.0, -_snowman / 4.0, 0.0));
            }

            dcn _snowmanxxxxxxxxx = this.cC();
            if (this.u && this.e(_snowmanxxxxxxxxx.b, _snowmanxxxxxxxxx.c + 0.6F - this.cE() + _snowmanxxxxxxxx, _snowmanxxxxxxxxx.d)) {
               this.n(_snowmanxxxxxxxxx.b, 0.3F, _snowmanxxxxxxxxx.d);
            }
         } else if (this.ef()) {
            dcn _snowmanxxxxxxxxx = this.cC();
            if (_snowmanxxxxxxxxx.c > -0.5) {
               this.C = 1.0F;
            }

            dcn _snowmanxxxxxxxxxx = this.bh();
            float _snowmanxxxxxxxxxxx = this.q * (float) (Math.PI / 180.0);
            double _snowmanxxxxxxxxxxxx = Math.sqrt(_snowmanxxxxxxxxxx.b * _snowmanxxxxxxxxxx.b + _snowmanxxxxxxxxxx.d * _snowmanxxxxxxxxxx.d);
            double _snowmanxxxxxxxxxxxxx = Math.sqrt(c(_snowmanxxxxxxxxx));
            double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.f();
            float _snowmanxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxx * (double)_snowmanxxxxxxxxxxxxxxx * Math.min(1.0, _snowmanxxxxxxxxxxxxxx / 0.4));
            _snowmanxxxxxxxxx = this.cC().b(0.0, _snowman * (-1.0 + (double)_snowmanxxxxxxxxxxxxxxx * 0.75), 0.0);
            if (_snowmanxxxxxxxxx.c < 0.0 && _snowmanxxxxxxxxxxxx > 0.0) {
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.c * -0.1 * (double)_snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.b(
                  _snowmanxxxxxxxxxx.b * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx.d * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx
               );
            }

            if (_snowmanxxxxxxxxxxx < 0.0F && _snowmanxxxxxxxxxxxx > 0.0) {
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * (double)(-afm.a(_snowmanxxxxxxxxxxx)) * 0.04;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.b(
                  -_snowmanxxxxxxxxxx.b * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx * 3.2, -_snowmanxxxxxxxxxx.d * _snowmanxxxxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx
               );
            }

            if (_snowmanxxxxxxxxxxxx > 0.0) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.b(
                  (_snowmanxxxxxxxxxx.b / _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxx.b) * 0.1,
                  0.0,
                  (_snowmanxxxxxxxxxx.d / _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxx.d) * 0.1
               );
            }

            this.f(_snowmanxxxxxxxxx.d(0.99F, 0.98F, 0.99F));
            this.a(aqr.a, this.cC());
            if (this.u && !this.l.v) {
               double _snowmanxxxxxxxxxxxxxxxx = Math.sqrt(c(this.cC()));
               double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxx * 10.0 - 3.0);
               if (_snowmanxxxxxxxxxxxxxxxxxx > 0.0F) {
                  this.a(this.o((int)_snowmanxxxxxxxxxxxxxxxxxx), 1.0F, 1.0F);
                  this.a(apk.l, _snowmanxxxxxxxxxxxxxxxxxx);
               }
            }

            if (this.t && !this.l.v) {
               this.b(7, false);
            }
         } else {
            fx _snowmanxxxxxxxxxxxxxxxx = this.as();
            float _snowmanxxxxxxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxxxxxxxx).b().j();
            float _snowmanxxxxxxxxxxxxxxxxxx = this.t ? _snowmanxxxxxxxxxxxxxxxxx * 0.91F : 0.91F;
            dcn _snowmanxxxxxxxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.c;
            if (this.a(apw.y)) {
               _snowmanxxxxxxxxxxxxxxxxxxxx += (0.05 * (double)(this.b(apw.y).c() + 1) - _snowmanxxxxxxxxxxxxxxxxxxx.c) * 0.2;
               this.C = 0.0F;
            } else if (this.l.v && !this.l.C(_snowmanxxxxxxxxxxxxxxxx)) {
               if (this.cE() > 0.0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxx = -0.1;
               } else {
                  _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0;
               }
            } else if (!this.aB()) {
               _snowmanxxxxxxxxxxxxxxxxxxxx -= _snowman;
            }

            this.n(_snowmanxxxxxxxxxxxxxxxxxxx.b * (double)_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx * 0.98F, _snowmanxxxxxxxxxxxxxxxxxxx.d * (double)_snowmanxxxxxxxxxxxxxxxxxx);
         }
      }

      this.a(this, this instanceof bag);
   }

   public void a(aqm var1, boolean var2) {
      _snowman.au = _snowman.av;
      double _snowman = _snowman.cD() - _snowman.m;
      double _snowmanx = _snowman ? _snowman.cE() - _snowman.n : 0.0;
      double _snowmanxx = _snowman.cH() - _snowman.o;
      float _snowmanxxx = afm.a(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx) * 4.0F;
      if (_snowmanxxx > 1.0F) {
         _snowmanxxx = 1.0F;
      }

      _snowman.av = _snowman.av + (_snowmanxxx - _snowman.av) * 0.4F;
      _snowman.aw = _snowman.aw + _snowman.av;
   }

   public dcn a(dcn var1, float var2) {
      this.a(this.t(_snowman), _snowman);
      this.f(this.i(this.cC()));
      this.a(aqr.a, this.cC());
      dcn _snowman = this.cC();
      if ((this.u || this.aQ) && this.c_()) {
         _snowman = new dcn(_snowman.b, 0.2, _snowman.d);
      }

      return _snowman;
   }

   public dcn a(double var1, boolean var3, dcn var4) {
      if (!this.aB() && !this.bA()) {
         double _snowman;
         if (_snowman && Math.abs(_snowman.c - 0.005) >= 0.003 && Math.abs(_snowman.c - _snowman / 16.0) < 0.003) {
            _snowman = -0.003;
         } else {
            _snowman = _snowman.c - _snowman / 16.0;
         }

         return new dcn(_snowman.b, _snowman, _snowman.d);
      } else {
         return _snowman;
      }
   }

   private dcn i(dcn var1) {
      if (this.c_()) {
         this.C = 0.0F;
         float _snowman = 0.15F;
         double _snowmanx = afm.a(_snowman.b, -0.15F, 0.15F);
         double _snowmanxx = afm.a(_snowman.d, -0.15F, 0.15F);
         double _snowmanxxx = Math.max(_snowman.c, -0.15F);
         if (_snowmanxxx < 0.0 && !this.ds().a(bup.lQ) && this.ee() && this instanceof bfw) {
            _snowmanxxx = 0.0;
         }

         _snowman = new dcn(_snowmanx, _snowmanxxx, _snowmanxx);
      }

      return _snowman;
   }

   private float t(float var1) {
      return this.t ? this.dN() * (0.21600002F / (_snowman * _snowman * _snowman)) : this.aE;
   }

   public float dN() {
      return this.bu;
   }

   public void q(float var1) {
      this.bu = _snowman;
   }

   public boolean B(aqa var1) {
      this.z(_snowman);
      return false;
   }

   @Override
   public void j() {
      super.j();
      this.t();
      this.v();
      if (!this.l.v) {
         int _snowman = this.dy();
         if (_snowman > 0) {
            if (this.al <= 0) {
               this.al = 20 * (30 - _snowman);
            }

            this.al--;
            if (this.al <= 0) {
               this.p(_snowman - 1);
            }
         }

         int _snowmanx = this.dz();
         if (_snowmanx > 0) {
            if (this.am <= 0) {
               this.am = 20 * (30 - _snowmanx);
            }

            this.am--;
            if (this.am <= 0) {
               this.q(_snowmanx - 1);
            }
         }

         this.p();
         if (this.K % 20 == 0) {
            this.dv().g();
         }

         if (!this.af) {
            boolean _snowmanxx = this.a(apw.x);
            if (this.i(6) != _snowmanxx) {
               this.b(6, _snowmanxx);
            }
         }

         if (this.em() && !this.x()) {
            this.en();
         }
      }

      this.k();
      double _snowmanxx = this.cD() - this.m;
      double _snowmanxxx = this.cH() - this.o;
      float _snowmanxxxx = (float)(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
      float _snowmanxxxxx = this.aA;
      float _snowmanxxxxxx = 0.0F;
      this.aJ = this.aK;
      float _snowmanxxxxxxx = 0.0F;
      if (_snowmanxxxx > 0.0025000002F) {
         _snowmanxxxxxxx = 1.0F;
         _snowmanxxxxxx = (float)Math.sqrt((double)_snowmanxxxx) * 3.0F;
         float _snowmanxxxxxxxx = (float)afm.d(_snowmanxxx, _snowmanxx) * (180.0F / (float)Math.PI) - 90.0F;
         float _snowmanxxxxxxxxx = afm.e(afm.g(this.p) - _snowmanxxxxxxxx);
         if (95.0F < _snowmanxxxxxxxxx && _snowmanxxxxxxxxx < 265.0F) {
            _snowmanxxxxx = _snowmanxxxxxxxx - 180.0F;
         } else {
            _snowmanxxxxx = _snowmanxxxxxxxx;
         }
      }

      if (this.as > 0.0F) {
         _snowmanxxxxx = this.p;
      }

      if (!this.t) {
         _snowmanxxxxxxx = 0.0F;
      }

      this.aK = this.aK + (_snowmanxxxxxxx - this.aK) * 0.3F;
      this.l.Z().a("headTurn");
      _snowmanxxxxxx = this.f(_snowmanxxxxx, _snowmanxxxxxx);
      this.l.Z().c();
      this.l.Z().a("rangeChecks");

      while (this.p - this.r < -180.0F) {
         this.r -= 360.0F;
      }

      while (this.p - this.r >= 180.0F) {
         this.r += 360.0F;
      }

      while (this.aA - this.aB < -180.0F) {
         this.aB -= 360.0F;
      }

      while (this.aA - this.aB >= 180.0F) {
         this.aB += 360.0F;
      }

      while (this.q - this.s < -180.0F) {
         this.s -= 360.0F;
      }

      while (this.q - this.s >= 180.0F) {
         this.s += 360.0F;
      }

      while (this.aC - this.aD < -180.0F) {
         this.aD -= 360.0F;
      }

      while (this.aC - this.aD >= 180.0F) {
         this.aD += 360.0F;
      }

      this.l.Z().c();
      this.aL += _snowmanxxxxxx;
      if (this.ef()) {
         this.be++;
      } else {
         this.be = 0;
      }

      if (this.em()) {
         this.q = 0.0F;
      }
   }

   private void p() {
      Map<aqf, bmb> _snowman = this.q();
      if (_snowman != null) {
         this.a(_snowman);
         if (!_snowman.isEmpty()) {
            this.b(_snowman);
         }
      }
   }

   @Nullable
   private Map<aqf, bmb> q() {
      Map<aqf, bmb> _snowman = null;

      for (aqf _snowmanx : aqf.values()) {
         bmb _snowmanxx;
         switch (_snowmanx.a()) {
            case a:
               _snowmanxx = this.e(_snowmanx);
               break;
            case b:
               _snowmanxx = this.d(_snowmanx);
               break;
            default:
               continue;
         }

         bmb _snowmanxx = this.b(_snowmanx);
         if (!bmb.b(_snowmanxx, _snowmanxx)) {
            if (_snowman == null) {
               _snowman = Maps.newEnumMap(aqf.class);
            }

            _snowman.put(_snowmanx, _snowmanxx);
            if (!_snowmanxx.a()) {
               this.dB().a(_snowmanxx.a(_snowmanx));
            }

            if (!_snowmanxx.a()) {
               this.dB().b(_snowmanxx.a(_snowmanx));
            }
         }
      }

      return _snowman;
   }

   private void a(Map<aqf, bmb> var1) {
      bmb _snowman = _snowman.get(aqf.a);
      bmb _snowmanx = _snowman.get(aqf.b);
      if (_snowman != null && _snowmanx != null && bmb.b(_snowman, this.e(aqf.b)) && bmb.b(_snowmanx, this.e(aqf.a))) {
         ((aag)this.l).i().b(this, new pn(this, (byte)55));
         _snowman.remove(aqf.a);
         _snowman.remove(aqf.b);
         this.c(aqf.a, _snowman.i());
         this.c(aqf.b, _snowmanx.i());
      }
   }

   private void b(Map<aqf, bmb> var1) {
      List<Pair<aqf, bmb>> _snowman = Lists.newArrayListWithCapacity(_snowman.size());
      _snowman.forEach((var2x, var3) -> {
         bmb _snowmanx = var3.i();
         _snowman.add(Pair.of(var2x, _snowmanx));
         switch (var2x.a()) {
            case a:
               this.c(var2x, _snowmanx);
               break;
            case b:
               this.b(var2x, _snowmanx);
         }
      });
      ((aag)this.l).i().b(this, new rd(this.Y(), _snowman));
   }

   private bmb d(aqf var1) {
      return this.bo.get(_snowman.b());
   }

   private void b(aqf var1, bmb var2) {
      this.bo.set(_snowman.b(), _snowman);
   }

   private bmb e(aqf var1) {
      return this.bn.get(_snowman.b());
   }

   private void c(aqf var1, bmb var2) {
      this.bn.set(_snowman.b(), _snowman);
   }

   protected float f(float var1, float var2) {
      float _snowman = afm.g(_snowman - this.aA);
      this.aA += _snowman * 0.3F;
      float _snowmanx = afm.g(this.p - this.aA);
      boolean _snowmanxx = _snowmanx < -90.0F || _snowmanx >= 90.0F;
      if (_snowmanx < -75.0F) {
         _snowmanx = -75.0F;
      }

      if (_snowmanx >= 75.0F) {
         _snowmanx = 75.0F;
      }

      this.aA = this.p - _snowmanx;
      if (_snowmanx * _snowmanx > 2500.0F) {
         this.aA += _snowmanx * 0.2F;
      }

      if (_snowmanxx) {
         _snowman *= -1.0F;
      }

      return _snowman;
   }

   public void k() {
      if (this.bv > 0) {
         this.bv--;
      }

      if (this.cs()) {
         this.aU = 0;
         this.c(this.cD(), this.cE(), this.cH());
      }

      if (this.aU > 0) {
         double _snowman = this.cD() + (this.aV - this.cD()) / (double)this.aU;
         double _snowmanx = this.cE() + (this.aW - this.cE()) / (double)this.aU;
         double _snowmanxx = this.cH() + (this.aX - this.cH()) / (double)this.aU;
         double _snowmanxxx = afm.g(this.aY - (double)this.p);
         this.p = (float)((double)this.p + _snowmanxxx / (double)this.aU);
         this.q = (float)((double)this.q + (this.aZ - (double)this.q) / (double)this.aU);
         this.aU--;
         this.d(_snowman, _snowmanx, _snowmanxx);
         this.a(this.p, this.q);
      } else if (!this.dS()) {
         this.f(this.cC().a(0.98));
      }

      if (this.bb > 0) {
         this.aC = (float)((double)this.aC + afm.g(this.ba - (double)this.aC) / (double)this.bb);
         this.bb--;
      }

      dcn _snowman = this.cC();
      double _snowmanx = _snowman.b;
      double _snowmanxx = _snowman.c;
      double _snowmanxxx = _snowman.d;
      if (Math.abs(_snowman.b) < 0.003) {
         _snowmanx = 0.0;
      }

      if (Math.abs(_snowman.c) < 0.003) {
         _snowmanxx = 0.0;
      }

      if (Math.abs(_snowman.d) < 0.003) {
         _snowmanxxx = 0.0;
      }

      this.n(_snowmanx, _snowmanxx, _snowmanxxx);
      this.l.Z().a("ai");
      if (this.dI()) {
         this.aQ = false;
         this.aR = 0.0F;
         this.aT = 0.0F;
      } else if (this.dS()) {
         this.l.Z().a("newAi");
         this.dP();
         this.l.Z().c();
      }

      this.l.Z().c();
      this.l.Z().a("jump");
      if (this.aQ && this.cT()) {
         double _snowmanxxxx;
         if (this.aQ()) {
            _snowmanxxxx = this.b(aef.c);
         } else {
            _snowmanxxxx = this.b(aef.b);
         }

         boolean _snowmanxxxxx = this.aE() && _snowmanxxxx > 0.0;
         double _snowmanxxxxxx = this.cx();
         if (!_snowmanxxxxx || this.t && !(_snowmanxxxx > _snowmanxxxxxx)) {
            if (!this.aQ() || this.t && !(_snowmanxxxx > _snowmanxxxxxx)) {
               if ((this.t || _snowmanxxxxx && _snowmanxxxx <= _snowmanxxxxxx) && this.bv == 0) {
                  this.dK();
                  this.bv = 10;
               }
            } else {
               this.c(aef.c);
            }
         } else {
            this.c(aef.b);
         }
      } else {
         this.bv = 0;
      }

      this.l.Z().c();
      this.l.Z().a("travel");
      this.aR *= 0.98F;
      this.aT *= 0.98F;
      this.r();
      dci _snowmanxxxxx = this.cc();
      this.g(new dcn((double)this.aR, (double)this.aS, (double)this.aT));
      this.l.Z().c();
      this.l.Z().a("push");
      if (this.bf > 0) {
         this.bf--;
         this.a(_snowmanxxxxx, this.cc());
      }

      this.dQ();
      this.l.Z().c();
      if (!this.l.v && this.dO() && this.aG()) {
         this.a(apk.h, 1.0F);
      }
   }

   public boolean dO() {
      return false;
   }

   private void r() {
      boolean _snowman = this.i(7);
      if (_snowman && !this.t && !this.br() && !this.a(apw.y)) {
         bmb _snowmanx = this.b(aqf.e);
         if (_snowmanx.b() == bmd.qo && bld.d(_snowmanx)) {
            _snowman = true;
            if (!this.l.v && (this.be + 1) % 20 == 0) {
               _snowmanx.a(1, this, var0 -> var0.c(aqf.e));
            }
         } else {
            _snowman = false;
         }
      } else {
         _snowman = false;
      }

      if (!this.l.v) {
         this.b(7, _snowman);
      }
   }

   protected void dP() {
   }

   protected void dQ() {
      List<aqa> _snowman = this.l.a(this, this.cc(), aqd.a(this));
      if (!_snowman.isEmpty()) {
         int _snowmanx = this.l.V().c(brt.s);
         if (_snowmanx > 0 && _snowman.size() > _snowmanx - 1 && this.J.nextInt(4) == 0) {
            int _snowmanxx = 0;

            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
               if (!_snowman.get(_snowmanxxx).br()) {
                  _snowmanxx++;
               }
            }

            if (_snowmanxx > _snowmanx - 1) {
               this.a(apk.g, 6.0F);
            }
         }

         for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
            aqa _snowmanxxxx = _snowman.get(_snowmanxx);
            this.C(_snowmanxxxx);
         }
      }
   }

   protected void a(dci var1, dci var2) {
      dci _snowman = _snowman.b(_snowman);
      List<aqa> _snowmanx = this.l.a(this, _snowman);
      if (!_snowmanx.isEmpty()) {
         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            aqa _snowmanxxx = _snowmanx.get(_snowmanxx);
            if (_snowmanxxx instanceof aqm) {
               this.g((aqm)_snowmanxxx);
               this.bf = 0;
               this.f(this.cC().a(-0.2));
               break;
            }
         }
      } else if (this.u) {
         this.bf = 0;
      }

      if (!this.l.v && this.bf <= 0) {
         this.c(4, false);
      }
   }

   protected void C(aqa var1) {
      _snowman.i(this);
   }

   protected void g(aqm var1) {
   }

   public void r(int var1) {
      this.bf = _snowman;
      if (!this.l.v) {
         this.c(4, true);
      }
   }

   public boolean dR() {
      return (this.R.a(ag) & 4) != 0;
   }

   @Override
   public void l() {
      aqa _snowman = this.ct();
      super.l();
      if (_snowman != null && _snowman != this.ct() && !this.l.v) {
         this.a(_snowman);
      }
   }

   @Override
   public void ba() {
      super.ba();
      this.aJ = this.aK;
      this.aK = 0.0F;
      this.C = 0.0F;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.aV = _snowman;
      this.aW = _snowman;
      this.aX = _snowman;
      this.aY = (double)_snowman;
      this.aZ = (double)_snowman;
      this.aU = _snowman;
   }

   @Override
   public void a(float var1, int var2) {
      this.ba = (double)_snowman;
      this.bb = _snowman;
   }

   public void o(boolean var1) {
      this.aQ = _snowman;
   }

   public void a(bcv var1) {
      bfw _snowman = _snowman.i() != null ? this.l.b(_snowman.i()) : null;
      if (_snowman instanceof aah) {
         ac.O.a((aah)_snowman, _snowman.g(), this);
      }
   }

   public void a(aqa var1, int var2) {
      if (!_snowman.y && !this.l.v && (_snowman instanceof bcv || _snowman instanceof bga || _snowman instanceof aqg)) {
         ((aag)this.l).i().b(_snowman, new rr(_snowman.Y(), this.Y(), _snowman));
      }
   }

   public boolean D(aqa var1) {
      dcn _snowman = new dcn(this.cD(), this.cG(), this.cH());
      dcn _snowmanx = new dcn(_snowman.cD(), _snowman.cG(), _snowman.cH());
      return this.l.a(new brf(_snowman, _snowmanx, brf.a.a, brf.b.a, this)).c() == dcl.a.a;
   }

   @Override
   public float h(float var1) {
      return _snowman == 1.0F ? this.aC : afm.g(_snowman, this.aD, this.aC);
   }

   public float r(float var1) {
      float _snowman = this.as - this.ar;
      if (_snowman < 0.0F) {
         _snowman++;
      }

      return this.ar + _snowman * _snowman;
   }

   public boolean dS() {
      return !this.l.v;
   }

   @Override
   public boolean aT() {
      return !this.y;
   }

   @Override
   public boolean aU() {
      return this.aX() && !this.a_() && !this.c_();
   }

   @Override
   protected void aS() {
      this.w = this.J.nextDouble() >= this.b(arl.c);
   }

   @Override
   public float bK() {
      return this.aC;
   }

   @Override
   public void m(float var1) {
      this.aC = _snowman;
   }

   @Override
   public void n(float var1) {
      this.aA = _snowman;
   }

   @Override
   protected dcn a(gc.a var1, i.a var2) {
      return h(super.a(_snowman, _snowman));
   }

   public static dcn h(dcn var0) {
      return new dcn(_snowman.b, _snowman.c, 0.0);
   }

   public float dT() {
      return this.bw;
   }

   public void s(float var1) {
      if (_snowman < 0.0F) {
         _snowman = 0.0F;
      }

      this.bw = _snowman;
   }

   public void g() {
   }

   public void h() {
   }

   protected void dU() {
      this.bp = true;
   }

   public abstract aqi dV();

   public boolean dW() {
      return (this.R.a(ag) & 1) > 0;
   }

   public aot dX() {
      return (this.R.a(ag) & 2) > 0 ? aot.b : aot.a;
   }

   private void t() {
      if (this.dW()) {
         if (bmb.d(this.b(this.dX()), this.bc)) {
            this.bc = this.b(this.dX());
            this.bc.b(this.l, this, this.dZ());
            if (this.u()) {
               this.b(this.bc, 5);
            }

            if (--this.bd == 0 && !this.l.v && !this.bc.m()) {
               this.s();
            }
         } else {
            this.ec();
         }
      }
   }

   private boolean u() {
      int _snowman = this.dZ();
      bhz _snowmanx = this.bc.b().t();
      boolean _snowmanxx = _snowmanx != null && _snowmanx.e();
      _snowmanxx |= _snowman <= this.bc.k() - 7;
      return _snowmanxx && _snowman % 4 == 0;
   }

   private void v() {
      this.bC = this.bB;
      if (this.bC()) {
         this.bB = Math.min(1.0F, this.bB + 0.09F);
      } else {
         this.bB = Math.max(0.0F, this.bB - 0.09F);
      }
   }

   protected void c(int var1, boolean var2) {
      int _snowman = this.R.a(ag);
      if (_snowman) {
         _snowman |= _snowman;
      } else {
         _snowman &= ~_snowman;
      }

      this.R.b(ag, (byte)_snowman);
   }

   public void c(aot var1) {
      bmb _snowman = this.b(_snowman);
      if (!_snowman.a() && !this.dW()) {
         this.bc = _snowman;
         this.bd = _snowman.k();
         if (!this.l.v) {
            this.c(1, true);
            this.c(2, _snowman == aot.b);
         }
      }
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (bj.equals(_snowman)) {
         if (this.l.v) {
            this.ek().ifPresent(this::a);
         }
      } else if (ag.equals(_snowman) && this.l.v) {
         if (this.dW() && this.bc.a()) {
            this.bc = this.b(this.dX());
            if (!this.bc.a()) {
               this.bd = this.bc.k();
            }
         } else if (!this.dW() && !this.bc.a()) {
            this.bc = bmb.b;
            this.bd = 0;
         }
      }
   }

   @Override
   public void a(dj.a var1, dcn var2) {
      super.a(_snowman, _snowman);
      this.aD = this.aC;
      this.aA = this.aC;
      this.aB = this.aA;
   }

   protected void b(bmb var1, int var2) {
      if (!_snowman.a() && this.dW()) {
         if (_snowman.l() == bnn.c) {
            this.a(this.c(_snowman), 0.5F, this.l.t.nextFloat() * 0.1F + 0.9F);
         }

         if (_snowman.l() == bnn.b) {
            this.a(_snowman, _snowman);
            this.a(this.d(_snowman), 0.5F + 0.5F * (float)this.J.nextInt(2), (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
         }
      }
   }

   private void a(bmb var1, int var2) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         dcn _snowmanx = new dcn(((double)this.J.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
         _snowmanx = _snowmanx.a(-this.q * (float) (Math.PI / 180.0));
         _snowmanx = _snowmanx.b(-this.p * (float) (Math.PI / 180.0));
         double _snowmanxx = (double)(-this.J.nextFloat()) * 0.6 - 0.3;
         dcn _snowmanxxx = new dcn(((double)this.J.nextFloat() - 0.5) * 0.3, _snowmanxx, 0.6);
         _snowmanxxx = _snowmanxxx.a(-this.q * (float) (Math.PI / 180.0));
         _snowmanxxx = _snowmanxxx.b(-this.p * (float) (Math.PI / 180.0));
         _snowmanxxx = _snowmanxxx.b(this.cD(), this.cG(), this.cH());
         this.l.a(new he(hh.I, _snowman), _snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d, _snowmanx.b, _snowmanx.c + 0.05, _snowmanx.d);
      }
   }

   protected void s() {
      aot _snowman = this.dX();
      if (!this.bc.equals(this.b(_snowman))) {
         this.eb();
      } else {
         if (!this.bc.a() && this.dW()) {
            this.b(this.bc, 16);
            bmb _snowmanx = this.bc.a(this.l, this);
            if (_snowmanx != this.bc) {
               this.a(_snowman, _snowmanx);
            }

            this.ec();
         }
      }
   }

   public bmb dY() {
      return this.bc;
   }

   public int dZ() {
      return this.bd;
   }

   public int ea() {
      return this.dW() ? this.bc.k() - this.dZ() : 0;
   }

   public void eb() {
      if (!this.bc.a()) {
         this.bc.a(this.l, this, this.dZ());
         if (this.bc.m()) {
            this.t();
         }
      }

      this.ec();
   }

   public void ec() {
      if (!this.l.v) {
         this.c(1, false);
      }

      this.bc = bmb.b;
      this.bd = 0;
   }

   public boolean ed() {
      if (this.dW() && !this.bc.a()) {
         blx _snowman = this.bc.b();
         return _snowman.d_(this.bc) != bnn.d ? false : _snowman.e_(this.bc) - this.bd >= 5;
      } else {
         return false;
      }
   }

   public boolean ee() {
      return this.bu();
   }

   public boolean ef() {
      return this.i(7);
   }

   @Override
   public boolean bC() {
      return super.bC() || !this.ef() && this.ae() == aqx.b;
   }

   public int eg() {
      return this.be;
   }

   public boolean a(double var1, double var3, double var5, boolean var7) {
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      double _snowmanxxx = _snowman;
      boolean _snowmanxxxx = false;
      fx _snowmanxxxxx = new fx(_snowman, _snowman, _snowman);
      brx _snowmanxxxxxx = this.l;
      if (_snowmanxxxxxx.C(_snowmanxxxxx)) {
         boolean _snowmanxxxxxxx = false;

         while (!_snowmanxxxxxxx && _snowmanxxxxx.v() > 0) {
            fx _snowmanxxxxxxxx = _snowmanxxxxx.c();
            ceh _snowmanxxxxxxxxx = _snowmanxxxxxx.d_(_snowmanxxxxxxxx);
            if (_snowmanxxxxxxxxx.c().c()) {
               _snowmanxxxxxxx = true;
            } else {
               _snowmanxxx--;
               _snowmanxxxxx = _snowmanxxxxxxxx;
            }
         }

         if (_snowmanxxxxxxx) {
            this.a(_snowman, _snowmanxxx, _snowman);
            if (_snowmanxxxxxx.k(this) && !_snowmanxxxxxx.d(this.cc())) {
               _snowmanxxxx = true;
            }
         }
      }

      if (!_snowmanxxxx) {
         this.a(_snowman, _snowmanx, _snowmanxx);
         return false;
      } else {
         if (_snowman) {
            _snowmanxxxxxx.a(this, (byte)46);
         }

         if (this instanceof aqu) {
            ((aqu)this).x().o();
         }

         return true;
      }
   }

   public boolean eh() {
      return true;
   }

   public boolean ei() {
      return true;
   }

   public void a(fx var1, boolean var2) {
   }

   public boolean e(bmb var1) {
      return false;
   }

   @Override
   public oj<?> P() {
      return new op(this);
   }

   @Override
   public aqb a(aqx var1) {
      return _snowman == aqx.c ? ah : super.a(_snowman).a(this.cS());
   }

   public ImmutableList<aqx> ej() {
      return ImmutableList.of(aqx.a);
   }

   public dci f(aqx var1) {
      aqb _snowman = this.a(_snowman);
      return new dci((double)(-_snowman.a / 2.0F), 0.0, (double)(-_snowman.a / 2.0F), (double)(_snowman.a / 2.0F), (double)_snowman.b, (double)(_snowman.a / 2.0F));
   }

   public Optional<fx> ek() {
      return this.R.a(bj);
   }

   public void e(fx var1) {
      this.R.b(bj, Optional.of(_snowman));
   }

   public void el() {
      this.R.b(bj, Optional.empty());
   }

   public boolean em() {
      return this.ek().isPresent();
   }

   public void b(fx var1) {
      if (this.br()) {
         this.l();
      }

      ceh _snowman = this.l.d_(_snowman);
      if (_snowman.b() instanceof buj) {
         this.l.a(_snowman, _snowman.a(buj.b, Boolean.valueOf(true)), 3);
      }

      this.b(aqx.c);
      this.a(_snowman);
      this.e(_snowman);
      this.f(dcn.a);
      this.Z = true;
   }

   private void a(fx var1) {
      this.d((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.6875, (double)_snowman.w() + 0.5);
   }

   private boolean x() {
      return this.ek().map(var1 -> this.l.d_(var1).b() instanceof buj).orElse(false);
   }

   public void en() {
      this.ek().filter(this.l::C).ifPresent(var1x -> {
         ceh _snowman = this.l.d_(var1x);
         if (_snowman.b() instanceof buj) {
            this.l.a(var1x, _snowman.a(buj.b, Boolean.valueOf(false)), 3);
            dcn _snowmanx = buj.a(this.X(), this.l, var1x, this.p).orElseGet(() -> {
               fx _snowmanxx = var1x.b();
               return new dcn((double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v() + 0.1, (double)_snowmanxx.w() + 0.5);
            });
            dcn _snowmanxx = dcn.c(var1x).d(_snowmanx).d();
            float _snowmanxxx = (float)afm.g(afm.d(_snowmanxx.d, _snowmanxx.b) * 180.0F / (float)Math.PI - 90.0);
            this.d(_snowmanx.b, _snowmanx.c, _snowmanx.d);
            this.p = _snowmanxxx;
            this.q = 0.0F;
         }
      });
      dcn _snowman = this.cA();
      this.b(aqx.a);
      this.d(_snowman.b, _snowman.c, _snowman.d);
      this.el();
   }

   @Nullable
   public gc eo() {
      fx _snowman = this.ek().orElse(null);
      return _snowman != null ? buj.a(this.l, _snowman) : null;
   }

   @Override
   public boolean aY() {
      return !this.em() && super.aY();
   }

   @Override
   protected final float a(aqx var1, aqb var2) {
      return _snowman == aqx.c ? 0.2F : this.b(_snowman, _snowman);
   }

   protected float b(aqx var1, aqb var2) {
      return super.a(_snowman, _snowman);
   }

   public bmb f(bmb var1) {
      return bmb.b;
   }

   public bmb a(brx var1, bmb var2) {
      if (_snowman.F()) {
         _snowman.a(null, this.cD(), this.cE(), this.cH(), this.d(_snowman), adr.g, 1.0F, 1.0F + (_snowman.t.nextFloat() - _snowman.t.nextFloat()) * 0.4F);
         this.a(_snowman, _snowman, this);
         if (!(this instanceof bfw) || !((bfw)this).bC.d) {
            _snowman.g(1);
         }
      }

      return _snowman;
   }

   private void a(bmb var1, brx var2, aqm var3) {
      blx _snowman = _snowman.b();
      if (_snowman.s()) {
         for (Pair<apu, Float> _snowmanx : _snowman.t().f()) {
            if (!_snowman.v && _snowmanx.getFirst() != null && _snowman.t.nextFloat() < (Float)_snowmanx.getSecond()) {
               _snowman.c(new apu((apu)_snowmanx.getFirst()));
            }
         }
      }
   }

   private static byte f(aqf var0) {
      switch (_snowman) {
         case a:
            return 47;
         case b:
            return 48;
         case f:
            return 49;
         case e:
            return 50;
         case c:
            return 52;
         case d:
            return 51;
         default:
            return 47;
      }
   }

   public void c(aqf var1) {
      this.l.a(this, f(_snowman));
   }

   public void d(aot var1) {
      this.c(_snowman == aot.a ? aqf.a : aqf.b);
   }

   @Override
   public dci cd() {
      if (this.b(aqf.f).b() == bmd.pj) {
         float _snowman = 0.5F;
         return this.cc().c(0.5, 0.5, 0.5);
      } else {
         return super.cd();
      }
   }
}
