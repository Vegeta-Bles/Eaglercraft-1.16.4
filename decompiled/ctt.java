import com.mojang.serialization.Codec;
import java.util.Random;

public abstract class ctt<C extends ctv> {
   private static final ceh a = bup.j.n();
   private static final ceh b = bup.i.n();
   private static final ceh c = bup.l.n();
   private static final ceh d = bup.E.n();
   private static final ceh e = bup.b.n();
   private static final ceh K = bup.k.n();
   private static final ceh L = bup.C.n();
   private static final ceh M = bup.D.n();
   private static final ceh N = bup.fF.n();
   private static final ceh O = bup.dT.n();
   private static final ceh P = bup.cM.n();
   private static final ceh Q = bup.cL.n();
   private static final ceh R = bup.ee.n();
   private static final ceh S = bup.mu.n();
   private static final ceh T = bup.ml.n();
   private static final ceh U = bup.iK.n();
   private static final ceh V = bup.mn.n();
   private static final ceh W = bup.np.n();
   private static final ceh X = bup.cO.n();
   private static final ceh Y = bup.iJ.n();
   public static final ctu f = new ctu(c, a, d);
   public static final ctu g = new ctu(d, d, d);
   public static final ctu h = new ctu(b, a, d);
   public static final ctu i = new ctu(e, e, d);
   public static final ctu j = new ctu(K, a, d);
   public static final ctu k = new ctu(L, L, d);
   public static final ctu l = new ctu(b, a, L);
   public static final ctu m = new ctu(L, L, L);
   public static final ctu n = new ctu(M, N, d);
   public static final ctu o = new ctu(O, a, d);
   public static final ctu p = new ctu(Q, Q, Q);
   public static final ctu q = new ctu(P, P, P);
   public static final ctu r = new ctu(R, R, R);
   public static final ctu s = new ctu(S, Q, U);
   public static final ctu t = new ctu(T, Q, V);
   public static final ctu u = new ctu(W, X, Y);
   public static final ctt<ctu> v = a("default", new cth(ctu.a));
   public static final ctt<ctu> w = a("mountain", new ctm(ctu.a));
   public static final ctt<ctu> x = a("shattered_savanna", new ctr(ctu.a));
   public static final ctt<ctu> y = a("gravelly_mountain", new ctl(ctu.a));
   public static final ctt<ctu> z = a("giant_tree_taiga", new ctk(ctu.a));
   public static final ctt<ctu> A = a("swamp", new ctw(ctu.a));
   public static final ctt<ctu> B = a("badlands", new cte(ctu.a));
   public static final ctt<ctu> C = a("wooded_badlands", new ctx(ctu.a));
   public static final ctt<ctu> D = a("eroded_badlands", new cti(ctu.a));
   public static final ctt<ctu> E = a("frozen_ocean", new ctj(ctu.a));
   public static final ctt<ctu> F = a("nether", new ctp(ctu.a));
   public static final ctt<ctu> G = a("nether_forest", new cto(ctu.a));
   public static final ctt<ctu> H = a("soul_sand_valley", new cts(ctu.a));
   public static final ctt<ctu> I = a("basalt_deltas", new ctf(ctu.a));
   public static final ctt<ctu> J = a("nope", new ctq(ctu.a));
   private final Codec<ctg<C>> Z;

   private static <C extends ctv, F extends ctt<C>> F a(String var0, F var1) {
      return gm.a(gm.aA, _snowman, _snowman);
   }

   public ctt(Codec<C> var1) {
      this.Z = _snowman.fieldOf("config").xmap(this::a, ctg::a).codec();
   }

   public Codec<ctg<C>> d() {
      return this.Z;
   }

   public ctg<C> a(C var1) {
      return new ctg<>(this, _snowman);
   }

   public abstract void a(Random var1, cfw var2, bsv var3, int var4, int var5, int var6, double var7, ceh var9, ceh var10, int var11, long var12, C var14);

   public void a(long var1) {
   }
}
