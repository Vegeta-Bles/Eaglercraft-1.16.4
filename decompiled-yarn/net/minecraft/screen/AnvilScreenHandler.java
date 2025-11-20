package net.minecraft.screen;

import java.util.Map;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilScreenHandler extends ForgingScreenHandler {
   private static final Logger LOGGER = LogManager.getLogger();
   private int repairItemUsage;
   private String newItemName;
   private final Property levelCost = Property.create();

   public AnvilScreenHandler(int syncId, PlayerInventory inventory) {
      this(syncId, inventory, ScreenHandlerContext.EMPTY);
   }

   public AnvilScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
      super(ScreenHandlerType.ANVIL, syncId, inventory, context);
      this.addProperty(this.levelCost);
   }

   @Override
   protected boolean canUse(BlockState state) {
      return state.isIn(BlockTags.ANVIL);
   }

   @Override
   protected boolean canTakeOutput(PlayerEntity player, boolean present) {
      return (player.abilities.creativeMode || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0;
   }

   @Override
   protected ItemStack onTakeOutput(PlayerEntity player, ItemStack stack) {
      if (!player.abilities.creativeMode) {
         player.addExperienceLevels(-this.levelCost.get());
      }

      this.input.setStack(0, ItemStack.EMPTY);
      if (this.repairItemUsage > 0) {
         ItemStack _snowman = this.input.getStack(1);
         if (!_snowman.isEmpty() && _snowman.getCount() > this.repairItemUsage) {
            _snowman.decrement(this.repairItemUsage);
            this.input.setStack(1, _snowman);
         } else {
            this.input.setStack(1, ItemStack.EMPTY);
         }
      } else {
         this.input.setStack(1, ItemStack.EMPTY);
      }

      this.levelCost.set(0);
      this.context.run((_snowmanx, _snowmanxx) -> {
         BlockState _snowmanxxx = _snowmanx.getBlockState(_snowmanxx);
         if (!player.abilities.creativeMode && _snowmanxxx.isIn(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.12F) {
            BlockState _snowmanxxx = AnvilBlock.getLandingState(_snowmanxxx);
            if (_snowmanxxx == null) {
               _snowmanx.removeBlock(_snowmanxx, false);
               _snowmanx.syncWorldEvent(1029, _snowmanxx, 0);
            } else {
               _snowmanx.setBlockState(_snowmanxx, _snowmanxxx, 2);
               _snowmanx.syncWorldEvent(1030, _snowmanxx, 0);
            }
         } else {
            _snowmanx.syncWorldEvent(1030, _snowmanxx, 0);
         }
      });
      return stack;
   }

   @Override
   public void updateResult() {
      ItemStack _snowman = this.input.getStack(0);
      this.levelCost.set(1);
      int _snowmanx = 0;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      if (_snowman.isEmpty()) {
         this.output.setStack(0, ItemStack.EMPTY);
         this.levelCost.set(0);
      } else {
         ItemStack _snowmanxxxx = _snowman.copy();
         ItemStack _snowmanxxxxx = this.input.getStack(1);
         Map<Enchantment, Integer> _snowmanxxxxxx = EnchantmentHelper.get(_snowmanxxxx);
         _snowmanxx += _snowman.getRepairCost() + (_snowmanxxxxx.isEmpty() ? 0 : _snowmanxxxxx.getRepairCost());
         this.repairItemUsage = 0;
         if (!_snowmanxxxxx.isEmpty()) {
            boolean _snowmanxxxxxxx = _snowmanxxxxx.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantmentTag(_snowmanxxxxx).isEmpty();
            if (_snowmanxxxx.isDamageable() && _snowmanxxxx.getItem().canRepair(_snowman, _snowmanxxxxx)) {
               int _snowmanxxxxxxxx = Math.min(_snowmanxxxx.getDamage(), _snowmanxxxx.getMaxDamage() / 4);
               if (_snowmanxxxxxxxx <= 0) {
                  this.output.setStack(0, ItemStack.EMPTY);
                  this.levelCost.set(0);
                  return;
               }

               int _snowmanxxxxxxxxx;
               for (_snowmanxxxxxxxxx = 0; _snowmanxxxxxxxx > 0 && _snowmanxxxxxxxxx < _snowmanxxxxx.getCount(); _snowmanxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxx = _snowmanxxxx.getDamage() - _snowmanxxxxxxxx;
                  _snowmanxxxx.setDamage(_snowmanxxxxxxxxxx);
                  _snowmanx++;
                  _snowmanxxxxxxxx = Math.min(_snowmanxxxx.getDamage(), _snowmanxxxx.getMaxDamage() / 4);
               }

               this.repairItemUsage = _snowmanxxxxxxxxx;
            } else {
               if (!_snowmanxxxxxxx && (_snowmanxxxx.getItem() != _snowmanxxxxx.getItem() || !_snowmanxxxx.isDamageable())) {
                  this.output.setStack(0, ItemStack.EMPTY);
                  this.levelCost.set(0);
                  return;
               }

               if (_snowmanxxxx.isDamageable() && !_snowmanxxxxxxx) {
                  int _snowmanxxxxxxxxx = _snowman.getMaxDamage() - _snowman.getDamage();
                  int _snowmanxxxxxxxxxx = _snowmanxxxxx.getMaxDamage() - _snowmanxxxxx.getDamage();
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx + _snowmanxxxx.getMaxDamage() * 12 / 100;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxx = _snowmanxxxx.getMaxDamage() - _snowmanxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx < 0) {
                     _snowmanxxxxxxxxxxxxx = 0;
                  }

                  if (_snowmanxxxxxxxxxxxxx < _snowmanxxxx.getDamage()) {
                     _snowmanxxxx.setDamage(_snowmanxxxxxxxxxxxxx);
                     _snowmanx += 2;
                  }
               }

               Map<Enchantment, Integer> _snowmanxxxxxxxxxxxxxx = EnchantmentHelper.get(_snowmanxxxxx);
               boolean _snowmanxxxxxxxxxxxxxxx = false;
               boolean _snowmanxxxxxxxxxxxxxxxx = false;

               for (Enchantment _snowmanxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx.keySet()) {
                  if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                     int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.getOrDefault(_snowmanxxxxxxxxxxxxxxxxx, 0);
                     int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.get(_snowmanxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxxxxxxxx
                        ? _snowmanxxxxxxxxxxxxxxxxxxx + 1
                        : Math.max(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                     boolean _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.isAcceptableItem(_snowman);
                     if (this.player.abilities.creativeMode || _snowman.getItem() == Items.ENCHANTED_BOOK) {
                        _snowmanxxxxxxxxxxxxxxxxxxxx = true;
                     }

                     for (Enchantment _snowmanxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxx.keySet()) {
                        if (_snowmanxxxxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx.canCombine(_snowmanxxxxxxxxxxxxxxxxxxxxx)) {
                           _snowmanxxxxxxxxxxxxxxxxxxxx = false;
                           _snowmanx++;
                        }
                     }

                     if (!_snowmanxxxxxxxxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxx = true;
                     } else {
                        _snowmanxxxxxxxxxxxxxxx = true;
                        if (_snowmanxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxxxxx.getMaxLevel()) {
                           _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.getMaxLevel();
                        }

                        _snowmanxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0;
                        switch (_snowmanxxxxxxxxxxxxxxxxx.getRarity()) {
                           case COMMON:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 1;
                              break;
                           case UNCOMMON:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 2;
                              break;
                           case RARE:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 4;
                              break;
                           case VERY_RARE:
                              _snowmanxxxxxxxxxxxxxxxxxxxxxx = 8;
                        }

                        if (_snowmanxxxxxxx) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxx = Math.max(1, _snowmanxxxxxxxxxxxxxxxxxxxxxx / 2);
                        }

                        _snowmanx += _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
                        if (_snowman.getCount() > 1) {
                           _snowmanx = 40;
                        }
                     }
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxx) {
                  this.output.setStack(0, ItemStack.EMPTY);
                  this.levelCost.set(0);
                  return;
               }
            }
         }

         if (StringUtils.isBlank(this.newItemName)) {
            if (_snowman.hasCustomName()) {
               _snowmanxxx = 1;
               _snowmanx += _snowmanxxx;
               _snowmanxxxx.removeCustomName();
            }
         } else if (!this.newItemName.equals(_snowman.getName().getString())) {
            _snowmanxxx = 1;
            _snowmanx += _snowmanxxx;
            _snowmanxxxx.setCustomName(new LiteralText(this.newItemName));
         }

         this.levelCost.set(_snowmanxx + _snowmanx);
         if (_snowmanx <= 0) {
            _snowmanxxxx = ItemStack.EMPTY;
         }

         if (_snowmanxxx == _snowmanx && _snowmanxxx > 0 && this.levelCost.get() >= 40) {
            this.levelCost.set(39);
         }

         if (this.levelCost.get() >= 40 && !this.player.abilities.creativeMode) {
            _snowmanxxxx = ItemStack.EMPTY;
         }

         if (!_snowmanxxxx.isEmpty()) {
            int _snowmanxxxxxxx = _snowmanxxxx.getRepairCost();
            if (!_snowmanxxxxx.isEmpty() && _snowmanxxxxxxx < _snowmanxxxxx.getRepairCost()) {
               _snowmanxxxxxxx = _snowmanxxxxx.getRepairCost();
            }

            if (_snowmanxxx != _snowmanx || _snowmanxxx == 0) {
               _snowmanxxxxxxx = getNextCost(_snowmanxxxxxxx);
            }

            _snowmanxxxx.setRepairCost(_snowmanxxxxxxx);
            EnchantmentHelper.set(_snowmanxxxxxx, _snowmanxxxx);
         }

         this.output.setStack(0, _snowmanxxxx);
         this.sendContentUpdates();
      }
   }

   public static int getNextCost(int cost) {
      return cost * 2 + 1;
   }

   public void setNewItemName(String _snowman) {
      this.newItemName = _snowman;
      if (this.getSlot(2).hasStack()) {
         ItemStack _snowmanx = this.getSlot(2).getStack();
         if (StringUtils.isBlank(_snowman)) {
            _snowmanx.removeCustomName();
         } else {
            _snowmanx.setCustomName(new LiteralText(this.newItemName));
         }
      }

      this.updateResult();
   }

   public int getLevelCost() {
      return this.levelCost.get();
   }
}
