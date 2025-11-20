package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class SleepTask extends Task<LivingEntity> {
   private long startTime;

   public SleepTask() {
      super(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LAST_WOKEN, MemoryModuleState.REGISTERED));
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      if (entity.hasVehicle()) {
         return false;
      } else {
         Brain<?> lv = entity.getBrain();
         GlobalPos lv2 = lv.getOptionalMemory(MemoryModuleType.HOME).get();
         if (world.getRegistryKey() != lv2.getDimension()) {
            return false;
         } else {
            Optional<Long> optional = lv.getOptionalMemory(MemoryModuleType.LAST_WOKEN);
            if (optional.isPresent()) {
               long l = world.getTime() - optional.get();
               if (l > 0L && l < 100L) {
                  return false;
               }
            }

            BlockState lv3 = world.getBlockState(lv2.getPos());
            return lv2.getPos().isWithinDistance(entity.getPos(), 2.0) && lv3.getBlock().isIn(BlockTags.BEDS) && !lv3.get(BedBlock.OCCUPIED);
         }
      }
   }

   @Override
   protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
      Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
      if (!optional.isPresent()) {
         return false;
      } else {
         BlockPos lv = optional.get().getPos();
         return entity.getBrain().hasActivity(Activity.REST) && entity.getY() > (double)lv.getY() + 0.4 && lv.isWithinDistance(entity.getPos(), 1.14);
      }
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      if (time > this.startTime) {
         OpenDoorsTask.method_30760(world, entity, null, null);
         entity.sleep(entity.getBrain().getOptionalMemory(MemoryModuleType.HOME).get().getPos());
      }
   }

   @Override
   protected boolean isTimeLimitExceeded(long time) {
      return false;
   }

   @Override
   protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
      if (entity.isSleeping()) {
         entity.wakeUp();
         this.startTime = time + 40L;
      }
   }
}
