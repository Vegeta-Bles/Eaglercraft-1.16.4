import java.util.Arrays;
import java.util.Comparator;

public enum djn {
   a(0, "options.off"),
   b(1, "options.clouds.fast"),
   c(2, "options.clouds.fancy");

   private static final djn[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(djn::a)).toArray(djn[]::new);
   private final int e;
   private final String f;

   private djn(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f;
   }

   public static djn a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
