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
import net.minecraft.util.Unit;

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

      for (int j = 0; j < 3; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
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
      this.context.run((arg2, arg3) -> {
         this.dropInventory(player, arg2, this.input);
         return Unit.INSTANCE;
      });
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return this.context
         .run(
            (arg2, arg3) -> !this.canUse(arg2.getBlockState(arg3))
                  ? false
                  : player.squaredDistanceTo((double)arg3.getX() + 0.5, (double)arg3.getY() + 0.5, (double)arg3.getZ() + 0.5) <= 64.0,
            true
         );
   }

   protected boolean method_30025(ItemStack arg) {
      return false;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         lv = lv3.copy();
         if (index == 2) {
            if (!this.insertItem(lv3, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
         } else if (index != 0 && index != 1) {
            if (index >= 3 && index < 39) {
               int j = this.method_30025(lv) ? 1 : 0;
               if (!this.insertItem(lv3, j, 2, false)) {
                  return ItemStack.EMPTY;
               }
            }
         } else if (!this.insertItem(lv3, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (lv3.isEmpty()) {
            lv2.setStack(ItemStack.EMPTY);
         } else {
            lv2.markDirty();
         }

         if (lv3.getCount() == lv.getCount()) {
            return ItemStack.EMPTY;
         }

         lv2.onTakeItem(player, lv3);
      }

      return lv;
   }
}
