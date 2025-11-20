package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class EndRaidTask extends Task<LivingEntity> {
   public EndRaidTask() {
      super(ImmutableMap.of());
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      return world.random.nextInt(20) == 0;
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      Raid _snowmanx = world.getRaidAt(entity.getBlockPos());
      if (_snowmanx == null || _snowmanx.hasStopped() || _snowmanx.hasLost()) {
         _snowman.setDefaultActivity(Activity.IDLE);
         _snowman.refreshActivities(world.getTimeOfDay(), world.getTime());
      }
   }
}
