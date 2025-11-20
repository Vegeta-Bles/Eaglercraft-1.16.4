package net.minecraft.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Unit;

public class LoomScreenHandler extends ScreenHandler {
   private final ScreenHandlerContext context;
   private final Property selectedPattern = Property.create();
   private Runnable inventoryChangeListener = () -> {
   };
   private final Slot bannerSlot;
   private final Slot dyeSlot;
   private final Slot patternSlot;
   private final Slot outputSlot;
   private long lastTakeResultTime;
   private final Inventory input = new SimpleInventory(3) {
      @Override
      public void markDirty() {
         super.markDirty();
         LoomScreenHandler.this.onContentChanged(this);
         LoomScreenHandler.this.inventoryChangeListener.run();
      }
   };
   private final Inventory output = new SimpleInventory(1) {
      @Override
      public void markDirty() {
         super.markDirty();
         LoomScreenHandler.this.inventoryChangeListener.run();
      }
   };

   public LoomScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public LoomScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
      super(ScreenHandlerType.LOOM, syncId);
      this.context = context;
      this.bannerSlot = this.addSlot(new Slot(this.input, 0, 13, 26) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof BannerItem;
         }
      });
      this.dyeSlot = this.addSlot(new Slot(this.input, 1, 33, 26) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof DyeItem;
         }
      });
      this.patternSlot = this.addSlot(new Slot(this.input, 2, 23, 45) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof BannerPatternItem;
         }
      });
      this.outputSlot = this.addSlot(new Slot(this.output, 0, 143, 58) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return false;
         }

         @Override
         public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
            LoomScreenHandler.this.bannerSlot.takeStack(1);
            LoomScreenHandler.this.dyeSlot.takeStack(1);
            if (!LoomScreenHandler.this.bannerSlot.hasStack() || !LoomScreenHandler.this.dyeSlot.hasStack()) {
               LoomScreenHandler.this.selectedPattern.set(0);
            }

            context.run((arg, arg2) -> {
               long l = arg.getTime();
               if (LoomScreenHandler.this.lastTakeResultTime != l) {
                  arg.playSound(null, arg2, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  LoomScreenHandler.this.lastTakeResultTime = l;
               }
               return Unit.INSTANCE;
            });
            return super.onTakeItem(player, stack);
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

      this.addProperty(this.selectedPattern);
   }

   @Environment(EnvType.CLIENT)
   public int getSelectedPattern() {
      return this.selectedPattern.get();
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.LOOM);
   }

   @Override
   public boolean onButtonClick(PlayerEntity player, int id) {
      if (id > 0 && id <= BannerPattern.LOOM_APPLICABLE_COUNT) {
         this.selectedPattern.set(id);
         this.updateOutputSlot();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      ItemStack lv = this.bannerSlot.getStack();
      ItemStack lv2 = this.dyeSlot.getStack();
      ItemStack lv3 = this.patternSlot.getStack();
      ItemStack lv4 = this.outputSlot.getStack();
      if (lv4.isEmpty()
         || !lv.isEmpty()
            && !lv2.isEmpty()
            && this.selectedPattern.get() > 0
            && (this.selectedPattern.get() < BannerPattern.COUNT - BannerPattern.field_24417 || !lv3.isEmpty())) {
         if (!lv3.isEmpty() && lv3.getItem() instanceof BannerPatternItem) {
            CompoundTag lv5 = lv.getOrCreateSubTag("BlockEntityTag");
            boolean bl = lv5.contains("Patterns", 9) && !lv.isEmpty() && lv5.getList("Patterns", 10).size() >= 6;
            if (bl) {
               this.selectedPattern.set(0);
            } else {
               this.selectedPattern.set(((BannerPatternItem)lv3.getItem()).getPattern().ordinal());
            }
         }
      } else {
         this.outputSlot.setStack(ItemStack.EMPTY);
         this.selectedPattern.set(0);
      }

      this.updateOutputSlot();
      this.sendContentUpdates();
   }

   @Environment(EnvType.CLIENT)
   public void setInventoryChangeListener(Runnable inventoryChangeListener) {
      this.inventoryChangeListener = inventoryChangeListener;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         lv = lv3.copy();
         if (index == this.outputSlot.id) {
            if (!this.insertItem(lv3, 4, 40, true)) {
               return ItemStack.EMPTY;
            }

            lv2.onStackChanged(lv3, lv);
         } else if (index != this.dyeSlot.id && index != this.bannerSlot.id && index != this.patternSlot.id) {
            if (lv3.getItem() instanceof BannerItem) {
               if (!this.insertItem(lv3, this.bannerSlot.id, this.bannerSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (lv3.getItem() instanceof DyeItem) {
               if (!this.insertItem(lv3, this.dyeSlot.id, this.dyeSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (lv3.getItem() instanceof BannerPatternItem) {
               if (!this.insertItem(lv3, this.patternSlot.id, this.patternSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 4 && index < 31) {
               if (!this.insertItem(lv3, 31, 40, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 31 && index < 40 && !this.insertItem(lv3, 4, 31, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(lv3, 4, 40, false)) {
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

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((arg2, arg3) -> {
         this.dropInventory(player, player.world, this.input);
         return Unit.INSTANCE;
      });
   }

   private void updateOutputSlot() {
      if (this.selectedPattern.get() > 0) {
         ItemStack lv = this.bannerSlot.getStack();
         ItemStack lv2 = this.dyeSlot.getStack();
         ItemStack lv3 = ItemStack.EMPTY;
         if (!lv.isEmpty() && !lv2.isEmpty()) {
            lv3 = lv.copy();
            lv3.setCount(1);
            BannerPattern lv4 = BannerPattern.values()[this.selectedPattern.get()];
            DyeColor lv5 = ((DyeItem)lv2.getItem()).getColor();
            CompoundTag lv6 = lv3.getOrCreateSubTag("BlockEntityTag");
            ListTag lv7;
            if (lv6.contains("Patterns", 9)) {
               lv7 = lv6.getList("Patterns", 10);
            } else {
               lv7 = new ListTag();
               lv6.put("Patterns", lv7);
            }

            CompoundTag lv9 = new CompoundTag();
            lv9.putString("Pattern", lv4.getId());
            lv9.putInt("Color", lv5.getId());
            lv7.add(lv9);
         }

         if (!ItemStack.areEqual(lv3, this.outputSlot.getStack())) {
            this.outputSlot.setStack(lv3);
         }
      }
   }

   @Environment(EnvType.CLIENT)
   public Slot getBannerSlot() {
      return this.bannerSlot;
   }

   @Environment(EnvType.CLIENT)
   public Slot getDyeSlot() {
      return this.dyeSlot;
   }

   @Environment(EnvType.CLIENT)
   public Slot getPatternSlot() {
      return this.patternSlot;
   }

   @Environment(EnvType.CLIENT)
   public Slot getOutputSlot() {
      return this.outputSlot;
   }
}
