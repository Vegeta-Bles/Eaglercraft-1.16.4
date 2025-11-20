package net.minecraft.loot.condition;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;

public class AlternativeLootCondition implements LootCondition {
   private final LootCondition[] terms;
   private final Predicate<LootContext> predicate;

   private AlternativeLootCondition(LootCondition[] terms) {
      this.terms = terms;
      this.predicate = LootConditionTypes.joinOr(terms);
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.ALTERNATIVE;
   }

   public final boolean test(LootContext _snowman) {
      return this.predicate.test(_snowman);
   }

   @Override
   public void validate(LootTableReporter reporter) {
      LootCondition.super.validate(reporter);

      for (int _snowman = 0; _snowman < this.terms.length; _snowman++) {
         this.terms[_snowman].validate(reporter.makeChild(".term[" + _snowman + "]"));
      }
   }

   public static AlternativeLootCondition.Builder builder(LootCondition.Builder... terms) {
      return new AlternativeLootCondition.Builder(terms);
   }

   public static class Builder implements LootCondition.Builder {
      private final List<LootCondition> terms = Lists.newArrayList();

      public Builder(LootCondition.Builder... terms) {
         for (LootCondition.Builder _snowman : terms) {
            this.terms.add(_snowman.build());
         }
      }

      @Override
      public AlternativeLootCondition.Builder or(LootCondition.Builder condition) {
         this.terms.add(condition.build());
         return this;
      }

      @Override
      public LootCondition build() {
         return new AlternativeLootCondition(this.terms.toArray(new LootCondition[0]));
      }
   }

   public static class Serializer implements JsonSerializer<AlternativeLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, AlternativeLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.add("terms", _snowman.serialize(_snowman.terms));
      }

      public AlternativeLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         LootCondition[] _snowmanxx = JsonHelper.deserialize(_snowman, "terms", _snowman, LootCondition[].class);
         return new AlternativeLootCondition(_snowmanxx);
      }
   }
}
