/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.WritableBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WrittenBookItem
extends Item {
    public WrittenBookItem(Item.Settings settings) {
        super(settings);
    }

    public static boolean isValid(@Nullable CompoundTag tag) {
        if (!WritableBookItem.isValid(tag)) {
            return false;
        }
        if (!tag.contains("title", 8)) {
            return false;
        }
        String string = tag.getString("title");
        if (string.length() > 32) {
            return false;
        }
        return tag.contains("author", 8);
    }

    public static int getGeneration(ItemStack stack) {
        return stack.getTag().getInt("generation");
    }

    public static int getPageCount(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null ? compoundTag.getList("pages", 8).size() : 0;
    }

    @Override
    public Text getName(ItemStack stack) {
        String string;
        if (stack.hasTag() && !ChatUtil.isEmpty(string = (_snowman = stack.getTag()).getString("title"))) {
            return new LiteralText(string);
        }
        return super.getName(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasTag()) {
            CompoundTag compoundTag = stack.getTag();
            String _snowman2 = compoundTag.getString("author");
            if (!ChatUtil.isEmpty(_snowman2)) {
                tooltip.add(new TranslatableText("book.byAuthor", _snowman2).formatted(Formatting.GRAY));
            }
            tooltip.add(new TranslatableText("book.generation." + compoundTag.getInt("generation")).formatted(Formatting.GRAY));
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState _snowman2 = world.getBlockState(_snowman = context.getBlockPos());
        if (_snowman2.isOf(Blocks.LECTERN)) {
            return LecternBlock.putBookIfAbsent(world, _snowman, _snowman2, context.getStack()) ? ActionResult.success(world.isClient) : ActionResult.PASS;
        }
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.openEditBookScreen(itemStack, hand);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public static boolean resolve(ItemStack book, @Nullable ServerCommandSource commandSource, @Nullable PlayerEntity player) {
        CompoundTag compoundTag = book.getTag();
        if (compoundTag == null || compoundTag.getBoolean("resolved")) {
            return false;
        }
        compoundTag.putBoolean("resolved", true);
        if (!WrittenBookItem.isValid(compoundTag)) {
            return false;
        }
        ListTag _snowman2 = compoundTag.getList("pages", 8);
        for (int i = 0; i < _snowman2.size(); ++i) {
            MutableText mutableText;
            String string = _snowman2.getString(i);
            try {
                mutableText = Text.Serializer.fromLenientJson(string);
                mutableText = Texts.parse(commandSource, mutableText, player, 0);
            }
            catch (Exception exception) {
                mutableText = new LiteralText(string);
            }
            _snowman2.set(i, StringTag.of(Text.Serializer.toJson(mutableText)));
        }
        compoundTag.put("pages", _snowman2);
        return true;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}

