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

      public void addEntryFields(JsonObject jsonObject, DynamicEntry arg, JsonSerializationContext jsonSerializationContext) {
         super.addEntryFields(jsonObject, arg, jsonSerializationContext);
         jsonObject.addProperty("name", arg.name.toString());
      }

      protected DynamicEntry fromJson(
         JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int i, int j, LootCondition[] args, LootFunction[] args2
      ) {
         Identifier lv = new Identifier(JsonHelper.getString(jsonObject, "name"));
         return new DynamicEntry(lv, i, j, args, args2);
      }
   }
}
