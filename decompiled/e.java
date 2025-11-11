import java.util.Arrays;

public enum e {
   a(0, 1, 2),
   b(1, 0, 2),
   c(0, 2, 1),
   d(1, 2, 0),
   e(2, 0, 1),
   f(2, 1, 0);

   private final int[] g;
   private final a h;
   private static final e[][] i = x.a(new e[values().length][values().length], var0 -> {
      for (e _snowman : values()) {
         for (e _snowmanx : values()) {
            int[] _snowmanxx = new int[3];

            for (int _snowmanxxx = 0; _snowmanxxx < 3; _snowmanxxx++) {
               _snowmanxx[_snowmanxxx] = _snowman.g[_snowmanx.g[_snowmanxxx]];
            }

            e _snowmanxxx = Arrays.stream(values()).filter(var1 -> Arrays.equals(var1.g, _snowman)).findFirst().get();
            var0[_snowman.ordinal()][_snowmanx.ordinal()] = _snowmanxxx;
         }
      }
   });

   private e(int var3, int var4, int var5) {
      this.g = new int[]{_snowman, _snowman, _snowman};
      this.h = new a();
      this.h.a(0, this.a(0), 1.0F);
      this.h.a(1, this.a(1), 1.0F);
      this.h.a(2, this.a(2), 1.0F);
   }

   public e a(e var1) {
      return i[this.ordinal()][_snowman.ordinal()];
   }

   public int a(int var1) {
      return this.g[_snowman];
   }

   public a a() {
      return this.h;
   }
}
