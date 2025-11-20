package net.minecraft.util.shape;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class VoxelShape {
   protected final VoxelSet voxels;
   @Nullable
   private VoxelShape[] shapeCache;

   VoxelShape(VoxelSet voxels) {
      this.voxels = voxels;
   }

   public double getMin(Direction.Axis axis) {
      int _snowman = this.voxels.getMin(axis);
      return _snowman >= this.voxels.getSize(axis) ? Double.POSITIVE_INFINITY : this.getPointPosition(axis, _snowman);
   }

   public double getMax(Direction.Axis axis) {
      int _snowman = this.voxels.getMax(axis);
      return _snowman <= 0 ? Double.NEGATIVE_INFINITY : this.getPointPosition(axis, _snowman);
   }

   public Box getBoundingBox() {
      if (this.isEmpty()) {
         throw (UnsupportedOperationException)Util.throwOrPause(new UnsupportedOperationException("No bounds for empty shape."));
      } else {
         return new Box(
            this.getMin(Direction.Axis.X),
            this.getMin(Direction.Axis.Y),
            this.getMin(Direction.Axis.Z),
            this.getMax(Direction.Axis.X),
            this.getMax(Direction.Axis.Y),
            this.getMax(Direction.Axis.Z)
         );
      }
   }

   protected double getPointPosition(Direction.Axis axis, int index) {
      return this.getPointPositions(axis).getDouble(index);
   }

   protected abstract DoubleList getPointPositions(Direction.Axis axis);

   public boolean isEmpty() {
      return this.voxels.isEmpty();
   }

   public VoxelShape offset(double x, double y, double z) {
      return (VoxelShape)(this.isEmpty()
         ? VoxelShapes.empty()
         : new ArrayVoxelShape(
            this.voxels,
            new OffsetDoubleList(this.getPointPositions(Direction.Axis.X), x),
            new OffsetDoubleList(this.getPointPositions(Direction.Axis.Y), y),
            new OffsetDoubleList(this.getPointPositions(Direction.Axis.Z), z)
         ));
   }

   public VoxelShape simplify() {
      VoxelShape[] _snowman = new VoxelShape[]{VoxelShapes.empty()};
      this.forEachBox(
         (_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> _snowman[0] = VoxelShapes.combine(
               _snowman[0], VoxelShapes.cuboid(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx), BooleanBiFunction.OR
            )
      );
      return _snowman[0];
   }

   public void forEachEdge(VoxelShapes.BoxConsumer _snowman) {
      this.voxels
         .forEachEdge(
            (_snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxx, _snowmanxx, _snowmanx) -> _snowman.consume(
                  this.getPointPosition(Direction.Axis.X, _snowmanxxxxxx),
                  this.getPointPosition(Direction.Axis.Y, _snowmanxxxxx),
                  this.getPointPosition(Direction.Axis.Z, _snowmanxxxx),
                  this.getPointPosition(Direction.Axis.X, _snowmanxxx),
                  this.getPointPosition(Direction.Axis.Y, _snowmanxx),
                  this.getPointPosition(Direction.Axis.Z, _snowmanx)
               ),
            true
         );
   }

   public void forEachBox(VoxelShapes.BoxConsumer _snowman) {
      DoubleList _snowmanx = this.getPointPositions(Direction.Axis.X);
      DoubleList _snowmanxx = this.getPointPositions(Direction.Axis.Y);
      DoubleList _snowmanxxx = this.getPointPositions(Direction.Axis.Z);
      this.voxels
         .forEachBox(
            (_snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxx, _snowmanxxxxx) -> _snowman.consume(
                  _snowman.getDouble(_snowmanxxxxxxx), _snowman.getDouble(_snowmanxxxxxx), _snowman.getDouble(_snowmanxxxxx), _snowman.getDouble(_snowmanxxxx), _snowman.getDouble(_snowmanxxxx), _snowman.getDouble(_snowmanxxxxx)
               ),
            true
         );
   }

   public List<Box> getBoundingBoxes() {
      List<Box> _snowman = Lists.newArrayList();
      this.forEachBox((_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) -> _snowman.add(new Box(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx)));
      return _snowman;
   }

   public double getEndingCoord(Direction.Axis _snowman, double from, double to) {
      Direction.Axis _snowmanx = AxisCycleDirection.FORWARD.cycle(_snowman);
      Direction.Axis _snowmanxx = AxisCycleDirection.BACKWARD.cycle(_snowman);
      int _snowmanxxx = this.getCoordIndex(_snowmanx, from);
      int _snowmanxxxx = this.getCoordIndex(_snowmanxx, to);
      int _snowmanxxxxx = this.voxels.getEndingAxisCoord(_snowman, _snowmanxxx, _snowmanxxxx);
      return _snowmanxxxxx <= 0 ? Double.NEGATIVE_INFINITY : this.getPointPosition(_snowman, _snowmanxxxxx);
   }

   protected int getCoordIndex(Direction.Axis _snowman, double coord) {
      return MathHelper.binarySearch(0, this.voxels.getSize(_snowman) + 1, _snowmanx -> {
         if (_snowmanx < 0) {
            return false;
         } else {
            return _snowmanx > this.voxels.getSize(_snowman) ? true : coord < this.getPointPosition(_snowman, _snowmanx);
         }
      }) - 1;
   }

   protected boolean contains(double x, double y, double z) {
      return this.voxels
         .inBoundsAndContains(this.getCoordIndex(Direction.Axis.X, x), this.getCoordIndex(Direction.Axis.Y, y), this.getCoordIndex(Direction.Axis.Z, z));
   }

   @Nullable
   public BlockHitResult raycast(Vec3d start, Vec3d end, BlockPos pos) {
      if (this.isEmpty()) {
         return null;
      } else {
         Vec3d _snowman = end.subtract(start);
         if (_snowman.lengthSquared() < 1.0E-7) {
            return null;
         } else {
            Vec3d _snowmanx = start.add(_snowman.multiply(0.001));
            return this.contains(_snowmanx.x - (double)pos.getX(), _snowmanx.y - (double)pos.getY(), _snowmanx.z - (double)pos.getZ())
               ? new BlockHitResult(_snowmanx, Direction.getFacing(_snowman.x, _snowman.y, _snowman.z).getOpposite(), pos, true)
               : Box.raycast(this.getBoundingBoxes(), start, end, pos);
         }
      }
   }

   public VoxelShape getFace(Direction facing) {
      if (!this.isEmpty() && this != VoxelShapes.fullCube()) {
         if (this.shapeCache != null) {
            VoxelShape _snowman = this.shapeCache[facing.ordinal()];
            if (_snowman != null) {
               return _snowman;
            }
         } else {
            this.shapeCache = new VoxelShape[6];
         }

         VoxelShape _snowman = this.getUncachedFace(facing);
         this.shapeCache[facing.ordinal()] = _snowman;
         return _snowman;
      } else {
         return this;
      }
   }

   private VoxelShape getUncachedFace(Direction facing) {
      Direction.Axis _snowman = facing.getAxis();
      Direction.AxisDirection _snowmanx = facing.getDirection();
      DoubleList _snowmanxx = this.getPointPositions(_snowman);
      if (_snowmanxx.size() == 2 && DoubleMath.fuzzyEquals(_snowmanxx.getDouble(0), 0.0, 1.0E-7) && DoubleMath.fuzzyEquals(_snowmanxx.getDouble(1), 1.0, 1.0E-7)) {
         return this;
      } else {
         int _snowmanxxx = this.getCoordIndex(_snowman, _snowmanx == Direction.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
         return new SlicedVoxelShape(this, _snowman, _snowmanxxx);
      }
   }

   public double calculateMaxDistance(Direction.Axis axis, Box box, double maxDist) {
      return this.calculateMaxDistance(AxisCycleDirection.between(axis, Direction.Axis.X), box, maxDist);
   }

   protected double calculateMaxDistance(AxisCycleDirection axisCycle, Box box, double maxDist) {
      if (this.isEmpty()) {
         return maxDist;
      } else if (Math.abs(maxDist) < 1.0E-7) {
         return 0.0;
      } else {
         AxisCycleDirection _snowman = axisCycle.opposite();
         Direction.Axis _snowmanx = _snowman.cycle(Direction.Axis.X);
         Direction.Axis _snowmanxx = _snowman.cycle(Direction.Axis.Y);
         Direction.Axis _snowmanxxx = _snowman.cycle(Direction.Axis.Z);
         double _snowmanxxxx = box.getMax(_snowmanx);
         double _snowmanxxxxx = box.getMin(_snowmanx);
         int _snowmanxxxxxx = this.getCoordIndex(_snowmanx, _snowmanxxxxx + 1.0E-7);
         int _snowmanxxxxxxx = this.getCoordIndex(_snowmanx, _snowmanxxxx - 1.0E-7);
         int _snowmanxxxxxxxx = Math.max(0, this.getCoordIndex(_snowmanxx, box.getMin(_snowmanxx) + 1.0E-7));
         int _snowmanxxxxxxxxx = Math.min(this.voxels.getSize(_snowmanxx), this.getCoordIndex(_snowmanxx, box.getMax(_snowmanxx) - 1.0E-7) + 1);
         int _snowmanxxxxxxxxxx = Math.max(0, this.getCoordIndex(_snowmanxxx, box.getMin(_snowmanxxx) + 1.0E-7));
         int _snowmanxxxxxxxxxxx = Math.min(this.voxels.getSize(_snowmanxxx), this.getCoordIndex(_snowmanxxx, box.getMax(_snowmanxxx) - 1.0E-7) + 1);
         int _snowmanxxxxxxxxxxxx = this.voxels.getSize(_snowmanx);
         if (maxDist > 0.0) {
            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx + 1; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                     if (this.voxels.inBoundsAndContains(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)) {
                        double _snowmanxxxxxxxxxxxxxxxx = this.getPointPosition(_snowmanx, _snowmanxxxxxxxxxxxxx) - _snowmanxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxx >= -1.0E-7) {
                           maxDist = Math.min(maxDist, _snowmanxxxxxxxxxxxxxxxx);
                        }

                        return maxDist;
                     }
                  }
               }
            }
         } else if (maxDist < 0.0) {
            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx - 1; _snowmanxxxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxxxx--) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
                     if (this.voxels.inBoundsAndContains(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)) {
                        double _snowmanxxxxxxxxxxxxxxxxx = this.getPointPosition(_snowmanx, _snowmanxxxxxxxxxxxxx + 1) - _snowmanxxxxx;
                        if (_snowmanxxxxxxxxxxxxxxxxx <= 1.0E-7) {
                           maxDist = Math.max(maxDist, _snowmanxxxxxxxxxxxxxxxxx);
                        }

                        return maxDist;
                     }
                  }
               }
            }
         }

         return maxDist;
      }
   }

   @Override
   public String toString() {
      return this.isEmpty() ? "EMPTY" : "VoxelShape[" + this.getBoundingBox() + "]";
   }
}
