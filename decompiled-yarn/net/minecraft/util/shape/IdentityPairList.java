package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class IdentityPairList implements PairList {
   private final DoubleList merged;

   public IdentityPairList(DoubleList values) {
      this.merged = values;
   }

   @Override
   public boolean forEachPair(PairList.Consumer predicate) {
      for (int _snowman = 0; _snowman <= this.merged.size(); _snowman++) {
         if (!predicate.merge(_snowman, _snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList getPairs() {
      return this.merged;
   }
}
