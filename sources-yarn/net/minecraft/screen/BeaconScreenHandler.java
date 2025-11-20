package net.minecraft.screen;

import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.ItemTags;

public class BeaconScreenHandler extends ScreenHandler {
   private final Inventory payment = new SimpleInventory(1) {
      @Override
      public boolean isValid(int slot, ItemStack stack) {
         return stack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
      }

      @Override
      public int getMaxCountPerStack() {
         return 1;
      }
   };
   private final BeaconScreenHandler.PaymentSlot paymentSlot;
   private final ScreenHandlerContext context;
   private final PropertyDelegate propertyDelegate;

   public BeaconScreenHandler(int syncId, Inventory inventory) {
      this(syncId, inventory, new ArrayPropertyDelegate(3), ScreenHandlerContext.EMPTY);
   }

   public BeaconScreenHandler(int syncId, Inventory inventory, PropertyDelegate propertyDelegate, ScreenHandlerContext context) {
      super(ScreenHandlerType.BEACON, syncId);
      checkDataCount(propertyDelegate, 3);
      this.propertyDelegate = propertyDelegate;
      this.context = context;
      this.paymentSlot = new BeaconScreenHandler.PaymentSlot(this.payment, 0, 136, 110);
      this.addSlot(this.paymentSlot);
      this.addProperties(propertyDelegate);
      int j = 36;
      int k = 137;

      for (int l = 0; l < 3; l++) {
         for (int m = 0; m < 9; m++) {
            this.addSlot(new Slot(inventory, m + l * 9 + 9, 36 + m * 18, 137 + l * 18));
         }
      }

      for (int n = 0; n < 9; n++) {
         this.addSlot(new Slot(inventory, n, 36 + n * 18, 195));
      }
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      if (!player.world.isClient) {
         ItemStack lv = this.paymentSlot.takeStack(this.paymentSlot.getMaxItemCount());
         if (!lv.isEmpty()) {
            player.dropItem(lv, false);
         }
      }
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.BEACON);
   }

   @Override
   public void setProperty(int id, int value) {
      super.setProperty(id, value);
      this.sendContentUpdates();
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         lv = lv3.copy();
         if (index == 0) {
            if (!this.insertItem(lv3, 1, 37, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
         } else if (!this.paymentSlot.hasStack() && this.paymentSlot.canInsert(lv3) && lv3.getCount() == 1) {
            if (!this.insertItem(lv3, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 1 && index < 28) {
            if (!this.insertItem(lv3, 28, 37, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 28 && index < 37) {
            if (!this.insertItem(lv3, 1, 28, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(lv3, 1, 37, false)) {
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

   @Environment(EnvType.CLIENT)
   public int getProperties() {
      return this.propertyDelegate.get(0);
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public StatusEffect getPrimaryEffect() {
      return StatusEffect.byRawId(this.propertyDelegate.get(1));
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public StatusEffect getSecondaryEffect() {
      return StatusEffect.byRawId(this.propertyDelegate.get(2));
   }

   public void setEffects(int primaryEffectId, int secondaryEffectId) {
      if (this.paymentSlot.hasStack()) {
         this.propertyDelegate.set(1, primaryEffectId);
         this.propertyDelegate.set(2, secondaryEffectId);
         this.paymentSlot.takeStack(1);
      }
   }

   @Environment(EnvType.CLIENT)
   public boolean hasPayment() {
      return !this.payment.getStack(0).isEmpty();
   }

   class PaymentSlot extends Slot {
      public PaymentSlot(Inventory inventory, int index, int x, int y) {
         super(inventory, index, x, y);
      }

      @Override
      public boolean canInsert(ItemStack stack) {
         return stack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
      }

      @Override
      public int getMaxItemCount() {
         return 1;
      }
   }
}
