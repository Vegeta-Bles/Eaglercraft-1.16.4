package net.minecraft.enchantment;

import net.minecraft.entity.EquipmentSlot;

public class LureEnchantment extends Enchantment {
   protected LureEnchantment(Enchantment.Rarity _snowman, EnchantmentTarget _snowman, EquipmentSlot... _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public int getMinPower(int level) {
      return 15 + (level - 1) * 9;
   }

   @Override
   public int getMaxPower(int level) {
      return super.getMinPower(level) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
