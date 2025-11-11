import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import javax.annotation.Nullable;

public abstract class ceg {
   protected static final gc[] ar = new gc[]{gc.e, gc.f, gc.c, gc.d, gc.a, gc.b};
   protected final cva as;
   protected final boolean at;
   protected final float au;
   protected final boolean av;
   protected final cae aw;
   protected final float ax;
   protected final float ay;
   protected final float az;
   protected final boolean aA;
   protected final ceg.c aB;
   @Nullable
   protected vk aC;

   public ceg(ceg.c var1) {
      this.as = _snowman.a;
      this.at = _snowman.c;
      this.aC = _snowman.m;
      this.au = _snowman.f;
      this.av = _snowman.i;
      this.aw = _snowman.d;
      this.ax = _snowman.j;
      this.ay = _snowman.k;
      this.az = _snowman.l;
      this.aA = _snowman.v;
      this.aB = _snowman;
   }

   @Deprecated
   public void a(ceh var1, bry var2, fx var3, int var4, int var5) {
   }

   @Deprecated
   public boolean a(ceh var1, brc var2, fx var3, cxe var4) {
      switch (_snowman) {
         case a:
            return !_snowman.r(_snowman, _snowman);
         case b:
            return _snowman.b(_snowman).a(aef.b);
         case c:
            return !_snowman.r(_snowman, _snowman);
         default:
            return false;
      }
   }

