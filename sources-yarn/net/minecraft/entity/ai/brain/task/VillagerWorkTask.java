package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

public class VillagerWorkTask extends Task<VillagerEntity> {
   private long lastCheckedTime;

   public VillagerWorkTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED));
   }

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      if (arg.getTime() - this.lastCheckedTime < 300L) {
         return false;
      } else if (arg.random.nextInt(2) != 0) {
         return false;
      } else {
         this.lastCheckedTime = arg.getTime();
         GlobalPos lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).get();
         return lv.getDimension() == arg.getRegistryKey() && lv.getPos().isWithinDistance(arg2.getPos(), 1.73);
      }
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      Brain<VillagerEntity> lv = arg2.getBrain();
      lv.remember(MemoryModuleType.LAST_WORKED_AT_POI, l);
      lv.getOptionalMemory(MemoryModuleType.JOB_SITE).ifPresent(arg2x -> lv.remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(arg2x.getPos())));
      arg2.playWorkSound();
      this.performAdditionalWork(arg, arg2);
      if (arg2.shouldRestock()) {
         arg2.restock();
      }
   }

   protected void performAdditionalWork(ServerWorld world, VillagerEntity entity) {
   }

   protected boolean shouldKeepRunning(ServerWorld arg, VillagerEntity arg2, long l) {
      Optional<GlobalPos> optional = arg2.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
      if (!optional.isPresent()) {
         return false;
      } else {
         GlobalPos lv = optional.get();
         return lv.getDimension() == arg.getRegistryKey() && lv.getPos().isWithinDistance(arg2.getPos(), 1.73);
      }
   }
}
