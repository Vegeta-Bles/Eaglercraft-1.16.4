package net.minecraft.util.math;

import com.google.common.collect.Lists;
import java.util.List;

public class GravityField {
   private final List<GravityField.Point> points = Lists.newArrayList();

   public GravityField() {
   }

   public void addPoint(BlockPos pos, double mass) {
      if (mass != 0.0) {
         this.points.add(new GravityField.Point(pos, mass));
      }
   }

   public double calculate(BlockPos pos, double mass) {
      if (mass == 0.0) {
         return 0.0;
      } else {
         double _snowman = 0.0;

         for (GravityField.Point _snowmanx : this.points) {
            _snowman += _snowmanx.getGravityFactor(pos);
         }

         return _snowman * mass;
      }
   }

   static class Point {
      private final BlockPos pos;
      private final double mass;

      public Point(BlockPos pos, double mass) {
         this.pos = pos;
         this.mass = mass;
      }

      public double getGravityFactor(BlockPos pos) {
         double _snowman = this.pos.getSquaredDistance(pos);
         return _snowman == 0.0 ? Double.POSITIVE_INFINITY : this.mass / Math.sqrt(_snowman);
      }
   }
}
