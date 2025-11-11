import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import javax.annotation.Nullable;

public class cga {
   private static final EnumSet<chn.a> n = EnumSet.of(chn.a.c, chn.a.a);
   private static final EnumSet<chn.a> o = EnumSet.of(chn.a.d, chn.a.b, chn.a.e, chn.a.f);
   private static final cga.c p = (var0, var1, var2, var3, var4, var5) -> {
      if (var5 instanceof cgp && !var5.k().b(var0)) {
         ((cgp)var5).a(var0);
      }

      return CompletableFuture.completedFuture(Either.left(var5));
   };
   public static final cga a = a("empty", null, -1, n, cga.a.a, (var0, var1, var2, var3) -> {
   });
   public static final cga b = a("structure_starts", a, 0, n, cga.a.a, (var0, var1, var2, var3, var4, var5, var6, var7) -> {
      if (!var7.k().b(var0)) {
         if (var1.l().aX().A().b()) {
            var2.a(var1.r(), var1.a(), var7, var3, var1.C());
         }

         if (var7 instanceof cgp) {
            ((cgp)var7).a(var0);
         }
      }

      return CompletableFuture.completedFuture(Either.left(var7));
   });
   public static final cga c = a("structure_references", b, 8, n, cga.a.a, (var0, var1, var2, var3) -> {
      aam _snowman = new aam(var0, var2);
      var1.a((bsr)_snowman, var0.a().a(_snowman), var3);
   });
   public static final cga d = a("biomes", c, 0, n, cga.a.a, (var0, var1, var2, var3) -> var1.a(var0.r().b(gm.ay), var3));
   public static final cga e = a("noise", d, 8, n, cga.a.a, (var0, var1, var2, var3) -> {
      aam _snowman = new aam(var0, var2);
      var1.a((bry)_snowman, var0.a().a(_snowman), var3);
   });
   public static final cga f = a("surface", e, 0, n, cga.a.a, (var0, var1, var2, var3) -> var1.a(new aam(var0, var2), var3));
   public static final cga g = a("carvers", f, 0, n, cga.a.a, (var0, var1, var2, var3) -> var1.a(var0.C(), var0.d(), var3, chm.a.a));
   public static final cga h = a("liquid_carvers", g, 0, o, cga.a.a, (var0, var1, var2, var3) -> var1.a(var0.C(), var0.d(), var3, chm.a.b));
   public static final cga i = a("features", h, 8, o, cga.a.a, (var0, var1, var2, var3, var4, var5, var6, var7) -> {
      cgp _snowman = (cgp)var7;
      _snowman.a(var4);
      if (!var7.k().b(var0)) {
         chn.a(var7, EnumSet.of(chn.a.e, chn.a.f, chn.a.d, chn.a.b));
         aam _snowmanx = new aam(var1, var6);
         var2.a(_snowmanx, var1.a().a(_snowmanx));
         _snowman.a(var0);
      }

      return CompletableFuture.completedFuture(Either.left(var7));
   });
   public static final cga j = a(
      "light",
      i,
      1,
      o,
      cga.a.a,
      (var0, var1, var2, var3, var4, var5, var6, var7) -> a(var0, var4, var7),
      (var0, var1, var2, var3, var4, var5) -> a(var0, var3, var5)
   );
   public static final cga k = a("spawn", j, 0, o, cga.a.a, (var0, var1, var2, var3) -> var1.a(new aam(var0, var2)));
   public static final cga l = a("heightmaps", k, 0, o, cga.a.a, (var0, var1, var2, var3) -> {
   });
   public static final cga m = a(
      "full", l, 0, o, cga.a.b, (var0, var1, var2, var3, var4, var5, var6, var7) -> var5.apply(var7), (var0, var1, var2, var3, var4, var5) -> var4.apply(var5)
   );
   private static final List<cga> q = ImmutableList.of(m, i, h, b, b, b, b, b, b, b, b);
   private static final IntList r = x.a(new IntArrayList(a().size()), var0 -> {
      int _snowman = 0;

      for (int _snowmanx = a().size() - 1; _snowmanx >= 0; _snowmanx--) {
         while (_snowman + 1 < q.size() && _snowmanx <= q.get(_snowman + 1).c()) {
            _snowman++;
         }

         var0.add(0, _snowman);
      }
   });
   private final String s;
   private final int t;
   private final cga u;
   private final cga.b v;
   private final cga.c w;
   private final int x;
   private final cga.a y;
   private final EnumSet<chn.a> z;

