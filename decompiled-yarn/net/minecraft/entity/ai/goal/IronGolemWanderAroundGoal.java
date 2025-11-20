package net.minecraft.entity.ai.goal;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

public class IronGolemWanderAroundGoal extends WanderAroundGoal {
   public IronGolemWanderAroundGoal(PathAwareEntity _snowman, double _snowman) {
      super(_snowman, _snowman, 240, false);
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      float _snowman = this.mob.world.random.nextFloat();
      if (this.mob.world.random.nextFloat() < 0.3F) {
         return this.method_27925();
      } else {
         Vec3d _snowmanx;
         if (_snowman < 0.7F) {
            _snowmanx = this.method_27926();
            if (_snowmanx == null) {
               _snowmanx = this.method_27927();
            }
         } else {
            _snowmanx = this.method_27927();
            if (_snowmanx == null) {
               _snowmanx = this.method_27926();
            }
         }

         return _snowmanx == null ? this.method_27925() : _snowmanx;
      }
   }

   @Nullable
   private Vec3d method_27925() {
      return TargetFinder.findGroundTarget(this.mob, 10, 7);
   }

   @Nullable
   private Vec3d method_27926() {
      ServerWorld _snowman = (ServerWorld)this.mob.world;
      List<VillagerEntity> _snowmanx = _snowman.getEntitiesByType(EntityType.VILLAGER, this.mob.getBoundingBox().expand(32.0), this::method_27922);
      if (_snowmanx.isEmpty()) {
         return null;
      } else {
         VillagerEntity _snowmanxx = _snowmanx.get(this.mob.world.random.nextInt(_snowmanx.size()));
         Vec3d _snowmanxxx = _snowmanxx.getPos();
         return TargetFinder.method_27929(this.mob, 10, 7, _snowmanxxx);
      }
   }

   @Nullable
   private Vec3d method_27927() {
      ChunkSectionPos _snowman = this.method_27928();
      if (_snowman == null) {
         return null;
      } else {
         BlockPos _snowmanx = this.method_27923(_snowman);
         return _snowmanx == null ? null : TargetFinder.method_27929(this.mob, 10, 7, Vec3d.ofBottomCenter(_snowmanx));
      }
   }

   @Nullable
   private ChunkSectionPos method_27928() {
      ServerWorld _snowman = (ServerWorld)this.mob.world;
      List<ChunkSectionPos> _snowmanx = ChunkSectionPos.stream(ChunkSectionPos.from(this.mob), 2)
         .filter(_snowmanxx -> _snowman.getOccupiedPointOfInterestDistance(_snowmanxx) == 0)
         .collect(Collectors.toList());
      return _snowmanx.isEmpty() ? null : _snowmanx.get(_snowman.random.nextInt(_snowmanx.size()));
   }

   @Nullable
   private BlockPos method_27923(ChunkSectionPos _snowman) {
      ServerWorld _snowmanx = (ServerWorld)this.mob.world;
      PointOfInterestStorage _snowmanxx = _snowmanx.getPointOfInterestStorage();
      List<BlockPos> _snowmanxxx = _snowmanxx.getInCircle(_snowmanxxxx -> true, _snowman.getCenterPos(), 8, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
         .map(PointOfInterest::getPos)
         .collect(Collectors.toList());
      return _snowmanxxx.isEmpty() ? null : _snowmanxxx.get(_snowmanx.random.nextInt(_snowmanxxx.size()));
   }

   private boolean method_27922(VillagerEntity _snowman) {
      return _snowman.canSummonGolem(this.mob.world.getTime());
   }
}
