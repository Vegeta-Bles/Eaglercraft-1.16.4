package net.minecraft.world.biome.layer.util;

public interface LayerSampleContext<R extends LayerSampler> extends LayerRandomnessSource {
   void initSeed(long x, long y);

   R createSampler(LayerOperator operator);

   default R createSampler(LayerOperator operator, R parent) {
      return this.createSampler(operator);
   }

   default R createSampler(LayerOperator operator, R _snowman, R _snowman) {
      return this.createSampler(operator);
   }

   default int choose(int a, int b) {
      return this.nextInt(2) == 0 ? a : b;
   }

   default int choose(int a, int b, int c, int d) {
      int _snowman = this.nextInt(4);
      if (_snowman == 0) {
         return a;
      } else if (_snowman == 1) {
         return b;
      } else {
         return _snowman == 2 ? c : d;
      }
   }
}
