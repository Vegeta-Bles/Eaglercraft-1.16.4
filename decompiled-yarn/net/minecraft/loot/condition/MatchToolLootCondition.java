package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.JsonSerializer;

public class MatchToolLootCondition implements LootCondition {
   private final ItemPredicate predicate;

   public MatchToolLootCondition(ItemPredicate predicate) {
      this.predicate = predicate;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.MATCH_TOOL;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.TOOL);
   }

   public boolean test(LootContext _snowman) {
      ItemStack _snowmanx = _snowman.get(LootContextParameters.TOOL);
      return _snowmanx != null && this.predicate.test(_snowmanx);
   }

   public static LootCondition.Builder builder(ItemPredicate.Builder predicate) {
      return () -> new MatchToolLootCondition(predicate.build());
   }

   public static class Serializer implements JsonSerializer<MatchToolLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, MatchToolLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("predicate", _snowman.predicate.toJson());
      }

      public MatchToolLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         ItemPredicate _snowmanxx = ItemPredicate.fromJson(_snowman.get("predicate"));
         return new MatchToolLootCondition(_snowmanxx);
      }
   }
}
