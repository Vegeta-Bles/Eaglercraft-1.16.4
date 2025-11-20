package net.minecraft.client.resource;

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
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
   protected static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, File> index = Maps.newHashMap();
   private final Map<Identifier, File> field_21556 = Maps.newHashMap();

   protected ResourceIndex() {
   }

   public ResourceIndex(File directory, String indexName) {
      File _snowman = new File(directory, "objects");
      File _snowmanx = new File(directory, "indexes/" + indexName + ".json");
      BufferedReader _snowmanxx = null;

      try {
         _snowmanxx = Files.newReader(_snowmanx, StandardCharsets.UTF_8);
         JsonObject _snowmanxxx = JsonHelper.deserialize(_snowmanxx);
         JsonObject _snowmanxxxx = JsonHelper.getObject(_snowmanxxx, "objects", null);
         if (_snowmanxxxx != null) {
            for (Entry<String, JsonElement> _snowmanxxxxx : _snowmanxxxx.entrySet()) {
               JsonObject _snowmanxxxxxx = (JsonObject)_snowmanxxxxx.getValue();
               String _snowmanxxxxxxx = _snowmanxxxxx.getKey();
               String[] _snowmanxxxxxxxx = _snowmanxxxxxxx.split("/", 2);
               String _snowmanxxxxxxxxx = JsonHelper.getString(_snowmanxxxxxx, "hash");
               File _snowmanxxxxxxxxxx = new File(_snowman, _snowmanxxxxxxxxx.substring(0, 2) + "/" + _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxx.length == 1) {
                  this.index.put(_snowmanxxxxxxxx[0], _snowmanxxxxxxxxxx);
               } else {
                  this.field_21556.put(new Identifier(_snowmanxxxxxxxx[0], _snowmanxxxxxxxx[1]), _snowmanxxxxxxxxxx);
               }
            }
         }
      } catch (JsonParseException var19) {
         LOGGER.error("Unable to parse resource index file: {}", _snowmanx);
      } catch (FileNotFoundException var20) {
         LOGGER.error("Can't find the resource index file: {}", _snowmanx);
      } finally {
         IOUtils.closeQuietly(_snowmanxx);
      }
   }

   @Nullable
   public File getResource(Identifier identifier) {
      return this.field_21556.get(identifier);
   }

   @Nullable
   public File findFile(String path) {
      return this.index.get(path);
   }

   public Collection<Identifier> getFilesRecursively(String _snowman, String _snowman, int _snowman, Predicate<String> _snowman) {
      return this.field_21556.keySet().stream().filter(_snowmanxxxxxx -> {
         String _snowmanxxxx = _snowmanxxxxxx.getPath();
         return _snowmanxxxxxx.getNamespace().equals(_snowman) && !_snowmanxxxx.endsWith(".mcmeta") && _snowmanxxxx.startsWith(_snowman + "/") && _snowman.test(_snowmanxxxx);
      }).collect(Collectors.toList());
   }
}
