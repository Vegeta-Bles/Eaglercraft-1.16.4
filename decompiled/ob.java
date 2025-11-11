import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;

public class ob {
   public static final ob a = new ob(null, null, null, null, null, null, null, null, null, null);
   public static final vk b = new vk("minecraft", "default");
   @Nullable
   private final od c;
   @Nullable
   private final Boolean d;
   @Nullable
   private final Boolean e;
   @Nullable
   private final Boolean f;
   @Nullable
   private final Boolean g;
   @Nullable
   private final Boolean h;
   @Nullable
   private final np i;
   @Nullable
   private final nv j;
   @Nullable
   private final String k;
   @Nullable
   private final vk l;

   private ob(
      @Nullable od var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable Boolean var6,
      @Nullable np var7,
      @Nullable nv var8,
      @Nullable String var9,
      @Nullable vk var10
   ) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
   }

   @Nullable
   public od a() {
      return this.c;
   }

   public boolean b() {
      return this.d == Boolean.TRUE;
   }

   public boolean c() {
      return this.e == Boolean.TRUE;
   }

   public boolean d() {
      return this.g == Boolean.TRUE;
   }

   public boolean e() {
      return this.f == Boolean.TRUE;
   }

   public boolean f() {
      return this.h == Boolean.TRUE;
   }

   public boolean g() {
      return this == a;
   }

   @Nullable
   public np h() {
      return this.i;
   }

   @Nullable
   public nv i() {
      return this.j;
   }

   @Nullable
   public String j() {
      return this.k;
   }

   public vk k() {
      return this.l != null ? this.l : b;
   }

   public ob a(@Nullable od var1) {
      return new ob(_snowman, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
   }

   public ob a(@Nullable k var1) {
      return this.a(_snowman != null ? od.a(_snowman) : null);
   }

   public ob a(@Nullable Boolean var1) {
      return new ob(this.c, _snowman, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
   }

   public ob b(@Nullable Boolean var1) {
      return new ob(this.c, this.d, _snowman, this.f, this.g, this.h, this.i, this.j, this.k, this.l);
   }

   public ob c(@Nullable Boolean var1) {
      return new ob(this.c, this.d, this.e, _snowman, this.g, this.h, this.i, this.j, this.k, this.l);
   }

   public ob a(@Nullable np var1) {
      return new ob(this.c, this.d, this.e, this.f, this.g, this.h, _snowman, this.j, this.k, this.l);
   }

   public ob a(@Nullable nv var1) {
      return new ob(this.c, this.d, this.e, this.f, this.g, this.h, this.i, _snowman, this.k, this.l);
   }

   public ob a(@Nullable String var1) {
      return new ob(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, _snowman, this.l);
   }

   public ob a(@Nullable vk var1) {
      return new ob(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, _snowman);
   }

   public ob b(k var1) {
      od _snowman = this.c;
      Boolean _snowmanx = this.d;
      Boolean _snowmanxx = this.e;
      Boolean _snowmanxxx = this.g;
      Boolean _snowmanxxxx = this.f;
      Boolean _snowmanxxxxx = this.h;
      switch (_snowman) {
         case q:
            _snowmanxxxxx = true;
            break;
         case r:
            _snowmanx = true;
            break;
         case s:
            _snowmanxxx = true;
            break;
         case t:
            _snowmanxxxx = true;
            break;
         case u:
            _snowmanxx = true;
            break;
         case v:
            return a;
         default:
            _snowman = od.a(_snowman);
      }

      return new ob(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.i, this.j, this.k, this.l);
   }

   public ob c(k var1) {
      od _snowman = this.c;
      Boolean _snowmanx = this.d;
      Boolean _snowmanxx = this.e;
      Boolean _snowmanxxx = this.g;
      Boolean _snowmanxxxx = this.f;
      Boolean _snowmanxxxxx = this.h;
      switch (_snowman) {
         case q:
            _snowmanxxxxx = true;
            break;
         case r:
            _snowmanx = true;
            break;
         case s:
            _snowmanxxx = true;
            break;
         case t:
            _snowmanxxxx = true;
            break;
         case u:
            _snowmanxx = true;
            break;
         case v:
            return a;
         default:
            _snowmanxxxxx = false;
            _snowmanx = false;
            _snowmanxxx = false;
            _snowmanxxxx = false;
            _snowmanxx = false;
            _snowman = od.a(_snowman);
      }

      return new ob(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.i, this.j, this.k, this.l);
   }

   public ob a(k... var1) {
      od _snowman = this.c;
      Boolean _snowmanx = this.d;
      Boolean _snowmanxx = this.e;
      Boolean _snowmanxxx = this.g;
      Boolean _snowmanxxxx = this.f;
      Boolean _snowmanxxxxx = this.h;

      for (k _snowmanxxxxxx : _snowman) {
         switch (_snowmanxxxxxx) {
            case q:
               _snowmanxxxxx = true;
               break;
            case r:
               _snowmanx = true;
               break;
            case s:
               _snowmanxxx = true;
               break;
            case t:
               _snowmanxxxx = true;
               break;
            case u:
               _snowmanxx = true;
               break;
            case v:
               return a;
            default:
               _snowman = od.a(_snowmanxxxxxx);
         }
      }

      return new ob(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.i, this.j, this.k, this.l);
   }

   public ob a(ob var1) {
      if (this == a) {
         return _snowman;
      } else {
         return _snowman == a
            ? this
            : new ob(
               this.c != null ? this.c : _snowman.c,
               this.d != null ? this.d : _snowman.d,
               this.e != null ? this.e : _snowman.e,
               this.f != null ? this.f : _snowman.f,
               this.g != null ? this.g : _snowman.g,
               this.h != null ? this.h : _snowman.h,
               this.i != null ? this.i : _snowman.i,
               this.j != null ? this.j : _snowman.j,
               this.k != null ? this.k : _snowman.k,
               this.l != null ? this.l : _snowman.l
            );
      }
   }

   @Override
   public String toString() {
      return "Style{ color="
         + this.c
         + ", bold="
         + this.d
         + ", italic="
         + this.e
         + ", underlined="
         + this.f
         + ", strikethrough="
         + this.g
         + ", obfuscated="
         + this.h
         + ", clickEvent="
         + this.h()
         + ", hoverEvent="
         + this.i()
         + ", insertion="
         + this.j()
         + ", font="
         + this.k()
         + '}';
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof ob)) {
         return false;
      } else {
         ob _snowman = (ob)_snowman;
         return this.b() == _snowman.b()
            && Objects.equals(this.a(), _snowman.a())
            && this.c() == _snowman.c()
            && this.f() == _snowman.f()
            && this.d() == _snowman.d()
            && this.e() == _snowman.e()
            && Objects.equals(this.h(), _snowman.h())
            && Objects.equals(this.i(), _snowman.i())
            && Objects.equals(this.j(), _snowman.j())
            && Objects.equals(this.k(), _snowman.k());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
   }

   public static class a implements JsonDeserializer<ob>, JsonSerializer<ob> {
      public a() {
      }

      @Nullable
      public ob a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (_snowman.isJsonObject()) {
            JsonObject _snowman = _snowman.getAsJsonObject();
            if (_snowman == null) {
               return null;
            } else {
               Boolean _snowmanx = a(_snowman, "bold");
               Boolean _snowmanxx = a(_snowman, "italic");
               Boolean _snowmanxxx = a(_snowman, "underlined");
               Boolean _snowmanxxxx = a(_snowman, "strikethrough");
               Boolean _snowmanxxxxx = a(_snowman, "obfuscated");
               od _snowmanxxxxxx = e(_snowman);
               String _snowmanxxxxxxx = d(_snowman);
               np _snowmanxxxxxxxx = c(_snowman);
               nv _snowmanxxxxxxxxx = b(_snowman);
               vk _snowmanxxxxxxxxxx = a(_snowman);
               return new ob(_snowmanxxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static vk a(JsonObject var0) {
         if (_snowman.has("font")) {
            String _snowman = afd.h(_snowman, "font");

            try {
               return new vk(_snowman);
            } catch (v var3) {
               throw new JsonSyntaxException("Invalid font name: " + _snowman);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static nv b(JsonObject var0) {
         if (_snowman.has("hoverEvent")) {
            JsonObject _snowman = afd.t(_snowman, "hoverEvent");
            nv _snowmanx = nv.a(_snowman);
            if (_snowmanx != null && _snowmanx.a().a()) {
               return _snowmanx;
            }
         }

         return null;
      }

      @Nullable
      private static np c(JsonObject var0) {
         if (_snowman.has("clickEvent")) {
            JsonObject _snowman = afd.t(_snowman, "clickEvent");
            String _snowmanx = afd.a(_snowman, "action", null);
            np.a _snowmanxx = _snowmanx == null ? null : np.a.a(_snowmanx);
            String _snowmanxxx = afd.a(_snowman, "value", null);
            if (_snowmanxx != null && _snowmanxxx != null && _snowmanxx.a()) {
               return new np(_snowmanxx, _snowmanxxx);
            }
         }

         return null;
      }

      @Nullable
      private static String d(JsonObject var0) {
         return afd.a(_snowman, "insertion", null);
      }

      @Nullable
      private static od e(JsonObject var0) {
         if (_snowman.has("color")) {
            String _snowman = afd.h(_snowman, "color");
            return od.a(_snowman);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean a(JsonObject var0, String var1) {
         return _snowman.has(_snowman) ? _snowman.get(_snowman).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement a(ob var1, Type var2, JsonSerializationContext var3) {
         if (_snowman.g()) {
            return null;
         } else {
            JsonObject _snowman = new JsonObject();
            if (_snowman.d != null) {
               _snowman.addProperty("bold", _snowman.d);
            }

            if (_snowman.e != null) {
               _snowman.addProperty("italic", _snowman.e);
            }

            if (_snowman.f != null) {
               _snowman.addProperty("underlined", _snowman.f);
            }

            if (_snowman.g != null) {
               _snowman.addProperty("strikethrough", _snowman.g);
            }

            if (_snowman.h != null) {
               _snowman.addProperty("obfuscated", _snowman.h);
            }

            if (_snowman.c != null) {
               _snowman.addProperty("color", _snowman.c.b());
            }

            if (_snowman.k != null) {
               _snowman.add("insertion", _snowman.serialize(_snowman.k));
            }

            if (_snowman.i != null) {
               JsonObject _snowmanx = new JsonObject();
               _snowmanx.addProperty("action", _snowman.i.a().b());
               _snowmanx.addProperty("value", _snowman.i.b());
               _snowman.add("clickEvent", _snowmanx);
            }

            if (_snowman.j != null) {
               _snowman.add("hoverEvent", _snowman.j.b());
            }

            if (_snowman.l != null) {
               _snowman.addProperty("font", _snowman.l.toString());
            }

            return _snowman;
         }
      }
   }
}
