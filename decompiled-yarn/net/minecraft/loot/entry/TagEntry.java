package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootChoice;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class TagEntry extends LeafEntry {
   private final Tag<Item> name;
   private final boolean expand;

   private TagEntry(Tag<Item> name, boolean expand, int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.name = name;
      this.expand = expand;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.TAG;
   }

   @Override
   public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
      this.name.values().forEach(_snowmanx -> lootConsumer.accept(new ItemStack(_snowmanx)));
   }

   private boolean grow(LootContext context, Consumer<LootChoice> lootChoiceExpander) {
      if (!this.test(context)) {
         return false;
      } else {
         for (final Item _snowman : this.name.values()) {
            lootChoiceExpander.accept(new LeafEntry.Choice() {
               @Override
               public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
                  lootConsumer.accept(new ItemStack(_snowman));
               }
            });
         }

         return true;
      }
   }

   @Override
   public boolean expand(LootContext _snowman, Consumer<LootChoice> _snowman) {
      return this.expand ? this.grow(_snowman, _snowman) : super.expand(_snowman, _snowman);
   }

   public static LeafEntry.Builder<?> builder(Tag<Item> name) {
      return builder((weight, quality, conditions, functions) -> new TagEntry(name, true, weight, quality, conditions, functions));
   }

   public static class Serializer extends LeafEntry.Serializer<TagEntry> {
      public Serializer() {
      }

      public void addEntryFields(JsonObject _snowman, TagEntry _snowman, JsonSerializationContext _snowman) {
         super.addEntryFields(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", ServerTagManagerHolder.getTagManager().getItems().getTagId(_snowman.name).toString());
         _snowman.addProperty("expand", _snowman.expand);
      }

      protected TagEntry fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, int _snowman, int _snowman, LootCondition[] _snowman, LootFunction[] _snowman) {
         Identifier _snowmanxxxxxx = new Identifier(JsonHelper.getString(_snowman, "name"));
         Tag<Item> _snowmanxxxxxxx = ServerTagManagerHolder.getTagManager().getItems().getTag(_snowmanxxxxxx);
         if (_snowmanxxxxxxx == null) {
            throw new JsonParseException("Can't find tag: " + _snowmanxxxxxx);
         } else {
            boolean _snowmanxxxxxxxx = JsonHelper.getBoolean(_snowman, "expand");
            return new TagEntry(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowman, _snowman, _snowman, _snowman);
         }
      }
   }
}
