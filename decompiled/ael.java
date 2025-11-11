import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface ael<T> {
   static <T> Codec<ael<T>> a(Supplier<aem<T>> var0) {
      return vk.a
         .flatXmap(
            var1 -> Optional.ofNullable(_snowman.get().a(var1)).<DataResult>map(DataResult::success).orElseGet(() -> DataResult.error("Unknown tag: " + var1)),
            var1 -> Optional.ofNullable(_snowman.get().a(var1)).<DataResult>map(DataResult::success).orElseGet(() -> DataResult.error("Unknown tag: " + var1))
         );
   }

   boolean a(T var1);

   List<T> b();

   default T a(Random var1) {
      List<T> _snowman = this.b();
      return _snowman.get(_snowman.nextInt(_snowman.size()));
   }

   static <T> ael<T> b(Set<T> var0) {
      return aei.a(_snowman);
   }

   public static class a {
      private final List<ael.b> a = Lists.newArrayList();

      public a() {
      }

      public static ael.a a() {
         return new ael.a();
      }

      public ael.a a(ael.b var1) {
         this.a.add(_snowman);
         return this;
      }

      public ael.a a(ael.d var1, String var2) {
         return this.a(new ael.b(_snowman, _snowman));
      }

      public ael.a a(vk var1, String var2) {
         return this.a(new ael.c(_snowman), _snowman);
      }

      public ael.a c(vk var1, String var2) {
         return this.a(new ael.h(_snowman), _snowman);
      }

      public <T> Optional<ael<T>> a(Function<vk, ael<T>> var1, Function<vk, T> var2) {
         Builder<T> _snowman = ImmutableSet.builder();

         for (ael.b _snowmanx : this.a) {
            if (!_snowmanx.a().a(_snowman, _snowman, _snowman::add)) {
               return Optional.empty();
            }
         }

         return Optional.of(ael.b(_snowman.build()));
      }

      public Stream<ael.b> b() {
         return this.a.stream();
      }

      public <T> Stream<ael.b> b(Function<vk, ael<T>> var1, Function<vk, T> var2) {
         return this.b().filter(var2x -> !var2x.a().a(_snowman, _snowman, var0x -> {
            }));
      }

      public ael.a a(JsonObject var1, String var2) {
         JsonArray _snowman = afd.u(_snowman, "values");
         List<ael.d> _snowmanx = Lists.newArrayList();

         for (JsonElement _snowmanxx : _snowman) {
            _snowmanx.add(a(_snowmanxx));
         }

         if (afd.a(_snowman, "replace", false)) {
            this.a.clear();
         }

         _snowmanx.forEach(var2x -> this.a.add(new ael.b(var2x, _snowman)));
         return this;
      }

      private static ael.d a(JsonElement var0) {
         String _snowman;
         boolean _snowmanx;
         if (_snowman.isJsonObject()) {
            JsonObject _snowmanxx = _snowman.getAsJsonObject();
            _snowman = afd.h(_snowmanxx, "id");
            _snowmanx = afd.a(_snowmanxx, "required", true);
         } else {
            _snowman = afd.a(_snowman, "id");
            _snowmanx = true;
         }

         if (_snowman.startsWith("#")) {
            vk _snowmanxx = new vk(_snowman.substring(1));
            return (ael.d)(_snowmanx ? new ael.h(_snowmanxx) : new ael.g(_snowmanxx));
         } else {
            vk _snowmanxx = new vk(_snowman);
            return (ael.d)(_snowmanx ? new ael.c(_snowmanxx) : new ael.f(_snowmanxx));
         }
      }

      public JsonObject c() {
         JsonObject _snowman = new JsonObject();
         JsonArray _snowmanx = new JsonArray();

         for (ael.b _snowmanxx : this.a) {
            _snowmanxx.a().a(_snowmanx);
         }

         _snowman.addProperty("replace", false);
         _snowman.add("values", _snowmanx);
         return _snowman;
      }
   }

   public static class b {
      private final ael.d a;
      private final String b;

      private b(ael.d var1, String var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public ael.d a() {
         return this.a;
      }

      @Override
      public String toString() {
         return this.a.toString() + " (from " + this.b + ")";
      }
   }

   public static class c implements ael.d {
      private final vk a;

      public c(vk var1) {
         this.a = _snowman;
      }

      @Override
      public <T> boolean a(Function<vk, ael<T>> var1, Function<vk, T> var2, Consumer<T> var3) {
         T _snowman = _snowman.apply(this.a);
         if (_snowman == null) {
            return false;
         } else {
            _snowman.accept(_snowman);
            return true;
         }
      }

      @Override
      public void a(JsonArray var1) {
         _snowman.add(this.a.toString());
      }

      @Override
      public String toString() {
         return this.a.toString();
      }
   }

   public interface d {
      <T> boolean a(Function<vk, ael<T>> var1, Function<vk, T> var2, Consumer<T> var3);

      void a(JsonArray var1);
   }

   public interface e<T> extends ael<T> {
      vk a();
   }

   public static class f implements ael.d {
      private final vk a;

      public f(vk var1) {
         this.a = _snowman;
      }

      @Override
      public <T> boolean a(Function<vk, ael<T>> var1, Function<vk, T> var2, Consumer<T> var3) {
         T _snowman = _snowman.apply(this.a);
         if (_snowman != null) {
            _snowman.accept(_snowman);
         }

         return true;
      }

      @Override
      public void a(JsonArray var1) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", this.a.toString());
         _snowman.addProperty("required", false);
         _snowman.add(_snowman);
      }

      @Override
      public String toString() {
         return this.a.toString() + "?";
      }
   }

   public static class g implements ael.d {
      private final vk a;

      public g(vk var1) {
         this.a = _snowman;
      }

      @Override
      public <T> boolean a(Function<vk, ael<T>> var1, Function<vk, T> var2, Consumer<T> var3) {
         ael<T> _snowman = _snowman.apply(this.a);
         if (_snowman != null) {
            _snowman.b().forEach(_snowman);
         }

         return true;
      }

      @Override
      public void a(JsonArray var1) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", "#" + this.a);
         _snowman.addProperty("required", false);
         _snowman.add(_snowman);
      }

      @Override
      public String toString() {
         return "#" + this.a + "?";
      }
   }

   public static class h implements ael.d {
      private final vk a;

      public h(vk var1) {
         this.a = _snowman;
      }

      @Override
      public <T> boolean a(Function<vk, ael<T>> var1, Function<vk, T> var2, Consumer<T> var3) {
         ael<T> _snowman = _snowman.apply(this.a);
         if (_snowman == null) {
            return false;
         } else {
            _snowman.b().forEach(_snowman);
            return true;
         }
      }

      @Override
      public void a(JsonArray var1) {
         _snowman.add("#" + this.a);
      }

      @Override
      public String toString() {
         return "#" + this.a;
      }
   }
}
