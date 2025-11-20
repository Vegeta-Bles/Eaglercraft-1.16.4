package net.minecraft.entity.ai;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TargetFinder {
   @Nullable
   public static Vec3d findTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance) {
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, null, true, (float) (Math.PI / 2), mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findGroundTarget(
      PathAwareEntity mob,
      int maxHorizontalDistance,
      int maxVerticalDistance,
      int preferredYDifference,
      @Nullable Vec3d preferredAngle,
      double maxAngleDifference
   ) {
      return findTarget(
         mob,
         maxHorizontalDistance,
         maxVerticalDistance,
         preferredYDifference,
         preferredAngle,
         true,
         maxAngleDifference,
         mob::getPathfindingFavor,
         true,
         0,
         0,
         false
      );
   }

   @Nullable
   public static Vec3d findGroundTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance) {
      return findGroundTarget(mob, maxHorizontalDistance, maxVerticalDistance, mob::getPathfindingFavor);
   }

   @Nullable
   public static Vec3d findGroundTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, ToDoubleFunction<BlockPos> pathfindingFavor) {
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, null, false, 0.0, pathfindingFavor, true, 0, 0, true);
   }

   @Nullable
   public static Vec3d findAirTarget(
      PathAwareEntity mob,
      int maxHorizontalDistance,
      int maxVerticalDistance,
      Vec3d preferredAngle,
      float maxAngleDifference,
      int distanceAboveGroundRange,
      int minDistanceAboveGround
   ) {
      return findTarget(
         mob,
         maxHorizontalDistance,
         maxVerticalDistance,
         0,
         preferredAngle,
         false,
         (double)maxAngleDifference,
         mob::getPathfindingFavor,
         true,
         distanceAboveGroundRange,
         minDistanceAboveGround,
         true
      );
   }

   @Nullable
   public static Vec3d method_27929(PathAwareEntity arg, int i, int j, Vec3d arg2) {
      Vec3d lv = arg2.subtract(arg.getX(), arg.getY(), arg.getZ());
      return findTarget(arg, i, j, 0, lv, false, (float) (Math.PI / 2), arg::getPathfindingFavor, true, 0, 0, true);
   }

   @Nullable
   public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d lv = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, lv, true, (float) (Math.PI / 2), mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos, double maxAngleDifference) {
      Vec3d lv = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, lv, true, maxAngleDifference, mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findGroundTargetTowards(
      PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, Vec3d pos, double maxAngleDifference
   ) {
      Vec3d lv = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(
         mob, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, lv, false, maxAngleDifference, mob::getPathfindingFavor, true, 0, 0, false
      );
   }

   @Nullable
   public static Vec3d findTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d lv = mob.getPos().subtract(pos);
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, lv, true, (float) (Math.PI / 2), mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findGroundTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d lv = mob.getPos().subtract(pos);
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, lv, false, (float) (Math.PI / 2), mob::getPathfindingFavor, true, 0, 0, true);
   }

   @Nullable
   private static Vec3d findTarget(
      PathAwareEntity mob,
      int maxHorizontalDistance,
      int maxVerticalDistance,
      int preferredYDifference,
      @Nullable Vec3d preferredAngle,
      boolean notInWater,
      double maxAngleDifference,
      ToDoubleFunction<BlockPos> favorProvider,
      boolean aboveGround,
      int distanceAboveGroundRange,
      int minDistanceAboveGround,
      boolean validPositionsOnly
   ) {
      EntityNavigation lv = mob.getNavigation();
      Random random = mob.getRandom();
      boolean bl4;
      if (mob.hasPositionTarget()) {
         bl4 = mob.getPositionTarget().isWithinDistance(mob.getPos(), (double)(mob.getPositionTargetRange() + (float)maxHorizontalDistance) + 1.0);
      } else {
         bl4 = false;
      }

      boolean bl6 = false;
      double e = Double.NEGATIVE_INFINITY;
      BlockPos lv2 = mob.getBlockPos();

      for (int n = 0; n < 10; n++) {
         BlockPos lv3 = getRandomOffset(random, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, preferredAngle, maxAngleDifference);
         if (lv3 != null) {
            int o = lv3.getX();
            int p = lv3.getY();
            int q = lv3.getZ();
            if (mob.hasPositionTarget() && maxHorizontalDistance > 1) {
               BlockPos lv4 = mob.getPositionTarget();
               if (mob.getX() > (double)lv4.getX()) {
                  o -= random.nextInt(maxHorizontalDistance / 2);
               } else {
                  o += random.nextInt(maxHorizontalDistance / 2);
               }

               if (mob.getZ() > (double)lv4.getZ()) {
                  q -= random.nextInt(maxHorizontalDistance / 2);
               } else {
                  q += random.nextInt(maxHorizontalDistance / 2);
               }
            }

            BlockPos lv5 = new BlockPos((double)o + mob.getX(), (double)p + mob.getY(), (double)q + mob.getZ());
            if (lv5.getY() >= 0
               && lv5.getY() <= mob.world.getHeight()
               && (!bl4 || mob.isInWalkTargetRange(lv5))
               && (!validPositionsOnly || lv.isValidPosition(lv5))) {
               if (aboveGround) {
                  lv5 = findValidPositionAbove(
                     lv5,
                     random.nextInt(distanceAboveGroundRange + 1) + minDistanceAboveGround,
                     mob.world.getHeight(),
                     arg2 -> mob.world.getBlockState(arg2).getMaterial().isSolid()
                  );
               }

               if (notInWater || !mob.world.getFluidState(lv5).isIn(FluidTags.WATER)) {
                  PathNodeType lv6 = LandPathNodeMaker.getLandNodeType(mob.world, lv5.mutableCopy());
                  if (mob.getPathfindingPenalty(lv6) == 0.0F) {
                     double f = favorProvider.applyAsDouble(lv5);
                     if (f > e) {
                        e = f;
                        lv2 = lv5;
                        bl6 = true;
                     }
                  }
               }
            }
         }
      }

      return bl6 ? Vec3d.ofBottomCenter(lv2) : null;
   }

   @Nullable
   private static BlockPos getRandomOffset(
      Random random, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, @Nullable Vec3d preferredAngle, double maxAngleDifference
   ) {
      if (preferredAngle != null && !(maxAngleDifference >= Math.PI)) {
         double e = MathHelper.atan2(preferredAngle.z, preferredAngle.x) - (float) (Math.PI / 2);
         double f = e + (double)(2.0F * random.nextFloat() - 1.0F) * maxAngleDifference;
         double g = Math.sqrt(random.nextDouble()) * (double)MathHelper.SQUARE_ROOT_OF_TWO * (double)maxHorizontalDistance;
         double h = -g * Math.sin(f);
         double o = g * Math.cos(f);
         if (!(Math.abs(h) > (double)maxHorizontalDistance) && !(Math.abs(o) > (double)maxHorizontalDistance)) {
            int p = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
            return new BlockPos(h, (double)p, o);
         } else {
            return null;
         }
      } else {
         int l = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
         int m = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
         int n = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
         return new BlockPos(l, m, n);
      }
   }

   static BlockPos findValidPositionAbove(BlockPos pos, int minDistanceAboveIllegal, int maxOffset, Predicate<BlockPos> illegalPredicate) {
      if (minDistanceAboveIllegal < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + minDistanceAboveIllegal + ", expected >= 0");
      } else if (!illegalPredicate.test(pos)) {
         return pos;
      } else {
         BlockPos lv = pos.up();

         while (lv.getY() < maxOffset && illegalPredicate.test(lv)) {
            lv = lv.up();
         }

         BlockPos lv2 = lv;

         while (lv2.getY() < maxOffset && lv2.getY() - lv.getY() < minDistanceAboveIllegal) {
            BlockPos lv3 = lv2.up();
            if (illegalPredicate.test(lv3)) {
               break;
            }

            lv2 = lv3;
         }

         return lv2;
      }
   }
}
