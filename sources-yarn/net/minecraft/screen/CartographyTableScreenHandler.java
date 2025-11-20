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
import net.minecraft.util.Unit;

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

   public CartographyTableScreenHandler(int syncId, PlayerInventory inventory, final ScreenHandlerContext context) {
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
            Item lv = stack.getItem();
            return lv == Items.PAPER || lv == Items.MAP || lv == Items.GLASS_PANE;
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
            context.run((arg, arg2) -> {
               long l = arg.getTime();
               if (CartographyTableScreenHandler.this.lastTakeResultTime != l) {
                  arg.playSound(null, arg2, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  CartographyTableScreenHandler.this.lastTakeResultTime = l;
               }
               return Unit.INSTANCE;
            });
            return super.onTakeItem(player, stack);
         }
      });

      for (int j = 0; j < 3; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(inventory, l, 8 + l * 18, 142));
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.CARTOGRAPHY_TABLE);
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      ItemStack lv = this.inventory.getStack(0);
      ItemStack lv2 = this.inventory.getStack(1);
      ItemStack lv3 = this.resultSlot.getStack(2);
      if (lv3.isEmpty() || !lv.isEmpty() && !lv2.isEmpty()) {
         if (!lv.isEmpty() && !lv2.isEmpty()) {
            this.updateResult(lv, lv2, lv3);
         }
      } else {
         this.resultSlot.removeStack(2);
      }
   }

   private void updateResult(ItemStack map, ItemStack item, ItemStack oldResult) {
      this.context.run((arg4, arg5) -> {
         Item lv = item.getItem();
         MapState lv2 = FilledMapItem.getMapState(map, arg4);
         if (lv2 != null) {
            ItemStack lv3;
            if (lv == Items.PAPER && !lv2.locked && lv2.scale < 4) {
               lv3 = map.copy();
               lv3.setCount(1);
               lv3.getOrCreateTag().putInt("map_scale_direction", 1);
               this.sendContentUpdates();
            } else if (lv == Items.GLASS_PANE && !lv2.locked) {
               lv3 = map.copy();
               lv3.setCount(1);
               lv3.getOrCreateTag().putBoolean("map_to_lock", true);
               this.sendContentUpdates();
            } else {
               if (lv != Items.MAP) {
                  this.resultSlot.removeStack(2);
                  this.sendContentUpdates();
                  return Unit.INSTANCE;
               }

               lv3 = map.copy();
               lv3.setCount(2);
               this.sendContentUpdates();
            }

            if (!ItemStack.areEqual(lv3, oldResult)) {
               this.resultSlot.setStack(2, lv3);
               this.sendContentUpdates();
            }
         }
         return Unit.INSTANCE;
      });
   }

   @Override
   public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
      return slot.inventory != this.resultSlot && super.canInsertIntoSlot(stack, slot);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         Item lv5 = lv3.getItem();
         lv = lv3.copy();
         if (index == 2) {
            lv5.onCraft(lv3, player.world, player);
            if (!this.insertItem(lv3, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
         } else if (index != 1 && index != 0) {
            if (lv5 == Items.FILLED_MAP) {
               if (!this.insertItem(lv3, 0, 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (lv5 != Items.PAPER && lv5 != Items.MAP && lv5 != Items.GLASS_PANE) {
               if (index >= 3 && index < 30) {
                  if (!this.insertItem(lv3, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 30 && index < 39 && !this.insertItem(lv3, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.insertItem(lv3, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(lv3, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (lv3.isEmpty()) {
            lv2.setStack(ItemStack.EMPTY);
         }

         lv2.markDirty();
         if (lv3.getCount() == lv.getCount()) {
            return ItemStack.EMPTY;
         }

         lv2.onTakeItem(player, lv3);
         this.sendContentUpdates();
      }

      return lv;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.resultSlot.removeStack(2);
      this.context.run((arg2, arg3) -> {
         this.dropInventory(player, player.world, this.inventory);
         return Unit.INSTANCE;
      });
   }
}
