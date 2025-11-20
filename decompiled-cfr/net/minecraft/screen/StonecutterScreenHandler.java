/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.screen;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StonecutterScreenHandler
extends ScreenHandler {
    private final ScreenHandlerContext context;
    private final Property selectedRecipe = Property.create();
    private final World world;
    private List<StonecuttingRecipe> availableRecipes = Lists.newArrayList();
    private ItemStack inputStack = ItemStack.EMPTY;
    private long lastTakeTime;
    final Slot inputSlot;
    final Slot outputSlot;
    private Runnable contentsChangedListener = () -> {};
    public final Inventory input = new SimpleInventory(this, 1){
        final /* synthetic */ StonecutterScreenHandler field_17637;
        {
            this.field_17637 = stonecutterScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            this.field_17637.onContentChanged(this);
            StonecutterScreenHandler.method_17857(this.field_17637).run();
        }
    };
    private final CraftingResultInventory output = new CraftingResultInventory();

    public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public StonecutterScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.STONECUTTER, syncId);
        int n;
        this.context = context;
        this.world = playerInventory.player.world;
        this.inputSlot = this.addSlot(new Slot(this.input, 0, 20, 33));
        this.outputSlot = this.addSlot(new Slot(this, this.output, 1, 143, 33, context){
            final /* synthetic */ ScreenHandlerContext field_17638;
            final /* synthetic */ StonecutterScreenHandler field_17639;
            {
                this.field_17639 = stonecutterScreenHandler;
                this.field_17638 = screenHandlerContext;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                stack.onCraft(player.world, player, stack.getCount());
                StonecutterScreenHandler.method_30960(this.field_17639).unlockLastRecipe(player);
                ItemStack itemStack = this.field_17639.inputSlot.takeStack(1);
                if (!itemStack.isEmpty()) {
                    StonecutterScreenHandler.method_17860(this.field_17639);
                }
                this.field_17638.run((world, blockPos) -> {
                    long l = world.getTime();
                    if (StonecutterScreenHandler.method_17861(this.field_17639) != l) {
                        world.playSound(null, (BlockPos)blockPos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                        StonecutterScreenHandler.method_17858(this.field_17639, l);
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
        this.addProperty(this.selectedRecipe);
    }

    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    public List<StonecuttingRecipe> getAvailableRecipes() {
        return this.availableRecipes;
    }

    public int getAvailableRecipeCount() {
        return this.availableRecipes.size();
    }

    public boolean canCraft() {
        return this.inputSlot.hasStack() && !this.availableRecipes.isEmpty();
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return StonecutterScreenHandler.canUse(this.context, player, Blocks.STONECUTTER);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        if (this.method_30160(id)) {
            this.selectedRecipe.set(id);
            this.populateResult();
        }
        return true;
    }

    private boolean method_30160(int n) {
        return n >= 0 && n < this.availableRecipes.size();
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        ItemStack itemStack = this.inputSlot.getStack();
        if (itemStack.getItem() != this.inputStack.getItem()) {
            this.inputStack = itemStack.copy();
            this.updateInput(inventory, itemStack);
        }
    }

    private void updateInput(Inventory input, ItemStack stack) {
        this.availableRecipes.clear();
        this.selectedRecipe.set(-1);
        this.outputSlot.setStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.availableRecipes = this.world.getRecipeManager().getAllMatches(RecipeType.STONECUTTING, input, this.world);
        }
    }

    private void populateResult() {
        if (!this.availableRecipes.isEmpty() && this.method_30160(this.selectedRecipe.get())) {
            StonecuttingRecipe stonecuttingRecipe = this.availableRecipes.get(this.selectedRecipe.get());
            this.output.setLastRecipe(stonecuttingRecipe);
            this.outputSlot.setStack(stonecuttingRecipe.craft(this.input));
        } else {
            this.outputSlot.setStack(ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return ScreenHandlerType.STONECUTTER;
    }

    public void setContentsChangedListener(Runnable runnable) {
        this.contentsChangedListener = runnable;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack _snowman3 = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            ItemStack itemStack;
            itemStack = _snowman2.getStack();
            Item item = itemStack.getItem();
            _snowman3 = itemStack.copy();
            if (index == 1) {
                item.onCraft(itemStack, player.world, player);
                if (!this.insertItem(itemStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(itemStack, _snowman3);
            } else if (index == 0 ? !this.insertItem(itemStack, 2, 38, false) : (this.world.getRecipeManager().getFirstMatch(RecipeType.STONECUTTING, new SimpleInventory(itemStack), this.world).isPresent() ? !this.insertItem(itemStack, 0, 1, false) : (index >= 2 && index < 29 ? !this.insertItem(itemStack, 29, 38, false) : index >= 29 && index < 38 && !this.insertItem(itemStack, 2, 29, false)))) {
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
        this.output.removeStack(1);
        this.context.run((world, blockPos) -> this.dropInventory(player, playerEntity.world, this.input));
    }

    static /* synthetic */ Runnable method_17857(StonecutterScreenHandler stonecutterScreenHandler) {
        return stonecutterScreenHandler.contentsChangedListener;
    }

    static /* synthetic */ CraftingResultInventory method_30960(StonecutterScreenHandler stonecutterScreenHandler) {
        return stonecutterScreenHandler.output;
    }

    static /* synthetic */ void method_17860(StonecutterScreenHandler stonecutterScreenHandler) {
        stonecutterScreenHandler.populateResult();
    }

    static /* synthetic */ long method_17861(StonecutterScreenHandler stonecutterScreenHandler) {
        return stonecutterScreenHandler.lastTakeTime;
    }

    static /* synthetic */ long method_17858(StonecutterScreenHandler stonecutterScreenHandler, long l) {
        stonecutterScreenHandler.lastTakeTime = l;
        return stonecutterScreenHandler.lastTakeTime;
    }
}

