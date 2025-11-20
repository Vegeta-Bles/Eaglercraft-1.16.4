package net.minecraft.screen;

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

   public LoomScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
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

            context.run((_snowman, _snowmanx) -> {
               long _snowmanxx = _snowman.getTime();
               if (LoomScreenHandler.this.lastTakeResultTime != _snowmanxx) {
                  _snowman.playSound(null, _snowmanx, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  LoomScreenHandler.this.lastTakeResultTime = _snowmanxx;
               }
            });
            return super.onTakeItem(player, stack);
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

      this.addProperty(this.selectedPattern);
   }

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
      ItemStack _snowman = this.bannerSlot.getStack();
      ItemStack _snowmanx = this.dyeSlot.getStack();
      ItemStack _snowmanxx = this.patternSlot.getStack();
      ItemStack _snowmanxxx = this.outputSlot.getStack();
      if (_snowmanxxx.isEmpty()
         || !_snowman.isEmpty()
            && !_snowmanx.isEmpty()
            && this.selectedPattern.get() > 0
            && (this.selectedPattern.get() < BannerPattern.COUNT - BannerPattern.field_24417 || !_snowmanxx.isEmpty())) {
         if (!_snowmanxx.isEmpty() && _snowmanxx.getItem() instanceof BannerPatternItem) {
            CompoundTag _snowmanxxxx = _snowman.getOrCreateSubTag("BlockEntityTag");
            boolean _snowmanxxxxx = _snowmanxxxx.contains("Patterns", 9) && !_snowman.isEmpty() && _snowmanxxxx.getList("Patterns", 10).size() >= 6;
            if (_snowmanxxxxx) {
               this.selectedPattern.set(0);
            } else {
               this.selectedPattern.set(((BannerPatternItem)_snowmanxx.getItem()).getPattern().ordinal());
            }
         }
      } else {
         this.outputSlot.setStack(ItemStack.EMPTY);
         this.selectedPattern.set(0);
      }

      this.updateOutputSlot();
      this.sendContentUpdates();
   }

   public void setInventoryChangeListener(Runnable inventoryChangeListener) {
      this.inventoryChangeListener = inventoryChangeListener;
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == this.outputSlot.id) {
            if (!this.insertItem(_snowmanxx, 4, 40, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index != this.dyeSlot.id && index != this.bannerSlot.id && index != this.patternSlot.id) {
            if (_snowmanxx.getItem() instanceof BannerItem) {
               if (!this.insertItem(_snowmanxx, this.bannerSlot.id, this.bannerSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (_snowmanxx.getItem() instanceof DyeItem) {
               if (!this.insertItem(_snowmanxx, this.dyeSlot.id, this.dyeSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (_snowmanxx.getItem() instanceof BannerPatternItem) {
               if (!this.insertItem(_snowmanxx, this.patternSlot.id, this.patternSlot.id + 1, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 4 && index < 31) {
               if (!this.insertItem(_snowmanxx, 31, 40, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (index >= 31 && index < 40 && !this.insertItem(_snowmanxx, 4, 31, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.insertItem(_snowmanxx, 4, 40, false)) {
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

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, player.world, this.input));
   }

   private void updateOutputSlot() {
      if (this.selectedPattern.get() > 0) {
         ItemStack _snowman = this.bannerSlot.getStack();
         ItemStack _snowmanx = this.dyeSlot.getStack();
         ItemStack _snowmanxx = ItemStack.EMPTY;
         if (!_snowman.isEmpty() && !_snowmanx.isEmpty()) {
            _snowmanxx = _snowman.copy();
            _snowmanxx.setCount(1);
            BannerPattern _snowmanxxx = BannerPattern.values()[this.selectedPattern.get()];
            DyeColor _snowmanxxxx = ((DyeItem)_snowmanx.getItem()).getColor();
            CompoundTag _snowmanxxxxx = _snowmanxx.getOrCreateSubTag("BlockEntityTag");
            ListTag _snowmanxxxxxx;
            if (_snowmanxxxxx.contains("Patterns", 9)) {
               _snowmanxxxxxx = _snowmanxxxxx.getList("Patterns", 10);
            } else {
               _snowmanxxxxxx = new ListTag();
               _snowmanxxxxx.put("Patterns", _snowmanxxxxxx);
            }

            CompoundTag _snowmanxxxxxxx = new CompoundTag();
            _snowmanxxxxxxx.putString("Pattern", _snowmanxxx.getId());
            _snowmanxxxxxxx.putInt("Color", _snowmanxxxx.getId());
            _snowmanxxxxxx.add(_snowmanxxxxxxx);
         }

         if (!ItemStack.areEqual(_snowmanxx, this.outputSlot.getStack())) {
            this.outputSlot.setStack(_snowmanxx);
         }
      }
   }

   public Slot getBannerSlot() {
      return this.bannerSlot;
   }

   public Slot getDyeSlot() {
      return this.dyeSlot;
   }

   public Slot getPatternSlot() {
      return this.patternSlot;
   }

   public Slot getOutputSlot() {
      return this.outputSlot;
   }
}
