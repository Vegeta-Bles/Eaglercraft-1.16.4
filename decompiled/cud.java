import java.util.Random;

public class cud {
   protected static final int[][] a = new int[][]{
      {1, 1, 0},
      {-1, 1, 0},
      {1, -1, 0},
      {-1, -1, 0},
      {1, 0, 1},
      {-1, 0, 1},
      {1, 0, -1},
      {-1, 0, -1},
      {0, 1, 1},
      {0, -1, 1},
      {0, 1, -1},
      {0, -1, -1},
      {1, 1, 0},
      {0, -1, 1},
      {-1, 1, 0},
      {0, -1, -1}
   };
   private static final double e = Math.sqrt(3.0);
   private static final double f = 0.5 * (e - 1.0);
   private static final double g = (3.0 - e) / 6.0;
   private final int[] h = new int[512];
   public final double b;
   public final double c;
   public final double d;

   public cud(Random var1) {
      this.b = _snowman.nextDouble() * 256.0;
      this.c = _snowman.nextDouble() * 256.0;
      this.d = _snowman.nextDouble() * 256.0;
      int _snowman = 0;

      while (_snowman < 256) {
         this.h[_snowman] = _snowman++;
      }

      for (int _snowmanx = 0; _snowmanx < 256; _snowmanx++) {
         int _snowmanxx = _snowman.nextInt(256 - _snowmanx);
         int _snowmanxxx = this.h[_snowmanx];
         this.h[_snowmanx] = this.h[_snowmanxx + _snowmanx];
         this.h[_snowmanxx + _snowmanx] = _snowmanxxx;
      }
   }

   private int a(int var1) {
      return this.h[_snowman & 0xFF];
   }

   protected static double a(int[] var0, double var1, double var3, double var5) {
      return (double)_snowman[0] * _snowman + (double)_snowman[1] * _snowman + (double)_snowman[2] * _snowman;
   }

   private double a(int var1, double var2, double var4, double var6, double var8) {
      double _snowman = _snowman - _snowman * _snowman - _snowman * _snowman - _snowman * _snowman;
      double _snowmanx;
      if (_snowman < 0.0) {
         _snowmanx = 0.0;
      } else {
         _snowman *= _snowman;
         _snowmanx = _snowman * _snowman * a(a[_snowman], _snowman, _snowman, _snowman);
      }

      return _snowmanx;
   }

   public double a(double var1, double var3) {
      double _snowman = (_snowman + _snowman) * f;
      int _snowmanx = afm.c(_snowman + _snowman);
      int _snowmanxx = afm.c(_snowman + _snowman);
      double _snowmanxxx = (double)(_snowmanx + _snowmanxx) * g;
      double _snowmanxxxx = (double)_snowmanx - _snowmanxxx;
      double _snowmanxxxxx = (double)_snowmanxx - _snowmanxxx;
      double _snowmanxxxxxx = _snowman - _snowmanxxxx;
      double _snowmanxxxxxxx = _snowman - _snowmanxxxxx;
      int _snowmanxxxxxxxx;
      int _snowmanxxxxxxxxx;
      if (_snowmanxxxxxx > _snowmanxxxxxxx) {
         _snowmanxxxxxxxx = 1;
         _snowmanxxxxxxxxx = 0;
      } else {
         _snowmanxxxxxxxx = 0;
         _snowmanxxxxxxxxx = 1;
      }

      double _snowmanxxxxxxxxxx = _snowmanxxxxxx - (double)_snowmanxxxxxxxx + g;
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxx - (double)_snowmanxxxxxxxxx + g;
      double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx - 1.0 + 2.0 * g;
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx - 1.0 + 2.0 * g;
      int _snowmanxxxxxxxxxxxxxx = _snowmanx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxx = _snowmanxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxx)) % 12;
      int _snowmanxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxx)) % 12;
      int _snowmanxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxx + 1 + this.a(_snowmanxxxxxxxxxxxxxxx + 1)) % 12;
      double _snowmanxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, 0.0, 0.5);
      double _snowmanxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 0.0, 0.5);
      double _snowmanxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0.0, 0.5);
      return 70.0 * (_snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx);
   }

   public double a(double var1, double var3, double var5) {
      double _snowman = 0.3333333333333333;
      double _snowmanx = (_snowman + _snowman + _snowman) * 0.3333333333333333;
      int _snowmanxx = afm.c(_snowman + _snowmanx);
      int _snowmanxxx = afm.c(_snowman + _snowmanx);
      int _snowmanxxxx = afm.c(_snowman + _snowmanx);
      double _snowmanxxxxx = 0.16666666666666666;
      double _snowmanxxxxxx = (double)(_snowmanxx + _snowmanxxx + _snowmanxxxx) * 0.16666666666666666;
      double _snowmanxxxxxxx = (double)_snowmanxx - _snowmanxxxxxx;
      double _snowmanxxxxxxxx = (double)_snowmanxxx - _snowmanxxxxxx;
      double _snowmanxxxxxxxxx = (double)_snowmanxxxx - _snowmanxxxxxx;
      double _snowmanxxxxxxxxxx = _snowman - _snowmanxxxxxxx;
      double _snowmanxxxxxxxxxxx = _snowman - _snowmanxxxxxxxx;
      double _snowmanxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxx >= _snowmanxxxxxxxxxxx) {
         if (_snowmanxxxxxxxxxxx >= _snowmanxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxx = 0;
         } else if (_snowmanxxxxxxxxxx >= _snowmanxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxx = 1;
         } else {
            _snowmanxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxx = 1;
         }
      } else if (_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxx) {
         _snowmanxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxx = 1;
      } else if (_snowmanxxxxxxxxxx < _snowmanxxxxxxxxxxxx) {
         _snowmanxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxx = 1;
      } else {
         _snowmanxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxx = 0;
      }

      double _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx - 1.0 + 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx - 1.0 + 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - 1.0 + 0.5;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx))
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxx
               + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx))
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxx
               + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx))
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1 + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1 + this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1))
         )
         % 12;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.6);
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      return 32.0
         * (
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
   }
}
