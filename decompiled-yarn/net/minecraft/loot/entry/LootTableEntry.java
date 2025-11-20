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
      LootTable _snowman = context.getSupplier(this.id);
      _snowman.generateUnprocessedLoot(context, lootConsumer);
   }

   @Override
   public void validate(LootTableReporter reporter) {
      if (reporter.hasTable(this.id)) {
         reporter.report("Table " + this.id + " is recursively called");
      } else {
         super.validate(reporter);
         LootTable _snowman = reporter.getTable(this.id);
         if (_snowman == null) {
            reporter.report("Unknown loot table called " + this.id);
         } else {
            _snowman.validate(reporter.withTable("->{" + this.id + "}", this.id));
         }
      }
   }

   public static LeafEntry.Builder<?> builder(Identifier id) {
      return builder((weight, quality, conditions, functions) -> new LootTableEntry(id, weight, quality, conditions, functions));
   }

   public static class Serializer extends LeafEntry.Serializer<LootTableEntry> {
      public Serializer() {
      }

      public void addEntryFields(JsonObject _snowman, LootTableEntry _snowman, JsonSerializationContext _snowman) {
         super.addEntryFields(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.id.toString());
      }

      protected LootTableEntry fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, int _snowman, int _snowman, LootCondition[] _snowman, LootFunction[] _snowman) {
         Identifier _snowmanxxxxxx = new Identifier(JsonHelper.getString(_snowman, "name"));
         return new LootTableEntry(_snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
