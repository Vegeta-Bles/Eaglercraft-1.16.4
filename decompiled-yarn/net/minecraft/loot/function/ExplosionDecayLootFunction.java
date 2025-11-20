package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

public class ExplosionDecayLootFunction extends ConditionalLootFunction {
   private ExplosionDecayLootFunction(LootCondition[] conditions) {
      super(conditions);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.EXPLOSION_DECAY;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Float _snowman = context.get(LootContextParameters.EXPLOSION_RADIUS);
      if (_snowman != null) {
         Random _snowmanx = context.getRandom();
         float _snowmanxx = 1.0F / _snowman;
         int _snowmanxxx = stack.getCount();
         int _snowmanxxxx = 0;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            if (_snowmanx.nextFloat() <= _snowmanxx) {
               _snowmanxxxx++;
            }
         }

         stack.setCount(_snowmanxxxx);
      }

      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder() {
      return builder(ExplosionDecayLootFunction::new);
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<ExplosionDecayLootFunction> {
      public Serializer() {
      }

      public ExplosionDecayLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         return new ExplosionDecayLootFunction(_snowman);
      }
   }
}
