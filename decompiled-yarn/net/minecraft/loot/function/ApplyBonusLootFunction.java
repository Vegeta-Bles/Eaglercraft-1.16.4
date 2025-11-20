package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class ApplyBonusLootFunction extends ConditionalLootFunction {
   private static final Map<Identifier, ApplyBonusLootFunction.FormulaFactory> FACTORIES = Maps.newHashMap();
   private final Enchantment enchantment;
   private final ApplyBonusLootFunction.Formula formula;

   private ApplyBonusLootFunction(LootCondition[] conditions, Enchantment enchantment, ApplyBonusLootFunction.Formula formula) {
      super(conditions);
      this.enchantment = enchantment;
      this.formula = formula;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.APPLY_BONUS;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.TOOL);
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      ItemStack _snowman = context.get(LootContextParameters.TOOL);
      if (_snowman != null) {
         int _snowmanx = EnchantmentHelper.getLevel(this.enchantment, _snowman);
         int _snowmanxx = this.formula.getValue(context.getRandom(), stack.getCount(), _snowmanx);
         stack.setCount(_snowmanxx);
      }

      return stack;
   }

   public static ConditionalLootFunction.Builder<?> binomialWithBonusCount(Enchantment enchantment, float probability, int extra) {
      return builder(conditions -> new ApplyBonusLootFunction(conditions, enchantment, new ApplyBonusLootFunction.BinomialWithBonusCount(extra, probability)));
   }

   public static ConditionalLootFunction.Builder<?> oreDrops(Enchantment enchantment) {
      return builder(conditions -> new ApplyBonusLootFunction(conditions, enchantment, new ApplyBonusLootFunction.OreDrops()));
   }

   public static ConditionalLootFunction.Builder<?> uniformBonusCount(Enchantment enchantment) {
      return builder(conditions -> new ApplyBonusLootFunction(conditions, enchantment, new ApplyBonusLootFunction.UniformBonusCount(1)));
   }

   public static ConditionalLootFunction.Builder<?> uniformBonusCount(Enchantment enchantment, int bonusMultiplier) {
      return builder(conditions -> new ApplyBonusLootFunction(conditions, enchantment, new ApplyBonusLootFunction.UniformBonusCount(bonusMultiplier)));
   }

   static {
      FACTORIES.put(ApplyBonusLootFunction.BinomialWithBonusCount.ID, ApplyBonusLootFunction.BinomialWithBonusCount::fromJson);
      FACTORIES.put(ApplyBonusLootFunction.OreDrops.ID, ApplyBonusLootFunction.OreDrops::fromJson);
      FACTORIES.put(ApplyBonusLootFunction.UniformBonusCount.ID, ApplyBonusLootFunction.UniformBonusCount::fromJson);
   }

   static final class BinomialWithBonusCount implements ApplyBonusLootFunction.Formula {
      public static final Identifier ID = new Identifier("binomial_with_bonus_count");
      private final int extra;
      private final float probability;

      public BinomialWithBonusCount(int extra, float probability) {
         this.extra = extra;
         this.probability = probability;
      }

      @Override
      public int getValue(Random random, int initialCount, int enchantmentLevel) {
         for (int _snowman = 0; _snowman < enchantmentLevel + this.extra; _snowman++) {
            if (random.nextFloat() < this.probability) {
               initialCount++;
            }
         }

         return initialCount;
      }

      @Override
      public void toJson(JsonObject json, JsonSerializationContext context) {
         json.addProperty("extra", this.extra);
         json.addProperty("probability", this.probability);
      }

      public static ApplyBonusLootFunction.Formula fromJson(JsonObject json, JsonDeserializationContext context) {
         int _snowman = JsonHelper.getInt(json, "extra");
         float _snowmanx = JsonHelper.getFloat(json, "probability");
         return new ApplyBonusLootFunction.BinomialWithBonusCount(_snowman, _snowmanx);
      }

      @Override
      public Identifier getId() {
         return ID;
      }
   }

   interface Formula {
      int getValue(Random random, int initialCount, int enchantmentLevel);

      void toJson(JsonObject json, JsonSerializationContext context);

      Identifier getId();
   }

   interface FormulaFactory {
      ApplyBonusLootFunction.Formula deserialize(JsonObject functionJson, JsonDeserializationContext context);
   }

   static final class OreDrops implements ApplyBonusLootFunction.Formula {
      public static final Identifier ID = new Identifier("ore_drops");

      private OreDrops() {
      }

      @Override
      public int getValue(Random random, int initialCount, int enchantmentLevel) {
         if (enchantmentLevel > 0) {
            int _snowman = random.nextInt(enchantmentLevel + 2) - 1;
            if (_snowman < 0) {
               _snowman = 0;
            }

            return initialCount * (_snowman + 1);
         } else {
            return initialCount;
         }
      }

      @Override
      public void toJson(JsonObject json, JsonSerializationContext context) {
      }

      public static ApplyBonusLootFunction.Formula fromJson(JsonObject json, JsonDeserializationContext context) {
         return new ApplyBonusLootFunction.OreDrops();
      }

      @Override
      public Identifier getId() {
         return ID;
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<ApplyBonusLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, ApplyBonusLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("enchantment", Registry.ENCHANTMENT.getId(_snowman.enchantment).toString());
         _snowman.addProperty("formula", _snowman.formula.getId().toString());
         JsonObject _snowmanxxx = new JsonObject();
         _snowman.formula.toJson(_snowmanxxx, _snowman);
         if (_snowmanxxx.size() > 0) {
            _snowman.add("parameters", _snowmanxxx);
         }
      }

      public ApplyBonusLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         Identifier _snowmanxxx = new Identifier(JsonHelper.getString(_snowman, "enchantment"));
         Enchantment _snowmanxxxx = Registry.ENCHANTMENT.getOrEmpty(_snowmanxxx).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + _snowman));
         Identifier _snowmanxxxxx = new Identifier(JsonHelper.getString(_snowman, "formula"));
         ApplyBonusLootFunction.FormulaFactory _snowmanxxxxxx = ApplyBonusLootFunction.FACTORIES.get(_snowmanxxxxx);
         if (_snowmanxxxxxx == null) {
            throw new JsonParseException("Invalid formula id: " + _snowmanxxxxx);
         } else {
            ApplyBonusLootFunction.Formula _snowmanxxxxxxx;
            if (_snowman.has("parameters")) {
               _snowmanxxxxxxx = _snowmanxxxxxx.deserialize(JsonHelper.getObject(_snowman, "parameters"), _snowman);
            } else {
               _snowmanxxxxxxx = _snowmanxxxxxx.deserialize(new JsonObject(), _snowman);
            }

            return new ApplyBonusLootFunction(_snowman, _snowmanxxxx, _snowmanxxxxxxx);
         }
      }
   }

   static final class UniformBonusCount implements ApplyBonusLootFunction.Formula {
      public static final Identifier ID = new Identifier("uniform_bonus_count");
      private final int bonusMultiplier;

      public UniformBonusCount(int bonusMultiplier) {
         this.bonusMultiplier = bonusMultiplier;
      }

      @Override
      public int getValue(Random random, int initialCount, int enchantmentLevel) {
         return initialCount + random.nextInt(this.bonusMultiplier * enchantmentLevel + 1);
      }

      @Override
      public void toJson(JsonObject json, JsonSerializationContext context) {
         json.addProperty("bonusMultiplier", this.bonusMultiplier);
      }

      public static ApplyBonusLootFunction.Formula fromJson(JsonObject json, JsonDeserializationContext context) {
         int _snowman = JsonHelper.getInt(json, "bonusMultiplier");
         return new ApplyBonusLootFunction.UniformBonusCount(_snowman);
      }

      @Override
      public Identifier getId() {
         return ID;
      }
   }
}
