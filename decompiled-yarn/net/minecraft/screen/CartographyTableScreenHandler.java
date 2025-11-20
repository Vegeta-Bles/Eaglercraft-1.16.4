package net.minecraft.screen;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class CartographyTableScreenHandler extends ScreenHandler {
   private final ScreenHandlerContext context;
   private long lastTakeResultTime;
   public final Inventory inventory = new SimpleInventory(2) {
      @Override
      public void markDirty() {
         CartographyTableScreenHandler.this.onContentChanged(this);
         super.markDirty();
      }
   };
   private final CraftingResultInventory resultSlot = new CraftingResultInventory() {
      @Override
      public void markDirty() {
         CartographyTableScreenHandler.this.onContentChanged(this);
         super.markDirty();
      }
   };

   public CartographyTableScreenHandler(int syncId, PlayerInventory inventory) {
      this(syncId, inventory, ScreenHandlerContext.EMPTY);
   }

   public CartographyTableScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.CARTOGRAPHY_TABLE, syncId);
      this.context = context;
      this.addSlot(new Slot(this.inventory, 0, 15, 15) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() == Items.FILLED_MAP;
         }
      });
      this.addSlot(new Slot(this.inventory, 1, 15, 52) {
         @Override
         public boolean canInsert(ItemStack stack) {
            Item _snowman = stack.getItem();
            return _snowman == Items.PAPER || _snowman == Items.MAP || _snowman == Items.GLASS_PANE;
         }
      });
      this.addSlot(new Slot(this.resultSlot, 2, 145, 39) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return false;
         }

         @Override
         public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
            CartographyTableScreenHandler.this.slots.get(0).takeStack(1);
            CartographyTableScreenHandler.this.slots.get(1).takeStack(1);
            stack.getItem().onCraft(stack, player.world, player);
            context.run((_snowman, _snowmanx) -> {
               long _snowmanxx = _snowman.getTime();
               if (CartographyTableScreenHandler.this.lastTakeResultTime != _snowmanxx) {
                  _snowman.playSound(null, _snowmanx, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  CartographyTableScreenHandler.this.lastTakeResultTime = _snowmanxx;
               }
            });
            return super.onTakeItem(player, stack);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(inventory, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(inventory, _snowman, 8 + _snowman * 18, 142));
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.CARTOGRAPHY_TABLE);
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      ItemStack _snowman = this.inventory.getStack(0);
      ItemStack _snowmanx = this.inventory.getStack(1);
      ItemStack _snowmanxx = this.resultSlot.getStack(2);
      if (_snowmanxx.isEmpty() || !_snowman.isEmpty() && !_snowmanx.isEmpty()) {
         if (!_snowman.isEmpty() && !_snowmanx.isEmpty()) {
            this.updateResult(_snowman, _snowmanx, _snowmanxx);
         }
      } else {
         this.resultSlot.removeStack(2);
      }
   }

   private void updateResult(ItemStack map, ItemStack item, ItemStack oldResult) {
      this.context.run((_snowmanxxx, _snowmanxxxx) -> {
         Item _snowmanxxxxx = item.getItem();
         MapState _snowmanx = FilledMapItem.getMapState(map, _snowmanxxx);
         if (_snowmanx != null) {
            ItemStack _snowmanxx;
            if (_snowmanxxxxx == Items.PAPER && !_snowmanx.locked && _snowmanx.scale < 4) {
               _snowmanxx = map.copy();
               _snowmanxx.setCount(1);
               _snowmanxx.getOrCreateTag().putInt("map_scale_direction", 1);
               this.sendContentUpdates();
            } else if (_snowmanxxxxx == Items.GLASS_PANE && !_snowmanx.locked) {
               _snowmanxx = map.copy();
               _snowmanxx.setCount(1);
               _snowmanxx.getOrCreateTag().putBoolean("map_to_lock", true);
               this.sendContentUpdates();
            } else {
               if (_snowmanxxxxx != Items.MAP) {
                  this.resultSlot.removeStack(2);
                  this.sendContentUpdates();
                  return;
               }

               _snowmanxx = map.copy();
               _snowmanxx.setCount(2);
               this.sendContentUpdates();
            }

            if (!ItemStack.areEqual(_snowmanxx, oldResult)) {
               this.resultSlot.setStack(2, _snowmanxx);
               this.sendContentUpdates();
            }
         }
      });
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.resultSlot && super.canInsertIntoSlot(stack, slot);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         Item _snowmanxxx = _snowmanxx.getItem();
         _snowman = _snowmanxx.copy();
         if (index == 2) {
            _snowmanxxx.onCraft(_snowmanxx, player.world, player);
            if (!this.insertItem(_snowmanxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index != 1 && index != 0) {
            if (_snowmanxxx == Items.FILLED_MAP) {
               if (!this.insertItem(_snowmanxx, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (_snowmanxxx != Items.PAPER && _snowmanxxx != Items.MAP && _snowmanxxx != Items.GLASS_PANE) {
               if (index >= 3 && index < 30) {
                  if (!this.insertItem(_snowmanxx, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 30 && index < 39 && !this.insertItem(_snowmanxx, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.insertItem(_snowmanxx, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         }

         _snowmanx.markDirty();
         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         _snowmanx.onTakeItem(player, _snowmanxx);
         this.sendContentUpdates();
      }

      return _snowman;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.resultSlot.removeStack(2);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, player.world, this.inventory));
   }
}
