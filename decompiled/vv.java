import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vv extends acj {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = new GsonBuilder().create();
   private z c = new z();
   private final cza d;

   public vv(cza var1) {
      super(b, "advancements");
      this.d = _snowman;
   }

   protected void a(Map<vk, JsonElement> var1, ach var2, anw var3) {
      Map<vk, y.a> _snowman = Maps.newHashMap();
      _snowman.forEach((var2x, var3x) -> {
         try {
            JsonObject _snowmanx = afd.m(var3x, "advancement");
            y.a _snowmanx = y.a.a(_snowmanx, new ax(var2x, this.d));
            _snowman.put(var2x, _snowmanx);
         } catch (IllegalArgumentException | JsonParseException var6) {
            a.error("Parsing error loading custom advancement {}: {}", var2x, var6.getMessage());
         }
      });
      z _snowmanx = new z();
      _snowmanx.a(_snowman);

      for (y _snowmanxx : _snowmanx.b()) {
         if (_snowmanxx.c() != null) {
            ak.a(_snowmanxx);
         }
      }

      this.c = _snowmanx;
   }

   @Nullable
   public y a(vk var1) {
      return this.c.a(_snowman);
   }

   public Collection<y> a() {
      return this.c.c();
   }
}
