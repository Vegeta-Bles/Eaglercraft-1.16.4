import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringEscapeUtils;

public class aew {
   private final Writer a;
   private final int b;

   private aew(Writer var1, List<String> var2) throws IOException {
      this.a = _snowman;
      this.b = _snowman.size();
      this.a(_snowman.stream());
   }

   public static aew.a a() {
      return new aew.a();
   }

   public void a(Object... var1) throws IOException {
      if (_snowman.length != this.b) {
         throw new IllegalArgumentException("Invalid number of columns, expected " + this.b + ", but got " + _snowman.length);
      } else {
         this.a(Stream.of(_snowman));
      }
   }

   private void a(Stream<?> var1) throws IOException {
      this.a.write(_snowman.<CharSequence>map(aew::a).collect(Collectors.joining(",")) + "\r\n");
   }

   private static String a(@Nullable Object var0) {
      return StringEscapeUtils.escapeCsv(_snowman != null ? _snowman.toString() : "[null]");
   }

   public static class a {
      private final List<String> a = Lists.newArrayList();

      public a() {
      }

      public aew.a a(String var1) {
         this.a.add(_snowman);
         return this;
      }

      public aew a(Writer var1) throws IOException {
         return new aew(_snowman, this.a);
      }
   }
}
