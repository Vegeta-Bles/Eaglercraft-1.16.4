/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BannerDuplicateRecipe
extends SpecialCraftingRecipe {
    public BannerDuplicateRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        DyeColor dyeColor = null;
        ItemStack _snowman2 = null;
        ItemStack _snowman3 = null;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            Item _snowman4 = itemStack.getItem();
            if (!(_snowman4 instanceof BannerItem)) continue;
            BannerItem _snowman5 = (BannerItem)_snowman4;
            if (dyeColor == null) {
                dyeColor = _snowman5.getColor();
            } else if (dyeColor != _snowman5.getColor()) {
                return false;
            }
            int _snowman6 = BannerBlockEntity.getPatternCount(itemStack);
            if (_snowman6 > 6) {
                return false;
            }
            if (_snowman6 > 0) {
                if (_snowman2 == null) {
                    _snowman2 = itemStack;
                    continue;
                }
                return false;
            }
            if (_snowman3 == null) {
                _snowman3 = itemStack;
                continue;
            }
            return false;
        }
        return _snowman2 != null && _snowman3 != null;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty() || (_snowman = BannerBlockEntity.getPatternCount(itemStack)) <= 0 || _snowman > 6) continue;
            _snowman = itemStack.copy();
            _snowman.setCount(1);
            return _snowman;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory craftingInventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(craftingInventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder()));
                continue;
            }
            if (!itemStack.hasTag() || BannerBlockEntity.getPatternCount(itemStack) <= 0) continue;
            _snowman = itemStack.copy();
            _snowman.setCount(1);
            defaultedList.set(i, _snowman);
        }
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.BANNER_DUPLICATE;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }
}

