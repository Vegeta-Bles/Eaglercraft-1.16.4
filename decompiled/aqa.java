import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class aqa implements aoy, da {
   protected static final Logger h = LogManager.getLogger();
   private static final AtomicInteger b = new AtomicInteger();
   private static final List<bmb> c = Collections.emptyList();
   private static final dci d = new dci(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static double e = 1.0;
   private final aqe<?> f;
   private int g = b.incrementAndGet();
   public boolean i;
   private final List<aqa> ag = Lists.newArrayList();
   protected int j;
   @Nullable
   private aqa ah;
   public boolean k;
   public brx l;
   public double m;
   public double n;
   public double o;
   private dcn ai;
   private fx aj;
   private dcn ak = dcn.a;
   public float p;
   public float q;
   public float r;
   public float s;
   private dci al = d;
   protected boolean t;
   public boolean u;
   public boolean v;
   public boolean w;
   protected dcn x = dcn.a;
   public boolean y;
   public float z;
   public float A;
   public float B;
   public float C;
   private float am = 1.0F;
   private float an = 1.0F;
   public double D;
   public double E;
   public double F;
   public float G;
   public boolean H;
   public float I;
   protected final Random J = new Random();
   public int K;
   private int ao = -this.cv();
   protected boolean L;
   protected Object2DoubleMap<ael<cuw>> M = new Object2DoubleArrayMap(2);
   protected boolean N;
   @Nullable
   protected ael<cuw> O;
   public int P;
   protected boolean Q = true;
   protected final uv R;
   protected static final us<Byte> S = uv.a(aqa.class, uu.a);
   private static final us<Integer> ap = uv.a(aqa.class, uu.b);
   private static final us<Optional<nr>> aq = uv.a(aqa.class, uu.f);
   private static final us<Boolean> ar = uv.a(aqa.class, uu.i);
   private static final us<Boolean> as = uv.a(aqa.class, uu.i);
   private static final us<Boolean> at = uv.a(aqa.class, uu.i);
   protected static final us<aqx> T = uv.a(aqa.class, uu.s);
   public boolean U;
   public int V;
   public int W;
   public int X;
   private boolean au;
   private dcn av;
   public boolean Y;
   public boolean Z;
   private int aw;
   protected boolean aa;
   protected int ab;
   protected fx ac;
   private boolean ax;
   protected UUID ad = afm.a(this.J);
   protected String ae = this.ad.toString();
   protected boolean af;
   private final Set<String> ay = Sets.newHashSet();
   private boolean az;
   private final double[] aA = new double[]{0.0, 0.0, 0.0};
   private long aB;
   private aqb aC;
   private float aD;

   public aqa(aqe<?> var1, brx var2) {
      this.f = _snowman;
      this.l = _snowman;
      this.aC = _snowman.l();
      this.ai = dcn.a;
      this.aj = fx.b;
      this.av = dcn.a;
      this.d(0.0, 0.0, 0.0);
      this.R = new uv(this);
      this.R.a(S, (byte)0);
      this.R.a(ap, this.bH());
      this.R.a(ar, false);
      this.R.a(aq, Optional.empty());
      this.R.a(as, false);
      this.R.a(at, false);
      this.R.a(T, aqx.a);
      this.e();
      this.aD = this.a(aqx.a, this.aC);
   }

   public boolean a(fx var1, ceh var2) {
      ddh _snowman = _snowman.b(this.l, _snowman, dcs.a(this));
      ddh _snowmanx = _snowman.a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
      return dde.c(_snowmanx, dde.a(this.cc()), dcr.i);
   }

   public int U() {
      ddp _snowman = this.bG();
      return _snowman != null && _snowman.n().e() != null ? _snowman.n().e() : 16777215;
   }

   public boolean a_() {
      return false;
   }

   public final void V() {
      if (this.bs()) {
         this.be();
      }

      if (this.br()) {
         this.l();
      }
   }

   public void c(double var1, double var3, double var5) {
      this.a(new dcn(_snowman, _snowman, _snowman));
   }

   public void a(dcn var1) {
      this.av = _snowman;
   }

   public dcn W() {
      return this.av;
   }

   public aqe<?> X() {
      return this.f;
   }

   public int Y() {
      return this.g;
   }

   public void e(int var1) {
      this.g = _snowman;
   }

   public Set<String> Z() {
      return this.ay;
   }

   public boolean a(String var1) {
      return this.ay.size() >= 1024 ? false : this.ay.add(_snowman);
   }

   public boolean b(String var1) {
      return this.ay.remove(_snowman);
   }

   public void aa() {
      this.ad();
   }

   protected abstract void e();

   public uv ab() {
      return this.R;
   }

   @Override
   public boolean equals(Object var1) {
      return _snowman instanceof aqa ? ((aqa)_snowman).g == this.g : false;
   }

   @Override
   public int hashCode() {
      return this.g;
   }

   protected void ac() {
      if (this.l != null) {
         for (double _snowman = this.cE(); _snowman > 0.0 && _snowman < 256.0; _snowman++) {
            this.d(this.cD(), _snowman, this.cH());
            if (this.l.k(this)) {
               break;
            }
         }

         this.f(dcn.a);
         this.q = 0.0F;
      }
   }

   public void ad() {
      this.y = true;
   }

   public void b(aqx var1) {
      this.R.b(T, _snowman);
   }

   public aqx ae() {
      return this.R.a(T);
   }

   public boolean a(aqa var1, double var2) {
      double _snowman = _snowman.ai.b - this.ai.b;
      double _snowmanx = _snowman.ai.c - this.ai.c;
      double _snowmanxx = _snowman.ai.d - this.ai.d;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx < _snowman * _snowman;
   }

   protected void a(float var1, float var2) {
      this.p = _snowman % 360.0F;
      this.q = _snowman % 360.0F;
   }

   public void d(double var1, double var3, double var5) {
      this.o(_snowman, _snowman, _snowman);
      this.a(this.aC.a(_snowman, _snowman, _snowman));
   }

   protected void af() {
      this.d(this.ai.b, this.ai.c, this.ai.d);
   }

   public void a(double var1, double var3) {
      double _snowman = _snowman * 0.15;
      double _snowmanx = _snowman * 0.15;
      this.q = (float)((double)this.q + _snowman);
      this.p = (float)((double)this.p + _snowmanx);
      this.q = afm.a(this.q, -90.0F, 90.0F);
      this.s = (float)((double)this.s + _snowman);
      this.r = (float)((double)this.r + _snowmanx);
      this.s = afm.a(this.s, -90.0F, 90.0F);
      if (this.ah != null) {
         this.ah.l(this);
      }
   }

   public void j() {
      if (!this.l.v) {
         this.b(6, this.bE());
      }

      this.ag();
   }

   public void ag() {
      this.l.Z().a("entityBaseTick");
      if (this.br() && this.ct().y) {
         this.l();
      }

      if (this.j > 0) {
         this.j--;
      }

      this.z = this.A;
      this.s = this.q;
      this.r = this.p;
      this.bk();
      if (this.aO()) {
         this.aP();
      }

      this.aK();
      this.m();
      this.aJ();
      if (this.l.v) {
         this.am();
      } else if (this.ao > 0) {
         if (this.aD()) {
            this.g(this.ao - 4);
            if (this.ao < 0) {
               this.am();
            }
         } else {
            if (this.ao % 20 == 0 && !this.aQ()) {
               this.a(apk.c, 1.0F);
            }

            this.g(this.ao - 1);
         }
      }

      if (this.aQ()) {
         this.ak();
         this.C *= 0.5F;
      }

      if (this.cE() < -64.0) {
         this.an();
      }

      if (!this.l.v) {
         this.b(0, this.ao > 0);
      }

      this.Q = false;
      this.l.Z().c();
   }

   public void ah() {
      this.aw = this.bl();
   }

   public boolean ai() {
      return this.aw > 0;
   }

   protected void E() {
      if (this.ai()) {
         this.aw--;
      }
   }

   public int aj() {
      return 0;
   }

   protected void ak() {
      if (!this.aD()) {
         this.f(15);
         this.a(apk.d, 4.0F);
      }
   }

   public void f(int var1) {
      int _snowman = _snowman * 20;
      if (this instanceof aqm) {
         _snowman = bqf.a((aqm)this, _snowman);
      }

      if (this.ao < _snowman) {
         this.g(_snowman);
      }
   }

   public void g(int var1) {
      this.ao = _snowman;
   }

   public int al() {
      return this.ao;
   }

   public void am() {
      this.g(0);
   }

   protected void an() {
      this.ad();
   }

   public boolean e(double var1, double var3, double var5) {
      return this.b(this.cc().d(_snowman, _snowman, _snowman));
   }

   private boolean b(dci var1) {
      return this.l.a_(this, _snowman) && !this.l.d(_snowman);
   }

   public void c(boolean var1) {
      this.t = _snowman;
   }

   public boolean ao() {
      return this.t;
   }

   public void a(aqr var1, dcn var2) {
      if (this.H) {
         this.a(this.cc().c(_snowman));
         this.au();
      } else {
         if (_snowman == aqr.c) {
            _snowman = this.b(_snowman);
            if (_snowman.equals(dcn.a)) {
               return;
            }
         }

         this.l.Z().a("move");
         if (this.x.g() > 1.0E-7) {
            _snowman = _snowman.h(this.x);
            this.x = dcn.a;
            this.f(dcn.a);
         }

         _snowman = this.a(_snowman, _snowman);
         dcn _snowman = this.g(_snowman);
         if (_snowman.g() > 1.0E-7) {
            this.a(this.cc().c(_snowman));
            this.au();
         }

         this.l.Z().c();
         this.l.Z().a("rest");
         this.u = !afm.b(_snowman.b, _snowman.b) || !afm.b(_snowman.d, _snowman.d);
         this.v = _snowman.c != _snowman.c;
         this.t = this.v && _snowman.c < 0.0;
         fx _snowmanx = this.ap();
         ceh _snowmanxx = this.l.d_(_snowmanx);
         this.a(_snowman.c, this.t, _snowmanxx, _snowmanx);
         dcn _snowmanxxx = this.cC();
         if (_snowman.b != _snowman.b) {
            this.n(0.0, _snowmanxxx.c, _snowmanxxx.d);
         }

         if (_snowman.d != _snowman.d) {
            this.n(_snowmanxxx.b, _snowmanxxx.c, 0.0);
         }

         buo _snowmanxxxx = _snowmanxx.b();
         if (_snowman.c != _snowman.c) {
            _snowmanxxxx.a(this.l, this);
         }

         if (this.t && !this.bv()) {
            _snowmanxxxx.a(this.l, _snowmanx, this);
         }

         if (this.aC() && !this.br()) {
            double _snowmanxxxxx = _snowman.b;
            double _snowmanxxxxxx = _snowman.c;
            double _snowmanxxxxxxx = _snowman.d;
            if (!_snowmanxxxx.a(aed.at)) {
               _snowmanxxxxxx = 0.0;
            }

            this.A = (float)((double)this.A + (double)afm.a(c(_snowman)) * 0.6);
            this.B = (float)((double)this.B + (double)afm.a(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx) * 0.6);
            if (this.B > this.am && !_snowmanxx.g()) {
               this.am = this.at();
               if (this.aE()) {
                  aqa _snowmanxxxxxxxx = this.bs() && this.cm() != null ? this.cm() : this;
                  float _snowmanxxxxxxxxx = _snowmanxxxxxxxx == this ? 0.35F : 0.4F;
                  dcn _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.cC();
                  float _snowmanxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxx.b * _snowmanxxxxxxxxxx.b * 0.2F + _snowmanxxxxxxxxxx.c * _snowmanxxxxxxxxxx.c + _snowmanxxxxxxxxxx.d * _snowmanxxxxxxxxxx.d * 0.2F)
                     * _snowmanxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxx > 1.0F) {
                     _snowmanxxxxxxxxxxx = 1.0F;
                  }

                  this.d(_snowmanxxxxxxxxxxx);
               } else {
                  this.b(_snowmanx, _snowmanxx);
               }
            } else if (this.B > this.an && this.az() && _snowmanxx.g()) {
               this.an = this.e(this.B);
            }
         }

         try {
            this.ay();
         } catch (Throwable var18) {
            l _snowmanxxxxxxxx = l.a(var18, "Checking entity block collision");
            m _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a("Entity being checked for collision");
            this.a(_snowmanxxxxxxxxx);
            throw new u(_snowmanxxxxxxxx);
         }

         float _snowmanxxxxxxxx = this.ar();
         this.f(this.cC().d((double)_snowmanxxxxxxxx, 1.0, (double)_snowmanxxxxxxxx));
         if (this.l.c(this.cc().h(0.001)).noneMatch(var0 -> var0.a(aed.an) || var0.a(bup.B)) && this.ao <= 0) {
            this.g(-this.cv());
         }

         if (this.aG() && this.bq()) {
            this.a(adq.eM, 0.7F, 1.6F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
            this.g(-this.cv());
         }

         this.l.Z().c();
      }
   }

   protected fx ap() {
      int _snowman = afm.c(this.ai.b);
      int _snowmanx = afm.c(this.ai.c - 0.2F);
      int _snowmanxx = afm.c(this.ai.d);
      fx _snowmanxxx = new fx(_snowman, _snowmanx, _snowmanxx);
      if (this.l.d_(_snowmanxxx).g()) {
         fx _snowmanxxxx = _snowmanxxx.c();
         ceh _snowmanxxxxx = this.l.d_(_snowmanxxxx);
         buo _snowmanxxxxxx = _snowmanxxxxx.b();
         if (_snowmanxxxxxx.a(aed.M) || _snowmanxxxxxx.a(aed.F) || _snowmanxxxxxx instanceof bwr) {
            return _snowmanxxxx;
         }
      }

      return _snowmanxxx;
   }

   protected float aq() {
      float _snowman = this.l.d_(this.cB()).b().l();
      float _snowmanx = this.l.d_(this.as()).b().l();
      return (double)_snowman == 1.0 ? _snowmanx : _snowman;
   }

   protected float ar() {
      buo _snowman = this.l.d_(this.cB()).b();
      float _snowmanx = _snowman.k();
      if (_snowman != bup.A && _snowman != bup.lc) {
         return (double)_snowmanx == 1.0 ? this.l.d_(this.as()).b().k() : _snowmanx;
      } else {
         return _snowmanx;
      }
   }

   protected fx as() {
      return new fx(this.ai.b, this.cc().b - 0.5000001, this.ai.d);
   }

   protected dcn a(dcn var1, aqr var2) {
      return _snowman;
   }

   protected dcn b(dcn var1) {
      if (_snowman.g() <= 1.0E-7) {
         return _snowman;
      } else {
         long _snowman = this.l.T();
         if (_snowman != this.aB) {
            Arrays.fill(this.aA, 0.0);
            this.aB = _snowman;
         }

         if (_snowman.b != 0.0) {
            double _snowmanx = this.a(gc.a.a, _snowman.b);
            return Math.abs(_snowmanx) <= 1.0E-5F ? dcn.a : new dcn(_snowmanx, 0.0, 0.0);
         } else if (_snowman.c != 0.0) {
            double _snowmanx = this.a(gc.a.b, _snowman.c);
            return Math.abs(_snowmanx) <= 1.0E-5F ? dcn.a : new dcn(0.0, _snowmanx, 0.0);
         } else if (_snowman.d != 0.0) {
            double _snowmanx = this.a(gc.a.c, _snowman.d);
            return Math.abs(_snowmanx) <= 1.0E-5F ? dcn.a : new dcn(0.0, 0.0, _snowmanx);
         } else {
            return dcn.a;
         }
      }
   }

   private double a(gc.a var1, double var2) {
      int _snowman = _snowman.ordinal();
      double _snowmanx = afm.a(_snowman + this.aA[_snowman], -0.51, 0.51);
      _snowman = _snowmanx - this.aA[_snowman];
      this.aA[_snowman] = _snowmanx;
      return _snowman;
   }

   private dcn g(dcn var1) {
      dci _snowman = this.cc();
      dcs _snowmanx = dcs.a(this);
      ddh _snowmanxx = this.l.f().c();
      Stream<ddh> _snowmanxxx = dde.c(_snowmanxx, dde.a(_snowman.h(1.0E-7)), dcr.i) ? Stream.empty() : Stream.of(_snowmanxx);
      Stream<ddh> _snowmanxxxx = this.l.c(this, _snowman.b(_snowman), var0 -> true);
      afo<ddh> _snowmanxxxxx = new afo<>(Stream.concat(_snowmanxxxx, _snowmanxxx));
      dcn _snowmanxxxxxx = _snowman.g() == 0.0 ? _snowman : a(this, _snowman, _snowman, this.l, _snowmanx, _snowmanxxxxx);
      boolean _snowmanxxxxxxx = _snowman.b != _snowmanxxxxxx.b;
      boolean _snowmanxxxxxxxx = _snowman.c != _snowmanxxxxxx.c;
      boolean _snowmanxxxxxxxxx = _snowman.d != _snowmanxxxxxx.d;
      boolean _snowmanxxxxxxxxxx = this.t || _snowmanxxxxxxxx && _snowman.c < 0.0;
      if (this.G > 0.0F && _snowmanxxxxxxxxxx && (_snowmanxxxxxxx || _snowmanxxxxxxxxx)) {
         dcn _snowmanxxxxxxxxxxx = a(this, new dcn(_snowman.b, (double)this.G, _snowman.d), _snowman, this.l, _snowmanx, _snowmanxxxxx);
         dcn _snowmanxxxxxxxxxxxx = a(this, new dcn(0.0, (double)this.G, 0.0), _snowman.b(_snowman.b, 0.0, _snowman.d), this.l, _snowmanx, _snowmanxxxxx);
         if (_snowmanxxxxxxxxxxxx.c < (double)this.G) {
            dcn _snowmanxxxxxxxxxxxxx = a(this, new dcn(_snowman.b, 0.0, _snowman.d), _snowman.c(_snowmanxxxxxxxxxxxx), this.l, _snowmanx, _snowmanxxxxx).e(_snowmanxxxxxxxxxxxx);
            if (c(_snowmanxxxxxxxxxxxxx) > c(_snowmanxxxxxxxxxxx)) {
               _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
            }
         }

         if (c(_snowmanxxxxxxxxxxx) > c(_snowmanxxxxxx)) {
            return _snowmanxxxxxxxxxxx.e(a(this, new dcn(0.0, -_snowmanxxxxxxxxxxx.c + _snowman.c, 0.0), _snowman.c(_snowmanxxxxxxxxxxx), this.l, _snowmanx, _snowmanxxxxx));
         }
      }

      return _snowmanxxxxxx;
   }

   public static double c(dcn var0) {
      return _snowman.b * _snowman.b + _snowman.d * _snowman.d;
   }

   public static dcn a(@Nullable aqa var0, dcn var1, dci var2, brx var3, dcs var4, afo<ddh> var5) {
      boolean _snowman = _snowman.b == 0.0;
      boolean _snowmanx = _snowman.c == 0.0;
      boolean _snowmanxx = _snowman.d == 0.0;
      if ((!_snowman || !_snowmanx) && (!_snowman || !_snowmanxx) && (!_snowmanx || !_snowmanxx)) {
         afo<ddh> _snowmanxxx = new afo<>(Stream.concat(_snowman.a(), _snowman.b(_snowman, _snowman.b(_snowman))));
         return a(_snowman, _snowman, _snowmanxxx);
      } else {
         return a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public static dcn a(dcn var0, dci var1, afo<ddh> var2) {
      double _snowman = _snowman.b;
      double _snowmanx = _snowman.c;
      double _snowmanxx = _snowman.d;
      if (_snowmanx != 0.0) {
         _snowmanx = dde.a(gc.a.b, _snowman, _snowman.a(), _snowmanx);
         if (_snowmanx != 0.0) {
            _snowman = _snowman.d(0.0, _snowmanx, 0.0);
         }
      }

      boolean _snowmanxxx = Math.abs(_snowman) < Math.abs(_snowmanxx);
      if (_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = dde.a(gc.a.c, _snowman, _snowman.a(), _snowmanxx);
         if (_snowmanxx != 0.0) {
            _snowman = _snowman.d(0.0, 0.0, _snowmanxx);
         }
      }

      if (_snowman != 0.0) {
         _snowman = dde.a(gc.a.a, _snowman, _snowman.a(), _snowman);
         if (!_snowmanxxx && _snowman != 0.0) {
            _snowman = _snowman.d(_snowman, 0.0, 0.0);
         }
      }

      if (!_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = dde.a(gc.a.c, _snowman, _snowman.a(), _snowmanxx);
      }

      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public static dcn a(dcn var0, dci var1, brz var2, dcs var3, afo<ddh> var4) {
      double _snowman = _snowman.b;
      double _snowmanx = _snowman.c;
      double _snowmanxx = _snowman.d;
      if (_snowmanx != 0.0) {
         _snowmanx = dde.a(gc.a.b, _snowman, _snowman, _snowmanx, _snowman, _snowman.a());
         if (_snowmanx != 0.0) {
            _snowman = _snowman.d(0.0, _snowmanx, 0.0);
         }
      }

      boolean _snowmanxxx = Math.abs(_snowman) < Math.abs(_snowmanxx);
      if (_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = dde.a(gc.a.c, _snowman, _snowman, _snowmanxx, _snowman, _snowman.a());
         if (_snowmanxx != 0.0) {
            _snowman = _snowman.d(0.0, 0.0, _snowmanxx);
         }
      }

      if (_snowman != 0.0) {
         _snowman = dde.a(gc.a.a, _snowman, _snowman, _snowman, _snowman, _snowman.a());
         if (!_snowmanxxx && _snowman != 0.0) {
            _snowman = _snowman.d(_snowman, 0.0, 0.0);
         }
      }

      if (!_snowmanxxx && _snowmanxx != 0.0) {
         _snowmanxx = dde.a(gc.a.c, _snowman, _snowman, _snowmanxx, _snowman, _snowman.a());
      }

      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   protected float at() {
      return (float)((int)this.B + 1);
   }

   public void au() {
      dci _snowman = this.cc();
      this.o((_snowman.a + _snowman.d) / 2.0, _snowman.b, (_snowman.c + _snowman.f) / 2.0);
   }

   protected adp av() {
      return adq.eQ;
   }

   protected adp aw() {
      return adq.eP;
   }

   protected adp ax() {
      return adq.eP;
   }

   protected void ay() {
      dci _snowman = this.cc();
      fx _snowmanx = new fx(_snowman.a + 0.001, _snowman.b + 0.001, _snowman.c + 0.001);
      fx _snowmanxx = new fx(_snowman.d - 0.001, _snowman.e - 0.001, _snowman.f - 0.001);
      fx.a _snowmanxxx = new fx.a();
      if (this.l.a(_snowmanx, _snowmanxx)) {
         for (int _snowmanxxxx = _snowmanx.u(); _snowmanxxxx <= _snowmanxx.u(); _snowmanxxxx++) {
            for (int _snowmanxxxxx = _snowmanx.v(); _snowmanxxxxx <= _snowmanxx.v(); _snowmanxxxxx++) {
               for (int _snowmanxxxxxx = _snowmanx.w(); _snowmanxxxxxx <= _snowmanxx.w(); _snowmanxxxxxx++) {
                  _snowmanxxx.d(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  ceh _snowmanxxxxxxx = this.l.d_(_snowmanxxx);

                  try {
                     _snowmanxxxxxxx.a(this.l, _snowmanxxx, this);
                     this.a(_snowmanxxxxxxx);
                  } catch (Throwable var12) {
                     l _snowmanxxxxxxxx = l.a(var12, "Colliding entity with block");
                     m _snowmanxxxxxxxxx = _snowmanxxxxxxxx.a("Block being collided with");
                     m.a(_snowmanxxxxxxxxx, _snowmanxxx, _snowmanxxxxxxx);
                     throw new u(_snowmanxxxxxxxx);
                  }
               }
            }
         }
      }
   }

   protected void a(ceh var1) {
   }

   protected void b(fx var1, ceh var2) {
      if (!_snowman.c().a()) {
         ceh _snowman = this.l.d_(_snowman.b());
         cae _snowmanx = _snowman.a(bup.cC) ? _snowman.o() : _snowman.o();
         this.a(_snowmanx.d(), _snowmanx.a() * 0.15F, _snowmanx.b());
      }
   }

   protected void d(float var1) {
      this.a(this.av(), _snowman, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
   }

   protected float e(float var1) {
      return 0.0F;
   }

   protected boolean az() {
      return false;
   }

   public void a(adp var1, float var2, float var3) {
      if (!this.aA()) {
         this.l.a(null, this.cD(), this.cE(), this.cH(), _snowman, this.cu(), _snowman, _snowman);
      }
   }

   public boolean aA() {
      return this.R.a(as);
   }

   public void d(boolean var1) {
      this.R.b(as, _snowman);
   }

   public boolean aB() {
      return this.R.a(at);
   }

   public void e(boolean var1) {
      this.R.b(at, _snowman);
   }

   protected boolean aC() {
      return true;
   }

   protected void a(double var1, boolean var3, ceh var4, fx var5) {
      if (_snowman) {
         if (this.C > 0.0F) {
            _snowman.b().a(this.l, _snowman, this, this.C);
         }

         this.C = 0.0F;
      } else if (_snowman < 0.0) {
         this.C = (float)((double)this.C - _snowman);
      }
   }

   public boolean aD() {
      return this.X().c();
   }

   public boolean b(float var1, float var2) {
      if (this.bs()) {
         for (aqa _snowman : this.cn()) {
            _snowman.b(_snowman, _snowman);
         }
      }

      return false;
   }

   public boolean aE() {
      return this.L;
   }

   private boolean i() {
      fx _snowman = this.cB();
      return this.l.t(_snowman) || this.l.t(new fx((double)_snowman.u(), this.cc().e, (double)_snowman.w()));
   }

   private boolean k() {
      return this.l.d_(this.cB()).a(bup.lc);
   }

   public boolean aF() {
      return this.aE() || this.i();
   }

   public boolean aG() {
      return this.aE() || this.i() || this.k();
   }

   public boolean aH() {
      return this.aE() || this.k();
   }

   public boolean aI() {
      return this.N && this.aE();
   }

   public void aJ() {
      if (this.bB()) {
         this.h(this.bA() && this.aE() && !this.br());
      } else {
         this.h(this.bA() && this.aI() && !this.br());
      }
   }

   protected boolean aK() {
      this.M.clear();
      this.aL();
      double _snowman = this.l.k().d() ? 0.007 : 0.0023333333333333335;
      boolean _snowmanx = this.a(aef.c, _snowman);
      return this.aE() || _snowmanx;
   }

   void aL() {
      if (this.ct() instanceof bhn) {
         this.L = false;
      } else if (this.a(aef.b, 0.014)) {
         if (!this.L && !this.Q) {
            this.aM();
         }

         this.C = 0.0F;
         this.L = true;
         this.am();
      } else {
         this.L = false;
      }
   }

   private void m() {
      this.N = this.a(aef.b);
      this.O = null;
      double _snowman = this.cG() - 0.11111111F;
      aqa _snowmanx = this.ct();
      if (_snowmanx instanceof bhn) {
         bhn _snowmanxx = (bhn)_snowmanx;
         if (!_snowmanxx.aI() && _snowmanxx.cc().e >= _snowman && _snowmanxx.cc().b <= _snowman) {
            return;
         }
      }

      fx _snowmanxx = new fx(this.cD(), _snowman, this.cH());
      cux _snowmanxxx = this.l.b(_snowmanxx);

      for (ael<cuw> _snowmanxxxx : aef.b()) {
         if (_snowmanxxx.a(_snowmanxxxx)) {
            double _snowmanxxxxx = (double)((float)_snowmanxx.v() + _snowmanxxx.a((brc)this.l, _snowmanxx));
            if (_snowmanxxxxx > _snowman) {
               this.O = _snowmanxxxx;
            }

            return;
         }
      }
   }

   protected void aM() {
      aqa _snowman = this.bs() && this.cm() != null ? this.cm() : this;
      float _snowmanx = _snowman == this ? 0.2F : 0.9F;
      dcn _snowmanxx = _snowman.cC();
      float _snowmanxxx = afm.a(_snowmanxx.b * _snowmanxx.b * 0.2F + _snowmanxx.c * _snowmanxx.c + _snowmanxx.d * _snowmanxx.d * 0.2F) * _snowmanx;
      if (_snowmanxxx > 1.0F) {
         _snowmanxxx = 1.0F;
      }

      if ((double)_snowmanxxx < 0.25) {
         this.a(this.aw(), _snowmanxxx, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
      } else {
         this.a(this.ax(), _snowmanxxx, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
      }

      float _snowmanxxxx = (float)afm.c(this.cE());

      for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < 1.0F + this.aC.a * 20.0F; _snowmanxxxxx++) {
         double _snowmanxxxxxx = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aC.a;
         double _snowmanxxxxxxx = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aC.a;
         this.l.a(hh.e, this.cD() + _snowmanxxxxxx, (double)(_snowmanxxxx + 1.0F), this.cH() + _snowmanxxxxxxx, _snowmanxx.b, _snowmanxx.c - this.J.nextDouble() * 0.2F, _snowmanxx.d);
      }

      for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < 1.0F + this.aC.a * 20.0F; _snowmanxxxxx++) {
         double _snowmanxxxxxx = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aC.a;
         double _snowmanxxxxxxx = (this.J.nextDouble() * 2.0 - 1.0) * (double)this.aC.a;
         this.l.a(hh.Z, this.cD() + _snowmanxxxxxx, (double)(_snowmanxxxx + 1.0F), this.cH() + _snowmanxxxxxxx, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d);
      }
   }

   protected ceh aN() {
      return this.l.d_(this.ap());
   }

   public boolean aO() {
      return this.bA() && !this.aE() && !this.a_() && !this.bz() && !this.aQ() && this.aX();
   }

   protected void aP() {
      int _snowman = afm.c(this.cD());
      int _snowmanx = afm.c(this.cE() - 0.2F);
      int _snowmanxx = afm.c(this.cH());
      fx _snowmanxxx = new fx(_snowman, _snowmanx, _snowmanxx);
      ceh _snowmanxxxx = this.l.d_(_snowmanxxx);
      if (_snowmanxxxx.h() != bzh.a) {
         dcn _snowmanxxxxx = this.cC();
         this.l
            .a(
               new hc(hh.d, _snowmanxxxx),
               this.cD() + (this.J.nextDouble() - 0.5) * (double)this.aC.a,
               this.cE() + 0.1,
               this.cH() + (this.J.nextDouble() - 0.5) * (double)this.aC.a,
               _snowmanxxxxx.b * -4.0,
               1.5,
               _snowmanxxxxx.d * -4.0
            );
      }
   }

   public boolean a(ael<cuw> var1) {
      return this.O == _snowman;
   }

   public boolean aQ() {
      return !this.Q && this.M.getDouble(aef.c) > 0.0;
   }

   public void a(float var1, dcn var2) {
      dcn _snowman = a(_snowman, _snowman, this.p);
      this.f(this.cC().e(_snowman));
   }

   private static dcn a(dcn var0, float var1, float var2) {
      double _snowman = _snowman.g();
      if (_snowman < 1.0E-7) {
         return dcn.a;
      } else {
         dcn _snowmanx = (_snowman > 1.0 ? _snowman.d() : _snowman).a((double)_snowman);
         float _snowmanxx = afm.a(_snowman * (float) (Math.PI / 180.0));
         float _snowmanxxx = afm.b(_snowman * (float) (Math.PI / 180.0));
         return new dcn(_snowmanx.b * (double)_snowmanxxx - _snowmanx.d * (double)_snowmanxx, _snowmanx.c, _snowmanx.d * (double)_snowmanxxx + _snowmanx.b * (double)_snowmanxx);
      }
   }

   public float aR() {
      fx.a _snowman = new fx.a(this.cD(), 0.0, this.cH());
      if (this.l.C(_snowman)) {
         _snowman.p(afm.c(this.cG()));
         return this.l.y(_snowman);
      } else {
         return 0.0F;
      }
   }

   public void a_(brx var1) {
      this.l = _snowman;
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      this.f(_snowman, _snowman, _snowman);
      this.p = _snowman % 360.0F;
      this.q = afm.a(_snowman, -90.0F, 90.0F) % 360.0F;
      this.r = this.p;
      this.s = this.q;
   }

   public void f(double var1, double var3, double var5) {
      double _snowman = afm.a(_snowman, -3.0E7, 3.0E7);
      double _snowmanx = afm.a(_snowman, -3.0E7, 3.0E7);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowmanx;
      this.d(_snowman, _snowman, _snowmanx);
   }

   public void d(dcn var1) {
      this.b(_snowman.b, _snowman.c, _snowman.d);
   }

   public void b(double var1, double var3, double var5) {
      this.b(_snowman, _snowman, _snowman, this.p, this.q);
   }

   public void a(fx var1, float var2, float var3) {
      this.b((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5, _snowman, _snowman);
   }

   public void b(double var1, double var3, double var5, float var7, float var8) {
      this.g(_snowman, _snowman, _snowman);
      this.p = _snowman;
      this.q = _snowman;
      this.af();
   }

   public void g(double var1, double var3, double var5) {
      this.o(_snowman, _snowman, _snowman);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
      this.D = _snowman;
      this.E = _snowman;
      this.F = _snowman;
   }

   public float g(aqa var1) {
      float _snowman = (float)(this.cD() - _snowman.cD());
      float _snowmanx = (float)(this.cE() - _snowman.cE());
      float _snowmanxx = (float)(this.cH() - _snowman.cH());
      return afm.c(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public double h(double var1, double var3, double var5) {
      double _snowman = this.cD() - _snowman;
      double _snowmanx = this.cE() - _snowman;
      double _snowmanxx = this.cH() - _snowman;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public double h(aqa var1) {
      return this.e(_snowman.cA());
   }

   public double e(dcn var1) {
      double _snowman = this.cD() - _snowman.b;
      double _snowmanx = this.cE() - _snowman.c;
      double _snowmanxx = this.cH() - _snowman.d;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public void a_(bfw var1) {
   }

   public void i(aqa var1) {
      if (!this.x(_snowman)) {
         if (!_snowman.H && !this.H) {
            double _snowman = _snowman.cD() - this.cD();
            double _snowmanx = _snowman.cH() - this.cH();
            double _snowmanxx = afm.a(_snowman, _snowmanx);
            if (_snowmanxx >= 0.01F) {
               _snowmanxx = (double)afm.a(_snowmanxx);
               _snowman /= _snowmanxx;
               _snowmanx /= _snowmanxx;
               double _snowmanxxx = 1.0 / _snowmanxx;
               if (_snowmanxxx > 1.0) {
                  _snowmanxxx = 1.0;
               }

               _snowman *= _snowmanxxx;
               _snowmanx *= _snowmanxxx;
               _snowman *= 0.05F;
               _snowmanx *= 0.05F;
               _snowman *= (double)(1.0F - this.I);
               _snowmanx *= (double)(1.0F - this.I);
               if (!this.bs()) {
                  this.i(-_snowman, 0.0, -_snowmanx);
               }

               if (!_snowman.bs()) {
                  _snowman.i(_snowman, 0.0, _snowmanx);
               }
            }
         }
      }
   }

   public void i(double var1, double var3, double var5) {
      this.f(this.cC().b(_snowman, _snowman, _snowman));
      this.Z = true;
   }

   protected void aS() {
      this.w = true;
   }

   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         this.aS();
         return false;
      }
   }

   public final dcn f(float var1) {
      return this.c(this.g(_snowman), this.h(_snowman));
   }

   public float g(float var1) {
      return _snowman == 1.0F ? this.q : afm.g(_snowman, this.s, this.q);
   }

   public float h(float var1) {
      return _snowman == 1.0F ? this.p : afm.g(_snowman, this.r, this.p);
   }

   protected final dcn c(float var1, float var2) {
      float _snowman = _snowman * (float) (Math.PI / 180.0);
      float _snowmanx = -_snowman * (float) (Math.PI / 180.0);
      float _snowmanxx = afm.b(_snowmanx);
      float _snowmanxxx = afm.a(_snowmanx);
      float _snowmanxxxx = afm.b(_snowman);
      float _snowmanxxxxx = afm.a(_snowman);
      return new dcn((double)(_snowmanxxx * _snowmanxxxx), (double)(-_snowmanxxxxx), (double)(_snowmanxx * _snowmanxxxx));
   }

   public final dcn i(float var1) {
      return this.d(this.g(_snowman), this.h(_snowman));
   }

   protected final dcn d(float var1, float var2) {
      return this.c(_snowman - 90.0F, _snowman);
   }

   public final dcn j(float var1) {
      if (_snowman == 1.0F) {
         return new dcn(this.cD(), this.cG(), this.cH());
      } else {
         double _snowman = afm.d((double)_snowman, this.m, this.cD());
         double _snowmanx = afm.d((double)_snowman, this.n, this.cE()) + (double)this.ce();
         double _snowmanxx = afm.d((double)_snowman, this.o, this.cH());
         return new dcn(_snowman, _snowmanx, _snowmanxx);
      }
   }

   public dcn k(float var1) {
      return this.j(_snowman);
   }

   public final dcn l(float var1) {
      double _snowman = afm.d((double)_snowman, this.m, this.cD());
      double _snowmanx = afm.d((double)_snowman, this.n, this.cE());
      double _snowmanxx = afm.d((double)_snowman, this.o, this.cH());
      return new dcn(_snowman, _snowmanx, _snowmanxx);
   }

   public dcl a(double var1, float var3, boolean var4) {
      dcn _snowman = this.j(_snowman);
      dcn _snowmanx = this.f(_snowman);
      dcn _snowmanxx = _snowman.b(_snowmanx.b * _snowman, _snowmanx.c * _snowman, _snowmanx.d * _snowman);
      return this.l.a(new brf(_snowman, _snowmanxx, brf.a.b, _snowman ? brf.b.c : brf.b.a, this));
   }

   public boolean aT() {
      return false;
   }

   public boolean aU() {
      return false;
   }

   public void a(aqa var1, int var2, apk var3) {
      if (_snowman instanceof aah) {
         ac.c.a((aah)_snowman, this, _snowman);
      }
   }

   public boolean j(double var1, double var3, double var5) {
      double _snowman = this.cD() - _snowman;
      double _snowmanx = this.cE() - _snowman;
      double _snowmanxx = this.cH() - _snowman;
      double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
      return this.a(_snowmanxxx);
   }

   public boolean a(double var1) {
      double _snowman = this.cc().a();
      if (Double.isNaN(_snowman)) {
         _snowman = 1.0;
      }

      _snowman *= 64.0 * e;
      return _snowman < _snowman * _snowman;
   }

   public boolean a_(md var1) {
      String _snowman = this.aW();
      if (!this.y && _snowman != null) {
         _snowman.a("id", _snowman);
         this.e(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public boolean d(md var1) {
      return this.br() ? false : this.a_(_snowman);
   }

   public md e(md var1) {
      try {
         if (this.ah != null) {
            _snowman.a("Pos", this.a(this.ah.cD(), this.cE(), this.ah.cH()));
         } else {
            _snowman.a("Pos", this.a(this.cD(), this.cE(), this.cH()));
         }

         dcn _snowman = this.cC();
         _snowman.a("Motion", this.a(_snowman.b, _snowman.c, _snowman.d));
         _snowman.a("Rotation", this.a(this.p, this.q));
         _snowman.a("FallDistance", this.C);
         _snowman.a("Fire", (short)this.ao);
         _snowman.a("Air", (short)this.bI());
         _snowman.a("OnGround", this.t);
         _snowman.a("Invulnerable", this.ax);
         _snowman.b("PortalCooldown", this.aw);
         _snowman.a("UUID", this.bS());
         nr _snowmanx = this.T();
         if (_snowmanx != null) {
            _snowman.a("CustomName", nr.a.a(_snowmanx));
         }

         if (this.bX()) {
            _snowman.a("CustomNameVisible", this.bX());
         }

         if (this.aA()) {
            _snowman.a("Silent", this.aA());
         }

         if (this.aB()) {
            _snowman.a("NoGravity", this.aB());
         }

         if (this.af) {
            _snowman.a("Glowing", this.af);
         }

         if (!this.ay.isEmpty()) {
            mj _snowmanxx = new mj();

            for (String _snowmanxxx : this.ay) {
               _snowmanxx.add(ms.a(_snowmanxxx));
            }

            _snowman.a("Tags", _snowmanxx);
         }

         this.b(_snowman);
         if (this.bs()) {
            mj _snowmanxx = new mj();

            for (aqa _snowmanxxx : this.cn()) {
               md _snowmanxxxx = new md();
               if (_snowmanxxx.a_(_snowmanxxxx)) {
                  _snowmanxx.add(_snowmanxxxx);
               }
            }

            if (!_snowmanxx.isEmpty()) {
               _snowman.a("Passengers", _snowmanxx);
            }
         }

         return _snowman;
      } catch (Throwable var8) {
         l _snowmanxx = l.a(var8, "Saving entity NBT");
         m _snowmanxxxx = _snowmanxx.a("Entity being saved");
         this.a(_snowmanxxxx);
         throw new u(_snowmanxx);
      }
   }

   public void f(md var1) {
      try {
         mj _snowman = _snowman.d("Pos", 6);
         mj _snowmanx = _snowman.d("Motion", 6);
         mj _snowmanxx = _snowman.d("Rotation", 5);
         double _snowmanxxx = _snowmanx.h(0);
         double _snowmanxxxx = _snowmanx.h(1);
         double _snowmanxxxxx = _snowmanx.h(2);
         this.n(Math.abs(_snowmanxxx) > 10.0 ? 0.0 : _snowmanxxx, Math.abs(_snowmanxxxx) > 10.0 ? 0.0 : _snowmanxxxx, Math.abs(_snowmanxxxxx) > 10.0 ? 0.0 : _snowmanxxxxx);
         this.g(_snowman.h(0), _snowman.h(1), _snowman.h(2));
         this.p = _snowmanxx.i(0);
         this.q = _snowmanxx.i(1);
         this.r = this.p;
         this.s = this.q;
         this.m(this.p);
         this.n(this.p);
         this.C = _snowman.j("FallDistance");
         this.ao = _snowman.g("Fire");
         this.j(_snowman.g("Air"));
         this.t = _snowman.q("OnGround");
         this.ax = _snowman.q("Invulnerable");
         this.aw = _snowman.h("PortalCooldown");
         if (_snowman.b("UUID")) {
            this.ad = _snowman.a("UUID");
            this.ae = this.ad.toString();
         }

         if (!Double.isFinite(this.cD()) || !Double.isFinite(this.cE()) || !Double.isFinite(this.cH())) {
            throw new IllegalStateException("Entity has invalid position");
         } else if (Double.isFinite((double)this.p) && Double.isFinite((double)this.q)) {
            this.af();
            this.a(this.p, this.q);
            if (_snowman.c("CustomName", 8)) {
               String _snowmanxxxxxx = _snowman.l("CustomName");

               try {
                  this.a(nr.a.a(_snowmanxxxxxx));
               } catch (Exception var14) {
                  h.warn("Failed to parse entity custom name {}", _snowmanxxxxxx, var14);
               }
            }

            this.n(_snowman.q("CustomNameVisible"));
            this.d(_snowman.q("Silent"));
            this.e(_snowman.q("NoGravity"));
            this.i(_snowman.q("Glowing"));
            if (_snowman.c("Tags", 9)) {
               this.ay.clear();
               mj _snowmanxxxxxx = _snowman.d("Tags", 8);
               int _snowmanxxxxxxx = Math.min(_snowmanxxxxxx.size(), 1024);

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxx++) {
                  this.ay.add(_snowmanxxxxxx.j(_snowmanxxxxxxxx));
               }
            }

            this.a(_snowman);
            if (this.aV()) {
               this.af();
            }
         } else {
            throw new IllegalStateException("Entity has invalid rotation");
         }
      } catch (Throwable var15) {
         l _snowmanxxxxxx = l.a(var15, "Loading entity NBT");
         m _snowmanxxxxxxx = _snowmanxxxxxx.a("Entity being loaded");
         this.a(_snowmanxxxxxxx);
         throw new u(_snowmanxxxxxx);
      }
   }

   protected boolean aV() {
      return true;
   }

   @Nullable
   protected final String aW() {
      aqe<?> _snowman = this.X();
      vk _snowmanx = aqe.a(_snowman);
      return _snowman.a() && _snowmanx != null ? _snowmanx.toString() : null;
   }

   protected abstract void a(md var1);

   protected abstract void b(md var1);

   protected mj a(double... var1) {
      mj _snowman = new mj();

      for (double _snowmanx : _snowman) {
         _snowman.add(me.a(_snowmanx));
      }

      return _snowman;
   }

   protected mj a(float... var1) {
      mj _snowman = new mj();

      for (float _snowmanx : _snowman) {
         _snowman.add(mg.a(_snowmanx));
      }

      return _snowman;
   }

   @Nullable
   public bcv a(brw var1) {
      return this.a(_snowman, 0);
   }

   @Nullable
   public bcv a(brw var1, int var2) {
      return this.a(new bmb(_snowman), (float)_snowman);
   }

   @Nullable
   public bcv a(bmb var1) {
      return this.a(_snowman, 0.0F);
   }

   @Nullable
   public bcv a(bmb var1, float var2) {
      if (_snowman.a()) {
         return null;
      } else if (this.l.v) {
         return null;
      } else {
         bcv _snowman = new bcv(this.l, this.cD(), this.cE() + (double)_snowman, this.cH(), _snowman);
         _snowman.m();
         this.l.c(_snowman);
         return _snowman;
      }
   }

   public boolean aX() {
      return !this.y;
   }

   public boolean aY() {
      if (this.H) {
         return false;
      } else {
         float _snowman = 0.1F;
         float _snowmanx = this.aC.a * 0.8F;
         dci _snowmanxx = dci.g((double)_snowmanx, 0.1F, (double)_snowmanx).d(this.cD(), this.cG(), this.cH());
         return this.l.b(this, _snowmanxx, (var1x, var2x) -> var1x.o(this.l, var2x)).findAny().isPresent();
      }
   }

   public aou a(bfw var1, aot var2) {
      return aou.c;
   }

   public boolean j(aqa var1) {
      return _snowman.aZ() && !this.x(_snowman);
   }

   public boolean aZ() {
      return false;
   }

   public void ba() {
      this.f(dcn.a);
      this.j();
      if (this.br()) {
         this.ct().k(this);
      }
   }

   public void k(aqa var1) {
      this.a(_snowman, aqa::d);
   }

   private void a(aqa var1, aqa.a var2) {
      if (this.w(_snowman)) {
         double _snowman = this.cE() + this.bc() + _snowman.bb();
         _snowman.accept(_snowman, this.cD(), _snowman, this.cH());
      }
   }

   public void l(aqa var1) {
   }

   public double bb() {
      return 0.0;
   }

   public double bc() {
      return (double)this.aC.b * 0.75;
   }

   public boolean m(aqa var1) {
      return this.a(_snowman, false);
   }

   public boolean bd() {
      return this instanceof aqm;
   }

   public boolean a(aqa var1, boolean var2) {
      for (aqa _snowman = _snowman; _snowman.ah != null; _snowman = _snowman.ah) {
         if (_snowman.ah == this) {
            return false;
         }
      }

      if (_snowman || this.n(_snowman) && _snowman.q(this)) {
         if (this.br()) {
            this.l();
         }

         this.b(aqx.a);
         this.ah = _snowman;
         this.ah.o(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean n(aqa var1) {
      return !this.bu() && this.j <= 0;
   }

   protected boolean c(aqx var1) {
      return this.l.a_(this, this.d(_snowman).h(1.0E-7));
   }

   public void be() {
      for (int _snowman = this.ag.size() - 1; _snowman >= 0; _snowman--) {
         this.ag.get(_snowman).l();
      }
   }

   public void bf() {
      if (this.ah != null) {
         aqa _snowman = this.ah;
         this.ah = null;
         _snowman.p(this);
      }
   }

   public void l() {
      this.bf();
   }

   protected void o(aqa var1) {
      if (_snowman.ct() != this) {
         throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
      } else {
         if (!this.l.v && _snowman instanceof bfw && !(this.cm() instanceof bfw)) {
            this.ag.add(0, _snowman);
         } else {
            this.ag.add(_snowman);
         }
      }
   }

   protected void p(aqa var1) {
      if (_snowman.ct() == this) {
         throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
      } else {
         this.ag.remove(_snowman);
         _snowman.j = 60;
      }
   }

   protected boolean q(aqa var1) {
      return this.cn().size() < 1;
   }

   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.d(_snowman, _snowman, _snowman);
      this.a(_snowman, _snowman);
   }

   public void a(float var1, int var2) {
      this.m(_snowman);
   }

   public float bg() {
      return 0.0F;
   }

   public dcn bh() {
      return this.c(this.q, this.p);
   }

   public dcm bi() {
      return new dcm(this.q, this.p);
   }

   public dcn bj() {
      return dcn.a(this.bi());
   }

   public void d(fx var1) {
      if (this.ai()) {
         this.ah();
      } else {
         if (!this.l.v && !_snowman.equals(this.ac)) {
            this.ac = _snowman.h();
         }

         this.aa = true;
      }
   }

   protected void bk() {
      if (this.l instanceof aag) {
         int _snowman = this.aj();
         aag _snowmanx = (aag)this.l;
         if (this.aa) {
            MinecraftServer _snowmanxx = _snowmanx.l();
            vj<brx> _snowmanxxx = this.l.Y() == brx.h ? brx.g : brx.h;
            aag _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxx.C() && !this.br() && this.ab++ >= _snowman) {
               this.l.Z().a("portal");
               this.ab = _snowman;
               this.ah();
               this.b(_snowmanxxxx);
               this.l.Z().c();
            }

            this.aa = false;
         } else {
            if (this.ab > 0) {
               this.ab -= 4;
            }

            if (this.ab < 0) {
               this.ab = 0;
            }
         }

         this.E();
      }
   }

   public int bl() {
      return 300;
   }

   public void k(double var1, double var3, double var5) {
      this.n(_snowman, _snowman, _snowman);
   }

   public void a(byte var1) {
      switch (_snowman) {
         case 53:
            bxk.a(this);
      }
   }

   public void bm() {
   }

   public Iterable<bmb> bn() {
      return c;
   }

   public Iterable<bmb> bo() {
      return c;
   }

   public Iterable<bmb> bp() {
      return Iterables.concat(this.bn(), this.bo());
   }

   public void a(aqf var1, bmb var2) {
   }

   public boolean bq() {
      boolean _snowman = this.l != null && this.l.v;
      return !this.aD() && (this.ao > 0 || _snowman && this.i(0));
   }

   public boolean br() {
      return this.ct() != null;
   }

   public boolean bs() {
      return !this.cn().isEmpty();
   }

   public boolean bt() {
      return true;
   }

   public void f(boolean var1) {
      this.b(1, _snowman);
   }

   public boolean bu() {
      return this.i(1);
   }

   public boolean bv() {
      return this.bu();
   }

   public boolean bw() {
      return this.bu();
   }

   public boolean bx() {
      return this.bu();
   }

   public boolean by() {
      return this.bu();
   }

   public boolean bz() {
      return this.ae() == aqx.f;
   }

   public boolean bA() {
      return this.i(3);
   }

   public void g(boolean var1) {
      this.b(3, _snowman);
   }

   public boolean bB() {
      return this.i(4);
   }

   public boolean bC() {
      return this.ae() == aqx.d;
   }

   public boolean bD() {
      return this.bC() && !this.aE();
   }

   public void h(boolean var1) {
      this.b(4, _snowman);
   }

   public boolean bE() {
      return this.af || this.l.v && this.i(6);
   }

   public void i(boolean var1) {
      this.af = _snowman;
      if (!this.l.v) {
         this.b(6, this.af);
      }
   }

   public boolean bF() {
      return this.i(5);
   }

   public boolean c(bfw var1) {
      if (_snowman.a_()) {
         return false;
      } else {
         ddp _snowman = this.bG();
         return _snowman != null && _snowman != null && _snowman.bG() == _snowman && _snowman.i() ? false : this.bF();
      }
   }

   @Nullable
   public ddp bG() {
      return this.l.G().i(this.bU());
   }

   public boolean r(aqa var1) {
      return this.a(_snowman.bG());
   }

   public boolean a(ddp var1) {
      return this.bG() != null ? this.bG().a(_snowman) : false;
   }

   public void j(boolean var1) {
      this.b(5, _snowman);
   }

   protected boolean i(int var1) {
      return (this.R.a(S) & 1 << _snowman) != 0;
   }

   protected void b(int var1, boolean var2) {
      byte _snowman = this.R.a(S);
      if (_snowman) {
         this.R.b(S, (byte)(_snowman | 1 << _snowman));
      } else {
         this.R.b(S, (byte)(_snowman & ~(1 << _snowman)));
      }
   }

   public int bH() {
      return 300;
   }

   public int bI() {
      return this.R.a(ap);
   }

   public void j(int var1) {
      this.R.b(ap, _snowman);
   }

   public void a(aag var1, aql var2) {
      this.g(this.ao + 1);
      if (this.ao == 0) {
         this.f(8);
      }

      this.a(apk.b, 5.0F);
   }

   public void k(boolean var1) {
      dcn _snowman = this.cC();
      double _snowmanx;
      if (_snowman) {
         _snowmanx = Math.max(-0.9, _snowman.c - 0.03);
      } else {
         _snowmanx = Math.min(1.8, _snowman.c + 0.1);
      }

      this.n(_snowman.b, _snowmanx, _snowman.d);
   }

   public void l(boolean var1) {
      dcn _snowman = this.cC();
      double _snowmanx;
      if (_snowman) {
         _snowmanx = Math.max(-0.3, _snowman.c - 0.03);
      } else {
         _snowmanx = Math.min(0.7, _snowman.c + 0.06);
      }

      this.n(_snowman.b, _snowmanx, _snowman.d);
      this.C = 0.0F;
   }

   public void a(aag var1, aqm var2) {
   }

   protected void l(double var1, double var3, double var5) {
      fx _snowman = new fx(_snowman, _snowman, _snowman);
      dcn _snowmanx = new dcn(_snowman - (double)_snowman.u(), _snowman - (double)_snowman.v(), _snowman - (double)_snowman.w());
      fx.a _snowmanxx = new fx.a();
      gc _snowmanxxx = gc.b;
      double _snowmanxxxx = Double.MAX_VALUE;

      for (gc _snowmanxxxxx : new gc[]{gc.c, gc.d, gc.e, gc.f, gc.b}) {
         _snowmanxx.a(_snowman, _snowmanxxxxx);
         if (!this.l.d_(_snowmanxx).r(this.l, _snowmanxx)) {
            double _snowmanxxxxxx = _snowmanx.a(_snowmanxxxxx.n());
            double _snowmanxxxxxxx = _snowmanxxxxx.e() == gc.b.a ? 1.0 - _snowmanxxxxxx : _snowmanxxxxxx;
            if (_snowmanxxxxxxx < _snowmanxxxx) {
               _snowmanxxxx = _snowmanxxxxxxx;
               _snowmanxxx = _snowmanxxxxx;
            }
         }
      }

      float _snowmanxxxxxx = this.J.nextFloat() * 0.2F + 0.1F;
      float _snowmanxxxxxxx = (float)_snowmanxxx.e().a();
      dcn _snowmanxxxxxxxx = this.cC().a(0.75);
      if (_snowmanxxx.n() == gc.a.a) {
         this.n((double)(_snowmanxxxxxxx * _snowmanxxxxxx), _snowmanxxxxxxxx.c, _snowmanxxxxxxxx.d);
      } else if (_snowmanxxx.n() == gc.a.b) {
         this.n(_snowmanxxxxxxxx.b, (double)(_snowmanxxxxxxx * _snowmanxxxxxx), _snowmanxxxxxxxx.d);
      } else if (_snowmanxxx.n() == gc.a.c) {
         this.n(_snowmanxxxxxxxx.b, _snowmanxxxxxxxx.c, (double)(_snowmanxxxxxxx * _snowmanxxxxxx));
      }
   }

   public void a(ceh var1, dcn var2) {
      this.C = 0.0F;
      this.x = _snowman;
   }

   private static nr b(nr var0) {
      nx _snowman = _snowman.g().a(_snowman.c().a(null));

      for (nr _snowmanx : _snowman.b()) {
         _snowman.a(b(_snowmanx));
      }

      return _snowman;
   }

   @Override
   public nr R() {
      nr _snowman = this.T();
      return _snowman != null ? b(_snowman) : this.bJ();
   }

   protected nr bJ() {
      return this.f.g();
   }

   public boolean s(aqa var1) {
      return this == _snowman;
   }

   public float bK() {
      return 0.0F;
   }

   public void m(float var1) {
   }

   public void n(float var1) {
   }

   public boolean bL() {
      return true;
   }

   public boolean t(aqa var1) {
      return false;
   }

   @Override
   public String toString() {
      return String.format(
         Locale.ROOT,
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.R().getString(),
         this.g,
         this.l == null ? "~NULL~" : this.l.toString(),
         this.cD(),
         this.cE(),
         this.cH()
      );
   }

   public boolean b(apk var1) {
      return this.ax && _snowman != apk.m && !_snowman.v();
   }

   public boolean bM() {
      return this.ax;
   }

   public void m(boolean var1) {
      this.ax = _snowman;
   }

   public void u(aqa var1) {
      this.b(_snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman.p, _snowman.q);
   }

   public void v(aqa var1) {
      md _snowman = _snowman.e(new md());
      _snowman.r("Dimension");
      this.f(_snowman);
      this.aw = _snowman.aw;
      this.ac = _snowman.ac;
   }

   @Nullable
   public aqa b(aag var1) {
      if (this.l instanceof aag && !this.y) {
         this.l.Z().a("changeDimension");
         this.V();
         this.l.Z().a("reposition");
         cxm _snowman = this.a(_snowman);
         if (_snowman == null) {
            return null;
         } else {
            this.l.Z().b("reloading");
            aqa _snowmanx = this.X().a(_snowman);
            if (_snowmanx != null) {
               _snowmanx.v(this);
               _snowmanx.b(_snowman.a.b, _snowman.a.c, _snowman.a.d, _snowman.c, _snowmanx.q);
               _snowmanx.f(_snowman.b);
               _snowman.e(_snowmanx);
               if (_snowman.Y() == brx.i) {
                  aag.a(_snowman);
               }
            }

            this.bN();
            this.l.Z().c();
            ((aag)this.l).p_();
            _snowman.p_();
            this.l.Z().c();
            return _snowmanx;
         }
      } else {
         return null;
      }
   }

   protected void bN() {
      this.y = true;
   }

   @Nullable
   protected cxm a(aag var1) {
      boolean _snowman = this.l.Y() == brx.i && _snowman.Y() == brx.g;
      boolean _snowmanx = _snowman.Y() == brx.i;
      if (!_snowman && !_snowmanx) {
         boolean _snowmanxx = _snowman.Y() == brx.h;
         if (this.l.Y() != brx.h && !_snowmanxx) {
            return null;
         } else {
            cfu _snowmanxxx = _snowman.f();
            double _snowmanxxxx = Math.max(-2.9999872E7, _snowmanxxx.e() + 16.0);
            double _snowmanxxxxx = Math.max(-2.9999872E7, _snowmanxxx.f() + 16.0);
            double _snowmanxxxxxx = Math.min(2.9999872E7, _snowmanxxx.g() - 16.0);
            double _snowmanxxxxxxx = Math.min(2.9999872E7, _snowmanxxx.h() - 16.0);
            double _snowmanxxxxxxxx = chd.a(this.l.k(), _snowman.k());
            fx _snowmanxxxxxxxxx = new fx(afm.a(this.cD() * _snowmanxxxxxxxx, _snowmanxxxx, _snowmanxxxxxx), this.cE(), afm.a(this.cH() * _snowmanxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx));
            return this.a(_snowman, _snowmanxxxxxxxxx, _snowmanxx).map(var2x -> {
               ceh _snowmanxxxxxxxxxx = this.l.d_(this.ac);
               gc.a _snowmanx;
               dcn _snowmanxx;
               if (_snowmanxxxxxxxxxx.b(cex.E)) {
                  _snowmanx = _snowmanxxxxxxxxxx.c(cex.E);
                  i.a _snowmanxxx = i.a(this.ac, _snowmanx, 21, gc.a.b, 21, var2xx -> this.l.d_(var2xx) == _snowman);
                  _snowmanxx = this.a(_snowmanx, _snowmanxxx);
               } else {
                  _snowmanx = gc.a.a;
                  _snowmanxx = new dcn(0.5, 0.0, 0.0);
               }

               return cxn.a(_snowman, var2x, _snowmanx, _snowmanxx, this.a(this.ae()), this.cC(), this.p, this.q);
            }).orElse(null);
         }
      } else {
         fx _snowmanxx;
         if (_snowmanx) {
            _snowmanxx = aag.a;
         } else {
            _snowmanxx = _snowman.a(chn.a.f, _snowman.u());
         }

         return new cxm(new dcn((double)_snowmanxx.u() + 0.5, (double)_snowmanxx.v(), (double)_snowmanxx.w() + 0.5), this.cC(), this.p, this.q);
      }
   }

   protected dcn a(gc.a var1, i.a var2) {
      return cxn.a(_snowman, _snowman, this.cA(), this.a(this.ae()));
   }

   protected Optional<i.a> a(aag var1, fx var2, boolean var3) {
      return _snowman.m().a(_snowman, _snowman);
   }

   public boolean bO() {
      return true;
   }

   public float a(brp var1, brc var2, fx var3, ceh var4, cux var5, float var6) {
      return _snowman;
   }

   public boolean a(brp var1, brc var2, fx var3, ceh var4, float var5) {
      return true;
   }

   public int bP() {
      return 3;
   }

   public boolean bQ() {
      return false;
   }

   public void a(m var1) {
      _snowman.a("Entity Type", () -> aqe.a(this.X()) + " (" + this.getClass().getCanonicalName() + ")");
      _snowman.a("Entity ID", this.g);
      _snowman.a("Entity Name", () -> this.R().getString());
      _snowman.a("Entity's Exact location", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", this.cD(), this.cE(), this.cH()));
      _snowman.a("Entity's Block location", m.a(afm.c(this.cD()), afm.c(this.cE()), afm.c(this.cH())));
      dcn _snowman = this.cC();
      _snowman.a("Entity's Momentum", String.format(Locale.ROOT, "%.2f, %.2f, %.2f", _snowman.b, _snowman.c, _snowman.d));
      _snowman.a("Entity's Passengers", () -> this.cn().toString());
      _snowman.a("Entity's Vehicle", () -> this.ct().toString());
   }

   public boolean bR() {
      return this.bq() && !this.a_();
   }

   public void a_(UUID var1) {
      this.ad = _snowman;
      this.ae = this.ad.toString();
   }

   public UUID bS() {
      return this.ad;
   }

   public String bT() {
      return this.ae;
   }

   public String bU() {
      return this.ae;
   }

   public boolean bV() {
      return true;
   }

   public static double bW() {
      return e;
   }

   public static void b(double var0) {
      e = _snowman;
   }

   @Override
   public nr d() {
      return ddl.a(this.bG(), this.R()).a(var1 -> var1.a(this.cb()).a(this.bT()));
   }

   public void a(@Nullable nr var1) {
      this.R.b(aq, Optional.ofNullable(_snowman));
   }

   @Nullable
   @Override
   public nr T() {
      return this.R.a(aq).orElse(null);
   }

   @Override
   public boolean S() {
      return this.R.a(aq).isPresent();
   }

   public void n(boolean var1) {
      this.R.b(ar, _snowman);
   }

   public boolean bX() {
      return this.R.a(ar);
   }

   public final void m(double var1, double var3, double var5) {
      if (this.l instanceof aag) {
         brd _snowman = new brd(new fx(_snowman, _snowman, _snowman));
         ((aag)this.l).i().a(aal.g, _snowman, 0, this.Y());
         this.l.d(_snowman.b, _snowman.c);
         this.a(_snowman, _snowman, _snowman);
      }
   }

   public void a(double var1, double var3, double var5) {
      if (this.l instanceof aag) {
         aag _snowman = (aag)this.l;
         this.b(_snowman, _snowman, _snowman, this.p, this.q);
         this.cp().forEach(var1x -> {
            _snowman.b(var1x);
            var1x.az = true;

            for (aqa _snowmanx : var1x.ag) {
               var1x.a(_snowmanx, aqa::b);
            }
         });
      }
   }

   public boolean bY() {
      return this.bX();
   }

   public void a(us<?> var1) {
      if (T.equals(_snowman)) {
         this.x_();
      }
   }

   public void x_() {
      aqb _snowman = this.aC;
      aqx _snowmanx = this.ae();
      aqb _snowmanxx = this.a(_snowmanx);
      this.aC = _snowmanxx;
      this.aD = this.a(_snowmanx, _snowmanxx);
      if (_snowmanxx.a < _snowman.a) {
         double _snowmanxxx = (double)_snowmanxx.a / 2.0;
         this.a(new dci(this.cD() - _snowmanxxx, this.cE(), this.cH() - _snowmanxxx, this.cD() + _snowmanxxx, this.cE() + (double)_snowmanxx.b, this.cH() + _snowmanxxx));
      } else {
         dci _snowmanxxx = this.cc();
         this.a(new dci(_snowmanxxx.a, _snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.a + (double)_snowmanxx.a, _snowmanxxx.b + (double)_snowmanxx.b, _snowmanxxx.c + (double)_snowmanxx.a));
         if (_snowmanxx.a > _snowman.a && !this.Q && !this.l.v) {
            float _snowmanxxxx = _snowman.a - _snowmanxx.a;
            this.a(aqr.a, new dcn((double)_snowmanxxxx, 0.0, (double)_snowmanxxxx));
         }
      }
   }

   public gc bZ() {
      return gc.a((double)this.p);
   }

   public gc ca() {
      return this.bZ();
   }

   protected nv cb() {
      return new nv(nv.a.c, new nv.b(this.X(), this.bS(), this.R()));
   }

   public boolean a(aah var1) {
      return true;
   }

   public dci cc() {
      return this.al;
   }

   public dci cd() {
      return this.cc();
   }

   protected dci d(aqx var1) {
      aqb _snowman = this.a(_snowman);
      float _snowmanx = _snowman.a / 2.0F;
      dcn _snowmanxx = new dcn(this.cD() - (double)_snowmanx, this.cE(), this.cH() - (double)_snowmanx);
      dcn _snowmanxxx = new dcn(this.cD() + (double)_snowmanx, this.cE() + (double)_snowman.b, this.cH() + (double)_snowmanx);
      return new dci(_snowmanxx, _snowmanxxx);
   }

   public void a(dci var1) {
      this.al = _snowman;
   }

   protected float a(aqx var1, aqb var2) {
      return _snowman.b * 0.85F;
   }

   public float e(aqx var1) {
      return this.a(_snowman, this.a(_snowman));
   }

   public final float ce() {
      return this.aD;
   }

   public dcn cf() {
      return new dcn(0.0, (double)this.ce(), (double)(this.cy() * 0.4F));
   }

   public boolean a_(int var1, bmb var2) {
      return false;
   }

   @Override
   public void a(nr var1, UUID var2) {
   }

   public brx cg() {
      return this.l;
   }

   @Nullable
   public MinecraftServer ch() {
      return this.l.l();
   }

   public aou a(bfw var1, dcn var2, aot var3) {
      return aou.c;
   }

   public boolean ci() {
      return false;
   }

   public void a(aqm var1, aqa var2) {
      if (_snowman instanceof aqm) {
         bpu.a((aqm)_snowman, _snowman);
      }

      bpu.b(_snowman, _snowman);
   }

   public void b(aah var1) {
   }

   public void c(aah var1) {
   }

   public float a(bzm var1) {
      float _snowman = afm.g(this.p);
      switch (_snowman) {
         case c:
            return _snowman + 180.0F;
         case d:
            return _snowman + 270.0F;
         case b:
            return _snowman + 90.0F;
         default:
            return _snowman;
      }
   }

   public float a(byg var1) {
      float _snowman = afm.g(this.p);
      switch (_snowman) {
         case b:
            return -_snowman;
         case c:
            return 180.0F - _snowman;
         default:
            return _snowman;
      }
   }

   public boolean cj() {
      return false;
   }

   public boolean ck() {
      boolean _snowman = this.az;
      this.az = false;
      return _snowman;
   }

   public boolean cl() {
      boolean _snowman = this.au;
      this.au = false;
      return _snowman;
   }

   @Nullable
   public aqa cm() {
      return null;
   }

   public List<aqa> cn() {
      return (List<aqa>)(this.ag.isEmpty() ? Collections.emptyList() : Lists.newArrayList(this.ag));
   }

   public boolean w(aqa var1) {
      for (aqa _snowman : this.cn()) {
         if (_snowman.equals(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public boolean a(Class<? extends aqa> var1) {
      for (aqa _snowman : this.cn()) {
         if (_snowman.isAssignableFrom(_snowman.getClass())) {
            return true;
         }
      }

      return false;
   }

   public Collection<aqa> co() {
      Set<aqa> _snowman = Sets.newHashSet();

      for (aqa _snowmanx : this.cn()) {
         _snowman.add(_snowmanx);
         _snowmanx.a(false, _snowman);
      }

      return _snowman;
   }

   public Stream<aqa> cp() {
      return Stream.concat(Stream.of(this), this.ag.stream().flatMap(aqa::cp));
   }

   public boolean cq() {
      Set<aqa> _snowman = Sets.newHashSet();
      this.a(true, _snowman);
      return _snowman.size() == 1;
   }

   private void a(boolean var1, Set<aqa> var2) {
      for (aqa _snowman : this.cn()) {
         if (!_snowman || aah.class.isAssignableFrom(_snowman.getClass())) {
            _snowman.add(_snowman);
         }

         _snowman.a(_snowman, _snowman);
      }
   }

   public aqa cr() {
      aqa _snowman = this;

      while (_snowman.br()) {
         _snowman = _snowman.ct();
      }

      return _snowman;
   }

   public boolean x(aqa var1) {
      return this.cr() == _snowman.cr();
   }

   public boolean y(aqa var1) {
      for (aqa _snowman : this.cn()) {
         if (_snowman.equals(_snowman)) {
            return true;
         }

         if (_snowman.y(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public boolean cs() {
      aqa _snowman = this.cm();
      return _snowman instanceof bfw ? ((bfw)_snowman).ez() : !this.l.v;
   }

   protected static dcn a(double var0, double var2, float var4) {
      double _snowman = (_snowman + _snowman + 1.0E-5F) / 2.0;
      float _snowmanx = -afm.a(_snowman * (float) (Math.PI / 180.0));
      float _snowmanxx = afm.b(_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxx = Math.max(Math.abs(_snowmanx), Math.abs(_snowmanxx));
      return new dcn((double)_snowmanx * _snowman / (double)_snowmanxxx, 0.0, (double)_snowmanxx * _snowman / (double)_snowmanxxx);
   }

   public dcn b(aqm var1) {
      return new dcn(this.cD(), this.cc().e, this.cH());
   }

   @Nullable
   public aqa ct() {
      return this.ah;
   }

   public cvc y_() {
      return cvc.a;
   }

   public adr cu() {
      return adr.g;
   }

   protected int cv() {
      return 1;
   }

   public db cw() {
      return new db(this, this.cA(), this.bi(), this.l instanceof aag ? (aag)this.l : null, this.y(), this.R().getString(), this.d(), this.l.l(), this);
   }

   protected int y() {
      return 0;
   }

   public boolean k(int var1) {
      return this.y() >= _snowman;
   }

   @Override
   public boolean a() {
      return this.l.V().b(brt.n);
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean R_() {
      return true;
   }

   public void a(dj.a var1, dcn var2) {
      dcn _snowman = _snowman.a(this);
      double _snowmanx = _snowman.b - _snowman.b;
      double _snowmanxx = _snowman.c - _snowman.c;
      double _snowmanxxx = _snowman.d - _snowman.d;
      double _snowmanxxxx = (double)afm.a(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx);
      this.q = afm.g((float)(-(afm.d(_snowmanxx, _snowmanxxxx) * 180.0F / (float)Math.PI)));
      this.p = afm.g((float)(afm.d(_snowmanxxx, _snowmanx) * 180.0F / (float)Math.PI) - 90.0F);
      this.m(this.p);
      this.s = this.q;
      this.r = this.p;
   }

   public boolean a(ael<cuw> var1, double var2) {
      dci _snowman = this.cc().h(0.001);
      int _snowmanx = afm.c(_snowman.a);
      int _snowmanxx = afm.f(_snowman.d);
      int _snowmanxxx = afm.c(_snowman.b);
      int _snowmanxxxx = afm.f(_snowman.e);
      int _snowmanxxxxx = afm.c(_snowman.c);
      int _snowmanxxxxxx = afm.f(_snowman.f);
      if (!this.l.a(_snowmanx, _snowmanxxx, _snowmanxxxxx, _snowmanxx, _snowmanxxxx, _snowmanxxxxxx)) {
         return false;
      } else {
         double _snowmanxxxxxxx = 0.0;
         boolean _snowmanxxxxxxxx = this.bV();
         boolean _snowmanxxxxxxxxx = false;
         dcn _snowmanxxxxxxxxxx = dcn.a;
         int _snowmanxxxxxxxxxxx = 0;
         fx.a _snowmanxxxxxxxxxxxx = new fx.a();

         for (int _snowmanxxxxxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxxxxxx < _snowmanxx; _snowmanxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                  _snowmanxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                  cux _snowmanxxxxxxxxxxxxxxxx = this.l.b(_snowmanxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxx.a(_snowman)) {
                     double _snowmanxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx.a((brc)this.l, _snowmanxxxxxxxxxxxx));
                     if (_snowmanxxxxxxxxxxxxxxxxx >= _snowman.b) {
                        _snowmanxxxxxxxxx = true;
                        _snowmanxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxxx - _snowman.b, _snowmanxxxxxxx);
                        if (_snowmanxxxxxxxx) {
                           dcn _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.c(this.l, _snowmanxxxxxxxxxxxx);
                           if (_snowmanxxxxxxx < 0.4) {
                              _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxx);
                           }

                           _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.e(_snowmanxxxxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxx++;
                        }
                     }
                  }
               }
            }
         }

         if (_snowmanxxxxxxxxxx.f() > 0.0) {
            if (_snowmanxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.a(1.0 / (double)_snowmanxxxxxxxxxxx);
            }

            if (!(this instanceof bfw)) {
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.d();
            }

            dcn _snowmanxxxxxxxxxxxxx = this.cC();
            _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.a(_snowman * 1.0);
            double _snowmanxxxxxxxxxxxxxx = 0.003;
            if (Math.abs(_snowmanxxxxxxxxxxxxx.b) < 0.003 && Math.abs(_snowmanxxxxxxxxxxxxx.d) < 0.003 && _snowmanxxxxxxxxxx.f() < 0.0045000000000000005) {
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.d().a(0.0045000000000000005);
            }

            this.f(this.cC().e(_snowmanxxxxxxxxxx));
         }

         this.M.put(_snowman, _snowmanxxxxxxx);
         return _snowmanxxxxxxxxx;
      }
   }

   public double b(ael<cuw> var1) {
      return this.M.getDouble(_snowman);
   }

   public double cx() {
      return (double)this.ce() < 0.4 ? 0.0 : 0.4;
   }

   public final float cy() {
      return this.aC.a;
   }

   public final float cz() {
      return this.aC.b;
   }

   public abstract oj<?> P();

   public aqb a(aqx var1) {
      return this.f.l();
   }

   public dcn cA() {
      return this.ai;
   }

   public fx cB() {
      return this.aj;
   }

   public dcn cC() {
      return this.ak;
   }

   public void f(dcn var1) {
      this.ak = _snowman;
   }

   public void n(double var1, double var3, double var5) {
      this.f(new dcn(_snowman, _snowman, _snowman));
   }

   public final double cD() {
      return this.ai.b;
   }

   public double c(double var1) {
      return this.ai.b + (double)this.cy() * _snowman;
   }

   public double d(double var1) {
      return this.c((2.0 * this.J.nextDouble() - 1.0) * _snowman);
   }

   public final double cE() {
      return this.ai.c;
   }

   public double e(double var1) {
      return this.ai.c + (double)this.cz() * _snowman;
   }

   public double cF() {
      return this.e(this.J.nextDouble());
   }

   public double cG() {
      return this.ai.c + (double)this.aD;
   }

   public final double cH() {
      return this.ai.d;
   }

   public double f(double var1) {
      return this.ai.d + (double)this.cy() * _snowman;
   }

   public double g(double var1) {
      return this.f((2.0 * this.J.nextDouble() - 1.0) * _snowman);
   }

   public void o(double var1, double var3, double var5) {
      if (this.ai.b != _snowman || this.ai.c != _snowman || this.ai.d != _snowman) {
         this.ai = new dcn(_snowman, _snowman, _snowman);
         int _snowman = afm.c(_snowman);
         int _snowmanx = afm.c(_snowman);
         int _snowmanxx = afm.c(_snowman);
         if (_snowman != this.aj.u() || _snowmanx != this.aj.v() || _snowmanxx != this.aj.w()) {
            this.aj = new fx(_snowman, _snowmanx, _snowmanxx);
         }

         this.au = true;
      }
   }

   public void cI() {
   }

   public dcn o(float var1) {
      return this.l(_snowman).b(0.0, (double)this.aD * 0.7, 0.0);
   }

   @FunctionalInterface
   public interface a {
      void accept(aqa var1, double var2, double var4, double var6);
   }
}
