/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.apache.logging.log4j.util.Supplier
 */
package net.minecraft.client.recipebook;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.RecipeBookGroup;
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

public class ClientRecipeBook
extends RecipeBook {
    private static final Logger field_25622 = LogManager.getLogger();
    private Map<RecipeBookGroup, List<RecipeResultCollection>> resultsByGroup = ImmutableMap.of();
    private List<RecipeResultCollection> orderedResults = ImmutableList.of();

    public void reload(Iterable<Recipe<?>> iterable) {
        Map<RecipeBookGroup, List<List<Recipe<?>>>> map = ClientRecipeBook.method_30283(iterable);
        HashMap _snowman2 = Maps.newHashMap();
        ImmutableList.Builder _snowman3 = ImmutableList.builder();
        map.forEach((recipeBookGroup, list) -> {
            List cfr_ignored_0 = (List)_snowman2.put(recipeBookGroup, list.stream().map(RecipeResultCollection::new).peek(arg_0 -> ((ImmutableList.Builder)_snowman3).add(arg_0)).collect(ImmutableList.toImmutableList()));
        });
        RecipeBookGroup.field_25783.forEach((recipeBookGroup2, list) -> {
            List cfr_ignored_0 = (List)_snowman2.put(recipeBookGroup2, list.stream().flatMap(recipeBookGroup -> ((List)_snowman2.getOrDefault(recipeBookGroup, ImmutableList.of())).stream()).collect(ImmutableList.toImmutableList()));
        });
        this.resultsByGroup = ImmutableMap.copyOf((Map)_snowman2);
        this.orderedResults = _snowman3.build();
    }

    private static Map<RecipeBookGroup, List<List<Recipe<?>>>> method_30283(Iterable<Recipe<?>> iterable) {
        HashMap hashMap = Maps.newHashMap();
        HashBasedTable _snowman2 = HashBasedTable.create();
        for (Recipe<?> recipe : iterable) {
            if (recipe.isIgnoredInRecipeBook()) continue;
            RecipeBookGroup recipeBookGroup2 = ClientRecipeBook.getGroupForRecipe(recipe);
            String _snowman3 = recipe.getGroup();
            if (_snowman3.isEmpty()) {
                hashMap.computeIfAbsent(recipeBookGroup2, recipeBookGroup -> Lists.newArrayList()).add(ImmutableList.of(recipe));
                continue;
            }
            List _snowman4 = (List)_snowman2.get((Object)recipeBookGroup2, (Object)_snowman3);
            if (_snowman4 == null) {
                _snowman4 = Lists.newArrayList();
                _snowman2.put((Object)recipeBookGroup2, (Object)_snowman3, (Object)_snowman4);
                hashMap.computeIfAbsent(recipeBookGroup2, recipeBookGroup -> Lists.newArrayList()).add(_snowman4);
            }
            _snowman4.add(recipe);
        }
        return hashMap;
    }

    private static RecipeBookGroup getGroupForRecipe(Recipe<?> recipe) {
        RecipeType<?> recipeType = recipe.getType();
        if (recipeType == RecipeType.CRAFTING) {
            ItemStack itemStack = recipe.getOutput();
            ItemGroup _snowman2 = itemStack.getItem().getGroup();
            if (_snowman2 == ItemGroup.BUILDING_BLOCKS) {
                return RecipeBookGroup.CRAFTING_BUILDING_BLOCKS;
            }
            if (_snowman2 == ItemGroup.TOOLS || _snowman2 == ItemGroup.COMBAT) {
                return RecipeBookGroup.CRAFTING_EQUIPMENT;
            }
            if (_snowman2 == ItemGroup.REDSTONE) {
                return RecipeBookGroup.CRAFTING_REDSTONE;
            }
            return RecipeBookGroup.CRAFTING_MISC;
        }
        if (recipeType == RecipeType.SMELTING) {
            if (recipe.getOutput().getItem().isFood()) {
                return RecipeBookGroup.FURNACE_FOOD;
            }
            if (recipe.getOutput().getItem() instanceof BlockItem) {
                return RecipeBookGroup.FURNACE_BLOCKS;
            }
            return RecipeBookGroup.FURNACE_MISC;
        }
        if (recipeType == RecipeType.BLASTING) {
            if (recipe.getOutput().getItem() instanceof BlockItem) {
                return RecipeBookGroup.BLAST_FURNACE_BLOCKS;
            }
            return RecipeBookGroup.BLAST_FURNACE_MISC;
        }
        if (recipeType == RecipeType.SMOKING) {
            return RecipeBookGroup.SMOKER_FOOD;
        }
        if (recipeType == RecipeType.STONECUTTING) {
            return RecipeBookGroup.STONECUTTER;
        }
        if (recipeType == RecipeType.CAMPFIRE_COOKING) {
            return RecipeBookGroup.CAMPFIRE;
        }
        if (recipeType == RecipeType.SMITHING) {
            return RecipeBookGroup.SMITHING;
        }
        Supplier[] supplierArray = new Supplier[2];
        supplierArray[0] = () -> Registry.RECIPE_TYPE.getId(recipe.getType());
        supplierArray[1] = recipe::getId;
        field_25622.warn("Unknown recipe category: {}/{}", supplierArray);
        return RecipeBookGroup.UNKNOWN;
    }

    public List<RecipeResultCollection> getOrderedResults() {
        return this.orderedResults;
    }

    public List<RecipeResultCollection> getResultsForGroup(RecipeBookGroup category) {
        return this.resultsByGroup.getOrDefault((Object)category, Collections.emptyList());
    }
}

