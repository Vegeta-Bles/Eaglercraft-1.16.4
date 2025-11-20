package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTableRange;
import net.minecraft.loot.LootTableRanges;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;

public class EnchantWithLevelsLootFunction extends ConditionalLootFunction {
   private final LootTableRange range;
   private final boolean treasureEnchantmentsAllowed;

   private EnchantWithLevelsLootFunction(LootCondition[] conditions, LootTableRange range, boolean treasureEnchantmentsAllowed) {
      super(conditions);
      this.range = range;
      this.treasureEnchantmentsAllowed = treasureEnchantmentsAllowed;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.ENCHANT_WITH_LEVELS;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Random _snowman = context.getRandom();
      return EnchantmentHelper.enchant(_snowman, stack, this.range.next(_snowman), this.treasureEnchantmentsAllowed);
   }

   public static EnchantWithLevelsLootFunction.Builder builder(LootTableRange range) {
      return new EnchantWithLevelsLootFunction.Builder(range);
   }

   public static class Builder extends ConditionalLootFunction.Builder<EnchantWithLevelsLootFunction.Builder> {
      private final LootTableRange range;
      private boolean treasureEnchantmentsAllowed;

      public Builder(LootTableRange range) {
         this.range = range;
      }

      protected EnchantWithLevelsLootFunction.Builder getThisBuilder() {
         return this;
      }

      public EnchantWithLevelsLootFunction.Builder allowTreasureEnchantments() {
         this.treasureEnchantmentsAllowed = true;
         return this;
      }

      @Override
      public LootFunction build() {
         return new EnchantWithLevelsLootFunction(this.getConditions(), this.range, this.treasureEnchantmentsAllowed);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<EnchantWithLevelsLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, EnchantWithLevelsLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("levels", LootTableRanges.toJson(_snowman.range, _snowman));
         _snowman.addProperty("treasure", _snowman.treasureEnchantmentsAllowed);
      }

      public EnchantWithLevelsLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         LootTableRange _snowmanxxx = LootTableRanges.fromJson(_snowman.get("levels"), _snowman);
         boolean _snowmanxxxx = JsonHelper.getBoolean(_snowman, "treasure", false);
         return new EnchantWithLevelsLootFunction(_snowman, _snowmanxxx, _snowmanxxxx);
      }
   }
}
