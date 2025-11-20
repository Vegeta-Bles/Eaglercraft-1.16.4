package net.minecraft.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionConsumingBuilder;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.LootFunctionConsumingBuilder;
import net.minecraft.loot.function.LootFunctionTypes;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.mutable.MutableInt;

public class LootPool {
   private final LootPoolEntry[] entries;
   private final LootCondition[] conditions;
   private final Predicate<LootContext> predicate;
   private final LootFunction[] functions;
   private final BiFunction<ItemStack, LootContext, ItemStack> javaFunctions;
   private final LootTableRange rolls;
   private final UniformLootTableRange bonusRolls;

   private LootPool(LootPoolEntry[] entries, LootCondition[] conditions, LootFunction[] functions, LootTableRange rolls, UniformLootTableRange bonusRolls) {
      this.entries = entries;
      this.conditions = conditions;
      this.predicate = LootConditionTypes.joinAnd(conditions);
      this.functions = functions;
      this.javaFunctions = LootFunctionTypes.join(functions);
      this.rolls = rolls;
      this.bonusRolls = bonusRolls;
   }

   private void supplyOnce(Consumer<ItemStack> lootConsumer, LootContext context) {
      Random _snowman = context.getRandom();
      List<LootChoice> _snowmanx = Lists.newArrayList();
      MutableInt _snowmanxx = new MutableInt();

      for (LootPoolEntry _snowmanxxx : this.entries) {
         _snowmanxxx.expand(context, choice -> {
            int _snowmanxxxx = choice.getWeight(context.getLuck());
            if (_snowmanxxxx > 0) {
               _snowman.add(choice);
               _snowman.add(_snowmanxxxx);
            }
         });
      }

      int _snowmanxxx = _snowmanx.size();
      if (_snowmanxx.intValue() != 0 && _snowmanxxx != 0) {
         if (_snowmanxxx == 1) {
            _snowmanx.get(0).generateLoot(lootConsumer, context);
         } else {
            int _snowmanxxxx = _snowman.nextInt(_snowmanxx.intValue());

            for (LootChoice _snowmanxxxxx : _snowmanx) {
               _snowmanxxxx -= _snowmanxxxxx.getWeight(context.getLuck());
               if (_snowmanxxxx < 0) {
                  _snowmanxxxxx.generateLoot(lootConsumer, context);
                  return;
               }
            }
         }
      }
   }

   public void addGeneratedLoot(Consumer<ItemStack> lootConsumer, LootContext context) {
      if (this.predicate.test(context)) {
         Consumer<ItemStack> _snowman = LootFunction.apply(this.javaFunctions, lootConsumer, context);
         Random _snowmanx = context.getRandom();
         int _snowmanxx = this.rolls.next(_snowmanx) + MathHelper.floor(this.bonusRolls.nextFloat(_snowmanx) * context.getLuck());

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            this.supplyOnce(_snowman, context);
         }
      }
   }

   public void validate(LootTableReporter _snowman) {
      for (int _snowmanx = 0; _snowmanx < this.conditions.length; _snowmanx++) {
         this.conditions[_snowmanx].validate(_snowman.makeChild(".condition[" + _snowmanx + "]"));
      }

      for (int _snowmanx = 0; _snowmanx < this.functions.length; _snowmanx++) {
         this.functions[_snowmanx].validate(_snowman.makeChild(".functions[" + _snowmanx + "]"));
      }

      for (int _snowmanx = 0; _snowmanx < this.entries.length; _snowmanx++) {
         this.entries[_snowmanx].validate(_snowman.makeChild(".entries[" + _snowmanx + "]"));
      }
   }

   public static LootPool.Builder builder() {
      return new LootPool.Builder();
   }

   public static class Builder implements LootFunctionConsumingBuilder<LootPool.Builder>, LootConditionConsumingBuilder<LootPool.Builder> {
      private final List<LootPoolEntry> entries = Lists.newArrayList();
      private final List<LootCondition> conditions = Lists.newArrayList();
      private final List<LootFunction> functions = Lists.newArrayList();
      private LootTableRange rolls = new UniformLootTableRange(1.0F);
      private UniformLootTableRange bonusRollsRange = new UniformLootTableRange(0.0F, 0.0F);

      public Builder() {
      }

      public LootPool.Builder rolls(LootTableRange rolls) {
         this.rolls = rolls;
         return this;
      }

      public LootPool.Builder getThis() {
         return this;
      }

      public LootPool.Builder with(LootPoolEntry.Builder<?> entry) {
         this.entries.add(entry.build());
         return this;
      }

      public LootPool.Builder conditionally(LootCondition.Builder _snowman) {
         this.conditions.add(_snowman.build());
         return this;
      }

      public LootPool.Builder apply(LootFunction.Builder _snowman) {
         this.functions.add(_snowman.build());
         return this;
      }

      public LootPool build() {
         if (this.rolls == null) {
            throw new IllegalArgumentException("Rolls not set");
         } else {
            return new LootPool(
               this.entries.toArray(new LootPoolEntry[0]),
               this.conditions.toArray(new LootCondition[0]),
               this.functions.toArray(new LootFunction[0]),
               this.rolls,
               this.bonusRollsRange
            );
         }
      }
   }

   public static class Serializer implements JsonDeserializer<LootPool>, JsonSerializer<LootPool> {
      public Serializer() {
      }

      public LootPool deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "loot pool");
         LootPoolEntry[] _snowmanxxxx = JsonHelper.deserialize(_snowmanxxx, "entries", _snowman, LootPoolEntry[].class);
         LootCondition[] _snowmanxxxxx = JsonHelper.deserialize(_snowmanxxx, "conditions", new LootCondition[0], _snowman, LootCondition[].class);
         LootFunction[] _snowmanxxxxxx = JsonHelper.deserialize(_snowmanxxx, "functions", new LootFunction[0], _snowman, LootFunction[].class);
         LootTableRange _snowmanxxxxxxx = LootTableRanges.fromJson(_snowmanxxx.get("rolls"), _snowman);
         UniformLootTableRange _snowmanxxxxxxxx = JsonHelper.deserialize(_snowmanxxx, "bonus_rolls", new UniformLootTableRange(0.0F, 0.0F), _snowman, UniformLootTableRange.class);
         return new LootPool(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      public JsonElement serialize(LootPool _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         _snowmanxxx.add("rolls", LootTableRanges.toJson(_snowman.rolls, _snowman));
         _snowmanxxx.add("entries", _snowman.serialize(_snowman.entries));
         if (_snowman.bonusRolls.getMinValue() != 0.0F && _snowman.bonusRolls.getMaxValue() != 0.0F) {
            _snowmanxxx.add("bonus_rolls", _snowman.serialize(_snowman.bonusRolls));
         }

         if (!ArrayUtils.isEmpty(_snowman.conditions)) {
            _snowmanxxx.add("conditions", _snowman.serialize(_snowman.conditions));
         }

         if (!ArrayUtils.isEmpty(_snowman.functions)) {
            _snowmanxxx.add("functions", _snowman.serialize(_snowman.functions));
         }

         return _snowmanxxx;
      }
   }
}
