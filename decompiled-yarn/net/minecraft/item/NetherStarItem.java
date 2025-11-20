package net.minecraft.item;

public class NetherStarItem extends Item {
   public NetherStarItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
