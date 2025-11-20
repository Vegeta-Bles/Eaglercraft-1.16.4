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

   protected AbstractTagProvider(DataGenerator root, Registry<T> _snowman) {
      this.root = root;
      this.registry = _snowman;
   }

   protected abstract void configure();

   @Override
   public void run(DataCache cache) {
      this.tagBuilders.clear();
      this.configure();
      Tag<T> _snowman = SetTag.empty();
      Function<Identifier, Tag<T>> _snowmanx = _snowmanxx -> this.tagBuilders.containsKey(_snowmanxx) ? _snowman : null;
      Function<Identifier, T> _snowmanxx = _snowmanxxx -> this.registry.getOrEmpty(_snowmanxxx).orElse(null);
      this.tagBuilders
         .forEach(
            (_snowmanxxx, _snowmanxxxx) -> {
               List<Tag.TrackedEntry> _snowmanxxx = _snowmanxxxx.streamUnresolvedEntries(_snowman, _snowman).collect(Collectors.toList());
               if (!_snowmanxxx.isEmpty()) {
                  throw new IllegalArgumentException(
                     String.format(
                        "Couldn't define tag %s as it is missing following references: %s",
                        _snowmanxxx,
                        _snowmanxxx.stream().map(Objects::toString).collect(Collectors.joining(","))
                     )
                  );
               } else {
                  JsonObject _snowmanx = _snowmanxxxx.toJson();
                  Path _snowmanxx = this.getOutput(_snowmanxxx);

                  try {
                     String _snowmanxxxxx = GSON.toJson(_snowmanx);
                     String _snowmanxxxxxx = SHA1.hashUnencodedChars(_snowmanxxxxx).toString();
                     if (!Objects.equals(cache.getOldSha1(_snowmanxx), _snowmanxxxxxx) || !Files.exists(_snowmanxx)) {
                        Files.createDirectories(_snowmanxx.getParent());

                        try (BufferedWriter _snowmanxxxxxxx = Files.newBufferedWriter(_snowmanxx)) {
                           _snowmanxxxxxxx.write(_snowmanxxxxx);
                        }
                     }

                     cache.updateSha1(_snowmanxx, _snowmanxxxxxx);
                  } catch (IOException var24) {
                     LOGGER.error("Couldn't save tags to {}", _snowmanxx, var24);
                  }
               }
            }
         );
   }

   protected abstract Path getOutput(Identifier var1);

   protected AbstractTagProvider.ObjectBuilder<T> getOrCreateTagBuilder(Tag.Identified<T> _snowman) {
      Tag.Builder _snowmanx = this.method_27169(_snowman);
      return new AbstractTagProvider.ObjectBuilder<>(_snowmanx, this.registry, "vanilla");
   }

   protected Tag.Builder method_27169(Tag.Identified<T> _snowman) {
      return this.tagBuilders.computeIfAbsent(_snowman.getId(), _snowmanx -> new Tag.Builder());
   }

   public static class ObjectBuilder<T> {
      private final Tag.Builder field_23960;
      private final Registry<T> field_23961;
      private final String field_23962;

      private ObjectBuilder(Tag.Builder _snowman, Registry<T> _snowman, String _snowman) {
         this.field_23960 = _snowman;
         this.field_23961 = _snowman;
         this.field_23962 = _snowman;
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
      public final AbstractTagProvider.ObjectBuilder<T> add(T... _snowman) {
         Stream.<T>of(_snowman).map(this.field_23961::getId).forEach(_snowmanx -> this.field_23960.add(_snowmanx, this.field_23962));
         return this;
      }
   }
}
