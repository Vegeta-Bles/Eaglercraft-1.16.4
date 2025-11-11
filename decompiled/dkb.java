import java.util.Arrays;
import java.util.Comparator;

public enum dkb {
   a(0, "options.narrator.off"),
   b(1, "options.narrator.all"),
   c(2, "options.narrator.chat"),
   d(3, "options.narrator.system");

   private static final dkb[] e = Arrays.stream(values()).sorted(Comparator.comparingInt(dkb::a)).toArray(dkb[]::new);
   private final int f;
   private final nr g;

   private dkb(int var3, String var4) {
      this.f = _snowman;
      this.g = new of(_snowman);
   }

   public int a() {
      return this.f;
   }

   public nr b() {
      return this.g;
   }

   public static dkb a(int var0) {
      return e[afm.b(_snowman, e.length)];
   }
}
