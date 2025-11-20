/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class CartographyTableScreenHandler
extends ScreenHandler {
    private final ScreenHandlerContext context;
    private long lastTakeResultTime;
    public final Inventory inventory = new SimpleInventory(this, 2){
        final /* synthetic */ CartographyTableScreenHandler field_17298;
        {
            this.field_17298 = cartographyTableScreenHandler;
            super(n);
        }

        public void markDirty() {
            this.field_17298.onContentChanged(this);
            super.markDirty();
        }
    };
    private final CraftingResultInventory resultSlot = new CraftingResultInventory(this){
        final /* synthetic */ CartographyTableScreenHandler field_19273;
        {
            this.field_19273 = cartographyTableScreenHandler;
        }

        public void markDirty() {
            this.field_19273.onContentChanged(this);
            super.markDirty();
        }
    };

    public CartographyTableScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public CartographyTableScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.CARTOGRAPHY_TABLE, syncId);
        int n;
        this.context = context;
        this.addSlot(new Slot(this, this.inventory, 0, 15, 15){
            final /* synthetic */ CartographyTableScreenHandler field_17299;
            {
                this.field_17299 = cartographyTableScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.FILLED_MAP;
            }
        });
        this.addSlot(new Slot(this, this.inventory, 1, 15, 52){
            final /* synthetic */ CartographyTableScreenHandler field_17300;
            {
                this.field_17300 = cartographyTableScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                Item item = stack.getItem();
                return item == Items.PAPER || item == Items.MAP || item == Items.GLASS_PANE;
            }
        });
        this.addSlot(new Slot(this, this.resultSlot, 2, 145, 39, context){
            final /* synthetic */ ScreenHandlerContext field_17301;
            final /* synthetic */ CartographyTableScreenHandler field_17303;
            {
                this.field_17303 = cartographyTableScreenHandler;
                this.field_17301 = screenHandlerContext;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                ((Slot)this.field_17303.slots.get(0)).takeStack(1);
                ((Slot)this.field_17303.slots.get(1)).takeStack(1);
                stack.getItem().onCraft(stack, player.world, player);
                this.field_17301.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (CartographyTableScreenHandler.method_21826(this.field_17303) != l) {
                        world.playSound(null, (BlockPos)blockPos, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        CartographyTableScreenHandler.method_21827(this.field_17303, l);
                    }
                });
                return super.onTakeItem(player, stack);
            }
        });
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(inventory, _snowman + n * 9 + 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(inventory, n, 8 + n * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return CartographyTableScreenHandler.canUse(this.context, player, Blocks.CARTOGRAPHY_TABLE);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inventory.getStack(0);
        _snowman = this.inventory.getStack(1);
        _snowman = this.resultSlot.getStack(2);
        if (!_snowman.isEmpty() && (itemStack.isEmpty() || _snowman.isEmpty())) {
            this.resultSlot.removeStack(2);
        } else if (!itemStack.isEmpty() && !_snowman.isEmpty()) {
            this.updateResult(itemStack, _snowman, _snowman);
        }
    }

    private void updateResult(ItemStack map, ItemStack item, ItemStack oldResult) {
        this.context.run((world, blockPos) -> {
            ItemStack itemStack4;
            Item item = item.getItem();
            MapState _snowman2 = FilledMapItem.getMapState(map, world);
            if (_snowman2 == null) {
                return;
            }
            if (item == Items.PAPER && !_snowman2.locked && _snowman2.scale < 4) {
                itemStack4 = map.copy();
                itemStack4.setCount(1);
                itemStack4.getOrCreateTag().putInt("map_scale_direction", 1);
                this.sendContentUpdates();
            } else if (item == Items.GLASS_PANE && !_snowman2.locked) {
                itemStack4 = map.copy();
                itemStack4.setCount(1);
                itemStack4.getOrCreateTag().putBoolean("map_to_lock", true);
                this.sendContentUpdates();
            } else if (item == Items.MAP) {
                itemStack4 = map.copy();
                itemStack4.setCount(2);
                this.sendContentUpdates();
            } else {
                this.resultSlot.removeStack(2);
                this.sendContentUpdates();
                return;
            }
            if (!ItemStack.areEqual(itemStack4, oldResult)) {
                this.resultSlot.setStack(2, itemStack4);
                this.sendContentUpdates();
            }
        });
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.resultSlot && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack _snowman3 = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            ItemStack itemStack;
            itemStack = _snowman = _snowman2.getStack();
            Item item = itemStack.getItem();
            _snowman3 = itemStack.copy();
            if (index == 2) {
                item.onCraft(itemStack, player.world, player);
                if (!this.insertItem(itemStack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(itemStack, _snowman3);
            } else if (index == 1 || index == 0 ? !this.insertItem(itemStack, 3, 39, false) : (item == Items.FILLED_MAP ? !this.insertItem(itemStack, 0, 1, false) : (item == Items.PAPER || item == Items.MAP || item == Items.GLASS_PANE ? !this.insertItem(itemStack, 1, 2, false) : (index >= 3 && index < 30 ? !this.insertItem(itemStack, 30, 39, false) : index >= 30 && index < 39 && !this.insertItem(itemStack, 3, 30, false))))) {
                return ItemStack.EMPTY;
            }
            if (itemStack.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            }
            _snowman2.markDirty();
            if (itemStack.getCount() == _snowman3.getCount()) {
                return ItemStack.EMPTY;
            }
            _snowman2.onTakeItem(player, itemStack);
            this.sendContentUpdates();
        }
        return _snowman3;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.resultSlot.removeStack(2);
        this.context.run((world, blockPos) -> this.dropInventory(player, playerEntity.world, this.inventory));
    }

    static /* synthetic */ long method_21826(CartographyTableScreenHandler cartographyTableScreenHandler) {
        return cartographyTableScreenHandler.lastTakeResultTime;
    }

    static /* synthetic */ long method_21827(CartographyTableScreenHandler cartographyTableScreenHandler, long l) {
        cartographyTableScreenHandler.lastTakeResultTime = l;
        return cartographyTableScreenHandler.lastTakeResultTime;
    }
}

