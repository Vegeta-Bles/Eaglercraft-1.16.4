package net.minecraft.block;

public abstract class GourdBlock extends Block {
   public GourdBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   public abstract StemBlock getStem();

   public abstract AttachedStemBlock getAttachedStem();
}
