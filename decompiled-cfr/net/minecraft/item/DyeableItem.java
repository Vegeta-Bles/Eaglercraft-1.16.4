/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.List;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

public interface DyeableItem {
    default public boolean hasColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("display");
        return compoundTag != null && compoundTag.contains("color", 99);
    }

    default public int getColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("display");
        if (compoundTag != null && compoundTag.contains("color", 99)) {
            return compoundTag.getInt("color");
        }
        return 10511680;
    }

    default public void removeColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("display");
        if (compoundTag != null && compoundTag.contains("color")) {
            compoundTag.remove("color");
        }
    }

    default public void setColor(ItemStack stack, int color) {
        stack.getOrCreateSubTag("display").putInt("color", color);
    }

    public static ItemStack blendAndSetColor(ItemStack stack, List<DyeItem> colors) {
        int _snowman13;
        float _snowman9;
        ItemStack itemStack = ItemStack.EMPTY;
        int[] _snowman2 = new int[3];
        int _snowman3 = 0;
        int _snowman4 = 0;
        DyeableItem _snowman5 = null;
        Item _snowman6 = stack.getItem();
        if (_snowman6 instanceof DyeableItem) {
            _snowman5 = (DyeableItem)((Object)_snowman6);
            itemStack = stack.copy();
            itemStack.setCount(1);
            if (_snowman5.hasColor(stack)) {
                int n = _snowman5.getColor(itemStack);
                float _snowman7 = (float)(n >> 16 & 0xFF) / 255.0f;
                float _snowman8 = (float)(n >> 8 & 0xFF) / 255.0f;
                _snowman9 = (float)(n & 0xFF) / 255.0f;
                _snowman3 = (int)((float)_snowman3 + Math.max(_snowman7, Math.max(_snowman8, _snowman9)) * 255.0f);
                _snowman2[0] = (int)((float)_snowman2[0] + _snowman7 * 255.0f);
                _snowman2[1] = (int)((float)_snowman2[1] + _snowman8 * 255.0f);
                _snowman2[2] = (int)((float)_snowman2[2] + _snowman9 * 255.0f);
                ++_snowman4;
            }
            for (DyeItem _snowman10 : colors) {
                float[] fArray = _snowman10.getColor().getColorComponents();
                int _snowman11 = (int)(fArray[0] * 255.0f);
                int _snowman12 = (int)(fArray[1] * 255.0f);
                _snowman13 = (int)(fArray[2] * 255.0f);
                _snowman3 += Math.max(_snowman11, Math.max(_snowman12, _snowman13));
                _snowman2[0] = _snowman2[0] + _snowman11;
                _snowman2[1] = _snowman2[1] + _snowman12;
                _snowman2[2] = _snowman2[2] + _snowman13;
                ++_snowman4;
            }
        }
        if (_snowman5 == null) {
            return ItemStack.EMPTY;
        }
        int n = _snowman2[0] / _snowman4;
        _snowman = _snowman2[1] / _snowman4;
        _snowman = _snowman2[2] / _snowman4;
        _snowman9 = (float)_snowman3 / (float)_snowman4;
        float _snowman14 = Math.max(n, Math.max(_snowman, _snowman));
        n = (int)((float)n * _snowman9 / _snowman14);
        _snowman = (int)((float)_snowman * _snowman9 / _snowman14);
        _snowman = (int)((float)_snowman * _snowman9 / _snowman14);
        _snowman13 = n;
        _snowman13 = (_snowman13 << 8) + _snowman;
        _snowman13 = (_snowman13 << 8) + _snowman;
        _snowman5.setColor(itemStack, _snowman13);
        return itemStack;
    }
}

