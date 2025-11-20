/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.recipe;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ArmorDyeRecipe
extends SpecialCraftingRecipe {
    public ArmorDyeRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ArrayList _snowman2 = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() instanceof DyeableItem) {
                if (!itemStack.isEmpty()) {
                    return false;
                }
                itemStack = itemStack2;
                continue;
            }
            if (itemStack2.getItem() instanceof DyeItem) {
                _snowman2.add(itemStack2);
                continue;
            }
            return false;
        }
        return !itemStack.isEmpty() && !_snowman2.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ArrayList arrayList = Lists.newArrayList();
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            Item _snowman3 = itemStack.getItem();
            if (_snowman3 instanceof DyeableItem) {
                if (!_snowman2.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                _snowman2 = itemStack.copy();
                continue;
            }
            if (_snowman3 instanceof DyeItem) {
                arrayList.add((DyeItem)_snowman3);
                continue;
            }
            return ItemStack.EMPTY;
        }
        if (_snowman2.isEmpty() || arrayList.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return DyeableItem.blendAndSetColor(_snowman2, arrayList);
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.ARMOR_DYE;
    }
}

