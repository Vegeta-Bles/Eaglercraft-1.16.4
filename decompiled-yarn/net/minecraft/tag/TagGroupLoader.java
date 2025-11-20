package net.minecraft.tag;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagGroupLoader<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new Gson();
   private static final int JSON_EXTENSION_LENGTH = ".json".length();
   private final Function<Identifier, Optional<T>> registryGetter;
   private final String dataType;
   private final String entryType;

   public TagGroupLoader(Function<Identifier, Optional<T>> registryGetter, String dataType, String entryType) {
      this.registryGetter = registryGetter;
      this.dataType = dataType;
      this.entryType = entryType;
   }

   public CompletableFuture<Map<Identifier, Tag.Builder>> prepareReload(ResourceManager manager, Executor prepareExecutor) {
      return CompletableFuture.supplyAsync(
         () -> {
            Map<Identifier, Tag.Builder> _snowmanx = Maps.newHashMap();

            for (Identifier _snowmanx : manager.findResources(this.dataType, _snowmanxx -> _snowmanxx.endsWith(".json"))) {
               String _snowmanxx = _snowmanx.getPath();
               Identifier _snowmanxxx = new Identifier(_snowmanx.getNamespace(), _snowmanxx.substring(this.dataType.length() + 1, _snowmanxx.length() - JSON_EXTENSION_LENGTH));

               try {
                  for (Resource _snowmanxxxx : manager.getAllResources(_snowmanx)) {
                     try (
                        InputStream _snowmanxxxxx = _snowmanxxxx.getInputStream();
                        Reader _snowmanxxxxxx = new BufferedReader(new InputStreamReader(_snowmanxxxxx, StandardCharsets.UTF_8));
                     ) {
                        JsonObject _snowmanxxxxxxx = JsonHelper.deserialize(GSON, _snowmanxxxxxx, JsonObject.class);
                        if (_snowmanxxxxxxx == null) {
                           LOGGER.error(
                              "Couldn't load {} tag list {} from {} in data pack {} as it is empty or null",
                              this.entryType,
                              _snowmanxxx,
                              _snowmanx,
                              _snowmanxxxx.getResourcePackName()
                           );
                        } else {
                           _snowmanx.computeIfAbsent(_snowmanxxx, _snowmanxxxxxxxx -> Tag.Builder.create()).read(_snowmanxxxxxxx, _snowmanxxxx.getResourcePackName());
                        }
                     } catch (RuntimeException | IOException var57) {
                        LOGGER.error("Couldn't read {} tag list {} from {} in data pack {}", this.entryType, _snowmanxxx, _snowmanx, _snowmanxxxx.getResourcePackName(), var57);
                     } finally {
                        IOUtils.closeQuietly(_snowmanxxxx);
                     }
                  }
               } catch (IOException var59) {
                  LOGGER.error("Couldn't read {} tag list {} from {}", this.entryType, _snowmanxxx, _snowmanx, var59);
               }
            }

            return _snowmanx;
         },
         prepareExecutor
      );
   }

   public TagGroup<T> applyReload(Map<Identifier, Tag.Builder> tags) {
      Map<Identifier, Tag<T>> _snowman = Maps.newHashMap();
      Function<Identifier, Tag<T>> _snowmanx = _snowman::get;
      Function<Identifier, T> _snowmanxx = _snowmanxxx -> this.registryGetter.apply(_snowmanxxx).orElse(null);

      while (!tags.isEmpty()) {
         boolean _snowmanxxx = false;
         Iterator<Entry<Identifier, Tag.Builder>> _snowmanxxxx = tags.entrySet().iterator();

         while (_snowmanxxxx.hasNext()) {
            Entry<Identifier, Tag.Builder> _snowmanxxxxx = _snowmanxxxx.next();
            Optional<Tag<T>> _snowmanxxxxxx = _snowmanxxxxx.getValue().build(_snowmanx, _snowmanxx);
            if (_snowmanxxxxxx.isPresent()) {
               _snowman.put(_snowmanxxxxx.getKey(), _snowmanxxxxxx.get());
               _snowmanxxxx.remove();
               _snowmanxxx = true;
            }
         }

         if (!_snowmanxxx) {
            break;
         }
      }

      tags.forEach(
         (_snowmanxxx, _snowmanxxxx) -> LOGGER.error(
               "Couldn't load {} tag {} as it is missing following references: {}",
               this.entryType,
               _snowmanxxx,
               _snowmanxxxx.streamUnresolvedEntries(_snowman, _snowman).map(Objects::toString).collect(Collectors.joining(","))
            )
      );
      return TagGroup.create(_snowman);
   }
}
