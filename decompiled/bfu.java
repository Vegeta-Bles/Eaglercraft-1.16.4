import java.util.Arrays;
import java.util.Comparator;

public enum bfu {
   a(0, "options.chat.visibility.full"),
   b(1, "options.chat.visibility.system"),
   c(2, "options.chat.visibility.hidden");

   private static final bfu[] d = Arrays.stream(values()).sorted(Comparator.comparingInt(bfu::a)).toArray(bfu[]::new);
   private final int e;
   private final String f;

   private bfu(int var3, String var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.e;
   }

   public String b() {
      return this.f;
   }

   public static bfu a(int var0) {
      return d[afm.b(_snowman, d.length)];
   }
}
