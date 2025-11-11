package net.minecraft.util;

import javax.annotation.Nonnull;

public class CubicSampler {
   private static final double[] a = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

   @Nonnull
   public static dcn a(dcn var0, CubicSampler.Vec3Fetcher var1) {
      int _snowman = afm.c(_snowman.a());
      int _snowmanx = afm.c(_snowman.b());
      int _snowmanxx = afm.c(_snowman.c());
      double _snowmanxxx = _snowman.a() - (double)_snowman;
      double _snowmanxxxx = _snowman.b() - (double)_snowmanx;
      double _snowmanxxxxx = _snowman.c() - (double)_snowmanxx;
      double _snowmanxxxxxx = 0.0;
      dcn _snowmanxxxxxxx = dcn.a;

      for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 6; _snowmanxxxxxxxx++) {
         double _snowmanxxxxxxxxx = afm.d(_snowmanxxx, a[_snowmanxxxxxxxx + 1], a[_snowmanxxxxxxxx]);
         int _snowmanxxxxxxxxxx = _snowman - 2 + _snowmanxxxxxxxx;

         for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 6; _snowmanxxxxxxxxxxx++) {
            double _snowmanxxxxxxxxxxxx = afm.d(_snowmanxxxx, a[_snowmanxxxxxxxxxxx + 1], a[_snowmanxxxxxxxxxxx]);
            int _snowmanxxxxxxxxxxxxx = _snowmanx - 2 + _snowmanxxxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < 6; _snowmanxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxx = afm.d(_snowmanxxxxx, a[_snowmanxxxxxxxxxxxxxx + 1], a[_snowmanxxxxxxxxxxxxxx]);
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxx - 2 + _snowmanxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxx += _snowmanxxxxxxxxxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxx.e(_snowman.fetch(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx).a(_snowmanxxxxxxxxxxxxxxxxx));
            }
         }
      }

      return _snowmanxxxxxxx.a(1.0 / _snowmanxxxxxx);
   }

   public interface Vec3Fetcher {
      dcn fetch(int var1, int var2, int var3);
   }
}
