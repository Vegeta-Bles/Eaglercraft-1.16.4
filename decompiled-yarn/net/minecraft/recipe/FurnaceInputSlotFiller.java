package net.minecraft.recipe;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Iterator;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FurnaceInputSlotFiller<C extends Inventory> extends InputSlotFiller<C> {
   private boolean slotMatchesRecipe;

   public FurnaceInputSlotFiller(AbstractRecipeScreenHandler<C> _snowman) {
      super(_snowman);
   }

   @Override
   protected void fillInputSlots(Recipe<C> _snowman, boolean craftAll) {
      this.slotMatchesRecipe = this.craftingScreenHandler.matches(_snowman);
      int _snowmanx = this.recipeFinder.countRecipeCrafts(_snowman, null);
      if (this.slotMatchesRecipe) {
         ItemStack _snowmanxx = this.craftingScreenHandler.getSlot(0).getStack();
         if (_snowmanxx.isEmpty() || _snowmanx <= _snowmanxx.getCount()) {
            return;
         }
      }

      int _snowmanxx = this.getAmountToFill(craftAll, _snowmanx, this.slotMatchesRecipe);
      IntList _snowmanxxx = new IntArrayList();
      if (this.recipeFinder.findRecipe(_snowman, _snowmanxxx, _snowmanxx)) {
         if (!this.slotMatchesRecipe) {
            this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
            this.returnSlot(0);
         }

         this.fillInputSlot(_snowmanxx, _snowmanxxx);
      }
   }

   @Override
   protected void returnInputs() {
      this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
      super.returnInputs();
   }

   protected void fillInputSlot(int limit, IntList inputs) {
      Iterator<Integer> _snowman = inputs.iterator();
      Slot _snowmanx = this.craftingScreenHandler.getSlot(0);
      ItemStack _snowmanxx = RecipeFinder.getStackFromId(_snowman.next());
      if (!_snowmanxx.isEmpty()) {
         int _snowmanxxx = Math.min(_snowmanxx.getMaxCount(), limit);
         if (this.slotMatchesRecipe) {
            _snowmanxxx -= _snowmanx.getStack().getCount();
         }

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
            this.fillInputSlot(_snowmanx, _snowmanxx);
         }
      }
   }
}
