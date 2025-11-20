/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 */
package net.minecraft.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class ScreenHandler {
    private final DefaultedList<ItemStack> trackedStacks = DefaultedList.of();
    public final List<Slot> slots = Lists.newArrayList();
    private final List<Property> properties = Lists.newArrayList();
    @Nullable
    private final ScreenHandlerType<?> type;
    public final int syncId;
    private short actionId;
    private int quickCraftStage = -1;
    private int quickCraftButton;
    private final Set<Slot> quickCraftSlots = Sets.newHashSet();
    private final List<ScreenHandlerListener> listeners = Lists.newArrayList();
    private final Set<PlayerEntity> restrictedPlayers = Sets.newHashSet();

    protected ScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        this.type = type;
        this.syncId = syncId;
    }

    protected static boolean canUse(ScreenHandlerContext context, PlayerEntity player, Block block) {
        return context.run((world, blockPos) -> {
            if (!world.getBlockState((BlockPos)blockPos).isOf(block)) {
                return false;
            }
            return player.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= 64.0;
        }, true);
    }

    public ScreenHandlerType<?> getType() {
        if (this.type == null) {
            throw new UnsupportedOperationException("Unable to construct this menu by type");
        }
        return this.type;
    }

    protected static void checkSize(Inventory inventory, int expectedSize) {
        int n = inventory.size();
        if (n < expectedSize) {
            throw new IllegalArgumentException("Container size " + n + " is smaller than expected " + expectedSize);
        }
    }

    protected static void checkDataCount(PropertyDelegate data, int expectedCount) {
        int n = data.size();
        if (n < expectedCount) {
            throw new IllegalArgumentException("Container data count " + n + " is smaller than expected " + expectedCount);
        }
    }

    protected Slot addSlot(Slot slot) {
        slot.id = this.slots.size();
        this.slots.add(slot);
        this.trackedStacks.add(ItemStack.EMPTY);
        return slot;
    }

    protected Property addProperty(Property property) {
        this.properties.add(property);
        return property;
    }

    protected void addProperties(PropertyDelegate propertyDelegate) {
        for (int i = 0; i < propertyDelegate.size(); ++i) {
            this.addProperty(Property.create(propertyDelegate, i));
        }
    }

    public void addListener(ScreenHandlerListener listener) {
        if (this.listeners.contains(listener)) {
            return;
        }
        this.listeners.add(listener);
        listener.onHandlerRegistered(this, this.getStacks());
        this.sendContentUpdates();
    }

    public void removeListener(ScreenHandlerListener listener) {
        this.listeners.remove(listener);
    }

    public DefaultedList<ItemStack> getStacks() {
        DefaultedList<ItemStack> defaultedList = DefaultedList.of();
        for (int i = 0; i < this.slots.size(); ++i) {
            defaultedList.add(this.slots.get(i).getStack());
        }
        return defaultedList;
    }

    public void sendContentUpdates() {
        for (int n = 0; n < this.slots.size(); ++n) {
            ItemStack object = this.slots.get(n).getStack();
            ItemStack _snowman2 = this.trackedStacks.get(n);
            if (ItemStack.areEqual(_snowman2, object)) continue;
            ItemStack itemStack = object.copy();
            this.trackedStacks.set(n, itemStack);
            for (ScreenHandlerListener screenHandlerListener : this.listeners) {
                screenHandlerListener.onSlotUpdate(this, n, itemStack);
            }
        }
        for (int i = 0; i < this.properties.size(); ++i) {
            Property property = this.properties.get(i);
            if (!property.hasChanged()) continue;
            for (ScreenHandlerListener screenHandlerListener : this.listeners) {
                screenHandlerListener.onPropertyUpdate(this, i, property.get());
            }
        }
    }

    public boolean onButtonClick(PlayerEntity player, int id) {
        return false;
    }

    public Slot getSlot(int index) {
        return this.slots.get(index);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        if (slot != null) {
            return slot.getStack();
        }
        return ItemStack.EMPTY;
    }

    public ItemStack onSlotClick(int n, int n2, SlotActionType actionType, PlayerEntity playerEntity) {
        try {
            return this.method_30010(n, n2, actionType, playerEntity);
        }
        catch (Exception exception) {
            CrashReport crashReport = CrashReport.create(exception, "Container click");
            CrashReportSection _snowman2 = crashReport.addElement("Click info");
            _snowman2.add("Menu Type", () -> this.type != null ? Registry.SCREEN_HANDLER.getId(this.type).toString() : "<no type>");
            _snowman2.add("Menu Class", () -> this.getClass().getCanonicalName());
            _snowman2.add("Slot Count", this.slots.size());
            _snowman2.add("Slot", n);
            _snowman2.add("Button", n2);
            _snowman2.add("Type", (Object)actionType);
            throw new CrashException(crashReport);
        }
    }

    private ItemStack method_30010(int n4, int n2, SlotActionType slotActionType2, PlayerEntity playerEntity2) {
        SlotActionType slotActionType2;
        ItemStack itemStack = ItemStack.EMPTY;
        PlayerInventory _snowman2 = playerEntity2.inventory;
        if (slotActionType2 == SlotActionType.QUICK_CRAFT) {
            int n3 = this.quickCraftButton;
            this.quickCraftButton = ScreenHandler.unpackQuickCraftStage(n2);
            if ((n3 != 1 || this.quickCraftButton != 2) && n3 != this.quickCraftButton) {
                this.endQuickCraft();
            } else if (_snowman2.getCursorStack().isEmpty()) {
                this.endQuickCraft();
            } else if (this.quickCraftButton == 0) {
                this.quickCraftStage = ScreenHandler.unpackQuickCraftButton(n2);
                if (ScreenHandler.shouldQuickCraftContinue(this.quickCraftStage, playerEntity2)) {
                    this.quickCraftButton = 1;
                    this.quickCraftSlots.clear();
                } else {
                    this.endQuickCraft();
                }
            } else if (this.quickCraftButton == 1) {
                Slot slot = this.slots.get(n4);
                ItemStack _snowman3 = _snowman2.getCursorStack();
                if (slot != null && ScreenHandler.canInsertItemIntoSlot(slot, _snowman3, true) && slot.canInsert(_snowman3) && (this.quickCraftStage == 2 || _snowman3.getCount() > this.quickCraftSlots.size()) && this.canInsertIntoSlot(slot)) {
                    this.quickCraftSlots.add(slot);
                }
            } else if (this.quickCraftButton == 2) {
                if (!this.quickCraftSlots.isEmpty()) {
                    ItemStack itemStack2 = _snowman2.getCursorStack().copy();
                    int _snowman4 = _snowman2.getCursorStack().getCount();
                    for (Slot slot : this.quickCraftSlots) {
                        ItemStack itemStack3 = _snowman2.getCursorStack();
                        if (slot == null || !ScreenHandler.canInsertItemIntoSlot(slot, itemStack3, true) || !slot.canInsert(itemStack3) || this.quickCraftStage != 2 && itemStack3.getCount() < this.quickCraftSlots.size() || !this.canInsertIntoSlot(slot)) continue;
                        _snowman = itemStack2.copy();
                        int _snowman5 = slot.hasStack() ? slot.getStack().getCount() : 0;
                        ScreenHandler.calculateStackSize(this.quickCraftSlots, this.quickCraftStage, _snowman, _snowman5);
                        int _snowman6 = Math.min(_snowman.getMaxCount(), slot.getMaxItemCount(_snowman));
                        if (_snowman.getCount() > _snowman6) {
                            _snowman.setCount(_snowman6);
                        }
                        _snowman4 -= _snowman.getCount() - _snowman5;
                        slot.setStack(_snowman);
                    }
                    itemStack2.setCount(_snowman4);
                    _snowman2.setCursorStack(itemStack2);
                }
                this.endQuickCraft();
            } else {
                this.endQuickCraft();
            }
        } else if (this.quickCraftButton != 0) {
            this.endQuickCraft();
        } else if (!(slotActionType2 != SlotActionType.PICKUP && slotActionType2 != SlotActionType.QUICK_MOVE || n2 != 0 && n2 != 1)) {
            if (n4 == -999) {
                if (!_snowman2.getCursorStack().isEmpty()) {
                    if (n2 == 0) {
                        playerEntity2.dropItem(_snowman2.getCursorStack(), true);
                        _snowman2.setCursorStack(ItemStack.EMPTY);
                    }
                    if (n2 == 1) {
                        playerEntity2.dropItem(_snowman2.getCursorStack().split(1), true);
                    }
                }
            } else if (slotActionType2 == SlotActionType.QUICK_MOVE) {
                if (n4 < 0) {
                    return ItemStack.EMPTY;
                }
                Slot slot = this.slots.get(n4);
                if (slot == null || !slot.canTakeItems(playerEntity2)) {
                    return ItemStack.EMPTY;
                }
                ItemStack _snowman7 = this.transferSlot(playerEntity2, n4);
                while (!_snowman7.isEmpty() && ItemStack.areItemsEqualIgnoreDamage(slot.getStack(), _snowman7)) {
                    itemStack = _snowman7.copy();
                    _snowman7 = this.transferSlot(playerEntity2, n4);
                }
            } else {
                int n4;
                if (n4 < 0) {
                    return ItemStack.EMPTY;
                }
                Slot _snowman8 = this.slots.get(n4);
                if (_snowman8 != null) {
                    PlayerEntity playerEntity2;
                    ItemStack itemStack4;
                    ItemStack itemStack5 = _snowman8.getStack();
                    itemStack4 = _snowman2.getCursorStack();
                    if (!itemStack5.isEmpty()) {
                        itemStack = itemStack5.copy();
                    }
                    if (itemStack5.isEmpty()) {
                        if (!itemStack4.isEmpty() && _snowman8.canInsert(itemStack4)) {
                            int n5 = n6 = n2 == 0 ? itemStack4.getCount() : 1;
                            if (n6 > _snowman8.getMaxItemCount(itemStack4)) {
                                int n6 = _snowman8.getMaxItemCount(itemStack4);
                            }
                            _snowman8.setStack(itemStack4.split(n6));
                        }
                    } else if (_snowman8.canTakeItems(playerEntity2)) {
                        if (itemStack4.isEmpty()) {
                            if (itemStack5.isEmpty()) {
                                _snowman8.setStack(ItemStack.EMPTY);
                                _snowman2.setCursorStack(ItemStack.EMPTY);
                            } else {
                                int n7 = n2 == 0 ? itemStack5.getCount() : (itemStack5.getCount() + 1) / 2;
                                _snowman2.setCursorStack(_snowman8.takeStack(n7));
                                if (itemStack5.isEmpty()) {
                                    _snowman8.setStack(ItemStack.EMPTY);
                                }
                                _snowman8.onTakeItem(playerEntity2, _snowman2.getCursorStack());
                            }
                        } else if (_snowman8.canInsert(itemStack4)) {
                            if (ScreenHandler.canStacksCombine(itemStack5, itemStack4)) {
                                int n8;
                                int n9 = n8 = n2 == 0 ? itemStack4.getCount() : 1;
                                if (n8 > _snowman8.getMaxItemCount(itemStack4) - itemStack5.getCount()) {
                                    n8 = _snowman8.getMaxItemCount(itemStack4) - itemStack5.getCount();
                                }
                                if (n8 > itemStack4.getMaxCount() - itemStack5.getCount()) {
                                    n8 = itemStack4.getMaxCount() - itemStack5.getCount();
                                }
                                itemStack4.decrement(n8);
                                itemStack5.increment(n8);
                            } else if (itemStack4.getCount() <= _snowman8.getMaxItemCount(itemStack4)) {
                                _snowman8.setStack(itemStack4);
                                _snowman2.setCursorStack(itemStack5);
                            }
                        } else if (itemStack4.getMaxCount() > 1 && ScreenHandler.canStacksCombine(itemStack5, itemStack4) && !itemStack5.isEmpty() && (_snowman = itemStack5.getCount()) + itemStack4.getCount() <= itemStack4.getMaxCount()) {
                            itemStack4.increment(_snowman);
                            itemStack5 = _snowman8.takeStack(_snowman);
                            if (itemStack5.isEmpty()) {
                                _snowman8.setStack(ItemStack.EMPTY);
                            }
                            _snowman8.onTakeItem(playerEntity2, _snowman2.getCursorStack());
                        }
                    }
                    _snowman8.markDirty();
                }
            }
        } else if (slotActionType2 == SlotActionType.SWAP) {
            Slot slot = this.slots.get(n4);
            ItemStack _snowman9 = _snowman2.getStack(n2);
            ItemStack _snowman10 = slot.getStack();
            if (!_snowman9.isEmpty() || !_snowman10.isEmpty()) {
                if (_snowman9.isEmpty()) {
                    if (slot.canTakeItems(playerEntity2)) {
                        _snowman2.setStack(n2, _snowman10);
                        slot.onTake(_snowman10.getCount());
                        slot.setStack(ItemStack.EMPTY);
                        slot.onTakeItem(playerEntity2, _snowman10);
                    }
                } else if (_snowman10.isEmpty()) {
                    if (slot.canInsert(_snowman9)) {
                        int n10 = slot.getMaxItemCount(_snowman9);
                        if (_snowman9.getCount() > n10) {
                            slot.setStack(_snowman9.split(n10));
                        } else {
                            slot.setStack(_snowman9);
                            _snowman2.setStack(n2, ItemStack.EMPTY);
                        }
                    }
                } else if (slot.canTakeItems(playerEntity2) && slot.canInsert(_snowman9)) {
                    int n11 = slot.getMaxItemCount(_snowman9);
                    if (_snowman9.getCount() > n11) {
                        slot.setStack(_snowman9.split(n11));
                        slot.onTakeItem(playerEntity2, _snowman10);
                        if (!_snowman2.insertStack(_snowman10)) {
                            playerEntity2.dropItem(_snowman10, true);
                        }
                    } else {
                        slot.setStack(_snowman9);
                        _snowman2.setStack(n2, _snowman10);
                        slot.onTakeItem(playerEntity2, _snowman10);
                    }
                }
            }
        } else if (slotActionType2 == SlotActionType.CLONE && playerEntity2.abilities.creativeMode && _snowman2.getCursorStack().isEmpty() && n4 >= 0) {
            Slot slot = this.slots.get(n4);
            if (slot != null && slot.hasStack()) {
                ItemStack itemStack6 = slot.getStack().copy();
                itemStack6.setCount(itemStack6.getMaxCount());
                _snowman2.setCursorStack(itemStack6);
            }
        } else if (slotActionType2 == SlotActionType.THROW && _snowman2.getCursorStack().isEmpty() && n4 >= 0) {
            Slot slot = this.slots.get(n4);
            if (slot != null && slot.hasStack() && slot.canTakeItems(playerEntity2)) {
                ItemStack itemStack7 = slot.takeStack(n2 == 0 ? 1 : slot.getStack().getCount());
                slot.onTakeItem(playerEntity2, itemStack7);
                playerEntity2.dropItem(itemStack7, true);
            }
        } else if (slotActionType2 == SlotActionType.PICKUP_ALL && n4 >= 0) {
            Slot slot = this.slots.get(n4);
            ItemStack _snowman11 = _snowman2.getCursorStack();
            if (!(_snowman11.isEmpty() || slot != null && slot.hasStack() && slot.canTakeItems(playerEntity2))) {
                int n12 = n2 == 0 ? 0 : this.slots.size() - 1;
                _snowman = n2 == 0 ? 1 : -1;
                for (_snowman = 0; _snowman < 2; ++_snowman) {
                    for (_snowman = n12; _snowman >= 0 && _snowman < this.slots.size() && _snowman11.getCount() < _snowman11.getMaxCount(); _snowman += _snowman) {
                        Slot slot2 = this.slots.get(_snowman);
                        if (!slot2.hasStack() || !ScreenHandler.canInsertItemIntoSlot(slot2, _snowman11, true) || !slot2.canTakeItems(playerEntity2) || !this.canInsertIntoSlot(_snowman11, slot2)) continue;
                        ItemStack _snowman12 = slot2.getStack();
                        if (_snowman == 0 && _snowman12.getCount() == _snowman12.getMaxCount()) continue;
                        int _snowman13 = Math.min(_snowman11.getMaxCount() - _snowman11.getCount(), _snowman12.getCount());
                        ItemStack _snowman14 = slot2.takeStack(_snowman13);
                        _snowman11.increment(_snowman13);
                        if (_snowman14.isEmpty()) {
                            slot2.setStack(ItemStack.EMPTY);
                        }
                        slot2.onTakeItem(playerEntity2, _snowman14);
                    }
                }
            }
            this.sendContentUpdates();
        }
        return itemStack;
    }

    public static boolean canStacksCombine(ItemStack first, ItemStack second) {
        return first.getItem() == second.getItem() && ItemStack.areTagsEqual(first, second);
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return true;
    }

    public void close(PlayerEntity player) {
        PlayerInventory playerInventory = player.inventory;
        if (!playerInventory.getCursorStack().isEmpty()) {
            player.dropItem(playerInventory.getCursorStack(), false);
            playerInventory.setCursorStack(ItemStack.EMPTY);
        }
    }

    protected void dropInventory(PlayerEntity player, World world, Inventory inventory) {
        if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
            for (int i = 0; i < inventory.size(); ++i) {
                player.dropItem(inventory.removeStack(i), false);
            }
            return;
        }
        for (int i = 0; i < inventory.size(); ++i) {
            player.inventory.offerOrDrop(world, inventory.removeStack(i));
        }
    }

    public void onContentChanged(Inventory inventory) {
        this.sendContentUpdates();
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        this.getSlot(slot).setStack(stack);
    }

    public void updateSlotStacks(List<ItemStack> stacks) {
        for (int i = 0; i < stacks.size(); ++i) {
            this.getSlot(i).setStack(stacks.get(i));
        }
    }

    public void setProperty(int id, int value) {
        this.properties.get(id).set(value);
    }

    public short getNextActionId(PlayerInventory playerInventory) {
        this.actionId = (short)(this.actionId + 1);
        return this.actionId;
    }

    public boolean isNotRestricted(PlayerEntity player) {
        return !this.restrictedPlayers.contains(player);
    }

    public void setPlayerRestriction(PlayerEntity player, boolean unrestricted) {
        if (unrestricted) {
            this.restrictedPlayers.remove(player);
        } else {
            this.restrictedPlayers.add(player);
        }
    }

    public abstract boolean canUse(PlayerEntity var1);

    protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        ItemStack _snowman3;
        boolean _snowman4 = false;
        int _snowman2 = startIndex;
        if (fromLast) {
            _snowman2 = endIndex - 1;
        }
        if (stack.isStackable()) {
            while (!stack.isEmpty() && (fromLast ? _snowman2 >= startIndex : _snowman2 < endIndex)) {
                Slot slot = this.slots.get(_snowman2);
                _snowman3 = slot.getStack();
                if (!_snowman3.isEmpty() && ScreenHandler.canStacksCombine(stack, _snowman3)) {
                    int n = _snowman3.getCount() + stack.getCount();
                    if (n <= stack.getMaxCount()) {
                        stack.setCount(0);
                        _snowman3.setCount(n);
                        slot.markDirty();
                        _snowman4 = true;
                    } else if (_snowman3.getCount() < stack.getMaxCount()) {
                        stack.decrement(stack.getMaxCount() - _snowman3.getCount());
                        _snowman3.setCount(stack.getMaxCount());
                        slot.markDirty();
                        _snowman4 = true;
                    }
                }
                if (fromLast) {
                    --_snowman2;
                    continue;
                }
                ++_snowman2;
            }
        }
        if (!stack.isEmpty()) {
            _snowman2 = fromLast ? endIndex - 1 : startIndex;
            while (fromLast ? _snowman2 >= startIndex : _snowman2 < endIndex) {
                slot = this.slots.get(_snowman2);
                _snowman3 = slot.getStack();
                if (_snowman3.isEmpty() && slot.canInsert(stack)) {
                    if (stack.getCount() > slot.getMaxItemCount()) {
                        slot.setStack(stack.split(slot.getMaxItemCount()));
                    } else {
                        slot.setStack(stack.split(stack.getCount()));
                    }
                    slot.markDirty();
                    _snowman4 = true;
                    break;
                }
                if (fromLast) {
                    --_snowman2;
                    continue;
                }
                ++_snowman2;
            }
        }
        return _snowman4;
    }

    public static int unpackQuickCraftButton(int quickCraftData) {
        return quickCraftData >> 2 & 3;
    }

    public static int unpackQuickCraftStage(int quickCraftData) {
        return quickCraftData & 3;
    }

    public static int packQuickCraftData(int quickCraftStage, int buttonId) {
        return quickCraftStage & 3 | (buttonId & 3) << 2;
    }

    public static boolean shouldQuickCraftContinue(int stage, PlayerEntity player) {
        if (stage == 0) {
            return true;
        }
        if (stage == 1) {
            return true;
        }
        return stage == 2 && player.abilities.creativeMode;
    }

    protected void endQuickCraft() {
        this.quickCraftButton = 0;
        this.quickCraftSlots.clear();
    }

    public static boolean canInsertItemIntoSlot(@Nullable Slot slot, ItemStack stack, boolean allowOverflow) {
        boolean bl;
        boolean bl2 = bl = slot == null || !slot.hasStack();
        if (!bl && stack.isItemEqualIgnoreDamage(slot.getStack()) && ItemStack.areTagsEqual(slot.getStack(), stack)) {
            return slot.getStack().getCount() + (allowOverflow ? 0 : stack.getCount()) <= stack.getMaxCount();
        }
        return bl;
    }

    public static void calculateStackSize(Set<Slot> slots, int mode, ItemStack stack, int stackSize) {
        switch (mode) {
            case 0: {
                stack.setCount(MathHelper.floor((float)stack.getCount() / (float)slots.size()));
                break;
            }
            case 1: {
                stack.setCount(1);
                break;
            }
            case 2: {
                stack.setCount(stack.getItem().getMaxCount());
            }
        }
        stack.increment(stackSize);
    }

    public boolean canInsertIntoSlot(Slot slot) {
        return true;
    }

    public static int calculateComparatorOutput(@Nullable BlockEntity entity) {
        if (entity instanceof Inventory) {
            return ScreenHandler.calculateComparatorOutput((Inventory)((Object)entity));
        }
        return 0;
    }

    public static int calculateComparatorOutput(@Nullable Inventory inventory) {
        if (inventory == null) {
            return 0;
        }
        int n = 0;
        float _snowman2 = 0.0f;
        for (_snowman = 0; _snowman < inventory.size(); ++_snowman) {
            ItemStack itemStack = inventory.getStack(_snowman);
            if (itemStack.isEmpty()) continue;
            _snowman2 += (float)itemStack.getCount() / (float)Math.min(inventory.getMaxCountPerStack(), itemStack.getMaxCount());
            ++n;
        }
        return MathHelper.floor((_snowman2 /= (float)inventory.size()) * 14.0f) + (n > 0 ? 1 : 0);
    }
}

