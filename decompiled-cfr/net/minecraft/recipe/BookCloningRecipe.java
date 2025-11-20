/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BookCloningRecipe
extends SpecialCraftingRecipe {
    public BookCloningRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (_snowman = 0; _snowman < craftingInventory.size(); ++_snowman) {
            ItemStack itemStack = craftingInventory.getStack(_snowman);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == Items.WRITTEN_BOOK) {
                if (!_snowman2.isEmpty()) {
                    return false;
                }
                _snowman2 = itemStack;
                continue;
            }
            if (itemStack.getItem() == Items.WRITABLE_BOOK) {
                ++n;
                continue;
            }
            return false;
        }
        return !_snowman2.isEmpty() && _snowman2.hasTag() && n > 0;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        Object _snowman4;
        int n = 0;
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (_snowman = 0; _snowman < craftingInventory.size(); ++_snowman) {
            _snowman4 = craftingInventory.getStack(_snowman);
            if (((ItemStack)_snowman4).isEmpty()) continue;
            if (((ItemStack)_snowman4).getItem() == Items.WRITTEN_BOOK) {
                if (!_snowman2.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                _snowman2 = _snowman4;
                continue;
            }
            if (((ItemStack)_snowman4).getItem() == Items.WRITABLE_BOOK) {
                ++n;
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (_snowman2.isEmpty() || !_snowman2.hasTag() || n < 1 || WrittenBookItem.getGeneration(_snowman2) >= 2) {
            return ItemStack.EMPTY;
        }
        ItemStack _snowman3 = new ItemStack(Items.WRITTEN_BOOK, n);
        _snowman4 = _snowman2.getTag().copy();
        ((CompoundTag)_snowman4).putInt("generation", WrittenBookItem.getGeneration(_snowman2) + 1);
        _snowman3.setTag((CompoundTag)_snowman4);
        return _snowman3;
    }

    @Override
    public DefaultedList<ItemStack> getRemainingStacks(CraftingInventory craftingInventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(craftingInventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(itemStack.getItem().getRecipeRemainder()));
                continue;
            }
            if (!(itemStack.getItem() instanceof WrittenBookItem)) continue;
            _snowman = itemStack.copy();
            _snowman.setCount(1);
            defaultedList.set(i, _snowman);
            break;
        }
        return defaultedList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.BOOK_CLONING;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }
}

