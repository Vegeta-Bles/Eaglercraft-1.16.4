import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;

public final class dcp extends ddh {
   private final DoubleList b;
   private final DoubleList c;
   private final DoubleList d;

   protected dcp(dcw var1, double[] var2, double[] var3, double[] var4) {
      this(
         _snowman,
         DoubleArrayList.wrap(Arrays.copyOf(_snowman, _snowman.b() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(_snowman, _snowman.c() + 1)),
         DoubleArrayList.wrap(Arrays.copyOf(_snowman, _snowman.d() + 1))
      );
   }

   dcp(dcw var1, DoubleList var2, DoubleList var3, DoubleList var4) {
      super(_snowman);
      int _snowman = _snowman.b() + 1;
      int _snowmanx = _snowman.c() + 1;
      int _snowmanxx = _snowman.d() + 1;
      if (_snowman == _snowman.size() && _snowmanx == _snowman.size() && _snowmanxx == _snowman.size()) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      } else {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
      }
   }

   @Override
   protected DoubleList a(gc.a var1) {
      switch (_snowman) {
         case a:
            return this.b;
         case b:
            return this.c;
         case c:
            return this.d;
         default:
            throw new IllegalArgumentException();
      }
   }
}
