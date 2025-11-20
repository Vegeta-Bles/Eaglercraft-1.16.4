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
         float f = 1.0F - this.durabilityRange.nextFloat(context.getRandom());
         stack.setDamage(MathHelper.floor(f * (float)stack.getMaxDamage()));
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

      public void toJson(JsonObject jsonObject, SetDamageLootFunction arg, JsonSerializationContext jsonSerializationContext) {
         super.toJson(jsonObject, arg, jsonSerializationContext);
         jsonObject.add("damage", jsonSerializationContext.serialize(arg.durabilityRange));
      }

      public SetDamageLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         return new SetDamageLootFunction(args, JsonHelper.deserialize(jsonObject, "damage", jsonDeserializationContext, UniformLootTableRange.class));
      }
   }
}
