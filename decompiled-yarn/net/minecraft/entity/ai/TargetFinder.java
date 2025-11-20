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
   public static Vec3d method_27929(PathAwareEntity _snowman, int _snowman, int _snowman, Vec3d _snowman) {
      Vec3d _snowmanxxxx = _snowman.subtract(_snowman.getX(), _snowman.getY(), _snowman.getZ());
      return findTarget(_snowman, _snowman, _snowman, 0, _snowmanxxxx, false, (float) (Math.PI / 2), _snowman::getPathfindingFavor, true, 0, 0, true);
   }

   @Nullable
   public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d _snowman = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, _snowman, true, (float) (Math.PI / 2), mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos, double maxAngleDifference) {
      Vec3d _snowman = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, _snowman, true, maxAngleDifference, mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findGroundTargetTowards(
      PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, Vec3d pos, double maxAngleDifference
   ) {
      Vec3d _snowman = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
      return findTarget(
         mob, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, _snowman, false, maxAngleDifference, mob::getPathfindingFavor, true, 0, 0, false
      );
   }

   @Nullable
   public static Vec3d findTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d _snowman = mob.getPos().subtract(pos);
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, _snowman, true, (float) (Math.PI / 2), mob::getPathfindingFavor, false, 0, 0, true);
   }

   @Nullable
   public static Vec3d findGroundTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
      Vec3d _snowman = mob.getPos().subtract(pos);
      return findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, _snowman, false, (float) (Math.PI / 2), mob::getPathfindingFavor, true, 0, 0, true);
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
      EntityNavigation _snowman = mob.getNavigation();
      Random _snowmanx = mob.getRandom();
      boolean _snowmanxx;
      if (mob.hasPositionTarget()) {
         _snowmanxx = mob.getPositionTarget().isWithinDistance(mob.getPos(), (double)(mob.getPositionTargetRange() + (float)maxHorizontalDistance) + 1.0);
      } else {
         _snowmanxx = false;
      }

      boolean _snowmanxxx = false;
      double _snowmanxxxx = Double.NEGATIVE_INFINITY;
      BlockPos _snowmanxxxxx = mob.getBlockPos();

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 10; _snowmanxxxxxx++) {
         BlockPos _snowmanxxxxxxx = getRandomOffset(_snowmanx, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, preferredAngle, maxAngleDifference);
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = _snowmanxxxxxxx.getX();
            int _snowmanxxxxxxxxx = _snowmanxxxxxxx.getY();
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxx.getZ();
            if (mob.hasPositionTarget() && maxHorizontalDistance > 1) {
               BlockPos _snowmanxxxxxxxxxxx = mob.getPositionTarget();
               if (mob.getX() > (double)_snowmanxxxxxxxxxxx.getX()) {
                  _snowmanxxxxxxxx -= _snowmanx.nextInt(maxHorizontalDistance / 2);
               } else {
                  _snowmanxxxxxxxx += _snowmanx.nextInt(maxHorizontalDistance / 2);
               }

               if (mob.getZ() > (double)_snowmanxxxxxxxxxxx.getZ()) {
                  _snowmanxxxxxxxxxx -= _snowmanx.nextInt(maxHorizontalDistance / 2);
               } else {
                  _snowmanxxxxxxxxxx += _snowmanx.nextInt(maxHorizontalDistance / 2);
               }
            }

            BlockPos _snowmanxxxxxxxxxxxx = new BlockPos((double)_snowmanxxxxxxxx + mob.getX(), (double)_snowmanxxxxxxxxx + mob.getY(), (double)_snowmanxxxxxxxxxx + mob.getZ());
            if (_snowmanxxxxxxxxxxxx.getY() >= 0
               && _snowmanxxxxxxxxxxxx.getY() <= mob.world.getHeight()
               && (!_snowmanxx || mob.isInWalkTargetRange(_snowmanxxxxxxxxxxxx))
               && (!validPositionsOnly || _snowman.isValidPosition(_snowmanxxxxxxxxxxxx))) {
               if (aboveGround) {
                  _snowmanxxxxxxxxxxxx = findValidPositionAbove(
                     _snowmanxxxxxxxxxxxx,
                     _snowmanx.nextInt(distanceAboveGroundRange + 1) + minDistanceAboveGround,
                     mob.world.getHeight(),
                     _snowmanxxxxxxxxxxxxx -> mob.world.getBlockState(_snowmanxxxxxxxxxxxxx).getMaterial().isSolid()
                  );
               }

               if (notInWater || !mob.world.getFluidState(_snowmanxxxxxxxxxxxx).isIn(FluidTags.WATER)) {
                  PathNodeType _snowmanxxxxxxxxxxxxx = LandPathNodeMaker.getLandNodeType(mob.world, _snowmanxxxxxxxxxxxx.mutableCopy());
                  if (mob.getPathfindingPenalty(_snowmanxxxxxxxxxxxxx) == 0.0F) {
                     double _snowmanxxxxxxxxxxxxxx = favorProvider.applyAsDouble(_snowmanxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxx > _snowmanxxxx) {
                        _snowmanxxxx = _snowmanxxxxxxxxxxxxxx;
                        _snowmanxxxxx = _snowmanxxxxxxxxxxxx;
                        _snowmanxxx = true;
                     }
                  }
               }
            }
         }
      }

      return _snowmanxxx ? Vec3d.ofBottomCenter(_snowmanxxxxx) : null;
   }

   @Nullable
   private static BlockPos getRandomOffset(
      Random random, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, @Nullable Vec3d preferredAngle, double maxAngleDifference
   ) {
      if (preferredAngle != null && !(maxAngleDifference >= Math.PI)) {
         double _snowman = MathHelper.atan2(preferredAngle.z, preferredAngle.x) - (float) (Math.PI / 2);
         double _snowmanx = _snowman + (double)(2.0F * random.nextFloat() - 1.0F) * maxAngleDifference;
         double _snowmanxx = Math.sqrt(random.nextDouble()) * (double)MathHelper.SQUARE_ROOT_OF_TWO * (double)maxHorizontalDistance;
         double _snowmanxxx = -_snowmanxx * Math.sin(_snowmanx);
         double _snowmanxxxx = _snowmanxx * Math.cos(_snowmanx);
         if (!(Math.abs(_snowmanxxx) > (double)maxHorizontalDistance) && !(Math.abs(_snowmanxxxx) > (double)maxHorizontalDistance)) {
            int _snowmanxxxxx = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
            return new BlockPos(_snowmanxxx, (double)_snowmanxxxxx, _snowmanxxxx);
         } else {
            return null;
         }
      } else {
         int _snowman = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
         int _snowmanx = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
         int _snowmanxx = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
         return new BlockPos(_snowman, _snowmanx, _snowmanxx);
      }
   }

   static BlockPos findValidPositionAbove(BlockPos pos, int minDistanceAboveIllegal, int maxOffset, Predicate<BlockPos> illegalPredicate) {
      if (minDistanceAboveIllegal < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + minDistanceAboveIllegal + ", expected >= 0");
      } else if (!illegalPredicate.test(pos)) {
         return pos;
      } else {
         BlockPos _snowman = pos.up();

         while (_snowman.getY() < maxOffset && illegalPredicate.test(_snowman)) {
            _snowman = _snowman.up();
         }

         BlockPos _snowmanx = _snowman;

         while (_snowmanx.getY() < maxOffset && _snowmanx.getY() - _snowman.getY() < minDistanceAboveIllegal) {
            BlockPos _snowmanxx = _snowmanx.up();
            if (illegalPredicate.test(_snowmanxx)) {
               break;
            }

            _snowmanx = _snowmanxx;
         }

         return _snowmanx;
      }
   }
}
