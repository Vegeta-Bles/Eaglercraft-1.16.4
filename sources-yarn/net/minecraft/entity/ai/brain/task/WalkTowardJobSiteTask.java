package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;

public class WalkTowardJobSiteTask extends Task<VillagerEntity> {
   final float speed;

   public WalkTowardJobSiteTask(float speed) {
      super(ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryModuleState.VALUE_PRESENT), 1200);
      this.speed = speed;
   }

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      return arg2.getBrain()
         .getFirstPossibleNonCoreActivity()
         .map(argx -> argx == Activity.IDLE || argx == Activity.WORK || argx == Activity.PLAY)
         .orElse(true);
   }

   protected boolean shouldKeepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      return arg2.getBrain().hasMemoryModule(MemoryModuleType.POTENTIAL_JOB_SITE);
   }

   protected void keepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      LookTargetUtil.walkTowards(arg2, arg2.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos(), this.speed, 1);
   }

   protected void finishRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      Optional<GlobalPos> optional = arg2.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      optional.ifPresent(arg2x -> {
         BlockPos lv = arg2x.getPos();
         ServerWorld lv2 = arg.getServer().getWorld(arg2x.getDimension());
         if (lv2 != null) {
            PointOfInterestStorage lv3 = lv2.getPointOfInterestStorage();
            if (lv3.test(lv, argxx -> true)) {
               lv3.releaseTicket(lv);
            }

            DebugInfoSender.sendPointOfInterest(arg, lv);
         }
      });
      arg2.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}
