package net.minecraft.screen;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

public class GrindstoneScreenHandler extends ScreenHandler {
   private final Inventory result = new CraftingResultInventory();
   private final Inventory input = new SimpleInventory(2) {
      @Override
      public void markDirty() {
         super.markDirty();
         GrindstoneScreenHandler.this.onContentChanged(this);
      }
   };
   private final ScreenHandlerContext context;

   public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory) {
      this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
   }

   public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.GRINDSTONE, syncId);
      this.context = context;
      this.addSlot(new Slot(this.input, 0, 49, 19) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.hasEnchantments();
         }
      });
      this.addSlot(new Slot(this.input, 1, 49, 40) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.hasEnchantments();
         }
      });
      this.addSlot(new Slot(this.result, 2, 129, 34) {
         @Override
         public boolean canInsert(ItemStack stack) {
            return false;
         }

         @Override
         public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
            context.run((_snowman, _snowmanx) -> {
               int _snowmanxx = this.getExperience(_snowman);

               while (_snowmanxx > 0) {
                  int _snowmanxxx = ExperienceOrbEntity.roundToOrbSize(_snowmanxx);
                  _snowmanxx -= _snowmanxxx;
                  _snowman.spawnEntity(new ExperienceOrbEntity(_snowman, (double)_snowmanx.getX(), (double)_snowmanx.getY() + 0.5, (double)_snowmanx.getZ() + 0.5, _snowmanxxx));
               }

               _snowman.syncWorldEvent(1042, _snowmanx, 0);
            });
            GrindstoneScreenHandler.this.input.setStack(0, ItemStack.EMPTY);
            GrindstoneScreenHandler.this.input.setStack(1, ItemStack.EMPTY);
            return stack;
         }

         private int getExperience(World world) {
            int _snowman = 0;
            _snowman += this.getExperience(GrindstoneScreenHandler.this.input.getStack(0));
            _snowman += this.getExperience(GrindstoneScreenHandler.this.input.getStack(1));
            if (_snowman > 0) {
               int _snowmanx = (int)Math.ceil((double)_snowman / 2.0);
               return _snowmanx + world.random.nextInt(_snowmanx);
            } else {
               return 0;
            }
         }

         private int getExperience(ItemStack stack) {
            int _snowman = 0;
            Map<Enchantment, Integer> _snowmanx = EnchantmentHelper.get(stack);

            for (Entry<Enchantment, Integer> _snowmanxx : _snowmanx.entrySet()) {
               Enchantment _snowmanxxx = _snowmanxx.getKey();
               Integer _snowmanxxxx = _snowmanxx.getValue();
               if (!_snowmanxxx.isCursed()) {
                  _snowman += _snowmanxxx.getMinPower(_snowmanxxxx);
               }
            }

            return _snowman;
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

   @Override
   public void onContentChanged(Inventory inventory) {
      super.onContentChanged(inventory);
      if (inventory == this.input) {
         this.updateResult();
      }
   }

   private void updateResult() {
      ItemStack _snowman = this.input.getStack(0);
      ItemStack _snowmanx = this.input.getStack(1);
      boolean _snowmanxx = !_snowman.isEmpty() || !_snowmanx.isEmpty();
      boolean _snowmanxxx = !_snowman.isEmpty() && !_snowmanx.isEmpty();
      if (!_snowmanxx) {
         this.result.setStack(0, ItemStack.EMPTY);
      } else {
         boolean _snowmanxxxx = !_snowman.isEmpty() && _snowman.getItem() != Items.ENCHANTED_BOOK && !_snowman.hasEnchantments()
            || !_snowmanx.isEmpty() && _snowmanx.getItem() != Items.ENCHANTED_BOOK && !_snowmanx.hasEnchantments();
         if (_snowman.getCount() > 1 || _snowmanx.getCount() > 1 || !_snowmanxxx && _snowmanxxxx) {
            this.result.setStack(0, ItemStack.EMPTY);
            this.sendContentUpdates();
            return;
         }

         int _snowmanxxxxx = 1;
         int _snowmanxxxxxx;
         ItemStack _snowmanxxxxxxx;
         if (_snowmanxxx) {
            if (_snowman.getItem() != _snowmanx.getItem()) {
               this.result.setStack(0, ItemStack.EMPTY);
               this.sendContentUpdates();
               return;
            }

            Item _snowmanxxxxxxxx = _snowman.getItem();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getMaxDamage() - _snowman.getDamage();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getMaxDamage() - _snowmanx.getDamage();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxx.getMaxDamage() * 5 / 100;
            _snowmanxxxxxx = Math.max(_snowmanxxxxxxxx.getMaxDamage() - _snowmanxxxxxxxxxxx, 0);
            _snowmanxxxxxxx = this.transferEnchantments(_snowman, _snowmanx);
            if (!_snowmanxxxxxxx.isDamageable()) {
               if (!ItemStack.areEqual(_snowman, _snowmanx)) {
                  this.result.setStack(0, ItemStack.EMPTY);
                  this.sendContentUpdates();
                  return;
               }

               _snowmanxxxxx = 2;
            }
         } else {
            boolean _snowmanxxxxxxxx = !_snowman.isEmpty();
            _snowmanxxxxxx = _snowmanxxxxxxxx ? _snowman.getDamage() : _snowmanx.getDamage();
            _snowmanxxxxxxx = _snowmanxxxxxxxx ? _snowman : _snowmanx;
         }

         this.result.setStack(0, this.grind(_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx));
      }

      this.sendContentUpdates();
   }

   private ItemStack transferEnchantments(ItemStack target, ItemStack source) {
      ItemStack _snowman = target.copy();
      Map<Enchantment, Integer> _snowmanx = EnchantmentHelper.get(source);

      for (Entry<Enchantment, Integer> _snowmanxx : _snowmanx.entrySet()) {
         Enchantment _snowmanxxx = _snowmanxx.getKey();
         if (!_snowmanxxx.isCursed() || EnchantmentHelper.getLevel(_snowmanxxx, _snowman) == 0) {
            _snowman.addEnchantment(_snowmanxxx, _snowmanxx.getValue());
         }
      }

      return _snowman;
   }

   private ItemStack grind(ItemStack item, int damage, int amount) {
      ItemStack _snowman = item.copy();
      _snowman.removeSubTag("Enchantments");
      _snowman.removeSubTag("StoredEnchantments");
      if (damage > 0) {
         _snowman.setDamage(damage);
      } else {
         _snowman.removeSubTag("Damage");
      }

      _snowman.setCount(amount);
      Map<Enchantment, Integer> _snowmanx = EnchantmentHelper.get(item)
         .entrySet()
         .stream()
         .filter(_snowmanxx -> _snowmanxx.getKey().isCursed())
         .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
      EnchantmentHelper.set(_snowmanx, _snowman);
      _snowman.setRepairCost(0);
      if (_snowman.getItem() == Items.ENCHANTED_BOOK && _snowmanx.size() == 0) {
         _snowman = new ItemStack(Items.BOOK);
         if (item.hasCustomName()) {
            _snowman.setCustomName(item.getName());
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
         _snowman.setRepairCost(AnvilScreenHandler.getNextCost(_snowman.getRepairCost()));
      }

      return _snowman;
   }

   @Override
   public void close(PlayerEntity player) {
      super.close(player);
      this.context.run((_snowmanx, _snowmanxx) -> this.dropInventory(player, _snowmanx, this.input));
   }

   @Override
   public boolean canUse(PlayerEntity player) {
      return canUse(this.context, player, Blocks.GRINDSTONE);
   }

   @Override
   public ItemStack transferSlot(PlayerEntity player, int index) {
      ItemStack _snowman = ItemStack.EMPTY;
      Slot _snowmanx = this.slots.get(index);
      if (_snowmanx != null && _snowmanx.hasStack()) {
         ItemStack _snowmanxx = _snowmanx.getStack();
         _snowman = _snowmanxx.copy();
         ItemStack _snowmanxxx = this.input.getStack(0);
         ItemStack _snowmanxxxx = this.input.getStack(1);
         if (index == 2) {
            if (!this.insertItem(_snowmanxx, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            _snowmanx.onStackChanged(_snowmanxx, _snowman);
         } else if (index != 0 && index != 1) {
            if (!_snowmanxxx.isEmpty() && !_snowmanxxxx.isEmpty()) {
               if (index >= 3 && index < 30) {
                  if (!this.insertItem(_snowmanxx, 30, 39, false)) {
                     return ItemStack.EMPTY;
                  }
               } else if (index >= 30 && index < 39 && !this.insertItem(_snowmanxx, 3, 30, false)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.insertItem(_snowmanxx, 0, 2, false)) {
               return ItemStack.EMPTY;
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
