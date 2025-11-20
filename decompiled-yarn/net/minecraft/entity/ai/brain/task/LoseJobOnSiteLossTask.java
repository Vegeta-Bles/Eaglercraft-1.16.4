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

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      VillagerData _snowmanxx = _snowman.getVillagerData();
      return _snowmanxx.getProfession() != VillagerProfession.NONE
         && _snowmanxx.getProfession() != VillagerProfession.NITWIT
         && _snowman.getExperience() == 0
         && _snowmanxx.getLevel() <= 1;
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      _snowman.setVillagerData(_snowman.getVillagerData().withProfession(VillagerProfession.NONE));
      _snowman.reinitializeBrain(_snowman);
   }
}
