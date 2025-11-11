import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ele {
   public static final elf a = new elf();
   private final ele.a b;

   public ele(ele.a var1) {
      this.b = _snowman;
   }

   public ele.a a() {
      return this.b;
   }

   public static enum a {
      a("none"),
      b("partial"),
      c("full");

      private static final Map<String, ele.a> d = Arrays.stream(values()).collect(Collectors.toMap(ele.a::a, var0 -> (ele.a)var0));
      private final String e;

      private a(String var3) {
         this.e = _snowman;
      }

      public String a() {
         return this.e;
      }

      public static ele.a a(String var0) {
         return d.getOrDefault(_snowman, a);
      }
   }
}
