import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;

public class cua {
   private final double a;
   private final cub b;
   private final cub c;

   public static cua a(chx var0, int var1, DoubleList var2) {
      return new cua(_snowman, _snowman, _snowman);
   }

   private cua(chx var1, int var2, DoubleList var3) {
      this.b = cub.a(_snowman, _snowman, _snowman);
      this.c = cub.a(_snowman, _snowman, _snowman);
      int _snowman = Integer.MAX_VALUE;
      int _snowmanx = Integer.MIN_VALUE;
      DoubleListIterator _snowmanxx = _snowman.iterator();

      while (_snowmanxx.hasNext()) {
         int _snowmanxxx = _snowmanxx.nextIndex();
         double _snowmanxxxx = _snowmanxx.nextDouble();
         if (_snowmanxxxx != 0.0) {
            _snowman = Math.min(_snowman, _snowmanxxx);
            _snowmanx = Math.max(_snowmanx, _snowmanxxx);
         }
      }

      this.a = 0.16666666666666666 / a(_snowmanx - _snowman);
   }

   private static double a(int var0) {
      return 0.1 * (1.0 + 1.0 / (double)(_snowman + 1));
   }

   public double a(double var1, double var3, double var5) {
      double _snowman = _snowman * 1.0181268882175227;
      double _snowmanx = _snowman * 1.0181268882175227;
      double _snowmanxx = _snowman * 1.0181268882175227;
      return (this.b.a(_snowman, _snowman, _snowman) + this.c.a(_snowman, _snowmanx, _snowmanxx)) * this.a;
   }
}
