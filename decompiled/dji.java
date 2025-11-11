import java.util.Arrays;
import java.util.Comparator;

public enum dji {
   a(0, "options.off"),
   b(1, "options.attack.crosshair"),
   c(2, "options.attack.hotbar");

   private static final dji[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(dji::a)).toArray(dji[]::new);
   private final int e;
   private final String f;

   private dji(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f;
   }

   public static dji a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
