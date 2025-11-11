import java.util.Arrays;
import java.util.Comparator;

public enum djt {
   a(0, "options.graphics.fast"),
   b(1, "options.graphics.fancy"),
   c(2, "options.graphics.fabulous");

   private static final djt[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(djt::a)).toArray(djt[]::new);
   private final int e;
   private final String f;

   private djt(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f;
   }

   public djt c() {
      return a(this.a() + 1);
   }

   @Override
   public String toString() {
      switch (this) {
         case a:
            return "fast";
         case b:
            return "fancy";
         case c:
            return "fabulous";
         default:
            throw new IllegalArgumentException();
      }
   }

   public static djt a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
