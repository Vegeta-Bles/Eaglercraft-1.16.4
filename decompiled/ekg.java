import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ekg {
   protected static final Logger a = LogManager.getLogger();
   private final Map<String, File> b = Maps.newHashMap();
   private final Map<vk, File> c = Maps.newHashMap();

   protected ekg() {
   }

   public ekg(File var1, String var2) {
      File _snowman = new File(_snowman, "objects");
      File _snowmanx = new File(_snowman, "indexes/" + _snowman + ".json");
      BufferedReader _snowmanxx = null;

      try {
         _snowmanxx = Files.newReader(_snowmanx, StandardCharsets.UTF_8);
         JsonObject _snowmanxxx = afd.a(_snowmanxx);
         JsonObject _snowmanxxxx = afd.a(_snowmanxxx, "objects", null);
         if (_snowmanxxxx != null) {
            for (Entry<String, JsonElement> _snowmanxxxxx : _snowmanxxxx.entrySet()) {
               JsonObject _snowmanxxxxxx = (JsonObject)_snowmanxxxxx.getValue();
               String _snowmanxxxxxxx = _snowmanxxxxx.getKey();
               String[] _snowmanxxxxxxxx = _snowmanxxxxxxx.split("/", 2);
               String _snowmanxxxxxxxxx = afd.h(_snowmanxxxxxx, "hash");
               File _snowmanxxxxxxxxxx = new File(_snowman, _snowmanxxxxxxxxx.substring(0, 2) + "/" + _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxx.length == 1) {
                  this.b.put(_snowmanxxxxxxxx[0], _snowmanxxxxxxxxxx);
               } else {
                  this.c.put(new vk(_snowmanxxxxxxxx[0], _snowmanxxxxxxxx[1]), _snowmanxxxxxxxxxx);
               }
            }
         }
      } catch (JsonParseException var19) {
         a.error("Unable to parse resource index file: {}", _snowmanx);
      } catch (FileNotFoundException var20) {
         a.error("Can't find the resource index file: {}", _snowmanx);
      } finally {
         IOUtils.closeQuietly(_snowmanxx);
      }
   }

   @Nullable
   public File a(vk var1) {
      return this.c.get(_snowman);
   }

   @Nullable
   public File a(String var1) {
      return this.b.get(_snowman);
   }

   public Collection<vk> a(String var1, String var2, int var3, Predicate<String> var4) {
      return this.c.keySet().stream().filter(var3x -> {
         String _snowman = var3x.a();
         return var3x.b().equals(_snowman) && !_snowman.endsWith(".mcmeta") && _snowman.startsWith(_snowman + "/") && _snowman.test(_snowman);
      }).collect(Collectors.toList());
   }
}
