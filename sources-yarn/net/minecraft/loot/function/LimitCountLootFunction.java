package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.util.JsonHelper;

public class LimitCountLootFunction extends ConditionalLootFunction {
   private final BoundedIntUnaryOperator limit;

   private LimitCountLootFunction(LootCondition[] conditions, BoundedIntUnaryOperator limit) {
      super(conditions);
      this.limit = limit;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.LIMIT_COUNT;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      int i = this.limit.applyAsInt(stack.getCount());
      stack.setCount(i);
      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(BoundedIntUnaryOperator limit) {
      return builder(conditions -> new LimitCountLootFunction(conditions, limit));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<LimitCountLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, LimitCountLootFunction arg, JsonSerializationContext jsonSerializationContext) {
         super.toJson(jsonObject, arg, jsonSerializationContext);
         jsonObject.add("limit", jsonSerializationContext.serialize(arg.limit));
      }

      public LimitCountLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         BoundedIntUnaryOperator lv = JsonHelper.deserialize(jsonObject, "limit", jsonDeserializationContext, BoundedIntUnaryOperator.class);
         return new LimitCountLootFunction(args, lv);
      }
   }
}
