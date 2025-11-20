package net.minecraft.util.shape;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class FractionalPairList implements PairList {
   private final FractionalDoubleList mergedList;
   private final int firstSectionCount;
   private final int secondSectionCount;
   private final int gcd;

   FractionalPairList(int firstSectionCount, int secondSectionCount) {
      this.mergedList = new FractionalDoubleList((int)VoxelShapes.lcm(firstSectionCount, secondSectionCount));
      this.firstSectionCount = firstSectionCount;
      this.secondSectionCount = secondSectionCount;
      this.gcd = IntMath.gcd(firstSectionCount, secondSectionCount);
   }

   @Override
   public boolean forEachPair(PairList.Consumer predicate) {
      int _snowman = this.firstSectionCount / this.gcd;
      int _snowmanx = this.secondSectionCount / this.gcd;

      for (int _snowmanxx = 0; _snowmanxx <= this.mergedList.size(); _snowmanxx++) {
         if (!predicate.merge(_snowmanxx / _snowmanx, _snowmanxx / _snowman, _snowmanxx)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public DoubleList getPairs() {
      return this.mergedList;
   }
}
