/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class BannerItem
extends WallStandingBlockItem {
    public BannerItem(Block block, Block block2, Item.Settings settings) {
        super(block, block2, settings);
        Validate.isInstanceOf(AbstractBannerBlock.class, (Object)block);
        Validate.isInstanceOf(AbstractBannerBlock.class, (Object)block2);
    }

    public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
        CompoundTag compoundTag = stack.getSubTag("BlockEntityTag");
        if (compoundTag == null || !compoundTag.contains("Patterns")) {
            return;
        }
        ListTag _snowman2 = compoundTag.getList("Patterns", 10);
        for (int i = 0; i < _snowman2.size() && i < 6; ++i) {
            CompoundTag compoundTag2 = _snowman2.getCompound(i);
            DyeColor _snowman3 = DyeColor.byId(compoundTag2.getInt("Color"));
            BannerPattern _snowman4 = BannerPattern.byId(compoundTag2.getString("Pattern"));
            if (_snowman4 == null) continue;
            tooltip.add(new TranslatableText("block.minecraft.banner." + _snowman4.getName() + '.' + _snowman3.getName()).formatted(Formatting.GRAY));
        }
    }

    public DyeColor getColor() {
        return ((AbstractBannerBlock)this.getBlock()).getColor();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        BannerItem.appendBannerTooltip(stack, tooltip);
    }
}