   private static CompletableFuture<Either<cfw, zr.a>> a(cga var0, aaj var1, cfw var2) {
      boolean _snowman = a(_snowman, _snowman);
      if (!_snowman.k().b(_snowman)) {
         ((cgp)_snowman).a(_snowman);
      }

      return _snowman.a(_snowman, _snowman).thenApply(Either::left);
   }

   private static cga a(String var0, @Nullable cga var1, int var2, EnumSet<chn.a> var3, cga.a var4, cga.d var5) {
      return a(_snowman, _snowman, _snowman, _snowman, _snowman, (cga.b)_snowman);
   }

   private static cga a(String var0, @Nullable cga var1, int var2, EnumSet<chn.a> var3, cga.a var4, cga.b var5) {
      return a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, p);
   }

   private static cga a(String var0, @Nullable cga var1, int var2, EnumSet<chn.a> var3, cga.a var4, cga.b var5, cga.c var6) {
      return gm.a(gm.Z, _snowman, new cga(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static List<cga> a() {
      List<cga> _snowman = Lists.newArrayList();

      cga _snowmanx;
      for (_snowmanx = m; _snowmanx.e() != _snowmanx; _snowmanx = _snowmanx.e()) {
         _snowman.add(_snowmanx);
      }

      _snowman.add(_snowmanx);
      Collections.reverse(_snowman);
      return _snowman;
   }

   private static boolean a(cga var0, cfw var1) {
      return _snowman.k().b(_snowman) && _snowman.r();
   }

   public static cga a(int var0) {
      if (_snowman >= q.size()) {
         return a;
      } else {
         return _snowman < 0 ? m : q.get(_snowman);
      }
   }

   public static int b() {
      return q.size();
   }

   public static int a(cga var0) {
      return r.getInt(_snowman.c());
   }

   cga(String var1, @Nullable cga var2, int var3, EnumSet<chn.a> var4, cga.a var5, cga.b var6, cga.c var7) {
      this.s = _snowman;
      this.u = _snowman == null ? this : _snowman;
      this.v = _snowman;
      this.w = _snowman;
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
      this.t = _snowman == null ? 0 : _snowman.c() + 1;
   }

   public int c() {
      return this.t;
   }

   public String d() {
      return this.s;
   }

   public cga e() {
      return this.u;
   }

   public CompletableFuture<Either<cfw, zr.a>> a(
      aag var1, cfy var2, csw var3, aaj var4, Function<cfw, CompletableFuture<Either<cfw, zr.a>>> var5, List<cfw> var6
   ) {
      return this.v.doWork(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.get(_snowman.size() / 2));
   }

   public CompletableFuture<Either<cfw, zr.a>> a(aag var1, csw var2, aaj var3, Function<cfw, CompletableFuture<Either<cfw, zr.a>>> var4, cfw var5) {
      return this.w.doWork(this, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public int f() {
      return this.x;
   }

   public cga.a g() {
      return this.y;
   }

   public static cga a(String var0) {
      return gm.Z.a(vk.a(_snowman));
   }

   public EnumSet<chn.a> h() {
      return this.z;
   }

   public boolean b(cga var1) {
      return this.c() >= _snowman.c();
   }

   @Override
   public String toString() {
      return gm.Z.b(this).toString();
   }

   public static enum a {
      a,
      b;

      private a() {
      }
   }

   interface b {
      CompletableFuture<Either<cfw, zr.a>> doWork(
         cga var1, aag var2, cfy var3, csw var4, aaj var5, Function<cfw, CompletableFuture<Either<cfw, zr.a>>> var6, List<cfw> var7, cfw var8
      );
   }

   interface c {
      CompletableFuture<Either<cfw, zr.a>> doWork(cga var1, aag var2, csw var3, aaj var4, Function<cfw, CompletableFuture<Either<cfw, zr.a>>> var5, cfw var6);
   }

   interface d extends cga.b {
      @Override
      default CompletableFuture<Either<cfw, zr.a>> doWork(
         cga var1, aag var2, cfy var3, csw var4, aaj var5, Function<cfw, CompletableFuture<Either<cfw, zr.a>>> var6, List<cfw> var7, cfw var8
      ) {
         if (!_snowman.k().b(_snowman)) {
            this.doWork(_snowman, _snowman, _snowman, _snowman);
            if (_snowman instanceof cgp) {
               ((cgp)_snowman).a(_snowman);
            }
         }

         return CompletableFuture.completedFuture(Either.left(_snowman));
      }

      void doWork(aag var1, cfy var2, List<cfw> var3, cfw var4);
   }
}
