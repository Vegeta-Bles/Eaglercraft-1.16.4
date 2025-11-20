package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonHelper;

public class LootingEnchantLootFunction extends ConditionalLootFunction {
   private final UniformLootTableRange countRange;
   private final int limit;

   private LootingEnchantLootFunction(LootCondition[] conditions, UniformLootTableRange countRange, int limit) {
      super(conditions);
      this.countRange = countRange;
      this.limit = limit;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.LOOTING_ENCHANT;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.KILLER_ENTITY);
   }

   private boolean hasLimit() {
      return this.limit > 0;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Entity _snowman = context.get(LootContextParameters.KILLER_ENTITY);
      if (_snowman instanceof LivingEntity) {
         int _snowmanx = EnchantmentHelper.getLooting((LivingEntity)_snowman);
         if (_snowmanx == 0) {
            return stack;
         }

         float _snowmanxx = (float)_snowmanx * this.countRange.nextFloat(context.getRandom());
         stack.increment(Math.round(_snowmanxx));
         if (this.hasLimit() && stack.getCount() > this.limit) {
            stack.setCount(this.limit);
         }
      }

      return stack;
   }

   public static LootingEnchantLootFunction.Builder builder(UniformLootTableRange countRange) {
      return new LootingEnchantLootFunction.Builder(countRange);
   }

   public static class Builder extends ConditionalLootFunction.Builder<LootingEnchantLootFunction.Builder> {
      private final UniformLootTableRange countRange;
      private int limit = 0;

      public Builder(UniformLootTableRange countRange) {
         this.countRange = countRange;
      }

      protected LootingEnchantLootFunction.Builder getThisBuilder() {
         return this;
      }

      public LootingEnchantLootFunction.Builder withLimit(int limit) {
         this.limit = limit;
         return this;
      }

      @Override
      public LootFunction build() {
         return new LootingEnchantLootFunction(this.getConditions(), this.countRange, this.limit);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<LootingEnchantLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, LootingEnchantLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("count", _snowman.serialize(_snowman.countRange));
         if (_snowman.hasLimit()) {
            _snowman.add("limit", _snowman.serialize(_snowman.limit));
         }
      }

      public LootingEnchantLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         int _snowmanxxx = JsonHelper.getInt(_snowman, "limit", 0);
         return new LootingEnchantLootFunction(_snowman, JsonHelper.deserialize(_snowman, "count", _snowman, UniformLootTableRange.class), _snowmanxxx);
      }
   }
}
