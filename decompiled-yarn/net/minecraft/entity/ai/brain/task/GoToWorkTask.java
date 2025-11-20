package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;

public class GoToWorkTask extends Task<VillagerEntity> {
   public GoToWorkTask() {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleState.VALUE_PRESENT));
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      BlockPos _snowmanxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
      return _snowmanxx.isWithinDistance(_snowman.getPos(), 2.0) || _snowman.isNatural();
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      GlobalPos _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
      _snowman.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
      _snowman.getBrain().remember(MemoryModuleType.JOB_SITE, _snowmanxxx);
      _snowman.sendEntityStatus(_snowman, (byte)14);
      if (_snowman.getVillagerData().getProfession() == VillagerProfession.NONE) {
         MinecraftServer _snowmanxxxx = _snowman.getServer();
         Optional.ofNullable(_snowmanxxxx.getWorld(_snowmanxxx.getDimension()))
            .flatMap(_snowmanxxxxx -> _snowmanxxxxx.getPointOfInterestStorage().getType(_snowman.getPos()))
            .flatMap(_snowmanxxxxx -> Registry.VILLAGER_PROFESSION.stream().filter(_snowmanxxxxxx -> _snowmanxxxxxx.getWorkStation() == _snowmanxxx).findFirst())
            .ifPresent(_snowmanxxxxx -> {
               _snowman.setVillagerData(_snowman.getVillagerData().withProfession(_snowmanxxxxx));
               _snowman.reinitializeBrain(_snowman);
            });
      }
   }
}
