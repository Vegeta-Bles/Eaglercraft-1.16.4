package net.minecraft.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class RepairItemRecipe extends SpecialCraftingRecipe {
   public RepairItemRecipe(Identifier _snowman) {
      super(_snowman);
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      List<ItemStack> _snowmanxx = Lists.newArrayList();

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = _snowman.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            _snowmanxx.add(_snowmanxxxx);
            if (_snowmanxx.size() > 1) {
               ItemStack _snowmanxxxxx = _snowmanxx.get(0);
               if (_snowmanxxxx.getItem() != _snowmanxxxxx.getItem() || _snowmanxxxxx.getCount() != 1 || _snowmanxxxx.getCount() != 1 || !_snowmanxxxxx.getItem().isDamageable()) {
                  return false;
               }
            }
         }
      }

      return _snowmanxx.size() == 2;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      List<ItemStack> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         ItemStack _snowmanxxx = _snowman.getStack(_snowmanxx);
         if (!_snowmanxxx.isEmpty()) {
            _snowmanx.add(_snowmanxxx);
            if (_snowmanx.size() > 1) {
               ItemStack _snowmanxxxx = _snowmanx.get(0);
               if (_snowmanxxx.getItem() != _snowmanxxxx.getItem() || _snowmanxxxx.getCount() != 1 || _snowmanxxx.getCount() != 1 || !_snowmanxxxx.getItem().isDamageable()) {
                  return ItemStack.EMPTY;
               }
            }
         }
      }

      if (_snowmanx.size() == 2) {
         ItemStack _snowmanxxx = _snowmanx.get(0);
         ItemStack _snowmanxxxx = _snowmanx.get(1);
         if (_snowmanxxx.getItem() == _snowmanxxxx.getItem() && _snowmanxxx.getCount() == 1 && _snowmanxxxx.getCount() == 1 && _snowmanxxx.getItem().isDamageable()) {
            Item _snowmanxxxxx = _snowmanxxx.getItem();
            int _snowmanxxxxxx = _snowmanxxxxx.getMaxDamage() - _snowmanxxx.getDamage();
            int _snowmanxxxxxxx = _snowmanxxxxx.getMaxDamage() - _snowmanxxxx.getDamage();
            int _snowmanxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxx + _snowmanxxxxx.getMaxDamage() * 5 / 100;
            int _snowmanxxxxxxxxx = _snowmanxxxxx.getMaxDamage() - _snowmanxxxxxxxx;
            if (_snowmanxxxxxxxxx < 0) {
               _snowmanxxxxxxxxx = 0;
            }

            ItemStack _snowmanxxxxxxxxxx = new ItemStack(_snowmanxxx.getItem());
            _snowmanxxxxxxxxxx.setDamage(_snowmanxxxxxxxxx);
            Map<Enchantment, Integer> _snowmanxxxxxxxxxxx = Maps.newHashMap();
            Map<Enchantment, Integer> _snowmanxxxxxxxxxxxx = EnchantmentHelper.get(_snowmanxxx);
            Map<Enchantment, Integer> _snowmanxxxxxxxxxxxxx = EnchantmentHelper.get(_snowmanxxxx);
            Registry.ENCHANTMENT.stream().filter(Enchantment::isCursed).forEach(_snowmanxxxxxxxxxxxxxx -> {
               int _snowmanxxxxxxxxxxxxxxx = Math.max(_snowman.getOrDefault(_snowmanxxxxxxxxxxxxxx, 0), _snowman.getOrDefault(_snowmanxxxxxxxxxxxxxx, 0));
               if (_snowmanxxxxxxxxxxxxxxx > 0) {
                  _snowman.put(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
               }
            });
            if (!_snowmanxxxxxxxxxxx.isEmpty()) {
               EnchantmentHelper.set(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx);
            }

            return _snowmanxxxxxxxxxx;
         }
      }

      return ItemStack.EMPTY;
   }

   @Override
   public boolean fits(int width, int height) {
      return width * height >= 2;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.REPAIR_ITEM;
   }
}
