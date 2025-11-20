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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      VoxelSet lv = new BitSetVoxelSet(1, 1, 1);
      lv.set(0, 0, 0, true, true);
      return new SimpleVoxelShape(lv);
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
      int i = findRequiredBitResolution(box.minX, box.maxX);
      int j = findRequiredBitResolution(box.minY, box.maxY);
      int k = findRequiredBitResolution(box.minZ, box.maxZ);
      if (i >= 0 && j >= 0 && k >= 0) {
         if (i == 0 && j == 0 && k == 0) {
            return box.contains(0.5, 0.5, 0.5) ? fullCube() : empty();
         } else {
            int l = 1 << i;
            int m = 1 << j;
            int n = 1 << k;
            int o = (int)Math.round(box.minX * (double)l);
            int p = (int)Math.round(box.maxX * (double)l);
            int q = (int)Math.round(box.minY * (double)m);
            int r = (int)Math.round(box.maxY * (double)m);
            int s = (int)Math.round(box.minZ * (double)n);
            int t = (int)Math.round(box.maxZ * (double)n);
            BitSetVoxelSet lv = new BitSetVoxelSet(l, m, n, o, q, s, p, r, t);

            for (long u = (long)o; u < (long)p; u++) {
               for (long v = (long)q; v < (long)r; v++) {
                  for (long w = (long)s; w < (long)t; w++) {
                     lv.set((int)u, (int)v, (int)w, false, true);
                  }
               }
            }

            return new SimpleVoxelShape(lv);
         }
      } else {
         return new ArrayVoxelShape(FULL_CUBE.voxels, new double[]{box.minX, box.maxX}, new double[]{box.minY, box.maxY}, new double[]{box.minZ, box.maxZ});
      }
   }

   private static int findRequiredBitResolution(double min, double max) {
      if (!(min < -1.0E-7) && !(max > 1.0000001)) {
         for (int i = 0; i <= 3; i++) {
            double f = min * (double)(1 << i);
            double g = max * (double)(1 << i);
            boolean bl = Math.abs(f - Math.floor(f)) < 1.0E-7;
            boolean bl2 = Math.abs(g - Math.floor(g)) < 1.0E-7;
            if (bl && bl2) {
               return i;
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
         boolean bl = function.apply(true, false);
         boolean bl2 = function.apply(false, true);
         if (one.isEmpty()) {
            return bl2 ? two : empty();
         } else if (two.isEmpty()) {
            return bl ? one : empty();
         } else {
            PairList lv = createListPair(1, one.getPointPositions(Direction.Axis.X), two.getPointPositions(Direction.Axis.X), bl, bl2);
            PairList lv2 = createListPair(lv.getPairs().size() - 1, one.getPointPositions(Direction.Axis.Y), two.getPointPositions(Direction.Axis.Y), bl, bl2);
            PairList lv3 = createListPair(
               (lv.getPairs().size() - 1) * (lv2.getPairs().size() - 1),
               one.getPointPositions(Direction.Axis.Z),
               two.getPointPositions(Direction.Axis.Z),
               bl,
               bl2
            );
            BitSetVoxelSet lv4 = BitSetVoxelSet.combine(one.voxels, two.voxels, lv, lv2, lv3, function);
            return (VoxelShape)(lv instanceof FractionalPairList && lv2 instanceof FractionalPairList && lv3 instanceof FractionalPairList
               ? new SimpleVoxelShape(lv4)
               : new ArrayVoxelShape(lv4, lv.getPairs(), lv2.getPairs(), lv3.getPairs()));
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
         boolean bl = predicate.apply(true, false);
         boolean bl2 = predicate.apply(false, true);

         for (Direction.Axis lv : AxisCycleDirection.AXES) {
            if (shape1.getMax(lv) < shape2.getMin(lv) - 1.0E-7) {
               return bl || bl2;
            }

            if (shape2.getMax(lv) < shape1.getMin(lv) - 1.0E-7) {
               return bl || bl2;
            }
         }

         PairList lv2 = createListPair(1, shape1.getPointPositions(Direction.Axis.X), shape2.getPointPositions(Direction.Axis.X), bl, bl2);
         PairList lv3 = createListPair(
            lv2.getPairs().size() - 1, shape1.getPointPositions(Direction.Axis.Y), shape2.getPointPositions(Direction.Axis.Y), bl, bl2
         );
         PairList lv4 = createListPair(
            (lv2.getPairs().size() - 1) * (lv3.getPairs().size() - 1),
            shape1.getPointPositions(Direction.Axis.Z),
            shape2.getPointPositions(Direction.Axis.Z),
            bl,
            bl2
         );
         return matchesAnywhere(lv2, lv3, lv4, shape1.voxels, shape2.voxels, predicate);
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
      Iterator<VoxelShape> iterator = shapes.iterator();

      while (iterator.hasNext()) {
         if (Math.abs(maxDist) < 1.0E-7) {
            return 0.0;
         }

         maxDist = iterator.next().calculateMaxDistance(axis, box, maxDist);
      }

      return maxDist;
   }

   public static double calculatePushVelocity(Direction.Axis arg, Box box, WorldView world, double initial, ShapeContext context, Stream<VoxelShape> shapes) {
      return calculatePushVelocity(box, world, initial, context, AxisCycleDirection.between(arg, Direction.Axis.Z), shapes);
   }

   private static double calculatePushVelocity(
      Box box, WorldView world, double initial, ShapeContext context, AxisCycleDirection direction, Stream<VoxelShape> shapes
   ) {
      if (box.getXLength() < 1.0E-6 || box.getYLength() < 1.0E-6 || box.getZLength() < 1.0E-6) {
         return initial;
      } else if (Math.abs(initial) < 1.0E-7) {
         return 0.0;
      } else {
         AxisCycleDirection lv = direction.opposite();
         Direction.Axis lv2 = lv.cycle(Direction.Axis.X);
         Direction.Axis lv3 = lv.cycle(Direction.Axis.Y);
         Direction.Axis lv4 = lv.cycle(Direction.Axis.Z);
         BlockPos.Mutable lv5 = new BlockPos.Mutable();
         int i = MathHelper.floor(box.getMin(lv2) - 1.0E-7) - 1;
         int j = MathHelper.floor(box.getMax(lv2) + 1.0E-7) + 1;
         int k = MathHelper.floor(box.getMin(lv3) - 1.0E-7) - 1;
         int l = MathHelper.floor(box.getMax(lv3) + 1.0E-7) + 1;
         double e = box.getMin(lv4) - 1.0E-7;
         double f = box.getMax(lv4) + 1.0E-7;
         boolean bl = initial > 0.0;
         int m = bl ? MathHelper.floor(box.getMax(lv4) - 1.0E-7) - 1 : MathHelper.floor(box.getMin(lv4) + 1.0E-7) + 1;
         int n = clamp(initial, e, f);
         int o = bl ? 1 : -1;

         for (int p = m; bl ? p <= n : p >= n; p += o) {
            for (int q = i; q <= j; q++) {
               for (int r = k; r <= l; r++) {
                  int s = 0;
                  if (q == i || q == j) {
                     s++;
                  }

                  if (r == k || r == l) {
                     s++;
                  }

                  if (p == m || p == n) {
                     s++;
                  }

                  if (s < 3) {
                     lv5.set(lv, q, r, p);
                     BlockState lv6 = world.getBlockState(lv5);
                     if ((s != 1 || lv6.exceedsCube()) && (s != 2 || lv6.isOf(Blocks.MOVING_PISTON))) {
                        initial = lv6.getCollisionShape(world, lv5, context)
                           .calculateMaxDistance(lv4, box.offset((double)(-lv5.getX()), (double)(-lv5.getY()), (double)(-lv5.getZ())), initial);
                        if (Math.abs(initial) < 1.0E-7) {
                           return 0.0;
                        }

                        n = clamp(initial, e, f);
                     }
                  }
               }
            }
         }

         double[] ds = new double[]{initial};
         shapes.forEach(arg3 -> ds[0] = arg3.calculateMaxDistance(lv4, box, ds[0]));
         return ds[0];
      }
   }

   private static int clamp(double value, double min, double max) {
      return value > 0.0 ? MathHelper.floor(max + value) + 1 : MathHelper.floor(min + value) - 1;
   }

   @Environment(EnvType.CLIENT)
   public static boolean isSideCovered(VoxelShape shape, VoxelShape neighbor, Direction direction) {
      if (shape == fullCube() && neighbor == fullCube()) {
         return true;
      } else if (neighbor.isEmpty()) {
         return false;
      } else {
         Direction.Axis lv = direction.getAxis();
         Direction.AxisDirection lv2 = direction.getDirection();
         VoxelShape lv3 = lv2 == Direction.AxisDirection.POSITIVE ? shape : neighbor;
         VoxelShape lv4 = lv2 == Direction.AxisDirection.POSITIVE ? neighbor : shape;
         BooleanBiFunction lv5 = lv2 == Direction.AxisDirection.POSITIVE ? BooleanBiFunction.ONLY_FIRST : BooleanBiFunction.ONLY_SECOND;
         return DoubleMath.fuzzyEquals(lv3.getMax(lv), 1.0, 1.0E-7)
            && DoubleMath.fuzzyEquals(lv4.getMin(lv), 0.0, 1.0E-7)
            && !matchesAnywhere(new SlicedVoxelShape(lv3, lv, lv3.voxels.getSize(lv) - 1), new SlicedVoxelShape(lv4, lv, 0), lv5);
      }
   }

   public static VoxelShape extrudeFace(VoxelShape shape, Direction direction) {
      if (shape == fullCube()) {
         return fullCube();
      } else {
         Direction.Axis lv = direction.getAxis();
         boolean bl;
         int i;
         if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
            bl = DoubleMath.fuzzyEquals(shape.getMax(lv), 1.0, 1.0E-7);
            i = shape.voxels.getSize(lv) - 1;
         } else {
            bl = DoubleMath.fuzzyEquals(shape.getMin(lv), 0.0, 1.0E-7);
            i = 0;
         }

         return (VoxelShape)(!bl ? empty() : new SlicedVoxelShape(shape, lv, i));
      }
   }

   public static boolean adjacentSidesCoverSquare(VoxelShape one, VoxelShape two, Direction direction) {
      if (one != fullCube() && two != fullCube()) {
         Direction.Axis lv = direction.getAxis();
         Direction.AxisDirection lv2 = direction.getDirection();
         VoxelShape lv3 = lv2 == Direction.AxisDirection.POSITIVE ? one : two;
         VoxelShape lv4 = lv2 == Direction.AxisDirection.POSITIVE ? two : one;
         if (!DoubleMath.fuzzyEquals(lv3.getMax(lv), 1.0, 1.0E-7)) {
            lv3 = empty();
         }

         if (!DoubleMath.fuzzyEquals(lv4.getMin(lv), 0.0, 1.0E-7)) {
            lv4 = empty();
         }

         return !matchesAnywhere(
            fullCube(),
            combine(new SlicedVoxelShape(lv3, lv, lv3.voxels.getSize(lv) - 1), new SlicedVoxelShape(lv4, lv, 0), BooleanBiFunction.OR),
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
      int j = first.size() - 1;
      int k = second.size() - 1;
      if (first instanceof FractionalDoubleList && second instanceof FractionalDoubleList) {
         long l = lcm(j, k);
         if ((long)size * l <= 256L) {
            return new FractionalPairList(j, k);
         }
      }

      if (first.getDouble(j) < second.getDouble(0) - 1.0E-7) {
         return new DisjointPairList(first, second, false);
      } else if (second.getDouble(k) < first.getDouble(0) - 1.0E-7) {
         return new DisjointPairList(second, first, true);
      } else if (j != k || !Objects.equals(first, second)) {
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
