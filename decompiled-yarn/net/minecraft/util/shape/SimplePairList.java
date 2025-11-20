package net.minecraft.util.shape;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

public final class SimplePairList implements PairList {
   private final DoubleArrayList valueIndices;
   private final IntArrayList minValues;
   private final IntArrayList maxValues;

   protected SimplePairList(DoubleList first, DoubleList second, boolean includeFirstOnly, boolean includeSecondOnly) {
      int _snowman = 0;
      int _snowmanx = 0;
      double _snowmanxx = Double.NaN;
      int _snowmanxxx = first.size();
      int _snowmanxxxx = second.size();
      int _snowmanxxxxx = _snowmanxxx + _snowmanxxxx;
      this.valueIndices = new DoubleArrayList(_snowmanxxxxx);
      this.minValues = new IntArrayList(_snowmanxxxxx);
      this.maxValues = new IntArrayList(_snowmanxxxxx);

      while (true) {
         boolean _snowmanxxxxxx = _snowman < _snowmanxxx;
         boolean _snowmanxxxxxxx = _snowmanx < _snowmanxxxx;
         if (!_snowmanxxxxxx && !_snowmanxxxxxxx) {
            if (this.valueIndices.isEmpty()) {
               this.valueIndices.add(Math.min(first.getDouble(_snowmanxxx - 1), second.getDouble(_snowmanxxxx - 1)));
            }

            return;
         }

         boolean _snowmanxxxxxxxx = _snowmanxxxxxx && (!_snowmanxxxxxxx || first.getDouble(_snowman) < second.getDouble(_snowmanx) + 1.0E-7);
         double _snowmanxxxxxxxxx = _snowmanxxxxxxxx ? first.getDouble(_snowman++) : second.getDouble(_snowmanx++);
         if ((_snowman != 0 && _snowmanxxxxxx || _snowmanxxxxxxxx || includeSecondOnly) && (_snowmanx != 0 && _snowmanxxxxxxx || !_snowmanxxxxxxxx || includeFirstOnly)) {
            if (!(_snowmanxx >= _snowmanxxxxxxxxx - 1.0E-7)) {
               this.minValues.add(_snowman - 1);
               this.maxValues.add(_snowmanx - 1);
               this.valueIndices.add(_snowmanxxxxxxxxx);
               _snowmanxx = _snowmanxxxxxxxxx;
            } else if (!this.valueIndices.isEmpty()) {
               this.minValues.set(this.minValues.size() - 1, _snowman - 1);
               this.maxValues.set(this.maxValues.size() - 1, _snowmanx - 1);
            }
         }
      }
   }

   @Override
   public boolean forEachPair(PairList.Consumer predicate) {
      for (int _snowman = 0; _snowman < this.valueIndices.size() - 1; _snowman++) {
         if (!predicate.merge(this.minValues.getInt(_snowman), this.maxValues.getInt(_snowman), _snowman)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList getPairs() {
      return this.valueIndices;
   }
}
