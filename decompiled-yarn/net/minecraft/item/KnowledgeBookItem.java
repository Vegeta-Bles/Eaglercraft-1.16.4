package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowledgeBookItem extends Item {
   private static final Logger LOGGER = LogManager.getLogger();

   public KnowledgeBookItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      CompoundTag _snowmanx = _snowman.getTag();
      if (!user.abilities.creativeMode) {
         user.setStackInHand(hand, ItemStack.EMPTY);
      }

      if (_snowmanx != null && _snowmanx.contains("Recipes", 9)) {
         if (!world.isClient) {
            ListTag _snowmanxx = _snowmanx.getList("Recipes", 8);
            List<Recipe<?>> _snowmanxxx = Lists.newArrayList();
            RecipeManager _snowmanxxxx = world.getServer().getRecipeManager();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx.size(); _snowmanxxxxx++) {
               String _snowmanxxxxxx = _snowmanxx.getString(_snowmanxxxxx);
               Optional<? extends Recipe<?>> _snowmanxxxxxxx = _snowmanxxxx.get(new Identifier(_snowmanxxxxxx));
               if (!_snowmanxxxxxxx.isPresent()) {
                  LOGGER.error("Invalid recipe: {}", _snowmanxxxxxx);
                  return TypedActionResult.fail(_snowman);
               }

               _snowmanxxx.add((Recipe<?>)_snowmanxxxxxxx.get());
            }

            user.unlockRecipes(_snowmanxxx);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
         }

         return TypedActionResult.success(_snowman, world.isClient());
      } else {
         LOGGER.error("Tag not valid: {}", _snowmanx);
         return TypedActionResult.fail(_snowman);
      }
   }
}
