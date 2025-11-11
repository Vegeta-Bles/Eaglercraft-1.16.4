import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;

public class cuc implements cue {
   private final cud[] a;
   private final double b;
   private final double c;

   public cuc(chx var1, IntStream var2) {
      this(_snowman, _snowman.boxed().collect(ImmutableList.toImmutableList()));
   }

   public cuc(chx var1, List<Integer> var2) {
      this(_snowman, new IntRBTreeSet(_snowman));
   }

   private cuc(chx var1, IntSortedSet var2) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int _snowman = -_snowman.firstInt();
         int _snowmanx = _snowman.lastInt();
         int _snowmanxx = _snowman + _snowmanx + 1;
         if (_snowmanxx < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            cud _snowmanxxx = new cud(_snowman);
            int _snowmanxxxx = _snowmanx;
            this.a = new cud[_snowmanxx];
            if (_snowmanx >= 0 && _snowmanx < _snowmanxx && _snowman.contains(0)) {
               this.a[_snowmanx] = _snowmanxxx;
            }

            for (int _snowmanxxxxx = _snowmanx + 1; _snowmanxxxxx < _snowmanxx; _snowmanxxxxx++) {
               if (_snowmanxxxxx >= 0 && _snowman.contains(_snowmanxxxx - _snowmanxxxxx)) {
                  this.a[_snowmanxxxxx] = new cud(_snowman);
               } else {
                  _snowman.a(262);
               }
            }

            if (_snowmanx > 0) {
               long _snowmanxxxxxx = (long)(_snowmanxxx.a(_snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d) * 9.223372E18F);
               chx _snowmanxxxxxxx = new chx(_snowmanxxxxxx);

               for (int _snowmanxxxxxxxx = _snowmanxxxx - 1; _snowmanxxxxxxxx >= 0; _snowmanxxxxxxxx--) {
                  if (_snowmanxxxxxxxx < _snowmanxx && _snowman.contains(_snowmanxxxx - _snowmanxxxxxxxx)) {
                     this.a[_snowmanxxxxxxxx] = new cud(_snowmanxxxxxxx);
                  } else {
                     _snowmanxxxxxxx.a(262);
                  }
               }
            }

            this.c = Math.pow(2.0, (double)_snowmanx);
            this.b = 1.0 / (Math.pow(2.0, (double)_snowmanxx) - 1.0);
         }
      }
   }

   public double a(double var1, double var3, boolean var5) {
      double _snowman = 0.0;
      double _snowmanx = this.c;
      double _snowmanxx = this.b;

      for (cud _snowmanxxx : this.a) {
         if (_snowmanxxx != null) {
            _snowman += _snowmanxxx.a(_snowman * _snowmanx + (_snowman ? _snowmanxxx.b : 0.0), _snowman * _snowmanx + (_snowman ? _snowmanxxx.c : 0.0)) * _snowmanxx;
         }

         _snowmanx /= 2.0;
         _snowmanxx *= 2.0;
      }

      return _snowman;
   }

   @Override
   public double a(double var1, double var3, double var5, double var7) {
      return this.a(_snowman, _snowman, true) * 0.55;
   }
}
