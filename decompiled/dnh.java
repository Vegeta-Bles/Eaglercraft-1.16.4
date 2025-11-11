import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.function.Function;

public enum dnh {
   a("bitmap", dnf.a::a),
   b("ttf", dnj::a),
   c("legacy_unicode", dni.a::a);

   private static final Map<String, dnh> d = x.a(Maps.newHashMap(), var0 -> {
      for (dnh _snowman : values()) {
         var0.put(_snowman.e, _snowman);
      }
   });
   private final String e;
   private final Function<JsonObject, dng> f;

   private dnh(String var3, Function<JsonObject, dng> var4) {
      this.e = _snowman;
      this.f = _snowman;
   }

   public static dnh a(String var0) {
      dnh _snowman = d.get(_snowman);
      if (_snowman == null) {
         throw new IllegalArgumentException("Invalid type: " + _snowman);
      } else {
         return _snowman;
      }
   }

   public dng a(JsonObject var1) {
      return this.f.apply(_snowman);
   }
}
