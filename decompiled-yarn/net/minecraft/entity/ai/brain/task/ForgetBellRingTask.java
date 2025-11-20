package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ForgetBellRingTask extends Task<LivingEntity> {
   private final int distance;
   private final int maxHiddenTicks;
   private int hiddenTicks;

   public ForgetBellRingTask(int maxHiddenSeconds, int distance) {
      super(ImmutableMap.of(MemoryModuleType.HIDING_PLACE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.HEARD_BELL_TIME, MemoryModuleState.VALUE_PRESENT));
      this.maxHiddenTicks = maxHiddenSeconds * 20;
      this.hiddenTicks = 0;
      this.distance = distance;
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      Optional<Long> _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.HEARD_BELL_TIME);
      boolean _snowmanxx = _snowmanx.get() + 300L <= time;
      if (this.hiddenTicks <= this.maxHiddenTicks && !_snowmanxx) {
         BlockPos _snowmanxxx = _snowman.getOptionalMemory(MemoryModuleType.HIDING_PLACE).get().getPos();
         if (_snowmanxxx.isWithinDistance(entity.getBlockPos(), (double)this.distance)) {
            this.hiddenTicks++;
         }
      } else {
         _snowman.forget(MemoryModuleType.HEARD_BELL_TIME);
         _snowman.forget(MemoryModuleType.HIDING_PLACE);
         _snowman.refreshActivities(world.getTimeOfDay(), world.getTime());
         this.hiddenTicks = 0;
      }
   }
}
