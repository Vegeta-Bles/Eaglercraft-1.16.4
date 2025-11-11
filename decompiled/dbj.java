import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class dbj implements dbo {
   private final Map<String, czd> a;
   private final cyv.c b;

   private dbj(Map<String, czd> var1, cyv.c var2) {
      this.a = ImmutableMap.copyOf(_snowman);
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.g;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(this.b.a());
   }

   public boolean a(cyv var1) {
      aqa _snowman = _snowman.c(this.b.a());
      if (_snowman == null) {
         return false;
      } else {
         ddn _snowmanx = _snowman.l.G();

         for (Entry<String, czd> _snowmanxx : this.a.entrySet()) {
            if (!this.a(_snowman, _snowmanx, _snowmanxx.getKey(), _snowmanxx.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean a(aqa var1, ddn var2, String var3, czd var4) {
      ddk _snowman = _snowman.d(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         String _snowmanx = _snowman.bU();
         return !_snowman.b(_snowmanx, _snowman) ? false : _snowman.a(_snowman.c(_snowmanx, _snowman).b());
      }
   }

   public static class b implements cze<dbj> {
      public b() {
      }

      public void a(JsonObject var1, dbj var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();

         for (Entry<String, czd> _snowmanx : _snowman.a.entrySet()) {
            _snowman.add(_snowmanx.getKey(), _snowman.serialize(_snowmanx.getValue()));
         }

         _snowman.add("scores", _snowman);
         _snowman.add("entity", _snowman.serialize(_snowman.b));
      }

      public dbj b(JsonObject var1, JsonDeserializationContext var2) {
         Set<Entry<String, JsonElement>> _snowman = afd.t(_snowman, "scores").entrySet();
         Map<String, czd> _snowmanx = Maps.newLinkedHashMap();

         for (Entry<String, JsonElement> _snowmanxx : _snowman) {
            _snowmanx.put(_snowmanxx.getKey(), afd.a(_snowmanxx.getValue(), "score", _snowman, czd.class));
         }

         return new dbj(_snowmanx, afd.a(_snowman, "entity", _snowman, cyv.c.class));
      }
   }
}
