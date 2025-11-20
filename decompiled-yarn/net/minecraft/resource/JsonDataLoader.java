package net.minecraft.resource;

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
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class JsonDataLoader extends SinglePreparationResourceReloadListener<Map<Identifier, JsonElement>> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final int FILE_SUFFIX_LENGTH = ".json".length();
   private final Gson gson;
   private final String dataType;

   public JsonDataLoader(Gson gson, String dataType) {
      this.gson = gson;
      this.dataType = dataType;
   }

   protected Map<Identifier, JsonElement> prepare(ResourceManager _snowman, Profiler _snowman) {
      Map<Identifier, JsonElement> _snowmanxx = Maps.newHashMap();
      int _snowmanxxx = this.dataType.length() + 1;

      for (Identifier _snowmanxxxx : _snowman.findResources(this.dataType, _snowmanxxxxx -> _snowmanxxxxx.endsWith(".json"))) {
         String _snowmanxxxxx = _snowmanxxxx.getPath();
         Identifier _snowmanxxxxxx = new Identifier(_snowmanxxxx.getNamespace(), _snowmanxxxxx.substring(_snowmanxxx, _snowmanxxxxx.length() - FILE_SUFFIX_LENGTH));

         try (
            Resource _snowmanxxxxxxx = _snowman.getResource(_snowmanxxxx);
            InputStream _snowmanxxxxxxxx = _snowmanxxxxxxx.getInputStream();
            Reader _snowmanxxxxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxxxxx, StandardCharsets.UTF_8));
         ) {
            JsonElement _snowmanxxxxxxxxxx = JsonHelper.deserialize(this.gson, _snowmanxxxxxxxxx, JsonElement.class);
            if (_snowmanxxxxxxxxxx != null) {
               JsonElement _snowmanxxxxxxxxxxx = _snowmanxx.put(_snowmanxxxxxx, _snowmanxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxx != null) {
                  throw new IllegalStateException("Duplicate data file ignored with ID " + _snowmanxxxxxx);
               }
            } else {
               LOGGER.error("Couldn't load data file {} from {} as it's null or empty", _snowmanxxxxxx, _snowmanxxxx);
            }
         } catch (IllegalArgumentException | IOException | JsonParseException var68) {
            LOGGER.error("Couldn't parse data file {} from {}", _snowmanxxxxxx, _snowmanxxxx, var68);
         }
      }

      return _snowmanxx;
   }
}
