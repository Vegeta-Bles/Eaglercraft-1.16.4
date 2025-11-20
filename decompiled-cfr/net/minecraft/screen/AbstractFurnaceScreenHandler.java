/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.FurnaceInputSlotFiller;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

public abstract class AbstractFurnaceScreenHandler
extends AbstractRecipeScreenHandler<Inventory> {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;
    protected final World world;
    private final RecipeType<? extends AbstractCookingRecipe> recipeType;
    private final RecipeBookCategory category;

    protected AbstractFurnaceScreenHandler(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory recipeBookCategory, int n, PlayerInventory playerInventory) {
        this(type, recipeType, recipeBookCategory, n, playerInventory, new SimpleInventory(3), new ArrayPropertyDelegate(4));
    }

    protected AbstractFurnaceScreenHandler(ScreenHandlerType<?> type, RecipeType<? extends AbstractCookingRecipe> recipeType, RecipeBookCategory recipeBookCategory, int n, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate2) {
        super(type, n);
        PropertyDelegate propertyDelegate2;
        int n2;
        this.recipeType = recipeType;
        this.category = recipeBookCategory;
        AbstractFurnaceScreenHandler.checkSize(inventory, 3);
        AbstractFurnaceScreenHandler.checkDataCount(propertyDelegate2, 4);
        this.inventory = inventory;
        this.propertyDelegate = propertyDelegate2;
        this.world = playerInventory.player.world;
        this.addSlot(new Slot(inventory, 0, 56, 17));
        this.addSlot(new FurnaceFuelSlot(this, inventory, 1, 56, 53));
        this.addSlot(new FurnaceOutputSlot(playerInventory.player, inventory, 2, 116, 35));
        for (n2 = 0; n2 < 3; ++n2) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n2 * 9 + 9, 8 + _snowman * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 8 + n2 * 18, 142));
        }
        this.addProperties(propertyDelegate2);
    }

    @Override
    public void populateRecipeFinder(RecipeFinder finder) {
        if (this.inventory instanceof RecipeInputProvider) {
            ((RecipeInputProvider)((Object)this.inventory)).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.inventory.clear();
    }

    @Override
    public void fillInputSlots(boolean craftAll, Recipe<?> recipe, ServerPlayerEntity player) {
        new FurnaceInputSlotFiller<Inventory>(this).fillInputSlots(player, recipe, craftAll);
    }

    @Override
    public boolean matches(Recipe<? super Inventory> recipe) {
        return recipe.matches(this.inventory, this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 2;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 3;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
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
            } else if (index == 1 || index == 0 ? !this.insertItem(_snowman, 3, 39, false) : (this.isSmeltable(_snowman) ? !this.insertItem(_snowman, 0, 1, false) : (this.isFuel(_snowman) ? !this.insertItem(_snowman, 1, 2, false) : (index >= 3 && index < 30 ? !this.insertItem(_snowman, 30, 39, false) : index >= 30 && index < 39 && !this.insertItem(_snowman, 3, 30, false))))) {
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

    protected boolean isSmeltable(ItemStack itemStack) {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, new SimpleInventory(itemStack), this.world).isPresent();
    }

    protected boolean isFuel(ItemStack itemStack) {
        return AbstractFurnaceBlockEntity.canUseAsFuel(itemStack);
    }

    public int getCookProgress() {
        int n = this.propertyDelegate.get(2);
        _snowman = this.propertyDelegate.get(3);
        if (_snowman == 0 || n == 0) {
            return 0;
        }
        return n * 24 / _snowman;
    }

    public int getFuelProgress() {
        int n = this.propertyDelegate.get(1);
        if (n == 0) {
            n = 200;
        }
        return this.propertyDelegate.get(0) * 13 / n;
    }

    public boolean isBurning() {
        return this.propertyDelegate.get(0) > 0;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return this.category;
    }
}

