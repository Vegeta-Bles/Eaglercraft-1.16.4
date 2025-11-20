package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class InvertedLootCondition implements LootCondition {
   private final LootCondition term;

   private InvertedLootCondition(LootCondition term) {
      this.term = term;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.INVERTED;
   }

   public final boolean test(LootContext _snowman) {
      return !this.term.test(_snowman);
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return this.term.getRequiredParameters();
   }

   @Override
   public void validate(LootTableReporter reporter) {
      LootCondition.super.validate(reporter);
      this.term.validate(reporter);
   }

   public static LootCondition.Builder builder(LootCondition.Builder term) {
      InvertedLootCondition _snowman = new InvertedLootCondition(term.build());
      return () -> _snowman;
   }

   public static class Serializer implements JsonSerializer<InvertedLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, InvertedLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("term", _snowman.serialize(_snowman.term));
      }

      public InvertedLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         LootCondition _snowmanxx = JsonHelper.deserialize(_snowman, "term", _snowman, LootCondition.class);
         return new InvertedLootCondition(_snowmanxx);
      }
   }
}
