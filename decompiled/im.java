import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface im extends Supplier<JsonElement> {
   void a(cei<?, ?> var1);

   static im.c a() {
      return new im.c();
   }

   static im b(im... var0) {
      return new im.a(im.b.b, Arrays.asList(_snowman));
   }

   public static class a implements im {
      private final im.b a;
      private final List<im> b;

      private a(im.b var1, List<im> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public void a(cei<?, ?> var1) {
         this.b.forEach(var1x -> var1x.a(_snowman));
      }

      public JsonElement b() {
         JsonArray _snowman = new JsonArray();
         this.b.stream().map(Supplier::get).forEach(_snowman::add);
         JsonObject _snowmanx = new JsonObject();
         _snowmanx.add(this.a.c, _snowman);
         return _snowmanx;
      }
   }

   public static enum b {
      a("AND"),
      b("OR");

      private final String c;

      private b(String var3) {
         this.c = _snowman;
      }
   }

   public static class c implements im {
      private final Map<cfj<?>, String> a = Maps.newHashMap();

      public c() {
      }

      private static <T extends Comparable<T>> String a(cfj<T> var0, Stream<T> var1) {
         return _snowman.<CharSequence>map(_snowman::a).collect(Collectors.joining("|"));
      }

      private static <T extends Comparable<T>> String c(cfj<T> var0, T var1, T[] var2) {
         return a(_snowman, Stream.concat(Stream.of(_snowman), Stream.of(_snowman)));
      }

      private <T extends Comparable<T>> void a(cfj<T> var1, String var2) {
         String _snowman = this.a.put(_snowman, _snowman);
         if (_snowman != null) {
            throw new IllegalStateException("Tried to replace " + _snowman + " value from " + _snowman + " to " + _snowman);
         }
      }

      public final <T extends Comparable<T>> im.c a(cfj<T> var1, T var2) {
         this.a(_snowman, _snowman.a(_snowman));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> im.c a(cfj<T> var1, T var2, T... var3) {
         this.a(_snowman, c(_snowman, _snowman, _snowman));
         return this;
      }

      public JsonElement b() {
         JsonObject _snowman = new JsonObject();
         this.a.forEach((var1x, var2) -> _snowman.addProperty(var1x.f(), var2));
         return _snowman;
      }

      @Override
      public void a(cei<?, ?> var1) {
         List<cfj<?>> _snowman = this.a.keySet().stream().filter(var1x -> _snowman.a(var1x.f()) != var1x).collect(Collectors.toList());
         if (!_snowman.isEmpty()) {
            throw new IllegalStateException("Properties " + _snowman + " are missing from " + _snowman);
         }
      }
   }
}
