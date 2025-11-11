import java.util.Arrays;
import java.util.Comparator;

public enum bbf {
   a(0),
   b(1),
   c(2),
   d(3),
   e(4);

   private static final bbf[] f = Arrays.stream(values()).sorted(Comparator.comparingInt(bbf::a)).toArray(bbf[]::new);
   private final int g;

   private bbf(int var3) {
      this.g = _snowman;
   }

   public int a() {
      return this.g;
   }

   public static bbf a(int var0) {
      return f[_snowman % f.length];
   }
}
