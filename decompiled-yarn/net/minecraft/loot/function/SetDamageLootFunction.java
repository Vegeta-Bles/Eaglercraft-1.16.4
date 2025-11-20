package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDamageLootFunction extends ConditionalLootFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   private final UniformLootTableRange durabilityRange;

   private SetDamageLootFunction(LootCondition[] contents, UniformLootTableRange durabilityRange) {
      super(contents);
      this.durabilityRange = durabilityRange;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_DAMAGE;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.isDamageable()) {
         float _snowman = 1.0F - this.durabilityRange.nextFloat(context.getRandom());
         stack.setDamage(MathHelper.floor(_snowman * (float)stack.getMaxDamage()));
      } else {
         LOGGER.warn("Couldn't set damage of loot item {}", stack);
      }

      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(UniformLootTableRange durabilityRange) {
      return builder(conditions -> new SetDamageLootFunction(conditions, durabilityRange));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetDamageLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetDamageLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.add("damage", _snowman.serialize(_snowman.durabilityRange));
      }

      public SetDamageLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         return new SetDamageLootFunction(_snowman, JsonHelper.deserialize(_snowman, "damage", _snowman, UniformLootTableRange.class));
      }
   }
}
