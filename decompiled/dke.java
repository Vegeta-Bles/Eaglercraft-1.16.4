import java.util.Arrays;
import java.util.Comparator;

public enum dke {
   a(0, "options.particles.all"),
   b(1, "options.particles.decreased"),
   c(2, "options.particles.minimal");

   private static final dke[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(dke::b)).toArray(dke[]::new);
   private final int e;
   private final String f;

   private dke(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public String a() {
      return this.f;
   }

   public int b() {
      return this.e;
   }

   public static dke a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
