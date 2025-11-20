/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ShulkerBoxColoringRecipe
extends SpecialCraftingRecipe {
    public ShulkerBoxColoringRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        int n = 0;
        _snowman = 0;
        for (_snowman = 0; _snowman < craftingInventory.size(); ++_snowman) {
            ItemStack itemStack = craftingInventory.getStack(_snowman);
            if (itemStack.isEmpty()) continue;
            if (Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock) {
                ++n;
            } else if (itemStack.getItem() instanceof DyeItem) {
                ++_snowman;
            } else {
                return false;
            }
            if (_snowman <= 1 && n <= 1) continue;
            return false;
        }
        return n == 1 && _snowman == 1;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        DyeItem _snowman2 = (DyeItem)Items.WHITE_DYE;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            Item _snowman3 = itemStack2.getItem();
            if (Block.getBlockFromItem(_snowman3) instanceof ShulkerBoxBlock) {
                itemStack = itemStack2;
                continue;
            }
            if (!(_snowman3 instanceof DyeItem)) continue;
            _snowman2 = (DyeItem)_snowman3;
        }
        _snowman = ShulkerBoxBlock.getItemStack(_snowman2.getColor());
        if (itemStack.hasTag()) {
            _snowman.setTag(itemStack.getTag().copy());
        }
        return _snowman;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SHULKER_BOX;
    }
}

