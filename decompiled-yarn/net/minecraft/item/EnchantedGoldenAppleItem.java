package net.minecraft.item;

public class EnchantedGoldenAppleItem extends Item {
   public EnchantedGoldenAppleItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
