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

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      return _snowman.getBrain().getFirstPossibleNonCoreActivity().map(_snowmanxx -> _snowmanxx == Activity.IDLE || _snowmanxx == Activity.WORK || _snowmanxx == Activity.PLAY).orElse(true);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return _snowman.getBrain().hasMemoryModule(MemoryModuleType.POTENTIAL_JOB_SITE);
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      LookTargetUtil.walkTowards(_snowman, _snowman.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get().getPos(), this.speed, 1);
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Optional<GlobalPos> _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
      _snowmanxxx.ifPresent(_snowmanxxxxxxx -> {
         BlockPos _snowmanxx = _snowmanxxxxxxx.getPos();
         ServerWorld _snowmanxxx = _snowman.getServer().getWorld(_snowmanxxxxxxx.getDimension());
         if (_snowmanxxx != null) {
            PointOfInterestStorage _snowmanxxxx = _snowmanxxx.getPointOfInterestStorage();
            if (_snowmanxxxx.test(_snowmanxx, _snowmanxxxxxxxx -> true)) {
               _snowmanxxxx.releaseTicket(_snowmanxx);
            }

            DebugInfoSender.sendPointOfInterest(_snowman, _snowmanxx);
         }
      });
      _snowman.getBrain().forget(MemoryModuleType.POTENTIAL_JOB_SITE);
   }
}
