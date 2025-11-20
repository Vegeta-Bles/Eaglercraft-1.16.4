/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtil;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class TippedArrowRecipe
extends SpecialCraftingRecipe {
    public TippedArrowRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        if (craftingInventory.getWidth() != 3 || craftingInventory.getHeight() != 3) {
            return false;
        }
        for (int i = 0; i < craftingInventory.getWidth(); ++i) {
            for (_snowman = 0; _snowman < craftingInventory.getHeight(); ++_snowman) {
                ItemStack itemStack = craftingInventory.getStack(i + _snowman * craftingInventory.getWidth());
                if (itemStack.isEmpty()) {
                    return false;
                }
                Item _snowman2 = itemStack.getItem();
                if (!(i == 1 && _snowman == 1 ? _snowman2 != Items.LINGERING_POTION : _snowman2 != Items.ARROW)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = craftingInventory.getStack(1 + craftingInventory.getWidth());
        if (itemStack.getItem() != Items.LINGERING_POTION) {
            return ItemStack.EMPTY;
        }
        _snowman = new ItemStack(Items.TIPPED_ARROW, 8);
        PotionUtil.setPotion(_snowman, PotionUtil.getPotion(itemStack));
        PotionUtil.setCustomPotionEffects(_snowman, PotionUtil.getCustomPotionEffects(itemStack));
        return _snowman;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.TIPPED_ARROW;
    }
}

