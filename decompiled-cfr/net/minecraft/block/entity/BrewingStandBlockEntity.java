/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BrewingStandBlockEntity
extends LockableContainerBlockEntity
implements SidedInventory,
Tickable {
    private static final int[] TOP_SLOTS = new int[]{3};
    private static final int[] BOTTOM_SLOTS = new int[]{0, 1, 2, 3};
    private static final int[] SIDE_SLOTS = new int[]{0, 1, 2, 4};
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] slotsEmptyLastTick;
    private Item itemBrewing;
    private int fuel;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate(this){
        final /* synthetic */ BrewingStandBlockEntity field_17382;
        {
            this.field_17382 = brewingStandBlockEntity;
        }

        public int get(int index) {
            switch (index) {
                case 0: {
                    return BrewingStandBlockEntity.method_17498(this.field_17382);
                }
                case 1: {
                    return BrewingStandBlockEntity.method_17500(this.field_17382);
                }
            }
            return 0;
        }

        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    BrewingStandBlockEntity.method_17499(this.field_17382, value);
                    break;
                }
                case 1: {
                    BrewingStandBlockEntity.method_17501(this.field_17382, value);
                }
            }
        }

        public int size() {
            return 2;
        }
    };

    public BrewingStandBlockEntity() {
        super(BlockEntityType.BREWING_STAND);
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.brewing");
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void tick() {
        ItemStack itemStack = this.inventory.get(4);
        if (this.fuel <= 0 && itemStack.getItem() == Items.BLAZE_POWDER) {
            this.fuel = 20;
            itemStack.decrement(1);
            this.markDirty();
        }
        boolean _snowman2 = this.canCraft();
        boolean _snowman3 = this.brewTime > 0;
        _snowman = this.inventory.get(3);
        if (_snowman3) {
            --this.brewTime;
            boolean bl = _snowman = this.brewTime == 0;
            if (_snowman && _snowman2) {
                this.craft();
                this.markDirty();
            } else if (!_snowman2) {
                this.brewTime = 0;
                this.markDirty();
            } else if (this.itemBrewing != _snowman.getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        } else if (_snowman2 && this.fuel > 0) {
            --this.fuel;
            this.brewTime = 400;
            this.itemBrewing = _snowman.getItem();
            this.markDirty();
        }
        if (!this.world.isClient && !Arrays.equals(_snowman = this.getSlotsEmpty(), this.slotsEmptyLastTick)) {
            this.slotsEmptyLastTick = _snowman;
            BlockState blockState = this.world.getBlockState(this.getPos());
            if (!(blockState.getBlock() instanceof BrewingStandBlock)) {
                return;
            }
            for (int i = 0; i < BrewingStandBlock.BOTTLE_PROPERTIES.length; ++i) {
                blockState = (BlockState)blockState.with(BrewingStandBlock.BOTTLE_PROPERTIES[i], _snowman[i]);
            }
            this.world.setBlockState(this.pos, blockState, 2);
        }
    }

    public boolean[] getSlotsEmpty() {
        boolean[] blArray = new boolean[3];
        for (int i = 0; i < 3; ++i) {
            if (this.inventory.get(i).isEmpty()) continue;
            blArray[i] = true;
        }
        return blArray;
    }

    private boolean canCraft() {
        ItemStack itemStack = this.inventory.get(3);
        if (itemStack.isEmpty()) {
            return false;
        }
        if (!BrewingRecipeRegistry.isValidIngredient(itemStack)) {
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            ItemStack itemStack2 = this.inventory.get(i);
            if (itemStack2.isEmpty() || !BrewingRecipeRegistry.hasRecipe(itemStack2, itemStack)) continue;
            return true;
        }
        return false;
    }

    private void craft() {
        ItemStack itemStack = this.inventory.get(3);
        for (int i = 0; i < 3; ++i) {
            this.inventory.set(i, BrewingRecipeRegistry.craft(itemStack, this.inventory.get(i)));
        }
        itemStack.decrement(1);
        BlockPos _snowman2 = this.getPos();
        if (itemStack.getItem().hasRecipeRemainder()) {
            _snowman = new ItemStack(itemStack.getItem().getRecipeRemainder());
            if (itemStack.isEmpty()) {
                itemStack = _snowman;
            } else if (!this.world.isClient) {
                ItemScatterer.spawn(this.world, (double)_snowman2.getX(), (double)_snowman2.getY(), (double)_snowman2.getZ(), _snowman);
            }
        }
        this.inventory.set(3, itemStack);
        this.world.syncWorldEvent(1035, _snowman2, 0);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
        this.brewTime = tag.getShort("BrewTime");
        this.fuel = tag.getByte("Fuel");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("BrewTime", (short)this.brewTime);
        Inventories.toTag(tag, this.inventory);
        tag.putByte("Fuel", (byte)this.fuel);
        return tag;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= 0 && slot < this.inventory.size()) {
            return this.inventory.get(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        }
        return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 3) {
            return BrewingRecipeRegistry.isValidIngredient(stack);
        }
        Item item = stack.getItem();
        if (slot == 4) {
            return item == Items.BLAZE_POWDER;
        }
        return (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && this.getStack(slot).isEmpty();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        return SIDE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if (slot == 3) {
            return stack.getItem() == Items.GLASS_BOTTLE;
        }
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new BrewingStandScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }

    static /* synthetic */ int method_17498(BrewingStandBlockEntity brewingStandBlockEntity) {
        return brewingStandBlockEntity.brewTime;
    }

    static /* synthetic */ int method_17500(BrewingStandBlockEntity brewingStandBlockEntity) {
        return brewingStandBlockEntity.fuel;
    }

    static /* synthetic */ int method_17499(BrewingStandBlockEntity brewingStandBlockEntity, int n) {
        brewingStandBlockEntity.brewTime = n;
        return brewingStandBlockEntity.brewTime;
    }

    static /* synthetic */ int method_17501(BrewingStandBlockEntity brewingStandBlockEntity, int n) {
        brewingStandBlockEntity.fuel = n;
        return brewingStandBlockEntity.fuel;
    }
}

