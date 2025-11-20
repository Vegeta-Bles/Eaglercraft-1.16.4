package net.minecraft.loot.function;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class SetStewEffectLootFunction extends ConditionalLootFunction {
   private final Map<StatusEffect, UniformLootTableRange> effects;

   private SetStewEffectLootFunction(LootCondition[] conditions, Map<StatusEffect, UniformLootTableRange> effects) {
      super(conditions);
      this.effects = ImmutableMap.copyOf(effects);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_STEW_EFFECT;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.getItem() == Items.SUSPICIOUS_STEW && !this.effects.isEmpty()) {
         Random _snowman = context.getRandom();
         int _snowmanx = _snowman.nextInt(this.effects.size());
         Entry<StatusEffect, UniformLootTableRange> _snowmanxx = (Entry<StatusEffect, UniformLootTableRange>)Iterables.get(this.effects.entrySet(), _snowmanx);
         StatusEffect _snowmanxxx = _snowmanxx.getKey();
         int _snowmanxxxx = _snowmanxx.getValue().next(_snowman);
         if (!_snowmanxxx.isInstant()) {
            _snowmanxxxx *= 20;
         }

         SuspiciousStewItem.addEffectToStew(stack, _snowmanxxx, _snowmanxxxx);
         return stack;
      } else {
         return stack;
      }
   }

   public static SetStewEffectLootFunction.Builder builder() {
      return new SetStewEffectLootFunction.Builder();
   }

   public static class Builder extends ConditionalLootFunction.Builder<SetStewEffectLootFunction.Builder> {
      private final Map<StatusEffect, UniformLootTableRange> map = Maps.newHashMap();

      public Builder() {
      }

      protected SetStewEffectLootFunction.Builder getThisBuilder() {
         return this;
      }

      public SetStewEffectLootFunction.Builder withEffect(StatusEffect effect, UniformLootTableRange durationRange) {
         this.map.put(effect, durationRange);
         return this;
      }

      @Override
      public LootFunction build() {
         return new SetStewEffectLootFunction(this.getConditions(), this.map);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetStewEffectLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetStewEffectLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         if (!_snowman.effects.isEmpty()) {
            JsonArray _snowmanxxx = new JsonArray();

            for (StatusEffect _snowmanxxxx : _snowman.effects.keySet()) {
               JsonObject _snowmanxxxxx = new JsonObject();
               Identifier _snowmanxxxxxx = Registry.STATUS_EFFECT.getId(_snowmanxxxx);
               if (_snowmanxxxxxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize mob effect " + _snowmanxxxx);
               }

               _snowmanxxxxx.add("type", new JsonPrimitive(_snowmanxxxxxx.toString()));
               _snowmanxxxxx.add("duration", _snowman.serialize(_snowman.effects.get(_snowmanxxxx)));
               _snowmanxxx.add(_snowmanxxxxx);
            }

            _snowman.add("effects", _snowmanxxx);
         }
      }

      public SetStewEffectLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         Map<StatusEffect, UniformLootTableRange> _snowmanxxx = Maps.newHashMap();
         if (_snowman.has("effects")) {
            for (JsonElement _snowmanxxxx : JsonHelper.getArray(_snowman, "effects")) {
               String _snowmanxxxxx = JsonHelper.getString(_snowmanxxxx.getAsJsonObject(), "type");
               StatusEffect _snowmanxxxxxx = Registry.STATUS_EFFECT
                  .getOrEmpty(new Identifier(_snowmanxxxxx))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown mob effect '" + _snowman + "'"));
               UniformLootTableRange _snowmanxxxxxxx = JsonHelper.deserialize(_snowmanxxxx.getAsJsonObject(), "duration", _snowman, UniformLootTableRange.class);
               _snowmanxxx.put(_snowmanxxxxxx, _snowmanxxxxxxx);
            }
         }

         return new SetStewEffectLootFunction(_snowman, _snowmanxxx);
      }
   }
}
