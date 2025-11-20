package net.minecraft.util.math;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.hit.BlockHitResult;

public class Box {
   public final double minX;
   public final double minY;
   public final double minZ;
   public final double maxX;
   public final double maxY;
   public final double maxZ;

   public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
      this.minX = Math.min(x1, x2);
      this.minY = Math.min(y1, y2);
      this.minZ = Math.min(z1, z2);
      this.maxX = Math.max(x1, x2);
      this.maxY = Math.max(y1, y2);
      this.maxZ = Math.max(z1, z2);
   }

   public Box(BlockPos pos) {
      this((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1));
   }

   public Box(BlockPos pos1, BlockPos pos2) {
      this((double)pos1.getX(), (double)pos1.getY(), (double)pos1.getZ(), (double)pos2.getX(), (double)pos2.getY(), (double)pos2.getZ());
   }

   public Box(Vec3d pos1, Vec3d pos2) {
      this(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z);
   }

   public static Box from(BlockBox mutable) {
      return new Box(
         (double)mutable.minX, (double)mutable.minY, (double)mutable.minZ, (double)(mutable.maxX + 1), (double)(mutable.maxY + 1), (double)(mutable.maxZ + 1)
      );
   }

   public static Box method_29968(Vec3d _snowman) {
      return new Box(_snowman.x, _snowman.y, _snowman.z, _snowman.x + 1.0, _snowman.y + 1.0, _snowman.z + 1.0);
   }

   public double getMin(Direction.Axis axis) {
      return axis.choose(this.minX, this.minY, this.minZ);
   }

   public double getMax(Direction.Axis axis) {
      return axis.choose(this.maxX, this.maxY, this.maxZ);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Box)) {
         return false;
      } else {
         Box _snowman = (Box)o;
         if (Double.compare(_snowman.minX, this.minX) != 0) {
            return false;
         } else if (Double.compare(_snowman.minY, this.minY) != 0) {
            return false;
         } else if (Double.compare(_snowman.minZ, this.minZ) != 0) {
            return false;
         } else if (Double.compare(_snowman.maxX, this.maxX) != 0) {
            return false;
         } else {
            return Double.compare(_snowman.maxY, this.maxY) != 0 ? false : Double.compare(_snowman.maxZ, this.maxZ) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long _snowman = Double.doubleToLongBits(this.minX);
      int _snowmanx = (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.minY);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.minZ);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.maxX);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.maxY);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.maxZ);
      return 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
   }

   public Box shrink(double x, double y, double z) {
      double _snowman = this.minX;
      double _snowmanx = this.minY;
      double _snowmanxx = this.minZ;
      double _snowmanxxx = this.maxX;
      double _snowmanxxxx = this.maxY;
      double _snowmanxxxxx = this.maxZ;
      if (x < 0.0) {
         _snowman -= x;
      } else if (x > 0.0) {
         _snowmanxxx -= x;
      }

      if (y < 0.0) {
         _snowmanx -= y;
      } else if (y > 0.0) {
         _snowmanxxxx -= y;
      }

      if (z < 0.0) {
         _snowmanxx -= z;
      } else if (z > 0.0) {
         _snowmanxxxxx -= z;
      }

      return new Box(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public Box stretch(Vec3d scale) {
      return this.stretch(scale.x, scale.y, scale.z);
   }

   public Box stretch(double x, double y, double z) {
      double _snowman = this.minX;
      double _snowmanx = this.minY;
      double _snowmanxx = this.minZ;
      double _snowmanxxx = this.maxX;
      double _snowmanxxxx = this.maxY;
      double _snowmanxxxxx = this.maxZ;
      if (x < 0.0) {
         _snowman += x;
      } else if (x > 0.0) {
         _snowmanxxx += x;
      }

      if (y < 0.0) {
         _snowmanx += y;
      } else if (y > 0.0) {
         _snowmanxxxx += y;
      }

      if (z < 0.0) {
         _snowmanxx += z;
      } else if (z > 0.0) {
         _snowmanxxxxx += z;
      }

      return new Box(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public Box expand(double x, double y, double z) {
      double _snowman = this.minX - x;
      double _snowmanx = this.minY - y;
      double _snowmanxx = this.minZ - z;
      double _snowmanxxx = this.maxX + x;
      double _snowmanxxxx = this.maxY + y;
      double _snowmanxxxxx = this.maxZ + z;
      return new Box(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public Box expand(double value) {
      return this.expand(value, value, value);
   }

   public Box intersection(Box box) {
      double _snowman = Math.max(this.minX, box.minX);
      double _snowmanx = Math.max(this.minY, box.minY);
      double _snowmanxx = Math.max(this.minZ, box.minZ);
      double _snowmanxxx = Math.min(this.maxX, box.maxX);
      double _snowmanxxxx = Math.min(this.maxY, box.maxY);
      double _snowmanxxxxx = Math.min(this.maxZ, box.maxZ);
      return new Box(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public Box union(Box box) {
      double _snowman = Math.min(this.minX, box.minX);
      double _snowmanx = Math.min(this.minY, box.minY);
      double _snowmanxx = Math.min(this.minZ, box.minZ);
      double _snowmanxxx = Math.max(this.maxX, box.maxX);
      double _snowmanxxxx = Math.max(this.maxY, box.maxY);
      double _snowmanxxxxx = Math.max(this.maxZ, box.maxZ);
      return new Box(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public Box offset(double x, double y, double z) {
      return new Box(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
   }

   public Box offset(BlockPos blockPos) {
      return new Box(
         this.minX + (double)blockPos.getX(),
         this.minY + (double)blockPos.getY(),
         this.minZ + (double)blockPos.getZ(),
         this.maxX + (double)blockPos.getX(),
         this.maxY + (double)blockPos.getY(),
         this.maxZ + (double)blockPos.getZ()
      );
   }

   public Box offset(Vec3d vec3d) {
      return this.offset(vec3d.x, vec3d.y, vec3d.z);
   }

   public boolean intersects(Box box) {
      return this.intersects(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
   }

   public boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
   }

   public boolean intersects(Vec3d from, Vec3d to) {
      return this.intersects(
         Math.min(from.x, to.x), Math.min(from.y, to.y), Math.min(from.z, to.z), Math.max(from.x, to.x), Math.max(from.y, to.y), Math.max(from.z, to.z)
      );
   }

   public boolean contains(Vec3d vec) {
      return this.contains(vec.x, vec.y, vec.z);
   }

   public boolean contains(double x, double y, double z) {
      return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY && z >= this.minZ && z < this.maxZ;
   }

   public double getAverageSideLength() {
      double _snowman = this.getXLength();
      double _snowmanx = this.getYLength();
      double _snowmanxx = this.getZLength();
      return (_snowman + _snowmanx + _snowmanxx) / 3.0;
   }

   public double getXLength() {
      return this.maxX - this.minX;
   }

   public double getYLength() {
      return this.maxY - this.minY;
   }

   public double getZLength() {
      return this.maxZ - this.minZ;
   }

   public Box contract(double value) {
      return this.expand(-value);
   }

   public Optional<Vec3d> raycast(Vec3d min, Vec3d max) {
      double[] _snowman = new double[]{1.0};
      double _snowmanx = max.x - min.x;
      double _snowmanxx = max.y - min.y;
      double _snowmanxxx = max.z - min.z;
      Direction _snowmanxxxx = traceCollisionSide(this, min, _snowman, null, _snowmanx, _snowmanxx, _snowmanxxx);
      if (_snowmanxxxx == null) {
         return Optional.empty();
      } else {
         double _snowmanxxxxx = _snowman[0];
         return Optional.of(min.add(_snowmanxxxxx * _snowmanx, _snowmanxxxxx * _snowmanxx, _snowmanxxxxx * _snowmanxxx));
      }
   }

   @Nullable
   public static BlockHitResult raycast(Iterable<Box> boxes, Vec3d from, Vec3d to, BlockPos pos) {
      double[] _snowman = new double[]{1.0};
      Direction _snowmanx = null;
      double _snowmanxx = to.x - from.x;
      double _snowmanxxx = to.y - from.y;
      double _snowmanxxxx = to.z - from.z;

      for (Box _snowmanxxxxx : boxes) {
         _snowmanx = traceCollisionSide(_snowmanxxxxx.offset(pos), from, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      if (_snowmanx == null) {
         return null;
      } else {
         double _snowmanxxxxx = _snowman[0];
         return new BlockHitResult(from.add(_snowmanxxxxx * _snowmanxx, _snowmanxxxxx * _snowmanxxx, _snowmanxxxxx * _snowmanxxxx), _snowmanx, pos, false);
      }
   }

   @Nullable
   private static Direction traceCollisionSide(
      Box box, Vec3d intersectingVector, double[] traceDistanceResult, @Nullable Direction approachDirection, double xDelta, double yDelta, double zDelta
   ) {
      if (xDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            xDelta,
            yDelta,
            zDelta,
            box.minX,
            box.minY,
            box.maxY,
            box.minZ,
            box.maxZ,
            Direction.WEST,
            intersectingVector.x,
            intersectingVector.y,
            intersectingVector.z
         );
      } else if (xDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            xDelta,
            yDelta,
            zDelta,
            box.maxX,
            box.minY,
            box.maxY,
            box.minZ,
            box.maxZ,
            Direction.EAST,
            intersectingVector.x,
            intersectingVector.y,
            intersectingVector.z
         );
      }

      if (yDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            yDelta,
            zDelta,
            xDelta,
            box.minY,
            box.minZ,
            box.maxZ,
            box.minX,
            box.maxX,
            Direction.DOWN,
            intersectingVector.y,
            intersectingVector.z,
            intersectingVector.x
         );
      } else if (yDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            yDelta,
            zDelta,
            xDelta,
            box.maxY,
            box.minZ,
            box.maxZ,
            box.minX,
            box.maxX,
            Direction.UP,
            intersectingVector.y,
            intersectingVector.z,
            intersectingVector.x
         );
      }

      if (zDelta > 1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            zDelta,
            xDelta,
            yDelta,
            box.minZ,
            box.minX,
            box.maxX,
            box.minY,
            box.maxY,
            Direction.NORTH,
            intersectingVector.z,
            intersectingVector.x,
            intersectingVector.y
         );
      } else if (zDelta < -1.0E-7) {
         approachDirection = traceCollisionSide(
            traceDistanceResult,
            approachDirection,
            zDelta,
            xDelta,
            yDelta,
            box.maxZ,
            box.minX,
            box.maxX,
            box.minY,
            box.maxY,
            Direction.SOUTH,
            intersectingVector.z,
            intersectingVector.x,
            intersectingVector.y
         );
      }

      return approachDirection;
   }

   @Nullable
   private static Direction traceCollisionSide(
      double[] traceDistanceResult,
      @Nullable Direction approachDirection,
      double xDelta,
      double yDelta,
      double zDelta,
      double begin,
      double minX,
      double maxX,
      double minZ,
      double maxZ,
      Direction resultDirection,
      double startX,
      double startY,
      double startZ
   ) {
      double _snowman = (begin - startX) / xDelta;
      double _snowmanx = startY + _snowman * yDelta;
      double _snowmanxx = startZ + _snowman * zDelta;
      if (0.0 < _snowman && _snowman < traceDistanceResult[0] && minX - 1.0E-7 < _snowmanx && _snowmanx < maxX + 1.0E-7 && minZ - 1.0E-7 < _snowmanxx && _snowmanxx < maxZ + 1.0E-7) {
         traceDistanceResult[0] = _snowman;
         return resultDirection;
      } else {
         return approachDirection;
      }
   }

   @Override
   public String toString() {
      return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
   }

   public boolean isValid() {
      return Double.isNaN(this.minX)
         || Double.isNaN(this.minY)
         || Double.isNaN(this.minZ)
         || Double.isNaN(this.maxX)
         || Double.isNaN(this.maxY)
         || Double.isNaN(this.maxZ);
   }

   public Vec3d getCenter() {
      return new Vec3d(MathHelper.lerp(0.5, this.minX, this.maxX), MathHelper.lerp(0.5, this.minY, this.maxY), MathHelper.lerp(0.5, this.minZ, this.maxZ));
   }

   public static Box method_30048(double _snowman, double _snowman, double _snowman) {
      return new Box(-_snowman / 2.0, -_snowman / 2.0, -_snowman / 2.0, _snowman / 2.0, _snowman / 2.0, _snowman / 2.0);
   }
}
