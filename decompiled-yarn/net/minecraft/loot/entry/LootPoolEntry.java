package net.minecraft.loot.entry;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionConsumingBuilder;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import org.apache.commons.lang3.ArrayUtils;

public abstract class LootPoolEntry implements EntryCombiner {
   protected final LootCondition[] conditions;
   private final Predicate<LootContext> conditionPredicate;

   protected LootPoolEntry(LootCondition[] conditions) {
      this.conditions = conditions;
      this.conditionPredicate = LootConditionTypes.joinAnd(conditions);
   }

   public void validate(LootTableReporter reporter) {
      for (int _snowman = 0; _snowman < this.conditions.length; _snowman++) {
         this.conditions[_snowman].validate(reporter.makeChild(".condition[" + _snowman + "]"));
      }
   }

   protected final boolean test(LootContext context) {
      return this.conditionPredicate.test(context);
   }

   public abstract LootPoolEntryType getType();

   public abstract static class Builder<T extends LootPoolEntry.Builder<T>> implements LootConditionConsumingBuilder<T> {
      private final List<LootCondition> conditions = Lists.newArrayList();

      public Builder() {
      }

      protected abstract T getThisBuilder();

      public T conditionally(LootCondition.Builder _snowman) {
         this.conditions.add(_snowman.build());
         return this.getThisBuilder();
      }

      public final T getThis() {
         return this.getThisBuilder();
      }

      protected LootCondition[] getConditions() {
         return this.conditions.toArray(new LootCondition[0]);
      }

      public AlternativeEntry.Builder alternatively(LootPoolEntry.Builder<?> builder) {
         return new AlternativeEntry.Builder(this, builder);
      }

      public abstract LootPoolEntry build();
   }

   public abstract static class Serializer<T extends LootPoolEntry> implements JsonSerializer<T> {
      public Serializer() {
      }

      public final void toJson(JsonObject _snowman, T _snowman, JsonSerializationContext _snowman) {
         if (!ArrayUtils.isEmpty(_snowman.conditions)) {
            _snowman.add("conditions", _snowman.serialize(_snowman.conditions));
         }

         this.addEntryFields(_snowman, _snowman, _snowman);
      }

      public final T fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         LootCondition[] _snowmanxx = JsonHelper.deserialize(_snowman, "conditions", new LootCondition[0], _snowman, LootCondition[].class);
         return this.fromJson(_snowman, _snowman, _snowmanxx);
      }

      public abstract void addEntryFields(JsonObject json, T entry, JsonSerializationContext context);

      public abstract T fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions);
   }
}
