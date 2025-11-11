import com.google.common.collect.ImmutableSet;
import javax.annotation.Nullable;

public class bfm {
   public static final bfm a = a("none", azr.c, null);
   public static final bfm b = a("armorer", azr.d, adq.pZ);
   public static final bfm c = a("butcher", azr.e, adq.qa);
   public static final bfm d = a("cartographer", azr.f, adq.qb);
   public static final bfm e = a("cleric", azr.g, adq.qc);
   public static final bfm f = a("farmer", azr.h, ImmutableSet.of(bmd.kW, bmd.kV, bmd.qg, bmd.mK), ImmutableSet.of(bup.bX), adq.qd);
   public static final bfm g = a("fisherman", azr.i, adq.qe);
   public static final bfm h = a("fletcher", azr.j, adq.qf);
   public static final bfm i = a("leatherworker", azr.k, adq.qg);
   public static final bfm j = a("librarian", azr.l, adq.qh);
   public static final bfm k = a("mason", azr.m, adq.qi);
   public static final bfm l = a("nitwit", azr.n, null);
   public static final bfm m = a("shepherd", azr.o, adq.qj);
   public static final bfm n = a("toolsmith", azr.p, adq.qk);
   public static final bfm o = a("weaponsmith", azr.q, adq.ql);
   private final String p;
   private final azr q;
   private final ImmutableSet<blx> r;
   private final ImmutableSet<buo> s;
   @Nullable
   private final adp t;

   private bfm(String var1, azr var2, ImmutableSet<blx> var3, ImmutableSet<buo> var4, @Nullable adp var5) {
      this.p = _snowman;
      this.q = _snowman;
      this.r = _snowman;
      this.s = _snowman;
      this.t = _snowman;
   }

   public azr b() {
      return this.q;
   }

   public ImmutableSet<blx> c() {
      return this.r;
   }

   public ImmutableSet<buo> d() {
      return this.s;
   }

   @Nullable
   public adp e() {
      return this.t;
   }

   @Override
   public String toString() {
      return this.p;
   }

   static bfm a(String var0, azr var1, @Nullable adp var2) {
      return a(_snowman, _snowman, ImmutableSet.of(), ImmutableSet.of(), _snowman);
   }

   static bfm a(String var0, azr var1, ImmutableSet<blx> var2, ImmutableSet<buo> var3, @Nullable adp var4) {
      return gm.a(gm.ai, new vk(_snowman), new bfm(_snowman, _snowman, _snowman, _snowman, _snowman));
   }
}
