/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 */
package net.minecraft.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.FireworkItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;

public class FireworkStarRecipe
extends SpecialCraftingRecipe {
    private static final Ingredient TYPE_MODIFIER = Ingredient.ofItems(Items.FIRE_CHARGE, Items.FEATHER, Items.GOLD_NUGGET, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD);
    private static final Ingredient TRAIL_MODIFIER = Ingredient.ofItems(Items.DIAMOND);
    private static final Ingredient FLICKER_MODIFIER = Ingredient.ofItems(Items.GLOWSTONE_DUST);
    private static final Map<Item, FireworkItem.Type> TYPE_MODIFIER_MAP = Util.make(Maps.newHashMap(), hashMap -> {
        hashMap.put(Items.FIRE_CHARGE, FireworkItem.Type.LARGE_BALL);
        hashMap.put(Items.FEATHER, FireworkItem.Type.BURST);
        hashMap.put(Items.GOLD_NUGGET, FireworkItem.Type.STAR);
        hashMap.put(Items.SKELETON_SKULL, FireworkItem.Type.CREEPER);
        hashMap.put(Items.WITHER_SKELETON_SKULL, FireworkItem.Type.CREEPER);
        hashMap.put(Items.CREEPER_HEAD, FireworkItem.Type.CREEPER);
        hashMap.put(Items.PLAYER_HEAD, FireworkItem.Type.CREEPER);
        hashMap.put(Items.DRAGON_HEAD, FireworkItem.Type.CREEPER);
        hashMap.put(Items.ZOMBIE_HEAD, FireworkItem.Type.CREEPER);
    });
    private static final Ingredient GUNPOWDER = Ingredient.ofItems(Items.GUNPOWDER);

    public FireworkStarRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        boolean bl = false;
        bl5 = false;
        bl2 = false;
        bl4 = false;
        bl3 = false;
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            if (TYPE_MODIFIER.test(itemStack)) {
                if (bl2) {
                    return false;
                }
                boolean bl2 = true;
                continue;
            }
            if (FLICKER_MODIFIER.test(itemStack)) {
                if (bl3) {
                    return false;
                }
                boolean bl3 = true;
                continue;
            }
            if (TRAIL_MODIFIER.test(itemStack)) {
                if (bl4) {
                    return false;
                }
                boolean bl4 = true;
                continue;
            }
            if (GUNPOWDER.test(itemStack)) {
                if (bl) {
                    return false;
                }
                bl = true;
                continue;
            }
            if (itemStack.getItem() instanceof DyeItem) {
                boolean bl5 = true;
                continue;
            }
            return false;
        }
        return bl && bl5;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_STAR);
        CompoundTag _snowman2 = itemStack.getOrCreateSubTag("Explosion");
        FireworkItem.Type _snowman3 = FireworkItem.Type.SMALL_BALL;
        ArrayList _snowman4 = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (TYPE_MODIFIER.test(itemStack2)) {
                _snowman3 = TYPE_MODIFIER_MAP.get(itemStack2.getItem());
                continue;
            }
            if (FLICKER_MODIFIER.test(itemStack2)) {
                _snowman2.putBoolean("Flicker", true);
                continue;
            }
            if (TRAIL_MODIFIER.test(itemStack2)) {
                _snowman2.putBoolean("Trail", true);
                continue;
            }
            if (!(itemStack2.getItem() instanceof DyeItem)) continue;
            _snowman4.add(((DyeItem)itemStack2.getItem()).getColor().getFireworkColor());
        }
        _snowman2.putIntArray("Colors", _snowman4);
        _snowman2.putByte("Type", (byte)_snowman3.getId());
        return itemStack;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getOutput() {
        return new ItemStack(Items.FIREWORK_STAR);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.FIREWORK_STAR;
    }
}

