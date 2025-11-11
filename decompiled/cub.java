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

public class cub implements cue {
   private final ctz[] a;
   private final DoubleList b;
   private final double c;
   private final double d;

   public cub(chx var1, IntStream var2) {
      this(_snowman, _snowman.boxed().collect(ImmutableList.toImmutableList()));
   }

   public cub(chx var1, List<Integer> var2) {
      this(_snowman, new IntRBTreeSet(_snowman));
   }

   public static cub a(chx var0, int var1, DoubleList var2) {
      return new cub(_snowman, Pair.of(_snowman, _snowman));
   }

   private static Pair<Integer, DoubleList> a(IntSortedSet var0) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int _snowman = -_snowman.firstInt();
         int _snowmanx = _snowman.lastInt();
         int _snowmanxx = _snowman + _snowmanx + 1;
         if (_snowmanxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList _snowmanxxx = new DoubleArrayList(new double[_snowmanxx]);
            IntBidirectionalIterator _snowmanxxxx = _snowman.iterator();

            while (_snowmanxxxx.hasNext()) {
               int _snowmanxxxxx = _snowmanxxxx.nextInt();
               _snowmanxxx.set(_snowmanxxxxx + _snowman, 1.0);
            }

            return Pair.of(-_snowman, _snowmanxxx);
         }
      }
   }

   private cub(chx var1, IntSortedSet var2) {
      this(_snowman, a(_snowman));
   }

   private cub(chx var1, Pair<Integer, DoubleList> var2) {
      int _snowman = (Integer)_snowman.getFirst();
      this.b = (DoubleList)_snowman.getSecond();
      ctz _snowmanx = new ctz(_snowman);
      int _snowmanxx = this.b.size();
      int _snowmanxxx = -_snowman;
      this.a = new ctz[_snowmanxx];
      if (_snowmanxxx >= 0 && _snowmanxxx < _snowmanxx) {
         double _snowmanxxxx = this.b.getDouble(_snowmanxxx);
         if (_snowmanxxxx != 0.0) {
            this.a[_snowmanxxx] = _snowmanx;
         }
      }

      for (int _snowmanxxxx = _snowmanxxx - 1; _snowmanxxxx >= 0; _snowmanxxxx--) {
         if (_snowmanxxxx < _snowmanxx) {
            double _snowmanxxxxx = this.b.getDouble(_snowmanxxxx);
            if (_snowmanxxxxx != 0.0) {
               this.a[_snowmanxxxx] = new ctz(_snowman);
            } else {
               _snowman.a(262);
            }
         } else {
            _snowman.a(262);
         }
      }

      if (_snowmanxxx < _snowmanxx - 1) {
         long _snowmanxxxxx = (long)(_snowmanx.a(0.0, 0.0, 0.0, 0.0, 0.0) * 9.223372E18F);
         chx _snowmanxxxxxx = new chx(_snowmanxxxxx);

         for (int _snowmanxxxxxxx = _snowmanxxx + 1; _snowmanxxxxxxx < _snowmanxx; _snowmanxxxxxxx++) {
            if (_snowmanxxxxxxx >= 0) {
               double _snowmanxxxxxxxx = this.b.getDouble(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx != 0.0) {
                  this.a[_snowmanxxxxxxx] = new ctz(_snowmanxxxxxx);
               } else {
                  _snowmanxxxxxx.a(262);
               }
            } else {
               _snowmanxxxxxx.a(262);
            }
         }
      }

      this.d = Math.pow(2.0, (double)(-_snowmanxxx));
      this.c = Math.pow(2.0, (double)(_snowmanxx - 1)) / (Math.pow(2.0, (double)_snowmanxx) - 1.0);
   }

   public double a(double var1, double var3, double var5) {
      return this.a(_snowman, _snowman, _snowman, 0.0, 0.0, false);
   }

   public double a(double var1, double var3, double var5, double var7, double var9, boolean var11) {
      double _snowman = 0.0;
      double _snowmanx = this.d;
      double _snowmanxx = this.c;

      for (int _snowmanxxx = 0; _snowmanxxx < this.a.length; _snowmanxxx++) {
         ctz _snowmanxxxx = this.a[_snowmanxxx];
         if (_snowmanxxxx != null) {
            _snowman += this.b.getDouble(_snowmanxxx) * _snowmanxxxx.a(a(_snowman * _snowmanx), _snowman ? -_snowmanxxxx.b : a(_snowman * _snowmanx), a(_snowman * _snowmanx), _snowman * _snowmanx, _snowman * _snowmanx) * _snowmanxx;
         }

         _snowmanx *= 2.0;
         _snowmanxx /= 2.0;
      }

      return _snowman;
   }

   @Nullable
   public ctz a(int var1) {
      return this.a[this.a.length - 1 - _snowman];
   }

   public static double a(double var0) {
      return _snowman - (double)afm.d(_snowman / 3.3554432E7 + 0.5) * 3.3554432E7;
   }

   @Override
   public double a(double var1, double var3, double var5, double var7) {
      return this.a(_snowman, _snowman, 0.0, _snowman, _snowman, false);
   }
}
