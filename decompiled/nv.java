import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class nv {
   private static final Logger a = LogManager.getLogger();
   private final nv.a<?> b;
   private final Object c;

   public <T> nv(nv.a<T> var1, T var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public nv.a<?> a() {
      return this.b;
   }

   @Nullable
   public <T> T a(nv.a<T> var1) {
      return this.b == _snowman ? _snowman.b(this.c) : null;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         nv _snowman = (nv)_snowman;
         return this.b == _snowman.b && Objects.equals(this.c, _snowman.c);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "HoverEvent{action=" + this.b + ", value='" + this.c + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int _snowman = this.b.hashCode();
      return 31 * _snowman + (this.c != null ? this.c.hashCode() : 0);
   }

   @Nullable
   public static nv a(JsonObject var0) {
      String _snowman = afd.a(_snowman, "action", null);
      if (_snowman == null) {
         return null;
      } else {
         nv.a<?> _snowmanx = nv.a.a(_snowman);
         if (_snowmanx == null) {
            return null;
         } else {
            JsonElement _snowmanxx = _snowman.get("contents");
            if (_snowmanxx != null) {
               return _snowmanx.a(_snowmanxx);
            } else {
               nr _snowmanxxx = nr.a.a(_snowman.get("value"));
               return _snowmanxxx != null ? _snowmanx.a(_snowmanxxx) : null;
            }
         }
      }
   }

   public JsonObject b() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("action", this.b.b());
      _snowman.add("contents", this.b.a(this.c));
      return _snowman;
   }

   public static class a<T> {
      public static final nv.a<nr> a = new nv.a<>("show_text", true, nr.a::a, nr.a::b, Function.identity());
      public static final nv.a<nv.c> b = new nv.a<>("show_item", true, var0 -> nv.c.b(var0), var0 -> var0.b(), var0 -> nv.c.b(var0));
      public static final nv.a<nv.b> c = new nv.a<>("show_entity", true, nv.b::a, nv.b::a, nv.b::a);
      private static final Map<String, nv.a> d = Stream.of(a, b, c).collect(ImmutableMap.toImmutableMap(nv.a::b, var0 -> var0));
      private final String e;
      private final boolean f;
      private final Function<JsonElement, T> g;
      private final Function<T, JsonElement> h;
      private final Function<nr, T> i;

      public a(String var1, boolean var2, Function<JsonElement, T> var3, Function<T, JsonElement> var4, Function<nr, T> var5) {
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
      }

      public boolean a() {
         return this.f;
      }

      public String b() {
         return this.e;
      }

      @Nullable
      public static nv.a a(String var0) {
         return d.get(_snowman);
      }

      private T b(Object var1) {
         return (T)_snowman;
      }

      @Nullable
      public nv a(JsonElement var1) {
         T _snowman = this.g.apply(_snowman);
         return _snowman == null ? null : new nv(this, _snowman);
      }

      @Nullable
      public nv a(nr var1) {
         T _snowman = this.i.apply(_snowman);
         return _snowman == null ? null : new nv(this, _snowman);
      }

      public JsonElement a(Object var1) {
         return this.h.apply(this.b(_snowman));
      }

      @Override
      public String toString() {
         return "<action " + this.e + ">";
      }
   }

   public static class b {
      public final aqe<?> a;
      public final UUID b;
      @Nullable
      public final nr c;
      @Nullable
      private List<nr> d;

      public b(aqe<?> var1, UUID var2, @Nullable nr var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Nullable
      public static nv.b a(JsonElement var0) {
         if (!_snowman.isJsonObject()) {
            return null;
         } else {
            JsonObject _snowman = _snowman.getAsJsonObject();
            aqe<?> _snowmanx = gm.S.a(new vk(afd.h(_snowman, "type")));
            UUID _snowmanxx = UUID.fromString(afd.h(_snowman, "id"));
            nr _snowmanxxx = nr.a.a(_snowman.get("name"));
            return new nv.b(_snowmanx, _snowmanxx, _snowmanxxx);
         }
      }

      @Nullable
      public static nv.b a(nr var0) {
         try {
            md _snowman = mu.a(_snowman.getString());
            nr _snowmanx = nr.a.a(_snowman.l("name"));
            aqe<?> _snowmanxx = gm.S.a(new vk(_snowman.l("type")));
            UUID _snowmanxxx = UUID.fromString(_snowman.l("id"));
            return new nv.b(_snowmanxx, _snowmanxxx, _snowmanx);
         } catch (CommandSyntaxException | JsonSyntaxException var5) {
            return null;
         }
      }

      public JsonElement a() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("type", gm.S.b(this.a).toString());
         _snowman.addProperty("id", this.b.toString());
         if (this.c != null) {
            _snowman.add("name", nr.a.b(this.c));
         }

         return _snowman;
      }

      public List<nr> b() {
         if (this.d == null) {
            this.d = Lists.newArrayList();
            if (this.c != null) {
               this.d.add(this.c);
            }

            this.d.add(new of("gui.entity_tooltip.type", this.a.g()));
            this.d.add(new oe(this.b.toString()));
         }

         return this.d;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            nv.b _snowman = (nv.b)_snowman;
            return this.a.equals(_snowman.a) && this.b.equals(_snowman.b) && Objects.equals(this.c, _snowman.c);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a.hashCode();
         _snowman = 31 * _snowman + this.b.hashCode();
         return 31 * _snowman + (this.c != null ? this.c.hashCode() : 0);
      }
   }

   public static class c {
      private final blx a;
      private final int b;
      @Nullable
      private final md c;
      @Nullable
      private bmb d;

      c(blx var1, int var2, @Nullable md var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public c(bmb var1) {
         this(_snowman.b(), _snowman.E(), _snowman.o() != null ? _snowman.o().g() : null);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            nv.c _snowman = (nv.c)_snowman;
            return this.b == _snowman.b && this.a.equals(_snowman.a) && Objects.equals(this.c, _snowman.c);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a.hashCode();
         _snowman = 31 * _snowman + this.b;
         return 31 * _snowman + (this.c != null ? this.c.hashCode() : 0);
      }

      public bmb a() {
         if (this.d == null) {
            this.d = new bmb(this.a, this.b);
            if (this.c != null) {
               this.d.c(this.c);
            }
         }

         return this.d;
      }

      private static nv.c b(JsonElement var0) {
         if (_snowman.isJsonPrimitive()) {
            return new nv.c(gm.T.a(new vk(_snowman.getAsString())), 1, null);
         } else {
            JsonObject _snowman = afd.m(_snowman, "item");
            blx _snowmanx = gm.T.a(new vk(afd.h(_snowman, "id")));
            int _snowmanxx = afd.a(_snowman, "count", 1);
            if (_snowman.has("tag")) {
               String _snowmanxxx = afd.h(_snowman, "tag");

               try {
                  md _snowmanxxxx = mu.a(_snowmanxxx);
                  return new nv.c(_snowmanx, _snowmanxx, _snowmanxxxx);
               } catch (CommandSyntaxException var6) {
                  nv.a.warn("Failed to parse tag: {}", _snowmanxxx, var6);
               }
            }

            return new nv.c(_snowmanx, _snowmanxx, null);
         }
      }

      @Nullable
      private static nv.c b(nr var0) {
         try {
            md _snowman = mu.a(_snowman.getString());
            return new nv.c(bmb.a(_snowman));
         } catch (CommandSyntaxException var2) {
            nv.a.warn("Failed to parse item tag: {}", _snowman, var2);
            return null;
         }
      }

      private JsonElement b() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", gm.T.b(this.a).toString());
         if (this.b != 1) {
            _snowman.addProperty("count", this.b);
         }

         if (this.c != null) {
            _snowman.addProperty("tag", this.c.toString());
         }

         return _snowman;
      }
   }
}
