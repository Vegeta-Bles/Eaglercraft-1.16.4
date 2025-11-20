package net.minecraft.screen;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public abstract class ScreenHandler {
   private final DefaultedList<ItemStack> trackedStacks = DefaultedList.of();
   public final List<Slot> slots = Lists.newArrayList();
   private final List<Property> properties = Lists.newArrayList();
   @Nullable
   private final ScreenHandlerType<?> type;
   public final int syncId;
   private short actionId;
   private int quickCraftStage = -1;
   private int quickCraftButton;
   private final Set<Slot> quickCraftSlots = Sets.newHashSet();
   private final List<ScreenHandlerListener> listeners = Lists.newArrayList();
   private final Set<PlayerEntity> restrictedPlayers = Sets.newHashSet();

   protected ScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
      this.type = type;
      this.syncId = syncId;
   }

   protected static boolean canUse(ScreenHandlerContext context, PlayerEntity player, Block block) {
      return context.run(
         (_snowmanxx, _snowmanxxx) -> !_snowmanxx.getBlockState(_snowmanxxx).isOf(block)
               ? false
               : player.squaredDistanceTo((double)_snowmanxxx.getX() + 0.5, (double)_snowmanxxx.getY() + 0.5, (double)_snowmanxxx.getZ() + 0.5) <= 64.0,
         true
      );
   }

   public ScreenHandlerType<?> getType() {
      if (this.type == null) {
         throw new UnsupportedOperationException("Unable to construct this menu by type");
      } else {
         return this.type;
      }
   }

   protected static void checkSize(Inventory inventory, int expectedSize) {
      int _snowman = inventory.size();
      if (_snowman < expectedSize) {
         throw new IllegalArgumentException("Container size " + _snowman + " is smaller than expected " + expectedSize);
      }
   }

   protected static void checkDataCount(PropertyDelegate data, int expectedCount) {
      int _snowman = data.size();
      if (_snowman < expectedCount) {
         throw new IllegalArgumentException("Container data count " + _snowman + " is smaller than expected " + expectedCount);
      }
   }

   protected Slot addSlot(Slot slot) {
      slot.id = this.slots.size();
      this.slots.add(slot);
      this.trackedStacks.add(ItemStack.EMPTY);
      return slot;
   }

   protected Property addProperty(Property _snowman) {
      this.properties.add(_snowman);
      return _snowman;
   }

   protected void addProperties(PropertyDelegate _snowman) {
      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.addProperty(Property.create(_snowman, _snowmanx));
      }
   }

   public void addListener(ScreenHandlerListener listener) {
      if (!this.listeners.contains(listener)) {
         this.listeners.add(listener);
         listener.onHandlerRegistered(this, this.getStacks());
         this.sendContentUpdates();
      }
   }

   public void removeListener(ScreenHandlerListener listener) {
      this.listeners.remove(listener);
   }

   public DefaultedList<ItemStack> getStacks() {
      DefaultedList<ItemStack> _snowman = DefaultedList.of();

      for (int _snowmanx = 0; _snowmanx < this.slots.size(); _snowmanx++) {
         _snowman.add(this.slots.get(_snowmanx).getStack());
      }

      return _snowman;
   }

   public void sendContentUpdates() {
      for (int _snowman = 0; _snowman < this.slots.size(); _snowman++) {
         ItemStack _snowmanx = this.slots.get(_snowman).getStack();
         ItemStack _snowmanxx = this.trackedStacks.get(_snowman);
         if (!ItemStack.areEqual(_snowmanxx, _snowmanx)) {
            ItemStack _snowmanxxx = _snowmanx.copy();
            this.trackedStacks.set(_snowman, _snowmanxxx);

            for (ScreenHandlerListener _snowmanxxxx : this.listeners) {
               _snowmanxxxx.onSlotUpdate(this, _snowman, _snowmanxxx);
            }
         }
      }

      for (int _snowmanx = 0; _snowmanx < this.properties.size(); _snowmanx++) {
         Property _snowmanxx = this.properties.get(_snowmanx);
         if (_snowmanxx.hasChanged()) {
            for (ScreenHandlerListener _snowmanxxx : this.listeners) {
               _snowmanxxx.onPropertyUpdate(this, _snowmanx, _snowmanxx.get());
            }
         }
      }
   }

   public boolean onButtonClick(PlayerEntity player, int id) {
      return false;
   }

   public Slot getSlot(int index) {
      return this.slots.get(index);
   }

   public ItemStack transferSlot(PlayerEntity player, int index) {
      Slot _snowman = this.slots.get(index);
      return _snowman != null ? _snowman.getStack() : ItemStack.EMPTY;
   }

   public ItemStack onSlotClick(int _snowman, int _snowman, SlotActionType actionType, PlayerEntity _snowman) {
      try {
         return this.method_30010(_snowman, _snowman, actionType, _snowman);
      } catch (Exception var8) {
         CrashReport _snowmanxxx = CrashReport.create(var8, "Container click");
         CrashReportSection _snowmanxxxx = _snowmanxxx.addElement("Click info");
         _snowmanxxxx.add("Menu Type", () -> this.type != null ? Registry.SCREEN_HANDLER.getId(this.type).toString() : "<no type>");
         _snowmanxxxx.add("Menu Class", () -> this.getClass().getCanonicalName());
         _snowmanxxxx.add("Slot Count", this.slots.size());
         _snowmanxxxx.add("Slot", _snowman);
         _snowmanxxxx.add("Button", _snowman);
         _snowmanxxxx.add("Type", actionType);
         throw new CrashException(_snowmanxxx);
      }
   }

   private ItemStack method_30010(int _snowman, int _snowman, SlotActionType _snowman, PlayerEntity _snowman) {
      ItemStack _snowmanxxxx = ItemStack.EMPTY;
      PlayerInventory _snowmanxxxxx = _snowman.inventory;
      if (_snowman == SlotActionType.QUICK_CRAFT) {
         int _snowmanxxxxxx = this.quickCraftButton;
         this.quickCraftButton = unpackQuickCraftStage(_snowman);
         if ((_snowmanxxxxxx != 1 || this.quickCraftButton != 2) && _snowmanxxxxxx != this.quickCraftButton) {
            this.endQuickCraft();
         } else if (_snowmanxxxxx.getCursorStack().isEmpty()) {
            this.endQuickCraft();
         } else if (this.quickCraftButton == 0) {
            this.quickCraftStage = unpackQuickCraftButton(_snowman);
            if (shouldQuickCraftContinue(this.quickCraftStage, _snowman)) {
               this.quickCraftButton = 1;
               this.quickCraftSlots.clear();
            } else {
               this.endQuickCraft();
            }
         } else if (this.quickCraftButton == 1) {
            Slot _snowmanxxxxxxx = this.slots.get(_snowman);
            ItemStack _snowmanxxxxxxxx = _snowmanxxxxx.getCursorStack();
            if (_snowmanxxxxxxx != null
               && canInsertItemIntoSlot(_snowmanxxxxxxx, _snowmanxxxxxxxx, true)
               && _snowmanxxxxxxx.canInsert(_snowmanxxxxxxxx)
               && (this.quickCraftStage == 2 || _snowmanxxxxxxxx.getCount() > this.quickCraftSlots.size())
               && this.canInsertIntoSlot(_snowmanxxxxxxx)) {
               this.quickCraftSlots.add(_snowmanxxxxxxx);
            }
         } else if (this.quickCraftButton == 2) {
            if (!this.quickCraftSlots.isEmpty()) {
               ItemStack _snowmanxxxxxxx = _snowmanxxxxx.getCursorStack().copy();
               int _snowmanxxxxxxxx = _snowmanxxxxx.getCursorStack().getCount();

               for (Slot _snowmanxxxxxxxxx : this.quickCraftSlots) {
                  ItemStack _snowmanxxxxxxxxxx = _snowmanxxxxx.getCursorStack();
                  if (_snowmanxxxxxxxxx != null
                     && canInsertItemIntoSlot(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, true)
                     && _snowmanxxxxxxxxx.canInsert(_snowmanxxxxxxxxxx)
                     && (this.quickCraftStage == 2 || _snowmanxxxxxxxxxx.getCount() >= this.quickCraftSlots.size())
                     && this.canInsertIntoSlot(_snowmanxxxxxxxxx)) {
                     ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.copy();
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.hasStack() ? _snowmanxxxxxxxxx.getStack().getCount() : 0;
                     calculateStackSize(this.quickCraftSlots, this.quickCraftStage, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
                     int _snowmanxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxx.getMaxCount(), _snowmanxxxxxxxxx.getMaxItemCount(_snowmanxxxxxxxxxxx));
                     if (_snowmanxxxxxxxxxxx.getCount() > _snowmanxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxx.setCount(_snowmanxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxxxx -= _snowmanxxxxxxxxxxx.getCount() - _snowmanxxxxxxxxxxxx;
                     _snowmanxxxxxxxxx.setStack(_snowmanxxxxxxxxxxx);
                  }
               }

               _snowmanxxxxxxx.setCount(_snowmanxxxxxxxx);
               _snowmanxxxxx.setCursorStack(_snowmanxxxxxxx);
            }

            this.endQuickCraft();
         } else {
            this.endQuickCraft();
         }
      } else if (this.quickCraftButton != 0) {
         this.endQuickCraft();
      } else if ((_snowman == SlotActionType.PICKUP || _snowman == SlotActionType.QUICK_MOVE) && (_snowman == 0 || _snowman == 1)) {
         if (_snowman == -999) {
            if (!_snowmanxxxxx.getCursorStack().isEmpty()) {
               if (_snowman == 0) {
                  _snowman.dropItem(_snowmanxxxxx.getCursorStack(), true);
                  _snowmanxxxxx.setCursorStack(ItemStack.EMPTY);
               }

               if (_snowman == 1) {
                  _snowman.dropItem(_snowmanxxxxx.getCursorStack().split(1), true);
               }
            }
         } else if (_snowman == SlotActionType.QUICK_MOVE) {
            if (_snowman < 0) {
               return ItemStack.EMPTY;
            }

            Slot _snowmanxxxxxx = this.slots.get(_snowman);
            if (_snowmanxxxxxx == null || !_snowmanxxxxxx.canTakeItems(_snowman)) {
               return ItemStack.EMPTY;
            }

            for (ItemStack _snowmanxxxxxxx = this.transferSlot(_snowman, _snowman);
               !_snowmanxxxxxxx.isEmpty() && ItemStack.areItemsEqualIgnoreDamage(_snowmanxxxxxx.getStack(), _snowmanxxxxxxx);
               _snowmanxxxxxxx = this.transferSlot(_snowman, _snowman)
            ) {
               _snowmanxxxx = _snowmanxxxxxxx.copy();
            }
         } else {
            if (_snowman < 0) {
               return ItemStack.EMPTY;
            }

            Slot _snowmanxxxxxx = this.slots.get(_snowman);
            if (_snowmanxxxxxx != null) {
               ItemStack _snowmanxxxxxxx = _snowmanxxxxxx.getStack();
               ItemStack _snowmanxxxxxxxx = _snowmanxxxxx.getCursorStack();
               if (!_snowmanxxxxxxx.isEmpty()) {
                  _snowmanxxxx = _snowmanxxxxxxx.copy();
               }

               if (_snowmanxxxxxxx.isEmpty()) {
                  if (!_snowmanxxxxxxxx.isEmpty() && _snowmanxxxxxx.canInsert(_snowmanxxxxxxxx)) {
                     int _snowmanxxxxxxxxxx = _snowman == 0 ? _snowmanxxxxxxxx.getCount() : 1;
                     if (_snowmanxxxxxxxxxx > _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxx)) {
                        _snowmanxxxxxxxxxx = _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxx);
                     }

                     _snowmanxxxxxx.setStack(_snowmanxxxxxxxx.split(_snowmanxxxxxxxxxx));
                  }
               } else if (_snowmanxxxxxx.canTakeItems(_snowman)) {
                  if (_snowmanxxxxxxxx.isEmpty()) {
                     if (_snowmanxxxxxxx.isEmpty()) {
                        _snowmanxxxxxx.setStack(ItemStack.EMPTY);
                        _snowmanxxxxx.setCursorStack(ItemStack.EMPTY);
                     } else {
                        int _snowmanxxxxxxxxxx = _snowman == 0 ? _snowmanxxxxxxx.getCount() : (_snowmanxxxxxxx.getCount() + 1) / 2;
                        _snowmanxxxxx.setCursorStack(_snowmanxxxxxx.takeStack(_snowmanxxxxxxxxxx));
                        if (_snowmanxxxxxxx.isEmpty()) {
                           _snowmanxxxxxx.setStack(ItemStack.EMPTY);
                        }

                        _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxx.getCursorStack());
                     }
                  } else if (_snowmanxxxxxx.canInsert(_snowmanxxxxxxxx)) {
                     if (canStacksCombine(_snowmanxxxxxxx, _snowmanxxxxxxxx)) {
                        int _snowmanxxxxxxxxxx = _snowman == 0 ? _snowmanxxxxxxxx.getCount() : 1;
                        if (_snowmanxxxxxxxxxx > _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxx) - _snowmanxxxxxxx.getCount()) {
                           _snowmanxxxxxxxxxx = _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxx) - _snowmanxxxxxxx.getCount();
                        }

                        if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxx.getMaxCount() - _snowmanxxxxxxx.getCount()) {
                           _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getMaxCount() - _snowmanxxxxxxx.getCount();
                        }

                        _snowmanxxxxxxxx.decrement(_snowmanxxxxxxxxxx);
                        _snowmanxxxxxxx.increment(_snowmanxxxxxxxxxx);
                     } else if (_snowmanxxxxxxxx.getCount() <= _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxx)) {
                        _snowmanxxxxxx.setStack(_snowmanxxxxxxxx);
                        _snowmanxxxxx.setCursorStack(_snowmanxxxxxxx);
                     }
                  } else if (_snowmanxxxxxxxx.getMaxCount() > 1 && canStacksCombine(_snowmanxxxxxxx, _snowmanxxxxxxxx) && !_snowmanxxxxxxx.isEmpty()) {
                     int _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.getCount();
                     if (_snowmanxxxxxxxxxxx + _snowmanxxxxxxxx.getCount() <= _snowmanxxxxxxxx.getMaxCount()) {
                        _snowmanxxxxxxxx.increment(_snowmanxxxxxxxxxxx);
                        _snowmanxxxxxxx = _snowmanxxxxxx.takeStack(_snowmanxxxxxxxxxxx);
                        if (_snowmanxxxxxxx.isEmpty()) {
                           _snowmanxxxxxx.setStack(ItemStack.EMPTY);
                        }

                        _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxx.getCursorStack());
                     }
                  }
               }

               _snowmanxxxxxx.markDirty();
            }
         }
      } else if (_snowman == SlotActionType.SWAP) {
         Slot _snowmanxxxxxx = this.slots.get(_snowman);
         ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxx.getStack(_snowman);
         ItemStack _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.getStack();
         if (!_snowmanxxxxxxxxxxx.isEmpty() || !_snowmanxxxxxxxxxxxx.isEmpty()) {
            if (_snowmanxxxxxxxxxxx.isEmpty()) {
               if (_snowmanxxxxxx.canTakeItems(_snowman)) {
                  _snowmanxxxxx.setStack(_snowman, _snowmanxxxxxxxxxxxx);
                  _snowmanxxxxxx.onTake(_snowmanxxxxxxxxxxxx.getCount());
                  _snowmanxxxxxx.setStack(ItemStack.EMPTY);
                  _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxxxxxxxxx);
               }
            } else if (_snowmanxxxxxxxxxxxx.isEmpty()) {
               if (_snowmanxxxxxx.canInsert(_snowmanxxxxxxxxxxx)) {
                  int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxx.getCount() > _snowmanxxxxxxxxxxxxx) {
                     _snowmanxxxxxx.setStack(_snowmanxxxxxxxxxxx.split(_snowmanxxxxxxxxxxxxx));
                  } else {
                     _snowmanxxxxxx.setStack(_snowmanxxxxxxxxxxx);
                     _snowmanxxxxx.setStack(_snowman, ItemStack.EMPTY);
                  }
               }
            } else if (_snowmanxxxxxx.canTakeItems(_snowman) && _snowmanxxxxxx.canInsert(_snowmanxxxxxxxxxxx)) {
               int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx.getMaxItemCount(_snowmanxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxx.getCount() > _snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxx.setStack(_snowmanxxxxxxxxxxx.split(_snowmanxxxxxxxxxxxxx));
                  _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxxxxxxxxx);
                  if (!_snowmanxxxxx.insertStack(_snowmanxxxxxxxxxxxx)) {
                     _snowman.dropItem(_snowmanxxxxxxxxxxxx, true);
                  }
               } else {
                  _snowmanxxxxxx.setStack(_snowmanxxxxxxxxxxx);
                  _snowmanxxxxx.setStack(_snowman, _snowmanxxxxxxxxxxxx);
                  _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxxxxxxxxx);
               }
            }
         }
      } else if (_snowman == SlotActionType.CLONE && _snowman.abilities.creativeMode && _snowmanxxxxx.getCursorStack().isEmpty() && _snowman >= 0) {
         Slot _snowmanxxxxxx = this.slots.get(_snowman);
         if (_snowmanxxxxxx != null && _snowmanxxxxxx.hasStack()) {
            ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxxx.getStack().copy();
            _snowmanxxxxxxxxxxx.setCount(_snowmanxxxxxxxxxxx.getMaxCount());
            _snowmanxxxxx.setCursorStack(_snowmanxxxxxxxxxxx);
         }
      } else if (_snowman == SlotActionType.THROW && _snowmanxxxxx.getCursorStack().isEmpty() && _snowman >= 0) {
         Slot _snowmanxxxxxx = this.slots.get(_snowman);
         if (_snowmanxxxxxx != null && _snowmanxxxxxx.hasStack() && _snowmanxxxxxx.canTakeItems(_snowman)) {
            ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxxx.takeStack(_snowman == 0 ? 1 : _snowmanxxxxxx.getStack().getCount());
            _snowmanxxxxxx.onTakeItem(_snowman, _snowmanxxxxxxxxxxx);
            _snowman.dropItem(_snowmanxxxxxxxxxxx, true);
         }
      } else if (_snowman == SlotActionType.PICKUP_ALL && _snowman >= 0) {
         Slot _snowmanxxxxxx = this.slots.get(_snowman);
         ItemStack _snowmanxxxxxxxxxxx = _snowmanxxxxx.getCursorStack();
         if (!_snowmanxxxxxxxxxxx.isEmpty() && (_snowmanxxxxxx == null || !_snowmanxxxxxx.hasStack() || !_snowmanxxxxxx.canTakeItems(_snowman))) {
            int _snowmanxxxxxxxxxxxx = _snowman == 0 ? 0 : this.slots.size() - 1;
            int _snowmanxxxxxxxxxxxxx = _snowman == 0 ? 1 : -1;

            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
                  _snowmanxxxxxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxxxxxx < this.slots.size() && _snowmanxxxxxxxxxxx.getCount() < _snowmanxxxxxxxxxxx.getMaxCount();
                  _snowmanxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxx
               ) {
                  Slot _snowmanxxxxxxxxxxxxxxxx = this.slots.get(_snowmanxxxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxxxxx.hasStack()
                     && canInsertItemIntoSlot(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, true)
                     && _snowmanxxxxxxxxxxxxxxxx.canTakeItems(_snowman)
                     && this.canInsertIntoSlot(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)) {
                     ItemStack _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.getStack();
                     if (_snowmanxxxxxxxxxxxxxx != 0 || _snowmanxxxxxxxxxxxxxxxxx.getCount() != _snowmanxxxxxxxxxxxxxxxxx.getMaxCount()) {
                        int _snowmanxxxxxxxxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxx.getMaxCount() - _snowmanxxxxxxxxxxx.getCount(), _snowmanxxxxxxxxxxxxxxxxx.getCount());
                        ItemStack _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.takeStack(_snowmanxxxxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxxxxxx.increment(_snowmanxxxxxxxxxxxxxxxxxx);
                        if (_snowmanxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                           _snowmanxxxxxxxxxxxxxxxx.setStack(ItemStack.EMPTY);
                        }

                        _snowmanxxxxxxxxxxxxxxxx.onTakeItem(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx);
                     }
                  }
               }
            }
         }

         this.sendContentUpdates();
      }

      return _snowmanxxxx;
   }

   public static boolean canStacksCombine(ItemStack first, ItemStack second) {
      return first.getItem() == second.getItem() && ItemStack.areTagsEqual(first, second);
   }

   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return true;
   }

   public void close(PlayerEntity player) {
      PlayerInventory _snowman = player.inventory;
      if (!_snowman.getCursorStack().isEmpty()) {
         player.dropItem(_snowman.getCursorStack(), false);
         _snowman.setCursorStack(ItemStack.EMPTY);
      }
   }

   protected void dropInventory(PlayerEntity player, World world, Inventory inventory) {
      if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity)player).isDisconnected()) {
         for (int _snowman = 0; _snowman < inventory.size(); _snowman++) {
            player.dropItem(inventory.removeStack(_snowman), false);
         }
      } else {
         for (int _snowman = 0; _snowman < inventory.size(); _snowman++) {
            player.inventory.offerOrDrop(world, inventory.removeStack(_snowman));
         }
      }
   }

   public void onContentChanged(Inventory inventory) {
      this.sendContentUpdates();
   }

   public void setStackInSlot(int slot, ItemStack stack) {
      this.getSlot(slot).setStack(stack);
   }

   public void updateSlotStacks(List<ItemStack> stacks) {
      for (int _snowman = 0; _snowman < stacks.size(); _snowman++) {
         this.getSlot(_snowman).setStack(stacks.get(_snowman));
      }
   }

   public void setProperty(int id, int value) {
      this.properties.get(id).set(value);
   }

   public short getNextActionId(PlayerInventory playerInventory) {
      this.actionId++;
      return this.actionId;
   }

   public boolean isNotRestricted(PlayerEntity player) {
      return !this.restrictedPlayers.contains(player);
   }

   public void setPlayerRestriction(PlayerEntity player, boolean unrestricted) {
      if (unrestricted) {
         this.restrictedPlayers.remove(player);
      } else {
         this.restrictedPlayers.add(player);
      }
   }

   public abstract boolean canUse(PlayerEntity player);

   protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
      boolean _snowman = false;
      int _snowmanx = startIndex;
      if (fromLast) {
         _snowmanx = endIndex - 1;
      }

      if (stack.isStackable()) {
         while (!stack.isEmpty() && (fromLast ? _snowmanx >= startIndex : _snowmanx < endIndex)) {
            Slot _snowmanxx = this.slots.get(_snowmanx);
            ItemStack _snowmanxxx = _snowmanxx.getStack();
            if (!_snowmanxxx.isEmpty() && canStacksCombine(stack, _snowmanxxx)) {
               int _snowmanxxxx = _snowmanxxx.getCount() + stack.getCount();
               if (_snowmanxxxx <= stack.getMaxCount()) {
                  stack.setCount(0);
                  _snowmanxxx.setCount(_snowmanxxxx);
                  _snowmanxx.markDirty();
                  _snowman = true;
               } else if (_snowmanxxx.getCount() < stack.getMaxCount()) {
                  stack.decrement(stack.getMaxCount() - _snowmanxxx.getCount());
                  _snowmanxxx.setCount(stack.getMaxCount());
                  _snowmanxx.markDirty();
                  _snowman = true;
               }
            }

            if (fromLast) {
               _snowmanx--;
            } else {
               _snowmanx++;
            }
         }
      }

      if (!stack.isEmpty()) {
         if (fromLast) {
            _snowmanx = endIndex - 1;
         } else {
            _snowmanx = startIndex;
         }

         while (fromLast ? _snowmanx >= startIndex : _snowmanx < endIndex) {
            Slot _snowmanxxxx = this.slots.get(_snowmanx);
            ItemStack _snowmanxxxxx = _snowmanxxxx.getStack();
            if (_snowmanxxxxx.isEmpty() && _snowmanxxxx.canInsert(stack)) {
               if (stack.getCount() > _snowmanxxxx.getMaxItemCount()) {
                  _snowmanxxxx.setStack(stack.split(_snowmanxxxx.getMaxItemCount()));
               } else {
                  _snowmanxxxx.setStack(stack.split(stack.getCount()));
               }

               _snowmanxxxx.markDirty();
               _snowman = true;
               break;
            }

            if (fromLast) {
               _snowmanx--;
            } else {
               _snowmanx++;
            }
         }
      }

      return _snowman;
   }

   public static int unpackQuickCraftButton(int quickCraftData) {
      return quickCraftData >> 2 & 3;
   }

   public static int unpackQuickCraftStage(int quickCraftData) {
      return quickCraftData & 3;
   }

   public static int packQuickCraftData(int quickCraftStage, int buttonId) {
      return quickCraftStage & 3 | (buttonId & 3) << 2;
   }

   public static boolean shouldQuickCraftContinue(int stage, PlayerEntity player) {
      if (stage == 0) {
         return true;
      } else {
         return stage == 1 ? true : stage == 2 && player.abilities.creativeMode;
      }
   }

   protected void endQuickCraft() {
      this.quickCraftButton = 0;
      this.quickCraftSlots.clear();
   }

   public static boolean canInsertItemIntoSlot(@Nullable Slot slot, ItemStack stack, boolean allowOverflow) {
      boolean _snowman = slot == null || !slot.hasStack();
      return !_snowman && stack.isItemEqualIgnoreDamage(slot.getStack()) && ItemStack.areTagsEqual(slot.getStack(), stack)
         ? slot.getStack().getCount() + (allowOverflow ? 0 : stack.getCount()) <= stack.getMaxCount()
         : _snowman;
   }

   public static void calculateStackSize(Set<Slot> slots, int mode, ItemStack stack, int stackSize) {
      switch (mode) {
         case 0:
            stack.setCount(MathHelper.floor((float)stack.getCount() / (float)slots.size()));
            break;
         case 1:
            stack.setCount(1);
            break;
         case 2:
            stack.setCount(stack.getItem().getMaxCount());
      }

      stack.increment(stackSize);
   }

   public boolean canInsertIntoSlot(Slot slot) {
      return true;
   }

   public static int calculateComparatorOutput(@Nullable BlockEntity entity) {
      return entity instanceof Inventory ? calculateComparatorOutput((Inventory)entity) : 0;
   }

   public static int calculateComparatorOutput(@Nullable Inventory inventory) {
      if (inventory == null) {
         return 0;
      } else {
         int _snowman = 0;
         float _snowmanx = 0.0F;

         for (int _snowmanxx = 0; _snowmanxx < inventory.size(); _snowmanxx++) {
            ItemStack _snowmanxxx = inventory.getStack(_snowmanxx);
            if (!_snowmanxxx.isEmpty()) {
               _snowmanx += (float)_snowmanxxx.getCount() / (float)Math.min(inventory.getMaxCountPerStack(), _snowmanxxx.getMaxCount());
               _snowman++;
            }
         }

         _snowmanx /= (float)inventory.size();
         return MathHelper.floor(_snowmanx * 14.0F) + (_snowman > 0 ? 1 : 0);
      }
   }
}
