package net.minecraft.screen;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public abstract class ForgingScreenHandler extends ScreenHandler {
   protected final CraftingResultInventory output = new CraftingResultInventory();
   protected final Inventory input = new SimpleInventory(2) {
      @Override
      public void markDirty() {
         super.markDirty();
         ForgingScreenHandler.this.onContentChanged(this);
      }
   };
   protected final ScreenHandlerContext context;
   protected final PlayerEntity player;

   protected abstract boolean canTakeOutput(PlayerEntity player, boolean present);

   protected abstract ItemStack onTakeOutput(PlayerEntity player, ItemStack stack);

   protected abstract boolean canUse(BlockState state);

   public ForgingScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(type, syncId);
      this.context = context;
      this.player = playerInventory.player;
      this.addSlot(new Slot(this.input, 0, 27, 47));
      this.addSlot(new Slot(this.input, 1, 76, 47));
      this.addSlot(new Slot(this.output, 2, 134, 47) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return false;
         }

         @Override
         public boolean canTakeItems(PlayerEntity playerEntity) {
            return ForgingScreenHandler.this.canTakeOutput(playerEntity, this.hasStack());
         }

         @Override
         public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
            return ForgingScreenHandler.this.onTakeOutput(player, stack);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.addSlot(new Slot(playerInventory, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.addSlot(new Slot(playerInventory, _snowman, 8 + _snowman * 18, 142));
      }
   }

   public abstract void updateResult();

   @Override
   public void onContentChanged(Inventory inventory) {
      super.onContentChanged(inventory);
      if (inventory == this.input) {
         this.updateResult();
      }
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, _snowmanx, this.input));
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return this.context
         .run(
            (_snowmanx, _snowmanxx) -> !this.canUse(_snowmanx.getBlockState(_snowmanxx))
                  ? false
                  : player.squaredDistanceTo((double)_snowmanxx.getX() + 0.5, (double)_snowmanxx.getY() + 0.5, (double)_snowmanxx.getZ() + 0.5) <= 64.0,
            true
         );
   }

   protected boolean method_30025(ItemStack _snowman) {
      return false;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 2) {
            if (!this.insertItem(_snowmanxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index != 0 && index != 1) {
            if (index >= 3 && index < 39) {
               int _snowmanxxx = this.method_30025(_snowman) ? 1 : 0;
               if (!this.insertItem(_snowmanxx, _snowmanxxx, 2, false)) {
                  return ItemStack.EMPTY;
               }
            }
         } else if (!this.insertItem(_snowmanxx, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (_snowmanxx.isEmpty()) {
            _snowmanx.setStack(ItemStack.EMPTY);
         } else {
            _snowmanx.markDirty();
         }

         if (_snowmanxx.getCount() == _snowman.getCount()) {
            return ItemStack.EMPTY;
         }

         _snowmanx.onTakeItem(player, _snowmanxx);
      }

      return _snowman;
   }
}
