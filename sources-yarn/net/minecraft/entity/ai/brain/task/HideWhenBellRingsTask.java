package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class HideWhenBellRingsTask extends Task<LivingEntity> {
   public HideWhenBellRingsTask() {
      super(ImmutableMap.of(MemoryModuleType.HEARD_BELL_TIME, MemoryModuleState.VALUE_PRESENT));
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> lv = entity.getBrain();
      Raid lv2 = world.getRaidAt(entity.getBlockPos());
      if (lv2 == null) {
         lv.doExclusively(Activity.HIDE);
      }
   }
}
