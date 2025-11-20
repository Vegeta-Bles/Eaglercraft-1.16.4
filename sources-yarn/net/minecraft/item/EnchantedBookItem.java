package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   public EnchantedBookItem(Item.Settings arg) {
      super(arg);
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
      CompoundTag lv = stack.getTag();
      return lv != null ? lv.getList("StoredEnchantments", 10) : new ListTag();
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      super.appendTooltip(stack, world, tooltip, context);
      ItemStack.appendEnchantments(tooltip, getEnchantmentTag(stack));
   }

   public static void addEnchantment(ItemStack stack, EnchantmentLevelEntry entry) {
      ListTag lv = getEnchantmentTag(stack);
      boolean bl = true;
      Identifier lv2 = Registry.ENCHANTMENT.getId(entry.enchantment);

      for (int i = 0; i < lv.size(); i++) {
         CompoundTag lv3 = lv.getCompound(i);
         Identifier lv4 = Identifier.tryParse(lv3.getString("id"));
         if (lv4 != null && lv4.equals(lv2)) {
            if (lv3.getInt("lvl") < entry.level) {
               lv3.putShort("lvl", (short)entry.level);
            }

            bl = false;
            break;
         }
      }

      if (bl) {
         CompoundTag lv5 = new CompoundTag();
         lv5.putString("id", String.valueOf(lv2));
         lv5.putShort("lvl", (short)entry.level);
         lv.add(lv5);
      }

      stack.getOrCreateTag().put("StoredEnchantments", lv);
   }

   public static ItemStack forEnchantment(EnchantmentLevelEntry info) {
      ItemStack lv = new ItemStack(Items.ENCHANTED_BOOK);
      addEnchantment(lv, info);
      return lv;
   }

   @Override
   public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
      if (group == ItemGroup.SEARCH) {
         for (Enchantment lv : Registry.ENCHANTMENT) {
            if (lv.type != null) {
               for (int i = lv.getMinLevel(); i <= lv.getMaxLevel(); i++) {
                  stacks.add(forEnchantment(new EnchantmentLevelEntry(lv, i)));
               }
            }
         }
      } else if (group.getEnchantments().length != 0) {
         for (Enchantment lv2 : Registry.ENCHANTMENT) {
            if (group.containsEnchantments(lv2.type)) {
               stacks.add(forEnchantment(new EnchantmentLevelEntry(lv2, lv2.getMaxLevel())));
            }
         }
      }
   }
}
