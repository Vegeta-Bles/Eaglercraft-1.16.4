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

   public boolean test(LootContext _snowman) {
      Entity _snowmanx = _snowman.get(LootContextParameters.KILLER_ENTITY);
      int _snowmanxx = 0;
      if (_snowmanx instanceof LivingEntity) {
         _snowmanxx = EnchantmentHelper.getLooting((LivingEntity)_snowmanx);
      }

      return _snowman.getRandom().nextFloat() < this.chance + (float)_snowmanxx * this.lootingMultiplier;
   }

   public static LootCondition.Builder builder(float chance, float lootingMultiplier) {
      return () -> new RandomChanceWithLootingLootCondition(chance, lootingMultiplier);
   }

   public static class Serializer implements JsonSerializer<RandomChanceWithLootingLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, RandomChanceWithLootingLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("chance", _snowman.chance);
         _snowman.addProperty("looting_multiplier", _snowman.lootingMultiplier);
      }

      public RandomChanceWithLootingLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         return new RandomChanceWithLootingLootCondition(JsonHelper.getFloat(_snowman, "chance"), JsonHelper.getFloat(_snowman, "looting_multiplier"));
      }
   }
}
