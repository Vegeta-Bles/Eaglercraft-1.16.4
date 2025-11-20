package net.minecraft.loot.function;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionConsumingBuilder;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import org.apache.commons.lang3.ArrayUtils;

public abstract class ConditionalLootFunction implements LootFunction {
   protected final LootCondition[] conditions;
   private final Predicate<LootContext> predicate;

   protected ConditionalLootFunction(LootCondition[] conditions) {
      this.conditions = conditions;
      this.predicate = LootConditionTypes.joinAnd(conditions);
   }

   public final ItemStack apply(ItemStack arg, LootContext arg2) {
      return this.predicate.test(arg2) ? this.process(arg, arg2) : arg;
   }

   protected abstract ItemStack process(ItemStack stack, LootContext context);

   @Override
   public void validate(LootTableReporter reporter) {
      LootFunction.super.validate(reporter);

      for (int i = 0; i < this.conditions.length; i++) {
         this.conditions[i].validate(reporter.makeChild(".conditions[" + i + "]"));
      }
   }

   protected static ConditionalLootFunction.Builder<?> builder(Function<LootCondition[], LootFunction> joiner) {
      return new ConditionalLootFunction.Joiner(joiner);
   }

   public abstract static class Builder<T extends ConditionalLootFunction.Builder<T>> implements LootFunction.Builder, LootConditionConsumingBuilder<T> {
      private final List<LootCondition> conditionList = Lists.newArrayList();

      public Builder() {
      }

      public T conditionally(LootCondition.Builder arg) {
         this.conditionList.add(arg.build());
         return this.getThisBuilder();
      }

      public final T getThis() {
         return this.getThisBuilder();
      }

      protected abstract T getThisBuilder();

      protected LootCondition[] getConditions() {
         return this.conditionList.toArray(new LootCondition[0]);
      }
   }

   static final class Joiner extends ConditionalLootFunction.Builder<ConditionalLootFunction.Joiner> {
      private final Function<LootCondition[], LootFunction> joiner;

      public Joiner(Function<LootCondition[], LootFunction> joiner) {
         this.joiner = joiner;
      }

      protected ConditionalLootFunction.Joiner getThisBuilder() {
         return this;
      }

      @Override
      public LootFunction build() {
         return this.joiner.apply(this.getConditions());
      }
   }

   public abstract static class Serializer<T extends ConditionalLootFunction> implements JsonSerializer<T> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, T arg, JsonSerializationContext jsonSerializationContext) {
         if (!ArrayUtils.isEmpty(arg.conditions)) {
            jsonObject.add("conditions", jsonSerializationContext.serialize(arg.conditions));
         }
      }

      public final T fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         LootCondition[] lvs = JsonHelper.deserialize(jsonObject, "conditions", new LootCondition[0], jsonDeserializationContext, LootCondition[].class);
         return this.fromJson(jsonObject, jsonDeserializationContext, lvs);
      }

      public abstract T fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions);
   }
}
