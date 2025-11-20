package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTableRange;
import net.minecraft.loot.LootTableRanges;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;

public class SetCountLootFunction extends ConditionalLootFunction {
   private final LootTableRange countRange;

   private SetCountLootFunction(LootCondition[] conditions, LootTableRange countRange) {
      super(conditions);
      this.countRange = countRange;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_COUNT;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      stack.setCount(this.countRange.next(context.getRandom()));
      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(LootTableRange countRange) {
      return builder(conditions -> new SetCountLootFunction(conditions, countRange));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetCountLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetCountLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("count", LootTableRanges.toJson(_snowman.countRange, _snowman));
      }

      public SetCountLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         LootTableRange _snowmanxxx = LootTableRanges.fromJson(_snowman.get("count"), _snowman);
         return new SetCountLootFunction(_snowman, _snowmanxxx);
      }
   }
}
