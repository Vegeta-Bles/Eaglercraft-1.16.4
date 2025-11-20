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
import net.minecraft.item.FireworkItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FireworkChargeItem
extends Item {
    public FireworkChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag compoundTag = stack.getSubTag("Explosion");
        if (compoundTag != null) {
            FireworkChargeItem.appendFireworkTooltip(compoundTag, tooltip);
        }
    }

    public static void appendFireworkTooltip(CompoundTag tag, List<Text> tooltip) {
        FireworkItem.Type type = FireworkItem.Type.byId(tag.getByte("Type"));
        tooltip.add(new TranslatableText("item.minecraft.firework_star.shape." + type.getName()).formatted(Formatting.GRAY));
        int[] _snowman2 = tag.getIntArray("Colors");
        if (_snowman2.length > 0) {
            tooltip.add(FireworkChargeItem.appendColors(new LiteralText("").formatted(Formatting.GRAY), _snowman2));
        }
        if ((_snowman = tag.getIntArray("FadeColors")).length > 0) {
            tooltip.add(FireworkChargeItem.appendColors(new TranslatableText("item.minecraft.firework_star.fade_to").append(" ").formatted(Formatting.GRAY), _snowman));
        }
        if (tag.getBoolean("Trail")) {
            tooltip.add(new TranslatableText("item.minecraft.firework_star.trail").formatted(Formatting.GRAY));
        }
        if (tag.getBoolean("Flicker")) {
            tooltip.add(new TranslatableText("item.minecraft.firework_star.flicker").formatted(Formatting.GRAY));
        }
    }

    private static Text appendColors(MutableText line, int[] colors) {
        for (int i = 0; i < colors.length; ++i) {
            if (i > 0) {
                line.append(", ");
            }
            line.append(FireworkChargeItem.getColorText(colors[i]));
        }
        return line;
    }

    private static Text getColorText(int color) {
        DyeColor dyeColor = DyeColor.byFireworkColor(color);
        if (dyeColor == null) {
            return new TranslatableText("item.minecraft.firework_star.custom_color");
        }
        return new TranslatableText("item.minecraft.firework_star." + dyeColor.getName());
    }
}

