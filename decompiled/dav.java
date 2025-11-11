import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class dav extends dai {
   private final Map<aps, czd> a;

   private dav(dbo[] var1, Map<aps, czd> var2) {
      super(_snowman);
      this.a = ImmutableMap.copyOf(_snowman);
   }

   @Override
   public dak b() {
      return dal.l;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.b() == bmd.qR && !this.a.isEmpty()) {
         Random _snowman = _snowman.a();
         int _snowmanx = _snowman.nextInt(this.a.size());
         Entry<aps, czd> _snowmanxx = (Entry<aps, czd>)Iterables.get(this.a.entrySet(), _snowmanx);
         aps _snowmanxxx = _snowmanxx.getKey();
         int _snowmanxxxx = _snowmanxx.getValue().a(_snowman);
         if (!_snowmanxxx.a()) {
            _snowmanxxxx *= 20;
         }

         bne.a(_snowman, _snowmanxxx, _snowmanxxxx);
         return _snowman;
      } else {
         return _snowman;
      }
   }

   public static dav.a c() {
      return new dav.a();
   }

   public static class a extends dai.a<dav.a> {
      private final Map<aps, czd> a = Maps.newHashMap();

      public a() {
      }

      protected dav.a a() {
         return this;
      }

      public dav.a a(aps var1, czd var2) {
         this.a.put(_snowman, _snowman);
         return this;
      }

      @Override
      public daj b() {
         return new dav(this.g(), this.a);
      }
   }

   public static class b extends dai.c<dav> {
      public b() {
      }

      public void a(JsonObject var1, dav var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         if (!_snowman.a.isEmpty()) {
            JsonArray _snowman = new JsonArray();

            for (aps _snowmanx : _snowman.a.keySet()) {
               JsonObject _snowmanxx = new JsonObject();
               vk _snowmanxxx = gm.P.b(_snowmanx);
               if (_snowmanxxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize mob effect " + _snowmanx);
               }

               _snowmanxx.add("type", new JsonPrimitive(_snowmanxxx.toString()));
               _snowmanxx.add("duration", _snowman.serialize(_snowman.a.get(_snowmanx)));
               _snowman.add(_snowmanxx);
            }

            _snowman.add("effects", _snowman);
         }
      }

      public dav a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         Map<aps, czd> _snowman = Maps.newHashMap();
         if (_snowman.has("effects")) {
            for (JsonElement _snowmanx : afd.u(_snowman, "effects")) {
               String _snowmanxx = afd.h(_snowmanx.getAsJsonObject(), "type");
               aps _snowmanxxx = gm.P.b(new vk(_snowmanxx)).orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + _snowman + "'"));
               czd _snowmanxxxx = afd.a(_snowmanx.getAsJsonObject(), "duration", _snowman, czd.class);
               _snowman.put(_snowmanxxx, _snowmanxxxx);
            }
         }

         return new dav(_snowman, _snowman);
      }
   }
}
