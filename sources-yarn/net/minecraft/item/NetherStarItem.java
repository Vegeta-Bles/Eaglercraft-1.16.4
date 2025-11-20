package net.minecraft.item;

public class NetherStarItem extends Item {
   public NetherStarItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
