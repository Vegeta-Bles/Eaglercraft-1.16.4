/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.entity.player;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Nameable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.World;

public class PlayerInventory
implements Inventory,
Nameable {
    public final DefaultedList<ItemStack> main = DefaultedList.ofSize(36, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> armor = DefaultedList.ofSize(4, ItemStack.EMPTY);
    public final DefaultedList<ItemStack> offHand = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private final List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(this.main, this.armor, this.offHand);
    public int selectedSlot;
    public final PlayerEntity player;
    private ItemStack cursorStack = ItemStack.EMPTY;
    private int changeCount;

    public PlayerInventory(PlayerEntity player) {
        this.player = player;
    }

    public ItemStack getMainHandStack() {
        if (PlayerInventory.isValidHotbarIndex(this.selectedSlot)) {
            return this.main.get(this.selectedSlot);
        }
        return ItemStack.EMPTY;
    }

    public static int getHotbarSize() {
        return 9;
    }

    private boolean canStackAddMore(ItemStack existingStack, ItemStack stack) {
        return !existingStack.isEmpty() && this.areItemsEqual(existingStack, stack) && existingStack.isStackable() && existingStack.getCount() < existingStack.getMaxCount() && existingStack.getCount() < this.getMaxCountPerStack();
    }

    private boolean areItemsEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && ItemStack.areTagsEqual(stack1, stack2);
    }

    public int getEmptySlot() {
        for (int i = 0; i < this.main.size(); ++i) {
            if (!this.main.get(i).isEmpty()) continue;
            return i;
        }
        return -1;
    }

    public void addPickBlock(ItemStack stack) {
        int n = this.getSlotWithStack(stack);
        if (PlayerInventory.isValidHotbarIndex(n)) {
            this.selectedSlot = n;
            return;
        }
        if (n == -1) {
            this.selectedSlot = this.getSwappableHotbarSlot();
            if (!this.main.get(this.selectedSlot).isEmpty() && (_snowman = this.getEmptySlot()) != -1) {
                this.main.set(_snowman, this.main.get(this.selectedSlot));
            }
            this.main.set(this.selectedSlot, stack);
        } else {
            this.swapSlotWithHotbar(n);
        }
    }

    public void swapSlotWithHotbar(int hotbarSlot) {
        this.selectedSlot = this.getSwappableHotbarSlot();
        ItemStack itemStack = this.main.get(this.selectedSlot);
        this.main.set(this.selectedSlot, this.main.get(hotbarSlot));
        this.main.set(hotbarSlot, itemStack);
    }

    public static boolean isValidHotbarIndex(int slot) {
        return slot >= 0 && slot < 9;
    }

    public int getSlotWithStack(ItemStack stack) {
        for (int i = 0; i < this.main.size(); ++i) {
            if (this.main.get(i).isEmpty() || !this.areItemsEqual(stack, this.main.get(i))) continue;
            return i;
        }
        return -1;
    }

    public int method_7371(ItemStack itemStack) {
        for (int i = 0; i < this.main.size(); ++i) {
            ItemStack itemStack2 = this.main.get(i);
            if (this.main.get(i).isEmpty() || !this.areItemsEqual(itemStack, this.main.get(i)) || this.main.get(i).isDamaged() || itemStack2.hasEnchantments() || itemStack2.hasCustomName()) continue;
            return i;
        }
        return -1;
    }

    public int getSwappableHotbarSlot() {
        int n;
        for (n = 0; n < 9; ++n) {
            _snowman = (this.selectedSlot + n) % 9;
            if (!this.main.get(_snowman).isEmpty()) continue;
            return _snowman;
        }
        for (n = 0; n < 9; ++n) {
            _snowman = (this.selectedSlot + n) % 9;
            if (this.main.get(_snowman).hasEnchantments()) continue;
            return _snowman;
        }
        return this.selectedSlot;
    }

    public void scrollInHotbar(double scrollAmount) {
        if (scrollAmount > 0.0) {
            scrollAmount = 1.0;
        }
        if (scrollAmount < 0.0) {
            scrollAmount = -1.0;
        }
        this.selectedSlot = (int)((double)this.selectedSlot - scrollAmount);
        while (this.selectedSlot < 0) {
            this.selectedSlot += 9;
        }
        while (this.selectedSlot >= 9) {
            this.selectedSlot -= 9;
        }
    }

    public int remove(Predicate<ItemStack> shouldRemove, int maxCount, Inventory craftingInventory) {
        int n = 0;
        boolean _snowman2 = maxCount == 0;
        n += Inventories.remove(this, shouldRemove, maxCount - n, _snowman2);
        n += Inventories.remove(craftingInventory, shouldRemove, maxCount - n, _snowman2);
        n += Inventories.remove(this.cursorStack, shouldRemove, maxCount - n, _snowman2);
        if (this.cursorStack.isEmpty()) {
            this.cursorStack = ItemStack.EMPTY;
        }
        return n;
    }

    private int addStack(ItemStack stack) {
        int n = this.getOccupiedSlotWithRoomForStack(stack);
        if (n == -1) {
            n = this.getEmptySlot();
        }
        if (n == -1) {
            return stack.getCount();
        }
        return this.addStack(n, stack);
    }

    private int addStack(int slot, ItemStack stack) {
        int n;
        Item item = stack.getItem();
        int _snowman2 = stack.getCount();
        ItemStack _snowman3 = this.getStack(slot);
        if (_snowman3.isEmpty()) {
            _snowman3 = new ItemStack(item, 0);
            if (stack.hasTag()) {
                _snowman3.setTag(stack.getTag().copy());
            }
            this.setStack(slot, _snowman3);
        }
        if ((n = _snowman2) > _snowman3.getMaxCount() - _snowman3.getCount()) {
            n = _snowman3.getMaxCount() - _snowman3.getCount();
        }
        if (n > this.getMaxCountPerStack() - _snowman3.getCount()) {
            n = this.getMaxCountPerStack() - _snowman3.getCount();
        }
        if (n == 0) {
            return _snowman2;
        }
        _snowman3.increment(n);
        _snowman3.setCooldown(5);
        return _snowman2 -= n;
    }

    public int getOccupiedSlotWithRoomForStack(ItemStack stack) {
        if (this.canStackAddMore(this.getStack(this.selectedSlot), stack)) {
            return this.selectedSlot;
        }
        if (this.canStackAddMore(this.getStack(40), stack)) {
            return 40;
        }
        for (int i = 0; i < this.main.size(); ++i) {
            if (!this.canStackAddMore(this.main.get(i), stack)) continue;
            return i;
        }
        return -1;
    }

    public void updateItems() {
        for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            for (int i = 0; i < defaultedList.size(); ++i) {
                if (defaultedList.get(i).isEmpty()) continue;
                defaultedList.get(i).inventoryTick(this.player.world, this.player, i, this.selectedSlot == i);
            }
        }
    }

    public boolean insertStack(ItemStack stack) {
        return this.insertStack(-1, stack);
    }

    public boolean insertStack(int slot, ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        try {
            if (!stack.isDamaged()) {
                int n;
                do {
                    n = stack.getCount();
                    if (slot == -1) {
                        stack.setCount(this.addStack(stack));
                        continue;
                    }
                    stack.setCount(this.addStack(slot, stack));
                } while (!stack.isEmpty() && stack.getCount() < n);
                if (stack.getCount() == n && this.player.abilities.creativeMode) {
                    stack.setCount(0);
                    return true;
                }
                return stack.getCount() < n;
            }
            if (slot == -1) {
                slot = this.getEmptySlot();
            }
            if (slot >= 0) {
                this.main.set(slot, stack.copy());
                this.main.get(slot).setCooldown(5);
                stack.setCount(0);
                return true;
            }
            if (this.player.abilities.creativeMode) {
                stack.setCount(0);
                return true;
            }
            return false;
        }
        catch (Throwable throwable) {
            CrashReport crashReport = CrashReport.create(throwable, "Adding item to inventory");
            CrashReportSection _snowman2 = crashReport.addElement("Item being added");
            _snowman2.add("Item ID", Item.getRawId(stack.getItem()));
            _snowman2.add("Item data", stack.getDamage());
            _snowman2.add("Item name", () -> stack.getName().getString());
            throw new CrashException(crashReport);
        }
    }

    public void offerOrDrop(World world, ItemStack stack) {
        if (world.isClient) {
            return;
        }
        while (!stack.isEmpty()) {
            int n = this.getOccupiedSlotWithRoomForStack(stack);
            if (n == -1) {
                n = this.getEmptySlot();
            }
            if (n == -1) {
                this.player.dropItem(stack, false);
                break;
            }
            _snowman = stack.getMaxCount() - this.getStack(n).getCount();
            if (!this.insertStack(n, stack.split(_snowman))) continue;
            ((ServerPlayerEntity)this.player).networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-2, n, this.getStack(n)));
        }
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
            return Inventories.splitStack(defaultedList, slot, amount);
        }
        return ItemStack.EMPTY;
    }

    public void removeOne(ItemStack stack) {
        block0: for (DefaultedList<ItemStack> defaultedList : this.combinedInventory) {
            for (int i = 0; i < defaultedList.size(); ++i) {
                if (defaultedList.get(i) != stack) continue;
                defaultedList.set(i, ItemStack.EMPTY);
                continue block0;
            }
        }
    }

    @Override
    public ItemStack removeStack(int slot) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
            ItemStack itemStack = defaultedList.get(slot);
            defaultedList.set(slot, ItemStack.EMPTY);
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        if (defaultedList != null) {
            defaultedList.set(slot, stack);
        }
    }

    public float getBlockBreakingSpeed(BlockState block) {
        return this.main.get(this.selectedSlot).getMiningSpeedMultiplier(block);
    }

    public ListTag serialize(ListTag tag) {
        CompoundTag compoundTag;
        int n;
        for (n = 0; n < this.main.size(); ++n) {
            if (this.main.get(n).isEmpty()) continue;
            compoundTag = new CompoundTag();
            compoundTag.putByte("Slot", (byte)n);
            this.main.get(n).toTag(compoundTag);
            tag.add(compoundTag);
        }
        for (n = 0; n < this.armor.size(); ++n) {
            if (this.armor.get(n).isEmpty()) continue;
            compoundTag = new CompoundTag();
            compoundTag.putByte("Slot", (byte)(n + 100));
            this.armor.get(n).toTag(compoundTag);
            tag.add(compoundTag);
        }
        for (n = 0; n < this.offHand.size(); ++n) {
            if (this.offHand.get(n).isEmpty()) continue;
            compoundTag = new CompoundTag();
            compoundTag.putByte("Slot", (byte)(n + 150));
            this.offHand.get(n).toTag(compoundTag);
            tag.add(compoundTag);
        }
        return tag;
    }

    public void deserialize(ListTag tag) {
        this.main.clear();
        this.armor.clear();
        this.offHand.clear();
        for (int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundTag = tag.getCompound(i);
            int _snowman2 = compoundTag.getByte("Slot") & 0xFF;
            ItemStack _snowman3 = ItemStack.fromTag(compoundTag);
            if (_snowman3.isEmpty()) continue;
            if (_snowman2 >= 0 && _snowman2 < this.main.size()) {
                this.main.set(_snowman2, _snowman3);
                continue;
            }
            if (_snowman2 >= 100 && _snowman2 < this.armor.size() + 100) {
                this.armor.set(_snowman2 - 100, _snowman3);
                continue;
            }
            if (_snowman2 < 150 || _snowman2 >= this.offHand.size() + 150) continue;
            this.offHand.set(_snowman2 - 150, _snowman3);
        }
    }

    @Override
    public int size() {
        return this.main.size() + this.armor.size() + this.offHand.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.main) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        for (ItemStack itemStack : this.armor) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        for (ItemStack itemStack : this.offHand) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        DefaultedList<ItemStack> defaultedList = null;
        for (DefaultedList<ItemStack> defaultedList2 : this.combinedInventory) {
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
            slot -= defaultedList2.size();
        }
        return defaultedList == null ? ItemStack.EMPTY : (ItemStack)defaultedList.get(slot);
    }

    @Override
    public Text getName() {
        return new TranslatableText("container.inventory");
    }

    public ItemStack getArmorStack(int slot) {
        return this.armor.get(slot);
    }

    public void damageArmor(DamageSource damageSource, float f) {
        if (f <= 0.0f) {
            return;
        }
        if ((f /= 4.0f) < 1.0f) {
            f = 1.0f;
        }
        for (int i = 0; i < this.armor.size(); ++i) {
            ItemStack itemStack = this.armor.get(i);
            if (damageSource.isFire() && itemStack.getItem().isFireproof() || !(itemStack.getItem() instanceof ArmorItem)) continue;
            int _snowman2 = i;
            itemStack.damage((int)f, this.player, playerEntity -> playerEntity.sendEquipmentBreakStatus(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, _snowman2)));
        }
    }

    public void dropAll() {
        for (List list : this.combinedInventory) {
            for (int i = 0; i < list.size(); ++i) {
                ItemStack itemStack = (ItemStack)list.get(i);
                if (itemStack.isEmpty()) continue;
                this.player.dropItem(itemStack, true, false);
                list.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void markDirty() {
        ++this.changeCount;
    }

    public int getChangeCount() {
        return this.changeCount;
    }

    public void setCursorStack(ItemStack stack) {
        this.cursorStack = stack;
    }

    public ItemStack getCursorStack() {
        return this.cursorStack;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.player.removed) {
            return false;
        }
        return !(player.squaredDistanceTo(this.player) > 64.0);
    }

    public boolean contains(ItemStack stack) {
        for (List list : this.combinedInventory) {
            for (ItemStack itemStack : list) {
                if (itemStack.isEmpty() || !itemStack.isItemEqualIgnoreDamage(stack)) continue;
                return true;
            }
        }
        return false;
    }

    public boolean contains(Tag<Item> tag) {
        for (List list : this.combinedInventory) {
            for (ItemStack itemStack : list) {
                if (itemStack.isEmpty() || !tag.contains(itemStack.getItem())) continue;
                return true;
            }
        }
        return false;
    }

    public void clone(PlayerInventory other) {
        for (int i = 0; i < this.size(); ++i) {
            this.setStack(i, other.getStack(i));
        }
        this.selectedSlot = other.selectedSlot;
    }

    @Override
    public void clear() {
        for (List list : this.combinedInventory) {
            list.clear();
        }
    }

    public void populateRecipeFinder(RecipeFinder finder) {
        for (ItemStack itemStack : this.main) {
            finder.addNormalItem(itemStack);
        }
    }
}

