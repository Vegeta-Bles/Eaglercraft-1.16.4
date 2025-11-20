package net.minecraft.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.util.collection.DefaultedList;

public class SimpleInventory implements Inventory, RecipeInputProvider {
   private final int size;
   private final DefaultedList<ItemStack> stacks;
   private List<InventoryChangedListener> listeners;

   public SimpleInventory(int size) {
      this.size = size;
      this.stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
   }

   public SimpleInventory(ItemStack... items) {
      this.size = items.length;
      this.stacks = DefaultedList.copyOf(ItemStack.EMPTY, items);
   }

   public void addListener(InventoryChangedListener listener) {
      if (this.listeners == null) {
         this.listeners = Lists.newArrayList();
      }

      this.listeners.add(listener);
   }

   public void removeListener(InventoryChangedListener listener) {
      this.listeners.remove(listener);
   }

   @Override
   public ItemStack getStack(int slot) {
      return slot >= 0 && slot < this.stacks.size() ? this.stacks.get(slot) : ItemStack.EMPTY;
   }

   public List<ItemStack> clearToList() {
      List<ItemStack> _snowman = this.stacks.stream().filter(_snowmanx -> !_snowmanx.isEmpty()).collect(Collectors.toList());
      this.clear();
      return _snowman;
   }

   @Override
   public ItemStack removeStack(int slot, int amount) {
      ItemStack _snowman = Inventories.splitStack(this.stacks, slot, amount);
      if (!_snowman.isEmpty()) {
         this.markDirty();
      }

      return _snowman;
   }

   public ItemStack removeItem(Item item, int count) {
      ItemStack _snowman = new ItemStack(item, 0);

      for (int _snowmanx = this.size - 1; _snowmanx >= 0; _snowmanx--) {
         ItemStack _snowmanxx = this.getStack(_snowmanx);
         if (_snowmanxx.getItem().equals(item)) {
            int _snowmanxxx = count - _snowman.getCount();
            ItemStack _snowmanxxxx = _snowmanxx.split(_snowmanxxx);
            _snowman.increment(_snowmanxxxx.getCount());
            if (_snowman.getCount() == count) {
               break;
            }
         }
      }

      if (!_snowman.isEmpty()) {
         this.markDirty();
      }

      return _snowman;
   }

   public ItemStack addStack(ItemStack stack) {
      ItemStack _snowman = stack.copy();
      this.addToExistingSlot(_snowman);
      if (_snowman.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.addToNewSlot(_snowman);
         return _snowman.isEmpty() ? ItemStack.EMPTY : _snowman;
      }
   }

   public boolean canInsert(ItemStack stack) {
      boolean _snowman = false;

      for (ItemStack _snowmanx : this.stacks) {
         if (_snowmanx.isEmpty() || this.canCombine(_snowmanx, stack) && _snowmanx.getCount() < _snowmanx.getMaxCount()) {
            _snowman = true;
            break;
         }
      }

      return _snowman;
   }

   @Override
   public ItemStack removeStack(int slot) {
      ItemStack _snowman = this.stacks.get(slot);
      if (_snowman.isEmpty()) {
         return ItemStack.EMPTY;
      } else {
         this.stacks.set(slot, ItemStack.EMPTY);
         return _snowman;
      }
   }

   @Override
   public void setStack(int slot, ItemStack stack) {
      this.stacks.set(slot, stack);
      if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
         stack.setCount(this.getMaxCountPerStack());
      }

      this.markDirty();
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack _snowman : this.stacks) {
         if (!_snowman.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public void markDirty() {
      if (this.listeners != null) {
         for (InventoryChangedListener _snowman : this.listeners) {
            _snowman.onInventoryChanged(this);
         }
      }
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return true;
   }

   @Override
   public void clear() {
      this.stacks.clear();
      this.markDirty();
   }

   @Override
   public void provideRecipeInputs(RecipeFinder finder) {
      for (ItemStack _snowman : this.stacks) {
         finder.addItem(_snowman);
      }
   }

   @Override
   public String toString() {
      return this.stacks.stream().filter(_snowman -> !_snowman.isEmpty()).collect(Collectors.toList()).toString();
   }

   private void addToNewSlot(ItemStack stack) {
      for (int _snowman = 0; _snowman < this.size; _snowman++) {
         ItemStack _snowmanx = this.getStack(_snowman);
         if (_snowmanx.isEmpty()) {
            this.setStack(_snowman, stack.copy());
            stack.setCount(0);
            return;
         }
      }
   }

   private void addToExistingSlot(ItemStack stack) {
      for (int _snowman = 0; _snowman < this.size; _snowman++) {
         ItemStack _snowmanx = this.getStack(_snowman);
         if (this.canCombine(_snowmanx, stack)) {
            this.transfer(stack, _snowmanx);
            if (stack.isEmpty()) {
               return;
            }
         }
      }
   }

   private boolean canCombine(ItemStack one, ItemStack two) {
      return one.getItem() == two.getItem() && ItemStack.areTagsEqual(one, two);
   }

   private void transfer(ItemStack source, ItemStack target) {
      int _snowman = Math.min(this.getMaxCountPerStack(), target.getMaxCount());
      int _snowmanx = Math.min(source.getCount(), _snowman - target.getCount());
      if (_snowmanx > 0) {
         target.increment(_snowmanx);
         source.decrement(_snowmanx);
         this.markDirty();
      }
   }

   public void readTags(ListTag tags) {
      for (int _snowman = 0; _snowman < tags.size(); _snowman++) {
         ItemStack _snowmanx = ItemStack.fromTag(tags.getCompound(_snowman));
         if (!_snowmanx.isEmpty()) {
            this.addStack(_snowmanx);
         }
      }
   }

   public ListTag getTags() {
      ListTag _snowman = new ListTag();

      for (int _snowmanx = 0; _snowmanx < this.size(); _snowmanx++) {
         ItemStack _snowmanxx = this.getStack(_snowmanx);
         if (!_snowmanxx.isEmpty()) {
            _snowman.add(_snowmanxx.toTag(new CompoundTag()));
         }
      }

      return _snowman;
   }
}
