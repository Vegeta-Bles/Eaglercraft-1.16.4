package net.minecraft.screen;

import java.util.List;
import java.util.Random;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.registry.Registry;

public class EnchantmentScreenHandler extends ScreenHandler {
   private final Inventory inventory = new SimpleInventory(2) {
      @Override
      public void markDirty() {
         super.markDirty();
         EnchantmentScreenHandler.this.onContentChanged(this);
      }
   };
   private final ScreenHandlerContext context;
   private final Random random = new Random();
   private final Property seed = Property.create();
   public final int[] enchantmentPower = new int[3];
   public final int[] enchantmentId = new int[]{-1, -1, -1};
   public final int[] enchantmentLevel = new int[]{-1, -1, -1};

   public EnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public EnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.ENCHANTMENT, syncId);
      this.context = context;
      this.addSlot(new Slot(this.inventory, 0, 15, 47) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return true;
         }

         @Override
         public int getMaxItemCount() {
            return 1;
         }
      });
      this.addSlot(new Slot(this.inventory, 1, 35, 47) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.getItem() == Items.LAPIS_LAZULI;
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

      this.addProperty(Property.create(this.enchantmentPower, 0));
      this.addProperty(Property.create(this.enchantmentPower, 1));
      this.addProperty(Property.create(this.enchantmentPower, 2));
      this.addProperty(this.seed).set(playerInventory.player.getEnchantmentTableSeed());
      this.addProperty(Property.create(this.enchantmentId, 0));
      this.addProperty(Property.create(this.enchantmentId, 1));
      this.addProperty(Property.create(this.enchantmentId, 2));
      this.addProperty(Property.create(this.enchantmentLevel, 0));
      this.addProperty(Property.create(this.enchantmentLevel, 1));
      this.addProperty(Property.create(this.enchantmentLevel, 2));
   }

   @Override
   public void onContentChanged(Inventory inventory) {
      if (inventory == this.inventory) {
         ItemStack _snowman = inventory.getStack(0);
         if (!_snowman.isEmpty() && _snowman.isEnchantable()) {
            this.context.run((_snowmanx, _snowmanxx) -> {
               int _snowmanx = 0;

               for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
                  for (int _snowmanxxxx = -1; _snowmanxxxx <= 1; _snowmanxxxx++) {
                     if ((_snowmanxxx != 0 || _snowmanxxxx != 0) && _snowmanx.isAir(_snowmanxx.add(_snowmanxxxx, 0, _snowmanxxx)) && _snowmanx.isAir(_snowmanxx.add(_snowmanxxxx, 1, _snowmanxxx))) {
                        if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx * 2, 0, _snowmanxxx * 2)).isOf(Blocks.BOOKSHELF)) {
                           _snowmanx++;
                        }

                        if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx * 2, 1, _snowmanxxx * 2)).isOf(Blocks.BOOKSHELF)) {
                           _snowmanx++;
                        }

                        if (_snowmanxxxx != 0 && _snowmanxxx != 0) {
                           if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx * 2, 0, _snowmanxxx)).isOf(Blocks.BOOKSHELF)) {
                              _snowmanx++;
                           }

                           if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx * 2, 1, _snowmanxxx)).isOf(Blocks.BOOKSHELF)) {
                              _snowmanx++;
                           }

                           if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx, 0, _snowmanxxx * 2)).isOf(Blocks.BOOKSHELF)) {
                              _snowmanx++;
                           }

                           if (_snowmanx.getBlockState(_snowmanxx.add(_snowmanxxxx, 1, _snowmanxxx * 2)).isOf(Blocks.BOOKSHELF)) {
                              _snowmanx++;
                           }
                        }
                     }
                  }
               }

               this.random.setSeed((long)this.seed.get());

               for (int _snowmanxxx = 0; _snowmanxxx < 3; _snowmanxxx++) {
                  this.enchantmentPower[_snowmanxxx] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, _snowmanxxx, _snowmanx, _snowman);
                  this.enchantmentId[_snowmanxxx] = -1;
                  this.enchantmentLevel[_snowmanxxx] = -1;
                  if (this.enchantmentPower[_snowmanxxx] < _snowmanxxx + 1) {
                     this.enchantmentPower[_snowmanxxx] = 0;
                  }
               }

               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 3; _snowmanxxxxx++) {
                  if (this.enchantmentPower[_snowmanxxxxx] > 0) {
                     List<EnchantmentLevelEntry> _snowmanxxxxxx = this.generateEnchantments(_snowman, _snowmanxxxxx, this.enchantmentPower[_snowmanxxxxx]);
                     if (_snowmanxxxxxx != null && !_snowmanxxxxxx.isEmpty()) {
                        EnchantmentLevelEntry _snowmanxxxxxxx = _snowmanxxxxxx.get(this.random.nextInt(_snowmanxxxxxx.size()));
                        this.enchantmentId[_snowmanxxxxx] = Registry.ENCHANTMENT.getRawId(_snowmanxxxxxxx.enchantment);
                        this.enchantmentLevel[_snowmanxxxxx] = _snowmanxxxxxxx.level;
                     }
                  }
               }

               this.sendContentUpdates();
            });
         } else {
            for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
               this.enchantmentPower[_snowmanx] = 0;
               this.enchantmentId[_snowmanx] = -1;
               this.enchantmentLevel[_snowmanx] = -1;
            }
         }
      }
   }

   @Override
   public boolean onButtonClick(PlayerEntity player, int id) {
      ItemStack _snowman = this.inventory.getStack(0);
      ItemStack _snowmanx = this.inventory.getStack(1);
      int _snowmanxx = id + 1;
      if ((_snowmanx.isEmpty() || _snowmanx.getCount() < _snowmanxx) && !player.abilities.creativeMode) {
         return false;
      } else if (this.enchantmentPower[id] <= 0
         || _snowman.isEmpty()
         || (player.experienceLevel < _snowmanxx || player.experienceLevel < this.enchantmentPower[id]) && !player.abilities.creativeMode) {
         return false;
      } else {
         this.context.run((_snowmanxxxxx, _snowmanxxxxxx) -> {
            ItemStack _snowmanxxx = _snowman;
            List<EnchantmentLevelEntry> _snowmanx = this.generateEnchantments(_snowman, id, this.enchantmentPower[id]);
            if (!_snowmanx.isEmpty()) {
               player.applyEnchantmentCosts(_snowman, _snowman);
               boolean _snowmanxx = _snowman.getItem() == Items.BOOK;
               if (_snowmanxx) {
                  _snowmanxxx = new ItemStack(Items.ENCHANTED_BOOK);
                  CompoundTag _snowmanxxx = _snowman.getTag();
                  if (_snowmanxxx != null) {
                     _snowmanxxx.setTag(_snowmanxxx.copy());
                  }

                  this.inventory.setStack(0, _snowmanxxx);
               }

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
                  EnchantmentLevelEntry _snowmanxxxx = _snowmanx.get(_snowmanxxx);
                  if (_snowmanxx) {
                     EnchantedBookItem.addEnchantment(_snowmanxxx, _snowmanxxxx);
                  } else {
                     _snowmanxxx.addEnchantment(_snowmanxxxx.enchantment, _snowmanxxxx.level);
                  }
               }

               if (!player.abilities.creativeMode) {
                  _snowman.decrement(_snowman);
                  if (_snowman.isEmpty()) {
                     this.inventory.setStack(1, ItemStack.EMPTY);
                  }
               }

               player.incrementStat(Stats.ENCHANT_ITEM);
               if (player instanceof ServerPlayerEntity) {
                  Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, _snowmanxxx, _snowman);
               }

               this.inventory.markDirty();
               this.seed.set(player.getEnchantmentTableSeed());
               this.onContentChanged(this.inventory);
               _snowmanxxxxx.playSound(null, _snowmanxxxxxx, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, _snowmanxxxxx.random.nextFloat() * 0.1F + 0.9F);
            }
         });
         return true;
      }
   }

   private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
      this.random.setSeed((long)(this.seed.get() + slot));
      List<EnchantmentLevelEntry> _snowman = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
      if (stack.getItem() == Items.BOOK && _snowman.size() > 1) {
         _snowman.remove(this.random.nextInt(_snowman.size()));
      }

      return _snowman;
   }

   public int getLapisCount() {
      ItemStack _snowman = this.inventory.getStack(1);
      return _snowman.isEmpty() ? 0 : _snowman.getCount();
   }

   public int getSeed() {
      return this.seed.get();
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, player.world, this.inventory));
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.ENCHANTING_TABLE);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         if (index == 0) {
            if (!this.insertItem(_snowmanxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (index == 1) {
            if (!this.insertItem(_snowmanxx, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (_snowmanxx.getItem() == Items.LAPIS_LAZULI) {
            if (!this.insertItem(_snowmanxx, 1, 2, true)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (this.slots.get(0).hasStack() || !this.slots.get(0).canInsert(_snowmanxx)) {
               return ItemStack.EMPTY;
            }

            ItemStack _snowmanxxx = _snowmanxx.copy();
            _snowmanxxx.setCount(1);
            _snowmanxx.decrement(1);
            this.slots.get(0).setStack(_snowmanxxx);
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
