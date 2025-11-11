import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyy {
   private static final Logger c = LogManager.getLogger();
   public static final cyy a = new cyy(dbb.a, new cyx[0], new daj[0]);
   public static final dba b = dbb.k;
   private final dba d;
   private final cyx[] e;
   private final daj[] f;
   private final BiFunction<bmb, cyv, bmb> g;

   private cyy(dba var1, cyx[] var2, daj[] var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = dal.a(_snowman);
   }

   public static Consumer<bmb> a(Consumer<bmb> var0) {
      return var1 -> {
         if (var1.E() < var1.c()) {
            _snowman.accept(var1);
         } else {
            int _snowman = var1.E();

            while (_snowman > 0) {
               bmb _snowmanx = var1.i();
               _snowmanx.e(Math.min(var1.c(), _snowman));
               _snowman -= _snowmanx.E();
               _snowman.accept(_snowmanx);
            }
         }
      };
   }

   public void a(cyv var1, Consumer<bmb> var2) {
      if (_snowman.a(this)) {
         Consumer<bmb> _snowman = daj.a(this.g, _snowman, _snowman);

         for (cyx _snowmanx : this.e) {
            _snowmanx.a(_snowman, _snowman);
         }

         _snowman.b(this);
      } else {
         c.warn("Detected infinite loop in loot tables");
      }
   }

   public void b(cyv var1, Consumer<bmb> var2) {
      this.a(_snowman, a(_snowman));
   }

   public List<bmb> a(cyv var1) {
      List<bmb> _snowman = Lists.newArrayList();
      this.b(_snowman, _snowman::add);
      return _snowman;
   }

   public dba a() {
      return this.d;
   }

   public void a(czg var1) {
      for (int _snowman = 0; _snowman < this.e.length; _snowman++) {
         this.e[_snowman].a(_snowman.b(".pools[" + _snowman + "]"));
      }

      for (int _snowman = 0; _snowman < this.f.length; _snowman++) {
         this.f[_snowman].a(_snowman.b(".functions[" + _snowman + "]"));
      }
   }

   public void a(aon var1, cyv var2) {
      List<bmb> _snowman = this.a(_snowman);
      Random _snowmanx = _snowman.a();
      List<Integer> _snowmanxx = this.a(_snowman, _snowmanx);
      this.a(_snowman, _snowmanxx.size(), _snowmanx);

      for (bmb _snowmanxxx : _snowman) {
         if (_snowmanxx.isEmpty()) {
            c.warn("Tried to over-fill a container");
            return;
         }

         if (_snowmanxxx.a()) {
            _snowman.a(_snowmanxx.remove(_snowmanxx.size() - 1), bmb.b);
         } else {
            _snowman.a(_snowmanxx.remove(_snowmanxx.size() - 1), _snowmanxxx);
         }
      }
   }

   private void a(List<bmb> var1, int var2, Random var3) {
      List<bmb> _snowman = Lists.newArrayList();
      Iterator<bmb> _snowmanx = _snowman.iterator();

      while (_snowmanx.hasNext()) {
         bmb _snowmanxx = _snowmanx.next();
         if (_snowmanxx.a()) {
            _snowmanx.remove();
         } else if (_snowmanxx.E() > 1) {
            _snowman.add(_snowmanxx);
            _snowmanx.remove();
         }
      }

      while (_snowman - _snowman.size() - _snowman.size() > 0 && !_snowman.isEmpty()) {
         bmb _snowmanxx = _snowman.remove(afm.a(_snowman, 0, _snowman.size() - 1));
         int _snowmanxxx = afm.a(_snowman, 1, _snowmanxx.E() / 2);
         bmb _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
         if (_snowmanxx.E() > 1 && _snowman.nextBoolean()) {
            _snowman.add(_snowmanxx);
         } else {
            _snowman.add(_snowmanxx);
         }

         if (_snowmanxxxx.E() > 1 && _snowman.nextBoolean()) {
            _snowman.add(_snowmanxxxx);
         } else {
            _snowman.add(_snowmanxxxx);
         }
      }

      _snowman.addAll(_snowman);
      Collections.shuffle(_snowman, _snowman);
   }

   private List<Integer> a(aon var1, Random var2) {
      List<Integer> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman.Z_(); _snowmanx++) {
         if (_snowman.a(_snowmanx).a()) {
            _snowman.add(_snowmanx);
         }
      }

      Collections.shuffle(_snowman, _snowman);
      return _snowman;
   }

   public static cyy.a b() {
      return new cyy.a();
   }

   public static class a implements dag<cyy.a> {
      private final List<cyx> a = Lists.newArrayList();
      private final List<daj> b = Lists.newArrayList();
      private dba c = cyy.b;

      public a() {
      }

      public cyy.a a(cyx.a var1) {
         this.a.add(_snowman.b());
         return this;
      }

      public cyy.a a(dba var1) {
         this.c = _snowman;
         return this;
      }

      public cyy.a a(daj.a var1) {
         this.b.add(_snowman.b());
         return this;
      }

      public cyy.a a() {
         return this;
      }

      public cyy b() {
         return new cyy(this.c, this.a.toArray(new cyx[0]), this.b.toArray(new daj[0]));
      }
   }

   public static class b implements JsonDeserializer<cyy>, JsonSerializer<cyy> {
      public b() {
      }

      public cyy a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "loot table");
         cyx[] _snowmanx = afd.a(_snowman, "pools", new cyx[0], _snowman, cyx[].class);
         dba _snowmanxx = null;
         if (_snowman.has("type")) {
            String _snowmanxxx = afd.h(_snowman, "type");
            _snowmanxx = dbb.a(new vk(_snowmanxxx));
         }

         daj[] _snowmanxxx = afd.a(_snowman, "functions", new daj[0], _snowman, daj[].class);
         return new cyy(_snowmanxx != null ? _snowmanxx : dbb.k, _snowmanx, _snowmanxxx);
      }

      public JsonElement a(cyy var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         if (_snowman.d != cyy.b) {
            vk _snowmanx = dbb.a(_snowman.d);
            if (_snowmanx != null) {
               _snowman.addProperty("type", _snowmanx.toString());
            } else {
               cyy.c.warn("Failed to find id for param set " + _snowman.d);
            }
         }

         if (_snowman.e.length > 0) {
            _snowman.add("pools", _snowman.serialize(_snowman.e));
         }

         if (!ArrayUtils.isEmpty(_snowman.f)) {
            _snowman.add("functions", _snowman.serialize(_snowman.f));
         }

         return _snowman;
      }
   }
}
