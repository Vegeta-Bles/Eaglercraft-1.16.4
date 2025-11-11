import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public interface nr extends Message, nu {
   ob c();

   String a();

   @Override
   default String getString() {
      return nu.super.getString();
   }

   default String a(int var1) {
      StringBuilder _snowman = new StringBuilder();
      this.a(var2x -> {
         int _snowmanx = _snowman - _snowman.length();
         if (_snowmanx <= 0) {
            return b;
         } else {
            _snowman.append(var2x.length() <= _snowmanx ? var2x : var2x.substring(0, _snowmanx));
            return Optional.empty();
         }
      });
      return _snowman.toString();
   }

   List<nr> b();

   nx g();

   nx e();

   afa f();

   @Override
   default <T> Optional<T> a(nu.b<T> var1, ob var2) {
      ob _snowman = this.c().a(_snowman);
      Optional<T> _snowmanx = this.b(_snowman, _snowman);
      if (_snowmanx.isPresent()) {
         return _snowmanx;
      } else {
         for (nr _snowmanxx : this.b()) {
            Optional<T> _snowmanxxx = _snowmanxx.a(_snowman, _snowman);
            if (_snowmanxxx.isPresent()) {
               return _snowmanxxx;
            }
         }

         return Optional.empty();
      }
   }

   @Override
   default <T> Optional<T> a(nu.a<T> var1) {
      Optional<T> _snowman = this.b(_snowman);
      if (_snowman.isPresent()) {
         return _snowman;
      } else {
         for (nr _snowmanx : this.b()) {
            Optional<T> _snowmanxx = _snowmanx.a(_snowman);
            if (_snowmanxx.isPresent()) {
               return _snowmanxx;
            }
         }

         return Optional.empty();
      }
   }

   default <T> Optional<T> b(nu.b<T> var1, ob var2) {
      return _snowman.accept(_snowman, this.a());
   }

   default <T> Optional<T> b(nu.a<T> var1) {
      return _snowman.accept(this.a());
   }

   static nr a(@Nullable String var0) {
      return (nr)(_snowman != null ? new oe(_snowman) : oe.d);
   }

   public static class a implements JsonDeserializer<nx>, JsonSerializer<nr> {
      private static final Gson a = x.a((Supplier<Gson>)(() -> {
         GsonBuilder _snowman = new GsonBuilder();
         _snowman.disableHtmlEscaping();
         _snowman.registerTypeHierarchyAdapter(nr.class, new nr.a());
         _snowman.registerTypeHierarchyAdapter(ob.class, new ob.a());
         _snowman.registerTypeAdapterFactory(new afl());
         return _snowman.create();
      }));
      private static final Field b = x.a((Supplier<Field>)(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field _snowman = JsonReader.class.getDeclaredField("pos");
            _snowman.setAccessible(true);
            return _snowman;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      }));
      private static final Field c = x.a((Supplier<Field>)(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field _snowman = JsonReader.class.getDeclaredField("lineStart");
            _snowman.setAccessible(true);
            return _snowman;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      }));

      public a() {
      }

      public nx a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (_snowman.isJsonPrimitive()) {
            return new oe(_snowman.getAsString());
         } else if (!_snowman.isJsonObject()) {
            if (_snowman.isJsonArray()) {
               JsonArray _snowman = _snowman.getAsJsonArray();
               nx _snowmanx = null;

               for (JsonElement _snowmanxx : _snowman) {
                  nx _snowmanxxx = this.a(_snowmanxx, _snowmanxx.getClass(), _snowman);
                  if (_snowmanx == null) {
                     _snowmanx = _snowmanxxx;
                  } else {
                     _snowmanx.a(_snowmanxxx);
                  }
               }

               return _snowmanx;
            } else {
               throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
            }
         } else {
            JsonObject _snowman = _snowman.getAsJsonObject();
            nx _snowmanx;
            if (_snowman.has("text")) {
               _snowmanx = new oe(afd.h(_snowman, "text"));
            } else if (_snowman.has("translate")) {
               String _snowmanxxx = afd.h(_snowman, "translate");
               if (_snowman.has("with")) {
                  JsonArray _snowmanxxxx = afd.u(_snowman, "with");
                  Object[] _snowmanxxxxx = new Object[_snowmanxxxx.size()];

                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.length; _snowmanxxxxxx++) {
                     _snowmanxxxxx[_snowmanxxxxxx] = this.a(_snowmanxxxx.get(_snowmanxxxxxx), _snowman, _snowman);
                     if (_snowmanxxxxx[_snowmanxxxxxx] instanceof oe) {
                        oe _snowmanxxxxxxx = (oe)_snowmanxxxxx[_snowmanxxxxxx];
                        if (_snowmanxxxxxxx.c().g() && _snowmanxxxxxxx.b().isEmpty()) {
                           _snowmanxxxxx[_snowmanxxxxxx] = _snowmanxxxxxxx.h();
                        }
                     }
                  }

                  _snowmanx = new of(_snowmanxxx, _snowmanxxxxx);
               } else {
                  _snowmanx = new of(_snowmanxxx);
               }
            } else if (_snowman.has("score")) {
               JsonObject _snowmanxxx = afd.t(_snowman, "score");
               if (!_snowmanxxx.has("name") || !_snowmanxxx.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               _snowmanx = new nz(afd.h(_snowmanxxx, "name"), afd.h(_snowmanxxx, "objective"));
            } else if (_snowman.has("selector")) {
               _snowmanx = new oa(afd.h(_snowman, "selector"));
            } else if (_snowman.has("keybind")) {
               _snowmanx = new nw(afd.h(_snowman, "keybind"));
            } else {
               if (!_snowman.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
               }

               String _snowmanxxx = afd.h(_snowman, "nbt");
               boolean _snowmanxxxx = afd.a(_snowman, "interpret", false);
               if (_snowman.has("block")) {
                  _snowmanx = new ny.a(_snowmanxxx, _snowmanxxxx, afd.h(_snowman, "block"));
               } else if (_snowman.has("entity")) {
                  _snowmanx = new ny.b(_snowmanxxx, _snowmanxxxx, afd.h(_snowman, "entity"));
               } else {
                  if (!_snowman.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
                  }

                  _snowmanx = new ny.c(_snowmanxxx, _snowmanxxxx, new vk(afd.h(_snowman, "storage")));
               }
            }

            if (_snowman.has("extra")) {
               JsonArray _snowmanxxx = afd.u(_snowman, "extra");
               if (_snowmanxxx.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
                  _snowmanx.a(this.a(_snowmanxxx.get(_snowmanxxxx), _snowman, _snowman));
               }
            }

            _snowmanx.a((ob)_snowman.deserialize(_snowman, ob.class));
            return _snowmanx;
         }
      }

      private void a(ob var1, JsonObject var2, JsonSerializationContext var3) {
         JsonElement _snowman = _snowman.serialize(_snowman);
         if (_snowman.isJsonObject()) {
            JsonObject _snowmanx = (JsonObject)_snowman;

            for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
               _snowman.add(_snowmanxx.getKey(), _snowmanxx.getValue());
            }
         }
      }

      public JsonElement a(nr var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         if (!_snowman.c().g()) {
            this.a(_snowman.c(), _snowman, _snowman);
         }

         if (!_snowman.b().isEmpty()) {
            JsonArray _snowmanx = new JsonArray();

            for (nr _snowmanxx : _snowman.b()) {
               _snowmanx.add(this.a(_snowmanxx, _snowmanxx.getClass(), _snowman));
            }

            _snowman.add("extra", _snowmanx);
         }

         if (_snowman instanceof oe) {
            _snowman.addProperty("text", ((oe)_snowman).h());
         } else if (_snowman instanceof of) {
            of _snowmanx = (of)_snowman;
            _snowman.addProperty("translate", _snowmanx.i());
            if (_snowmanx.j() != null && _snowmanx.j().length > 0) {
               JsonArray _snowmanxx = new JsonArray();

               for (Object _snowmanxxx : _snowmanx.j()) {
                  if (_snowmanxxx instanceof nr) {
                     _snowmanxx.add(this.a((nr)_snowmanxxx, _snowmanxxx.getClass(), _snowman));
                  } else {
                     _snowmanxx.add(new JsonPrimitive(String.valueOf(_snowmanxxx)));
                  }
               }

               _snowman.add("with", _snowmanxx);
            }
         } else if (_snowman instanceof nz) {
            nz _snowmanx = (nz)_snowman;
            JsonObject _snowmanxx = new JsonObject();
            _snowmanxx.addProperty("name", _snowmanx.h());
            _snowmanxx.addProperty("objective", _snowmanx.j());
            _snowman.add("score", _snowmanxx);
         } else if (_snowman instanceof oa) {
            oa _snowmanx = (oa)_snowman;
            _snowman.addProperty("selector", _snowmanx.h());
         } else if (_snowman instanceof nw) {
            nw _snowmanx = (nw)_snowman;
            _snowman.addProperty("keybind", _snowmanx.i());
         } else {
            if (!(_snowman instanceof ny)) {
               throw new IllegalArgumentException("Don't know how to serialize " + _snowman + " as a Component");
            }

            ny _snowmanx = (ny)_snowman;
            _snowman.addProperty("nbt", _snowmanx.h());
            _snowman.addProperty("interpret", _snowmanx.i());
            if (_snowman instanceof ny.a) {
               ny.a _snowmanxx = (ny.a)_snowman;
               _snowman.addProperty("block", _snowmanxx.j());
            } else if (_snowman instanceof ny.b) {
               ny.b _snowmanxx = (ny.b)_snowman;
               _snowman.addProperty("entity", _snowmanxx.j());
            } else {
               if (!(_snowman instanceof ny.c)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + _snowman + " as a Component");
               }

               ny.c _snowmanxx = (ny.c)_snowman;
               _snowman.addProperty("storage", _snowmanxx.j().toString());
            }
         }

         return _snowman;
      }

      public static String a(nr var0) {
         return a.toJson(_snowman);
      }

      public static JsonElement b(nr var0) {
         return a.toJsonTree(_snowman);
      }

      @Nullable
      public static nx a(String var0) {
         return afd.a(a, _snowman, nx.class, false);
      }

      @Nullable
      public static nx a(JsonElement var0) {
         return (nx)a.fromJson(_snowman, nx.class);
      }

      @Nullable
      public static nx b(String var0) {
         return afd.a(a, _snowman, nx.class, true);
      }

      public static nx a(com.mojang.brigadier.StringReader var0) {
         try {
            JsonReader _snowman = new JsonReader(new StringReader(_snowman.getRemaining()));
            _snowman.setLenient(false);
            nx _snowmanx = (nx)a.getAdapter(nx.class).read(_snowman);
            _snowman.setCursor(_snowman.getCursor() + a(_snowman));
            return _snowmanx;
         } catch (StackOverflowError | IOException var3) {
            throw new JsonParseException(var3);
         }
      }

      private static int a(JsonReader var0) {
         try {
            return b.getInt(_snowman) - c.getInt(_snowman) + 1;
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException("Couldn't read position of JsonReader", var2);
         }
      }
   }
}