   @Deprecated
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return _snowman;
   }

   @Deprecated
   public boolean a(ceh var1, ceh var2, gc var3) {
      return false;
   }

   @Deprecated
   public void a(ceh var1, brx var2, fx var3, buo var4, fx var5, boolean var6) {
      rz.a(_snowman, _snowman);
   }

   @Deprecated
   public void b(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
   }

   @Deprecated
   public void a(ceh var1, brx var2, fx var3, ceh var4, boolean var5) {
      if (this.q() && !_snowman.a(_snowman.b())) {
         _snowman.o(_snowman);
      }
   }

   @Deprecated
   public aou a(ceh var1, brx var2, fx var3, bfw var4, aot var5, dcj var6) {
      return aou.c;
   }

   @Deprecated
   public boolean a(ceh var1, brx var2, fx var3, int var4, int var5) {
      return false;
   }

   @Deprecated
   public bzh b(ceh var1) {
      return bzh.c;
   }

   @Deprecated
   public boolean c_(ceh var1) {
      return false;
   }

   @Deprecated
   public boolean b_(ceh var1) {
      return false;
   }

   @Deprecated
   public cvc f(ceh var1) {
      return this.as.g();
   }

   @Deprecated
   public cux d(ceh var1) {
      return cuy.a.h();
   }

   @Deprecated
   public boolean a(ceh var1) {
      return false;
   }

   public ceg.b ah_() {
      return ceg.b.a;
   }

   @Deprecated
   public ceh a(ceh var1, bzm var2) {
      return _snowman;
   }

   @Deprecated
   public ceh a(ceh var1, byg var2) {
      return _snowman;
   }

   @Deprecated
   public boolean a(ceh var1, bny var2) {
      return this.as.e() && (_snowman.m().a() || _snowman.m().b() != this.h());
   }

   @Deprecated
   public boolean a(ceh var1, cuw var2) {
      return this.as.e() || !this.as.b();
   }

   @Deprecated
   public List<bmb> a(ceh var1, cyv.a var2) {
      vk _snowman = this.r();
      if (_snowman == cyq.a) {
         return Collections.emptyList();
      } else {
         cyv _snowmanx = _snowman.a(dbc.g, _snowman).a(dbb.l);
         aag _snowmanxx = _snowmanx.c();
         cyy _snowmanxxx = _snowmanxx.l().aJ().a(_snowman);
         return _snowmanxxx.a(_snowmanx);
      }
   }

   @Deprecated
   public long a(ceh var1, fx var2) {
      return afm.a(_snowman);
   }

   @Deprecated
   public ddh d(ceh var1, brc var2, fx var3) {
      return _snowman.j(_snowman, _snowman);
   }

   @Deprecated
   public ddh e(ceh var1, brc var2, fx var3) {
      return this.c(_snowman, _snowman, _snowman, dcs.a());
   }

   @Deprecated
   public ddh a_(ceh var1, brc var2, fx var3) {
      return dde.a();
   }

   @Deprecated
   public int f(ceh var1, brc var2, fx var3) {
      if (_snowman.i(_snowman, _snowman)) {
         return _snowman.K();
      } else {
         return _snowman.a(_snowman, _snowman) ? 0 : 1;
      }
   }

   @Nullable
   @Deprecated
   public aox b(ceh var1, brx var2, fx var3) {
      return null;
   }

   @Deprecated
   public boolean a(ceh var1, brz var2, fx var3) {
      return true;
   }

   @Deprecated
   public float a(ceh var1, brc var2, fx var3) {
      return _snowman.r(_snowman, _snowman) ? 0.2F : 1.0F;
   }

   @Deprecated
   public int a(ceh var1, brx var2, fx var3) {
      return 0;
   }

   @Deprecated
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return dde.b();
   }

   @Deprecated
   public ddh c(ceh var1, brc var2, fx var3, dcs var4) {
      return this.at ? _snowman.j(_snowman, _snowman) : dde.a();
   }

   @Deprecated
   public ddh a(ceh var1, brc var2, fx var3, dcs var4) {
      return this.c(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      this.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public void a(ceh var1, aag var2, fx var3, Random var4) {
   }

   @Deprecated
   public float a(ceh var1, bfw var2, brc var3, fx var4) {
      float _snowman = _snowman.h(_snowman, _snowman);
      if (_snowman == -1.0F) {
         return 0.0F;
      } else {
         int _snowmanx = _snowman.d(_snowman) ? 30 : 100;
         return _snowman.c(_snowman) / _snowman / (float)_snowmanx;
      }
   }

   @Deprecated
   public void a(ceh var1, aag var2, fx var3, bmb var4) {
   }

   @Deprecated
   public void a(ceh var1, brx var2, fx var3, bfw var4) {
   }

   @Deprecated
   public int a(ceh var1, brc var2, fx var3, gc var4) {
      return 0;
   }

   @Deprecated
   public void a(ceh var1, brx var2, fx var3, aqa var4) {
   }

   @Deprecated
   public int b(ceh var1, brc var2, fx var3, gc var4) {
      return 0;
   }

   public final boolean q() {
      return this instanceof bwm;
   }

   public final vk r() {
      if (this.aC == null) {
         vk _snowman = gm.Q.b(this.p());
         this.aC = new vk(_snowman.b(), "blocks/" + _snowman.a());
      }

      return this.aC;
   }

   @Deprecated
   public void a(brx var1, ceh var2, dcj var3, bgm var4) {
   }

   public abstract blx h();

   protected abstract buo p();

   public cvb s() {
      return this.aB.b.apply(this.p().n());
   }

   public abstract static class a extends cej<buo, ceh> {
      private final int b;
      private final boolean e;
      private final boolean f;
      private final cva g;
      private final cvb h;
      private final float i;
      private final boolean j;
      private final boolean k;
      private final ceg.e l;
      private final ceg.e m;
      private final ceg.e n;
      private final ceg.e o;
      private final ceg.e p;
      @Nullable
      protected ceg.a.a a;

      protected a(buo var1, ImmutableMap<cfj<?>, Comparable<?>> var2, MapCodec<ceh> var3) {
         super(_snowman, _snowman, _snowman);
         ceg.c _snowman = _snowman.aB;
         this.b = _snowman.e.applyAsInt(this.p());
         this.e = _snowman.c_(this.p());
         this.f = _snowman.o;
         this.g = _snowman.a;
         this.h = _snowman.b.apply(this.p());
         this.i = _snowman.g;
         this.j = _snowman.h;
         this.k = _snowman.n;
         this.l = _snowman.q;
         this.m = _snowman.r;
         this.n = _snowman.s;
         this.o = _snowman.t;
         this.p = _snowman.u;
      }

      public void a() {
         if (!this.b().o()) {
            this.a = new ceg.a.a(this.p());
         }
      }

      public buo b() {
         return this.c;
      }

      public cva c() {
         return this.g;
      }

      public boolean a(brc var1, fx var2, aqe<?> var3) {
         return this.b().aB.p.test(this.p(), _snowman, _snowman, _snowman);
      }

      public boolean a(brc var1, fx var2) {
         return this.a != null ? this.a.g : this.b().b(this.p(), _snowman, _snowman);
      }

      public int b(brc var1, fx var2) {
         return this.a != null ? this.a.h : this.b().f(this.p(), _snowman, _snowman);
      }

      public ddh a(brc var1, fx var2, gc var3) {
         return this.a != null && this.a.i != null ? this.a.i[_snowman.ordinal()] : dde.a(this.c(_snowman, _snowman), _snowman);
      }

      public ddh c(brc var1, fx var2) {
         return this.b().d(this.p(), _snowman, _snowman);
      }

      public boolean d() {
         return this.a == null || this.a.c;
      }

      public boolean e() {
         return this.e;
      }

      public int f() {
         return this.b;
      }

      public boolean g() {
         return this.f;
      }

      public cvb d(brc var1, fx var2) {
         return this.h;
      }

      public ceh a(bzm var1) {
         return this.b().a(this.p(), _snowman);
      }

      public ceh a(byg var1) {
         return this.b().a(this.p(), _snowman);
      }

      public bzh h() {
         return this.b().b(this.p());
      }

      public boolean e(brc var1, fx var2) {
         return this.p.test(this.p(), _snowman, _snowman);
      }

      public float f(brc var1, fx var2) {
         return this.b().a(this.p(), _snowman, _snowman);
      }

      public boolean g(brc var1, fx var2) {
         return this.l.test(this.p(), _snowman, _snowman);
      }

      public boolean i() {
         return this.b().b_(this.p());
      }

      public int b(brc var1, fx var2, gc var3) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public boolean j() {
         return this.b().a(this.p());
      }

      public int a(brx var1, fx var2) {
         return this.b().a(this.p(), _snowman, _snowman);
      }

      public float h(brc var1, fx var2) {
         return this.i;
      }

      public float a(bfw var1, brc var2, fx var3) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public int c(brc var1, fx var2, gc var3) {
         return this.b().b(this.p(), _snowman, _snowman, _snowman);
      }

      public cvc k() {
         return this.b().f(this.p());
      }

      public boolean i(brc var1, fx var2) {
         if (this.a != null) {
            return this.a.a;
         } else {
            ceh _snowman = this.p();
            return _snowman.l() ? buo.a(_snowman.c(_snowman, _snowman)) : false;
         }
      }

      public boolean l() {
         return this.k;
      }

      public boolean a(ceh var1, gc var2) {
         return this.b().a(this.p(), _snowman, _snowman);
      }

      public ddh j(brc var1, fx var2) {
         return this.a(_snowman, _snowman, dcs.a());
      }

      public ddh a(brc var1, fx var2, dcs var3) {
         return this.b().b(this.p(), _snowman, _snowman, _snowman);
      }

      public ddh k(brc var1, fx var2) {
         return this.a != null ? this.a.b : this.b(_snowman, _snowman, dcs.a());
      }

      public ddh b(brc var1, fx var2, dcs var3) {
         return this.b().c(this.p(), _snowman, _snowman, _snowman);
      }

      public ddh l(brc var1, fx var2) {
         return this.b().e(this.p(), _snowman, _snowman);
      }

      public ddh c(brc var1, fx var2, dcs var3) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public ddh m(brc var1, fx var2) {
         return this.b().a_(this.p(), _snowman, _snowman);
      }

      public final boolean a(brc var1, fx var2, aqa var3) {
         return this.a(_snowman, _snowman, _snowman, gc.b);
      }

      public final boolean a(brc var1, fx var2, aqa var3, gc var4) {
         return buo.a(this.b(_snowman, _snowman, dcs.a(_snowman)), _snowman);
      }

      public dcn n(brc var1, fx var2) {
         ceg.b _snowman = this.b().ah_();
         if (_snowman == ceg.b.a) {
            return dcn.a;
         } else {
            long _snowmanx = afm.c(_snowman.u(), 0, _snowman.w());
            return new dcn(
               ((double)((float)(_snowmanx & 15L) / 15.0F) - 0.5) * 0.5,
               _snowman == ceg.b.c ? ((double)((float)(_snowmanx >> 4 & 15L) / 15.0F) - 1.0) * 0.2 : 0.0,
               ((double)((float)(_snowmanx >> 8 & 15L) / 15.0F) - 0.5) * 0.5
            );
         }
      }

      public boolean a(brx var1, fx var2, int var3, int var4) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman, _snowman);
      }

      public void a(brx var1, fx var2, buo var3, fx var4, boolean var5) {
         this.b().a(this.p(), _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public final void a(bry var1, fx var2, int var3) {
         this.a(_snowman, _snowman, _snowman, 512);
      }

      public final void a(bry var1, fx var2, int var3, int var4) {
         this.b();
         fx.a _snowman = new fx.a();

         for (gc _snowmanx : ceg.ar) {
            _snowman.a(_snowman, _snowmanx);
            ceh _snowmanxx = _snowman.d_(_snowman);
            ceh _snowmanxxx = _snowmanxx.a(_snowmanx.f(), this.p(), _snowman, _snowman, _snowman);
            buo.a(_snowmanxx, _snowmanxxx, _snowman, _snowman, _snowman, _snowman);
         }
      }

      public final void b(bry var1, fx var2, int var3) {
         this.b(_snowman, _snowman, _snowman, 512);
      }

      public void b(bry var1, fx var2, int var3, int var4) {
         this.b().a(this.p(), _snowman, _snowman, _snowman, _snowman);
      }

      public void a(brx var1, fx var2, ceh var3, boolean var4) {
         this.b().b(this.p(), _snowman, _snowman, _snowman, _snowman);
      }

      public void b(brx var1, fx var2, ceh var3, boolean var4) {
         this.b().a(this.p(), _snowman, _snowman, _snowman, _snowman);
      }

      public void a(aag var1, fx var2, Random var3) {
         this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public void b(aag var1, fx var2, Random var3) {
         this.b().b(this.p(), _snowman, _snowman, _snowman);
      }

      public void a(brx var1, fx var2, aqa var3) {
         this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public void a(aag var1, fx var2, bmb var3) {
         this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public List<bmb> a(cyv.a var1) {
         return this.b().a(this.p(), _snowman);
      }

      public aou a(brx var1, bfw var2, aot var3, dcj var4) {
         return this.b().a(this.p(), _snowman, _snowman.a(), _snowman, _snowman, _snowman);
      }

      public void a(brx var1, fx var2, bfw var3) {
         this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public boolean o(brc var1, fx var2) {
         return this.m.test(this.p(), _snowman, _snowman);
      }

      public boolean p(brc var1, fx var2) {
         return this.n.test(this.p(), _snowman, _snowman);
      }

      public ceh a(gc var1, ceh var2, bry var3, fx var4, fx var5) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public boolean a(brc var1, fx var2, cxe var3) {
         return this.b().a(this.p(), _snowman, _snowman, _snowman);
      }

      public boolean a(bny var1) {
         return this.b().a(this.p(), _snowman);
      }

      public boolean a(cuw var1) {
         return this.b().a(this.p(), _snowman);
      }

      public boolean a(brz var1, fx var2) {
         return this.b().a(this.p(), _snowman, _snowman);
      }

      public boolean q(brc var1, fx var2) {
         return this.o.test(this.p(), _snowman, _snowman);
      }

      @Nullable
      public aox b(brx var1, fx var2) {
         return this.b().b(this.p(), _snowman, _snowman);
      }

      public boolean a(ael<buo> var1) {
         return this.b().a(_snowman);
      }

      public boolean a(ael<buo> var1, Predicate<ceg.a> var2) {
         return this.b().a(_snowman) && _snowman.test(this);
      }

      public boolean a(buo var1) {
         return this.b().a(_snowman);
      }

      public cux m() {
         return this.b().d(this.p());
      }

      public boolean n() {
         return this.b().a_(this.p());
      }

      public long a(fx var1) {
         return this.b().a(this.p(), _snowman);
      }

      public cae o() {
         return this.b().k(this.p());
      }

      public void a(brx var1, ceh var2, dcj var3, bgm var4) {
         this.b().a(_snowman, _snowman, _snowman, _snowman);
      }

      public boolean d(brc var1, fx var2, gc var3) {
         return this.a(_snowman, _snowman, _snowman, cat.a);
      }

      public boolean a(brc var1, fx var2, gc var3, cat var4) {
         return this.a != null ? this.a.a(_snowman, _snowman) : _snowman.a(this.p(), _snowman, _snowman, _snowman);
      }

      public boolean r(brc var1, fx var2) {
         return this.a != null ? this.a.d : buo.a(this.k(_snowman, _snowman));
      }

      protected abstract ceh p();

      public boolean q() {
         return this.j;
      }

      static final class a {
         private static final gc[] e = gc.values();
         private static final int f = cat.values().length;
         protected final boolean a;
         private final boolean g;
         private final int h;
         @Nullable
         private final ddh[] i;
         protected final ddh b;
         protected final boolean c;
         private final boolean[] j;
         protected final boolean d;

         private a(ceh var1) {
            buo _snowman = _snowman.b();
            this.a = _snowman.i(brl.a, fx.b);
            this.g = _snowman.b(_snowman, brl.a, fx.b);
            this.h = _snowman.f(_snowman, brl.a, fx.b);
            if (!_snowman.l()) {
               this.i = null;
            } else {
               this.i = new ddh[e.length];
               ddh _snowmanx = _snowman.d(_snowman, brl.a, fx.b);

               for (gc _snowmanxx : e) {
                  this.i[_snowmanxx.ordinal()] = dde.a(_snowmanx, _snowmanxx);
               }
            }

            this.b = _snowman.c(_snowman, brl.a, fx.b, dcs.a());
            this.c = Arrays.stream(gc.a.values()).anyMatch(var1x -> this.b.b(var1x) < 0.0 || this.b.c(var1x) > 1.0);
            this.j = new boolean[e.length * f];

            for (gc _snowmanx : e) {
               for (cat _snowmanxx : cat.values()) {
                  this.j[b(_snowmanx, _snowmanxx)] = _snowmanxx.a(_snowman, brl.a, fx.b, _snowmanx);
               }
            }

            this.d = buo.a(_snowman.k(brl.a, fx.b));
         }

         public boolean a(gc var1, cat var2) {
            return this.j[b(_snowman, _snowman)];
         }

         private static int b(gc var0, cat var1) {
            return _snowman.ordinal() * f + _snowman.ordinal();
         }
      }
   }

   public static enum b {
      a,
      b,
      c;

      private b() {
      }
   }

   public static class c {
      private cva a;
      private Function<ceh, cvb> b;
      private boolean c = true;
      private cae d = cae.e;
      private ToIntFunction<ceh> e = var0 -> 0;
      private float f;
      private float g;
      private boolean h;
      private boolean i;
      private float j = 0.6F;
      private float k = 1.0F;
      private float l = 1.0F;
      private vk m;
      private boolean n = true;
      private boolean o;
      private ceg.d<aqe<?>> p = (var0, var1x, var2x, var3) -> var0.d(var1x, var2x, gc.b) && var0.f() < 14;
      private ceg.e q = (var0, var1x, var2x) -> var0.c().f() && var0.r(var1x, var2x);
      private ceg.e r = (var1x, var2x, var3) -> this.a.c() && var1x.r(var2x, var3);
      private ceg.e s = this.r;
      private ceg.e t = (var0, var1x, var2x) -> false;
      private ceg.e u = (var0, var1x, var2x) -> false;
      private boolean v;

      private c(cva var1, cvb var2) {
         this(_snowman, var1x -> _snowman);
      }

      private c(cva var1, Function<ceh, cvb> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public static ceg.c a(cva var0) {
         return a(_snowman, _snowman.h());
      }

      public static ceg.c a(cva var0, bkx var1) {
         return a(_snowman, _snowman.f());
      }

      public static ceg.c a(cva var0, cvb var1) {
         return new ceg.c(_snowman, _snowman);
      }

      public static ceg.c a(cva var0, Function<ceh, cvb> var1) {
         return new ceg.c(_snowman, _snowman);
      }

      public static ceg.c a(ceg var0) {
         ceg.c _snowman = new ceg.c(_snowman.as, _snowman.aB.b);
         _snowman.a = _snowman.aB.a;
         _snowman.g = _snowman.aB.g;
         _snowman.f = _snowman.aB.f;
         _snowman.c = _snowman.aB.c;
         _snowman.i = _snowman.aB.i;
         _snowman.e = _snowman.aB.e;
         _snowman.b = _snowman.aB.b;
         _snowman.d = _snowman.aB.d;
         _snowman.j = _snowman.aB.j;
         _snowman.k = _snowman.aB.k;
         _snowman.v = _snowman.aB.v;
         _snowman.n = _snowman.aB.n;
         _snowman.o = _snowman.aB.o;
         _snowman.h = _snowman.aB.h;
         return _snowman;
      }

      public ceg.c a() {
         this.c = false;
         this.n = false;
         return this;
      }

      public ceg.c b() {
         this.n = false;
         return this;
      }

      public ceg.c a(float var1) {
         this.j = _snowman;
         return this;
      }

      public ceg.c b(float var1) {
         this.k = _snowman;
         return this;
      }

      public ceg.c c(float var1) {
         this.l = _snowman;
         return this;
      }

      public ceg.c a(cae var1) {
         this.d = _snowman;
         return this;
      }

      public ceg.c a(ToIntFunction<ceh> var1) {
         this.e = _snowman;
         return this;
      }

      public ceg.c a(float var1, float var2) {
         this.g = _snowman;
         this.f = Math.max(0.0F, _snowman);
         return this;
      }

      public ceg.c c() {
         return this.d(0.0F);
      }

      public ceg.c d(float var1) {
         this.a(_snowman, _snowman);
         return this;
      }

      public ceg.c d() {
         this.i = true;
         return this;
      }

      public ceg.c e() {
         this.v = true;
         return this;
      }

      public ceg.c f() {
         this.m = cyq.a;
         return this;
      }

      public ceg.c a(buo var1) {
         this.m = _snowman.r();
         return this;
      }

      public ceg.c g() {
         this.o = true;
         return this;
      }

      public ceg.c a(ceg.d<aqe<?>> var1) {
         this.p = _snowman;
         return this;
      }

      public ceg.c a(ceg.e var1) {
         this.q = _snowman;
         return this;
      }

      public ceg.c b(ceg.e var1) {
         this.r = _snowman;
         return this;
      }

      public ceg.c c(ceg.e var1) {
         this.s = _snowman;
         return this;
      }

      public ceg.c d(ceg.e var1) {
         this.t = _snowman;
         return this;
      }

      public ceg.c e(ceg.e var1) {
         this.u = _snowman;
         return this;
      }

      public ceg.c h() {
         this.h = true;
         return this;
      }
   }

   public interface d<A> {
      boolean test(ceh var1, brc var2, fx var3, A var4);
   }

   public interface e {
      boolean test(ceh var1, brc var2, fx var3);
   }
}
