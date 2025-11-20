package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class RandomChanceWithLootingLootCondition implements LootCondition {
   private final float chance;
   private final float lootingMultiplier;

   private RandomChanceWithLootingLootCondition(float chance, float lootingMultiplier) {
      this.chance = chance;
      this.lootingMultiplier = lootingMultiplier;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.RANDOM_CHANCE_WITH_LOOTING;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.KILLER_ENTITY);
   }

   public boolean test(LootContext arg) {
      Entity lv = arg.get(LootContextParameters.KILLER_ENTITY);
      int i = 0;
      if (lv instanceof LivingEntity) {
         i = EnchantmentHelper.getLooting((LivingEntity)lv);
      }

      return arg.getRandom().nextFloat() < this.chance + (float)i * this.lootingMultiplier;
   }

   public static LootCondition.Builder builder(float chance, float lootingMultiplier) {
      return () -> new RandomChanceWithLootingLootCondition(chance, lootingMultiplier);
   }

   public static class Serializer implements JsonSerializer<RandomChanceWithLootingLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, RandomChanceWithLootingLootCondition arg, JsonSerializationContext jsonSerializationContext) {
         jsonObject.addProperty("chance", arg.chance);
         jsonObject.addProperty("looting_multiplier", arg.lootingMultiplier);
      }

      public RandomChanceWithLootingLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         return new RandomChanceWithLootingLootCondition(JsonHelper.getFloat(jsonObject, "chance"), JsonHelper.getFloat(jsonObject, "looting_multiplier"));
      }
   }
}
