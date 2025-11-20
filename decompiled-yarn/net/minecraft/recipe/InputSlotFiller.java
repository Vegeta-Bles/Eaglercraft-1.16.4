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

   public InputSlotFiller(AbstractRecipeScreenHandler<C> _snowman) {
      this.craftingScreenHandler = _snowman;
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
      for (int _snowman = 0; _snowman < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; _snowman++) {
         if (_snowman != this.craftingScreenHandler.getCraftingResultSlotIndex()
            || !(this.craftingScreenHandler instanceof CraftingScreenHandler) && !(this.craftingScreenHandler instanceof PlayerScreenHandler)) {
            this.returnSlot(_snowman);
         }
      }

      this.craftingScreenHandler.clearCraftingSlots();
   }

   protected void returnSlot(int _snowman) {
      ItemStack _snowmanx = this.craftingScreenHandler.getSlot(_snowman).getStack();
      if (!_snowmanx.isEmpty()) {
         for (; _snowmanx.getCount() > 0; this.craftingScreenHandler.getSlot(_snowman).takeStack(1)) {
            int _snowmanxx = this.inventory.getOccupiedSlotWithRoomForStack(_snowmanx);
            if (_snowmanxx == -1) {
               _snowmanxx = this.inventory.getEmptySlot();
            }

            ItemStack _snowmanxxx = _snowmanx.copy();
            _snowmanxxx.setCount(1);
            if (!this.inventory.insertStack(_snowmanxx, _snowmanxxx)) {
               LOGGER.error("Can't find any space for item in the inventory");
            }
         }
      }
   }

   protected void fillInputSlots(Recipe<C> _snowman, boolean craftAll) {
      boolean _snowmanx = this.craftingScreenHandler.matches(_snowman);
      int _snowmanxx = this.recipeFinder.countRecipeCrafts(_snowman, null);
      if (_snowmanx) {
         for (int _snowmanxxx = 0; _snowmanxxx < this.craftingScreenHandler.getCraftingHeight() * this.craftingScreenHandler.getCraftingWidth() + 1; _snowmanxxx++) {
            if (_snowmanxxx != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
               ItemStack _snowmanxxxx = this.craftingScreenHandler.getSlot(_snowmanxxx).getStack();
               if (!_snowmanxxxx.isEmpty() && Math.min(_snowmanxx, _snowmanxxxx.getMaxCount()) < _snowmanxxxx.getCount() + 1) {
                  return;
               }
            }
         }
      }

      int _snowmanxxxx = this.getAmountToFill(craftAll, _snowmanxx, _snowmanx);
      IntList _snowmanxxxxx = new IntArrayList();
      if (this.recipeFinder.findRecipe(_snowman, _snowmanxxxxx, _snowmanxxxx)) {
         int _snowmanxxxxxx = _snowmanxxxx;
         IntListIterator var8 = _snowmanxxxxx.iterator();

         while (var8.hasNext()) {
            int _snowmanxxxxxxx = (Integer)var8.next();
            int _snowmanxxxxxxxx = RecipeFinder.getStackFromId(_snowmanxxxxxxx).getMaxCount();
            if (_snowmanxxxxxxxx < _snowmanxxxxxx) {
               _snowmanxxxxxx = _snowmanxxxxxxxx;
            }
         }

         if (this.recipeFinder.findRecipe(_snowman, _snowmanxxxxx, _snowmanxxxxxx)) {
            this.returnInputs();
            this.alignRecipeToGrid(
               this.craftingScreenHandler.getCraftingWidth(),
               this.craftingScreenHandler.getCraftingHeight(),
               this.craftingScreenHandler.getCraftingResultSlotIndex(),
               _snowman,
               _snowmanxxxxx.iterator(),
               _snowmanxxxxxx
            );
         }
      }
   }

   @Override
   public void acceptAlignedInput(Iterator<Integer> inputs, int slot, int amount, int gridX, int gridY) {
      Slot _snowman = this.craftingScreenHandler.getSlot(slot);
      ItemStack _snowmanx = RecipeFinder.getStackFromId(inputs.next());
      if (!_snowmanx.isEmpty()) {
         for (int _snowmanxx = 0; _snowmanxx < amount; _snowmanxx++) {
            this.fillInputSlot(_snowman, _snowmanx);
         }
      }
   }

   protected int getAmountToFill(boolean craftAll, int limit, boolean recipeInCraftingSlots) {
      int _snowman = 1;
      if (craftAll) {
         _snowman = limit;
      } else if (recipeInCraftingSlots) {
         _snowman = 64;

         for (int _snowmanx = 0; _snowmanx < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; _snowmanx++) {
            if (_snowmanx != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
               ItemStack _snowmanxx = this.craftingScreenHandler.getSlot(_snowmanx).getStack();
               if (!_snowmanxx.isEmpty() && _snowman > _snowmanxx.getCount()) {
                  _snowman = _snowmanxx.getCount();
               }
            }
         }

         if (_snowman < 64) {
            _snowman++;
         }
      }

      return _snowman;
   }

   protected void fillInputSlot(Slot _snowman, ItemStack _snowman) {
      int _snowmanxx = this.inventory.method_7371(_snowman);
      if (_snowmanxx != -1) {
         ItemStack _snowmanxxx = this.inventory.getStack(_snowmanxx).copy();
         if (!_snowmanxxx.isEmpty()) {
            if (_snowmanxxx.getCount() > 1) {
               this.inventory.removeStack(_snowmanxx, 1);
            } else {
               this.inventory.removeStack(_snowmanxx);
            }

            _snowmanxxx.setCount(1);
            if (_snowman.getStack().isEmpty()) {
               _snowman.setStack(_snowmanxxx);
            } else {
               _snowman.getStack().increment(1);
            }
         }
      }
   }

   private boolean canReturnInputs() {
      List<ItemStack> _snowman = Lists.newArrayList();
      int _snowmanx = this.getFreeInventorySlots();

      for (int _snowmanxx = 0; _snowmanxx < this.craftingScreenHandler.getCraftingWidth() * this.craftingScreenHandler.getCraftingHeight() + 1; _snowmanxx++) {
         if (_snowmanxx != this.craftingScreenHandler.getCraftingResultSlotIndex()) {
            ItemStack _snowmanxxx = this.craftingScreenHandler.getSlot(_snowmanxx).getStack().copy();
            if (!_snowmanxxx.isEmpty()) {
               int _snowmanxxxx = this.inventory.getOccupiedSlotWithRoomForStack(_snowmanxxx);
               if (_snowmanxxxx == -1 && _snowman.size() <= _snowmanx) {
                  for (ItemStack _snowmanxxxxx : _snowman) {
                     if (_snowmanxxxxx.isItemEqualIgnoreDamage(_snowmanxxx)
                        && _snowmanxxxxx.getCount() != _snowmanxxxxx.getMaxCount()
                        && _snowmanxxxxx.getCount() + _snowmanxxx.getCount() <= _snowmanxxxxx.getMaxCount()) {
                        _snowmanxxxxx.increment(_snowmanxxx.getCount());
                        _snowmanxxx.setCount(0);
                        break;
                     }
                  }

                  if (!_snowmanxxx.isEmpty()) {
                     if (_snowman.size() >= _snowmanx) {
                        return false;
                     }

                     _snowman.add(_snowmanxxx);
                  }
               } else if (_snowmanxxxx == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int getFreeInventorySlots() {
      int _snowman = 0;

      for (ItemStack _snowmanx : this.inventory.main) {
         if (_snowmanx.isEmpty()) {
            _snowman++;
         }
      }

      return _snowman;
   }
}
