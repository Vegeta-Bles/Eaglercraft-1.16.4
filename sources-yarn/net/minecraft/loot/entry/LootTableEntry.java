package net.minecraft.loot.entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class LootTableEntry extends LeafEntry {
   private final Identifier id;

   private LootTableEntry(Identifier id, int weight, int quality, LootCondition[] conditions, LootFunction[] functions) {
      super(weight, quality, conditions, functions);
      this.id = id;
   }

   @Override
   public LootPoolEntryType getType() {
      return LootPoolEntryTypes.LOOT_TABLE;
   }

   @Override
   public void generateLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
      LootTable lv = context.getSupplier(this.id);
      lv.generateUnprocessedLoot(context, lootConsumer);
   }

   @Override
   public void validate(LootTableReporter reporter) {
      if (reporter.hasTable(this.id)) {
         reporter.report("Table " + this.id + " is recursively called");
      } else {
         super.validate(reporter);
         LootTable lv = reporter.getTable(this.id);
         if (lv == null) {
            reporter.report("Unknown loot table called " + this.id);
         } else {
            lv.validate(reporter.withTable("->{" + this.id + "}", this.id));
         }
      }
   }

   public static LeafEntry.Builder<?> builder(Identifier id) {
      return builder((weight, quality, conditions, functions) -> new LootTableEntry(id, weight, quality, conditions, functions));
   }

   public static class Serializer extends LeafEntry.Serializer<LootTableEntry> {
      public Serializer() {
      }

      public void addEntryFields(JsonObject jsonObject, LootTableEntry arg, JsonSerializationContext jsonSerializationContext) {
         super.addEntryFields(jsonObject, arg, jsonSerializationContext);
         jsonObject.addProperty("name", arg.id.toString());
      }

      protected LootTableEntry fromJson(
         JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, int i, int j, LootCondition[] args, LootFunction[] args2
      ) {
         Identifier lv = new Identifier(JsonHelper.getString(jsonObject, "name"));
         return new LootTableEntry(lv, i, j, args, args2);
      }
   }
}
