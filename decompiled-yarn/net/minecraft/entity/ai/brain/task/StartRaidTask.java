package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class StartRaidTask extends Task<LivingEntity> {
   public StartRaidTask() {
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
      if (_snowmanx != null) {
         if (_snowmanx.hasSpawned() && !_snowmanx.isPreRaid()) {
            _snowman.setDefaultActivity(Activity.RAID);
            _snowman.doExclusively(Activity.RAID);
         } else {
            _snowman.setDefaultActivity(Activity.PRE_RAID);
            _snowman.doExclusively(Activity.PRE_RAID);
         }
      }
   }
}
