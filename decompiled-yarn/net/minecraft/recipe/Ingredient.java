package net.minecraft.recipe;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public final class Ingredient implements Predicate<ItemStack> {
   public static final Ingredient EMPTY = new Ingredient(Stream.empty());
   private final Ingredient.Entry[] entries;
   private ItemStack[] matchingStacks;
   private IntList ids;

   private Ingredient(Stream<? extends Ingredient.Entry> entries) {
      this.entries = entries.toArray(Ingredient.Entry[]::new);
   }

   public ItemStack[] getMatchingStacksClient() {
      this.cacheMatchingStacks();
      return this.matchingStacks;
   }

   private void cacheMatchingStacks() {
      if (this.matchingStacks == null) {
         this.matchingStacks = Arrays.stream(this.entries).flatMap(_snowman -> _snowman.getStacks().stream()).distinct().toArray(ItemStack[]::new);
      }
   }

   public boolean test(@Nullable ItemStack _snowman) {
      if (_snowman == null) {
         return false;
      } else {
         this.cacheMatchingStacks();
         if (this.matchingStacks.length == 0) {
            return _snowman.isEmpty();
         } else {
            for (ItemStack _snowmanx : this.matchingStacks) {
               if (_snowmanx.getItem() == _snowman.getItem()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public IntList getIds() {
      if (this.ids == null) {
         this.cacheMatchingStacks();
         this.ids = new IntArrayList(this.matchingStacks.length);

         for (ItemStack _snowman : this.matchingStacks) {
            this.ids.add(RecipeFinder.getItemId(_snowman));
         }

         this.ids.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.ids;
   }

   public void write(PacketByteBuf buf) {
      this.cacheMatchingStacks();
      buf.writeVarInt(this.matchingStacks.length);

      for (int _snowman = 0; _snowman < this.matchingStacks.length; _snowman++) {
         buf.writeItemStack(this.matchingStacks[_snowman]);
      }
   }

   public JsonElement toJson() {
      if (this.entries.length == 1) {
         return this.entries[0].toJson();
      } else {
         JsonArray _snowman = new JsonArray();

         for (Ingredient.Entry _snowmanx : this.entries) {
            _snowman.add(_snowmanx.toJson());
         }

         return _snowman;
      }
   }

   public boolean isEmpty() {
      return this.entries.length == 0 && (this.matchingStacks == null || this.matchingStacks.length == 0) && (this.ids == null || this.ids.isEmpty());
   }

   private static Ingredient ofEntries(Stream<? extends Ingredient.Entry> entries) {
      Ingredient _snowman = new Ingredient(entries);
      return _snowman.entries.length == 0 ? EMPTY : _snowman;
   }

   public static Ingredient ofItems(ItemConvertible... items) {
      return ofStacks(Arrays.stream(items).map(ItemStack::new));
   }

   public static Ingredient ofStacks(ItemStack... stacks) {
      return ofStacks(Arrays.stream(stacks));
   }

   public static Ingredient ofStacks(Stream<ItemStack> stacks) {
      return ofEntries(stacks.filter(_snowman -> !_snowman.isEmpty()).map(stack -> new Ingredient.StackEntry(stack)));
   }

   public static Ingredient fromTag(Tag<Item> tag) {
      return ofEntries(Stream.of(new Ingredient.TagEntry(tag)));
   }

   public static Ingredient fromPacket(PacketByteBuf buf) {
      int _snowman = buf.readVarInt();
      return ofEntries(Stream.<Ingredient.Entry>generate(() -> new Ingredient.StackEntry(buf.readItemStack())).limit((long)_snowman));
   }

   public static Ingredient fromJson(@Nullable JsonElement json) {
      if (json == null || json.isJsonNull()) {
         throw new JsonSyntaxException("Item cannot be null");
      } else if (json.isJsonObject()) {
         return ofEntries(Stream.of(entryFromJson(json.getAsJsonObject())));
      } else if (json.isJsonArray()) {
         JsonArray _snowman = json.getAsJsonArray();
         if (_snowman.size() == 0) {
            throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
         } else {
            return ofEntries(
               StreamSupport.<JsonElement>stream(_snowman.spliterator(), false).map(jsonElement -> entryFromJson(JsonHelper.asObject(jsonElement, "item")))
            );
         }
      } else {
         throw new JsonSyntaxException("Expected item to be object or array of objects");
      }
   }

   private static Ingredient.Entry entryFromJson(JsonObject json) {
      if (json.has("item") && json.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (json.has("item")) {
         Identifier _snowman = new Identifier(JsonHelper.getString(json, "item"));
         Item _snowmanx = Registry.ITEM.getOrEmpty(_snowman).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + _snowman + "'"));
         return new Ingredient.StackEntry(new ItemStack(_snowmanx));
      } else if (json.has("tag")) {
         Identifier _snowman = new Identifier(JsonHelper.getString(json, "tag"));
         Tag<Item> _snowmanx = ServerTagManagerHolder.getTagManager().getItems().getTag(_snowman);
         if (_snowmanx == null) {
            throw new JsonSyntaxException("Unknown item tag '" + _snowman + "'");
         } else {
            return new Ingredient.TagEntry(_snowmanx);
         }
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   interface Entry {
      Collection<ItemStack> getStacks();

      JsonObject toJson();
   }

   static class StackEntry implements Ingredient.Entry {
      private final ItemStack stack;

      private StackEntry(ItemStack stack) {
         this.stack = stack;
      }

      @Override
      public Collection<ItemStack> getStacks() {
         return Collections.singleton(this.stack);
      }

      @Override
      public JsonObject toJson() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("item", Registry.ITEM.getId(this.stack.getItem()).toString());
         return _snowman;
      }
   }

   static class TagEntry implements Ingredient.Entry {
      private final Tag<Item> tag;

      private TagEntry(Tag<Item> tag) {
         this.tag = tag;
      }

      @Override
      public Collection<ItemStack> getStacks() {
         List<ItemStack> _snowman = Lists.newArrayList();

         for (Item _snowmanx : this.tag.values()) {
            _snowman.add(new ItemStack(_snowmanx));
         }

         return _snowman;
      }

      @Override
      public JsonObject toJson() {
         JsonObject _snowman = new JsonObject();
         _snowman.addProperty("tag", ServerTagManagerHolder.getTagManager().getItems().getTagId(this.tag).toString());
         return _snowman;
      }
   }
}
