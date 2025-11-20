/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntListIterator
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.recipe;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.CraftFailedResponseS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeGridAligner;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputSlotFiller<C extends Inventory>
implements RecipeGridAligner<Integer> {
    protected static final Logger LOGGER = LogManager.getLogger();
    protected final RecipeFinder recipeFinder = new RecipeFinder();
    protected PlayerInventory inventory;
    protected AbstractRecipeScreenHandler<C> craftingScreenHandler;

    public InputSlotFiller(AbstractRecipeScreenHandler<C> abstractRecipeScreenHandler) {
        this.craftingScreenHandler = abstractRecipeScreenHandler;
    }

    public void fillInputSlots(ServerPlayerEntity entity, @Nullable Recipe<C> recipe, boolean craftAll) {
        if (recipe == null || !entity.getRecipeBook().contains(recipe)) {
            return;
        }
        this.inventory = entity.inventory;
        if (!this.canReturnInputs() && !entity.isCreative()) {
            return;
        }
        this.recipeFinder.clear();
        entity.inventory.populateRecipeFinder(this.recipeFinder);
        this.craftingScreenHandler.populateRecipeFinder(this.recipeFinder);
        if (this.recipeFinder.findRecipe(recipe, null)) {
            this.fillInputSlots(recipe, craftAll);
        } else {
            this.returnInputs();
            entity.networkHandler.sendPacket(new CraftFailedResponseS2CPacket(entity.currentScreenHandler.syncId, recipe));
        }
        entity.inventory.markDirty();
    }

    protected void returnInputs() {
        for (int i = 0; i < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; ++i) {
            if (i == this.craftingScreenHandler.getCraftingResultSlotIndex() && (this.craftingScreenHandler instanceof CraftingScreenHandler || this.craftingScreenHandler instanceof PlayerScreenHandler)) continue;
            this.returnSlot(i);
        }
        this.craftingScreenHandler.clearCraftingSlots();
    }

    protected void returnSlot(int n) {
        ItemStack itemStack = this.craftingScreenHandler.getSlot(n).getStack();
        if (itemStack.isEmpty()) {
            return;
        }
        while (itemStack.getCount() > 0) {
            int n2 = this.inventory.getOccupiedSlotWithRoomForStack(itemStack);
            if (n2 == -1) {
                n2 = this.inventory.getEmptySlot();
            }
            ItemStack _snowman2 = itemStack.copy();
            _snowman2.setCount(1);
            if (!this.inventory.insertStack(n2, _snowman2)) {
                LOGGER.error("Can't find any space for item in the inventory");
            }
            this.craftingScreenHandler.getSlot(n).takeStack(1);
        }
    }

    protected void fillInputSlots(Recipe<C> recipe2, boolean craftAll) {
        Recipe<C> recipe2;
        boolean bl = this.craftingScreenHandler.matches(recipe2);
        int _snowman2 = this.recipeFinder.countRecipeCrafts(recipe2, null);
        if (bl) {
            for (int i = 0; i < this.craftingScreenHandler.getCraftingHeight() * this.craftingScreenHandler.getCraftingWidth() + 1; ++i) {
                if (i == this.craftingScreenHandler.getCraftingResultSlotIndex() || ((ItemStack)(_snowman = this.craftingScreenHandler.getSlot(i).getStack())).isEmpty() || Math.min(_snowman2, ((ItemStack)_snowman).getMaxCount()) >= ((ItemStack)_snowman).getCount() + 1) continue;
                return;
            }
        }
        if (this.recipeFinder.findRecipe(recipe2, (IntList)(_snowman = new IntArrayList()), i = this.getAmountToFill(craftAll, _snowman2, bl))) {
            int n = i;
            IntListIterator intListIterator = _snowman.iterator();
            while (intListIterator.hasNext()) {
                _snowman = (Integer)intListIterator.next();
                _snowman = RecipeFinder.getStackFromId(_snowman).getMaxCount();
                if (_snowman >= n) continue;
                n = _snowman;
            }
            i = n;
            if (this.recipeFinder.findRecipe(recipe2, (IntList)_snowman, i)) {
                this.returnInputs();
                this.alignRecipeToGrid(this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.craftingScreenHandler.getCraftingResultSlotIndex(), recipe2, _snowman.iterator(), i);
            }
        }
    }

    @Override
    public void acceptAlignedInput(Iterator<Integer> inputs, int slot, int amount, int gridX, int gridY) {
        Slot slot2 = this.craftingScreenHandler.getSlot(slot);
        ItemStack _snowman2 = RecipeFinder.getStackFromId(inputs.next());
        if (!_snowman2.isEmpty()) {
            for (int i = 0; i < amount; ++i) {
                this.fillInputSlot(slot2, _snowman2);
            }
        }
    }

    protected int getAmountToFill(boolean craftAll, int limit, boolean recipeInCraftingSlots) {
        int n = 1;
        if (craftAll) {
            n = limit;
        } else if (recipeInCraftingSlots) {
            n = 64;
            for (_snowman = 0; _snowman < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; ++_snowman) {
                if (_snowman == this.craftingScreenHandler.getCraftingResultSlotIndex() || (_snowman = this.craftingScreenHandler.getSlot(_snowman).getStack()).isEmpty() || n <= _snowman.getCount()) continue;
                n = _snowman.getCount();
            }
            if (n < 64) {
                ++n;
            }
        }
        return n;
    }

    protected void fillInputSlot(Slot slot, ItemStack itemStack) {
        int n = this.inventory.method_7371(itemStack);
        if (n == -1) {
            return;
        }
        ItemStack _snowman2 = this.inventory.getStack(n).copy();
        if (_snowman2.isEmpty()) {
            return;
        }
        if (_snowman2.getCount() > 1) {
            this.inventory.removeStack(n, 1);
        } else {
            this.inventory.removeStack(n);
        }
        _snowman2.setCount(1);
        if (slot.getStack().isEmpty()) {
            slot.setStack(_snowman2);
        } else {
            slot.getStack().increment(1);
        }
    }

    private boolean canReturnInputs() {
        ArrayList arrayList = Lists.newArrayList();
        int _snowman2 = this.getFreeInventorySlots();
        for (int i = 0; i < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; ++i) {
            int n;
            if (i == this.craftingScreenHandler.getCraftingResultSlotIndex() || (itemStack = this.craftingScreenHandler.getSlot(i).getStack().copy()).isEmpty()) continue;
            n = this.inventory.getOccupiedSlotWithRoomForStack(itemStack);
            if (n == -1 && arrayList.size() <= _snowman2) {
                ItemStack itemStack;
                for (ItemStack itemStack2 : arrayList) {
                    if (!itemStack2.isItemEqualIgnoreDamage(itemStack) || itemStack2.getCount() == itemStack2.getMaxCount() || itemStack2.getCount() + itemStack.getCount() > itemStack2.getMaxCount()) continue;
                    itemStack2.increment(itemStack.getCount());
                    itemStack.setCount(0);
                    break;
                }
                if (itemStack.isEmpty()) continue;
                if (arrayList.size() < _snowman2) {
                    arrayList.add(itemStack);
                    continue;
                }
                return false;
            }
            if (n != -1) continue;
            return false;
        }
        return true;
    }

    private int getFreeInventorySlots() {
        int n = 0;
        for (ItemStack itemStack : this.inventory.main) {
            if (!itemStack.isEmpty()) continue;
            ++n;
        }
        return n;
    }
}

