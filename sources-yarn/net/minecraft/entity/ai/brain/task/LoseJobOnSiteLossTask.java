package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;

public class LoseJobOnSiteLossTask extends Task<VillagerEntity> {
   public LoseJobOnSiteLossTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_ABSENT));
   }

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      VillagerData lv = arg2.getVillagerData();
      return lv.getProfession() != VillagerProfession.NONE
         && lv.getProfession() != VillagerProfession.NITWIT
         && arg2.getExperience() == 0
         && lv.getLevel() <= 1;
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      arg2.setVillagerData(arg2.getVillagerData().withProfession(VillagerProfession.NONE));
      arg2.reinitializeBrain(arg);
   }
}
