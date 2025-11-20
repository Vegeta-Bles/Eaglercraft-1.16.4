package net.minecraft.data.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tag.SetTag;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractTagProvider<T> implements DataProvider {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   protected final DataGenerator root;
   protected final Registry<T> registry;
   private final Map<Identifier, Tag.Builder> tagBuilders = Maps.newLinkedHashMap();

   protected AbstractTagProvider(DataGenerator root, Registry<T> arg2) {
      this.root = root;
      this.registry = arg2;
   }

   protected abstract void configure();

   @Override
   public void run(DataCache cache) {
      this.tagBuilders.clear();
      this.configure();
      Tag<T> lv = SetTag.empty();
      Function<Identifier, Tag<T>> function = arg2 -> this.tagBuilders.containsKey(arg2) ? lv : null;
      Function<Identifier, T> function2 = arg -> this.registry.getOrEmpty(arg).orElse(null);
      this.tagBuilders
         .forEach(
            (arg2, arg3) -> {
               List<Tag.TrackedEntry> list = arg3.streamUnresolvedEntries(function, function2).collect(Collectors.toList());
               if (!list.isEmpty()) {
                  throw new IllegalArgumentException(
                     String.format(
                        "Couldn't define tag %s as it is missing following references: %s",
                        arg2,
                        list.stream().map(Objects::toString).collect(Collectors.joining(","))
                     )
                  );
               } else {
                  JsonObject jsonObject = arg3.toJson();
                  Path path = this.getOutput(arg2);

                  try {
                     String string = GSON.toJson(jsonObject);
                     String string2 = SHA1.hashUnencodedChars(string).toString();
                     if (!Objects.equals(cache.getOldSha1(path), string2) || !Files.exists(path)) {
                        Files.createDirectories(path.getParent());

                        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                           bufferedWriter.write(string);
                        }
                     }

                     cache.updateSha1(path, string2);
                  } catch (IOException var24) {
                     LOGGER.error("Couldn't save tags to {}", path, var24);
                  }
               }
            }
         );
   }

   protected abstract Path getOutput(Identifier arg);

   protected AbstractTagProvider.ObjectBuilder<T> getOrCreateTagBuilder(Tag.Identified<T> arg) {
      Tag.Builder lv = this.method_27169(arg);
      return new AbstractTagProvider.ObjectBuilder<>(lv, this.registry, "vanilla");
   }

   protected Tag.Builder method_27169(Tag.Identified<T> arg) {
      return this.tagBuilders.computeIfAbsent(arg.getId(), argx -> new Tag.Builder());
   }

   public static class ObjectBuilder<T> {
      private final Tag.Builder field_23960;
      private final Registry<T> field_23961;
      private final String field_23962;

      private ObjectBuilder(Tag.Builder arg, Registry<T> arg2, String string) {
         this.field_23960 = arg;
         this.field_23961 = arg2;
         this.field_23962 = string;
      }

      public AbstractTagProvider.ObjectBuilder<T> add(T element) {
         this.field_23960.add(this.field_23961.getId(element), this.field_23962);
         return this;
      }

      public AbstractTagProvider.ObjectBuilder<T> addTag(Tag.Identified<T> identifiedTag) {
         this.field_23960.addTag(identifiedTag.getId(), this.field_23962);
         return this;
      }

      @SafeVarargs
      public final AbstractTagProvider.ObjectBuilder<T> add(T... objects) {
         Stream.<T>of(objects).map(this.field_23961::getId).forEach(arg -> this.field_23960.add(arg, this.field_23962));
         return this;
      }
   }
}
