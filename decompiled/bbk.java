import java.util.Arrays;
import java.util.Comparator;

public enum bbk {
   a(0),
   b(1),
   c(2),
   d(3),
   e(4),
   f(5),
   g(6);

   private static final bbk[] h = Arrays.stream(values()).sorted(Comparator.comparingInt(bbk::a)).toArray(bbk[]::new);
   private final int i;

   private bbk(int var3) {
      this.i = _snowman;
   }

   public int a() {
      return this.i;
   }

   public static bbk a(int var0) {
      return h[_snowman % h.length];
   }
}
