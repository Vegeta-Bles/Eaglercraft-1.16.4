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

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      BlockPos lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos();
      return lv.isWithinDistance(arg2.getPos(), 2.0) || arg2.isNatural();
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      GlobalPos lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
      arg2.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
      arg2.getBrain().remember(MemoryModuleType.JOB_SITE, lv);
      arg.sendEntityStatus(arg2, (byte)14);
      if (arg2.getVillagerData().getProfession() == VillagerProfession.NONE) {
         MinecraftServer minecraftServer = arg.getServer();
         Optional.ofNullable(minecraftServer.getWorld(lv.getDimension()))
            .flatMap(arg2x -> arg2x.getPointOfInterestStorage().getType(lv.getPos()))
            .flatMap(argx -> Registry.VILLAGER_PROFESSION.stream().filter(arg2x -> arg2x.getWorkStation() == argx).findFirst())
            .ifPresent(arg3 -> {
               arg2.setVillagerData(arg2.getVillagerData().withProfession(arg3));
               arg2.reinitializeBrain(arg);
            });
      }
   }
}
