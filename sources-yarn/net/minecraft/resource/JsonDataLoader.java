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

   protected Map<Identifier, JsonElement> prepare(ResourceManager arg, Profiler arg2) {
      Map<Identifier, JsonElement> map = Maps.newHashMap();
      int i = this.dataType.length() + 1;

      for (Identifier lv : arg.findResources(this.dataType, stringx -> stringx.endsWith(".json"))) {
         String string = lv.getPath();
         Identifier lv2 = new Identifier(lv.getNamespace(), string.substring(i, string.length() - FILE_SUFFIX_LENGTH));

         try (
            Resource lv3 = arg.getResource(lv);
            InputStream inputStream = lv3.getInputStream();
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
         ) {
            JsonElement jsonElement = JsonHelper.deserialize(this.gson, reader, JsonElement.class);
            if (jsonElement != null) {
               JsonElement jsonElement2 = map.put(lv2, jsonElement);
               if (jsonElement2 != null) {
                  throw new IllegalStateException("Duplicate data file ignored with ID " + lv2);
               }
            } else {
               LOGGER.error("Couldn't load data file {} from {} as it's null or empty", lv2, lv);
            }
         } catch (IllegalArgumentException | IOException | JsonParseException var68) {
            LOGGER.error("Couldn't parse data file {} from {}", lv2, lv, var68);
         }
      }

      return map;
   }
}
