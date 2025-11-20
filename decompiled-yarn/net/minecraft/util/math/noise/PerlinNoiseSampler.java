package net.minecraft.util.math.noise;

import java.util.Random;
import net.minecraft.util.math.MathHelper;

public final class PerlinNoiseSampler {
   private final byte[] permutations;
   public final double originX;
   public final double originY;
   public final double originZ;

   public PerlinNoiseSampler(Random random) {
      this.originX = random.nextDouble() * 256.0;
      this.originY = random.nextDouble() * 256.0;
      this.originZ = random.nextDouble() * 256.0;
      this.permutations = new byte[256];

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         this.permutations[_snowman] = (byte)_snowman;
      }

      for (int _snowman = 0; _snowman < 256; _snowman++) {
         int _snowmanx = random.nextInt(256 - _snowman);
         byte _snowmanxx = this.permutations[_snowman];
         this.permutations[_snowman] = this.permutations[_snowman + _snowmanx];
         this.permutations[_snowman + _snowmanx] = _snowmanxx;
      }
   }

   public double sample(double x, double y, double z, double _snowman, double _snowman) {
      double _snowmanxx = x + this.originX;
      double _snowmanxxx = y + this.originY;
      double _snowmanxxxx = z + this.originZ;
      int _snowmanxxxxx = MathHelper.floor(_snowmanxx);
      int _snowmanxxxxxx = MathHelper.floor(_snowmanxxx);
      int _snowmanxxxxxxx = MathHelper.floor(_snowmanxxxx);
      double _snowmanxxxxxxxx = _snowmanxx - (double)_snowmanxxxxx;
      double _snowmanxxxxxxxxx = _snowmanxxx - (double)_snowmanxxxxxx;
      double _snowmanxxxxxxxxxx = _snowmanxxxx - (double)_snowmanxxxxxxx;
      double _snowmanxxxxxxxxxxx = MathHelper.perlinFade(_snowmanxxxxxxxx);
      double _snowmanxxxxxxxxxxxx = MathHelper.perlinFade(_snowmanxxxxxxxxx);
      double _snowmanxxxxxxxxxxxxx = MathHelper.perlinFade(_snowmanxxxxxxxxxx);
      double _snowmanxxxxxxxxxxxxxx;
      if (_snowman != 0.0) {
         double _snowmanxxxxxxxxxxxxxxx = Math.min(_snowman, _snowmanxxxxxxxxx);
         _snowmanxxxxxxxxxxxxxx = (double)MathHelper.floor(_snowmanxxxxxxxxxxxxxxx / _snowman) * _snowman;
      } else {
         _snowmanxxxxxxxxxxxxxx = 0.0;
      }

      return this.sample(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx - _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
   }

   private static double grad(int hash, double x, double y, double z) {
      int _snowman = hash & 15;
      return SimplexNoiseSampler.dot(SimplexNoiseSampler.gradients[_snowman], x, y, z);
   }

   private int getGradient(int hash) {
      return this.permutations[hash & 0xFF] & 0xFF;
   }

   public double sample(
      int sectionX, int sectionY, int sectionZ, double localX, double localY, double localZ, double fadeLocalX, double fadeLocalY, double fadeLocalZ
   ) {
      int _snowman = this.getGradient(sectionX) + sectionY;
      int _snowmanx = this.getGradient(_snowman) + sectionZ;
      int _snowmanxx = this.getGradient(_snowman + 1) + sectionZ;
      int _snowmanxxx = this.getGradient(sectionX + 1) + sectionY;
      int _snowmanxxxx = this.getGradient(_snowmanxxx) + sectionZ;
      int _snowmanxxxxx = this.getGradient(_snowmanxxx + 1) + sectionZ;
      double _snowmanxxxxxx = grad(this.getGradient(_snowmanx), localX, localY, localZ);
      double _snowmanxxxxxxx = grad(this.getGradient(_snowmanxxxx), localX - 1.0, localY, localZ);
      double _snowmanxxxxxxxx = grad(this.getGradient(_snowmanxx), localX, localY - 1.0, localZ);
      double _snowmanxxxxxxxxx = grad(this.getGradient(_snowmanxxxxx), localX - 1.0, localY - 1.0, localZ);
      double _snowmanxxxxxxxxxx = grad(this.getGradient(_snowmanx + 1), localX, localY, localZ - 1.0);
      double _snowmanxxxxxxxxxxx = grad(this.getGradient(_snowmanxxxx + 1), localX - 1.0, localY, localZ - 1.0);
      double _snowmanxxxxxxxxxxxx = grad(this.getGradient(_snowmanxx + 1), localX, localY - 1.0, localZ - 1.0);
      double _snowmanxxxxxxxxxxxxx = grad(this.getGradient(_snowmanxxxxx + 1), localX - 1.0, localY - 1.0, localZ - 1.0);
      return MathHelper.lerp3(
         fadeLocalX, fadeLocalY, fadeLocalZ, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx
      );
   }
}
