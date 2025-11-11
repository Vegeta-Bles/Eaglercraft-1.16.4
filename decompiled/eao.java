import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.Hash.Strategy;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import javax.annotation.Nullable;

public abstract class eao extends ean {
   private static final eao W = a("solid", dfk.h, 7, 2097152, true, false, eao.b.a().a(l).a(t).a(m).a(true));
   private static final eao X = a("cutout_mipped", dfk.h, 7, 131072, true, false, eao.b.a().a(l).a(t).a(m).a(j).a(true));
   private static final eao Y = a("cutout", dfk.h, 7, 131072, true, false, eao.b.a().a(l).a(t).a(n).a(j).a(true));
   private static final eao Z = a("translucent", dfk.h, 7, 262144, true, true, C());
   private static final eao aa = a("translucent_moving_block", dfk.h, 7, 262144, false, true, D());
   private static final eao ab = a("translucent_no_crumbling", dfk.h, 7, 262144, false, true, C());
   private static final eao ac = a("leash", dfk.m, 7, 256, eao.b.a().a(o).a(A).a(t).a(false));
   private static final eao ad = a("water_mask", dfk.k, 7, 256, eao.b.a().a(o).a(G).a(false));
   private static final eao ae = a("armor_glint", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(r).a(J).a(false));
   private static final eao af = a("armor_entity_glint", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(s).a(J).a(false));
   private static final eao ag = a("glint_translucent", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(r).a(T).a(false));
   private static final eao ah = a("glint", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(r).a(false));
   private static final eao ai = a("glint_direct", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(r).a(false));
   private static final eao aj = a("entity_glint", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(T).a(s).a(false));
   private static final eao ak = a("entity_glint_direct", dfk.n, 7, 256, eao.b.a().a(new ean.o(efo.a, true, false)).a(F).a(A).a(C).a(e).a(s).a(false));
   private static final eao al = a("lightning", dfk.l, 7, 256, false, true, eao.b.a().a(E).a(d).a(R).a(l).a(false));
   private static final eao am = a("tripwire", dfk.h, 7, 262144, true, true, E());
   public static final eao.a V = a("lines", dfk.l, 1, 256, eao.b.a().a(new ean.i(OptionalDouble.empty())).a(J).a(g).a(T).a(E).a(false));
   private final dfr an;
   private final int ao;
   private final int ap;
   private final boolean aq;
   private final boolean ar;
   private final Optional<eao> as;

   public static eao c() {
      return W;
   }

   public static eao d() {
      return X;
   }

   public static eao e() {
      return Y;
   }

   private static eao.b C() {
      return eao.b.a().a(l).a(t).a(m).a(g).a(P).a(true);
   }

   public static eao f() {
      return Z;
   }

   private static eao.b D() {
      return eao.b.a().a(l).a(t).a(m).a(g).a(T).a(true);
   }

   public static eao g() {
      return aa;
   }

   public static eao h() {
      return ab;
   }

   public static eao a(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(b).a(x).a(i).a(A).a(t).a(v).a(J).a(true);
      return a("armor_cutout_no_cull", dfk.i, 7, 256, true, false, _snowman);
   }

   public static eao b(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(b).a(x).a(t).a(v).a(true);
      return a("entity_solid", dfk.i, 7, 256, true, false, _snowman);
   }

   public static eao c(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(b).a(x).a(i).a(t).a(v).a(true);
      return a("entity_cutout", dfk.i, 7, 256, true, false, _snowman);
   }

