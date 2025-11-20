package net.minecraft.util.collection;

import java.util.List;
import java.util.Random;
import net.minecraft.util.Util;

public class WeightedPicker {
   public static int getWeightSum(List<? extends WeightedPicker.Entry> list) {
      int _snowman = 0;
      int _snowmanx = 0;

      for (int _snowmanxx = list.size(); _snowmanx < _snowmanxx; _snowmanx++) {
         WeightedPicker.Entry _snowmanxxx = list.get(_snowmanx);
         _snowman += _snowmanxxx.weight;
      }

      return _snowman;
   }

   public static <T extends WeightedPicker.Entry> T getRandom(Random random, List<T> list, int weightSum) {
      if (weightSum <= 0) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException());
      } else {
         int _snowman = random.nextInt(weightSum);
         return getAt(list, _snowman);
      }
   }

   public static <T extends WeightedPicker.Entry> T getAt(List<T> list, int weightMark) {
      int _snowman = 0;

      for (int _snowmanx = list.size(); _snowman < _snowmanx; _snowman++) {
         T _snowmanxx = (T)list.get(_snowman);
         weightMark -= _snowmanxx.weight;
         if (weightMark < 0) {
            return _snowmanxx;
         }
      }

      return null;
   }

   public static <T extends WeightedPicker.Entry> T getRandom(Random random, List<T> list) {
      return getRandom(random, list, getWeightSum(list));
   }

   public static class Entry {
      protected final int weight;

      public Entry(int _snowman) {
         this.weight = _snowman;
      }
   }
}
