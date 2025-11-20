/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EnchantedBookItem
extends Item {
    public EnchantedBookItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    public static ListTag getEnchantmentTag(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            return compoundTag.getList("StoredEnchantments", 10);
        }
        return new ListTag();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        ItemStack.appendEnchantments(tooltip, EnchantedBookItem.getEnchantmentTag(stack));
    }

    public static void addEnchantment(ItemStack stack, EnchantmentLevelEntry entry) {
        ListTag listTag = EnchantedBookItem.getEnchantmentTag(stack);
        boolean _snowman2 = true;
        Identifier _snowman3 = Registry.ENCHANTMENT.getId(entry.enchantment);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = listTag.getCompound(i);
            Identifier _snowman4 = Identifier.tryParse(compoundTag.getString("id"));
            if (_snowman4 == null || !_snowman4.equals(_snowman3)) continue;
            if (compoundTag.getInt("lvl") < entry.level) {
                compoundTag.putShort("lvl", (short)entry.level);
            }
            _snowman2 = false;
            break;
        }
        if (_snowman2) {
            _snowman = new CompoundTag();
            _snowman.putString("id", String.valueOf(_snowman3));
            _snowman.putShort("lvl", (short)entry.level);
            listTag.add(_snowman);
        }
        stack.getOrCreateTag().put("StoredEnchantments", listTag);
    }

    public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
        ItemStack itemStack = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantedBookItem.addEnchantment(itemStack, info);
        return itemStack;
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        block4: {
            block3: {
                if (group != ItemGroup.SEARCH) break block3;
                for (Enchantment enchantment : Registry.ENCHANTMENT) {
                    if (enchantment.type == null) continue;
                    for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); ++i) {
                        stacks.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, i)));
                    }
                }
                break block4;
            }
            if (group.getEnchantments().length == 0) break block4;
            for (Enchantment enchantment : Registry.ENCHANTMENT) {
                if (!group.containsEnchantments(enchantment.type)) continue;
                stacks.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(enchantment, enchantment.getMaxLevel())));
            }
        }
    }
}

