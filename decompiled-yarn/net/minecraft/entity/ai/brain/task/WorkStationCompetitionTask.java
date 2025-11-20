package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

public class WorkStationCompetitionTask extends Task<VillagerEntity> {
   final VillagerProfession profession;

   public WorkStationCompetitionTask(VillagerProfession profession) {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.MOBS, MemoryModuleState.VALUE_PRESENT));
      this.profession = profession;
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      GlobalPos _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).get();
      _snowman.getPointOfInterestStorage()
         .getType(_snowmanxxx.getPos())
         .ifPresent(
            _snowmanxxxx -> LookTargetUtil.streamSeenVillagers(_snowman, _snowmanxxxxxx -> this.isUsingWorkStationAt(_snowman, _snowmanxxx, _snowmanxxxxxx))
                  .reduce(_snowman, WorkStationCompetitionTask::keepJobSiteForMoreExperiencedVillager)
         );
   }

   private static VillagerEntity keepJobSiteForMoreExperiencedVillager(VillagerEntity first, VillagerEntity second) {
      VillagerEntity _snowman;
      VillagerEntity _snowmanx;
      if (first.getExperience() > second.getExperience()) {
         _snowman = first;
         _snowmanx = second;
      } else {
         _snowman = second;
         _snowmanx = first;
      }

      _snowmanx.getBrain().forget(MemoryModuleType.JOB_SITE);
      return _snowman;
   }

   private boolean isUsingWorkStationAt(GlobalPos pos, PointOfInterestType poiType, VillagerEntity villager) {
      return this.hasJobSite(villager)
         && pos.equals(villager.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).get())
         && this.isCompletedWorkStation(poiType, villager.getVillagerData().getProfession());
   }

   private boolean isCompletedWorkStation(PointOfInterestType poiType, VillagerProfession profession) {
      return profession.getWorkStation().getCompletionCondition().test(poiType);
   }

   private boolean hasJobSite(VillagerEntity villager) {
      return villager.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).isPresent();
   }
}
