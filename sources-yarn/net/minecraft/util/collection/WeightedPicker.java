package net.minecraft.util.collection;

import java.util.List;
import java.util.Random;
import net.minecraft.util.Util;

public class WeightedPicker {
   public static int getWeightSum(List<? extends WeightedPicker.Entry> list) {
      int i = 0;
      int j = 0;

      for (int k = list.size(); j < k; j++) {
         WeightedPicker.Entry lv = list.get(j);
         i += lv.weight;
      }

      return i;
   }

   public static <T extends WeightedPicker.Entry> T getRandom(Random random, List<T> list, int weightSum) {
      if (weightSum <= 0) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException());
      } else {
         int j = random.nextInt(weightSum);
         return getAt(list, j);
      }
   }

   public static <T extends WeightedPicker.Entry> T getAt(List<T> list, int weightMark) {
      int j = 0;

      for (int k = list.size(); j < k; j++) {
         T lv = (T)list.get(j);
         weightMark -= lv.weight;
         if (weightMark < 0) {
            return lv;
         }
      }

      return null;
   }

   public static <T extends WeightedPicker.Entry> T getRandom(Random random, List<T> list) {
      return getRandom(random, list, getWeightSum(list));
   }

   public static class Entry {
      protected final int weight;

      public Entry(int i) {
         this.weight = i;
      }
   }
}
