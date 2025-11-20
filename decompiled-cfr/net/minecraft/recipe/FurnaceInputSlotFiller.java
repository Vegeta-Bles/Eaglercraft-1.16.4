/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 */
package net.minecraft.recipe;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.InputSlotFiller;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FurnaceInputSlotFiller<C extends Inventory>
extends InputSlotFiller<C> {
    private boolean slotMatchesRecipe;

    public FurnaceInputSlotFiller(AbstractRecipeScreenHandler<C> abstractRecipeScreenHandler) {
        super(abstractRecipeScreenHandler);
    }

    @Override
    protected void fillInputSlots(Recipe<C> recipe, boolean craftAll) {
        this.slotMatchesRecipe = this.craftingScreenHandler.matches(recipe);
        int n = this.recipeFinder.countRecipeCrafts(recipe, null);
        if (this.slotMatchesRecipe && ((_snowman = this.craftingScreenHandler.getSlot(0).getStack()).isEmpty() || n <= _snowman.getCount())) {
            return;
        }
        IntArrayList _snowman2 = new IntArrayList();
        _snowman = this.getAmountToFill(craftAll, n, this.slotMatchesRecipe);
        if (!this.recipeFinder.findRecipe(recipe, (IntList)_snowman2, _snowman)) {
            return;
        }
        if (!this.slotMatchesRecipe) {
            this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
            this.returnSlot(0);
        }
        this.fillInputSlot(_snowman, (IntList)_snowman2);
    }

    @Override
    protected void returnInputs() {
        this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
        super.returnInputs();
    }

    protected void fillInputSlot(int limit, IntList inputs) {
        IntListIterator intListIterator = inputs.iterator();
        Slot _snowman2 = this.craftingScreenHandler.getSlot(0);
        ItemStack _snowman3 = RecipeFinder.getStackFromId((Integer)intListIterator.next());
        if (_snowman3.isEmpty()) {
            return;
        }
        int _snowman4 = Math.min(_snowman3.getMaxCount(), limit);
        if (this.slotMatchesRecipe) {
            _snowman4 -= _snowman2.getStack().getCount();
        }
        for (int i = 0; i < _snowman4; ++i) {
            this.fillInputSlot(_snowman2, _snowman3);
        }
    }
}

