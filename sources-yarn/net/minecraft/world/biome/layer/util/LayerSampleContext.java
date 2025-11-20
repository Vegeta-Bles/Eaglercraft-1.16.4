package net.minecraft.world.biome.layer.util;

public interface LayerSampleContext<R extends LayerSampler> extends LayerRandomnessSource {
   void initSeed(long x, long y);

   R createSampler(LayerOperator operator);

   default R createSampler(LayerOperator operator, R parent) {
      return this.createSampler(operator);
   }

   default R createSampler(LayerOperator operator, R arg2, R arg3) {
      return this.createSampler(operator);
   }

   default int choose(int a, int b) {
      return this.nextInt(2) == 0 ? a : b;
   }

   default int choose(int a, int b, int c, int d) {
      int m = this.nextInt(4);
      if (m == 0) {
         return a;
      } else if (m == 1) {
         return b;
      } else {
         return m == 2 ? c : d;
      }
   }
}
