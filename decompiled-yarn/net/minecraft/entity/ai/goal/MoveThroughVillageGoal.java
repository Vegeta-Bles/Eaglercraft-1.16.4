package net.minecraft.entity.ai.goal;

import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.class_5493;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

public class MoveThroughVillageGoal extends Goal {
   protected final PathAwareEntity mob;
   private final double speed;
   private Path targetPath;
   private BlockPos target;
   private final boolean requiresNighttime;
   private final List<BlockPos> visitedTargets = Lists.newArrayList();
   private final int distance;
   private final BooleanSupplier doorPassingThroughGetter;

   public MoveThroughVillageGoal(PathAwareEntity _snowman, double speed, boolean requiresNighttime, int distance, BooleanSupplier doorPassingThroughGetter) {
      this.mob = _snowman;
      this.speed = speed;
      this.requiresNighttime = requiresNighttime;
      this.distance = distance;
      this.doorPassingThroughGetter = doorPassingThroughGetter;
      this.setControls(EnumSet.of(Goal.Control.MOVE));
      if (!class_5493.method_30955(_snowman)) {
         throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
      }
   }

   @Override
   public boolean canStart() {
      if (!class_5493.method_30955(this.mob)) {
         return false;
      } else {
         this.forgetOldTarget();
         if (this.requiresNighttime && this.mob.world.isDay()) {
            return false;
         } else {
            ServerWorld _snowman = (ServerWorld)this.mob.world;
            BlockPos _snowmanx = this.mob.getBlockPos();
            if (!_snowman.isNearOccupiedPointOfInterest(_snowmanx, 6)) {
               return false;
            } else {
               Vec3d _snowmanxx = TargetFinder.findGroundTarget(
                  this.mob,
                  15,
                  7,
                  _snowmanxxx -> {
                     if (!_snowman.isNearOccupiedPointOfInterest(_snowmanxxx)) {
                        return Double.NEGATIVE_INFINITY;
                     } else {
                        Optional<BlockPos> _snowmanxxxx = _snowman.getPointOfInterestStorage()
                           .getPosition(PointOfInterestType.ALWAYS_TRUE, this::shouldVisit, _snowmanxxx, 10, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED);
                        return !_snowmanxxxx.isPresent() ? Double.NEGATIVE_INFINITY : -_snowmanxxxx.get().getSquaredDistance(_snowman);
                     }
                  }
               );
               if (_snowmanxx == null) {
                  return false;
               } else {
                  Optional<BlockPos> _snowmanxxx = _snowman.getPointOfInterestStorage()
                     .getPosition(
                        PointOfInterestType.ALWAYS_TRUE, this::shouldVisit, new BlockPos(_snowmanxx), 10, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED
                     );
                  if (!_snowmanxxx.isPresent()) {
                     return false;
                  } else {
                     this.target = _snowmanxxx.get().toImmutable();
                     MobNavigation _snowmanxxxx = (MobNavigation)this.mob.getNavigation();
                     boolean _snowmanxxxxx = _snowmanxxxx.canEnterOpenDoors();
                     _snowmanxxxx.setCanPathThroughDoors(this.doorPassingThroughGetter.getAsBoolean());
                     this.targetPath = _snowmanxxxx.findPathTo(this.target, 0);
                     _snowmanxxxx.setCanPathThroughDoors(_snowmanxxxxx);
                     if (this.targetPath == null) {
                        Vec3d _snowmanxxxxxx = TargetFinder.findTargetTowards(this.mob, 10, 7, Vec3d.ofBottomCenter(this.target));
                        if (_snowmanxxxxxx == null) {
                           return false;
                        }

                        _snowmanxxxx.setCanPathThroughDoors(this.doorPassingThroughGetter.getAsBoolean());
                        this.targetPath = this.mob.getNavigation().findPathTo(_snowmanxxxxxx.x, _snowmanxxxxxx.y, _snowmanxxxxxx.z, 0);
                        _snowmanxxxx.setCanPathThroughDoors(_snowmanxxxxx);
                        if (this.targetPath == null) {
                           return false;
                        }
                     }

                     for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < this.targetPath.getLength(); _snowmanxxxxxxx++) {
                        PathNode _snowmanxxxxxxxx = this.targetPath.getNode(_snowmanxxxxxxx);
                        BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowmanxxxxxxxx.x, _snowmanxxxxxxxx.y + 1, _snowmanxxxxxxxx.z);
                        if (DoorBlock.isWoodenDoor(this.mob.world, _snowmanxxxxxxxxx)) {
                           this.targetPath = this.mob.getNavigation().findPathTo((double)_snowmanxxxxxxxx.x, (double)_snowmanxxxxxxxx.y, (double)_snowmanxxxxxxxx.z, 0);
                           break;
                        }
                     }

                     return this.targetPath != null;
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      return this.mob.getNavigation().isIdle() ? false : !this.target.isWithinDistance(this.mob.getPos(), (double)(this.mob.getWidth() + (float)this.distance));
   }

   @Override
   public void start() {
      this.mob.getNavigation().startMovingAlong(this.targetPath, this.speed);
   }

   @Override
   public void stop() {
      if (this.mob.getNavigation().isIdle() || this.target.isWithinDistance(this.mob.getPos(), (double)this.distance)) {
         this.visitedTargets.add(this.target);
      }
   }

   private boolean shouldVisit(BlockPos pos) {
      for (BlockPos _snowman : this.visitedTargets) {
         if (Objects.equals(pos, _snowman)) {
            return false;
         }
      }

      return true;
   }

   private void forgetOldTarget() {
      if (this.visitedTargets.size() > 15) {
         this.visitedTargets.remove(0);
      }
   }
}
