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

   public final ItemStack apply(ItemStack _snowman, LootContext _snowman) {
      return this.predicate.test(_snowman) ? this.process(_snowman, _snowman) : _snowman;
   }

   protected abstract ItemStack process(ItemStack stack, LootContext context);

   @Override
   public void validate(LootTableReporter reporter) {
      LootFunction.super.validate(reporter);

      for (int _snowman = 0; _snowman < this.conditions.length; _snowman++) {
         this.conditions[_snowman].validate(reporter.makeChild(".conditions[" + _snowman + "]"));
      }
   }

   protected static ConditionalLootFunction.Builder<?> builder(Function<LootCondition[], LootFunction> joiner) {
      return new ConditionalLootFunction.Joiner(joiner);
   }

   public abstract static class Builder<T extends ConditionalLootFunction.Builder<T>> implements LootFunction.Builder, LootConditionConsumingBuilder<T> {
      private final List<LootCondition> conditionList = Lists.newArrayList();

      public Builder() {
      }

      public T conditionally(LootCondition.Builder _snowman) {
         this.conditionList.add(_snowman.build());
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

      public void toJson(JsonObject _snowman, T _snowman, JsonSerializationContext _snowman) {
         if (!ArrayUtils.isEmpty(_snowman.conditions)) {
            _snowman.add("conditions", _snowman.serialize(_snowman.conditions));
         }
      }

      public final T fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         LootCondition[] _snowmanxx = JsonHelper.deserialize(_snowman, "conditions", new LootCondition[0], _snowman, LootCondition[].class);
         return this.fromJson(_snowman, _snowman, _snowmanxx);
      }

      public abstract T fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions);
   }
}
