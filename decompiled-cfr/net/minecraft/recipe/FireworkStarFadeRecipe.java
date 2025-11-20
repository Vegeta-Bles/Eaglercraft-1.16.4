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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireworkStarFadeRecipe
extends SpecialCraftingRecipe {
    private static final Ingredient INPUT_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);

    public FireworkStarFadeRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl;
        boolean bl2 = false;
        bl = false;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() instanceof DyeItem) {
                bl2 = true;
                continue;
            }
            if (INPUT_STAR.test(itemStack)) {
                if (bl) {
                    return false;
                }
                bl = true;
                continue;
            }
            return false;
        }
        return bl && bl2;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ArrayList arrayList = Lists.newArrayList();
        ItemStack _snowman2 = null;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            Item _snowman3 = itemStack.getItem();
            if (_snowman3 instanceof DyeItem) {
                arrayList.add(((DyeItem)_snowman3).getColor().getFireworkColor());
                continue;
            }
            if (!INPUT_STAR.test(itemStack)) continue;
            _snowman2 = itemStack.copy();
            _snowman2.setCount(1);
        }
        if (_snowman2 == null || arrayList.isEmpty()) {
            return ItemStack.EMPTY;
        }
        _snowman2.getOrCreateSubTag("Explosion").putIntArray("FadeColors", arrayList);
        return _snowman2;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.FIREWORK_STAR_FADE;
    }
}

