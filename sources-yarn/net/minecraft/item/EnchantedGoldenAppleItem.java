package net.minecraft.item;

public class EnchantedGoldenAppleItem extends Item {
   public EnchantedGoldenAppleItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
