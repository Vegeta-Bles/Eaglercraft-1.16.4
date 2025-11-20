package net.minecraft.util;

import javax.annotation.Nonnull;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class CubicSampler {
   private static final double[] DENSITY_CURVE = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   @Nonnull
   public static Vec3d sampleColor(Vec3d pos, CubicSampler.RgbFetcher rgbFetcher) {
      int _snowman = MathHelper.floor(pos.getX());
      int _snowmanx = MathHelper.floor(pos.getY());
      int _snowmanxx = MathHelper.floor(pos.getZ());
      double _snowmanxxx = pos.getX() - (double)_snowman;
      double _snowmanxxxx = pos.getY() - (double)_snowmanx;
      double _snowmanxxxxx = pos.getZ() - (double)_snowmanxx;
      double _snowmanxxxxxx = 0.0;
      Vec3d _snowmanxxxxxxx = Vec3d.ZERO;

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 6; _snowmanxxxxxxxx++) {
         double _snowmanxxxxxxxxx = MathHelper.lerp(_snowmanxxx, DENSITY_CURVE[_snowmanxxxxxxxx + 1], DENSITY_CURVE[_snowmanxxxxxxxx]);
         int _snowmanxxxxxxxxxx = _snowman - 2 + _snowmanxxxxxxxx;

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 6; _snowmanxxxxxxxxxxx++) {
            double _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxx, DENSITY_CURVE[_snowmanxxxxxxxxxxx + 1], DENSITY_CURVE[_snowmanxxxxxxxxxxx]);
            int _snowmanxxxxxxxxxxxxx = _snowmanx - 2 + _snowmanxxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 6; _snowmanxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxx, DENSITY_CURVE[_snowmanxxxxxxxxxxxxxx + 1], DENSITY_CURVE[_snowmanxxxxxxxxxxxxxx]);
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxx - 2 + _snowmanxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxx.add(rgbFetcher.fetch(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx).multiply(_snowmanxxxxxxxxxxxxxxxxx));
            }
         }
      }

      return _snowmanxxxxxxx.multiply(1.0 / _snowmanxxxxxx);
   }

   public interface RgbFetcher {
      Vec3d fetch(int x, int y, int z);
   }
}
