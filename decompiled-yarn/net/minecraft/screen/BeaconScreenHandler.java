package net.minecraft.screen;

import javax.annotation.Nullable;
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
      int _snowman = 36;
      int _snowmanx = 137;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.addSlot(new Slot(inventory, _snowmanxxx + _snowmanxx * 9 + 9, 36 + _snowmanxxx * 18, 137 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.addSlot(new Slot(inventory, _snowmanxx, 36 + _snowmanxx * 18, 195));
      }
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      if (!player.world.isClient) {
         ItemStack _snowman = this.paymentSlot.takeStack(this.paymentSlot.getMaxItemCount());
         if (!_snowman.isEmpty()) {
            player.dropItem(_snowman, false);
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
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 0) {
            if (!this.insertItem(_snowmanxx, 1, 37, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (!this.paymentSlot.hasStack() && this.paymentSlot.canInsert(_snowmanxx) && _snowmanxx.getCount() == 1) {
            if (!this.insertItem(_snowmanxx, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 1 && index < 28) {
            if (!this.insertItem(_snowmanxx, 28, 37, false)) {
               return ItemStack.EMPTY;
            }
         } else if (index >= 28 && index < 37) {
            if (!this.insertItem(_snowmanxx, 1, 28, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 1, 37, false)) {
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

   public int getProperties() {
      return this.propertyDelegate.get(0);
   }

   @Nullable
   public StatusEffect getPrimaryEffect() {
      return StatusEffect.byRawId(this.propertyDelegate.get(1));
   }

   @Nullable
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
