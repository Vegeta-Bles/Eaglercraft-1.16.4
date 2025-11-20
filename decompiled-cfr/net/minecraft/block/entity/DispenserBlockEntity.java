/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class DispenserBlockEntity
extends LootableContainerBlockEntity {
    private static final Random RANDOM = new Random();
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);

    protected DispenserBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public DispenserBlockEntity() {
        this(BlockEntityType.DISPENSER);
    }

    @Override
    public int size() {
        return 9;
    }

    public int chooseNonEmptySlot() {
        this.checkLootInteraction(null);
        int n = -1;
        _snowman = 1;
        for (_snowman = 0; _snowman < this.inventory.size(); ++_snowman) {
            if (this.inventory.get(_snowman).isEmpty() || RANDOM.nextInt(_snowman++) != 0) continue;
            n = _snowman;
        }
        return n;
    }

    public int addToFirstFreeSlot(ItemStack stack) {
        for (int i = 0; i < this.inventory.size(); ++i) {
            if (!this.inventory.get(i).isEmpty()) continue;
            this.setStack(i, stack);
            return i;
        }
        return -1;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.dispenser");
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(tag)) {
            Inventories.fromTag(tag, this.inventory);
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (!this.serializeLootTable(tag)) {
            Inventories.toTag(tag, this.inventory);
        }
        return tag;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
    }
}

