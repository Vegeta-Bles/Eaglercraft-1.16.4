package net.minecraft.text;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HoverEvent {
   private static final Logger LOGGER = LogManager.getLogger();
   private final HoverEvent.Action<?> action;
   private final Object contents;

   public <T> HoverEvent(HoverEvent.Action<T> action, T contents) {
      this.action = action;
      this.contents = contents;
   }

   public HoverEvent.Action<?> getAction() {
      return this.action;
   }

   @Nullable
   public <T> T getValue(HoverEvent.Action<T> action) {
      return this.action == action ? action.cast(this.contents) : null;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         HoverEvent _snowman = (HoverEvent)obj;
         return this.action == _snowman.action && Objects.equals(this.contents, _snowman.contents);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "HoverEvent{action=" + this.action + ", value='" + this.contents + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int _snowman = this.action.hashCode();
      return 31 * _snowman + (this.contents != null ? this.contents.hashCode() : 0);
   }

   @Nullable
   public static HoverEvent fromJson(JsonObject json) {
      String _snowman = JsonHelper.getString(json, "action", null);
      if (_snowman == null) {
         return null;
      } else {
         HoverEvent.Action<?> _snowmanx = HoverEvent.Action.byName(_snowman);
         if (_snowmanx == null) {
            return null;
         } else {
            JsonElement _snowmanxx = json.get("contents");
            if (_snowmanxx != null) {
               return _snowmanx.buildHoverEvent(_snowmanxx);
            } else {
               Text _snowmanxxx = Text.Serializer.fromJson(json.get("value"));
               return _snowmanxxx != null ? _snowmanx.buildHoverEvent(_snowmanxxx) : null;
            }
         }
      }
   }

   public JsonObject toJson() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("action", this.action.getName());
      _snowman.add("contents", this.action.contentsToJson(this.contents));
      return _snowman;
   }

   public static class Action<T> {
      public static final HoverEvent.Action<Text> SHOW_TEXT = new HoverEvent.Action<>(
         "show_text", true, Text.Serializer::fromJson, Text.Serializer::toJsonTree, Function.identity()
      );
      public static final HoverEvent.Action<HoverEvent.ItemStackContent> SHOW_ITEM = new HoverEvent.Action<>(
         "show_item", true, _snowman -> HoverEvent.ItemStackContent.parse(_snowman), _snowman -> _snowman.toJson(), _snowman -> HoverEvent.ItemStackContent.parse(_snowman)
      );
      public static final HoverEvent.Action<HoverEvent.EntityContent> SHOW_ENTITY = new HoverEvent.Action<>(
         "show_entity", true, HoverEvent.EntityContent::parse, HoverEvent.EntityContent::toJson, HoverEvent.EntityContent::parse
      );
      private static final Map<String, HoverEvent.Action> BY_NAME = Stream.of(SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY)
         .collect(ImmutableMap.toImmutableMap(HoverEvent.Action::getName, _snowman -> _snowman));
      private final String name;
      private final boolean parsable;
      private final Function<JsonElement, T> deserializer;
      private final Function<T, JsonElement> serializer;
      private final Function<Text, T> legacyDeserializer;

      public Action(
         String name, boolean parsable, Function<JsonElement, T> deserializer, Function<T, JsonElement> serializer, Function<Text, T> legacyDeserializer
      ) {
         this.name = name;
         this.parsable = parsable;
         this.deserializer = deserializer;
         this.serializer = serializer;
         this.legacyDeserializer = legacyDeserializer;
      }

      public boolean isParsable() {
         return this.parsable;
      }

      public String getName() {
         return this.name;
      }

      @Nullable
      public static HoverEvent.Action byName(String name) {
         return BY_NAME.get(name);
      }

      private T cast(Object o) {
         return (T)o;
      }

      @Nullable
      public HoverEvent buildHoverEvent(JsonElement contents) {
         T _snowman = this.deserializer.apply(contents);
         return _snowman == null ? null : new HoverEvent(this, _snowman);
      }

      @Nullable
      public HoverEvent buildHoverEvent(Text value) {
         T _snowman = this.legacyDeserializer.apply(value);
         return _snowman == null ? null : new HoverEvent(this, _snowman);
      }

      public JsonElement contentsToJson(Object contents) {
         return this.serializer.apply(this.cast(contents));
      }

      @Override
      public String toString() {
         return "<action " + this.name + ">";
      }
   }

   public static class EntityContent {
      public final EntityType<?> entityType;
      public final UUID uuid;
      @Nullable
      public final Text name;
      @Nullable
      private List<Text> tooltip;

      public EntityContent(EntityType<?> entityType, UUID uuid, @Nullable Text name) {
         this.entityType = entityType;
         this.uuid = uuid;
         this.name = name;
      }

      @Nullable
      public static HoverEvent.EntityContent parse(JsonElement json) {
         if (!json.isJsonObject()) {
            return null;
         } else {
            JsonObject _snowman = json.getAsJsonObject();
            EntityType<?> _snowmanx = Registry.ENTITY_TYPE.get(new Identifier(JsonHelper.getString(_snowman, "type")));
            UUID _snowmanxx = UUID.fromString(JsonHelper.getString(_snowman, "id"));
            Text _snowmanxxx = Text.Serializer.fromJson(_snowman.get("name"));
            return new HoverEvent.EntityContent(_snowmanx, _snowmanxx, _snowmanxxx);
         }
      }

      @Nullable
      public static HoverEvent.EntityContent parse(Text text) {
         try {
            CompoundTag _snowman = StringNbtReader.parse(text.getString());
            Text _snowmanx = Text.Serializer.fromJson(_snowman.getString("name"));
            EntityType<?> _snowmanxx = Registry.ENTITY_TYPE.get(new Identifier(_snowman.getString("type")));
            UUID _snowmanxxx = UUID.fromString(_snowman.getString("id"));
            return new HoverEvent.EntityContent(_snowmanxx, _snowmanxxx, _snowmanx);
         } catch (CommandSyntaxException | JsonSyntaxException var5) {
            return null;
         }
      }

      public JsonElement toJson() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("type", Registry.ENTITY_TYPE.getId(this.entityType).toString());
         _snowman.addProperty("id", this.uuid.toString());
         if (this.name != null) {
            _snowman.add("name", Text.Serializer.toJsonTree(this.name));
         }

         return _snowman;
      }

      public List<Text> asTooltip() {
         if (this.tooltip == null) {
            this.tooltip = Lists.newArrayList();
            if (this.name != null) {
               this.tooltip.add(this.name);
            }

            this.tooltip.add(new TranslatableText("gui.entity_tooltip.type", this.entityType.getName()));
            this.tooltip.add(new LiteralText(this.uuid.toString()));
         }

         return this.tooltip;
      }

      @Override
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            HoverEvent.EntityContent _snowmanx = (HoverEvent.EntityContent)_snowman;
            return this.entityType.equals(_snowmanx.entityType) && this.uuid.equals(_snowmanx.uuid) && Objects.equals(this.name, _snowmanx.name);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.entityType.hashCode();
         _snowman = 31 * _snowman + this.uuid.hashCode();
         return 31 * _snowman + (this.name != null ? this.name.hashCode() : 0);
      }
   }

   public static class ItemStackContent {
      private final Item item;
      private final int count;
      @Nullable
      private final CompoundTag tag;
      @Nullable
      private ItemStack stack;

      ItemStackContent(Item item, int count, @Nullable CompoundTag tag) {
         this.item = item;
         this.count = count;
         this.tag = tag;
      }

      public ItemStackContent(ItemStack stack) {
         this(stack.getItem(), stack.getCount(), stack.getTag() != null ? stack.getTag().copy() : null);
      }

      @Override
      public boolean equals(Object _snowman) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            HoverEvent.ItemStackContent _snowmanx = (HoverEvent.ItemStackContent)_snowman;
            return this.count == _snowmanx.count && this.item.equals(_snowmanx.item) && Objects.equals(this.tag, _snowmanx.tag);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.item.hashCode();
         _snowman = 31 * _snowman + this.count;
         return 31 * _snowman + (this.tag != null ? this.tag.hashCode() : 0);
      }

      public ItemStack asStack() {
         if (this.stack == null) {
            this.stack = new ItemStack(this.item, this.count);
            if (this.tag != null) {
               this.stack.setTag(this.tag);
            }
         }

         return this.stack;
      }

      private static HoverEvent.ItemStackContent parse(JsonElement json) {
         if (json.isJsonPrimitive()) {
            return new HoverEvent.ItemStackContent(Registry.ITEM.get(new Identifier(json.getAsString())), 1, null);
         } else {
            JsonObject _snowman = JsonHelper.asObject(json, "item");
            Item _snowmanx = Registry.ITEM.get(new Identifier(JsonHelper.getString(_snowman, "id")));
            int _snowmanxx = JsonHelper.getInt(_snowman, "count", 1);
            if (_snowman.has("tag")) {
               String _snowmanxxx = JsonHelper.getString(_snowman, "tag");

               try {
                  CompoundTag _snowmanxxxx = StringNbtReader.parse(_snowmanxxx);
                  return new HoverEvent.ItemStackContent(_snowmanx, _snowmanxx, _snowmanxxxx);
               } catch (CommandSyntaxException var6) {
                  HoverEvent.LOGGER.warn("Failed to parse tag: {}", _snowmanxxx, var6);
               }
            }

            return new HoverEvent.ItemStackContent(_snowmanx, _snowmanxx, null);
         }
      }

      @Nullable
      private static HoverEvent.ItemStackContent parse(Text text) {
         try {
            CompoundTag _snowman = StringNbtReader.parse(text.getString());
            return new HoverEvent.ItemStackContent(ItemStack.fromTag(_snowman));
         } catch (CommandSyntaxException var2) {
            HoverEvent.LOGGER.warn("Failed to parse item tag: {}", text, var2);
            return null;
         }
      }

      private JsonElement toJson() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("id", Registry.ITEM.getId(this.item).toString());
         if (this.count != 1) {
            _snowman.addProperty("count", this.count);
         }

         if (this.tag != null) {
            _snowman.addProperty("tag", this.tag.toString());
         }

         return _snowman;
      }
   }
}
