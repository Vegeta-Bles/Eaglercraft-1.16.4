package net.minecraft.util.math.noise;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public class SimplexNoiseSampler {
   protected static final int[][] gradients = new int[][]{
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
   private static final double SQRT_3 = Math.sqrt(3.0);
   private static final double SKEW_FACTOR_2D = 0.5 * (SQRT_3 - 1.0);
   private static final double UNSKEW_FACTOR_2D = (3.0 - SQRT_3) / 6.0;
   private final int[] permutations = new int[512];
   public final double originX;
   public final double originY;
   public final double originZ;

   public SimplexNoiseSampler(Random random) {
      this.originX = random.nextDouble() * 256.0;
      this.originY = random.nextDouble() * 256.0;
      this.originZ = random.nextDouble() * 256.0;
      int _snowman = 0;

      while (_snowman < 256) {
         this.permutations[_snowman] = _snowman++;
      }

      for (int _snowmanx = 0; _snowmanx < 256; _snowmanx++) {
         int _snowmanxx = random.nextInt(256 - _snowmanx);
         int _snowmanxxx = this.permutations[_snowmanx];
         this.permutations[_snowmanx] = this.permutations[_snowmanxx + _snowmanx];
         this.permutations[_snowmanxx + _snowmanx] = _snowmanxxx;
      }
   }

   private int getGradient(int hash) {
      return this.permutations[hash & 0xFF];
   }

   protected static double dot(int[] gArr, double x, double y, double z) {
      return (double)gArr[0] * x + (double)gArr[1] * y + (double)gArr[2] * z;
   }

   private double grad(int hash, double x, double y, double z, double _snowman) {
      double _snowmanx = _snowman - x * x - y * y - z * z;
      double _snowmanxx;
      if (_snowmanx < 0.0) {
         _snowmanxx = 0.0;
      } else {
         _snowmanx *= _snowmanx;
         _snowmanxx = _snowmanx * _snowmanx * dot(gradients[hash], x, y, z);
      }

      return _snowmanxx;
   }

   public double sample(double x, double y) {
      double _snowman = (x + y) * SKEW_FACTOR_2D;
      int _snowmanx = MathHelper.floor(x + _snowman);
      int _snowmanxx = MathHelper.floor(y + _snowman);
      double _snowmanxxx = (double)(_snowmanx + _snowmanxx) * UNSKEW_FACTOR_2D;
      double _snowmanxxxx = (double)_snowmanx - _snowmanxxx;
      double _snowmanxxxxx = (double)_snowmanxx - _snowmanxxx;
      double _snowmanxxxxxx = x - _snowmanxxxx;
      double _snowmanxxxxxxx = y - _snowmanxxxxx;
      int _snowmanxxxxxxxx;
      int _snowmanxxxxxxxxx;
      if (_snowmanxxxxxx > _snowmanxxxxxxx) {
         _snowmanxxxxxxxx = 1;
         _snowmanxxxxxxxxx = 0;
      } else {
         _snowmanxxxxxxxx = 0;
         _snowmanxxxxxxxxx = 1;
      }

      double _snowmanxxxxxxxxxx = _snowmanxxxxxx - (double)_snowmanxxxxxxxx + UNSKEW_FACTOR_2D;
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxx - (double)_snowmanxxxxxxxxx + UNSKEW_FACTOR_2D;
      double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx - 1.0 + 2.0 * UNSKEW_FACTOR_2D;
      int _snowmanxxxxxxxxxxxxxx = _snowmanx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxx = _snowmanxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxx = this.getGradient(_snowmanxxxxxxxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxx)) % 12;
      int _snowmanxxxxxxxxxxxxxxxxx = this.getGradient(_snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxx + _snowmanxxxxxxxxx)) % 12;
      int _snowmanxxxxxxxxxxxxxxxxxx = this.getGradient(_snowmanxxxxxxxxxxxxxx + 1 + this.getGradient(_snowmanxxxxxxxxxxxxxxx + 1)) % 12;
      double _snowmanxxxxxxxxxxxxxxxxxxx = this.grad(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, 0.0, 0.5);
      double _snowmanxxxxxxxxxxxxxxxxxxxx = this.grad(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 0.0, 0.5);
      double _snowmanxxxxxxxxxxxxxxxxxxxxx = this.grad(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 0.0, 0.5);
      return 70.0 * (_snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx);
   }

   public double method_22416(double _snowman, double _snowman, double _snowman) {
      double _snowmanxxx = 0.3333333333333333;
      double _snowmanxxxx = (_snowman + _snowman + _snowman) * 0.3333333333333333;
      int _snowmanxxxxx = MathHelper.floor(_snowman + _snowmanxxxx);
      int _snowmanxxxxxx = MathHelper.floor(_snowman + _snowmanxxxx);
      int _snowmanxxxxxxx = MathHelper.floor(_snowman + _snowmanxxxx);
      double _snowmanxxxxxxxx = 0.16666666666666666;
      double _snowmanxxxxxxxxx = (double)(_snowmanxxxxx + _snowmanxxxxxx + _snowmanxxxxxxx) * 0.16666666666666666;
      double _snowmanxxxxxxxxxx = (double)_snowmanxxxxx - _snowmanxxxxxxxxx;
      double _snowmanxxxxxxxxxxx = (double)_snowmanxxxxxx - _snowmanxxxxxxxxx;
      double _snowmanxxxxxxxxxxxx = (double)_snowmanxxxxxxx - _snowmanxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxxx;
      double _snowmanxxxxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxxx;
      int _snowmanxxxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxx) {
         if (_snowmanxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = 0;
         } else if (_snowmanxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = 1;
         } else {
            _snowmanxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxxx = 1;
            _snowmanxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxx = 1;
         }
      } else if (_snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx) {
         _snowmanxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxxxxx = 1;
      } else if (_snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxx) {
         _snowmanxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxxxxx = 1;
      } else {
         _snowmanxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxx = 0;
         _snowmanxxxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
         _snowmanxxxxxxxxxxxxxxxxxxxxx = 0;
      }

      double _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxxx + 0.16666666666666666;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx - (double)_snowmanxxxxxxxxxxxxxxxxxxxxx + 0.3333333333333333;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - 1.0 + 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx - 1.0 + 0.5;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx - 1.0 + 0.5;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx & 0xFF;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getGradient(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx))
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getGradient(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxx
               + this.getGradient(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx)
               )
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getGradient(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxx
               + this.getGradient(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx)
               )
         )
         % 12;
      int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getGradient(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + 1
               + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1 + this.getGradient(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1))
         )
         % 12;
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.grad(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.6);
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.grad(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.grad(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.grad(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0.6
      );
      return 32.0
         * (
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
         );
   }
}
