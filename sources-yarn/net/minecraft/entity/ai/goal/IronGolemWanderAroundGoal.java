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
   public IronGolemWanderAroundGoal(PathAwareEntity arg, double d) {
      super(arg, d, 240, false);
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      float f = this.mob.world.random.nextFloat();
      if (this.mob.world.random.nextFloat() < 0.3F) {
         return this.method_27925();
      } else {
         Vec3d lv;
         if (f < 0.7F) {
            lv = this.method_27926();
            if (lv == null) {
               lv = this.method_27927();
            }
         } else {
            lv = this.method_27927();
            if (lv == null) {
               lv = this.method_27926();
            }
         }

         return lv == null ? this.method_27925() : lv;
      }
   }

   @Nullable
   private Vec3d method_27925() {
      return TargetFinder.findGroundTarget(this.mob, 10, 7);
   }

   @Nullable
   private Vec3d method_27926() {
      ServerWorld lv = (ServerWorld)this.mob.world;
      List<VillagerEntity> list = lv.getEntitiesByType(EntityType.VILLAGER, this.mob.getBoundingBox().expand(32.0), this::method_27922);
      if (list.isEmpty()) {
         return null;
      } else {
         VillagerEntity lv2 = list.get(this.mob.world.random.nextInt(list.size()));
         Vec3d lv3 = lv2.getPos();
         return TargetFinder.method_27929(this.mob, 10, 7, lv3);
      }
   }

   @Nullable
   private Vec3d method_27927() {
      ChunkSectionPos lv = this.method_27928();
      if (lv == null) {
         return null;
      } else {
         BlockPos lv2 = this.method_27923(lv);
         return lv2 == null ? null : TargetFinder.method_27929(this.mob, 10, 7, Vec3d.ofBottomCenter(lv2));
      }
   }

   @Nullable
   private ChunkSectionPos method_27928() {
      ServerWorld lv = (ServerWorld)this.mob.world;
      List<ChunkSectionPos> list = ChunkSectionPos.stream(ChunkSectionPos.from(this.mob), 2)
         .filter(arg2 -> lv.getOccupiedPointOfInterestDistance(arg2) == 0)
         .collect(Collectors.toList());
      return list.isEmpty() ? null : list.get(lv.random.nextInt(list.size()));
   }

   @Nullable
   private BlockPos method_27923(ChunkSectionPos arg) {
      ServerWorld lv = (ServerWorld)this.mob.world;
      PointOfInterestStorage lv2 = lv.getPointOfInterestStorage();
      List<BlockPos> list = lv2.getInCircle(argx -> true, arg.getCenterPos(), 8, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED)
         .map(PointOfInterest::getPos)
         .collect(Collectors.toList());
      return list.isEmpty() ? null : list.get(lv.random.nextInt(list.size()));
   }

   private boolean method_27922(VillagerEntity arg) {
      return arg.canSummonGolem(this.mob.world.getTime());
   }
}
