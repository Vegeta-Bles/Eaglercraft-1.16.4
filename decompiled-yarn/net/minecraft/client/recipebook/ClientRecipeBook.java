package net.minecraft.client.recipebook;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class ClientRecipeBook extends RecipeBook {
   private static final Logger field_25622 = LogManager.getLogger();
   private Map<RecipeBookGroup, List<RecipeResultCollection>> resultsByGroup = ImmutableMap.of();
   private List<RecipeResultCollection> orderedResults = ImmutableList.of();

   public ClientRecipeBook() {
   }

   public void reload(Iterable<Recipe<?>> _snowman) {
      Map<RecipeBookGroup, List<List<Recipe<?>>>> _snowmanx = method_30283(_snowman);
      Map<RecipeBookGroup, List<RecipeResultCollection>> _snowmanxx = Maps.newHashMap();
      Builder<RecipeResultCollection> _snowmanxxx = ImmutableList.builder();
      _snowmanx.forEach((_snowmanxxxx, _snowmanxxxxx) -> {
         List var10000 = _snowman.put(_snowmanxxxx, _snowmanxxxxx.stream().map(RecipeResultCollection::new).peek(_snowman::add).collect(ImmutableList.toImmutableList()));
      });
      RecipeBookGroup.field_25783
         .forEach(
            (_snowmanxxxx, _snowmanxxxxx) -> {
               List var10000 = _snowman.put(
                  _snowmanxxxx, _snowmanxxxxx.stream().flatMap(_snowmanxxxxxx -> _snowman.getOrDefault(_snowmanxxxxxx, ImmutableList.of()).stream()).collect(ImmutableList.toImmutableList())
               );
            }
         );
      this.resultsByGroup = ImmutableMap.copyOf(_snowmanxx);
      this.orderedResults = _snowmanxxx.build();
   }

   private static Map<RecipeBookGroup, List<List<Recipe<?>>>> method_30283(Iterable<Recipe<?>> _snowman) {
      Map<RecipeBookGroup, List<List<Recipe<?>>>> _snowmanx = Maps.newHashMap();
      Table<RecipeBookGroup, String, List<Recipe<?>>> _snowmanxx = HashBasedTable.create();

      for (Recipe<?> _snowmanxxx : _snowman) {
         if (!_snowmanxxx.isIgnoredInRecipeBook()) {
            RecipeBookGroup _snowmanxxxx = getGroupForRecipe(_snowmanxxx);
            String _snowmanxxxxx = _snowmanxxx.getGroup();
            if (_snowmanxxxxx.isEmpty()) {
               _snowmanx.computeIfAbsent(_snowmanxxxx, _snowmanxxxxxx -> Lists.newArrayList()).add(ImmutableList.of(_snowmanxxx));
            } else {
               List<Recipe<?>> _snowmanxxxxxx = (List<Recipe<?>>)_snowmanxx.get(_snowmanxxxx, _snowmanxxxxx);
               if (_snowmanxxxxxx == null) {
                  _snowmanxxxxxx = Lists.newArrayList();
                  _snowmanxx.put(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  _snowmanx.computeIfAbsent(_snowmanxxxx, _snowmanxxxxxxx -> Lists.newArrayList()).add(_snowmanxxxxxx);
               }

               _snowmanxxxxxx.add(_snowmanxxx);
            }
         }
      }

      return _snowmanx;
   }

   private static RecipeBookGroup getGroupForRecipe(Recipe<?> _snowman) {
      RecipeType<?> _snowmanx = _snowman.getType();
      if (_snowmanx == RecipeType.CRAFTING) {
         ItemStack _snowmanxx = _snowman.getOutput();
         ItemGroup _snowmanxxx = _snowmanxx.getItem().getGroup();
         if (_snowmanxxx == ItemGroup.BUILDING_BLOCKS) {
            return RecipeBookGroup.CRAFTING_BUILDING_BLOCKS;
         } else if (_snowmanxxx == ItemGroup.TOOLS || _snowmanxxx == ItemGroup.COMBAT) {
            return RecipeBookGroup.CRAFTING_EQUIPMENT;
         } else {
            return _snowmanxxx == ItemGroup.REDSTONE ? RecipeBookGroup.CRAFTING_REDSTONE : RecipeBookGroup.CRAFTING_MISC;
         }
      } else if (_snowmanx == RecipeType.SMELTING) {
         if (_snowman.getOutput().getItem().isFood()) {
            return RecipeBookGroup.FURNACE_FOOD;
         } else {
            return _snowman.getOutput().getItem() instanceof BlockItem ? RecipeBookGroup.FURNACE_BLOCKS : RecipeBookGroup.FURNACE_MISC;
         }
      } else if (_snowmanx == RecipeType.BLASTING) {
         return _snowman.getOutput().getItem() instanceof BlockItem ? RecipeBookGroup.BLAST_FURNACE_BLOCKS : RecipeBookGroup.BLAST_FURNACE_MISC;
      } else if (_snowmanx == RecipeType.SMOKING) {
         return RecipeBookGroup.SMOKER_FOOD;
      } else if (_snowmanx == RecipeType.STONECUTTING) {
         return RecipeBookGroup.STONECUTTER;
      } else if (_snowmanx == RecipeType.CAMPFIRE_COOKING) {
         return RecipeBookGroup.CAMPFIRE;
      } else if (_snowmanx == RecipeType.SMITHING) {
         return RecipeBookGroup.SMITHING;
      } else {
         field_25622.warn("Unknown recipe category: {}/{}", new Supplier[]{() -> Registry.RECIPE_TYPE.getId(_snowman.getType()), _snowman::getId});
         return RecipeBookGroup.UNKNOWN;
      }
   }

   public List<RecipeResultCollection> getOrderedResults() {
      return this.orderedResults;
   }

   public List<RecipeResultCollection> getResultsForGroup(RecipeBookGroup category) {
      return this.resultsByGroup.getOrDefault(category, Collections.emptyList());
   }
}
