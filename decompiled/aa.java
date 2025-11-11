import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class aa implements Comparable<aa> {
   private final Map<String, ae> a = Maps.newHashMap();
   private String[][] b = new String[0][];

   public aa() {
   }

   public void a(Map<String, ad> var1, String[][] var2) {
      Set<String> _snowman = _snowman.keySet();
      this.a.entrySet().removeIf(var1x -> !_snowman.contains(var1x.getKey()));

      for (String _snowmanx : _snowman) {
         if (!this.a.containsKey(_snowmanx)) {
            this.a.put(_snowmanx, new ae());
         }
      }

      this.b = _snowman;
   }

   public boolean a() {
      if (this.b.length == 0) {
         return false;
      } else {
         for (String[] _snowman : this.b) {
            boolean _snowmanx = false;

            for (String _snowmanxx : _snowman) {
               ae _snowmanxxx = this.c(_snowmanxx);
               if (_snowmanxxx != null && _snowmanxxx.a()) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean b() {
      for (ae _snowman : this.a.values()) {
         if (_snowman.a()) {
            return true;
         }
      }

      return false;
   }

   public boolean a(String var1) {
      ae _snowman = this.a.get(_snowman);
      if (_snowman != null && !_snowman.a()) {
         _snowman.b();
         return true;
      } else {
         return false;
      }
   }

   public boolean b(String var1) {
      ae _snowman = this.a.get(_snowman);
      if (_snowman != null && _snowman.a()) {
         _snowman.c();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "AdvancementProgress{criteria=" + this.a + ", requirements=" + Arrays.deepToString(this.b) + '}';
   }

   public void a(nf var1) {
      _snowman.d(this.a.size());

      for (Entry<String, ae> _snowman : this.a.entrySet()) {
         _snowman.a(_snowman.getKey());
         _snowman.getValue().a(_snowman);
      }
   }

   public static aa b(nf var0) {
      aa _snowman = new aa();
      int _snowmanx = _snowman.i();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         _snowman.a.put(_snowman.e(32767), ae.b(_snowman));
      }

      return _snowman;
   }

   @Nullable
   public ae c(String var1) {
      return this.a.get(_snowman);
   }

   public float c() {
      if (this.a.isEmpty()) {
         return 0.0F;
      } else {
         float _snowman = (float)this.b.length;
         float _snowmanx = (float)this.h();
         return _snowmanx / _snowman;
      }
   }

   @Nullable
   public String d() {
      if (this.a.isEmpty()) {
         return null;
      } else {
         int _snowman = this.b.length;
         if (_snowman <= 1) {
            return null;
         } else {
            int _snowmanx = this.h();
            return _snowmanx + "/" + _snowman;
         }
      }
   }

   private int h() {
      int _snowman = 0;

      for (String[] _snowmanx : this.b) {
         boolean _snowmanxx = false;

         for (String _snowmanxxx : _snowmanx) {
            ae _snowmanxxxx = this.c(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxxxx.a()) {
               _snowmanxx = true;
               break;
            }
         }

         if (_snowmanxx) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public Iterable<String> e() {
      List<String> _snowman = Lists.newArrayList();

      for (Entry<String, ae> _snowmanx : this.a.entrySet()) {
         if (!_snowmanx.getValue().a()) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   public Iterable<String> f() {
      List<String> _snowman = Lists.newArrayList();

      for (Entry<String, ae> _snowmanx : this.a.entrySet()) {
         if (_snowmanx.getValue().a()) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   @Nullable
   public Date g() {
      Date _snowman = null;

      for (ae _snowmanx : this.a.values()) {
         if (_snowmanx.a() && (_snowman == null || _snowmanx.d().before(_snowman))) {
            _snowman = _snowmanx.d();
         }
      }

      return _snowman;
   }

   public int a(aa var1) {
      Date _snowman = this.g();
      Date _snowmanx = _snowman.g();
      if (_snowman == null && _snowmanx != null) {
         return 1;
      } else if (_snowman != null && _snowmanx == null) {
         return -1;
      } else {
         return _snowman == null && _snowmanx == null ? 0 : _snowman.compareTo(_snowmanx);
      }
   }

   public static class a implements JsonDeserializer<aa>, JsonSerializer<aa> {
      public a() {
      }

      public JsonElement a(aa var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         JsonObject _snowmanx = new JsonObject();

         for (Entry<String, ae> _snowmanxx : _snowman.a.entrySet()) {
            ae _snowmanxxx = _snowmanxx.getValue();
            if (_snowmanxxx.a()) {
               _snowmanx.add(_snowmanxx.getKey(), _snowmanxxx.e());
            }
         }

         if (!_snowmanx.entrySet().isEmpty()) {
            _snowman.add("criteria", _snowmanx);
         }

         _snowman.addProperty("done", _snowman.a());
         return _snowman;
      }

      public aa a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "advancement");
         JsonObject _snowmanx = afd.a(_snowman, "criteria", new JsonObject());
         aa _snowmanxx = new aa();

         for (Entry<String, JsonElement> _snowmanxxx : _snowmanx.entrySet()) {
            String _snowmanxxxx = _snowmanxxx.getKey();
            _snowmanxx.a.put(_snowmanxxxx, ae.a(afd.a(_snowmanxxx.getValue(), _snowmanxxxx)));
         }

         return _snowmanxx;
      }
   }
}
