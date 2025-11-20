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

   public FurnaceInputSlotFiller(AbstractRecipeScreenHandler<C> arg) {
      super(arg);
   }

   @Override
   protected void fillInputSlots(Recipe<C> arg, boolean craftAll) {
      this.slotMatchesRecipe = this.craftingScreenHandler.matches(arg);
      int i = this.recipeFinder.countRecipeCrafts(arg, null);
      if (this.slotMatchesRecipe) {
         ItemStack lv = this.craftingScreenHandler.getSlot(0).getStack();
         if (lv.isEmpty() || i <= lv.getCount()) {
            return;
         }
      }

      int j = this.getAmountToFill(craftAll, i, this.slotMatchesRecipe);
      IntList intList = new IntArrayList();
      if (this.recipeFinder.findRecipe(arg, intList, j)) {
         if (!this.slotMatchesRecipe) {
            this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
            this.returnSlot(0);
         }

         this.fillInputSlot(j, intList);
      }
   }

   @Override
   protected void returnInputs() {
      this.returnSlot(this.craftingScreenHandler.getCraftingResultSlotIndex());
      super.returnInputs();
   }

   protected void fillInputSlot(int limit, IntList inputs) {
      Iterator<Integer> iterator = inputs.iterator();
      Slot lv = this.craftingScreenHandler.getSlot(0);
      ItemStack lv2 = RecipeFinder.getStackFromId(iterator.next());
      if (!lv2.isEmpty()) {
         int j = Math.min(lv2.getMaxCount(), limit);
         if (this.slotMatchesRecipe) {
            j -= lv.getStack().getCount();
         }

         for (int k = 0; k < j; k++) {
            this.fillInputSlot(lv, lv2);
         }
      }
   }
}
