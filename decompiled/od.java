import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public final class od {
   private static final Map<k, od> a = Stream.of(k.values())
      .filter(k::d)
      .collect(ImmutableMap.toImmutableMap(Function.identity(), var0 -> new od(var0.e(), var0.f())));
   private static final Map<String, od> b = a.values().stream().collect(ImmutableMap.toImmutableMap(var0 -> var0.d, Function.identity()));
   private final int c;
   @Nullable
   private final String d;

   private od(int var1, String var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   private od(int var1) {
      this.c = _snowman;
      this.d = null;
   }

   public int a() {
      return this.c;
   }

   public String b() {
      return this.d != null ? this.d : this.c();
   }

   private String c() {
      return String.format("#%06X", this.c);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         od _snowman = (od)_snowman;
         return this.c == _snowman.c;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c, this.d);
   }

   @Override
   public String toString() {
      return this.d != null ? this.d : this.c();
   }

   @Nullable
   public static od a(k var0) {
      return a.get(_snowman);
   }

   public static od a(int var0) {
      return new od(_snowman);
   }

   @Nullable
   public static od a(String var0) {
      if (_snowman.startsWith("#")) {
         try {
            int _snowman = Integer.parseInt(_snowman.substring(1), 16);
            return a(_snowman);
         } catch (NumberFormatException var2) {
            return null;
         }
      } else {
         return b.get(_snowman);
      }
   }
}
