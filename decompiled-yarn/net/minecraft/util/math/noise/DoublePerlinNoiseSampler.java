package net.minecraft.util.math.noise;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.minecraft.world.gen.ChunkRandom;

public class DoublePerlinNoiseSampler {
   private final double amplitude;
   private final OctavePerlinNoiseSampler firstSampler;
   private final OctavePerlinNoiseSampler secondSampler;

   public static DoublePerlinNoiseSampler method_30846(ChunkRandom _snowman, int _snowman, DoubleList _snowman) {
      return new DoublePerlinNoiseSampler(_snowman, _snowman, _snowman);
   }

   private DoublePerlinNoiseSampler(ChunkRandom _snowman, int _snowman, DoubleList _snowman) {
      this.firstSampler = OctavePerlinNoiseSampler.method_30847(_snowman, _snowman, _snowman);
      this.secondSampler = OctavePerlinNoiseSampler.method_30847(_snowman, _snowman, _snowman);
      int _snowmanxxx = Integer.MAX_VALUE;
      int _snowmanxxxx = Integer.MIN_VALUE;
      DoubleListIterator _snowmanxxxxx = _snowman.iterator();

      while (_snowmanxxxxx.hasNext()) {
         int _snowmanxxxxxx = _snowmanxxxxx.nextIndex();
         double _snowmanxxxxxxx = _snowmanxxxxx.nextDouble();
         if (_snowmanxxxxxxx != 0.0) {
            _snowmanxxx = Math.min(_snowmanxxx, _snowmanxxxxxx);
            _snowmanxxxx = Math.max(_snowmanxxxx, _snowmanxxxxxx);
         }
      }

      this.amplitude = 0.16666666666666666 / createAmplitude(_snowmanxxxx - _snowmanxxx);
   }

   private static double createAmplitude(int octaves) {
      return 0.1 * (1.0 + 1.0 / (double)(octaves + 1));
   }

   public double sample(double x, double y, double z) {
      double _snowman = x * 1.0181268882175227;
      double _snowmanx = y * 1.0181268882175227;
      double _snowmanxx = z * 1.0181268882175227;
      return (this.firstSampler.sample(x, y, z) + this.secondSampler.sample(_snowman, _snowmanx, _snowmanxx)) * this.amplitude;
   }
}
