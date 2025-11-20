package net.minecraft.recipe;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.CraftFailedResponseS2CPacket;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputSlotFiller<C extends Inventory> implements RecipeGridAligner<Integer> {
   protected static final Logger LOGGER = LogManager.getLogger();
   protected final RecipeFinder recipeFinder = new RecipeFinder();
   protected PlayerInventory inventory;
   protected AbstractRecipeScreenHandler<C> craftingScreenHandler;

   public InputSlotFiller(AbstractRecipeScreenHandler<C> arg) {
      this.craftingScreenHandler = arg;
   }

   public void fillInputSlots(ServerPlayerEntity entity, @Nullable Recipe<C> recipe, boolean craftAll) {
      if (recipe != null && entity.getRecipeBook().contains(recipe)) {
         this.inventory = entity.inventory;
         if (this.canReturnInputs() || entity.isCreative()) {
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
      }
   }

   protected void returnInputs() {
      for (int i = 0; i < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; i++) {
         if (i != this.craftingScreenHandler.getCraftingResultSlotIndex()
            || !(this.craftingScreenHandler instanceof CraftingScreenHandler) && !(this.craftingScreenHandler instanceof PlayerScreenHandler)) {
            this.returnSlot(i);
         }
      }

      this.craftingScreenHandler.clearCraftingSlots();
   }

   protected void returnSlot(int i) {
      ItemStack lv = this.craftingScreenHandler.getSlot(i).getStack();
      if (!lv.isEmpty()) {
         for (; lv.getCount() > 0; this.craftingScreenHandler.getSlot(i).takeStack(1)) {
            int j = this.inventory.getOccupiedSlotWithRoomForStack(lv);
            if (j == -1) {
               j = this.inventory.getEmptySlot();
            }

            ItemStack lv2 = lv.copy();
            lv2.setCount(1);
            if (!this.inventory.insertStack(j, lv2)) {
               LOGGER.error("Can't find any space for item in the inventory");
            }
         }
      }
   }

   protected void fillInputSlots(Recipe<C> arg, boolean craftAll) {
      boolean bl2 = this.craftingScreenHandler.matches(arg);
      int i = this.recipeFinder.countRecipeCrafts(arg, null);
      if (bl2) {
         for (int j = 0; j < this.craftingScreenHandler.getCraftingHeight() * this.craftingScreenHandler.getCraftingWidth() + 1; j++) {
            if (j != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
               ItemStack lv = this.craftingScreenHandler.getSlot(j).getStack();
               if (!lv.isEmpty() && Math.min(i, lv.getMaxCount()) < lv.getCount() + 1) {
                  return;
               }
            }
         }
      }

      int k = this.getAmountToFill(craftAll, i, bl2);
      IntList intList = new IntArrayList();
      if (this.recipeFinder.findRecipe(arg, intList, k)) {
         int l = k;
         IntListIterator var8 = intList.iterator();

         while (var8.hasNext()) {
            int m = (Integer)var8.next();
            int n = RecipeFinder.getStackFromId(m).getMaxCount();
            if (n < l) {
               l = n;
            }
         }

         if (this.recipeFinder.findRecipe(arg, intList, l)) {
            this.returnInputs();
            this.alignRecipeToGrid(
               this.craftingScreenHandler.getCraftingWidth(),
               this.craftingScreenHandler.getCraftingHeight(),
               this.craftingScreenHandler.getCraftingResultSlotIndex(),
               arg,
               intList.iterator(),
               l
            );
         }
      }
   }

   @Override
   public void acceptAlignedInput(Iterator<Integer> inputs, int slot, int amount, int gridX, int gridY) {
      Slot lv = this.craftingScreenHandler.getSlot(slot);
      ItemStack lv2 = RecipeFinder.getStackFromId(inputs.next());
      if (!lv2.isEmpty()) {
         for (int m = 0; m < amount; m++) {
            this.fillInputSlot(lv, lv2);
         }
      }
   }

   protected int getAmountToFill(boolean craftAll, int limit, boolean recipeInCraftingSlots) {
      int j = 1;
      if (craftAll) {
         j = limit;
      } else if (recipeInCraftingSlots) {
         j = 64;

         for (int k = 0; k < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; k++) {
            if (k != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
               ItemStack lv = this.craftingScreenHandler.getSlot(k).getStack();
               if (!lv.isEmpty() && j > lv.getCount()) {
                  j = lv.getCount();
               }
            }
         }

         if (j < 64) {
            j++;
         }
      }

      return j;
   }

   protected void fillInputSlot(Slot arg, ItemStack arg2) {
      int i = this.inventory.method_7371(arg2);
      if (i != -1) {
         ItemStack lv = this.inventory.getStack(i).copy();
         if (!lv.isEmpty()) {
            if (lv.getCount() > 1) {
               this.inventory.removeStack(i, 1);
            } else {
               this.inventory.removeStack(i);
            }

            lv.setCount(1);
            if (arg.getStack().isEmpty()) {
               arg.setStack(lv);
            } else {
               arg.getStack().increment(1);
            }
         }
      }
   }

   private boolean canReturnInputs() {
      List<ItemStack> list = Lists.newArrayList();
      int i = this.getFreeInventorySlots();

      for (int j = 0; j < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; j++) {
         if (j != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
            ItemStack lv = this.craftingScreenHandler.getSlot(j).getStack().copy();
            if (!lv.isEmpty()) {
               int k = this.inventory.getOccupiedSlotWithRoomForStack(lv);
               if (k == -1 && list.size() <= i) {
                  for (ItemStack lv2 : list) {
                     if (lv2.isItemEqualIgnoreDamage(lv) && lv2.getCount() != lv2.getMaxCount() && lv2.getCount() + lv.getCount() <= lv2.getMaxCount()) {
                        lv2.increment(lv.getCount());
                        lv.setCount(0);
                        break;
                     }
                  }

                  if (!lv.isEmpty()) {
                     if (list.size() >= i) {
                        return false;
                     }

                     list.add(lv);
                  }
               } else if (k == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int getFreeInventorySlots() {
      int i = 0;

      for (ItemStack lv : this.inventory.main) {
         if (lv.isEmpty()) {
            i++;
         }
      }

      return i;
   }
}
