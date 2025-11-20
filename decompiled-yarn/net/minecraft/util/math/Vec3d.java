package net.minecraft.util.math;

import java.util.EnumSet;
import net.minecraft.client.util.math.Vector3f;

public class Vec3d implements Position {
   public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
   public final double x;
   public final double y;
   public final double z;

   public static Vec3d unpackRgb(int rgb) {
      double _snowman = (double)(rgb >> 16 & 0xFF) / 255.0;
      double _snowmanx = (double)(rgb >> 8 & 0xFF) / 255.0;
      double _snowmanxx = (double)(rgb & 0xFF) / 255.0;
      return new Vec3d(_snowman, _snowmanx, _snowmanxx);
   }

   public static Vec3d ofCenter(Vec3i vec) {
      return new Vec3d((double)vec.getX() + 0.5, (double)vec.getY() + 0.5, (double)vec.getZ() + 0.5);
   }

   public static Vec3d of(Vec3i vec) {
      return new Vec3d((double)vec.getX(), (double)vec.getY(), (double)vec.getZ());
   }

   public static Vec3d ofBottomCenter(Vec3i vec) {
      return new Vec3d((double)vec.getX() + 0.5, (double)vec.getY(), (double)vec.getZ() + 0.5);
   }

   public static Vec3d ofCenter(Vec3i vec, double deltaY) {
      return new Vec3d((double)vec.getX() + 0.5, (double)vec.getY() + deltaY, (double)vec.getZ() + 0.5);
   }

   public Vec3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3d(Vector3f vec) {
      this((double)vec.getX(), (double)vec.getY(), (double)vec.getZ());
   }

   public Vec3d reverseSubtract(Vec3d vec) {
      return new Vec3d(vec.x - this.x, vec.y - this.y, vec.z - this.z);
   }

   public Vec3d normalize() {
      double _snowman = (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
      return _snowman < 1.0E-4 ? ZERO : new Vec3d(this.x / _snowman, this.y / _snowman, this.z / _snowman);
   }

   public double dotProduct(Vec3d vec) {
      return this.x * vec.x + this.y * vec.y + this.z * vec.z;
   }

   public Vec3d crossProduct(Vec3d vec) {
      return new Vec3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
   }

   public Vec3d subtract(Vec3d vec) {
      return this.subtract(vec.x, vec.y, vec.z);
   }

   public Vec3d subtract(double x, double y, double z) {
      return this.add(-x, -y, -z);
   }

   public Vec3d add(Vec3d vec) {
      return this.add(vec.x, vec.y, vec.z);
   }

   public Vec3d add(double x, double y, double z) {
      return new Vec3d(this.x + x, this.y + y, this.z + z);
   }

   public boolean isInRange(Position pos, double radius) {
      return this.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < radius * radius;
   }

   public double distanceTo(Vec3d vec) {
      double _snowman = vec.x - this.x;
      double _snowmanx = vec.y - this.y;
      double _snowmanxx = vec.z - this.z;
      return (double)MathHelper.sqrt(_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx);
   }

   public double squaredDistanceTo(Vec3d vec) {
      double _snowman = vec.x - this.x;
      double _snowmanx = vec.y - this.y;
      double _snowmanxx = vec.z - this.z;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public double squaredDistanceTo(double x, double y, double z) {
      double _snowman = x - this.x;
      double _snowmanx = y - this.y;
      double _snowmanxx = z - this.z;
      return _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
   }

   public Vec3d multiply(double mult) {
      return this.multiply(mult, mult, mult);
   }

   public Vec3d negate() {
      return this.multiply(-1.0);
   }

   public Vec3d multiply(Vec3d mult) {
      return this.multiply(mult.x, mult.y, mult.z);
   }

   public Vec3d multiply(double multX, double multY, double multZ) {
      return new Vec3d(this.x * multX, this.y * multY, this.z * multZ);
   }

   public double length() {
      return (double)MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Vec3d)) {
         return false;
      } else {
         Vec3d _snowman = (Vec3d)o;
         if (Double.compare(_snowman.x, this.x) != 0) {
            return false;
         } else {
            return Double.compare(_snowman.y, this.y) != 0 ? false : Double.compare(_snowman.z, this.z) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long _snowman = Double.doubleToLongBits(this.x);
      int _snowmanx = (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.y);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.z);
      return 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
   }

   @Override
   public String toString() {
      return "(" + this.x + ", " + this.y + ", " + this.z + ")";
   }

   public Vec3d rotateX(float angle) {
      float _snowman = MathHelper.cos(angle);
      float _snowmanx = MathHelper.sin(angle);
      double _snowmanxx = this.x;
      double _snowmanxxx = this.y * (double)_snowman + this.z * (double)_snowmanx;
      double _snowmanxxxx = this.z * (double)_snowman - this.y * (double)_snowmanx;
      return new Vec3d(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public Vec3d rotateY(float angle) {
      float _snowman = MathHelper.cos(angle);
      float _snowmanx = MathHelper.sin(angle);
      double _snowmanxx = this.x * (double)_snowman + this.z * (double)_snowmanx;
      double _snowmanxxx = this.y;
      double _snowmanxxxx = this.z * (double)_snowman - this.x * (double)_snowmanx;
      return new Vec3d(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public Vec3d rotateZ(float angle) {
      float _snowman = MathHelper.cos(angle);
      float _snowmanx = MathHelper.sin(angle);
      double _snowmanxx = this.x * (double)_snowman + this.y * (double)_snowmanx;
      double _snowmanxxx = this.y * (double)_snowman - this.x * (double)_snowmanx;
      double _snowmanxxxx = this.z;
      return new Vec3d(_snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public static Vec3d fromPolar(Vec2f polar) {
      return fromPolar(polar.x, polar.y);
   }

   public static Vec3d fromPolar(float pitch, float yaw) {
      float _snowman = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanx = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxx = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float _snowmanxxx = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3d((double)(_snowmanx * _snowmanxx), (double)_snowmanxxx, (double)(_snowman * _snowmanxx));
   }

   public Vec3d floorAlongAxes(EnumSet<Direction.Axis> axes) {
      double _snowman = axes.contains(Direction.Axis.X) ? (double)MathHelper.floor(this.x) : this.x;
      double _snowmanx = axes.contains(Direction.Axis.Y) ? (double)MathHelper.floor(this.y) : this.y;
      double _snowmanxx = axes.contains(Direction.Axis.Z) ? (double)MathHelper.floor(this.z) : this.z;
      return new Vec3d(_snowman, _snowmanx, _snowmanxx);
   }

   public double getComponentAlongAxis(Direction.Axis _snowman) {
      return _snowman.choose(this.x, this.y, this.z);
   }

   @Override
   public final double getX() {
      return this.x;
   }

   @Override
   public final double getY() {
      return this.y;
   }

   @Override
   public final double getZ() {
      return this.z;
   }
}
