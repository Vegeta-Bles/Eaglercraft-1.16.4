package net.minecraft.screen;

import java.util.List;
import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.util.Unit;
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

      for (int j = 0; j < 3; j++) {
         for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
         }
      }

      for (int l = 0; l < 9; l++) {
         this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
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
         ItemStack lv = inventory.getStack(0);
         if (!lv.isEmpty() && lv.isEnchantable()) {
            this.context.run((arg2, arg3) -> {
               int ix = 0;

               for (int j = -1; j <= 1; j++) {
                  for (int k = -1; k <= 1; k++) {
                     if ((j != 0 || k != 0) && arg2.isAir(arg3.add(k, 0, j)) && arg2.isAir(arg3.add(k, 1, j))) {
                        if (arg2.getBlockState(arg3.add(k * 2, 0, j * 2)).isOf(Blocks.BOOKSHELF)) {
                           ix++;
                        }

                        if (arg2.getBlockState(arg3.add(k * 2, 1, j * 2)).isOf(Blocks.BOOKSHELF)) {
                           ix++;
                        }

                        if (k != 0 && j != 0) {
                           if (arg2.getBlockState(arg3.add(k * 2, 0, j)).isOf(Blocks.BOOKSHELF)) {
                              ix++;
                           }

                           if (arg2.getBlockState(arg3.add(k * 2, 1, j)).isOf(Blocks.BOOKSHELF)) {
                              ix++;
                           }

                           if (arg2.getBlockState(arg3.add(k, 0, j * 2)).isOf(Blocks.BOOKSHELF)) {
                              ix++;
                           }

                           if (arg2.getBlockState(arg3.add(k, 1, j * 2)).isOf(Blocks.BOOKSHELF)) {
                              ix++;
                           }
                        }
                     }
                  }
               }

               this.random.setSeed((long)this.seed.get());

               for (int l = 0; l < 3; l++) {
                  this.enchantmentPower[l] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, l, ix, lv);
                  this.enchantmentId[l] = -1;
                  this.enchantmentLevel[l] = -1;
                  if (this.enchantmentPower[l] < l + 1) {
                     this.enchantmentPower[l] = 0;
                  }
               }

               for (int m = 0; m < 3; m++) {
                  if (this.enchantmentPower[m] > 0) {
                     List<EnchantmentLevelEntry> list = this.generateEnchantments(lv, m, this.enchantmentPower[m]);
                     if (list != null && !list.isEmpty()) {
                        EnchantmentLevelEntry lvx = list.get(this.random.nextInt(list.size()));
                        this.enchantmentId[m] = Registry.ENCHANTMENT.getRawId(lvx.enchantment);
                        this.enchantmentLevel[m] = lvx.level;
                     }
                  }
               }

               this.sendContentUpdates();
               return Unit.INSTANCE;
            });
         } else {
            for (int i = 0; i < 3; i++) {
               this.enchantmentPower[i] = 0;
               this.enchantmentId[i] = -1;
               this.enchantmentLevel[i] = -1;
            }
         }
      }
   }

   @Override
   public boolean onButtonClick(PlayerEntity player, int id) {
      ItemStack lv = this.inventory.getStack(0);
      ItemStack lv2 = this.inventory.getStack(1);
      int j = id + 1;
      if ((lv2.isEmpty() || lv2.getCount() < j) && !player.abilities.creativeMode) {
         return false;
      } else if (this.enchantmentPower[id] <= 0
         || lv.isEmpty()
         || (player.experienceLevel < j || player.experienceLevel < this.enchantmentPower[id]) && !player.abilities.creativeMode) {
         return false;
      } else {
         this.context.run((arg4, arg5) -> {
            ItemStack lvx = lv;
            List<EnchantmentLevelEntry> list = this.generateEnchantments(lv, id, this.enchantmentPower[id]);
            if (!list.isEmpty()) {
               player.applyEnchantmentCosts(lv, j);
               boolean bl = lv.getItem() == Items.BOOK;
               if (bl) {
                  lvx = new ItemStack(Items.ENCHANTED_BOOK);
                  CompoundTag lv2x = lv.getTag();
                  if (lv2x != null) {
                     lvx.setTag(lv2x.copy());
                  }

                  this.inventory.setStack(0, lvx);
               }

               for (int k = 0; k < list.size(); k++) {
                  EnchantmentLevelEntry lv3 = list.get(k);
                  if (bl) {
                     EnchantedBookItem.addEnchantment(lvx, lv3);
                  } else {
                     lvx.addEnchantment(lv3.enchantment, lv3.level);
                  }
               }

               if (!player.abilities.creativeMode) {
                  lv2.decrement(j);
                  if (lv2.isEmpty()) {
                     this.inventory.setStack(1, ItemStack.EMPTY);
                  }
               }

               player.incrementStat(Stats.ENCHANT_ITEM);
               if (player instanceof ServerPlayerEntity) {
                  Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, lvx, j);
               }

               this.inventory.markDirty();
               this.seed.set(player.getEnchantmentTableSeed());
               this.onContentChanged(this.inventory);
               arg4.playSound(null, arg5, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, arg4.random.nextFloat() * 0.1F + 0.9F);
            }
            return Unit.INSTANCE;
         });
         return true;
      }
   }

   private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
      this.random.setSeed((long)(this.seed.get() + slot));
      List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
      if (stack.getItem() == Items.BOOK && list.size() > 1) {
         list.remove(this.random.nextInt(list.size()));
      }

      return list;
   }

   @Environment(EnvType.CLIENT)
   public int getLapisCount() {
      ItemStack lv = this.inventory.getStack(1);
      return lv.isEmpty() ? 0 : lv.getCount();
   }

   @Environment(EnvType.CLIENT)
   public int getSeed() {
      return this.seed.get();
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((arg2, arg3) -> {
         this.dropInventory(player, player.world, this.inventory);
         return Unit.INSTANCE;
      });
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.ENCHANTING_TABLE);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack lv = ItemStack.EMPTY;
      Slot lv2 = this.slots.get(index);
      if (lv2 != null && lv2.hasStack()) {
         ItemStack lv3 = lv2.getStack();
         lv = lv3.copy();
         if (index == 0) {
            if (!this.insertItem(lv3, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (index == 1) {
            if (!this.insertItem(lv3, 2, 38, true)) {
               return ItemStack.EMPTY;
            }
         } else if (lv3.getItem() == Items.LAPIS_LAZULI) {
            if (!this.insertItem(lv3, 1, 2, true)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (this.slots.get(0).hasStack() || !this.slots.get(0).canInsert(lv3)) {
               return ItemStack.EMPTY;
            }

            ItemStack lv4 = lv3.copy();
            lv4.setCount(1);
            lv3.decrement(1);
            this.slots.get(0).setStack(lv4);
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
