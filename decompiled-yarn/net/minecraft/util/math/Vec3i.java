package net.minecraft.util.math;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.util.Util;

@Immutable
public class Vec3i implements Comparable<Vec3i> {
   public static final Codec<Vec3i> CODEC = Codec.INT_STREAM
      .comapFlatMap(_snowman -> Util.toIntArray(_snowman, 3).map(_snowmanx -> new Vec3i(_snowmanx[0], _snowmanx[1], _snowmanx[2])), _snowman -> IntStream.of(_snowman.getX(), _snowman.getY(), _snowman.getZ()));
   public static final Vec3i ZERO = new Vec3i(0, 0, 0);
   private int x;
   private int y;
   private int z;

   public Vec3i(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3i(double x, double y, double z) {
      this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof Vec3i)) {
         return false;
      } else {
         Vec3i _snowmanx = (Vec3i)_snowman;
         if (this.getX() != _snowmanx.getX()) {
            return false;
         } else {
            return this.getY() != _snowmanx.getY() ? false : this.getZ() == _snowmanx.getZ();
         }
      }
   }

   @Override
   public int hashCode() {
      return (this.getY() + this.getZ() * 31) * 31 + this.getX();
   }

   public int compareTo(Vec3i _snowman) {
      if (this.getY() == _snowman.getY()) {
         return this.getZ() == _snowman.getZ() ? this.getX() - _snowman.getX() : this.getZ() - _snowman.getZ();
      } else {
         return this.getY() - _snowman.getY();
      }
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   protected void setX(int x) {
      this.x = x;
   }

   protected void setY(int y) {
      this.y = y;
   }

   protected void setZ(int z) {
      this.z = z;
   }

   public Vec3i up() {
      return this.up(1);
   }

   public Vec3i up(int distance) {
      return this.offset(Direction.UP, distance);
   }

   public Vec3i down() {
      return this.down(1);
   }

   public Vec3i down(int distance) {
      return this.offset(Direction.DOWN, distance);
   }

   public Vec3i offset(Direction direction, int distance) {
      return distance == 0
         ? this
         : new Vec3i(
            this.getX() + direction.getOffsetX() * distance, this.getY() + direction.getOffsetY() * distance, this.getZ() + direction.getOffsetZ() * distance
         );
   }

   public Vec3i crossProduct(Vec3i vec) {
      return new Vec3i(
         this.getY() * vec.getZ() - this.getZ() * vec.getY(),
         this.getZ() * vec.getX() - this.getX() * vec.getZ(),
         this.getX() * vec.getY() - this.getY() * vec.getX()
      );
   }

   public boolean isWithinDistance(Vec3i vec, double distance) {
      return this.getSquaredDistance((double)vec.getX(), (double)vec.getY(), (double)vec.getZ(), false) < distance * distance;
   }

   public boolean isWithinDistance(Position pos, double distance) {
      return this.getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), true) < distance * distance;
   }

   public double getSquaredDistance(Vec3i vec) {
      return this.getSquaredDistance((double)vec.getX(), (double)vec.getY(), (double)vec.getZ(), true);
   }

   public double getSquaredDistance(Position pos, boolean treatAsBlockPos) {
      return this.getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), treatAsBlockPos);
   }

   public double getSquaredDistance(double x, double y, double z, boolean treatAsBlockPos) {
      double _snowman = treatAsBlockPos ? 0.5 : 0.0;
      double _snowmanx = (double)this.getX() + _snowman - x;
      double _snowmanxx = (double)this.getY() + _snowman - y;
      double _snowmanxxx = (double)this.getZ() + _snowman - z;
      return _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
   }

   public int getManhattanDistance(Vec3i vec) {
      float _snowman = (float)Math.abs(vec.getX() - this.getX());
      float _snowmanx = (float)Math.abs(vec.getY() - this.getY());
      float _snowmanxx = (float)Math.abs(vec.getZ() - this.getZ());
      return (int)(_snowman + _snowmanx + _snowmanxx);
   }

   public int getComponentAlongAxis(Direction.Axis _snowman) {
      return _snowman.choose(this.x, this.y, this.z);
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
   }

   public String toShortString() {
      return "" + this.getX() + ", " + this.getY() + ", " + this.getZ();
   }
}
