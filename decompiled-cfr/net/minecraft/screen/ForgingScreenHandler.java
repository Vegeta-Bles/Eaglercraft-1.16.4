/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.screen;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ForgingScreenHandler
extends ScreenHandler {
    protected final CraftingResultInventory output = new CraftingResultInventory();
    protected final Inventory input = new SimpleInventory(this, 2){
        final /* synthetic */ ForgingScreenHandler field_7778;
        {
            this.field_7778 = forgingScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            this.field_7778.onContentChanged(this);
        }
    };
    protected final ScreenHandlerContext context;
    protected final PlayerEntity player;

    protected abstract boolean canTakeOutput(PlayerEntity var1, boolean var2);

    protected abstract ItemStack onTakeOutput(PlayerEntity var1, ItemStack var2);

    protected abstract boolean canUse(BlockState var1);

    public ForgingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId);
        int n;
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new Slot(this.input, 0, 27, 47));
        this.addSlot(new Slot(this.input, 1, 76, 47));
        this.addSlot(new Slot(this, this.output, 2, 134, 47){
            final /* synthetic */ ForgingScreenHandler field_22483;
            {
                this.field_22483 = forgingScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public boolean canTakeItems(PlayerEntity playerEntity) {
                return this.field_22483.canTakeOutput(playerEntity, this.hasStack());
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                return this.field_22483.onTakeOutput(player, stack);
            }
        });
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n * 9 + 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 142));
        }
    }

    public abstract void updateResult();

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, (World)world, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.context.run((world, blockPos) -> {
            if (!this.canUse(world.getBlockState((BlockPos)blockPos))) {
                return false;
            }
            return player.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= 64.0;
        }, true);
    }

    protected boolean method_30025(ItemStack itemStack) {
        return false;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == 2) {
                if (!this.insertItem(_snowman, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (index == 0 || index == 1) {
                if (!this.insertItem(_snowman, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 3 && index < 39) {
                int n = _snowman = this.method_30025(itemStack) ? 1 : 0;
                if (!this.insertItem(_snowman, _snowman, 2, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (_snowman.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            } else {
                _snowman2.markDirty();
            }
            if (_snowman.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            _snowman2.onTakeItem(player, _snowman);
        }
        return itemStack;
    }
}

