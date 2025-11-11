import java.util.Arrays;
import java.util.Comparator;

public enum djh {
   a(0, "options.ao.off"),
   b(1, "options.ao.min"),
   c(2, "options.ao.max");

   private static final djh[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(djh::a)).toArray(djh[]::new);
   private final int e;
   private final String f;

   private djh(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f;
   }

   public static djh a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
