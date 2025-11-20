package net.minecraft;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntStack;
import java.util.function.Predicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class class_5459 {
   public static class_5459.class_5460 method_30574(BlockPos arg, Direction.Axis arg2, int i, Direction.Axis arg3, int j, Predicate<BlockPos> predicate) {
      BlockPos.Mutable lv = arg.mutableCopy();
      Direction lv2 = Direction.get(Direction.AxisDirection.NEGATIVE, arg2);
      Direction lv3 = lv2.getOpposite();
      Direction lv4 = Direction.get(Direction.AxisDirection.NEGATIVE, arg3);
      Direction lv5 = lv4.getOpposite();
      int k = method_30575(predicate, lv.set(arg), lv2, i);
      int l = method_30575(predicate, lv.set(arg), lv3, i);
      int m = k;
      class_5459.IntBounds[] lvs = new class_5459.IntBounds[k + 1 + l];
      lvs[k] = new class_5459.IntBounds(method_30575(predicate, lv.set(arg), lv4, j), method_30575(predicate, lv.set(arg), lv5, j));
      int n = lvs[k].min;

      for (int o = 1; o <= k; o++) {
         class_5459.IntBounds lv6 = lvs[m - (o - 1)];
         lvs[m - o] = new class_5459.IntBounds(
            method_30575(predicate, lv.set(arg).move(lv2, o), lv4, lv6.min), method_30575(predicate, lv.set(arg).move(lv2, o), lv5, lv6.max)
         );
      }

      for (int p = 1; p <= l; p++) {
         class_5459.IntBounds lv7 = lvs[m + p - 1];
         lvs[m + p] = new class_5459.IntBounds(
            method_30575(predicate, lv.set(arg).move(lv3, p), lv4, lv7.min), method_30575(predicate, lv.set(arg).move(lv3, p), lv5, lv7.max)
         );
      }

      int q = 0;
      int r = 0;
      int s = 0;
      int t = 0;
      int[] is = new int[lvs.length];

      for (int u = n; u >= 0; u--) {
         for (int v = 0; v < lvs.length; v++) {
            class_5459.IntBounds lv8 = lvs[v];
            int w = n - lv8.min;
            int x = n + lv8.max;
            is[v] = u >= w && u <= x ? x + 1 - u : 0;
         }

         Pair<class_5459.IntBounds, Integer> pair = method_30576(is);
         class_5459.IntBounds lv9 = (class_5459.IntBounds)pair.getFirst();
         int y = 1 + lv9.max - lv9.min;
         int z = (Integer)pair.getSecond();
         if (y * z > s * t) {
            q = lv9.min;
            r = u;
            s = y;
            t = z;
         }
      }

      return new class_5459.class_5460(arg.offset(arg2, q - m).offset(arg3, r - n), s, t);
   }

   private static int method_30575(Predicate<BlockPos> predicate, BlockPos.Mutable arg, Direction arg2, int i) {
      int j = 0;

      while (j < i && predicate.test(arg.move(arg2))) {
         j++;
      }

      return j;
   }

   @VisibleForTesting
   static Pair<class_5459.IntBounds, Integer> method_30576(int[] is) {
      int i = 0;
      int j = 0;
      int k = 0;
      IntStack intStack = new IntArrayList();
      intStack.push(0);

      for (int l = 1; l <= is.length; l++) {
         int m = l == is.length ? 0 : is[l];

         while (!intStack.isEmpty()) {
            int n = is[intStack.topInt()];
            if (m >= n) {
               intStack.push(l);
               break;
            }

            intStack.popInt();
            int o = intStack.isEmpty() ? 0 : intStack.topInt() + 1;
            if (n * (l - o) > k * (j - i)) {
               j = l;
               i = o;
               k = n;
            }
         }

         if (intStack.isEmpty()) {
            intStack.push(l);
         }
      }

      return new Pair(new class_5459.IntBounds(i, j - 1), k);
   }

   public static class IntBounds {
      public final int min;
      public final int max;

      public IntBounds(int min, int max) {
         this.min = min;
         this.max = max;
      }

      @Override
      public String toString() {
         return "IntBounds{min=" + this.min + ", max=" + this.max + '}';
      }
   }

   public static class class_5460 {
      public final BlockPos field_25936;
      public final int field_25937;
      public final int field_25938;

      public class_5460(BlockPos arg, int i, int j) {
         this.field_25936 = arg;
         this.field_25937 = i;
         this.field_25938 = j;
      }
   }
}
