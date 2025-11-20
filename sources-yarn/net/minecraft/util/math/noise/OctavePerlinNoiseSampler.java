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

   public static OctavePerlinNoiseSampler method_30847(ChunkRandom arg, int i, DoubleList doubleList) {
      return new OctavePerlinNoiseSampler(arg, Pair.of(i, doubleList));
   }

   private static Pair<Integer, DoubleList> method_30848(IntSortedSet intSortedSet) {
      if (intSortedSet.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int i = -intSortedSet.firstInt();
         int j = intSortedSet.lastInt();
         int k = i + j + 1;
         if (k < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList doubleList = new DoubleArrayList(new double[k]);
            IntBidirectionalIterator intBidirectionalIterator = intSortedSet.iterator();

            while (intBidirectionalIterator.hasNext()) {
               int l = intBidirectionalIterator.nextInt();
               doubleList.set(l + i, 1.0);
            }

            return Pair.of(-i, doubleList);
         }
      }
   }

   private OctavePerlinNoiseSampler(ChunkRandom random, IntSortedSet octaves) {
      this(random, method_30848(octaves));
   }

   private OctavePerlinNoiseSampler(ChunkRandom arg, Pair<Integer, DoubleList> pair) {
      int i = (Integer)pair.getFirst();
      this.field_26445 = (DoubleList)pair.getSecond();
      PerlinNoiseSampler lv = new PerlinNoiseSampler(arg);
      int j = this.field_26445.size();
      int k = -i;
      this.octaveSamplers = new PerlinNoiseSampler[j];
      if (k >= 0 && k < j) {
         double d = this.field_26445.getDouble(k);
         if (d != 0.0) {
            this.octaveSamplers[k] = lv;
         }
      }

      for (int l = k - 1; l >= 0; l--) {
         if (l < j) {
            double e = this.field_26445.getDouble(l);
            if (e != 0.0) {
               this.octaveSamplers[l] = new PerlinNoiseSampler(arg);
            } else {
               arg.consume(262);
            }
         } else {
            arg.consume(262);
         }
      }

      if (k < j - 1) {
         long m = (long)(lv.sample(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372E18F);
         ChunkRandom lv2 = new ChunkRandom(m);

         for (int n = k + 1; n < j; n++) {
            if (n >= 0) {
               double f = this.field_26445.getDouble(n);
               if (f != 0.0) {
                  this.octaveSamplers[n] = new PerlinNoiseSampler(lv2);
               } else {
                  lv2.consume(262);
               }
            } else {
               lv2.consume(262);
            }
         }
      }

      this.field_20660 = Math.pow(2.0, (double)(-k));
      this.field_20659 = Math.pow(2.0, (double)(j - 1)) / (Math.pow(2.0, (double)j) - 1.0);
   }

   public double sample(double x, double y, double z) {
      return this.sample(x, y, z, 0.0, 0.0, false);
   }

   public double sample(double x, double y, double z, double g, double h, boolean bl) {
      double i = 0.0;
      double j = this.field_20660;
      double k = this.field_20659;

      for (int l = 0; l < this.octaveSamplers.length; l++) {
         PerlinNoiseSampler lv = this.octaveSamplers[l];
         if (lv != null) {
            i += this.field_26445.getDouble(l)
               * lv.sample(maintainPrecision(x * j), bl ? -lv.originY : maintainPrecision(y * j), maintainPrecision(z * j), g * j, h * j)
               * k;
         }

         j *= 2.0;
         k /= 2.0;
      }

      return i;
   }

   @Nullable
   public PerlinNoiseSampler getOctave(int octave) {
      return this.octaveSamplers[this.octaveSamplers.length - 1 - octave];
   }

   public static double maintainPrecision(double d) {
      return d - (double)MathHelper.lfloor(d / 3.3554432E7 + 0.5) * 3.3554432E7;
   }

   @Override
   public double sample(double x, double y, double f, double g) {
      return this.sample(x, y, 0.0, f, g, false);
   }
}
