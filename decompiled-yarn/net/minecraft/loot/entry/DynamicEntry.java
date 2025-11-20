package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class DynamicEntry extends LeafEntry {
   private final Identifier name;

   private DynamicEntry(Identifier name, int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.name = name;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.DYNAMIC;
   }

   @Override
   public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
      context.drop(this.name, lootConsumer);
   }

   public static LeafEntry.Builder<?> builder(Identifier name) {
      return builder((weight, quality, conditions, functions) -> new DynamicEntry(name, weight, quality, conditions, functions));
   }

   public static class Serializer extends LeafEntry.Serializer<DynamicEntry> {
      public Serializer() {
      }

      public void addEntryFields(JsonObject _snowman, DynamicEntry _snowman, JsonSerializationContext _snowman) {
         super.addEntryFields(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.name.toString());
      }

      protected DynamicEntry fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, int _snowman, int _snowman, LootCondition[] _snowman, LootFunction[] _snowman) {
         Identifier _snowmanxxxxxx = new Identifier(JsonHelper.getString(_snowman, "name"));
         return new DynamicEntry(_snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
