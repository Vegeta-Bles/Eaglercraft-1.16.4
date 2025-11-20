package net.minecraft.block;

import java.util.Random;

public class VineLogic {
   public static boolean isValidForWeepingStem(BlockState state) {
      return state.isAir();
   }

   public static int method_26381(Random _snowman) {
      double _snowmanx = 1.0;

      int _snowmanxx;
      for (_snowmanxx = 0; _snowman.nextDouble() < _snowmanx; _snowmanxx++) {
         _snowmanx *= 0.826;
      }

      return _snowmanxx;
   }
}
