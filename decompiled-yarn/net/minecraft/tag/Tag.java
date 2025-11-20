package net.minecraft.tag;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public interface Tag<T> {
   static <T> Codec<Tag<T>> codec(Supplier<TagGroup<T>> groupGetter) {
      return Identifier.CODEC
         .flatXmap(
            id -> Optional.ofNullable(groupGetter.get().getTag(id))
                  .<DataResult>map(DataResult::success)
                  .orElseGet(() -> DataResult.error("Unknown tag: " + id)),
            _snowmanx -> Optional.ofNullable(groupGetter.get().getUncheckedTagId(_snowmanx))
                  .<DataResult>map(DataResult::success)
                  .orElseGet(() -> DataResult.error("Unknown tag: " + _snowmanx))
         );
   }

   boolean contains(T entry);

   List<T> values();

   default T getRandom(Random random) {
      List<T> _snowman = this.values();
      return _snowman.get(random.nextInt(_snowman.size()));
   }

   static <T> Tag<T> of(Set<T> values) {
      return SetTag.of(values);
   }

   public static class Builder {
      private final List<Tag.TrackedEntry> entries = Lists.newArrayList();

      public Builder() {
      }

      public static Tag.Builder create() {
         return new Tag.Builder();
      }

      public Tag.Builder add(Tag.TrackedEntry trackedEntry) {
         this.entries.add(trackedEntry);
         return this;
      }

      public Tag.Builder add(Tag.Entry entry, String source) {
         return this.add(new Tag.TrackedEntry(entry, source));
      }

      public Tag.Builder add(Identifier id, String source) {
         return this.add(new Tag.ObjectEntry(id), source);
      }

      public Tag.Builder addTag(Identifier id, String source) {
         return this.add(new Tag.TagEntry(id), source);
      }

      public <T> Optional<Tag<T>> build(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter) {
         com.google.common.collect.ImmutableSet.Builder<T> _snowman = ImmutableSet.builder();

         for (Tag.TrackedEntry _snowmanx : this.entries) {
            if (!_snowmanx.getEntry().resolve(tagGetter, objectGetter, _snowman::add)) {
               return Optional.empty();
            }
         }

         return Optional.of(Tag.of(_snowman.build()));
      }

      public Stream<Tag.TrackedEntry> streamEntries() {
         return this.entries.stream();
      }

      public <T> Stream<Tag.TrackedEntry> streamUnresolvedEntries(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter) {
         return this.streamEntries().filter(trackedEntry -> !trackedEntry.getEntry().resolve(tagGetter, objectGetter, _snowmanxx -> {
            }));
      }

      public Tag.Builder read(JsonObject json, String source) {
         JsonArray _snowman = JsonHelper.getArray(json, "values");
         List<Tag.Entry> _snowmanx = Lists.newArrayList();

         for (JsonElement _snowmanxx : _snowman) {
            _snowmanx.add(resolveEntry(_snowmanxx));
         }

         if (JsonHelper.getBoolean(json, "replace", false)) {
            this.entries.clear();
         }

         _snowmanx.forEach(entry -> this.entries.add(new Tag.TrackedEntry(entry, source)));
         return this;
      }

      private static Tag.Entry resolveEntry(JsonElement json) {
         String _snowman;
         boolean _snowmanx;
         if (json.isJsonObject()) {
            JsonObject _snowmanxx = json.getAsJsonObject();
            _snowman = JsonHelper.getString(_snowmanxx, "id");
            _snowmanx = JsonHelper.getBoolean(_snowmanxx, "required", true);
         } else {
            _snowman = JsonHelper.asString(json, "id");
            _snowmanx = true;
         }

         if (_snowman.startsWith("#")) {
            Identifier _snowmanxx = new Identifier(_snowman.substring(1));
            return (Tag.Entry)(_snowmanx ? new Tag.TagEntry(_snowmanxx) : new Tag.OptionalTagEntry(_snowmanxx));
         } else {
            Identifier _snowmanxx = new Identifier(_snowman);
            return (Tag.Entry)(_snowmanx ? new Tag.ObjectEntry(_snowmanxx) : new Tag.OptionalObjectEntry(_snowmanxx));
         }
      }

      public JsonObject toJson() {
         JsonObject _snowman = new JsonObject();
         JsonArray _snowmanx = new JsonArray();

         for (Tag.TrackedEntry _snowmanxx : this.entries) {
            _snowmanxx.getEntry().addToJson(_snowmanx);
         }

         _snowman.addProperty("replace", false);
         _snowman.add("values", _snowmanx);
         return _snowman;
      }
   }

   public interface Entry {
      <T> boolean resolve(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter, Consumer<T> collector);

      void addToJson(JsonArray json);
   }

   public interface Identified<T> extends Tag<T> {
      Identifier getId();
   }

   public static class ObjectEntry implements Tag.Entry {
      private final Identifier id;

      public ObjectEntry(Identifier id) {
         this.id = id;
      }

      @Override
      public <T> boolean resolve(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter, Consumer<T> collector) {
         T _snowman = objectGetter.apply(this.id);
         if (_snowman == null) {
            return false;
         } else {
            collector.accept(_snowman);
            return true;
         }
      }

      @Override
      public void addToJson(JsonArray json) {
         json.add(this.id.toString());
      }

      @Override
      public String toString() {
         return this.id.toString();
      }
   }

   public static class OptionalObjectEntry implements Tag.Entry {
      private final Identifier id;

      public OptionalObjectEntry(Identifier id) {
         this.id = id;
      }

      @Override
      public <T> boolean resolve(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter, Consumer<T> collector) {
         T _snowman = objectGetter.apply(this.id);
         if (_snowman != null) {
            collector.accept(_snowman);
         }

         return true;
      }

      @Override
      public void addToJson(JsonArray json) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", this.id.toString());
         _snowman.addProperty("required", false);
         json.add(_snowman);
      }

      @Override
      public String toString() {
         return this.id.toString() + "?";
      }
   }

   public static class OptionalTagEntry implements Tag.Entry {
      private final Identifier id;

      public OptionalTagEntry(Identifier id) {
         this.id = id;
      }

      @Override
      public <T> boolean resolve(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter, Consumer<T> collector) {
         Tag<T> _snowman = tagGetter.apply(this.id);
         if (_snowman != null) {
            _snowman.values().forEach(collector);
         }

         return true;
      }

      @Override
      public void addToJson(JsonArray json) {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", "#" + this.id);
         _snowman.addProperty("required", false);
         json.add(_snowman);
      }

      @Override
      public String toString() {
         return "#" + this.id + "?";
      }
   }

   public static class TagEntry implements Tag.Entry {
      private final Identifier id;

      public TagEntry(Identifier id) {
         this.id = id;
      }

      @Override
      public <T> boolean resolve(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter, Consumer<T> collector) {
         Tag<T> _snowman = tagGetter.apply(this.id);
         if (_snowman == null) {
            return false;
         } else {
            _snowman.values().forEach(collector);
            return true;
         }
      }

      @Override
      public void addToJson(JsonArray json) {
         json.add("#" + this.id);
      }

      @Override
      public String toString() {
         return "#" + this.id;
      }
   }

   public static class TrackedEntry {
      private final Tag.Entry entry;
      private final String source;

      private TrackedEntry(Tag.Entry entry, String source) {
         this.entry = entry;
         this.source = source;
      }

      public Tag.Entry getEntry() {
         return this.entry;
      }

      @Override
      public String toString() {
         return this.entry.toString() + " (from " + this.source + ")";
      }
   }
}
