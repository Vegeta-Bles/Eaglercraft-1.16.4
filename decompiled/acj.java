import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class acj extends ack<Map<vk, JsonElement>> {
   private static final Logger a = LogManager.getLogger();
   private static final int b = ".json".length();
   private final Gson c;
   private final String d;

   public acj(Gson var1, String var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   protected Map<vk, JsonElement> a(ach var1, anw var2) {
      Map<vk, JsonElement> _snowman = Maps.newHashMap();
      int _snowmanx = this.d.length() + 1;

      for (vk _snowmanxx : _snowman.a(this.d, var0 -> var0.endsWith(".json"))) {
         String _snowmanxxx = _snowmanxx.a();
         vk _snowmanxxxx = new vk(_snowmanxx.b(), _snowmanxxx.substring(_snowmanx, _snowmanxxx.length() - b));

         try (
            acg _snowmanxxxxx = _snowman.a(_snowmanxx);
            InputStream _snowmanxxxxxx = _snowmanxxxxx.b();
            Reader _snowmanxxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxxx, StandardCharsets.UTF_8));
         ) {
            JsonElement _snowmanxxxxxxxx = afd.a(this.c, _snowmanxxxxxxx, JsonElement.class);
            if (_snowmanxxxxxxxx != null) {
               JsonElement _snowmanxxxxxxxxx = _snowman.put(_snowmanxxxx, _snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxx != null) {
                  throw new IllegalStateException("Duplicate data file ignored with ID " + _snowmanxxxx);
               }
            } else {
               a.error("Couldn't load data file {} from {} as it's null or empty", _snowmanxxxx, _snowmanxx);
            }
         } catch (IllegalArgumentException | IOException | JsonParseException var68) {
            a.error("Couldn't parse data file {} from {}", _snowmanxxxx, _snowmanxx, var68);
         }
      }

      return _snowman;
   }
}
