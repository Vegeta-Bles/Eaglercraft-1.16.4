/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.ai;

import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TargetFinder {
    @Nullable
    public static Vec3d findTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance) {
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, null, true, 1.5707963705062866, mob::getPathfindingFavor, false, 0, 0, true);
    }

    @Nullable
    public static Vec3d findGroundTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, @Nullable Vec3d preferredAngle, double maxAngleDifference) {
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, preferredAngle, true, maxAngleDifference, mob::getPathfindingFavor, true, 0, 0, false);
    }

    @Nullable
    public static Vec3d findGroundTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance) {
        return TargetFinder.findGroundTarget(mob, maxHorizontalDistance, maxVerticalDistance, mob::getPathfindingFavor);
    }

    @Nullable
    public static Vec3d findGroundTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, ToDoubleFunction<BlockPos> pathfindingFavor) {
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, null, false, 0.0, pathfindingFavor, true, 0, 0, true);
    }

    @Nullable
    public static Vec3d findAirTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d preferredAngle, float maxAngleDifference, int distanceAboveGroundRange, int minDistanceAboveGround) {
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, preferredAngle, false, maxAngleDifference, mob::getPathfindingFavor, true, distanceAboveGroundRange, minDistanceAboveGround, true);
    }

    @Nullable
    public static Vec3d method_27929(PathAwareEntity pathAwareEntity, int n, int n2, Vec3d vec3d) {
        _snowman = vec3d.subtract(pathAwareEntity.getX(), pathAwareEntity.getY(), pathAwareEntity.getZ());
        return TargetFinder.findTarget(pathAwareEntity, n, n2, 0, _snowman, false, 1.5707963705062866, pathAwareEntity::getPathfindingFavor, true, 0, 0, true);
    }

    @Nullable
    public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
        Vec3d vec3d = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, vec3d, true, 1.5707963705062866, mob::getPathfindingFavor, false, 0, 0, true);
    }

    @Nullable
    public static Vec3d findTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos, double maxAngleDifference) {
        Vec3d vec3d = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, vec3d, true, maxAngleDifference, mob::getPathfindingFavor, false, 0, 0, true);
    }

    @Nullable
    public static Vec3d findGroundTargetTowards(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, Vec3d pos, double maxAngleDifference) {
        Vec3d vec3d = pos.subtract(mob.getX(), mob.getY(), mob.getZ());
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, vec3d, false, maxAngleDifference, mob::getPathfindingFavor, true, 0, 0, false);
    }

    @Nullable
    public static Vec3d findTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
        Vec3d vec3d = mob.getPos().subtract(pos);
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, vec3d, true, 1.5707963705062866, mob::getPathfindingFavor, false, 0, 0, true);
    }

    @Nullable
    public static Vec3d findGroundTargetAwayFrom(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, Vec3d pos) {
        Vec3d vec3d = mob.getPos().subtract(pos);
        return TargetFinder.findTarget(mob, maxHorizontalDistance, maxVerticalDistance, 0, vec3d, false, 1.5707963705062866, mob::getPathfindingFavor, true, 0, 0, true);
    }

    @Nullable
    private static Vec3d findTarget(PathAwareEntity mob, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, @Nullable Vec3d preferredAngle, boolean notInWater, double maxAngleDifference, ToDoubleFunction<BlockPos> favorProvider, boolean aboveGround, int distanceAboveGroundRange, int minDistanceAboveGround, boolean validPositionsOnly) {
        EntityNavigation entityNavigation = mob.getNavigation();
        Random _snowman2 = mob.getRandom();
        boolean _snowman3 = mob.hasPositionTarget() ? mob.getPositionTarget().isWithinDistance(mob.getPos(), (double)(mob.getPositionTargetRange() + (float)maxHorizontalDistance) + 1.0) : false;
        boolean _snowman4 = false;
        double _snowman5 = Double.NEGATIVE_INFINITY;
        BlockPos _snowman6 = mob.getBlockPos();
        for (int i = 0; i < 10; ++i) {
            BlockPos blockPos2 = TargetFinder.getRandomOffset(_snowman2, maxHorizontalDistance, maxVerticalDistance, preferredYDifference, preferredAngle, maxAngleDifference);
            if (blockPos2 == null) continue;
            int _snowman7 = blockPos2.getX();
            int _snowman8 = blockPos2.getY();
            int _snowman9 = blockPos2.getZ();
            if (mob.hasPositionTarget() && maxHorizontalDistance > 1) {
                _snowman = mob.getPositionTarget();
                _snowman7 = mob.getX() > (double)_snowman.getX() ? (_snowman7 -= _snowman2.nextInt(maxHorizontalDistance / 2)) : (_snowman7 += _snowman2.nextInt(maxHorizontalDistance / 2));
                _snowman9 = mob.getZ() > (double)_snowman.getZ() ? (_snowman9 -= _snowman2.nextInt(maxHorizontalDistance / 2)) : (_snowman9 += _snowman2.nextInt(maxHorizontalDistance / 2));
            }
            if ((_snowman = new BlockPos((double)_snowman7 + mob.getX(), (double)_snowman8 + mob.getY(), (double)_snowman9 + mob.getZ())).getY() < 0 || _snowman.getY() > mob.world.getHeight() || _snowman3 && !mob.isInWalkTargetRange(_snowman) || validPositionsOnly && !entityNavigation.isValidPosition(_snowman)) continue;
            if (aboveGround) {
                _snowman = TargetFinder.findValidPositionAbove(_snowman, _snowman2.nextInt(distanceAboveGroundRange + 1) + minDistanceAboveGround, mob.world.getHeight(), blockPos -> pathAwareEntity.world.getBlockState((BlockPos)blockPos).getMaterial().isSolid());
            }
            if (!notInWater && mob.world.getFluidState(_snowman).isIn(FluidTags.WATER) || mob.getPathfindingPenalty(_snowman = LandPathNodeMaker.getLandNodeType(mob.world, _snowman.mutableCopy())) != 0.0f || !((_snowman = favorProvider.applyAsDouble(_snowman)) > _snowman5)) continue;
            _snowman5 = _snowman;
            _snowman6 = _snowman;
            _snowman4 = true;
        }
        if (_snowman4) {
            return Vec3d.ofBottomCenter(_snowman6);
        }
        return null;
    }

    @Nullable
    private static BlockPos getRandomOffset(Random random, int maxHorizontalDistance, int maxVerticalDistance, int preferredYDifference, @Nullable Vec3d preferredAngle, double maxAngleDifference) {
        if (preferredAngle == null || maxAngleDifference >= Math.PI) {
            int n = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
            _snowman = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
            _snowman = random.nextInt(2 * maxHorizontalDistance + 1) - maxHorizontalDistance;
            return new BlockPos(n, _snowman, _snowman);
        }
        double d = MathHelper.atan2(preferredAngle.z, preferredAngle.x) - 1.5707963705062866;
        _snowman = d + (double)(2.0f * random.nextFloat() - 1.0f) * maxAngleDifference;
        _snowman = Math.sqrt(random.nextDouble()) * (double)MathHelper.SQUARE_ROOT_OF_TWO * (double)maxHorizontalDistance;
        _snowman = -_snowman * Math.sin(_snowman);
        _snowman = _snowman * Math.cos(_snowman);
        if (Math.abs(_snowman) > (double)maxHorizontalDistance || Math.abs(_snowman) > (double)maxHorizontalDistance) {
            return null;
        }
        int _snowman2 = random.nextInt(2 * maxVerticalDistance + 1) - maxVerticalDistance + preferredYDifference;
        return new BlockPos(_snowman, (double)_snowman2, _snowman);
    }

    static BlockPos findValidPositionAbove(BlockPos pos, int minDistanceAboveIllegal, int maxOffset, Predicate<BlockPos> illegalPredicate) {
        if (minDistanceAboveIllegal < 0) {
            throw new IllegalArgumentException("aboveSolidAmount was " + minDistanceAboveIllegal + ", expected >= 0");
        }
        if (illegalPredicate.test(pos)) {
            BlockPos blockPos = pos.up();
            while (blockPos.getY() < maxOffset && illegalPredicate.test(blockPos)) {
                blockPos = blockPos.up();
            }
            _snowman = blockPos;
            while (_snowman.getY() < maxOffset && _snowman.getY() - blockPos.getY() < minDistanceAboveIllegal && !illegalPredicate.test(_snowman = _snowman.up())) {
                _snowman = _snowman;
            }
            return _snowman;
        }
        return pos;
    }
}

