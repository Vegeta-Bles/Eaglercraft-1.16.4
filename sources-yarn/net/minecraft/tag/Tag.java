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
            (Tag<T> tag) -> {
               Identifier id = groupGetter.get().getUncheckedTagId(tag);
               return id != null ? DataResult.success(id) : DataResult.error("Unknown tag: " + tag);
            }
         );
   }

   boolean contains(T entry);

   List<T> values();

   default T getRandom(Random random) {
      List<T> list = this.values();
      return list.get(random.nextInt(list.size()));
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
         com.google.common.collect.ImmutableSet.Builder<T> builder = ImmutableSet.builder();

         for (Tag.TrackedEntry lv : this.entries) {
            if (!lv.getEntry().resolve(tagGetter, objectGetter, builder::add)) {
               return Optional.empty();
            }
         }

         return Optional.of(Tag.of(builder.build()));
      }

      public Stream<Tag.TrackedEntry> streamEntries() {
         return this.entries.stream();
      }

      public <T> Stream<Tag.TrackedEntry> streamUnresolvedEntries(Function<Identifier, Tag<T>> tagGetter, Function<Identifier, T> objectGetter) {
         return this.streamEntries().filter(trackedEntry -> !trackedEntry.getEntry().resolve(tagGetter, objectGetter, object -> {
            }));
      }

      public Tag.Builder read(JsonObject json, String source) {
         JsonArray jsonArray = JsonHelper.getArray(json, "values");
         List<Tag.Entry> list = Lists.newArrayList();

         for (JsonElement jsonElement : jsonArray) {
            list.add(resolveEntry(jsonElement));
         }

         if (JsonHelper.getBoolean(json, "replace", false)) {
            this.entries.clear();
         }

         list.forEach(entry -> this.entries.add(new Tag.TrackedEntry(entry, source)));
         return this;
      }

      private static Tag.Entry resolveEntry(JsonElement json) {
         String string;
         boolean bl;
         if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            string = JsonHelper.getString(jsonObject, "id");
            bl = JsonHelper.getBoolean(jsonObject, "required", true);
         } else {
            string = JsonHelper.asString(json, "id");
            bl = true;
         }

         if (string.startsWith("#")) {
            Identifier lv = new Identifier(string.substring(1));
            return (Tag.Entry)(bl ? new Tag.TagEntry(lv) : new Tag.OptionalTagEntry(lv));
         } else {
            Identifier lv2 = new Identifier(string);
            return (Tag.Entry)(bl ? new Tag.ObjectEntry(lv2) : new Tag.OptionalObjectEntry(lv2));
         }
      }

      public JsonObject toJson() {
         JsonObject jsonObject = new JsonObject();
         JsonArray jsonArray = new JsonArray();

         for (Tag.TrackedEntry lv : this.entries) {
            lv.getEntry().addToJson(jsonArray);
         }

         jsonObject.addProperty("replace", false);
         jsonObject.add("values", jsonArray);
         return jsonObject;
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
         T object = objectGetter.apply(this.id);
         if (object == null) {
            return false;
         } else {
            collector.accept(object);
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
         T object = objectGetter.apply(this.id);
         if (object != null) {
            collector.accept(object);
         }

         return true;
      }

      @Override
      public void addToJson(JsonArray json) {
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("id", this.id.toString());
         jsonObject.addProperty("required", false);
         json.add(jsonObject);
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
         Tag<T> lv = tagGetter.apply(this.id);
         if (lv != null) {
            lv.values().forEach(collector);
         }

         return true;
      }

      @Override
      public void addToJson(JsonArray json) {
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("id", "#" + this.id);
         jsonObject.addProperty("required", false);
         json.add(jsonObject);
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
         Tag<T> lv = tagGetter.apply(this.id);
         if (lv == null) {
            return false;
         } else {
            lv.values().forEach(collector);
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
