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
            Map<Identifier, Tag.Builder> map = Maps.newHashMap();

            for (Identifier lv : manager.findResources(this.dataType, stringx -> stringx.endsWith(".json"))) {
               String string = lv.getPath();
               Identifier lv2 = new Identifier(lv.getNamespace(), string.substring(this.dataType.length() + 1, string.length() - JSON_EXTENSION_LENGTH));

               try {
                  for (Resource lv3 : manager.getAllResources(lv)) {
                     try (
                        InputStream inputStream = lv3.getInputStream();
                        Reader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                     ) {
                        JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);
                        if (jsonObject == null) {
                           LOGGER.error(
                              "Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", this.entryType, lv2, lv, lv3.getResourcePackName()
                           );
                        } else {
                           map.computeIfAbsent(lv2, arg -> Tag.Builder.create()).read(jsonObject, lv3.getResourcePackName());
                        }
                     } catch (RuntimeException | IOException var57) {
                        LOGGER.error("Couldn't read {} tag list {} from {} in data pack {}", this.entryType, lv2, lv, lv3.getResourcePackName(), var57);
                     } finally {
                        IOUtils.closeQuietly(lv3);
                     }
                  }
               } catch (IOException var59) {
                  LOGGER.error("Couldn't read {} tag list {} from {}", this.entryType, lv2, lv, var59);
               }
            }

            return map;
         },
         prepareExecutor
      );
   }

   public TagGroup<T> applyReload(Map<Identifier, Tag.Builder> tags) {
      Map<Identifier, Tag<T>> map2 = Maps.newHashMap();
      Function<Identifier, Tag<T>> function = map2::get;
      Function<Identifier, T> function2 = arg -> this.registryGetter.apply(arg).orElse(null);

      while (!tags.isEmpty()) {
         boolean bl = false;
         Iterator<Entry<Identifier, Tag.Builder>> iterator = tags.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<Identifier, Tag.Builder> entry = iterator.next();
            Optional<Tag<T>> optional = entry.getValue().build(function, function2);
            if (optional.isPresent()) {
               map2.put(entry.getKey(), optional.get());
               iterator.remove();
               bl = true;
            }
         }

         if (!bl) {
            break;
         }
      }

      tags.forEach(
         (arg, arg2) -> LOGGER.error(
               "Couldn't load {} tag {} as it is missing following references: {}",
               this.entryType,
               arg,
               arg2.streamUnresolvedEntries(function, function2).map(Objects::toString).collect(Collectors.joining(","))
            )
      );
      return TagGroup.create(map2);
   }
}