   public static eao a(vk var0, boolean var1) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(b).a(x).a(i).a(A).a(t).a(v).a(_snowman);
      return a("entity_cutout_no_cull", dfk.i, 7, 256, true, false, _snowman);
   }

   public static eao d(vk var0) {
      return a(_snowman, true);
   }

   public static eao b(vk var0, boolean var1) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(b).a(x).a(i).a(A).a(t).a(v).a(J).a(_snowman);
      return a("entity_cutout_no_cull_z_offset", dfk.i, 7, 256, true, false, _snowman);
   }

   public static eao e(vk var0) {
      return b(_snowman, true);
   }

   public static eao f(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(g).a(T).a(x).a(i).a(t).a(v).a(ean.E).a(true);
      return a("item_entity_translucent_cull", dfk.i, 7, 256, true, true, _snowman);
   }

   public static eao g(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(g).a(x).a(i).a(t).a(v).a(true);
      return a("entity_translucent_cull", dfk.i, 7, 256, true, true, _snowman);
   }

   public static eao c(vk var0, boolean var1) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(g).a(x).a(i).a(A).a(t).a(v).a(_snowman);
      return a("entity_translucent", dfk.i, 7, 256, true, true, _snowman);
   }

   public static eao h(vk var0) {
      return c(_snowman, true);
   }

   public static eao i(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(j).a(x).a(l).a(A).a(t).a(true);
      return a("entity_smooth_cutout", dfk.i, 7, 256, _snowman);
   }

   public static eao d(vk var0, boolean var1) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(_snowman ? g : b).a(_snowman ? F : E).a(K).a(false);
      return a("beacon_beam", dfk.h, 7, 256, false, true, _snowman);
   }

   public static eao j(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(x).a(i).a(C).a(A).a(t).a(v).a(false);
      return a("entity_decal", dfk.i, 7, 256, _snowman);
   }

   public static eao k(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(g).a(x).a(i).a(A).a(t).a(v).a(F).a(false);
      return a("entity_no_outline", dfk.i, 7, 256, false, true, _snowman);
   }

   public static eao l(vk var0) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(g).a(x).a(i).a(z).a(t).a(v).a(F).a(D).a(J).a(false);
      return a("entity_shadow", dfk.i, 7, 256, false, false, _snowman);
   }

   public static eao a(vk var0, float var1) {
      eao.b _snowman = eao.b.a().a(new ean.o(_snowman, false, false)).a(new ean.a(_snowman)).a(A).a(true);
      return a("entity_alpha", dfk.i, 7, 256, _snowman);
   }

   public static eao m(vk var0) {
      ean.o _snowman = new ean.o(_snowman, false, false);
      return a("eyes", dfk.i, 7, 256, false, true, eao.b.a().a(_snowman).a(c).a(F).a(M).a(false));
   }

   public static eao a(vk var0, float var1, float var2) {
      return a(
         "energy_swirl", dfk.i, 7, 256, false, true, eao.b.a().a(new ean.o(_snowman, false, false)).a(new ean.j(_snowman, _snowman)).a(M).a(c).a(x).a(i).a(A).a(t).a(v).a(false)
      );
   }

   public static eao i() {
      return ac;
   }

   public static eao j() {
      return ad;
   }

   public static eao n(vk var0) {
      return a(_snowman, A);
   }

   public static eao a(vk var0, ean.c var1) {
      return a("outline", dfk.o, 7, 256, eao.b.a().a(new ean.o(_snowman, false, false)).a(_snowman).a(B).a(i).a(q).a(K).a(O).a(eao.c.b));
   }

   public static eao k() {
      return ae;
   }

   public static eao l() {
      return af;
   }

   public static eao m() {
      return ag;
   }

   public static eao n() {
      return ah;
   }

   public static eao o() {
      return ai;
   }

   public static eao p() {
      return aj;
   }

   public static eao q() {
      return ak;
   }

   public static eao o(vk var0) {
      ean.o _snowman = new ean.o(_snowman, false, false);
      return a("crumbling", dfk.h, 7, 256, false, true, eao.b.a().a(_snowman).a(i).a(f).a(F).a(I).a(false));
   }

   public static eao p(vk var0) {
      return a("text", dfk.q, 7, 256, false, true, eao.b.a().a(new ean.o(_snowman, false, false)).a(i).a(g).a(t).a(false));
   }

   public static eao q(vk var0) {
      return a("text_see_through", dfk.q, 7, 256, false, true, eao.b.a().a(new ean.o(_snowman, false, false)).a(i).a(g).a(t).a(B).a(F).a(false));
   }

   public static eao r() {
      return al;
   }

   private static eao.b E() {
      return eao.b.a().a(l).a(t).a(m).a(g).a(R).a(true);
   }

   public static eao s() {
      return am;
   }

   public static eao a(int var0) {
      ean.q _snowman;
      ean.o _snowmanx;
      if (_snowman <= 1) {
         _snowman = g;
         _snowmanx = new ean.o(ecs.a, false, false);
      } else {
         _snowman = c;
         _snowmanx = new ean.o(ecs.c, false, false);
      }

      return a("end_portal", dfk.l, 7, 256, false, true, eao.b.a().a(_snowman).a(_snowmanx).a(new ean.m(_snowman)).a(M).a(false));
   }

   public static eao t() {
      return V;
   }

   public eao(String var1, dfr var2, int var3, int var4, boolean var5, boolean var6, Runnable var7, Runnable var8) {
      super(_snowman, _snowman, _snowman);
      this.an = _snowman;
      this.ao = _snowman;
      this.ap = _snowman;
      this.aq = _snowman;
      this.ar = _snowman;
      this.as = Optional.of(this);
   }

   public static eao.a a(String var0, dfr var1, int var2, int var3, eao.b var4) {
      return a(_snowman, _snowman, _snowman, _snowman, false, false, _snowman);
   }

   public static eao.a a(String var0, dfr var1, int var2, int var3, boolean var4, boolean var5, eao.b var6) {
      return eao.a.c(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(dfh var1, int var2, int var3, int var4) {
      if (_snowman.j()) {
         if (this.ar) {
            _snowman.a((float)_snowman, (float)_snowman, (float)_snowman);
         }

         _snowman.c();
         this.a();
         dfi.a(_snowman);
         this.b();
      }
   }

   @Override
   public String toString() {
      return this.a;
   }

   public static List<eao> u() {
      return ImmutableList.of(c(), d(), e(), f(), s());
   }

   public int v() {
      return this.ap;
   }

   public dfr w() {
      return this.an;
   }

   public int x() {
      return this.ao;
   }

   public Optional<eao> y() {
      return Optional.empty();
   }

   public boolean z() {
      return false;
   }

   public boolean A() {
      return this.aq;
   }

   public Optional<eao> B() {
      return this.as;
   }

   static final class a extends eao {
      private static final ObjectOpenCustomHashSet<eao.a> W = new ObjectOpenCustomHashSet(eao.a.a.a);
      private final eao.b X;
      private final int Y;
      private final Optional<eao> Z;
      private final boolean aa;

      private a(String var1, dfr var2, int var3, int var4, boolean var5, boolean var6, eao.b var7) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, () -> _snowman.q.forEach(ean::a), () -> _snowman.q.forEach(ean::b));
         this.X = _snowman;
         this.Z = _snowman.p == eao.c.c ? _snowman.a.c().map(var1x -> a(var1x, _snowman.g)) : Optional.empty();
         this.aa = _snowman.p == eao.c.b;
         this.Y = Objects.hash(super.hashCode(), _snowman);
      }

      private static eao.a c(String var0, dfr var1, int var2, int var3, boolean var4, boolean var5, eao.b var6) {
         return (eao.a)W.addOrGet(new eao.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      }

      @Override
      public Optional<eao> y() {
         return this.Z;
      }

      @Override
      public boolean z() {
         return this.aa;
      }

      @Override
      public boolean equals(@Nullable Object var1) {
         return this == _snowman;
      }

      @Override
      public int hashCode() {
         return this.Y;
      }

      @Override
      public String toString() {
         return "RenderType[" + this.X + ']';
      }

      static enum a implements Strategy<eao.a> {
         a;

         private a() {
         }

         public int a(@Nullable eao.a var1) {
            return _snowman == null ? 0 : _snowman.Y;
         }

         public boolean a(@Nullable eao.a var1, @Nullable eao.a var2) {
            if (_snowman == _snowman) {
               return true;
            } else {
               return _snowman != null && _snowman != null ? Objects.equals(_snowman.X, _snowman.X) : false;
            }
         }
      }
   }

   public static final class b {
      private final ean.o a;
      private final ean.q b;
      private final ean.e c;
      private final ean.n d;
      private final ean.a e;
      private final ean.d f;
      private final ean.c g;
      private final ean.h h;
      private final ean.l i;
      private final ean.f j;
      private final ean.g k;
      private final ean.k l;
      private final ean.p m;
      private final ean.r n;
      private final ean.i o;
      private final eao.c p;
      private final ImmutableList<ean> q;

      private b(
         ean.o var1,
         ean.q var2,
         ean.e var3,
         ean.n var4,
         ean.a var5,
         ean.d var6,
         ean.c var7,
         ean.h var8,
         ean.l var9,
         ean.f var10,
         ean.g var11,
         ean.k var12,
         ean.p var13,
         ean.r var14,
         ean.i var15,
         eao.c var16
      ) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
         this.l = _snowman;
         this.m = _snowman;
         this.n = _snowman;
         this.o = _snowman;
         this.p = _snowman;
         this.q = ImmutableList.of(
            this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, new ean[]{this.m, this.n, this.o}
         );
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            eao.b _snowman = (eao.b)_snowman;
            return this.p == _snowman.p && this.q.equals(_snowman.q);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.q, this.p);
      }

      @Override
      public String toString() {
         return "CompositeState[" + this.q + ", outlineProperty=" + this.p + ']';
      }

      public static eao.b.a a() {
         return new eao.b.a();
      }

      public static class a {
         private ean.o a = ean.o;
         private ean.q b;
         private ean.e c;
         private ean.n d;
         private ean.a e;
         private ean.d f;
         private ean.c g;
         private ean.h h;
         private ean.l i;
         private ean.f j;
         private ean.g k;
         private ean.k l;
         private ean.p m;
         private ean.r n;
         private ean.i o;

         private a() {
            this.b = ean.b;
            this.c = ean.y;
            this.d = ean.k;
            this.e = ean.h;
            this.f = ean.D;
            this.g = ean.z;
            this.h = ean.u;
            this.i = ean.w;
            this.j = ean.L;
            this.k = ean.H;
            this.l = ean.N;
            this.m = ean.p;
            this.n = ean.E;
            this.o = ean.U;
         }

         public eao.b.a a(ean.o var1) {
            this.a = _snowman;
            return this;
         }

         public eao.b.a a(ean.q var1) {
            this.b = _snowman;
            return this;
         }

         public eao.b.a a(ean.e var1) {
            this.c = _snowman;
            return this;
         }

         public eao.b.a a(ean.n var1) {
            this.d = _snowman;
            return this;
         }

         public eao.b.a a(ean.a var1) {
            this.e = _snowman;
            return this;
         }

         public eao.b.a a(ean.d var1) {
            this.f = _snowman;
            return this;
         }

         public eao.b.a a(ean.c var1) {
            this.g = _snowman;
            return this;
         }

         public eao.b.a a(ean.h var1) {
            this.h = _snowman;
            return this;
         }

         public eao.b.a a(ean.l var1) {
            this.i = _snowman;
            return this;
         }

         public eao.b.a a(ean.f var1) {
            this.j = _snowman;
            return this;
         }

         public eao.b.a a(ean.g var1) {
            this.k = _snowman;
            return this;
         }

         public eao.b.a a(ean.k var1) {
            this.l = _snowman;
            return this;
         }

         public eao.b.a a(ean.p var1) {
            this.m = _snowman;
            return this;
         }

         public eao.b.a a(ean.r var1) {
            this.n = _snowman;
            return this;
         }

         public eao.b.a a(ean.i var1) {
            this.o = _snowman;
            return this;
         }

         public eao.b a(boolean var1) {
            return this.a(_snowman ? eao.c.c : eao.c.a);
         }

         public eao.b a(eao.c var1) {
            return new eao.b(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, _snowman);
         }
      }
   }

   static enum c {
      a("none"),
      b("is_outline"),
      c("affects_outline");

      private final String d;

      private c(String var3) {
         this.d = _snowman;
      }

      @Override
      public String toString() {
         return this.d;
      }
   }
}
