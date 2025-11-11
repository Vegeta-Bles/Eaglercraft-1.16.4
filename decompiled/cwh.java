import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;

public enum cwh implements cwr {
   a;

   private static final IntSet b = new IntOpenHashSet(new int[]{26, 11, 12, 13, 140, 30, 31, 158, 10});
   private static final IntSet c = new IntOpenHashSet(new int[]{168, 169, 21, 22, 23, 149, 151});

   private cwh() {
   }

   @Override
   public int a(cvk var1, int var2, int var3, int var4, int var5, int var6) {
      if (_snowman == 14) {
         if (cvx.b(_snowman) || cvx.b(_snowman) || cvx.b(_snowman) || cvx.b(_snowman)) {
            return 15;
         }
      } else if (c.contains(_snowman)) {
         if (!c(_snowman) || !c(_snowman) || !c(_snowman) || !c(_snowman)) {
            return 23;
         }

         if (cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman)) {
            return 16;
         }
      } else if (_snowman != 3 && _snowman != 34 && _snowman != 20) {
         if (b.contains(_snowman)) {
            if (!cvx.a(_snowman) && (cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman))) {
               return 26;
            }
         } else if (_snowman != 37 && _snowman != 38) {
            if (!cvx.a(_snowman) && _snowman != 7 && _snowman != 6 && (cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman))) {
               return 16;
            }
         } else if (!cvx.a(_snowman) && !cvx.a(_snowman) && !cvx.a(_snowman) && !cvx.a(_snowman) && (!this.d(_snowman) || !this.d(_snowman) || !this.d(_snowman) || !this.d(_snowman))) {
            return 2;
         }
      } else if (!cvx.a(_snowman) && (cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman) || cvx.a(_snowman))) {
         return 25;
      }

      return _snowman;
   }

   private static boolean c(int var0) {
      return c.contains(_snowman) || _snowman == 4 || _snowman == 5 || cvx.a(_snowman);
   }

   private boolean d(int var1) {
      return _snowman == 37 || _snowman == 38 || _snowman == 39 || _snowman == 165 || _snowman == 166 || _snowman == 167;
   }
}
