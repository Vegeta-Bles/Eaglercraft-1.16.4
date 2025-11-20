/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public interface Recipe<C extends Inventory> {
    public boolean matches(C var1, World var2);

    public ItemStack craft(C var1);

    public boolean fits(int var1, int var2);

    public ItemStack getOutput();

    default public DefaultedList<ItemStack> getRemainingStacks(C c) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(c.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultedList.size(); ++i) {
            Item item = c.getStack(i).getItem();
            if (!item.hasRecipeRemainder()) continue;
            defaultedList.set(i, new ItemStack(item.getRecipeRemainder()));
        }
        return defaultedList;
    }

    default public DefaultedList<Ingredient> getPreviewInputs() {
        return DefaultedList.of();
    }

    default public boolean isIgnoredInRecipeBook() {
        return false;
    }

    default public String getGroup() {
        return "";
    }

    default public ItemStack getRecipeKindIcon() {
        return new ItemStack(Blocks.CRAFTING_TABLE);
    }

    public Identifier getId();

    public RecipeSerializer<?> getSerializer();

    public RecipeType<?> getType();
}

