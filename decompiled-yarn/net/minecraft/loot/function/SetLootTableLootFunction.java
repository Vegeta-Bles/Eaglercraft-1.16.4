package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class SetLootTableLootFunction extends ConditionalLootFunction {
   private final Identifier id;
   private final long seed;

   private SetLootTableLootFunction(LootCondition[] conditions, Identifier id, long seed) {
      super(conditions);
      this.id = id;
      this.seed = seed;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_LOOT_TABLE;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.isEmpty()) {
         return stack;
      } else {
         CompoundTag _snowman = new CompoundTag();
         _snowman.putString("LootTable", this.id.toString());
         if (this.seed != 0L) {
            _snowman.putLong("LootTableSeed", this.seed);
         }

         stack.getOrCreateTag().put("BlockEntityTag", _snowman);
         return stack;
      }
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

   public static class Serializer extends ConditionalLootFunction.Serializer<SetLootTableLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetLootTableLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.id.toString());
         if (_snowman.seed != 0L) {
            _snowman.addProperty("seed", _snowman.seed);
         }
      }

      public SetLootTableLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "name"));
         long _snowmanxxxx = JsonHelper.getLong(_snowman, "seed", 0L);
         return new SetLootTableLootFunction(_snowman, _snowmanxxx, _snowmanxxxx);
      }
   }
}
