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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class ClientRecipeBook extends RecipeBook {
   private static final Logger field_25622 = LogManager.getLogger();
   private Map<RecipeBookGroup, List<RecipeResultCollection>> resultsByGroup = ImmutableMap.of();
   private List<RecipeResultCollection> orderedResults = ImmutableList.of();

   public ClientRecipeBook() {
   }

   public void reload(Iterable<Recipe<?>> iterable) {
      Map<RecipeBookGroup, List<List<Recipe<?>>>> map = method_30283(iterable);
      Map<RecipeBookGroup, List<RecipeResultCollection>> map2 = Maps.newHashMap();
      Builder<RecipeResultCollection> builder = ImmutableList.builder();
      map.forEach((arg, list) -> {
         List var10000 = map2.put(arg, list.stream().map(RecipeResultCollection::new).peek(builder::add).collect(ImmutableList.toImmutableList()));
      });
      RecipeBookGroup.field_25783
         .forEach(
            (arg, list) -> {
               List var10000 = map2.put(
                  arg, list.stream().flatMap(argx -> map2.getOrDefault(argx, ImmutableList.of()).stream()).collect(ImmutableList.toImmutableList())
               );
            }
         );
      this.resultsByGroup = ImmutableMap.copyOf(map2);
      this.orderedResults = builder.build();
   }

   private static Map<RecipeBookGroup, List<List<Recipe<?>>>> method_30283(Iterable<Recipe<?>> iterable) {
      Map<RecipeBookGroup, List<List<Recipe<?>>>> map = Maps.newHashMap();
      Table<RecipeBookGroup, String, List<Recipe<?>>> table = HashBasedTable.create();

      for (Recipe<?> lv : iterable) {
         if (!lv.isIgnoredInRecipeBook()) {
            RecipeBookGroup lv2 = getGroupForRecipe(lv);
            String string = lv.getGroup();
            if (string.isEmpty()) {
               map.computeIfAbsent(lv2, arg -> Lists.newArrayList()).add(ImmutableList.of(lv));
            } else {
               List<Recipe<?>> list = (List<Recipe<?>>)table.get(lv2, string);
               if (list == null) {
                  list = Lists.newArrayList();
                  table.put(lv2, string, list);
                  map.computeIfAbsent(lv2, arg -> Lists.newArrayList()).add(list);
               }

               list.add(lv);
            }
         }
      }

      return map;
   }

   private static RecipeBookGroup getGroupForRecipe(Recipe<?> arg) {
      RecipeType<?> lv = arg.getType();
      if (lv == RecipeType.CRAFTING) {
         ItemStack lv2 = arg.getOutput();
         ItemGroup lv3 = lv2.getItem().getGroup();
         if (lv3 == ItemGroup.BUILDING_BLOCKS) {
            return RecipeBookGroup.CRAFTING_BUILDING_BLOCKS;
         } else if (lv3 == ItemGroup.TOOLS || lv3 == ItemGroup.COMBAT) {
            return RecipeBookGroup.CRAFTING_EQUIPMENT;
         } else {
            return lv3 == ItemGroup.REDSTONE ? RecipeBookGroup.CRAFTING_REDSTONE : RecipeBookGroup.CRAFTING_MISC;
         }
      } else if (lv == RecipeType.SMELTING) {
         if (arg.getOutput().getItem().isFood()) {
            return RecipeBookGroup.FURNACE_FOOD;
         } else {
            return arg.getOutput().getItem() instanceof BlockItem ? RecipeBookGroup.FURNACE_BLOCKS : RecipeBookGroup.FURNACE_MISC;
         }
      } else if (lv == RecipeType.BLASTING) {
         return arg.getOutput().getItem() instanceof BlockItem ? RecipeBookGroup.BLAST_FURNACE_BLOCKS : RecipeBookGroup.BLAST_FURNACE_MISC;
      } else if (lv == RecipeType.SMOKING) {
         return RecipeBookGroup.SMOKER_FOOD;
      } else if (lv == RecipeType.STONECUTTING) {
         return RecipeBookGroup.STONECUTTER;
      } else if (lv == RecipeType.CAMPFIRE_COOKING) {
         return RecipeBookGroup.CAMPFIRE;
      } else if (lv == RecipeType.SMITHING) {
         return RecipeBookGroup.SMITHING;
      } else {
         field_25622.warn("Unknown recipe category: {}/{}", new Supplier[]{() -> Registry.RECIPE_TYPE.getId(arg.getType()), arg::getId});
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
