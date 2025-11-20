/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MapCloningRecipe
extends SpecialCraftingRecipe {
    public MapCloningRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (_snowman = 0; _snowman < craftingInventory.size(); ++_snowman) {
            ItemStack itemStack = craftingInventory.getStack(_snowman);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == Items.FILLED_MAP) {
                if (!_snowman2.isEmpty()) {
                    return false;
                }
                _snowman2 = itemStack;
                continue;
            }
            if (itemStack.getItem() == Items.MAP) {
                ++n;
                continue;
            }
            return false;
        }
        return !_snowman2.isEmpty() && n > 0;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        int n = 0;
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (_snowman = 0; _snowman < craftingInventory.size(); ++_snowman) {
            ItemStack itemStack = craftingInventory.getStack(_snowman);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == Items.FILLED_MAP) {
                if (!_snowman2.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                _snowman2 = itemStack;
                continue;
            }
            if (itemStack.getItem() == Items.MAP) {
                ++n;
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (_snowman2.isEmpty() || n < 1) {
            return ItemStack.EMPTY;
        }
        ItemStack _snowman3 = _snowman2.copy();
        _snowman3.setCount(n + 1);
        return _snowman3;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.MAP_CLONING;
    }
}

