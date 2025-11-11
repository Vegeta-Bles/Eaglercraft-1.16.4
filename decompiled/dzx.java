public enum dzx {
   a(new dzx.b(dzx.a.f, dzx.a.e, dzx.a.a), new dzx.b(dzx.a.f, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.a)),
   b(new dzx.b(dzx.a.f, dzx.a.b, dzx.a.d), new dzx.b(dzx.a.f, dzx.a.b, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.b, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.b, dzx.a.d)),
   c(new dzx.b(dzx.a.c, dzx.a.b, dzx.a.d), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.f, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.f, dzx.a.b, dzx.a.d)),
   d(new dzx.b(dzx.a.f, dzx.a.b, dzx.a.a), new dzx.b(dzx.a.f, dzx.a.e, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.b, dzx.a.a)),
   e(new dzx.b(dzx.a.f, dzx.a.b, dzx.a.d), new dzx.b(dzx.a.f, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.f, dzx.a.e, dzx.a.a), new dzx.b(dzx.a.f, dzx.a.b, dzx.a.a)),
   f(new dzx.b(dzx.a.c, dzx.a.b, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.a), new dzx.b(dzx.a.c, dzx.a.e, dzx.a.d), new dzx.b(dzx.a.c, dzx.a.b, dzx.a.d));

   private static final dzx[] g = x.a(new dzx[6], var0 -> {
      var0[dzx.a.e] = a;
      var0[dzx.a.b] = b;
      var0[dzx.a.d] = c;
      var0[dzx.a.a] = d;
      var0[dzx.a.f] = e;
      var0[dzx.a.c] = f;
   });
   private final dzx.b[] h;

   public static dzx a(gc var0) {
      return g[_snowman.c()];
   }

   private dzx(dzx.b... var3) {
      this.h = _snowman;
   }

   public dzx.b a(int var1) {
      return this.h[_snowman];
   }

   public static final class a {
      public static final int a = gc.d.c();
      public static final int b = gc.b.c();
      public static final int c = gc.f.c();
      public static final int d = gc.c.c();
      public static final int e = gc.a.c();
      public static final int f = gc.e.c();
   }

   public static class b {
      public final int a;
      public final int b;
      public final int c;

      private b(int var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
