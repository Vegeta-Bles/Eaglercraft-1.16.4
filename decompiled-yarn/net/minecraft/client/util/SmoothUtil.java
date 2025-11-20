package net.minecraft.client.util;

import net.minecraft.util.math.MathHelper;

public class SmoothUtil {
   private double actualSum;
   private double smoothedSum;
   private double movementLatency;

   public SmoothUtil() {
   }

   public double smooth(double original, double smoother) {
      this.actualSum += original;
      double _snowman = this.actualSum - this.smoothedSum;
      double _snowmanx = MathHelper.lerp(0.5, this.movementLatency, _snowman);
      double _snowmanxx = Math.signum(_snowman);
      if (_snowmanxx * _snowman > _snowmanxx * this.movementLatency) {
         _snowman = _snowmanx;
      }

      this.movementLatency = _snowmanx;
      this.smoothedSum += _snowman * smoother;
      return _snowman * smoother;
   }

   public void clear() {
      this.actualSum = 0.0;
      this.smoothedSum = 0.0;
      this.movementLatency = 0.0;
   }
}
