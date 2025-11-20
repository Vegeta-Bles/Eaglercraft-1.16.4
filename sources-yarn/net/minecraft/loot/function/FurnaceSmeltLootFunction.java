package net.minecraft.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Optional;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FurnaceSmeltLootFunction extends ConditionalLootFunction {
   private static final Logger LOGGER = LogManager.getLogger();

   private FurnaceSmeltLootFunction(LootCondition[] conditions) {
      super(conditions);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.FURNACE_SMELT;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (stack.isEmpty()) {
         return stack;
      } else {
         Optional<SmeltingRecipe> optional = context.getWorld()
            .getRecipeManager()
            .getFirstMatch(RecipeType.SMELTING, new SimpleInventory(stack), context.getWorld());
         if (optional.isPresent()) {
            ItemStack lv = optional.get().getOutput();
            if (!lv.isEmpty()) {
               ItemStack lv2 = lv.copy();
               lv2.setCount(stack.getCount());
               return lv2;
            }
         }

         LOGGER.warn("Couldn't smelt {} because there is no smelting recipe", stack);
         return stack;
      }
   }

   public static ConditionalLootFunction.Builder<?> builder() {
      return builder(FurnaceSmeltLootFunction::new);
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<FurnaceSmeltLootFunction> {
      public Serializer() {
      }

      public FurnaceSmeltLootFunction fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootCondition[] args) {
         return new FurnaceSmeltLootFunction(args);
      }
   }
}
