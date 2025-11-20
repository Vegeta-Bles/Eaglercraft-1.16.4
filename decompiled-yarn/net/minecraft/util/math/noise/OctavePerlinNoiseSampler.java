package net.minecraft.util.math.noise;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.ChunkRandom;

public class OctavePerlinNoiseSampler implements NoiseSampler {
   private final PerlinNoiseSampler[] octaveSamplers;
   private final DoubleList field_26445;
   private final double field_20659;
   private final double field_20660;

   public OctavePerlinNoiseSampler(ChunkRandom random, IntStream octaves) {
      this(random, octaves.boxed().collect(ImmutableList.toImmutableList()));
   }

   public OctavePerlinNoiseSampler(ChunkRandom random, List<Integer> octaves) {
      this(random, new IntRBTreeSet(octaves));
   }

   public static OctavePerlinNoiseSampler method_30847(ChunkRandom _snowman, int _snowman, DoubleList _snowman) {
      return new OctavePerlinNoiseSampler(_snowman, Pair.of(_snowman, _snowman));
   }

   private static Pair<Integer, DoubleList> method_30848(IntSortedSet _snowman) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int _snowmanx = -_snowman.firstInt();
         int _snowmanxx = _snowman.lastInt();
         int _snowmanxxx = _snowmanx + _snowmanxx + 1;
         if (_snowmanxxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList _snowmanxxxx = new DoubleArrayList(new double[_snowmanxxx]);
            IntBidirectionalIterator _snowmanxxxxx = _snowman.iterator();

            while (_snowmanxxxxx.hasNext()) {
               int _snowmanxxxxxx = _snowmanxxxxx.nextInt();
               _snowmanxxxx.set(_snowmanxxxxxx + _snowmanx, 1.0);
            }

            return Pair.of(-_snowmanx, _snowmanxxxx);
         }
      }
   }

   private OctavePerlinNoiseSampler(ChunkRandom random, IntSortedSet octaves) {
      this(random, method_30848(octaves));
   }

   private OctavePerlinNoiseSampler(ChunkRandom _snowman, Pair<Integer, DoubleList> _snowman) {
      int _snowmanxx = (Integer)_snowman.getFirst();
      this.field_26445 = (DoubleList)_snowman.getSecond();
      PerlinNoiseSampler _snowmanxxx = new PerlinNoiseSampler(_snowman);
      int _snowmanxxxx = this.field_26445.size();
      int _snowmanxxxxx = -_snowmanxx;
      this.octaveSamplers = new PerlinNoiseSampler[_snowmanxxxx];
      if (_snowmanxxxxx >= 0 && _snowmanxxxxx < _snowmanxxxx) {
         double _snowmanxxxxxx = this.field_26445.getDouble(_snowmanxxxxx);
         if (_snowmanxxxxxx != 0.0) {
            this.octaveSamplers[_snowmanxxxxx] = _snowmanxxx;
         }
      }

      for (int _snowmanxxxxxx = _snowmanxxxxx - 1; _snowmanxxxxxx >= 0; _snowmanxxxxxx--) {
         if (_snowmanxxxxxx < _snowmanxxxx) {
            double _snowmanxxxxxxx = this.field_26445.getDouble(_snowmanxxxxxx);
            if (_snowmanxxxxxxx != 0.0) {
               this.octaveSamplers[_snowmanxxxxxx] = new PerlinNoiseSampler(_snowman);
            } else {
               _snowman.consume(262);
            }
         } else {
            _snowman.consume(262);
         }
      }

      if (_snowmanxxxxx < _snowmanxxxx - 1) {
         long _snowmanxxxxxxx = (long)(_snowmanxxx.sample(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372E18F);
         ChunkRandom _snowmanxxxxxxxx = new ChunkRandom(_snowmanxxxxxxx);

         for (int _snowmanxxxxxxxxx = _snowmanxxxxx + 1; _snowmanxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxx++) {
            if (_snowmanxxxxxxxxx >= 0) {
               double _snowmanxxxxxxxxxx = this.field_26445.getDouble(_snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxx != 0.0) {
                  this.octaveSamplers[_snowmanxxxxxxxxx] = new PerlinNoiseSampler(_snowmanxxxxxxxx);
               } else {
                  _snowmanxxxxxxxx.consume(262);
               }
            } else {
               _snowmanxxxxxxxx.consume(262);
            }
         }
      }

      this.field_20660 = Math.pow(2.0, (double)(-_snowmanxxxxx));
      this.field_20659 = Math.pow(2.0, (double)(_snowmanxxxx - 1)) / (Math.pow(2.0, (double)_snowmanxxxx) - 1.0);
   }

   public double sample(double x, double y, double z) {
      return this.sample(x, y, z, 0.0, 0.0, false);
   }

   public double sample(double x, double y, double z, double _snowman, double _snowman, boolean _snowman) {
      double _snowmanxxx = 0.0;
      double _snowmanxxxx = this.field_20660;
      double _snowmanxxxxx = this.field_20659;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.octaveSamplers.length; _snowmanxxxxxx++) {
         PerlinNoiseSampler _snowmanxxxxxxx = this.octaveSamplers[_snowmanxxxxxx];
         if (_snowmanxxxxxxx != null) {
            _snowmanxxx += this.field_26445.getDouble(_snowmanxxxxxx)
               * _snowmanxxxxxxx.sample(
                  maintainPrecision(x * _snowmanxxxx), _snowman ? -_snowmanxxxxxxx.originY : maintainPrecision(y * _snowmanxxxx), maintainPrecision(z * _snowmanxxxx), _snowman * _snowmanxxxx, _snowman * _snowmanxxxx
               )
               * _snowmanxxxxx;
         }

         _snowmanxxxx *= 2.0;
         _snowmanxxxxx /= 2.0;
      }

      return _snowmanxxx;
   }

   @Nullable
   public PerlinNoiseSampler getOctave(int octave) {
      return this.octaveSamplers[this.octaveSamplers.length - 1 - octave];
   }

   public static double maintainPrecision(double _snowman) {
      return _snowman - (double)MathHelper.lfloor(_snowman / 3.3554432E7 + 0.5) * 3.3554432E7;
   }

   @Override
   public double sample(double x, double y, double _snowman, double _snowman) {
      return this.sample(x, y, 0.0, _snowman, _snowman, false);
   }
}
