package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class SeekSkyAfterRaidWinTask extends SeekSkyTask {
   public SeekSkyAfterRaidWinTask(float _snowman) {
      super(_snowman);
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      Raid _snowman = world.getRaidAt(entity.getBlockPos());
      return _snowman != null && _snowman.hasWon() && super.shouldRun(world, entity);
   }
}
