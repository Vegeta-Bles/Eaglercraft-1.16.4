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
         Brain<?> _snowman = entity.getBrain();
         GlobalPos _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.HOME).get();
         if (world.getRegistryKey() != _snowmanx.getDimension()) {
            return false;
         } else {
            Optional<Long> _snowmanxx = _snowman.getOptionalMemory(MemoryModuleType.LAST_WOKEN);
            if (_snowmanxx.isPresent()) {
               long _snowmanxxx = world.getTime() - _snowmanxx.get();
               if (_snowmanxxx > 0L && _snowmanxxx < 100L) {
                  return false;
               }
            }

            BlockState _snowmanxxx = world.getBlockState(_snowmanx.getPos());
            return _snowmanx.getPos().isWithinDistance(entity.getPos(), 2.0) && _snowmanxxx.getBlock().isIn(BlockTags.BEDS) && !_snowmanxxx.get(BedBlock.OCCUPIED);
         }
      }
   }

   @Override
   protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
      Optional<GlobalPos> _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
      if (!_snowman.isPresent()) {
         return false;
      } else {
         BlockPos _snowmanx = _snowman.get().getPos();
         return entity.getBrain().hasActivity(Activity.REST) && entity.getY() > (double)_snowmanx.getY() + 0.4 && _snowmanx.isWithinDistance(entity.getPos(), 1.14);
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
