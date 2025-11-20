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
import java.util.HashMap;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RepairItemRecipe
extends SpecialCraftingRecipe {
    public RepairItemRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            arrayList.add(itemStack);
            if (arrayList.size() <= 1) continue;
            _snowman = (ItemStack)arrayList.get(0);
            if (itemStack.getItem() == _snowman.getItem() && _snowman.getCount() == 1 && itemStack.getCount() == 1 && _snowman.getItem().isDamageable()) continue;
            return false;
        }
        return arrayList.size() == 2;
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        Object _snowman2;
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            arrayList.add(itemStack);
            if (arrayList.size() <= 1) continue;
            _snowman2 = (ItemStack)arrayList.get(0);
            if (itemStack.getItem() == ((ItemStack)_snowman2).getItem() && ((ItemStack)_snowman2).getCount() == 1 && itemStack.getCount() == 1 && ((ItemStack)_snowman2).getItem().isDamageable()) continue;
            return ItemStack.EMPTY;
        }
        if (arrayList.size() == 2) {
            _snowman = (ItemStack)arrayList.get(0);
            itemStack = (ItemStack)arrayList.get(1);
            if (_snowman.getItem() == itemStack.getItem() && _snowman.getCount() == 1 && itemStack.getCount() == 1 && _snowman.getItem().isDamageable()) {
                _snowman2 = _snowman.getItem();
                int _snowman3 = ((Item)_snowman2).getMaxDamage() - _snowman.getDamage();
                int _snowman4 = ((Item)_snowman2).getMaxDamage() - itemStack.getDamage();
                int _snowman5 = _snowman3 + _snowman4 + ((Item)_snowman2).getMaxDamage() * 5 / 100;
                int _snowman6 = ((Item)_snowman2).getMaxDamage() - _snowman5;
                if (_snowman6 < 0) {
                    _snowman6 = 0;
                }
                _snowman = new ItemStack(_snowman.getItem());
                _snowman.setDamage(_snowman6);
                HashMap _snowman7 = Maps.newHashMap();
                Map<Enchantment, Integer> _snowman8 = EnchantmentHelper.get(_snowman);
                Map<Enchantment, Integer> _snowman9 = EnchantmentHelper.get(itemStack);
                Registry.ENCHANTMENT.stream().filter(Enchantment::isCursed).forEach(enchantment -> {
                    int n = Math.max(_snowman8.getOrDefault(enchantment, 0), _snowman9.getOrDefault(enchantment, 0));
                    if (n > 0) {
                        _snowman7.put(enchantment, n);
                    }
                });
                if (!_snowman7.isEmpty()) {
                    EnchantmentHelper.set(_snowman7, _snowman);
                }
                return _snowman;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializer.REPAIR_ITEM;
    }
}

