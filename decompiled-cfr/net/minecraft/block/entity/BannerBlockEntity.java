/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.BannerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Nameable;

public class BannerBlockEntity
extends BlockEntity
implements Nameable {
    @Nullable
    private Text customName;
    @Nullable
    private DyeColor baseColor = DyeColor.WHITE;
    @Nullable
    private ListTag patternListTag;
    private boolean patternListTagRead;
    @Nullable
    private List<Pair<BannerPattern, DyeColor>> patterns;

    public BannerBlockEntity() {
        super(BlockEntityType.BANNER);
    }

    public BannerBlockEntity(DyeColor baseColor) {
        this();
        this.baseColor = baseColor;
    }

    @Nullable
    public static ListTag getPatternListTag(ItemStack stack) {
        ListTag listTag = null;
        CompoundTag _snowman2 = stack.getSubTag("BlockEntityTag");
        if (_snowman2 != null && _snowman2.contains("Patterns", 9)) {
            listTag = _snowman2.getList("Patterns", 10).copy();
        }
        return listTag;
    }

    public void readFrom(ItemStack stack, DyeColor baseColor) {
        this.patternListTag = BannerBlockEntity.getPatternListTag(stack);
        this.baseColor = baseColor;
        this.patterns = null;
        this.patternListTagRead = true;
        this.customName = stack.hasCustomName() ? stack.getName() : null;
    }

    @Override
    public Text getName() {
        if (this.customName != null) {
            return this.customName;
        }
        return new TranslatableText("block.minecraft.banner");
    }

    @Override
    @Nullable
    public Text getCustomName() {
        return this.customName;
    }

    public void setCustomName(Text customName) {
        this.customName = customName;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (this.patternListTag != null) {
            tag.put("Patterns", this.patternListTag);
        }
        if (this.customName != null) {
            tag.putString("CustomName", Text.Serializer.toJson(this.customName));
        }
        return tag;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        if (tag.contains("CustomName", 8)) {
            this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
        }
        this.baseColor = this.hasWorld() ? ((AbstractBannerBlock)this.getCachedState().getBlock()).getColor() : null;
        this.patternListTag = tag.getList("Patterns", 10);
        this.patterns = null;
        this.patternListTagRead = true;
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 6, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    public static int getPatternCount(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("BlockEntityTag");
        if (compoundTag != null && compoundTag.contains("Patterns")) {
            return compoundTag.getList("Patterns", 10).size();
        }
        return 0;
    }

    public List<Pair<BannerPattern, DyeColor>> getPatterns() {
        if (this.patterns == null && this.patternListTagRead) {
            this.patterns = BannerBlockEntity.method_24280(this.getColorForState(this::getCachedState), this.patternListTag);
        }
        return this.patterns;
    }

    public static List<Pair<BannerPattern, DyeColor>> method_24280(DyeColor dyeColor, @Nullable ListTag listTag) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(Pair.of((Object)((Object)BannerPattern.BASE), (Object)dyeColor));
        if (listTag != null) {
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                BannerPattern _snowman2 = BannerPattern.byId(compoundTag.getString("Pattern"));
                if (_snowman2 == null) continue;
                int _snowman3 = compoundTag.getInt("Color");
                arrayList.add(Pair.of((Object)((Object)_snowman2), (Object)DyeColor.byId(_snowman3)));
            }
        }
        return arrayList;
    }

    public static void loadFromItemStack(ItemStack stack) {
        CompoundTag compoundTag = stack.getSubTag("BlockEntityTag");
        if (compoundTag == null || !compoundTag.contains("Patterns", 9)) {
            return;
        }
        ListTag _snowman2 = compoundTag.getList("Patterns", 10);
        if (_snowman2.isEmpty()) {
            return;
        }
        _snowman2.remove(_snowman2.size() - 1);
        if (_snowman2.isEmpty()) {
            stack.removeSubTag("BlockEntityTag");
        }
    }

    public ItemStack getPickStack(BlockState state) {
        ItemStack itemStack = new ItemStack(BannerBlock.getForColor(this.getColorForState(() -> state)));
        if (this.patternListTag != null && !this.patternListTag.isEmpty()) {
            itemStack.getOrCreateSubTag("BlockEntityTag").put("Patterns", this.patternListTag.copy());
        }
        if (this.customName != null) {
            itemStack.setCustomName(this.customName);
        }
        return itemStack;
    }

    public DyeColor getColorForState(Supplier<BlockState> supplier) {
        if (this.baseColor == null) {
            this.baseColor = ((AbstractBannerBlock)supplier.get().getBlock()).getColor();
        }
        return this.baseColor;
    }
}

