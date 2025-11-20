package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class TakeJobSiteTask extends Task<VillagerEntity> {
   private final float speed;

   public TakeJobSiteTask(float speed) {
      super(
         ImmutableMap.of(
            MemoryModuleType.POTENTIAL_JOB_SITE,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.JOB_SITE,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.MOBS,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.speed = speed;
   }

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      return arg2.isBaby() ? false : arg2.getVillagerData().getProfession() == VillagerProfession.NONE;
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      BlockPos lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
      Optional<PointOfInterestType> optional = arg.getPointOfInterestStorage().getType(lv);
      if (optional.isPresent()) {
         LookTargetUtil.streamSeenVillagers(arg2, arg2x -> this.canUseJobSite(optional.get(), arg2x, lv))
            .findFirst()
            .ifPresent(arg4 -> this.claimSite(arg, arg2, arg4, lv, arg4.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).isPresent()));
      }
   }

   private boolean canUseJobSite(PointOfInterestType poiType, VillagerEntity villager, BlockPos pos) {
      boolean bl = villager.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).isPresent();
      if (bl) {
         return false;
      } else {
         Optional<GlobalPos> optional = villager.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
         VillagerProfession lv = villager.getVillagerData().getProfession();
         if (villager.getVillagerData().getProfession() == VillagerProfession.NONE || !lv.getWorkStation().getCompletionCondition().test(poiType)) {
            return false;
         } else {
            return !optional.isPresent() ? this.canReachJobSite(villager, pos, poiType) : optional.get().getPos().equals(pos);
         }
      }
   }

   private void claimSite(ServerWorld world, VillagerEntity previousOwner, VillagerEntity newOwner, BlockPos pos, boolean jobSitePresent) {
      this.forgetJobSiteAndWalkTarget(previousOwner);
      if (!jobSitePresent) {
         LookTargetUtil.walkTowards(newOwner, pos, this.speed, 1);
         newOwner.getBrain().remember(MemoryModuleType.POTENTIAL_JOB_SITE, GlobalPos.create(world.getRegistryKey(), pos));
         DebugInfoSender.sendPointOfInterest(world, pos);
      }
   }

   private boolean canReachJobSite(VillagerEntity villager, BlockPos pos, PointOfInterestType poiType) {
      Path lv = villager.getNavigation().findPathTo(pos, poiType.getSearchDistance());
      return lv != null && lv.reachesTarget();
   }

   private void forgetJobSiteAndWalkTarget(VillagerEntity villager) {
      villager.getBrain().forget(MemoryModuleType.WALK_TARGET);
      villager.getBrain().forget(MemoryModuleType.LOOK_TARGET);
      villager.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}
