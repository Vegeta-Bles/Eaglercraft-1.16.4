import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public enum aya {
   a("major_negative", -5, 100, 10, 10),
   b("minor_negative", -1, 200, 20, 20),
   c("minor_positive", 1, 200, 1, 5),
   d("major_positive", 5, 100, 0, 100),
   e("trading", 1, 25, 2, 20);

   public final String f;
   public final int g;
   public final int h;
   public final int i;
   public final int j;
   private static final Map<String, aya> k = Stream.of(values()).collect(ImmutableMap.toImmutableMap(var0 -> var0.f, Function.identity()));

   private aya(String var3, int var4, int var5, int var6, int var7) {
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   @Nullable
   public static aya a(String var0) {
      return k.get(_snowman);
   }
}
