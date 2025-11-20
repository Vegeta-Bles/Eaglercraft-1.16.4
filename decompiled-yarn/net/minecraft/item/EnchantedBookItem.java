package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class EnchantedBookItem extends Item {
   public EnchantedBookItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }

   @Override
   public boolean isEnchantable(ItemStack stack) {
      return false;
   }

   public static ListTag getEnchantmentTag(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null ? _snowman.getList("StoredEnchantments", 10) : new ListTag();
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      super.appendTooltip(stack, world, tooltip, context);
      ItemStack.appendEnchantments(tooltip, getEnchantmentTag(stack));
   }

   public static void addEnchantment(ItemStack stack, EnchantmentLevelEntry entry) {
      ListTag _snowman = getEnchantmentTag(stack);
      boolean _snowmanx = true;
      Identifier _snowmanxx = Registry.ENCHANTMENT.getId(entry.enchantment);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
         CompoundTag _snowmanxxxx = _snowman.getCompound(_snowmanxxx);
         Identifier _snowmanxxxxx = Identifier.tryParse(_snowmanxxxx.getString("id"));
         if (_snowmanxxxxx != null && _snowmanxxxxx.equals(_snowmanxx)) {
            if (_snowmanxxxx.getInt("lvl") < entry.level) {
               _snowmanxxxx.putShort("lvl", (short)entry.level);
            }

            _snowmanx = false;
            break;
         }
      }

      if (_snowmanx) {
         CompoundTag _snowmanxxxx = new CompoundTag();
         _snowmanxxxx.putString("id", String.valueOf(_snowmanxx));
         _snowmanxxxx.putShort("lvl", (short)entry.level);
         _snowman.add(_snowmanxxxx);
      }

      stack.getOrCreateTag().put("StoredEnchantments", _snowman);
   }

   public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
      ItemStack _snowman = new ItemStack(Items.ENCHANTED_BOOK);
      addEnchantment(_snowman, info);
      return _snowman;
   }

   @Override
   public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
      if (group == ItemGroup.SEARCH) {
         for (Enchantment _snowman : Registry.ENCHANTMENT) {
            if (_snowman.type != null) {
               for (int _snowmanx = _snowman.getMinLevel(); _snowmanx <= _snowman.getMaxLevel(); _snowmanx++) {
                  stacks.add(forEnchantment(new EnchantmentLevelEntry(_snowman, _snowmanx)));
               }
            }
         }
      } else if (group.getEnchantments().length != 0) {
         for (Enchantment _snowmanx : Registry.ENCHANTMENT) {
            if (group.containsEnchantments(_snowmanx.type)) {
               stacks.add(forEnchantment(new EnchantmentLevelEntry(_snowmanx, _snowmanx.getMaxLevel())));
            }
         }
      }
   }
}
