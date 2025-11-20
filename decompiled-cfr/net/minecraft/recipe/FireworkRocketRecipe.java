/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FireworkRocketRecipe
extends SpecialCraftingRecipe {
    private static final Ingredient PAPER = Ingredient.ofItems(Items.PAPER);
    private static final Ingredient DURATION_MODIFIER = Ingredient.ofItems(Items.GUNPOWDER);
    private static final Ingredient FIREWORK_STAR = Ingredient.ofItems(Items.FIREWORK_STAR);

    public FireworkRocketRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        int _snowman2 = 0;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (PAPER.test(itemStack)) {
                if (bl) {
                    return false;
                }
                bl = true;
                continue;
            }
            if (!(DURATION_MODIFIER.test(itemStack) ? ++_snowman2 > 3 : !FIREWORK_STAR.test(itemStack))) continue;
            return false;
        }
        return bl && _snowman2 >= 1;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET, 3);
        CompoundTag _snowman2 = itemStack.getOrCreateSubTag("Fireworks");
        ListTag _snowman3 = new ListTag();
        int _snowman4 = 0;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (DURATION_MODIFIER.test(itemStack2)) {
                ++_snowman4;
                continue;
            }
            if (!FIREWORK_STAR.test(itemStack2) || (_snowman = itemStack2.getSubTag("Explosion")) == null) continue;
            _snowman3.add(_snowman);
        }
        _snowman2.putByte("Flight", (byte)_snowman4);
        if (!_snowman3.isEmpty()) {
            _snowman2.put("Explosions", _snowman3);
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(Items.FIREWORK_ROCKET);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.FIREWORK_ROCKET;
    }
}

