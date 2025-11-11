import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class chm {
   public static enum a implements afs {
      a("air"),
      b("liquid");

      public static final Codec<chm.a> c = afs.a(chm.a::values, chm.a::a);
      private static final Map<String, chm.a> d = Arrays.stream(values()).collect(Collectors.toMap(chm.a::b, var0 -> (chm.a)var0));
      private final String e;

      private a(String var3) {
         this.e = _snowman;
      }

      public String b() {
         return this.e;
      }

      @Nullable
      public static chm.a a(String var0) {
         return d.get(_snowman);
      }

      @Override
      public String a() {
         return this.e;
      }
   }

   public static enum b {
      a,
      b,
      c,
      d,
      e,
      f,
      g,
      h,
      i,
      j;

      private b() {
      }
   }
}
