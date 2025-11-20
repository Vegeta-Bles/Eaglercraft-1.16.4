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
      int _snowman = this.limit.applyAsInt(stack.getCount());
      stack.setCount(_snowman);
      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(BoundedIntUnaryOperator limit) {
      return builder(conditions -> new LimitCountLootFunction(conditions, limit));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<LimitCountLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, LimitCountLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("limit", _snowman.serialize(_snowman.limit));
      }

      public LimitCountLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         BoundedIntUnaryOperator _snowmanxxx = JsonHelper.deserialize(_snowman, "limit", _snowman, BoundedIntUnaryOperator.class);
         return new LimitCountLootFunction(_snowman, _snowmanxxx);
      }
   }
}
