package net.minecraft.util.shape;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldView;

public final class VoxelShapes {
   private static final VoxelShape FULL_CUBE = Util.make(() -> {
      VoxelSet _snowman = new BitSetVoxelSet(1, 1, 1);
      _snowman.set(0, 0, 0, true, true);
      return new SimpleVoxelShape(_snowman);
   });
   public static final VoxelShape UNBOUNDED = cuboid(
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.NEGATIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY,
      Double.POSITIVE_INFINITY
   );
   private static final VoxelShape EMPTY = new ArrayVoxelShape(
      new BitSetVoxelSet(0, 0, 0), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0}), new DoubleArrayList(new double[]{0.0})
   );

   public static VoxelShape empty() {
      return EMPTY;
   }

   public static VoxelShape fullCube() {
      return FULL_CUBE;
   }

   public static VoxelShape cuboid(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
      return cuboid(new Box(xMin, yMin, zMin, xMax, yMax, zMax));
   }

   public static VoxelShape cuboid(Box box) {
      int _snowman = findRequiredBitResolution(box.minX, box.maxX);
      int _snowmanx = findRequiredBitResolution(box.minY, box.maxY);
      int _snowmanxx = findRequiredBitResolution(box.minZ, box.maxZ);
      if (_snowman >= 0 && _snowmanx >= 0 && _snowmanxx >= 0) {
         if (_snowman == 0 && _snowmanx == 0 && _snowmanxx == 0) {
            return box.contains(0.5, 0.5, 0.5) ? fullCube() : empty();
         } else {
            int _snowmanxxx = 1 << _snowman;
            int _snowmanxxxx = 1 << _snowmanx;
            int _snowmanxxxxx = 1 << _snowmanxx;
            int _snowmanxxxxxx = (int)Math.round(box.minX * (double)_snowmanxxx);
            int _snowmanxxxxxxx = (int)Math.round(box.maxX * (double)_snowmanxxx);
            int _snowmanxxxxxxxx = (int)Math.round(box.minY * (double)_snowmanxxxx);
            int _snowmanxxxxxxxxx = (int)Math.round(box.maxY * (double)_snowmanxxxx);
            int _snowmanxxxxxxxxxx = (int)Math.round(box.minZ * (double)_snowmanxxxxx);
            int _snowmanxxxxxxxxxxx = (int)Math.round(box.maxZ * (double)_snowmanxxxxx);
            BitSetVoxelSet _snowmanxxxxxxxxxxxx = new BitSetVoxelSet(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx);

            for (long _snowmanxxxxxxxxxxxxx = (long)_snowmanxxxxxx; _snowmanxxxxxxxxxxxxx < (long)_snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (long _snowmanxxxxxxxxxxxxxx = (long)_snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxx < (long)_snowmanxxxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  for (long _snowmanxxxxxxxxxxxxxxx = (long)_snowmanxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx < (long)_snowmanxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                     _snowmanxxxxxxxxxxxx.set((int)_snowmanxxxxxxxxxxxxx, (int)_snowmanxxxxxxxxxxxxxx, (int)_snowmanxxxxxxxxxxxxxxx, false, true);
                  }
               }
            }

            return new SimpleVoxelShape(_snowmanxxxxxxxxxxxx);
         }
      } else {
         return new ArrayVoxelShape(FULL_CUBE.voxels, new double[]{box.minX, box.maxX}, new double[]{box.minY, box.maxY}, new double[]{box.minZ, box.maxZ});
      }
   }

   private static int findRequiredBitResolution(double min, double max) {
      if (!(min < -1.0E-7) && !(max > 1.0000001)) {
         for (int _snowman = 0; _snowman <= 3; _snowman++) {
            double _snowmanx = min * (double)(1 << _snowman);
            double _snowmanxx = max * (double)(1 << _snowman);
            boolean _snowmanxxx = Math.abs(_snowmanx - Math.floor(_snowmanx)) < 1.0E-7;
            boolean _snowmanxxxx = Math.abs(_snowmanxx - Math.floor(_snowmanxx)) < 1.0E-7;
            if (_snowmanxxx && _snowmanxxxx) {
               return _snowman;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long lcm(int a, int b) {
      return (long)a * (long)(b / IntMath.gcd(a, b));
   }

   public static VoxelShape union(VoxelShape first, VoxelShape second) {
      return combineAndSimplify(first, second, BooleanBiFunction.OR);
   }

   public static VoxelShape union(VoxelShape first, VoxelShape... others) {
      return Arrays.stream(others).reduce(first, VoxelShapes::union);
   }

   public static VoxelShape combineAndSimplify(VoxelShape first, VoxelShape second, BooleanBiFunction function) {
      return combine(first, second, function).simplify();
   }

   public static VoxelShape combine(VoxelShape one, VoxelShape two, BooleanBiFunction function) {
      if (function.apply(false, false)) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException());
      } else if (one == two) {
         return function.apply(true, true) ? one : empty();
      } else {
         boolean _snowman = function.apply(true, false);
         boolean _snowmanx = function.apply(false, true);
         if (one.isEmpty()) {
            return _snowmanx ? two : empty();
         } else if (two.isEmpty()) {
            return _snowman ? one : empty();
         } else {
            PairList _snowmanxx = createListPair(1, one.getPointPositions(Direction.Axis.X), two.getPointPositions(Direction.Axis.X), _snowman, _snowmanx);
            PairList _snowmanxxx = createListPair(_snowmanxx.getPairs().size() - 1, one.getPointPositions(Direction.Axis.Y), two.getPointPositions(Direction.Axis.Y), _snowman, _snowmanx);
            PairList _snowmanxxxx = createListPair(
               (_snowmanxx.getPairs().size() - 1) * (_snowmanxxx.getPairs().size() - 1),
               one.getPointPositions(Direction.Axis.Z),
               two.getPointPositions(Direction.Axis.Z),
               _snowman,
               _snowmanx
            );
            BitSetVoxelSet _snowmanxxxxx = BitSetVoxelSet.combine(one.voxels, two.voxels, _snowmanxx, _snowmanxxx, _snowmanxxxx, function);
            return (VoxelShape)(_snowmanxx instanceof FractionalPairList && _snowmanxxx instanceof FractionalPairList && _snowmanxxxx instanceof FractionalPairList
               ? new SimpleVoxelShape(_snowmanxxxxx)
               : new ArrayVoxelShape(_snowmanxxxxx, _snowmanxx.getPairs(), _snowmanxxx.getPairs(), _snowmanxxxx.getPairs()));
         }
      }
   }

   public static boolean matchesAnywhere(VoxelShape shape1, VoxelShape shape2, BooleanBiFunction predicate) {
      if (predicate.apply(false, false)) {
         throw (IllegalArgumentException)Util.throwOrPause(new IllegalArgumentException());
      } else if (shape1 == shape2) {
         return predicate.apply(true, true);
      } else if (shape1.isEmpty()) {
         return predicate.apply(false, !shape2.isEmpty());
      } else if (shape2.isEmpty()) {
         return predicate.apply(!shape1.isEmpty(), false);
      } else {
         boolean _snowman = predicate.apply(true, false);
         boolean _snowmanx = predicate.apply(false, true);

         for (Direction.Axis _snowmanxx : AxisCycleDirection.AXES) {
            if (shape1.getMax(_snowmanxx) < shape2.getMin(_snowmanxx) - 1.0E-7) {
               return _snowman || _snowmanx;
            }

            if (shape2.getMax(_snowmanxx) < shape1.getMin(_snowmanxx) - 1.0E-7) {
               return _snowman || _snowmanx;
            }
         }

         PairList _snowmanxx = createListPair(1, shape1.getPointPositions(Direction.Axis.X), shape2.getPointPositions(Direction.Axis.X), _snowman, _snowmanx);
         PairList _snowmanxxx = createListPair(
            _snowmanxx.getPairs().size() - 1, shape1.getPointPositions(Direction.Axis.Y), shape2.getPointPositions(Direction.Axis.Y), _snowman, _snowmanx
         );
         PairList _snowmanxxxx = createListPair(
            (_snowmanxx.getPairs().size() - 1) * (_snowmanxxx.getPairs().size() - 1),
            shape1.getPointPositions(Direction.Axis.Z),
            shape2.getPointPositions(Direction.Axis.Z),
            _snowman,
            _snowmanx
         );
         return matchesAnywhere(_snowmanxx, _snowmanxxx, _snowmanxxxx, shape1.voxels, shape2.voxels, predicate);
      }
   }

   private static boolean matchesAnywhere(PairList mergedX, PairList mergedY, PairList mergedZ, VoxelSet shape1, VoxelSet shape2, BooleanBiFunction predicate) {
      return !mergedX.forEachPair(
         (x1, x2, index1) -> mergedY.forEachPair(
               (y1, y2, index2) -> mergedZ.forEachPair(
                     (z1, z2, index3) -> !predicate.apply(shape1.inBoundsAndContains(x1, y1, z1), shape2.inBoundsAndContains(x2, y2, z2))
                  )
            )
      );
   }

   public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {
      Iterator<VoxelShape> _snowman = shapes.iterator();

      while (_snowman.hasNext()) {
         if (Math.abs(maxDist) < 1.0E-7) {
            return 0.0;
         }

         maxDist = _snowman.next().calculateMaxDistance(axis, box, maxDist);
      }

      return maxDist;
   }

   public static double calculatePushVelocity(Direction.Axis _snowman, Box box, WorldView world, double initial, ShapeContext context, Stream<VoxelShape> shapes) {
      return calculatePushVelocity(box, world, initial, context, AxisCycleDirection.between(_snowman, Direction.Axis.Z), shapes);
   }

   private static double calculatePushVelocity(
      Box box, WorldView world, double initial, ShapeContext context, AxisCycleDirection direction, Stream<VoxelShape> shapes
   ) {
      if (box.getXLength() < 1.0E-6 || box.getYLength() < 1.0E-6 || box.getZLength() < 1.0E-6) {
         return initial;
      } else if (Math.abs(initial) < 1.0E-7) {
         return 0.0;
      } else {
         AxisCycleDirection _snowman = direction.opposite();
         Direction.Axis _snowmanx = _snowman.cycle(Direction.Axis.X);
         Direction.Axis _snowmanxx = _snowman.cycle(Direction.Axis.Y);
         Direction.Axis _snowmanxxx = _snowman.cycle(Direction.Axis.Z);
         BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();
         int _snowmanxxxxx = MathHelper.floor(box.getMin(_snowmanx) - 1.0E-7) - 1;
         int _snowmanxxxxxx = MathHelper.floor(box.getMax(_snowmanx) + 1.0E-7) + 1;
         int _snowmanxxxxxxx = MathHelper.floor(box.getMin(_snowmanxx) - 1.0E-7) - 1;
         int _snowmanxxxxxxxx = MathHelper.floor(box.getMax(_snowmanxx) + 1.0E-7) + 1;
         double _snowmanxxxxxxxxx = box.getMin(_snowmanxxx) - 1.0E-7;
         double _snowmanxxxxxxxxxx = box.getMax(_snowmanxxx) + 1.0E-7;
         boolean _snowmanxxxxxxxxxxx = initial > 0.0;
         int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? MathHelper.floor(box.getMax(_snowmanxxx) - 1.0E-7) - 1 : MathHelper.floor(box.getMin(_snowmanxxx) + 1.0E-7) + 1;
         int _snowmanxxxxxxxxxxxxx = clamp(initial, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx ? 1 : -1;

         for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxx ? _snowmanxxxxxxxxxxxxxxx <= _snowmanxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxx;
            _snowmanxxxxxxxxxxxxxxx += _snowmanxxxxxxxxxxxxxx
         ) {
            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx <= _snowmanxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
                  int _snowmanxxxxxxxxxxxxxxxxxx = 0;
                  if (_snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxx || _snowmanxxxxxxxxxxxxxxxx == _snowmanxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxx || _snowmanxxxxxxxxxxxxxxxxx == _snowmanxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxx == _snowmanxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx++;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxx < 3) {
                     _snowmanxxxx.set(_snowman, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     BlockState _snowmanxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxxx);
                     if ((_snowmanxxxxxxxxxxxxxxxxxx != 1 || _snowmanxxxxxxxxxxxxxxxxxxx.exceedsCube())
                        && (_snowmanxxxxxxxxxxxxxxxxxx != 2 || _snowmanxxxxxxxxxxxxxxxxxxx.isOf(Blocks.MOVING_PISTON))) {
                        initial = _snowmanxxxxxxxxxxxxxxxxxxx.getCollisionShape(world, _snowmanxxxx, context)
                           .calculateMaxDistance(_snowmanxxx, box.offset((double)(-_snowmanxxxx.getX()), (double)(-_snowmanxxxx.getY()), (double)(-_snowmanxxxx.getZ())), initial);
                        if (Math.abs(initial) < 1.0E-7) {
                           return 0.0;
                        }

                        _snowmanxxxxxxxxxxxxx = clamp(initial, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                     }
                  }
               }
            }
         }

         double[] _snowmanxxxxxxxxxxxxxxx = new double[]{initial};
         shapes.forEach(_snowmanxxxxxxxxxxxxxxxx -> _snowman[0] = _snowmanxxxxxxxxxxxxxxxx.calculateMaxDistance(_snowman, box, _snowman[0]));
         return _snowmanxxxxxxxxxxxxxxx[0];
      }
   }

   private static int clamp(double value, double min, double max) {
      return value > 0.0 ? MathHelper.floor(max + value) + 1 : MathHelper.floor(min + value) - 1;
   }

   public static boolean isSideCovered(VoxelShape shape, VoxelShape neighbor, Direction direction) {
      if (shape == fullCube() && neighbor == fullCube()) {
         return true;
      } else if (neighbor.isEmpty()) {
         return false;
      } else {
         Direction.Axis _snowman = direction.getAxis();
         Direction.AxisDirection _snowmanx = direction.getDirection();
         VoxelShape _snowmanxx = _snowmanx == Direction.AxisDirection.POSITIVE ? shape : neighbor;
         VoxelShape _snowmanxxx = _snowmanx == Direction.AxisDirection.POSITIVE ? neighbor : shape;
         BooleanBiFunction _snowmanxxxx = _snowmanx == Direction.AxisDirection.POSITIVE ? BooleanBiFunction.ONLY_FIRST : BooleanBiFunction.ONLY_SECOND;
         return DoubleMath.fuzzyEquals(_snowmanxx.getMax(_snowman), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(_snowmanxxx.getMin(_snowman), 0.0, 1.0E-7)
            && !matchesAnywhere(new SlicedVoxelShape(_snowmanxx, _snowman, _snowmanxx.voxels.getSize(_snowman) - 1), new SlicedVoxelShape(_snowmanxxx, _snowman, 0), _snowmanxxxx);
      }
   }

   public static VoxelShape extrudeFace(VoxelShape shape, Direction direction) {
      if (shape == fullCube()) {
         return fullCube();
      } else {
         Direction.Axis _snowman = direction.getAxis();
         boolean _snowmanx;
         int _snowmanxx;
         if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
            _snowmanx = DoubleMath.fuzzyEquals(shape.getMax(_snowman), 1.0, 1.0E-7);
            _snowmanxx = shape.voxels.getSize(_snowman) - 1;
         } else {
            _snowmanx = DoubleMath.fuzzyEquals(shape.getMin(_snowman), 0.0, 1.0E-7);
            _snowmanxx = 0;
         }

         return (VoxelShape)(!_snowmanx ? empty() : new SlicedVoxelShape(shape, _snowman, _snowmanxx));
      }
   }

   public static boolean adjacentSidesCoverSquare(VoxelShape one, VoxelShape two, Direction direction) {
      if (one != fullCube() && two != fullCube()) {
         Direction.Axis _snowman = direction.getAxis();
         Direction.AxisDirection _snowmanx = direction.getDirection();
         VoxelShape _snowmanxx = _snowmanx == Direction.AxisDirection.POSITIVE ? one : two;
         VoxelShape _snowmanxxx = _snowmanx == Direction.AxisDirection.POSITIVE ? two : one;
         if (!DoubleMath.fuzzyEquals(_snowmanxx.getMax(_snowman), 1.0, 1.0E-7)) {
            _snowmanxx = empty();
         }

         if (!DoubleMath.fuzzyEquals(_snowmanxxx.getMin(_snowman), 0.0, 1.0E-7)) {
            _snowmanxxx = empty();
         }

         return !matchesAnywhere(
            fullCube(),
            combine(new SlicedVoxelShape(_snowmanxx, _snowman, _snowmanxx.voxels.getSize(_snowman) - 1), new SlicedVoxelShape(_snowmanxxx, _snowman, 0), BooleanBiFunction.OR),
            BooleanBiFunction.ONLY_FIRST
         );
      } else {
         return true;
      }
   }

   public static boolean unionCoversFullCube(VoxelShape one, VoxelShape two) {
      if (one == fullCube() || two == fullCube()) {
         return true;
      } else {
         return one.isEmpty() && two.isEmpty() ? false : !matchesAnywhere(fullCube(), combine(one, two, BooleanBiFunction.OR), BooleanBiFunction.ONLY_FIRST);
      }
   }

   @VisibleForTesting
   protected static PairList createListPair(int size, DoubleList first, DoubleList second, boolean includeFirst, boolean includeSecond) {
      int _snowman = first.size() - 1;
      int _snowmanx = second.size() - 1;
      if (first instanceof FractionalDoubleList && second instanceof FractionalDoubleList) {
         long _snowmanxx = lcm(_snowman, _snowmanx);
         if ((long)size * _snowmanxx <= 256L) {
            return new FractionalPairList(_snowman, _snowmanx);
         }
      }

      if (first.getDouble(_snowman) < second.getDouble(0) - 1.0E-7) {
         return new DisjointPairList(first, second, false);
      } else if (second.getDouble(_snowmanx) < first.getDouble(0) - 1.0E-7) {
         return new DisjointPairList(second, first, true);
      } else if (_snowman != _snowmanx || !Objects.equals(first, second)) {
         return new SimplePairList(first, second, includeFirst, includeSecond);
      } else if (first instanceof IdentityPairList) {
         return (PairList)first;
      } else {
         return (PairList)(second instanceof IdentityPairList ? (PairList)second : new IdentityPairList(first));
      }
   }

   public interface BoxConsumer {
      void consume(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);
   }
}
