import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyz extends acj {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = cys.c().create();
   private Map<vk, cyy> c = ImmutableMap.of();
   private final cza d;

   public cyz(cza var1) {
      super(b, "loot_tables");
      this.d = _snowman;
   }

   public cyy a(vk var1) {
      return this.c.getOrDefault(_snowman, cyy.a);
   }

   protected void a(Map<vk, JsonElement> var1, ach var2, anw var3) {
      Builder<vk, cyy> _snowman = ImmutableMap.builder();
      JsonElement _snowmanx = _snowman.remove(cyq.a);
      if (_snowmanx != null) {
         a.warn("Datapack tried to redefine {} loot table, ignoring", cyq.a);
      }

      _snowman.forEach((var1x, var2x) -> {
         try {
            cyy _snowmanxx = (cyy)b.fromJson(var2x, cyy.class);
            _snowman.put(var1x, _snowmanxx);
         } catch (Exception var4x) {
            a.error("Couldn't parse loot table {}", var1x, var4x);
         }
      });
      _snowman.put(cyq.a, cyy.a);
      ImmutableMap<vk, cyy> _snowmanxx = _snowman.build();
      czg _snowmanxxx = new czg(dbb.k, this.d::a, _snowmanxx::get);
      _snowmanxx.forEach((var1x, var2x) -> a(_snowman, var1x, var2x));
      _snowmanxxx.a().forEach((var0, var1x) -> a.warn("Found validation problem in " + var0 + ": " + var1x));
      this.c = _snowmanxx;
   }

   public static void a(czg var0, vk var1, cyy var2) {
      _snowman.a(_snowman.a(_snowman.a()).a("{" + _snowman + "}", _snowman));
   }

   public static JsonElement a(cyy var0) {
      return b.toJsonTree(_snowman);
   }

   public Set<vk> a() {
      return this.c.keySet();
   }
}
