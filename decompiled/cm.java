import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class cm {
   public static final cm a = new cm(ImmutableList.of());
   private final List<cm.c> b;

   private static cm.c a(String var0, JsonElement var1) {
      if (_snowman.isJsonPrimitive()) {
         String _snowman = _snowman.getAsString();
         return new cm.b(_snowman, _snowman);
      } else {
         JsonObject _snowman = afd.m(_snowman, "value");
         String _snowmanx = _snowman.has("min") ? b(_snowman.get("min")) : null;
         String _snowmanxx = _snowman.has("max") ? b(_snowman.get("max")) : null;
         return (cm.c)(_snowmanx != null && _snowmanx.equals(_snowmanxx) ? new cm.b(_snowman, _snowmanx) : new cm.d(_snowman, _snowmanx, _snowmanxx));
      }
   }

   @Nullable
   private static String b(JsonElement var0) {
      return _snowman.isJsonNull() ? null : _snowman.getAsString();
   }

   private cm(List<cm.c> var1) {
      this.b = ImmutableList.copyOf(_snowman);
   }

   public <S extends cej<?, S>> boolean a(cei<?, S> var1, S var2) {
      for (cm.c _snowman : this.b) {
         if (!_snowman.a(_snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   public boolean a(ceh var1) {
      return this.a(_snowman.b().m(), _snowman);
   }

   public boolean a(cux var1) {
      return this.a(_snowman.a().g(), _snowman);
   }

   public void a(cei<?, ?> var1, Consumer<String> var2) {
      this.b.forEach(var2x -> var2x.a(_snowman, _snowman));
   }

   public static cm a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "properties");
         List<cm.c> _snowmanx = Lists.newArrayList();

         for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
            _snowmanx.add(a(_snowmanxx.getKey(), _snowmanxx.getValue()));
         }

         return new cm(_snowmanx);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (!this.b.isEmpty()) {
            this.b.forEach(var1x -> _snowman.add(var1x.b(), var1x.a()));
         }

         return _snowman;
      }
   }

   public static class a {
      private final List<cm.c> a = Lists.newArrayList();

      private a() {
      }

      public static cm.a a() {
         return new cm.a();
      }

      public cm.a a(cfj<?> var1, String var2) {
         this.a.add(new cm.b(_snowman.f(), _snowman));
         return this;
      }

      public cm.a a(cfj<Integer> var1, int var2) {
         return this.a(_snowman, Integer.toString(_snowman));
      }

      public cm.a a(cfj<Boolean> var1, boolean var2) {
         return this.a(_snowman, Boolean.toString(_snowman));
      }

      public <T extends Comparable<T> & afs> cm.a a(cfj<T> var1, T var2) {
         return this.a(_snowman, _snowman.a());
      }

      public cm b() {
         return new cm(this.a);
      }
   }

   static class b extends cm.c {
      private final String a;

      public b(String var1, String var2) {
         super(_snowman);
         this.a = _snowman;
      }

      @Override
      protected <T extends Comparable<T>> boolean a(cej<?, ?> var1, cfj<T> var2) {
         T _snowman = _snowman.c(_snowman);
         Optional<T> _snowmanx = _snowman.b(this.a);
         return _snowmanx.isPresent() && _snowman.compareTo(_snowmanx.get()) == 0;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(this.a);
      }
   }

   abstract static class c {
      private final String a;

      public c(String var1) {
         this.a = _snowman;
      }

      public <S extends cej<?, S>> boolean a(cei<?, S> var1, S var2) {
         cfj<?> _snowman = _snowman.a(this.a);
         return _snowman == null ? false : this.a(_snowman, _snowman);
      }

      protected abstract <T extends Comparable<T>> boolean a(cej<?, ?> var1, cfj<T> var2);

      public abstract JsonElement a();

      public String b() {
         return this.a;
      }

      public void a(cei<?, ?> var1, Consumer<String> var2) {
         cfj<?> _snowman = _snowman.a(this.a);
         if (_snowman == null) {
            _snowman.accept(this.a);
         }
      }
   }

   static class d extends cm.c {
      @Nullable
      private final String a;
      @Nullable
      private final String b;

      public d(String var1, @Nullable String var2, @Nullable String var3) {
         super(_snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      protected <T extends Comparable<T>> boolean a(cej<?, ?> var1, cfj<T> var2) {
         T _snowman = _snowman.c(_snowman);
         if (this.a != null) {
            Optional<T> _snowmanx = _snowman.b(this.a);
            if (!_snowmanx.isPresent() || _snowman.compareTo(_snowmanx.get()) < 0) {
               return false;
            }
         }

         if (this.b != null) {
            Optional<T> _snowmanx = _snowman.b(this.b);
            if (!_snowmanx.isPresent() || _snowman.compareTo(_snowmanx.get()) > 0) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonElement a() {
         JsonObject _snowman = new JsonObject();
         if (this.a != null) {
            _snowman.addProperty("min", this.a);
         }

         if (this.b != null) {
            _snowman.addProperty("max", this.b);
         }

         return _snowman;
      }
   }
}
