/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ShieldDecorationRecipe
extends SpecialCraftingRecipe {
    public ShieldDecorationRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        _snowman = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() instanceof BannerItem) {
                if (!_snowman.isEmpty()) {
                    return false;
                }
                _snowman = itemStack2;
                continue;
            }
            if (itemStack2.getItem() == Items.SHIELD) {
                if (!itemStack.isEmpty()) {
                    return false;
                }
                if (itemStack2.getSubTag("BlockEntityTag") != null) {
                    return false;
                }
                itemStack = itemStack2;
                continue;
            }
            return false;
        }
        return !itemStack.isEmpty() && !_snowman.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack _snowman2;
        Object _snowman3;
        ItemStack itemStack = ItemStack.EMPTY;
        _snowman2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            _snowman3 = craftingInventory.getStack(i);
            if (((ItemStack)_snowman3).isEmpty()) continue;
            if (((ItemStack)_snowman3).getItem() instanceof BannerItem) {
                itemStack = _snowman3;
                continue;
            }
            if (((ItemStack)_snowman3).getItem() != Items.SHIELD) continue;
            _snowman2 = ((ItemStack)_snowman3).copy();
        }
        if (_snowman2.isEmpty()) {
            return _snowman2;
        }
        CompoundTag compoundTag = itemStack.getSubTag("BlockEntityTag");
        _snowman3 = compoundTag == null ? new CompoundTag() : compoundTag.copy();
        ((CompoundTag)_snowman3).putInt("Base", ((BannerItem)itemStack.getItem()).getColor().getId());
        _snowman2.putSubTag("BlockEntityTag", (Tag)_snowman3);
        return _snowman2;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SHIELD_DECORATION;
    }
}

