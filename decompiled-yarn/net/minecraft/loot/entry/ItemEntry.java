package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class ItemEntry extends LeafEntry {
   private final Item item;

   private ItemEntry(Item item, int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.item = item;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.ITEM;
   }

   @Override
   public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
      lootConsumer.accept(new ItemStack(this.item));
   }

   public static LeafEntry.Builder<?> builder(ItemConvertible drop) {
      return builder((weight, quality, conditions, functions) -> new ItemEntry(drop.asItem(), weight, quality, conditions, functions));
   }

   public static class Serializer extends LeafEntry.Serializer<ItemEntry> {
      public Serializer() {
      }

      public void addEntryFields(JsonObject _snowman, ItemEntry _snowman, JsonSerializationContext _snowman) {
         super.addEntryFields(_snowman, _snowman, _snowman);
         Identifier _snowmanxxx = Registry.ITEM.getId(_snowman.item);
         if (_snowmanxxx == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + _snowman.item);
         } else {
            _snowman.addProperty("name", _snowmanxxx.toString());
         }
      }

      protected ItemEntry fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, int _snowman, int _snowman, LootCondition[] _snowman, LootFunction[] _snowman) {
         Item _snowmanxxxxxx = JsonHelper.getItem(_snowman, "name");
         return new ItemEntry(_snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
