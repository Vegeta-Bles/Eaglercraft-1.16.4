package net.minecraft.entity.ai.brain.task;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.raid.Raid;

public class RunAroundAfterRaidTask extends FindWalkTargetTask {
   public RunAroundAfterRaidTask(float _snowman) {
      super(_snowman);
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      Raid _snowmanxx = _snowman.getRaidAt(_snowman.getBlockPos());
      return _snowmanxx != null && _snowmanxx.hasWon() && super.shouldRun(_snowman, _snowman);
   }
}
