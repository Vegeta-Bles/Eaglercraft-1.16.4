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
         Optional<SmeltingRecipe> _snowman = context.getWorld().getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(stack), context.getWorld());
         if (_snowman.isPresent()) {
            ItemStack _snowmanx = _snowman.get().getOutput();
            if (!_snowmanx.isEmpty()) {
               ItemStack _snowmanxx = _snowmanx.copy();
               _snowmanxx.setCount(stack.getCount());
               return _snowmanxx;
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

      public FurnaceSmeltLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         return new FurnaceSmeltLootFunction(_snowman);
      }
   }
}
