package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class DisjointPairList extends AbstractDoubleList implements PairList {
   private final DoubleList first;
   private final DoubleList second;
   private final boolean inverted;

   public DisjointPairList(DoubleList first, DoubleList second, boolean inverted) {
      this.first = first;
      this.second = second;
      this.inverted = inverted;
   }

   public int size() {
      return this.first.size() + this.second.size();
   }

   @Override
   public boolean forEachPair(PairList.Consumer predicate) {
      return this.inverted ? this.iterateSections((_snowmanx, _snowmanxx, _snowmanxxx) -> predicate.merge(_snowmanxx, _snowmanx, _snowmanxxx)) : this.iterateSections(predicate);
   }

   private boolean iterateSections(PairList.Consumer _snowman) {
      int _snowmanx = this.first.size() - 1;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         if (!_snowman.merge(_snowmanxx, -1, _snowmanxx)) {
            return false;
         }
      }

      if (!_snowman.merge(_snowmanx, -1, _snowmanx)) {
         return false;
      } else {
         for (int _snowmanxxx = 0; _snowmanxxx < this.second.size(); _snowmanxxx++) {
            if (!_snowman.merge(_snowmanx, _snowmanxxx, _snowmanx + 1 + _snowmanxxx)) {
               return false;
            }
         }

         return true;
      }
   }

   public double getDouble(int position) {
      return position < this.first.size() ? this.first.getDouble(position) : this.second.getDouble(position - this.first.size());
   }

   @Override
   public DoubleList getPairs() {
      return this;
   }
}
