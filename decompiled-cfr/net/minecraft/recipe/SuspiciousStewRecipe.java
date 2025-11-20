/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.recipe;

import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class SuspiciousStewRecipe
extends SpecialCraftingRecipe {
    public SuspiciousStewRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        bl3 = false;
        bl2 = false;
        bl4 = false;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (itemStack.getItem() == Blocks.BROWN_MUSHROOM.asItem() && !bl2) {
                boolean bl2 = true;
                continue;
            }
            if (itemStack.getItem() == Blocks.RED_MUSHROOM.asItem() && !bl3) {
                boolean bl3 = true;
                continue;
            }
            if (itemStack.getItem().isIn(ItemTags.SMALL_FLOWERS) && !bl) {
                bl = true;
                continue;
            }
            if (itemStack.getItem() == Items.BOWL && !bl4) {
                boolean bl4 = true;
                continue;
            }
            return false;
        }
        return bl && bl2 && bl3 && bl4;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        Object object;
        ItemStack _snowman2 = ItemStack.EMPTY;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            object = craftingInventory.getStack(i);
            if (((ItemStack)object).isEmpty() || !((ItemStack)object).getItem().isIn(ItemTags.SMALL_FLOWERS)) continue;
            _snowman2 = object;
            break;
        }
        ItemStack itemStack = new ItemStack(Items.SUSPICIOUS_STEW, 1);
        if (_snowman2.getItem() instanceof BlockItem && ((BlockItem)_snowman2.getItem()).getBlock() instanceof FlowerBlock) {
            object = (FlowerBlock)((BlockItem)_snowman2.getItem()).getBlock();
            StatusEffect _snowman3 = ((FlowerBlock)object).getEffectInStew();
            SuspiciousStewItem.addEffectToStew(itemStack, _snowman3, ((FlowerBlock)object).getEffectInStewDuration());
        }
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.SUSPICIOUS_STEW;
    }
}

