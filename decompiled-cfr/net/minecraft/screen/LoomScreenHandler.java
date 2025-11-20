/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class LoomScreenHandler
extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Property selectedPattern = Property.create();
    private Runnable inventoryChangeListener = () -> {};
    private final Slot bannerSlot;
    private final Slot dyeSlot;
    private final Slot patternSlot;
    private final Slot outputSlot;
    private long lastTakeResultTime;
    private final Inventory input = new SimpleInventory(this, 3){
        final /* synthetic */ LoomScreenHandler field_7851;
        {
            this.field_7851 = loomScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            this.field_7851.onContentChanged(this);
            LoomScreenHandler.method_17421(this.field_7851).run();
        }
    };
    private final Inventory output = new SimpleInventory(this, 1){
        final /* synthetic */ LoomScreenHandler field_17324;
        {
            this.field_17324 = loomScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            LoomScreenHandler.method_17421(this.field_17324).run();
        }
    };

    public LoomScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public LoomScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.LOOM, syncId);
        int n;
        this.context = context;
        this.bannerSlot = this.addSlot(new Slot(this, this.input, 0, 13, 26){
            final /* synthetic */ LoomScreenHandler field_7852;
            {
                this.field_7852 = loomScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof BannerItem;
            }
        });
        this.dyeSlot = this.addSlot(new Slot(this, this.input, 1, 33, 26){
            final /* synthetic */ LoomScreenHandler field_7853;
            {
                this.field_7853 = loomScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }
        });
        this.patternSlot = this.addSlot(new Slot(this, this.input, 2, 23, 45){
            final /* synthetic */ LoomScreenHandler field_7854;
            {
                this.field_7854 = loomScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() instanceof BannerPatternItem;
            }
        });
        this.outputSlot = this.addSlot(new Slot(this, this.output, 0, 143, 58, context){
            final /* synthetic */ ScreenHandlerContext field_17325;
            final /* synthetic */ LoomScreenHandler field_7855;
            {
                this.field_7855 = loomScreenHandler;
                this.field_17325 = screenHandlerContext;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                LoomScreenHandler.method_17425(this.field_7855).takeStack(1);
                LoomScreenHandler.method_17426(this.field_7855).takeStack(1);
                if (!LoomScreenHandler.method_17425(this.field_7855).hasStack() || !LoomScreenHandler.method_17426(this.field_7855).hasStack()) {
                    LoomScreenHandler.method_17427(this.field_7855).set(0);
                }
                this.field_17325.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (LoomScreenHandler.method_21829(this.field_7855) != l) {
                        world.playSound(null, (BlockPos)blockPos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        LoomScreenHandler.method_21828(this.field_7855, l);
                    }
                });
                return super.onTakeItem(player, stack);
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
        this.addProperty(this.selectedPattern);
    }

    public int getSelectedPattern() {
        return this.selectedPattern.get();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return LoomScreenHandler.canUse(this.context, player, Blocks.LOOM);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (id > 0 && id <= BannerPattern.LOOM_APPLICABLE_COUNT) {
            this.selectedPattern.set(id);
            this.updateOutputSlot();
            return true;
        }
        return false;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.bannerSlot.getStack();
        _snowman = this.dyeSlot.getStack();
        _snowman = this.patternSlot.getStack();
        _snowman = this.outputSlot.getStack();
        if (!_snowman.isEmpty() && (itemStack.isEmpty() || _snowman.isEmpty() || this.selectedPattern.get() <= 0 || this.selectedPattern.get() >= BannerPattern.COUNT - BannerPattern.field_24417 && _snowman.isEmpty())) {
            this.outputSlot.setStack(ItemStack.EMPTY);
            this.selectedPattern.set(0);
        } else if (!_snowman.isEmpty() && _snowman.getItem() instanceof BannerPatternItem) {
            CompoundTag compoundTag = itemStack.getOrCreateSubTag("BlockEntityTag");
            boolean bl = _snowman = compoundTag.contains("Patterns", 9) && !itemStack.isEmpty() && compoundTag.getList("Patterns", 10).size() >= 6;
            if (_snowman) {
                this.selectedPattern.set(0);
            } else {
                this.selectedPattern.set(((BannerPatternItem)_snowman.getItem()).getPattern().ordinal());
            }
        }
        this.updateOutputSlot();
        this.sendContentUpdates();
    }

    public void setInventoryChangeListener(Runnable inventoryChangeListener) {
        this.inventoryChangeListener = inventoryChangeListener;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == this.outputSlot.id) {
                if (!this.insertItem(_snowman, 4, 40, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (index == this.dyeSlot.id || index == this.bannerSlot.id || index == this.patternSlot.id ? !this.insertItem(_snowman, 4, 40, false) : (_snowman.getItem() instanceof BannerItem ? !this.insertItem(_snowman, this.bannerSlot.id, this.bannerSlot.id + 1, false) : (_snowman.getItem() instanceof DyeItem ? !this.insertItem(_snowman, this.dyeSlot.id, this.dyeSlot.id + 1, false) : (_snowman.getItem() instanceof BannerPatternItem ? !this.insertItem(_snowman, this.patternSlot.id, this.patternSlot.id + 1, false) : (index >= 4 && index < 31 ? !this.insertItem(_snowman, 31, 40, false) : index >= 31 && index < 40 && !this.insertItem(_snowman, 4, 31, false)))))) {
                return ItemStack.EMPTY;
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

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, playerEntity.world, this.input));
    }

    private void updateOutputSlot() {
        if (this.selectedPattern.get() > 0) {
            ItemStack itemStack;
            ItemStack itemStack2 = this.bannerSlot.getStack();
            _snowman = this.dyeSlot.getStack();
            itemStack = ItemStack.EMPTY;
            if (!itemStack2.isEmpty() && !_snowman.isEmpty()) {
                ListTag listTag;
                itemStack = itemStack2.copy();
                itemStack.setCount(1);
                BannerPattern bannerPattern = BannerPattern.values()[this.selectedPattern.get()];
                DyeColor _snowman2 = ((DyeItem)_snowman.getItem()).getColor();
                CompoundTag _snowman3 = itemStack.getOrCreateSubTag("BlockEntityTag");
                if (_snowman3.contains("Patterns", 9)) {
                    listTag = _snowman3.getList("Patterns", 10);
                } else {
                    listTag = new ListTag();
                    _snowman3.put("Patterns", listTag);
                }
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString("Pattern", bannerPattern.getId());
                compoundTag.putInt("Color", _snowman2.getId());
                listTag.add(compoundTag);
            }
            if (!ItemStack.areEqual(itemStack, this.outputSlot.getStack())) {
                this.outputSlot.setStack(itemStack);
            }
        }
    }

    public Slot getBannerSlot() {
        return this.bannerSlot;
    }

    public Slot getDyeSlot() {
        return this.dyeSlot;
    }

    public Slot getPatternSlot() {
        return this.patternSlot;
    }

    public Slot getOutputSlot() {
        return this.outputSlot;
    }

    static /* synthetic */ Runnable method_17421(LoomScreenHandler loomScreenHandler) {
        return loomScreenHandler.inventoryChangeListener;
    }

    static /* synthetic */ Slot method_17425(LoomScreenHandler loomScreenHandler) {
        return loomScreenHandler.bannerSlot;
    }

    static /* synthetic */ Slot method_17426(LoomScreenHandler loomScreenHandler) {
        return loomScreenHandler.dyeSlot;
    }

    static /* synthetic */ Property method_17427(LoomScreenHandler loomScreenHandler) {
        return loomScreenHandler.selectedPattern;
    }

    static /* synthetic */ long method_21829(LoomScreenHandler loomScreenHandler) {
        return loomScreenHandler.lastTakeResultTime;
    }

    static /* synthetic */ long method_21828(LoomScreenHandler loomScreenHandler, long l) {
        loomScreenHandler.lastTakeResultTime = l;
        return loomScreenHandler.lastTakeResultTime;
    }
}

