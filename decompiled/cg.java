import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class cg {
   public static final cg a = new cg.d().b();
   private final bz.d b;
   private final bru c;
   private final Map<adx<?>, bz.d> d;
   private final Object2BooleanMap<vk> e;
   private final Map<vk, cg.c> f;

   private static cg.c b(JsonElement var0) {
      if (_snowman.isJsonPrimitive()) {
         boolean _snowman = _snowman.getAsBoolean();
         return new cg.b(_snowman);
      } else {
         Object2BooleanMap<String> _snowman = new Object2BooleanOpenHashMap();
         JsonObject _snowmanx = afd.m(_snowman, "criterion data");
         _snowmanx.entrySet().forEach(var1x -> {
            boolean _snowmanxx = afd.c((JsonElement)var1x.getValue(), "criterion test");
            _snowman.put(var1x.getKey(), _snowmanxx);
         });
         return new cg.a(_snowman);
      }
   }

   private cg(bz.d var1, bru var2, Map<adx<?>, bz.d> var3, Object2BooleanMap<vk> var4, Map<vk, cg.c> var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public boolean a(aqa var1) {
      if (this == a) {
         return true;
      } else if (!(_snowman instanceof aah)) {
         return false;
      } else {
         aah _snowman = (aah)_snowman;
         if (!this.b.d(_snowman.bD)) {
            return false;
         } else if (this.c != bru.a && this.c != _snowman.d.b()) {
            return false;
         } else {
            aeb _snowmanx = _snowman.A();

            for (Entry<adx<?>, bz.d> _snowmanxx : this.d.entrySet()) {
               int _snowmanxxx = _snowmanx.a(_snowmanxx.getKey());
               if (!_snowmanxx.getValue().d(_snowmanxxx)) {
                  return false;
               }
            }

            adt _snowmanxxx = _snowman.B();
            ObjectIterator var11 = this.e.object2BooleanEntrySet().iterator();

            while (var11.hasNext()) {
               it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<vk> _snowmanxxxx = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<vk>)var11.next();
               if (_snowmanxxx.b((vk)_snowmanxxxx.getKey()) != _snowmanxxxx.getBooleanValue()) {
                  return false;
               }
            }

            if (!this.f.isEmpty()) {
               vt _snowmanxxxx = _snowman.J();
               vv _snowmanxxxxx = _snowman.ch().aA();

               for (Entry<vk, cg.c> _snowmanxxxxxx : this.f.entrySet()) {
                  y _snowmanxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx.getKey());
                  if (_snowmanxxxxxxx == null || !_snowmanxxxxxx.getValue().test(_snowmanxxxx.b(_snowmanxxxxxxx))) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public static cg a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "player");
         bz.d _snowmanx = bz.d.a(_snowman.get("level"));
         String _snowmanxx = afd.a(_snowman, "gamemode", "");
         bru _snowmanxxx = bru.a(_snowmanxx, bru.a);
         Map<adx<?>, bz.d> _snowmanxxxx = Maps.newHashMap();
         JsonArray _snowmanxxxxx = afd.a(_snowman, "stats", null);
         if (_snowmanxxxxx != null) {
            for (JsonElement _snowmanxxxxxx : _snowmanxxxxx) {
               JsonObject _snowmanxxxxxxx = afd.m(_snowmanxxxxxx, "stats entry");
               vk _snowmanxxxxxxxx = new vk(afd.h(_snowmanxxxxxxx, "type"));
               adz<?> _snowmanxxxxxxxxx = gm.ag.a(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxx == null) {
                  throw new JsonParseException("Invalid stat type: " + _snowmanxxxxxxxx);
               }

               vk _snowmanxxxxxxxxxx = new vk(afd.h(_snowmanxxxxxxx, "stat"));
               adx<?> _snowmanxxxxxxxxxxx = a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
               bz.d _snowmanxxxxxxxxxxxx = bz.d.a(_snowmanxxxxxxx.get("value"));
               _snowmanxxxx.put(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            }
         }

         Object2BooleanMap<vk> _snowmanxxxxxx = new Object2BooleanOpenHashMap();
         JsonObject _snowmanxxxxxxx = afd.a(_snowman, "recipes", new JsonObject());

         for (Entry<String, JsonElement> _snowmanxxxxxxxx : _snowmanxxxxxxx.entrySet()) {
            vk _snowmanxxxxxxxxx = new vk(_snowmanxxxxxxxx.getKey());
            boolean _snowmanxxxxxxxxxx = afd.c(_snowmanxxxxxxxx.getValue(), "recipe present");
            _snowmanxxxxxx.put(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         }

         Map<vk, cg.c> _snowmanxxxxxxxx = Maps.newHashMap();
         JsonObject _snowmanxxxxxxxxx = afd.a(_snowman, "advancements", new JsonObject());

         for (Entry<String, JsonElement> _snowmanxxxxxxxxxx : _snowmanxxxxxxxxx.entrySet()) {
            vk _snowmanxxxxxxxxxxx = new vk(_snowmanxxxxxxxxxx.getKey());
            cg.c _snowmanxxxxxxxxxxxx = b(_snowmanxxxxxxxxxx.getValue());
            _snowmanxxxxxxxx.put(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
         }

         return new cg(_snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx);
      } else {
         return a;
      }
   }

   private static <T> adx<T> a(adz<T> var0, vk var1) {
      gm<T> _snowman = _snowman.a();
      T _snowmanx = _snowman.a(_snowman);
      if (_snowmanx == null) {
         throw new JsonParseException("Unknown object " + _snowman + " for stat type " + gm.ag.b(_snowman));
      } else {
         return _snowman.b(_snowmanx);
      }
   }

   private static <T> vk a(adx<T> var0) {
      return _snowman.a().a().b(_snowman.b());
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("level", this.b.d());
         if (this.c != bru.a) {
            _snowman.addProperty("gamemode", this.c.b());
         }

         if (!this.d.isEmpty()) {
            JsonArray _snowmanx = new JsonArray();
            this.d.forEach((var1x, var2x) -> {
               JsonObject _snowmanxx = new JsonObject();
               _snowmanxx.addProperty("type", gm.ag.b(var1x.a()).toString());
               _snowmanxx.addProperty("stat", a((adx<?>)var1x).toString());
               _snowmanxx.add("value", var2x.d());
               _snowman.add(_snowmanxx);
            });
            _snowman.add("stats", _snowmanx);
         }

         if (!this.e.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            this.e.forEach((var1x, var2x) -> _snowman.addProperty(var1x.toString(), var2x));
            _snowman.add("recipes", _snowmanx);
         }

         if (!this.f.isEmpty()) {
            JsonObject _snowmanx = new JsonObject();
            this.f.forEach((var1x, var2x) -> _snowman.add(var1x.toString(), var2x.a()));
            _snowman.add("advancements", _snowmanx);
         }

         return _snowman;
      }
   }

   static class a implements cg.c {
      private final Object2BooleanMap<String> a;

      public a(Object2BooleanMap<String> var1) {
         this.a = _snowman;
      }

      @Override
      public JsonElement a() {
         JsonObject _snowman = new JsonObject();
         this.a.forEach(_snowman::addProperty);
         return _snowman;
      }

      public boolean a(aa var1) {
         ObjectIterator var2 = this.a.object2BooleanEntrySet().iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String> _snowman = (it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry<String>)var2.next();
            ae _snowmanx = _snowman.c((String)_snowman.getKey());
            if (_snowmanx == null || _snowmanx.a() != _snowman.getBooleanValue()) {
               return false;
            }
         }

         return true;
      }
   }

   static class b implements cg.c {
      private final boolean a;

      public b(boolean var1) {
         this.a = _snowman;
      }

      @Override
      public JsonElement a() {
         return new JsonPrimitive(this.a);
      }

      public boolean a(aa var1) {
         return _snowman.a() == this.a;
      }
   }

   interface c extends Predicate<aa> {
      JsonElement a();
   }

   public static class d {
      private bz.d a = bz.d.e;
      private bru b = bru.a;
      private final Map<adx<?>, bz.d> c = Maps.newHashMap();
      private final Object2BooleanMap<vk> d = new Object2BooleanOpenHashMap();
      private final Map<vk, cg.c> e = Maps.newHashMap();

      public d() {
      }

      public cg b() {
         return new cg(this.a, this.b, this.c, this.d, this.e);
      }
   }
}
