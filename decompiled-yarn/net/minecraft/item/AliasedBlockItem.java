package net.minecraft.item;

import net.minecraft.block.Block;

public class AliasedBlockItem extends BlockItem {
   public AliasedBlockItem(Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public String getTranslationKey() {
      return this.getOrCreateTranslationKey();
   }
}
