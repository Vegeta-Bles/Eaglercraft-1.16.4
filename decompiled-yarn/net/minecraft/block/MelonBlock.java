package net.minecraft.block;

public class MelonBlock extends GourdBlock {
   protected MelonBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public StemBlock getStem() {
      return (StemBlock)Blocks.MELON_STEM;
   }

   @Override
   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_MELON_STEM;
   }
}
