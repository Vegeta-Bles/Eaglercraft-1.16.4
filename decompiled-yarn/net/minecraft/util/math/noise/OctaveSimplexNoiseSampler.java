package net.minecraft.util.math.noise;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.world.gen.ChunkRandom;

public class OctaveSimplexNoiseSampler implements NoiseSampler {
   private final SimplexNoiseSampler[] octaveSamplers;
   private final double field_20661;
   private final double field_20662;

   public OctaveSimplexNoiseSampler(ChunkRandom _snowman, IntStream _snowman) {
      this(_snowman, _snowman.boxed().collect(ImmutableList.toImmutableList()));
   }

   public OctaveSimplexNoiseSampler(ChunkRandom _snowman, List<Integer> _snowman) {
      this(_snowman, new IntRBTreeSet(_snowman));
   }

   private OctaveSimplexNoiseSampler(ChunkRandom _snowman, IntSortedSet _snowman) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int _snowmanxx = -_snowman.firstInt();
         int _snowmanxxx = _snowman.lastInt();
         int _snowmanxxxx = _snowmanxx + _snowmanxxx + 1;
         if (_snowmanxxxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            SimplexNoiseSampler _snowmanxxxxx = new SimplexNoiseSampler(_snowman);
            int _snowmanxxxxxx = _snowmanxxx;
            this.octaveSamplers = new SimplexNoiseSampler[_snowmanxxxx];
            if (_snowmanxxx >= 0 && _snowmanxxx < _snowmanxxxx && _snowman.contains(0)) {
               this.octaveSamplers[_snowmanxxx] = _snowmanxxxxx;
            }

            for (int _snowmanxxxxxxx = _snowmanxxx + 1; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
               if (_snowmanxxxxxxx >= 0 && _snowman.contains(_snowmanxxxxxx - _snowmanxxxxxxx)) {
                  this.octaveSamplers[_snowmanxxxxxxx] = new SimplexNoiseSampler(_snowman);
               } else {
                  _snowman.consume(262);
               }
            }

            if (_snowmanxxx > 0) {
               long _snowmanxxxxxxxx = (long)(_snowmanxxxxx.method_22416(_snowmanxxxxx.originX, _snowmanxxxxx.originY, _snowmanxxxxx.originZ) * 9.223372E18F);
               ChunkRandom _snowmanxxxxxxxxx = new ChunkRandom(_snowmanxxxxxxxx);

               for (int _snowmanxxxxxxxxxx = _snowmanxxxxxx - 1; _snowmanxxxxxxxxxx >= 0; _snowmanxxxxxxxxxx--) {
                  if (_snowmanxxxxxxxxxx < _snowmanxxxx && _snowman.contains(_snowmanxxxxxx - _snowmanxxxxxxxxxx)) {
                     this.octaveSamplers[_snowmanxxxxxxxxxx] = new SimplexNoiseSampler(_snowmanxxxxxxxxx);
                  } else {
                     _snowmanxxxxxxxxx.consume(262);
                  }
               }
            }

            this.field_20662 = Math.pow(2.0, (double)_snowmanxxx);
            this.field_20661 = 1.0 / (Math.pow(2.0, (double)_snowmanxxxx) - 1.0);
         }
      }
   }

   public double sample(double x, double y, boolean _snowman) {
      double _snowmanx = 0.0;
      double _snowmanxx = this.field_20662;
      double _snowmanxxx = this.field_20661;

      for (SimplexNoiseSampler _snowmanxxxx : this.octaveSamplers) {
         if (_snowmanxxxx != null) {
            _snowmanx += _snowmanxxxx.sample(x * _snowmanxx + (_snowman ? _snowmanxxxx.originX : 0.0), y * _snowmanxx + (_snowman ? _snowmanxxxx.originY : 0.0)) * _snowmanxxx;
         }

         _snowmanxx /= 2.0;
         _snowmanxxx *= 2.0;
      }

      return _snowmanx;
   }

   @Override
   public double sample(double x, double y, double _snowman, double _snowman) {
      return this.sample(x, y, true) * 0.55;
   }
}
